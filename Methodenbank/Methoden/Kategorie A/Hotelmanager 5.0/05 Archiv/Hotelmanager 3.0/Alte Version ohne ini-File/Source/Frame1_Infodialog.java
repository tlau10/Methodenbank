package hotelbelegung;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * �berschrift:   Hotelbelegung
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author Volker Wohlleber
 * @version 2.0 Oliver B�hler, Kilian Thiel Juni 2003 
 */

public class Frame1_Infodialog extends JDialog implements ActionListener {

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JPanel insetsPanel2 = new JPanel();
  JPanel insetsPanel3 = new JPanel();
  JButton button1 = new JButton();
  JLabel imageLabel = new JLabel();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  JLabel label5 = new JLabel();
  JLabel label6 = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  String product = "Hotel-Manager V 2.0";
  String copyright = "Autoren:";
  String comments = "Florian Raiber, Oliver Schraag und Volker Wohlleber";
  String Weiterentwicklung = "Weiterentwicklung SS2003:";
  String Namen = "Oliver B�hler, Kilian Thiel";
  public Frame1_Infodialog(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }
  /**Initialisierung der Komponenten*/
  private void jbInit() throws Exception  {
    //imageLabel.setIcon(new ImageIcon(Frame1_Infodialog.class.getResource("[Ihre Grafik]")));
    this.setTitle("Info");
    setResizable(false);
    panel1.setLayout(borderLayout1);
    panel2.setLayout(borderLayout2);
    insetsPanel1.setLayout(flowLayout1);
    insetsPanel2.setLayout(flowLayout1);
    insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    gridLayout1.setRows(5);
    gridLayout1.setColumns(1);
    label1.setText(product);
//    label2.setText(version);
    label3.setText(copyright);
    label4.setText(comments);
    label5.setText(Weiterentwicklung);
	label6.setText(Namen);
    insetsPanel3.setLayout(gridLayout1);
    insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    button1.setText("OK");
    button1.addActionListener(this);
    insetsPanel2.add(imageLabel, null);
    panel2.add(insetsPanel2, BorderLayout.WEST);
    this.getContentPane().add(panel1, null);
    insetsPanel3.add(label1, null);
//    insetsPanel3.add(label2, null);
    insetsPanel3.add(label3, null);
    insetsPanel3.add(label4, null);
    insetsPanel3.add(label5, null);
	insetsPanel3.add(label6, null);
    panel2.add(insetsPanel3, BorderLayout.CENTER);
    insetsPanel1.add(button1, null);
    panel1.add(insetsPanel1, BorderLayout.SOUTH);
    panel1.add(panel2, BorderLayout.NORTH);
  }
  /**�berschrieben, so dass eine Beendigung beim Schlie�en des Fensters m�glich ist.*/
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }
  /**Dialog schlie�en*/
  void cancel() {
    dispose();
  }
  /**Dialog bei Schalter-Ereignis schlie�en*/
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
      cancel();
    }
  }
}