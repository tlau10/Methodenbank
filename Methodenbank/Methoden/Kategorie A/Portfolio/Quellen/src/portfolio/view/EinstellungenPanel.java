/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */
package portfolio.view;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;


import portfolio.business.*;
import portfolio.view.util.*;


public class EinstellungenPanel extends JPanel {

  // the GUI Objects
  JRadioButton typ0RadioButton = new JRadioButton();
  JRadioButton typ4RadioButton = new JRadioButton();
  JRadioButton typ1RadioButton = new JRadioButton();
  JLabel aktienLabel = new JLabel();
  JLabel kursLabel = new JLabel();
  JLabel berkLabel = new JLabel();

  // the external panels
  ButtonPanel buttonPanel;
  OptimierungsParamPanel paramPanel;
  OptionsPanel optionsPanel;
  ResultPanel resultPanel;

  private Frame parentFrame;

  /**
   * constructor
   * @param parentFrame
   */
  public EinstellungenPanel(Frame parentFrame) {
    this.parentFrame = parentFrame;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * initialize the GUI
   * @throws Exception
   */
  private void jbInit() throws Exception {

    paramPanel = new OptimierungsParamPanel(parentFrame);
    optionsPanel = new OptionsPanel();
    buttonPanel = new ButtonPanel();
    buttonPanel.addActionListener((ActionListener)parentFrame);
    resultPanel = new ResultPanel();

    JLabel jLabel3 = new JLabel("Aktien:");
    JLabel jLabel4 = new JLabel("Kurse:");
    JLabel jLabel5 = new JLabel("Renditen:");
    aktienLabel.setText("-");
    berkLabel.setText("-");
    kursLabel.setText("-");

    typ0RadioButton.setText("Renditemaximierer (Typ 0)");
    typ0RadioButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            typ0RadioButton_actionPerformed(e);
        }
    });
    typ0RadioButton.setSelected(true);

    typ1RadioButton.setText("Varianzminimierer (Typ 1)");
    typ1RadioButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            typ1RadioButton_actionPerformed(e);
        }
    });

    typ4RadioButton.setText("Varianzminimierer (Typ 4)");
    typ4RadioButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            typ4RadioButton_actionPerformed(e);
        }
    });

    typ0RadioButton.setSelected(true);
    ButtonGroup buttonGroup1 = new ButtonGroup();
    buttonGroup1.add(typ0RadioButton);
    buttonGroup1.add(typ1RadioButton);
    buttonGroup1.add(typ4RadioButton);


    JPanel loadedPanel = new JPanel( new GridBagLayout());
    loadedPanel.setMinimumSize(new Dimension(180, 130));
    loadedPanel.setPreferredSize(new Dimension(180, 130));
    loadedPanel.setBorder(BorderFactory.createTitledBorder(" Datenbasis ") );
    loadedPanel.add( jLabel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    loadedPanel.add( aktienLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    loadedPanel.add(new JPanel(),      new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    loadedPanel.add( jLabel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    loadedPanel.add( kursLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    loadedPanel.add( jLabel5, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    loadedPanel.add( berkLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    JPanel typesPanel = new JPanel( new GridBagLayout() );
    typesPanel.setMinimumSize(new Dimension(200, 130));
    typesPanel.setPreferredSize(new Dimension(200, 130));
    typesPanel.setBorder(BorderFactory.createTitledBorder(" Optimierungsmodell ") );
    typesPanel.add(typ0RadioButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    typesPanel.add(typ1RadioButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    typesPanel.add(typ4RadioButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));


    JPanel einstellungsPanel = new JPanel( new GridBagLayout() );
    einstellungsPanel.add(loadedPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    einstellungsPanel.add(typesPanel,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    einstellungsPanel.add(optionsPanel,  new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    einstellungsPanel.add(resultPanel,     new GridBagConstraints(2, 0, 1, 3, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 1, 5), 0, 3));
    einstellungsPanel.add(paramPanel,     new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 2), 66, 0));
    einstellungsPanel.add(buttonPanel,  new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0
              ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    this.add(einstellungsPanel);
  }// jbInit



    void typ0RadioButton_actionPerformed(ActionEvent e)
    {
      optionsPanel.setMuMinCheckboxEnabled(false);
    }

    void typ1RadioButton_actionPerformed(ActionEvent e)
    {
      optionsPanel.setMuMinCheckboxEnabled(true);
    }

    void typ4RadioButton_actionPerformed(ActionEvent e)
    {
      optionsPanel.setMuMinCheckboxEnabled(true);
    }



////////////////////////////////////////////////////////////////////////////////
// SETTER & GETTER
////////////////////////////////////////////////////////////////////////////////
  public int getSchrittweite() {
    return optionsPanel.getSchrittweite();
  }

  public double getAnteilAmPortfolio() {
    return optionsPanel.getAnteilAmPortfolio();
  }


  /**
   * returns the appropriate model for the selected radio button
   */
  public LPModell getSelectedTypeModel() {
    if(typ0RadioButton.isSelected())
    {
        return new Typ0Modell();
    }
    else if(typ1RadioButton.isSelected())
    {
        return new Typ1Modell();
    }
    else if(typ4RadioButton.isSelected())
    {
        return new Typ4Modell();
    }
    else
    {
        System.err.println("EinstellungenPanel.java->getSelectedTypeModel(): unknown index selected! Return null!");
        return null;
    }
  }


  public OptionsPanel getOptionsPanel() {
    return optionsPanel;
  }

  public void setAnzahlAktien(int anzahl) {
    aktienLabel.setText("" + anzahl);
  }

  public void setAnzahlKurse(int anzahl) {
    kursLabel.setText("" + anzahl);
  }

  public void setAnzahlRenditen(int anzahl) {
    berkLabel.setText("" + anzahl);
  }


  public void addActionListener(ActionListener objToRegister)
  {
    // weiterleiten
    buttonPanel.addActionListener(objToRegister);
  }

  public ResultPanel getResultPanel() {
    return resultPanel;
  }

  public OptimierungsParamPanel getParamPanel() {
    return paramPanel;
  }
}