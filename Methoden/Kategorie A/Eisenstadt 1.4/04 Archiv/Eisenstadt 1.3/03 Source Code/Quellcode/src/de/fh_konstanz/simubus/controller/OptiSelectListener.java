package de.fh_konstanz.simubus.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JTable;

import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.util.OrUtil;
import de.fh_konstanz.simubus.view.HaltestellenCell;
import de.fh_konstanz.simubus.view.SimuGraph;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.ZieleCell;

/**
 * Die Klasse <code>OptiSelectListener</code> ist ein Listener, welche
 * die optimierte Haltestelle sowie auch die Ziele auf die
 * Karte markiert(hervorhebt).
 * 
 * 
 * @author Erkan Erpolat
 * 
 */
public class OptiSelectListener extends MouseAdapter {

	/** Tabelle für Lösungsinfo */
	private JTable table;

	/** Koordinate von ausgewählten Zelle in Lösungs Tabelle */
	private String coordinates;

	/** X Koordinate von ausgewählten Zelle */
	private int x;

	/** Y Koordinate von ausgewählten Zelle */
	private int y;

	/** Pixel X Koordinate für Haltestelle Image */
	private int pixelXCoordinate;

	/** Pixel Y Koordinate für Haltestelle Image */
	private int pixelYCoordinate;

	/** Ausgewählte Zeile */
	private int selectedRow;

	/** Ausgewählte Spalte */
	private int selectedColumn;

	/** Letzte Koordinaten Daten */
	private static String lastCoordinates;

	/** Simulation Panel Instanz */
	private SimuPanel simuPanelInstance = SimuPanel.getInstance();

	/** Zuletzt ausgewählte Haltestelle */
	private Haltestelle lastSelectHaltestelle = null;

	/** Zuletzt ausgewählte Ziele */
	private ZieleCell lastSelectZiel = null;

	/**
	 * Instanz von Lösungstabelle per Konstruktor setzen
	 * 
	 * @param table
	 */
	public OptiSelectListener(JTable table) {
		this.table = table;
	}

	/**
	 * Ausgewählte Zelle von Lösungs-Tabelle
	 * 
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {

		selectedRow = table.getSelectedRow();
		selectedColumn = table.getSelectedColumn();

		if (selectedRow < 0 || selectedColumn < 0) {

			if (table.getColumnName(selectedColumn).equals("Haltestellen") == true) {
				setHaltestelleCoordinates(lastCoordinates);
				this.selectHaltestelle();
			}

			if (table.getColumnName(selectedColumn).equals("Ziele") == true) {
				setZieleCoordinates(lastCoordinates);
				this.selectZiele();
			}

			return;
		}

		if (table.getColumnName(selectedColumn).equals("Haltestellen") == true) {
			coordinates = table.getValueAt(selectedRow, selectedColumn)
					.toString();
			setHaltestelleCoordinates(coordinates);
			this.selectHaltestelle();

		}

		if (table.getColumnName(selectedColumn).equals("Ziele") == true) {
			coordinates = table.getValueAt(selectedRow, selectedColumn)
					.toString();
			setZieleCoordinates(coordinates);
			this.selectZiele();

		}

		lastCoordinates = coordinates;

	}

	/**
	 * Koordinaten von optimierte Haltestellen setzen
	 * 
	 * @param coordinates
	 */
	public void setHaltestelleCoordinates(String coordinates) {
		String replaceIt = coordinates.replace("[", "");
		coordinates = replaceIt.replace("]", "");
		String[] splittCoordinate = coordinates.split("[x:,y:]");
		String[] coordinateValue = new String[2];
		int i = 0;

		for (int j = 0; j < splittCoordinate.length; j++) {

			if (splittCoordinate[j].equals("") == false) {
				coordinateValue[i] = splittCoordinate[j];
				i++;
			}

		}

		x = Integer.parseInt(coordinateValue[0].replaceAll(" ", ""));
		y = Integer.parseInt(coordinateValue[1].replaceAll(" ", ""));

	}

	/**
	 * Koordinaten von optimierte Ziele setzen
	 * 
	 * @param coordinates
	 */
	public void setZieleCoordinates(String coordinates) {
		StringBuffer stringBuff = new StringBuffer(coordinates);
		int buffLength = stringBuff.length();
		String xCoordinate = "";
		String yCoordinate = "";
		int counter = 0;

		for (int i = 0; i < buffLength; i++) {

			if (counter == 2)
				break;

			if (Character.isDigit(stringBuff.charAt(i)) == true) {
				++counter;

				for (int j = i; j < buffLength; j++, i = j) {

					if (Character.isDigit(stringBuff.charAt(j)) == false)
						break;

					if (counter == 1)
						xCoordinate += String.valueOf(stringBuff.charAt(j));

					else
						yCoordinate += String.valueOf(stringBuff.charAt(j));

				}

			}

		}
		x = Integer.parseInt(xCoordinate);
		y = Integer.parseInt(yCoordinate);

	}

	/**
	 * Optimierte Ziele auswählen
	 * 
	 */
	public void selectZiele() {
		this.deselectAll();
		pixelXCoordinate = x;
		pixelYCoordinate = y;

		x = OrUtil.getPlanquadratXCoordinate(x);
		y = OrUtil.getPlanquadratYCoordinate(y);

		simuPanelInstance = SimuPanel.getInstance();

		ArrayList<ZieleCell> ziele = simuPanelInstance.getZieleCells();

		for (ZieleCell ziel : ziele) {

			if (ziel.getKoordinaten().x == x && ziel.getKoordinaten().y == y) {

				SimuGraph simuGraph = simuPanelInstance.getGraph();
				Object cell = simuGraph.getFirstCellForLocation(
						pixelXCoordinate, pixelYCoordinate);

				if (cell instanceof ZieleCell) {
					ZieleCell zieleCell = ((ZieleCell) cell);
					lastSelectZiel = zieleCell;
					zieleCell.getAttributes().applyMap(
							simuPanelInstance.createCellAttributes(new Point(
									pixelXCoordinate, pixelYCoordinate),
									"optimierteZiele"));
					simuGraph.getGraphLayoutCache().insert(zieleCell);
					simuGraph.getGraphLayoutCache().insert(zieleCell);
				}

			}

		}
	}

	/**
	 * Optimierte Haltestelle auswählen
	 */
	public void selectHaltestelle() {
		this.deselectAll();
		pixelXCoordinate = x;
		pixelYCoordinate = y;

		x = OrUtil.getPlanquadratXCoordinate(x);
		y = OrUtil.getPlanquadratYCoordinate(y);

		simuPanelInstance = SimuPanel.getInstance();

		Collection<Haltestelle> haltestellen = simuPanelInstance
				.getAllHaltestellen();

		for (Haltestelle haltestelle : haltestellen) {

			if (haltestelle.getKoordinaten().x == x
					&& haltestelle.getKoordinaten().y == y) {

				lastSelectHaltestelle = haltestelle;
				SimuGraph simuGraph = simuPanelInstance.getGraph();
				Object cell = simuGraph.getFirstCellForLocation(
						pixelXCoordinate, pixelYCoordinate);

				if (cell instanceof HaltestellenCell) {
					HaltestellenCell halteStellenCell = ((HaltestellenCell) cell);
					halteStellenCell.getAttributes().applyMap(
							simuPanelInstance.createCellAttributes(new Point(
									pixelXCoordinate, pixelYCoordinate),
									"optimierteHaltestelle"));
					simuGraph.getGraphLayoutCache().insert(halteStellenCell);

				}

			}

		}
	}

	/**
	 * 
	 * Alle ausgewählten Ziele und Haltestellen wieder deselektieren
	 * 
	 */
	public void deselectAll() {

		SimuGraph simuGraph = simuPanelInstance.getGraph();

		if (lastSelectHaltestelle != null) {

			pixelXCoordinate = lastSelectHaltestelle.getPixelXCoordinate();
			pixelYCoordinate = lastSelectHaltestelle.getPixelYCoordinate();

			Object cell = simuGraph.getFirstCellForLocation(pixelXCoordinate,
					pixelYCoordinate);

			if (cell instanceof HaltestellenCell) {
				HaltestellenCell halteStellenCell = ((HaltestellenCell) cell);
				halteStellenCell.getAttributes().applyMap(
						simuPanelInstance
								.createCellAttributes(new Point(
										pixelXCoordinate, pixelYCoordinate),
										"deselect"));
				simuGraph.getGraphLayoutCache().insert(halteStellenCell);

			}

		}

		if (lastSelectZiel != null) {

			pixelXCoordinate = OrUtil.getPixelXCoordinate((int) lastSelectZiel
					.getKoordinaten().getX());

			pixelYCoordinate = OrUtil.getPixelXCoordinate((int) lastSelectZiel
					.getKoordinaten().getY());

			Object cell = simuGraph.getFirstCellForLocation(pixelXCoordinate,
					pixelYCoordinate);

			if (cell instanceof ZieleCell) {
				ZieleCell zieleCell = ((ZieleCell) cell);
				zieleCell.getAttributes().applyMap(
						simuPanelInstance
								.createCellAttributes(new Point(
										pixelXCoordinate, pixelYCoordinate),
										"deselect"));
				simuGraph.getGraphLayoutCache().insert(zieleCell);
				simuGraph.getGraphLayoutCache().insert(zieleCell);
			}
		}
	}
}