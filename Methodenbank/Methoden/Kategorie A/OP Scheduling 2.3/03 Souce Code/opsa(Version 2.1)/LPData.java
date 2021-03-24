package opsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

/**
 * Title: Operationssleplanung Description: Copyright: Copyright (c) 2002
 * Company: FHKN
 *
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 *
 * erweitert Juni 2003
 * @author Nina Bruch, Katharina Dammeier
 * @version 2.0
 */

public class LPData {
	int anzOp = 0;
	int anzPer = 0;
	int anzRestrictionNK;
	// NK default Matrizen
	int anzSaal;
	String colon = ";\n ";
	String dataNK = "";
	int defaultperiod = 60;
	String ergebnisStringNK = "";
	String ganzZahl = "int ";
	String lpsolvePath = "";

	String[] restrictionNK = new String[100];
	LPsolve theLPsolve;
	Vector<Object> vectorErgebnisse = new Vector<Object>();
	Vector<Object> vectorOperation;
	Vector<Object> vectorPeriodeSaal;
	String[][] xMatrix1;
	String[][] xMatrix2;
	int xNrNK;
	String zielNK = "min: ";

	public LPData(int defaultperiod, int saalAnzahl) {
		// ini Restriction
		this.defaultperiod = defaultperiod;
		this.anzSaal = saalAnzahl;
	}// ende Konstruktor

	public void setLPSolvePath(String lpPath){
		this.lpsolvePath = lpPath;
	}

	public void finalizeRestrictionString() {
		// Restriktionen NK
		try {
			// Zielfunktion:
			zielNK += "+0.001x" + xNrNK;
			dataNK = zielNK + colon;
			// dataNK
			for (int i = 1; i < anzRestrictionNK; i++)
				dataNK += restrictionNK[i] + colon;
			// Ganzzahligkeit
			dataNK += ganzZahl + colon;
			// Ausgabe dataNK
			// System.out.println(dataNK);
		} catch (Exception e2) {
			System.out.println(e2.toString() + "!!!!!!!!!!!!!");
		}
	}

	public String getErgebnisse() {
		return ergebnisStringNK;
	}

	public boolean initialisierung(Vector<Object> in_vectorOperation,
			Vector<Object> in_vectorPeriodeSaal) {
		xNrNK = 1;
		anzRestrictionNK = 1;
		dataNK = "";
		ergebnisStringNK = "";
		for (int i = 0; i < 100; i++) {
			restrictionNK[i] = new String();
		}

		vectorOperation = in_vectorOperation;
		vectorPeriodeSaal = in_vectorPeriodeSaal;

		int maxMatrix = 0;
		if (vectorPeriodeSaal.size() > vectorOperation.size())
			maxMatrix = vectorPeriodeSaal.size() + 1;
		else
			maxMatrix = vectorOperation.size() + 1;

		// NK Matrizen je OP-Saal (2 Sle) fllen
		xMatrix1 = new String[maxMatrix][maxMatrix];
		for (int r = 0; r < maxMatrix; r++) {
			for (int c = 0; c < maxMatrix; c++) {
				xMatrix1[r][c] = new String("x");
			}
		}

		xMatrix2 = new String[maxMatrix][maxMatrix];
		for (int r = 0; r < maxMatrix; r++) {
			for (int c = 0; c < maxMatrix; c++) {
				xMatrix2[r][c] = new String("x");
			}
		}
		return true;
	}

	public void parseData() {
		try {
//			boolean IsRestriction = false;
//			boolean moreX = false;

			// NK set
			// xMatrix1----------------------------------------------------
			for (int i = 0; i < vectorOperation.size(); i++) {
				for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
					if (((Operation) vectorOperation.elementAt(i) != null)
							&& (j >= ((Operation) vectorOperation.elementAt(i))
									.getAnfang())
							&& (j <= ((Operation) vectorOperation.elementAt(i))
									.getEnde())) {
						xMatrix1[i + 1][j] += xNrNK++;
					}
				}
			}

//			System.out.println("xMatrix1"); for(int i=0;i<xMatrix1.length;i++) {
//			for(int j=0;j<xMatrix1[i].length;j++) { System.out.println(i+
//			+j+" :"+xMatrix1[i][j]); } }


			// NK set
			// xMatrix2----------------------------------------------------
			for (int i = 0; i < vectorOperation.size(); i++) {
				for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
					if (((Operation) vectorOperation.elementAt(i) != null)
							&& (j >= ((Operation) vectorOperation.elementAt(i))
									.getAnfang())
							&& (j <= ((Operation) vectorOperation.elementAt(i))
									.getEnde())) {
						xMatrix2[i + 1][j] += xNrNK++;
					}
				}
			}


//			System.out.println("xMatrix2"); for(int i=0;i<xMatrix2.length;i++) {
//			for(int j=0;j<xMatrix2[i].length;j++) { System.out.println(i+
//			+j+" :"+xMatrix2[i][j]); }}

			// Aufrufen der internen Methoden zum Setzen der Restriktionen
			this.restricionOP();
			this.restrictionPeriode();
			this.restrictionAuslastung();
			this.restrictionDefaultPeriod();
			this.finalizeRestrictionString();
			this.setData();
		} catch (Exception e2) {
			System.out.println(e2.toString() + "!!!!!!!!!!!!!");
		}
		// Ende NK
	}

	public void readText() {
		try {
			String theLine = null;
			File inFile = new File("lpsolve.out");
			FileReader theReader = new FileReader(inFile);
			BufferedReader in = new BufferedReader(theReader);
//			System.out.println("teste, eine Datei einzulesen.................");
			while (in.ready()) {
				theLine = in.readLine();
				System.out.println(theLine);
			}
			in.close();
		} catch (Exception e3) {
			System.out.println(e3.toString());
		}
	}

	public void restricionOP() {
		boolean IsRestriction = false;
		boolean moreX = false;
		boolean erstesMal = true;
		// Ganzzahligkeit
		boolean ersteGanzzahl = true;
		// zhler fr die Zielfunktion
		double zielf = 1;

		// NK erstelle Restriktionen: Operationen
		// Saal 1 (Matrix 1)---------------------
		// zeile
		try {
			for (int i = 0; i < xMatrix1.length; i++) {
				// spalte
				for (int j = 0; j < xMatrix1[i].length; j++) {
					if ((xMatrix1[i][j].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix1[i][j];
							if (erstesMal == true) // ohne Pluszeichen
							{
								zielNK += zielf + xMatrix1[i][j];
								erstesMal = false;
							} else
								zielNK += "+" + zielf + xMatrix1[i][j];

							moreX = true;
						} else {
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix1[i][j];
							zielf += 0.1;
							zielNK += "+" + zielf + xMatrix1[i][j];
						}
						IsRestriction = true;
					}
				}// end for 1
				// zielf wieder auf 1 setzen
				zielf = 1;

				moreX = false;
				if (IsRestriction == true) {
					restrictionNK[anzRestrictionNK] += "+1000x" + xNrNK;
					restrictionNK[anzRestrictionNK] += ">="
							+ ((Operation) vectorOperation.elementAt(i - 1))
									.getDauer();

					// Restriktion Schaltervariable <=1
					anzRestrictionNK++;
					restrictionNK[anzRestrictionNK] = "x" + xNrNK + "<=1";
					// Ganzzahligkeit der Schaltervariablen
					if (ersteGanzzahl == true) // zum ersten Mal aufgerufen
					{
						ganzZahl += "x" + xNrNK++;
						ersteGanzzahl = false;
					} else // schonmal aufgerufen, xNrNK muss trotzdem
					// hochgezhlt werden
					{
						ganzZahl += ", x" + xNrNK++;
					}

					IsRestriction = false;
					anzRestrictionNK++;
				}
			}

			// xNrNK um die Anzahl der Operationen runterzhlen, damit
			// Schaltervariablen
			// richtig gesetzt werden knnen

			xNrNK = xNrNK - vectorOperation.size();

			// Saal 2 (Matrix 2)---------------------
			// zeile
			for (int i = 0; i < xMatrix2.length; i++) {
				// spalte
				for (int j = 0; j < xMatrix2[i].length; j++) {
					if ((xMatrix2[i][j].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix2[i][j];
							// immer mit Pluszeichen
							zielNK += "+" + zielf + xMatrix2[i][j];
							moreX = true;
						} else {
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix2[i][j];
							zielf += 0.1;
							zielNK += "+" + zielf + xMatrix2[i][j];
						}
						IsRestriction = true;
					}
				}
				// zielf wieder auf 1 setzen
				zielf = 1;

				moreX = false;
				if (IsRestriction == true) {
					restrictionNK[anzRestrictionNK] += "-1000x" + xNrNK++;
					int subtraktion = ((Operation) vectorOperation
							.elementAt(i - 1)).getDauer() - 1000;
					restrictionNK[anzRestrictionNK] += ">=" + subtraktion;
					IsRestriction = false;
					anzRestrictionNK++;
				}
			}
		}// ende try
		catch (Exception e2) {
			System.out.println(e2.toString() + "!!!!!!!!!!!!!");
		}
	}

	public void restrictionAuslastung() {
		// erstelle Restriktionen: <=Periode NK (<=120)
		boolean IsRestriction = false;
		boolean moreX = false;

		try {
			// Matrix 1

			for (int i = 0; i < vectorPeriodeSaal.size(); i++) {
				// Matrix 1
				for (int j = 1; j < xMatrix1.length; j++) {
					if ((xMatrix1[j][i + 1].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix1[j][i + 1];
							moreX = true;
						} else
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix1[j][i + 1];
						IsRestriction = true;
					}
				}
				// Matrix 2
				for (int j = 1; j < xMatrix2.length; j++) {
					if ((xMatrix2[j][i + 1].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix2[j][i + 1];
							moreX = true;
						} else
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix2[j][i + 1];
						IsRestriction = true;
					}
				}
				moreX = false;
				if (IsRestriction == true) {
					restrictionNK[anzRestrictionNK] += "<="
							+ (((periodSaal) vectorPeriodeSaal.elementAt(i))
									.getAnzSaal()) * defaultperiod;
					anzRestrictionNK++;
					IsRestriction = false;
				}
			}
		} catch (Exception e2) {
			System.out.println(e2.toString() + "!!!!!!!!!!!!!");
		}

	}

	public void restrictionDefaultPeriod() {
		// erstelle Restriktionen: <=Periode NK (<=60)
		boolean IsRestriction = false;
		boolean moreX = false;

		try {
			// Matrix 1

			for (int i = 0; i < vectorPeriodeSaal.size(); i++) {
				for (int j = 1; j < xMatrix1.length; j++) {
					if ((xMatrix1[j][i + 1].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix1[j][i + 1];
							moreX = true;
						} else
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix1[j][i + 1];
						IsRestriction = true;
					}
				}
				moreX = false;
				if (IsRestriction == true) {
					restrictionNK[anzRestrictionNK] += "<=" + defaultperiod;
					anzRestrictionNK++;
					IsRestriction = false;
				}
			}

			// Matrix 2

			for (int i = 0; i < vectorPeriodeSaal.size(); i++) {
				for (int j = 1; j < xMatrix2.length; j++) {
					if ((xMatrix2[j][i + 1].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix2[j][i + 1];
							moreX = true;
						} else
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix2[j][i + 1];
						IsRestriction = true;
					}
				}
				moreX = false;
				if (IsRestriction == true) {
					restrictionNK[anzRestrictionNK] += "<=" + defaultperiod;
					anzRestrictionNK++;
					IsRestriction = false;
				}
			}
		} catch (Exception e2) {
			System.out.println(e2.toString() + "!!!!!!!!!!!!!");
		}
		// Ende Restriktionen Periode NK
	}

	public void restrictionPeriode() {
		boolean IsRestriction = false;
		boolean moreX = false;

		try {
			// spalte
			for (int i = 0; i < vectorPeriodeSaal.size(); i++) {
				// hole aus Matrix 1
				for (int j = 1; j < xMatrix1.length; j++) {
					if ((xMatrix1[j][i + 1].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix1[j][i + 1];
							moreX = true;
						} else
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix1[j][i + 1];
						IsRestriction = true;
					}
				}
				// hole aus Matrix 2
				for (int j = 1; j < xMatrix2.length; j++) {
					if ((xMatrix2[j][i + 1].length()) > 1) {
						if (moreX == false) {
							restrictionNK[anzRestrictionNK] += xMatrix2[j][i + 1];
							moreX = true;
						} else
							restrictionNK[anzRestrictionNK] += "+"
									+ xMatrix2[j][i + 1];
						IsRestriction = true;
					}
				}

				moreX = false;
				if (IsRestriction == true) {
					restrictionNK[anzRestrictionNK] = restrictionNK[anzRestrictionNK]
							+ "-"
							+ ((periodSaal) vectorPeriodeSaal.elementAt(i))
									.getAnzSaal() + "x" + xNrNK + "<=0";
					anzRestrictionNK++;
					IsRestriction = false;
				}
			}
		} catch (Exception e2) {
			System.out.println(e2.toString() + "!!!!!!!!!!!!!");
		}
	}

	public void setData() {
		// erzeuge ein Objekt von LPsolve
		// hole die Ergebnisse
		theLPsolve = new LPsolve(dataNK, lpsolvePath);
		this.setVectorErgebnisse();
	}

	public void setErgebnisseString() {
		// NK ---------------------------------------------------
		int ergebnisNr = 0;

		ergebnisStringNK = "Zielfunktionswert: "
				+ (String) vectorErgebnisse.elementAt(ergebnisNr++);

		// Anzahl Variablen je Matrix (ohne Schaltervariablen und Y)
		int anzVarMatrizen = (xNrNK - vectorOperation.size() - 1);
		int anzVarGesamt = xNrNK;
		int anzVarJeMatrix = anzVarMatrizen / 2;
		// STelle, an der Y im Ergebnisvektor steht
		int stelleY;

		// LPSolve-Ausgabe fngt bei Variablenanzahl >=10 mit der Variable 10 an
		// ->Abprfen
		int zaehler = 10;
		String vergleich = "x10";

		// Berechnung Y:
		if (anzVarGesamt < 10)
			stelleY = anzVarGesamt;
		else
			stelleY = anzVarGesamt - 10 + 1;

		if (anzVarMatrizen >= 10) {
			if (anzVarJeMatrix < 10) {
				// erst Matrix 2 befllen, mit allen Variablen >= x10

				for (int i = 0; i < vectorOperation.size(); i++) {
					for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
						if ((xMatrix2[i + 1][j].compareTo(vergleich)) == 0) {
							xMatrix2[i + 1][j] = (String) vectorErgebnisse
									.elementAt(ergebnisNr++);
							vergleich = "x" + (++zaehler);
						}
					}
				}

				// ergebnisNr muss um Anzahl der Schaltervariablen hochgesetzt
				// werden,
				// da die Schaltervariablen im Vektorstring an zu weit vorne
				// stehen (sie
				// stehen in Reihenfolge des lpSolve-Formats
				ergebnisNr += vectorOperation.size() + 1;
				// an dieser Stelle stehen die Schaltervariablen und das Y

				// Matrix1 befllen
				vergleich = "x1";
				zaehler = 1;
				for (int i = 0; i < vectorOperation.size(); i++) {
					for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
						if ((xMatrix1[i + 1][j].compareTo(vergleich)) == 0) {
							xMatrix1[i + 1][j] = (String) vectorErgebnisse
									.elementAt(ergebnisNr++);
							vergleich = "x" + (++zaehler);
						}
					}
				}
				// Rest in Matrix2 schreiben
				while (zaehler < 10) {
					for (int i = 0; i < vectorOperation.size(); i++) {
						for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
							if ((xMatrix2[i + 1][j].compareTo(vergleich)) == 0) {
								xMatrix2[i + 1][j] = (String) vectorErgebnisse
										.elementAt(ergebnisNr++);
								vergleich = "x" + (++zaehler);
							}
						}
					}
					zaehler++;
				}
			}// endif
			else // anzVariablenJeMatrix >=10
			{
				zaehler = 10;
				vergleich = "x10";
				// flle Matrix1 von x10 bis anzVarJeMatrix
				while (zaehler <= anzVarJeMatrix) {
					for (int i = 0; i < vectorOperation.size(); i++) {
						for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
							if ((xMatrix1[i + 1][j].compareTo(vergleich)) == 0) {
								xMatrix1[i + 1][j] = (String) vectorErgebnisse
										.elementAt(ergebnisNr++);
								vergleich = "x" + (++zaehler);
							}
						}
					}
				} // end while

				// alles folgende in Matrix2 schreiben
				while (zaehler <= anzVarMatrizen) {
					for (int i = 0; i < vectorOperation.size(); i++) {
						for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
							if ((xMatrix2[i + 1][j].compareTo(vergleich)) == 0) {
								xMatrix2[i + 1][j] = (String) vectorErgebnisse
										.elementAt(ergebnisNr++);
								vergleich = "x" + (++zaehler);
							}
						}
					}
				} // end while

				// ergebnisNr muss um Anzahl der Schaltervariablen hochgesetzt
				// werden,
				// da die Schaltervariablen im Vektorstring an zu weit vorne
				// stehen (sie
				// stehen in Reihenfolge des lpSolve-Formats
				ergebnisNr += vectorOperation.size() + 1;
				// an dieser Stelle stehen die Schaltervariablen und das Y

				// Von x1 bis x9 alles in Matrix 1 schreiben
				zaehler = 1;
				vergleich = "x1";

				while (zaehler < 10) {
					for (int i = 0; i < vectorOperation.size(); i++) {
						for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
							if ((xMatrix1[i + 1][j].compareTo(vergleich)) == 0) {
								xMatrix1[i + 1][j] = (String) vectorErgebnisse
										.elementAt(ergebnisNr++);
								vergleich = "x" + (++zaehler);
							}
						}
					}
				} // end while
			}// end else
		}// endif
		else // anzVarMatrizen < 10
		{
			zaehler = 1;
			// Start mit x1
			vergleich = "x1";

			// Matrix1 befllen
			while (zaehler <= anzVarJeMatrix) {
				for (int i = 0; i < vectorOperation.size(); i++) {
					for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
						if ((xMatrix1[i + 1][j].compareTo(vergleich)) == 0) {
							xMatrix1[i + 1][j] = (String) vectorErgebnisse
									.elementAt(ergebnisNr++);
							vergleich = "x" + (++zaehler);
						}
					}
				}
			} // end while

			// Matrix2 befllen
			for (int i = 0; i < vectorOperation.size(); i++) {
				for (int j = 0; j < (vectorPeriodeSaal.size() + 1); j++) {
					if ((xMatrix2[i + 1][j].compareTo(vergleich)) == 0) {
						xMatrix2[i + 1][j] = (String) vectorErgebnisse
								.elementAt(ergebnisNr++);
						vergleich = "x" + (++zaehler);
					}
				}
			}
		}

		// Ausgabe

//		System.out.println("xMatrix1 nach Setzen aus Vektorstring"); for(int
//		i=0;i<xMatrix1.length;i++) { for(int j=0;j<xMatrix1[i].length;j++) {
//		System.out.println(i+" "+j+" :"+xMatrix1[i][j]); } }
//		System.out.println("xMatrix2 nach Setzen aus Vektorstring"); for(int
//		i=0;i<xMatrix2.length;i++) { for(int j=0;j<xMatrix2[i].length;j++) {
//		System.out.println(i+" "+j+" :"+xMatrix2[i][j]); } }

		// Setzen der Ausgabe im Ergebnisfeld
		ergebnisStringNK += "\nOptimale Auslastung betrgt: "
				+ (String) vectorErgebnisse.elementAt(stelleY) + " min"
				+ "\n\n" + "Optimale Verteilung der Operationen auf die Sle:\n";

		String inhalt;
		// Matrix1
		ergebnisStringNK += "\nSaal 1:";
		for (int i = 1; i < xMatrix1.length; i++) // Zeile
		{
			boolean moreX = false;
			for (int j = 1; j < xMatrix1.length; j++) // Spalte
			{
				inhalt = xMatrix1[i][j].toString();
				if ((xMatrix1[i][j].length()) >= 1
						&& !(inhalt.compareTo("0") == 0)
						&& !(inhalt.compareTo("x") == 0)) // keine Ausgabe,
				// wenn 0
				{
					if (moreX == false) {
						// + Feld Bemerkung
						ergebnisStringNK += "\nOperation Nr."
								+ (i)
								+ " ("
								+ ((Operation) vectorOperation.elementAt(i - 1))
										.getMerk() + ") luft in Periode " + (j)
								+ ": " + xMatrix1[i][j] + " min";
						moreX = true;
					} else
						ergebnisStringNK += " und in Periode " + (j) + ": "
								+ xMatrix1[i][j] + " min";
				}

			}
			moreX = false;
		}
		ergebnisStringNK += "\n";

		// Matrix2
		ergebnisStringNK += "\nSaal 2: ";
		for (int i = 1; i < xMatrix2.length; i++) // Zeile
		{
			boolean moreX = false;
			for (int j = 1; j < xMatrix2.length; j++) // Spalte
			{
				inhalt = xMatrix2[i][j].toString();
				if ((xMatrix2[i][j].length()) >= 1
						&& !(inhalt.compareTo("0") == 0)
						&& !(inhalt.compareTo("x") == 0)) // keine Ausgabe,
				// wenn 0
				{
					if (moreX == false) {
						ergebnisStringNK += "\nOperation Nr."
								+ (i)
								+ " ("
								+ ((Operation) vectorOperation.elementAt(i - 1))
										.getMerk() + ") luft in Periode " + (j)
								+ ": " + xMatrix2[i][j] + " min";
						moreX = true;
					} else
						ergebnisStringNK += " und in Periode " + (j) + ": "
								+ xMatrix2[i][j] + " min";
				}
			}
			moreX = false;
		}
//		System.out.println(ergebnisStringNK);

		// Ende Einlesen NK
	}

	@SuppressWarnings("unchecked")
	public void setVectorErgebnisse() {
		if (vectorErgebnisse.size() != 0)
			vectorErgebnisse.removeAllElements();
		vectorErgebnisse = theLPsolve.getMyStringVector();
		this.setErgebnisseString();
	}
}