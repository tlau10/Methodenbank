package opsa;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Title:        Operationssäleplanung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FHKN
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 */

public class DlgConfig extends JFrame {
  XYLayout xYLayout1 = new XYLayout();
  Label label1 = new Label();
  Label label2 = new Label();
  TextField textFieldAnzOp = new TextField();
  TextField textFieldAnzPer = new TextField();
  Label label3 = new Label();
  TextField textFieldAnzSaal = new TextField();
  FmOpSa theFmOpSa;
  Button buttonCancel = new Button();
  Button buttonOK = new Button();

  public DlgConfig(FmOpSa aFmOpSa) {
    super.setTitle("Einstellung");
    theFmOpSa=aFmOpSa;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    label1.setText("Anzahl der Operationen");
    this.getContentPane().setLayout(xYLayout1);
    label2.setText("Anzahl der Perioden");
    label3.setText("Anzahl der Sälen");
    xYLayout1.setWidth(338);
    xYLayout1.setHeight(219);
    this.getContentPane().setBackground(new Color(0, 209, 255));
    this.setResizable(false);
    buttonCancel.setLabel("Abbrechen");
    buttonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonCancel_actionPerformed(e);
      }
    });
    buttonOK.setLabel("OK");
    buttonOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonOK_actionPerformed(e);
      }
    });
    this.getContentPane().add(label3,  new XYConstraints(41, 109, -1, -1));
    this.getContentPane().add(label2, new XYConstraints(41, 70, -1, -1));
    this.getContentPane().add(label1, new XYConstraints(41, 30, -1, -1));
    this.getContentPane().add(buttonCancel, new XYConstraints(99, 163, -1, -1));
    this.getContentPane().add(buttonOK,    new XYConstraints(204, 163, 69, -1));
    this.getContentPane().add(textFieldAnzSaal,  new XYConstraints(190, 110, 56, -1));
    this.getContentPane().add(textFieldAnzOp, new XYConstraints(190, 29, 56, -1));
    this.getContentPane().add(textFieldAnzPer, new XYConstraints(190, 70, 56, -1));
  }
  void buttonOK_actionPerformed(ActionEvent e) {
    try{
      int anzOp=Integer.parseInt((String)textFieldAnzOp.getText());
      int anzPer=Integer.parseInt((String)textFieldAnzPer.getText());
      int anzSaal=Integer.parseInt((String)textFieldAnzSaal.getText());
      theFmOpSa.setOperationsAnz(anzOp);
      theFmOpSa.setPeriodeAnz(anzPer);
      theFmOpSa.setSaalAnz(anzSaal);
      theFmOpSa.setDefaultTablePeriode(anzPer);
      theFmOpSa.setDefaultTableOperation(anzOp);
      this.dispose();
    }
    catch(NumberFormatException ex){
    JOptionPane.showMessageDialog(this,"Eingabe falsch! Nur Ziffe zulässig! ",
                                          "Info", JOptionPane.INFORMATION_MESSAGE);
    }
  }
  void buttonCancel_actionPerformed(ActionEvent e) {
    this.dispose();
  }
}