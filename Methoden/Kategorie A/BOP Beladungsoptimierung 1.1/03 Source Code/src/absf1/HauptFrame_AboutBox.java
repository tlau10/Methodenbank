package absf1;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/**
 * <p>Title: HauptFrame_AboutBox</p>
 * <p>Description: Stellt den "Über"-Dialog in der Datei-Leiste dar.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FH Konstanz</p>
 * @author Jürgen Kambeitz
 * @version 1.0
 */

public class HauptFrame_AboutBox extends JDialog implements ActionListener {

  private JPanel panel1 = new JPanel();
  private JPanel panel2 = new JPanel();
  private JPanel insetsPanel1 = new JPanel();
  private JPanel insetsPanel3 = new JPanel();
  private JPanel insetsPanel4 = new JPanel();
  private JButton button1 = new JButton();
  private JLabel label1 = new JLabel();
  private JLabel label2 = new JLabel();
  private JLabel label3 = new JLabel();
  private JLabel label4 = new JLabel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private String product = "BeladungsOptimierungsProgramm BOP";
  private String version = "1.0";
  private String copyright = "Copyright (c) 2003";
  private String comments = "Programmiert an der FH Konstanz von Florian Pütz und Jürgen Kambeitz";
  private XYLayout xYLayout1 = new XYLayout();
  private ImageIcon imgIcon;
  private Image img;
  private FlowLayout flowLayout3 = new FlowLayout();
  private FlowLayout flowLayout4 = new FlowLayout();
  private JLabel iconLabel1 = new JLabel(new ImageIcon("src/absf1/laster.jpg"));
  private FlowLayout flowLayout1 = new FlowLayout();
  private FlowLayout flowLayout2 = new FlowLayout();

  /**
   * Konstruktor der Klasse HauptFrame_AboutBox, hier wird die Methode jbinit() aufgerufen
   * @param parent
   */
  public HauptFrame_AboutBox(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Hier findet die Initialisierung der Komponenten statt
   * @throws Exception
   */
  private void jbInit() throws Exception  {

    this.setTitle("Über BOP");
    this.getContentPane().setLayout(flowLayout3);

    panel1.setLayout(flowLayout1);
    panel2.setLayout(borderLayout2);
    label1.setFont(new java.awt.Font("Dialog", 1, 14));
    label1.setText(product);
    label2.setFont(new java.awt.Font("Dialog", 1, 12));
    label2.setText(version);
    label3.setFont(new java.awt.Font("Dialog", 1, 12));
    label3.setText(copyright);
    label4.setBackground(new Color(125, 171, 210));
    label4.setFont(new java.awt.Font("Dialog", 1, 12));
    label4.setText(comments);

    insetsPanel3.setLayout(xYLayout1);
    insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
    insetsPanel4.setLayout(flowLayout4);

    button1.setText("Ok");
    button1.addActionListener(this);

    insetsPanel1.setLayout(flowLayout2);
    panel1.setMinimumSize(new Dimension(450, 300));
    panel1.setPreferredSize(new Dimension(450, 300));
    this.getContentPane().add(panel1, null);
    insetsPanel3.add(label2,    new XYConstraints(166, 17, 20, -1));
    insetsPanel3.add(label3,    new XYConstraints(123, 34, 106, -1));
    insetsPanel3.add(label4,    new XYConstraints(-27, 51, -1, -1));
    insetsPanel3.add(label1,    new XYConstraints(37, 0, 278, -1));

    insetsPanel4.add(iconLabel1, null);
    panel2.add(insetsPanel3, BorderLayout.CENTER);
    panel2.add(insetsPanel4, BorderLayout.NORTH);
    panel1.add(panel2, null);
    panel1.add(insetsPanel1, null);
    insetsPanel1.add(button1, null);

    setResizable(true);

  }

  /**
   * Dient zum Schliessen des Fensters
   * @param e
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }

  /**
   * Eigentliche "Schliessen"-Funktion
   */
  void cancel() {
    dispose();
  }

  /**
   * Schliesst den Dialog beim betätigen des Buttons
   * @param e
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
      cancel();
    }
  }

}