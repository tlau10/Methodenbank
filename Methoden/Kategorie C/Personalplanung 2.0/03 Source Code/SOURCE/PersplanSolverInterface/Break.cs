using System;

namespace PersplanLibrary
{
	/// <summary>
	/// Zusammenfassung für Break.
	/// </summary>
	public class Break
	{
		private DateTime _start;
		private DateTime _end;
		private Double	 _activity;

		public Break()
		{
		}

		public DateTime Start { get { return _start; } set { _start = value; } }
		public DateTime End { get { return _end; } set { _end = value; } }
		public double Activity { get { return _activity; } set { _activity = value; } }
	}
}
