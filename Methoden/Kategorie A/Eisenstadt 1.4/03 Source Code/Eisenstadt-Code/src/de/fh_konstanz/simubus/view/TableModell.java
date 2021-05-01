package de.fh_konstanz.simubus.view;

import javax.swing.table.DefaultTableModel;

/**
 * Die Klasse <code>TableModell</code> präsentiert die Modell für die
 * JTable(Table)
 * 
 * @author Erkan Erpolat
 * 
 */
public class TableModell extends DefaultTableModel {

	/**
	 * Versions ID
	 */
	private static final long serialVersionUID = 6742995361445088122L;

	/**
	 * Konstruktor. TableModell mit Zeilen Inhalte und Spalten Namen anlegen.
	 * 
	 * @param rowData
	 * @param columnNames
	 */
	public TableModell(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * Festlegen ob die Zellen von Spalten editierbar sind
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}