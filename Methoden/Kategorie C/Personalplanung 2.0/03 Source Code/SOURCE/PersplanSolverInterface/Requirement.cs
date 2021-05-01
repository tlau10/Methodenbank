using System;

namespace PersplanLibrary
{
	/// <summary>
	/// Zusammenfassung für Requirement.
	/// </summary>
	public class Requirement
	{
		private DateTime	_From;
		private DateTime	_Until;
		private int			_RequiredPersons;

		public Requirement()
		{
		}

		public DateTime From { get {return _From;} set {_From = value;}}
		public DateTime Until { get {return _Until;} set {_Until = value;}}
		public int RequiredPersons { get {return _RequiredPersons;} set {_RequiredPersons = value;}}
	}
}
