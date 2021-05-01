using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Speichert eine Restriktion.
	/// Ungültige Restriktionswerte werden per Exception abgefangen.
	/// </summary>
	public class Zielfunktion
	{
		private Polynom[] m_polynome;
		private string m_typ;
		
		/// <summary>
		/// Legt eine neue Zielfunktion an.
		/// </summary>
		/// <param name="koeffizienten">Koeffizienten der einzelnen xi, gültig sind alle double Werte</param>
		/// <param name="typ">Typ der Zielfunktion, gültige Werte: "max", "min"</param>
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
					  // dann ungültiger Typ
					  throw new Exception("ungültiger Zielfunktionstyp: " +value);
				  }
				  else 
				  {
					  this.m_typ = value;
				  } 
			}
		}
	}
}
