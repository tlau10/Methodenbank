package absf1;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * <p>Title: SolverEingabeBox</p>
 * <p>Description: Dient als Pop-Up Fenster für die Eingabe der Solverpfade</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FH Konstanz</p>
 * @author Jürgen Kambeitz
 * @version 1.0
 */

public class SolverEingabeBox extends JDialog {
  private FlowLayout flowLayout1 = new FlowLayout();
  private JPanel jPanel1 = new JPanel();
  private XYLayout xYLayout1 = new XYLayout();
  private JPanel jPanel2 = new JPanel();
  private JButton jButtonOk = new JButton();
  private JButton jButtonAbbrechen = new JButton();
  private JTextField jTextFieldSolverPfad = new JTextField();
  private XYLayout xYLayout2 = new XYLayout();
  private Border border1;
  private TitledBorder titledBorder1;
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTextFieldSolverPfad1 = new JTextField();
  private JLabel jLabel3 = new JLabel();
  private JTextField jTextFieldSolverPfad2 = new JTextField();
  private JButton jButtonFc1 = new JButton();
  private JButton jButtonFc2 = new JButton();
  private JButton jButtonFc3 = new JButton();
  private String solverPfadLp = "solver.bat";//Hier wird der Default-Pfad des Solvers eingestellt

  /**
   * Konstruktor der Klasse SolverEingabeBox
   */
  public SolverEingabeBox() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Erzeugung der Komponenten
   * @throws Exception
   */
  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder1 = new TitledBorder(border1,"Solverpfade");
    this.setTitle("Setzen des Solverpfades");
    this.getContentPane().setLayout(flowLayout1);
    jPanel1.setLayout(xYLayout1);
    jButtonOk.setText("OK");
    jButtonOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonOk_actionPerformed(e);
      }
    });
    jButtonAbbrechen.setText("Abbrechen");
    jButtonAbbrechen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAbbrechen_actionPerformed(e);
      }
    });
    jPanel2.setLayout(xYLayout2);
    jPanel2.setBorder(titledBorder1);
    jLabel1.setText("LP-Solve");
    jLabel2.setEnabled(false);
    jLabel2.setText("XA");
    jLabel3.setEnabled(false);
    jLabel3.setText("MOPS");
    jButtonFc1.setText("...");
    jButtonFc1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonFc1_actionPerformed(e);
      }
    });

    jButtonFc2.setEnabled(false);
    jButtonFc2.setText("...");
    jButtonFc3.setEnabled(false);
    jButtonFc3.setText("...");
    jTextFieldSolverPfad.setText(this.getSolverPfadLp());
    jTextFieldSolverPfad1.setEnabled(false);
    jTextFieldSolverPfad2.setEnabled(false);
    this.getContentPane().add(jPanel1, null);
    jPanel1.add(jPanel2,                       new XYConstraints(1, 1, 420, 186));
    jPanel2.add(jTextFieldSolverPfad1, new XYConstraints(73, 41, 255, -1));
    jPanel2.add(jLabel1, new XYConstraints(19, 9, -1, -1));
    jPanel2.add(jTextFieldSolverPfad, new XYConstraints(73, 7, 255, -1));
    jPanel2.add(jButtonFc1, new XYConstraints(344, 4, -1, -1));
    jPanel2.add(jButtonFc2, new XYConstraints(344, 39, -1, -1));
    jPanel2.add(jButtonFc3, new XYConstraints(344, 73, -1, -1));
    jPanel2.add(jTextFieldSolverPfad2, new XYConstraints(73, 75, 255, -1));
    jPanel2.add(jLabel2, new XYConstraints(54, 43, -1, -1));
    jPanel2.add(jLabel3, new XYConstraints(34, 77, -1, -1));
    jPanel2.add(jButtonAbbrechen, new XYConstraints(188, 121, -1, -1));
    jPanel2.add(jButtonOk,  new XYConstraints(128, 121, -1, -1));
  }

  /**
   * Wird der Button "Abbrechen" gedrückt, wird das Fenster ohne Übernahme der Werte geschlossen
   * @param e
   */
  void jButtonAbbrechen_actionPerformed(ActionEvent e) {

    this.dispose();
  }

  /**
   * Wird der Button mit den drei Punkten gedrückt, öffnet sich ein FileChooser-Dialog, aus dem der gewünschte Pfad für den Solver gewählt werden kann.
   * @param e
   */
  void jButtonFc1_actionPerformed(ActionEvent e) {

    JFileChooser fc = new JFileChooser();
    fc.showOpenDialog(null);
    String solverPfad = fc.getSelectedFile().toString();
    jTextFieldSolverPfad.setText(solverPfad);
  }

  /**
   * Wird der "OK"-Button gedrück, wird der Pfad gespeichert und das Fenster geschlossen.
   * @param e
   */
  void jButtonOk_actionPerformed(ActionEvent e) {

    this.setSolverPfadLp(jTextFieldSolverPfad.getText());
    this.dispose();
  }

  /**
   * Set-Methode für die Membervariable solverPfadLp
   * @param pSolverPfadLp
   */
  public void setSolverPfadLp(String pSolverPfadLp)
  {
    solverPfadLp = pSolverPfadLp;
  }

  /**
   * Get-Methode für die Membervariable solverPfadLp
   * @param pSolverPfadLp
   */
  public  String getSolverPfadLp()
  {
    return solverPfadLp;
  }
}