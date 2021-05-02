using System;
using System.IO;
using System.Data;
using System.Drawing;
using System.Resources;
using System.Reflection;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Drawing.Drawing2D;
using System.Drawing.Printing;
using System.Xml.Serialization;
using Crownwood.Magic.Menus;
using Crownwood.Magic.Win32;
using Crownwood.Magic.Common;
using Crownwood.Magic.Controls;
using Crownwood.Magic.Docking;
using Crownwood.Magic.Forms;

namespace Maschinenbelegungsplanung
{
	public class Form1 : System.Windows.Forms.Form
	{
		private System.ComponentModel.IContainer components;
		private int _countGroup1 = 2;
		private int _countGroup2 = 2;
		private int _image = -1;
		private Crownwood.Magic.Menus.MenuControl menuControl1;
		private System.Windows.Forms.ImageList mainTabs;
		private System.Windows.Forms.ImageList groupTabs;
		private ImageList _images = null;
		private StatusBar _status = null;
		private StatusBarPanel _statusBarPanel = null;
		private Crownwood.Magic.Menus.MenuControl _topMenu = null;

		private Crownwood.Magic.Controls.TabControl tabControl1;
		private Crownwood.Magic.Controls.TabPage tabPage1;
		private Crownwood.Magic.Controls.TabPage tabPage2;
		private Crownwood.Magic.Controls.TabbedGroups tabbedGroups1;
		private Crownwood.Magic.Controls.TabbedGroups tabbedGroups2;

		private Crownwood.Magic.Controls.TabPage helpPage = null;
		private Crownwood.Magic.Controls.TabPage handbookPage = null;
		private Crownwood.Magic.Controls.TabPage resultPageOneMachine = null;
        private Crownwood.Magic.Controls.TabPage resultPageMoreMachine = null;


		private System.Windows.Forms.OpenFileDialog openFileDialog1;
		private System.Windows.Forms.SaveFileDialog saveFileDialog1;
		private System.Windows.Forms.PrintPreviewDialog printPreviewDialog1;
		private System.Windows.Forms.PrintDialog printDialog1;
		private System.Windows.Forms.PageSetupDialog pageSetupDialog1;
		

		private string fileName = null;
		public static string solverPath = "L:\\BESF\\SOLVER\\LP_SOLVE\\EXEC";
		public static string workingDir = "C:\\Temp";
		private System.Windows.Forms.ImageList imageList1;
		private PrintDocument printDocument =  null;
		private DataGridPrinter dataGridPrinter = null;
		private AusgabeDaten calculatedResults = null;

		static public PrintableAuftraegeDataGrid printableAuftraegeDataGrid;
		static public PrintablePeriodenDataGrid printablePeriodenDataGrid;
		static public GetPath getPath;
			
		private eingabeDaten eingabeDatenParam;
		private printableDataGridToEingabeDaten pDTE;

		public Form1()
		{
			InitializeComponent();
			SetupMenus();
			SetupStatusBar();
			CreateInitialPagesGroup1(1);
			CreateInitialPagesGroup2(1);
		}


		#region menue
		protected void SetupMenus()
		{
			// Create the MenuControl
			_topMenu = new Crownwood.Magic.Menus.MenuControl();

			// We want the control to handle the MDI pendant
			_topMenu.MdiContainer = this;

			// Create the top level Menu
			MenuCommand fileMenue = new MenuCommand("&Datei");
			MenuCommand viewMenue = new MenuCommand("&Ausgabeoptionen");
			MenuCommand calculateMenue = new MenuCommand("&Berechnen");
			MenuCommand preferencesMenue = new MenuCommand("&Einstellungen");
			MenuCommand helpMenue = new MenuCommand("&Hilfe");
						
			_topMenu.MenuCommands.AddRange(new MenuCommand[]{fileMenue, viewMenue, calculateMenue, preferencesMenue, helpMenue});

			// Create the submenus
			CreateFileMenue(fileMenue);
			CreateViewMenu(viewMenue);
			CreateCalculateMenu(calculateMenue);
			CreatePreferencesMenu(preferencesMenue);
			CreateHelpMenus(helpMenue);

			// Add to the display
			_topMenu.Dock = DockStyle.Top;
			Controls.Add(_topMenu);

		}

		protected void CreateFileMenue(MenuCommand mc)
		{
			// Create menu commands
			MenuCommand fileNew = new MenuCommand("&Neu", new EventHandler( onFileNewClick ));
			fileNew.Shortcut = Shortcut.CtrlN;

			MenuCommand fileOpen = new MenuCommand("Ö&ffnen", new EventHandler( onFileOpenClick ));
			fileOpen.Shortcut = Shortcut.CtrlO;

			MenuCommand fileSplitterOne = new MenuCommand("-");

			MenuCommand fileSave = new MenuCommand("&Speichern", new EventHandler( onFileSaveClick ));
			fileSave.Shortcut = Shortcut.CtrlS;
						
			MenuCommand fileSaveAs = new MenuCommand("Speichern &unter...", new EventHandler(onFileSaveAsClick ));
			MenuCommand fileSplitterTwo = new MenuCommand("-");

			MenuCommand filePrintSetup = new MenuCommand("D&rucker einrichten", new EventHandler(onFilePrintSetupClick ));
			MenuCommand filePrintPreview = new MenuCommand("Druck&vorschau", new EventHandler( onFilePrintPreviewClick ));
			MenuCommand filePrint = new MenuCommand("&Drucken", new EventHandler( onFilePrintClick ));
			
			filePrintSetup.Enabled = false;
			filePrintPreview.Enabled = false;
			filePrint.Enabled = false;
			filePrint.Shortcut = Shortcut.CtrlD;


			MenuCommand fileSplitterThree = new MenuCommand("-");
			MenuCommand fileExit = new MenuCommand("B&eenden", new EventHandler(OnExit));
			fileExit.Shortcut = Shortcut.AltF4;

			// Setup event handlers
			mc.MenuCommands.AddRange(new MenuCommand[]{fileNew, fileOpen, fileSplitterOne, fileSave, fileSaveAs, fileSplitterTwo,
														  filePrintSetup, filePrintPreview, filePrint, fileSplitterThree, fileExit});
						
			mc.MenuCommands.ExtraText = "Datei";
			mc.MenuCommands.ExtraTextColor = Color.White;
			mc.MenuCommands.ExtraBackColor = Color.SlateGray;
			mc.MenuCommands.ExtraFont = new Font("Times New Roman", 8f, FontStyle.Bold | FontStyle.Italic);
		}
		protected void CreateViewMenu(MenuCommand mc)
		{
			// Create menu commands
			MenuCommand viewNummeric = new MenuCommand("&Numerisch", new EventHandler( onViewNummericClicked ));
			viewNummeric.Checked = true;
			MenuCommand viewSplitterOne = new MenuCommand("-");
			MenuCommand viewGraphical = new MenuCommand("&Graphisch");
			viewGraphical.Enabled = false;


			mc.MenuCommands.AddRange(new MenuCommand[]{viewNummeric, viewSplitterOne, viewGraphical});
						
			mc.MenuCommands.ExtraText = "Ausgabeoptionen";
			mc.MenuCommands.ExtraTextColor = Color.White;
			mc.MenuCommands.ExtraBackColor = Color.SlateGray;
			mc.MenuCommands.ExtraFont = new Font("Times New Roman", 8f, FontStyle.Bold | FontStyle.Italic);            

					
		}

		protected void CreateCalculateMenu(MenuCommand mc)
		{
			// Create menu commands
			MenuCommand calculate = new MenuCommand("B&erechnen", new EventHandler( onCalculateClicked ));
			calculate.Shortcut = Shortcut.F5;
			mc.MenuCommands.AddRange(new MenuCommand[]{calculate});
		}

		protected void CreatePreferencesMenu(MenuCommand mc)
		{
			// Create menu commands
			MenuCommand prefFolder = new MenuCommand("Arbeitsverzeichnis", new EventHandler( onSolutionPathChanged ));
			MenuCommand prefSolver = new MenuCommand("Solverpfad", new EventHandler( onSolverPathChanged ));
			//MenuCommand prefFunction = new MenuCommand("Zielfunktion");

			// Setup event handlers
			mc.MenuCommands.AddRange(new MenuCommand[]{ prefFolder, prefSolver});
						
			mc.MenuCommands.ExtraText = "Prefs.";
			mc.MenuCommands.ExtraTextColor = Color.White;
			mc.MenuCommands.ExtraBackColor = Color.SlateGray;
			mc.MenuCommands.ExtraFont = new Font("Times New Roman", 8f, FontStyle.Bold | FontStyle.Italic);            

					
		}

		protected void CreateHelpMenus(MenuCommand mc)
		{
			// Create menu commands
			MenuCommand helpHandbook = new MenuCommand("Handbuch",new EventHandler( OnHandbookInfo ));
			//helpHandbook = new MenuCommand("Handbuch",new EventHandler( OnRemovePage ));
			
			helpHandbook.Shortcut = Shortcut.CtrlH;
			MenuCommand helpSplitterOne = new MenuCommand("-");
			MenuCommand helpInfo = new MenuCommand("Info", new EventHandler( OnHelpInfo ));
			helpInfo.Shortcut = Shortcut.CtrlF1;

			// Setup event handlers
			mc.MenuCommands.AddRange(new MenuCommand[]{helpHandbook, helpSplitterOne, helpInfo});
						
			mc.MenuCommands.ExtraText = "Hilfe";
			mc.MenuCommands.ExtraTextColor = Color.White;
			mc.MenuCommands.ExtraBackColor = Color.SlateGray;
			mc.MenuCommands.ExtraFont = new Font("Times New Roman", 8f, FontStyle.Bold | FontStyle.Italic);            

					
		}

		#endregion

		#region statusbar
		protected void SetupStatusBar()
		{
			// Create and setup the StatusBar object
			_status = new StatusBar();
			_status.Dock = DockStyle.Bottom;
			_status.ShowPanels = true;

			// Create and setup a single panel for the StatusBar
			_statusBarPanel = new StatusBarPanel();
			_statusBarPanel.AutoSize = StatusBarPanelAutoSize.Spring;
			_status.Panels.Add(_statusBarPanel);

			Controls.Add(_status);
		}

		public void SetStatusBarText(string text)
		{
			_statusBarPanel.Text = text;
		}

		#endregion

		#region images, style
		public ImageList Images
		{
			get { return _images; }
		}

		public VisualStyle Style
		{
			get { return _topMenu.Style; }
		}
		#endregion 

		#region menue events
		protected void onFileNewClick( object sender, EventArgs e)
		{
			// Berechnung für eine Maschine
			if (tabControl1.SelectedIndex == 0)
			{
				CreateInitialPagesGroup1WithNumberedTabs( this._countGroup1++ );
				this.tabPage1.Visible = true;
				SetStatusBarText( "Neu" );
			}
			else
			{   // Berechnung für mehrere Maschinen
				if (tabControl1.SelectedIndex == 1)
				{
					CreateInitialPagesGroup2(this._countGroup2++ );
					this.tabPage2.Visible = true;
					SetStatusBarText( "Neu" );
				}
			}
		}

		protected void onSolutionPathChanged( object sender, EventArgs e )
		{
			PreferencesForm aPreferencesForm = new PreferencesForm( " Bitte geben Sie den Arbeitsverzeichnis ein: \n\n( Bspl: \" C:\\Temp \" )  ", workingDir , "workingPath" );
			aPreferencesForm.Show( );
			
		}

		protected void onSolverPathChanged( object sender, EventArgs e )
		{
			PreferencesForm aPreferencesForm = new PreferencesForm( " Bitte geben Sie den Solverpfad ein: \n\n( Bspl: \" L:\\BESF\\SOLVER\\LP_SOLVE\\EXEC \" ) ", solverPath , "solverPath" );
			aPreferencesForm.Show( );
		}

		protected void onFileOpenClick( object sender, EventArgs e)
		{
			DialogResult result;
			int anzahlMaschinen = 2;
			eingabeDaten loadDaten;

			openFileDialog1.InitialDirectory = workingDir;
			openFileDialog1.Filter = "XML-Dateien ( *.xml )|*.xml";
			result = openFileDialog1.ShowDialog();
			fileName = openFileDialog1.FileName;
			if( result == DialogResult.OK )
			{			
				// Berechnung für eine Maschine
				if (tabControl1.SelectedIndex == 0)
				{
					SetStatusBarText( fileName );
					loadDaten = new eingabeDaten();
			
					System.Xml.Serialization.XmlSerializer serializer = new System.Xml.Serialization.XmlSerializer( typeof( eingabeDaten ) );
					System.IO.StreamReader fileStream = new System.IO.StreamReader( new System.IO.FileStream( fileName, System.IO.FileMode.Open));
					loadDaten = (eingabeDaten) serializer.Deserialize( fileStream );
					fileStream.Close( );
					CreateInitialPagesGroup1WithNumberedTabsAndLoadedData( this._countGroup1++, loadDaten );
				}
				else
				{   // Berechnung für mehrere Maschinen
					if (tabControl1.SelectedIndex == 1)
					{
						SetStatusBarText( fileName );
						loadDaten = new eingabeDaten(anzahlMaschinen);
			
						System.Xml.Serialization.XmlSerializer serializer = new System.Xml.Serialization.XmlSerializer( typeof( eingabeDaten ) );
						System.IO.StreamReader fileStream = new System.IO.StreamReader( new System.IO.FileStream( fileName, System.IO.FileMode.Open));
						loadDaten = (eingabeDaten) serializer.Deserialize( fileStream );
						fileStream.Close( );
						CreateInitialPagesGroup2WithNumberedTabsAndLoadedData( this._countGroup2++, loadDaten );
					}
				}				

				System.DateTime myTime = System.DateTime.Now;
				SetStatusBarText( fileName +" geladen ( " +myTime.ToString() +" ). " );								
			}

		}

		protected void onFileSaveAsClick( object sender, EventArgs e)
		{
			DialogResult result;

			SaveFileDialog fileSaveDialog1 = new SaveFileDialog( );
			fileSaveDialog1.InitialDirectory = workingDir;
			fileSaveDialog1.Filter = "XML-Dateien ( *.xml )|*.xml";
			result = fileSaveDialog1.ShowDialog( );
			fileName = fileSaveDialog1.FileName;

			int anzahlMaschinen = 2;

			if( result == DialogResult.OK )
			{
				// Berechnung für eine Maschine
				if (tabControl1.SelectedIndex == 0)
				{
					printableDataGridToEingabeDaten pDTE = new printableDataGridToEingabeDaten( printableAuftraegeDataGrid( ), printablePeriodenDataGrid( ) );
					eingabeDaten saveData = pDTE.returnEingabeDaten(1);

					System.IO.FileStream myFileStream = new System.IO.FileStream(fileName, System.IO.FileMode.Create);
					System.Xml.XmlTextWriter myXmlWriter = 	new System.Xml.XmlTextWriter(myFileStream, System.Text.Encoding.Unicode);

					XmlSerializer mySerializer = new XmlSerializer( typeof( eingabeDaten ) );
					mySerializer.Serialize( myXmlWriter, saveData );
					myXmlWriter.Close();

					System.DateTime myTime = System.DateTime.Now;
					SetStatusBarText( fileName +" gespeichert ( " +myTime.ToString() +" ). " );
				}
				else
				{   // Berechnung für mehrere Maschinen
					if (tabControl1.SelectedIndex == 1)
					{
						printableDataGridToEingabeDaten pDTE = new printableDataGridToEingabeDaten( printableAuftraegeDataGrid( ), printablePeriodenDataGrid( ) );
						eingabeDaten saveData = pDTE.returnEingabeDaten(anzahlMaschinen);

						System.IO.FileStream myFileStream = new System.IO.FileStream(fileName, System.IO.FileMode.Create);
						System.Xml.XmlTextWriter myXmlWriter = 	new System.Xml.XmlTextWriter(myFileStream, System.Text.Encoding.Unicode);

						XmlSerializer mySerializer = new XmlSerializer( typeof( eingabeDaten ) );
						mySerializer.Serialize( myXmlWriter, saveData );
						myXmlWriter.Close();

						System.DateTime myTime = System.DateTime.Now;
						SetStatusBarText( fileName +" gespeichert ( " +myTime.ToString() +" ). " );
					}
				}

			}

		}

		protected void onFileSaveClick( object sender, EventArgs e)
		{
			int anzahlMaschinen = 2;

			if( fileName == null) 
				onFileSaveAsClick( sender, e );
			else 
			{
				// Berechnung für eine Maschine
				if (tabControl1.SelectedIndex == 0)
				{
					printableDataGridToEingabeDaten pDTE = new printableDataGridToEingabeDaten( printableAuftraegeDataGrid( ), printablePeriodenDataGrid( ) );
					eingabeDaten saveData = pDTE.returnEingabeDaten(1);

					System.IO.FileStream myFileStream = new System.IO.FileStream(fileName, System.IO.FileMode.Create);
					System.Xml.XmlTextWriter myXmlWriter = 	new System.Xml.XmlTextWriter(myFileStream, System.Text.Encoding.Unicode);

					XmlSerializer mySerializer = new XmlSerializer( typeof( eingabeDaten ) );
					mySerializer.Serialize( myXmlWriter, saveData );

					myXmlWriter.Close();
					System.DateTime myTime = System.DateTime.Now;
					SetStatusBarText( fileName +" gespeichert ( " +myTime.ToString() +" ). " );		
				}
				else
				{   // Berechnung für mehrere Maschinen
					if (tabControl1.SelectedIndex == 1)
					{
						printableDataGridToEingabeDaten pDTE = new printableDataGridToEingabeDaten( printableAuftraegeDataGrid( ), printablePeriodenDataGrid( ) );
						eingabeDaten saveData = pDTE.returnEingabeDaten(anzahlMaschinen);

						System.IO.FileStream myFileStream = new System.IO.FileStream(fileName, System.IO.FileMode.Create);
						System.Xml.XmlTextWriter myXmlWriter = 	new System.Xml.XmlTextWriter(myFileStream, System.Text.Encoding.Unicode);

						XmlSerializer mySerializer = new XmlSerializer( typeof( eingabeDaten ) );
						mySerializer.Serialize( myXmlWriter, saveData );

						myXmlWriter.Close();
						System.DateTime myTime = System.DateTime.Now;
						SetStatusBarText( fileName +" gespeichert ( " +myTime.ToString() +" ). " );	
					}
				}
			}
		}

		protected void onFilePrintSetupClick( object sender, EventArgs e)
		{
			pageSetupDialog1.PageSettings = new PageSettings( );
			pageSetupDialog1.PrinterSettings = new PrinterSettings( );
			pageSetupDialog1.AllowOrientation = true;
			pageSetupDialog1.AllowMargins = true;
			pageSetupDialog1.Document = printDocument;
			pageSetupDialog1.ShowDialog( this );
		}

		protected void onFilePrintPreviewClick( object sender, EventArgs e)
		{
			printPreviewDialog1.Document = printDocument;
			printPreviewDialog1.PrintPreviewControl.Zoom = 1.0;
			printPreviewDialog1.WindowState = FormWindowState.Maximized;
			printPreviewDialog1.ShowDialog( this );
		}

		protected void onFilePrintClick( object sender, EventArgs e)
		{
			printDialog1.PrinterSettings = pageSetupDialog1.PrinterSettings;
			printDialog1.Document = printDocument;
			if( printDialog1.ShowDialog() == DialogResult.OK )
				printDocument.Print( );
		}

		private void printDocument_PrintPage( object sender, PrintPageEventArgs e )
		{
			TabbedGroups activeGroup = null;
			// Druck für eine Maschine
			if (tabControl1.SelectedIndex == 0) 
				activeGroup = tabbedGroups1; 
			else 
			{	// Druck für mehrere Maschinen
				if (tabControl1.SelectedIndex == 1) 
					activeGroup = tabbedGroups2; 
			}

			dataGridPrinter = new DataGridPrinter( printableAuftraegeDataGrid(), printDocument );
			dataGridPrinter.PageCounter++;
			Graphics g = e.Graphics;
			bool more = dataGridPrinter.Draw( g, e );
			if ( more ) e.HasMorePages = true;
			else dataGridPrinter.ResetCounters( );
		}

		protected void OnExit(object sender, EventArgs e)
		{
			Close();
		}

		protected void onViewNummericClicked( object sender, EventArgs e)
		{
			// what??
		}

		protected void onCalculateClicked( object sender, EventArgs e)
		{
			int anzahlMaschinen = 2; 

			// Berechnung für eine Maschine
			if (tabControl1.SelectedIndex == 0)
			{
				pDTE = new printableDataGridToEingabeDaten( printableAuftraegeDataGrid( ), printablePeriodenDataGrid( ) );
				eingabeDaten data = pDTE.returnEingabeDaten(1);

				if( data.auftragsListe[0] != null )
				{
					ISolver theSolver = new OneMachineSolver( data, "LP-Solve", solverPath, workingDir );
					theSolver.createMPS( );
					theSolver.solve( );
					calculatedResults = theSolver.readResults( );
					showOneSolverResults( );
					tabbedGroups1.ActiveLeaf.TabPages["Ergebnis"].Selected = true;
				}
				else
				{
					MessageBox.Show( "Fehler! Sie haben keine Daten eingegeben" );
				}
			}
			else
			{   // Berechnung für mehrere Maschinen
				if (tabControl1.SelectedIndex == 1)
				{
					pDTE = new printableDataGridToEingabeDaten( printableAuftraegeDataGrid( ), printablePeriodenDataGrid( ) );
					eingabeDaten data = pDTE.returnEingabeDaten(anzahlMaschinen);

					if( data.auftragsListe[0] != null )
					{
						ISolver theSolver = new MultipleMachinesSolver( data, "LP-Solve", solverPath, workingDir, anzahlMaschinen );
						theSolver.createMPS( );
						theSolver.solve( );
						calculatedResults = theSolver.readResults( );
						showMultipleSolverResults( );
						tabbedGroups2.ActiveLeaf.TabPages["Ergebnis"].Selected = true;
					}
					else
					{
						MessageBox.Show( "Fehler! Sie haben keine Daten eingegeben" );
					}	
				}
			}

		}

		protected void OnHelpInfo( object sender, EventArgs e)
		{
			if ( helpPage == null )
			{
				Panel helpPanel = new Panel( );
				helpPanel.BackColor = System.Drawing.SystemColors.Control;
				helpPanel.Font = new System.Drawing.Font("Verdana", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				
				Label helpLabel = new Label();
				helpLabel.Size = new System.Drawing.Size(800, 600);
				helpLabel.Font = new System.Drawing.Font("Verdana", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				helpLabel.Text = "\n Implementiert von:\n\n Dirk Anderhuber\n Mike Fröhlich\n Sven Bohnenstengel";
				helpLabel.Text += "\n\n im Rahmen der Vorlesung\n Anwendungen der betrieblichen Systemforschung";
				helpLabel.Text += "\n\n unter Anleitung von Herrn Prof. Dr. M. Grütz";
				helpLabel.TextAlign = System.Drawing.ContentAlignment.TopCenter;

				helpPanel.Controls.AddRange(new System.Windows.Forms.Control[] { helpLabel });

				helpPage = NewNamedInputTabPage( "Info", helpPanel );
				helpPage.Name = "helpPage";
				helpPage.ImageList = imageList1;
				helpPage.ImageIndex = 0;
				helpPage.Capture = true;
				helpPage.BackColor = System.Drawing.SystemColors.Control;


				if (tabControl1.SelectedIndex == 0)
				{
					if (tabbedGroups1.ActiveLeaf != null)
					{
						tabbedGroups1.ActiveLeaf.TabPages.Add( helpPage );
						tabbedGroups1.ActiveLeaf.TabPages["Info"].Selected = true;
					}
				}
				else
				{
					if (tabControl1.SelectedIndex == 1)
					{
						if (tabbedGroups2.ActiveLeaf != null)
						{
							tabbedGroups2.ActiveLeaf.TabPages.Add( helpPage );
							tabbedGroups2.ActiveLeaf.TabPages["Info"].Selected = true;
						}
					}
				}
				Form1_Resize(this, new EventArgs());
				helpPage.Dispose();
			}
			
		}

		protected void OnHandbookInfo( object sender, EventArgs e)
		{
			if ( handbookPage == null )
			{
				Panel handbookPanel = new Panel( );
				handbookPanel.BackColor = System.Drawing.SystemColors.Control;
				handbookPanel.Font = new System.Drawing.Font("Verdana", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				
				Label handbookLabel = new Label();
				handbookLabel.Size = this.ClientSize;
				handbookLabel.Font = new System.Drawing.Font("Verdana", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				handbookLabel.Text = "\n Handbuch...";
				handbookLabel.TextAlign = System.Drawing.ContentAlignment.TopCenter;

				handbookPanel.Controls.AddRange(new System.Windows.Forms.Control[] { handbookLabel });

				handbookPage = NewNamedInputTabPage( "Handbuch", new AboutForm( this.ClientSize));
				handbookPage.Name = "handbook";
				handbookPage.ImageList = imageList1;
				handbookPage.ImageIndex = 4;
				handbookPage.Capture = true;
				handbookPage.BackColor = System.Drawing.SystemColors.Control;


				if (tabControl1.SelectedIndex == 0)
				{
					if (tabbedGroups1.ActiveLeaf != null)
					{
						tabbedGroups1.ActiveLeaf.TabPages.Add( handbookPage );
						tabbedGroups1.ActiveLeaf.TabPages["Handbuch"].Selected = true;
					}
				}
				else
				{
					if (tabControl1.SelectedIndex == 1)
					{
						if (tabbedGroups2.ActiveLeaf != null)
						{
							tabbedGroups2.ActiveLeaf.TabPages.Add( handbookPage );
							tabbedGroups2.ActiveLeaf.TabPages["Handbuch"].Selected = true;
						}
					}
				}

				Form1_Resize(this, new EventArgs());
				handbookPage.Dispose();
			}
			

		}
		#endregion

		#region Dispose(...)
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
					components.Dispose();

			}
			base.Dispose( disposing );
		}
		#endregion

		#region Tab fuer eine Maschine
		protected void CreateInitialPagesGroup1(int numberForTabbs)
		{        
			TabGroupLeaf tgl = tabbedGroups1.RootSequence[0] as TabGroupLeaf;  // inputPanel
			Crownwood.Magic.Controls.TabPage tabPage1 = new Crownwood.Magic.Controls.TabPage("Eingabe " +numberForTabbs, new InputForm(), NextImage());
			tabPage1.ImageList = imageList1;
			tabPage1.ImageIndex = 1;
			tgl.TabPages.Add(tabPage1);
			tgl.TabPages["Eingabe "+numberForTabbs].Selected = true;
			/*
			calculatedResults = new AusgabeDaten( );
			calculate();
			*/
			Form1_Resize(this, new EventArgs());
		}

		protected void CreateInitialPagesGroup1WithNumberedTabs( int numberForTabbs)
		{        
			int       selectedSolver = 3;
			eingabeDatenParam = new eingabeDaten( );

			TabGroupLeaf tgl = tabbedGroups1.RootSequence[0] as TabGroupLeaf;                           // inputPanel
			Crownwood.Magic.Controls.TabPage tabPage1 = new Crownwood.Magic.Controls.TabPage("Eingabe " +numberForTabbs , new InputForm( selectedSolver, eingabeDatenParam ), NextImage());
			tabPage1.Name = "Eingabe" +numberForTabbs;
			tabPage1.ImageList = imageList1;
			tabPage1.ImageIndex = 1;
			tgl.TabPages.Add(tabPage1);
			tgl.TabPages["Eingabe "+numberForTabbs].Selected = true;
		}

		protected void CreateInitialPagesGroup1WithNumberedTabsAndLoadedData( int numberForTabbs, eingabeDaten eingabeDatenParam )
		{        
			int selectedSolver = 3;

			TabGroupLeaf tgl = tabbedGroups1.RootSequence[0] as TabGroupLeaf;                           // inputPanel
			Crownwood.Magic.Controls.TabPage tabPage1 = new Crownwood.Magic.Controls.TabPage("Eingabe " +numberForTabbs , new InputForm( selectedSolver, eingabeDatenParam ), NextImage());
			tabPage1.Name = "Eingabe" +numberForTabbs;
			tabPage1.ImageList = imageList1;
			tabPage1.ImageIndex = 1;
			tgl.TabPages.Add(tabPage1);
			tgl.TabPages["Eingabe "+numberForTabbs].Selected = true;
		}
		#endregion

		#region Tab fuer mehrere Maschinen
		protected void CreateInitialPagesGroup2(int numberForTabbs)
		{       
			int       selectedNumberOfMaschines = 2;
			TabGroupLeaf tgl = tabbedGroups2.RootSequence[0] as TabGroupLeaf;							// inputPanel
			Crownwood.Magic.Controls.TabPage tabPage1 = new Crownwood.Magic.Controls.TabPage("Eingabe " +numberForTabbs, new InputForm(selectedNumberOfMaschines), NextImage());
			tabPage1.ImageList = imageList1;
			tabPage1.ImageIndex = 1;
			tgl.TabPages.Add(tabPage1);
			tgl.TabPages["Eingabe "+numberForTabbs].Selected = true;
			Form1_Resize(this, new EventArgs());
		}

		protected void CreateInitialPagesGroup2WithNumberedTabs( int numberForTabbs)
		{        
			int selectedSolver = 3;
			int selectedNumberOfMaschines = 2;
			eingabeDatenParam = new eingabeDaten( );

			TabGroupLeaf tgl = tabbedGroups2.RootSequence[0] as TabGroupLeaf;                           // inputPanel
			Crownwood.Magic.Controls.TabPage tabPage1 = new Crownwood.Magic.Controls.TabPage("Eingabe " +numberForTabbs , new InputForm( selectedSolver, eingabeDatenParam, selectedNumberOfMaschines ), NextImage());
			tabPage1.Name = "Eingabe" +numberForTabbs;
			tabPage1.ImageList = imageList1;
			tabPage1.ImageIndex = 1;
			tgl.TabPages.Add(tabPage1);
			tgl.TabPages["Eingabe "+numberForTabbs].Selected = true;
		}

		protected void CreateInitialPagesGroup2WithNumberedTabsAndLoadedData( int numberForTabbs, eingabeDaten eingabeDatenParam )
		{        
			int selectedSolver = 3;
			int selectedNumberOfMaschines = 2;
			TabGroupLeaf tgl = tabbedGroups2.RootSequence[0] as TabGroupLeaf;                           // inputPanel
			Crownwood.Magic.Controls.TabPage tabPage1 = new Crownwood.Magic.Controls.TabPage("Eingabe " +numberForTabbs , new InputForm( selectedSolver, eingabeDatenParam, selectedNumberOfMaschines ), NextImage());
			tabPage1.Name = "Eingabe" +numberForTabbs;
			tabPage1.ImageList = imageList1;
			tabPage1.ImageIndex = 1;
			tgl.TabPages.Add(tabPage1);
			tgl.TabPages["Eingabe "+numberForTabbs].Selected = true;
		}
		#endregion
		
		#region NextImage()
		protected int NextImage()
		{
			_image = ++_image % 8;
			return _image;
		}
		#endregion
        
		#region NewTabPage()
		protected Crownwood.Magic.Controls.TabPage NewTabPage()
		{
			return new Crownwood.Magic.Controls.TabPage("Page" + _countGroup1++, new RichTextBox(), NextImage());       
		}
		#endregion

		#region NewNamedTabPage(...)
		protected Crownwood.Magic.Controls.TabPage NewNamedTabPage( string pageName)
		{
			return new Crownwood.Magic.Controls.TabPage( pageName, new Panel(), NextImage());       
		}
		#endregion
		
		#region NewNamedInputTabPage(...)
		protected Crownwood.Magic.Controls.TabPage NewNamedInputTabPage( string pageName, System.Windows.Forms.Control controlObject)
		{
			return new Crownwood.Magic.Controls.TabPage( pageName, controlObject ,NextImage());       
		}
		#endregion

		#region OnPages(...)
		protected void OnPages(MenuCommand pages)
		{
			Crownwood.Magic.Controls.TabControl tc = null;

			// Find the active tab control in the selected group 
			if (tabControl1.SelectedIndex == 0)
			{
				if (tabbedGroups1.ActiveLeaf != null)
					tc = tabbedGroups1.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
			}
			else
			{
				if (tabControl1.SelectedIndex == 1)
				{
					if (tabbedGroups2.ActiveLeaf != null)
						tc = tabbedGroups2.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
				}
			}
            
			// Did we find a current tab control?
			if ((tc != null) && (tc.SelectedTab != null))
				pages.MenuCommands[1].Enabled = true;
			else
				pages.MenuCommands[1].Enabled = false;
		}
		#endregion

		#region OnAddPage(...)
		protected void OnAddPage(object sender, EventArgs e)
		{
			if (tabControl1.SelectedIndex == 0)
			{
				if (tabbedGroups1.ActiveLeaf != null)
					tabbedGroups1.ActiveLeaf.TabPages.Add(NewTabPage());
			}
			else
			{
				if (tabControl1.SelectedIndex == 1)
				{
					if (tabbedGroups2.ActiveLeaf != null)
						tabbedGroups2.ActiveLeaf.TabPages.Add(NewTabPage());
				}
			}
		}
		#endregion

		#region OnRemovePage(...)
		protected void OnRemovePage(object sender, EventArgs e)
		{
			Crownwood.Magic.Controls.TabControl tc = null;

			// Find the active tab control in the selected group 
			if (tabControl1.SelectedIndex == 0)
			{
				if (tabbedGroups1.ActiveLeaf != null)
					tc = tabbedGroups1.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
			}
			else
			{
				if (tabControl1.SelectedIndex == 1)
				{
					if (tabbedGroups2.ActiveLeaf != null)
						tc = tabbedGroups2.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
				}
			}
            
			// Did we find a current tab control?
			if (tc != null)
			{
				// Does it have a selected tab?
				if (tc.SelectedTab != null)
				{
					// Remove the page
					tc.TabPages.Remove(tc.SelectedTab);
					handbookPage=null;
				}
			}
		}  
		#endregion
					
		#region InitializeComponent()
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(Form1));
			this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
			this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
			this.printPreviewDialog1 = new System.Windows.Forms.PrintPreviewDialog();
			this.printDialog1 = new System.Windows.Forms.PrintDialog();
			this.pageSetupDialog1 = new System.Windows.Forms.PageSetupDialog();
			this.menuControl1 = new Crownwood.Magic.Menus.MenuControl();
			this.tabControl1 = new Crownwood.Magic.Controls.TabControl();
			this.mainTabs = new System.Windows.Forms.ImageList(this.components);
			this.tabPage2 = new Crownwood.Magic.Controls.TabPage();
			this.tabbedGroups2 = new Crownwood.Magic.Controls.TabbedGroups();
			this.groupTabs = new System.Windows.Forms.ImageList(this.components);
			this.imageList1 = new System.Windows.Forms.ImageList(this.components);
			this.tabPage1 = new Crownwood.Magic.Controls.TabPage();
			this.tabbedGroups1 = new Crownwood.Magic.Controls.TabbedGroups();
			this.tabPage2.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.tabbedGroups2)).BeginInit();
			this.tabPage1.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.tabbedGroups1)).BeginInit();
			this.SuspendLayout();
			// 
			// printPreviewDialog1
			// 
			this.printPreviewDialog1.AutoScrollMargin = new System.Drawing.Size(0, 0);
			this.printPreviewDialog1.AutoScrollMinSize = new System.Drawing.Size(0, 0);
			this.printPreviewDialog1.ClientSize = new System.Drawing.Size(400, 300);
			this.printPreviewDialog1.Enabled = true;
			this.printPreviewDialog1.Icon = ((System.Drawing.Icon)(resources.GetObject("printPreviewDialog1.Icon")));
			this.printPreviewDialog1.Location = new System.Drawing.Point(378, 5);
			this.printPreviewDialog1.MaximumSize = new System.Drawing.Size(0, 0);
			this.printPreviewDialog1.Name = "printPreviewDialog1";
			this.printPreviewDialog1.Opacity = 1;
			this.printPreviewDialog1.TransparencyKey = System.Drawing.Color.Empty;
			this.printPreviewDialog1.Visible = false;
			// 
			// menuControl1
			// 
			this.menuControl1.AnimateStyle = Crownwood.Magic.Menus.Animation.System;
			this.menuControl1.AnimateTime = 100;
			this.menuControl1.Cursor = System.Windows.Forms.Cursors.Arrow;
			this.menuControl1.Direction = Crownwood.Magic.Common.Direction.Horizontal;
			this.menuControl1.Dock = System.Windows.Forms.DockStyle.Top;
			this.menuControl1.Font = new System.Drawing.Font("Tahoma", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
			this.menuControl1.HighlightTextColor = System.Drawing.SystemColors.MenuText;
			this.menuControl1.Name = "menuControl1";
			this.menuControl1.Size = new System.Drawing.Size(792, 25);
			this.menuControl1.Style = Crownwood.Magic.Common.VisualStyle.IDE;
			this.menuControl1.TabIndex = 0;
			this.menuControl1.TabStop = false;
			this.menuControl1.Text = "menuControl1";
			// 
			// tabControl1
			// 
			this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.tabControl1.ImageList = this.mainTabs;
			this.tabControl1.Location = new System.Drawing.Point(0, 25);
			this.tabControl1.Name = "tabControl1";
			this.tabControl1.SelectedIndex = 0;
			this.tabControl1.SelectedTab = this.tabPage1;
			this.tabControl1.Size = new System.Drawing.Size(792, 541);
			this.tabControl1.TabIndex = 2;
			this.tabControl1.TabPages.AddRange(new Crownwood.Magic.Controls.TabPage[] {
																						  this.tabPage1,
																						  this.tabPage2});
			// 
			// mainTabs
			// 
			this.mainTabs.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
			this.mainTabs.ImageSize = new System.Drawing.Size(16, 16);
			this.mainTabs.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// tabPage2
			// 
			this.tabPage2.Controls.AddRange(new System.Windows.Forms.Control[] {
																				   this.tabbedGroups2});
			this.tabPage2.ImageIndex = 7;
			this.tabPage2.ImageList = this.imageList1;
			this.tabPage2.Name = "tabPage2";
			this.tabPage2.Selected = false;
			this.tabPage2.Size = new System.Drawing.Size(792, 516);
			this.tabPage2.TabIndex = 1;
			this.tabPage2.Title = "Berechnung für mehrere Maschinen";
			// 
			// tabbedGroups2
			// 
			this.tabbedGroups2.ActiveLeaf = null;
			this.tabbedGroups2.AllowDrop = true;
			this.tabbedGroups2.AtLeastOneLeaf = true;
			this.tabbedGroups2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.tabbedGroups2.ImageList = this.groupTabs;
			this.tabbedGroups2.Name = "tabbedGroups2";
			this.tabbedGroups2.ProminentLeaf = null;
			this.tabbedGroups2.ResizeBarColor = System.Drawing.SystemColors.Control;
			this.tabbedGroups2.Size = new System.Drawing.Size(792, 516);
			this.tabbedGroups2.TabIndex = 0;
			this.tabbedGroups2.PageCloseRequest += new Crownwood.Magic.Controls.TabbedGroups.PageCloseRequestHandler(this.tabbedGroups2_PageCloseRequest);
			// 
			// groupTabs
			// 
			this.groupTabs.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
			this.groupTabs.ImageSize = new System.Drawing.Size(16, 16);
			this.groupTabs.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// imageList1
			// 
			this.imageList1.ColorDepth = System.Windows.Forms.ColorDepth.Depth8Bit;
			this.imageList1.ImageSize = new System.Drawing.Size(16, 16);
			this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// tabPage1
			// 
			this.tabPage1.Controls.AddRange(new System.Windows.Forms.Control[] {
																				   this.tabbedGroups1});
			this.tabPage1.ImageIndex = 0;
			this.tabPage1.Name = "tabPage1";
			this.tabPage1.Size = new System.Drawing.Size(792, 516);
			this.tabPage1.TabIndex = 0;
			this.tabPage1.Title = "Berechnung für eine Maschine";
			// 
			// tabbedGroups14
			// 
			this.tabbedGroups1.ActiveLeaf = null;
			this.tabbedGroups1.AllowDrop = true;
			this.tabbedGroups1.AtLeastOneLeaf = true;
			this.tabbedGroups1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.tabbedGroups1.ImageList = this.groupTabs;
			this.tabbedGroups1.Name = "tabbedGroups1";
			this.tabbedGroups1.ProminentLeaf = null;
			this.tabbedGroups1.ResizeBarColor = System.Drawing.SystemColors.Control;
			this.tabbedGroups1.Size = new System.Drawing.Size(792, 516);
			this.tabbedGroups1.TabIndex = 0;
			this.tabbedGroups1.PageCloseRequest += new Crownwood.Magic.Controls.TabbedGroups.PageCloseRequestHandler(this.tabbedGroups1_PageCloseRequest);
			// 
			// Form1
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(792, 566);
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.tabControl1,
																		  this.menuControl1});
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.MinimumSize = new System.Drawing.Size(800, 600);
			this.Name = "Form1";
			this.Text = "Maschinenbelegungsplanung";
			this.Resize += new System.EventHandler(this.Form1_Resize);
			this.tabPage2.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.tabbedGroups2)).EndInit();
			this.tabPage1.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.tabbedGroups1)).EndInit();
			this.ResumeLayout(false);
		}
		#endregion
					
		#region Main()	
		[STAThread]
		static void Main() 
		{
			Application.Run(new Form1());
		}
		#endregion

		//public void calculate( object sender, EventArgs e )

		#region showOneSolverResults()
		public void showOneSolverResults(  )
		{
			if ( resultPageOneMachine == null )
			{
				Panel resultPanel = new Panel( );
				resultPanel.BackColor = System.Drawing.SystemColors.Control;
				resultPanel.Font = new System.Drawing.Font("Verdana", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				
				Label resultLabel = new Label();
				resultLabel.Size = new System.Drawing.Size(500, 700);
				resultLabel.Font = new System.Drawing.Font("Verdana", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				resultLabel.Text = "\n se:";
				resultLabel.TextAlign = System.Drawing.ContentAlignment.TopCenter;

				resultPanel.Controls.AddRange(new System.Windows.Forms.Control[] { resultLabel });

				resultPageOneMachine = NewNamedInputTabPage( "Ergebnis", new ResultForm( this.calculatedResults ) );
				resultPageOneMachine.ImageList = imageList1;
				resultPageOneMachine.ImageIndex = 2;
				resultPageOneMachine.Capture = true;
				resultPageOneMachine.BackColor = System.Drawing.SystemColors.Control;


				if (tabControl1.SelectedIndex == 0)
				{
					if (tabbedGroups1.ActiveLeaf != null)
						tabbedGroups1.ActiveLeaf.TabPages.Add( resultPageOneMachine );
				}
				else
				{
					if (tabControl1.SelectedIndex == 1)
					{
						if (tabbedGroups2.ActiveLeaf != null)
							tabbedGroups2.ActiveLeaf.TabPages.Add( resultPageOneMachine );
					}
				}
				
			}
			resultPageOneMachine.Dispose();
		}
		#endregion

		#region showMultipleSolverResults()
		public void showMultipleSolverResults(  )
		{
			if ( resultPageMoreMachine == null )
			{
				Panel resultPanel = new Panel( );
				resultPanel.BackColor = System.Drawing.SystemColors.Control;
				resultPanel.Font = new System.Drawing.Font("Verdana", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				
				Label resultLabel = new Label();
				resultLabel.Size = new System.Drawing.Size(500, 700);
				resultLabel.Font = new System.Drawing.Font("Verdana", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.World);
				resultLabel.Text = "\n se:";
				resultLabel.TextAlign = System.Drawing.ContentAlignment.TopCenter;

				resultPanel.Controls.AddRange(new System.Windows.Forms.Control[] { resultLabel });

                resultPageMoreMachine = NewNamedInputTabPage("Ergebnis", new ResultForm(this.calculatedResults));
                resultPageMoreMachine.ImageList = imageList1;
                resultPageMoreMachine.ImageIndex = 2;
                resultPageMoreMachine.Capture = true;
                resultPageMoreMachine.BackColor = System.Drawing.SystemColors.Control;


				if (tabControl1.SelectedIndex == 0)
				{
					if (tabbedGroups1.ActiveLeaf != null)
						tabbedGroups1.ActiveLeaf.TabPages.Add( resultPageMoreMachine );
				}
				else
				{
					if (tabControl1.SelectedIndex == 1)
					{
						if (tabbedGroups2.ActiveLeaf != null)
							tabbedGroups2.ActiveLeaf.TabPages.Add( resultPageMoreMachine );
					}
				}
				
			}
            resultPageMoreMachine.Dispose();
		}
		#endregion

		#region tabbedGroups1_PageCloseRequest(..)
		private void tabbedGroups1_PageCloseRequest(Crownwood.Magic.Controls.TabbedGroups tg, Crownwood.Magic.Controls.TGCloseRequestEventArgs e)
		{
			Crownwood.Magic.Controls.TabControl tc = null;

			// Find the active tab control in the selected group 
			if (tabControl1.SelectedIndex == 0)
			{
				if (tabbedGroups1.ActiveLeaf != null)
					tc = tabbedGroups1.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
			}
			else
			{
				if (tabControl1.SelectedIndex == 1)
				{
					if (tabbedGroups2.ActiveLeaf != null)
						tc = tabbedGroups2.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
				}
			}
            
			// Did we find a current tab control?
			if (tc != null)
			{
				// Does it have a selected tab?
				if (tc.SelectedTab.Title == "Handbuch")
				{
					// Remove the page
					handbookPage=null;
				}
				// Does it have a selected tab?
				if (tc.SelectedTab.Title == "Info")
				{
					// Remove the page
					helpPage=null;
				}

				// Does it have a selected tab?
				if (tc.SelectedTab.Title == "Ergebnis")
				{
					// Remove the page
					resultPageOneMachine=null;
					pDTE = null;
				}

				if (tc.SelectedTab.Title.StartsWith("Eingabe"))
				{
					// Remove the page
					eingabeDatenParam = null;
				}
			}
		}
		#endregion

		#region tabbedGroups2_PageCloseRequest(...)
		private void tabbedGroups2_PageCloseRequest(Crownwood.Magic.Controls.TabbedGroups tg, Crownwood.Magic.Controls.TGCloseRequestEventArgs e)
		{
			Crownwood.Magic.Controls.TabControl tc = null;

			// Find the active tab control in the selected group 
			if (tabControl1.SelectedIndex == 0)
			{
				if (tabbedGroups1.ActiveLeaf != null)
					tc = tabbedGroups1.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
			}
			else
			{
				if (tabControl1.SelectedIndex == 1)
				{
					if (tabbedGroups2.ActiveLeaf != null)
						tc = tabbedGroups2.ActiveLeaf.GroupControl as Crownwood.Magic.Controls.TabControl;
				}
			}
            
			// Did we find a current tab control?
			if (tc != null)
			{
				// Does it have a selected tab?
				if (tc.SelectedTab.Title == "Handbuch")
				{
					// Remove the page
					handbookPage=null;
				}
				// Does it have a selected tab?
				if (tc.SelectedTab.Title == "Info")
				{
					// Remove the page
					helpPage=null;
				}

				// Does it have a selected tab?
				if (tc.SelectedTab.Title == "Ergebnis")
				{
					// Remove the page
					resultPageOneMachine=null;
					pDTE = null;
				}

				if (tc.SelectedTab.Title.StartsWith("Eingabe"))
				{
					// Remove the page
					eingabeDatenParam = null;
				}
			}		
		}
		#endregion

		#region Form1_Resize(...)
		private void Form1_Resize(object sender, System.EventArgs e)
		{
			if(tabControl1 != null)
			{
				tabControl1.ClientSize = this.ClientSize;
				tabControl1.Refresh();

			}
		}
		#endregion
	
	}

}
