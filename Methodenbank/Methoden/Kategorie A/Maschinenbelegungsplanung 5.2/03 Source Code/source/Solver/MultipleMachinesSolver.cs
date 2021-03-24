using System;
using System.Threading;
using System.Diagnostics;
using System.IO;
using System.Data;
using System.Drawing;
using System.Resources;
using System.Reflection;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Drawing.Drawing2D;
using System.Drawing.Printing;
using System.Xml.Serialization;
using System.Text.RegularExpressions;


namespace Maschinenbelegungsplanung
{
	public class MultipleMachinesSolver : ISolver
	{
		#region private members
		private auftrag[] auftragsListe = null;
		private periode[] periodenListe = null;
		private AusgabeDaten rValue;
		private string solverPathLP;
        private string solverPathGLPK;
		private string workDir;
		private int anzahlAuftraege;
		private int anzahlPerioden;
		private int anzahlMaschinen;
		private string solver; // Für den ersten Prototyp: nur LP-Solve
		private Thread tCreateBatchFile;
		private Thread tRunSolver;
		private Thread tOpenOutFile;
		#endregion
		
		#region default-constructor
		public MultipleMachinesSolver() 
		{ 
			// do nothing
		}
		#endregion

		#region constructor
		/// <summary>
		/// Konstruktor
		/// </summary>
		/// <param name="inEingabeDatenParam">
		/// Enthält die zu verarbeitenden Eingabedaten</param>
		/// <param name="inChosenSolver">
		/// string: in unsererer ersten Implementierung nur LP-Solve</param>
		/// <param name="inSolverPathLPParam">
		/// Pfadangabe, wo sich LP-Solve befindet</param>
		/// <param name="inWorkDirParam">
		/// Pfadangabe, des Arbeitsverzeichnises des Benutzers</param>
		public MultipleMachinesSolver(eingabeDaten inEingabeDatenParam, String inChosenSolver, String inSolverPathLPParam, String inSolverPathGLPKParam, String inWorkDirParam, int inAnzahlMaschinen )
		{
			auftragsListe = inEingabeDatenParam.auftragsListe;
			periodenListe = inEingabeDatenParam.periodenListe;
			anzahlAuftraege = auftragsListe.Length;
			anzahlPerioden  = periodenListe.Length;
			anzahlMaschinen = inAnzahlMaschinen;
			solver    = inChosenSolver;
			solverPathLP = inSolverPathLPParam;
            solverPathGLPK = inSolverPathGLPKParam;
			workDir = inWorkDirParam;
            Console.Out.WriteLine(solver);
			rValue = new AusgabeDaten(anzahlAuftraege, anzahlPerioden, anzahlMaschinen);
		}
		#endregion

		#region ISolver:createMPS()
		/// <summary>
		/// Auslesen der Daten aus den Arrays
		///  auftraege[] und perioden[] und
		///  erstellen der MPS Datei, welche
		///  dann an LP-Solve übergeben wird.
		/// </summary>
		public override void createMPS( )
		{
			// LP Modell erstellen, welches später in eine MPS Datei umgewandelt werden kann
			// ein LPModell besteht aus genau einer Zielfunktion und einer oder mehrerern Restriktionen
			// eine Zielfunktion besteht aus Polynomen und einem Typ ("max", "min");
			// eine Restriktion besteht aus Polynomen, einem Typ ("<=", ">=", "=") und einem Wert

			// Variabledefinition Xa1p3 bedeutet, Xa1b1 Minuten von Auftrag 1 in Periode 3

			LPModell lp = null;
			Zielfunktion zf = null;
			Restriktion rs = null;
			Restriktion[] rsArray = null;
			ArrayList rsAL = null;
			Polynom poly = null;
			Polynom[] polyArray = null;
			ArrayList polyAL = null;

			// 1. Zielfunktion erstellen
			polyArray = new Polynom[1];
			polyArray[0] = new Polynom(1,"Y");
			zf = new Zielfunktion(polyArray, "min");

			// 2. Restriktionen erstellen
			rsAL = new ArrayList();

			// 2.1 Restriktionen für Perioden in denen der Auftrag möglich wäre.
			// Jeder Schleifendurchgang ergibt eine neue Restriktion.
			for (int a=0; a<auftragsListe.Length; a++) // a wie Auftrag
			{
				polyAL = new ArrayList();
				int ap=auftragsListe[a].Beginn;		// ap wie AuftragsPeriode
				// Restriktion erstellen
				while (ap <= auftragsListe[a].Ende)
				{
					for (int typ = 0; typ < anzahlMaschinen; typ++)
					{
						if(auftragsListe[a].Typen[typ] != 0)
						{
							poly = new Polynom(1,"A" +auftragsListe[a].Nummer +"P" +ap + "T" + (typ+1).ToString());
							polyAL.Add(poly);
						}
					}
					ap++;
				}

				// Restriktion speichern
				polyArray = new Polynom[polyAL.Count];
				for (int z=0; z<polyArray.Length; z++)	// Umwandlung ArrayList -> Array
				{
					polyArray[z] = (Polynom)polyAL[z];
				}				
				rs = new Restriktion(polyArray, "=", auftragsListe[a].Dauer);
				rsAL.Add(rs);
			}

			// 2.2 Restriktionen für periodenorientierte Glättung.
			// Jeder Schleifendurchgang ergibt eine neue Restriktion.
			for (int typ = 0; typ < anzahlMaschinen; typ++)
			{
				for (int p=0; p<periodenListe.Length; p++) // p wie Periode
				{
					polyAL = new ArrayList();
				
					if (periodenListe[p].Typen[typ] != 0 )
					{
						for (int a=0; a<auftragsListe.Length; a++) // a wie Auftrag
						{
							if ((periodenListe[p].Nummer >= auftragsListe[a].Beginn) && (periodenListe[p].Nummer <= auftragsListe[a].Ende))
							{
								if(auftragsListe[a].Typen[typ] != 0)
								{
									// dann diesen Auftrag als Polynom in Restriktion aufnehmen
									poly = new Polynom(1,"A" +auftragsListe[a].Nummer +"P" +periodenListe[p].Nummer + "T" + (typ+1).ToString());
									polyAL.Add(poly);
								}
							}
						}
				
						// Anzahl Maschinen * Y abziehen
						polyAL.Add(new Polynom(-1*(periodenListe[p].Typen[typ]),"Y"));
				
						// Restriktion speichern
						polyArray = new Polynom[polyAL.Count];
						for (int z=0; z<polyArray.Length; z++)	// Umwandlung ArrayList -> Array
						{
							polyArray[z] = (Polynom)polyAL[z];
						}
						rs = new Restriktion(polyArray, "<=", 0);
						rsAL.Add(rs);
					}
				}
			}
			
			// 2.3 Restriktionen bezüglich der zur Verfügung stehenden Kapazitäten.
			// Jeder Schleifendurchgang ergibt eine neue Restriktion.
			// Dieser Teil ist 2.2 fast gleich. 
			// Unterschiede: hier wird die Anzahl Maschinen in der Restriktion nicht abgezogen und der RHS Wert ist nicht 0
			for (int typ = 0; typ < anzahlMaschinen; typ++)
			{		
				for (int p=0; p<periodenListe.Length; p++)
				{
					polyAL = new ArrayList();
				
					if (periodenListe[p].Typen[typ] != 0 )
					{
						for (int a=0; a<auftragsListe.Length; a++) // a wie Auftrag
						{
							if (periodenListe[p].Nummer >= auftragsListe[a].Beginn && periodenListe[p].Nummer <= auftragsListe[a].Ende)
							{
								if(auftragsListe[a].Typen[typ] != 0)
								{
									// dann diesen Auftrag als Polynom in Restriktion aufnehmen
									poly = new Polynom(1,"A" +auftragsListe[a].Nummer +"P" +periodenListe[p].Nummer + "T" + (typ+1).ToString());
									polyAL.Add(poly);
								}
							}
						}
					}
		
					// Restriktion speichern
					polyArray = new Polynom[polyAL.Count];
					for (int z=0; z<polyArray.Length; z++)	// Umwandlung ArrayList -> Array
					{
						polyArray[z] = (Polynom)polyAL[z];
					}
					rs = new Restriktion(polyArray, "<=", periodenListe[p].Typen[typ]*60);	// 60: 60 Minuten als eine Maschinenkapazitätseinheit
					rsAL.Add(rs);
				}
			}

			// 3. Schaltervariablen für Typen einpflegen
			for (int p=0; p<periodenListe.Length; p++)
			{
				for (int a=0; a<auftragsListe.Length; a++) // a wie Auftrag
				{
					if (periodenListe[p].Nummer >= auftragsListe[a].Beginn && periodenListe[p].Nummer <= auftragsListe[a].Ende)
					{
						ArrayList Maschinen = new ArrayList();
						for (int i = 0; i<anzahlMaschinen; i++) 
						{
							if (auftragsListe[a].Typen[i] == 1) Maschinen.Add(i);
						}
						//  Gibt es mehr als eine Maschine für den gleichn Auftrag?
						if (Maschinen.Count >= 2 ) 
						{
							for(int j = 0; j < Maschinen.Count; j++)
							{
								polyAL = new ArrayList();

								for (int p2 = p ; p2<periodenListe.Length; p2++)
								{
									// dann diesen Auftrag mit Schalter als Polynom in Restriktion aufnehmen
									poly = new Polynom(1,"A" +auftragsListe[a].Nummer +"P" + (p2+1).ToString() + "T" + ((Int32)Maschinen[j]+1).ToString());
                                    //Console.Out.WriteLine("A" +auftragsListe[a].Nummer +"P" + (p2+1).ToString() + "T" + ((Int32)Maschinen[j]+1).ToString());
									polyAL.Add(poly);
								}

								// M mit 10.000 gewichten 
								polyAL.Add(new Polynom(10000,"Z"));
								if(j == 1 )
								{
									// M negativ gewichten 
									polyAL.Add(new Polynom(-10000,"H"));
								}

								// Restriktion speichern
								polyArray = new Polynom[polyAL.Count];
								for (int z=0; z<polyArray.Length; z++)	// Umwandlung ArrayList -> Array
								{
									polyArray[z] = (Polynom)polyAL[z];
								}
								rs = new Restriktion(polyArray, "<=", auftragsListe[a].Dauer);	
								rsAL.Add(rs);
							}
						}						
					}
				}
			}

			// 4. Zielfunktion und Restriktionen zu LPModell zusammenführen.
			rsArray = new Restriktion[rsAL.Count];
			for (int z=0; z<rsArray.Length; z++)	// Umwandlung ArrayList -> Array
			{
				rsArray[z] = (Restriktion)rsAL[z];
			}
			lp = new LPModell(zf, rsArray, "Maschinenbelegungsplanung");


			// 4. Umwandlung des LPModells in MPS.
			//if (solver.Equals("LP-Solve"))
			//{
				EncodeToMPS etm = new EncodeToMPS(lp, workDir +"\\mbp2003.mps");
			//}
		}
		#endregion

		#region ISolver:solve()
		public override void solve( )
		{
			/*tCreateBatchFile = new Thread(new ThreadStart(this.createBatchFile));
			tCreateBatchFile.Name = "CreateBatchFile";
			tCreateBatchFile.Start();

			tRunSolver = new Thread(new ThreadStart(this.runSolver));
			tRunSolver.Name = "RunSolver";
			tRunSolver.Start();

			tOpenOutFile = new Thread(new ThreadStart(this.openOutFile));
			tOpenOutFile.Name = "OpenOutFile";
			tOpenOutFile.Start();
			*/
			createBatchFile();
			runSolver();
			openOutFile();
		}
		#endregion

		#region ISolver:readResults()
		public override AusgabeDaten readResults( )
		{
			return rValue;
		}
		#endregion

		#region createBatchFile()
		/// <summary>
		/// legt ein batch-file an, dass den
		/// LP Solve startet, ihm die Eingabedatei
		/// angibt und die Ausgabedatei bennennt.
		/// Eingabedatei: (Arbeitsverzeichnis des Benutzers)\lpsolve.mps
		/// Ausgabedatei: (Arbeitsverzeichnis des Benutzers)\lpsolve.ou
		/// </summary>
		/// <returns>true / false</returns>
		private void createBatchFile()
		{
			FileStream m_FsoInputFile = new FileStream( workDir + "\\runSolver.bat",FileMode.Create,FileAccess.Write, FileShare.ReadWrite);
			StreamWriter m_SrwInputFile = new StreamWriter(m_FsoInputFile);
			
            // Solverpfad dynamisch und in Anführungsstrichen
            // Solverpfad für GLPK hinzugefügt
			switch( solver )
			{
				case "LP-Solve":
                    m_SrwInputFile.WriteLine("\"" + solverPathLP + "\\lp_solve.exe\" -mps <" + "\"" + 
                        workDir + "\\mbp2003.mps\"" + "> " + "\"" + workDir + "\\mbp2003_multiple.out\"");
					break;

                case "GLPK":
                    m_SrwInputFile.WriteLine("\"" + solverPathGLPK + "\\glpsol.exe\" --freemps " + "\"" + 
                        workDir + "\\mbp2003.mps\"" + " -o " + "\"" + workDir + "\\mbp2003_multiple.out\"");
                    break;
				
					/*******************************************************************
					 * Zur Erweiterung:
					 * Für jeden weiteren Solver ( XA oder änliches ) muss ein weiterer
					 * 'case' eingefügt werden, der die betreffende Zeile im Batch-File
					 * generiert.
					 *******************************************************************/
				default:
					break;
			}

			m_SrwInputFile.Close();
			m_FsoInputFile.Close();
		}
		#endregion

		#region runSolver()
		/// <summary>
		/// started den Solver
		/// </summary>
		/// <returns> true / false </returns>
		protected void runSolver( )
		{

            System.Diagnostics.ProcessStartInfo p = new System.Diagnostics.ProcessStartInfo(@workDir + "\\runSolver.bat");
            System.Diagnostics.Process proc = new System.Diagnostics.Process();
            proc.StartInfo = p;

            proc.Start();
            proc.WaitForExit();			
		}
		#endregion

		#region openOutFile()
		protected void openOutFile() 
		{

			System.IO.FileStream lpSolveOutputFile = new System.IO.FileStream( workDir + "\\mbp2003_multiple.out", System.IO.FileMode.Open, System.IO.FileAccess.Read, System.IO.FileShare.None);;
			System.IO.StreamReader lpSolveStreamReader = new System.IO.StreamReader( lpSolveOutputFile );

			#region leseErgebnisAus
			string zeileAusDatei;
			zeileAusDatei = lpSolveStreamReader.ReadLine( );

            switch (solver)
            {
                case "LP-Solve":
                    if (zeileAusDatei.StartsWith("This problem is infeasible") || zeileAusDatei.StartsWith("This Solution is unbounded"))
                    {
                        rValue.Failure = "\n\n" + zeileAusDatei;
                    }
                    else
                    {
                        rValue.Failure = "\n\n" + zeileAusDatei;
                        rValue.Failure = rValue.Failure.Replace("Value of objective function", "Durchschnittliche Maschinenauslastung pro Periode");
                    }
            #endregion

                    Regex myRegex = new Regex("(A[0-9]*P[0-9]*T[0-9])");


                    while ((zeileAusDatei = lpSolveStreamReader.ReadLine()) != null)
                    {
                        Match ma = myRegex.Match(zeileAusDatei);
                        if (ma.Success)
                        {
                            int tmpAuftrag;
                            int tmpPeriode;
                            int tmpTyp;
                            double tmpWert;

                            zeileAusDatei = zeileAusDatei.Replace(".", ",");
                            int startA = zeileAusDatei.IndexOf("A", 0);
                            int startP = zeileAusDatei.IndexOf("P", 0);
                            int startT = zeileAusDatei.IndexOf("T", 0);
                            int startErstesLeerzeichen = zeileAusDatei.IndexOf(' ');
                            int startLetztesLeerzeichen = zeileAusDatei.LastIndexOf(' ');

                            tmpAuftrag = Int32.Parse(zeileAusDatei.Substring(startA + 1, (startP - (startA + 1))));
                            tmpPeriode = Int32.Parse(zeileAusDatei.Substring(startP + 1, (startT - (startP + 1))));
                            tmpTyp = Int32.Parse(zeileAusDatei.Substring(startT + 1, (startErstesLeerzeichen + 1) - startT));
                            tmpWert = Double.Parse(zeileAusDatei.Substring(startLetztesLeerzeichen + 1, zeileAusDatei.Length - (startLetztesLeerzeichen + 1)));

                            rValue.setMultipleSolverResults(tmpAuftrag, tmpPeriode, tmpTyp, tmpWert);
                        }
                    }

                    lpSolveStreamReader.Close();
                    rValue.AnzahlAuftraege = anzahlAuftraege;
                    rValue.AnzahlPerioden = anzahlPerioden;
                    rValue.AnzahlTypen = anzahlMaschinen;
                    //deleteFiles();
                    break;

                case "GLPK":
                    bool statusReached = false;
                    while (!(statusReached))
                    {
                        if (zeileAusDatei.StartsWith("Status:"))
                        {
                            statusReached = true;
                            Debug.WriteLine("Status reached");
                            if (zeileAusDatei == "Status:     OPTIMAL")
                            {
                                Debug.WriteLine("Status=Optimal");
                                zeileAusDatei = lpSolveStreamReader.ReadLine();
                                Debug.WriteLine(zeileAusDatei);
                                rValue.Failure = "\n\n" + zeileAusDatei;
                                rValue.Failure = rValue.Failure.Replace("Objective:  ZF =", "Durchschnittliche Maschinenauslastung pro Periode:");
                            }
                        }

                        zeileAusDatei = lpSolveStreamReader.ReadLine();
                    }

                    Regex glpkRegex = new Regex("(A[0-9]*P[0-9]*T[0-9]*)");
                    while ((zeileAusDatei = lpSolveStreamReader.ReadLine()) != null)
                    {
                        Match ma = glpkRegex.Match(zeileAusDatei);
                        if (ma.Success)
                        {

                            //String zusammenbasteln
                            Console.Out.WriteLine(zeileAusDatei);
                            Regex lineStart = new Regex("\\s+[0-9]+\\s+A");
                            zeileAusDatei = lineStart.Replace(zeileAusDatei, "A");
                            Console.Out.WriteLine(zeileAusDatei);
                            Regex middle = new Regex("\\s+[A-Z]+\\s+");
                            zeileAusDatei = middle.Replace(zeileAusDatei, " ");
                            Console.Out.WriteLine(zeileAusDatei);

                            int tmpAuftrag;
                            int tmpPeriode;
                            int tmpTyp;
                            double tmpWert;

                            zeileAusDatei = zeileAusDatei.Replace(".", ",");
                            int startA = zeileAusDatei.IndexOf("A", 0);
                            int startP = zeileAusDatei.IndexOf("P", 0);
                            int startT = zeileAusDatei.IndexOf("T", 0);
                            int startErstesLeerzeichen = zeileAusDatei.IndexOf(' ');

                            tmpAuftrag = Int32.Parse(zeileAusDatei.Substring(startA + 1, (startP - (startA + 1))));
                            tmpPeriode = Int32.Parse(zeileAusDatei.Substring(startP + 1, (startT - (startP + 1))));
                            tmpTyp = Int32.Parse(zeileAusDatei.Substring(startT + 1, (startErstesLeerzeichen - 1) - startT));
                            Console.Out.WriteLine("Auftrag:"+tmpAuftrag+" Periode:"+tmpPeriode+" Typ:"+tmpTyp);
                            Console.Out.WriteLine(zeileAusDatei.Substring(startErstesLeerzeichen + 1, 7));
                            tmpWert = Double.Parse(zeileAusDatei.Substring(startErstesLeerzeichen + 1, 7));
                            Console.Out.WriteLine("Wert: "+tmpWert);

                            rValue.setMultipleSolverResults(tmpAuftrag, tmpPeriode, tmpTyp, tmpWert);

                            //rValue.setResults(tmpPeriode, tmpAuftrag, tmpWert);

                        }
                    }
                    lpSolveStreamReader.Close();
                    rValue.AnzahlAuftraege = anzahlAuftraege;
                    rValue.AnzahlPerioden = anzahlPerioden;
                    rValue.AnzahlTypen = anzahlMaschinen;
                    break;
            }
		}
		#endregion

        public void deleteFiles()
        {
            File.Delete(workDir + "\\mbp2003_multiple.out");
            File.Delete(workDir + "\\runSolver.bat");
            File.Delete(workDir + "\\mbp2003.mps");
        }
	}
}
