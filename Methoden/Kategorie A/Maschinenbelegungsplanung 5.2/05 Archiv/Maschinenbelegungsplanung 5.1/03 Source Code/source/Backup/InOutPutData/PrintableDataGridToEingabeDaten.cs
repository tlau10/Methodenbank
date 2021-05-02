using System;
using System.Resources;
using System.Reflection;
using System.Collections;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;



namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Konvertiert DataGrids zu
	/// Eingabedaten
	/// </summary>
	public class printableDataGridToEingabeDaten
	{
		private eingabeDaten rValue = null;
		private auftrag[] auftragsListe = null;
		private periode[] periodenListe = null;
		private DataTable auftraegeDataTable = null;
		private DataTable periodenDataTable = null;

		public printableDataGridToEingabeDaten()
		{
		}

		public printableDataGridToEingabeDaten( DataGrid auftraege, DataGrid perioden )
		{
			auftraegeDataTable = ( DataTable ) auftraege.DataSource;
			periodenDataTable  = ( DataTable ) perioden.DataSource;
		}

		protected void convertToEingabeDaten(int inAnzahlMaschinen)
		{
			int rowCounter = 0;
			auftragsListe = new auftrag[auftraegeDataTable.Rows.Count];
			periodenListe = new periode[periodenDataTable.Rows.Count];
		
			if(inAnzahlMaschinen == 1)
			{
				for(; rowCounter < auftraegeDataTable.Rows.Count; rowCounter++ )
				{
					if( auftraegeDataTable.Rows[rowCounter]["Nr."].ToString().Equals("0") ) { break; }

					auftrag tmpAuftrag = new auftrag( );
					tmpAuftrag.Nummer = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["Nr."].ToString() );
					tmpAuftrag.Bezeichnung = auftraegeDataTable.Rows[rowCounter]["Bezeichnung"].ToString();
					tmpAuftrag.Dauer = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["Dauer"].ToString() );
					tmpAuftrag.Beginn = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["von Periode"].ToString() );
					tmpAuftrag.Ende = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["bis Periode"].ToString() );
					auftragsListe[rowCounter] = tmpAuftrag;
				}
			
				// Counter reseten :)
				rowCounter = 0;
				for(; rowCounter < periodenDataTable.Rows.Count; rowCounter++ )
				{
					if( periodenDataTable.Rows[rowCounter]["Perioden-Nr."].ToString().Equals("0") ) { break; }

					periode tmpPeriode = new periode( );
					tmpPeriode.Nummer = Int32.Parse( periodenDataTable.Rows[rowCounter]["Perioden-Nr."].ToString() );
					tmpPeriode.Anzahl = Int32.Parse( periodenDataTable.Rows[rowCounter]["# Maschinen Typ 1"].ToString() );
					periodenListe[rowCounter] = tmpPeriode;
				}
			
				// Übergabe an eingabeDaten-Objekt
				rValue = new eingabeDaten( auftragsListe, periodenListe );
			}
			else
			{
				for(; rowCounter < auftraegeDataTable.Rows.Count; rowCounter++ )
				{
					if( auftraegeDataTable.Rows[rowCounter]["Nr."].ToString().Equals("0") ) { break; }

					auftrag tmpAuftrag = new auftrag(inAnzahlMaschinen);
					tmpAuftrag.Nummer = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["Nr."].ToString() );
					tmpAuftrag.Bezeichnung = auftraegeDataTable.Rows[rowCounter]["Bezeichnung"].ToString();
					tmpAuftrag.Dauer = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["Dauer"].ToString() );
					int[] tmpAuftragTypen = new int[inAnzahlMaschinen];
					for(int i = 0; i<inAnzahlMaschinen; i++)
					{
						tmpAuftragTypen[i] = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["Typ " + (i+1).ToString()].ToString() );
					}
					tmpAuftrag.Typen = tmpAuftragTypen;
					tmpAuftrag.Beginn = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["von Periode"].ToString() );
					tmpAuftrag.Ende = Int32.Parse( auftraegeDataTable.Rows[rowCounter]["bis Periode"].ToString() );
					auftragsListe[rowCounter] = tmpAuftrag;
				}
			
				// Counter reseten :)
				rowCounter = 0;
				for(; rowCounter < periodenDataTable.Rows.Count; rowCounter++ )
				{
					if( periodenDataTable.Rows[rowCounter]["Perioden-Nr."].ToString().Equals("0") ) { break; }

					periode tmpPeriode = new periode(inAnzahlMaschinen);
					tmpPeriode.Nummer = Int32.Parse( periodenDataTable.Rows[rowCounter]["Perioden-Nr."].ToString() );
					int[] tmpPeriodenTypen = new int[inAnzahlMaschinen];
					for(int i = 0; i<inAnzahlMaschinen; i++)
					{
						 tmpPeriodenTypen[i] = Int32.Parse( periodenDataTable.Rows[rowCounter]["# Maschinen Typ " + i.ToString()].ToString() );
					}
					tmpPeriode.Typen = tmpPeriodenTypen;
					periodenListe[rowCounter] = tmpPeriode;
				}
			
				// Übergabe an eingabeDaten-Objekt
				rValue = new eingabeDaten( auftragsListe, periodenListe );
			}
		}

		public eingabeDaten returnEingabeDaten(int inAnzahlMaschinen )
		{
			convertToEingabeDaten(inAnzahlMaschinen);
			return rValue;
		}


	}
}
