package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 * Die Klasse <code>SelectTable</code> ist eine Event Klasse f�rs Spalten,
 * Zellen, Zeilen. Es beinhaltet die Funktionalit�t von Kontext Men�.
 * 
 * 
 * @author Erkan Erpolat
 * 
 */
public class SelectTable extends MouseAdapter {

	/** Popup Men�s Werte */
	private String[] popUpMenuValues = { "Alles markieren", "Kopieren",
			"Zelle Farbe ausw�hlen", "Zeile Farbe ausw�hlen",
			"Spalte Farbe ausw�hlen", "Hintergrund Farbe ausw�hlen" };

	/** Instanz von JPopupMen� */
	private JPopupMenu menu;

	/** Inhalt von JPopupMen� */
	private JMenuItem menuItem;

	/** L�sungs Tabelle */
	private Table tables;

	/** JTable Instanz */
	private JTable table;

	/** Koordinaten von Selektierten Zellen */
	private Point point;

	/**
	 * Konstruktor. Tabellen Instanzen �bergeben
	 * 
	 * @param resultTable
	 */
	public SelectTable(Table selectTable) {
		this.tables = selectTable;
		table = selectTable.getTable();
	}

	/**
	 * Listener wenn eine Zelle in der Tabelle angeklickt wird
	 * 
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {

		// Es wird auch nur angezeigt, wenn eine Zeile oder oder mehrere Zeilen
		// ausgew�hlt sind.
		if (SwingUtilities.isRightMouseButton(e)
				&& table.getSelectedRowCount() > 0) {

			createPopupMenu();
			point = e.getPoint();
			menu.show((Component) e.getSource(), point.x, point.y);
		}
	}

	/**
	 * PopupMen� wird mit Werten gef�llt
	 * 
	 */
	private void createPopupMenu() {

		menu = new JPopupMenu();
		for (int i = 0; i < popUpMenuValues.length; i++) {
			menuItem = new JMenuItem(popUpMenuValues[i]);
			menu.add(menuItem);
			menuItem.addActionListener(listener);

		}

	}

	/**
	 * Listener Farbenauswahl f�r Spalten, Zellen, Zeilen, ganzes Tabelle
	 * 
	 */
	private ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("Spalte Farbe ausw�hlen") == true) {
				Color color = getSelectedColor();
				int[] selectedColumns = table.getSelectedColumns();
				tables.changeColumnsColor(color, table.getRowCount(),
						selectedColumns);

			}

			else if (e.getActionCommand().equals("Zelle Farbe ausw�hlen") == true) {
				Color color = getSelectedColor();
				int[] selectedColumns = table.getSelectedColumns();
				int[] selectedRows = table.getSelectedRows();
				tables.changeCellsColor(color, selectedRows, selectedColumns);

			}

			else if (e.getActionCommand().equals("Zeile Farbe ausw�hlen") == true) {
				Color color = getSelectedColor();
				int[] selectedRows = table.getSelectedRows();
				tables.changeRowBackgroundColor(color, selectedRows, table
						.getColumnCount());

			}

			else if (e.getActionCommand().equals("Hintergrund Farbe ausw�hlen") == true) {
				Color color = getSelectedColor();
				tables.changeTableBackgroundColor(color, table.getRowCount(),
						table.getColumnCount());
			}

			else if (e.getActionCommand().equals("Kopieren") == true) {
				e.setSource(table);
				ActionListener copyAction2 = table.getActionMap().get("copy");
				copyAction2.actionPerformed(e);
			}

			else if (e.getActionCommand().equals("Alles markieren") == true) {
				table.selectAll();
			}

		}
	};

	/**
	 * Farbpalette zur Verf�gung stellen bzw. einblenden
	 * 
	 * @return
	 */
	private Color getSelectedColor() {
		Color newColor = JColorChooser.showDialog(null, "W�hle neue Farbe",
				Color.red);
		return newColor;
	}

}
