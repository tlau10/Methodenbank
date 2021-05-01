package com.htwg.powerlp.controller;

import java.awt.Checkbox;
import java.awt.Component;
import java.util.Enumeration;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Hier werden die eingegebenen Daten so aufbereitet, dass sie gespeichert und
 * ge�ffnet werden k�nnen
 * 
 * @author Teamprojekt WS 2015/2016 (Ducho, Keller, Lagun, Lu, Pllana)
 * @version 1.0
 */
public class MatrixContentHandler {

	private static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private static String transformEntry(String s, int i, int j, int len) {
		String t = s;
		if (j > 0) {
			if (j != len - 2) {
				if (!isNumber(s.trim())) {
					if (!s.equals("max !")) {
						if (!s.equals("min !")) {
							t = "0";
						}
					}
				}
			}
		}
		return t;
	}

	/**
	 * Methode um die "eingegebenen" Daten aus dem Frame zu holen
	 * 
	 * @param internalFrame
	 *            aktive Frame
	 * @return Daten aus dem Frame
	 */
	public static String prepareInternalFrameContext(
			JInternalFrame internalFrame) {
		// Anlegen der Variablen
		JViewport viewport;
		JTable myTable;
		String tableValue = "";
		// Holen der Objekte des aktuellen Frames
		String fileData = internalFrame.getTitle() + "\r\n";
		JScrollPane mainScroller = (JScrollPane) internalFrame.getContentPane()
				.getComponent(0);
		viewport = mainScroller.getViewport();
		JPanel mainPanel = (JPanel) viewport.getView();
		// Objekte nach den passenden Komponenten durchsuchen
		JPanel[] componentPanel = new JPanel[mainPanel.getComponentCount()];
		String[] panelName = new String[mainPanel.getComponentCount()];
		for (int i = 0; i < componentPanel.length; i++) {
			componentPanel[i] = (JPanel) mainPanel.getComponent(i);
			panelName[i] = mainPanel.getComponent(i).getName();
		}
		// Werte der bestimmten Komponenten (nach Namen gesucht) auslesen
		// Alle Werte werden in einem String gespeichert (String wird f�r die
		// Datei vorbereitet)
		Component[] panelInformation = new Component[componentPanel[0]
				.getComponentCount()];
		for (int i = 0; i < panelInformation.length; i++) {
			panelInformation[i] = componentPanel[0].getComponent(i);
			switch (componentPanel[0].getComponent(i).getName()) {
			case "spinnerAmountValue":
				JSpinner spinnerAmountValue = (JSpinner) componentPanel[0]
						.getComponent(i);
				fileData = fileData + (int) spinnerAmountValue.getValue()
						+ "\r\n";
				break;
			case "spinnerAmountRestrictions":
				JSpinner spinnerAmountRestrictions = (JSpinner) componentPanel[0]
						.getComponent(i);
				fileData = fileData
						+ (int) spinnerAmountRestrictions.getValue() + "\r\n";
				break;
			case "comboBoxMinMax":
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxMinMax = (JComboBox<String>) componentPanel[0]
						.getComponent(i);
				fileData = fileData + (String) comboBoxMinMax.getSelectedItem()
						+ "\r\n";
				break;
			case "checkboxBorders":
				Checkbox checkboxBorders = (Checkbox) componentPanel[0]
						.getComponent(i);
				fileData = fileData + checkboxBorders.getState() + "\r\n";
				break;
			default:
			}
		}
		// Werte aus der Matrix holen
		Component[] panelMatrix = new Component[componentPanel[1]
				.getComponentCount()];
		panelMatrix[0] = componentPanel[1].getComponent(0);
		JScrollPane scrollPaneMatrix = (JScrollPane) panelMatrix[0];
		viewport = scrollPaneMatrix.getViewport();
		myTable = (JTable) viewport.getView();
		fileData = fileData + "Matrixtable_start" + "\r\n";
		for (int j = 0; j < myTable.getRowCount(); j++) {
			for (int k = 0; k < myTable.getColumnCount(); k++) {
				tableValue = (String) myTable.getValueAt(j, k);
				tableValue = transformEntry(tableValue, j, k,
						myTable.getColumnCount());
				if (tableValue == null || tableValue.equals("")) {
					fileData = fileData + "0" + ";";
				} else {
					fileData = fileData + tableValue + ";";
				}
			}
			fileData = fileData.substring(0, fileData.length() - 1);
			fileData = fileData + "\r\n";
		}
		fileData = fileData + "Matrixtable_end" + "\r\n";
		// Daten aus der Integer/Border Tabelle auslesen
		Component[] panelBorders = new Component[componentPanel[2]
				.getComponentCount()];
		panelBorders[0] = componentPanel[2].getComponent(0);
		JScrollPane scrollPaneBorders = (JScrollPane) panelBorders[0];
		viewport = scrollPaneBorders.getViewport();
		myTable = (JTable) viewport.getView();
		fileData = fileData + "Borderstable_start" + "\r\n";

		for (int l = 0; l < myTable.getRowCount(); l++) {
			for (int m = 0; m < myTable.getColumnCount(); m++) {
				tableValue = (String) myTable.getValueAt(l, m);
				tableValue = transformEntry(tableValue, l, m);
				if (tableValue == null && m < 3) {
					fileData = fileData + "" + ";";
				} else if (tableValue == null && m == 3) {
					fileData = fileData + "Nein" + ";";
				} else {
					fileData = fileData + tableValue + ";";
				}
			}
			// Das letzte ";" l�schen, um das Format beizubehalten
			fileData = fileData.substring(0, fileData.length() - 1);
			fileData = fileData + "\r\n";
		}
		fileData = fileData + "Borderstable_end";
		// String f�r *.lpi Datei weitergeben
		return fileData;
	}

	private static String transformEntry(String s, int i, int j) {
		String t = s;
		if (j == 1) {
			if (!isNumber(t)) {
				t = "0";
			}
		} else if (j == 2) {
			if (!isNumber(t)) {
				t = "0";
			}
		}
		return t;
	}

	/**
	 * Ausgelesene Daten aus der gespeicherten Datei in das neue Unterfenster
	 * schreiben
	 * 
	 * @param internalFrame
	 *            aktueller Frame
	 * @param data
	 *            Dateiinhalt
	 * @param optionComboCell
	 *            Combobox f�r die Matrix
	 * @param optionComboCellInteger
	 *            Combobox f�r Integer/Bounds
	 * @return leerer String
	 */
	public static String openContext(JInternalFrame internalFrame, String data,
			JComboBox<String> optionComboCell,
			JComboBox<String> optionComboCellInteger) {
		// Variablen anlegen
		JViewport viewport;
		JTable myTable;
		String[] dataField = data.trim().split("\r\n");
		// Holen der Objekte des aktuellen Frames
		JScrollPane mainScroller = (JScrollPane) internalFrame.getContentPane()
				.getComponent(0);
		viewport = mainScroller.getViewport();
		JPanel mainPanel = (JPanel) viewport.getView();

		// Objekte nach Namen holen und die Werte in jeweilige Komponente
		// schreiben
		JPanel[] componentPanel = new JPanel[mainPanel.getComponentCount()];
		String[] panelName = new String[mainPanel.getComponentCount()];
		for (int i = 0; i < componentPanel.length; i++) {
			componentPanel[i] = (JPanel) mainPanel.getComponent(i);
			panelName[i] = mainPanel.getComponent(i).getName();
		}
		Component[] panelInformation = new Component[componentPanel[0]
				.getComponentCount()];
		for (int i = 0; i < panelInformation.length; i++) {
			switch (componentPanel[0].getComponent(i).getName()) {
			case "spinnerAmountValue":
				JSpinner spinnerAmountValue = (JSpinner) componentPanel[0]
						.getComponent(i);
				spinnerAmountValue.setModel(new SpinnerNumberModel(new Integer(
						Integer.parseInt(dataField[0].trim())), new Integer(1),
						null, new Integer(1)));
				break;
			case "spinnerAmountRestrictions":
				JSpinner spinnerAmountRestrictions = (JSpinner) componentPanel[0]
						.getComponent(i);
				spinnerAmountRestrictions.setModel(new SpinnerNumberModel(
						new Integer(Integer.parseInt(dataField[1].trim())),
						new Integer(2), null, new Integer(1)));
				break;
			case "comboBoxMinMax":
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxMinMax = (JComboBox<String>) componentPanel[0]
						.getComponent(i);
				if (dataField[2].trim().equals("max !")) {
					comboBoxMinMax.setSelectedIndex(0);
				} else {
					comboBoxMinMax.setSelectedIndex(1);
				}
				break;
			case "checkboxBorders":
				Checkbox checkboxBorders = (Checkbox) componentPanel[0]
						.getComponent(i);
				if (dataField[3].trim().equals("true")) {
					checkboxBorders.setState(true);
				} else {
					checkboxBorders.setState(false);
				}
				break;
			default:
			}
		}

		// Werte in die Matrix schreiben, dabei wird der Anfang der
		// eingegebenen Variablen bestimmt und als start/end gespeichert
		int matrixtable_start = 0;
		int matrixtable_end = dataField.length;
		int matrixCounter = 0;
		for (int i = 0; i < matrixtable_end; i++) {
			if (dataField[i].trim().equals("Matrixtable_start")) {
				matrixtable_start = i + 1;
			}
			if (dataField[i].trim().equals("Matrixtable_end")) {
				matrixtable_end = i;
			}
		}
		String[] panelMatrixData = new String[matrixtable_end
				- matrixtable_start];
		for (int i = matrixtable_start; i < matrixtable_end; i++) {
			panelMatrixData[matrixCounter] = dataField[i];
			matrixCounter++;
		}
		// Die Standardwerte (Namen der Reihen, Spalten) in die Matrix schreiben
		String[] matrixColumnAmountValue = dataField[matrixtable_start]
				.split(";");
		Component[] panelMatrix = new Component[componentPanel[1]
				.getComponentCount()];
		panelMatrix[0] = componentPanel[1].getComponent(0);
		JScrollPane scrollPaneMatrix = (JScrollPane) panelMatrix[0];
		viewport = scrollPaneMatrix.getViewport();
		myTable = (JTable) viewport.getView();
		DefaultTableModel matrixTableModel = (DefaultTableModel) myTable
				.getModel();
		matrixTableModel.setRowCount(matrixtable_end - matrixtable_start);
		matrixTableModel.setColumnCount(matrixColumnAmountValue.length);
		// Header der Tabelle holen
		Enumeration<TableColumn> tcs;
		JTableHeader th;
		TableColumnModel tcm;
		TableColumn tc;
		th = myTable.getTableHeader();
		tcm = th.getColumnModel();
		tcs = tcm.getColumns();
		tcs.nextElement().setHeaderValue("------");
		for (int i = 1; i < matrixColumnAmountValue.length - 2; i++) {
			tc = tcs.nextElement();
			tc.setHeaderValue("X" + (i));
		}
		tc = tcs.nextElement();
		tc.setHeaderValue("");
		// Die Spalte mit ComboBox belegen, gilt f�r ganze Spalte
		tc.setCellEditor(new DefaultCellEditor(optionComboCell));

		tc = tcs.nextElement();
		tc.setHeaderValue("b");
		// Headerdaten schreiben (Matrixdaten schreiben)
		for (int r = 0; r < matrixtable_end - matrixtable_start; r++) {
			String[] matrixElement = panelMatrixData[r].trim().split(";");
			for (int c = 0; c < matrixColumnAmountValue.length; c++) {
				matrixTableModel.setValueAt((String) matrixElement[c], r, c);
			}
		}
		// Integer/Borders Tabelle mit Werten beschreiben. Anfang/Ende der Werte
		// der
		// Tabelle bestimmen
		int borderstable_start = 0;
		int borderstable_end = dataField.length;
		int bordersCounter = 0;
		for (int i = 0; i < borderstable_end; i++) {
			if (dataField[i].trim().equals("Borderstable_start")) {
				borderstable_start = i + 1;
			}
			if (dataField[i].trim().equals("Borderstable_end")) {
				borderstable_end = i;
			}
		}
		String[] panelBordersData = new String[borderstable_end
				- borderstable_start];
		for (int i = borderstable_start; i < borderstable_end; i++) {
			panelBordersData[bordersCounter] = dataField[i];
			bordersCounter++;
		}
		// Komponente der Tabelle f�r die Datenbeschreibung ausw�hlen
		Component[] panelBorders = new Component[componentPanel[2]
				.getComponentCount()];
		panelBorders[0] = componentPanel[2].getComponent(0);
		JScrollPane scrollPaneBorders = (JScrollPane) panelBorders[0];
		JViewport bordersViewport = scrollPaneBorders.getViewport();
		JTable bordersMyTable = (JTable) bordersViewport.getView();
		DefaultTableModel bordersTableModel = (DefaultTableModel) bordersMyTable
				.getModel();
		bordersTableModel.setRowCount(borderstable_end - borderstable_start);
		// Pr�fen ob die Tabelle sichtbar bzw. unsichtbar ist
		if (dataField[3].trim().equals("true")) {
			bordersMyTable.setVisible(true);
		} else {
			bordersMyTable.setVisible(false);
		}
		// Header belegen
		Enumeration<TableColumn> tcs_borders;
		JTableHeader th_borders;
		TableColumnModel tcm_borders;
		TableColumn tc_borders;
		th_borders = bordersMyTable.getTableHeader();
		tcm_borders = th_borders.getColumnModel();
		tcs_borders = tcm_borders.getColumns();
		tcs_borders.nextElement().setHeaderValue("");
		tcs_borders.nextElement().setHeaderValue("Lower");
		tcs_borders.nextElement().setHeaderValue("Upper");
		tc_borders = tcs_borders.nextElement();
		tc_borders.setHeaderValue("Integer");
		tc_borders.setCellEditor(new DefaultCellEditor(optionComboCellInteger));
		// Tabelle mit Werten beschreiben
		for (int i = 0; i < borderstable_end - borderstable_start; i++) {
			bordersTableModel.setValueAt("X" + (i + 1), i, 0);
		}

		for (int r = 0; r < borderstable_end - borderstable_start; r++) {
			String[] bordersElement = panelBordersData[r].split(";");
			for (int c = 1; c < 4; c++) {
				bordersTableModel.setValueAt((String) bordersElement[c], r, c);
			}
		}
		internalFrame.repaint();
		return "";
	}
}
