package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Modellier Grafik Verwaltung</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.//
 */


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;

import JSX.*;
import javax.swing.border.*;
import java.util.*;

public class ModellierView extends JPanel{

  private ButtonGroup buttonGroup1 = new ButtonGroup();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel7 = new JPanel();
  private JTextArea jTextArea1 = new JTextArea();
  private JLabel aktionLabel = new JLabel();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JPanel jPanel4 = new JPanel();
  private JLabel jLabel2 = new JLabel();
  private JPanel jPanel3 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private ModellierManager mManager;
  private ModellierViewGrafik mView = new ModellierViewGrafik(mManager,aktionLabel);
  private JScrollPane jPanel1 = new JScrollPane(mView);
  //       private JScrollPane jPanel1 = new JScrollPane(); // für Designer :-)
  private JButton route_btn_all = new JButton();
  private JButton jButton1 = new JButton();
  private JButton route_btn_one = new JButton();
  private Border border1;
  private Progress progressPanel = new Progress(0,null);
  private int calculatedRouten;
  private String modellFile;// = new String(null);



  public ModellierView() {

    mManager = new ModellierManager(this);
    mManager.updateButtonState(1);
    mView.setManager(mManager);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {

    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142));
    this.setLayout(borderLayout1);
    route_btn_all.setMaximumSize(new Dimension(140, 27));
    route_btn_all.setMinimumSize(new Dimension(140, 27));
    route_btn_all.setPreferredSize(new Dimension(140, 27));
    route_btn_all.setText("berech. alle Route");
    route_btn_all.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        route_btn_all_actionPerformed(e);
      }
    });
    jButton1.setMinimumSize(new Dimension(140, 27));
    jButton1.setPreferredSize(new Dimension(140, 27));
    jButton1.setText("Beschriftung Aus");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jPanel4.setMinimumSize(new Dimension(160, 237));
    jPanel4.setPreferredSize(new Dimension(160, 237));
    jPanel3.setPreferredSize(new Dimension(135, 38));
    route_btn_one.setMaximumSize(new Dimension(140, 27));
    route_btn_one.setMinimumSize(new Dimension(140, 27));
    route_btn_one.setPreferredSize(new Dimension(140, 27));
    route_btn_one.setText("berech. eine Route");
    route_btn_one.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        route_btn_one_actionPerformed(e);
      }
    });
    this.add(jPanel7,  BorderLayout.CENTER);

    mView.setMinimumSize(new Dimension(1280, 1024));
    mView.setPreferredSize(new Dimension(1280, 1024));

    jPanel7.setLayout(gridBagLayout1);
    jPanel1.getViewport().setBackground(Color.white);
    jPanel1.setBorder(null);
    jTextArea1.setMinimumSize(new Dimension(118, 118));
    jTextArea1.setPreferredSize(new Dimension(135, 118));
    jTextArea1.setEditable(false);
    jTextArea1.setText(" ");
    aktionLabel.setFont(new java.awt.Font("Monospaced", 0, 12));
    aktionLabel.setText("neues Element");
    jPanel4.setLayout(gridBagLayout4);
    jLabel2.setFont(new java.awt.Font("Monospaced", 0, 12));
    jLabel2.setMaximumSize(new Dimension(130, 17));
    jLabel2.setMinimumSize(new Dimension(130, 17));
    jLabel2.setPreferredSize(new Dimension(150, 17));
    jLabel2.setText("gewählte Option:");
    jPanel3.setLayout(gridBagLayout3);
    jPanel3.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanel2.setLayout(gridBagLayout2);
    jPanel2.setAlignmentX((float) 0.0);
    jPanel2.setAlignmentY((float) 0.0);

    jPanel7.add(jPanel2,         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    jPanel3.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(aktionLabel,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(progressPanel,      new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 5, 0, 0), 0, 0));
    jPanel7.add(jPanel1,      new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    jPanel2.add(jPanel4,                          new GridBagConstraints(0, 2, 2, 1, 0.0, 0.9
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 0, 0, 0), 0, 0));
    jPanel4.add(jTextArea1,                       new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel4.add(route_btn_all,                  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(25, 5, 0, 5), 0, 0));
    jPanel4.add(jButton1,         new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
    jPanel4.add(route_btn_one,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0));
    jPanel2.add(jPanel3,    new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));

    jTextArea1.setFont(( new Font("Systems",Font.CENTER_BASELINE, 10) ));
  }

  public void updatePropertyText(String s){
    jTextArea1.setText(s);
  }

  void route_btn_all_actionPerformed(ActionEvent e) {

    mManager.getKantenListe().clearPath();

    RouterManager rm = new RouterManager(mManager.getKnotenListe(), mManager.getKantenListe(),this);
 //   rm.RouterManagerRun(mManager.getKnotenListe(), mManager.getKantenListe());
    rm.setDoAllRoute();
    rm.start();
/*
    try{ // warte bis Thread RoutenManager beendet ist
      while(rm.isAlive()){
      this.wait(100);
      }}catch(InterruptedException ex){}
*/
    this.paint(this.getGraphics());
    this.repaint();


  }

  public String openDia(int filter){ // filter 0 == images, filter 1==modelldaten

    final JFileChooser fc = new JFileChooser("./daten");
    if (filter == 0)
      fc.addChoosableFileFilter(new ImageFilter());
    if (filter == 1){
      fc.addChoosableFileFilter(new ModellFilter());
    }
    int returnVal = fc.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      return file.getAbsolutePath();
    }
    return (null);
  }

  public String saveDia(){

    final JFileChooser fc = new JFileChooser("./daten");
    int returnVal = fc.showSaveDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      JOptionPane.showMessageDialog(null, ""+file.getAbsolutePath() );
      return file.getAbsolutePath();
    }
    return (null);
  }

  public void saveModell(){

    String fileToSave = (saveDia());
    if (fileToSave != null){
      // dateinamen überprüpfen
      if(!fileToSave.endsWith(".xml") ){
        if(fileToSave.lastIndexOf(".") == -1){
          fileToSave+=(".xml");
        }else{
          fileToSave = fileToSave.substring(0,fileToSave.lastIndexOf("."))+(".xml");
        }
      }
//      RouterManager rManager = new RouterManager(mManager.getKnotenListe(), mManager.getKantenListe());
      RouterManager rManager = new RouterManager();
      mManager.getKnotenListe().clearStart();
      mManager.getKnotenListe().clearZiel();
      mManager.getKantenListe().clearPath();

     try{
        ObjOut out = new ObjOut( new FileWriter(fileToSave) );
/*
        out.writeObject(  mManager.getKnotenListe() );
        out.writeObject(  mManager.getKantenListe() );
        out.writeObject(  rManager.getRoutenListe() );
*/
        Vector v = new Vector();
        v.add( mManager.getKnotenListe() );
        v.add( mManager.getKantenListe() );
        v.add( rManager.getRoutenListe() );
        out.writeObject( v );
        out.close();
      }
      catch ( FileNotFoundException e1 ) {
        System.out.println("Dateifehler" );
      }
      catch ( IOException e2 ) {
        System.out.println("Lesefehler");
      }
    modellFile = fileToSave;
    }
    this.repaint();
  }



  void jButton1_actionPerformed(ActionEvent e) {
    if (jButton1.getText() == "Beschriftung Aus"){
      jButton1.setText("Beschriftung An");
      mView.setBeschriftung(false);
    }
    else{
      jButton1.setText("Beschriftung Aus");
      mView.setBeschriftung(true);
    }
    this.repaint();
  }

  public void newRoute(){
    if( JOptionPane.showConfirmDialog(null,"bisherige Route wird gelöscht."
                                      +"\nSind Sie sicher?") == JOptionPane.OK_OPTION){
      reset();
    }
  }

public ModellierManager getManager(){

  return mView.getManager() ;
}



  public void reset(){

    KnotenListe ktn = new KnotenListe();
    KantenListe k = new KantenListe(ktn);
    mManager.setKantenListe(k);
    mManager.setKnotenListe(ktn);
    mManager.setMarkedKante(-1);

    mView.repaint();
  }

  public void setImage(){
    if( JOptionPane.showConfirmDialog(null,"bisherige Route wird gelöscht."
                                      +"\nSind Sie sicher?") == JOptionPane.OK_OPTION){
      String fileName = (openDia(0));
      if (fileName != null){
        File file = new File(fileName);
        ImageFilter filter = new ImageFilter();
        if (filter.accept(file)){
          mView.setImage(file.getAbsolutePath());
          reset();
        }
        else{
          JOptionPane.showMessageDialog(null,"Datei enthält keine Bild Informationen");
        }
        this.paintComponent(this.getGraphics());
      }
    }
  }

  public void openModell(){

    String fileToOpen = (openDia(1));
    if (fileToOpen != null){

      KnotenListe knotenListe = new KnotenListe();
      KantenListe kantenListe = new KantenListe(knotenListe);
      RoutenListe routenListe = new RoutenListe();
      RouterManager rManager = new RouterManager();
      try{
        ObjIn in = new ObjIn( new FileReader(fileToOpen));
        Object o = in.readObject( );
        knotenListe = (KnotenListe)((Vector)o).get(0);
        kantenListe = (KantenListe)((Vector)o).get(1);
        routenListe = (RoutenListe)((Vector)o).get(2);
        in.close();
        modellFile = fileToOpen;
      }
      catch ( FileNotFoundException e1 ) {
        System.out.println("Dateifehler" );
      }
      catch ( IOException e2 ) {
        System.out.println("Lesefehler");
      }
      catch ( ClassNotFoundException e3 ) {
        System.out.println("Klassenfehler");
      }
      mManager.setKantenListe(kantenListe);
      mManager.setKnotenListe(knotenListe);
      mManager.setKantenKnotenListe(knotenListe);
      rManager.setRoutenListe( routenListe );
//      rManager.getRoutenListe().display();
      mManager.setMarkedKante(-1);
      mManager.setMarkedKnoten(-1);
      this.repaint();
    }
  }

  void route_btn_one_actionPerformed(ActionEvent e) {
    mManager.getKantenListe().clearPath();

    RouterManager rm = new RouterManager(mManager.getKnotenListe(), mManager.getKantenListe(),this);
    rm.setDoOnlyOneRoute();

    if(rm.doPlausiCheck(mManager.getKnotenListe(), mManager.getKantenListe()))
      return;
    rm.start();
/*
      try{ // warte bis Thread RoutenManager beendet ist
        while(rm.isAlive()){
        Thread.currentThread().wait(100);
      }}catch(InterruptedException ex){}
*/
    this.paint(this.getGraphics());
    this.repaint();

    // Fester mit Ausgabe der RoutenInfo
    Route route = new Route();
    route = rm.getRoutenListe().getRoute(mManager.getKnotenListe().getStartKnotenId(), mManager.getKnotenListe().getZielKnotenId());
    if(route == null)
      System.err.println("Route ist NULL");
    route.display();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    DialogRoutenInfo diaRoutenInfo = new DialogRoutenInfo();
    diaRoutenInfo.setSize(300,250);
    diaRoutenInfo.setLocation((screenSize.width - mView.getX()) / 2, (screenSize.height - mView.getX()) / 2);

    String text = "";
    int kosten = 0;

    route.resetKnoten();
    route.resetKante();

    text += "Start-Knoten: " + mManager.getKnotenListe().getName(route.nextKnoten())+"\n";
    while (route.hasNextKnoten()){
      text += "  via Knoten: " + mManager.getKnotenListe().getName(route.nextKnoten())+"\n";
    }
    text += " Ziel-Knoten: " + mManager.getKnotenListe().getName(route.getZiel())+"\n";
    text += "Gesamtkosten: " + rm.getOptimum();

    diaRoutenInfo.jTextPane1.setText( text );
    diaRoutenInfo.show();
  }

void StartProgress(int anzahl){
  progressPanel.init(this,anzahl);
  progressPanel.start();
}
void StopProgressPanel(){
  progressPanel.stop();
  progressPanel.setValue(0);
}
int getCalculatedRouten(){
  return calculatedRouten;
}
void setCalculatedRouten(int anzahl){
  calculatedRouten = anzahl;
}
public String getModellFile(){
  return modellFile;
  }
}