package Simulation;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Grafik Darstellung der Simulation</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;

import ptss.*;
import javax.swing.event.*;
import javax.swing.border.*;

//import Simulation.eduni.simdiag.*;


public class SimulationView extends JPanel{
 // public JLabel jLabel1 = new JLabel();
  private SimulationManager sManager;
  private ModellierManager mManager;
  private SimulationViewGrafik  sView = new SimulationViewGrafik(sManager,mManager);
  private JScrollPane jPanel1 = new JScrollPane(sView);

  private Border border1;
  private Border border2;
  private JPanel jPanel3 = new JPanel();
  private JTextArea simuSpeed = new JTextArea();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton jButton3 = new JButton();
  private JButton jButton2 = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JButton jButton1 = new JButton();
  private JSlider jSlider1 = new JSlider();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel4 = new JPanel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();

  public SimulationView(ModellierManager m) {

  mManager = m;


  sManager = new SimulationManager(this,mManager,sView);
  sView.setSimuManager(sManager);
  sView.setModellierManager(mManager);
  sManager.setModellierManager(mManager);
  sManager.setGrafik(sView);

 //   kantenListe = _kantenListe;
 //   knotenListe = _knotenListe;

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
 //   jLabel1.setText("Das wird die SimulationsView");
//    this.add(jLabel1, null);
 //   this.add(jPanel1);
    border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(124, 124, 124),new Color(178, 178, 178));
    border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(99, 99, 124),new Color(142, 142, 178));
    this.setLayout(gridBagLayout2);
    simuSpeed.setFont(new java.awt.Font("Monospaced", 0, 12));
    simuSpeed.setBorder(BorderFactory.createLoweredBevelBorder());
    simuSpeed.setPreferredSize(new Dimension(140, 38));
    simuSpeed.setRows(3);
    simuSpeed.setText(" Geschwindigkeit:"+"\n Echzeit");
    jButton3.setText("Pause");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton3_actionPerformed(e);
      }
    });
    jButton2.setText("Stop");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jLabel1.setPreferredSize(new Dimension(140, 17));
    jLabel1.setToolTipText("");
    jLabel1.setText("  -   Geschwindigkeit     +");
    jButton1.setText("Start");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jSlider1.setMaximum(200);
    jSlider1.setPreferredSize(new Dimension(100, 16));
    jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        jSlider1_stateChanged(e);
      }
    });
    jSlider1.setValue(100);
    jPanel2.setLayout(gridBagLayout1);
    jPanel3.setLayout(gridBagLayout3);
    this.add(jPanel3,      new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    jPanel3.add(jPanel4,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jPanel2,         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    jPanel2.add(jButton3,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 69, 0));
    jPanel2.add(jButton1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 77, 0));
    jPanel2.add(jButton2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 79, 0));
    jPanel2.add(jLabel1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 0, 5), 0, 0));
    jPanel2.add(jSlider1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
    jPanel2.add(simuSpeed, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
    this.add(jPanel1,    new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    sView.setMinimumSize(new Dimension(1280, 1024));
    sView.setPreferredSize(new Dimension(1280, 1024));


  }

  void jButton1_actionPerformed(ActionEvent e) {
    sManager.startSim_Btn();
  }

  void jButton2_actionPerformed(ActionEvent e) {
    sManager.stopSimu_Btn();
  }

  void jButton3_actionPerformed(ActionEvent e) {
    sManager.pauseSimu_Btn();
  }

  void jSlider1_stateChanged(ChangeEvent e) {
    sManager.sliderChanged(jSlider1.getValue());
  }
  public void setSpeedText(String s){
        simuSpeed.setText(s);
  }

  public void setPauseBtnText(String s){
    jButton3.setText(s);

  }

}