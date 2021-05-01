/*
 * Created on 02.07.2000
 *
 * Projektarbeit Simulation Sommersemester 2003
 * Fachhochschule Konstanz
 */
package hotelbelegung;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.text.*;

/**
 * Überschrift: Hotelbelegung Beschreibung: Copyright: Copyleft (c) 2014
 * Organisation: HTWG
 * 
 * @author Florian Raiber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003
 * @Version 2.0.1 Christian Gruhler SS08
 * @Version 4.0 Vitaliy Davats, Dominique Lebert, Manuel Falkenstein WS2013/14
 */

public class JTablePanel extends JPanel {

	private static final long serialVersionUID = -8625009748307476111L;
	private JTable jtTable;
	private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");

	public JTablePanel(Manager myManager, Belegung myBelegung) {

		this.setLayout(new GridLayout(1, 1));

		// Spaltenüberschriften für die Tabelle
		Object[] columnNames = new Object[Manager.kategorien + 1];
		columnNames[0] = new String("Datum");
		columnNames[1] = new String("Kategorie 1");
		columnNames[2] = new String("Kategorie 2");
		columnNames[3] = new String("Kategorie 3");
		// Tabellendaten: x Zeilen und 4 Spalten
		String[][] data = new String[myManager.getZeitraum()][Manager.kategorien + 1];

		Date tempDate = new Date();
		tempDate.setTime(myManager.getAnfangDatum().getTime());
		// Tabelleneinträge für alle Tage des Betrachtungszeitraumes
		for (int i = 0; i < myManager.getZeitraum(); i++) {
			// in 1. Spalte Datum eintragen
			data[i][0] = outFormat.format(tempDate).toString();
			// in die Kategoriespalten entsprechende Werte eintragen
			data[i][1] = "DB: "
					+ Double.toString((double) Math.round(myBelegung
							.getDeckungsbeitrag(tempDate, 1) * 100) / 100);
			data[i][2] = "DB: "
					+ Double.toString((double) Math.round(myBelegung
							.getDeckungsbeitrag(tempDate, 2) * 100) / 100);
			data[i][3] = "DB: "
					+ Double.toString((double) Math.round(myBelegung
							.getDeckungsbeitrag(tempDate, 3) * 100) / 100);

			/*
			 * data[i][1] = (new
			 * Integer(myBelegung.getBelegung(tempDate,1))).toString();
			 * data[i][2] = (new
			 * Integer(myBelegung.getBelegung(tempDate,2))).toString();
			 * data[i][3] = (new
			 * Integer(myBelegung.getBelegung(tempDate,3))).toString();
			 * 
			 * // Überbuchungswahrscheinlichkeit data[i][1] = data[i][1] + " / "
			 * + Double.toString((double)Math.round(myBelegung.getUeberbuchung(
			 * tempDate,1)*100)/100);
			 * System.out.println(Double.toString((double)
			 * Math.round(myBelegung.getUeberbuchung(tempDate,1)*100)/100) + "/"
			 * + myBelegung.getUeberbuchung(tempDate,1)); data[i][2] =
			 * data[i][2] + " / " +
			 * Double.toString((double)Math.round(myBelegung
			 * .getUeberbuchung(tempDate,2)*100)/100);
			 * System.out.println(Double.
			 * toString((double)Math.round(myBelegung.getUeberbuchung
			 * (tempDate,2)*100)/100) + "/" +
			 * myBelegung.getUeberbuchung(tempDate,2)); data[i][3] = data[i][3]
			 * + " / " +
			 * Double.toString((double)Math.round(myBelegung.getUeberbuchung
			 * (tempDate,3)*100)/100);
			 * System.out.println(Double.toString((double
			 * )Math.round(myBelegung.getUeberbuchung(tempDate,3)*100)/100) +
			 * "/" + myBelegung.getUeberbuchung(tempDate,3));
			 * 
			 * // freie Zimmer data[i][1] = data[i][1] + " / " +
			 * Integer.toString(150-myBelegung.getBelegung(tempDate,1));
			 * data[i][2] = data[i][2] + " / " +
			 * Integer.toString(300-myBelegung.getBelegung(tempDate,2));
			 * data[i][3] = data[i][3] + " / " +
			 * Integer.toString(250-myBelegung.getBelegung(tempDate,3));
			 * 
			 * // Deckungsbeitrag data[i][1] = data[i][1] + " / " +
			 * Double.toString
			 * ((double)Math.round(myBelegung.getDeckungsbeitrag(tempDate
			 * ,1)*100)/100);
			 * System.out.println(Double.toString((double)Math.round
			 * (myBelegung.getDeckungsbeitrag(tempDate,1)*100)/100) + "/" +
			 * myBelegung.getUeberbuchung(tempDate,1)); data[i][2] = data[i][2]
			 * + " / " +
			 * Double.toString((double)Math.round(myBelegung.getDeckungsbeitrag
			 * (tempDate,2)*100)/100);
			 * System.out.println(Double.toString((double
			 * )Math.round(myBelegung.getUeberbuchung(tempDate,2)*100)/100) +
			 * "/" + myBelegung.getUeberbuchung(tempDate,2)); data[i][3] =
			 * data[i][3] + " / " +
			 * Double.toString((double)Math.round(myBelegung
			 * .getDeckungsbeitrag(tempDate,3)*100)/100);
			 * System.out.println(Double
			 * .toString((double)Math.round(myBelegung.getUeberbuchung
			 * (tempDate,3)*100)/100) + "/" +
			 * myBelegung.getUeberbuchung(tempDate,3));
			 */
			// Datum um einen Tag erhöhen
			tempDate = myManager.setDatum(tempDate, 1);
		}

		jtTable = new JTable(data, columnNames);
		// jtTable.doLayout();
		JScrollPane test = new JScrollPane(jtTable);
		this.add(test);
		this.setPreferredSize(new Dimension(100, 100));
		this.setBounds(new Rectangle(0, 0, 500, 400));
	}
}
