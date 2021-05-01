using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for LPModell.
	/// </summary>
	public class LPModell
	{
		private string m_bez;			// Bezeichnung des LPModells
		private Zielfunktion m_zf;		// Zielfunktion
		private Restriktion[] m_rs;		// Restriktionen
		
		public LPModell(Zielfunktion zielfunktion, Restriktion[] restriktionen, string bezeichnung)
		{
			this.m_zf = zielfunktion;
			this.m_rs = restriktionen;
			this.m_bez = bezeichnung;
		}
		
		public Zielfunktion ZF 
		{
			get { return m_zf; }
			set { m_zf = value; }
		}

		public Restriktion[] RS 
		{
			get { return m_rs; }
			set { m_rs = value; }
		}

		public string Bezeichnung 
		{
			get { return m_bez; }
			set { m_bez = value; }
		}
	}
}
