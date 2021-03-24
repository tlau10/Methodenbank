package de.fh_konstanz.simubus.view;

import java.awt.Color;

/**
 * Die Klasse <code>TableColor</code> ist f�r Setzen von Tabelle Farbe.
 * 
 * @author Erkan Erpolat
 *
 */
public class TableColor {

	/** Tabelle Spalte */
	private int column;
	
	/** Tabelle Zeile*/
	private int row;
	
	/** Farbe von Tabelle */
	private Color color;
	
	/**
	 * Spalte zur�ckgeben
	 * 
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Spalte setzen
	 * 
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * Tabellen Zeile zur�ckliefern
	 * 
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Tabellen Zeile setzen
	 * 
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Tabellen Farbe zur�ckgeben
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Farbe setzen
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	
	
	
}
