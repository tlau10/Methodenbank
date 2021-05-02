using System;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for MPSRow.
	/// </summary>
	public class MPSRow : IComparable
	{
		private string var;
		private string bez;
		private double wert;
		
		public MPSRow(string variable, string bezeichnung, double wert)
		{
			this.var = variable;
			this.bez = bezeichnung;
			this.wert = wert;
		}

		public int CompareTo(object o)
		{
			MPSRow m = (MPSRow)o;

			return m.Var.CompareTo(this.Var);
		}

		public string Var 
		{
			get { return this.var;}
			set { this.var = value;}
		}

		public string Bez 
		{
			get { return this.bez;}
			set { this.bez = value;}
		}

		public double Wert 
		{
			get { return this.wert;}
			set { this.wert = value;}
		}
	}
}
