package opsa;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import com.borland.jbcl.layout.*;
import javax.swing.table.*;

public class FmOpSa extends JFrame {
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  XYLayout xYLayout1 = new XYLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JScrollPane jScrollPane2 = new JScrollPane();
  TextArea textAreaInfo = new TextArea();
  Button buttonOpt = new Button();
  JTable jTablePeriode=null;
  JTable jTableOperation=null;
  JMenuItem jMenuItemNew = new JMenuItem();
  JMenu jMenuTool = new JMenu();
  JMenuItem jMenuItemOpt = new JMenuItem();
  JMenuItem jMenuItemOpen = new JMenuItem();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();

  //
  String[] tbOp_columnNames = {"Nr.", "Dauer(min)", "von Periode", "bis Periode", "Merkung"};
  String[] tbPer_columnNames = {"Nr.", "Saal"};
  DefaultTableModel tbOpModel;
  DefaultTableModel tbPerModel;
  Vector vectorOperation=new Vector();
  Vector vectorPeriodeSaal=new Vector();
  Vector vectorErgebnisse=new Vector();

  private int operationsAnz;
  private int periodeAnz;
  private int saalAnz;
  Vector ergebnisse=new Vector();
  LPData theLpdata;

  String lpsolvePath="";
  int defaultPeriode=0;

  /**Construct the frame*/
  public FmOpSa() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Component initialization*/
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(FmOpSa.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(xYLayout1);
    this.setSize(new Dimension(605, 485));
    this.setTitle("Operationssäleplanung");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileExit_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    textAreaInfo.setText("Ergebnis");
    buttonOpt.setFont(new java.awt.Font("Dialog", 1, 12));
    buttonOpt.setLabel("Optimieren");
    buttonOpt.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonOpt_actionPerformed(e);
      }
    });
    jMenuItemNew.setText("New");
    jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemNew_actionPerformed(e);
      }
    });
    jMenuTool.setText("Tool");
    jMenuItemOpt.setText("Optimieren");
    jMenuItemOpt.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemOpt_actionPerformed(e);
      }
    });
    jMenuItemOpen.setText("Open");
    jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemOpen_actionPerformed(e);
      }
    });
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Operation");
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel2.setText("Periode & Saal");
    jMenuFile.add(jMenuItemNew);
    jMenuFile.add(jMenuItemOpen);
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuTool);
    jMenuBar1.add(jMenuHelp);
    contentPane.add(jScrollPane1,              new XYConstraints(5, 37, 448, 222));
    contentPane.add(jScrollPane2,                   new XYConstraints(465, 37, 133, 187));
    contentPane.add(jLabel1,      new XYConstraints(8, 15, -1, -1));
    contentPane.add(textAreaInfo,     new XYConstraints(1, 272, 600, 209));
    contentPane.add(jLabel2,                                  new XYConstraints(465, 17, -1, -1));
    contentPane.add(buttonOpt,        new XYConstraints(481, 235, -1, -1));
    jMenuTool.add(jMenuItemOpt);
    this.resetTableModel();
    this.setJMenuBar(jMenuBar1);
    jTableOperation = new JTable(tbOpModel);
    jTableOperation.setCellSelectionEnabled(true);
    jScrollPane1.getViewport().add(jTableOperation, null);
    jTablePeriode = new JTable(tbPerModel);
    jTablePeriode.setCellSelectionEnabled(true);
    jScrollPane2.getViewport().add(jTablePeriode, null);
    Ini theIni=new Ini();
    lpsolvePath=theIni.getLPsolvePath();
    defaultPeriode=theIni.getDefauldPeriode();
    System.out.println(lpsolvePath);
    System.out.println(defaultPeriode);
  }
  /**File | Exit action performed*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
  /**Help | About action performed*/
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    FmOpSa_AboutBox dlg = new FmOpSa_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }
  public void setDefaultTableOperation(int rowCount)
  {
    //tbOp_data = new String [rowCount][tbOp_columnNames.length];
    tbOpModel.setRowCount(rowCount);
    for(int i=0;i<rowCount;i++)
      tbOpModel.setValueAt(""+(i+1),i,0);
  }
  public void setDefaultTablePeriode(int rowCount){
    tbPerModel.setRowCount(rowCount);
    for(int i=0;i<rowCount;i++)
      tbPerModel.setValueAt(""+(i+1),i,0);
  }
  public void setOperationsAnz(int anz){
    operationsAnz=anz;
  }
  public void setPeriodeAnz(int anz){
    periodeAnz=anz;
  }
  public void setSaalAnz(int in_saalAnz)
  {
    saalAnz=in_saalAnz;
  }
  void jMenuItemNew_actionPerformed(ActionEvent e) {
    textAreaInfo.setText("");
    tbPerModel.setNumRows(0);
    tbOpModel.setNumRows(0);
    DlgConfig theDlgConifg=new DlgConfig(this);
    theDlgConifg.setLocation(250,300);
    theDlgConifg.setSize(337,230);
    theDlgConifg.show();
  }
  public void reset()
  {
    if(vectorOperation.size()!=0) vectorOperation.removeAllElements();
    if(vectorPeriodeSaal.size()!=0) vectorPeriodeSaal.removeAllElements();
    if((vectorErgebnisse.size())!=0) vectorErgebnisse.removeAllElements();
    //tbOpModel=null;
    //tbPerModel=null;

    //operationsAnz=0;
    //periodeAnz=0;
    //saalAnz=0;
  }
  public boolean setOperations()
  {
    this.reset();
    int ID=0,anfang=0,ende=0,dauer=0;
    String merk="" ;
    for(int i=0;i<tbOpModel.getRowCount();i++){
      try{
        ID=Integer.parseInt((String)tbOpModel.getValueAt(i,0));
        dauer=Integer.parseInt((String)tbOpModel.getValueAt(i,1));
        anfang=Integer.parseInt((String)tbOpModel.getValueAt(i,2));
        ende=Integer.parseInt((String)tbOpModel.getValueAt(i,3));
        //merk=(String)tbOpModel.getValueAt(i,4);
        if(anfang<=0 || ende<=0 || anfang>periodeAnz ||ende>periodeAnz)
        {
            JOptionPane.showMessageDialog(this,"Maxperiode ist: "+periodeAnz
                                              +"\n keine negative Zahl",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        vectorOperation.add(new Operation(ID,anfang,ende,dauer,merk));
      }
      catch(Exception e1){
        JOptionPane.showMessageDialog(this,e1.toString()
          +"\nEingabe bei der Operation falsch\nOder Enter vergessen!",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
    }
    int periodeID=0;
    int anzSaal=0;
    for(int j=0;j<tbPerModel.getRowCount();j++){
      try{
        periodeID=Integer.parseInt((String)tbPerModel.getValueAt(j,0));
        anzSaal=Integer.parseInt((String)tbPerModel.getValueAt(j,1));
        if(anzSaal<=0 || anzSaal>saalAnz)
        {
            JOptionPane.showMessageDialog(this,"Maxsaal ist: "+saalAnz
                                            +"\n keine negative Zahl",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        vectorPeriodeSaal.add(new periodSaal(periodeID,anzSaal));
      }
      catch(Exception e2){
        JOptionPane.showMessageDialog(this,e2.toString()
          +"Eingabe bei der Periode-Saal falsch\nOder Enter vergessen!!",
                                        "Info", JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
    }
    return true;
  }
  void buttonOpt_actionPerformed(ActionEvent e){
    if(tbOpModel.getRowCount()==0)
    {
      JOptionPane.showMessageDialog(this,"Bitte wahlen Sie new von Menu aus.",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    if(tbOpModel.getValueAt(tbOpModel.getRowCount()-1,tbOpModel.getColumnCount()-2)==null)
    {
      JOptionPane.showMessageDialog(this,"Bitte geben Sie Daten in die Tabelle Operation ein.",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    if(tbPerModel.getValueAt(tbPerModel.getRowCount()-1,tbPerModel.getColumnCount()-1)==null)
    {
      JOptionPane.showMessageDialog(this,"Bitte geben Sie Daten in die Tabelle Periode ein.",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    //lese die Daten ein und erzeuge Objekts von Operationen
    if(this.setOperations()==true)
    {
      //erstelle die txt-Datei
      if(theLpdata==null)
        theLpdata = new LPData(lpsolvePath,defaultPeriode);
      if(theLpdata!=null)
      {
        if(theLpdata.initialisierung(vectorOperation,vectorPeriodeSaal)==true)
        {
          theLpdata.parseData();
          this.setInfo();
        }
      }
    }
    else
    {//abbrechen
      return;
    }
  }

  void jMenuItemOpt_actionPerformed(ActionEvent e) {
      buttonOpt_actionPerformed(e);
  }

  void jMenuItemOpen_actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(this,"Open Option wurde nicht implementiert.",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
  }
  public void setInfo()
  {
    textAreaInfo.setText(theLpdata.getErgebnisse()+"\n");
  }
  public void resetTableModel()
  {
    tbOpModel=new DefaultTableModel(tbOp_columnNames,0);
    tbPerModel=new DefaultTableModel(tbPer_columnNames,0);
  }
}