package com.htwg.powerlp.file;

import javax.swing.JInternalFrame;

import com.htwg.powerlp.controller.MatrixContentHandler;

/**
 * Hier werden die Daten in MPS-Format umgewandelt, damit die Solver die Daten
 * verarbeiten k�nnen
 * 
 * @author Teamprojekt WS 2015/2016 (Ducho, Keller, Lagun, Lu, Pllana)
 * @version 1.0
 */

public class MpsParser extends MatrixContentHandler {

	private String data;

	/**
	 * Methode um Daten in MPS-Format umzuwandeln
	 * 
	 * @param internalFrame
	 *            aktives Frame
	 * @return Daten in MPS-Format
	 */
	// ---------------------------------------------------------------------------------------
	// Hier wird MPS Format vorbereitet
	// ---------------------------------------------------------------------------------------
	public String prepareMPSFormat(JInternalFrame internalFrame) {
		// Daten einlesen aus dem gew�hlten Frame
		data = prepareInternalFrameContext(internalFrame);
		// Schreiben der Daten, zeilenweise in ein Feld
		String[] dataField = data.split("\r\n");
		// Schreiben der Standard Werte in die Variablen
		int matrixtable_start = 0;
		int matrixtable_end = dataField.length;
		int matrixCounter = 0;
		// Gr�sse der Matrixtabelle festlegen (Start bzw. Ende der Tabelle)
		for (int i = 0; i < matrixtable_end; i++) {
			if (dataField[i].trim().equals("Matrixtable_start")) {
				matrixtable_start = i + 1;
			}
			if (dataField[i].trim().equals("Matrixtable_end")) {
				matrixtable_end = i;
			}
		}
		// Schreiben der Matrixtabellenwerte in ein Feld
		String[] panelMatrixData = new String[matrixtable_end - matrixtable_start];
		for (int i = matrixtable_start; i < matrixtable_end; i++) {
			panelMatrixData[matrixCounter] = dataField[i];
			matrixCounter++;
		}

		// �berpr�fen der Gr�sse der Matrix und diese bestimmen
		String[][] matrixDataField = new String[panelMatrixData.length][Integer.parseInt(dataField[1].trim()) + 3];
		String[] rowData;
		for (int i = 0; i < panelMatrixData.length; i++) {
			rowData = panelMatrixData[i].split(";");
			for (int j = 0; j < (rowData.length); j++) {
				// Tabellenwerte mit null durch "0" ersetzen (f�r eine
				// einheitliche Ausgabe)
				if (rowData[j].equals("null")) {
					matrixDataField[i][j] = "0";
				} else {
					// Zellen mit einzelnen Werten belegen
					matrixDataField[i][j] = rowData[j];
				}
			}
		}
		// Initialiseren der Variablen mit neuen Werten
		int borderstable_start = 0;
		int borderstable_end = dataField.length;
		int bordersCounter = 0;
		// Start und Ende der Tabelle bestimmen
		for (int i = 0; i < borderstable_end; i++) {
			if (dataField[i].trim().equals("Borderstable_start")) {
				borderstable_start = i + 1;
			}
			if (dataField[i].trim().equals("Borderstable_end")) {
				borderstable_end = i;
			}
		}
		// Tabellenwerte der Border/Integer in einem Feld speichern
		String[] panelBordersData = new String[borderstable_end - borderstable_start];
		for (int i = borderstable_start; i < borderstable_end; i++) {
			panelBordersData[bordersCounter] = dataField[i];
			bordersCounter++;
		}
		// Belegen des Feldes mit der L�nge und Breite
		String[][] bordersDataField = new String[panelBordersData.length][Integer.parseInt(dataField[1].trim()) + 3];
		String[] bordersRowData;
		// Einlesen der Tabellenwerte in das Feld
		for (int i = 0; i < panelBordersData.length; i++) {
			bordersRowData = panelBordersData[i].split(";");
			for (int j = 0; j < (bordersRowData.length); j++) {
				bordersDataField[i][j] = bordersRowData[j];

			}
		}
		// Speichern der Daten und ihrer Abst�nde (MPS Datei-Vorlage)
		String mpsData = "NAME         ";
		// Checken ob minimiert oder maximiert wird

		if (dataField[3].equals("min !")) {
			mpsData = mpsData + " " + "Power-LP MINIMIZE" + "\r\n";
		}
		if (dataField[3].equals("max !")) {
			mpsData = mpsData + " " + "Power-LP MAXIMIZE" + "\r\n";
		}

		// Holen der richtigen Anfangsbuchstaben je nach Zeichen aus der Tabelle
		// (<=,>=, etc.)
		mpsData = mpsData + "ROWS" + "\r\n";
		mpsData = mpsData + " N  " + matrixDataField[0][0] + "\r\n";
		// In der gleichen Zeile, mit bestimmtem Abstand, werden die Werte zu
		// den Buchstaben ge-
		// schrieben. Alle ausser "-->" (Standardgem�ss in der Zielfunktion)
		for (int i = 0; i < panelMatrixData.length; i++) {
			if ((matrixDataField[i][matrixDataField[i].length - 2]).equals("<=")) {
				mpsData = mpsData + " L  " + matrixDataField[i][0] + "\r\n";
			}
			if ((matrixDataField[i][matrixDataField[i].length - 2]).equals(">=")) {
				mpsData = mpsData + " G  " + matrixDataField[i][0] + "\r\n";
			}
			if ((matrixDataField[i][matrixDataField[i].length - 2]).equals("=")) {
				mpsData = mpsData + " E  " + matrixDataField[i][0] + "\r\n";
			}
		}
		mpsData = mpsData + "COLUMNS" + "\r\n";
		// Elemente f�r die weitere Bearbeitung der Tabellenwerte setzen
		int x = 0;
		int m = 0;
		String elementX = "";
		String[] elementXField = new String[bordersDataField.length];
		// Einlesen der Raw-Daten (Ohne Marker)
		// Nachher werden diese Daten, je nach dem Marker(Integer:Ja/Nein),
		// eingesetzt
		for (int h = 0; h < bordersDataField.length; h++) {
			for (int i = 0; i < panelMatrixData.length; i++) {
				elementX = elementX + "    " + bordersDataField[x][0] + emptySpaceCounter(bordersDataField[x][0]);
				if (m + 1 < panelMatrixData.length) {
					m++;
				}
				elementX = elementX + matrixDataField[i][0] + emptySpaceCounter(matrixDataField[i][0])
						+ doubleValuesConverter(matrixDataField[i][h + 1].toString());
				;
				elementX = elementX + "\r\n";
			}
			elementXField[h] = elementX;
			elementX = "";
			x++;
		}
		// Daten der Matrixtabelle mit den Integer:Ja/Nein verbinden und
		// Aufbauen
		int markCounter = 0;
		for (int y = 0; y < elementXField.length; y++) {
			if (dataField[4].equals("true")) {
				if (y != 0) {
					if (bordersDataField[y - 1][3].equals("Ja") && bordersDataField[y][3].equals("Ja")) {
						mpsData = mpsData + elementXField[y];
					}

					if (bordersDataField[y - 1][3].equals("Ja") && bordersDataField[y][3].equals("Nein")) {
						// Mit dem ValuefOf(.... werden die nullen vor den
						// Markernamen bestimmt z.B. 0001)
						mpsData = mpsData + "    " + "MARK" + String.valueOf(markCounter + 10000).substring(1)
								+ "  'MARKER'                 'INTEND'" + "\r\n";
						markCounter++;
						mpsData = mpsData + elementXField[y];
					}
					if (bordersDataField[y - 1][3].equals("Nein") && bordersDataField[y][3].equals("Ja")) {
						mpsData = mpsData + "    " + "MARK" + String.valueOf(markCounter + 10000).substring(1)
								+ "  'MARKER'                 'INTORG'" + "\r\n";
						markCounter++;
						mpsData = mpsData + elementXField[y];
					}
					if (bordersDataField[y - 1][3].equals("Nein") && bordersDataField[y][3].equals("Nein")) {
						mpsData = mpsData + elementXField[y];
					}
				} else {
					if (bordersDataField[y][3].equals("Ja")) {
						mpsData = mpsData + "    " + "MARK" + String.valueOf(markCounter + 10000).substring(1)
								+ "  'MARKER'                 'INTORG'" + "\r\n";
						markCounter++;
						mpsData = mpsData + elementXField[y];
					}
					if (bordersDataField[y][3].equals("Nein")) {
						mpsData = mpsData + elementXField[y];
					}
				}
				if (y == elementXField.length - 1) {

					if (bordersDataField[y][3].equals("Ja")) {
						mpsData = mpsData + "    " + "MARK" + String.valueOf(markCounter + 10000).substring(1)
								+ "  'MARKER'                 'INTEND'" + "\r\n";
						markCounter++;
					}
					if (bordersDataField[y][3].equals("Nein")) {
					}
				}
				if (bordersDataField[y][3].equals("0")) {
					mpsData = mpsData + elementXField[y];
				}

			} else {
				mpsData = mpsData + elementXField[y];
			}
		}
		// Weitere Tabellendaten aus der Matrix lesen (im MPS Format, Section:
		// RHS)
		mpsData = mpsData + "RHS" + "\r\n";
		for (int i = 0; i < panelMatrixData.length; i++) {
		}
		// Daten mit den Abst�nden vorbereiten und zu dem forlaufenden String
		// hinzuf�gen
		for (int h = 0; h < 1; h++) {
			m = 1;
			for (int i = 0; i < panelMatrixData.length - 1; i++) {

				mpsData = mpsData + "    " + "MYRHS";
				mpsData = mpsData + "     " + matrixDataField[m][0] + emptySpaceCounter(matrixDataField[m][0])
						+ doubleValuesConverter(matrixDataField[m][matrixDataField[1].length - 1].toString());
				if (m < panelMatrixData.length - 1) {
					m++;
				}
				mpsData = mpsData + "\r\n";
				x++;
			}

		}
		// Weitere Tabellendaten aus der Matrix lesen (im MPS Format, Section:
		// BOUNDS)
		if (dataField[4].equals("true")) {
			mpsData = mpsData + "BOUNDS" + "\r\n";
			// Daten mit den Abst�nden vorbereiten und zu dem forlaufenden
			// String hinzuf�gen
			for (int i = 0; i < bordersDataField.length; i++) {
				//Ensure that default value is zero to plus infinity 
				//Otherwise some solvers will interprete binary variables
				if (bordersDataField[i][1].equals("") && bordersDataField[i][2].equals("")) {
					mpsData = mpsData + " PL " + "MYBOUND   " + bordersDataField[i][0]
							+ emptySpaceCounter(bordersDataField[i][0])
							+ doubleValuesConverter(bordersDataField[i][1]).toString() + "\r\n";
					continue;
				}
				
				if (!bordersDataField[i][1].isEmpty()
						&& !bordersDataField[i][1].equals("null")) {
					if (bordersDataField[i][1].equals("")) {
						bordersDataField[i][1] = "0";
					}
					mpsData = mpsData + " LO " + "MYBOUND   " + bordersDataField[i][0]
							+ emptySpaceCounter(bordersDataField[i][0])
							+ doubleValuesConverter(bordersDataField[i][1]).toString() + "\r\n";		
				}
				if (!bordersDataField[i][2].isEmpty()
						&& !bordersDataField[i][2].equals("null")) {
					if(bordersDataField[i][2].equals("")){
						
					}
					mpsData = mpsData + " UP " + "MYBOUND   " + bordersDataField[i][0]
							+ emptySpaceCounter(bordersDataField[i][0])
							+ doubleValuesConverter(bordersDataField[i][2]).toString() + "\r\n";
				
				}
				
			}
		}
		// Ende des Datensatzen festlegen
		mpsData = mpsData + "ENDATA\r\n";
		return mpsData;
	}

	/**
	 * Zur Fehlerminimierung bei der Eingabe werden Komma-Dezimaltrennzeichen in
	 * Punkt-Dezimaltrennzeichen umgewandelt (Benutzereingabe mit "." und ","
	 * m�glich)c
	 * 
	 * @param value
	 *            Wert aus der Tabelle
	 * @return Wert mit Punkt-Dezimaltrennzeichen
	 */
	public String doubleValuesConverter(String value) {
		if (value.contains(",")) {
			value = value.replace(",", ".");

		}
		return value;
	}

	/**
	 * Ermitteln der ben�tigten Leerzeichen in dem MPS-Format (zur
	 * Fehlervermeidung w�hrend des programmierens und zur besseren
	 * Nachvollziehbarkeit des Codes f�r Dritte)
	 * 
	 * @param var
	 *            String um festzustellen wie viele leere Zeichen geschrieben
	 *            werden
	 * @return String mit bestimmter Anzahl der Leerzeichen
	 */
	// ---------------------------------------------------------------------------------------
	// Anzahl der Leerzeichen bestimmen
	// ---------------------------------------------------------------------------------------
	public String emptySpaceCounter(String var) {
		String space = "";
		for (int i = 0; i < 10 - var.length(); i++) {
			space = space + " ";
		}
		return space;
	}
}
