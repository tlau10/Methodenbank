package zuordnungsplanung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;


/**
 * <p>
 * Title: Zuordnungsplanung V 1.0
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * <p>Geändert im Jahr 2013 durch Benedikt Wölfle um Solver.pif nicht mehr zu benötigen</p>
 * @author Bettina Pfeiffer, Benedikt Woelfle
 * @version 1.0
 */

public class SolveLP extends Thread implements Solver {

	String[] Ergebnis;
	int Zielfunktionswert;
	private Process solver;
	private Process berechnung;
	
	 //Temp-Verzeichnis laden
    String solverDirectory = IniLaden.getTemp();

	public SolveLP() {
		Ergebnis = new String[0];
	}

	public void starteSolver(DataModel dataModel) {
		Object columnVector[] = dataModel.getColumnVector();
		Object rowVector[] = dataModel.getRowVector();
		boolean maximierung = dataModel.getMaximize();

		try {
			// BatchDatei und InputFile für Solver erstellen
		      PrintWriter myLpSolveBatch = new PrintWriter(new BufferedWriter(new FileWriter(solverDirectory +"/Solver.bat")));
		      PrintWriter myLPSolveIn = new PrintWriter(new BufferedWriter(new FileWriter(solverDirectory +"/lp_solve.in")));
		      PrintWriter myLPSolveCopyBatch = new PrintWriter(new BufferedWriter(new FileWriter(solverDirectory +"/solverCopy.bat")));
		      //Batchdateien schreiben
		      File SolverLP = new File (IniLaden.getLpSolvePath()+" "+solverDirectory);
		      myLPSolveCopyBatch.println(Variables.copy + SolverLP);
//		      File SolverPif = new File (IniLaden.getSolverPIF()+" " + solverDirectory);
//		      myLPSolveCopyBatch.println(Variables.copy + SolverPif);
		      myLPSolveCopyBatch.close();
		      
		      //Batch-Datei für Solver-Start
		      
		      myLpSolveBatch.println("@echo off");
		      myLpSolveBatch.println("REM Solver: LP-Solver");
		      myLpSolveBatch.println("set Oldpath=%path%");
		      File Solver = new File(solverDirectory + "/lp_solve");
		      myLpSolveBatch.println("path " + Solver + ";%path%");
		      myLpSolveBatch.println("lp_solve -p < lp_solve.in > lp_solve.out");
		      myLpSolveBatch.println("path %Oldpath%");
		      myLpSolveBatch.println("set Oldpath=");
		      myLpSolveBatch.println("exit");
		      myLpSolveBatch.close();
			/********************** Zielfunktion bilden *************/
			String Zielfunktion = "max: ";
			int i = 0;
			int j = 0;

			// bei Minimumproblem muss die Zielfunktion mal -1 genommen werden
			if (maximierung == false) {
				Zielfunktion = "min: ";
			}

			for (i = 1; i < rowVector.length; i++) {
				for (j = 1; j < columnVector.length; j++) {
					if (i <= 9 && j <= 9)
						Zielfunktion = Zielfunktion
								+ Integer.parseInt((String) dataModel
										.getValueAt(i, j)) + "x0" + (i) + "0"
								+ (j) + " + ";
					else if (i <= 9)
						Zielfunktion = Zielfunktion
								+ Integer.parseInt((String) dataModel
										.getValueAt(i, j)) + "x0" + (i) + (j)
								+ " + ";
					else if (j <= 9)
						Zielfunktion = Zielfunktion
								+ Integer.parseInt((String) dataModel
										.getValueAt(i, j)) + "x" + (i) + "0"
								+ (j) + " + ";
					else
						Zielfunktion = Zielfunktion
								+ Integer.parseInt((String) dataModel
										.getValueAt(i, j)) + "x" + (i) + (j)
								+ " + ";
				}
			}
			// letztes ' + ' oder ' - ' muss gelöscht werden und ein
			// abschließendes Semikolon wird hinzugefügt
			Zielfunktion = Zielfunktion.substring(0, Zielfunktion.length() - 3)
					+ ";";
			myLPSolveIn.println(Zielfunktion);
			myLPSolveIn.println();

			/************** Restriktionen bilden *******************/
			int k = 0;
			int l = 0;

			// Wenn die Matrix nicht auf beiden Seiten gleich lang ist muss die
			// Restriktion geändert werden
			String vergleich1 = " = 1;";
			String vergleich2 = " = 1;";
			if (rowVector.length < columnVector.length)
				vergleich2 = " <=1;";
			else if (rowVector.length > columnVector.length)
				vergleich1 = " <=1;";
			String Restriktion = "";

			// Restriktionen für rowVector
			for (k = 1; k < rowVector.length; k++) {
				Restriktion = "R" + (k) + ": ";
				for (l = 1; l < columnVector.length; l++) {
					if (k <= 9 && l <= 9)
						Restriktion += "x0" + (k) + "0" + (l) + " + ";
					else if (k <= 9)
						Restriktion += "x0" + (k) + (l) + " + ";
					else if (l <= 9)
						Restriktion += "x" + (k) + "0" + (l) + " + ";
					else
						Restriktion += "x" + (k) + (l) + " + ";
				}
				Restriktion = Restriktion
						.substring(0, Restriktion.length() - 3) + vergleich1;
				myLPSolveIn.println(Restriktion);
			}

			int m = 0;
			int n = 0;

			// Restriktionen für Stellen
			for (m = 1; m < columnVector.length; m++) {
				Restriktion = "R" + (k) + ": ";
				k++;
				for (n = 1; n < rowVector.length; n++) {
					if (n <= 9 && m <= 9)
						Restriktion += "x0" + (n) + "0" + (m) + " + ";
					else if (n <= 9)
						Restriktion += "x0" + (n) + (m) + " + ";
					else if (m <= 9)
						Restriktion += "x" + (n) + "0" + (m) + " + ";
					else
						Restriktion += "x" + (n) + (m) + " + ";
				}
				Restriktion = Restriktion
						.substring(0, Restriktion.length() - 3) + vergleich2;
				myLPSolveIn.println(Restriktion);
			}

			// Bounds

			for (i = 1; i < rowVector.length; i++) {
				for (j = 1; j < columnVector.length; j++) {
					if (i <= 9 && j <= 9) {
						myLPSolveIn.println("x0" + (i) + "0" + (j) + "<=1;");
					} else if (i <= 9) {
						myLPSolveIn.println("x0" + (i) + (j) + "<=1;");
					} else if (j <= 9) {
						myLPSolveIn.println("x" + (i) + "0" + (j) + "<=1;");
					} else {
						myLPSolveIn.println("x" + (i) + (j) + "<=1;");
					}
				}
			}

			// Ganzzahligkeit
			myLPSolveIn.println("");
			String Ganzzahl = " int ";
			for (i = 1; i < rowVector.length; i++) {
				for (j = 1; j < columnVector.length; j++) {
					if (i <= 9 && j <= 9) {
						Ganzzahl = Ganzzahl + "x0" + (i) + "0" + (j) + ",";
					} else if (i <= 9) {
						Ganzzahl = Ganzzahl + "x0" + (i) + (j) + ",";
					} else if (j <= 9) {
						Ganzzahl = Ganzzahl + "x" + (i) + "0" + (j) + ",";
					} else {
						Ganzzahl = Ganzzahl + "x" + (i) + (j) + ",";
					}
				}
			}
			myLPSolveIn.println(Ganzzahl.substring(0, Ganzzahl.length() - 1)
					+ ";");
			myLPSolveIn.close();
			/****************** LP-Solver starten ******************/
			solver = Runtime.getRuntime().exec(solverDirectory + "\\solverCopy.bat");
			try {
				solver.waitFor();
			} catch (InterruptedException eth) {
				ExceptionDialog.showExceptionDialog("Fehler",
						"Fehler beim Solver LP.", eth);
			}
			// ---------------------------------------------------------------------------------
			// solver = Runtime.getRuntime().exec(solverdirectory +
			// "/Solver.pif");
			// ---------------------------------------------------------------------------------


			/**************** Auslesen aus dem Solver **************/
			// --------------------------------------------------------------------------------------------------------------------------------------------
			//Starten des Solvers über CMD-eingabe
			try {

				java.io.FileWriter fileWriterlpbatch = new java.io.FileWriter(
						solverDirectory + "\\lpbatch.bat");
				java.io.PrintWriter batchWriter55 = new java.io.PrintWriter(
						fileWriterlpbatch);
				batchWriter55.println("@echo off");
				batchWriter55.println("C:");
				batchWriter55.println("chdir " + solverDirectory);
				batchWriter55.println("start /wait Solver.bat");
				batchWriter55.flush();
				batchWriter55.close();
				try {
					Thread.sleep(5 * 100);
				} catch (InterruptedException e) {
				}
				try {
					Process lpproc = Runtime.getRuntime().exec(
							solverDirectory + "\\lpbatch.bat");
					lpproc.waitFor();
					lpproc.destroy();
				} catch (Exception e) {
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"LP_SOLVE.exe konnte nicht kopiert werden",
						"Solver fehler", JOptionPane.WARNING_MESSAGE);
			}

			File file = new File(solverDirectory+"\\lp_solve.out");
			do {
				Thread.sleep(500);
			} while (!file.exists());
			// -------------------------------------------------------------------------------------------------------------------------------------------------
			File lp_SolveOut = new File(solverDirectory	+ "/lp_solve.out");
			BufferedReader outputDatei = new BufferedReader(new FileReader(
					lp_SolveOut));
			
			//-------------------------------------------------------------------------------------------------------------

			// Zielfunktionswert
			Zielfunktionswert = Integer.parseInt(outputDatei.readLine().substring(30).trim());
			// falls Zielfunktionswert negativ sein sollte
			if (Zielfunktionswert < 0)
				Zielfunktionswert = Zielfunktionswert * (-1);
			// Ergebnis
			String Ergebniswert = outputDatei.readLine();
			String wert;
			if (rowVector.length < columnVector.length) {
				Ergebnis = new String[rowVector.length - 1];
			} else {
				Ergebnis = new String[columnVector.length - 1];
			}
			int index = 0;
			while (Ergebniswert.length() > 0) {
				wert = Ergebniswert.substring(Ergebniswert.length() - 1);
				if (wert.compareTo("1") == 0) {
					Ergebnis[index] = Ergebniswert;
					index++;
				}
				Ergebniswert = outputDatei.readLine();
			}
			outputDatei.close();
			// Ergebnisfeld zusammensetzen
			for (int p = 0; p < Ergebnis.length; p++) {
				Ergebnis[p] = rowVector[Integer.parseInt(Ergebnis[p].substring(
						1, 3))]
						+ " -> "
						+ columnVector[Integer.parseInt(Ergebnis[p].substring(
								3, 5))];
			}
			//TEMP-Verzeichnis leeren
			try {

				java.io.FileWriter fileWriterlpbatch = new java.io.FileWriter(
						solverDirectory + "\\lpclear.bat");
				java.io.PrintWriter batchWriter55 = new java.io.PrintWriter(
						fileWriterlpbatch);
				batchWriter55.println("@echo off");
				batchWriter55.println("C:");
				batchWriter55.println("chdir " + solverDirectory);
				batchWriter55.println("del lp_solve.out");
				batchWriter55.println("del lp_solve.in");
				batchWriter55.println("del lpbatch.bat ");
				batchWriter55.println("del Solver.bat");
				batchWriter55.println("del solverCopy.bat");
				batchWriter55.flush();
				batchWriter55.close();
				try {
					Thread.sleep(5 * 100);
				} catch (InterruptedException e) {
				}
				try {
					Process lpproc = Runtime.getRuntime().exec(
							solverDirectory + "\\lpclear.bat");
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

		catch (IOException e) {
			ExceptionDialog.showExceptionDialog("Fehler beim Solver LP",
					e.getMessage(), e);
		} catch (Exception ex) {
			ExceptionDialog.showExceptionDialog("Fehler beim Solver LP",
					ex.getMessage(), ex);
		}
		return;

	}

	public String[] getErgebnis() {
		return Ergebnis;
	}

	public String getZielfunktionswert() {
		return Zielfunktionswert + "";
	}

	@Override
	public long getZeit() {
		return 0;
	}
}