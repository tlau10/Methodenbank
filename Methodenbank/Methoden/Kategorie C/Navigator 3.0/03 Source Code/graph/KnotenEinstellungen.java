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

public class KnotenEinstellungen extends JDialog {
  int oriId;

  Knoten myKnoten;
  JPanel jPanel1 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JButton jButton2 = new JButton();
  JButton jButton1 = new JButton();
  XYLayout xYLayout4 = new XYLayout();
  JPanel panel1 = new JPanel();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel6 = new JLabel();
  XYLayout xYLayout3 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();

  public KnotenEinstellungen(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public KnotenEinstellungen() {
    this(null, "", false);
  }

  public KnotenEinstellungen(Knoten tmp) {
    this(null, "", false);
    myKnoten=tmp;
  }

  void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    jLabel3.setForeground(Color.red);
    jPanel1.setBackground(SystemColor.activeCaptionBorder);
    jPanel1.setMinimumSize(new Dimension(400, 30));
    jPanel1.setPreferredSize(new Dimension(400, 30));
    this.getContentPane().setBackground(Color.red);
    this.setModal(true);
    jPanel2.setBackground(SystemColor.text);
    jPanel2.setLayout(xYLayout4);
    jPanel3.setMinimumSize(new Dimension(145, 40));
    jPanel3.setPreferredSize(new Dimension(145, 40));
    jPanel3.setLayout(flowLayout1);
    jButton2.setText("Abbruch");
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
    panel1.setLayout(xYLayout3);
    panel1.setMinimumSize(new Dimension(172, 50));
    panel1.setPreferredSize(new Dimension(172, 50));
    jTextField2.setMinimumSize(new Dimension(10, 10));
    jTextField2.setPreferredSize(new Dimension(10, 10));
    jTextField1.setMaximumSize(new Dimension(10, 10));
    jTextField1.setMinimumSize(new Dimension(10, 10));
    jTextField1.setPreferredSize(new Dimension(10, 10));
    jLabel6.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel6.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel6.setText("Knotennummer");
    jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel1.setText("Name");
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel3.add(jButton1, null);
    jPanel3.add(jButton2, null);
    jPanel2.add(panel1,     new XYConstraints(10, 12, 361, 98));
    panel1.add(jLabel6,  new XYConstraints(22, 15, 97, 26));
    panel1.add(jLabel1, new XYConstraints(22, 44, 87, 22));
    panel1.add(jTextField2, new XYConstraints(120, 18, 50, 25));
    panel1.add(jTextField1, new XYConstraints(120, 45, 208, 25));
    jPanel2.add(jPanel3,        new XYConstraints(10, 120, 361, -1));
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(jLabel3, null);
  }

  public void setOriId(int tmp) {
    oriId=tmp;
  }

  void jButton1_actionPerformed(ActionEvent e) {
    //Einstellungen speichern
    if (jTextField2.getText().length() ==0) {
      jLabel3.setText("Bitte geben Sie eine Knotennummer ein.");
      return;
    }
    try {
      Integer myInt = new Integer(0);
      if (myInt.parseInt(jTextField2.getText()) < 0) {
        jLabel3.setText("Bitte geben Sie eine Knotennummer ein, die >= Null ist.");
        return;
      }

      if ((myKnoten.myEc.isIdUsed(myInt.parseInt(jTextField2.getText()))==true) && (myInt.parseInt(jTextField2.getText()) != oriId)) {
        jLabel3.setText("Knotennummer schon vergeben");
        return;
      }
      else {
        myKnoten.id = myInt.parseInt(jTextField2.getText());    // id speichern
      }
    }
    catch (Exception ex) {
      //ex.printStackTrace();
      jLabel3.setText("Bitte geben Sie eine numerische Knotennummer ein.");
      return;
    }
    myKnoten.name = jTextField1.getText();    //name speichern
    this.setVisible(false);
  }

  void jButton2_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}