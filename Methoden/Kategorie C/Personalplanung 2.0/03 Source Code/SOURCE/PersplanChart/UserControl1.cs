using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;
using PersplanLibrary;

namespace PersplanChart
{
	/// <summary>
	/// Zusammenfassung für UserControl1.
	/// </summary>
	public class PersplanChart : System.Windows.Forms.UserControl
	{
		private bool _ShowRequirements;
		private bool _ShowShifts;
		private bool _ShowSolution;
		private int _MaximumRequirement = 0;
		private ExtendedTreeNode _RootNode;

		public bool ShowRequirements { get { return _ShowRequirements; } set { _ShowRequirements = value; }}
		public bool ShowShifts { get { return _ShowShifts; } set { _ShowShifts = value; }}
		public bool ShowSolution { get { return _ShowSolution; } set { _ShowSolution = value; }}
		public ExtendedTreeNode RootNode 
		{ 
			set 
			{
				_RootNode = value; 
				foreach (ExtendedTreeNode Node in _RootNode.Nodes)
				{
					if (Node.ID == TreeNodeID.TreeNodeIDRequirements)
					{
						foreach (RequirementTreeNode rtn in Node.Nodes)
						{
							if (rtn.RequirementDetails.RequiredPersons > this._MaximumRequirement)
								this._MaximumRequirement = rtn.RequirementDetails.RequiredPersons;
						}
						break;
					}
				}
			}
		}
		/// <summary>
		/// Erforderliche Designervariable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public PersplanChart()
		{
			// Dieser Aufruf ist für den Windows Form-Designer erforderlich.
			InitializeComponent();

			// TODO: Initialisierungen nach dem Aufruf von InitComponent hinzufügen

		}

		/// <summary>
		/// Die verwendeten Ressourcen bereinigen.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if( components != null )
					components.Dispose();
			}
			base.Dispose( disposing );
		}

		#region Vom Komponenten-Designer generierter Code
		/// <summary>
		/// Erforderliche Methode für die Designerunterstützung. 
		/// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
		/// </summary>
		private void InitializeComponent()
		{
			// 
			// PersplanChart
			// 
			this.Name = "PersplanChart";
			this.Paint += new System.Windows.Forms.PaintEventHandler(this.PersplanChart_Paint);

		}
		#endregion

		private void DrawAxes (Graphics g)
		{
			RectangleF rect = Region.GetBounds(g);
			float xOrigin =  rect.Left  + 20;
			float yOrigin =  rect.Bottom - 70;
			float xAxisX = rect.Right - 20;
			float yAxisY = rect.Top;
                        
			/*Pen axisPen = new Pen(axesColor);
                
			g.DrawLine(axisPen,xOrigin,yOrigin,xAxisX,yOrigin);
			g.DrawLine(axisPen,xOrigin,yOrigin,xOrigin,yAxisY);
                        
			axisPen.Dispose();*/
		}

		private void PersplanChart_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			/*Rectangle bound = new Rectangle(new Point(0,0),Size);
			graphRegion = new Region(bound);
			Region = graphRegion;
			DrawAxes(pe.Graphics);*/
			
		}
	}
}
