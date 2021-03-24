using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Diagnostics;
using System.Threading;
using System.IO;


namespace Clearview
{
	/// <summary>
	/// Dies ist die View Klasse von Clearview
	/// </summary>
	public class ClearViewMain : System.Windows.Forms.Form
	{
		private System.Windows.Forms.TabControl SelectTab;
		private System.Windows.Forms.TabPage EinfachPage;
		private System.Windows.Forms.TabPage SystemPage;
		private System.Windows.Forms.TabPage DetailPage;
		private System.Windows.Forms.ListView VariableList;
		private System.Windows.Forms.ColumnHeader Variable;
		public System.Windows.Forms.ColumnHeader Wert;
		private System.Windows.Forms.Label ZFLbl;
		private System.Windows.Forms.TextBox ZFBox;
		private System.Windows.Forms.DataGrid SystemGrid;
		private System.Windows.Forms.MainMenu MainMenu;
		private System.Windows.Forms.MenuItem DateiMenu;
		private System.Windows.Forms.MenuItem OpenFile;
		private System.Windows.Forms.MenuItem Save;
		private System.Windows.Forms.MenuItem Beenden;
		private System.Windows.Forms.MenuItem BearbeitenMenu;
		private System.Windows.Forms.MenuItem GetSystemClipboard;
		private System.Windows.Forms.MenuItem ExportHTML;
		private System.Windows.Forms.DataGrid FullInfoGrid;
		/// <summary>
		/// Erforderliche Designervariable.
		/// </summary>
		private System.ComponentModel.Container components = null;
		private System.Windows.Forms.DataGridTableStyle dataGridTableStyle1;
		private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn1;
		private System.Windows.Forms.MenuItem Exportmenu;
		private System.Windows.Forms.MenuItem CloseMenu;
		private System.Windows.Forms.DataGridTableStyle dataGridTableStyle2;
		private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn2;

		
		private System.Windows.Forms.MenuItem InfoMenu;
		private System.Windows.Forms.MenuItem MiscMenu;
		private System.Windows.Forms.MenuItem HelpMenu;
		private System.Windows.Forms.TextBox SolverBox;
		private System.Windows.Forms.TextBox BoxMessage;
		private System.Windows.Forms.Label SolverLbl;
		private System.Windows.Forms.MenuItem ConfigMenu;
		private System.Windows.Forms.Label label2;
		
		//eigene Klassen
		private SolverData theData;
		private AccessIni theAccessIni;
		public ClearViewMain()
		{
			//
			// Erforderlich für die Windows Form-Designerunterstützung
			//
			InitializeComponent();			
			theData=new SolverData();
			theAccessIni=new AccessIni();
			String verzeichnis=Directory.GetCurrentDirectory()+ "\\CVConfig.xml";
			theAccessIni.Load(verzeichnis);
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

		/// <summary>
		/// (Neu-) Laden der Daten in das Window
		/// </summary>
		/// <param name="path">Pfad und Name der XML </param>
		private void DisplayData(String path)
		{
			if(theData.Load(path)==false)
			{
				MessageBox.Show(theData.getErrorText(), "Kritischer Fehler");
				return;
			}		

			//Aufbau der einfachen Seite
			VariableList.Items.Clear();
			for(int i=0; i<theData.getAnzahlColumns(); i++)
			{
				VariableList.Items.Add(theData.getColumn(i).Name);				
				VariableList.Items[i].SubItems.Add(theData.getColumn(i).FinalValue);
			}
			ZFBox.Text=theData.getFunktionValue();
			SolverBox.Text=theData.getUsedSolver();
			BoxMessage.Text=theData.getMessage();
			
			//Aufbau der System Seite
			SystemGrid.ColumnHeadersVisible=true;
			SystemGrid.CaptionText="Das Ursprungssystem";
			try
			{
				DataSet myDataSet = new DataSet("myDataSet");				
				myDataSet.Tables.Add(theData.getSystemTable());
				SystemGrid.SetDataBinding(myDataSet, "SystemData");				
			}
			catch(Exception e)
			{
				MessageBox.Show(e.ToString());
				return;
			}

			//Aufbau der Detail Seite
			FullInfoGrid.ColumnHeadersVisible=true;
			FullInfoGrid.CaptionText="Alle Daten";
			try
			{
				DataSet myDataSet = new DataSet("myDataSet");				
				myDataSet.Tables.Add(theData.getFullTable());
				FullInfoGrid.SetDataBinding(myDataSet, "FullData");
				
			}
			catch(Exception e)
			{
				MessageBox.Show(e.ToString());
				return;
			}

		}

		#region Vom Windows Form-Designer generierter Code
		/// <summary>
		/// Erforderliche Methode für die Designerunterstützung. 
		/// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
		/// </summary>
		private void InitializeComponent()
		{
			this.SelectTab = new System.Windows.Forms.TabControl();
			this.EinfachPage = new System.Windows.Forms.TabPage();
			this.label2 = new System.Windows.Forms.Label();
			this.SolverLbl = new System.Windows.Forms.Label();
			this.BoxMessage = new System.Windows.Forms.TextBox();
			this.SolverBox = new System.Windows.Forms.TextBox();
			this.ZFBox = new System.Windows.Forms.TextBox();
			this.ZFLbl = new System.Windows.Forms.Label();
			this.VariableList = new System.Windows.Forms.ListView();
			this.Variable = new System.Windows.Forms.ColumnHeader();
			this.Wert = new System.Windows.Forms.ColumnHeader();
			this.SystemPage = new System.Windows.Forms.TabPage();
			this.SystemGrid = new System.Windows.Forms.DataGrid();
			this.dataGridTableStyle1 = new System.Windows.Forms.DataGridTableStyle();
			this.dataGridTextBoxColumn1 = new System.Windows.Forms.DataGridTextBoxColumn();
			this.DetailPage = new System.Windows.Forms.TabPage();
			this.FullInfoGrid = new System.Windows.Forms.DataGrid();
			this.dataGridTableStyle2 = new System.Windows.Forms.DataGridTableStyle();
			this.dataGridTextBoxColumn2 = new System.Windows.Forms.DataGridTextBoxColumn();
			this.MainMenu = new System.Windows.Forms.MainMenu();
			this.DateiMenu = new System.Windows.Forms.MenuItem();
			this.OpenFile = new System.Windows.Forms.MenuItem();
			this.Save = new System.Windows.Forms.MenuItem();
			this.CloseMenu = new System.Windows.Forms.MenuItem();
			this.ExportHTML = new System.Windows.Forms.MenuItem();
			this.Beenden = new System.Windows.Forms.MenuItem();
			this.BearbeitenMenu = new System.Windows.Forms.MenuItem();
			this.GetSystemClipboard = new System.Windows.Forms.MenuItem();
			this.Exportmenu = new System.Windows.Forms.MenuItem();
			this.MiscMenu = new System.Windows.Forms.MenuItem();
			this.HelpMenu = new System.Windows.Forms.MenuItem();
			this.InfoMenu = new System.Windows.Forms.MenuItem();
			this.ConfigMenu = new System.Windows.Forms.MenuItem();
			this.SelectTab.SuspendLayout();
			this.EinfachPage.SuspendLayout();
			this.SystemPage.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.SystemGrid)).BeginInit();
			this.DetailPage.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.FullInfoGrid)).BeginInit();
			this.SuspendLayout();
			// 
			// SelectTab
			// 
			this.SelectTab.Controls.Add(this.EinfachPage);
			this.SelectTab.Controls.Add(this.SystemPage);
			this.SelectTab.Controls.Add(this.DetailPage);
			this.SelectTab.Dock = System.Windows.Forms.DockStyle.Fill;
			this.SelectTab.Location = new System.Drawing.Point(0, 0);
			this.SelectTab.Name = "SelectTab";
			this.SelectTab.SelectedIndex = 0;
			this.SelectTab.Size = new System.Drawing.Size(734, 454);
			this.SelectTab.TabIndex = 0;
			// 
			// EinfachPage
			// 
			this.EinfachPage.Controls.Add(this.label2);
			this.EinfachPage.Controls.Add(this.SolverLbl);
			this.EinfachPage.Controls.Add(this.BoxMessage);
			this.EinfachPage.Controls.Add(this.SolverBox);
			this.EinfachPage.Controls.Add(this.ZFBox);
			this.EinfachPage.Controls.Add(this.ZFLbl);
			this.EinfachPage.Controls.Add(this.VariableList);
			this.EinfachPage.Location = new System.Drawing.Point(4, 25);
			this.EinfachPage.Name = "EinfachPage";
			this.EinfachPage.Size = new System.Drawing.Size(726, 425);
			this.EinfachPage.TabIndex = 0;
			this.EinfachPage.Text = "Einfach";
			// 
			// label2
			// 
			this.label2.Location = new System.Drawing.Point(16, 352);
			this.label2.Name = "label2";
			this.label2.TabIndex = 6;
			this.label2.Text = "Nachricht";
			// 
			// SolverLbl
			// 
			this.SolverLbl.Location = new System.Drawing.Point(16, 312);
			this.SolverLbl.Name = "SolverLbl";
			this.SolverLbl.TabIndex = 5;
			this.SolverLbl.Text = "Solver";
			// 
			// BoxMessage
			// 
			this.BoxMessage.Location = new System.Drawing.Point(128, 352);
			this.BoxMessage.Name = "BoxMessage";
			this.BoxMessage.ReadOnly = true;
			this.BoxMessage.Size = new System.Drawing.Size(160, 22);
			this.BoxMessage.TabIndex = 4;
			this.BoxMessage.Text = "";
			// 
			// SolverBox
			// 
			this.SolverBox.Location = new System.Drawing.Point(128, 312);
			this.SolverBox.Name = "SolverBox";
			this.SolverBox.ReadOnly = true;
			this.SolverBox.Size = new System.Drawing.Size(160, 22);
			this.SolverBox.TabIndex = 3;
			this.SolverBox.Text = "";
			// 
			// ZFBox
			// 
			this.ZFBox.Location = new System.Drawing.Point(128, 272);
			this.ZFBox.Name = "ZFBox";
			this.ZFBox.ReadOnly = true;
			this.ZFBox.Size = new System.Drawing.Size(160, 22);
			this.ZFBox.TabIndex = 2;
			this.ZFBox.Text = "";
			// 
			// ZFLbl
			// 
			this.ZFLbl.Location = new System.Drawing.Point(16, 272);
			this.ZFLbl.Name = "ZFLbl";
			this.ZFLbl.TabIndex = 1;
			this.ZFLbl.Text = "Zielfunktion";
			// 
			// VariableList
			// 
			this.VariableList.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																						   this.Variable,
																						   this.Wert});
			this.VariableList.FullRowSelect = true;
			this.VariableList.GridLines = true;
			this.VariableList.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
			this.VariableList.Location = new System.Drawing.Point(56, 8);
			this.VariableList.MultiSelect = false;
			this.VariableList.Name = "VariableList";
			this.VariableList.Size = new System.Drawing.Size(232, 240);
			this.VariableList.TabIndex = 0;
			this.VariableList.View = System.Windows.Forms.View.Details;
			// 
			// Variable
			// 
			this.Variable.Text = "Variable";
			this.Variable.Width = 91;
			// 
			// Wert
			// 
			this.Wert.Text = "Wert";
			this.Wert.Width = 137;
			// 
			// SystemPage
			// 
			this.SystemPage.Controls.Add(this.SystemGrid);
			this.SystemPage.Location = new System.Drawing.Point(4, 25);
			this.SystemPage.Name = "SystemPage";
			this.SystemPage.Size = new System.Drawing.Size(726, 425);
			this.SystemPage.TabIndex = 1;
			this.SystemPage.Text = "System";
			// 
			// SystemGrid
			// 
			this.SystemGrid.AllowNavigation = false;
			this.SystemGrid.AllowSorting = false;
			this.SystemGrid.DataMember = "";
			this.SystemGrid.Dock = System.Windows.Forms.DockStyle.Fill;
			this.SystemGrid.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.SystemGrid.Location = new System.Drawing.Point(0, 0);
			this.SystemGrid.Name = "SystemGrid";
			this.SystemGrid.ReadOnly = true;
			this.SystemGrid.RowHeadersVisible = false;
			this.SystemGrid.Size = new System.Drawing.Size(726, 425);
			this.SystemGrid.TabIndex = 0;
			this.SystemGrid.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[] {
																								   this.dataGridTableStyle1});
			// 
			// dataGridTableStyle1
			// 
			this.dataGridTableStyle1.AllowSorting = false;
			this.dataGridTableStyle1.DataGrid = this.SystemGrid;
			this.dataGridTableStyle1.ForeColor = System.Drawing.SystemColors.HotTrack;
			this.dataGridTableStyle1.GridColumnStyles.AddRange(new System.Windows.Forms.DataGridColumnStyle[] {
																												  this.dataGridTextBoxColumn1});
			this.dataGridTableStyle1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.dataGridTableStyle1.MappingName = "";
			this.dataGridTableStyle1.ReadOnly = true;
			this.dataGridTableStyle1.RowHeadersVisible = false;
			// 
			// dataGridTextBoxColumn1
			// 
			this.dataGridTextBoxColumn1.Format = "";
			this.dataGridTextBoxColumn1.FormatInfo = null;
			this.dataGridTextBoxColumn1.MappingName = "";
			this.dataGridTextBoxColumn1.ReadOnly = true;
			this.dataGridTextBoxColumn1.Width = 75;
			// 
			// DetailPage
			// 
			this.DetailPage.Controls.Add(this.FullInfoGrid);
			this.DetailPage.Location = new System.Drawing.Point(4, 25);
			this.DetailPage.Name = "DetailPage";
			this.DetailPage.Size = new System.Drawing.Size(726, 425);
			this.DetailPage.TabIndex = 2;
			this.DetailPage.Text = "Details";
			// 
			// FullInfoGrid
			// 
			this.FullInfoGrid.AllowNavigation = false;
			this.FullInfoGrid.AllowSorting = false;
			this.FullInfoGrid.DataMember = "";
			this.FullInfoGrid.Dock = System.Windows.Forms.DockStyle.Fill;
			this.FullInfoGrid.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.FullInfoGrid.Location = new System.Drawing.Point(0, 0);
			this.FullInfoGrid.Name = "FullInfoGrid";
			this.FullInfoGrid.PreferredColumnWidth = 100;
			this.FullInfoGrid.ReadOnly = true;
			this.FullInfoGrid.RowHeadersVisible = false;
			this.FullInfoGrid.Size = new System.Drawing.Size(726, 425);
			this.FullInfoGrid.TabIndex = 0;
			this.FullInfoGrid.TableStyles.AddRange(new System.Windows.Forms.DataGridTableStyle[] {
																									 this.dataGridTableStyle2});
			// 
			// dataGridTableStyle2
			// 
			this.dataGridTableStyle2.DataGrid = this.FullInfoGrid;
			this.dataGridTableStyle2.GridColumnStyles.AddRange(new System.Windows.Forms.DataGridColumnStyle[] {
																												  this.dataGridTextBoxColumn2});
			this.dataGridTableStyle2.HeaderForeColor = System.Drawing.SystemColors.ControlText;
			this.dataGridTableStyle2.MappingName = "";
			this.dataGridTableStyle2.PreferredColumnWidth = 125;
			// 
			// dataGridTextBoxColumn2
			// 
			this.dataGridTextBoxColumn2.Format = "";
			this.dataGridTextBoxColumn2.FormatInfo = null;
			this.dataGridTextBoxColumn2.MappingName = "";
			this.dataGridTextBoxColumn2.ReadOnly = true;
			this.dataGridTextBoxColumn2.Width = 250;
			// 
			// MainMenu
			// 
			this.MainMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					 this.DateiMenu,
																					 this.BearbeitenMenu,
																					 this.Exportmenu,
																					 this.MiscMenu});
			// 
			// DateiMenu
			// 
			this.DateiMenu.Index = 0;
			this.DateiMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.OpenFile,
																					  this.Save,
																					  this.CloseMenu,
																					  this.ExportHTML,
																					  this.ConfigMenu,
																					  this.Beenden});
			this.DateiMenu.Text = "&Datei";
			// 
			// OpenFile
			// 
			this.OpenFile.Index = 0;
			this.OpenFile.Shortcut = System.Windows.Forms.Shortcut.CtrlO;
			this.OpenFile.Text = "Ö&ffnen";
			this.OpenFile.Click += new System.EventHandler(this.OpenFile_Click);
			// 
			// Save
			// 
			this.Save.Index = 1;
			this.Save.Shortcut = System.Windows.Forms.Shortcut.CtrlS;
			this.Save.Text = "&Speichern";
			this.Save.Click += new System.EventHandler(this.Save_Click);
			// 
			// CloseMenu
			// 
			this.CloseMenu.Index = 2;
			this.CloseMenu.Shortcut = System.Windows.Forms.Shortcut.CtrlC;
			this.CloseMenu.Text = "S&chliessen";
			this.CloseMenu.Click += new System.EventHandler(this.Close_Click);
			// 
			// ExportHTML
			// 
			this.ExportHTML.Index = 3;
			this.ExportHTML.Shortcut = System.Windows.Forms.Shortcut.CtrlE;
			this.ExportHTML.Text = "&Export nach HTML";
			this.ExportHTML.Click += new System.EventHandler(this.ExportHTML_Click);
			// 
			// Beenden
			// 
			this.Beenden.Index = 5;
			this.Beenden.Shortcut = System.Windows.Forms.Shortcut.CtrlQ;
			this.Beenden.Text = "&Beenden";
			this.Beenden.Click += new System.EventHandler(this.Beenden_Click);
			// 
			// BearbeitenMenu
			// 
			this.BearbeitenMenu.Index = 1;
			this.BearbeitenMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																						   this.GetSystemClipboard});
			this.BearbeitenMenu.Text = "&Bearbeiten";
			// 
			// GetSystemClipboard
			// 
			this.GetSystemClipboard.Index = 0;
			this.GetSystemClipboard.Shortcut = System.Windows.Forms.Shortcut.CtrlV;
			this.GetSystemClipboard.Text = "System aus Clip&board holen";
			this.GetSystemClipboard.Click += new System.EventHandler(this.GetSystemClipboard_Click);
			// 
			// Exportmenu
			// 
			this.Exportmenu.Index = 2;
			this.Exportmenu.Shortcut = System.Windows.Forms.Shortcut.CtrlE;
			this.Exportmenu.Text = "Export nach HTML";
			this.Exportmenu.Click += new System.EventHandler(this.ExportHTML_Click);
			// 
			// MiscMenu
			// 
			this.MiscMenu.Index = 3;
			this.MiscMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					 this.HelpMenu,
																					 this.InfoMenu});
			this.MiscMenu.Text = "?";
			// 
			// HelpMenu
			// 
			this.HelpMenu.Index = 0;
			this.HelpMenu.Text = "Hilfe...";
			this.HelpMenu.Click += new System.EventHandler(this.HelpMenu_Click);
			// 
			// InfoMenu
			// 
			this.InfoMenu.Index = 1;
			this.InfoMenu.Text = "Info...";
			this.InfoMenu.Click += new System.EventHandler(this.InfoMenu_Click);
			// 
			// ConfigMenu
			// 
			this.ConfigMenu.Index = 4;
			this.ConfigMenu.Text = "Einstellungen...";
			this.ConfigMenu.Click += new System.EventHandler(this.ConfigMenu_Click);
			// 
			// ClearViewMain
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(6, 15);
			this.ClientSize = new System.Drawing.Size(734, 454);
			this.Controls.Add(this.SelectTab);
			this.Menu = this.MainMenu;
			this.Name = "ClearViewMain";
			this.Text = "ClearView";
			this.SelectTab.ResumeLayout(false);
			this.EinfachPage.ResumeLayout(false);
			this.SystemPage.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.SystemGrid)).EndInit();
			this.DetailPage.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.FullInfoGrid)).EndInit();
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// Der Haupteinstiegspunkt für die Anwendung.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new ClearViewMain());
		}
		/// <summary>
		/// Methode zum Beenden von ClearView
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void Beenden_Click(object sender, System.EventArgs e)
		{
			theAccessIni.Close();
			this.Close();
		}
		/// <summary>
		/// Methode zum Öffnen vorhandener XMl Dateien
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void OpenFile_Click(object sender, System.EventArgs e)
		{
			
			OpenFileDialog openFileDialog1 = new OpenFileDialog();
			openFileDialog1.InitialDirectory = "c:\\temp" ;
			openFileDialog1.Filter = "XML Dateien (*.xml)|*.xml|Alle Dateien (*.*)|*.*" ;
			openFileDialog1.FilterIndex = 1 ;
			openFileDialog1.RestoreDirectory = true ;

			if(openFileDialog1.ShowDialog() == DialogResult.OK)
			{
				this.DisplayData(openFileDialog1.FileName);	
				this.Text="ClearView: " +openFileDialog1.FileName; 			
			}
		}
		/// <summary>
		/// Methode zum Spechern von XML Dateien
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void Save_Click(object sender, System.EventArgs e)
		{
			if(theData.getLPDocPath().Equals("")==true)
			{
				MessageBox.Show("Bitte laden Sie zuerst eine Datei!","Keine Datei geladen");
				return;
			}
			SaveFileDialog saveFileDialog1 = new SaveFileDialog();
			saveFileDialog1.Filter = "XML Dateien (*.xml)|*.xml|Alle Dateien (*.*)|*.*"  ;
			saveFileDialog1.FilterIndex = 1 ;
			saveFileDialog1.RestoreDirectory = true ;
 
			if(saveFileDialog1.ShowDialog() == DialogResult.OK)
			{
				//Kopieren erfolgt durch copy befehl
				String befehl="copy \"" + theData.getLPDocPath() + "\" \"" + saveFileDialog1.FileName + "\" /Y";
				Process p = Process.Start("cmd.exe", "/c " + befehl); //+ " /c/q"
				p.WaitForExit();				
			}

		}
		/// <summary>
		/// Methode zum Erstellen der HTML Statistik
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void ExportHTML_Click(object sender, System.EventArgs e)
		{
			if(theData.getLPDocPath().Equals("")==true)
			{
				MessageBox.Show("Bitte laden Sie zuerst eine Datei!","Keine Datei geladen");
				return;
			}
			SaveFileDialog saveFileDialog1 = new SaveFileDialog();
			saveFileDialog1.Filter = "HTML Dateien (*.htm, *html)|*.htm; *.html|Alle Dateien (*.*)|*.*"  ;
			saveFileDialog1.FilterIndex = 1 ;
			saveFileDialog1.RestoreDirectory = true ;
			if(saveFileDialog1.ShowDialog() == DialogResult.OK)
			{
				if(theData.generateHTMLStatistic(saveFileDialog1.FileName)==false)
				{
					MessageBox.Show(theData.getErrorText(), "Transformationsfehler");
					return;
				}
				try
				{
					Process.Start("IExplore.exe",saveFileDialog1.FileName);
				}
				catch(Exception f) //unwichtig, wenn Anzeige versagt.
				{ return; }
			}
		}
		/// <summary>
		/// Methode zum Holen von LP Daten aus dem Zwischenspeicher
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void GetSystemClipboard_Click(object sender, System.EventArgs e)
		{
			//Datei wird geschlossen.
			this.Close_Click(sender,e);
			
			//Temp Datei wird gelöscht.
			Process p = Process.Start("cmd.exe", "/c \"del c:\\temp\\clearview.xml\"");
			p.WaitForExit();

			String JavaBefehl=theAccessIni.getJavaPath(); 
			if(JavaBefehl.Equals("")==true)
				JavaBefehl="java.exe";
			else
				JavaBefehl+="\\java.exe";
			
			String Transformerbefehl=theAccessIni.getTransformerPath();
			if(Transformerbefehl.Equals("")==true)
				Transformerbefehl=" -jar transformer.jar clipboard C:\\TEMP\\clearview.xml";
			else
				Transformerbefehl=" -jar " +Transformerbefehl+ "\\transformer.jar clipboard C:\\TEMP\\clearview.xml";

			p = Process.Start(JavaBefehl, Transformerbefehl);			
			//Verzögerung, damit Erstellen erfolgreich ist 
			String pfad="C:\\TEMP\\clearview.xml";
			p.WaitForExit();
			if(p.ExitCode==0)			
				this.DisplayData(pfad);
			else if(p.ExitCode==1)
				MessageBox.Show("Ungültige Aufrufparameter!", "Transformationsfehler");			
			else if(p.ExitCode==2)
				MessageBox.Show("Fehler beim Transformieren!", "Transformationsfehler");					 
			else if(p.ExitCode==3)
				MessageBox.Show("Unbekanntes Eingabeformat!", "Transformationsfehler");
			else
			{
				this.DisplayData(pfad);
			}	
		}

		/// <summary>
		/// Methode zum Schließen einer XML Datei
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void Close_Click(object sender, System.EventArgs e)
		{
			VariableList.Items.Clear();
			ZFBox.Text="";
			SolverBox.Text="";
			BoxMessage.Text="";
			theData.ClearData();
			SystemGrid.ColumnHeadersVisible=false;
			FullInfoGrid.ColumnHeadersVisible=false;
			this.Text="ClearView";
		}
		/// <summary>
		/// Anzeige des Info Fensters
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void InfoMenu_Click(object sender, System.EventArgs e)
		{
			String Info="ClearView Version 1.0\nViewer: Markus Wiezorrek\n";
			Info=Info + "Transformer und Format: Uwe Raetz\n";
			Info=Info + "Copyright 2004";
			MessageBox.Show(Info, "Information");		
		}
		/// <summary>
		/// Anzeige der Hilfedatei
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void HelpMenu_Click(object sender, System.EventArgs e)
		{
			String HelpFile="";
			try
			{
				HelpFile=Directory.GetCurrentDirectory();
				HelpFile=HelpFile + "\\hilfe.html";
				Process.Start("IExplore.exe",HelpFile);
			}
			catch(Exception f) 
			{
				MessageBox.Show("Fehler beim Anzeigen der Hilfe: " +f.Message, "keine Hilfe");
				return;
			}
			return;		
		}

		private void ConfigMenu_Click(object sender, System.EventArgs e)
		{
			EinstellungForm aDialog= new EinstellungForm();
			aDialog.setAccessIni(theAccessIni);
			aDialog.Show();		
		}

		
	}
}



		

	

