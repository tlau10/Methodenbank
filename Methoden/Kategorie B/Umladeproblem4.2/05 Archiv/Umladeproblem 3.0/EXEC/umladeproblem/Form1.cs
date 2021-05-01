using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;

namespace umladeproblem
{
	/// <summary>
	/// Summary description for Form1.
	/// </summary>
	public class Form1 : System.Windows.Forms.Form
	{
		private System.Windows.Forms.MainMenu mainMenu1;
		private System.Windows.Forms.MenuItem menuItem1;
		private System.Windows.Forms.MenuItem menuItem2;
		private System.Windows.Forms.MenuItem menuItem3;
		private System.Windows.Forms.MenuItem menuItem4;
		private System.Windows.Forms.MenuItem menuItem5;
		private System.Windows.Forms.MenuItem menuItem6;
		private System.Windows.Forms.MenuItem menuItem7;
		private System.Windows.Forms.Button btn_uebern;
		private System.Windows.Forms.NumericUpDown num_Knotenzahl;
		private System.Windows.Forms.NumericUpDown num_Abnehmer;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.SaveFileDialog saveFileDialog1;
		private System.Windows.Forms.MenuItem menuItem8;
		private System.Windows.Forms.OpenFileDialog openFileDialog1;
		private System.Windows.Forms.DataGrid dataGrid1;
		private System.Data.DataColumn dataColumn1;
		private System.Data.DataColumn dataColumn2;
		private System.Data.DataSet dataSetAnbieter;
		private System.Windows.Forms.DataGrid dataGrid2;
		private System.Data.DataSet dataSetAbnehmer;
		private System.Data.DataTable myTableAbnehmer;
		private System.Data.DataColumn dataColumn3;
		private System.Data.DataColumn dataColumn4;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.StatusBar myStatusBar;
		private System.Windows.Forms.NumericUpDown num_Anbieter;
		private System.Data.DataTable myTableAnbieter;
		private System.Windows.Forms.Panel panel1;
		private System.Windows.Forms.PictureBox pictureBox1;
		private System.Windows.Forms.Panel panel2;
		private System.Windows.Forms.PictureBox pictureBox2;
		private System.ComponentModel.IContainer components;

		private ArrayList myAnbieter = new ArrayList();
		private int myMouseX = 0;
		private int myMouseY = 0;
		//private Anbieter[] myAnbieter = new Anbieter[100];
		//private Object[] myObjects = null;

		public Form1()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();
			

			//
			// TODO: Add any constructor code after InitializeComponent call
			//
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
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
			this.mainMenu1 = new System.Windows.Forms.MainMenu();
			this.menuItem1 = new System.Windows.Forms.MenuItem();
			this.menuItem5 = new System.Windows.Forms.MenuItem();
			this.menuItem2 = new System.Windows.Forms.MenuItem();
			this.menuItem4 = new System.Windows.Forms.MenuItem();
			this.menuItem8 = new System.Windows.Forms.MenuItem();
			this.menuItem3 = new System.Windows.Forms.MenuItem();
			this.menuItem6 = new System.Windows.Forms.MenuItem();
			this.menuItem7 = new System.Windows.Forms.MenuItem();
			this.btn_uebern = new System.Windows.Forms.Button();
			this.num_Knotenzahl = new System.Windows.Forms.NumericUpDown();
			this.num_Abnehmer = new System.Windows.Forms.NumericUpDown();
			this.num_Anbieter = new System.Windows.Forms.NumericUpDown();
			this.label1 = new System.Windows.Forms.Label();
			this.label3 = new System.Windows.Forms.Label();
			this.label2 = new System.Windows.Forms.Label();
			this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
			this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
			this.dataGrid1 = new System.Windows.Forms.DataGrid();
			this.myTableAbnehmer = new System.Data.DataTable();
			this.dataColumn3 = new System.Data.DataColumn();
			this.dataColumn4 = new System.Data.DataColumn();
			this.myTableAnbieter = new System.Data.DataTable();
			this.dataColumn1 = new System.Data.DataColumn();
			this.dataColumn2 = new System.Data.DataColumn();
			this.dataSetAnbieter = new System.Data.DataSet();
			this.dataGrid2 = new System.Windows.Forms.DataGrid();
			this.dataSetAbnehmer = new System.Data.DataSet();
			this.label4 = new System.Windows.Forms.Label();
			this.label5 = new System.Windows.Forms.Label();
			this.myStatusBar = new System.Windows.Forms.StatusBar();
			this.panel1 = new System.Windows.Forms.Panel();
			this.pictureBox1 = new System.Windows.Forms.PictureBox();
			this.panel2 = new System.Windows.Forms.Panel();
			this.pictureBox2 = new System.Windows.Forms.PictureBox();
			((System.ComponentModel.ISupportInitialize)(this.num_Knotenzahl)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.num_Abnehmer)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.num_Anbieter)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.myTableAbnehmer)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.myTableAnbieter)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.dataSetAnbieter)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.dataGrid2)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.dataSetAbnehmer)).BeginInit();
			this.panel2.SuspendLayout();
			this.SuspendLayout();
			// 
			// mainMenu1
			// 
			this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem1,
																					  this.menuItem6});
			// 
			// menuItem1
			// 
			this.menuItem1.Index = 0;
			this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem5,
																					  this.menuItem2,
																					  this.menuItem4,
																					  this.menuItem8,
																					  this.menuItem3});
			this.menuItem1.Text = "Datei";
			// 
			// menuItem5
			// 
			this.menuItem5.Index = 0;
			this.menuItem5.Text = "Neu";
			this.menuItem5.Click += new System.EventHandler(this.menuItem5_Click);
			// 
			// menuItem2
			// 
			this.menuItem2.Index = 1;
			this.menuItem2.Shortcut = System.Windows.Forms.Shortcut.CtrlO;
			this.menuItem2.Text = "Öffnen";
			this.menuItem2.Click += new System.EventHandler(this.menuItem2_Click);
			// 
			// menuItem4
			// 
			this.menuItem4.Enabled = false;
			this.menuItem4.Index = 2;
			this.menuItem4.Shortcut = System.Windows.Forms.Shortcut.CtrlS;
			this.menuItem4.Text = "Speichern";
			this.menuItem4.Click += new System.EventHandler(this.menuItem4_Click);
			// 
			// menuItem8
			// 
			this.menuItem8.Enabled = false;
			this.menuItem8.Index = 3;
			this.menuItem8.Text = "Speichern unter...";
			this.menuItem8.Click += new System.EventHandler(this.menuItem8_Click);
			// 
			// menuItem3
			// 
			this.menuItem3.Index = 4;
			this.menuItem3.Text = "Beenden";
			this.menuItem3.Click += new System.EventHandler(this.menuItem3_Click);
			// 
			// menuItem6
			// 
			this.menuItem6.Index = 1;
			this.menuItem6.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem7});
			this.menuItem6.Text = "Hilfe";
			// 
			// menuItem7
			// 
			this.menuItem7.Index = 0;
			this.menuItem7.Text = "Info";
			this.menuItem7.Click += new System.EventHandler(this.menuItem7_Click);
			// 
			// btn_uebern
			// 
			this.btn_uebern.Location = new System.Drawing.Point(176, 16);
			this.btn_uebern.Name = "btn_uebern";
			this.btn_uebern.Size = new System.Drawing.Size(96, 24);
			this.btn_uebern.TabIndex = 4;
			this.btn_uebern.Text = "Übernehmen";
			this.btn_uebern.Click += new System.EventHandler(this.btn_uebern_Click);
			// 
			// num_Knotenzahl
			// 
			this.num_Knotenzahl.Location = new System.Drawing.Point(16, 16);
			this.num_Knotenzahl.Maximum = new System.Decimal(new int[] {
																		   24,
																		   0,
																		   0,
																		   0});
			this.num_Knotenzahl.Minimum = new System.Decimal(new int[] {
																		   4,
																		   0,
																		   0,
																		   0});
			this.num_Knotenzahl.Name = "num_Knotenzahl";
			this.num_Knotenzahl.Size = new System.Drawing.Size(48, 20);
			this.num_Knotenzahl.TabIndex = 5;
			this.num_Knotenzahl.Value = new System.Decimal(new int[] {
																		 4,
																		 0,
																		 0,
																		 0});
			this.num_Knotenzahl.ValueChanged += new System.EventHandler(this.num_Knotenzahl_ValueChanged);
			// 
			// num_Abnehmer
			// 
			this.num_Abnehmer.Location = new System.Drawing.Point(16, 80);
			this.num_Abnehmer.Maximum = new System.Decimal(new int[] {
																		 24,
																		 0,
																		 0,
																		 0});
			this.num_Abnehmer.Minimum = new System.Decimal(new int[] {
																		 1,
																		 0,
																		 0,
																		 0});
			this.num_Abnehmer.Name = "num_Abnehmer";
			this.num_Abnehmer.Size = new System.Drawing.Size(48, 20);
			this.num_Abnehmer.TabIndex = 6;
			this.num_Abnehmer.Value = new System.Decimal(new int[] {
																	   1,
																	   0,
																	   0,
																	   0});
			this.num_Abnehmer.ValueChanged += new System.EventHandler(this.num_Abnehmer_ValueChanged);
			// 
			// num_Anbieter
			// 
			this.num_Anbieter.Location = new System.Drawing.Point(16, 48);
			this.num_Anbieter.Maximum = new System.Decimal(new int[] {
																		 24,
																		 0,
																		 0,
																		 0});
			this.num_Anbieter.Minimum = new System.Decimal(new int[] {
																		 1,
																		 0,
																		 0,
																		 0});
			this.num_Anbieter.Name = "num_Anbieter";
			this.num_Anbieter.Size = new System.Drawing.Size(48, 20);
			this.num_Anbieter.TabIndex = 7;
			this.num_Anbieter.Value = new System.Decimal(new int[] {
																	   1,
																	   0,
																	   0,
																	   0});
			this.num_Anbieter.ValueChanged += new System.EventHandler(this.numAnbieter_ValueChanged);
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(80, 19);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(73, 14);
			this.label1.TabIndex = 8;
			this.label1.Text = "Knotenzahl";
			// 
			// label3
			// 
			this.label3.Location = new System.Drawing.Point(80, 52);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(73, 14);
			this.label3.TabIndex = 10;
			this.label3.Text = "Anbieter";
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(80, 84);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(73, 14);
			this.label2.TabIndex = 11;
			this.label2.Text = "Abnehmer";
			// 
			// saveFileDialog1
			// 
			this.saveFileDialog1.Title = "Datei speichern";
			// 
			// openFileDialog1
			// 
			this.openFileDialog1.Title = "Datei öffnen...";
			// 
			// dataGrid1
			// 
			this.dataGrid1.AccessibleName = "myAnbieterDataGrid";
			this.dataGrid1.AccessibleRole = System.Windows.Forms.AccessibleRole.Text;
			this.dataGrid1.AllowSorting = false;
			this.dataGrid1.Anchor = (((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right);
			this.dataGrid1.BackgroundColor = System.Drawing.SystemColors.Control;
			this.dataGrid1.BorderStyle = System.Windows.Forms.BorderStyle.None;
			this.dataGrid1.CaptionVisible = false;
			this.dataGrid1.CausesValidation = false;
			this.dataGrid1.DataMember = "";
			this.dataGrid1.DataSource = this.myTableAbnehmer;
			this.dataGrid1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.dataGrid1.Location = new System.Drawing.Point(16, 264);
			this.dataGrid1.Name = "dataGrid1";
			this.dataGrid1.RowHeadersVisible = false;
			this.dataGrid1.Size = new System.Drawing.Size(168, 104);
			this.dataGrid1.TabIndex = 12;
			// 
			// myTableAbnehmer
			// 
			this.myTableAbnehmer.Columns.AddRange(new System.Data.DataColumn[] {
																				   this.dataColumn3,
																				   this.dataColumn4});
			this.myTableAbnehmer.TableName = "Abnehmer";
			// 
			// dataColumn3
			// 
			this.dataColumn3.ColumnName = "Knotennr.";
			this.dataColumn3.DefaultValue = "";
			// 
			// dataColumn4
			// 
			this.dataColumn4.ColumnName = "Menge";
			this.dataColumn4.DefaultValue = "";
			// 
			// myTableAnbieter
			// 
			this.myTableAnbieter.Columns.AddRange(new System.Data.DataColumn[] {
																				   this.dataColumn1,
																				   this.dataColumn2});
			this.myTableAnbieter.MinimumCapacity = 4;
			this.myTableAnbieter.TableName = "anbieter";
			// 
			// dataColumn1
			// 
			this.dataColumn1.ColumnName = "Knotennr.";
			this.dataColumn1.DefaultValue = "";
			// 
			// dataColumn2
			// 
			this.dataColumn2.ColumnName = "Menge";
			this.dataColumn2.DefaultValue = "";
			// 
			// dataSetAnbieter
			// 
			this.dataSetAnbieter.DataSetName = "myDataSet_anbieter";
			this.dataSetAnbieter.Locale = new System.Globalization.CultureInfo("de-DE");
			this.dataSetAnbieter.Tables.AddRange(new System.Data.DataTable[] {
																				 this.myTableAnbieter});
			// 
			// dataGrid2
			// 
			this.dataGrid2.AccessibleName = "myAnbieterDataGrid";
			this.dataGrid2.AccessibleRole = System.Windows.Forms.AccessibleRole.Text;
			this.dataGrid2.AllowSorting = false;
			this.dataGrid2.Anchor = (((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right);
			this.dataGrid2.BackgroundColor = System.Drawing.SystemColors.Control;
			this.dataGrid2.BorderStyle = System.Windows.Forms.BorderStyle.None;
			this.dataGrid2.CaptionVisible = false;
			this.dataGrid2.CausesValidation = false;
			this.dataGrid2.DataMember = "";
			this.dataGrid2.DataSource = this.myTableAnbieter;
			this.dataGrid2.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.dataGrid2.Location = new System.Drawing.Point(16, 136);
			this.dataGrid2.Name = "dataGrid2";
			this.dataGrid2.RowHeadersVisible = false;
			this.dataGrid2.Size = new System.Drawing.Size(168, 104);
			this.dataGrid2.TabIndex = 13;
			// 
			// dataSetAbnehmer
			// 
			this.dataSetAbnehmer.DataSetName = "NewDataSet";
			this.dataSetAbnehmer.Locale = new System.Globalization.CultureInfo("de-DE");
			this.dataSetAbnehmer.Tables.AddRange(new System.Data.DataTable[] {
																				 this.myTableAbnehmer});
			// 
			// label4
			// 
			this.label4.Location = new System.Drawing.Point(16, 120);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(80, 16);
			this.label4.TabIndex = 14;
			this.label4.Text = "Anbieterdaten";
			// 
			// label5
			// 
			this.label5.Location = new System.Drawing.Point(16, 248);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(92, 16);
			this.label5.TabIndex = 15;
			this.label5.Text = "Abnehmerdaten";
			// 
			// myStatusBar
			// 
			this.myStatusBar.Cursor = System.Windows.Forms.Cursors.Default;
			this.myStatusBar.Location = new System.Drawing.Point(0, 504);
			this.myStatusBar.Name = "myStatusBar";
			this.myStatusBar.Size = new System.Drawing.Size(712, 22);
			this.myStatusBar.TabIndex = 16;
			this.myStatusBar.Visible = false;
			// 
			// panel1
			// 
			this.panel1.AllowDrop = true;
			this.panel1.Anchor = System.Windows.Forms.AnchorStyles.None;
			this.panel1.BackColor = System.Drawing.Color.White;
			this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.panel1.ImeMode = System.Windows.Forms.ImeMode.NoControl;
			this.panel1.Location = new System.Drawing.Point(208, 80);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(488, 408);
			this.panel1.TabIndex = 17;
			this.panel1.DragEnter += new System.Windows.Forms.DragEventHandler(this.panel1_DragEnter);
			this.panel1.DragDrop += new System.Windows.Forms.DragEventHandler(this.panel1_DragDrop);
			this.panel1.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panel1_MouseMove);
			// 
			// pictureBox1
			// 
			this.pictureBox1.Anchor = System.Windows.Forms.AnchorStyles.None;
			this.pictureBox1.BackColor = System.Drawing.Color.IndianRed;
			this.pictureBox1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			this.pictureBox1.Location = new System.Drawing.Point(8, 16);
			this.pictureBox1.Name = "pictureBox1";
			this.pictureBox1.Size = new System.Drawing.Size(40, 40);
			this.pictureBox1.TabIndex = 18;
			this.pictureBox1.TabStop = false;
			this.pictureBox1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseDown);
			// 
			// panel2
			// 
			this.panel2.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(224)), ((System.Byte)(224)), ((System.Byte)(224)));
			this.panel2.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.panel2.Controls.AddRange(new System.Windows.Forms.Control[] {
																				 this.pictureBox2,
																				 this.pictureBox1});
			this.panel2.Location = new System.Drawing.Point(24, 384);
			this.panel2.Name = "panel2";
			this.panel2.Size = new System.Drawing.Size(160, 96);
			this.panel2.TabIndex = 18;
			// 
			// pictureBox2
			// 
			this.pictureBox2.BackColor = System.Drawing.Color.RoyalBlue;
			this.pictureBox2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			this.pictureBox2.Location = new System.Drawing.Point(88, 16);
			this.pictureBox2.Name = "pictureBox2";
			this.pictureBox2.Size = new System.Drawing.Size(40, 40);
			this.pictureBox2.TabIndex = 19;
			this.pictureBox2.TabStop = false;
			// 
			// Form1
			// 
			this.AllowDrop = true;
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(712, 526);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.panel2,
																		  this.myStatusBar,
																		  this.label5,
																		  this.label4,
																		  this.dataGrid2,
																		  this.dataGrid1,
																		  this.label2,
																		  this.label3,
																		  this.label1,
																		  this.num_Anbieter,
																		  this.num_Abnehmer,
																		  this.num_Knotenzahl,
																		  this.btn_uebern,
																		  this.panel1});
			this.Menu = this.mainMenu1;
			this.Name = "Form1";
			this.StartPosition = System.Windows.Forms.FormStartPosition.Manual;
			this.Text = "Umladeproblem";
			this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Form1_MouseMove);
			((System.ComponentModel.ISupportInitialize)(this.num_Knotenzahl)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.num_Abnehmer)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.num_Anbieter)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.dataGrid1)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.myTableAbnehmer)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.myTableAnbieter)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.dataSetAnbieter)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.dataGrid2)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.dataSetAbnehmer)).EndInit();
			this.panel2.ResumeLayout(false);
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new Form1());
		}

		private void menuItem3_Click(object sender, System.EventArgs e)
		{
			Application.Exit();
		}

		private void btn_uebern_Click(object sender, System.EventArgs e)
		{
			this.panel1.Controls.AddRange(new System.Windows.Forms.Control[] {
																				 
																				 this.pictureBox1});
		}

		private void menuItem7_Click(object sender, System.EventArgs e)
		{
			frm_Info myInfo = new frm_Info();
			myInfo.Show();
			
		}

		private void menuItem4_Click(object sender, System.EventArgs e)
		{
			//
			// Datei speichern...
			//
		}
		// Speichern unter...
		private void menuItem8_Click(object sender, System.EventArgs e)
		{
			saveFileDialog1.ShowDialog();
			String mySaveFilePath = "";
			mySaveFilePath = saveFileDialog1.FileName.ToString();
			Console.Write("mySaveFilePath: " + mySaveFilePath);

		}
		// Öffnen...
		private void menuItem2_Click(object sender, System.EventArgs e)
		{
			myStatusBar.Text = "Datei öffnen...";
			myStatusBar.Show();
			openFileDialog1.ShowDialog();
			String myOpenFilePath = "";
			myOpenFilePath = openFileDialog1.FileName.ToString();
			myStatusBar.Text = myOpenFilePath;
			myStatusBar.Update();
		}

		private void toolBar1_ButtonClick(object sender, System.Windows.Forms.ToolBarButtonClickEventArgs e)
		{
		
		}

		private void num_Knotenzahl_ValueChanged(object sender, System.EventArgs e)
		{
			menuItem4.Enabled = true;
			menuItem8.Enabled = true;
		}

		private void numAnbieter_ValueChanged(object sender, System.EventArgs e)
		{
			menuItem4.Enabled = true;
			menuItem8.Enabled = true;
		}

		private void num_Abnehmer_ValueChanged(object sender, System.EventArgs e)
		{
			menuItem4.Enabled = true;
			menuItem8.Enabled = true;
		}

		private void menuItem5_Click(object sender, System.EventArgs e)
		{
			num_Knotenzahl.Value = 4;
			num_Anbieter.Value = 1;
			num_Abnehmer.Value = 1;
			menuItem4.Enabled = false;
			menuItem8.Enabled = false;
		}

		private void pictureBox1_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			
			Anbieter myTmpAnbieter = new Anbieter("IndianRed");
			this.myAnbieter.Add(myTmpAnbieter);
			pictureBox1.DoDragDrop(pictureBox1, DragDropEffects.Copy);		
		}

		private void panel1_DragEnter(object sender, System.Windows.Forms.DragEventArgs e)
		{
		   e.Effect = DragDropEffects.Copy;  
		}

		private void panel1_DragDrop(object sender, System.Windows.Forms.DragEventArgs e)
		{   		
			int myLastAnbieter = this.myAnbieter.Count-1;
			Anbieter myTmpAnbieter = (Anbieter)this.myAnbieter[myLastAnbieter];
			myTmpAnbieter.setLocation( e.X-(this.Location.X+panel1.Location.X), e.Y-(this.Location.Y+panel1.Location.Y)-50);
			this.panel1.Controls.Add(myTmpAnbieter.getMyPicBox());
			//Console.WriteLine("Elemesnt dazugemacht!! ");
			System.Diagnostics.Debug.WriteLine(
				"Mouse X: "+((e.X-(this.Location.X+panel1.Location.X))).ToString()+
				" Mouse Y: "+((e.Y-(this.Location.Y+panel1.Location.Y))).ToString()
			);
				
						
		}

		private void Form1_DragEnter(object sender, System.Windows.Forms.DragEventArgs e)
		{
			e.Effect = DragDropEffects.Copy;  
		}

		private void Form1_DragDrop(object sender, System.Windows.Forms.DragEventArgs e)
		{

			
			this.pictureBox1.Show();
			//Form1.ActiveForm.Container.Add(this.pictureBox1);
			//Form1.SendToBack();
			//pictureBox1.BringToFront();
			//pictureBox1.Show();
		}

		
		private void panel1_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			this.myMouseX = e.X;
			this.myMouseY = e.Y;
			//Console.WriteLine("Mouse X: " + e.X.ToString() + " --- y : " + e.Y.ToString());
	
		}

		private void Form1_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			this.myMouseX = e.X;
			this.myMouseY = e.Y;
			//Console.WriteLine("Mouse X: " + e.X.ToString() + " --- y : " + e.Y.ToString());
	
		}

		

		
		

		
	}
}
