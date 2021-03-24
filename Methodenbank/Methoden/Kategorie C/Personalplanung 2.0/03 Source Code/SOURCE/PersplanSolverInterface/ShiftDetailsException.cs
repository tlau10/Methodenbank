using System;

namespace PersplanLibrary
{
	/// <summary>
	/// Zusammenfassung für ShiftDetailsException.
	/// </summary>
	public class ShiftDetailsException : ApplicationException
	{
		public string _Message;

		public override string Message
		{
			get
			{
				return this._Message;
			}
		}

		public ShiftDetailsException()
		{
		}

		public ShiftDetailsException (string Message)
		{
			this._Message = Message;
		}
	}
}
