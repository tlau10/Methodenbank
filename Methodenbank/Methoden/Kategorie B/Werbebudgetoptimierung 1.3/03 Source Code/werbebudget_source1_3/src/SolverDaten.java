/*
    Die Klasse enthlt alle Daten, die zum erstellen der
    Solver-Eingabedatei ntig sind. Die eingelesenen Daten
    knnen als String bergeben werden. Kann der String nicht
    in einen Integer-Wert umgewandelt werden, wird false
    zurckgegeben. Weiterhin lsst diese Klasse die Ausgabe-
    Datei des Solvers erzeugen
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class SolverDaten implements Serializable {
	/**
	 * Serilizable Version ID for Class Version
	 */
	private static final long serialVersionUID = -795292501415025445L;

	// max. zu verwendendes Budget
	private int budget;

	// max. zu verwendendes Budget fr die einzelnen Medien
	private int maxBudgetFernsehanstalten;
	private int maxBudgetRadiosender;
	private int maxBudgetZeitschriften;
	private int maxBudgetSonstigeMedien;

	// Optimierungskriterium
	private int optimierung;

	// Anzahl der unterschiedlichen Medien
	private int anzahlFernsehanstalten;
	private int anzahlRadiosender;
	private int anzahlZeitschriften;
	private int anzahlSonstigeMedien;

	// Anzahl der zu erreichenden Kunden
	// anzahlMaennlicheKunden und anzahlWeiblicheKunden = -1
	// wenn nichts oder fehlerhafte Daten eingegeben werden
	private int anzahlKunden;
	private int anzahlMaennlicheKunden;
	private int anzahlWeiblicheKunden;

	// tatschlich erreichte Kunden und tatschlich
	// verwendetes Budget
	private int solKunden;
	private int solMaennlicheKunden;
	private int solWeiblicheKunden;
	private int solBudget;

	// Die einzelnen eingegebenen Datenfelder
	// [anzahlMedien][Kategorie][Spalte]
	private int[][][] fernsehanstaltenDaten;
	private int[][][] radiosenderDaten;
	private int[][][] zeitschriftenDaten;
	private int[][][] sonstigeMedienDaten;

	// StandardKonstruktor
	public SolverDaten() {
		budget = 0;

		maxBudgetFernsehanstalten = 0;
		maxBudgetRadiosender = 0;
		maxBudgetZeitschriften = 0;
		maxBudgetSonstigeMedien = 0;

		optimierung = 0;

		anzahlFernsehanstalten = 0;
		anzahlRadiosender = 0;
		anzahlZeitschriften = 0;
		anzahlSonstigeMedien = 0;

		anzahlKunden = 0;
		anzahlMaennlicheKunden = -1;
		anzahlWeiblicheKunden = -1;

		solKunden = 0;
		solMaennlicheKunden = 0;
		solWeiblicheKunden = 0;
		solBudget = 0;
	}

	// übergibt das GesamtBudget
	public boolean setBudget(String b) {
		try {
			budget = Integer.parseInt(b);
		} catch (Exception e) {
			budget = 0;
			return false;
		}

		return true;
	}

	// gibt das GesamtBudget zurck
	public int getBudget() {
		return budget;
	}

	// übergibt das Budget fr die Fernsehanstalten
	public boolean setMaxBudgetFernsehanstalten(String b) {
		try {
			maxBudgetFernsehanstalten = Integer.parseInt(b);
		} catch (Exception e) {
			maxBudgetFernsehanstalten = 0;
			return false;
		}

		return true;
	}

	// gibt das Budget fr die Fernsehanstalten zurck
	public int getMaxBudgetFernsehanstalten() {
		return maxBudgetFernsehanstalten;
	}

	// bergibt das Budget fr die Radiosender
	public boolean setMaxBudgetRadiosender(String b) {
		try {
			maxBudgetRadiosender = Integer.parseInt(b);
		} catch (Exception e) {
			maxBudgetRadiosender = 0;
			return false;
		}

		return true;
	}

	// gibt das Budget fr die Radiosender zurck
	public int getMaxBudgetRadiosender() {
		return maxBudgetRadiosender;
	}

	// bergibt das Budget fr die Zeitschriften
	public boolean setMaxBudgetZeitschriften(String b) {
		try {
			maxBudgetZeitschriften = Integer.parseInt(b);
		} catch (Exception e) {
			maxBudgetZeitschriften = 0;
			return false;
		}

		return true;
	}

	// gibt das Budget fr die Zeitschriften zurck
	public int getMaxBudgetZeitschriften() {
		return maxBudgetZeitschriften;
	}

	// bergibt das Budget fr die sonstigen Medien
	public boolean setMaxBudgetSonstigeMedien(String b) {
		try {
			maxBudgetSonstigeMedien = Integer.parseInt(b);
		} catch (Exception e) {
			maxBudgetSonstigeMedien = 0;
			return false;
		}

		return true;
	}

	// gibt das Budget fr die sonstigen Medien zurck
	public int getMaxBudgetSonstigeMedien() {
		return maxBudgetSonstigeMedien;
	}

	// legt die Optimierung fest
	// 0: Minimierung
	// 1: Maximierung
	public void setOptimierung(int index) {
		optimierung = index;
	}

	// gibt die Optimierung zurck
	public int getOptimierung() {
		return optimierung;
	}

	// übergibt die Anzahl Fernsehanstalten
	public boolean setAnzahlFernsehanstalten(String a) {
		try {
			anzahlFernsehanstalten = Integer.parseInt(a);
			fernsehanstaltenDaten = new int[anzahlFernsehanstalten][][];
		} catch (Exception e) {
			anzahlFernsehanstalten = 0;
			fernsehanstaltenDaten = null;
			return false;
		}

		return true;
	}

	// gibt die Anzahl Fernsehsender zurck
	public int getAnzahlFernsehanstalten() {
		return anzahlFernsehanstalten;
	}

	// gibt die Anzahl Kategorien zurck
	public int getAnzahlFernsehanstaltenKategorien(int index) {
		if (index > anzahlFernsehanstalten) {
			return 0;
		}

		try {
			return fernsehanstaltenDaten[index - 1].length;
		} catch (Exception e) {
			return -1;
		}
	}

	// bergibt die Anzahl Radiosender
	public boolean setAnzahlRadiosender(String a) {
		try {
			anzahlRadiosender = Integer.parseInt(a);
			radiosenderDaten = new int[anzahlRadiosender][][];
		} catch (Exception e) {
			anzahlRadiosender = 0;
			radiosenderDaten = null;
			return false;
		}

		return true;
	}

	// gibt die Anzahl Radiosender zurck
	public int getAnzahlRadiosender() {
		return anzahlRadiosender;
	}

	// gibt die Anzahl Kategorien zurck
	public int getAnzahlRadiosenderKategorien(int index) {
		if (index > anzahlRadiosender) {
			return 0;
		}

		try {
			return radiosenderDaten[index - 1].length;
		} catch (Exception e) {
			return -1;
		}
	}

	// bergibt die Anzahl Zeitschriften
	public boolean setAnzahlZeitschriften(String a) {
		try {
			anzahlZeitschriften = Integer.parseInt(a);
			zeitschriftenDaten = new int[anzahlZeitschriften][][];
		} catch (Exception e) {
			anzahlZeitschriften = 0;
			zeitschriftenDaten = null;
			return false;
		}

		return true;
	}

	// gibt die Anzahl Zeitschriften zurck
	public int getAnzahlZeitschriften() {
		return anzahlZeitschriften;
	}

	// gibt die Anzahl Kategorien zurck
	public int getAnzahlZeitschriftenKategorien(int index) {
		if (index > anzahlZeitschriften) {
			return 0;
		}

		try {
			return zeitschriftenDaten[index - 1].length;
		} catch (Exception e) {
			return -1;
		}
	}

	// bergibt die Anzahl sonstige Medien
	public boolean setAnzahlSonstigeMedien(String a) {
		try {
			anzahlSonstigeMedien = Integer.parseInt(a);
			sonstigeMedienDaten = new int[anzahlSonstigeMedien][][];
		} catch (Exception e) {
			anzahlSonstigeMedien = 0;
			sonstigeMedienDaten = null;
			return false;
		}

		return true;
	}

	// gibt die Anzahl sonstige Medien zurck
	public int getAnzahlSonstigeMedien() {
		return anzahlSonstigeMedien;
	}

	// gibt die Anzahl Kategorien zurck
	public int getAnzahlSonstigeMedienKategorien(int index) {
		if (index > anzahlSonstigeMedien) {
			return 0;
		}

		try {
			return sonstigeMedienDaten[index - 1].length;
		} catch (Exception e) {
			return -1;
		}
	}

	// bergibt die Anzahl der Kunden
	public boolean setAnzahlKunden(String a) {
		try {
			anzahlKunden = Integer.parseInt(a);
		} catch (Exception e) {
			anzahlKunden = 0;
			return false;
		}

		return true;
	}

	// gibt die Anzahl der Kunden zurck
	public int getAnzahlKunden() {
		return anzahlKunden;
	}

	// bergibt die Anzahl mnnlicher Kunden
	public boolean setAnzahlMaennlicheKunden(String a) {
		try {
			anzahlMaennlicheKunden = Integer.parseInt(a);
			anzahlWeiblicheKunden = anzahlKunden - anzahlMaennlicheKunden;

			if (anzahlMaennlicheKunden > anzahlKunden) {
				anzahlMaennlicheKunden = -1;
				anzahlWeiblicheKunden = -1;
				return false;
			}
		} catch (Exception e) {
			anzahlMaennlicheKunden = -1;
			anzahlWeiblicheKunden = -1;
			return false;
		}

		return true;
	}

	// gibt die Anzahl mnnlicher Kunden zurck
	public int getAnzahlMaennlicheKunden() {
		return anzahlMaennlicheKunden;
	}

	// bergibt die Anzahl weiblicher Kunden
	public boolean setAnzahlWeiblicheKunden(String a) {
		try {
			anzahlWeiblicheKunden = Integer.parseInt(a);
			anzahlMaennlicheKunden = anzahlKunden - anzahlWeiblicheKunden;

			if (anzahlWeiblicheKunden > anzahlKunden) {
				anzahlMaennlicheKunden = -1;
				anzahlWeiblicheKunden = -1;
				return false;
			}
		} catch (Exception e) {
			anzahlMaennlicheKunden = -1;
			anzahlWeiblicheKunden = -1;
			return false;
		}

		return true;
	}

	// gibt die Anzahl weiblicher Kunden zurck
	public int getAnzahlWeiblicheKunden() {
		return anzahlWeiblicheKunden;
	}

	// set- und getMethoden fr die einzelnen Daten
	// ----------
	public boolean setFernsehanstaltenKategorien(int i, int x, int y) {
		try {
			fernsehanstaltenDaten[i] = new int[x][y];
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean setFernsehanstaltenDaten(int i, int x, int y, String value) {
		try {
			fernsehanstaltenDaten[i][x][y] = Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public int getFernsehanstaltenKosten(int nr, int kategorie) {
		try {
			return fernsehanstaltenDaten[nr][kategorie][0];
		} catch (Exception e) {
			return -1;
		}
	}

	public int getFernsehanstaltenKunden(int nr, int kategorie) {
		try {
			return fernsehanstaltenDaten[nr][kategorie][1];
		} catch (Exception e) {
			return -1;
		}
	}

	public boolean setRadiosenderKategorien(int i, int x, int y) {
		try {
			radiosenderDaten[i] = new int[x][y];
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean setRadiosenderDaten(int i, int x, int y, String value) {
		try {
			radiosenderDaten[i][x][y] = Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public int getRadiosenderKosten(int nr, int kategorie) {
		try {
			return radiosenderDaten[nr][kategorie][0];
		} catch (Exception e) {
			return -1;
		}
	}

	public int getRadiosenderKunden(int nr, int kategorie) {
		try {
			return radiosenderDaten[nr][kategorie][1];
		} catch (Exception e) {
			return -1;
		}
	}

	public boolean setZeitschriftenKategorien(int i, int x, int y) {
		try {
			zeitschriftenDaten[i] = new int[x][y];
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean setZeitschriftenDaten(int i, int x, int y, String value) {
		try {
			zeitschriftenDaten[i][x][y] = Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public int getZeitschriftenKosten(int nr, int kategorie) {
		try {
			return zeitschriftenDaten[nr][kategorie][0];
		} catch (Exception e) {
			return -1;
		}
	}

	public int getZeitschriftenKunden(int nr, int kategorie) {
		try {
			return zeitschriftenDaten[nr][kategorie][1];
		} catch (Exception e) {
			return -1;
		}
	}

	public boolean setSonstigeMedienKategorien(int i, int x, int y) {
		try {
			sonstigeMedienDaten[i] = new int[x][y];
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean setSonstigeMedienDaten(int i, int x, int y, String value) {
		try {
			sonstigeMedienDaten[i][x][y] = Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public int getSonstigeMedienKosten(int nr, int kategorie) {
		try {
			return sonstigeMedienDaten[nr][kategorie][0];
		} catch (Exception e) {
			return -1;
		}
	}

	public int getSonstigeMedienKunden(int nr, int kategorie) {
		try {
			return sonstigeMedienDaten[nr][kategorie][1];
		} catch (Exception e) {
			return -1;
		}
	}

	public int getGesamtAnzahlKategorien() {
		int anzahl = 0;

		for (int i = 0; i < anzahlFernsehanstalten; i++) {
			anzahl += fernsehanstaltenDaten[i].length;
		}

		for (int i = 0; i < anzahlRadiosender; i++) {
			anzahl += radiosenderDaten[i].length;
		}

		for (int i = 0; i < anzahlZeitschriften; i++) {
			anzahl += zeitschriftenDaten[i].length;
		}

		for (int i = 0; i < anzahlSonstigeMedien; i++) {
			anzahl += sonstigeMedienDaten[i].length;
		}

		return anzahl;
	}

	public int[][][] getfernsehanstaltenDaten() {
		return fernsehanstaltenDaten;
	}

	public int[][][] getradiosenderDaten() {
		return radiosenderDaten;
	}

	public int[][][] getzeitschriftenDaten() {
		return zeitschriftenDaten;
	}

	public int[][][] getsonstigeMedienDaten() {
		return sonstigeMedienDaten;
	}

	// ----------

	// Die Werte die der Solver geliefert hat werden in
	// einem Array übergeben und den entsprechenden Variablen
	// zugeordnet
	public void setSolverOutDaten(int[] x) {
		int zaehler = 0;

		solKunden = 0;
		solMaennlicheKunden = 0;
		solWeiblicheKunden = 0;
		solBudget = 0;

		try {
			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					solBudget = solBudget
							+ (fernsehanstaltenDaten[i][j][0] * x[zaehler]);
					solKunden = solKunden
							+ (fernsehanstaltenDaten[i][j][1] * x[zaehler]);
					solMaennlicheKunden = solMaennlicheKunden
							+ (fernsehanstaltenDaten[i][j][2] * x[zaehler]);
					solWeiblicheKunden = solWeiblicheKunden
							+ (fernsehanstaltenDaten[i][j][3] * x[zaehler]);

					zaehler++;
				}
			}

			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					solBudget = solBudget
							+ (radiosenderDaten[i][j][0] * x[zaehler]);
					solKunden = solKunden
							+ (radiosenderDaten[i][j][1] * x[zaehler]);
					solMaennlicheKunden = solMaennlicheKunden
							+ (radiosenderDaten[i][j][2] * x[zaehler]);
					solWeiblicheKunden = solWeiblicheKunden
							+ (radiosenderDaten[i][j][3] * x[zaehler]);

					zaehler++;
				}
			}

			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					solBudget = solBudget
							+ (zeitschriftenDaten[i][j][0] * x[zaehler]);
					solKunden = solKunden
							+ (zeitschriftenDaten[i][j][1] * x[zaehler]);
					solMaennlicheKunden = solMaennlicheKunden
							+ (zeitschriftenDaten[i][j][2] * x[zaehler]);
					solWeiblicheKunden = solWeiblicheKunden
							+ (zeitschriftenDaten[i][j][3] * x[zaehler]);

					zaehler++;
				}
			}

			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					solBudget = solBudget
							+ (sonstigeMedienDaten[i][j][0] * x[zaehler]);
					solKunden = solKunden
							+ (sonstigeMedienDaten[i][j][1] * x[zaehler]);
					solMaennlicheKunden = solMaennlicheKunden
							+ (sonstigeMedienDaten[i][j][2] * x[zaehler]);
					solWeiblicheKunden = solWeiblicheKunden
							+ (sonstigeMedienDaten[i][j][3] * x[zaehler]);

					zaehler++;
				}
			}
		} catch (Exception e) {
			return;
		}
	}

	// gibt das errechnete Budget zurck
	public int getSolverBudget() {
		return solBudget;
	}

	// gibt die anzahl erreichter Kunden zurck
	public int getSolverKunden() {
		return solKunden;
	}

	// gibt die Anzahl mnnlicher Kunden zurck
	public int getSolverMaennlicheKunden() {
		return solMaennlicheKunden;
	}

	// gibt die Anzahl weiblicher Kunden zurck
	public int getSolverWeiblicheKunden() {
		return solWeiblicheKunden;
	}

	// die Methode testet, ob fr alle Kategorien die Kosten
	// eingegeben wurden
	public boolean testKostenSet() {
		try {
			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					if (fernsehanstaltenDaten[i][j][0] == 0) {
						return false;
					}
				}
			}

			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					if (radiosenderDaten[i][j][0] == 0) {
						return false;
					}
				}
			}

			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					if (zeitschriftenDaten[i][j][0] == 0) {
						return false;
					}
				}
			}

			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					if (sonstigeMedienDaten[i][j][0] == 0) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// die Methode testet, ob fr alle FernsehKategorien die Kosten
	// eingegeben wurden
	public boolean testFernsehKostenSet() {
		try {
			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					if (fernsehanstaltenDaten[i][j][0] == 0) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// die Methode testet, ob fr alle RadioKategorien die Kosten
	// eingegeben wurden
	public boolean testRadioKostenSet() {
		try {
			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					if (radiosenderDaten[i][j][0] == 0) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// die Methode testet, ob fr alle ZeitscriftenKategorien die Kosten
	// eingegeben wurden
	public boolean testZeitschriftenKostenSet() {
		try {
			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					if (zeitschriftenDaten[i][j][0] == 0) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// die Methode testet, ob fr alle sonstigen Medien Kategorien die Kosten
	// eingegeben wurden
	public boolean testSonstigeKostenSet() {
		try {
			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					if (sonstigeMedienDaten[i][j][0] == 0) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// diese komplexe Methode erzeugt eine Datei, die an
	// den LP_SOLVE zur Berechnung bergeben werden kann
	// und startet den Solver
	public void setSolverInput() {
		// bestimme Anzahl Elemente x
		int anzahlX = 0;

		for (int i = 0; i < anzahlFernsehanstalten; i++) {
			anzahlX += fernsehanstaltenDaten[i].length;
		}

		for (int i = 0; i < anzahlRadiosender; i++) {
			anzahlX += radiosenderDaten[i].length;
		}

		for (int i = 0; i < anzahlZeitschriften; i++) {
			anzahlX += zeitschriftenDaten[i].length;
		}

		for (int i = 0; i < anzahlSonstigeMedien; i++) {
			anzahlX += sonstigeMedienDaten[i].length;
		}

		if (anzahlX == 0) {
			return;
		}

		// schreibe Solver.in - Datei
		try {
			File solverIn;
			solverIn = new File("C:/Temp/Solver.in");
			if (solverIn.exists()) {
				if (solverIn.delete() == false) {
					JOptionPane
							.showMessageDialog(
									null,
									"Datei Solver.in kann nicht berschrieben werden. Exit 1",
									"Fehler", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}

			int zaehler = 1;
			PrintWriter out = new PrintWriter(new FileWriter("C:/Temp/Solver.in"));

			// Start Zielfunktion
			if (optimierung == 0) // Minimierung des Budgets
			{
				out.print("min:");
				for (int i = 0; i < anzahlFernsehanstalten; i++) {
					for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
						out.print(" " + fernsehanstaltenDaten[i][j][0] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlRadiosender; i++) {
					for (int j = 0; j < radiosenderDaten[i].length; j++) {
						out.print(" " + radiosenderDaten[i][j][0] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlZeitschriften; i++) {
					for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
						out.print(" " + zeitschriftenDaten[i][j][0] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlSonstigeMedien; i++) {
					for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
						out.print(" " + sonstigeMedienDaten[i][j][0] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}
				out.println(";");
				out.println("");
			} else if (optimierung == 1) // Maximierung der Kunden
			{
				out.print("max:");
				for (int i = 0; i < anzahlFernsehanstalten; i++) {
					for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
						out.print(" " + fernsehanstaltenDaten[i][j][1] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlRadiosender; i++) {
					for (int j = 0; j < radiosenderDaten[i].length; j++) {
						out.print(" " + radiosenderDaten[i][j][1] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlZeitschriften; i++) {
					for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
						out.print(" " + zeitschriftenDaten[i][j][1] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlSonstigeMedien; i++) {
					for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
						out.print(" " + sonstigeMedienDaten[i][j][1] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}
				out.println(";");
				out.println("");
			}
			// Ende Zielfunktion

			// Start Kosten-Bedingung
			zaehler = 1;

			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					out.print(" " + fernsehanstaltenDaten[i][j][0] + "x"
							+ zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}

			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					out.print(" " + radiosenderDaten[i][j][0] + "x" + zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}

			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					out
							.print(" " + zeitschriftenDaten[i][j][0] + "x"
									+ zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}

			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					out.print(" " + sonstigeMedienDaten[i][j][0] + "x"
							+ zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}
			out.println(" <= " + budget + ";");
			// Ende Kosten-Bedingung

			// Start Kunden-Bedingung
			zaehler = 1;

			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					out.print(" " + fernsehanstaltenDaten[i][j][1] + "x"
							+ zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}

			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					out.print(" " + radiosenderDaten[i][j][1] + "x" + zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}

			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					out
							.print(" " + zeitschriftenDaten[i][j][1] + "x"
									+ zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}

			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					out.print(" " + sonstigeMedienDaten[i][j][1] + "x"
							+ zaehler);
					if (zaehler < anzahlX) {
						zaehler++;
						out.print(" +");
					}
				}
			}
			out.println(" >= " + anzahlKunden + ";");
			// Ende Kunden-Bedingung

			// Start Kosten-Bedingung einzelne Medien
			zaehler = 1;

			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					if (maxBudgetFernsehanstalten > 0) {
						out.print(fernsehanstaltenDaten[i][j][0] + "x"
								+ zaehler);

						if (j == fernsehanstaltenDaten[i].length - 1) {
							out.println(" <= " + maxBudgetFernsehanstalten
									+ ";");
						} else {
							out.print(" + ");
						}
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					if (maxBudgetRadiosender > 0) {
						out.print(radiosenderDaten[i][j][0] + "x" + zaehler);

						if (j == radiosenderDaten[i].length - 1) {
							out.println(" <= " + maxBudgetRadiosender + ";");
						} else {
							out.print(" + ");
						}
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					if (maxBudgetZeitschriften > 0) {
						out.print(zeitschriftenDaten[i][j][0] + "x" + zaehler);

						if (j == zeitschriftenDaten[i].length - 1) {
							out.println(" <= " + maxBudgetZeitschriften + ";");
						} else {
							out.print(" + ");
						}
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					if (maxBudgetSonstigeMedien > 0) {
						out.print(sonstigeMedienDaten[i][j][0] + "x" + zaehler);

						if (j == sonstigeMedienDaten[i].length - 1) {
							out.println(" <= " + maxBudgetSonstigeMedien + ";");
						} else {
							out.print(" + ");
						}
					}
					zaehler++;
				}
			}
			// Ende Kosten-Bedingung einzelne Medien

			// Start mnnl. Kunden - Bedingung
			if (anzahlMaennlicheKunden > 0) {
				zaehler = 1;

				for (int i = 0; i < anzahlFernsehanstalten; i++) {
					for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
						out.print(" " + fernsehanstaltenDaten[i][j][2] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlRadiosender; i++) {
					for (int j = 0; j < radiosenderDaten[i].length; j++) {
						out.print(" " + radiosenderDaten[i][j][2] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlZeitschriften; i++) {
					for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
						out.print(" " + zeitschriftenDaten[i][j][2] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlSonstigeMedien; i++) {
					for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
						out.print(" " + sonstigeMedienDaten[i][j][2] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}
				out.println(" >= " + anzahlMaennlicheKunden + ";");
			}
			// Ende mnnl. Kunden - Bedingung

			// Start weibl. Kunden - Bedingung
			if (anzahlWeiblicheKunden > 0) {
				zaehler = 1;

				for (int i = 0; i < anzahlFernsehanstalten; i++) {
					for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
						out.print(" " + fernsehanstaltenDaten[i][j][3] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlRadiosender; i++) {
					for (int j = 0; j < radiosenderDaten[i].length; j++) {
						out.print(" " + radiosenderDaten[i][j][3] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlZeitschriften; i++) {
					for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
						out.print(" " + zeitschriftenDaten[i][j][3] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}

				for (int i = 0; i < anzahlSonstigeMedien; i++) {
					for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
						out.print(" " + sonstigeMedienDaten[i][j][3] + "x"
								+ zaehler);
						if (zaehler < anzahlX) {
							zaehler++;
							out.print(" +");
						}
					}
				}
				out.println(" >= " + anzahlWeiblicheKunden + ";");
			}
			// Ende weibl. Kunden - Bedingung

			// Start min. Einheiten - Bedingungen
			zaehler = 1;

			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					if (fernsehanstaltenDaten[i][j][4] > 0) {
						out.println("x" + zaehler + " >= "
								+ fernsehanstaltenDaten[i][j][4] + ";");
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					if (radiosenderDaten[i][j][4] > 0) {
						out.println("x" + zaehler + " >= "
								+ radiosenderDaten[i][j][4] + ";");
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					if (zeitschriftenDaten[i][j][4] > 0) {
						out.println("x" + zaehler + " >= "
								+ zeitschriftenDaten[i][j][4] + ";");
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					if (sonstigeMedienDaten[i][j][4] > 0) {
						out.println("x" + zaehler + " >= "
								+ sonstigeMedienDaten[i][j][4] + ";");
					}
					zaehler++;
				}
			}
			// Ende min. Einheiten - Bedingungen

			// Start max. Einheiten - Bedingungen
			zaehler = 1;

			for (int i = 0; i < anzahlFernsehanstalten; i++) {
				for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
					if (fernsehanstaltenDaten[i][j][5] > 0) {
						out.println("x" + zaehler + " <= "
								+ fernsehanstaltenDaten[i][j][5] + ";");
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlRadiosender; i++) {
				for (int j = 0; j < radiosenderDaten[i].length; j++) {
					if (radiosenderDaten[i][j][5] > 0) {
						out.println("x" + zaehler + " <= "
								+ radiosenderDaten[i][j][5] + ";");
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlZeitschriften; i++) {
				for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
					if (zeitschriftenDaten[i][j][5] > 0) {
						out.println("x" + zaehler + " <= "
								+ zeitschriftenDaten[i][j][5] + ";");
					}
					zaehler++;
				}
			}

			for (int i = 0; i < anzahlSonstigeMedien; i++) {
				for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
					if (sonstigeMedienDaten[i][j][5] > 0) {
						out.println("x" + zaehler + " <= "
								+ sonstigeMedienDaten[i][j][5] + ";");
					}
					zaehler++;
				}
			}
			// Ende max. Einheiten - Bedingungen

			// Ganzzahligkeit sichern
			out.println("");
			out.print("int");
			for (int i = 1; i <= anzahlX; i++) {
				out.print(" x" + i);
				if (i < anzahlX) {
					out.print(",");
				} else {
					out.println(";");
				}
			}

			out.close();
		} catch (Exception e) {
		}

		File solver;
		solver = new File("C:/Temp/Solver.out");
		if (solver.exists()) {
			if (solver.delete() == false) {
				JOptionPane
						.showMessageDialog(
								null,
								"Datei Solver.out kann nicht berschrieben werden. Exit 1",
								"Fehler", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}

		// Starten des Solvers
		String command = "solver.bat";

		Runtime rt;
		Process p = null;

		try {
			rt = Runtime.getRuntime();
			p = rt.exec(command);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Kann Solver.bat nicht ausf\u00fchren. Exit 1", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		int counter = 60;
		for (int i = 0; i < counter; i++) {
			solver = new File("C:/Temp/Solver.out");

			if (solver.exists()) {
				i = counter;
			} else {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
			}
		}

		if (p != null) {
			p.destroy();
		}
	}

	// die folgenden Methoden sind nur Testmethoden, welche bei der
	// Entwicklung bentigt wurden. Fr den Programmablauf sind sie
	// nicht ntig, eventuell aber fr Weiterentwicklungen
	public void printFernsehDaten() {
		for (int i = 0; i < fernsehanstaltenDaten.length; i++) {
			System.out.println("Tabelle " + i);
			for (int j = 0; j < fernsehanstaltenDaten[i].length; j++) {
				System.out.print("Kategorie " + j + ": ");
				for (int k = 0; k < fernsehanstaltenDaten[i][j].length; k++) {
					System.out.print(fernsehanstaltenDaten[i][j][k] + " ");
				}
				System.out.println(" ");
			}
			System.out.println(" ");
		}
	}

	public void printRadioDaten() {
		for (int i = 0; i < radiosenderDaten.length; i++) {
			System.out.println("Tabelle " + i);
			for (int j = 0; j < radiosenderDaten[i].length; j++) {
				System.out.print("Kategorie " + j + ": ");
				for (int k = 0; k < radiosenderDaten[i][j].length; k++) {
					System.out.print(radiosenderDaten[i][j][k] + " ");
				}
				System.out.println(" ");
			}
			System.out.println(" ");
		}
	}

	public void printZeitschriftDaten() {
		for (int i = 0; i < zeitschriftenDaten.length; i++) {
			System.out.println("Tabelle " + i);
			for (int j = 0; j < zeitschriftenDaten[i].length; j++) {
				System.out.print("Kategorie " + j + ": ");
				for (int k = 0; k < zeitschriftenDaten[i][j].length; k++) {
					System.out.print(zeitschriftenDaten[i][j][k] + " ");
				}
				System.out.println(" ");
			}
			System.out.println(" ");
		}
	}

	public void printSonstigeMedienDaten() {
		for (int i = 0; i < sonstigeMedienDaten.length; i++) {
			System.out.println("Tabelle " + i);
			for (int j = 0; j < sonstigeMedienDaten[i].length; j++) {
				System.out.print("Kategorie " + j + ": ");
				for (int k = 0; k < sonstigeMedienDaten[i][j].length; k++) {
					System.out.print(sonstigeMedienDaten[i][j][k] + " ");
				}
				System.out.println(" ");
			}
			System.out.println(" ");
		}
	}
}
