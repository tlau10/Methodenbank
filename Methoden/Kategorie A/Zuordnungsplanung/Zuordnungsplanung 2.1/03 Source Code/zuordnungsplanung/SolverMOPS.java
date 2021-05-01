package zuordnungsplanung;

import java.io.*;

import javax.swing.*;

import java.lang.String;
import java.lang.InterruptedException;

/**
 * <p>
 * Überschrift: Zuordnungsplanung2004
 * </p>
 * <p>
 * Beschreibung: Anbindung von MOPS-Solver
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Organisation: FH-Konstanz
 * </p>
 * 
 * @author Angelina Bader, Benedikt Woelfle
 * @version 2.0
 */
public class SolverMOPS extends Thread implements Solver {
	String[] ergebnis;
	String[] ergVariablenKoordinaten;
	String[] ergVariablen;
	// Temp-Verzeichnis laden
	String solverDirectory = IniLaden.getTemp();

	String zielfunktionswert;

	public SolverMOPS() {
		ergebnis = new String[0];
	}

	public void starteSolver(DataModel dataModel) {

		Object columnVector[] = dataModel.getColumnVector();
		Object rowVector[] = dataModel.getRowVector();
		boolean maximierung = dataModel.getMaximize();

		try {
			// Create mops.mps
			PrintWriter myMOPSSolveIn = new PrintWriter(new BufferedWriter(
					new FileWriter(solverDirectory + "/mops.mps")));
			myMOPSSolveIn.println("NAME          Zuordnungsplanung2004 ");

			int anzRestriktionen = (dataModel.getRows() + dataModel
					.getColumns()) - 2;
			myMOPSSolveIn.println("ROWS");
			myMOPSSolveIn.println(" N  ZF");
			if (rowVector.length < columnVector.length) {
				for (int i = 1; i < anzRestriktionen + 1; i++) {
					if (i <= (dataModel.getRows() - 1)) {
						myMOPSSolveIn.println(" E  R" + i);
					} else {
						myMOPSSolveIn.println(" L  R" + i);
					}
				}
			} else {
				for (int i = 1; i < anzRestriktionen + 1; i++) {
					if (i <= (dataModel.getRows() - 1)) {
						myMOPSSolveIn.println(" L  R" + i);
					} else {
						myMOPSSolveIn.println(" E  R" + i);
					}
				}

			}

			myMOPSSolveIn.println("COLUMNS");
			myMOPSSolveIn
					.println("    MARK0001  'MARKER'                 'INTORG'");

			/************** Restriktionen bilden *******************/

			int anzZeilen = dataModel.getRows() - 1;
			int anzSpalten = dataModel.getColumns() - 1;

			int restriktionenZaehler1 = 1;
			int restriktionenZaehler2 = (anzZeilen + 1);

			int zeilenZaehler = 1;
			int spaltenZaehler = 1;

			String varKoordinaten;

			int xVariables = ((anzZeilen) * (anzSpalten));
			ergVariablenKoordinaten = new String[xVariables + 1];
			// Restriktionen
			for (int k = 1; k < xVariables + 1; k++) {
				if (spaltenZaehler > anzSpalten) {
					spaltenZaehler = 1;
					zeilenZaehler++;
					restriktionenZaehler2 = (anzZeilen + 1);
					restriktionenZaehler1++;
				}
				if (zeilenZaehler <= 9 && spaltenZaehler <= 9) {
					varKoordinaten = "0" + zeilenZaehler + "0" + spaltenZaehler;
				} else if (zeilenZaehler > 9 && spaltenZaehler <= 9) {
					varKoordinaten = zeilenZaehler + "0" + spaltenZaehler;
				} else if (zeilenZaehler <= 9 && spaltenZaehler > 9) {
					varKoordinaten = "0" + zeilenZaehler + spaltenZaehler;
				} else {
					varKoordinaten = "" + zeilenZaehler + spaltenZaehler;
				}
				for (int l = 1; l < 4; l++) {
					if (l == 1) {
						int zfWert = Integer.parseInt((String) dataModel
								.getValueAt(zeilenZaehler, spaltenZaehler));
						if (k <= 9) {
							myMOPSSolveIn.println("    X" + k + "        ZF"
									+ "        " + zfWert + ".");
						} else if (k > 9 && k <= 99) {
							myMOPSSolveIn.println("    X" + k + "       ZF"
									+ "        " + zfWert + ".");
						} else {
							myMOPSSolveIn.println("    X" + k + "      ZF"
									+ "        " + zfWert + ".");
						}
						ergVariablenKoordinaten[k] = varKoordinaten;
						spaltenZaehler++;
					} else if (l == 2) {
						if (k <= 9 && restriktionenZaehler1 <= 9) {
							myMOPSSolveIn.println("    X" + k + "        R"
									+ restriktionenZaehler1 + "        " + 1
									+ ".");
						} else if (k <= 9 && restriktionenZaehler1 > 9) {
							myMOPSSolveIn.println("    X" + k + "        R"
									+ restriktionenZaehler1 + "       " + 1
									+ ".");
						} else if (k <= 99 && restriktionenZaehler1 <= 9) {
							myMOPSSolveIn.println("    X" + k + "       R"
									+ restriktionenZaehler1 + "        " + 1
									+ ".");
						} else if (k <= 99 && restriktionenZaehler1 > 9) {
							myMOPSSolveIn.println("    X" + k + "       R"
									+ restriktionenZaehler1 + "       " + 1
									+ ".");
						} else if (k > 99 && restriktionenZaehler1 <= 9) {
							myMOPSSolveIn.println("    X" + k + "      R"
									+ restriktionenZaehler1 + "        " + 1
									+ ".");
						} else {
							myMOPSSolveIn.println("    X" + k + "      R"
									+ restriktionenZaehler1 + "       " + 1
									+ ".");
						}

					} else {
						if (k <= 9 && restriktionenZaehler2 <= 9) {
							myMOPSSolveIn.println("    X" + k + "        R"
									+ restriktionenZaehler2 + "        " + 1
									+ ".");
						} else if (k <= 9 && restriktionenZaehler2 > 9) {
							myMOPSSolveIn.println("    X" + k + "        R"
									+ restriktionenZaehler2 + "       " + 1
									+ ".");
						} else if (k <= 99 && restriktionenZaehler2 <= 9) {
							myMOPSSolveIn.println("    X" + k + "       R"
									+ restriktionenZaehler2 + "        " + 1
									+ ".");
						} else if (k <= 99 && restriktionenZaehler2 > 9) {
							myMOPSSolveIn.println("    X" + k + "       R"
									+ restriktionenZaehler2 + "       " + 1
									+ ".");
						} else {
							myMOPSSolveIn.println("    X" + k + "      R"
									+ restriktionenZaehler2 + "       " + 1
									+ ".");
						}
						restriktionenZaehler2++;

					}

				}
			}
			myMOPSSolveIn
					.println("    MARK0001  'MARKER'                 'INTEND'");
			/************** Restriktionen bilden ende *******************/

			myMOPSSolveIn.println("RHS");

			int X = 1;
			for (int i = 1; i < anzRestriktionen + 1; i++) {
				myMOPSSolveIn.println("    MYRHS     R" + i + "        " + 1
						+ ".");
			}

			myMOPSSolveIn.println("BOUNDS");
			for (int i = 1; i < xVariables; i++) {
				if (i <= 9) {
					myMOPSSolveIn.println(" UP MYBOUND   X" + i + "        1.");
				} else {
					myMOPSSolveIn.println(" UP MYBOUND   X" + i + "       1.");
				}

			}
			myMOPSSolveIn.println("ENDATA");
			myMOPSSolveIn.close();

			// write xmops.pro
			try {
				java.io.FileWriter fileWriterMOPSProfile = new java.io.FileWriter(
						solverDirectory + "\\xmops.pro");
				java.io.PrintWriter printWriter88 = new java.io.PrintWriter(
						fileWriterMOPSProfile);
				printWriter88.println("xoutsl = 1");
				printWriter88.println("xlptyp = 0");
				printWriter88.println("xmxmin = 15.0");
				printWriter88.println("xfnmps = '" + solverDirectory
						+ "\\mops.mps'");
				printWriter88.println("");
				if (maximierung) {
					printWriter88.println("xminmx='max'");
					printWriter88.println("");

				} else {
					printWriter88.println("xminmx='min'");
					printWriter88.println("");
				}
				printWriter88.flush();
				printWriter88.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"xmops.pro konnte nicht geschrieben werden!",
						"Solver fehler", JOptionPane.WARNING_MESSAGE);
			}

			// copy and start mops.exe
			BufferedReader p_inBuffer;
			String proc_input_string = "";
			try {

				java.io.FileWriter fileWritermopsbatch = new java.io.FileWriter(
						solverDirectory + "\\mopsbatch.cmd");
				java.io.PrintWriter batchWriter55 = new java.io.PrintWriter(
						fileWritermopsbatch);
				batchWriter55.println("@echo off");
				batchWriter55.println("copy  \"" + IniLaden.getMOPS() + "\" "
						+ solverDirectory + "\\");
				batchWriter55.println("C:");
				batchWriter55.println("chdir " + solverDirectory);
				batchWriter55.println("start /wait " + solverDirectory
						+ "\\MOPS.EXE > " + solverDirectory + "\\MOPS.LOG");
				batchWriter55.flush();
				batchWriter55.close();
				try {
					Thread.sleep(5 * 100);
				} catch (InterruptedException e) {
				}
				try {
					Process mopsproc = Runtime.getRuntime().exec(
							solverDirectory + "\\mopsbatch.cmd");
					mopsproc.waitFor();
					mopsproc.destroy();
				} catch (Exception e) {
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"MOPS.exe konnte nicht kopiert werden",
						"Solver fehler", JOptionPane.WARNING_MESSAGE);
			}

			/**************** Auslesen aus dem Solver **************/

			File MOPS_SolveOut = new File(solverDirectory + "/Mops.lps");
			System.out.println(solverDirectory);
			BufferedReader outputDatei = new BufferedReader(new FileReader(
					MOPS_SolveOut));

			String Ausgabe = "";
			String SolutionRead = "";
			// Zielfunktionswert auslesen
			while (!SolutionRead.equals("functional")) {
				Ausgabe = outputDatei.readLine();
				SolutionRead = Ausgabe.substring(11, 21);
			}
			this.zielfunktionswert = Ausgabe.substring(30, 33);
			// Standorte Auslesen
			while (!SolutionRead.equals("SECTION 2 - COLUMNS")) {
				Ausgabe = outputDatei.readLine();
				SolutionRead = Ausgabe.substring(2, 21);
			}
			outputDatei.readLine();
			int CountLines = 0;
			ergVariablen = new String[xVariables];
			int anzErgVariablen = 0;
			for (int i = 0; i < xVariables; i++) {
				Ausgabe = outputDatei.readLine();
				SolutionRead = Ausgabe.substring(32, 33);
				System.out.println(SolutionRead);
				if (SolutionRead.equals("1")) {
					// Nummer des X-Wertes aus Tabelle holen
					if (i <= 9) {
						this.ergVariablen[i] = Ausgabe.substring(12, 13);
					} else if (i <= 99) {
						this.ergVariablen[i] = Ausgabe.substring(12, 14);
					} else {
						this.ergVariablen[i] = Ausgabe.substring(12, 15);
					}
					anzErgVariablen++;
				}

			}
			outputDatei.close();
			int counter = 0;
			ergebnis = new String[anzErgVariablen];
			for (int i = 0; i < ergVariablen.length; i++) {
				if (ergVariablen[i] == null) {
					continue;
				}
				String test = ergVariablenKoordinaten[i + 1].substring(0, 2);
				ergebnis[counter] = rowVector[Integer
						.parseInt((ergVariablenKoordinaten[i + 1]).substring(0,
								2))]
						+ " -> "
						+ columnVector[Integer
								.parseInt((ergVariablenKoordinaten[i + 1])
										.substring(2, 4))];
				counter++;

			}

			// Temp Verzeichnis leeren
			
			try {

				java.io.FileWriter fileWriterlpbatch = new java.io.FileWriter(
						solverDirectory + "\\mopsclear.bat");
				java.io.PrintWriter batchWriter55 = new java.io.PrintWriter(
						fileWriterlpbatch);
				batchWriter55.println("@echo off");
				batchWriter55.println("C:");
				batchWriter55.println("chdir " + solverDirectory);
				batchWriter55.println("del mops.ips");
				batchWriter55.println("del mops.lps");
				batchWriter55.println("del MOPS.txt");
				batchWriter55.println("del mops.sta");
				batchWriter55.println("del mops.mps");
				batchWriter55.println("del xmops.pro");
				batchWriter55.println("del mopsbatch.cmd");
				batchWriter55.flush();
				batchWriter55.close();
				try {
					Thread.sleep(5 * 100);
				} catch (InterruptedException e) {
				}
				try {
					Process lpproc = Runtime.getRuntime().exec(
							solverDirectory + "\\mopsclear.bat");
					lpproc.waitFor();
					lpproc.destroy();
				} catch (Exception e) {
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Temp konnte nicht gellert werden",
						"Loeschfehler", JOptionPane.WARNING_MESSAGE);
			}

		}

		catch (IOException eio) {
			ExceptionDialog.showExceptionDialog("Fehler im Solver MOPS.",
					eio.getMessage(), eio);
		} catch (Exception ex) {
			ExceptionDialog.showExceptionDialog("Fehler im Solver MOPS.",
					ex.getMessage(), ex);
		}
		return;
	}

	public String[] getErgebnis() {
		return ergebnis;
	}

	public String getZielfunktionswert() {
		return zielfunktionswert;
	}

	@Override
	public long getZeit() {
		return 0;
	}

}
