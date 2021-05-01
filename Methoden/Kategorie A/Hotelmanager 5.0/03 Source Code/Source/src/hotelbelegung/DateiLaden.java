package hotelbelegung;

import java.io.*;

/**
 * Überschrift: Hotelbelegung Beschreibung: Copyright: Copyleft (c) 2014
 * Organisation: HTWG
 * 
 * @author Volker Wohlleber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003
 * @Version 2.0.1 Christian Gruhler SS08
 * @Version 4.0 Vitaliy Davats, Dominique Lebert, Manuel Falkenstein WS2013/14
 * @Version 5.0 Rehman Latif, Umut Ak WS2015/16
 */
public class DateiLaden {
	public String Laden(String dateiname) // Die Lade-Methode
	{
		String ausgabe = "";
		File datei = new File(dateiname);

		if (datei != null) {
			try // try-catch Prozedur um eventl fehler abzufangen
			{
				FileReader eingabestrom = new FileReader(datei);
				int gelesen;
				StringBuffer text = new StringBuffer(100);
				boolean ende = false;

				// lese Zeichen, bis Dateiende erreicht ist
				while (!ende) {
					gelesen = eingabestrom.read();

					if (gelesen == -1) {
						ende = true;
					} else {
						text.append((char) gelesen);
					}
				}

				ausgabe = (text.toString()); // Ausgabe des eingelesenen Strings
				eingabestrom.close(); // Schließen des Lesestroms (Datei wird
										// dadurch wieder "frei")
			} catch (FileNotFoundException f) // Fängt Fehler beim Öffnen ab
			{
				System.err.println(dateiname + " konnte nicht unter "
						+ System.getProperty("user.dir") + " gefunden werden.");
			} catch (IOException f) // Fängt Fehler beim Einlesen ab
			{
				System.err.println("Fehler beim Einlesen der Datei "
						+ dateiname + " von " + System.getProperty("user.dir"));
			}
		}
		return ausgabe; // return des Ausgabestrings

	}
}
