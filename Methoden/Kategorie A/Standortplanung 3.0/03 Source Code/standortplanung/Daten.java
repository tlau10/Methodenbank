package standortplanung;

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

public class Daten {

	private int rows;
	private int columns;
	private int entfernung;
	Object daten[][];

	public Daten(int rows, int columns, int entfernung) {
		this.rows = rows;
		this.columns = columns;
		this.entfernung = entfernung;
		daten = new Object[rows + 1][columns + 1];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getEntfernung() {
		return entfernung;
	}

	public void setEntfernung(int entfernung) {
		this.entfernung = entfernung;
	}

	public void setValueAt(Object daten, int row, int column) {
		this.daten[row][column] = daten;
	}

	public Object getValueAt(int row, int column) {
		return daten[row][column];
	}

	public void setRowCount(int rows) {
		this.rows = rows;
	}

	public void setColumnCount(int columns) {
		this.columns = columns;
	}

	public Object[] getRowVector() {
		Object rowVector[] = new Object[rows];
		for (int i = 0; i < rows; i++)
			rowVector[i] = daten[i][0];
		return rowVector;
	}

	public Object[] getColumnVector() {
		Object columnVector[] = new Object[columns];
		for (int i = 0; i < columns; i++)
			columnVector[i] = daten[0][i];
		return columnVector;
	}

}