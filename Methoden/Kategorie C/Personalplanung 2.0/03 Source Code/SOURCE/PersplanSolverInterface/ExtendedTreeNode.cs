using System;

namespace PersplanLibrary
{
	public enum TreeNodeID 
	{
		TreeNodeIDRoot, 
		TreeNodeIDShift, 
		TreeNodeIDRequirements, 
		TreeNodeIDSolution,
		TreeNodeIDShiftEntry,
		TreeNodeIDRequirementsEntry
	};

	/// <summary>
	/// Treenode derived class with id property to make identification of node possible
	/// </summary>
	public class ExtendedTreeNode : System.Windows.Forms.TreeNode
	{
		private TreeNodeID _ID;

		public ExtendedTreeNode()
		{
		}

		public ExtendedTreeNode(string Text, TreeNodeID ID)
		{
			this.Text = Text;
			this.ID = ID;
		}

		public ExtendedTreeNode(string Text, TreeNodeID ID, System.Windows.Forms.TreeNode[] Nodes)
		{
			this.Nodes.AddRange(Nodes);
			this.Text = Text;
			this.ID = ID;
		}

		public TreeNodeID ID { get { return this._ID; } set { this._ID = value; }}
	}
}
