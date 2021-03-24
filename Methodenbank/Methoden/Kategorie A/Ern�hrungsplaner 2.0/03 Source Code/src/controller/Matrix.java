package controller;

import view.ErrorMessages;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: Matrix
 * </p>
 * <p>
 * Description: Erstellt die zu berechnende Matrix
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */
public class Matrix {
	// Zweidimensionales Array zur Aufnahme der Daten
	private String[][] core;
	private int numberOfRows;
	private int numberOfColumns;
	private int upperboundRows;
	private int upperboundColumns;

	public Matrix(int rows, int columns, int upperR, int upperC) {
		this.setUpperboundRows(upperR);
		this.setUpperboundColumns(upperC);
		this.setNumberOfRows(rows);
		this.setNumberOfColumns(columns);

		// Wenn eingegebene Dimensionen die obere Beschränkung übersteigt
		// werden die oberen Beschränkungen als Dimensionen gesetzt
		if (columns > this.getUpperboundColumns())
			this.setNumberOfColumns(this.getUpperboundColumns());

		if (rows > this.getUpperboundRows())
			this.setNumberOfRows(this.getUpperboundRows());

		// Array wird mit seinen neuen Abmessungen initialisiert
		core = new String[this.getNumberOfRows()][this.getNumberOfColumns()];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (j == numberOfColumns - 2)
					core[i][j] = new String("=");
				else
					core[i][j] = new String("0");
			}
		}
	}

	// Methode zum Setzten des Wertes an einer bestimmten Stelle des Arrays
	public void setElement(int rows, int column, String elem) {

		// Wenn der Wert in die Matrix passt bestimmt durch 2 Indizes
		if (rows < numberOfRows && column < numberOfColumns)
			core[rows][column] = new String(elem);
		else {
			// Pruefen, ob obere Grenzen verletzt sind
			if (rows >= upperboundRows || column >= upperboundColumns)
				ErrorMessages
						.throwErrorMessage("Grenzen der Matrix ueberschritten");
			int numberOfRowsNew = numberOfRows;
			if (rows >= numberOfRows)
				numberOfRowsNew = rows + 1;
			if (column >= numberOfColumns)
				ErrorMessages
						.throwErrorMessage("Spalten der Matrix dürfen nach initialisieren"
								+ " der Matrix nicht mehr verändert werden");
			String[][] core2 = new String[numberOfRowsNew][numberOfColumns];

			// Array initalisieren
			for (int i = 0; i < numberOfRowsNew; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					// Vorletzte Spalte mit "=" initialisieren
					if (j == numberOfColumns - 2)
						core2[i][j] = new String("=");
					else
						core2[i][j] = new String("0");
				}
			}

			// Alte Werte kopieren
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					core2[i][j] = core[i][j];
				}
			}
			numberOfRows = numberOfRowsNew;
			core = core2;
			// Neuen Wert setzen
			core[rows][column] = new String(elem);

		}
	}

	public void setElement(int row, int column, int elem) {
		if (row < numberOfRows && column < numberOfColumns)
			core[row][column] = new String(Integer.toString(elem));
		else {
			// Pruefen, ob obere Grenzen verletzt sind
			if (row >= upperboundRows || column >= upperboundColumns)
				ErrorMessages
						.throwErrorMessage("Grenzen der Matrix ueberschritten");
			int numberOfRowsNew = numberOfRows;
			if (row >= numberOfRows)
				numberOfRowsNew = row + 1;
			if (column >= numberOfColumns)
				ErrorMessages
						.throwErrorMessage("Spalten der Matrix dürfen nach initialisieren"
								+ " der Matrix nicht mehr verändert werden");
			String[][] core2 = new String[numberOfRowsNew][numberOfColumns];

			// Array initalisieren
			for (int i = 0; i < numberOfRowsNew; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					// Vorletzte Spalte mit "=" initialisieren
					if (j == numberOfColumns - 2)
						core2[i][j] = new String("=");
					else
						core2[i][j] = new String("0");
				}
			}
			// Alte Werte kopieren
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					core2[i][j] = core[i][j];
				}
			}
			numberOfRows = numberOfRowsNew;
			core = core2;
			// Neuen Wert setzen
			core[row][column] = new String(Integer.toString(elem));
		}
	}

	public void setElement(int row, int column, double elem) {

		if (row < numberOfRows && column < numberOfColumns)
			core[row][column] = new String(Double.toString(elem));
		else {
			// Pruefen, ob obere Grenzen verletzt sind
			if (row >= upperboundRows || column >= upperboundColumns)
				ErrorMessages
						.throwErrorMessage("Grenzen der Matrix ueberschritten");
			int numberOfRowsNew = numberOfRows;
			if (row >= numberOfRows)
				numberOfRowsNew = row + 1;
			if (column >= numberOfColumns)
				ErrorMessages
						.throwErrorMessage("Spalten der Matrix dürfen nach initialisieren"
								+ " der Matrix nicht mehr verändert werden");
			String[][] core2 = new String[numberOfRowsNew][numberOfColumns];

			// Array initalisieren
			for (int i = 0; i < numberOfRowsNew; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					// Vorletzte Spalte mit "=" initialisieren
					if (j == numberOfColumns - 2)
						core2[i][j] = new String("=");
					else
						core2[i][j] = new String("0");
				}
			}
			// Alte Werte kopieren
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfColumns; j++) {
					core2[i][j] = core[i][j];
				}
			}
			numberOfRows = numberOfRowsNew;
			core = core2;
			// Neuen Wert setzen
			core[row][column] = new String(Double.toString(elem));
		}
	}

	public void setNumberOfRows(int elem) {
		numberOfRows = elem;
	}

	public void setNumberOfColumns(int elem) {
		numberOfColumns = elem;
	}

	public void setUpperboundRows(int elem) {
		upperboundRows = elem;
	}

	public void setUpperboundColumns(int elem) {
		upperboundColumns = elem;
	}

	public String getElement(int zeile, int spalte) {
		return core[zeile][spalte];
	}

	public int getElementInt(int zeile, int spalte) {
		return Integer.parseInt(core[zeile][spalte]);
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public int getUpperboundRows() {
		return upperboundRows;
	}

	public int getUpperboundColumns() {
		return upperboundColumns;
	}

}