using System;
using System.Collections;

namespace PersplanLibrary
{
	/// <summary>
	/// Zusammenfassung für Shift.
	/// </summary>
	public class Shift
	{
		private DateTime	_Start;
		private DateTime	_End;
		private DateTime	_BreakStart;
		private DateTime	_BreakEnd;
		private int			_Preference;
		private int			_MaxPersons;
		private double		_Activity;
		private ArrayList	_Breaks;

		public Shift()
		{
			this._Breaks = new ArrayList();
		}

		public DateTime Start { get { return _Start; } set { _Start = value; }}
		public DateTime End { get { return _End; } set { _End = value; }}
		public DateTime BreakStart { get { return _BreakStart; } set { _BreakStart = value; }}
		public DateTime BreakEnd { get { return _BreakEnd; } set { _BreakEnd = value; }}
		public int Preference { get { return _Preference; } set { _Preference = value; }}
		public int MaxPersons { get { return _MaxPersons; } set { _MaxPersons = value; }}
		public double Activity { get { return _Activity; } set { _Activity = value; }}
		public ArrayList Breaks { get { return _Breaks; } set { _Breaks = value; }}
	}
}
