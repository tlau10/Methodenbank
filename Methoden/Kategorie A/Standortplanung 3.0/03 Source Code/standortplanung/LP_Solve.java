package standortplanung;

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

public class LP_Solve extends Thread implements Solver {

	String[] Ergebnis;
	int Zielfunktionswert;
	private Process solver;
	private String tempZeile = "";
	private String laufwerksbuchstabe = "";
	long SolverTime;
	 //Temp-Verzeichnis laden
    String solverDirectory = IniLaden.getTemp();

	public LP_Solve() {
		Ergebnis = new String[0];
	}

	public void starteSolver(Daten matrix, int distanz) {
		Object columnVector[] = matrix.getColumnVector();
		Object rowVector[] = matrix.getRowVector();
		int entfernung = distanz;

		try {
			// BatchDatei und InputFile für Solver erstellen
		      PrintWriter myLpSolveBatch = new PrintWriter(new BufferedWriter(new FileWriter(solverDirectory +"/Solver.bat")));
		      PrintWriter myLPSolveIn = new PrintWriter(new BufferedWriter(new FileWriter(solverDirectory +"/lp_solve.in")));
		      PrintWriter myLPSolveCopyBatch = new PrintWriter(new BufferedWriter(new FileWriter(solverDirectory +"/solverCopy.bat")));
		      //Batchdateien schreiben
		      File SolverLP = new File ("\"" + IniLaden.getLpSolvePath() + "\"" +" "+solverDirectory);
		      myLPSolveCopyBatch.println(Variablen.copy + SolverLP);
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
		      myLpSolveBatch.close();
			/********************** Zielfunktion bilden *************/
		      String Zielfunktion = "min: ";

		      int i = 0;

		      for (i = 1; i < rowVector.length; i++){

		          if(i <= 9 )
		            Zielfunktion = Zielfunktion + "x0" + (i) + " + ";
		          else
		            Zielfunktion = Zielfunktion + "x" + (i) + " + ";

		      }
			
			// letztes ' + ' oder ' - ' muss gelöscht werden und ein
			// abschließendes Semikolon wird hinzugefügt
		      Zielfunktion = Zielfunktion.substring(0, Zielfunktion.length()-3) + ";";
		      myLPSolveIn.println(Zielfunktion);
		      myLPSolveIn.println();

			/************** Restriktionen bilden *******************/
		      int k = 0;
		      int l = 0;

		      String vergleich = " >=1;";
		      String Restriktion = "";

			// Wenn die Matrix nicht auf beiden Seiten gleich lang ist muss die
			// Restriktion geändert werden
//			String vergleich1 = " >= 1;";
//			String vergleich2 = " = 1;";
//			if (rowVector.length < columnVector.length)
//				vergleich2 = " <=1;";
//			else if (rowVector.length > columnVector.length)
//				vergleich1 = " <=1;";
//			String Restriktion = "";
//			
//			System.out.println(rowVector.length);
//			System.out.println(columnVector.length);
//			
			// Restriktionen für rowVector
		      for (k = 1; k < rowVector.length; k++){
		          Restriktion = "R" + (k) + ": ";
		          for (l = 1; l < columnVector.length; l++){
		            if((Integer.parseInt((String) matrix.getValueAt(l,k))) <= entfernung){
		              if(l<=9 && k<=9)
		                Restriktion += "x0" + (l) + " + ";
		              else if (l <=9)
		                Restriktion += "x0" + (l) + " + ";
		              else
		                Restriktion += "x" + (l) + " + ";
		            }
		          }
		          Restriktion =Restriktion.substring(0, Restriktion.length()-3) + vergleich;
		          myLPSolveIn.println(Restriktion);
		        }

			// Restriktionen für Standorte
//			for (m = 1; m < columnVector.length; m++) {
//				Restriktion = "R" + (k) + ": ";
//				k++;
//				for (n = 1; n < rowVector.length; n++) {
//					if (n <= 9 && m <= 9)
//						Restriktion += "x0" + (n) + "0" + (m) + " + ";
//					else if (n <= 9)
//						Restriktion += "x0" + (n) + (m) + " + ";
//					else if (m <= 9)
//						Restriktion += "x" + (n) + "0" + (m) + " + ";
//					else
//						Restriktion += "x" + (n) + (m) + " + ";
//				}
//				Restriktion = Restriktion
//						.substring(0, Restriktion.length() - 3) + vergleich2;
//				myLPSolveIn.println(Restriktion);
//			}

			// Bounds
		      for (i = 1; i < rowVector.length; i++){

		          if(i <= 9){
		            myLPSolveIn.println("x0" + (i) + "<=1;");
		          }
		          else{
		            myLPSolveIn.println("x" + (i) + "<=1;");
		          }

		      }
			
			// Ganzzahligkeit
		      myLPSolveIn.println("");
		      String Ganzzahl = " int ";
		      for (i = 1; i < rowVector.length; i++){

		          if(i <= 9 ){
		            Ganzzahl = Ganzzahl + "x0" + (i) + ",";
		          }
		          else{
		            Ganzzahl = Ganzzahl + "x" + (i) + ",";
		          }

		      }
		      myLPSolveIn.println(Ganzzahl.substring(0,Ganzzahl.length()-1) + ";");
		      myLPSolveIn.close();
			/****************** LP-Solver starten ******************/
			solver = Runtime.getRuntime().exec(solverDirectory + "\\solverCopy.bat");
			try {
				solver.waitFor();
			} catch (InterruptedException eth) {
				JOptionPane.showMessageDialog(null,
						"Fehler beim LP-Solve!",
						"Solver-Fehler", JOptionPane.WARNING_MESSAGE);
			}
			SolverTime = System.currentTimeMillis();
			
			// ---------------------------------------------------------------------------------
			// solver = Runtime.getRuntime().exec(solverdirectory +
			// "/Solver.pif");
			// ---------------------------------------------------------------------------------

			try {
				solver.waitFor();
			} catch (InterruptedException eth) {
				JOptionPane.showMessageDialog(null,
						"Fehler beim LP-Solve!",
						"Solver-Fehler", JOptionPane.WARNING_MESSAGE);
			}
			SolverTime = System.currentTimeMillis() - SolverTime;

			/**************** Auslesen aus dem Solver **************/
			// --------------------------------------------------------------------------------------------------------------------------------------------
			//Starten des Solvers über CMD-eingabe
			Runtime rt = Runtime.getRuntime();
			
//			FrameInfo laufwerksbuchstabe
			
			BufferedReader brLaufwerksbuchstabe = new BufferedReader(new FileReader("solverpath.txt"));
			for (int o = 0; o < 4; o++) {
				if (o==3) {
					tempZeile = brLaufwerksbuchstabe.readLine();
					continue;
				}
				brLaufwerksbuchstabe.readLine();
			}
			brLaufwerksbuchstabe.close();
			
			for (int o = 0; o < tempZeile.length(); o++) {
				if(o==5){
					laufwerksbuchstabe = String.valueOf(tempZeile.charAt(o));
				}
			}
			
			Process pr = rt.exec("cmd /C start cmd.exe /C \"" + laufwerksbuchstabe + ":" +" && cd "+ solverDirectory+" && solver.bat\"");
			pr.waitFor();
//			pr = rt.exec("cd "+ solverDirectory+" && solver.bat\"");
//			pr.waitFor();
			File file = new File(solverDirectory+"\\lp_solve.out");
			do {
				Thread.sleep(500);
			} while (!file.exists());
			// -------------------------------------------------------------------------------------------------------------------------------------------------
			File lp_SolveOut = new File(solverDirectory
					+ "/lp_solve.out");
			BufferedReader outputDatei = new BufferedReader(new FileReader(
					lp_SolveOut));
			
			//-------------------------------------------------------------------------------------------------------------
			do {
				Thread.sleep(500);
			} while (!file.exists());
			//--------------------------------------------------------------------------------------------------------------
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
			//Temp Verzeichnis leeren
//			 pr = rt.exec("cmd /c start cmd.exe /C \"cd c:\\temp && del lp_solve.out && del lp_solve.in && del Solver.bat && del solverCopy.bat && del lp_solve.exe \"");
//			 pr.waitFor();

		}

		catch (IOException e) {
//			ExceptionDialog.showExceptionDialog("Fehler beim Solver LP",
//					e.getMessage(), e);
		} catch (Exception ex) {
//			ExceptionDialog.showExceptionDialog("Fehler beim Solver LP",
//					ex.getMessage(), ex);
		}
		return;

	}

	public String[] getErgebnis() {
		return Ergebnis;
	}

	public String getZielfunktionswert() {
		return Zielfunktionswert + "";
	}

	public long getZeit() {
		return SolverTime;
	}
}