package de.fh_konstanz.simubus.view;

/**
 * Die Klasse <code>ColumnProperties</code> beinhaltet die Spalten Breite und
 * weitere Spalten Eigenschaften werden hier festgelegt
 * 
 * @author Erkan Erpolat
 * 
 */
public class ColumnProperties {

	/** Tabellen Spalten */
	private int[] columns;

	/** Spalten Breiten */
	private int[] widths;

	/** Spalten Breite �nderbar */
	private boolean[] resizables;

	/**
	 * Konstruktor. Spalten, Spalten Breite und Spalten Breite �nderbar werden
	 * gesetzt.
	 * 
	 * @param columns
	 * @param widths
	 * @param resizables
	 */
	public ColumnProperties(int[] columns, int[] widths, boolean[] resizables) {
		this.columns = columns;
		this.widths = widths;
		this.resizables = resizables;
	}

	/**
	 * Spalten zur�ckgeben
	 * 
	 * @return
	 */
	public int[] getColumns() {
		return columns;
	}

	/**
	 * Spalten setzen
	 * 
	 * @param columns
	 */
	public void setColumns(int[] columns) {
		this.columns = columns;
	}

	/**
	 * Ob Spalte Breite �nderbar ist, zur�ckgeben.
	 * 
	 * @return
	 */
	public boolean[] getResizables() {
		return resizables;
	}

	/**
	 * Spalte Breite �nderbar setzen.
	 * 
	 * @param resizables
	 */
	public void setResizables(boolean[] resizables) {
		this.resizables = resizables;
	}

	/**
	 * Spalte Breiten werden zur�ck geliefert
	 * 
	 * @return
	 */
	public int[] getWidths() {
		return widths;
	}

	/**
	 * Spalte Breiten setzen
	 * 
	 * @param widths
	 */
	public void setWidths(int[] widths) {
		this.widths = widths;
	}

}
