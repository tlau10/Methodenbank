package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: Erstellt Knoten Dialog</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */


import javax.swing.*;
import java.util.Enumeration;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

class DialogKnoten extends JDialog{
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JButton abbrButton = new JButton();
  private JButton okButton = new JButton();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JPanel jPanel3 = new JPanel();
  private JLabel labelName = new JLabel();
  private JTextField knotenName = new JTextField();
  private Border border1;
  private ButtonGroup buttonGroup1 = new ButtonGroup();
//  private ButtonGroup buttonGroup2 = new ButtonGroup();

  private boolean button1State = false;
  private boolean button2State = false;
  private boolean button3State = false;

  private boolean okClicked = false;
  private String knotenNameStr;
  private KnotenListe kListe;
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JRadioButton jRadioButton1 = new JRadioButton();
  private JRadioButton jRadioButton2 = new JRadioButton();
  private JRadioButton deSelect = new JRadioButton();
  private Border border2;
  private TitledBorder titledBorder1;
  private Border border3;
  private TitledBorder titledBorder2;
  private JTextField eventProEinheit_Text = new JTextField();
  private JTextField eventAnzahl_Text = new JTextField();
  private JLabel eventProEinheit = new JLabel();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JLabel eventAnzahl = new JLabel();
  private JPanel jPanel4 = new JPanel();

  private int eventAnzahl_int;
  private int eventproEinheit_int;

  public DialogKnoten(KnotenListe k) {
    kListe = k;

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    border1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),"Ändern");
    border2 = BorderFactory.createLineBorder(Color.black,2);
    titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"Angaben zur Simulation");
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142));
    titledBorder2 = new TitledBorder(border3,"Angaben zur Simulation");
    this.getContentPane().setLayout(borderLayout1);
    abbrButton.setText("Abbrechen");
    abbrButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        abbrButton_actionPerformed(e);
      }
    });
    okButton.setNextFocusableComponent(abbrButton);
    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    jPanel1.setLayout(gridBagLayout1);
    labelName.setText("Name:");
    knotenName.setFont(new java.awt.Font("DialogInput", 0, 12));
    knotenName.setMinimumSize(new Dimension(100, 21));
    knotenName.setPreferredSize(new Dimension(100, 21));
    knotenName.setText("k1");
    jPanel3.setBorder(border1);
    jPanel3.setMinimumSize(new Dimension(200, 140));
    jPanel3.setPreferredSize(new Dimension(200, 140));
    jPanel3.setToolTipText("");
    jPanel3.setLayout(gridBagLayout2);
    jPanel1.setBorder(BorderFactory.createLoweredBevelBorder());
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Eigenschaften von Knoten");
    jRadioButton1.setText("Start Knoten");
    jRadioButton1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jRadioButton1_actionPerformed(e);
      }
    });
    jRadioButton2.setText("Ziel Knoten");
    jRadioButton2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jRadioButton2_actionPerformed(e);
      }
    });
    deSelect.setToolTipText("");
    deSelect.setText("Selektion aufheben");
    deSelect.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deSelect_actionPerformed(e);
      }
    });
    eventProEinheit_Text.setPreferredSize(new Dimension(39, 21));
    eventProEinheit_Text.setText("5");
    eventAnzahl_Text.setPreferredSize(new Dimension(39, 21));
    eventAnzahl_Text.setText("1000");
    eventProEinheit.setPreferredSize(new Dimension(100, 17));
    eventProEinheit.setText("Events pro Einh. :");
    eventAnzahl.setPreferredSize(new Dimension(100, 17));
    eventAnzahl.setText("Event Anzahl:");
    jPanel4.setLayout(gridBagLayout3);
    jPanel4.setBorder(titledBorder2);
    jPanel4.setMinimumSize(new Dimension(200, 85));
    jPanel4.setPreferredSize(new Dimension(200, 85));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    this.getContentPane().add(jPanel2,  BorderLayout.SOUTH);
    jPanel2.add(okButton, null);
    jPanel2.add(abbrButton, null);
    jPanel1.add(jPanel3,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jRadioButton1,                      new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 30, 0, 0), 0, 0));
    jPanel3.add(jRadioButton2,                 new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 30, 0, 0), 0, 0));
    jPanel3.add(knotenName,               new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(labelName,           new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    jPanel3.add(deSelect,                 new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 30, 0, 0), 0, 0));
    jPanel1.add(jPanel4,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    jPanel4.add(eventAnzahl,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 0, 0, 0), 0, 0));
    jPanel4.add(eventProEinheit,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel4.add(eventAnzahl_Text,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 10, 0, 0), 0, 0));
    jPanel4.add(eventProEinheit_Text,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));

    buttonGroup1.add(jRadioButton1);
    buttonGroup1.add(jRadioButton2);
    buttonGroup1.add(deSelect);

    jRadioButton1.setSelected(button1State);
    jRadioButton2.setSelected(button2State);

  }

  public void setKnotenName(String s){
    knotenNameStr = s;
    knotenName.setText(s);
  }

  public String getKnotenName(){
    return knotenNameStr;
  }
  public boolean getOK (){
    return okClicked;
  }

  void okButton_actionPerformed(ActionEvent e) {
    okClicked = true;
    if (kListe.getKnotenByName(knotenName.getText()) == -1){ // Name schon vorhanden ?
      knotenNameStr = knotenName.getText();
      checkSimuEingaben();
    }
    else
      if ( knotenNameStr.compareTo(knotenName.getText()) == 0) // eigener Name ...
      checkSimuEingaben();
    else
      JOptionPane.showMessageDialog(null, "Knoten Name schon vergeben");
  }

  void abbrButton_actionPerformed(ActionEvent e) {
    okClicked = false;
    this.dispose();
  }

  void jRadioButton1_actionPerformed(ActionEvent e)
  {
    button1State = true;
  }

  void jRadioButton2_actionPerformed(ActionEvent e)
  {
    button2State = true;
  }

  public boolean getStart(){
    return button1State;
  }
  public boolean getZiel(){
    return button2State;
  }

  void checkSimuEingaben(){
    try{
      eventAnzahl_int = Integer.parseInt(eventAnzahl_Text.getText());
      eventproEinheit_int = Integer.parseInt(eventProEinheit_Text.getText());
      this.dispose();
    }catch(NumberFormatException x) {
      JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Zahl bei Simulationsangaben ein");
    }
  }

  void deSelect_actionPerformed(ActionEvent e) {

    kListe.getKnotenById(kListe.getKnotenByName(knotenNameStr)).unSetStart();
    kListe.getKnotenById(kListe.getKnotenByName(knotenNameStr)).unSetZiel();
  }

  void setEventAnzahl(int i){
    this.eventAnzahl_int = i;
    this.eventAnzahl_Text.setText(Integer.toString(i));
  }

  void setEventProEinheit(int i){
    this.eventproEinheit_int = i;
    this.eventProEinheit_Text.setText(Integer.toString(i));
  }
  int getEventAnzahl(){
    return eventAnzahl_int;
  }
  int getEventProEinheit(){
    return eventproEinheit_int;
  }

}