package hotelbelegung;


import java.util.*;
import java.text.*;
import java.awt.*;
import javax.swing.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      fh-konstanz
 * @author:      Oliver Schraag
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003
 */

public class ServiceLevelPanel extends JPanel {
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
  //private Date aktDatum = new Date();
  private int chartBars = 10; // Standard Anzeige der Belegung über zehn Tage hinweg
  private int minimum = 0;
  private int maximum = 0;
  private Object[][] data;

  // Normalansicht 	
  public ServiceLevelPanel(Manager manager, Belegung belegung, int maxBelegung, Date beginnAnzeige, int tageAnzeige, int kategorie)
  {
    try {
      jbInit(manager, belegung, maxBelegung, beginnAnzeige, tageAnzeige, kategorie);
      //System.out.println("Standard");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  // Ansicht mit Alternativbuchung
  public ServiceLevelPanel(Manager manager, Belegung belegung, int maxBelegung, Date altDatum, Date buchung, int anzTage, int kategorie) {
    try {
      jbInit(manager, belegung, maxBelegung, altDatum, buchung, anzTage, kategorie);
	  //System.out.println("Buchung");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit(Manager manager, Belegung belegung, int maxBelegung, Date beginnAnzeige, int tageAnzeige, int kategorie) throws Exception {
	maximum = maxBelegung;
	chartBars = tageAnzeige;
    this.setLayout(null);
    this.setMinimumSize(new Dimension(251, 108));
        
    JLabel jlKategorie = new JLabel("Kategorie: "+kategorie);
	jlKategorie.setBounds(new Rectangle(230,10,100,30));
    this.add(jlKategorie, null);

    // JTable mit Daten erzeugen
    Date tmpDatum = beginnAnzeige;
    //data = new Object[chartBars+1][chartBars+1];
	data = new Object[chartBars+1][2];
    data[0][0] = "Datum";
    data[0][1] = "Belegung";
    for (int i=1; i <= chartBars; i++) {
	  data[i][0] = outFormatSmall.format(tmpDatum);
      data[i][1] = Integer.toString(belegung.getBelegung(tmpDatum,kategorie));
      tmpDatum = manager.setDatum(tmpDatum, 1);
    }
	String[] columnNames = {"Datum", "Belegung"};
    final JTable jTableServiceLevel = new JTable(data, columnNames);
	
	// Chart erstellen
    chartCols = new ChartColourScheme();
    chart = new Chart(jTableServiceLevel,chartCols,chartBars,0,false,maximum,minimum,true,false);
    chartPan = chart.getPanel();
   
	chartPan.setBounds(new Rectangle(0,0,500,350));
    this.add(chartPan, null);
    
	this.setPreferredSize(new Dimension(100, 100));
	this.setBounds(new Rectangle(0, 0, 500, 400));    
  }

  private void jbInit(Manager manager, Belegung belegung, int maxBelegung, Date altDatum, Date buchung, int anzTage, int kategorie) throws Exception {
	maximum = maxBelegung;
	this.setLayout(null);
    this.setMinimumSize(new Dimension(251, 108));
    
	JLabel jlKategorie = new JLabel("Kategorie: "+kategorie);
	jlKategorie.setBounds(new Rectangle(230,10,100,30));
	this.add(jlKategorie, null);
    

    chartBars = anzTage * 3;  // Anzeige des gesamten Alternativbuchungs-Zeitraums
    Date tmpDatum = altDatum;
    Date buchDatum = buchung;
    int tagBuchung = 0;
    data = new Object[chartBars+1][chartBars+1];
    data[0][0] = "Datum";
    data[0][1] = "Belegung";
	
    for (int i=1; i <= chartBars; i++) {
      data[i][0] = outFormatSmall.format(tmpDatum);
      data[i][1] = Integer.toString(belegung.getBelegung(tmpDatum,kategorie));
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

	// Chart erstellen
    chartCols = new ChartColourScheme();
    chart = new Chart(jTableServiceLevel,chartCols,chartBars,0,false,maximum,minimum,true,true);
    chartPan = chart.getPanel(tagBuchung, anzTage, bezAlternative, bezBuchung);
     
	chartPan.setBounds(new Rectangle(0,0,500,350));
	this.add(chartPan, null);
    
	this.setPreferredSize(new Dimension(100, 100));
	this.setBounds(new Rectangle(0, 0, 500, 400)); 
  }
  
  
  public Object[][] getData() {
  	return data;
  }
  
  public int getChartBars() {
  	return chartBars;
  }
}