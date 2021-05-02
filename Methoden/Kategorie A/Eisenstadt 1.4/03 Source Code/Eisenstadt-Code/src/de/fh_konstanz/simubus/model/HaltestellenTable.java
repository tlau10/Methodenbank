package de.fh_konstanz.simubus.model;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import de.fh_konstanz.simubus.view.HaltestellenCell;
import de.fh_konstanz.simubus.view.SimuPanel;

/**
 * Die Klasse <code>HaltestellenTable</code> ist eine Tabelle, welche die Daten
 * aller Haltestellen hält.
 * 
 * @author Ingo Kroh, Michael Franz, Daniel Weber
 * @version 1.0 (05.06.2006)
 */

public class HaltestellenTable extends AbstractTableModel {

	private static final long serialVersionUID = -2987215348188188218L;

	/**
	 * Liste aller Haltestellen
	 */
	private List<Haltestelle> daten;

	/**
	 * Namen der Tabellenspalten
	 */
	private Vector<String> columnNames = new Vector<String>();

	/**
	 * Instanz der Haltestellentabelle
	 */
	private static HaltestellenTable instance;

	/**
	 * Singelton, welches die Instanz liefert
	 * 
	 * @return Instanz der Haltestellentabelle
	 */
	public static HaltestellenTable getInstance() {
		if (instance == null)
			instance = new HaltestellenTable();
		return instance;
	}

	/**
	 * Konstruktor, welcher das Aussehen der Tabelle bestimmt.
	 * 
	 */
	public HaltestellenTable() {
		super();
		this.columnNames.add("ID");
		this.columnNames.add("Haltestellen-Name");
		this.columnNames.add("Pos-X");
		this.columnNames.add("Pos-Y");
		this.columnNames.add("Kapazität");

	}

	/**
	 * fügt die übergeben Haltestellen der Liste hinzu
	 * 
	 * @param haltestellen
	 *            Haltestellen fuer Liste
	 */
	public void setHaltestellen(List<Haltestelle> haltestellen) {
		daten = haltestellen;
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames.get(col).toString();
	}

	/**
	 * liefert die Anzahl der Zeilen
	 * 
	 * @return Anzahl der Zeilen
	 */
	public int getRowCount() {
		return daten.size();
	}

	/**
	 * liefert die Anzahl der Spalten
	 * 
	 * @return Anzahl der Spalten
	 */
	public int getColumnCount() {
		return columnNames.size();
	}

	/**
	 * liefert ein Object mit den Werten an der Stelle der übergebenen Zeile und
	 * Spalte
	 * 
	 * @param row
	 *            Zeile die übergeben wird
	 * 
	 * @param col
	 *            Spalte die übergeben wird
	 * 
	 * @return liefert ein Object mit den Werten aus row und col
	 */
	public Object getValueAt(int row, int col) {
		if (col == 0) {
			return (daten.get(row).getID());
		} else if (col == 1) {
			return (daten.get(row).getName());
		} else if (col == 2) {
			return daten.get(row).getPixelXCoordinate();
		} else if (col == 3) {
			return daten.get(row).getPixelYCoordinate();
		} else if (col == 4) {
			return (daten.get(row).getKapazitaet());
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int row, int col) {
		if (col == 0) {
			daten.get(row).setID((Integer) aValue);
		} else if (col == 1) {
			daten.get(row).setName(aValue.toString());
			Object o[] = SimuPanel.getInstance().getGraph().getGraphLayoutCache().getCells(false, true, false, false);
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof HaltestellenCell) {
					HaltestellenCell hCell = (HaltestellenCell) o[i];
					if (daten.get(row).getID() == hCell.getId()) {
						hCell.setName(aValue.toString());
					}
				}
			}
		} else if (col == 2) {
			daten.get(row).setX((Integer) aValue);
		} else if (col == 3) {
			daten.get(row).setY((Integer) aValue);
		} else if (col == 4) {
			daten.get(row).setKapazitaet((Integer) aValue);
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 0 || col == 2 || col == 3) {
			return false;
		} else {
			return true;
		}
	}
}
