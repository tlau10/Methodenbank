package hotelbelegung;

import java.lang.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import javax.swing.*;
import java.beans.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      fh-konstanz
 * @author:      Oliver Schraag
 * @version 1.0
 */

public class ServiceLevelPanel extends JPanel {
  JPanel jPanelInfo = new JPanel();
  JPanel jPanelServiceLevel = new JPanel();
  //JTable jTableServiceLevel = new JTable();
  BorderLayout borderLayoutServiceLevel = new BorderLayout();
  JTextArea jTextAreaDescription = new JTextArea();
  GridBagLayout gridBagLayoutServiecLevelInfo = new GridBagLayout();
  JLabel jLabelUnit = new JLabel();

  private Frame1 frame;
  private Manager manager;
  private Belegung belegung;
  private DateFormat outFormat = new SimpleDateFormat("dd.MM.yy");
  private DateFormat outFormatSmall = new SimpleDateFormat("dd.MM");
  private DateFormat compareFormat = new SimpleDateFormat("yyyyMMdd");
  private boolean area;
  private ChartPanel chartPan;
  private ChartColourScheme chartCols;
  private Chart chart;
  private Date aktDatum = new Date();

  private int chartBars = 10; // Standard Anzeige der Belegung über zehn Tage hinweg

  private int minimum = 0;
  private int maximum = 0;

  GridBagLayout gridBagLayoutServiceLevel = new GridBagLayout();

  public ServiceLevelPanel(Manager manager, Belegung belegung, String maxBelegung)
  {
    try {
      jbInit(manager, belegung, maxBelegung);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public ServiceLevelPanel(Manager manager, Belegung belegung, String maxBelegung, Date altDatum, Date buchung, int anzTage) {
    try {
      jbInit(manager, belegung, maxBelegung, altDatum, buchung, anzTage);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit(Manager manager, Belegung belegung, String maxBelegung) throws Exception {
    this.setLayout(gridBagLayoutServiceLevel);
    jPanelServiceLevel.setBorder(BorderFactory.createEtchedBorder());
    jPanelServiceLevel.setLayout(borderLayoutServiceLevel);
    jPanelInfo.setBorder(BorderFactory.createEtchedBorder());
    jPanelInfo.setPreferredSize(new Dimension(282, 139));
    jPanelInfo.setLayout(gridBagLayoutServiecLevelInfo);
    jTextAreaDescription.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextAreaDescription.setMaximumSize(new Dimension(100, 63));
    jTextAreaDescription.setMinimumSize(new Dimension(100, 63));
    jTextAreaDescription.setPreferredSize(new Dimension(100, 63));
    this.setMinimumSize(new Dimension(251, 108));
    this.add(jPanelServiceLevel,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    Date tmpDatum = aktDatum;
    Object[][] data = new Object[chartBars+1][chartBars+1];
    data[0][0] = "Datum";
    data[0][1] = "Belegung";

    for (int i=1; i <= chartBars; i++) {
      data[i][0] = outFormat.format(tmpDatum);
      data[i][1] = Integer.toString(belegung.getBelegung(tmpDatum));
      tmpDatum = manager.setDatum(tmpDatum, 1);
    }

    String[] columnNames = {"Datum", "Belegung"};
    final JTable jTableServiceLevel = new JTable(data, columnNames);
    jPanelServiceLevel.add(jTableServiceLevel, BorderLayout.NORTH);

    chartCols = new ChartColourScheme();
    maximum = Integer.parseInt(maxBelegung);
    //colPan = chartCols.getPanel();		// will be added to a tabbed pane later

    chart = new Chart(jTableServiceLevel,chartCols,chartBars,0,false,maximum,minimum,true,false);
    chartPan = chart.getPanel();
    this.add(chartPan,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
  }

  private void jbInit(Manager manager, Belegung belegung, String maxBelegung, Date altDatum, Date buchung, int anzTage) throws Exception {
    this.setLayout(gridBagLayoutServiceLevel);
    jPanelServiceLevel.setBorder(BorderFactory.createEtchedBorder());
    jPanelServiceLevel.setLayout(borderLayoutServiceLevel);
    jPanelInfo.setBorder(BorderFactory.createEtchedBorder());
    jPanelInfo.setPreferredSize(new Dimension(282, 139));
    jPanelInfo.setLayout(gridBagLayoutServiecLevelInfo);
    jTextAreaDescription.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextAreaDescription.setMaximumSize(new Dimension(100, 63));
    jTextAreaDescription.setMinimumSize(new Dimension(100, 63));
    jTextAreaDescription.setPreferredSize(new Dimension(100, 63));
    this.setMinimumSize(new Dimension(251, 108));
    this.add(jPanelServiceLevel,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    chartBars = anzTage * 3;  // Anzeige des gesamten Alternativbuchungs-Zeitraums
    Date tmpDatum = altDatum;
    Date buchDatum = buchung;
    int tagBuchung = 0;
    Object[][] data = new Object[chartBars+1][chartBars+1];
    data[0][0] = "Datum";
    data[0][1] = "Belegung";

    for (int i=1; i <= chartBars; i++) {
      if (chartBars/3 < 4) {
        data[i][0] = outFormat.format(tmpDatum);
      }
      else {
        data[i][0] = outFormatSmall.format(tmpDatum);
      }
      data[i][1] = Integer.toString(belegung.getBelegung(tmpDatum));
      int vglAltDatum = Integer.parseInt(compareFormat.format(tmpDatum));
      int vglBuchDatum = Integer.parseInt(compareFormat.format(buchDatum));
      if(vglAltDatum == vglBuchDatum) {
        tagBuchung = i-1;
      }
      tmpDatum = manager.setDatum(tmpDatum, 1);
    }

    String bezBuchung;
    String bezAlternative = "";

    if (tagBuchung == anzTage) {
      bezBuchung = "Wunsch-Buchung";
    }
    else {
      bezBuchung = "Alternativ-Buchung";
    }

    String[] columnNames = {bezBuchung, bezAlternative};
    final JTable jTableServiceLevel = new JTable(data, columnNames);
    jPanelServiceLevel.add(jTableServiceLevel, BorderLayout.NORTH);

    chartCols = new ChartColourScheme();
    maximum = Integer.parseInt(maxBelegung);
    //colPan = chartCols.getPanel();		// will be added to a tabbed pane later

    chart = new Chart(jTableServiceLevel,chartCols,chartBars,0,false,maximum,minimum,true,true);
    chartPan = chart.getPanel(tagBuchung, anzTage, bezAlternative, bezBuchung);
    this.add(chartPan,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
  }
}