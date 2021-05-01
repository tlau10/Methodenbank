using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for Polynom.
	/// </summary>
	public class Polynom
	{
		private double k;
		private string v;
		
		public Polynom(double koeffizient, string variable)
		{
			this.K=koeffizient;
			this.V=variable;
		}

		public double K
		{
			get { return this.k; }
			set { this.k = value; }
		}

		public string V
		{
			get { return this.v; }
			set { this.v = value; }
		}

	}
}
