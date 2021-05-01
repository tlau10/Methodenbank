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

public class Dialog2 extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JButton jButton1 = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();

  public Dialog2(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Dialog2() {
    this(null, "", false);
  }
  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    this.getContentPane().setLayout(xYLayout2);
    this.setTitle("About");
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jLabel1.setText("Navigator 3");
    jLabel2.setText("Nachfolger des Navigators 2");
    jLabel3.setText("erstellt von:");
    jLabel4.setText("Tobias Berlinger und Christian Oswald");
    jLabel5.setText("(WS 0102)");
    this.getContentPane().add(panel1,   new XYConstraints(-1, 151, 452, -1));
    panel1.add(jLabel5,   new XYConstraints(174, 18, -1, -1));
    panel1.add(jButton1, new XYConstraints(174, 63, -1, -1));
    this.getContentPane().add(jLabel1, new XYConstraints(177, 51, -1, -1));
    this.getContentPane().add(jLabel2, new XYConstraints(131, 82, -1, -1));
    this.getContentPane().add(jLabel3, new XYConstraints(173, 110, -1, -1));
    this.getContentPane().add(jLabel4, new XYConstraints(98, 138, -1, -1));
  }

  void jButton1_actionPerformed(ActionEvent e) {
  this.hide();
  }
}