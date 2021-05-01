package Dakin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Vector;
import javax.swing.JPanel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.annotations.XYPolygonAnnotation;

/**
 * <p>
 * Überschrift:
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author unbekannt
 * @version 1.0
 */

// ////////////////////////////////////////////////
// SS13 ==> Klassen wurden von InternalFrames zu JPanels geändert
// für die Darstellung in den Tabs
// ////////////////////////////////////////////////
public class Visualisiere extends JPanel {

	// Zielfunktionsgerade(n)
	private DakinKoordinate zfKoeffizienten;
	private DakinKoordinate[] zfGerade;
	private DakinKoordinate[] zfGerade2;
	private float zfWert;
	private float zfWert2;
	// Restriktionen
	private int anzahlEingabeRestriktionen;
	private DakinKoordinate[] rKoeffizienten;
	private DakinKoordinate[] rGeraden;
	private Float[] bVektor;
	private DakinKoordinate restriktionenSchnitt;
	// Loesungsraum-Polygon
	private DakinKoordinate[] loesungsPolygon;
	private DakinKoordinate[] loesungsPolygon2;
	private boolean sortierePoly;
	private int infoDialog = -1;
	// Range-Bereich
	float maxX;
	float maxY;

	private JFreeChart chart;
	private XYPlot plot;

	private XYLineAndShapeRenderer renderer;

	private ChartPanel chartPanel;
	private JPanel unteresPanel;
	private JButton weiterButton;
	private JButton polygonKoordinaten;

	private Vector visualierungsObjekte;
	private Vector alleErgebnisVektoren;
	private boolean letzteIteration;

	private boolean ersteVisualisierung;

	private double[] datenPolygon;
	private double[] datenPolygon2;

	public Visualisiere() {
		super();

		visualierungsObjekte = new Vector();
		// ///////////////////////////////////////////////////////////
		// SS13 ==> gesamtesPanel fällt weg, Visualisiere-Objekt ist jetzt ein
		// Panel!!!
		// gesamtesPanel = new JPanel();
		// setContentPane(gesamtesPanel);
		setLayout(new BorderLayout());
		letzteIteration = false;
		ersteVisualisierung = true;
	}

	public void setVisualisierungsObjekte(Vector _visualisierungsObjekte) {
		this.visualierungsObjekte = _visualisierungsObjekte;
	}

	public void setAlleErgebnisVektoren(Vector _alleErgebnisVektoren) {
		this.alleErgebnisVektoren = _alleErgebnisVektoren;
	}

	// /////////////////////////////////////////////////////////////
	// SS13 ==> Aufrufe um die visualierungsObjekte bei jeder neuen Berechnung
	// zu resetten.
	// Bei der letzten Iteration wird der Vector geleert.
	public void testAufLetzteIteration() {
		visualierungsObjekte.clear();
	}

	public boolean getErsteVisualisierung() {
		return this.ersteVisualisierung;
	}

	// /////////////////////////////////////////////////////////////

	public void update() {
		XYSeries zf1;
		XYSeries zf2;
		XYSeries r1;
		XYSeries r2;
		datenPolygon = null;
		datenPolygon2 = null;
		XYSeriesCollection datasetLines;

		ersteVisualisierung = false;

		// Wenn es nichts darzustellen gibt, stelle auch nichts dar.
		if (((Vector) (visualierungsObjekte.get(0))).size() == 0) {
			datasetLines = null;
			// ////////////////////////////////////////////////
			// NEU SS13 ==> KOMPLETTE Überarbeitung des Plotters
			// Für die Areas müssen "Dummies" erstellt werden, wenn
			// keine Punkte vorhanden sind, ansonsten gibt es unten
			// Probleme bei der Zuordnung
			datenPolygon = new double[] { 0 };
			datenPolygon2 = new double[] { 0 };
		} else {
			zf1 = new XYSeries("ZF-Gerade");
			if (((DakinKoordinate[]) ((Vector) visualierungsObjekte.get(0))
					.get(0)) != null) {
				zf1.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(0))[0].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(0))[0].x2()));
				zf1.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(0))[1].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(0))[1].x2()));
			}

			zf2 = new XYSeries("ZF-Gerade2");
			if (((DakinKoordinate[]) ((Vector) visualierungsObjekte.get(0))
					.get(1)) != null) {
				zf2.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(1))[0].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(1))[0].x2()));
				zf2.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(1))[1].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(1))[1].x2()));
			}

			r1 = new XYSeries("R1");
			if (((DakinKoordinate[]) ((Vector) visualierungsObjekte.get(0))
					.get(2)) != null) {
				r1.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(2))[0].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(2))[0].x2()));
				r1.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(2))[1].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(2))[1].x2()));
			}

			r2 = new XYSeries("R2");
			if (((DakinKoordinate[]) ((Vector) visualierungsObjekte.get(0))
					.get(2)) != null) {
				r2.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(2))[2].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(2))[2].x2()));
				r2.add((((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(2))[3].x1()),
						(((DakinKoordinate[]) ((Vector) visualierungsObjekte
								.get(0)).get(2))[3].x2()));
			}

			// ////////////////////////////////////////////////
			// NEU SS13 ==> Punkte müssen in ein "double-Array" eingelesen
			// werden. Immer 2 Werte ergeben eine Koordinate für die spätere
			// Darstellung.
			if (((DakinKoordinate[]) ((Vector) visualierungsObjekte.get(0))
					.get(3)) != null) {
				// ///////////////////////////////////////////////////
				// SS13 --> Nicht mehr in SeriesCollection !!!ALT!!!
				// Jetzt in doubleArray[]!
				/*
				 * for (int i = 0; i < ((DakinKoordinate[]) ((Vector)
				 * visualierungsObjekte .get(0)).get(3)).length; i++) {
				 * seriesPolygon .add((((DakinKoordinate[]) ((Vector)
				 * visualierungsObjekte .get(0)).get(3))[i].x1()),
				 * (((DakinKoordinate[]) ((Vector) visualierungsObjekte
				 * .get(0)).get(3))[i].x2())); }
				 */
				// ///////////////////////////////////////////////////

				int datenPolygonPlatz = 0;
				double datenPolygonX1 = 0;
				double datenPolygonX2 = 0;
				// Anzahl der Punkte auslesen
				int anzahlPunktePolygon = ((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(3)).length;

				// System.out.println("Anzahl Punkte Polygon: "
				// + anzahlPunktePolygon);

				// Anzahl der Punkte mal 2, für die Arraygröße --> 1 Punkt = 2
				// Koordinaten (x1 & x2)
				// XYPolygonAnnotation liest immer 2 Werte im Array als 1 Punkt!
				datenPolygon = new double[(anzahlPunktePolygon) * 2];

				// Schleife wird so oft durchlaufen wie es Punkte gibt --> pro
				// Schleifen durchlauf immer 2 Werte in Array!
				// x1 und x2!
				for (int i = 0; i < anzahlPunktePolygon; i++) {
					// x1 und x2 in double umwandeln
					datenPolygonX1 = (((double) (((DakinKoordinate[]) ((Vector) visualierungsObjekte
							.get(0)).get(3))[i].x1())));
					datenPolygonX2 = ((double) (((DakinKoordinate[]) ((Vector) visualierungsObjekte
							.get(0)).get(3))[i].x2()));

					// x1 und x2 dem Array hinzufügen
					datenPolygon[datenPolygonPlatz] = datenPolygonX1;
					datenPolygon[datenPolygonPlatz + 1] = datenPolygonX2;
					System.out.println("Polygon - Punkt " + i + ": ("
							+ datenPolygonX1 + "/" + datenPolygonX2 + ")");
					datenPolygonPlatz = datenPolygonPlatz + 2;
				}
			} else {
				// DUMMIE einsetzen, damit kein NULLPOINTER!!!
				datenPolygon = new double[] { 0 };
			}
			System.out.println("--------");

			// ///////////////////////////////////////////////////
			// NEU SS13 ==> Selbe Überarbeitung wie oben.
			// ///////////////////////////////////////////////////
			if (((DakinKoordinate[]) ((Vector) visualierungsObjekte.get(0))
					.get(4)) != null) {
				// ///////////////////////////////////////////////////
				// SS13 --> Nicht mehr in SeriesCollection !!!ALT!!!
				// Jetzt in doubleArray[]!
				/*
				 * for (int i = 0; i < ((DakinKoordinate[]) ((Vector)
				 * visualierungsObjekte .get(0)).get(4)).length; i++) {
				 * seriesPolygon2 .add((((DakinKoordinate[]) ((Vector)
				 * visualierungsObjekte .get(0)).get(4))[i].x1()),
				 * (((DakinKoordinate[]) ((Vector) visualierungsObjekte
				 * .get(0)).get(4))[i].x2())); }
				 */
				// ///////////////////////////////////////////////////
				int datenPolygon2Platz = 0;
				double datenPolygon2X1 = 0;
				double datenPolygon2X2 = 0;
				int anzahlPunktePolygon2 = ((DakinKoordinate[]) ((Vector) visualierungsObjekte
						.get(0)).get(4)).length;

				System.out.println("Anzahl Punkte Polygon2: "
						+ anzahlPunktePolygon2);
				datenPolygon2 = new double[(anzahlPunktePolygon2) * 2];
				for (int i = 0; i < anzahlPunktePolygon2; i++) {
					datenPolygon2X1 = ((((DakinKoordinate[]) ((Vector) visualierungsObjekte
							.get(0)).get(4))[i].x1()));
					datenPolygon2X2 = (((DakinKoordinate[]) ((Vector) visualierungsObjekte
							.get(0)).get(4))[i].x2());

					datenPolygon2[datenPolygon2Platz] = datenPolygon2X1;
					datenPolygon2[datenPolygon2Platz + 1] = datenPolygon2X2;
					System.out.println("Polygon 2 - Punkt " + i + ": ("
							+ datenPolygon2X1 + "/" + datenPolygon2X2 + ")");

					datenPolygon2Platz = datenPolygon2Platz + 2;
				}
			} else {
				// DUMMIE einsetzen, damit kein NULLPOINTER!!!
				datenPolygon2 = new double[] { 0 };
			}
			System.out.println("--------------------------------------");

			// SeriesCollection für die einzelnen Linien
			datasetLines = new XYSeriesCollection(r1);
			datasetLines.addSeries(r2);
			datasetLines.addSeries(zf1);
			datasetLines.addSeries(zf2);
		}

		String title;
		if (((Vector) alleErgebnisVektoren.get(0)).size() == 0) {
			title = "Knoten nicht lösbar";
		} else {
			if (((Vector) alleErgebnisVektoren.firstElement()).size() == 1)
				title = "Lösung des Knotens: ";
			else
				title = "Lösung der Knoten: ";
			for (int x = 0; x < ((Vector) alleErgebnisVektoren.firstElement())
					.size(); x++)
				title += ((Vector) alleErgebnisVektoren.firstElement()).get(x)
						.toString();
		}

		// Renderer für die Strokes OHNE gefüllten Bereich
		renderer = new XYLineAndShapeRenderer();
		// Die Linienpunkte nicht anzeigen
		renderer.setShapesVisible(false);
		// Einzelne Linienfarben bestimmen
		renderer.setSeriesPaint(0, Color.black);
		renderer.setSeriesPaint(1, Color.blue);
		renderer.setSeriesPaint(2, Color.red);
		renderer.setSeriesPaint(2, Color.orange);

		// Achsen werden mit Zahlen und X1 & X2 beschriftet
		NumberAxis xax = new NumberAxis("x1");
		NumberAxis yax = new NumberAxis("x2");
		// Plot wird initialisiert mit dem Dataset (die Linien) und dessem
		// Renderer an der Stelle 0
		plot = new XYPlot(datasetLines, xax, yax, renderer);

		// ///////////////////////////////////////////////
		// SS13 --> kann wieder weg, funktioniert NICHT über XYArea!!!
		// läuft jetzt über XYPolygoneAnnotation!!!
		// ///////////////////////////////////////////////

		// Initialisieren des 2. Renderers für die Lösungsräume mit Füllung
		// areaRenderer = new XYAreaRenderer();
		// Hinzufügen des 2. Datasets (Lösungsräume) zu dem Plot an der Stelle 1
		// plot.setDataset(1, datasetAreas);
		// Flächenfarbe bestimmen
		// areaRenderer.setSeriesPaint(0, Color.yellow);
		// areaRenderer.setSeriesPaint(1, Color.red);
		// Einstellung des Renderers, damit dessen Linien darunter gefüllt
		// werden
		// areaRenderer.setUseFillPaint(true);
		// Hinzufügen des Renderes im Plot an der Stelle 1 zum Dataset 2
		// plot.setRenderer(1, areaRenderer);
		// ///////////////////////////////////////////////

		// Durchsichtigkeit und Farbstärke einstellen
		plot.setForegroundAlpha(0.5f);

		// ////////////////////////////////////////////////
		// SS13 ==> Start IMMER bei 0 auf der X-Achse!
		xax.setRange(0, xax.getUpperBound());

		// ////////////////////////////////////////////////
		// SS13 ==> Für die Area-Darstellung mit der XYPolygonAnnotation
		// müssen die Punkte im oder gegen den Uhrzeigersinn im
		// Array angeordnet sein.
		// Wenn mehr als 2 Werte im Array vorhanden, soll diese
		// sortiert werden
		if (datenPolygon.length > 5) {
			datenPolygon = sortierePolygonKoordinaten(datenPolygon);
		}

		// System.out.println("BEIDE SORTIEREN");

		if (datenPolygon2.length > 5) {
			datenPolygon2 = sortierePolygonKoordinaten(datenPolygon2);
		}

		// SS 13 ==> Area-Annotations dem plot hinzufügen ==> Plotten der Lösungsräume
		try {
			XYPolygonAnnotation polygone = new XYPolygonAnnotation(
					datenPolygon, null, Color.green, Color.green);
			polygone.setToolTipText("Polygon 1");
			plot.addAnnotation(polygone);
		} catch (IllegalArgumentException e) {
			System.err.println("Poly1" + e);
		}

		try {
			XYPolygonAnnotation polygone2 = new XYPolygonAnnotation(
					datenPolygon2, null, Color.green, Color.green);
			polygone2.setToolTipText("Polygon 2");
			plot.addAnnotation(polygone2);
		} catch (IllegalArgumentException e) {
			System.err.println("Poly2" + e);
		}

		chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		// ///////////////////////////////////
		// SS13 ==> Jetzt in Tabs ==> Panels werden immer bei jedem Update();
		// neu erstellt und geadded
		// ==> Immer erst alles entfernen und wieder hinzfügen
		removeAll();

		chartPanel = new ChartPanel(chart);
		chartPanel.setSize(new java.awt.Dimension(500, 270));

		// ///////////////////////////////////////////////
		// SS13 ==> gesamtesPanel fällt weg!
		// gesamtesPanel = new JPanel(new BorderLayout());

		unteresPanel = new JPanel();

		weiterButton = new JButton("Weiter");
		weiterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (visualierungsObjekte.size() > 1) {
					update();
				} else {
					letzteIteration = true;
					update();
				}
			}
		});

		// //////////////////////////////////////////
		// SS13 --> Zusätzlicher Button um die Lösungsraumkoordinaten
		// anzuzeigen!
		polygonKoordinaten = new JButton("Lösungsraum-Koordinaten");
		polygonKoordinaten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ausgabePunktePolygone();
			}
		});
		
		// Abfrage um den "Weiter"-Button unten bei der letzten Iteration
		// nicht mehr einzufügen.
		if (visualierungsObjekte.size() <= 1) {
			letzteIteration = true;
		}

		if (letzteIteration == false) {
			unteresPanel.add(weiterButton);
			unteresPanel.add(polygonKoordinaten);
			add(unteresPanel, BorderLayout.SOUTH, 0);
		} else {
			letzteIteration = false;
			infoDialog = -1;
			unteresPanel.add(polygonKoordinaten);
			add(unteresPanel, BorderLayout.SOUTH, 0);
		}

		add(chartPanel, BorderLayout.CENTER, 0);

		alleErgebnisVektoren.remove(0);
		visualierungsObjekte.remove(0);

		if (infoDialog >= 0) {
			if (infoDialog == 0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Die Visualisierung der Branchingschritte wird \n nicht korrekt bzw. unvollständig dargestellt.",
								"Warnung !", JOptionPane.PLAIN_MESSAGE, null);
				infoDialog = -1;
			} else if (infoDialog == 1) {
				JOptionPane
						.showMessageDialog(
								null,
								"Die Visualisierung des Lösungsraums \n wird nicht korrekt dargestellt.",
								"Warnung !", JOptionPane.PLAIN_MESSAGE, null);
				infoDialog = -1;
			}
		}
	}

	// ////////////////////////////////////////////////
	// SS13 ==> Methode zur Sortierung
	// Anhand des ersten Punktes im Array werden die Steigung
	// und die Distanz zu den nächsten berechnet, anschließend
	// wird über ein Bubble-Sort anhand der Steigung
	// aufsteigend sortiert.
	public double[] sortierePolygonKoordinaten(double[] polygon) {
		int anzahlWerte = polygon.length;
		// Zweidimensionales DoubleArray 4 Werten an zweiter Stelle
		// [0] = X, [1] = Y, später: [2] = Steigung, [3] = Distanz
		double[][] SortPolygon = new double[anzahlWerte / 2][4];
		int k = 0;
		// Werte werden in ein 2-Dimensionales Array geschrieben. 1 Platz = 1
		// Punkt mit X und Y (Stelle 0 = X, Stelle 1 = Y)
		for (int i = 0; i < anzahlWerte / 2; i++) {
			SortPolygon[i][0] = polygon[k];
			SortPolygon[i][1] = polygon[k + 1];
			k = k + 2;
		}

		// Ausgabe dieser Punkte zum Test
		/*
		 * for (int z = 0; z < listSortPolygon.length; z++) {
		 * System.out.println("Punkt: (" + listSortPolygon[z][0] + "/" +
		 * listSortPolygon[z][1] + ")"); }
		 */

		// Sortiertes Array ==> Methode simple_polygon berechnet ausgegangen vom
		// 1. Punkt die Steigung und Distanz
		// Zurück kommt die berechnete Rückgabe & schon sortiert.
		double[][] sortedPolygon;
		sortedPolygon = simple_polygon(SortPolygon, 0, (anzahlWerte / 2) - 1);

		// System.out.println("---------SORTIERE----------");

		// Ausgabe dieser Werte
		/*
		 * for (int i = 0; i < sortedPolygon.length; i++) { System.out
		 * .println("Punkt: (" + sortedPolygon[i][0] + "/" + sortedPolygon[i][1]
		 * + "), Steigung: " + sortedPolygon[i][2] + ", Distanz: " +
		 * sortedPolygon[i][3]); }
		 */

		// Zweidimensionales Array zu eindimensionalem Array umschreiben
		double[] sortedPolygonRueckgabe = new double[anzahlWerte];
		int j = 0;
		for (int m = 0; m < sortedPolygon.length; m++) {
			sortedPolygonRueckgabe[j] = sortedPolygon[m][0];
			sortedPolygonRueckgabe[j + 1] = sortedPolygon[m][1];
			j = j + 2;
		}
		// Rückgabe des sortiertenPolygons
		return sortedPolygonRueckgabe;
	}

	// ////////////////////////////////////////////////
	// SS13 ==> Methode zur Berechnung der Steigung und
	// Distanz der Punkte ausgegangen vom ersten Punkt
	// im Array
	// Quelle und Erklärung:
	// http://www2.cs.uni-paderborn.de/cs/ag-monien/PERSONAL/OBELIX/Lehre/EffAlg/WS02/0502a.pdf
	// Umgeschriebener C++ Code
	public double[][] simple_polygon(double[][] p, int start, int end) {
		double xmin = Double.POSITIVE_INFINITY;
		double ymin = Double.POSITIVE_INFINITY;
		double dx;
		double dy;
		int imin = 0;
		for (int i = start; i <= end; i++) { // find extreme point
			if ((p[i][0] < xmin) || (p[i][0] == xmin && p[i][1] < ymin)) {
				xmin = p[i][0];
				ymin = p[i][1];
				imin = i;
			}
		}
		for (int i = start; i <= end; i++) { // calculate slopes and distances
												// for all points
			if (i != imin) {
				dx = p[i][0] - xmin;
				dy = p[i][1] - ymin;
				if (dx != 0) {
					p[i][2] = dy / dx;
				} else {
					// p[][2] == slope (Steigung)
					p[i][2] = Double.POSITIVE_INFINITY;
					// p[][2] == Distanz
					p[i][3] = Math.sqrt(dx * dx + dy * dy);
				}
			} else {
				p[i][2] = -Double.POSITIVE_INFINITY;
				p[i][3] = 0;
			}

		}
		// Aufruf des Quicksorts für den slope (Steigung)
		// Für spätere Optimierungen bei dem Hinzufügen von mehreren
		// Restriktionen, kann hier noch bei Überschneidungen
		// der Steigung ein zweites Sortierkriterium anhand der,
		// Distanz hinzugefügt werden --> So wird diese Problem behoben
		// In den bisherigen Berechnungen wird dies noch nicht benötig.
		p = qsort(p, start, end);

		return p;
	}

	// ////////////////////////////////////////////////
	// SS13 ==> Methode zur Sortierung der Koordinatenpunkte
	// anhand der Steigung (ArrayPlatz [2])
	public double[][] qsort(double[][] array, int le, int ri) {
		int lo = le;
		int hi = ri;

		if (hi > lo) {
			double mid = array[(lo + hi) / 2][2];
			while (lo <= hi) {
				// Erstes Element suchen, das größer oder gleich dem
				// Pivotelement ist, beginnend vom linken Index
				while (lo < ri && array[lo][2] < mid)
					++lo;

				// Element suchen, das kleiner oder gleich dem
				// Pivotelement ist, beginnend vom rechten Index
				while (hi > le && array[hi][2] > mid)
					--hi;

				// Wenn Indexe nicht gekreuzt --> Inhalte vertauschen
				if (lo <= hi) {
					// swap!!!
					double[] tmp = array[lo];
					array[lo] = array[hi];
					array[hi] = tmp;
					++lo;
					--hi;
				}
			}
			// Linke Partition sortieren
			if (le < hi) {
				qsort(array, le, hi);
			}

			// Rechte Partition sortieren
			if (lo < ri) {
				qsort(array, lo, ri);
			}
		}
		return array;
	}

	// ////////////////////////////////////////////////
	// SS13 --> Ausgabe der Polygone in MessageBox!
	public void ausgabePunktePolygone() {
		String txtAusgabePolygon1 = "Polygon 1: (x/y)\n";
		String txtAusgabePolygon2 = "\n\nPolygon 2: (x/y)\n";
		int k = 0;
		if (datenPolygon.length > 5) {
			for (int i = 0; i < datenPolygon.length / 2; i++) {
				txtAusgabePolygon1 = txtAusgabePolygon1 + "Punkt " + (i + 1)
						+ ": (" + datenPolygon[k] + "/" + datenPolygon[k + 1]
						+ ") \n";
				k = k + 2;
			}
		} else {
			txtAusgabePolygon1 = txtAusgabePolygon1 + "Keine Punkte vorhanden.";
		}

		if (datenPolygon2.length > 5) {
			k = 0;
			for (int i = 0; i < datenPolygon2.length / 2; i++) {
				txtAusgabePolygon2 = txtAusgabePolygon2 + "Punkt " + (i + 1)
						+ ": (" + datenPolygon2[k] + "/" + datenPolygon2[k + 1]
						+ ") \n";
				k = k + 2;
			}
		} else {
			txtAusgabePolygon2 = txtAusgabePolygon2 + "Keine Punkte vorhanden.";
		}
		JOptionPane.showMessageDialog(null,
				(txtAusgabePolygon1 + txtAusgabePolygon2), "Lösungsräume", 1);
	}

	// ////////////////////////////////////////////////

	// ergebnisVektoren kann ein oder zwei Elemente besitzen, je nachdem, ob ein
	// oder zwei Blätter des
	// Trees visualisiert werden sollen
	// zusaetzlicheRestriktionen kann 0 - 3 Elemente haben (nämlich 0 bei der
	// Visualiserung des roots,
	// in der nächsten Stufe 2 und dann immer 3, nämlich die beiden aktuellen
	// und die Restriktion eins drüber)
	public Vector berechneKoordinaten(Vector ergebnisVektoren,
			Vector zusaetzlicheRestriktionen, matrix eingabeMatrix) {
		// Initialisiere alle Daten mit null
		zfGerade = null;
		zfGerade2 = null;
		rGeraden = null;
		loesungsPolygon = null;
		loesungsPolygon2 = null;

		if (ergebnisVektoren.size() == 0)
			return new Vector(0);

		anzahlEingabeRestriktionen = 2;
		// Zielfunktionsgerade berechnen
		// ZF-Wert aus ergebnisArray holen
		String checkFeasibility = "ERROR";
		if (ergebnisVektoren.size() > 0) {
			checkFeasibility = ((Vector) ergebnisVektoren.get(0)).get(0)
					.toString();
			if (!checkFeasibility.equals("ERROR"))
				zfWert = Float.parseFloat(checkFeasibility);
		}

		if (ergebnisVektoren.size() > 1)
			zfWert2 = Float.parseFloat(((Vector) ergebnisVektoren.get(1))
					.get(0).toString());

		// Koeffizienten der x-Werte aus eingabeMatrix holen, dabei aufpassen,
		// dass b-Vektor und Relation nicht mitgeholt werden (-2)
		// Anmerkung: es gibt immer nur zwei Koeffizienten: x1 und x2
		zfKoeffizienten = new DakinKoordinate((new Float(
				eingabeMatrix.getValueAt(0, 0)).floatValue()), (new Float(
				eingabeMatrix.getValueAt(0, 1)).floatValue()));

		// Schnittpunkte der ZF-Gerade mit den beiden Achsen berechnen
		zfGerade = new DakinKoordinate[2];
		zfGerade[0] = new DakinKoordinate(zfWert / zfKoeffizienten.x1(), 0);
		zfGerade[1] = new DakinKoordinate(0, zfWert / zfKoeffizienten.x2());

		if (ergebnisVektoren.size() > 1) {
			zfGerade2 = new DakinKoordinate[2];
			zfGerade2[0] = new DakinKoordinate(zfWert2 / zfKoeffizienten.x1(),
					0);
			zfGerade2[1] = new DakinKoordinate(0, zfWert2
					/ zfKoeffizienten.x2());
		}

		// bVektor anlegen für alle Restriktionen
		bVektor = new Float[anzahlEingabeRestriktionen];
		// Array für Koeffizienten aller Restriktionen anlegen
		rKoeffizienten = new DakinKoordinate[anzahlEingabeRestriktionen];
		// Schleife über alle Restriktionen
		for (int rest = 1; rest < anzahlEingabeRestriktionen + 1; rest++) {
			// b-Vektor-Wert aus eingabeMatrix holen
			bVektor[rest - 1] = new Float(eingabeMatrix.getValueAt(rest,
					eingabeMatrix.getNumCols() - 1));
			// Koeffizienten der x-Werte aus eingabeMatrix holen
			rKoeffizienten[rest - 1] = new DakinKoordinate((new Float(
					eingabeMatrix.getValueAt(rest, 0)).floatValue()),
					(new Float(eingabeMatrix.getValueAt(rest, 1)).floatValue()));
		}
		// Schnittpunkte der Restriktionen mit den beiden Achsen berechnen
		rGeraden = new DakinKoordinate[anzahlEingabeRestriktionen * 2];
		int schnittpunkt = 0;
		for (int rest = 0; rest < anzahlEingabeRestriktionen; rest++) {
			rGeraden[schnittpunkt] = new DakinKoordinate(
					(bVektor[rest]).floatValue() / rKoeffizienten[rest].x1(), 0);
			schnittpunkt++;
			rGeraden[schnittpunkt] = new DakinKoordinate(0,
					(bVektor[rest]).floatValue() / rKoeffizienten[rest].x2());
			schnittpunkt++;
		}

		// Schnittpunkt der beiden Restriktionsgeraden berechnen
		restriktionenSchnitt = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue());

		maxX = Float.NEGATIVE_INFINITY;
		maxY = Float.NEGATIVE_INFINITY;
		// Finde Range-Bereich
		if (zfGerade != null) {
			for (int i = 0; i < zfGerade.length; i++) {
				if (zfGerade[i].x1() > maxX)
					maxX = zfGerade[i].x1();
				if (zfGerade[i].x2() > maxY)
					maxY = zfGerade[i].x2();
			}
		}
		if (zfGerade2 != null) {
			for (int i = 0; i < zfGerade2.length; i++) {
				if (zfGerade2[i].x1() > maxX)
					maxX = zfGerade2[i].x1();
				if (zfGerade2[i].x2() > maxY)
					maxY = zfGerade2[i].x2();
			}
		}
		if (rGeraden != null) {
			for (int i = 0; i < rGeraden.length; i++) {
				if (rGeraden[i].x1() > maxX)
					maxX = rGeraden[i].x1();
				if (rGeraden[i].x2() > maxY)
					maxY = rGeraden[i].x2();
			}
		}
		if (loesungsPolygon != null) {
			for (int i = 0; i < loesungsPolygon.length; i++) {
				if (loesungsPolygon[i].x1() > maxX)
					maxX = loesungsPolygon[i].x1();
				if (loesungsPolygon[i].x2() > maxY)
					maxY = loesungsPolygon[i].x2();
			}
		}
		if (loesungsPolygon2 != null) {
			for (int i = 0; i < loesungsPolygon2.length; i++) {
				if (loesungsPolygon2[i].x1() > maxX)
					maxX = loesungsPolygon2[i].x1();
				if (loesungsPolygon2[i].x2() > maxY)
					maxY = loesungsPolygon2[i].x2();
			}
		}

		// Lösungsraum berechnen
		if (!checkFeasibility.equals("ERROR"))
			berechneLoesungsraumPolygon(ergebnisVektoren,
					zusaetzlicheRestriktionen, eingabeMatrix);
		else {
			loesungsPolygon = new DakinKoordinate[0];
			loesungsPolygon2 = new DakinKoordinate[0];
		}

		// Prüfen, ob Restriktionsgeraden im negativen Bereich dargestellt
		// werden
		boolean streckeGerade = false;
		for (int rest = 0; rest < rGeraden.length; rest++) {
			if (rGeraden[rest].x1() < 0 || rGeraden[rest].x2() < 0) {
				streckeGerade = true;
			}
		}

		// Ändere x-Werte im negativen Bereich auf positiven Bereich
		int index = 0;
		if (streckeGerade) {
			for (int rest = 0; rest < anzahlEingabeRestriktionen; rest++) {
				if (rGeraden[index].x1() < 0) {

					DakinKoordinate neueKoordinate = schneideGeraden(
							rKoeffizienten[rest].x1(),
							rKoeffizienten[rest].x2(),
							bVektor[rest].floatValue(), 1, 0, maxX);

					rGeraden[index].setx1(neueKoordinate.x1());
					rGeraden[index].setx2(neueKoordinate.x2());
				}
				index++;
				if (rGeraden[index].x1() < 0) {
					DakinKoordinate neueKoordinate = schneideGeraden(
							rKoeffizienten[rest].x1(),
							rKoeffizienten[rest].x2(),
							bVektor[rest].floatValue(), 1, 0, maxX);

					rGeraden[index].setx1(neueKoordinate.x1());
					rGeraden[index].setx2(neueKoordinate.x2());
				}
				index++;
			}

			// Ändere y-Werte im negativen Bereich auf positiven Bereich
			index = 0;
			for (int rest = 0; rest < anzahlEingabeRestriktionen; rest++) {
				if (rGeraden[index].x2() < 0) {

					DakinKoordinate neueKoordinate = schneideGeraden(
							rKoeffizienten[rest].x1(),
							rKoeffizienten[rest].x2(),
							bVektor[rest].floatValue(), 0, 1, maxY);

					rGeraden[index].setx1(neueKoordinate.x1());
					rGeraden[index].setx2(neueKoordinate.x2());
				}
				index++;
				if (rGeraden[index].x2() < 0) {
					DakinKoordinate neueKoordinate = schneideGeraden(
							rKoeffizienten[rest].x1(),
							rKoeffizienten[rest].x2(),
							bVektor[rest].floatValue(), 0, 1, maxY);

					rGeraden[index].setx1(neueKoordinate.x1());
					rGeraden[index].setx2(neueKoordinate.x2());
				}
				index++;
			}
		}

		// Rückgabewerte für Visualisierung
		Vector rueckgabewert = new Vector();
		rueckgabewert.add(zfGerade);
		rueckgabewert.add(zfGerade2);
		rueckgabewert.add(rGeraden);
		rueckgabewert.add(loesungsPolygon);
		rueckgabewert.add(loesungsPolygon2);
		return rueckgabewert;
	}

	public void berechneLoesungsraumPolygon(Vector ergebnisVektoren,
			Vector zusaetzlicheRestriktionen, matrix eingabeMatrix) {
		String relation;
		boolean relationKleiner = false;
		boolean relationGroesser = false;
		sortierePoly = true;

		// Finde heraus, von welchem Typ die 2 EingabeRelationen sind
		for (int row = 1; row < 3; row++) {
			relation = eingabeMatrix.getValueAt(row,
					eingabeMatrix.getNumCols() - 2);
			if (relation.equals("<="))
				relationKleiner = true;
			else
				relationGroesser = true;
		}

		/* 1. Fall: beide Restriktionen sind <= */
		if (relationKleiner && !relationGroesser) {
			/* Berechne Standardpolygon (in jedem Fall) */
			berechneKleinerGleichPolygon(eingabeMatrix);
			/* BranchingSituation bestimmen */
			if (zusaetzlicheRestriktionen != null) {
				berechneLoesungsraumEinschraenkung(zusaetzlicheRestriktionen);
			} else {
				loesungsPolygon2 = new DakinKoordinate[0];
			}
		}
		/* 2. Fall: beide Restriktionen sind >= */
		else if (!relationKleiner && relationGroesser) {
			berechneGroesserGleichPolygon(eingabeMatrix);
			if (zusaetzlicheRestriktionen != null) {
				berechneLoesungsraumEinschraenkung(zusaetzlicheRestriktionen);
			} else {
				loesungsPolygon2 = new DakinKoordinate[0];
			}
		}
		/* 3. Fall: eine Restriktion ist <=, die andere >= */
		else {
			berechneGemischteRelationPolygon(eingabeMatrix,
					zusaetzlicheRestriktionen);
			if (zusaetzlicheRestriktionen != null) {
				berechneLoesungsraumEinschraenkung(zusaetzlicheRestriktionen);
			} else {
				loesungsPolygon2 = new DakinKoordinate[0];
			}
		}

		if (sortierePoly) {
			sortierePolygon(loesungsPolygon);
		}

	}

	public void berechneLoesungsraumEinschraenkung(
			Vector zusaetzlicheRestriktionen) {
		sortiereZusaetzlicheRestriktionen(zusaetzlicheRestriktionen);
		// neuerPolygon1 wird nach der Berechnung in loesungsPolygon kopiert
		// und neuerPolygon2 in loesungsPolygon2
		DakinKoordinate[] neuerPolygon1 = null;
		DakinKoordinate[] neuerPolygon2 = null;

		// tmpPolygon zwischenspeichert pro Restriktionsiteration den neuen
		// Polygon,
		// bevor er entweder neuerPolygon oder loesungsPolygon2 zugewiesen wird
		DakinKoordinate[] tmpPolygon = null;
		DakinKoordinate[] tmpPolygon2 = new DakinKoordinate[0];
		boolean kopiereNeuenPunkt;
		// wird auf true gesetzt, sobald die erste Parallele zur x1-Achse
		// auftaucht
		boolean paralleleZuX2 = false;
		int zweiPolygone = 0;
		int neuerPolyIndex = 0;
		int neuerPolyIndex2 = 0;
		boolean unendlich;
		// Schleife über die zusätzlichen Restriktionen, um diese nacheinander
		// zu
		// behandeln
		for (int rest = 0; rest < zusaetzlicheRestriktionen.size(); rest++) {
			// Initialisiere tmpPolygon
			tmpPolygon = new DakinKoordinate[0];
			// Herausfinden, um was für eine Art von Restriktion es sich
			// handelt.
			// Dabei gibt es 4 Kategorien: Parallele zur x1-Achse und <=;
			// Parallele
			// zur x1-Achse und >=; Parallele zur x2-Achse und <=; Parallele zur
			// x2-Achse und >=.
			int x1 = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(rest)).get(0).toString());
			int x2 = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(rest)).get(1).toString());
			String relation = ((Vector) zusaetzlicheRestriktionen.get(rest))
					.get(2).toString();
			int bWert = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(rest)).get(3).toString());
			int i;
			int j;
			int k;
			int l;
			int m;
			DakinKoordinate neuerPolyPunkt = null;
			DakinKoordinate neuerPolyPunkt1 = null;
			DakinKoordinate neuerPolyPunkt2 = null;
			DakinKoordinate neuerPolyPunkt3 = null;

			if (x1 == 1 && x2 == 0) { // es handelt sich um eine Parallele zur
										// x2-Achse
				paralleleZuX2 = true;
				// Sortiere Loesungspolygon nach x1-Werten
				sortierePolygon(loesungsPolygon);

				// Schneide zusätzliche Restriktion mit den Umrissgeraden des
				// Polygons

				for (i = 1; i <= loesungsPolygon.length; i++) {
					if (i == loesungsPolygon.length) {
						neuerPolyPunkt = schneideStreckeMitAchsenParallele(
								loesungsPolygon[0],
								loesungsPolygon[loesungsPolygon.length - 1],
								x1, x2, bWert);
					} else {
						neuerPolyPunkt = schneideStreckeMitAchsenParallele(
								loesungsPolygon[i - 1], loesungsPolygon[i], x1,
								x2, bWert);
					}
					if (neuerPolyPunkt != null) {
						for (j = i + 1; j <= loesungsPolygon.length; j++) {
							if (j == loesungsPolygon.length) {
								neuerPolyPunkt1 = schneideStreckeMitAchsenParallele(
										loesungsPolygon[0],
										loesungsPolygon[loesungsPolygon.length - 1],
										x1, x2, bWert);
							} else {
								neuerPolyPunkt1 = schneideStreckeMitAchsenParallele(
										loesungsPolygon[j - 1],
										loesungsPolygon[j], x1, x2, bWert);
							}
							if (neuerPolyPunkt1 != null)
								break;
						}
						break;
					}
				}

				// Wenn kein Schnittpunkt des Polygons mit der zusätzlichen
				// Restriktion
				// gefunden wurde, mach weiter mit der nächsten Iteration
				if (neuerPolyPunkt == null && neuerPolyPunkt1 == null)
					continue;

				if (relation.equals("<=")) {
					// Kopiere nun alle Punkte aus loesungsPolygon in den
					// neuerPolygon,
					// die einen x1-Wert kleiner gleich bWert haben, der letzte
					// Punkt ist der neuerPolyPunkt
					neuerPolyIndex = 0;
					for (j = 0; j < loesungsPolygon.length; j++) {
						if (loesungsPolygon[j].x1() <= bWert) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = loesungsPolygon[j];
							neuerPolyIndex++;
						}
					}
					// Kopiere neuerPolyPunkt nur, wenn es den gleichen nicht
					// schon im
					// tmpPolygon gibt
					if (neuerPolyPunkt != null) {
						kopiereNeuenPunkt = true;
						for (i = 0; i < tmpPolygon.length; i++) {
							if (tmpPolygon[i].x1() == neuerPolyPunkt.x1()
									&& tmpPolygon[i].x2() == neuerPolyPunkt
											.x2()) {
								kopiereNeuenPunkt = false;
								break;
							}
						}
						if (kopiereNeuenPunkt) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = neuerPolyPunkt;
							neuerPolyIndex++;
						}
					}

					// Kopiere neuerPolyPunkt1 nur, wenn es den gleichen nicht
					// schon im
					// tmpPolygon gibt
					if (neuerPolyPunkt1 != null) {
						kopiereNeuenPunkt = true;
						for (i = 0; i < tmpPolygon.length; i++) {
							if (tmpPolygon[i].x1() == neuerPolyPunkt1.x1()
									&& tmpPolygon[i].x2() == neuerPolyPunkt1
											.x2()) {
								kopiereNeuenPunkt = false;
								break;
							}
						}
						if (kopiereNeuenPunkt) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = neuerPolyPunkt1;
							neuerPolyIndex++;
						}
					}

				} else { // relation == >=
					// Kopiere nun alle Punkte aus loesungsPolygon in den
					// neuerPolygon,
					// die einen x1-Wert grösser gleich bWert haben, der letzte
					// Punkt ist der neuerPolyPunkt
					neuerPolyIndex = 0;
					for (j = 0; j < loesungsPolygon.length; j++) {
						if (loesungsPolygon[j].x1() >= bWert) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = loesungsPolygon[j];
							neuerPolyIndex++;
						}
					}
					// Kopiere neuerPolyPunkt nur, wenn es den gleichen nicht
					// schon im
					// tmpPolygon gibt
					if (neuerPolyPunkt != null) {
						kopiereNeuenPunkt = true;
						for (i = 0; i < tmpPolygon.length; i++) {
							if (tmpPolygon[i].x1() == neuerPolyPunkt.x1()
									&& tmpPolygon[i].x2() == neuerPolyPunkt
											.x2()) {
								kopiereNeuenPunkt = false;
								break;
							}
						}
						if (kopiereNeuenPunkt) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = neuerPolyPunkt;
							neuerPolyIndex++;
						}
					}
					// Kopiere neuerPolyPunkt1 nur, wenn es den gleichen nicht
					// schon im
					// tmpPolygon gibt
					if (neuerPolyPunkt1 != null) {
						kopiereNeuenPunkt = true;
						for (i = 0; i < tmpPolygon.length; i++) {
							if (tmpPolygon[i].x1() == neuerPolyPunkt1.x1()
									&& tmpPolygon[i].x2() == neuerPolyPunkt1
											.x2()) {
								kopiereNeuenPunkt = false;
								break;
							}
						}
						if (kopiereNeuenPunkt) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = neuerPolyPunkt1;
							neuerPolyIndex++;
						}
					}
				}

				// Kopiere nun tmpPolygon in den noch freien neuenPolygon
				if (neuerPolygon1 == null) {
					neuerPolygon1 = kopiereArray(tmpPolygon, neuerPolygon1);
				} else { // neuerPolygon2 ist noch frei
					neuerPolygon2 = kopiereArray(tmpPolygon, neuerPolygon2);
				}
			} else if (x1 == 0 && x2 == 1) { // es handelt sich um eine
												// Parallele zur x1-Achse
				if (!paralleleZuX2 || neuerPolygon1 == null) {
					neuerPolygon1 = kopiereArray(loesungsPolygon, neuerPolygon1);
				}
				// Nun müssen die Parallelen zur x1-Achse mit den bisher (aus
				// den Parallelen
				// zur x2-Achse) erzeugten Polygonen geschnitten werden
				// Sortiere Loesungspolygone nach x2-Werten
				if (sortierePoly) {
					if (neuerPolygon1 != null) {
						sortierePolygon(neuerPolygon1);
					}
				}
				if (zweiPolygone == 0) {
					if (neuerPolygon2 != null) {
						sortierePolygon(neuerPolygon2);
						zweiPolygone = 2;
					} else
						zweiPolygone = 1;
				}

				// Schneide zusätzliche Restriktion mit den Umrissgeraden des
				// Polygons
				for (i = 1; i <= neuerPolygon1.length; i++) {
					if (i == neuerPolygon1.length) {
						neuerPolyPunkt = schneideStreckeMitAchsenParallele(
								neuerPolygon1[0],
								neuerPolygon1[neuerPolygon1.length - 1], x1,
								x2, bWert);
					} else {
						neuerPolyPunkt = schneideStreckeMitAchsenParallele(
								neuerPolygon1[i - 1], neuerPolygon1[i], x1, x2,
								bWert);
					}
					if (neuerPolyPunkt != null) {
						for (j = i + 1; j <= neuerPolygon1.length; j++) {
							if (j == neuerPolygon1.length) {
								neuerPolyPunkt1 = schneideStreckeMitAchsenParallele(
										neuerPolygon1[0],
										neuerPolygon1[neuerPolygon1.length - 1],
										x1, x2, bWert);
							} else {
								neuerPolyPunkt1 = schneideStreckeMitAchsenParallele(
										neuerPolygon1[j - 1], neuerPolygon1[j],
										x1, x2, bWert);
							}
							if (neuerPolyPunkt1 != null)
								break;
						}
						break;
					}
				}
				if (zweiPolygone == 2) {
					for (i = 1; i <= neuerPolygon2.length; i++) {
						if (i == neuerPolygon2.length) {
							neuerPolyPunkt2 = schneideStreckeMitAchsenParallele(
									neuerPolygon2[0],
									neuerPolygon2[neuerPolygon2.length - 1],
									x1, x2, bWert);
						} else {
							neuerPolyPunkt2 = schneideStreckeMitAchsenParallele(
									neuerPolygon2[i - 1], neuerPolygon2[i], x1,
									x2, bWert);
						}
						if (neuerPolyPunkt2 != null) {
							for (j = i + 1; j <= neuerPolygon2.length; j++) {
								if (j == neuerPolygon2.length) {
									neuerPolyPunkt3 = schneideStreckeMitAchsenParallele(
											neuerPolygon2[0],
											neuerPolygon2[neuerPolygon2.length - 1],
											x1, x2, bWert);
								} else {
									neuerPolyPunkt3 = schneideStreckeMitAchsenParallele(
											neuerPolygon2[j - 1],
											neuerPolygon2[j], x1, x2, bWert);
								}
								if (neuerPolyPunkt3 != null)
									break;
							}
							break;
						}
					}
				}

				// Wenn kein Schnittpunkt der Polygone mit der zusätzlichen
				// Restriktion
				// gefunden wurde, mach weiter mit der nächsten Iteration
				if (neuerPolyPunkt == null && neuerPolyPunkt1 == null
						&& neuerPolyPunkt2 == null && neuerPolyPunkt3 == null) {
					tmpPolygon = new DakinKoordinate[0];
					tmpPolygon = new DakinKoordinate[0];
					neuerPolygon2 = new DakinKoordinate[0];
					continue;
				}

				if (relation.equals("<=")) {
					// Kopiere nun alle Punkte aus neuerPolygon1 in den
					// tmpPolygon,
					// die einen x2-Wert kleiner gleich bWert haben
					neuerPolyIndex = 0;
					for (k = 0; k < neuerPolygon1.length; k++) {
						if (neuerPolygon1[k].x2() <= bWert) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = neuerPolygon1[k];
							neuerPolyIndex++;
						}
					}
					if (zweiPolygone == 2) {
						neuerPolyIndex2 = 0;
						for (k = 0; k < neuerPolygon2.length; k++) {
							if (neuerPolygon2[k].x2() <= bWert) {
								tmpPolygon2 = vergroessereArray(tmpPolygon2, 1);
								tmpPolygon2[neuerPolyIndex2] = neuerPolygon2[k];
								neuerPolyIndex2++;
							}
						}
					}
				} else { // relation == >=
					// Kopiere nun alle Punkte aus neuerPolygon1 in den
					// tmpPolygon,
					// die einen x2-Wert größer gleich bWert haben
					neuerPolyIndex = 0;
					for (k = 0; k < neuerPolygon1.length; k++) {
						if (neuerPolygon1[k].x2() >= bWert) {
							tmpPolygon = vergroessereArray(tmpPolygon, 1);
							tmpPolygon[neuerPolyIndex] = neuerPolygon1[k];
							neuerPolyIndex++;
						}
					}
					if (zweiPolygone == 2) {
						neuerPolyIndex2 = 0;
						for (k = 0; k < neuerPolygon2.length; k++) {
							if (neuerPolygon2[k].x2() >= bWert) {
								tmpPolygon2 = vergroessereArray(tmpPolygon2, 1);
								tmpPolygon2[neuerPolyIndex2] = neuerPolygon2[k];
								neuerPolyIndex2++;
							}
						}
					}
				}
				// der letzte Punkt ist der neuerPolyPunkt
				if (neuerPolyPunkt != null && neuerPolyPunkt1 != null) {
					kopiereNeuenPunkt = true;
					if (neuerPolyPunkt != null) {
						for (k = 0; k < tmpPolygon.length; k++) {
							if (tmpPolygon[k].x1() == neuerPolyPunkt.x1()
									&& tmpPolygon[k].x2() == neuerPolyPunkt
											.x2()) {
								kopiereNeuenPunkt = false;
								break;
							}
						}
					}
					if (kopiereNeuenPunkt) {
						tmpPolygon = vergroessereArray(tmpPolygon, 1);
						tmpPolygon[neuerPolyIndex] = neuerPolyPunkt;
						neuerPolyIndex++;
					}
					kopiereNeuenPunkt = true;
					if (neuerPolyPunkt1 != null) {
						for (k = 0; k < tmpPolygon.length; k++) {
							if (tmpPolygon[k].x1() == neuerPolyPunkt1.x1()
									&& tmpPolygon[k].x2() == neuerPolyPunkt1
											.x2()) {
								kopiereNeuenPunkt = false;
								break;
							}
						}
					}
					if (kopiereNeuenPunkt) {
						tmpPolygon = vergroessereArray(tmpPolygon, 1);
						tmpPolygon[neuerPolyIndex] = neuerPolyPunkt1;
						neuerPolyIndex++;
					}
				} else
					tmpPolygon = null;

				if (neuerPolyPunkt2 != null && neuerPolyPunkt3 != null) {
					kopiereNeuenPunkt = true;
					for (k = 0; k < tmpPolygon2.length; k++) {
						if (tmpPolygon2[k].x1() == neuerPolyPunkt2.x1()
								&& tmpPolygon2[k].x2() == neuerPolyPunkt2.x2()) {
							kopiereNeuenPunkt = false;
							break;
						}
					}
					if (kopiereNeuenPunkt) {
						tmpPolygon2 = vergroessereArray(tmpPolygon2, 1);
						tmpPolygon2[neuerPolyIndex2] = neuerPolyPunkt2;
						neuerPolyIndex2++;
					}
					kopiereNeuenPunkt = true;
					if (neuerPolyPunkt3 != null) {
						for (k = 0; k < tmpPolygon2.length; k++) {
							if (tmpPolygon2[k].x1() == neuerPolyPunkt3.x1()
									&& tmpPolygon2[k].x2() == neuerPolyPunkt3
											.x2()) {
								kopiereNeuenPunkt = false;
								break;
							}
						}
					}
					if (kopiereNeuenPunkt) {
						tmpPolygon2 = vergroessereArray(tmpPolygon2, 1);
						tmpPolygon2[neuerPolyIndex2] = neuerPolyPunkt3;
						neuerPolyIndex2++;
					}
				}
				if (neuerPolygon2 == null) {
					if (tmpPolygon != null)
						neuerPolygon2 = kopiereArray(tmpPolygon, neuerPolygon2);
				}
			}
		} // Ende: for (zusaetzlicheRestriktionen)

		if (zusaetzlicheRestriktionen.size() == 1) {
			if (neuerPolygon2 != null) {
				loesungsPolygon = kopiereArray(neuerPolygon2, loesungsPolygon);
			} else if (neuerPolygon1 != null) {
				loesungsPolygon = kopiereArray(neuerPolygon1, loesungsPolygon);
			} else if (tmpPolygon != null) {
				loesungsPolygon = kopiereArray(tmpPolygon, loesungsPolygon);
			} else {
				loesungsPolygon = null;
				System.out
						.println("Fehler 2 in Visualisiere.berechneLoesungsraumEinschraenkung");
			}
		} else if (zusaetzlicheRestriktionen.size() == 2) {
			int r1x1 = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(0)).get(0).toString());
			int r2x2 = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(1)).get(1).toString());
			// x1 & x2
			if (r1x1 == 1 && r2x2 == 1) {
				if (tmpPolygon != null)
					loesungsPolygon = kopiereArray(tmpPolygon, loesungsPolygon);
				else
					loesungsPolygon = new DakinKoordinate[0];
			}
			// x2 & x2
			else if (r1x1 == 0 && r2x2 == 1) {
				if (neuerPolygon1 != null)
					loesungsPolygon = kopiereArray(neuerPolygon2,
							loesungsPolygon);
				else
					loesungsPolygon = new DakinKoordinate[0];
				if (tmpPolygon != null)
					loesungsPolygon2 = kopiereArray(tmpPolygon,
							loesungsPolygon2);
			}
			// x1 & x1
			else {
				if (neuerPolygon1 != null)
					loesungsPolygon = kopiereArray(neuerPolygon1,
							loesungsPolygon);
				else
					loesungsPolygon = new DakinKoordinate[0];
				if (neuerPolygon2 != null)
					loesungsPolygon2 = kopiereArray(neuerPolygon2,
							loesungsPolygon2);
			}
		} else if (zusaetzlicheRestriktionen.size() == 3) {
			int r1x1 = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(0)).get(0).toString());
			int r2x1 = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(1)).get(0).toString());
			int r3x1 = Integer.parseInt(((Vector) zusaetzlicheRestriktionen
					.get(2)).get(0).toString());
			// x1, x2, x2
			if (r1x1 == 1 && r2x1 == 0 && r3x1 == 0) {
				if ((tmpPolygon != null && neuerPolygon2 != null)
						&& (tmpPolygon.length == 0 && neuerPolygon2.length == 0)
						&& neuerPolygon1 != null) {
					loesungsPolygon = kopiereArray(neuerPolygon1,
							loesungsPolygon);
				} else {
					if (tmpPolygon != null)
						loesungsPolygon = kopiereArray(tmpPolygon,
								loesungsPolygon);
					else
						loesungsPolygon = new DakinKoordinate[0];
					if (neuerPolygon2 != null)
						loesungsPolygon2 = kopiereArray(neuerPolygon2,
								loesungsPolygon2);
				}
			}
			// x1, x1, x2
			else if (r1x1 == 1 && r2x1 == 1 && r3x1 == 0) {
				if (tmpPolygon != null && tmpPolygon2 != null) {
					loesungsPolygon = kopiereArray(tmpPolygon, loesungsPolygon);
					loesungsPolygon2 = kopiereArray(tmpPolygon2,
							loesungsPolygon2);
				} else if (tmpPolygon != null && tmpPolygon2 == null
						&& neuerPolygon2 != null) {
					loesungsPolygon = kopiereArray(tmpPolygon, loesungsPolygon);
					loesungsPolygon2 = kopiereArray(neuerPolygon2,
							loesungsPolygon2);
				} else if (tmpPolygon == null && tmpPolygon2 != null
						&& neuerPolygon1 != null) {
					loesungsPolygon = kopiereArray(neuerPolygon1,
							loesungsPolygon);
					loesungsPolygon2 = kopiereArray(tmpPolygon2,
							loesungsPolygon2);
				} else {
					loesungsPolygon = new DakinKoordinate[0];
					System.out
							.println("Fehler 3 in Visualisiere.berechneLoesungsraumEinschraenkung");
				}
			}
		} else
			System.out
					.println("Fehler 4 in Visualisiere.berechneLoesungsraumEinschraenkung");

		if (sortierePoly) {
			if (loesungsPolygon != null)
				sortierePolygon(loesungsPolygon);
			if (loesungsPolygon2 != null)
				sortierePolygon(loesungsPolygon2);

		}

		return;
	}

	public DakinKoordinate maxKoordinate(DakinKoordinate p1,
			DakinKoordinate p2, int comparer) {
		DakinKoordinate max;
		if (comparer == 1) {
			if (p1.x1() > p2.x1()) {
				max = new DakinKoordinate(p1);
			} else {
				max = new DakinKoordinate(p2);
			}
		} else if (comparer == 2) {
			if (p1.x2() > p2.x2()) {
				max = new DakinKoordinate(p1);
			} else {
				max = new DakinKoordinate(p2);
			}
		} else {
			System.out.println("Fehler in TreeDakin.maxKoordinate");
			return null;
		}
		return max;
	}

	public DakinKoordinate minKoordinate(DakinKoordinate p1,
			DakinKoordinate p2, int comparer) {
		DakinKoordinate min;
		if (comparer == 1) {
			if (p1.x1() < p2.x1()) {
				min = new DakinKoordinate(p1);
			} else {
				min = new DakinKoordinate(p2);
			}
		} else if (comparer == 2) {
			if (p1.x2() < p2.x2()) {
				min = new DakinKoordinate(p1);
			} else {
				min = new DakinKoordinate(p2);
			}
		} else {
			System.out.println("Fehler in TreeDakin.minKoordinate");
			return null;
		}
		return min;
	}

	public DakinKoordinate minKoordinate(DakinKoordinate p1,
			DakinKoordinate p2, DakinKoordinate p3, int comparer) {
		DakinKoordinate min;
		DakinKoordinate tmpMin;
		tmpMin = minKoordinate(p1, p2, comparer);
		min = minKoordinate(tmpMin, p3, comparer);
		return min;
	}

	public DakinKoordinate minKoordinate(DakinKoordinate p1,
			DakinKoordinate p2, DakinKoordinate p3, int comparer, float minWert) {
		DakinKoordinate[] koordArray = new DakinKoordinate[3];
		koordArray[0] = new DakinKoordinate(p1);
		koordArray[1] = new DakinKoordinate(p2);
		koordArray[2] = new DakinKoordinate(p3);
		if (minIndex(koordArray[1], koordArray[0], comparer) == 1) {
			tauscheKoords(koordArray, 0, 1);
		}
		if (minIndex(koordArray[2], koordArray[0], comparer) == 1) {
			tauscheKoords(koordArray, 0, 2);
		}
		if (minIndex(koordArray[2], koordArray[1], comparer) == 1) {
			tauscheKoords(koordArray, 1, 2);
		}
		if (comparer == 1) {
			for (int i = 0; i < koordArray.length; i++) {
				if (koordArray[i].x1() >= minWert) {
					return koordArray[i];
				}
			}
		} else {
			for (int i = 0; i < koordArray.length; i++) {
				if (koordArray[i].x2() >= minWert) {
					return koordArray[i];
				}
			}
		}
		return null;

	}

	public void tauscheKoords(DakinKoordinate[] koordArray, int i1, int i2) {
		DakinKoordinate tmp;
		tmp = new DakinKoordinate(koordArray[i1]);
		koordArray[i1] = koordArray[i2];
		koordArray[i2] = tmp;
		return;
	}

	public void vectorSwap(Vector restriktionsVektor, int i1, int i2) {
		Vector tmp = new Vector();
		tmp.add(restriktionsVektor.get(i1));
		restriktionsVektor.setElementAt(restriktionsVektor.elementAt(i2), i1);
		restriktionsVektor.setElementAt(tmp.elementAt(0), i2);
		return;
	}

	public int minIndex(DakinKoordinate p1, DakinKoordinate p2, int comparer) {
		DakinKoordinate min;
		if (comparer == 1) {
			if (p1.x1() < p2.x1()) {
				return 1;
			} else {
				return 2;
			}
		} else if (comparer == 2) {
			if (p1.x2() < p2.x2()) {
				return 1;
			} else {
				return 2;
			}
		} else {
			System.out.println("Fehler in TreeDakin.minIndex");
			return -1;
		}
	}

	public DakinKoordinate schneideGeraden(float x11koeff, float x12koeff,
			float b1koeff, float x21koeff, float x22koeff, float b2koeff) {
		float gleichung1_b;
		float gleichung1_x2koeff;

		float x1_ungerundet;
		float x2_ungerundet;
		float x1_wert;
		float x2_wert;
		DakinKoordinate schnittpunkt;

		// Löse Gleichung 1 nach x11 auf:
		gleichung1_b = b1koeff / x11koeff;
		gleichung1_x2koeff = -(x12koeff / x11koeff);

		// Setze Ergebnis in Gleichung 2 ein:
		if ((b2koeff - (x21koeff * gleichung1_b)) == 0
				&& ((x21koeff * gleichung1_x2koeff) + x22koeff) == 0) {
			return null;
		}

		x2_ungerundet = (b2koeff - (x21koeff * gleichung1_b))
				/ ((x21koeff * gleichung1_x2koeff) + x22koeff);
		x2_wert = (float) (Math.round(x2_ungerundet * 100)) / 100;

		// Setze x2_wert in Gleichung 1 ein:
		x1_ungerundet = gleichung1_b + (gleichung1_x2koeff * x2_wert);
		x1_wert = (float) (Math.round(x1_ungerundet * 100)) / 100;

		schnittpunkt = new DakinKoordinate(x1_wert, x2_wert);
		return schnittpunkt;
	}

	public DakinKoordinate schneideStreckeMitAchsenParallele(
			DakinKoordinate streckeP1, DakinKoordinate streckeP2, int geradeX1,
			int geradeX2, int geradeBWert) {
		// Finde heraus, ob sich Gerade und Strecke ueberhaupt schneiden
		if (geradeX1 == 1) { // Parallele zur x2-Achse
			// Schauen, ob der b-Wert der Geraden zwischen den x1-Werten der
			// Strecke liegt
			if ((geradeBWert >= minKoordinate(streckeP1, streckeP2, 1).x1())
					&& (geradeBWert <= maxKoordinate(streckeP1, streckeP2, 1)
							.x1())) {
				if ((streckeP2.x1() == streckeP1.x1())
						&& (streckeP2.x2() == streckeP2.x2())
						&& geradeBWert == streckeP2.x1()) {
					return new DakinKoordinate(streckeP1);
				}
				// Schnittpunkt berechnen
				float faktor = (geradeBWert - streckeP1.x1())
						/ (streckeP2.x1() - streckeP1.x1());
				float x2_ungerundet = ((streckeP2.x2() - streckeP1.x2()) * faktor)
						+ streckeP1.x2();
				float x2Wert = (float) (Math.round(x2_ungerundet * 100)) / 100;
				return new DakinKoordinate((float) geradeBWert, x2Wert);
			}
		} else { // Parallele zur x1-Achse
			// Schauen, ob der b-Wert der Geraden zwischen den x2-Werten der
			// Strecke liegt
			if ((geradeBWert >= minKoordinate(streckeP1, streckeP2, 2).x2())
					&& (geradeBWert <= maxKoordinate(streckeP1, streckeP2, 2)
							.x2())) {
				if ((streckeP2.x2() == streckeP1.x2())
						&& (streckeP1.x1() == streckeP2.x1())
						&& (geradeBWert == streckeP2.x2())) {
					return new DakinKoordinate(streckeP1);
				}
				// Schnittpunkt berechnen
				float faktor = (geradeBWert - streckeP1.x2())
						/ (streckeP2.x2() - streckeP1.x2());
				float x1_ungerundet = ((streckeP2.x1() - streckeP1.x1()) * faktor)
						+ streckeP1.x1();
				float x1Wert = (float) (Math.round(x1_ungerundet * 100)) / 100;
				return new DakinKoordinate(x1Wert, (float) geradeBWert);
			}
		}
		// Es gibt keinen Schnittpunkt
		return null;
	}

	public DakinKoordinate sucheKleinstenXSchnitt(matrix eingabeMatrix) {
		// Minimumsuche: Den kleineren Schnittpunkt mit der x1-Achse finden
		DakinKoordinate p1 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 0, 1,
				0);

		DakinKoordinate p2 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 0, 1,
				0);

		// fange ab, dass Schnitt im ersten Quadranten liegt!
		if (p1.x1() >= 0 && p2.x1() >= 0) {
			return minKoordinate(p1, p2, 1);
		} else if (p1.x1() >= 0 && p2.x1() < 0) {
			return p1;
		} else if (p1.x1() < 0 && p2.x1() >= 0) {
			return p2;
		} else {
			return new DakinKoordinate(0, 0);
		}
	}

	public DakinKoordinate sucheKleinstenYSchnitt(matrix eingabeMatrix) {
		// Minimumsuche: Den kleineren Schnittpunkt mit der x2-Achse finden
		DakinKoordinate p1 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		// fange ab, dass Schnitt im ersten Quadranten liegt!
		if (p1.x2() >= 0 && p2.x2() >= 0) {
			return minKoordinate(p1, p2, 2);
		} else if (p1.x2() >= 0 && p2.x2() < 0) {
			return p1;
		} else if (p1.x2() < 0 && p2.x2() >= 0) {
			return p2;
		} else {
			return new DakinKoordinate(0, 0);
		}
	}

	public DakinKoordinate sucheKleinstenYSchnitt(matrix eingabeMatrix,
			float x1Wert) {
		// Minimumsuche: Den kleineren Schnittpunkt mit der x2-Achse finden
		DakinKoordinate p1 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				x1Wert);

		DakinKoordinate p2 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				x1Wert);

		// fange ab, dass Schnitt im ersten Quadranten liegt!
		if (p1.x2() >= 0 && p2.x2() >= 0) {
			return minKoordinate(p1, p2, 2);
		} else if (p2.x2() >= 0 && p2.x2() < 0) {
			return p1;
		} else if (p1.x2() < 0 && p2.x2() >= 0) {
			return p2;
		} else {
			return new DakinKoordinate(0, 0);
		}
	}

	public DakinKoordinate sucheGroesstenXSchnitt(matrix eingabeMatrix) {
		// Minimumsuche: Den kleineren Schnittpunkt mit der x1-Achse finden
		DakinKoordinate p1 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 0, 1,
				0);

		DakinKoordinate p2 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 0, 1,
				0);

		// fange ab, dass Schnitt im ersten Quadranten liegt!
		if (p1.x1() >= 0 && p2.x1() >= 0) {
			return maxKoordinate(p1, p2, 1);
		} else if (p1.x1() >= 0 && p2.x1() < 0) {
			return p1;
		} else if (p1.x1() < 0 && p2.x1() >= 0) {
			return p2;
		} else {
			return new DakinKoordinate(0, 0);
		}
	}

	public DakinKoordinate sucheGroesstenYSchnitt(matrix eingabeMatrix,
			float x1Wert) {
		// Minimumsuche: Den größeren Schnittpunkt mit der Parallelen zur
		// x2-Achse finden
		DakinKoordinate p1 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				x1Wert);

		DakinKoordinate p2 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				x1Wert);

		// fange ab, dass Schnitt im ersten Quadranten liegt!
		if (p1.x2() >= 0 && p2.x2() >= 0) {
			return maxKoordinate(p1, p2, 2);
		} else if (p2.x2() >= 0 && p2.x2() < 0) {
			return p1;
		} else if (p1.x2() < 0 && p2.x2() >= 0) {
			return p2;
		} else {
			return new DakinKoordinate(0, 0);
		}
	}

	public DakinKoordinate sucheGroesstenYSchnitt(matrix eingabeMatrix) {
		// Minimumsuche: Den kleineren Schnittpunkt mit der x2-Achse finden
		DakinKoordinate p1 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		// fange ab, dass Schnitt im ersten Quadranten liegt!
		if (p1.x2() >= 0 && p2.x2() >= 0) {
			return maxKoordinate(p1, p2, 2);
		} else if (p1.x2() >= 0 && p2.x2() < 0) {
			return p1;
		} else if (p1.x2() < 0 && p2.x2() >= 0) {
			return p2;
		} else {
			return new DakinKoordinate(0, 0);
		}
	}

	public int sucheGroesstenYSchnittGeradenIndex(matrix eingabeMatrix) {
		// Minimumsuche: Den kleineren Schnittpunkt mit der x2-Achse finden
		DakinKoordinate p1 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2 = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		// fange ab, dass Schnitt im ersten Quadranten liegt!
		if (p1.x2() >= 0 && p2.x2() >= 0) {
			if (p1.x2() > p2.x2()) {
				return 1;
			} else {
				return 2;
			}
		} else if (p1.x2() >= 0 && p2.x2() < 0) {
			return 1;
		} else if (p1.x2() < 0 && p2.x2() >= 0) {
			return 2;
		} else {
			return -1;
		}
	}

	public DakinKoordinate sucheRechtestenSchnitt(matrix eingabeMatrix) {
		DakinKoordinate rechtesterSchnitt = new DakinKoordinate(0, 0);

		// finde Schnitte mit x-Achse
		DakinKoordinate p1x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 0, 1,
				0);

		DakinKoordinate p2x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 0, 1,
				0);

		// finde Schnitte mit y-Achse
		DakinKoordinate p1y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		// suche positive Schnitte
		DakinKoordinate[] positiveSchnitte = new DakinKoordinate[0];
		int index = 0;
		if (p1x.x1() >= 0 && p1x.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p1x);
			index++;
		}
		if (p2x.x1() >= 0 && p2x.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p2x);
			index++;
		}
		if (p1y.x1() >= 0 && p1y.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p1y);
			index++;
		}
		if (p2y.x1() >= 0 && p2y.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p2y);
			index++;
		}

		// suche größten x-Wert der positiven Schnittpunkte
		float maxX = Float.NEGATIVE_INFINITY;
		DakinKoordinate[] gleicherXWertArray = new DakinKoordinate[0];
		index = 0;
		for (int i = 0; i < positiveSchnitte.length; i++) {
			if (positiveSchnitte[i].x1() > maxX) {
				gleicherXWertArray = vergroessereArray(gleicherXWertArray, 1);
				gleicherXWertArray[index] = new DakinKoordinate(
						positiveSchnitte[i]);
				index++;
				maxX = positiveSchnitte[i].x1();
			} else if (positiveSchnitte[i].x1() == maxX) {
				if (gleicherXWertArray.length > 0) {
					if (gleicherXWertArray[0].x1() != maxX) {
						gleicherXWertArray = new DakinKoordinate[0];
					}
				}
				gleicherXWertArray = vergroessereArray(gleicherXWertArray, 1);
				gleicherXWertArray[index] = positiveSchnitte[i];
				index++;
			}
		}

		if (gleicherXWertArray.length == 1) {
			return gleicherXWertArray[0];
		}

		// suche kleinsten y-Wert der gleichen X-Werte
		float minY = Float.MAX_VALUE;
		for (int i = 0; i < gleicherXWertArray.length; i++) {
			if (gleicherXWertArray[i].x2() < minY) {
				minY = gleicherXWertArray[i].x2();
				rechtesterSchnitt = new DakinKoordinate(gleicherXWertArray[i]);
			}
		}
		return rechtesterSchnitt;
	}

	public DakinKoordinate sucheLinkestenSchnitt(matrix eingabeMatrix) {
		DakinKoordinate linksterSchnitt = new DakinKoordinate(0, 0);

		// finde Schnitte mit x-Achse
		DakinKoordinate p1x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 0, 1,
				0);

		DakinKoordinate p2x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 0, 1,
				0);

		// finde Schnitte mit y-Achse
		DakinKoordinate p1y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		// suche positive Schnitte
		DakinKoordinate[] positiveSchnitte = new DakinKoordinate[0];
		int index = 0;
		if (p1x.x1() >= 0 && p1x.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p1x);
			index++;
		}
		if (p2x.x1() >= 0 && p2x.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p2x);
			index++;
		}
		if (p1y.x1() >= 0 && p1y.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p1y);
			index++;
		}
		if (p2y.x1() >= 0 && p2y.x2() >= 0) {
			positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
			positiveSchnitte[index] = new DakinKoordinate(p2y);
			index++;
		}

		// suche größten y-Wert der positiven Schnittpunkte
		float maxY = Float.NEGATIVE_INFINITY;
		DakinKoordinate[] gleicherYWertArray = new DakinKoordinate[0];
		index = 0;
		for (int i = 0; i < positiveSchnitte.length; i++) {
			if (positiveSchnitte[i].x2() > maxY) {
				gleicherYWertArray = vergroessereArray(gleicherYWertArray, 1);
				gleicherYWertArray[index] = new DakinKoordinate(
						positiveSchnitte[i]);
				index++;
				maxY = positiveSchnitte[i].x2();
			} else if (positiveSchnitte[i].x2() == maxY) {
				if (gleicherYWertArray.length > 0) {
					if (gleicherYWertArray[0].x2() != maxY) {
						gleicherYWertArray = new DakinKoordinate[0];
					}
				}
				gleicherYWertArray = vergroessereArray(gleicherYWertArray, 1);
				gleicherYWertArray[index] = positiveSchnitte[i];
				index++;
			}
		}

		if (gleicherYWertArray.length == 1) {
			return gleicherYWertArray[0];
		}

		// suche kleinsten x-Wert der gleichen Y-Werte
		float minX = Float.MAX_VALUE;
		for (int i = 0; i < gleicherYWertArray.length; i++) {
			if (gleicherYWertArray[i].x1() < minX) {
				minX = gleicherYWertArray[i].x1();
				linksterSchnitt = new DakinKoordinate(gleicherYWertArray[i]);
			}
		}
		return linksterSchnitt;
	}

	public DakinKoordinate sucheZweitLinkestenSchnitt(matrix eingabeMatrix,
			DakinKoordinate linksterSchnitt) {
		DakinKoordinate zweitLinksterSchnitt = new DakinKoordinate(0, 0);

		// finde Schnitte mit x-Achse
		DakinKoordinate p1x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 0, 1,
				0);

		DakinKoordinate p2x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 0, 1,
				0);

		// finde Schnitte mit y-Achse
		DakinKoordinate p1y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		// suche positive Schnitte
		DakinKoordinate[] positiveSchnitte = new DakinKoordinate[0];
		int index = 0;
		if (!((p1x.x1() == linksterSchnitt.x1()) && (p1x.x2() == linksterSchnitt
				.x2()))) {
			if (p1x.x1() >= 0 && p1x.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p1x);
				index++;
			}
		}
		if (!((p2x.x1() == linksterSchnitt.x1()) && (p2x.x2() == linksterSchnitt
				.x2()))) {
			if (p2x.x1() >= 0 && p2x.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p2x);
				index++;
			}
		}
		if (!((p1y.x1() == linksterSchnitt.x1()) && (p1y.x2() == linksterSchnitt
				.x2()))) {
			if (p1y.x1() >= 0 && p1y.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p1y);
				index++;
			}
		}
		if (!((p2y.x1() == linksterSchnitt.x1()) && (p2y.x2() == linksterSchnitt
				.x2()))) {
			if (p2y.x1() >= 0 && p2y.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p2y);
				index++;
			}
		}

		// suche größten y-Wert der positiven Schnittpunkte
		float maxY = Float.NEGATIVE_INFINITY;
		DakinKoordinate[] gleicherYWertArray = new DakinKoordinate[0];
		index = 0;
		for (int i = 0; i < positiveSchnitte.length; i++) {
			if (positiveSchnitte[i].x2() > maxY) {
				gleicherYWertArray = vergroessereArray(gleicherYWertArray, 1);
				gleicherYWertArray[index] = new DakinKoordinate(
						positiveSchnitte[i]);
				index++;
				maxY = positiveSchnitte[i].x2();
			} else if (positiveSchnitte[i].x2() == maxY) {
				if (gleicherYWertArray.length > 0) {
					if (gleicherYWertArray[0].x2() != maxY) {
						gleicherYWertArray = new DakinKoordinate[0];
					}
				}
				gleicherYWertArray = vergroessereArray(gleicherYWertArray, 1);
				gleicherYWertArray[index] = positiveSchnitte[i];
				index++;
			}
		}

		if (gleicherYWertArray.length == 1) {
			return gleicherYWertArray[0];
		}

		// suche kleinsten x-Wert der gleichen Y-Werte
		float minX = Float.MAX_VALUE;
		for (int i = 0; i < gleicherYWertArray.length; i++) {
			if (gleicherYWertArray[i].x1() < minX) {
				minX = gleicherYWertArray[i].x1();
				zweitLinksterSchnitt = new DakinKoordinate(
						gleicherYWertArray[i]);
			}
		}
		return zweitLinksterSchnitt;

	}

	public DakinKoordinate sucheZweitRechtestenSchnitt(matrix eingabeMatrix,
			DakinKoordinate rechtesterSchnitt) {
		DakinKoordinate zweitRechtesterSchnitt = new DakinKoordinate(0, 0);

		// finde Schnitte mit x-Achse
		DakinKoordinate p1x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 0, 1,
				0);

		DakinKoordinate p2x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 0, 1,
				0);

		// finde Schnitte mit y-Achse
		DakinKoordinate p1y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		// suche positive Schnitte
		DakinKoordinate[] positiveSchnitte = new DakinKoordinate[0];
		int index = 0;
		if (!((p1x.x1() == rechtesterSchnitt.x1()) && (p1x.x2() == rechtesterSchnitt
				.x2()))) {
			if (p1x.x1() >= 0 && p1x.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p1x);
				index++;
			}
		}
		if (!((p2x.x1() == rechtesterSchnitt.x1()) && (p2x.x2() == rechtesterSchnitt
				.x2()))) {
			if (p2x.x1() >= 0 && p2x.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p2x);
				index++;
			}
		}
		if (!((p1y.x1() == rechtesterSchnitt.x1()) && (p1y.x2() == rechtesterSchnitt
				.x2()))) {
			if (p1y.x1() >= 0 && p1y.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p1y);
				index++;
			}
		}
		if (!((p2y.x1() == rechtesterSchnitt.x1()) && (p2y.x2() == rechtesterSchnitt
				.x2()))) {
			if (p2y.x1() >= 0 && p2y.x2() >= 0) {
				positiveSchnitte = vergroessereArray(positiveSchnitte, 1);
				positiveSchnitte[index] = new DakinKoordinate(p2y);
				index++;
			}
		}

		// suche größten x-Wert der positiven Schnittpunkte
		float maxX = Float.NEGATIVE_INFINITY;
		DakinKoordinate[] gleicherXWertArray = new DakinKoordinate[0];
		index = 0;
		for (int i = 0; i < positiveSchnitte.length; i++) {
			if (positiveSchnitte[i].x1() > maxX) {
				gleicherXWertArray = vergroessereArray(gleicherXWertArray, 1);
				gleicherXWertArray[index] = new DakinKoordinate(
						positiveSchnitte[i]);
				index++;
				maxX = positiveSchnitte[i].x1();
			} else if (positiveSchnitte[i].x1() == maxX) {
				if (gleicherXWertArray.length > 0) {
					if (gleicherXWertArray[0].x1() != maxX) {
						gleicherXWertArray = new DakinKoordinate[0];
					}
				}
				gleicherXWertArray = vergroessereArray(gleicherXWertArray, 1);
				gleicherXWertArray[index] = positiveSchnitte[i];
				index++;
			}
		}

		if (gleicherXWertArray.length == 1) {
			return gleicherXWertArray[0];
		}

		// suche kleinsten y-Wert der gleichen X-Werte
		float minY = Float.MAX_VALUE;
		for (int i = 0; i < gleicherXWertArray.length; i++) {
			if (gleicherXWertArray[i].x2() < minY) {
				minY = gleicherXWertArray[i].x2();
				zweitRechtesterSchnitt = new DakinKoordinate(
						gleicherXWertArray[i]);
			}
		}
		return zweitRechtesterSchnitt;
	}

	public DakinKoordinate[] sucheAlleXSchnitte(matrix eingabeMatrix) {
		DakinKoordinate[] alleSchnitte = new DakinKoordinate[0];
		int index = 0;

		// finde Schnitte mit x-Achse
		DakinKoordinate p1x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 0, 1,
				0);

		DakinKoordinate p2x = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 0, 1,
				0);

		if (p1x.x1() >= 0) {
			alleSchnitte = vergroessereArray(alleSchnitte, 1);
			alleSchnitte[index] = new DakinKoordinate(p1x);
			index++;
		}
		if (p2x.x1() >= 0) {
			alleSchnitte = vergroessereArray(alleSchnitte, 1);
			alleSchnitte[index] = new DakinKoordinate(p2x);
		}
		return alleSchnitte;
	}

	public DakinKoordinate[] sucheAlleYSchnitte(matrix eingabeMatrix) {
		DakinKoordinate[] alleSchnitte = new DakinKoordinate[0];
		int index = 0;

		// finde Schnitte mit y-Achse
		DakinKoordinate p1y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(1, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(1, 3))).floatValue(), 1, 0,
				0);

		DakinKoordinate p2y = schneideGeraden(
				(new Float(eingabeMatrix.getValueAt(2, 0))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 1))).floatValue(),
				(new Float(eingabeMatrix.getValueAt(2, 3))).floatValue(), 1, 0,
				0);

		if (p1y.x2() >= 0) {
			alleSchnitte = vergroessereArray(alleSchnitte, 1);
			alleSchnitte[index] = new DakinKoordinate(p1y);
			index++;
		}
		if (p2y.x2() >= 0) {
			alleSchnitte = vergroessereArray(alleSchnitte, 1);
			alleSchnitte[index] = new DakinKoordinate(p2y);
		}
		return alleSchnitte;
	}

	public DakinKoordinate[] vergroessereArray(DakinKoordinate[] array, int wert) {
		DakinKoordinate[] tmpArray = new DakinKoordinate[array.length + wert];
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null)
				tmpArray[i] = new DakinKoordinate(array[i]);
		}
		return tmpArray;
	}

	public DakinKoordinate[] kopiereArray(DakinKoordinate[] quelle,
			DakinKoordinate[] senke) {
		senke = new DakinKoordinate[quelle.length];
		for (int i = 0; i < quelle.length; i++) {
			senke[i] = quelle[i];
		}
		return senke;
	}

	public boolean getSchnittquadrant(matrix eingabeMatrix) {
		if (restriktionenSchnitt == null)
			return false;
		if (restriktionenSchnitt.x1() > 0 && restriktionenSchnitt.x2() > 0)
			return true;

		return false;
	}

	public void berechneKleinerGleichPolygon(matrix eingabeMatrix) {
		// Herausfinden, welches Vorzeichen die Steigungen haben
		boolean negSteigung = true;
		for (int i = 0; i < rKoeffizienten.length; i++) {
			if (!((rKoeffizienten[i].x1() < 0 && rKoeffizienten[i].x2() < 0) || (rKoeffizienten[i]
					.x1() >= 0 && rKoeffizienten[i].x2() >= 0))) {
				negSteigung = false;
			}
		}

		if (negSteigung) {
			/*
			 * alle Steigungen sind negativ, weil jeder Wert der Restriktionen
			 * in der Eingabematrix positiv ist
			 */
			// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
			// schneiden
			boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
			if (relevanterSchnitt) {
				// Geraden schneiden sich im 1. Quadranten
				// Initialisieren des Polygons: 3 Punkte reichen, weil (0/0)
				// auf jeden Fall dabei ist
				loesungsPolygon = new DakinKoordinate[4];
				loesungsPolygon[0] = sucheKleinstenXSchnitt(eingabeMatrix);
				loesungsPolygon[1] = sucheKleinstenYSchnitt(eingabeMatrix);
				loesungsPolygon[2] = new DakinKoordinate(restriktionenSchnitt);
				loesungsPolygon[3] = new DakinKoordinate(0, 0);
			} else {
				// Geraden schneiden sich nicht im 1. Quadranten
				// Initialisieren des Polygons: 2 Punkte reichen, weil
				// kein Geradenschnitt im 1. Quadranten
				loesungsPolygon = new DakinKoordinate[3];
				loesungsPolygon[0] = sucheKleinstenXSchnitt(eingabeMatrix);
				loesungsPolygon[1] = sucheKleinstenYSchnitt(eingabeMatrix);
				loesungsPolygon[2] = new DakinKoordinate(0, 0);
			}
		} else {
			// Steigungen sind nicht alle negativ
			boolean posSteigung = true;

			for (int i = 0; i < rKoeffizienten.length; i++) {
				if (!((rKoeffizienten[i].x1() < 0 && rKoeffizienten[i].x2() >= 0) || (rKoeffizienten[i]
						.x1() >= 0 && rKoeffizienten[i].x2() < 0))) {
					posSteigung = false;
				}
			}

			if (posSteigung) {
				infoDialog = 1;
				/* alle Steigungen sind positiv */
				// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
				// schneiden
				boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
				if (relevanterSchnitt) {
					// Geraden schneiden sich im 1. Quadranten
					loesungsPolygon = new DakinKoordinate[5];
					// 1. Punkt ist "rechtester" Schnittpunkt einer Gerade mit
					// den Achsen
					loesungsPolygon[0] = sucheRechtestenSchnitt(eingabeMatrix);
					int index = 1;
					if (loesungsPolygon[0].x2() > 0) {
						loesungsPolygon = vergroessereArray(loesungsPolygon, 1);
						loesungsPolygon[index] = new DakinKoordinate(0, 0);
						index++;
					}
					// 2. Punkt ist Schnittpunkt der Restriktionsgeraden
					loesungsPolygon[index] = new DakinKoordinate(
							restriktionenSchnitt);
					index++;
					// 3. Punkt ist unendlich --> ein weiterer Punkt auf der
					// Gerade, die
					// den Polygon im Unendlichen von oben begrenzt, anhand
					// derer auch
					// ein Punkt im Unendlichen ausgerechnet werden kann (in
					// com/jrefinery/chart/AreaXYItemRenderer.java)
					float tmp = restriktionenSchnitt.x1() + 1;
					loesungsPolygon[index] = sucheKleinstenYSchnitt(
							eingabeMatrix, tmp);
					index++;
					loesungsPolygon[index] = new DakinKoordinate(
							loesungsPolygon[index - 1].x1(), 0);
					index++;
					// 4. Punkt ist Kopie des 3., so dass AreaXYItemRenderer
					// weiß, dass
					// es um einen Raum im Rechtsunendlichen geht
					loesungsPolygon[index] = new DakinKoordinate(
							loesungsPolygon[index - 1]);

				} else {
					// Geraden schneiden sich nicht im 1. Quadranten
					// Initialisieren des Polygons: 2 Punkte reichen, weil
					// kein Geradenschnitt im 1. Quadranten, 3.Punkt ist Kopie
					// des 2.
					// Punktes für AreyXYItemRenderer.java
					loesungsPolygon = new DakinKoordinate[4];
					loesungsPolygon[0] = sucheRechtestenSchnitt(eingabeMatrix);
					int index = 1;
					if (loesungsPolygon[0].x2() > 0) {
						loesungsPolygon = vergroessereArray(loesungsPolygon, 1);
						loesungsPolygon[index] = new DakinKoordinate(0, 0);
						index++;
					}
					float tmp = loesungsPolygon[0].x1() + 1;
					loesungsPolygon[index] = sucheKleinstenYSchnitt(
							eingabeMatrix, tmp);
					index++;
					loesungsPolygon[index] = new DakinKoordinate(
							loesungsPolygon[index - 1].x1(), 0);
					index++;
					loesungsPolygon[index] = new DakinKoordinate(
							loesungsPolygon[index - 1]);

				}
			} else {
				/* eine Steigung ist positiv, die andere negativ */
				// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
				// schneiden
				boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
				if (relevanterSchnitt) {
					/* Schnittpunkt liegt im 1. Quadranten */
					loesungsPolygon = new DakinKoordinate[3];
					loesungsPolygon[0] = restriktionenSchnitt;
					loesungsPolygon[1] = sucheRechtestenSchnitt(eingabeMatrix);
					loesungsPolygon[2] = sucheZweitRechtestenSchnitt(
							eingabeMatrix, loesungsPolygon[1]);
					if (loesungsPolygon[2].x2() > 0) {
						loesungsPolygon = vergroessereArray(loesungsPolygon, 1);
						loesungsPolygon[3] = new DakinKoordinate(0, 0);
					}
				} else {
					/*
					 * Schnittpunkt liegt nicht im 1. Quadranten und muss im 4.
					 * Quadranten liegen, sonst wäre die Lösung infeasible
					 */
					loesungsPolygon = new DakinKoordinate[3];
					loesungsPolygon[0] = sucheKleinstenXSchnitt(eingabeMatrix);
					loesungsPolygon[1] = sucheKleinstenYSchnitt(eingabeMatrix);
					loesungsPolygon[2] = new DakinKoordinate(0, 0);
				}
			}
		} // Ende: welche Steigungen haben die Geraden
		return;
	}

	public void berechneGroesserGleichPolygon(matrix eingabeMatrix) {
		// Herausfinden, welches Vorzeichen die Steigungen haben
		boolean negSteigung = true;
		for (int i = 0; i < rKoeffizienten.length; i++) {
			if (!((rKoeffizienten[i].x1() < 0 && rKoeffizienten[i].x2() < 0) || (rKoeffizienten[i]
					.x1() >= 0 && rKoeffizienten[i].x2() >= 0))) {
				negSteigung = false;
			}
		}

		if (negSteigung) {
			infoDialog = 0;
			/*
			 * alle Steigungen sind negativ, weil jeder Wert der Restriktionen
			 * in der Eingabematrix positiv ist
			 */
			// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
			// schneiden
			boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
			if (relevanterSchnitt) {
				// Geraden schneiden sich im 1. Quadranten
				// Initialisieren des Polygons: 3 Punkte reichen, weil (0/0)
				// auf jeden Fall dabei ist; zusätzlicher 2. Punkt ist Kopie des
				// ersten,
				// um Polygon ins x2- Unendliche zu zeichnen, zusätzlicher
				// letzter Punkt
				// ist Kopie des vorletzten, um Polygon ins x1-Unendliche zu
				// zeichnen
				loesungsPolygon = new DakinKoordinate[5];
				loesungsPolygon[0] = sucheGroesstenYSchnitt(eingabeMatrix);
				loesungsPolygon[1] = new DakinKoordinate(loesungsPolygon[0]);
				loesungsPolygon[2] = new DakinKoordinate(restriktionenSchnitt);
				loesungsPolygon[3] = sucheGroesstenXSchnitt(eingabeMatrix);
				loesungsPolygon[4] = new DakinKoordinate(loesungsPolygon[3]);
			} else {
				// Geraden schneiden sich nicht im 1. Quadranten
				// Initialisieren des Polygons: 2 Punkte reichen, weil
				// kein Geradenschnitt im 1. Quadranten
				loesungsPolygon = new DakinKoordinate[4];
				loesungsPolygon[0] = sucheGroesstenYSchnitt(eingabeMatrix);
				loesungsPolygon[1] = new DakinKoordinate(loesungsPolygon[0]);
				loesungsPolygon[2] = sucheGroesstenXSchnitt(eingabeMatrix);
				loesungsPolygon[3] = new DakinKoordinate(loesungsPolygon[2]);
			}
		} else {
			// Steigungen sind nicht alle negativ
			boolean posSteigung = true;

			for (int i = 0; i < rKoeffizienten.length; i++) {
				if (!((rKoeffizienten[i].x1() < 0 && rKoeffizienten[i].x2() >= 0) || (rKoeffizienten[i]
						.x1() >= 0 && rKoeffizienten[i].x2() < 0))) {
					posSteigung = false;
				}
			}

			if (posSteigung) {
				infoDialog = 0;
				/* alle Steigungen sind positiv */
				// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
				// schneiden
				boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
				if (relevanterSchnitt) {
					// Geraden schneiden sich im 1. Quadranten
					loesungsPolygon = new DakinKoordinate[3];
					// erster Punkt ist linkester Schnitt
					loesungsPolygon[0] = sucheLinkestenSchnitt(eingabeMatrix);
					// x2-Unendlichkeit: kopiere ersten Punkt in zweiten
					loesungsPolygon[1] = new DakinKoordinate(loesungsPolygon[0]);
					// 3. Punkt ist der Restriktionsgeradenschnittpunkt
					loesungsPolygon[2] = new DakinKoordinate(
							restriktionenSchnitt);
					if (loesungsPolygon[0].x1() > 0) {
						loesungsPolygon = vergroessereArray(loesungsPolygon, 1);
						loesungsPolygon[3] = new DakinKoordinate(0, 0);
					}

				} else {
					// Geraden schneiden sich nicht im 1. Quadranten
					loesungsPolygon = new DakinKoordinate[4];
					loesungsPolygon[0] = sucheLinkestenSchnitt(eingabeMatrix);
					loesungsPolygon[1] = new DakinKoordinate(loesungsPolygon[0]);
					int index = 2;
					if (loesungsPolygon[0].x1() > 0) {
						loesungsPolygon = vergroessereArray(loesungsPolygon, 1);
						loesungsPolygon[index] = new DakinKoordinate(0, 0);
						index++;
					}
					float tmp = loesungsPolygon[0].x1() + 1;
					loesungsPolygon[index] = sucheGroesstenYSchnitt(
							eingabeMatrix, tmp);
					index++;
					loesungsPolygon[index] = new DakinKoordinate(
							loesungsPolygon[index - 1]);
				}
			} else {
				infoDialog = 0;
				/* eine Steigung ist positiv, die andere negativ */
				// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
				// schneiden
				boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
				if (relevanterSchnitt) {
					/* Schnittpunkt liegt im 1. Quadranten */
					loesungsPolygon = new DakinKoordinate[5];
					loesungsPolygon[0] = sucheLinkestenSchnitt(eingabeMatrix);
					loesungsPolygon[1] = new DakinKoordinate(loesungsPolygon[0]);
					loesungsPolygon[2] = new DakinKoordinate(
							restriktionenSchnitt);
					float tmp = restriktionenSchnitt.x1() + 1;
					loesungsPolygon[3] = sucheGroesstenYSchnitt(eingabeMatrix,
							tmp);
					loesungsPolygon[4] = new DakinKoordinate(loesungsPolygon[3]);
				} else {
					/* Schnittpunkt liegt nicht im 1. Quadranten */
					// Prüfen, ob Schnitt im 2. oder 3./4. Quadranten liegt
					if (restriktionenSchnitt.x1() < 0) {
						// Schnittpunkt liegt im 3. oder im 4. Quadranten
						loesungsPolygon = new DakinKoordinate[4];
						loesungsPolygon[0] = sucheLinkestenSchnitt(eingabeMatrix);
						loesungsPolygon[1] = new DakinKoordinate(
								loesungsPolygon[0]);
						int index = 2;
						if (loesungsPolygon[0].x1() > 0) {
							loesungsPolygon = vergroessereArray(
									loesungsPolygon, 1);
							loesungsPolygon[index] = new DakinKoordinate(0, 0);
							index++;
						}
						float tmp = loesungsPolygon[0].x1() + 1;
						loesungsPolygon[index] = sucheGroesstenYSchnitt(
								eingabeMatrix, tmp);
						index++;
						loesungsPolygon[index] = new DakinKoordinate(
								loesungsPolygon[index - 1]);

					} else if (restriktionenSchnitt.x2() < 0) {
						// Schnittpunkt liegt im 2. Quadranten
						loesungsPolygon = new DakinKoordinate[4];
						loesungsPolygon[0] = sucheGroesstenYSchnitt(eingabeMatrix);
						if (loesungsPolygon[0] == null) {
							loesungsPolygon[0] = new DakinKoordinate(0, 0);
						}
						loesungsPolygon[1] = new DakinKoordinate(
								loesungsPolygon[0]);
						DakinKoordinate[] alleXSchnitte = sucheAlleXSchnitte(eingabeMatrix);
						loesungsPolygon = vergroessereArray(loesungsPolygon,
								alleXSchnitte.length);
						int index = 2;
						for (int i = 0; i < alleXSchnitte.length; i++) {
							loesungsPolygon[index] = alleXSchnitte[i];
							index++;
						}
						float tmp = 0;
						if (alleXSchnitte.length > 1) {
							tmp = (maxKoordinate(alleXSchnitte[0],
									alleXSchnitte[1], 1)).x1() + 1;
						} else if (alleXSchnitte.length == 1) {
							tmp = alleXSchnitte[0].x1() + 1;
						} else {
							System.out
									.println("Fehler in berechneGroesserGleichPolygon: kein Geraden-Schnitt mit x1-Achse!");
						}
						loesungsPolygon[index] = sucheGroesstenYSchnitt(
								eingabeMatrix, tmp);
						index++;
						loesungsPolygon[index] = new DakinKoordinate(
								loesungsPolygon[index - 1]);
					} else {
						System.out
								.println("Fehler in berechneGroesserGleichPolygon: wo liegt der Schnitt???");
					}
				}
			}
		} // Ende: welche Steigungen haben die Geraden
		return;
	}

	public void berechneGemischteRelationPolygon(matrix eingabeMatrix,
			Vector zusaetzlicheRestriktionen) {
		// Herausfinden, welches Vorzeichen die Steigungen haben
		boolean negSteigung = true;
		for (int i = 0; i < rKoeffizienten.length; i++) {
			if (!((rKoeffizienten[i].x1() < 0 && rKoeffizienten[i].x2() < 0) || (rKoeffizienten[i]
					.x1() >= 0 && rKoeffizienten[i].x2() >= 0))) {
				negSteigung = false;
			}
		}

		if (negSteigung) {
			/*
			 * alle Steigungen sind negativ, weil jeder Wert der Restriktionen
			 * in der Eingabematrix positiv ist
			 */
			// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
			// schneiden
			boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
			if (relevanterSchnitt) {
				// Finde heraus, wo sich der Lösungsraumpolygon befindet
				int groessterSchnittGeradenIndex = sucheGroesstenYSchnittGeradenIndex(eingabeMatrix);
				if (eingabeMatrix.getValueAt(groessterSchnittGeradenIndex, 2)
						.equals("<=")) {
					loesungsPolygon = new DakinKoordinate[1];
					loesungsPolygon[0] = new DakinKoordinate(
							restriktionenSchnitt);
					DakinKoordinate[] alleYSchnitte = sucheAlleYSchnitte(eingabeMatrix);
					loesungsPolygon = vergroessereArray(loesungsPolygon,
							alleYSchnitte.length);
					int index = 1;
					for (int i = 0; i < alleYSchnitte.length; i++) {
						loesungsPolygon[index] = new DakinKoordinate(
								alleYSchnitte[i]);
						index++;
					}
				} else { // >= -Relation
					loesungsPolygon = new DakinKoordinate[1];
					loesungsPolygon[0] = new DakinKoordinate(
							restriktionenSchnitt);
					DakinKoordinate[] alleXSchnitte = sucheAlleXSchnitte(eingabeMatrix);
					loesungsPolygon = vergroessereArray(loesungsPolygon,
							alleXSchnitte.length);
					int index = 1;
					for (int i = 0; i < alleXSchnitte.length; i++) {
						loesungsPolygon[index] = new DakinKoordinate(
								alleXSchnitte[i]);
						index++;
					}
				}
			} else {
				// Geraden schneiden sich nicht im 1. Quadranten
				// suche alle positiven Schnittpunkte der Restriktionsgeraden
				// mit den Achsen
				infoDialog = 1;
				sortierePoly = false;
				DakinKoordinate[] alleXSchnitte = sucheAlleXSchnitte(eingabeMatrix);
				DakinKoordinate[] alleYSchnitte = sucheAlleYSchnitte(eingabeMatrix);
				loesungsPolygon = new DakinKoordinate[alleXSchnitte.length
						+ alleYSchnitte.length];
				int index = 0;
				for (int i = 0; i < alleYSchnitte.length; i++) {
					loesungsPolygon[index] = new DakinKoordinate(
							alleYSchnitte[i]);
					index++;
				}
				if (alleYSchnitte.length > 1) {
					if (loesungsPolygon[index - 1].x2() > loesungsPolygon[index - 2]
							.x2()) {
						tauscheKoords(loesungsPolygon, index - 1, index - 2);
					}
				}
				for (int i = 0; i < alleXSchnitte.length; i++) {
					loesungsPolygon[index] = new DakinKoordinate(
							alleXSchnitte[i]);
					index++;
				}
			}
		} else {
			// Steigungen sind nicht alle negativ
			boolean posSteigung = true;

			for (int i = 0; i < rKoeffizienten.length; i++) {
				if (!((rKoeffizienten[i].x1() < 0 && rKoeffizienten[i].x2() >= 0) || (rKoeffizienten[i]
						.x1() >= 0 && rKoeffizienten[i].x2() < 0))) {
					posSteigung = false;
				}
			}

			if (posSteigung) {
				/* alle Steigungen sind positiv */
				// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
				// schneiden
				boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
				if (relevanterSchnitt) {
					int groessterSchnittGeradenIndex = sucheGroesstenYSchnittGeradenIndex(eingabeMatrix);
					if (eingabeMatrix.getValueAt(groessterSchnittGeradenIndex,
							2).equals("<=")) {
						loesungsPolygon = new DakinKoordinate[1];
						loesungsPolygon[0] = new DakinKoordinate(
								restriktionenSchnitt);
						DakinKoordinate[] alleXSchnitte = sucheAlleXSchnitte(eingabeMatrix);
						DakinKoordinate[] alleYSchnitte = sucheAlleYSchnitte(eingabeMatrix);
						loesungsPolygon = vergroessereArray(loesungsPolygon,
								alleXSchnitte.length + alleYSchnitte.length);
						int index = 1;
						for (int i = 0; i < alleXSchnitte.length; i++) {
							loesungsPolygon[index] = new DakinKoordinate(
									alleXSchnitte[i]);
							index++;
						}
						for (int i = 0; i < alleYSchnitte.length; i++) {
							loesungsPolygon[index] = new DakinKoordinate(
									alleYSchnitte[i]);
							index++;
						}
						if (alleXSchnitte.length == 1
								&& alleYSchnitte.length == 1) {
							loesungsPolygon = vergroessereArray(
									loesungsPolygon, 1);
							loesungsPolygon[index] = new DakinKoordinate(0, 0);
						}
					} else { // >= -Relation
						infoDialog = 1;
						loesungsPolygon = new DakinKoordinate[4];
						loesungsPolygon[0] = new DakinKoordinate(
								restriktionenSchnitt);
						float tmp = restriktionenSchnitt.x1() + 1;
						loesungsPolygon[1] = sucheGroesstenYSchnitt(
								eingabeMatrix, tmp);
						loesungsPolygon[2] = sucheKleinstenYSchnitt(
								eingabeMatrix, tmp);
						loesungsPolygon[3] = new DakinKoordinate(
								loesungsPolygon[2]);
					}
				} else {
					infoDialog = 1;
					// Geraden schneiden sich nicht im 1. Quadranten
					loesungsPolygon = new DakinKoordinate[0];
					DakinKoordinate[] alleXSchnitte = sucheAlleXSchnitte(eingabeMatrix);
					DakinKoordinate[] alleYSchnitte = sucheAlleYSchnitte(eingabeMatrix);
					loesungsPolygon = vergroessereArray(loesungsPolygon,
							alleXSchnitte.length + alleYSchnitte.length);
					int index = 0;
					for (int i = 0; i < alleXSchnitte.length; i++) {
						loesungsPolygon[index] = new DakinKoordinate(
								alleXSchnitte[i]);
						index++;
					}
					for (int i = 0; i < alleYSchnitte.length; i++) {
						loesungsPolygon[index] = new DakinKoordinate(
								alleYSchnitte[i]);
						index++;
					}
					sortierePolygon(loesungsPolygon);
					int bisherigeLaenge = loesungsPolygon.length;
					loesungsPolygon = vergroessereArray(loesungsPolygon,
							loesungsPolygon.length);
					float tmp = maxX;

					int i;
					for (i = 0; i < bisherigeLaenge; i++) {
						if (i == 0)
							loesungsPolygon[index] = sucheGroesstenYSchnitt(
									eingabeMatrix, tmp);
						else
							loesungsPolygon[index] = sucheKleinstenYSchnitt(
									eingabeMatrix, tmp);
						index++;
					}
					sortierePolygon(loesungsPolygon);
					loesungsPolygon = vergroessereArray(loesungsPolygon, 1);
					loesungsPolygon[index] = new DakinKoordinate(
							loesungsPolygon[index - 1]);
				}
			} else {
				/* eine Steigung ist positiv, die andere negativ */
				// Finde heraus, ob sich Restriktionsgeraden im 1. Quadranten
				// schneiden
				infoDialog = 1;
				boolean relevanterSchnitt = getSchnittquadrant(eingabeMatrix);
				if (relevanterSchnitt) {
					/* Schnittpunkt liegt im 1. Quadranten */
					int linksterSchnittGeradenIndex;
					DakinKoordinate linksterSchnitt = sucheLinkestenSchnitt(eingabeMatrix);

					float checkVal = (bVektor[0].floatValue() - rKoeffizienten[0]
							.x2() * linksterSchnitt.x2())
							/ rKoeffizienten[0].x1();
					float checkValGerundet = (float) (Math
							.round(checkVal * 100)) / 100;
					if (checkValGerundet == linksterSchnitt.x1())
						linksterSchnittGeradenIndex = 0;
					else
						linksterSchnittGeradenIndex = 1;

					if (eingabeMatrix
							.getValueAt(linksterSchnittGeradenIndex, 2).equals(
									"<=")) {
						loesungsPolygon = new DakinKoordinate[3];
						loesungsPolygon[0] = sucheLinkestenSchnitt(eingabeMatrix);
						loesungsPolygon[1] = new DakinKoordinate(0, 0);
						loesungsPolygon[2] = sucheZweitLinkestenSchnitt(
								eingabeMatrix, loesungsPolygon[0]);
						loesungsPolygon[3] = new DakinKoordinate(
								restriktionenSchnitt);
					} else { // Relation der Gerade des linkesten Schnittes ist
								// >=
						loesungsPolygon = new DakinKoordinate[5];
						loesungsPolygon[0] = new DakinKoordinate(
								restriktionenSchnitt);
						loesungsPolygon[1] = sucheRechtestenSchnitt(eingabeMatrix);
						loesungsPolygon[2] = sucheGroesstenYSchnitt(
								eingabeMatrix, loesungsPolygon[1].x1() + 1);
						loesungsPolygon[3] = sucheKleinstenYSchnitt(
								eingabeMatrix, loesungsPolygon[1].x1() + 1);
						loesungsPolygon[4] = new DakinKoordinate(
								loesungsPolygon[3]);
					}
				} else {
					/* Schnittpunkt liegt nicht im 1. Quadranten */
					// Prüfen, ob Schnitt im 2. oder 3./4. Quadranten liegt
					if (restriktionenSchnitt.x1() < 0
							&& restriktionenSchnitt.x2() >= 0) {
						// Schnittpunkt liegt im 4. Quadranten
						DakinKoordinate[] alleXSchnitte = sucheAlleXSchnitte(eingabeMatrix);
						DakinKoordinate[] alleYSchnitte = sucheAlleYSchnitte(eingabeMatrix);
						loesungsPolygon = new DakinKoordinate[alleXSchnitte.length
								+ alleYSchnitte.length];
						int index = 0;
						for (int i = 0; i < alleXSchnitte.length; i++) {
							loesungsPolygon[index] = alleXSchnitte[i];
							index++;
						}
						for (int i = 0; i < alleYSchnitte.length; i++) {
							loesungsPolygon[index] = alleYSchnitte[i];
							index++;
						}
						int bisherigeLaenge = loesungsPolygon.length;
						DakinKoordinate neuerPunkt;
						for (int i = 0; i < bisherigeLaenge; i++) {
							neuerPunkt = sucheKleinstenYSchnitt(eingabeMatrix,
									loesungsPolygon[i].x1() + 1);
							if (neuerPunkt != null) {
								loesungsPolygon = vergroessereArray(
										loesungsPolygon, 1);
								loesungsPolygon[index] = new DakinKoordinate(
										neuerPunkt);
								index++;
							}
							neuerPunkt = sucheGroesstenYSchnitt(eingabeMatrix,
									loesungsPolygon[i].x1() + 1);
							if (neuerPunkt != null) {
								loesungsPolygon = vergroessereArray(
										loesungsPolygon, 1);
								loesungsPolygon[index] = new DakinKoordinate(
										neuerPunkt);
								index++;
							}
						}
						loesungsPolygon = vergroessereArray(loesungsPolygon, 1);
						loesungsPolygon[index] = new DakinKoordinate(
								loesungsPolygon[index - 1]);
					} else if (restriktionenSchnitt.x1() < 0
							&& restriktionenSchnitt.x2() < 0) {
						// Schnittpunkt liegt im 3. Quadranten
						loesungsPolygon = new DakinKoordinate[1];
						loesungsPolygon[0] = sucheRechtestenSchnitt(eingabeMatrix);
						int index = 1;
						if (loesungsPolygon[0].x2() > 0) {
							loesungsPolygon = vergroessereArray(
									loesungsPolygon, 1);
							loesungsPolygon[index] = new DakinKoordinate(0, 0);
							index++;
						}
						loesungsPolygon = vergroessereArray(loesungsPolygon, 2);
						loesungsPolygon[index] = sucheGroesstenYSchnitt(
								eingabeMatrix, loesungsPolygon[0].x1() + 1);
						index++;
						loesungsPolygon[index] = new DakinKoordinate(
								loesungsPolygon[0].x1() + 1, 0);

					} else if (restriktionenSchnitt.x1() >= 0
							&& restriktionenSchnitt.x2() < 0) {
						// Schnittpunkt liegt im 2. Quadranten
						int linksterSchnittGeradenIndex = -1;
						DakinKoordinate rechtesterSchnitt = sucheRechtestenSchnitt(eingabeMatrix);
						int i;
						for (i = 0; i < anzahlEingabeRestriktionen; i++) {
							DakinKoordinate pruefeSchnittpunkt = schneideGeraden(
									rechtesterSchnitt.x1(),
									rechtesterSchnitt.x2(), 0,
									rKoeffizienten[i].x1(),
									rKoeffizienten[i].x2(),
									bVektor[i].floatValue());
							if (pruefeSchnittpunkt != null) {
								linksterSchnittGeradenIndex = i;
								break;
							}
						}
						if (eingabeMatrix.getValueAt(
								linksterSchnittGeradenIndex, 2).equals("<=")) {
							loesungsPolygon = new DakinKoordinate[3];
							loesungsPolygon[0] = new DakinKoordinate(
									rechtesterSchnitt);
							loesungsPolygon[1] = sucheGroesstenYSchnitt(
									eingabeMatrix, rechtesterSchnitt.x1() + 1);
							loesungsPolygon[2] = new DakinKoordinate(
									loesungsPolygon[1]);
						} else { // Restriktion des rechtesten Schnittpunktes
									// ist >=
							// suche Schnittpunkte der anderen Gerade mit den
							// Achsen
							int j;
							if (i == 0)
								j = 1;
							else
								j = 0;
							loesungsPolygon = new DakinKoordinate[3];
							loesungsPolygon[0] = new DakinKoordinate(0, 0);
							loesungsPolygon[1] = schneideGeraden(1, 0, 0,
									rKoeffizienten[j].x1(),
									rKoeffizienten[j].x2(),
									bVektor[j].floatValue());
							loesungsPolygon[2] = schneideGeraden(0, 1, 0,
									rKoeffizienten[j].x1(),
									rKoeffizienten[j].x2(),
									bVektor[j].floatValue());
						}
					} else {
						System.out
								.println("Fehler in berechneGemischteRelationPolygon: wo liegt der Schnitt???");
					}
				}
			}
		} // Ende: welche Steigungen haben die Geraden
		return;
	}

	public void sortierePolygon(DakinKoordinate[] polygon) {
		int i;
		int j;
		// nach x-werten sortieren
		for (i = 1; i < polygon.length; i++) {
			for (j = polygon.length - 1; j >= i; j--) {
				if (polygon[j].x1() < polygon[j - 1].x1()) {
					tauscheKoords(polygon, j, j - 1);
				}
			}
		}
		for (i = 1; i < polygon.length; i++) {
			for (j = polygon.length - 1; j >= i; j--) {
				if (polygon[j].x1() == polygon[j - 1].x1()) {
					if (j == polygon.length - 1 && polygon.length > 2) {
						if (polygon[j].x2() == polygon[j - 1].x2()) {
							if (polygon[j - 1].x1() == polygon[j - 2].x1()) {
								if (polygon[j - 1].x2() > polygon[j - 2].x2()) {
									tauscheKoords(polygon, j - 1, j - 2);
								}
							}
						} else {
							if (polygon[j].x2() > polygon[j - 1].x2()) {
								tauscheKoords(polygon, j, j - 1);
							}
						}
					}
					if (j == 1) {
						if (polygon[j].x2() < polygon[j - 1].x2()) {
							tauscheKoords(polygon, j, j - 1);
						}
					}
				}
			}
		}
		return;
	}

	public void sortierePolygon2(DakinKoordinate[] polygon, int wonach) {
		int i;
		int j;
		if (wonach == 1) { // sortiere nach x1-Werten
			for (i = 1; i < polygon.length; i++) {
				for (j = polygon.length - 1; j >= i; j--) {
					if (polygon[j].x1() < polygon[j - 1].x1()) {
						tauscheKoords(polygon, j, j - 1);
					}
				}
			}
		} else { // sortiere nach x2-Werten
			for (i = 1; i < polygon.length; i++) {
				for (j = polygon.length - 1; j >= i; j--) {
					if (polygon[j].x2() < polygon[j - 1].x2()) {
						tauscheKoords(polygon, j, j - 1);
					}
				}
			}
		}
		return;
	}

	public void sortiereZusaetzlicheRestriktionen(Vector restriktionsVektor) {
		int x1R1;
		int x1R2;
		int i;
		int j;

		for (i = 1; i < restriktionsVektor.size(); i++) {
			for (j = restriktionsVektor.size() - 1; j >= i; j--) {
				x1R1 = Integer.parseInt(((Vector) restriktionsVektor.get(j))
						.get(0).toString());
				x1R2 = Integer
						.parseInt(((Vector) restriktionsVektor.get(j - 1)).get(
								0).toString());
				if (x1R1 == 1 && x1R2 == 0) {
					vectorSwap(restriktionsVektor, j, j - 1);
				}
			}
		}
		return;
	}

}
