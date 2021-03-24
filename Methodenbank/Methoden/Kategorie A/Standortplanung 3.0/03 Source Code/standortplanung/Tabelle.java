package standortplanung;

import javax.swing.table.AbstractTableModel;
import javax.swing.*;

/**
 * <p>
 * Überschrift: Standortplanung
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author Tobias Kienzler, Jan Just
 * @version 1.0
 */

public class Tabelle extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rows = Variablen.startWithRows;
	private int columns = Variablen.startWithColumns;
	private int entfernung;
	private int tableSize = Variablen.maxSize;

	private Object data[][] = new Object[tableSize + 1][tableSize + 1];

	// Falls eine neue Datei erstellt wird --> Tabellen-Konstruktor
	public Tabelle(int rows, int columns, int entfernung) {
		this.setRowCount(rows);
		this.setColumnCount(columns);
		this.entfernung = entfernung;

		// data initialisieren
		for (int i = 0; i < tableSize + 1; i++)
			for (int j = 0; j < tableSize + 1; j++)
				data[i][j] = "";

		// data mit Überschriften (Standorten) füllen
		for (int i = 1; i <= tableSize; i++)
			data[0][i] = "Standort " + String.valueOf(i);
		for (int i = 1; i <= tableSize; i++)
			data[i][0] = "Standort " + String.valueOf(i);
	}

	// Falls eine Matrix erstellt werden soll
	public void setMatrix(Daten matrix) {
		this.setRowCount(matrix.getRows());
		this.setColumnCount(matrix.getColumns());

		// Werte von matrix in data schreiben
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				data[i][j] = matrix.getValueAt(i, j);

		// update der Tabellenmatrixoberfläche
		this.fireTableDataChanged();
		this.fireTableRowsUpdated(rows, columns);
	}

	// wird benutzt, wenn die aktuellen Daten gespeichert werden
	public Daten getMatrix() {
		// erzeugt neue Daten-Matrix
		Daten matrix = new Daten(rows, columns, entfernung);

		// kopiert this.data in matrix.daten
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				matrix.setValueAt(data[i][j], i, j);
		return matrix;
	}

	public String getColumnName(int column) {// Gibt den Spaltenname zurück
		return "";
	}

	public int getRowCount() {// Gibt die Zeilenanzahl der Matrix zurück
		return rows;
	}

	public int getColumnCount() {// Gibt die Spaltenanzahl der Matrix zurück
		return columns;
	}

	public int getEntfernung() {
		return entfernung;
	}

	public void setEntfernung(int entfernung) {
		this.entfernung = entfernung;
	}

	public Object getValueAt(int row, int column) {// Gibt den Attributwert der
													// Zeile/Spalte zurück
		return data[row][column];
	}

	public boolean isCellEditable(int row, int column) {
		if (row == 0 && column == 0)
			return false;
		else
			return true;
	}

	public void setValueAt(Object value, int row, int column) {
		if (row != 0 && column != 0
				&& !pruefeStringAufInt(value.toString(), true))// falls nicht
																// Headlines und
																// nicht Integer
		{
			JOptionPane.showMessageDialog(null, "Falscher Wert eingegeben!",
					"Matrixfehler", JOptionPane.WARNING_MESSAGE);
			return;
		}
		this.data[row][column] = value;
		fireTableCellUpdated(row, column);
	}
	
	public void setValue2At(Object value, int row, int column) {
		this.data[row][column] = value;
		fireTableCellUpdated(row, column);
	}

	public Object[] getRowVector() {
		Object rowVector[] = new Object[rows];
		for (int i = 0; i < rows; i++)
			rowVector[i] = data[i][0];
		return rowVector;
	}

	public Object[] getColumnVector() {
		Object columnVector[] = new Object[rows];
		for (int i = 0; i < rows; i++)
			columnVector[i] = data[0][i];
		return columnVector;
	}

	public Class<? extends Object> getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}

	public void setColumnCount(int columns) {
		this.columns = columns;
		this.fireTableStructureChanged();
	}

	public void setRowCount(int rows) {
		this.rows = rows;
		this.fireTableStructureChanged();
	}

	public boolean pruefeStringAufInt(String s, boolean allowNull) {
		if (allowNull && s.equals(""))
			return true;
		try {
			int wert;
			wert = Integer.parseInt(s);
			if (wert < 0)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}