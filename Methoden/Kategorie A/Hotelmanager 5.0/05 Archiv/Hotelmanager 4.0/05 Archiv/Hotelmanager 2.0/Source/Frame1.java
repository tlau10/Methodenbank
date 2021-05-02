package hotelbelegung;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.lang.*;
import java.util.*;
import java.text.*;

/**
 * Überschrift:   Hotelbelegung
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author Volker Wohlleber
 * @version 1.0
 */

public class Frame1 extends JFrame {
  private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");
  private DateFormat compareFormat = new SimpleDateFormat("yyyyMMdd");
  private Date aktDatum = new Date();
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel Reservierung = new JPanel();
  JPanel jPanel1 = new JPanel();
  JTextField jTextField1 = new JTextField();
  JPanel jPanel2 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  JPanel jPanel3 = new JPanel();
  Border border2;
  TitledBorder titledBorder2;
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JButton jButton4 = new JButton();
  JComboBox jComboBox2 = new JComboBox();
  JComboBox jComboBox3 = new JComboBox();
  JComboBox jComboBox4 = new JComboBox();
  Border border3;
  JPanel jPanel4 = new JPanel();
  Border border4;
  TitledBorder titledBorder3;
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JPanel Einstellungen = new JPanel();
  Border border5;
  TitledBorder titledBorder4;
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JButton jButtonEinst = new JButton();
  JTextField jTextFieldSolver = new JTextField();
  JTextField jTextFieldArbeit = new JTextField();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  Border border6;
  TitledBorder titledBorder5;
  JPanel Belegung = new JPanel();  // Panel für graphische Darstellung
  JTextField jTextFieldBelegung = new JTextField();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JPanel jPanel7 = new JPanel();
  JTextField jTextFieldPreis = new JTextField();

  Solver mySolver = new LP_Solve();
  Belegung myBelegung = new Belegung();
  Manager myManager = new Manager(mySolver,myBelegung);

  /**Den Frame konstruieren*/
  public Frame1() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Initialisierung der Komponenten*/
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Ihr Symbol]")));
    contentPane = (JPanel) this.getContentPane();
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(border1,"Heutiges Datum");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Reservierungswunsch");
    border3 = BorderFactory.createEmptyBorder();
    border4 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Parameter");
    border5 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder4 = new TitledBorder(border5,"Einstellungen");
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(800, 600));
    this.setTitle("Hotel-Manager");
    jMenuFile.setText("Datei");
    jMenuFileExit.setText("Beenden");
    jMenuFileExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileExit_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Hilfe");
    jMenuHelpAbout.setText("Info");
    jMenuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    jTabbedPane1.setBackground(new Color(149, 195, 206));
    jTabbedPane1.setFont(new java.awt.Font("SansSerif", 1, 14));
    Reservierung.setBackground(new Color(212, 225, 223));
    Reservierung.setBorder(border3);
    Reservierung.setLayout(null);
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setPreferredSize(new Dimension(100, 100));
    jPanel1.setBounds(new Rectangle(13, 14, 517, 459));
    jPanel1.setLayout(null);
    jTextField1.setFont(new java.awt.Font("SansSerif", 1, 16));
    jTextField1.setText("  " + outFormat.format(aktDatum));
    jTextField1.setEditable(false);
    jTextField1.setBounds(new Rectangle(26, 37, 107, 29));
    jPanel2.setBorder(titledBorder1);
    jPanel2.setBounds(new Rectangle(28, 24, 454, 92));
    jPanel2.setLayout(null);
    jPanel3.setBorder(titledBorder2);
    jPanel3.setBounds(new Rectangle(28, 254, 455, 139));
    jPanel3.setLayout(null);
    jLabel1.setText("Anreisedatum:");
    jLabel1.setBounds(new Rectangle(28, 33, 115, 29));
    jLabel2.setText("Aufenthaltsdauer:");
    jLabel2.setBounds(new Rectangle(27, 89, 124, 26));
    jComboBox1.setBounds(new Rectangle(137, 91, 121, 24));
    jButton4.setBackground(UIManager.getColor("info"));
    jButton4.setFont(new java.awt.Font("Dialog", 1, 14));
    jButton4.setText("Anfrage starten");
    jButton4.setBounds(new Rectangle(310, 405, 170, 35));
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jComboBox2.setBounds(new Rectangle(137, 37, 61, 23));
    jComboBox3.setBounds(new Rectangle(214, 37, 112, 23));
    jComboBox4.setBounds(new Rectangle(342, 37, 82, 23));
    jPanel4.setBorder(titledBorder3);
    jPanel4.setBounds(new Rectangle(28, 127, 454, 112));
    jPanel4.setLayout(null);
    jPanel5.setBorder(BorderFactory.createEtchedBorder());
    jPanel5.setBounds(new Rectangle(13, 14, 515, 452));
    jPanel5.setLayout(null);
    jPanel6.setBorder(titledBorder3);
    jPanel6.setBounds(new Rectangle(37, 24, 430, 201));
    jPanel6.setLayout(null);
    jButtonEinst.setBackground(SystemColor.info);
    jButtonEinst.setFont(new java.awt.Font("Dialog", 1, 14));
    jButtonEinst.setText("Übernehmen");
    jButtonEinst.setBounds(new Rectangle(338, 401, 130, 32));
    jButtonEinst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonEinst_actionPerformed(e);
      }
    });
    jLabel6.setText("Arbeitsverzeichnis:");
    jLabel6.setBounds(new Rectangle(34, 120, 117, 16));
    jTextFieldSolver.setText("L:\\Besf\\Solver\\");
    jTextFieldSolver.setBounds(new Rectangle(34, 63, 357, 27));
    jLabel5.setText("Solverpfad:");
    jLabel5.setBounds(new Rectangle(35, 38, 81, 17));
    jTextFieldArbeit.setText("C:\\Temp\\");
    jTextFieldArbeit.setBounds(new Rectangle(35, 144, 355, 27));
    jTextFieldBelegung.setBounds(new Rectangle(241, 34, 99, 27));
    jTextFieldBelegung.setText("300");
    jLabel9.setBounds(new Rectangle(67, 91, 198, 16));
    jLabel9.setText("Standard Zimmerpreis in €:");
    jLabel10.setBounds(new Rectangle(67, 41, 191, 17));
    jLabel10.setText("Maximale Hotelbelegung:");
    jPanel7.setLayout(null);
    jPanel7.setBounds(new Rectangle(38, 237, 431, 138));
    jPanel7.setBorder(titledBorder3);
    jTextFieldPreis.setText("100");
    jTextFieldPreis.setBounds(new Rectangle(241, 84, 98, 27));
    jLabel3.setText("Maximale Hotelbelegung:");
    jLabel3.setBounds(new Rectangle(25, 29, 164, 21));
    jLabel4.setText("Standard Zimmerpreis:");
    jLabel4.setBounds(new Rectangle(25, 67, 157, 19));
    jTextField2.setText(jTextFieldBelegung.getText());
    jTextField2.setEnabled(false);
    jTextField2.setBounds(new Rectangle(192, 29, 74, 22));
    jTextField3.setText(jTextFieldPreis.getText());
    jTextField3.setEnabled(false);
    jTextField3.setBounds(new Rectangle(192, 65, 75, 22));
    contentPane.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(Reservierung, "Reservierung");
    Reservierung.add(jPanel1, null);
    jPanel1.add(jPanel2, null);
    jPanel2.add(jTextField1, null);
    jPanel1.add(jPanel4, null);
    jPanel4.add(jLabel3, null);
    jPanel4.add(jTextField2, null);
    jPanel4.add(jTextField3, null);
    jPanel4.add(jLabel4, null);
    jPanel1.add(jPanel3, null);
    jPanel3.add(jComboBox3, null);
    jPanel3.add(jComboBox4, null);
    jPanel3.add(jComboBox1, null);
    jPanel3.add(jLabel2, null);
    jPanel3.add(jLabel1, null);
    jPanel3.add(jComboBox2, null);
    jPanel1.add(jButton4, null);
    jPanel6.add(jTextFieldSolver, null);
    jPanel6.add(jLabel5, null);
    jPanel6.add(jLabel6, null);
    jPanel6.add(jTextFieldArbeit, null);
    jPanel5.add(jPanel7, null);
    jPanel7.add(jTextFieldBelegung, null);
    jPanel7.add(jTextFieldPreis, null);
    jPanel7.add(jLabel10, null);
    jPanel7.add(jLabel9, null);
    jPanel5.add(jButtonEinst, null);
    jPanel5.add(jPanel6, null);
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);

    jComboBox1.addItem("Anzahl der Tage");
    jComboBox1.addItem("----------------------------");
    jComboBox1.addItem("1");
    jComboBox1.addItem("2");
    jComboBox1.addItem("3");
    jComboBox1.addItem("4");
    jComboBox1.addItem("5");
    jComboBox1.addItem("6");
    jComboBox1.addItem("7");

    jComboBox2.addItem("Tag");
    jComboBox2.addItem("----------------");
    jComboBox2.addItem("1");
    jComboBox2.addItem("2");
    jComboBox2.addItem("3");
    jComboBox2.addItem("4");
    jComboBox2.addItem("5");
    jComboBox2.addItem("6");
    jComboBox2.addItem("7");
    jComboBox2.addItem("8");
    jComboBox2.addItem("9");
    jComboBox2.addItem("10");
    jComboBox2.addItem("11");
    jComboBox2.addItem("12");
    jComboBox2.addItem("13");
    jComboBox2.addItem("14");
    jComboBox2.addItem("15");
    jComboBox2.addItem("16");
    jComboBox2.addItem("17");
    jComboBox2.addItem("18");
    jComboBox2.addItem("19");
    jComboBox2.addItem("20");
    jComboBox2.addItem("21");
    jComboBox2.addItem("22");
    jComboBox2.addItem("23");
    jComboBox2.addItem("24");
    jComboBox2.addItem("25");
    jComboBox2.addItem("26");
    jComboBox2.addItem("27");
    jComboBox2.addItem("28");
    jComboBox2.addItem("29");
    jComboBox2.addItem("30");
    jComboBox2.addItem("31");

    jComboBox3.addItem("Monat");
    jComboBox3.addItem("-------------------------");
    jComboBox3.addItem("Januar");
    jComboBox3.addItem("Februar");
    jComboBox3.addItem("März");
    jComboBox3.addItem("April");
    jComboBox3.addItem("Mai");
    jComboBox3.addItem("Juni");
    jComboBox3.addItem("Juli");
    jComboBox3.addItem("August");
    jComboBox3.addItem("September");
    jComboBox3.addItem("Oktober");
    jComboBox3.addItem("November");
    jComboBox3.addItem("Dezember");

    jComboBox4.addItem("Jahr");
    jComboBox4.addItem("------------------------");
    jComboBox4.addItem("2003");
    jComboBox4.addItem("2004");
    jComboBox4.addItem("2005");
    jComboBox4.addItem("2006");
    jComboBox4.addItem("2007");
    jComboBox4.addItem("2008");

    // Einfügung der graphischen Darstellung
    Belegung.setLayout(null);
    Belegung.setBackground(new Color(212, 225, 223));
    Belegung = new ServiceLevelPanel(myManager, myBelegung, jTextField2.getText());
    jTabbedPane1.add(Belegung, "Belegung");

    // Einfügung der Parameterübersicht
    Einstellungen.setLayout(null);
    Einstellungen.setBackground(new Color(212, 225, 223));
    jTabbedPane1.add(Einstellungen, "Einstellungen");
    Einstellungen.add(jPanel5, null);
  }

  /**Aktion Datei | Beenden durchgeführt*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
  /**Aktion Hilfe | Info durchgeführt*/
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    Frame1_Infodialog dlg = new Frame1_Infodialog(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
  /**Überschrieben, so dass eine Beendigung beim Schließen des Fensters möglich ist.*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }

  /**Anfrage starten*/
  void jButton4_actionPerformed(ActionEvent e) {
  if ((jComboBox4.getSelectedIndex() == 3 || jComboBox4.getSelectedIndex() == 7) &&
       jComboBox3.getSelectedIndex() == 3 && jComboBox2.getSelectedIndex() > 30)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ungültiges Datum eingegeben. Das ist ein Schaltjahr.");
          return;
        }
  if (jComboBox2.getSelectedIndex() > 27 && jComboBox3.getSelectedIndex() == 3 &&
      jComboBox4.getSelectedIndex() != 3 && jComboBox4.getSelectedIndex() != 7)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ungültiges Datum eingegeben.");
          return;
        }
  if (jComboBox2.getSelectedIndex() == 32 && (jComboBox3.getSelectedIndex() == 5 ||
      jComboBox3.getSelectedIndex() == 7 || jComboBox3.getSelectedIndex() == 10 ||
      jComboBox3.getSelectedIndex() == 12))
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ungültiges Datum eingegeben.");
          return;
        }
  if (jComboBox2.getSelectedIndex() == 0 || jComboBox3.getSelectedIndex() == 0 ||
      jComboBox4.getSelectedIndex() == 0)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ihr ausgewähltes Datum ist unvollständig.");
          return;
        }
  if (jComboBox2.getSelectedIndex() == 1 || jComboBox3.getSelectedIndex() == 1 ||
      jComboBox4.getSelectedIndex() == 1)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ihr ausgewähltes Datum ist unvollständig.");
          return;
        }
  if (jComboBox1.getSelectedIndex() == 0 || jComboBox1.getSelectedIndex() == 1)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Aufenthaltsdauer nicht ausgewählt.");
          return;
        }
    try {
    int parseDatum = ((jComboBox4.getSelectedIndex()+2001)*10000)+((jComboBox3.getSelectedIndex()-1)*100)+(jComboBox2.getSelectedIndex()-1);
    String parseStart = String.valueOf(parseDatum);
    Date startdatum = compareFormat.parse(parseStart);
    if (Integer.parseInt(compareFormat.format(startdatum)) <= Integer.parseInt(compareFormat.format(aktDatum)))
        {
          JOptionPane.showMessageDialog(null, "Achtung: Buchungsdatum nicht möglich, bitte prüfen.");
          return;
        }
    int Preis = Integer.parseInt(jTextField3.getText());
    int MaxBelegung = Integer.parseInt(jTextField2.getText());
    if (Preis < 10 || MaxBelegung < 0) {
      JOptionPane.showMessageDialog(null, "Achtung: Kein berechenbarer Wert eingetragen, bitte anpassen.");
      this.jTabbedPane1.setSelectedComponent(this.Einstellungen);
      this.jTabbedPane1.addNotify();
      return;
    }
    myManager.setBelegung(this, startdatum, jComboBox1.getSelectedIndex()-1, Preis, MaxBelegung);
    }
    catch (ParseException f) {
      /* Ausnahme beim Parsen behandeln */
    }
  }

  /**Übernahme der geänderten Daten ins Programm*/
  void jButtonEinst_actionPerformed(ActionEvent e) {
    try {
      Integer.parseInt(this.jTextFieldBelegung.getText());
      Integer.parseInt(this.jTextFieldPreis.getText());
      this.jTextField2.setText(this.jTextFieldBelegung.getText());
      this.jTextField3.setText(this.jTextFieldPreis.getText());
      this.jTabbedPane1.setSelectedComponent(this.Reservierung);
      this.jTabbedPane1.addNotify();
    }
    catch (NumberFormatException n) {
      JOptionPane.showMessageDialog(null, "Achtung: Ein Wert ist nicht korrekt eingeben.");
      return;
    }
  }
}