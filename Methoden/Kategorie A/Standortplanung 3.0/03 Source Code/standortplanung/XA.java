package standortplanung;

import java.io.*;
import java.io.File;
import javax.swing.*;
import java.lang.String;
import java.lang.InterruptedException;

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

public class XA extends Thread implements Solver {
	String[] ergebnis;
	String zielfunktionswert;
	private Process solver;

	public XA() {
		ergebnis = new String[0];
	}

	public void starteSolver(Daten matrix, int distanz) {
		// Object columnVector[] = matrix.getColumnVector();
		Object rowVector[] = matrix.getRowVector();
		int entfernung = distanz;

		// Temp-Verzeichnis laden
		String solverDirectory = IniLaden.getTemp();

		try {
			// BatchDatei und InputFile für Solver erstellen
			PrintWriter myXASolveBatch = new PrintWriter(new BufferedWriter(
					new FileWriter(solverDirectory + "/solver.bat")));
			PrintWriter myXASolveIn = new PrintWriter(new BufferedWriter(
					new FileWriter(solverDirectory + "/xa_solve.in")));
			PrintWriter myXASolveCopyBatch = new PrintWriter(
					new BufferedWriter(new FileWriter(solverDirectory
							+ "/solverCopy.bat")));
			// Batchdateien schreiben

			// XA-Solver nach c:\temp kopieren
			// File SolverXA = new File ("solver/xa.exe " +
			// Variablen.solverDirectory);
			File SolverXA = new File(IniLaden.getXA() + " " + solverDirectory);
			myXASolveCopyBatch.println(Variablen.copy + SolverXA);
			// Sovler.pif nach c:\temp kopieren
			// File SolverPif = new File ("SOLVER.pif " +
			// Variablen.solverDirectory);
//			File SolverPif = new File(IniLaden.getSolverPIF() + " "
//					+ solverDirectory);
//			myXASolveCopyBatch.println(Variablen.copy + SolverPif);
			myXASolveCopyBatch.close();

			// Batch-Datei für Solver-Start
			myXASolveBatch.println("@echo off");
			myXASolveBatch.println("REM Solver: XA");
			myXASolveBatch.println("set Oldpath=%path%");
			File Solver = new File(solverDirectory + "/xa");
			myXASolveBatch.println("path " + Solver + ";%path%");
			myXASolveBatch.println("xa xa_solve.in > xa_solve.out");
			myXASolveBatch.println("path %Oldpath%");
			myXASolveBatch.println("set Oldpath=");
			myXASolveBatch.close();

			// Minimierung
			String min = "MINIMIZE";

			myXASolveIn.println("..TITLE");
			myXASolveIn.println("  Standortplanung");
			myXASolveIn.println("..OBJECTIVE " + min);
			/********************** Zielfunktion bilden *************/
			// Ganzzahligkeit
			myXASolveIn.println("  [");
			String Zielfunktion = "   + ";
			int i = 0;
			int t = 0;

			for (i = 1; i < rowVector.length; i++) {
				if (t == 100) {
					Zielfunktion = Zielfunktion.substring(0,
							Zielfunktion.length() - 3);
					myXASolveIn.println(Zielfunktion);
					Zielfunktion = "   + ";
					t = 0;
				}
				t++;
				if (i <= 9)
					Zielfunktion = Zielfunktion + " x0" + (i) + " + ";
				else
					Zielfunktion = Zielfunktion + " x" + (i) + " + ";
			}

			// letztes ' + ' oder ' - ' muss gelöscht werden und ein
			// abschließendes Semikolon wird hinzugefügt
			Zielfunktion = Zielfunktion.substring(0, Zielfunktion.length() - 3);
			myXASolveIn.println(Zielfunktion);
			// Ganzzahligkeit
			myXASolveIn.println("  ]");
			myXASolveIn.println("..BOUNDS");

			// Bounds
			for (i = 1; i < rowVector.length; i++) {
				if (i <= 9) {
					myXASolveIn.println(" x0" + (i) + ">=0");
					myXASolveIn.println(" x0" + (i) + "<=1");
				} else {
					myXASolveIn.println(" x" + (i) + ">=0");
					myXASolveIn.println(" x" + (i) + "<=1");
				}
			}

			/************** Restriktionen bilden *******************/
			myXASolveIn.println("..CONSTRAINTS");
			int k = 0;
			int l = 0;

			// Restriktionen
			String vergleich = " >= 1";
			String Restriktion = "";

			for (k = 1; k < rowVector.length; k++) {
				Restriktion = "  R" + (k) + ": ";
				for (l = 1; l < rowVector.length; l++) {
					if ((Integer.parseInt((String) matrix.getValueAt(l, k))) <= entfernung) {
						if (k <= 9 && l <= 9)
							Restriktion += "x0" + (l) + " + ";
						else if (l <= 9)
							Restriktion += "x0" + (l) + " + ";
						else
							Restriktion += "x" + (l) + " + ";

					}
				}
				Restriktion = Restriktion
						.substring(0, Restriktion.length() - 3) + vergleich;
				myXASolveIn.println(Restriktion);
			}

			myXASolveIn.close();
			/****************** XA-Solver starten ******************/
			solver = Runtime.getRuntime().exec(
					solverDirectory + "/solverCopy.bat");
			try {
				solver.waitFor();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null,
						"XA-Solver nicht richtig gestartet!",
						"XA-Solver fehler", JOptionPane.WARNING_MESSAGE);
				return;
			}
			//solver = Runtime.getRuntime().exec(solverDirectory + "/SOLVER.pif");
			try {
				solver.waitFor();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null,
						"XA-Solver nicht richtig gestartet!",
						"XA-Solver fehler", JOptionPane.WARNING_MESSAGE);
				return;
			}
			/**************** Auslesen aus dem Solver **************/
			File xa_SolveOut = new File(solverDirectory + "/xa_solve.out");
			BufferedReader outputDatei = new BufferedReader(new FileReader(
					xa_SolveOut));
			// Zielfunktionswert
			String Ausgabe = "";
			String Solution = "";
			while (Solution.compareTo("SOLUTION") != 0) {
				Ausgabe = outputDatei.readLine();
				if (Ausgabe.length() >= 8)
					Solution = Ausgabe.substring(0, 8);
			}
			zielfunktionswert = (Ausgabe.substring(21, 27).trim());
			/************************** Ergebnis *************************************/
			String Ergebniswert = "";
			for (int x = 0; x < 4; x++)
				Ergebniswert = outputDatei.readLine();
			String wert;

			ergebnis = new String[Integer.parseInt(zielfunktionswert)];

			int index = 0;
			boolean gehtnichtsmehr = false;
			while (gehtnichtsmehr == false) {
				if (Ergebniswert.substring(17, 18).compareTo("1") == 0
						|| Ergebniswert.substring(17, 18).compareTo("0") == 0) {
					for (int y = 0; y < 6; y++) {
						if (Ergebniswert.length() > 18) {
							wert = Ergebniswert.substring(17, 18);
							if (wert.compareTo("1") == 0) {
								ergebnis[index] = Ergebniswert.substring(6, 10);
								index++;
							}
						}
						if (Ergebniswert.length() > 57) {
							wert = Ergebniswert.substring(56, 57);
							if (wert.compareTo("1") == 0) {
								ergebnis[index] = Ergebniswert
										.substring(45, 49);
								index++;
							}
						}
						for (int u = 0; u < 3; u++)
							Ergebniswert = outputDatei.readLine();
					}
					for (int x = 0; x < 7; x++)
						Ergebniswert = outputDatei.readLine();
				} else
					gehtnichtsmehr = true;

			}
			outputDatei.close();
			
			// Temp Verzeichnis leeren, Partnerprojekt
			Runtime rt = Runtime.getRuntime();
			Process pr = rt
					.exec("cmd /c start cmd.exe /C \"cd c:\\temp && del Solver.cmd && del solverCopy.cmd && del xa_solve.in \"");
			pr.waitFor();
			
			// Ergebnisfeld zusammensetzen
			for (int p = 0; p < ergebnis.length; p++) {

				ergebnis[p] = rowVector[Integer.parseInt(ergebnis[p].substring(
						2, 4))] + "";

			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"XA-Solver nicht richtig gestartet!", "Solver-Fehler",
					JOptionPane.WARNING_MESSAGE);
			return;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"XA-Solver nicht richtig gestartet!", "Solver-Fehler",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		return;
	}

	public String[] getErgebnis() {
		return ergebnis;
	}

	public String getZielfunktionswert() {
		return zielfunktionswert;
	}

}
