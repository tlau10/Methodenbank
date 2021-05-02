using System;
using System.Windows.Forms;

namespace PersplanLibrary
{
	/// <summary>
	/// Zusammenfassung für ShiftTreeNode.
	/// </summary>
	public class ShiftTreeNode : ExtendedTreeNode
	{
		private Shift _ShiftDetails;
		private ExtendedTreeNode _ShiftTreeNodeRoot;

		public ShiftTreeNode(ExtendedTreeNode ShiftTreeNodeRoot)
		{
			this.ID = TreeNodeID.TreeNodeIDShiftEntry;
			this._ShiftTreeNodeRoot = ShiftTreeNodeRoot;
		}

		public ShiftTreeNode(string Text, ExtendedTreeNode ShiftTreeNodeRoot)
		{
			this.Text = Text;
			this.ID = TreeNodeID.TreeNodeIDShiftEntry;
			this._ShiftTreeNodeRoot = ShiftTreeNodeRoot;
		}

		public Shift ShiftDetails 
		{ 
			get 
			{ 
				return _ShiftDetails;
			}
			set
			{
				string ErrorMsg = CheckDetails (value);
				if (ErrorMsg == "")
				{
					_ShiftDetails = value; 
					this.Text = _ShiftDetails.Start.ToShortTimeString() + " - " + _ShiftDetails.End.ToShortTimeString();
				}
				else
					throw new ShiftDetailsException(ErrorMsg);
			}
		}

		public string CheckDetails (Shift DetailsToCheck)
		{
			//if (DetailsToCheck.Start > DetailsToCheck.End)
			//	return "Das Zuweisen der Daten schlug fehl.\n\nDer Endzeitpunkt der Schicht liegt vor dem Startzeitpunkt.";
			//if (DetailsToCheck.BreakStart > DetailsToCheck.BreakEnd)
			//	return "Das Zuweisen der Daten schlug fehl.\n\nDer Endzeitpunkt der Pause liegt vor dem Startzeitpunkt.";
			if ((DetailsToCheck.BreakStart.Hour != 0 && DetailsToCheck.BreakStart.Minute != 0) ||
				(DetailsToCheck.BreakEnd.Hour != 0 && DetailsToCheck.BreakEnd.Minute != 0))
			{
				if (DetailsToCheck.BreakEnd > DetailsToCheck.End)
					return "Das Zuweisen der Daten schlug fehl.\n\nDer Endzeitpunkt der Pause liegt nach dem Endzeitpunkt der Schicht.";
				if (DetailsToCheck.BreakStart < DetailsToCheck.Start)
					return "Das Zuweisen der Daten schlug fehl.\n\nDer Startzeitpunkt der Pause liegt vor dem Startzeitpunkt der Schicht.";
			}
			/*foreach (ShiftTreeNode stn in _ShiftTreeNodeRoot.Nodes)
			{
				if (stn != this && stn.ShiftDetails != null)
				{
					if (!((DetailsToCheck.Start < stn.ShiftDetails.Start && DetailsToCheck.End < stn.ShiftDetails.Start) ||
						(DetailsToCheck.Start > stn.ShiftDetails.End && DetailsToCheck.End > stn.ShiftDetails.End)))
					{
						return "Das Zuweisen der Daten schlug fehl.\n\nStellen Sie sicher, daß sich die Schichtzeiten nicht überschneiden.";
					}
				}
			}*/
			return "";
		}

		public ListViewItem GetListViewItem()
		{
			ListViewItem lvi = new ListViewItem(new string[] 
				{					
					this.ShiftDetails.Start.ToShortTimeString(),
					this.ShiftDetails.End.ToShortTimeString(),
					this.ShiftDetails.BreakStart.ToShortTimeString(),
					this.ShiftDetails.BreakEnd.ToShortTimeString(),
					this.ShiftDetails.Preference.ToString() + " %",
					this.ShiftDetails.MaxPersons.ToString()
				});
			return lvi;
		}
	}
}
