/* Die Klasse stellt eine Tabelle dar, um Daten fr Kosten,
   Kunden, usw. einzugeben. Mit dem Konstruktor wird festgelegt,
   wieviele Zeilen die Tabelle haben soll. Die Methode
   getTabelle() liefert die erzeugte Tabelle zurck, welche in
   ein JScrollPane gebettet ist
 */

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class Tabelle extends JPanel {

	/**
	 * Serilizable Version ID for Class Version
	 */
	private static final long serialVersionUID = -7017399360556518482L;
	// privte Datenfelder der Klasse
	private JTable tableView;
	private JScrollPane scrollpane;
	private TableModel dataModel;

	private String[] spalten;
	private String[][] daten;

	// Konstruktor, dem ein int-Wert übergeben wird, welcher
	// die Anzahl Zeilen der zu erzeugenden Tabelle festlegt
	public Tabelle(int rows) {
		// Anzahl Spalten = 7
		spalten = new String[7];

		// Festlegung der Spaltennamen
		spalten[0] = "Kategorie";
		spalten[1] = "Kosten";
		spalten[2] = "Kunden";
		spalten[3] = "m\u00e4nnliche";
		spalten[4] = "weibliche";
		spalten[5] = "min.";
		spalten[6] = "max.";

		// 2-dimensionales String-Array, welches die Daten
		// der Tabelle darstellt
		daten = new String[rows][spalten.length];

		// Initialisierung der Tabellendaten
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < spalten.length; j++) {
				if (j == 0) {
					// erste Spalte der Tabelle (Kategorie) wird
					// fortlaufend durchnummeriert
					daten[i][j] = new String(String.valueOf(i + 1));
				} else {
					// Datenfelder der Tabelle werden mit leerem
					// String initialisiert
					daten[i][j] = new String("");
				}
			}
		}

		// Implementierung des Tabellenmodells
		dataModel = new AbstractTableModel() {
			/**
			 *
			 */
			private static final long serialVersionUID = 113696625691343929L;

			public int getColumnCount() {
				return spalten.length;
			}

			public int getRowCount() {
				return daten.length;
			}

			public Object getValueAt(int row, int col) {
				return daten[row][col];
			}

			public String getColumnName(int col) {
				return spalten[col];
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}

			public boolean isCellEditable(int row, int col) {
				return getColumnClass(col) == String.class;
			}

			public void setValueAt(Object aValue, int row, int col) {
				daten[row][col] = (String) aValue;
			}
		};

		// Instanzierung der Tabellenkomponente mit dem
		// Datenmodell
		tableView = new JTable(dataModel);

		// Einbettung der Tabelle in einen ScrollPane
		scrollpane = new JScrollPane(tableView);

		// Größe der Tabelle festlegen
		Dimension dim = new Dimension(453, 100);
		scrollpane.setPreferredSize(dim);
		scrollpane.setMaximumSize(dim);
	}

	// gibt die durch den Konstruktor erzeugte Tabelle zurck,
	// die in einen ScrollPane eingebettet ist
	public JScrollPane getTabelle() {
		return scrollpane;
	}

	// gibt den Wert in Zeile x, Spalte y zurck
	// der zurckgegebene Wert muss in der aufrufenden Methode
	// gecastet werden
	public Object getValueAt(int x, int y) {
		return dataModel.getValueAt(x, y);
	}

	public void setValueAt(int value, int x, int y) {
		dataModel.setValueAt("" + value, x, y);
	}
}
