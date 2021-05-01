package standortplanung;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.lang.String;

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

public class FileHandler {

	public FileHandler() {

	}

	public void writeData(Daten matrix, String filename, int distanz) throws Exception {
		Object columnVector[] = matrix.getColumnVector();
		Object rowVector[] = matrix.getRowVector();
		int entfernung = distanz;

		try {
			// Datei mit StandortDaten erstellen
			PrintWriter myFile = new PrintWriter(new BufferedWriter(
					new FileWriter(filename)));
			/********************** Datenstrom bilden *************/
			String SerialTab = "";
			int i = 0;
			int j = 0;

			myFile.println(Variablen.toolName + ";" + Variablen.toolVersion
					+ ";");
			myFile.println("columns=" + ";" + columnVector.length + ";");
			myFile.println("rows=" + ";" + rowVector.length + ";");
			myFile.println("Entfernung=" + ";" + entfernung + ";");

			for (i = 0; i < rowVector.length; i++) {
				for (j = 0; j < columnVector.length; j++) {
					SerialTab = SerialTab + (String) matrix.getValueAt(i, j)
							+ ";";
				}
				myFile.println(SerialTab);
				SerialTab = "";
			}
			myFile.close();
		}

		catch (Exception e) {
			throw e;
		}
		return;
	}

	public Daten readData(String filename) throws Exception {

		Daten matrix = new Daten(0, 0, 0);
		;
		try {
			/**************** Auslesen aus dem Solver **************/

			File myFileName = new File(filename);
			BufferedReader outputDatei = new BufferedReader(new FileReader(
					myFileName));

			// init Bereich *****************************************

			outputDatei.readLine();

			int rowSize = 0;
			int columnSize = 0;
			int maxi = 0;

			String columnsline = outputDatei.readLine();
			columnSize = convertStringToInt(
					columnsline.substring(9, columnsline.indexOf(";", 9)),
					false);

			String rowline = outputDatei.readLine();
			rowSize = convertStringToInt(
					rowline.substring(6, rowline.indexOf(";", 6)), false);

			String maxline = outputDatei.readLine();
			maxi = convertStringToInt(
					maxline.substring(12, maxline.indexOf(";", 12)), false);
			matrix.setEntfernung(maxi);

			matrix = new Daten(rowSize, columnSize, maxi);

			for (int i = 0; i < rowSize; i++) {
				String Ergebniswert = outputDatei.readLine();
				char lastChar = Ergebniswert.charAt(Ergebniswert.length() - 1);
				if (lastChar != ';')
					Ergebniswert = Ergebniswert + ";";

				int posY1 = 0;
				int posY2 = 0;
				for (int j = 0; j < columnSize; j++) {
					if ((i == 0) && (j == 0)) {
					}
					posY2 = Ergebniswert.indexOf(";", posY1);
					if (i != 0 && j != 0)
						convertStringToInt(
								Ergebniswert.substring(posY1, posY2), true);
					matrix.setValueAt(Ergebniswert.substring(posY1, posY2), i,
							j);
					posY1 = posY2 + 1;
				}
			}
			outputDatei.close();

		} catch (Exception e) {
			throw e;
		}
		return matrix;
	}

	/**
	 * readFile opens and reads myFilename and returns the content as String
	 * 
	 * @param s
	 *            : the name of the String
	 * @param nullValueValid
	 *            : true, if an nullValue is an valid Integer; false, if an
	 *            nullValue is not an Integer
	 * @return the integer Value of String s
	 * @throws Exception
	 *             when the String value can not be converted to Integer
	 */
	private int convertStringToInt(String s, boolean nullValueValid)
			throws Exception {
		if (nullValueValid && s.equals(""))
			return 0;
		try {
			int i = Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			throw new Exception();
		}
	}
}