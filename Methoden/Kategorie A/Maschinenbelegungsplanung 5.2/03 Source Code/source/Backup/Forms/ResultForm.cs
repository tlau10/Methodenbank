using System;
using System.Drawing;
using System.Collections;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for resultForm.
	/// </summary>
	public class ResultForm : System.Windows.Forms.Form
	{
		
		
		private AusgabeDaten ausgabeDaten = null;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.DataGrid dataGrid1;
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public ResultForm()
		{
			ausgabeDaten = new AusgabeDaten( );
			ausgabeDaten.Failure = "Noch keine Berechnung durchgeführt!";
			InitializeComponent();
		}
		
		public ResultForm( AusgabeDaten ausgabeDatenParam)
		{
			ausgabeDaten = ausgabeDatenParam;
			InitializeComponent();
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.label1 = new System.Windows.Forms.Label();
			this.dataGrid1 = new System.Windows.Forms.DataGrid();
			((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).BeginInit();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(8, 8);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(400, 76);
			this.label1.TabIndex = 0;
			this.label1.Text = ausgabeDaten.Failure;
			// 
			// dataGrid1
			// 
			
			DataTable myDataTable = new DataTable("Ergebnisse");
			
			
			// Aufträge sind in Spalten
			DataColumn myDataColumn;
			DataRow myDataRow;

			myDataColumn = new DataColumn( );
			myDataColumn.DataType = Type.GetType("System.String");
			myDataColumn.ColumnName = "-";
			myDataColumn.ReadOnly = true;
			myDataTable.Columns.Add( myDataColumn );

			// oneMaschineSolver
			if (ausgabeDaten.AnzahlTypen == 1)
			{
				double[,] sinlgeSolverResults = ausgabeDaten.Results;

				for( int i = 0; i < ausgabeDaten.AnzahlAuftraege; i++ )
				{
					myDataColumn = new DataColumn();
					myDataColumn.DataType = Type.GetType("System.String");
					myDataColumn.ColumnName = "Auftrag " +(i+1);
					myDataColumn.ReadOnly = false;
					myDataColumn.DefaultValue = "0";
					myDataTable.Columns.Add( myDataColumn );
				}

				for( int j = 0; j < ausgabeDaten.AnzahlPerioden; j++ )
				{
					myDataRow = myDataTable.NewRow( );
					for( int i = 0; i < ausgabeDaten.AnzahlAuftraege; i++ )
					{
						myDataRow["Auftrag " +(i+1)] = sinlgeSolverResults[j+1,i+1];
						myDataRow["-"] = "Periode " +(j+1);
					}
					myDataTable.Rows.Add( myDataRow );
				}
			}
			else // multipleMaschineSolver
			{
				double[,,] multipleSolverResults = ausgabeDaten.MultipleSolverResults;

				for( int i = 0; i < ausgabeDaten.AnzahlAuftraege; i++ )
				{
					myDataColumn = new DataColumn( );
					myDataColumn.DataType = Type.GetType("System.String");
					myDataColumn.ColumnName = "Auftrag " +(i+1);
					myDataColumn.ReadOnly = false;
					myDataColumn.DefaultValue = "0";
					myDataTable.Columns.Add( myDataColumn );
				}

				for( int j = 1; j <= ausgabeDaten.AnzahlAuftraege; j++ )
				{
					for( int i = 1; i <= ausgabeDaten.AnzahlPerioden; i++ )
					{
						for (int k = 1; k <= ausgabeDaten.AnzahlTypen; k++)
						{
							myDataRow = myDataTable.NewRow();
							myDataRow["Auftrag " +(j)] = multipleSolverResults[j,i,k];
							myDataRow["-"] = "Periode " +(i).ToString() + " |Typ " + (k).ToString();
							myDataTable.Rows.Add( myDataRow );
						}
					}			
				}
			}
	
			this.dataGrid1.DataSource = myDataTable.DefaultView;
			this.dataGrid1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.dataGrid1.Location = new System.Drawing.Point(40, 60);
			this.dataGrid1.Name = "dataGrid1";
			this.dataGrid1.Size = new System.Drawing.Size(640, 280);
			this.dataGrid1.TabIndex = 1;
			this.dataGrid1.RowHeadersVisible = true;

			this.dataGrid1.AlternatingBackColor = System.Drawing.Color.Lavender;
			this.dataGrid1.BackColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid1.BackgroundColor = System.Drawing.Color.LightGray;
			this.dataGrid1.BorderStyle = System.Windows.Forms.BorderStyle.None;
			this.dataGrid1.CaptionBackColor = System.Drawing.Color.LightSteelBlue;
			this.dataGrid1.CaptionFont = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid1.CaptionForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.FlatMode = true;
			this.dataGrid1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid1.ForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.GridLineColor = System.Drawing.Color.Gainsboro;
			this.dataGrid1.GridLineStyle = System.Windows.Forms.DataGridLineStyle.None;
			this.dataGrid1.HeaderBackColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.HeaderFont = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid1.HeaderForeColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid1.LinkColor = System.Drawing.Color.Teal;
			this.dataGrid1.Name = "dataGrid1";
			this.dataGrid1.ParentRowsBackColor = System.Drawing.Color.Gainsboro;
			this.dataGrid1.ParentRowsForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.SelectionBackColor = System.Drawing.Color.CadetBlue;
			this.dataGrid1.SelectionForeColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid1.ReadOnly = true;

			


			// 
			// resultForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(800, 405);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.dataGrid1,
																		  this.label1});
			this.Name = "resultForm";
			this.Text = "resultForm";
			((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).EndInit();
			this.ResumeLayout(false);

		}
		#endregion

		private void label1_Click(object sender, System.EventArgs e)
		{
		
		}
	}
}
