using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Speichert eine Restriktion.
	/// Ung�ltige Restriktionswerte werden per Exception abgefangen.
	/// </summary>
	public class Restriktion
	{
		private Polynom[] m_polynome;
		private string m_typ;	// zul�ssige werte: <=, >=, =
		private double m_wert;
		
		/// <summary>
		/// Legt eine neue Restriktion an.
		/// </summary>
		/// <param name="koeffizienten">Koeffizienten der einzelnen xi, g�ltig sind alle double Werte</param>
		/// <param name="typ">Typ der Restriktion, zul�ssige Werte: "&lt;=", "&gt;=" und "=" </param>
		/// <param name="wert"></param>
		public Restriktion(Polynom[] polynome, string typ, double wert)
		{
			this.m_polynome = polynome;
			this.Typ = typ;
			this.m_wert = wert;
			
		}

		public Polynom[] Polynome
		{
			get { return this.m_polynome; }
			set { this.m_polynome = value; }
		}
		
		public string Typ 
		{
			get { return m_typ; }
			set 
			{
				if (!value.Equals("<=") && !value.Equals(">=") && !value.Equals("=")) 
				{
					// dann ung�ltiger Typ
					throw new Exception("ung�ltiger Restriktionstyp: " +m_typ);
				}
				else
				{
					this.m_typ = value;
				}
			}
		}

		public double Wert
		{
			get { return this.m_wert; }
			set { this.m_wert = value; }
		}
	}
}
