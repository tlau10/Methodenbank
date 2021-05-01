package graph;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Route extends JDialog {

  CDataSource ls_helper2;
  CDataSource ls_helper3;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JPanel jPanel1 = new JPanel();

  ElementController ec;
  Frame1 mainform;
  JLabel jLabel4 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();

  public Route(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Route() {
    this(null, "", false);
  }

  public Route(Frame1 m) {
    this(null, "", false);
    ec = m.jPanel1;
    mainform = m;
    ls_helper2 = new CDataSource(m);
    ls_helper3 = new CDataSource(this,m);
  }

/////////////////////////////////////////////////////////////////

/*  public Route(Frame1 m) {
    this(null, "", false);
    ec = m.jPanel1;
    mainform = m;
    ls_helper2 = new CDataSource(m);
  }*/
//////////////////////////////////////////////////////////////////

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    jButton1.setText("Route berechnen");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText("Schliessen");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jLabel1.setToolTipText("");
    jLabel1.setText("Start");
    jLabel2.setText("Ziel");
    jLabel3.setText("Ergebnis");
    jComboBox1.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jComboBox1_focusGained(e);
      }
    });
    jComboBox2.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jComboBox2_focusGained(e);
      }
    });
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox2_actionPerformed(e);
      }
    });
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboBox1_actionPerformed(e);
      }
    });
    jLabel4.setForeground(Color.red);
    jLabel4.setText("   ");
    jScrollPane1.getViewport().setBackground(Color.white);
    getContentPane().add(panel1);
    panel1.add(jComboBox1,   new XYConstraints(59, 54, 183, -1));
    panel1.add(jComboBox2,     new XYConstraints(59, 79, 183, -1));
    panel1.add(jLabel2, new XYConstraints(23, 81, -1, -1));
    panel1.add(jLabel1,  new XYConstraints(23, 57, -1, -1));
    panel1.add(jButton1,     new XYConstraints(22, 117, 130, -1));
    panel1.add(jButton2, new XYConstraints(18, 374, -1, -1));
    panel1.add(jPanel1,  new XYConstraints(0, 408, 551, 18));
    panel1.add(jLabel4,     new XYConstraints(24, 160, 234, 23));
    panel1.add(jLabel3, new XYConstraints(266, 20, -1, -1));
    panel1.add(jScrollPane1,   new XYConstraints(266, 52, 269, 357));
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.setTitle("Route");
  }

  //schliessen button
  void jButton2_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  //berechnen button
  void jButton1_actionPerformed(ActionEvent e) {

    File klarergfile = new File("c:/temp/end_navigat.out");
    int size = (int)klarergfile.length();
    int chars_read = 0;

    char[] ergklar = new char[size];
    String klarErgebnis = "";

    boolean isBerechnungOK;

    String tmpKnotenID = null;

    File lpbatfile = new File ("c:/temp/lpsolve.bat");
    File batfile = new File ("c:/temp/navigat.bat");
    String tmpStartOrt = "";
    String tmpZielOrt = "";

    // werte aus dem ini file ausgelesen.
    ConfigFile myconfigFile = new ConfigFile(System.getProperty("user.dir")+"\\Navigat.ini");
    String navigatBat = myconfigFile.getValue("perlpath") + " " + System.getProperty("user.dir") + "\\navigat.pl";
    String navigatBatEnde = System.getProperty("user.dir")+"\\Navigat.ini";

    jLabel4.setText(" ");
    if (jComboBox1.getSelectedIndex() == jComboBox2.getSelectedIndex()) {
      jLabel4.setText("Start und Ziel sind gleich.");
      return;
    }

    if (checkCalculatable()==true) {

      ls_helper2.vorbBearbeitung();

      try
      {
        tmpStartOrt = (jComboBox1.getItemAt(jComboBox1.getSelectedIndex())).toString() ;
        tmpZielOrt = (jComboBox2.getItemAt(jComboBox2.getSelectedIndex())).toString();
      }
      catch (java.lang.NullPointerException notarget)
      {
        jLabel4.setText("bitte einen Zielknoten wählen.");
      }

      tmpKnotenID = ec.findByName(tmpStartOrt);

      navigatBat=navigatBat+" "+tmpStartOrt+" "+tmpZielOrt+" "+navigatBatEnde;

      try
      {
        FileWriter outlpbat = new FileWriter(lpbatfile);
        outlpbat.write("L:\\Besf\\solver\\lp_solve\\exec\\lp_solve -p < C:\\temp\\navigat.lp > C:\\temp\\navigat.out");
        outlpbat.close();
      }
      catch (IOException ioex) {
        ioex.printStackTrace();
      }

      //externes programm aufrufen
      try {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(navigatBat);
        p.waitFor();
      } catch (Exception ex) {
        System.out.println("Der Solver kann nicht ausgeführt werden, prüfen Sie die Einstellungen in der Navigat.ini");
      }

      //rückgabe auswerten
      isBerechnungOK = ls_helper3.ausgabeBerechnung(tmpKnotenID);
      if (isBerechnungOK == false)
      {
        jTextArea1.setText("von "+tmpStartOrt+" führt keine Route weg. Sackgasse!");
      }
//      this.repaint();

    }
    else {
      jLabel4.setText("Netz enthält Kanten ohne Gewichtung.");
    }
  }

  public Object makeObj(final String item)  {
    return new Object() { public String toString() { return item; } };
  }

  void jComboBox1_focusGained(FocusEvent e) {
    //das control bekommt den focus
    jComboBox1.removeAllItems();
    for (int i=0;i<ec.eList.size();i++) {
      if (((Element)ec.eList.get(i)).getType()==1) {
        //knoten
        jComboBox1.addItem(makeObj(((Knoten)ec.eList.get(i)).getName())) ;
      }
    }
  }

  void jComboBox2_focusGained(FocusEvent e) {
    jComboBox2.removeAllItems();

    for (int i=0;i<ec.eList.size();i++) {
      if (((Element)ec.eList.get(i)).getType()==1) {
        //knoten
        jComboBox2.addItem(makeObj(((Knoten)ec.eList.get(i)).getName())) ;
      }
    }
  }

  //prüft ob alle verbindungen mind. eine gewichtung haben
  private boolean checkCalculatable() {
    for (int i=0;i<ec.eList.size();i++) {
      if (((Element)ec.eList.get(i)).getType()==2) {
        //kante
        if ((((Kante)ec.eList.get(i)).g1==0) && (((Kante)ec.eList.get(i)).g2==0)) return false;
      }
    }
    return true;
  }

  void jComboBox2_actionPerformed(ActionEvent e) {
  }

  void jComboBox1_actionPerformed(ActionEvent e) {
  }
}