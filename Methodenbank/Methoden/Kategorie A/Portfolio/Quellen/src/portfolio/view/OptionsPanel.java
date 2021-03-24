/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import portfolio.view.util.*;


public class OptionsPanel extends JPanel {

  private WholeNumberField schrittTextField;
  private WholeNumberField zufallsauswahlTextField;
  private DoubleNumberField anteilTextField;
  private DoubleNumberField muMinTextField;
  private JCheckBox muMinCheckBox = new JCheckBox();
  private JCheckBox zufallsauswahlCheckBox = new JCheckBox();

  private JLabel zufallStueckLabel;
  private JLabel labelMuMinProzent = new JLabel("%");


  /**
   * constructor
   */
  public OptionsPanel() {
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

    JLabel jLabel1 = new JLabel("Schrittweite");
    JLabel jLabel2 = new JLabel("Anteilsbegrenzung");


    schrittTextField = new WholeNumberField(1, 4);
    schrittTextField.setHorizontalAlignment(SwingConstants.RIGHT);

    anteilTextField = new DoubleNumberField(0.40, 4);
    anteilTextField.setHorizontalAlignment(SwingConstants.RIGHT);

    muMinCheckBox.setEnabled(false);
    muMinCheckBox.setText("Mindestrendite"); // set mu as unicode character
    muMinCheckBox.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
      muMinCheckBox_actionPerformed(e);
      }
    });
    muMinTextField = new DoubleNumberField(0.0, 4);
    muMinTextField.setMinimumSize(new Dimension(40, 21));
    muMinTextField.setHorizontalAlignment(SwingConstants.RIGHT);
    muMinTextField.setEnabled(false);
    labelMuMinProzent.setEnabled(false);
    labelMuMinProzent.setText("GE");

    // Zufallsauswahl
    zufallStueckLabel = new JLabel("Stück");
    zufallsauswahlCheckBox.setText("Zufallsauswahl");
    zufallsauswahlCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        zufallsauswahlCheckBox_actionPerformed(e);
      }
    });
    zufallsauswahlTextField = new WholeNumberField(0, 4);
    zufallsauswahlTextField.setEnabled(false);
    zufallsauswahlTextField.setText("1");
    zufallsauswahlTextField.setHorizontalAlignment(SwingConstants.RIGHT);
    zufallStueckLabel.setEnabled(false);
    zufallStueckLabel.setText("Aktien");


    // Option panel
    JPanel optionsPanel = new JPanel( new GridBagLayout());
    optionsPanel.add(jLabel1,           new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    optionsPanel.add(schrittTextField,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    optionsPanel.add(jLabel2,           new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    optionsPanel.add(anteilTextField,   new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    optionsPanel.add(muMinCheckBox,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    optionsPanel.add(muMinTextField,     new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    optionsPanel.add(labelMuMinProzent,     new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    optionsPanel.add(zufallsauswahlCheckBox, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    optionsPanel.add(zufallsauswahlTextField,  new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    optionsPanel.add(zufallStueckLabel,  new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));


    this.setBorder(BorderFactory.createTitledBorder(" Optionen ") );
    this.setLayout(new BorderLayout(10,10));
    this.add(optionsPanel, BorderLayout.WEST);
  }


  /**
   * enable/disable the checkbox
   * @param enabled
   */
  public void setMuMinCheckboxEnabled(boolean enabled) {
    if( !enabled)
    {
      muMinCheckBox.setSelected(false);
      muMinTextField.setEnabled(false);
      labelMuMinProzent.setEnabled(false);
    }

    muMinCheckBox.setEnabled(enabled);
  }


  private void muMinCheckBox_actionPerformed(ActionEvent e) {
    if(muMinCheckBox.isSelected()) {
      muMinTextField.setEnabled(true);
      labelMuMinProzent.setEnabled(true);
    }
    else {
      muMinTextField.setEnabled(false);
      labelMuMinProzent.setEnabled(false);
    }
  }


  private void zufallsauswahlCheckBox_actionPerformed(ActionEvent e) {
    if(zufallsauswahlCheckBox.isSelected()) {
      zufallsauswahlTextField.setEnabled(true);
      zufallStueckLabel.setEnabled(true);
    }
    else {
      zufallsauswahlTextField.setEnabled(false);
      zufallStueckLabel.setEnabled(false);
    }
  }


////////////////////////////////////////////////////////////////////////////////
// SETTER & GETTER
////////////////////////////////////////////////////////////////////////////////
  public int getSchrittweite() {
    return schrittTextField.getValue();
  }

  public double getAnteilAmPortfolio() {
    return anteilTextField.getValue();
  }

  public Double getMuMin() {
    if(muMinCheckBox.isEnabled() && muMinCheckBox.isSelected())
    {
      return new Double(muMinTextField.getValue());
    }
    else
      return null;
  }


  public Integer getZufallsAnzahlAktien() {
    if(zufallsauswahlCheckBox.isEnabled() && zufallsauswahlCheckBox.isSelected())
    {
      return new Integer(zufallsauswahlTextField.getValue());
    }
    else
      return null;
  }

  public void setColorMuMinTextfield(Color farbe){
    muMinTextField.setBackground(farbe);
  }

  public void setAnteilsbegrenzung(double anteil) {
    anteilTextField.setValue(anteil);
  }

}