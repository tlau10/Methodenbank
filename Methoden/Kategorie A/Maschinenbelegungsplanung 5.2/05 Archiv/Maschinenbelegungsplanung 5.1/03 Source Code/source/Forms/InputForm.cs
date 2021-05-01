using System;
using System.Drawing;
using System.Collections;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

public delegate DataGrid PrintableAuftraegeDataGrid( );
public delegate DataGrid PrintablePeriodenDataGrid( );


namespace Maschinenbelegungsplanung
{
	/// <summary>
	/// Summary description for inputForm.
	/// </summary>
	public class InputForm : System.Windows.Forms.Form
	{
		private System.Windows.Forms.GroupBox groupBox1;
		private System.Windows.Forms.ComboBox comboBox1;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Button button1;
		private System.Windows.Forms.GroupBox groupBox2;
		private System.Windows.Forms.RichTextBox textBox1;
		private System.Windows.Forms.DataGrid dataGrid1;
		private System.Windows.Forms.DataGrid dataGrid2;
		private System.Windows.Forms.ImageList imageList1;
		private System.Windows.Forms.DataGridTableStyle dataGridTableStyle1;
		private System.Windows.Forms.DataGridTableStyle dataGridTableStyle2;
		private System.ComponentModel.IContainer components;
		private eingabeDaten _eingabeDaten = null;
		private DataTable[] dataTables = null;

		private int selectedSolver;

		#region default-constructor for OneMaschineSolver: Event - onFileNewClick()
		public InputForm()
		{
			selectedSolver = 3;
			dataTables = new DataTable[2];
			dataTables = createDataTables( null );
			InitializeComponent();
			Form1.printableAuftraegeDataGrid += new PrintableAuftraegeDataGrid( returnDataGridAuftraege );
			Form1.printablePeriodenDataGrid += new PrintablePeriodenDataGrid( returnDataGridPerioden );			
		}
		#endregion

		#region default-constructor for MultipleMaschinesSolver: Event - onFileNewClick()
		public InputForm(int inAnzahlMaschinen)
		{
			selectedSolver = 3;
			dataTables = new DataTable[2];
			dataTables = createDataTablesForMultipleMaschines( null, inAnzahlMaschinen );
			InitializeComponent();
			Form1.printableAuftraegeDataGrid += new PrintableAuftraegeDataGrid( returnDataGridAuftraege );
			Form1.printablePeriodenDataGrid += new PrintablePeriodenDataGrid( returnDataGridPerioden );			
		}
		#endregion

		#region constructor for OneMaschineSolver: Event - onFileOpenClick()
		public InputForm( int selectedSolverParam, eingabeDaten eingabeDatenParam  )
		{
			selectedSolver = selectedSolverParam;
			_eingabeDaten = eingabeDatenParam;
			dataTables = new DataTable[2];
			dataTables = createDataTables( _eingabeDaten );
			InitializeComponent( );
			Form1.printableAuftraegeDataGrid += new PrintableAuftraegeDataGrid( returnDataGridAuftraege );
			Form1.printablePeriodenDataGrid += new PrintablePeriodenDataGrid( returnDataGridPerioden );
		}
		#endregion

		#region constructor for MultipleMaschinesSolver: Event - onFileOpenClick()
		public InputForm( int selectedSolverParam, eingabeDaten eingabeDatenParam, int inAnzahlMaschinen )
		{
			selectedSolver = selectedSolverParam;
			_eingabeDaten = eingabeDatenParam;
			dataTables = new DataTable[2];
			dataTables = createDataTablesForMultipleMaschines( _eingabeDaten, inAnzahlMaschinen );
			InitializeComponent( );
			Form1.printableAuftraegeDataGrid += new PrintableAuftraegeDataGrid( returnDataGridAuftraege );
			Form1.printablePeriodenDataGrid += new PrintablePeriodenDataGrid( returnDataGridPerioden );
		}
		#endregion

		private DataGrid returnDataGridAuftraege()
		{
			return dataGrid1;
		}

		private DataGrid returnDataGridPerioden()
		{
			return dataGrid2;
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
			this.components = new System.ComponentModel.Container();
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(InputForm));
			this.groupBox1 = new System.Windows.Forms.GroupBox();
			this.dataGrid1 = new System.Windows.Forms.DataGrid();

			this.dataGridTableStyle1 = new System.Windows.Forms.DataGridTableStyle();
			this.dataGridTableStyle2 = new System.Windows.Forms.DataGridTableStyle();

			this.comboBox1 = new System.Windows.Forms.ComboBox();
			this.label1 = new System.Windows.Forms.Label();
			this.button1 = new System.Windows.Forms.Button();
			this.imageList1 = new System.Windows.Forms.ImageList(this.components);
			this.groupBox2 = new System.Windows.Forms.GroupBox();
			this.dataGrid2 = new System.Windows.Forms.DataGrid();
			this.textBox1 = new System.Windows.Forms.RichTextBox();
			this.groupBox1.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).BeginInit();
			this.groupBox2.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.dataGrid2)).BeginInit();
			this.SuspendLayout();
			// 
			// groupBox1
			// 
			this.groupBox1.Controls.AddRange(new System.Windows.Forms.Control[] {
																					this.dataGrid1});
			this.groupBox1.Location = new System.Drawing.Point(8, 32);
			this.groupBox1.Name = "groupBox1";
			this.groupBox1.Size = new System.Drawing.Size(200, 312);
			this.groupBox1.TabIndex = 0;
			this.groupBox1.TabStop = false;
			this.groupBox1.Text = "   Auftragsdaten   ";
			// 
			// dataGrid1
			// 
			this.dataGrid1.AlternatingBackColor = System.Drawing.Color.Lavender;
			this.dataGrid1.BackColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid1.BackgroundColor = System.Drawing.Color.LightGray;
			this.dataGrid1.BorderStyle = System.Windows.Forms.BorderStyle.None;
			this.dataGrid1.CaptionBackColor = System.Drawing.Color.LightSteelBlue;
			this.dataGrid1.CaptionFont = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid1.CaptionForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.DataMember = "";
			this.dataGrid1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.dataGrid1.FlatMode = true;
			this.dataGrid1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid1.ForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.GridLineColor = System.Drawing.Color.Gainsboro;
			this.dataGrid1.GridLineStyle = System.Windows.Forms.DataGridLineStyle.None;
			this.dataGrid1.HeaderBackColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.HeaderFont = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid1.HeaderForeColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid1.LinkColor = System.Drawing.Color.Teal;
			this.dataGrid1.Location = new System.Drawing.Point(3, 16);
			this.dataGrid1.Name = "dataGrid1";
			this.dataGrid1.ParentRowsBackColor = System.Drawing.Color.Gainsboro;
			this.dataGrid1.ParentRowsForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid1.SelectionBackColor = System.Drawing.Color.CadetBlue;
			this.dataGrid1.SelectionForeColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid1.Size = new System.Drawing.Size(394, 293);
			this.dataGrid1.TabIndex = 2;
			this.dataGrid1.DataSource = dataTables[0];
			this.dataGrid1.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[] {
																								  this.dataGridTableStyle1,
																								  this.dataGridTableStyle2});
			// 
			// dataGridTableStyle1
			// 
			this.dataGridTableStyle1.DataGrid = this.dataGrid1;
			this.dataGridTableStyle1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.dataGridTableStyle1.MappingName = "";
			// 
			// dataGridTableStyle2
			// 
			this.dataGridTableStyle2.DataGrid = this.dataGrid1;
			this.dataGridTableStyle2.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.dataGridTableStyle2.MappingName = "";
			// 
			// comboBox1
			// 
			this.comboBox1.Items.AddRange(new object[] {
														   "XA Equation Style",
														   "XA MPS-Format",
														   "MOPS MPS-Format",
														   "LP-Solve LP-Format",
														   "STRADA MPS-Format",
														   "Weidenauer Optimizer"});
			this.comboBox1.Location = new System.Drawing.Point(96, 8);
			this.comboBox1.Name = "comboBox1";
			this.comboBox1.Size = new System.Drawing.Size(168, 21);
			this.comboBox1.TabIndex = 1;
			this.comboBox1.SelectedIndex = selectedSolver;
			this.comboBox1.Enabled = false;
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(8, 11);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(80, 16);
			this.label1.TabIndex = 6;
			this.label1.Text = "Solver:";
			// 
			// button1
			// 
			this.button1.Image = ((System.Drawing.Bitmap)(resources.GetObject("button1.Image")));
			this.button1.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
			this.button1.ImageIndex = 0;
			this.button1.ImageList = this.imageList1;
			this.button1.Location = new System.Drawing.Point(632, 360);
			this.button1.Name = "button1";
			this.button1.Size = new System.Drawing.Size(120, 23);
			this.button1.TabIndex = 4;
			this.button1.Text = "berechnen";
			this.button1.Visible = false;
			this.button1.Click += new System.EventHandler(this.button1_Click);
			this.Resize += new System.EventHandler(this.inputForm_Resize);
			// 
			// imageList1
			// 
			this.imageList1.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
			this.imageList1.ImageSize = new System.Drawing.Size(16, 16);
			this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
			this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// groupBox2
			// 
			this.groupBox2.Controls.AddRange(new System.Windows.Forms.Control[] {
																					this.dataGrid2});
			this.groupBox2.Location = new System.Drawing.Point(552, 32);
			this.groupBox2.Name = "groupBox2";
			this.groupBox2.Size = new System.Drawing.Size(330, 312);
			this.groupBox2.TabIndex = 5;
			this.groupBox2.TabStop = false;
			this.groupBox2.Text = "  Maschinendaten  ";
			// 
			// dataGrid2
			// 
			this.dataGrid2.AlternatingBackColor = System.Drawing.Color.Lavender;
			this.dataGrid2.BackColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid2.BackgroundColor = System.Drawing.Color.LightGray;
			this.dataGrid2.BorderStyle = System.Windows.Forms.BorderStyle.None;
			this.dataGrid2.CaptionBackColor = System.Drawing.Color.LightSteelBlue;
			this.dataGrid2.CaptionFont = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid2.CaptionForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid2.DataMember = "";
			this.dataGrid2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.dataGrid2.FlatMode = true;
			this.dataGrid2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid2.ForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid2.GridLineColor = System.Drawing.Color.Gainsboro;
			this.dataGrid2.GridLineStyle = System.Windows.Forms.DataGridLineStyle.None;
			this.dataGrid2.HeaderBackColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid2.HeaderFont = new System.Drawing.Font("Microsoft Sans Serif", 8F);
			this.dataGrid2.HeaderForeColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid2.LinkColor = System.Drawing.Color.Teal;
			this.dataGrid2.Location = new System.Drawing.Point(3,16);
			this.dataGrid2.Name = "dataGrid2";
			this.dataGrid2.ParentRowsBackColor = System.Drawing.Color.Gainsboro;
			this.dataGrid2.ParentRowsForeColor = System.Drawing.Color.MidnightBlue;
			this.dataGrid2.SelectionBackColor = System.Drawing.Color.CadetBlue;
			this.dataGrid2.SelectionForeColor = System.Drawing.Color.WhiteSmoke;
			this.dataGrid2.Size = new System.Drawing.Size(294, 293);
			this.dataGrid2.TabIndex = 3;
			this.dataGrid2.DataSource = dataTables[1];
			// 
			// textBox1
			// 
			this.textBox1.Location = new System.Drawing.Point(16, 24);
			this.textBox1.Name = "textBox1";
			this.textBox1.TabIndex = 0;
			this.textBox1.Text = "";
			// 
			// inputForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(800, 405);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.groupBox2,
																		  this.button1,
																		  this.label1,
																		  this.comboBox1,
																		  this.groupBox1});
			this.Name = "inputForm";
			this.Text = "inputForm";
			this.groupBox1.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).EndInit();
			this.groupBox2.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.dataGrid2)).EndInit();
			this.ResumeLayout(false);

		}
		#endregion

		private void button1_Click(object sender, System.EventArgs e)
		{
			ISolver theSolver = new OneMachineSolver(  );
			theSolver.createMPS( );
			theSolver.solve( );
			//AusgabeDaten results = theSolver.readResults( this._eingabeDaten );
			AusgabeDaten results = new AusgabeDaten( );
			results.IsUnbounded = true;
			results.Failure = "This solution is unbounded!";

			if( !results.IsUnbounded )
			{
				// zeige Lösung an;
			}
			else MessageBox.Show(this, "Dieses Problem ist nicht lösbar!\n\n\"" +results.Failure +"\"","Problem", System.Windows.Forms.MessageBoxButtons.OK, System.Windows.Forms.MessageBoxIcon.Stop );
		}

		private void inputForm_Resize(object sender, System.EventArgs e)
		{
			Size mSize = new Size(this.ClientSize.Width,this.ClientSize.Height);
			mSize.Width-=300;
			mSize.Height=312;

			this.groupBox1.ClientSize = mSize;
			this.groupBox1.Refresh();

			this.dataGrid1.ClientSize = mSize;
			this.dataGrid1.Refresh();

			mSize.Width-=10;
			mSize.Height=312;

			this.dataGrid2.ClientSize = mSize;
			this.dataGrid2.Refresh();

			System.Drawing.Point mLocation = new System.Drawing.Point(this.ClientSize.Width-280, 34);
			mSize.Width=270;
			mSize.Height=312;
			
			this.groupBox2.Location = mLocation;
			this.groupBox2.ClientSize=mSize;

			
		}

		private DataTable[] createDataTables( eingabeDaten eingabeDatenParam )
		{
			DataTable[] rValueDataTable = new DataTable[2];
			DataTable auftraege = new DataTable("auftraege");
			DataTable perioden = new DataTable("perioden");
			DataColumn auftraegeColum;
			DataColumn periodenColum;

			#region auftraegeDataTable anlegen
			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "Nr.";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );
			
			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.String");
			auftraegeColum.ColumnName = "Bezeichnung";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );			

			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "Dauer";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );			

			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "von Periode";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );

			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "bis Periode";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );
			#endregion

			#region periodenDataTable anlegen
			periodenColum = new DataColumn( );
			periodenColum.DataType = Type.GetType("System.Int32");
			periodenColum.ColumnName = "Perioden-Nr.";
			periodenColum.ReadOnly = false;
			periodenColum.AllowDBNull = false;
			perioden.Columns.Add( periodenColum );
			
			periodenColum = new DataColumn( );
			periodenColum.DataType = Type.GetType("System.Int32");
			periodenColum.ColumnName = "# Maschinen Typ 1";
			periodenColum.ReadOnly = false;
			periodenColum.AllowDBNull = false;
			perioden.Columns.Add( periodenColum );			
			#endregion

			#region Daten in auftraegeDateTable setzen
			DataRow auftraegeRow;
			if( eingabeDatenParam == null )
			{
				for( int iA = 0; iA < 1; iA++ ) 
				{
					auftraegeRow = auftraege.NewRow( );
					auftraegeRow["Nr."] = 0;
					auftraegeRow["Bezeichnung"] = " ";
					auftraegeRow["Dauer"] = 0;
					auftraegeRow["von Periode"] = 0;
					auftraegeRow["bis Periode"] = 0;
					auftraege.Rows.Add( auftraegeRow );
				}
			}
			else 
			{
				for( int iAa = 0; iAa < eingabeDatenParam.auftragsListe.Length; iAa++ )
				{
					auftraegeRow = auftraege.NewRow( );
					auftraegeRow["Nr."] = eingabeDatenParam.auftragsListe[iAa].Nummer;
					auftraegeRow["Bezeichnung"] = eingabeDatenParam.auftragsListe[iAa].Bezeichnung;
					auftraegeRow["Dauer"] = eingabeDatenParam.auftragsListe[iAa].Dauer;
					auftraegeRow["von Periode"] = eingabeDatenParam.auftragsListe[iAa].Beginn;
					auftraegeRow["bis Periode"] = eingabeDatenParam.auftragsListe[iAa].Ende;
					auftraege.Rows.Add( auftraegeRow );
				}

			}

			#endregion

			#region Daten in periodenDataTable setzen
			DataRow periodenRow;
			if( eingabeDatenParam == null )
			{
				for( int iB = 1; iB < 3; iB++ ) 
				{
					periodenRow = perioden.NewRow( );
					periodenRow["Perioden-Nr."] = iB;
					periodenRow["# Maschinen Typ 1"] = 1;
					perioden.Rows.Add( periodenRow );
				}
			}
			else 
			{
				for( int iBa = 0; iBa < eingabeDatenParam.periodenListe.Length; iBa++ )
				{
					periodenRow = perioden.NewRow( );
					periodenRow["Perioden-Nr."] = eingabeDatenParam.periodenListe[iBa].Nummer;
					periodenRow["# Maschinen Typ 1"] = eingabeDatenParam.periodenListe[iBa].Anzahl;
					perioden.Rows.Add( periodenRow );
				}
			}

			#endregion

			rValueDataTable[0] = auftraege;
			rValueDataTable[1] = perioden;
			return rValueDataTable;
		}


		private DataTable[] createDataTablesForMultipleMaschines( eingabeDaten eingabeDatenParam, int inAnzahlMaschinen)
		{
			DataTable[] rValueDataTable = new DataTable[2];
			DataTable auftraege = new DataTable("auftraege");
			DataTable perioden = new DataTable("perioden");
			DataColumn auftraegeColum;
			DataColumn periodenColum;

			#region auftraegeDataTable anlegen
			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "Nr.";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );
			
			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.String");
			auftraegeColum.ColumnName = "Bezeichnung";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );			

			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "Dauer";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );		
	
			for (int i = 1; i<=inAnzahlMaschinen; i++)
			{
				auftraegeColum = new DataColumn( );
				auftraegeColum.DataType = Type.GetType("System.Int32");
				auftraegeColum.ColumnName = "Typ " + i.ToString();
				auftraegeColum.ReadOnly = false;
				auftraegeColum.AllowDBNull = false;
				auftraege.Columns.Add( auftraegeColum );
			}

			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "von Periode";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );

			auftraegeColum = new DataColumn( );
			auftraegeColum.DataType = Type.GetType("System.Int32");
			auftraegeColum.ColumnName = "bis Periode";
			auftraegeColum.ReadOnly = false;
			auftraegeColum.AllowDBNull = false;
			auftraege.Columns.Add( auftraegeColum );
			#endregion

			#region periodenDataTable anlegen
			periodenColum = new DataColumn( );
			periodenColum.DataType = Type.GetType("System.Int32");
			periodenColum.ColumnName = "Perioden-Nr.";
			periodenColum.ReadOnly = false;
			periodenColum.AllowDBNull = false;
			perioden.Columns.Add( periodenColum );

			for (int i = 0; i<inAnzahlMaschinen; i++)
			{			
				periodenColum = new DataColumn( );
				periodenColum.DataType = Type.GetType("System.Int32");
				periodenColum.ColumnName = "# Maschinen Typ " + i.ToString();
				periodenColum.ReadOnly = false;
				periodenColum.AllowDBNull = false;
				perioden.Columns.Add( periodenColum );	
			}
			#endregion

			#region Daten in auftraegeDateTable setzen
			DataRow auftraegeRow;
			if( eingabeDatenParam == null )
			{
				for( int iA = 0; iA < 1; iA++ ) 
				{
					auftraegeRow = auftraege.NewRow( );
					auftraegeRow["Nr."] = 0;
					auftraegeRow["Bezeichnung"] = " ";
					auftraegeRow["Dauer"] = 0;
					for (int i = 1; i<=inAnzahlMaschinen; i++)
					{
						auftraegeRow["Typ " + i.ToString()] = 0;
					}
					auftraegeRow["von Periode"] = 0;
					auftraegeRow["bis Periode"] = 0;
					auftraege.Rows.Add( auftraegeRow );
				}
			}
			else 
			{
				for( int iAa = 0; iAa < eingabeDatenParam.auftragsListe.Length; iAa++ )
				{
					auftraegeRow = auftraege.NewRow( );
					auftraegeRow["Nr."] = eingabeDatenParam.auftragsListe[iAa].Nummer;
					auftraegeRow["Bezeichnung"] = eingabeDatenParam.auftragsListe[iAa].Bezeichnung;
					auftraegeRow["Dauer"] = eingabeDatenParam.auftragsListe[iAa].Dauer;
					for (int i = 0; i<inAnzahlMaschinen; i++)
					{
						auftraegeRow["Typ " + (i+1).ToString()] = eingabeDatenParam.auftragsListe[iAa].Typen[i];
					}
					auftraegeRow["von Periode"] = eingabeDatenParam.auftragsListe[iAa].Beginn;
					auftraegeRow["bis Periode"] = eingabeDatenParam.auftragsListe[iAa].Ende;
					auftraege.Rows.Add( auftraegeRow );
				}

			}
			#endregion

			#region Daten in periodenDataTable setzen
			DataRow periodenRow;
			if( eingabeDatenParam == null )
			{
				for( int iB = 0; iB < 3; iB++ ) 
				{
					periodenRow = perioden.NewRow( );
					periodenRow["Perioden-Nr."] = iB;

					for (int i = 0; i<inAnzahlMaschinen; i++)
					{
						periodenRow["# Maschinen Typ " + i.ToString()] = 1;
					}

					perioden.Rows.Add( periodenRow );
				}
			}
			else 
			{
				for( int iBa = 0; iBa < eingabeDatenParam.periodenListe.Length; iBa++ )
				{
					periodenRow = perioden.NewRow( );
					periodenRow["Perioden-Nr."] = eingabeDatenParam.periodenListe[iBa].Nummer;
					for (int i = 0; i<inAnzahlMaschinen; i++)
					{
						periodenRow["# Maschinen Typ " + i.ToString()] = eingabeDatenParam.periodenListe[iBa].Typen[i];
					}
					perioden.Rows.Add( periodenRow );
				}
			}
			#endregion

			rValueDataTable[0] = auftraege;
			rValueDataTable[1] = perioden;
			return rValueDataTable;
		}


	}
}
