package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:
 * <p>
 * Erstellt about Dialog</p>
 * <p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */


import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

class DialogAbout extends JDialog{
  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel2 = new JPanel();
  private JButton jButton1 = new JButton();
  private Border border1;
  private Border border2;
  private Border border3;
  private Border border4;
  private JTextArea jTextArea1 = new JTextArea();
  private BorderLayout borderLayout2 = new BorderLayout();

  public DialogAbout() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142));
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    border3 = BorderFactory.createEmptyBorder();
    border4 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(99, 99, 99),new Color(142, 142, 142));
    this.getContentPane().setLayout(borderLayout1);
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jPanel2.setBorder(border4);
    jPanel2.setLayout(borderLayout2);
    jTextArea1.setBackground(Color.cyan);
    jTextArea1.setEditable(false);
    jTextArea1.setText(" \n\t Das \"Paket Transport Simulations System\" "
                       +"\n \t wurde im Rahmen der Veranstaltung \"ABSF\" "
                       +"\n\n\t von:"
                       +"\n\t\t Mathias Jehle"
                       +"\n\t\t Thomas Geldner"
                       +"\n\n\t im WS 02/03 implementiert.");
    jTextArea1.setRows(10);
    jTextArea1.setTabSize(4);
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("About:");
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(jButton1, null);
    this.getContentPane().add(jPanel2,  BorderLayout.CENTER);
    jPanel2.add(jTextArea1,  BorderLayout.CENTER);
  }

  void jButton1_actionPerformed(ActionEvent e) {
    this.dispose();
  }
}