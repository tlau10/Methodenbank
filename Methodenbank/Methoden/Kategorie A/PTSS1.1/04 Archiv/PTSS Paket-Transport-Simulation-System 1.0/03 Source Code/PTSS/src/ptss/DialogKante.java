package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:
 * Erstellt Kanten Dialog</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

class DialogKante extends JDialog {
  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel2 = new JPanel();
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JPanel jPanel3 = new JPanel();
  private JPanel jPanel4 = new JPanel();
  private JLabel NameKB = new JLabel();
  private JLabel labelKB = new JLabel();
  private JLabel labelKA = new JLabel();
  private JButton ÄndernKnotenB = new JButton();
  private JButton ÄndernKnotenA = new JButton();
  private JTextField nameText = new JTextField();
  private JTextField jTextField3 = new JTextField();
  private JTextField jTextField2 = new JTextField();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JLabel NameKA = new JLabel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private Border border1;
  private TitledBorder titledBorder1;
  private Border border2;
  private TitledBorder titledBorder2;

  private DialogKnoten diaKnoten;
  private boolean okClicked = false;
  private String KanteName;
  private int GewA =0;
  private int GewB =0;
  private KnotenListe knotenListe;


  public DialogKante(KnotenListe k) {
    knotenListe = k;
    diaKnoten = new DialogKnoten(k);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),"Knoten Eigenschaften");
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),"Kante Eigenschaften");
    this.getContentPane().setLayout(borderLayout1);
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText("Abbrechen");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout1);
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Eigenschaften von Kante");
    jPanel2.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanel2.setPreferredSize(new Dimension(300, 250));
    NameKB.setFont(new java.awt.Font("Monospaced", 0, 12));
    NameKB.setText("NameKB");
    labelKB.setPreferredSize(new Dimension(65, 17));
    labelKB.setText("Knoten B:");
    labelKA.setPreferredSize(new Dimension(65, 17));
    labelKA.setText("Knoten A:");
    ÄndernKnotenB.setText("Ändern");
    ÄndernKnotenB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ÄndernKnotenB_actionPerformed(e);
      }
    });
    ÄndernKnotenA.setText("Ändern");
    ÄndernKnotenA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ÄndernKnotenA_actionPerformed(e);
      }
    });
    nameText.setFont(new java.awt.Font("DialogInput", 0, 12));
    nameText.setMinimumSize(new Dimension(100, 21));
    nameText.setPreferredSize(new Dimension(100, 21));
    nameText.setText(" ");
    jTextField3.setFont(new java.awt.Font("DialogInput", 0, 12));
    jTextField3.setMinimumSize(new Dimension(30, 21));
    jTextField3.setPreferredSize(new Dimension(30, 21));
    jTextField3.setText(Integer.toString(GewA));
    jTextField2.setFont(new java.awt.Font("DialogInput", 0, 12));
    jTextField2.setMinimumSize(new Dimension(30, 21));
    jTextField2.setPreferredSize(new Dimension(30, 21));
    jTextField2.setText(Integer.toString(GewB));
    jLabel3.setText("Name:");
    jLabel2.setText("Gew. Knoten A nach B");
    jLabel1.setText("Gew. Knoten B nach A");
    NameKA.setFont(new java.awt.Font("Monospaced", 0, 12));
    NameKA.setMaximumSize(new Dimension(75, 17));
    NameKA.setMinimumSize(new Dimension(75, 17));
    NameKA.setPreferredSize(new Dimension(75, 17));
    NameKA.setText("NameKA");
    jPanel3.setLayout(gridBagLayout2);
    jPanel4.setLayout(gridBagLayout3);
    jPanel3.setBorder(titledBorder1);
    jPanel4.setBorder(titledBorder2);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(jButton1, null);
    jPanel1.add(jButton2, null);
    this.getContentPane().add(jPanel2,  BorderLayout.CENTER);
    jPanel2.add(jPanel3,                            new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 15), 0, 0));
    jPanel3.add(ÄndernKnotenA,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(labelKB,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel3.add(NameKA,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel3.add(NameKB,                   new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    jPanel3.add(labelKA,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(ÄndernKnotenB,         new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel2.add(jPanel4,                             new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(30, 30, 0, 0), 0, 0));
    jPanel4.add(jLabel3,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
    jPanel4.add(jLabel2,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
    jPanel4.add(jLabel1,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    jPanel4.add(jTextField3,    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel4.add(jTextField2,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel4.add(nameText,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
  }

  public void setKanteNameText(String t){
    nameText.setText(t);
//    System.out.println(" text; " +t);
  }

  public void setKnotenAName(String s){
    NameKA.setText(s);
  }

  public void setKnotenBName(String s){
    NameKB.setText(s);
  }

  public void setGewA(int i){
   GewA = i;
   jTextField3.setText(Integer.toString(GewA));
  }

  public void setGewB(int i){
   GewB = i;
   jTextField2.setText(Integer.toString(GewB));
  }

  public int getGewA(){
    return GewA;
  }

  public int getGewB(){
   return GewB;
 }
 public boolean getOk(){
   return okClicked;
 }
 public String getName(){
   return KanteName;
 }
  public void showKnotenDia (int KnotenId){
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      diaKnoten = new DialogKnoten(knotenListe);

      diaKnoten.setSize(240,320);
      diaKnoten.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);

      // setze werte mit werten aus Objekt
        diaKnoten.setKnotenName(knotenListe.getKnotenById(KnotenId).getName());
        diaKnoten.setEventAnzahl(knotenListe.getKnotenById(KnotenId).getEventAnzahl());
        diaKnoten.setEventProEinheit(knotenListe.getKnotenById(KnotenId).getEventProEinheit());

        diaKnoten.show();

        // setze werte mit werten aus Dialog
        knotenListe.getKnotenById(KnotenId).setEventAnzahl(diaKnoten.getEventAnzahl());
        knotenListe.getKnotenById(KnotenId).setEventProEinheit(diaKnoten.getEventProEinheit());



      if (diaKnoten.getOK()){
        knotenListe.getKnotenById(KnotenId).setName(diaKnoten.getKnotenName());
        if (diaKnoten.getStart()) {
 //         knotenListe.updateStart();
          knotenListe.getKnotenById(KnotenId).setStart();
        }
        if (diaKnoten.getZiel()) {
 //         knotenListe.updateZiel();
          knotenListe.getKnotenById(KnotenId).setZiel();
        }
      }

  }

  void jButton1_actionPerformed(ActionEvent e) {
    okClicked = true;
       // Eingaben überprüfen ...
       KanteName = nameText.getText();
       //int überprüfen
         try{
           GewA = Integer.parseInt(jTextField3.getText());
           GewB = Integer.parseInt(jTextField2.getText());
           this.dispose();
         }catch(NumberFormatException x) {
           JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Zahl ein");
         }

  }

  void jButton2_actionPerformed(ActionEvent e) {
    okClicked = false;
    this.dispose();
  }

  void ÄndernKnotenA_actionPerformed(ActionEvent e) {
    showKnotenDia (knotenListe.getKnotenByName(NameKA.getText()));
    NameKA.setText(diaKnoten.getKnotenName());
  }

  void ÄndernKnotenB_actionPerformed(ActionEvent e) {
    showKnotenDia (knotenListe.getKnotenByName(NameKB.getText()));
    NameKB.setText(diaKnoten.getKnotenName());
  }
}