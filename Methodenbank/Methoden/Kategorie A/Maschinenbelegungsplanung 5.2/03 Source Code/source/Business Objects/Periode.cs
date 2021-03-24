using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Klasse periode<br>
	/// </br>
	/// Verwaltung der Perioden.
	/// </summary>
	[Serializable]
	public class periode
	{
		private int maschinen;
		private string typ;
		private int[] typen;
		private int nummer;
		
		public periode()
		{
			
		}

		public periode(int inAnzahlMaschinen)
		{
			typen = new int[inAnzahlMaschinen];
		}

		public periode( int anzahlMaschinen, string maschinenTyp, int periodenNummer )
		{
			maschinen = anzahlMaschinen;
			nummer = periodenNummer;
		}

		public periode( int anzahlMaschinen, int[] inMaschinenTypen, int periodenNummer )
		{
			maschinen = anzahlMaschinen;
			nummer = periodenNummer;
			typen = inMaschinenTypen;
		}


		public int Anzahl
		{
			get { return maschinen; }
			set { maschinen = value; }
		}

		public string Typ
		{
			get { return typ; }
			set { typ = value; }
		}
		
		public int[] Typen
		{
			get { return typen; }
			set { typen = value; }
		}

		public int Nummer
		{
			get { return nummer; }
			set { nummer = value; }
		}
	}
}
