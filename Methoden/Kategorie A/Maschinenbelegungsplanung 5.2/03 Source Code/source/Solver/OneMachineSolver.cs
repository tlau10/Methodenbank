using System;
using System.Threading;
using System.Diagnostics;
using System.IO;
using System.Data;
using System.Collections;
using System.Text.RegularExpressions;


namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// SolverKlasse für eine Maschine.
	/// In dieser ersten Implementierung
	/// wird nur LP-Solve unterstützt.
	/// </summary>
	public class OneMachineSolver : ISolver
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
		/// <summary>
		///  Für den ersten Prototyp:
		///   nur LP-Solve
		/// </summary>
		private string solver;
		#endregion
		
		#region default-constrcutor
		public OneMachineSolver() { }
		#endregion
		
		#region constructor
		/// <summary>
		/// Konstruktor
		/// </summary>
		/// <param name="eingabeDatenParam">
		/// Enthält die zu verarbeitenden Eingabedaten
		/// </param>
		/// <param name="chosenSolver">
		/// string: in unsererer ersten Implementierung nur LP-Solve
		/// </param>
		/// <param name="solverPathParam">
		/// Pfadangabe, wo sich LP-Solve befindet
		/// </param>
		/// <param name="workDirParam">
		/// Pfadangabe. Das arbeitsverzeichnis des Benutzers.
		/// Bsplw. C:\Temp 
		///</param>
        public OneMachineSolver(eingabeDaten eingabeDatenParam, String chosenSolver, String solverPathLPParam, String solverPathGLPKParam, String workDirParam)
		{
			auftragsListe = eingabeDatenParam.auftragsListe;
			periodenListe = eingabeDatenParam.periodenListe;
			anzahlAuftraege = auftragsListe.Length;
			anzahlPerioden  = periodenListe.Length;
			solver    = chosenSolver;
			solverPathLP = solverPathLPParam;
            solverPathGLPK = solverPathGLPKParam;
			workDir = workDirParam;
            //rValue = new AusgabeDaten( (eingabeDatenParam.auftragsListe.Length +1), (eingabeDatenParam.periodenListe.Length +1) );
			rValue = new AusgabeDaten( );
            Console.Out.WriteLine(solver);
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
			zf = new Zielfunktion(polyArray, "max");

			// 2. Restriktionen erstellen
			rsAL = new ArrayList();

			// 2.1 Restriktionen für Perioden in denen der Auftrag möglich wäre
			// jeder Schleifendurchgang ergibt eine neue Restriktion
			for (int a=0; a<auftragsListe.Length; a++) // a wie Auftrag
			{
				polyAL = new ArrayList();
				int ap=auftragsListe[a].Beginn;		// ap wie AuftragsPeriode
				// Restriktion erstellen
				while (ap <= auftragsListe[a].Ende)
				{
					poly = new Polynom(1,"A" +auftragsListe[a].Nummer +"P" +ap);
					polyAL.Add(poly);
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

			// 2.2 Restriktionen für periodenorientierte Glättung
			// jeder Schleifendurchgang ergibt eine neue Restriktion
			for (int p=0; p<periodenListe.Length; p++)
			{
				polyAL = new ArrayList();
				
				for (int a=0; a<auftragsListe.Length; a++)
				{
					if (periodenListe[p].Nummer >= auftragsListe[a].Beginn && periodenListe[p].Nummer <= auftragsListe[a].Ende)
					{
						// dann diesen Auftrag als Polynom in Restriktion aufnehmen
						poly = new Polynom(1,"A" +auftragsListe[a].Nummer +"P" +periodenListe[p].Nummer);
						polyAL.Add(poly);
					}
				}
				
				// Anzahl Maschinen * Y abziehen
				polyAL.Add(new Polynom(-1*(periodenListe[p].Anzahl),"Y"));
				
				// Restriktion speichern
				polyArray = new Polynom[polyAL.Count];
				for (int z=0; z<polyArray.Length; z++)	// Umwandlung ArrayList -> Array
				{
					polyArray[z] = (Polynom)polyAL[z];
				}
				rs = new Restriktion(polyArray, "<=", 0);
				rsAL.Add(rs);
			}

			// 2.3 Restriktionen bezüglich der zur verfügung stehenden Kapazitäten
			// jeder Schleifendurchgang ergibt eine neue Restriktion
			// dieser Teil ist 2.2 fast gleich. Unterschiede: hier wird die Anzahl Maschinen in der Restriktion nicht abgezogen und der RHS Wert ist nicht 0
			for (int p=0; p<periodenListe.Length; p++)
			{
				polyAL = new ArrayList();
				
				for (int a=0; a<auftragsListe.Length; a++)
				{
					if (periodenListe[p].Nummer >= auftragsListe[a].Beginn && periodenListe[p].Nummer <= auftragsListe[a].Ende)
					{
						// dann diesen Auftrag als Polynom in Restriktion aufnehmen
						poly = new Polynom(1,"A" +auftragsListe[a].Nummer +"P" +periodenListe[p].Nummer);
						polyAL.Add(poly);
					}
				}
								
				// Restriktion speichern
				polyArray = new Polynom[polyAL.Count];
				for (int z=0; z<polyArray.Length; z++)	// Umwandlung ArrayList -> Array
				{
					polyArray[z] = (Polynom)polyAL[z];
				}
				rs = new Restriktion(polyArray, "<=", periodenListe[p].Anzahl*60);	// 60: 60 Minuten als eine Maschinenkapazitätseinheit
				rsAL.Add(rs);
			}

			// 3. Zielfunktion und Restriktionen zu LPModell zusammenführen
			rsArray = new Restriktion[rsAL.Count];
			for (int z=0; z<rsArray.Length; z++)	// Umwandlung ArrayList -> Array
			{
				rsArray[z] = (Restriktion)rsAL[z];
			}
			lp = new LPModell(zf, rsArray, "Maschinenbelegungsplanung");


			// 4. Umwandlung des LPModells in MPS
			EncodeToMPS etm = new EncodeToMPS(lp, workDir +"\\mbp2003.mps");


		}
		#endregion

		#region ISolver:solve()
		/// <summary>
		/// legt das batchfile an und startet den solver
		/// anschliessend werden die daten aus der ausgabe-
		/// datei des solvers ausgelesen und in form von
		/// von 'AusgabeDaten' gespeichert.
		/// </summary>
		public override void solve( )
		{
			if( createBatchFile( ) && runSolver( ) )
			{
                System.IO.FileStream lpSolveOutputFile = new System.IO.FileStream(workDir + "\\mbp2003_single.out", System.IO.FileMode.Open, System.IO.FileAccess.Read, System.IO.FileShare.None); ;
                System.IO.StreamReader lpSolveStreamReader = new System.IO.StreamReader(lpSolveOutputFile);

                #region leseErgebnisAus
                string zeileAusDatei;
                zeileAusDatei = lpSolveStreamReader.ReadLine();                

                switch (solver)
                {
                    case "LP-Solve":
                        if (zeileAusDatei.StartsWith("This problem is infeasible") || zeileAusDatei.StartsWith("This Solution is unbounded"))
                        {
                            rValue.Failure = "\n\n" + zeileAusDatei;
                        } else{
                            rValue.Failure = "\n\n" + zeileAusDatei;
                            rValue.Failure = rValue.Failure.Replace("Value of objective function", "Durchschnittliche Maschinenauslastung pro Periode:");
                        }
                        #endregion                        

                        Regex myRegex = new Regex("(A[0-9]*P[0-9]*)");

                        while ((zeileAusDatei = lpSolveStreamReader.ReadLine()) != null){                    
                            Match ma = myRegex.Match(zeileAusDatei);
                            if(ma.Success){
                                int tmpAuftrag;
                                int tmpPeriode;
                                double tmpWert;

                                zeileAusDatei = zeileAusDatei.Replace(".", ",");
                                int startA = zeileAusDatei.IndexOf("A", 0);
                                int startP = zeileAusDatei.IndexOf("P", 0);
                                int startErstesLeerzeichen = zeileAusDatei.IndexOf(' ');
                                int startLetztesLeerzeichen = zeileAusDatei.LastIndexOf(' ');

                                tmpAuftrag = Int32.Parse(zeileAusDatei.Substring(startA + 1, (startP - (startA + 1))));
                                tmpPeriode = Int32.Parse(zeileAusDatei.Substring(startP + 1, (startErstesLeerzeichen + 1) - startP));
                                tmpWert = Double.Parse(zeileAusDatei.Substring(startLetztesLeerzeichen + 1, zeileAusDatei.Length - (startLetztesLeerzeichen + 1)));

                                rValue.setResults(tmpPeriode, tmpAuftrag, tmpWert);
                            }                   
                        }
                        lpSolveStreamReader.Close();
                        rValue.AnzahlAuftraege = anzahlAuftraege;
                        rValue.AnzahlPerioden = anzahlPerioden;
                    break;

                    //Einlesen der Output-Datei des GLPK
                    case "GLPK":

                        bool statusReached = false;
                        while(!(statusReached)){
                            if (zeileAusDatei.StartsWith("Status:")){
                                statusReached = true;
                                Debug.WriteLine("Status reached");
                                if (zeileAusDatei == "Status:     OPTIMAL") {
                                    Debug.WriteLine("Status=Optimal");
                                    zeileAusDatei = lpSolveStreamReader.ReadLine();
                                    Debug.WriteLine(zeileAusDatei);
                                    rValue.Failure = "\n\n" + zeileAusDatei;
                                    rValue.Failure = rValue.Failure.Replace("Objective:  ZF =", "Durchschnittliche Maschinenauslastung pro Periode:");
                                }
                            } 

                            zeileAusDatei = lpSolveStreamReader.ReadLine();
                        }

                        Regex glpkRegex = new Regex("(A[0-9]*P[0-9]*)");
                        while ((zeileAusDatei = lpSolveStreamReader.ReadLine()) != null)
                        {
                            Match ma = glpkRegex.Match(zeileAusDatei);
                            if (ma.Success){

                                //String zusammenbasteln
                                Regex lineStart = new Regex("\\s+[1-9]+\\s+");
                                zeileAusDatei = lineStart.Replace(zeileAusDatei, "");

                                Regex middle = new Regex("\\s+[A-Z]+\\s+");
                                zeileAusDatei = middle.Replace(zeileAusDatei, " ");

                                int tmpAuftrag;
                                int tmpPeriode;
                                double tmpWert;

                                zeileAusDatei = zeileAusDatei.Replace(".", ",");
                                int startA = zeileAusDatei.IndexOf("A", 0);
                                int startP = zeileAusDatei.IndexOf("P", 0);
                                int startErstesLeerzeichen = zeileAusDatei.IndexOf(' ');

                                tmpAuftrag = Int32.Parse(zeileAusDatei.Substring(startA + 1, (startP - (startA + 1))));
                                tmpPeriode = Int32.Parse(zeileAusDatei.Substring(startP + 1, (startErstesLeerzeichen - 1) - startP));
                                tmpWert = Double.Parse(zeileAusDatei.Substring(startErstesLeerzeichen + 1, 7));

                                rValue.setResults(tmpPeriode, tmpAuftrag, tmpWert);

                            }
                        }
                        lpSolveStreamReader.Close();
                        rValue.AnzahlAuftraege = anzahlAuftraege;
                        rValue.AnzahlPerioden = anzahlPerioden;
                        break;

                    default:
                        break;
                }
                //deleteFiles();
			}
		}
		#endregion

        public void deleteFiles()
        {
            File.Delete(workDir + "\\mbp2003_single.out");
            File.Delete(workDir + "\\runSolver.bat");
            File.Delete(workDir + "\\mbp2003.mps");                   
        }

		#region ISolver:readResults()
		/// <summary>
		/// liefert die Ergebnisse des Solvers in Form
		/// von 'AusgabeDaten' zurück
		/// </summary>
		/// <returns>AusgabeDaten</returns>
		public override AusgabeDaten readResults( )
		{
			return rValue;
		}
		#endregion
		
		#region createBatchFile()
		/// <summary>
		/// legt ein batch-file an, dass den
		/// Solver startet, ihm die Eingabedatei
		/// angibt und die Ausgabedatei bennennt.
		/// Eingabedatei: (Arbeitsverzeichnis des Benutzers)\mbp2003.mps
        /// Ausgabedatei: (Arbeitsverzeichnis des Benutzers)\mbp2003_single.out
		/// </summary>
		/// <returns>true / false</returns>
		private bool createBatchFile()
		{
			FileStream m_FsoInputFile = new FileStream( workDir + "\\runSolver.bat",FileMode.Create,FileAccess.Write, FileShare.ReadWrite);
			StreamWriter m_SrwInputFile = new StreamWriter(m_FsoInputFile);
			
			switch( solver )
			{
                    //Pfade in Anführungszeichen gesetzt um Leerzeichen im Dateipfad zu erlauben
				case "LP-Solve":
                    m_SrwInputFile.WriteLine("\"" + solverPathLP + "\\lp_solve.exe\" -mps <" + "\"" + workDir + "\\mbp2003.mps\"" + "> " + "\"" + workDir + "\\mbp2003_single.out\"");
					break;

				 /**
                  * Erzeugen einer Datei für den GLPK
                  **/
                case "GLPK":
                    m_SrwInputFile.WriteLine("\"" + solverPathGLPK + "\\glpsol.exe\" --freemps " + "\"" + workDir + "\\mbp2003.mps\"" + " -o " + "\"" + workDir + "\\mbp2003_single.out\"");
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
			return true;
		}
		#endregion
		
		#region runSolver()
		/// <summary>
		/// started den Solver
		/// </summary>
		/// <returns> true / false </returns>
		protected bool runSolver( )
		{
            System.Diagnostics.ProcessStartInfo p = new System.Diagnostics.ProcessStartInfo(@workDir + "\\runSolver.bat");
            System.Diagnostics.Process proc = new System.Diagnostics.Process();
            proc.StartInfo = p;

            proc.Start();
            proc.WaitForExit();
			return true;
		}
		#endregion
	}
}
