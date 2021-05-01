package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 * Die Klasse <code>Table</code> präsentiert eine Tabelle. Mit dieser Klasse
 * können weitere Tabellen angelegt werden
 * 
 * @author Erkan Erpolat
 * 
 */
public class Table {

	private static final long serialVersionUID = 6742995361445088122L;

	/** Schieberegler fürs Tabellen */
	private JScrollPane scrollPane;

	/** Spalten Namen fürs Tabellen */
	private Object[] columnNames;

	/** Zeilen Inhalte fürs Tabellen */
	private Object[][] rowData;

	/** Tabellen Breite */
	private int tableWidth;

	/** Tabelle Instanz */
	private JTable table;

	/** Tabellen Modell Instanz */
	private TableModell tableModell;

	/** Default Zellen Renderer */
	private DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

	/** Tabellen Spalten Modell */
	private TableColumnModel tableColumnModel;

	/** Ausgewählte Tabellen Elemente */
	private Map<Integer, TableColor> selectedTableItems = new Hashtable<Integer, TableColor>();

	/** Zähler für ausgewählten Tabellen Elemente */
	private int counter = 0;

	/**
	 * Konstruktor. Es werden Tabellen Breite und Spalten Namen übergeben
	 * 
	 * @param width
	 * @param columnNames
	 */
	public Table(int width, Object[] columnNames) {
		this.columnNames = columnNames;
		this.tableWidth = width;
	}

	/**
	 * Zeilen Inhalte zurückliefern
	 * 
	 * @return
	 */
	public Object[][] getRowData() {
		return rowData;
	}

	/**
	 * Zeilen Inhalte setzen
	 * 
	 * @param rowData
	 */
	public void setRowData(Object[][] rowData) {
		this.rowData = rowData;
	}

	/**
	 * Schieberegler fürs Tabelle zurückgeben
	 * 
	 * @param rowColors
	 * @return
	 */
	public JScrollPane getScrollPane(String tableName) {

		tableModell = new TableModell(this.getRowData(), columnNames);
		table = new JXTable(tableModell) {

			/**
			 * JXTable Versions ID
			 */
			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				if (row % 2 == 0)
					c.setBackground(Color.LIGHT_GRAY);

				for (int i = 0; i < selectedTableItems.size(); i++) {
					TableColor tableColor = (TableColor) selectedTableItems
							.get(new Integer(i));

					if (row == tableColor.getRow()
							&& column == tableColor.getColumn())
						c.setBackground(tableColor.getColor());
				}
				return c;
			}
		};
		table.setName(tableName);

		/**
		 * Spalten Namen zentrieren
		 */
		cellRenderer.setHorizontalTextPosition(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, cellRenderer);
		table.setSize(new Dimension(this.getTableWidth(), table.getRowCount()
				* table.getRowHeight()));
		table.setCellSelectionEnabled(true);
		renderColumns();
		scrollPane = new JScrollPane(table);

		if (table.getName().equals("Ergebnisse") == true) {

			/**
			 * Lösungs Spalte Sorter
			 * 
			 */
			Comparator integerComparator = new Comparator() {
				public int compare(Object o1, Object o2) {
					Integer d1 = Integer
							.valueOf(o1 == null ? "0" : (String) o1);
					Integer d2 = Integer
							.valueOf(o2 == null ? "0" : (String) o2);
					return d1.compareTo(d2);
				}
			};

			/**
			 * Durchschnittszeit Spalte Sorter
			 */
			Comparator doubleComparator = new Comparator() {
				public int compare(Object o1, Object o2) {
					Double d1 = Double.valueOf(o1 == null ? "0" : (String) o1);
					Double d2 = Double.valueOf(o2 == null ? "0" : (String) o2);
					return d1.compareTo(d2);
				}
			};

			TableColumnModel tableModell = table.getColumnModel();
			int columnIndex = tableModell.getColumnIndex(String
					.valueOf("Lösung"));
			TableColumn col = tableModell.getColumn(columnIndex);

			if (col instanceof TableColumnExt) {
				TableColumnExt column = (TableColumnExt) col;
				column.setComparator(integerComparator);
			}

			columnIndex = tableModell.getColumnIndex(String
					.valueOf("Durchschnittszeit"));
			col = tableModell.getColumn(columnIndex);

			if (col instanceof TableColumnExt) {
				TableColumnExt column = (TableColumnExt) col;
				column.setComparator(doubleComparator);
			}
			
			
			columnIndex = tableModell.getColumnIndex(String.valueOf("Gesamtzeit"));
			col = tableModell.getColumn(columnIndex);
			
			if(col instanceof TableColumnExt) {
				TableColumnExt column = (TableColumnExt) col;
				column.setComparator(doubleComparator);
			}
			
			

		}

		return scrollPane;
	}

	/**
	 * Tabellen Breite zurückgeben
	 * 
	 * @return
	 */
	public int getTableWidth() {
		return tableWidth;
	}

	/**
	 * Spalten Breite ändern
	 * 
	 * @param columnsWidth
	 */
	public void changeColumnWidth(ColumnProperties columnsWidth) {
		TableColumnModel tableColumn = table.getColumnModel();

		int[] columns = columnsWidth.getColumns();
		int[] widths = columnsWidth.getWidths();
		boolean[] resizables = columnsWidth.getResizables();

		for (int i = 0; i < columns.length; i++) {

			tableColumn.getColumn(columns[i]).setWidth(widths[i]);
			tableColumn.getColumn(columns[i]).setPreferredWidth(widths[i]);
			tableColumn.getColumn(columns[i]).setResizable(resizables[i]);
		}
	}

	/**
	 * Tabellen Inhalte zentrieren
	 * 
	 */
	public void renderColumns() {

		// Renderer für Tabelle bauen
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableColumnModel = table.getColumnModel();
		int columnCount = tableColumnModel.getColumnCount();

		for (int i = 0; i < columnCount; i++)
			tableColumnModel.getColumn(i).setCellRenderer(cellRenderer);

	}

	/**
	 * Tabelle Instanz zurückgeben
	 * 
	 * @return
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Tabelle Model zurückgeben
	 * 
	 * @return
	 */
	public TableModell getTableModell() {
		return tableModell;
	}

	/**
	 * Von ausgewählten Zellen die Farben ändern
	 * 
	 * @param color
	 * @param rows
	 * @param columns
	 */
	public void changeCellsColor(Color color, int[] rows, int[] columns) {

		for (int i = 0; i < rows.length; i++) {

			for (int j = 0; j < columns.length; j++) {

				if (table.isCellSelected(rows[i], columns[j]) == true) {
					TableColor tableColor = new TableColor();
					tableColor.setColor(color);
					tableColor.setRow(rows[i]);
					tableColor.setColumn(columns[j]);
					selectedTableItems.put(new Integer(counter), tableColor);
					++counter;
				}

			}

		}

		table.repaint();

	}

	/**
	 * Von ausgewählten Spalten die Farben ändern
	 * 
	 * @param color
	 * @param rows
	 * @param columns
	 */
	public void changeColumnsColor(Color color, int rows, int[] columns) {

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < columns.length; j++) {
				TableColor tableColor = new TableColor();
				tableColor.setColor(color);
				tableColor.setRow(i);
				tableColor.setColumn(columns[j]);
				selectedTableItems.put(new Integer(counter), tableColor);
				++counter;
			}

		}

		table.repaint();
	}

	/**
	 * Tabellen Hintergrund Farbe ändern
	 * 
	 * @param color
	 * @param rows
	 * @param columns
	 */
	public void changeTableBackgroundColor(Color color, int rows, int columns) {

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < columns; j++) {
				TableColor tableColor = new TableColor();
				tableColor.setColor(color);
				tableColor.setRow(i);
				tableColor.setColumn(j);
				selectedTableItems.put(new Integer(counter), tableColor);
				++counter;
			}
		}

		table.repaint();
	}

	/**
	 * Tabellen Zeilen Farbe ändern
	 * 
	 * @param color
	 * @param rows
	 * @param columns
	 */
	public void changeRowBackgroundColor(Color color, int[] rows, int columns) {

		for (int i = 0; i < rows.length; i++) {

			for (int j = 0; j < columns; j++) {
				TableColor tableColor = new TableColor();
				tableColor.setColor(color);
				tableColor.setRow(rows[i]);
				tableColor.setColumn(j);
				selectedTableItems.put(new Integer(counter), tableColor);
				++counter;
			}
		}
		table.repaint();
	}

}