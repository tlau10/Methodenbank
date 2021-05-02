using System;
using System.Data;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Klasse Auftrag<br>
	/// </br>.
	/// Verwaltung der Auftr�ge
	/// </summary>
	[Serializable]
	public class auftrag
	{
		/// <summary>
		/// auftragsnummer
		/// </summary>
		private int    nummer;
		/// <summary>
		/// auftragsname, -bezeichnung
		/// </summary>
		private string bezeichnung;
		/// <summary>
		/// dauer des auftrags in min
		/// </summary>
		private int    dauer;
		/// <summary>
		/// feld der maschinentypen
		/// </summary>
		private int[] typ;
		/// <summary>
		/// fr�heste startperiode
		/// </summary>
		private int    beginn;
		/// <summary>
		/// sp�tester fertigstellungstermin ( periode )
		/// </summary>
		private int    ende;

		
		public auftrag()
		{
			
		}

		public auftrag(int inAnzahlMaschinen)
		{
			typ = new int[inAnzahlMaschinen];
		}

		public auftrag( int aNummer, string aBezeichnung, int aDauer, int aBeginn, int aEnde )
		{
			nummer = aNummer;
			bezeichnung = aBezeichnung;
			dauer = aDauer;
			beginn = aBeginn;
			ende = aEnde;
		}

		public auftrag( int aNummer, string aBezeichnung, int aDauer, int[] aMaschinenTypen, int aBeginn, int aEnde )
		{
			nummer = aNummer;
			bezeichnung = aBezeichnung;
			dauer = aDauer;
			typ = aMaschinenTypen;
			beginn = aBeginn;
			ende = aEnde;
		}

		/// <summary>
		/// get und set Methoden f�r den 'Auftragsnamen'
		/// </summary>
		public string Bezeichnung
		{
			get { return bezeichnung; }
			set	{ bezeichnung = value; }
		}
		/// <summary>
		/// get und set Methoden f�r die 'Auftragsdauer'
		/// </summary>
		public int Dauer
		{
			get { return dauer; }
			set { dauer = value; }
		}
		/// <summary>
		/// get und set Methoden f�r die 'Maschinentypen'
		/// </summary>
		public int[] Typen
		{
			get { return typ; }
			set { typ = value; }
		}
		/// <summary>
		/// get und set Methoden f�r den 'Auftragsbeginn'
		/// </summary>
		public int Beginn
		{
			get { return beginn; }
			set { beginn = value; }
		}
		/// <summary>
		/// get und set f�r das 'AuftragsEnde'
		/// </summary>
		public int Ende
		{
			get { return ende; }
			set { ende = value; }
		}
		public int Nummer
		{
			get { return nummer; }
			set { nummer = value; }
		}
	}
}
