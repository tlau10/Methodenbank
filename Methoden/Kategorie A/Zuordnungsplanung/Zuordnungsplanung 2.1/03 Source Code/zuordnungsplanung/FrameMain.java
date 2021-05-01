
//awt

package zuordnungsplanung;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/**
 * <p>Title: FrameMain</p>
 * <p>Description: This class is the main GUI, with access to all other GUIs and to ApplicationControll.</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: </p>
 * <p>Teilweise geändert im Jahr 2013 durch Benedikt Wölfle</p>
 * @author Patrick Badent, Bendikt Wölfle
 * @version 1.1
 */


public class FrameMain extends JFrame
{
  ApplicationControll applicationControll;
  int solverTyp=0;

  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuDateiNeu = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpInfo = new JMenuItem();
  JToolBar jToolBar = new JToolBar();
  JButton jButtonDateiNeu = new JButton();
  JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();

  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  ButtonGroup buttonGroup1 = new ButtonGroup();

  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable();
  JButton jButtonSolverStarten = new JButton();
  JMenuItem jMenuDateiExit = new JMenuItem();
  JMenuItem jMenuDateiOeffnen = new JMenuItem();
  JMenuItem jMenuDateiSpeichern = new JMenuItem();
  JMenuItem jMenuDateiSpeichernUnter = new JMenuItem();
  JMenuItem jMenuHelpHilfe = new JMenuItem();
  JMenu jMenuSolver = new JMenu();
  TableModel tableModel = new TableModel(Variables.startWithRows+1,Variables.startWithColumns+1,true);

  boolean jComboBox1Init = false;
  boolean jComboBox2Init = false;
  JRadioButton jRadioButton1 = new JRadioButton();
  JRadioButton jRadioButton2 = new JRadioButton();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JButton jButtonSolverLP = new JButton();
  JButton jButtonDateiSpeichern = new JButton();
  JButton jButtonSolverXP = new JButton();
  JButton jButtonSolverEnum = new JButton();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JButton jButtonRowInsertLast = new JButton();
  JButton jButtonColInsertLast = new JButton();
  JButton jButtonRowDelLast = new JButton();
  JButton jButtonColDelLast = new JButton();
  JButton jButtonDateiOeffnen = new JButton();
  JCheckBoxMenuItem jCheckBoxMenuSolverLp = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuSolverXa = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuSolverEnum = new JCheckBoxMenuItem();



  //Constructor
  public FrameMain(ApplicationControll applicationControll)
  {
    this.applicationControll = applicationControll;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      ExceptionDialog.showExceptionDialog(
           "Fehler  beim Programmstart.","Es konnten nicht alle Dateien geladen werden." + e.getMessage(), e);
    }
    newTableModel();
  }
	private byte[] resourceToByteArray(String resName) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		InputStream iresStream = getClass().getResourceAsStream(resName);
		int bytesRead = 0;
		byte[] tmp = new byte[4096];

		while ((bytesRead = iresStream.read(tmp, 0, 4096)) > -1)
			byteStream.write(tmp, 0, bytesRead);

		iresStream.close();
		// JDocs: "Closing a ByteArrayOutputStream has no effect."

		return byteStream.toByteArray();
	}
  //Component initialization
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(Variables.frameMainSizeX, Variables.frameMainSizeY));
    this.setTitle(Variables.toolName);

    //Menu Datei------------------------------
    jMenuFile.setText("Datei");

    //Menu Datei->Neu
    jMenuDateiNeu.setText("Neu");
    jMenuDateiNeu.addActionListener(new FrameMain_jMenuDateiNeu_ActionAdapter(this));
    jComboBox1.addActionListener(new FrameMain_jComboBox1_actionAdapter(this));
    jComboBox2.addActionListener(new FrameMain_jComboBox2_actionAdapter(this));
    jButtonDateiNeu.addActionListener(new FrameMain_jButtonDateiNeu_actionAdapter(this));
    jButtonSolverLP.setToolTipText("Solver LP-Solve wählen");
    jButtonSolverLP.setIcon(new ImageIcon(resourceToByteArray("SolveLp.GIF")));
    jButtonSolverLP.addActionListener(new FrameMain_jButtonSolverLP_actionAdapter(this));
    jButtonDateiSpeichern.setToolTipText("Speichern");
    jButtonDateiSpeichern.setIcon(new ImageIcon(resourceToByteArray("Save24.gif")));
    jButtonDateiSpeichern.setText("");
    jButtonDateiSpeichern.addActionListener(new FrameMain_jButtonDateiSpeichern_actionAdapter(this));
    //Geändert 2013 Benedikt Wölfle (SolverXA.gif ersetzt)
    jButtonSolverXP.setIcon(new ImageIcon(resourceToByteArray("SolveMOPS.gif")));
    //---------------------------------------------------------------------------
    jButtonSolverXP.setText("");
    jButtonSolverXP.addActionListener(new FrameMain_jButtonSolverXA_actionAdapter(this));
    jButtonSolverXP.setToolTipText("Solver MOPS wählen");
    jButtonSolverEnum.setToolTipText("Solver Enumeration wählen");
    jButtonSolverEnum.setIcon(new ImageIcon(resourceToByteArray("SolveEnum.GIF")));
    jButtonSolverEnum.setSelected(false);
    jButtonSolverEnum.addActionListener(new FrameMain_jButtonSolverEnum_actionAdapter(this));
    jLabel3.setText(" ");
    jLabel4.setText(" ");
    jButtonDateiNeu.setPreferredSize(new Dimension(37, 35));
    jButtonDateiNeu.setRequestFocusEnabled(true);
    jButtonDateiNeu.setVerifyInputWhenFocusTarget(true);
    jButtonDateiNeu.setRolloverEnabled(false);
    jButtonDateiNeu.setText("");
    jButtonDateiNeu.setVerticalAlignment(SwingConstants.CENTER);
    jButtonRowInsertLast.setSelected(false);
    jButtonRowInsertLast.setText("");
    jButtonRowInsertLast.addActionListener(new FrameMain_jButtonRowInsertLast_actionAdapter(this));
    jButtonRowInsertLast.setIcon(new ImageIcon(resourceToByteArray("RowInsertLast24.gif")));
    jButtonRowInsertLast.setToolTipText("Zeile einfügen");
    jButtonColInsertLast.setToolTipText("Spalte einfügen");
    jButtonColInsertLast.setIcon(new ImageIcon(resourceToByteArray("ColumnInsertLast24.gif")));
    jButtonColInsertLast.setSelected(false);
    jButtonColInsertLast.addActionListener(new FrameMain_jButtonColInsertLast_actionAdapter(this));
    jButtonRowDelLast.setSelected(false);
    jButtonRowDelLast.addActionListener(new FrameMain_jButtonRowDelLast_actionAdapter(this));
    jButtonRowDelLast.setIcon(new ImageIcon(resourceToByteArray("RowDeleteLast24.gif")));
    jButtonRowDelLast.setToolTipText("letzte Zeile löschen");
    jButtonColDelLast.setToolTipText("letzte Spalte löschen");
    jButtonColDelLast.setIcon(new ImageIcon(resourceToByteArray("ColumnDeleteLast24.gif")));
    jButtonColDelLast.setSelected(false);
    jButtonColDelLast.addActionListener(new FrameMain_jButtonColDelLast_actionAdapter(this));
    jButtonDateiOeffnen.setToolTipText("Öffnen");
    jButtonDateiOeffnen.setIcon(new ImageIcon(resourceToByteArray("Open24.gif")));
    jButtonDateiOeffnen.setText("");
    jButtonDateiOeffnen.addActionListener(new FrameMain_jButtonDateiOeffnen_actionAdapter(this));
    jButtonDateiOeffnen.setRolloverEnabled(false);
    jRadioButton2.addActionListener(new FrameMain_jRadioButton2_actionAdapter(this));
    jRadioButton1.addActionListener(new FrameMain_jRadioButton1_actionAdapter(this));
    jCheckBoxMenuSolverLp.setText("LP-Solve");
    jCheckBoxMenuSolverLp.addActionListener(new FrameMain_jCheckBoxMenuSolverLp_actionAdapter(this));
    jCheckBoxMenuSolverXa.setText("MOPS");
    jCheckBoxMenuSolverXa.addActionListener(new FrameMain_jCheckBoxMenuSolverXa_actionAdapter(this));
    jCheckBoxMenuSolverEnum.setText("Enumeration");
    jCheckBoxMenuSolverEnum.addActionListener(new FrameMain_jCheckBoxMenuSolverEnum_actionAdapter(this));
    jMenuFile.add(jMenuDateiNeu);

    //Menu Datei->Oeffnen
    jMenuDateiOeffnen.setText("Öffnen...");
    jMenuDateiOeffnen.addActionListener(new FrameMain_jMenuDateiOeffnen_ActionAdapter(this));
    jMenuFile.add(jMenuDateiOeffnen);

    jMenuFile.addSeparator();

    //Menu Datei->Speichern
    jMenuDateiSpeichern.setText("Speichern");
    jMenuDateiSpeichern.addActionListener(new FrameMain_jMenuDateiSpeichern_ActionAdapter(this));
    jMenuFile.add(jMenuDateiSpeichern);

    //Menu Datei->SpeichernUnter
    jMenuDateiSpeichernUnter.setText("Speichern unter...");
    jMenuDateiSpeichernUnter.addActionListener(new FrameMain_jMenuDateiSpeichernUnter_ActionAdapter(this));
    jMenuFile.add(jMenuDateiSpeichernUnter);

    jMenuFile.addSeparator();

    //Manu Datei->Beenden
    jMenuDateiExit.setText("Beenden");
    jMenuDateiExit.addActionListener(new FrameMain_jMenuDateiSchliessen_ActionAdapter(this));
    jMenuFile.add(jMenuDateiExit);

    jMenuBar1.add(jMenuFile);

    //Menu Datei Ende------------------------------

    //Menu Solver------------------------------
    jMenuSolver.setText("Solver");

    //Menu Solver->Entry1

    //Menu Solver->Entry2
    jMenuSolver.add(jCheckBoxMenuSolverLp);
    jMenuSolver.add(jCheckBoxMenuSolverXa);
    jMenuSolver.add(jCheckBoxMenuSolverEnum);

    jMenuBar1.add(jMenuSolver);

    //Menu Solver Ende------------------------------

    //Menu Help------------------------------
    jMenuHelp.setText("?");

    //Menu Help->Hilfe
    jMenuHelpHilfe.setText("Hilfe");
    jMenuHelpHilfe.addActionListener(new FrameMain_jMenuHelpHilfe_ActionAdapter(this));
    jMenuHelp.add(jMenuHelpHilfe);

    //Menu Help->Info
    jMenuHelpInfo.setText("Info");
    jMenuHelpInfo.addActionListener(new FrameMain_jMenuHelpInfo_ActionAdapter(this));
    jMenuHelp.add(jMenuHelpInfo);

    jMenuBar1.add(jMenuHelp);
    //Menu Help Ende------------------------------

    this.setJMenuBar(jMenuBar1);

    //JComboBox------------------------------
    fillCombobox(jComboBox1, Variables.dataRowsMax);
    fillCombobox(jComboBox2, Variables.dataColumnsMax);
    //JComboBox Ende------------------------------


    //Radio Buttons------------------------------
    ButtonGroup buttonGroupMaximize = new ButtonGroup();

    jRadioButton1.setText("Minimieren");
    jRadioButton2.setSelected(false);
    buttonGroupMaximize.add(jRadioButton1);

    jRadioButton2.setText("Maximieren");
    jRadioButton2.setSelected(true);
    buttonGroupMaximize.add(jRadioButton2);
    //Menu Help Ende------------------------------

    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);

    statusBar.setText("");

    jButtonDateiNeu.setIcon(new ImageIcon(resourceToByteArray("New24.gif")));
    jButtonDateiNeu.setToolTipText("Neu");
    jPanel2.setAlignmentX((float) 0.5);
    jPanel2.setDoubleBuffered(true);
    jPanel2.setLayout(xYLayout2);
    jPanel1.setEnabled(true);
    jPanel1.setForeground(Color.black);
    jPanel1.setDebugGraphicsOptions(0);
    jPanel1.setLayout(xYLayout1);
    jLabel1.setToolTipText("");
    jLabel1.setText("Zeilen");
    jLabel2.setText("Spalten");

    jTable1.setMaximumSize(new Dimension(300, 780));
    jTable1.setMinimumSize(new Dimension(300, 780));
    jTable1.setPreferredSize(new Dimension(150, 780));
    jScrollPane1.setPreferredSize(new Dimension(470, 42));
    jButtonSolverStarten.setText("Solver starten");
    jButtonSolverStarten.addActionListener(new FrameMain_jButton_SolverStarten_actionAdapter(this));

    jToolBar.add(jButtonDateiNeu);
    jToolBar.add(jButtonDateiOeffnen, null);
    jToolBar.add(jButtonDateiSpeichern);
    jToolBar.add(jLabel3, null);
    jToolBar.add(jButtonSolverLP);
    jToolBar.add(jButtonSolverXP, null);
    jToolBar.add(jButtonSolverEnum, null);
    jToolBar.add(jLabel4, null);
    jToolBar.add(jButtonRowInsertLast, null);
    jToolBar.add(jButtonRowDelLast, null);
    jToolBar.add(jButtonColInsertLast, null);
    jToolBar.add(jButtonColDelLast, null);



    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel2,           new XYConstraints(0, 0, 775, 35));
    jPanel2.add(jLabel1,    new XYConstraints(8, 8, 50, 22));
    jPanel2.add(jComboBox1,     new XYConstraints(45, 10, 60, 20));
    jPanel2.add(jComboBox2,     new XYConstraints(168, 8, 60, 20));
    jPanel2.add(jLabel2,  new XYConstraints(121, 8, 50, 22));
    jPanel2.add(jRadioButton1, new XYConstraints(304, 6, -1, -1));
    jPanel2.add(jRadioButton2,  new XYConstraints(381, 6, -1, -1));
    jPanel2.add(jButtonSolverStarten, new XYConstraints(522, 2, -1, -1));
    jPanel1.add(jScrollPane1,                    new XYConstraints(0, 36, 790, 464));
    contentPane.add(jToolBar, BorderLayout.NORTH);
    statusBarText("Willkommen zur " + Variables.toolName + " " + Variables.toolVersion + " !");
  }

  public void jMenuDateiNeu_actionPerformed(ActionEvent e)
  {
    statusBarText("Es wurde ein neue Datei angelegt.");
    newTableModel();
    applicationControll.filename=null;
    this.setTitle(Variables.toolName);
  }

  public void jMenuDateiOeffnen_actionPerformed(ActionEvent e)
  {
    try
    {
      DataModel dataModel = applicationControll.dateiOeffnen(e);
      if(dataModel!=null)
      {
        this.newTableModel();
        tableModel.setDataModel(dataModel);
        this.updateGUIfromTabelModel();
        this.setTitle(Variables.toolName + " - " + applicationControll.filename);
      }
      else
        statusBarText("Öffnen abgebrochen.");
    }
    catch (Exception eOeffnen)
    {
      ExceptionDialog.showExceptionDialog("Fehler","Fehler beim öffnen der Datei. " + eOeffnen.getMessage(),eOeffnen);
    }
    statusBarText("Es wurde die Datei " + applicationControll.filename + " geöffnet.");
  }


  public void jMenuDateiSpeichern_actionPerformed(ActionEvent e)
  {
    if(applicationControll.dateiSpeichern(e,this.tableModel.getDataModel()))
    {
      statusBarText("Es wurde unter dem Namen " + applicationControll.filename + " gespeichert.");
      this.setTitle(Variables.toolName + " - " + applicationControll.filename);
    }
    else
      statusBarText("Speichern wurde abgebrochen.");
  }

  public void jMenuDateiSpeichernUnter_actionPerformed(ActionEvent e)
  {
    if(applicationControll.dateiSpeichernUnter(e,this.tableModel.getDataModel()))
    {
      statusBarText("Es wurde unter dem Namen " + applicationControll.filename + " gespeichert.");
      this.setTitle(Variables.toolName + " - " + applicationControll.filename);
    }
    else
      statusBarText("Speichern wurde abgebrochen.");
  }

  public void jMenuDateiSchliessen_actionPerformed(ActionEvent e)
  {
    if(Variables.jOptionPane_DateiSpeichern())
      jMenuDateiSpeichern_actionPerformed(e);
    if(e!=null)
      System.exit(0);
  }

  //Overridden so we can exit when window is closed
  @Override
protected void processWindowEvent(WindowEvent e)
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      jMenuDateiSchliessen_actionPerformed(null);
      super.processWindowEvent(e);
      System.exit(0);
    }
  }

  public void jMenuHelpHilfe_actionPerformed(ActionEvent e)
  {//www
    try
    {

      Runtime.getRuntime().exec( "rundll32 url.dll,FileProtocolHandler " + new java.io.File(Variables.helpFile).getAbsolutePath() );

    }
    catch(IOException ex)
    {
      ExceptionDialog.showExceptionDialog("Fehler beim Start des Browsers. Siehe " + Variables.helpFile + ".",ex.getMessage(), ex);
    }
    //jMenuShowText_actionPerformed("Hilfe",Variables.getHilfe());
  }

  public void jMenuHelpInfo_actionPerformed(ActionEvent e)
  {
//    FrameMain_HelpInfo dlg = new FrameMain_HelpInfo(this);
//    Dimension dlgSize = dlg.getPreferredSize();
//    Dimension frmSize = getSize();
//    Point loc = getLocation();
//    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
//    dlg.setModal(true);
//    dlg.pack();
//    dlg.show();
  }

  public void jMenuShowText_actionPerformed(String headline, String text)
  {
    FrameMain_ShowText dlg = new FrameMain_ShowText(this,headline,text);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.show();
  }


  public void fillCombobox(JComboBox jComboBox, int numberOfItems)
  {
    for(int i=1; i<=numberOfItems; i++)
      jComboBox.addItem(String.valueOf(i));
  }

  void jButton_SolverStarten(ActionEvent e)
  {
    if (solverTyp==0)
    {
      Variables.jOptionPane_SolverWaehlen();
      return;
    }
    statusBarText("Die Werte des TableModels werden geprüft.");
    for ( int i = 1; i < tableModel.getRowCount(); i++)
      for (int j = 1; j < tableModel.getColumnCount(); j++)
        if(tableModel.getValueAt(i,j).toString() == "")
        {
          if(Variables.jOptionPane_AssistentStarten())//if assistant start
          {
            boolean keieEingebe = true;
            String value=null;
            while (keieEingebe) //while there is there is not an integer value entered
            {
              value = Variables.jOptionPane_AssistentZahl(); //ask user for value
              if(value==null)//if user chooses quit
                return; //go back to table to enter data manually
              else if(!tableModel.pruefeStringAufInt(value,false)) //else if no integer value
                Variables.jOptionPane_IntergerWarning(value); //show integer warning
              else
                keieEingebe=false; //puh. user has entered a correct integer value, leave while
            }//while
            for(i=1; i<tableModel.getRowCount(); i++)
                for(j=1; j<tableModel.getColumnCount(); j++)
                  if(tableModel.getValueAt(i,j).toString() == "")
                    tableModel.setValueAt(value,i,j); //write integer value to tableModel
          }
          else//else do not start assistant
            return; //go back to table to enter data manually
        }//if(tableModel.getValueAt(i,j).toString() == "")

    //DataModel is now completed, sovler can be started

    statusBar.setText("Der Solver wird gestartet. ----Bitte warten---");
    //try{Thread.sleep(1000);this.wait(1000);}catch (Exception ex ){}
    String result = applicationControll.starteSolve(tableModel.getDataModel(),solverTyp); //1=LP,2=XA,3=Enum
    jMenuShowText_actionPerformed("Ergebnis",result);
  }

  void jComboBox1_actionPerformed(ActionEvent e)//this method is also called when adding items to the comboBox
  {
    if(jComboBox1Init)//only when jComboBox1Init is true
    {
      tableModel.setRowCount(Integer.parseInt(jComboBox1.getSelectedItem().toString())+1);
      this.setColumnWidth();//ajust the width of each column after changing the size
    }
  }

  void jComboBox2_actionPerformed(ActionEvent e)//same as jComboBox1_actionPerformed()
  {
    if(jComboBox2Init)
    {
      tableModel.setColumnCount(Integer.parseInt(jComboBox2.getSelectedItem().toString())+1);
      this.setColumnWidth();
    }
  }

  private void newTableModel()
  {
    jComboBox1.setSelectedIndex(Variables.startWithRows-1);
    jComboBox2.setSelectedIndex(Variables.startWithColumns-1);
    int rows = Integer.parseInt(jComboBox1.getSelectedItem ().toString());
    int columns = Integer.parseInt(jComboBox2.getSelectedItem().toString());

    jScrollPane1.remove(jTable1); //remove old table from GUI
    tableModel = new TableModel(rows+1,columns+1,true);//create new tableModel
    jTable1 = new JTable(tableModel);//create new table with tableModel
    jScrollPane1.getViewport().add(jTable1);//add table to GUI

    setColumnWidth();

    jComboBox1Init=true;
    jComboBox2Init=true;
  }

  private void setColumnWidth()
  {
    jTable1.setAutoResizeMode(0); //set column with
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);//width of the first column
    for (int i = 1; i < tableModel.getColumnCount(); i++)
      jTable1.getColumnModel().getColumn(i).setPreferredWidth(45);//of all other columns
  }


  private void statusBarText(String text)
  {
    statusBar.setText(" " + text);
    new Thread(){{start();}
     @Override
	public void run(){
       try{sleep(Variables.statusBarShowTextTime);statusBar.setText("");}
       catch (InterruptedException e )
       {ExceptionDialog.showExceptionDialog("Fehler bei der StatusBar.",e.getMessage(),e);}
     }};
  }

  private boolean statusBarText2(String text)
{
  statusBar.setText(" " + text);
  new Thread(){{start();}
   @Override
public void run(){
     try{sleep(Variables.statusBarShowTextTime);statusBar.setText("");}
     catch (InterruptedException e )
     {ExceptionDialog.showExceptionDialog("Fehler bei der StatusBar.",e.getMessage(),e);}
   }};
 return true;
}



  void jButtonDateiNeu_actionPerformed(ActionEvent e)
  {
    this.jMenuDateiNeu_actionPerformed(e);
  }

  void jButtonDateiSpeichern_actionPerformed(ActionEvent e)
  {
    this.jMenuDateiSpeichern_actionPerformed(e);
  }

  void jButtonDateiNeu_mouseEntered(MouseEvent e) {
    statusBarText("Hier wird ein neues TableModel angelegt. Ihre Eingabewerte, sichtbar oder nicht sichtbar, gehen verlohren.");
  }

  //for debugging only
  void jButton3_actionPerformed(ActionEvent e)
  {
    System.out.println("-Werte von TableModel-");
    System.out.println("max " + tableModel.getMaximize());
    System.out.println("rows "+ tableModel.getRowCount());
    System.out.println("cols "+ tableModel.getColumnCount());
    System.out.println("-ende-");

    //tableModel = new TableModel(dataModel);
    //jTable1 = new JTable(tableModel);
    //jScrollPane1.getViewport().add(jTable1);

  }

  void jButtonSolverLP_actionPerformed(ActionEvent e)
  {
    this.jCheckBoxMenuSolverLp_actionPerformed(e);
  }

  void jButtonSolverXA_actionPerformed(ActionEvent e)
  {
     this.jCheckBoxMenuSolverXa_actionPerformed(e);
  }

  void jButtonSolverEnum_actionPerformed(ActionEvent e)
  {
     this.jCheckBoxMenuSolverEnum_actionPerformed(e);
  }

  void jButtonDateiOeffnen_actionPerformed(ActionEvent e) {
    this.jMenuDateiOeffnen_actionPerformed(e);
  }

  void jButtonRowInsertLast_actionPerformed(ActionEvent e)
  {
    if(Integer.parseInt(jComboBox1.getSelectedItem().toString())<Variables.dataRowsMax)
    {
      //+1 for headline, the other +1 for increase the value, so total +2
      tableModel.setRowCount(Integer.parseInt(jComboBox1.getSelectedItem().toString()) +2);
      this.updateGUIfromTabelModel();
    }
    else
      this.statusBarText("Die maximale Anzahl an Reihen ist schon erreicht.");
  }

  void jButtonColInsertLast_actionPerformed(ActionEvent e)
  {
    if(Integer.parseInt(jComboBox2.getSelectedItem().toString())<Variables.dataColumnsMax)
    {
      tableModel.setColumnCount(Integer.parseInt(jComboBox2.getSelectedItem().toString()) +2);
      this.updateGUIfromTabelModel();
    }
    else
      this.statusBarText("Die maximale Anzahl an Spalten ist schon erreicht.");
  }



  void jButtonRowDelLast_actionPerformed(ActionEvent e)
  {
    if(Integer.parseInt(jComboBox1.getSelectedItem().toString())>1)
    {
      //+1 for headline, the other -1 for decrese the value, so total +0
      tableModel.setRowCount(Integer.parseInt(jComboBox1.getSelectedItem().toString()) +0);
      this.updateGUIfromTabelModel();
    }
    else
      this.statusBarText("Kleiner gehts nicht!");
  }

  void jButtonColDelLast_actionPerformed(ActionEvent e)
  {
    if(Integer.parseInt(jComboBox2.getSelectedItem().toString())>1)
    {
      tableModel.setColumnCount(Integer.parseInt(jComboBox2.getSelectedItem().toString()));
      this.updateGUIfromTabelModel();
    }
    else
      this.statusBarText("Kleiner gehts nicht!");

  }

  private void updateGUIfromTabelModel()//update ComboBoxes and min/max radio Button
  {
    jComboBox1.setSelectedItem(String.valueOf(tableModel.getRowCount()-1));
    jComboBox2.setSelectedItem(String.valueOf(tableModel.getColumnCount()-1));
    jRadioButton1.setSelected(!tableModel.getMaximize());
    jRadioButton2.setSelected(tableModel.getMaximize());
  }

  void jRadioButton2_actionPerformed(ActionEvent e)
  {
    tableModel.setMaximize(true);
  }

  void jRadioButton1_actionPerformed(ActionEvent e)
  {
    tableModel.setMaximize(false);
  }

  void jCheckBoxMenuSolverLp_actionPerformed(ActionEvent e)
  {
    jCheckBoxMenuSolverLp.setSelected(true);
    jCheckBoxMenuSolverXa.setSelected(false);
    jCheckBoxMenuSolverEnum.setSelected(false);
    this.solverTyp=1;
    statusBarText("Es wurde der Solver LP-Solve gewählt.");
  }

  void jCheckBoxMenuSolverXa_actionPerformed(ActionEvent e)
  {
    jCheckBoxMenuSolverLp.setSelected(false);
    jCheckBoxMenuSolverXa.setSelected(true);
    jCheckBoxMenuSolverEnum.setSelected(false);
    this.solverTyp=2;
    statusBarText("Es wurde der Solver MOPS gewählt.");
  }


  void jCheckBoxMenuSolverEnum_actionPerformed(ActionEvent e)
  {
    jCheckBoxMenuSolverLp.setSelected(false);
    jCheckBoxMenuSolverXa.setSelected(false);
    jCheckBoxMenuSolverEnum.setSelected(true);
    this.solverTyp=3;
    statusBarText("Es wurde der Solver Enumeration gewählt.");
  }




}

//------------
class FrameMain_jMenuDateiNeu_ActionAdapter implements ActionListener
{
  FrameMain adaptee;

  FrameMain_jMenuDateiNeu_ActionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }

  @Override
public void actionPerformed(ActionEvent e)
  {
    adaptee.jMenuDateiNeu_actionPerformed(e);
  }
}

class FrameMain_jMenuDateiOeffnen_ActionAdapter implements ActionListener
{
  FrameMain adaptee;

  FrameMain_jMenuDateiOeffnen_ActionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }

  @Override
public void actionPerformed(ActionEvent e)
  {
    adaptee.jMenuDateiOeffnen_actionPerformed(e);
  }
}


class FrameMain_jMenuDateiSpeichern_ActionAdapter implements ActionListener
{
  FrameMain adaptee;

  FrameMain_jMenuDateiSpeichern_ActionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }

  @Override
public void actionPerformed(ActionEvent e)
  {
    adaptee.jMenuDateiSpeichern_actionPerformed(e);
  }
}

class FrameMain_jMenuDateiSpeichernUnter_ActionAdapter implements ActionListener
{
  FrameMain adaptee;

  FrameMain_jMenuDateiSpeichernUnter_ActionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.jMenuDateiSpeichernUnter_actionPerformed(e);
  }
}

class FrameMain_jMenuDateiSchliessen_ActionAdapter implements ActionListener
{
  FrameMain adaptee;

  FrameMain_jMenuDateiSchliessen_ActionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.jMenuDateiSchliessen_actionPerformed(e);
  }
}


//helphilfe
class FrameMain_jMenuHelpHilfe_ActionAdapter implements ActionListener
{
  FrameMain adaptee;

  FrameMain_jMenuHelpHilfe_ActionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.jMenuHelpHilfe_actionPerformed(e);
  }
}
//helpinfo
class FrameMain_jMenuHelpInfo_ActionAdapter implements ActionListener
{
  FrameMain adaptee;

  FrameMain_jMenuHelpInfo_ActionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.jMenuHelpInfo_actionPerformed(e);
  }
}


class FrameMain_jButton_SolverStarten_actionAdapter implements java.awt.event.ActionListener
{
  FrameMain adaptee;

  FrameMain_jButton_SolverStarten_actionAdapter(FrameMain adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jButton_SolverStarten(e);
  }
}

class FrameMain_jComboBox1_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jComboBox1_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jComboBox1_actionPerformed(e);
  }
}

class FrameMain_jComboBox2_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jComboBox2_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jComboBox2_actionPerformed(e);
  }

}


class FrameMain_jButtonDateiNeu_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonDateiNeu_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonDateiNeu_actionPerformed(e);
  }
}



class FrameMain_jButtonDateiNeu_mouseAdapter extends java.awt.event.MouseAdapter {
  FrameMain adaptee;

  FrameMain_jButtonDateiNeu_mouseAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseEntered(MouseEvent e) {
    adaptee.jButtonDateiNeu_mouseEntered(e);
  }
}

class FrameMain_jButtonSolverLP_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonSolverLP_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSolverLP_actionPerformed(e);
  }
}

class FrameMain_jButtonSolverXA_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonSolverXA_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSolverXA_actionPerformed(e);
  }
}

class FrameMain_jButtonDateiOeffnen_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonDateiOeffnen_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonDateiOeffnen_actionPerformed(e);
  }
}

class FrameMain_jButtonRowInsertLast_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonRowInsertLast_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonRowInsertLast_actionPerformed(e);
  }
}

class FrameMain_jButtonColInsertLast_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonColInsertLast_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonColInsertLast_actionPerformed(e);
  }
}

class FrameMain_jButtonRowDelLast_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonRowDelLast_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonRowDelLast_actionPerformed(e);
  }
}

class FrameMain_jButtonColDelLast_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonColDelLast_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonColDelLast_actionPerformed(e);
  }
}

class FrameMain_jRadioButton2_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jRadioButton2_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButton2_actionPerformed(e);
  }
}

class FrameMain_jRadioButton1_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jRadioButton1_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButton1_actionPerformed(e);
  }
}

class FrameMain_jButtonDateiSpeichern_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonDateiSpeichern_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonDateiSpeichern_actionPerformed(e);
  }
}

class FrameMain_jButtonSolverEnum_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jButtonSolverEnum_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSolverEnum_actionPerformed(e);
  }
}

class FrameMain_jCheckBoxMenuSolverLp_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jCheckBoxMenuSolverLp_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuSolverLp_actionPerformed(e);
  }
}

class FrameMain_jCheckBoxMenuSolverEnum_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jCheckBoxMenuSolverEnum_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuSolverEnum_actionPerformed(e);
  }
}

class FrameMain_jCheckBoxMenuSolverXa_actionAdapter implements java.awt.event.ActionListener {
  FrameMain adaptee;

  FrameMain_jCheckBoxMenuSolverXa_actionAdapter(FrameMain adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuSolverXa_actionPerformed(e);
  }
}






