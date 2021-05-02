package standortplanung;

import java.io.*;

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

public class MOPS extends Thread implements Solver {
	String[] ergebnis;
	String zielfunktionswert;

	public MOPS() {
		ergebnis = new String[0];
	}

	public void starteSolver(Daten matrix, int distanz) {
		// Object columnVector[] = matrix.getColumnVector();
		Object rowVector[] = matrix.getRowVector();
		int entfernung = distanz;

		// Temp-Verzeichnis laden
		String solverDirectory = IniLaden.getTemp();
		// System.out.println("Das VZ: " + solverDirectory);

		try {
			// Create mops.mps
			PrintWriter myMOPSSolveIn = new PrintWriter(new BufferedWriter(
					new FileWriter(solverDirectory + "\\mops.mps")));
			myMOPSSolveIn.println("NAME          Standortplanung");

			myMOPSSolveIn.println("ROWS");
			myMOPSSolveIn.println(" N  ZF");
			for (int i = 1; i < rowVector.length; i++) {
				myMOPSSolveIn.println(" G  R" + i);
			}

			myMOPSSolveIn.println("COLUMNS");
			myMOPSSolveIn.println("    MARK0000  'MARKER'                 'INTORG'");
			int Y = 1;

			/************** Restriktionen bilden *******************/
			int k = 0;
			int l = 0;

			// Restriktionen

			for (k = 1; k < rowVector.length; k++) {
				myMOPSSolveIn.println("    C" + k + "        ZF        " + Y
						+ ".");
				for (l = 1; l < rowVector.length; l++) {

					if ((Integer.parseInt((String) matrix.getValueAt(k, l))) <= entfernung) {
						myMOPSSolveIn.println("    C" + k + "        R" + l
								+ "        " + Y + ".");

					}
				}
			}

			/************** Restriktionen bilden ende *******************/

			myMOPSSolveIn.println("RHS");
			int X = 1;
			for (int i = 1; i < rowVector.length; i++) {
				myMOPSSolveIn.println("    MYRHS     R" + i + "        " + X
						+ ".");
			}

			myMOPSSolveIn.println("BOUNDS");
			for (int i = 1; i < rowVector.length; i++) {
				myMOPSSolveIn.println(" UP B1234     C" + i + "        1.");
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
				printWriter88.flush();
				printWriter88.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"xmops.pro konnte nicht geschrieben werden!",
						"Solver-Fehler", JOptionPane.WARNING_MESSAGE);
			}

			try {
				java.io.FileWriter fileWritermopsbatch = new java.io.FileWriter(
						solverDirectory + "\\mopsbatch.cmd");
				java.io.PrintWriter batchWriter55 = new java.io.PrintWriter(
						fileWritermopsbatch);
				batchWriter55.println("@echo off");
				// batchWriter55.println("copy  \"L:\\BESF\\Solver\\MOPS 7.06\\Exec\\mops.exe\" C:\\temp\\");
				batchWriter55.println("copy  \"" + IniLaden.getMOPS() + "\" "
						+ solverDirectory + "\\");
				batchWriter55.println("C:");
				batchWriter55.println("chdir " + solverDirectory);
				batchWriter55.println("start /wait " + solverDirectory
						+ "\\mops.EXE > " + solverDirectory + "\\MOPS.LOG");
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
						"Solver-Fehler", JOptionPane.WARNING_MESSAGE);
			}

			/**************** Auslesen aus dem Solver **************/

			File MOPS_SolveOut = new File(solverDirectory + "\\Mops.lps");
			// System.out.println("Komm ich hier rein: " + solverDirectory);
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
			int CountLines = 1;
			ergebnis = new String[rowVector.length - 1];
			while (CountLines < rowVector.length) {
				Ausgabe = outputDatei.readLine();
				SolutionRead = Ausgabe.substring(32, 33);
				if (SolutionRead.equals("1")) {
					this.ergebnis[CountLines - 1] = "Standort " + CountLines
							+ "";
				} else {
					this.ergebnis[CountLines - 1] = "";
				}
				CountLines++;
			}

			outputDatei.close();

			// Temp Verzeichnis leeren, Partnerprojekt
			Runtime rt = Runtime.getRuntime();
			Process pr = rt
					.exec("cmd /c start cmd.exe /C \"cd c:\\temp && del mops.ips && del mops.lps && del MOPS.log && del mops.sta && del mops.mps && del xmops.pro && del mopsbatch.cmd && del mops.exe \"");
			pr.waitFor();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"MOPS-Solver nicht richtig gestartet!", "Solver-Fehler",
					JOptionPane.WARNING_MESSAGE);
			return;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"MOPS-Solver nicht richtig gestartet!", "Solver-Fehler",
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
