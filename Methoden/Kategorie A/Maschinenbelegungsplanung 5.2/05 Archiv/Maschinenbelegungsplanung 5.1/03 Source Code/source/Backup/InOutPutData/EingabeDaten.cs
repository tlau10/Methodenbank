using System;
using System.Reflection;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for eingabeDaten.
	/// </summary>
	
	[Serializable]
	public class eingabeDaten
	{
		auftrag[]  auftraege = null;
		periode[]  perioden  = null;
		
		#region default-constructor for OneMaschineSolver
		public eingabeDaten()
		{
			auftrag tmpAuftrag = new auftrag(0, "leer", 0, 0, 0 );
			auftraege = new auftrag[1];
			auftraege[0] = tmpAuftrag;

			periode tmpPeriode = new periode( 1, "leer", 1 );
			perioden = new periode[1];
			perioden[0] = tmpPeriode;
		}
		#endregion

		#region default-constructor for MultipleMaschinesSolver
		public eingabeDaten(int inAnzahlMaschinen)
		{
			auftrag tmpAuftrag = new auftrag(0, "leer", 0, 0, 0 );
			int[] typen = new int[inAnzahlMaschinen];
			for(int i = 0 ; i < inAnzahlMaschinen; i++)
			{
				typen[i] = 0;
			}
			tmpAuftrag.Typen = typen;
			auftraege = new auftrag[1];
			auftraege[0] = tmpAuftrag;

			periode tmpPeriode = new periode( 1, "leer", 1 );
			perioden = new periode[1];
			perioden[0] = tmpPeriode;
		}
		#endregion

		public eingabeDaten( auftrag[] auftraegeParam, periode[] periodenParam )
		{
			auftraege = auftraegeParam;
			perioden  = periodenParam;
		}

		public auftrag[] auftragsListe
		{
			get { return auftraege; }
			set { auftraege = value; }
		}

		public periode[] periodenListe
		{
			get { return perioden; }
			set { perioden = value; }
		}

	}
}
