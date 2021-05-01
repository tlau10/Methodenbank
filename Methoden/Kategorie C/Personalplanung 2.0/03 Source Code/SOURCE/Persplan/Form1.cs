using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Xml;
using PersplanLibrary;

namespace Persplan
{	
	/// <summary>
	/// Main Form for Persplan application
	/// </summary>
	public class Form1 : System.Windows.Forms.Form
	{
		private string Filename = null;

		private System.Windows.Forms.Splitter splitter1;
		public System.Windows.Forms.TreeView trvMain;
		private System.Windows.Forms.Panel pnlShift;
		private System.Windows.Forms.MainMenu mnuMain;
		private System.Windows.Forms.Label lblShiftHeader;
		private System.Windows.Forms.Label lblShiftFrom;
		private System.Windows.Forms.Label lblBreakUntil;
		private System.Windows.Forms.Label lblBreakFrom;
		private System.Windows.Forms.Label lblShiftUntil;
		private System.Windows.Forms.GroupBox grpShiftTime;
		private System.Windows.Forms.GroupBox grpShiftBreak;
		private System.Windows.Forms.GroupBox grpOptions;
		private System.Windows.Forms.Label lblShiftPreference;
		private System.Windows.Forms.Label lblShiftMaxPersons;
		private System.Windows.Forms.Panel pnlRequirement;
		private System.Windows.Forms.GroupBox groupBox1;
		private System.Windows.Forms.Label lblRequirementPersons;
		private System.Windows.Forms.GroupBox grpRequirementInterval;
		private System.Windows.Forms.Label lblRequirementUntil;
		private System.Windows.Forms.Label lblRequirementFrom;
		private System.Windows.Forms.Label lblRequirementHeader;
		private System.Windows.Forms.TextBox txtRequirementPersons;
		private System.Windows.Forms.TextBox txtShiftMaxPersons;
		private System.Windows.Forms.NumericUpDown nudShiftPreference;
		private System.Windows.Forms.Panel pnlAllRequirements;
		private System.Windows.Forms.Label lblAllRequirementsHeader;
		private System.Windows.Forms.ListView lsvAllRequirements;
		private System.Windows.Forms.ColumnHeader colFrom;
		private System.Windows.Forms.ColumnHeader colUntil;
		private System.Windows.Forms.ColumnHeader colRequiredPersons;
		private System.Windows.Forms.Panel pnlAllShifts;
		private System.Windows.Forms.ListView lsvAllShifts;
		private System.Windows.Forms.Label lblAllShiftsHeader;
		private System.Windows.Forms.ColumnHeader colShiftStart;
		private System.Windows.Forms.ColumnHeader colShiftEnd;
		private System.Windows.Forms.ColumnHeader colBreakStart;
		private System.Windows.Forms.ColumnHeader colBreakEnd;
		private System.Windows.Forms.ColumnHeader colPreference;
		private System.Windows.Forms.ColumnHeader colMaxPersons;
		private System.Windows.Forms.Panel pnlWelcome;
		private System.Windows.Forms.Label lblWelcomeHeader;
		private System.Windows.Forms.Label lblWelcome;
		private System.Windows.Forms.ContextMenu cmAllShiftsRequirements;
		private System.Windows.Forms.Button btnOK;
		private System.Windows.Forms.Button button1;
		private System.Windows.Forms.MenuItem menuItem5;
		private System.Windows.Forms.MenuItem menuItem7;
		private System.Windows.Forms.MenuItem menuItem10;
		private System.Windows.Forms.MenuItem mnuFile;
		private System.Windows.Forms.MenuItem mnuSave;
		private System.Windows.Forms.MenuItem mnuSaveAs;
		private System.Windows.Forms.MenuItem mnuOpen;
		private System.Windows.Forms.MenuItem mnuEnd;
		private System.Windows.Forms.MenuItem mnuFileNew;
		private System.Windows.Forms.MenuItem mnuHelp;
		private System.Windows.Forms.MenuItem mnuHelpInfo;
		private System.Windows.Forms.ContextMenu cmShiftRequirement;
		private System.Windows.Forms.MenuItem cmShiftRequirementDelete;
		private System.Windows.Forms.MenuItem cmAllShiftsRequirementsAdd;
		private System.Windows.Forms.MenuItem cmShiftRequirementEdit;
		private System.Windows.Forms.DateTimePicker dtpShiftFrom;
		private System.Windows.Forms.DateTimePicker dtpShiftUntil;
		private System.Windows.Forms.DateTimePicker dtpBreakFrom;
		private System.Windows.Forms.DateTimePicker dtpBreakUntil;
		private System.Windows.Forms.OpenFileDialog ofdMain;
		private System.Windows.Forms.SaveFileDialog sfdMain;

		private ExtendedTreeNode RootNode;
		private ExtendedTreeNode ShiftNode;
		private ExtendedTreeNode RequirementNode;
		private ExtendedTreeNode SolutionNode;
		private System.Windows.Forms.Label lblRequirementFromValue;
		private System.Windows.Forms.Label lblRequirementUntilValue;
		private UserControls.GraphControl graphControl1;
		private System.Collections.ArrayList Solvers;
		private System.Windows.Forms.Panel pnlSolution;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label lblSolutionHeader;
		private System.Windows.Forms.ComboBox cboSolver;
		private System.Windows.Forms.Button btnSolve;
		private System.Windows.Forms.ColumnHeader Start;
		private System.Windows.Forms.ColumnHeader End;
		private System.Windows.Forms.ColumnHeader BreakStart;
		private System.Windows.Forms.ColumnHeader BreakEnd;
		private System.Windows.Forms.ColumnHeader Activity;
		private System.Windows.Forms.ColumnHeader BreakActivity;
		private System.Windows.Forms.ListView lsvSolution;
		private UserControls.GraphControl graphControl2;

		/// <summary>
		/// Erforderliche Designervariable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public Form1()
		{
			//
			// Erforderlich für die Windows Form-Designerunterstützung
			//
			frmInitPlugin InitPlugin = new frmInitPlugin();
			InitPlugin.Show();
			this.Solvers = InitPlugin.GetPlugins();			
			InitPlugin.Dispose();

			InitializeComponent();
			InitializeTree();
			InitializeRequirements();
			InitializeCboSolvers();
		}

		/// <summary>
		/// Die verwendeten Ressourcen bereinigen.
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

		#region Vom Windows Form-Designer generierter Code
		/// <summary>
		/// Erforderliche Methode für die Designerunterstützung. 
		/// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
		/// </summary>
		private void InitializeComponent()
		{
			this.trvMain = new System.Windows.Forms.TreeView();
			this.splitter1 = new System.Windows.Forms.Splitter();
			this.pnlShift = new System.Windows.Forms.Panel();
			this.btnOK = new System.Windows.Forms.Button();
			this.lblShiftHeader = new System.Windows.Forms.Label();
			this.grpOptions = new System.Windows.Forms.GroupBox();
			this.nudShiftPreference = new System.Windows.Forms.NumericUpDown();
			this.txtShiftMaxPersons = new System.Windows.Forms.TextBox();
			this.lblShiftPreference = new System.Windows.Forms.Label();
			this.lblShiftMaxPersons = new System.Windows.Forms.Label();
			this.grpShiftBreak = new System.Windows.Forms.GroupBox();
			this.dtpBreakUntil = new System.Windows.Forms.DateTimePicker();
			this.dtpBreakFrom = new System.Windows.Forms.DateTimePicker();
			this.lblBreakUntil = new System.Windows.Forms.Label();
			this.lblBreakFrom = new System.Windows.Forms.Label();
			this.grpShiftTime = new System.Windows.Forms.GroupBox();
			this.dtpShiftUntil = new System.Windows.Forms.DateTimePicker();
			this.lblShiftUntil = new System.Windows.Forms.Label();
			this.lblShiftFrom = new System.Windows.Forms.Label();
			this.dtpShiftFrom = new System.Windows.Forms.DateTimePicker();
			this.mnuMain = new System.Windows.Forms.MainMenu();
			this.mnuFile = new System.Windows.Forms.MenuItem();
			this.mnuFileNew = new System.Windows.Forms.MenuItem();
			this.menuItem10 = new System.Windows.Forms.MenuItem();
			this.mnuSave = new System.Windows.Forms.MenuItem();
			this.mnuSaveAs = new System.Windows.Forms.MenuItem();
			this.menuItem5 = new System.Windows.Forms.MenuItem();
			this.mnuOpen = new System.Windows.Forms.MenuItem();
			this.menuItem7 = new System.Windows.Forms.MenuItem();
			this.mnuEnd = new System.Windows.Forms.MenuItem();
			this.mnuHelp = new System.Windows.Forms.MenuItem();
			this.mnuHelpInfo = new System.Windows.Forms.MenuItem();
			this.pnlRequirement = new System.Windows.Forms.Panel();
			this.button1 = new System.Windows.Forms.Button();
			this.groupBox1 = new System.Windows.Forms.GroupBox();
			this.lblRequirementPersons = new System.Windows.Forms.Label();
			this.txtRequirementPersons = new System.Windows.Forms.TextBox();
			this.grpRequirementInterval = new System.Windows.Forms.GroupBox();
			this.lblRequirementUntilValue = new System.Windows.Forms.Label();
			this.lblRequirementFromValue = new System.Windows.Forms.Label();
			this.lblRequirementUntil = new System.Windows.Forms.Label();
			this.lblRequirementFrom = new System.Windows.Forms.Label();
			this.lblRequirementHeader = new System.Windows.Forms.Label();
			this.pnlAllRequirements = new System.Windows.Forms.Panel();
			this.lblAllRequirementsHeader = new System.Windows.Forms.Label();
			this.graphControl1 = new UserControls.GraphControl();
			this.lsvAllRequirements = new System.Windows.Forms.ListView();
			this.colFrom = new System.Windows.Forms.ColumnHeader();
			this.colUntil = new System.Windows.Forms.ColumnHeader();
			this.colRequiredPersons = new System.Windows.Forms.ColumnHeader();
			this.pnlAllShifts = new System.Windows.Forms.Panel();
			this.lsvAllShifts = new System.Windows.Forms.ListView();
			this.colShiftStart = new System.Windows.Forms.ColumnHeader();
			this.colShiftEnd = new System.Windows.Forms.ColumnHeader();
			this.colBreakStart = new System.Windows.Forms.ColumnHeader();
			this.colBreakEnd = new System.Windows.Forms.ColumnHeader();
			this.colPreference = new System.Windows.Forms.ColumnHeader();
			this.colMaxPersons = new System.Windows.Forms.ColumnHeader();
			this.lblAllShiftsHeader = new System.Windows.Forms.Label();
			this.pnlWelcome = new System.Windows.Forms.Panel();
			this.lblWelcome = new System.Windows.Forms.Label();
			this.lblWelcomeHeader = new System.Windows.Forms.Label();
			this.cmAllShiftsRequirements = new System.Windows.Forms.ContextMenu();
			this.cmAllShiftsRequirementsAdd = new System.Windows.Forms.MenuItem();
			this.cmShiftRequirement = new System.Windows.Forms.ContextMenu();
			this.cmShiftRequirementEdit = new System.Windows.Forms.MenuItem();
			this.cmShiftRequirementDelete = new System.Windows.Forms.MenuItem();
			this.ofdMain = new System.Windows.Forms.OpenFileDialog();
			this.sfdMain = new System.Windows.Forms.SaveFileDialog();
			this.pnlSolution = new System.Windows.Forms.Panel();
			this.graphControl2 = new UserControls.GraphControl();
			this.lsvSolution = new System.Windows.Forms.ListView();
			this.Start = new System.Windows.Forms.ColumnHeader();
			this.End = new System.Windows.Forms.ColumnHeader();
			this.Activity = new System.Windows.Forms.ColumnHeader();
			this.BreakStart = new System.Windows.Forms.ColumnHeader();
			this.BreakEnd = new System.Windows.Forms.ColumnHeader();
			this.BreakActivity = new System.Windows.Forms.ColumnHeader();
			this.btnSolve = new System.Windows.Forms.Button();
			this.cboSolver = new System.Windows.Forms.ComboBox();
			this.label1 = new System.Windows.Forms.Label();
			this.lblSolutionHeader = new System.Windows.Forms.Label();
			this.pnlShift.SuspendLayout();
			this.grpOptions.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.nudShiftPreference)).BeginInit();
			this.grpShiftBreak.SuspendLayout();
			this.grpShiftTime.SuspendLayout();
			this.pnlRequirement.SuspendLayout();
			this.groupBox1.SuspendLayout();
			this.grpRequirementInterval.SuspendLayout();
			this.pnlAllRequirements.SuspendLayout();
			this.pnlAllShifts.SuspendLayout();
			this.pnlWelcome.SuspendLayout();
			this.pnlSolution.SuspendLayout();
			this.SuspendLayout();
			// 
			// trvMain
			// 
			this.trvMain.Dock = System.Windows.Forms.DockStyle.Left;
			this.trvMain.ImageIndex = -1;
			this.trvMain.Location = new System.Drawing.Point(0, 0);
			this.trvMain.Name = "trvMain";
			this.trvMain.SelectedImageIndex = -1;
			this.trvMain.Size = new System.Drawing.Size(144, 617);
			this.trvMain.TabIndex = 0;
			this.trvMain.MouseDown += new System.Windows.Forms.MouseEventHandler(this.trvMain_MouseDown);
			this.trvMain.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.trvMain_AfterSelect);
			// 
			// splitter1
			// 
			this.splitter1.Location = new System.Drawing.Point(144, 0);
			this.splitter1.Name = "splitter1";
			this.splitter1.Size = new System.Drawing.Size(3, 617);
			this.splitter1.TabIndex = 1;
			this.splitter1.TabStop = false;
			// 
			// pnlShift
			// 
			this.pnlShift.BackColor = System.Drawing.SystemColors.Control;
			this.pnlShift.Controls.Add(this.btnOK);
			this.pnlShift.Controls.Add(this.lblShiftHeader);
			this.pnlShift.Controls.Add(this.grpOptions);
			this.pnlShift.Controls.Add(this.grpShiftBreak);
			this.pnlShift.Controls.Add(this.grpShiftTime);
			this.pnlShift.Location = new System.Drawing.Point(152, 8);
			this.pnlShift.Name = "pnlShift";
			this.pnlShift.Size = new System.Drawing.Size(264, 48);
			this.pnlShift.TabIndex = 2;
			this.pnlShift.Visible = false;
			// 
			// btnOK
			// 
			this.btnOK.Location = new System.Drawing.Point(120, 304);
			this.btnOK.Name = "btnOK";
			this.btnOK.Size = new System.Drawing.Size(112, 32);
			this.btnOK.TabIndex = 11;
			this.btnOK.Text = "OK";
			this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
			// 
			// lblShiftHeader
			// 
			this.lblShiftHeader.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.lblShiftHeader.BackColor = System.Drawing.Color.Moccasin;
			this.lblShiftHeader.Font = new System.Drawing.Font("Verdana", 14.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
			this.lblShiftHeader.Location = new System.Drawing.Point(-85, 8);
			this.lblShiftHeader.Name = "lblShiftHeader";
			this.lblShiftHeader.Size = new System.Drawing.Size(344, 32);
			this.lblShiftHeader.TabIndex = 0;
			this.lblShiftHeader.Text = "Schichtinformation";
			this.lblShiftHeader.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// grpOptions
			// 
			this.grpOptions.Controls.Add(this.nudShiftPreference);
			this.grpOptions.Controls.Add(this.txtShiftMaxPersons);
			this.grpOptions.Controls.Add(this.lblShiftPreference);
			this.grpOptions.Controls.Add(this.lblShiftMaxPersons);
			this.grpOptions.Location = new System.Drawing.Point(8, 216);
			this.grpOptions.Name = "grpOptions";
			this.grpOptions.Size = new System.Drawing.Size(224, 72);
			this.grpOptions.TabIndex = 10;
			this.grpOptions.TabStop = false;
			this.grpOptions.Text = "Optionen";
			// 
			// nudShiftPreference
			// 
			this.nudShiftPreference.Location = new System.Drawing.Point(136, 18);
			this.nudShiftPreference.Maximum = new System.Decimal(new int[] {
																			   999,
																			   0,
																			   0,
																			   0});
			this.nudShiftPreference.Name = "nudShiftPreference";
			this.nudShiftPreference.Size = new System.Drawing.Size(80, 20);
			this.nudShiftPreference.TabIndex = 14;
			// 
			// txtShiftMaxPersons
			// 
			this.txtShiftMaxPersons.Location = new System.Drawing.Point(136, 44);
			this.txtShiftMaxPersons.Name = "txtShiftMaxPersons";
			this.txtShiftMaxPersons.Size = new System.Drawing.Size(80, 20);
			this.txtShiftMaxPersons.TabIndex = 13;
			this.txtShiftMaxPersons.Text = "";
			// 
			// lblShiftPreference
			// 
			this.lblShiftPreference.Location = new System.Drawing.Point(8, 24);
			this.lblShiftPreference.Name = "lblShiftPreference";
			this.lblShiftPreference.Size = new System.Drawing.Size(112, 16);
			this.lblShiftPreference.TabIndex = 3;
			this.lblShiftPreference.Text = "Präferierung [%]:";
			// 
			// lblShiftMaxPersons
			// 
			this.lblShiftMaxPersons.Location = new System.Drawing.Point(8, 48);
			this.lblShiftMaxPersons.Name = "lblShiftMaxPersons";
			this.lblShiftMaxPersons.Size = new System.Drawing.Size(112, 16);
			this.lblShiftMaxPersons.TabIndex = 7;
			this.lblShiftMaxPersons.Text = "Max. Personen:";
			// 
			// grpShiftBreak
			// 
			this.grpShiftBreak.Controls.Add(this.dtpBreakUntil);
			this.grpShiftBreak.Controls.Add(this.dtpBreakFrom);
			this.grpShiftBreak.Controls.Add(this.lblBreakUntil);
			this.grpShiftBreak.Controls.Add(this.lblBreakFrom);
			this.grpShiftBreak.Location = new System.Drawing.Point(8, 136);
			this.grpShiftBreak.Name = "grpShiftBreak";
			this.grpShiftBreak.Size = new System.Drawing.Size(224, 72);
			this.grpShiftBreak.TabIndex = 9;
			this.grpShiftBreak.TabStop = false;
			this.grpShiftBreak.Text = "Pausenbereich";
			// 
			// dtpBreakUntil
			// 
			this.dtpBreakUntil.CustomFormat = "HH:mm";
			this.dtpBreakUntil.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
			this.dtpBreakUntil.Location = new System.Drawing.Point(136, 46);
			this.dtpBreakUntil.Name = "dtpBreakUntil";
			this.dtpBreakUntil.ShowUpDown = true;
			this.dtpBreakUntil.Size = new System.Drawing.Size(80, 20);
			this.dtpBreakUntil.TabIndex = 16;
			this.dtpBreakUntil.Value = new System.DateTime(2004, 5, 11, 0, 0, 0, 0);
			this.dtpBreakUntil.Validating += new System.ComponentModel.CancelEventHandler(this.dtpBreakUntil_Validating);
			// 
			// dtpBreakFrom
			// 
			this.dtpBreakFrom.CustomFormat = "HH:mm";
			this.dtpBreakFrom.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
			this.dtpBreakFrom.Location = new System.Drawing.Point(136, 19);
			this.dtpBreakFrom.Name = "dtpBreakFrom";
			this.dtpBreakFrom.ShowUpDown = true;
			this.dtpBreakFrom.Size = new System.Drawing.Size(80, 20);
			this.dtpBreakFrom.TabIndex = 15;
			this.dtpBreakFrom.Value = new System.DateTime(2004, 5, 11, 0, 0, 0, 0);
			this.dtpBreakFrom.Validating += new System.ComponentModel.CancelEventHandler(this.dtpBreakFrom_Validating);
			// 
			// lblBreakUntil
			// 
			this.lblBreakUntil.Location = new System.Drawing.Point(8, 48);
			this.lblBreakUntil.Name = "lblBreakUntil";
			this.lblBreakUntil.Size = new System.Drawing.Size(112, 16);
			this.lblBreakUntil.TabIndex = 4;
			this.lblBreakUntil.Text = "Pausenbereich bis:";
			// 
			// lblBreakFrom
			// 
			this.lblBreakFrom.Location = new System.Drawing.Point(8, 24);
			this.lblBreakFrom.Name = "lblBreakFrom";
			this.lblBreakFrom.Size = new System.Drawing.Size(112, 16);
			this.lblBreakFrom.TabIndex = 5;
			this.lblBreakFrom.Text = "Pausenbereich von:";
			// 
			// grpShiftTime
			// 
			this.grpShiftTime.Controls.Add(this.dtpShiftUntil);
			this.grpShiftTime.Controls.Add(this.lblShiftUntil);
			this.grpShiftTime.Controls.Add(this.lblShiftFrom);
			this.grpShiftTime.Controls.Add(this.dtpShiftFrom);
			this.grpShiftTime.Location = new System.Drawing.Point(8, 56);
			this.grpShiftTime.Name = "grpShiftTime";
			this.grpShiftTime.Size = new System.Drawing.Size(224, 72);
			this.grpShiftTime.TabIndex = 8;
			this.grpShiftTime.TabStop = false;
			this.grpShiftTime.Text = "Arbeitszeit";
			// 
			// dtpShiftUntil
			// 
			this.dtpShiftUntil.CustomFormat = "HH:mm";
			this.dtpShiftUntil.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
			this.dtpShiftUntil.Location = new System.Drawing.Point(136, 44);
			this.dtpShiftUntil.Name = "dtpShiftUntil";
			this.dtpShiftUntil.ShowUpDown = true;
			this.dtpShiftUntil.Size = new System.Drawing.Size(80, 20);
			this.dtpShiftUntil.TabIndex = 15;
			this.dtpShiftUntil.Value = new System.DateTime(2004, 5, 11, 0, 0, 0, 0);
			this.dtpShiftUntil.Validating += new System.ComponentModel.CancelEventHandler(this.dtpShiftUntil_Validating);
			// 
			// lblShiftUntil
			// 
			this.lblShiftUntil.Location = new System.Drawing.Point(8, 48);
			this.lblShiftUntil.Name = "lblShiftUntil";
			this.lblShiftUntil.Size = new System.Drawing.Size(112, 16);
			this.lblShiftUntil.TabIndex = 6;
			this.lblShiftUntil.Text = "bis:";
			// 
			// lblShiftFrom
			// 
			this.lblShiftFrom.Location = new System.Drawing.Point(8, 24);
			this.lblShiftFrom.Name = "lblShiftFrom";
			this.lblShiftFrom.Size = new System.Drawing.Size(112, 16);
			this.lblShiftFrom.TabIndex = 2;
			this.lblShiftFrom.Text = "von:";
			// 
			// dtpShiftFrom
			// 
			this.dtpShiftFrom.CustomFormat = "HH:mm";
			this.dtpShiftFrom.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
			this.dtpShiftFrom.Location = new System.Drawing.Point(136, 18);
			this.dtpShiftFrom.Name = "dtpShiftFrom";
			this.dtpShiftFrom.ShowUpDown = true;
			this.dtpShiftFrom.Size = new System.Drawing.Size(80, 20);
			this.dtpShiftFrom.TabIndex = 14;
			this.dtpShiftFrom.Value = new System.DateTime(2004, 5, 11, 0, 0, 0, 0);
			this.dtpShiftFrom.Validating += new System.ComponentModel.CancelEventHandler(this.dtpShiftFrom_Validating);
			// 
			// mnuMain
			// 
			this.mnuMain.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					this.mnuFile,
																					this.mnuHelp});
			// 
			// mnuFile
			// 
			this.mnuFile.Index = 0;
			this.mnuFile.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					this.mnuFileNew,
																					this.menuItem10,
																					this.mnuSave,
																					this.mnuSaveAs,
																					this.menuItem5,
																					this.mnuOpen,
																					this.menuItem7,
																					this.mnuEnd});
			this.mnuFile.Text = "&Datei";
			// 
			// mnuFileNew
			// 
			this.mnuFileNew.Index = 0;
			this.mnuFileNew.Text = "&Neu";
			this.mnuFileNew.Click += new System.EventHandler(this.mnuFileNew_Click);
			// 
			// menuItem10
			// 
			this.menuItem10.Index = 1;
			this.menuItem10.Text = "-";
			// 
			// mnuSave
			// 
			this.mnuSave.Index = 2;
			this.mnuSave.Text = "&Speichern";
			this.mnuSave.Click += new System.EventHandler(this.mnuSave_Click);
			// 
			// mnuSaveAs
			// 
			this.mnuSaveAs.Index = 3;
			this.mnuSaveAs.Text = "&Speichern als...";
			this.mnuSaveAs.Click += new System.EventHandler(this.mnuSaveAs_Click);
			// 
			// menuItem5
			// 
			this.menuItem5.Index = 4;
			this.menuItem5.Text = "-";
			// 
			// mnuOpen
			// 
			this.mnuOpen.Index = 5;
			this.mnuOpen.Text = "Ö&ffnen";
			this.mnuOpen.Click += new System.EventHandler(this.mnuOpen_Click);
			// 
			// menuItem7
			// 
			this.menuItem7.Index = 6;
			this.menuItem7.Text = "-";
			// 
			// mnuEnd
			// 
			this.mnuEnd.Index = 7;
			this.mnuEnd.Text = "&Beenden";
			this.mnuEnd.Click += new System.EventHandler(this.mnuEnd_Click);
			// 
			// mnuHelp
			// 
			this.mnuHelp.Index = 1;
			this.mnuHelp.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					this.mnuHelpInfo});
			this.mnuHelp.Text = "&Hilfe";
			// 
			// mnuHelpInfo
			// 
			this.mnuHelpInfo.Index = 0;
			this.mnuHelpInfo.Text = "&Info";
			this.mnuHelpInfo.Click += new System.EventHandler(this.mnuHelpInfo_Click);
			// 
			// pnlRequirement
			// 
			this.pnlRequirement.BackColor = System.Drawing.SystemColors.Control;
			this.pnlRequirement.Controls.Add(this.button1);
			this.pnlRequirement.Controls.Add(this.groupBox1);
			this.pnlRequirement.Controls.Add(this.grpRequirementInterval);
			this.pnlRequirement.Controls.Add(this.lblRequirementHeader);
			this.pnlRequirement.Location = new System.Drawing.Point(152, 64);
			this.pnlRequirement.Name = "pnlRequirement";
			this.pnlRequirement.Size = new System.Drawing.Size(264, 48);
			this.pnlRequirement.TabIndex = 3;
			this.pnlRequirement.Visible = false;
			// 
			// button1
			// 
			this.button1.Location = new System.Drawing.Point(120, 304);
			this.button1.Name = "button1";
			this.button1.Size = new System.Drawing.Size(112, 32);
			this.button1.TabIndex = 12;
			this.button1.Text = "OK";
			this.button1.Click += new System.EventHandler(this.button1_Click);
			// 
			// groupBox1
			// 
			this.groupBox1.Controls.Add(this.lblRequirementPersons);
			this.groupBox1.Controls.Add(this.txtRequirementPersons);
			this.groupBox1.Location = new System.Drawing.Point(8, 136);
			this.groupBox1.Name = "groupBox1";
			this.groupBox1.Size = new System.Drawing.Size(224, 56);
			this.groupBox1.TabIndex = 10;
			this.groupBox1.TabStop = false;
			this.groupBox1.Text = "Optionen";
			// 
			// lblRequirementPersons
			// 
			this.lblRequirementPersons.Location = new System.Drawing.Point(8, 24);
			this.lblRequirementPersons.Name = "lblRequirementPersons";
			this.lblRequirementPersons.Size = new System.Drawing.Size(112, 16);
			this.lblRequirementPersons.TabIndex = 3;
			this.lblRequirementPersons.Text = "Anzahl Personen:";
			// 
			// txtRequirementPersons
			// 
			this.txtRequirementPersons.Location = new System.Drawing.Point(136, 22);
			this.txtRequirementPersons.Name = "txtRequirementPersons";
			this.txtRequirementPersons.Size = new System.Drawing.Size(80, 20);
			this.txtRequirementPersons.TabIndex = 11;
			this.txtRequirementPersons.Text = "";
			// 
			// grpRequirementInterval
			// 
			this.grpRequirementInterval.Controls.Add(this.lblRequirementUntilValue);
			this.grpRequirementInterval.Controls.Add(this.lblRequirementFromValue);
			this.grpRequirementInterval.Controls.Add(this.lblRequirementUntil);
			this.grpRequirementInterval.Controls.Add(this.lblRequirementFrom);
			this.grpRequirementInterval.Location = new System.Drawing.Point(8, 56);
			this.grpRequirementInterval.Name = "grpRequirementInterval";
			this.grpRequirementInterval.Size = new System.Drawing.Size(224, 72);
			this.grpRequirementInterval.TabIndex = 8;
			this.grpRequirementInterval.TabStop = false;
			this.grpRequirementInterval.Text = "Bedarfszeit";
			// 
			// lblRequirementUntilValue
			// 
			this.lblRequirementUntilValue.Location = new System.Drawing.Point(128, 48);
			this.lblRequirementUntilValue.Name = "lblRequirementUntilValue";
			this.lblRequirementUntilValue.Size = new System.Drawing.Size(88, 16);
			this.lblRequirementUntilValue.TabIndex = 8;
			// 
			// lblRequirementFromValue
			// 
			this.lblRequirementFromValue.Location = new System.Drawing.Point(128, 24);
			this.lblRequirementFromValue.Name = "lblRequirementFromValue";
			this.lblRequirementFromValue.Size = new System.Drawing.Size(88, 16);
			this.lblRequirementFromValue.TabIndex = 7;
			// 
			// lblRequirementUntil
			// 
			this.lblRequirementUntil.Location = new System.Drawing.Point(8, 48);
			this.lblRequirementUntil.Name = "lblRequirementUntil";
			this.lblRequirementUntil.Size = new System.Drawing.Size(112, 16);
			this.lblRequirementUntil.TabIndex = 6;
			this.lblRequirementUntil.Text = "bis:";
			// 
			// lblRequirementFrom
			// 
			this.lblRequirementFrom.Location = new System.Drawing.Point(8, 24);
			this.lblRequirementFrom.Name = "lblRequirementFrom";
			this.lblRequirementFrom.Size = new System.Drawing.Size(112, 16);
			this.lblRequirementFrom.TabIndex = 2;
			this.lblRequirementFrom.Text = "von:";
			// 
			// lblRequirementHeader
			// 
			this.lblRequirementHeader.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.lblRequirementHeader.BackColor = System.Drawing.Color.Moccasin;
			this.lblRequirementHeader.Font = new System.Drawing.Font("Verdana", 14.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
			this.lblRequirementHeader.Location = new System.Drawing.Point(-85, 8);
			this.lblRequirementHeader.Name = "lblRequirementHeader";
			this.lblRequirementHeader.Size = new System.Drawing.Size(344, 32);
			this.lblRequirementHeader.TabIndex = 0;
			this.lblRequirementHeader.Text = "Bedarfsinformation";
			this.lblRequirementHeader.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// pnlAllRequirements
			// 
			this.pnlAllRequirements.BackColor = System.Drawing.SystemColors.Control;
			this.pnlAllRequirements.Controls.Add(this.lblAllRequirementsHeader);
			this.pnlAllRequirements.Controls.Add(this.graphControl1);
			this.pnlAllRequirements.Controls.Add(this.lsvAllRequirements);
			this.pnlAllRequirements.Location = new System.Drawing.Point(152, 120);
			this.pnlAllRequirements.Name = "pnlAllRequirements";
			this.pnlAllRequirements.Size = new System.Drawing.Size(352, 432);
			this.pnlAllRequirements.TabIndex = 11;
			this.pnlAllRequirements.Visible = false;
			// 
			// lblAllRequirementsHeader
			// 
			this.lblAllRequirementsHeader.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.lblAllRequirementsHeader.BackColor = System.Drawing.Color.Moccasin;
			this.lblAllRequirementsHeader.Font = new System.Drawing.Font("Verdana", 14.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
			this.lblAllRequirementsHeader.Location = new System.Drawing.Point(3, 8);
			this.lblAllRequirementsHeader.Name = "lblAllRequirementsHeader";
			this.lblAllRequirementsHeader.Size = new System.Drawing.Size(344, 32);
			this.lblAllRequirementsHeader.TabIndex = 0;
			this.lblAllRequirementsHeader.Text = "Alle Bedarfspläne";
			this.lblAllRequirementsHeader.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// graphControl1
			// 
			this.graphControl1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.graphControl1.AxesColor = System.Drawing.Color.Red;
			this.graphControl1.BackColor = System.Drawing.Color.AliceBlue;
			this.graphControl1.GraphColor = System.Drawing.Color.Green;
			this.graphControl1.GraphStyle = UserControls.GraphType.Bar;
			this.graphControl1.Location = new System.Drawing.Point(8, 232);
			this.graphControl1.Name = "graphControl1";
			this.graphControl1.ShowPointsOnGraph = false;
			this.graphControl1.Size = new System.Drawing.Size(336, 192);
			this.graphControl1.TabIndex = 15;
			this.graphControl1.TextColor = System.Drawing.Color.Blue;
			this.graphControl1.TitleXAxis = "Bedarfszeit";
			this.graphControl1.TitleYAxis = "Bedarf";
			this.graphControl1.Resize += new System.EventHandler(this.graphControl1_Resize);
			// 
			// lsvAllRequirements
			// 
			this.lsvAllRequirements.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.lsvAllRequirements.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																								 this.colFrom,
																								 this.colUntil,
																								 this.colRequiredPersons});
			this.lsvAllRequirements.Location = new System.Drawing.Point(4, 56);
			this.lsvAllRequirements.Name = "lsvAllRequirements";
			this.lsvAllRequirements.Size = new System.Drawing.Size(343, 168);
			this.lsvAllRequirements.TabIndex = 1;
			this.lsvAllRequirements.View = System.Windows.Forms.View.Details;
			// 
			// colFrom
			// 
			this.colFrom.Text = "Von";
			// 
			// colUntil
			// 
			this.colUntil.Text = "Bis";
			// 
			// colRequiredPersons
			// 
			this.colRequiredPersons.Text = "Personalbedarf";
			// 
			// pnlAllShifts
			// 
			this.pnlAllShifts.BackColor = System.Drawing.SystemColors.Control;
			this.pnlAllShifts.Controls.Add(this.lsvAllShifts);
			this.pnlAllShifts.Controls.Add(this.lblAllShiftsHeader);
			this.pnlAllShifts.Location = new System.Drawing.Point(152, 176);
			this.pnlAllShifts.Name = "pnlAllShifts";
			this.pnlAllShifts.Size = new System.Drawing.Size(264, 48);
			this.pnlAllShifts.TabIndex = 12;
			this.pnlAllShifts.Visible = false;
			// 
			// lsvAllShifts
			// 
			this.lsvAllShifts.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.lsvAllShifts.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																						   this.colShiftStart,
																						   this.colShiftEnd,
																						   this.colBreakStart,
																						   this.colBreakEnd,
																						   this.colPreference,
																						   this.colMaxPersons});
			this.lsvAllShifts.Location = new System.Drawing.Point(4, 56);
			this.lsvAllShifts.Name = "lsvAllShifts";
			this.lsvAllShifts.Size = new System.Drawing.Size(255, 0);
			this.lsvAllShifts.TabIndex = 1;
			this.lsvAllShifts.View = System.Windows.Forms.View.Details;
			// 
			// colShiftStart
			// 
			this.colShiftStart.Text = "Von";
			// 
			// colShiftEnd
			// 
			this.colShiftEnd.Text = "Bis";
			// 
			// colBreakStart
			// 
			this.colBreakStart.Text = "Pausenbereich von";
			// 
			// colBreakEnd
			// 
			this.colBreakEnd.Text = "Pausenbereich bis";
			// 
			// colPreference
			// 
			this.colPreference.Text = "Präferierung";
			// 
			// colMaxPersons
			// 
			this.colMaxPersons.Text = "Max. Personal";
			// 
			// lblAllShiftsHeader
			// 
			this.lblAllShiftsHeader.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.lblAllShiftsHeader.BackColor = System.Drawing.Color.Moccasin;
			this.lblAllShiftsHeader.Font = new System.Drawing.Font("Verdana", 14.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
			this.lblAllShiftsHeader.Location = new System.Drawing.Point(-85, 8);
			this.lblAllShiftsHeader.Name = "lblAllShiftsHeader";
			this.lblAllShiftsHeader.Size = new System.Drawing.Size(344, 32);
			this.lblAllShiftsHeader.TabIndex = 0;
			this.lblAllShiftsHeader.Text = "Alle Schichtpläne";
			this.lblAllShiftsHeader.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// pnlWelcome
			// 
			this.pnlWelcome.BackColor = System.Drawing.SystemColors.Control;
			this.pnlWelcome.Controls.Add(this.lblWelcome);
			this.pnlWelcome.Controls.Add(this.lblWelcomeHeader);
			this.pnlWelcome.Location = new System.Drawing.Point(152, 232);
			this.pnlWelcome.Name = "pnlWelcome";
			this.pnlWelcome.Size = new System.Drawing.Size(264, 48);
			this.pnlWelcome.TabIndex = 13;
			// 
			// lblWelcome
			// 
			this.lblWelcome.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.lblWelcome.Location = new System.Drawing.Point(24, 64);
			this.lblWelcome.Name = "lblWelcome";
			this.lblWelcome.Size = new System.Drawing.Size(224, 0);
			this.lblWelcome.TabIndex = 1;
			this.lblWelcome.Text = "Willkommen bei PersPlan.\n\nWählen Sie im Baum links \"Schichten\", um Schichtzeiten " +
				"einzugeben und \"Bedarf\", um Personalbedarf zu planen. Lösen Sie das Problem nach" +
				" Eingabe aller Daten mittels \"Lösen\".";
			// 
			// lblWelcomeHeader
			// 
			this.lblWelcomeHeader.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.lblWelcomeHeader.BackColor = System.Drawing.Color.Moccasin;
			this.lblWelcomeHeader.Font = new System.Drawing.Font("Verdana", 14.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
			this.lblWelcomeHeader.Location = new System.Drawing.Point(-85, 8);
			this.lblWelcomeHeader.Name = "lblWelcomeHeader";
			this.lblWelcomeHeader.Size = new System.Drawing.Size(344, 32);
			this.lblWelcomeHeader.TabIndex = 0;
			this.lblWelcomeHeader.Text = "Willkommen!";
			this.lblWelcomeHeader.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// cmAllShiftsRequirements
			// 
			this.cmAllShiftsRequirements.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																									this.cmAllShiftsRequirementsAdd});
			// 
			// cmAllShiftsRequirementsAdd
			// 
			this.cmAllShiftsRequirementsAdd.Index = 0;
			this.cmAllShiftsRequirementsAdd.Text = "&Hinzufügen";
			this.cmAllShiftsRequirementsAdd.Click += new System.EventHandler(this.menuItem1_Click);
			// 
			// cmShiftRequirement
			// 
			this.cmShiftRequirement.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																							   this.cmShiftRequirementEdit,
																							   this.cmShiftRequirementDelete});
			// 
			// cmShiftRequirementEdit
			// 
			this.cmShiftRequirementEdit.Index = 0;
			this.cmShiftRequirementEdit.Text = "&Bearbeiten";
			// 
			// cmShiftRequirementDelete
			// 
			this.cmShiftRequirementDelete.Index = 1;
			this.cmShiftRequirementDelete.Text = "&Löschen";
			// 
			// ofdMain
			// 
			this.ofdMain.DefaultExt = "xml";
			this.ofdMain.Filter = "XML-Dateien|*.xml|Alle Dateien|*.*";
			this.ofdMain.Title = "Datei öffnen";
			// 
			// sfdMain
			// 
			this.sfdMain.DefaultExt = "xml";
			this.sfdMain.Filter = "XML-Dateien|*.xml|Alle Dateien|*.*";
			this.sfdMain.Title = "Speichern als...";
			// 
			// pnlSolution
			// 
			this.pnlSolution.BackColor = System.Drawing.SystemColors.Control;
			this.pnlSolution.Controls.Add(this.graphControl2);
			this.pnlSolution.Controls.Add(this.lsvSolution);
			this.pnlSolution.Controls.Add(this.btnSolve);
			this.pnlSolution.Controls.Add(this.cboSolver);
			this.pnlSolution.Controls.Add(this.label1);
			this.pnlSolution.Controls.Add(this.lblSolutionHeader);
			this.pnlSolution.Location = new System.Drawing.Point(168, 8);
			this.pnlSolution.Name = "pnlSolution";
			this.pnlSolution.Size = new System.Drawing.Size(544, 512);
			this.pnlSolution.TabIndex = 14;
			// 
			// graphControl2
			// 
			this.graphControl2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.graphControl2.AxesColor = System.Drawing.Color.Red;
			this.graphControl2.BackColor = System.Drawing.Color.AliceBlue;
			this.graphControl2.GraphColor = System.Drawing.Color.Green;
			this.graphControl2.GraphStyle = UserControls.GraphType.Line;
			this.graphControl2.Location = new System.Drawing.Point(16, 344);
			this.graphControl2.Name = "graphControl2";
			this.graphControl2.ShowPointsOnGraph = false;
			this.graphControl2.Size = new System.Drawing.Size(520, 160);
			this.graphControl2.TabIndex = 5;
			this.graphControl2.TextColor = System.Drawing.Color.Blue;
			this.graphControl2.TitleXAxis = "None";
			this.graphControl2.TitleYAxis = "None";
			this.graphControl2.Visible = false;
			this.graphControl2.Resize += new System.EventHandler(this.graphControl2_Resize);
			// 
			// lsvSolution
			// 
			this.lsvSolution.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.lsvSolution.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																						  this.Start,
																						  this.End,
																						  this.Activity,
																						  this.BreakStart,
																						  this.BreakEnd,
																						  this.BreakActivity});
			this.lsvSolution.Location = new System.Drawing.Point(16, 152);
			this.lsvSolution.Name = "lsvSolution";
			this.lsvSolution.Size = new System.Drawing.Size(520, 184);
			this.lsvSolution.TabIndex = 4;
			this.lsvSolution.View = System.Windows.Forms.View.Details;
			this.lsvSolution.Visible = false;
			// 
			// Start
			// 
			this.Start.Text = "Schicht von";
			this.Start.Width = 82;
			// 
			// End
			// 
			this.End.Text = "Schicht Bis";
			this.End.Width = 86;
			// 
			// Activity
			// 
			this.Activity.Text = "Personen";
			this.Activity.Width = 82;
			// 
			// BreakStart
			// 
			this.BreakStart.Text = "Pause Von";
			this.BreakStart.Width = 77;
			// 
			// BreakEnd
			// 
			this.BreakEnd.Text = "Pause Bis";
			this.BreakEnd.Width = 72;
			// 
			// BreakActivity
			// 
			this.BreakActivity.Text = "Personen in Pause";
			this.BreakActivity.Width = 116;
			// 
			// btnSolve
			// 
			this.btnSolve.Location = new System.Drawing.Point(160, 96);
			this.btnSolve.Name = "btnSolve";
			this.btnSolve.Size = new System.Drawing.Size(88, 32);
			this.btnSolve.TabIndex = 3;
			this.btnSolve.Text = "Lösen";
			this.btnSolve.Click += new System.EventHandler(this.btnSolve_Click);
			// 
			// cboSolver
			// 
			this.cboSolver.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.cboSolver.Location = new System.Drawing.Point(96, 56);
			this.cboSolver.Name = "cboSolver";
			this.cboSolver.Size = new System.Drawing.Size(440, 21);
			this.cboSolver.TabIndex = 2;
			// 
			// label1
			// 
			this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
				| System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.label1.Location = new System.Drawing.Point(16, 60);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(360, 464);
			this.label1.TabIndex = 1;
			this.label1.Text = "Solver wählen:";
			// 
			// lblSolutionHeader
			// 
			this.lblSolutionHeader.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.lblSolutionHeader.BackColor = System.Drawing.Color.Moccasin;
			this.lblSolutionHeader.Font = new System.Drawing.Font("Verdana", 14.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
			this.lblSolutionHeader.Location = new System.Drawing.Point(195, 8);
			this.lblSolutionHeader.Name = "lblSolutionHeader";
			this.lblSolutionHeader.Size = new System.Drawing.Size(344, 32);
			this.lblSolutionHeader.TabIndex = 0;
			this.lblSolutionHeader.Text = "Lösung";
			this.lblSolutionHeader.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
			// 
			// Form1
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(728, 617);
			this.Controls.Add(this.pnlShift);
			this.Controls.Add(this.pnlRequirement);
			this.Controls.Add(this.pnlWelcome);
			this.Controls.Add(this.pnlAllShifts);
			this.Controls.Add(this.pnlSolution);
			this.Controls.Add(this.pnlAllRequirements);
			this.Controls.Add(this.splitter1);
			this.Controls.Add(this.trvMain);
			this.Menu = this.mnuMain;
			this.MinimumSize = new System.Drawing.Size(400, 200);
			this.Name = "Form1";
			this.Text = "Persplan.exe";
			this.Load += new System.EventHandler(this.Form1_Load);
			this.pnlShift.ResumeLayout(false);
			this.grpOptions.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.nudShiftPreference)).EndInit();
			this.grpShiftBreak.ResumeLayout(false);
			this.grpShiftTime.ResumeLayout(false);
			this.pnlRequirement.ResumeLayout(false);
			this.groupBox1.ResumeLayout(false);
			this.grpRequirementInterval.ResumeLayout(false);
			this.pnlAllRequirements.ResumeLayout(false);
			this.pnlAllShifts.ResumeLayout(false);
			this.pnlWelcome.ResumeLayout(false);
			this.pnlSolution.ResumeLayout(false);
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// Der Haupteinstiegspunkt für die Anwendung.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new Form1());
		}

		private void Form1_Load(object sender, System.EventArgs e)
		{
			// set the panels to DockStyle.Fill
			this.pnlRequirement.Dock = DockStyle.Fill;
			this.pnlShift.Dock = DockStyle.Fill;
			this.pnlAllRequirements.Dock = DockStyle.Fill;
			this.pnlAllShifts.Dock = DockStyle.Fill;
			this.pnlWelcome.Dock = DockStyle.Fill;
			this.pnlSolution.Dock = DockStyle.Fill;
			
			// build up tree
			this.trvMain.Nodes.Add(RootNode);
			this.RootNode.Expand();
		}

		#region TreeView events
		private void trvMain_AfterSelect(object sender, System.Windows.Forms.TreeViewEventArgs e)
		{
			switch(((ExtendedTreeNode)e.Node).ID)
			{
				case TreeNodeID.TreeNodeIDShiftEntry:
					this.pnlRequirement.Visible = false;
					this.pnlAllRequirements.Visible = false;
					this.pnlAllShifts.Visible = false;
					this.pnlWelcome.Visible = false;
					this.pnlShift.Visible = true;						
					this.pnlSolution.Visible = false;
					this.SetShiftFields();					
					break;
				case TreeNodeID.TreeNodeIDRequirementsEntry:
					this.pnlShift.Visible = false;
					this.pnlAllShifts.Visible = false;
					this.pnlAllRequirements.Visible = false;
					this.pnlWelcome.Visible = false;
					this.pnlRequirement.Visible = true;						
					this.pnlSolution.Visible = false;
					this.SetRequirementFields();					
					break;
				case TreeNodeID.TreeNodeIDRequirements:
					this.pnlShift.Visible = false;
					this.pnlRequirement.Visible = false;
					this.pnlAllShifts.Visible = false;
					this.pnlWelcome.Visible = false;
					this.pnlAllRequirements.Visible = true;
					this.pnlSolution.Visible = false;
					this.FillRequirementsList();					
					SetGraphValues();
					break;
				case TreeNodeID.TreeNodeIDShift:
					this.pnlShift.Visible = false;
					this.pnlRequirement.Visible = false;						
					this.pnlAllRequirements.Visible = false;
					this.pnlWelcome.Visible = false;
					this.pnlAllShifts.Visible = true;
					this.pnlSolution.Visible = false;
					this.FillShiftList();					
					break;
				case TreeNodeID.TreeNodeIDRoot:
					this.pnlShift.Visible = false;
					this.pnlRequirement.Visible = false;						
					this.pnlAllRequirements.Visible = false;						
					this.pnlAllShifts.Visible = false;
					this.pnlWelcome.Visible = true;
					this.pnlSolution.Visible = false;
					break;
				case TreeNodeID.TreeNodeIDSolution:
					this.pnlShift.Visible = false;
					this.pnlRequirement.Visible = false;						
					this.pnlAllRequirements.Visible = false;
					this.pnlWelcome.Visible = false;
					this.pnlAllShifts.Visible = false;
					this.pnlSolution.Visible = true;
					this.FillShiftList();					
					break;
			}
		}

		private void trvMain_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
		{
			if (e.Button == MouseButtons.Right)
			{
				// right-click. Check for context menu.
				ExtendedTreeNode ClickedNode = this.trvMain.GetNodeAt(e.X, e.Y) as ExtendedTreeNode;
				if (ClickedNode != null)
				{
					switch (ClickedNode.ID)
					{
						case TreeNodeID.TreeNodeIDShift:
							this.trvMain.SelectedNode = ClickedNode;
							this.cmAllShiftsRequirements.Show(this.trvMain, new System.Drawing.Point(e.X+10, e.Y));
							break;
						case TreeNodeID.TreeNodeIDRequirements:
							this.trvMain.SelectedNode = ClickedNode;
							break;
						case TreeNodeID.TreeNodeIDShiftEntry:
							this.trvMain.SelectedNode = ClickedNode;
							this.cmShiftRequirement.Show(this.trvMain, new System.Drawing.Point(e.X+10, e.Y));
							break;
						case TreeNodeID.TreeNodeIDRequirementsEntry:
							this.trvMain.SelectedNode = ClickedNode;
							break;
					}
				}
			}
		}
		#endregion

		private void menuItem1_Click(object sender, System.EventArgs e)
		{
			//popup: add
			ExtendedTreeNode ClickedNode = this.trvMain.SelectedNode as ExtendedTreeNode;
			if (ClickedNode != null)
			{
				switch (ClickedNode.ID)
				{
					case TreeNodeID.TreeNodeIDShift:
						//add a new shift entry
						ShiftTreeNode ShiftNodeToAdd = new ShiftTreeNode("Neue Schicht", ClickedNode);
						ClickedNode.Nodes.Add(ShiftNodeToAdd);
						this.trvMain.SelectedNode = ShiftNodeToAdd;
						break;
				}
			}

		}

		private void btnOK_Click(object sender, System.EventArgs e)
		{
			// setting the shift details
			ShiftTreeNode NodeToAssign = this.trvMain.SelectedNode as ShiftTreeNode;
			if (NodeToAssign != null)
			{
				Shift NewShift = new Shift();				
				NewShift.Start = this.dtpShiftFrom.Value;
				NewShift.End = this.dtpShiftUntil.Value;
				NewShift.BreakStart = this.dtpBreakFrom.Value;
				NewShift.BreakEnd = this.dtpBreakUntil.Value;
				NewShift.Preference = (int)this.nudShiftPreference.Value;
				try
				{
					NewShift.MaxPersons = Convert.ToInt32(this.txtShiftMaxPersons.Text);
				}
				catch (Exception ex)
				{
					MessageBox.Show("Die Anzahl Personen muß eine Ganzzahl größer oder gleich 0 sein.");
					return;
				}
				try
				{
					NodeToAssign.ShiftDetails = NewShift;
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message);
					return;
				}				
			}
		}

		private void button1_Click(object sender, System.EventArgs e)
		{
			// setting the requirement details
			RequirementTreeNode NodeToAssign = this.trvMain.SelectedNode as RequirementTreeNode;
			if (NodeToAssign != null)
			{
				try
				{
					NodeToAssign.RequirementDetails.RequiredPersons = Convert.ToInt32(this.txtRequirementPersons.Text);
				}
				catch (Exception ex)
				{
					MessageBox.Show("Die Anzahl benötigter Personen muß eine Ganzzahl größer oder gleich 0 sein.");
					return;
				}
			}
		}

		#region Help Routines
		private void SetShiftFields()
		{
			// set the fields to the shift details, if there are any
			ShiftTreeNode SelectedNode = this.trvMain.SelectedNode as ShiftTreeNode;
			if (SelectedNode != null)
			{
				if (SelectedNode.ShiftDetails != null)
				{
					this.dtpShiftFrom.Value = SelectedNode.ShiftDetails.Start;
					this.dtpShiftUntil.Value = SelectedNode.ShiftDetails.End;
					this.dtpBreakFrom.Value = SelectedNode.ShiftDetails.BreakStart;
					this.dtpBreakUntil.Value = SelectedNode.ShiftDetails.BreakEnd;
					this.txtShiftMaxPersons.Text = SelectedNode.ShiftDetails.MaxPersons.ToString();
				}
			}
		}

		private void SetRequirementFields()
		{
			// set the fields to the requirement details, if there are any
			RequirementTreeNode SelectedNode = this.trvMain.SelectedNode as RequirementTreeNode;
			if (SelectedNode != null)
			{
				if (SelectedNode.RequirementDetails != null)
				{
					this.lblRequirementFromValue.Text = SelectedNode.RequirementDetails.From.ToShortTimeString();
					this.lblRequirementUntilValue.Text = SelectedNode.RequirementDetails.Until.ToShortTimeString();
					this.txtRequirementPersons.Text = SelectedNode.RequirementDetails.RequiredPersons.ToString();
				}
			}
		}

		private void FillShiftList()
		{
			this.lsvAllShifts.Items.Clear();
			foreach (ShiftTreeNode stn in this.trvMain.SelectedNode.Nodes)
			{
				this.lsvAllShifts.Items.Add(stn.GetListViewItem());
			}
		}

		private void FillRequirementsList()
		{
			this.lsvAllRequirements.Items.Clear();
			foreach (RequirementTreeNode rtn in this.trvMain.SelectedNode.Nodes)
			{
				this.lsvAllRequirements.Items.Add(rtn.GetListViewItem());
			}
		}
		#endregion

		#region Main Menu Actions
		private void mnuSave_Click(object sender, System.EventArgs e)
		{
			if (this.Filename == null)
				SaveFile(true);
			else
				SaveFile(false);
		}
		
		private void mnuSaveAs_Click(object sender, System.EventArgs e)
		{
			SaveFile(true);
		}

		private void mnuOpen_Click(object sender, System.EventArgs e)
		{
			LoadFile();
		}

		private void mnuFileNew_Click(object sender, System.EventArgs e)
		{
			// check whether current problem contains data, confirm loss
			if (this.ShiftNode.Nodes.Count > 0)
			{
				DialogResult result = MessageBox.Show("Wollen Sie die bestehenden Daten verwerfen?", "Daten verwerfen", MessageBoxButtons.OKCancel);
				if (result == DialogResult.Cancel)
					return;
			}

			this.trvMain.SelectedNode = RootNode;
			this.ShiftNode.Nodes.Clear();
			this.RequirementNode.Nodes.Clear();
			this.InitializeRequirements();
		}
		#endregion
		
		#region XML File I/O Routines
		private void LoadFile()
		{
			// open informations in an XML file
			ExtendedTreeNode ShiftNodeRoot = this.trvMain.Nodes[0].Nodes[0] as ExtendedTreeNode;
			ExtendedTreeNode RequirementsNodeRoot = this.trvMain.Nodes[0].Nodes[1] as ExtendedTreeNode;

			DialogResult openDialog = ofdMain.ShowDialog();
			if (openDialog == DialogResult.Cancel)
			return;
			else
			this.Filename = ofdMain.FileName;

			// check whether current problem contains data, confirm loss
			if (ShiftNodeRoot.Nodes.Count > 0 || RequirementsNodeRoot.Nodes.Count > 0)
			{
				DialogResult result = MessageBox.Show("Das Öffnen einer Datei verwirft die derzeitigen Daten. Wollen Sie mit dem Öffnen fortfahren?", "Daten verwerfen", MessageBoxButtons.OKCancel);
				if (result == DialogResult.Cancel)
				return;
			}

			XmlTextReader reader = new XmlTextReader(this.Filename);			
			reader.ReadStartElement("Persplan");
			ShiftNodeRoot.Nodes.Clear();
			RequirementsNodeRoot.Nodes.Clear();
			while (reader.IsStartElement("Shift"))
			{
				Shift ReadShift = new Shift();
				reader.ReadStartElement("Shift");
				reader.ReadStartElement("ShiftStart");
				ReadShift.Start = Convert.ToDateTime(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadStartElement("ShiftEnd");
				ReadShift.End = Convert.ToDateTime(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadStartElement("BreakStart");
				ReadShift.BreakStart = Convert.ToDateTime(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadStartElement("BreakEnd");
				ReadShift.BreakEnd = Convert.ToDateTime(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadStartElement("MaxPersons");
				ReadShift.MaxPersons = Convert.ToInt32(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadStartElement("Preference");
				ReadShift.Preference = Convert.ToInt32(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadEndElement();
				ShiftTreeNode NodeToAdd = new ShiftTreeNode(ShiftNodeRoot);
				NodeToAdd.ShiftDetails = ReadShift;
				ShiftNodeRoot.Nodes.Add(NodeToAdd);
			}
			while (reader.IsStartElement("Requirement"))
			{
				Requirement ReadRequirement = new Requirement();
				reader.ReadStartElement("Requirement");
				reader.ReadStartElement("Start");
				ReadRequirement.From = Convert.ToDateTime(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadStartElement("End");
				ReadRequirement.Until = Convert.ToDateTime(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadStartElement("RequiredPersons");
				ReadRequirement.RequiredPersons = Convert.ToInt32(reader.ReadString());
				reader.ReadEndElement();
				reader.ReadEndElement();
				RequirementTreeNode NodeToAdd = new RequirementTreeNode(RequirementsNodeRoot);
				NodeToAdd.RequirementDetails = ReadRequirement;
				RequirementsNodeRoot.Nodes.Add(NodeToAdd);
			}			
			reader.Close();
			this.trvMain.ExpandAll();
			this.Text = "PersPlan - " + this.Filename;
		}

		private void SaveFile(bool ShowSelectDialog)
		{
			if (ShowSelectDialog)
			{
				DialogResult saveDialog = sfdMain.ShowDialog();
				if (saveDialog == DialogResult.Cancel)
					return;
				else
					this.Filename = sfdMain.FileName;
			}
			// save informations in an XML file
			ExtendedTreeNode ShiftNodeRoot = this.trvMain.Nodes[0].Nodes[0] as ExtendedTreeNode;
			ExtendedTreeNode RequirementsNodeRoot = this.trvMain.Nodes[0].Nodes[1] as ExtendedTreeNode;

			XmlTextWriter writer = new XmlTextWriter(this.Filename, System.Text.Encoding.Default);
			writer.WriteStartElement("Persplan");
			foreach (ShiftTreeNode ShiftNode in ShiftNodeRoot.Nodes)
			{
				writer.WriteStartElement("Shift");
				writer.WriteElementString("ShiftStart", ShiftNode.ShiftDetails.Start.ToShortTimeString());
				writer.WriteElementString("ShiftEnd", ShiftNode.ShiftDetails.End.ToShortTimeString());
				writer.WriteElementString("BreakStart", ShiftNode.ShiftDetails.BreakStart.ToShortTimeString());
				writer.WriteElementString("BreakEnd", ShiftNode.ShiftDetails.BreakEnd.ToShortTimeString());
				writer.WriteElementString("MaxPersons", ShiftNode.ShiftDetails.MaxPersons.ToString());
				writer.WriteElementString("Preference", ShiftNode.ShiftDetails.Preference.ToString());
				writer.WriteEndElement();
			}
			foreach (RequirementTreeNode RequirementNode in RequirementsNodeRoot.Nodes)
			{
				writer.WriteStartElement("Requirement");
				writer.WriteElementString("Start", RequirementNode.RequirementDetails.From.ToShortTimeString());
				writer.WriteElementString("End", RequirementNode.RequirementDetails.Until.ToShortTimeString());
				writer.WriteElementString("RequiredPersons", RequirementNode.RequirementDetails.RequiredPersons.ToString());
				writer.WriteEndElement();
			}
			writer.WriteEndElement();
			writer.Close();
		}
		#endregion

		#region Validating DateTimePicker values
		private void dtpShiftFrom_Validating(object sender, System.ComponentModel.CancelEventArgs e)
		{
			if (this.dtpShiftFrom.Value.Minute != 0 && this.dtpShiftFrom.Value.Minute != 30)
			{
				MessageBox.Show("Es können nur halbstündige Intervalle eingegeben werden.", "Kein gültiger Wert!");
				e.Cancel=true;
			}
		}

		private void dtpShiftUntil_Validating(object sender, System.ComponentModel.CancelEventArgs e)
		{
			if (this.dtpShiftUntil.Value.Minute != 0 && this.dtpShiftUntil.Value.Minute != 30)
			{
				MessageBox.Show("Es können nur halbstündige Intervalle eingegeben werden.", "Kein gültiger Wert!");
				e.Cancel=true;
			}		
		}

		private void dtpBreakFrom_Validating(object sender, System.ComponentModel.CancelEventArgs e)
		{
			if (this.dtpBreakFrom.Value.Minute != 0 && this.dtpBreakFrom.Value.Minute != 30)
			{
				MessageBox.Show("Es können nur halbstündige Intervalle eingegeben werden.", "Kein gültiger Wert!");
				e.Cancel=true;
			}		
		}

		private void dtpBreakUntil_Validating(object sender, System.ComponentModel.CancelEventArgs e)
		{
			if (this.dtpBreakUntil.Value.Minute != 0 && this.dtpBreakUntil.Value.Minute != 30)
			{
				MessageBox.Show("Es können nur halbstündige Intervalle eingegeben werden.", "Kein gültiger Wert!");
				e.Cancel=true;
			}		
		}
		#endregion

		private void InitializeRequirements()
		{
			for (int hour = 0; hour <= 23; hour++)
			{
				Requirement NewReq = new Requirement();
				NewReq.From = new DateTime(2004, 1, 1, hour, 0, 0, 0);
				NewReq.Until = new DateTime(2004, 1, 1, hour, 30, 0, 0);
				RequirementTreeNode NewReqNode = new RequirementTreeNode(this.RequirementNode);
				NewReqNode.RequirementDetails = NewReq;
				this.RequirementNode.Nodes.Add(NewReqNode);
				
				NewReq = new Requirement();
				NewReq.From = new DateTime(2004, 1, 1, hour, 30, 0, 0);
				if (hour != 23)
					NewReq.Until = new DateTime(2004, 1, 1, hour+1, 0, 0, 0);
				else
					NewReq.Until = new DateTime(2004, 1, 1, 0, 0, 0, 0);
				NewReqNode = new RequirementTreeNode(this.RequirementNode);
				NewReqNode.RequirementDetails = NewReq;
				this.RequirementNode.Nodes.Add(NewReqNode);				
			}
		}

		private void InitializeTree()
		{
			this.RequirementNode = new ExtendedTreeNode("Bedarfszeiten", TreeNodeID.TreeNodeIDRequirements);
			this.ShiftNode = new ExtendedTreeNode("Schichten", TreeNodeID.TreeNodeIDShift);
			this.SolutionNode = new ExtendedTreeNode("Lösung", TreeNodeID.TreeNodeIDSolution);
			this.RootNode = new ExtendedTreeNode("Personalplan", TreeNodeID.TreeNodeIDRoot, new ExtendedTreeNode[] {this.ShiftNode, this.RequirementNode, this.SolutionNode});
		}

		private void graphControl1_Resize(object sender, System.EventArgs e)
		{
			this.graphControl1.Invalidate();
			SetGraphValues();
		}

		private void SetGraphValues()
		{
			int i = 1;
			int maximum = 0;
			this.graphControl1.ClearPoints();
			
			// find maximum
			foreach (RequirementTreeNode node in this.RequirementNode.Nodes)
			{
				if (node.RequirementDetails.RequiredPersons > maximum)
					maximum = node.RequirementDetails.RequiredPersons;
			}

			// initialize Requirements graph
			if (maximum > 5)
				this.graphControl1.GRAPHSTRUCT = new UserControls.GraphStruct(48, 6, 1, (int)maximum/5, 1, (int)maximum / 5);
			else
				this.graphControl1.GRAPHSTRUCT = new UserControls.GraphStruct(48, maximum, 1, 1, 1, 1);
 
			foreach (RequirementTreeNode node in this.RequirementNode.Nodes)
			{				
				this.graphControl1.AddPoints(i, node.RequirementDetails.RequiredPersons);
				i++;
			}
		}

		public void SetSolutionGraphValues()
		{
			double maximum = 0.0;
			//this.graphControl2.ClearPoints();
			this.graphControl2.ClearPointsSeries(1);
			this.graphControl2.ClearPointsSeries(2);
			this.graphControl2.SetSeriesColor(1, Color.BlueViolet);
			this.graphControl2.SetSeriesColor(2, Color.Chartreuse);
			this.graphControl2.SetSeriesStyle(1, false);
			this.graphControl2.SetSeriesStyle(2, true);
			this.graphControl2.SetSeriesName(1, "Zugeteilt");
			this.graphControl2.SetSeriesName(2, "Bedarf");

			// get numbers over periods
			double[] Staff = new double[48];
			int[] Rec = new int[48];

			// find maximum
			foreach (ShiftTreeNode node in this.ShiftNode.Nodes)
			{
				/*if (node.ShiftDetails.Activity > maximum)
					maximum = node.ShiftDetails.Activity;*/
				int startIndex = node.ShiftDetails.Start.Hour * 2 + (int)(node.ShiftDetails.Start.Minute/30);
				int endIndex = node.ShiftDetails.End.Hour * 2 + (int)(node.ShiftDetails.End.Minute/30);
				if (startIndex < endIndex)
				{
					for (; startIndex < endIndex; startIndex++)
					{
						Staff[startIndex] += node.ShiftDetails.Activity;
						if (Staff[startIndex] > maximum)
							maximum = Staff[startIndex];
					}
				}
				else
				{
					// shift spans midnight
					int OriginalEndIndex = endIndex;
					endIndex = 47;
					for (; startIndex < endIndex; startIndex++)
					{
						Staff[startIndex] += node.ShiftDetails.Activity;
						if (Staff[startIndex] > maximum)
							maximum = Staff[startIndex];
					}
					startIndex = 0;
					endIndex = OriginalEndIndex;
					for (; startIndex < endIndex; startIndex++)
					{
						Staff[startIndex] += node.ShiftDetails.Activity;
						if (Staff[startIndex] > maximum)
							maximum = Staff[startIndex];
					}
				}
			}

			foreach (RequirementTreeNode node in this.RequirementNode.Nodes)
			{
				int startIndex = node.RequirementDetails.From.Hour * 2 + (int)(node.RequirementDetails.From.Minute/30);
				int endIndex = node.RequirementDetails.Until.Hour * 2 + (int)(node.RequirementDetails.Until.Minute/30);
				for (; startIndex < endIndex; startIndex++)
				{
					Rec[startIndex] = node.RequirementDetails.RequiredPersons;
					if (Rec[startIndex] > (int)Math.Ceiling(maximum))
						maximum = Convert.ToDouble(Rec[startIndex]);
				}
			}
				

			// initialize Solution graph
			if (maximum > 5.0)
				this.graphControl2.GRAPHSTRUCT = new UserControls.GraphStruct(48, 6, 1, (int)Math.Ceiling(maximum/5), 1, (int)maximum / 5);
			else
				this.graphControl2.GRAPHSTRUCT = new UserControls.GraphStruct(48, (int)maximum, 1, 1, 1, 1);

			for (int i = 0; i < 48; i++)
			{
				this.graphControl2.AddPointsSeries(i+1, Rec[i], 2);
				this.graphControl2.AddPointsSeries(i+1, (int)Staff[i], 1);				
			}

			//this.graphControl2.
		}

		void InitializeCboSolvers()
		{			
			this.cboSolver.Items.Clear();
			foreach (sSolver Solver in this.Solvers)
			{				
				this.cboSolver.Items.Add(Solver);
			}
			if (this.cboSolver.Items.Count > 0)
				this.cboSolver.SelectedIndex=0;
		}

		private void btnSolve_Click(object sender, System.EventArgs e)
		{
			sSolver SelectedSolver = (sSolver)this.cboSolver.SelectedItem;
			PersplanSolverInterface.IPersplanSolver SolverObject = SelectedSolver.SolverObject;
			if (SolverObject != null)
			{
				SolverObject.Solve(this.RootNode);
				SolverObject.GetSolution();
			}

			this.lsvSolution.Items.Clear();
			// display result
			foreach (ExtendedTreeNode node in this.ShiftNode.Nodes)
			{
				ShiftTreeNode ShiftNode = node as ShiftTreeNode;				
				ListViewItem shownItem = new ListViewItem();
				shownItem.Text = ShiftNode.ShiftDetails.Start.ToShortTimeString();
				shownItem.SubItems.Add(ShiftNode.ShiftDetails.End.ToShortTimeString());
				shownItem.SubItems.Add(ShiftNode.ShiftDetails.Activity.ToString());
				this.lsvSolution.Items.Add(shownItem);
				foreach (Break b in ShiftNode.ShiftDetails.Breaks)
				{
					ListViewItem breakItem = new ListViewItem();
					breakItem.Text = "";
					breakItem.SubItems.Add("");
					breakItem.SubItems.Add("");
					breakItem.SubItems.Add(b.Start.ToShortTimeString());
					breakItem.SubItems.Add(b.End.ToShortTimeString());
					breakItem.SubItems.Add(b.Activity.ToString());
					this.lsvSolution.Items.Add(breakItem);
				}
			}
			this.lsvSolution.Visible = true;
			this.graphControl2.Visible = true;
			SetSolutionGraphValues();
			this.graphControl2.Invalidate();
		}

		private void graphControl2_Resize(object sender, System.EventArgs e)
		{
			this.graphControl2.Invalidate();
			SetSolutionGraphValues();
		}

		private void mnuHelpInfo_Click(object sender, System.EventArgs e)
		{
			string InfoMessage = "Persplan.exe\n\n";
			InfoMessage += "Eine Anwendung aus der betrieblichen Systemforschung\n";
			InfoMessage += "Roland Dick / SS 04\n";			
			InfoMessage += "Graph-Control basierend auf Quellcode von unbekanntem Autor\n";
			MessageBox.Show(InfoMessage);
		}

		private void mnuEnd_Click(object sender, System.EventArgs e)
		{
			Application.Exit();
		}

	}
}