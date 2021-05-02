using System;
using System.Data;
using System.IO;
using System.Collections;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Übersetzt ein LPModell in ein MPS File.
	/// </summary>
	public class EncodeToMPS
	{
		private LPModell m_lp;		// LP Modell
		private string m_mps;		// Pfad für MPS-File
		private FileStream m_FsoInputFile = null;
		private StreamWriter m_SrwInputFile = null;

		/// <summary>
		/// Übersetzt das übergebene LPModell in ein MPS File. Voraussetzung ist ein LPModell mit einer zu MINIMIERENDEN Zielfunktion!.
		/// </summary>
		/// <param name="myLPModell">LPModell, welches in ein MPS-File übersetzt werden soll</param>
		/// <param name="pfadMpsFile">Pfad und Filename des zu erstellenden MPS Files</param>
		public EncodeToMPS(LPModell myLPModell, string pfadMpsFile)
		{
			this.m_lp = myLPModell;
			this.m_mps = pfadMpsFile;
			
			m_FsoInputFile = new FileStream(pfadMpsFile,FileMode.Create,FileAccess.Write, FileShare.None);
			m_SrwInputFile = new StreamWriter(m_FsoInputFile);
			writeSolverInputFile();
			closeFileStreams();
			if (myLPModell.ZF.Typ.Equals("max"))
			{
				// es ist bisher nicht notwendig auf diesen Fall zu reagieren, sollte dies bei zukünftigen
				// Programmerweiterungen notwendig sein, dann kann an dieser Stelle
				// auf ein Modell mit ZF --> max reagiert werden.
				// Zur Zeit wird ein ZF --> max wie ZF --> min behandelt.
				
				// ToDo: Modell in übersetzen in Modell mit ZF --> min
				// Realisierbar in Klasse LP Modell, hier nur der Methodenaufruf
			}
		}
		private bool createSolverInputFileMpsHeader()
		{
			// Der Name wird in den File geschrieben
			m_SrwInputFile.WriteLine("NAME \t" +m_lp.Bezeichnung +" MINIMIZE");
			// Die Bedingungen für die Restriktionen werden in den File geschrieben
			m_SrwInputFile.WriteLine("ROWS");
			// Bedingung für Zielfunktion wird eingefügt
			m_SrwInputFile.WriteLine(" N  ZF");
			// Bedingungen für Restriktionen einfügen (Typ)
			string typ=null;
			for (int i=0; i<m_lp.RS.Length; i++)
			{
				if (m_lp.RS[i].Typ.Equals("<=")) typ = "L";
				else if (m_lp.RS[i].Typ.Equals(">=")) typ = "G";
				else if (m_lp.RS[i].Typ.Equals("="))typ = "E";
				m_SrwInputFile.WriteLine(" " +typ +"  R" +(i+1));
			}
			return true;
		}
        private bool createSolverInputFileMpsColums()
        {
            Polynom poly;
            ArrayList mpsRowAL = new ArrayList();

            // Durchlaufe Zielfunktion, speichere alle Polynome in mpsRowAL
            for (int p = 0; p < m_lp.ZF.Polynome.Length; p++)	// p wie Polynom
            {
                poly = m_lp.ZF.Polynome[p];
                mpsRowAL.Add(new MPSRow(poly.V, "ZF", poly.K));
            }

            // Durchlaufe alle Restriktionen, speichere enthaltene Polynome in mpsRowAL
            for (int r = 0; r < m_lp.RS.Length; r++)	// r wie Restriktion
            {
                for (int p = 0; p < m_lp.RS[r].Polynome.Length; p++)
                {
                    poly = m_lp.RS[r].Polynome[p];
                    mpsRowAL.Add(new MPSRow(poly.V, "R" + (r + 1), poly.K));
                }
            }

            // Schreibe Restriktionen- und Zielfunktionselemente in die MPS Datei
            m_SrwInputFile.WriteLine("COLUMNS");
            mpsRowAL.Sort();
            for (int r = 0; r < mpsRowAL.Count; r++)
            {
                MPSRow row = (MPSRow)mpsRowAL[r];
                writeRow(row.Var, row.Bez, row.Wert);
            }
            return true;
        }
        private bool createSolverInputFileMpsFooter()
        {
            // Werte für die Restriktionen eintragen
            m_SrwInputFile.WriteLine("RHS"); // Right Hand Side
            for (int i = 0; i < m_lp.RS.Length; i++)
            {
                writeRow("MYRHS", "R" + (i + 1), m_lp.RS[i].Wert);
            }
            m_SrwInputFile.WriteLine("ENDATA");
            return true;
        }

        public void writeRow(String var, String bez, double wert)
        {

            String rowString = "    "+var;

            for (int j = rowString.Length; j <= 13; j++)
            {
                rowString = rowString + " ";
            }
            rowString = rowString + bez;

            for (int j = rowString.Length; j <= 23; j++)
            {
                rowString = rowString + " ";
            }
            rowString = rowString + wert + ".";

            m_SrwInputFile.WriteLine(rowString);

        }

		public void writeSolverInputFile()
		{
			this.createSolverInputFileMpsHeader();
			this.createSolverInputFileMpsColums();
			this.createSolverInputFileMpsFooter();
		}
		private bool closeFileStreams()
		{
			m_SrwInputFile.Close();
			m_FsoInputFile.Close();
			return true;
		}
	}
}
