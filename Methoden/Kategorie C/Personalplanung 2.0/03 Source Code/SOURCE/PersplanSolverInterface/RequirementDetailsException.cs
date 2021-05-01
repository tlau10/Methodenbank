using System;

namespace PersplanLibrary
{
	/// <summary>
	/// Zusammenfassung f�r RequirementDetailsException.
	/// </summary>
	public class RequirementDetailsException : ApplicationException
	{
		public string _Message;

		public override string Message
		{
			get
			{
				return this._Message;
			}
		}

		public RequirementDetailsException()
		{
		}

		public RequirementDetailsException (string Message)
		{
			this._Message = Message;
		}
	}
}
