package model;

import java.io.*;

import view.ErrorMessages;
import controller.Matrix;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: LP_Solve
 * </p>
 * <p>
 * Description: Implementiert den LP_Solve als Solver, startet die Berechnung
 * und ließt das Ergebnis aus
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */

public class LP_Solve implements SolverInterface {

	private String tempPath;
	private String lpSolvePath;
	private String enter;

	public LP_Solve() {
		tempPath = IniPaths.getTempPath();
		lpSolvePath = IniPaths.getLpSolvePath();
		enter = System.getProperty("line.separator");
	}

	public double[] calcModel(Matrix lpModell, Matrix borders) {
		tempPath = IniPaths.getTempPath();
		lpSolvePath = IniPaths.getLpSolvePath();
		enter = System.getProperty("line.separator");
		createInputFile(lpModell, borders);
		createBatchFile();
		int numberColumns = lpModell.getNumberOfColumns();
		// Ausfuehren des Solvers
		try {
			synchronized (this) {
				// Hier wird der Solver aufgerufen
				Runtime rt = Runtime.getRuntime();
				Process p = rt.exec("cmd /C start " + tempPath + "diaet.bat");
				p.waitFor();
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Ausführen des Solvers");
		}

		// lesen der Ergebnisdatei
		double[] result = new double[numberColumns - 1];

		// Es wird versucht die vom Solver erzeugte Lösungsdatei zu lesen.
		// Falls dies nicht gelingt wird 2 Sekunden gewartet und es wird
		// erneut versucht, bis maximal 5 Versuche erreicht sind.

		int readTries = 1;
		boolean readSuccessful = false;

		while (!readSuccessful && readTries <= 5) {
			readTries++;

			try {
				BufferedReader in = new BufferedReader(new FileReader(tempPath
						+ "diaet.out"));

				try {
					// Zielfunktionswert
					in.readLine();
					String firstLine = in.readLine();

					// Testen ob Lösung berechenbar ist, sonst gleich 0 als
					// Ergebnis zurückgeben
					// "This problem is infeasible"
					// "This problem is unbounded"

					if (firstLine.substring(0, 4).equalsIgnoreCase("This")) {
						in.close();
						return result;
					}

					result[0] = Double.valueOf(firstLine.substring(28).trim())
							.doubleValue();

					String tmp;

					// Zählt die Zeilen bis das Ergebnis dargestellt wird, bei
					// mehr als 10 Zeilen wird ein Fehler ausgegeben
					int whiteLineCounter = 0;

					for (int i = 0; i < numberColumns - 2; i++) {
						readSuccessful = true;
						try {
							tmp = in.readLine();
							result[Integer.valueOf(tmp.substring(1, 4).trim())
									.intValue()] = Double.valueOf(
									tmp.substring(5).trim()).doubleValue();
						} catch (Exception e) {
							whiteLineCounter++;
							i--;
							if (whiteLineCounter >= 10) {
								readSuccessful = false;
								break;
							}
						}
					}

				} catch (Exception e) {
					System.out
							.println("Warten bis Ergebnisdatei fertig ist....");
					readSuccessful = false;
					in.close();
				}
			} catch (Exception e) {
				ErrorMessages
						.throwErrorMessage("Fehler beim Einlesen der Ergebnisdatei");
				readSuccessful = false;
			}
		}
		return result;
	}

	public void createBatchFile() {
		File newBatFile = new File(tempPath + "diaet.bat");
		try {
			FileWriter writeBatOut = new FileWriter(newBatFile);
			writeBatOut.write("\"" + lpSolvePath + "\" -p <" + tempPath
					+ "diaet.in >" + tempPath + "diaet.out");
			writeBatOut.write(enter + "exit");
			writeBatOut.flush();
			writeBatOut.close();
		} catch (IOException e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Erzeugen der LP-Solve Batch-Datei");
		}
	}

	public void createInputFile(Matrix lpModell, Matrix borders) {
		// Pfade von Oberfläche holen
		int numberColumns = lpModell.getNumberOfColumns();
		int numberLines = lpModell.getNumberOfRows();

		String solver_input = new String();

		// Zielfunktionszeile
		solver_input = solver_input.concat(lpModell.getElement(0,
				numberColumns - 1));
		solver_input = solver_input.concat(": ");

		for (int i = 0; i < numberColumns; i++) {
			if (i < numberColumns - 3) {
				solver_input = solver_input.concat(lpModell.getElement(0, i)
						+ "x" + (i + 1) + " + ");
			} else if (i == numberColumns - 3) {
				solver_input = solver_input.concat(lpModell.getElement(0, i)
						+ "x" + (i + 1) + ";");
			}
		}
		solver_input = solver_input.concat(enter);

		// Restriktionen
		for (int i = 1; i < numberLines; i++) {
			solver_input = solver_input.concat(enter + "R" + i + ": ");
			for (int j = 0; j < numberColumns; j++) {
				if (j < numberColumns - 3) {
					solver_input = solver_input.concat(lpModell
							.getElement(i, j) + " x" + (j + 1) + " + ");
				} else if (j < numberColumns - 2) {
					solver_input = solver_input.concat(lpModell
							.getElement(i, j) + " x" + (j + 1));
				} else if (j < numberColumns - 1) {
					solver_input = solver_input.concat(" "
							+ lpModell.getElement(i, j));
				} else {
					solver_input = solver_input.concat(" "
							+ lpModell.getElement(i, j) + ";");
				}
			}
		}

		// Ganzzahligkeit und Variableneinschraenkungen
		solver_input = solver_input.concat(enter);
		for (int x = 0; x < numberColumns - 2; x++) {
			if (borders.getElementInt(x, 0) > 0)
				solver_input = solver_input.concat("x" + (x + 1) + ">="
						+ borders.getElement(x, 0) + ";" + enter);
			if (borders.getElementInt(x, 1) > 0)
				solver_input = solver_input.concat("x" + (x + 1) + "<="
						+ borders.getElement(x, 1) + ";" + enter);
		}

		boolean first = true;
		for (int x = 0; x < numberColumns - 2; x++) {
			if (borders.getElement(x, 2).equalsIgnoreCase("ja")) {
				if (first) {
					solver_input = solver_input.concat(enter + "int ");
					solver_input = solver_input.concat("x" + (x + 1));
					first = false;
				} else
					solver_input = solver_input.concat(",x" + (x + 1));
			}
		}
		if (first == false)
			solver_input = solver_input.concat(";");

		File newInputFile = new File(tempPath + "diaet.in");
		try {
			FileWriter writeOut = new FileWriter(newInputFile);
			writeOut.write(solver_input);
			writeOut.flush();
			writeOut.close();
		} catch (IOException e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Erzeugen der LP-Solve Input-Datei");
		}
	}
}
