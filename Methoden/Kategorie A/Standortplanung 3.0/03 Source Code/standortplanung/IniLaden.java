package standortplanung;

import java.io.*;
import java.util.StringTokenizer;

/**
 * <p>
 * Überschrift: Standortplanung
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author Manuel Thoma, Markus Klemens
 * @version 3.0
 */

public class IniLaden {

	public static String laden(String dateiname) // Die Lade-Methode
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
		//System.out.println("Test 2: " + ausgabe);
		return ausgabe; // return des Ausgabestrings

	}

	public static String getLpSolvePath(){
            //Beispiel für das Ini-File: lpsolve=solver/lp_solve.exe
		// DefaultPfad für den Solver
			String ausgabe = "Z:\\Gruetz\\BESF\\Solver\\LP_Solve\\Exec\\lp_solve.exe";
            String solverPath = "solverpath.txt";
            String input = laden(solverPath);
            String output = tokenize(input,"LP-Solve");

            if(output != null){
              ausgabe = output;
            }
            return ausgabe;
          }

	public static String getXA() {
		// Beispiel für das Ini-File: xa=solver/xa.exe
		String ausgabe = "solver/xa.exe";
		String solverPath = "solverpath.txt";
		String input = laden(solverPath);
		String output = tokenize(input, "XA");

		if (output != null) {
			ausgabe = output;
		}
		return ausgabe;
	}

	public static String getMOPS() {
		// Beispiel für das Ini-File: mops=L:\\BESF\\Solver\\MOPS
		// 7.06\\Exec\\mops.exe
		// DefaultPfad für Solver
		String ausgabe = "Z:\\Gruetz\\BESF\\Solver\\MOPS\\MOPS EXE\\mops.exe";
		String solverPath = "solverpath.txt";
		String input = laden(solverPath);
		String output = tokenize(input, "MOPS");

		if (output != null) {
			ausgabe = output;
		}
		return ausgabe;
	}

	// Obsolet, da SOLVERPIF nicht existiert
	public static String getSolverPIF() {
		// Beispiel für das Ini-File: solverPIF=SOLVER.PIF
		String ausgabe = "SOLVER.pif";
		String iniPath = "path.ini";
		String input = laden(iniPath);
		String output = tokenize(input, "solverPIF");

		if (output != null) {
			ausgabe = output;
		}
		return ausgabe;
	}

	public static String getTemp() {
		// Beispiel für das Ini-File: temp=C:/temp
		String ausgabe = "C:\\Temp";
		String solverPath = "solverpath.txt";
		String input = laden(solverPath);
		String output = tokenize(input, "TEMP");

		if (output != null) {
			ausgabe = output;
		}
		return ausgabe;
	}

	public static String tokenize(String input, String solverName) {
		String ausgabe = null;

		if (!input.equals("")) {
			StringTokenizer st = new StringTokenizer(input, ";");

			while (st.hasMoreElements()) {
				String s = st.nextToken();
				s = s.trim();// Leerzeichen entfernen

				if (s.startsWith(solverName)) {
					int position = s.indexOf(":");

					if (position > 0) { // Pfadname ausschneiden
						ausgabe = s.substring(position + 1);
						ausgabe.trim();
					} else {
						System.err.println("Konnte keinen Substring finden");
					}
				}
			}
		}
		return ausgabe;
	}
}
