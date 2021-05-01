using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Speichert eine Restriktion.
	/// Ung�ltige Restriktionswerte werden per Exception abgefangen.
	/// </summary>
	public class Zielfunktion
	{
		private Polynom[] m_polynome;
		private string m_typ;
		
		/// <summary>
		/// Legt eine neue Zielfunktion an.
		/// </summary>
		/// <param name="koeffizienten">Koeffizienten der einzelnen xi, g�ltig sind alle double Werte</param>
		/// <param name="typ">Typ der Zielfunktion, g�ltige Werte: "max", "min"</param>
		/// <param name="wert"></param>
		public Zielfunktion(Polynom[] polynome, string typ)
		{
			this.m_polynome = polynome;
			this.Typ = typ;
		}

		public Polynom[] Polynome 
		{
			get { return this.m_polynome; }
			set { this.m_polynome = value; }
		}

		public string Typ 
		{
			get { return this.m_typ; }
			set 
			{
				if (!value.Equals("max") && !value.Equals("min"))
				  {
					  // dann ung�ltiger Typ
					  throw new Exception("ung�ltiger Zielfunktionstyp: " +value);
				  }
				  else 
				  {
					  this.m_typ = value;
				  } 
			}
		}
	}
}
