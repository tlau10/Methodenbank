package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Hauptframe</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ptss.*;
import Simulation.*;
import javax.swing.event.*;



public class HauptFrame extends JFrame {

  private JPanel contentPane;
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenu jMenu2 = new JMenu();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JMenuItem jMenuItem2 = new JMenuItem();

  private JMenu jMenu3 = new JMenu();
  private JMenuItem jMenuItem4 = new JMenuItem();
  private JMenuItem jMenuItem5 = new JMenuItem();
  private JMenuItem jMenuItem6 = new JMenuItem();
  private JMenuItem jMenuItem7 = new JMenuItem();
  private JMenuItem jMenuItem3 = new JMenuItem();

  private int checkView = 0; // 0==Modellier, 1==Simu;


  private ModellierView mView = new ModellierView();
//  private SimulationView sView = new SimulationView(mView.getManager()); // wird mal SimulationsView :-)

//  private JPanel jPanel1 = mView;

  private BorderLayout borderLayout1 = new BorderLayout();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JMenu jMenu4 = new JMenu();
  private JMenuItem jMenuItem8 = new JMenuItem();
  private JMenuItem jMenuItem9 = new JMenuItem();

  public HauptFrame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    contentPane = (JPanel) this.getContentPane();
    jMenu1.setMinimumSize(new Dimension(63, 21));
    jMenu1.setText("Datei");
    jMenu2.setText("Hilfe");
    jMenuItem1.setText("Modell laden");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem1_actionPerformed(e);
      }
    });
    jMenuItem2.setText("Modell speichern");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem2_actionPerformed(e);
      }
    });

    contentPane.setLayout(borderLayout1);
    contentPane.setEnabled(true);
    jMenuBar1.setAlignmentX((float) 0.0);
    jMenuBar1.setBorder(BorderFactory.createEtchedBorder());
    jMenuBar1.setDoubleBuffered(true);



    jMenu3.setText("Ansicht");
    jMenuItem4.setText("Modell");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem4_actionPerformed(e);
      }
    });
    jMenuItem5.setText("Simulation");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem5_actionPerformed(e);
      }
    });
    jMenuItem5.setEnabled(false);
    jMenuItem6.setText("Hilfe");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem6_actionPerformed(e);
      }
    });
    jMenuItem7.setText("Beenden");
    jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem7_actionPerformed(e);
      }
    });
    jMenuItem3.setText("About");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem3_actionPerformed(e);
      }
    });

    jMenu4.setText("Neu");
    jMenuItem8.setActionCommand("Modell");
    jMenuItem8.setText("Route");
    jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem8_actionPerformed(e);
      }
    });
    jMenuItem9.setText("Karte");
    jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem9_actionPerformed(e);
      }
    });
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu3);
    jMenuBar1.add(jMenu2);
    contentPane.add(jMenuBar1, BorderLayout.NORTH);

    jMenu1.add(jMenu4);
    jMenu1.add(jMenuItem1);
    jMenu1.add(jMenuItem2);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem7);
    jMenu2.add(jMenuItem3);
    jMenu2.add(jMenuItem6);

    contentPane.add(mView, BorderLayout.CENTER);
    jMenu3.add(jMenuItem4);
    jMenu3.add(jMenuItem5);
    jMenu4.add(jMenuItem8);
    jMenu4.add(jMenuItem9);
   // mView.buttonState =1;
    this.setSize(new Dimension(800, 600));
    this.setTitle(" Paket Transport System Simulation (c) 2002");
  }

  /**Überschrieben, so dass eine Beendigung beim Schließen des Fensters möglich ist.*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void jMenuItem4_actionPerformed(ActionEvent e) {
    contentPane.removeAll();
    contentPane.add(mView, BorderLayout.CENTER);
    contentPane.add(jMenuBar1, BorderLayout.NORTH);

    checkView = 0;

    this.paintAll(this.getGraphics());
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {

    contentPane.removeAll();
   // contentPane.add(sView, BorderLayout.CENTER);
    contentPane.add(jMenuBar1, BorderLayout.NORTH);

    checkView =1;

    this.paintAll(this.getGraphics());

  }

  void jMenuItem7_actionPerformed(ActionEvent e) {
      System.exit(0);
  }

  void jMenuItem1_actionPerformed(ActionEvent e) { // Modell laden
    if (checkView ==0)
      mView.openModell();
  }

  void jMenuItem2_actionPerformed(ActionEvent e) { // Modell speichern
    if (checkView ==0)
      mView.saveModell();
  }

  void jMenuItem3_actionPerformed(ActionEvent e) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    DialogAbout diaAbout = new DialogAbout();
    diaAbout.setSize(410,240);
    diaAbout.setLocation((screenSize.width - mView.getX()) / 2,
                          (screenSize.height - mView.getX()) / 2);
    diaAbout.show();
  }

  void jMenuItem6_actionPerformed(ActionEvent e) {
	try {
		Runtime.getRuntime().exec(
				"rundll32 url.dll,FileProtocolHandler "
						+ new java.io.File(".\\Programmhilfe\\Programmhilfe_Paket Transport Simulation System.html")
								.getAbsolutePath());
	} catch (Exception ex) {
		   JOptionPane.showMessageDialog(null, "Onlinee Hilfe fehler"
                   +"\n ");
	}
  }

  void jMenuItem8_actionPerformed(ActionEvent e) {
    if (checkView ==0)
      mView.newRoute();
  }

  void jMenuItem9_actionPerformed(ActionEvent e) {
    if (checkView ==0)
    mView.setImage();
  }





}

