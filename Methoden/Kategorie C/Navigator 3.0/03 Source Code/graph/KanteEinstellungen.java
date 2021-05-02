package graph;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class KanteEinstellungen extends JDialog {

  Kante myKante;
  JPanel jPanel5 = new JPanel();
  JPanel jPanel2 = new JPanel();
  XYLayout xYLayout3 = new XYLayout();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField1 = new JTextField();
  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JButton jButton4 = new JButton();
  JLabel jLabel3 = new JLabel();
  JButton jButton3 = new JButton();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel jLabel7 = new JLabel();
  JPanel jPanel1 = new JPanel();
  JButton jButton2 = new JButton();
  JButton jButton1 = new JButton();

  public KanteEinstellungen(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public KanteEinstellungen() {
    this(null, "", false);
  }

  public KanteEinstellungen(Kante tmp) {
    this(null, "", false);
    myKante=tmp;
  }

  void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    jPanel5.setBackground(Color.lightGray);
    this.setForeground(Color.orange);
    jPanel2.setLayout(xYLayout3);
    jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        jTextField1_keyTyped(e);
      }
    });
    panel1.setLayout(xYLayout1);
    jLabel6.setText("jLabel6");
    jLabel5.setText("jLabel5");
    jLabel4.setText("Gewichtung von B nach A");
    jButton4.setText("Knoteneigenschaften");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jLabel3.setText("Gewichtung von A nach B");
    jButton3.setText("Knoteneigenschaften");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton3_actionPerformed(e);
      }
    });
    jLabel2.setText("Knoten B");
    jLabel1.setText("Knoten A");
    jLabel7.setForeground(Color.red);
    jLabel7.setToolTipText("");
    jLabel7.setText("   ");
    jButton2.setText("Abbrechen");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(panel1,       new XYConstraints(7, 12, 427, 164));
    panel1.add(jLabel6, new XYConstraints(92, 63, -1, -1));
    panel1.add(jLabel5, new XYConstraints(92, 34, -1, -1));
    panel1.add(jButton3,  new XYConstraints(226, 28, 153, -1));
    panel1.add(jButton4,   new XYConstraints(225, 58, 154, -1));
    panel1.add(jLabel3, new XYConstraints(25, 108, -1, -1));
    panel1.add(jTextField1,   new XYConstraints(202, 105, 177, -1));
    panel1.add(jTextField2,    new XYConstraints(202, 131, 177, -1));
    panel1.add(jLabel4,  new XYConstraints(25, 136, -1, -1));
    panel1.add(jLabel2,   new XYConstraints(26, 63, -1, -1));
    panel1.add(jLabel1,    new XYConstraints(27, 34, -1, -1));
    jPanel2.add(jPanel1,   new XYConstraints(10, 193, 422, -1));
    jPanel1.add(jButton1, null);
    jPanel1.add(jButton2, null);
    this.getContentPane().add(jPanel5, BorderLayout.SOUTH);
    jPanel5.add(jLabel7, null);
  }

  void jButton1_actionPerformed(ActionEvent e) {
    //werte speichern
    Integer myInt = new Integer("1");
    int m1,m2;
    try {
      m1=myInt.parseInt(jTextField1.getText());
      m2=myInt.parseInt(jTextField2.getText());
    }
    catch (Exception ex) {
      jLabel7.setText("Bitte geben Sie eine Zahl ein für die Gewichtung.");
      return;
    }
    if ((m1<0) || (m2<0)) {
      jLabel7.setText("Die Gewichtungen müssen >= Null sein.");
      return;
    }
    myKante.g1 = myInt.parseInt(jTextField1.getText());
    myKante.g2 = myInt.parseInt(jTextField2.getText());

    this.setVisible(false);
  }

  void jButton2_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void jCheckBox1_actionPerformed(ActionEvent e) {
  }

  void jCheckBox2_actionPerformed(ActionEvent e) {
  }


  void jButton3_actionPerformed(ActionEvent e) {
    //Eigenschaften des Knoten A
    myKante.a.showPropertyPage();

    String tmpStr = new String(" ");
    myKante.a.thePropertyDialog.jTextField1.setText(myKante.a.name);
    myKante.a.thePropertyDialog.jTextField2.setText(tmpStr.valueOf(myKante.a.id));
    myKante.a.thePropertyDialog.setTitle("Einstellungen für diesen Knoten");
    myKante.a.thePropertyDialog.jLabel3.setText(" ");
    myKante.a.thePropertyDialog.setOriId(myKante.a.id);

    jLabel5.setText(myKante.a.name + " (x" + myKante.a.id + ")");
    jLabel4.setText("Gewichtung von x" + myKante.b.id + " nach x"+ myKante.a.id );
    jLabel3.setText("Gewichtung von x" +  myKante.a.id + " nach x"+ myKante.b.id );
  }

  void jButton4_actionPerformed(ActionEvent e) {
    myKante.b.showPropertyPage();

    String tmpStr = new String(" ");
    myKante.b.thePropertyDialog.jTextField1.setText(myKante.b.name);
    myKante.b.thePropertyDialog.jTextField2.setText(tmpStr.valueOf(myKante.b.id));
    myKante.b.thePropertyDialog.setTitle("Einstellungen für diesen Knoten");
    myKante.b.thePropertyDialog.jLabel3.setText(" ");
    myKante.b.thePropertyDialog.setOriId(myKante.b.id);

    jLabel6.setText(myKante.b.name + " (x" + myKante.b.id + ")");
    jLabel4.setText("Gewichtung von x" +  myKante.b.id + " nach x"+ myKante.a.id );
    jLabel3.setText("Gewichtung von x" +  myKante.a.id + " nach x"+ myKante.b.id );
  }
  void jTextField1_keyPressed(KeyEvent e) {

  }
  void jTextField1_keyReleased(KeyEvent e) {

  }
  void jTextField1_keyTyped(KeyEvent e) {

  }
}