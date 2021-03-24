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
import javax.swing.*;

import portfolio.model.Portfolio;
import portfolio.view.util.DoubleNumberField;


public class ResultQualitaetspruefungPanel extends JPanel {

    private JTextField textFieldVorhandenerGesamtzeitraum;
    private JTextField textFieldZeitBerechnungPortfAnteile;
    private JTextField textFieldZeitBerechnungPortfWert;
    private JTextField textFieldPortfPerformance;
    private JLabel jLabel1 = new JLabel();


    /**
     * constructor
     */
    public ResultQualitaetspruefungPanel() {

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

      // initialize the objective function text field
      textFieldVorhandenerGesamtzeitraum = new JTextField("0", 10);
      textFieldVorhandenerGesamtzeitraum.setEnabled(false);
      textFieldVorhandenerGesamtzeitraum.setHorizontalAlignment(SwingConstants.RIGHT);
      JLabel labelVorhandenerGesamtzeitraum = new JLabel("Gesamtzeitraum");
      JLabel labelTage = new JLabel("Tage");

      textFieldZeitBerechnungPortfAnteile = new JTextField("0", 10);
      textFieldZeitBerechnungPortfAnteile.setEnabled(false);
      textFieldZeitBerechnungPortfAnteile.setHorizontalAlignment(SwingConstants.RIGHT);
      JLabel labelZeitBerechnungPortfAnteile = new JLabel("Berechnungszeitraum");
      JLabel labelTage2 = new JLabel("Tage");

      textFieldZeitBerechnungPortfWert = new JTextField("0", 10);
      textFieldZeitBerechnungPortfWert.setEnabled(false);
      textFieldZeitBerechnungPortfWert.setHorizontalAlignment(SwingConstants.RIGHT);
      JLabel labelZeitBerechnungPortfWert = new JLabel("Überprüfungszeitraum");
      JLabel labelTage3 = new JLabel("Tage");

      textFieldPortfPerformance = new JTextField("0", 10);
      textFieldPortfPerformance.setEnabled(false);
      textFieldPortfPerformance.setHorizontalAlignment(SwingConstants.RIGHT);
      JLabel labelPortfPerformance = new JLabel("Portfolio-Performance");
      JLabel labelProzent = new JLabel("%");

      JPanel mainPanel = new JPanel( new GridBagLayout() );
      mainPanel.setBorder(BorderFactory.createTitledBorder(" Qualität des Portfolios "));
      jLabel1.setText("%");
      mainPanel.add(labelVorhandenerGesamtzeitraum, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(textFieldVorhandenerGesamtzeitraum, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(labelTage, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

      mainPanel.add(labelZeitBerechnungPortfAnteile, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(textFieldZeitBerechnungPortfAnteile, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(labelTage2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

      mainPanel.add(labelZeitBerechnungPortfWert, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(textFieldZeitBerechnungPortfWert, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(labelTage3, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

      mainPanel.add(labelPortfPerformance, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(textFieldPortfPerformance, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      mainPanel.add(jLabel1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

      mainPanel.add(new JPanel(), new GridBagConstraints(2, 5, 1, 1, 1.0, 1.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

      this.setLayout(new BorderLayout(10,10));
      this.add(mainPanel, BorderLayout.CENTER );
    }




////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
/////////////////////////// GETTER & SETTER ////////////////////////////////////

    public void setVorhandenerGesamtzeitraum(int tage) {
      textFieldVorhandenerGesamtzeitraum.setText( "" + tage);
    }

    public int getVorhandenerGesamtzeitraum() {
      return Integer.parseInt( textFieldVorhandenerGesamtzeitraum.getText() );
    }

    public void setZeitBerechnungPortfAnteile(int tage) {
      textFieldZeitBerechnungPortfAnteile.setText( "" + tage);
    }

    public int getZeitBerechnungPortfAnteile() {
      return Integer.parseInt( textFieldZeitBerechnungPortfAnteile.getText() );
    }

    public void setZeitBerechnungPortfWert(int tage) {
      textFieldZeitBerechnungPortfWert.setText( "" + tage);
    }

    public int getZeitBerechnungPortfWert() {
      return Integer.parseInt( textFieldZeitBerechnungPortfWert.getText() );
    }

    public void setPortfPerformance(double wert) {
      wert = (double)Math.round(wert * 100);
      wert = wert / 100;
      textFieldPortfPerformance.setText( "" + wert);
    }

    public double getPortfPerformance() {
      return Double.parseDouble( textFieldPortfPerformance.getText() );
    }

}