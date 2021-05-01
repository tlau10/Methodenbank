using System;
using System.Windows.Forms;

namespace PersplanLibrary
{
	/// <summary>
	/// Zusammenfassung für RequirementTreeNode.
	/// </summary>
	public class RequirementTreeNode : ExtendedTreeNode
	{
		private Requirement _RequirementDetails;
		private ExtendedTreeNode _RequirementTreeNodeRoot;

		public RequirementTreeNode(ExtendedTreeNode RequirementTreeNodeRoot)
		{
			this.ID = TreeNodeID.TreeNodeIDRequirementsEntry;
			this._RequirementTreeNodeRoot = RequirementTreeNodeRoot;
		}

		public RequirementTreeNode (string Text, ExtendedTreeNode RequirementTreeNodeRoot)
		{
			this.Text = Text;
			this.ID = TreeNodeID.TreeNodeIDRequirementsEntry;
			this._RequirementTreeNodeRoot = RequirementTreeNodeRoot;
		}

		public Requirement RequirementDetails
		{ 
			get 
			{
				return _RequirementDetails;
			} 
			set
			{
				string ErrorMsg = CheckDetails(value);
				if (ErrorMsg == "")
				{
					_RequirementDetails = value;
					this.Text = _RequirementDetails.From.ToShortTimeString() + " - " + _RequirementDetails.Until.ToShortTimeString();
				}
				else
					throw new RequirementDetailsException(ErrorMsg);
			}
		}

		public string CheckDetails (Requirement DetailsToCheck)
		{			
			return "";
		}

		public ListViewItem GetListViewItem()
		{
			ListViewItem lvi = new ListViewItem(new string[] 
				{
					this.RequirementDetails.From.ToShortTimeString(),
					this.RequirementDetails.Until.ToShortTimeString(),
					this.RequirementDetails.RequiredPersons.ToString()
				});
			return lvi;
		}
	}
}
