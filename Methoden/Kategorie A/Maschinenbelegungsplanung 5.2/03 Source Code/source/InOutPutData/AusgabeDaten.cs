using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for AusgabeDaten.
	/// </summary>
	public class AusgabeDaten
	{
		private bool isUnbounded;
		private string failureString;
		private double[,] results;
		private double[,,] multipleSolverResults;
		private int anzahlAuftraege;
		private int anzahlPerioden;
		private int anzahlTypen;
		
		public AusgabeDaten()
		{
			failureString = "This Solution is unbounded!";
			results = new double[ 10,10 ];
			multipleSolverResults = new double[10,10,2];
			anzahlTypen = 1;
		}

		public AusgabeDaten( int auftraege, int perioden )
		{
			failureString = "This Solution is unbounded!";
			results = new double[ auftraege, perioden ];
			anzahlTypen = 1;
		}

		public AusgabeDaten( int auftraege, int perioden, int inAnzahlTypen )
		{
			failureString = "This Solution is unbounded!";
			multipleSolverResults = new double[auftraege+1,perioden+1,inAnzahlTypen+1];
			anzahlTypen = inAnzahlTypen;
		}

		public bool IsUnbounded
		{
			get { return isUnbounded; }
			set { isUnbounded = value; }
		}
		
		public string Failure
		{
			get { return failureString; }
			set { failureString = value; }
		}

		public int AnzahlAuftraege
		{
			get { return anzahlAuftraege; }
			set { anzahlAuftraege = value; }
		}

		public int AnzahlPerioden
		{
			get { return anzahlPerioden; }
			set { anzahlPerioden = value; }
		}

		public int AnzahlTypen
		{
			get { return anzahlTypen; }
			set { anzahlTypen = value; }
		}

		public double[,] Results
		{
			get { return results; }
		}

		public double[,,] MultipleSolverResults
		{
			get { return multipleSolverResults; }
		}

		/// <summary>
		///  setzt Werte in der Ergebnismatrix
		/// </summary><br></br>
		/// <param name="xParam">Periode</param><br></br>
		/// <param name="yParam">Auftrag</param><br></br>
		/// <param name="wertParam">Feldwert</param><br></br>
		public void setResults( int xWertParam, int yWertParam, double wertParam )
		{
			results[ xWertParam, yWertParam ] = wertParam;
		}

		public void setMultipleSolverResults( int xWertParam, int yWertParam, int zWertParam, double wertParam )
		{
			multipleSolverResults[ xWertParam, yWertParam, zWertParam ] = wertParam;
		}

	}
}
