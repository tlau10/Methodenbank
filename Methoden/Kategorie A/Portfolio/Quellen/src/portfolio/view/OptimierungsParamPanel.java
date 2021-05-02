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
import java.util.Date;
import javax.swing.*;

import portfolio.view.util.*;


public class OptimierungsParamPanel extends JPanel {

  // für unterjährige Renditen -> Angabe in Tagen
  private WholeNumberField textFieldVergleichszeitraum;
  private DateSelectionPanel dateSelectionAnalyseVonPanel;
  private DateSelectionPanel dateSelectionAnalyseBisPanel;

  private Frame parentFrame;

  /**
   * constructor
   */
  public OptimierungsParamPanel(Frame parentFrame) {
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

    JLabel labelAnalyseVon = new JLabel("Analyse von");
    JLabel labelAnalyseBis = new JLabel("Analyse bis");
    JLabel labelVergleichszeitraum = new JLabel("Vergleichszeitraum");
    JLabel labelTage = new JLabel("Tage");

    dateSelectionAnalyseVonPanel = new DateSelectionPanel(parentFrame, "Datum auswählen");
    dateSelectionAnalyseBisPanel = new DateSelectionPanel(parentFrame, "Datum auswählen");
    textFieldVergleichszeitraum = new WholeNumberField(365, 4);

    JPanel paramPanel = new JPanel(new GridBagLayout());
    paramPanel.add(labelAnalyseVon, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    paramPanel.add(dateSelectionAnalyseVonPanel, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    paramPanel.add(labelAnalyseBis, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    paramPanel.add(dateSelectionAnalyseBisPanel, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    paramPanel.add(labelVergleichszeitraum, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    paramPanel.add(textFieldVergleichszeitraum, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    paramPanel.add(labelTage, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
        ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

    this.setBorder(BorderFactory.createTitledBorder(" Parameter "));
    this.setLayout(new BorderLayout(10,10));
    this.add(paramPanel, BorderLayout.WEST);

  }


////////////////////////////////////////////////////////////////////////////////
// SETTER & GETTER
////////////////////////////////////////////////////////////////////////////////
  public void setVergleichszeitraum(long tage) {
    String s = new Long(tage/24/60/60/1000).toString();
    textFieldVergleichszeitraum.setValue( new Integer(s).intValue());
  }

  public long getVergleichszeitraum() {
    long tmpLong = textFieldVergleichszeitraum.getValue();
    return new Long(tmpLong).longValue();
  }

  public void setAnalyseVon(Date datum) {
    dateSelectionAnalyseVonPanel.setDate(datum);
  }

  public Date getAnalyseVon() {
    return dateSelectionAnalyseVonPanel.getDate();
  }

  public void setAnalyseBis(Date datum) {
    dateSelectionAnalyseBisPanel.setDate(datum);
  }

  public Date getAnalyseBis() {
    return dateSelectionAnalyseBisPanel.getDate();
  }


}