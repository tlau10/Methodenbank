package com.htwg.powerlp.controller;

import java.awt.Checkbox;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

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

import com.htwg.powerlp.util.Configurator;
import com.htwg.powerlp.view.InternalFrame;

public class OpenMps {

	protected String fileData = "";
	String[] panelMatrixData;
	String[] panelMatrixFunctionTypes;
	boolean integerYesNo;

	public String openContext(JInternalFrame internalFrame, String data, JComboBox<String> optionComboCell,
			JComboBox<String> optionComboCellInteger, Configurator configurator, Controller controller) {
		// Variablen anlegen
		JViewport viewport;
		JTable myTable;

		// DATAFIELD
		String[] dataField = data.trim().split("\r\n");
		dataField = checkForInteger(dataField);

		// Holen der Objekte des aktuellen Frames
		JScrollPane mainScroller = (JScrollPane) internalFrame.getContentPane().getComponent(0);
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
		Component[] panelInformation = new Component[componentPanel[0].getComponentCount()];

		int startIndexRestrictions = 0;

		// DATAFIELD
		int stopIndexRestrictions = dataField.length;

		for (int i = 0; i < stopIndexRestrictions; i++) {

			// DATAFIELD
			if (dataField[i].trim().equals("ROWS")) {
				startIndexRestrictions = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("COLUMNS")) {
				stopIndexRestrictions = i;
			}
		}

		// Subtract 1 to remove the target function from the amount of
		// restrictions
		Integer amountRestrictions = (stopIndexRestrictions - startIndexRestrictions) - 1;

		int totalAmountFunctions = amountRestrictions + 1;
		int amountVariables = getAmountVariables(dataField, amountRestrictions);

	
		for (int i = 0; i < panelInformation.length; i++) {
			switch (componentPanel[0].getComponent(i).getName()) {
			case "spinnerAmountValue":
				JSpinner spinnerAmountValue = (JSpinner) componentPanel[0].getComponent(i);
				spinnerAmountValue
						.setModel(new SpinnerNumberModel(amountVariables, new Integer(1), null, new Integer(1)));
				break;
			case "spinnerAmountRestrictions":
				JSpinner spinnerAmountRestrictions = (JSpinner) componentPanel[0].getComponent(i);
				spinnerAmountRestrictions
						.setModel(new SpinnerNumberModel(amountRestrictions, new Integer(2), null, new Integer(1)));
				break;
			case "comboBoxMinMax":
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBoxMinMax = (JComboBox<String>) componentPanel[0].getComponent(i);
				if (dataField[0].toUpperCase().contains("MIN")) {
					comboBoxMinMax.setSelectedIndex(1);
				} else {
					comboBoxMinMax.setSelectedIndex(0);
				}
				break;
			case "checkboxBorders":
				Checkbox checkboxBorders = (Checkbox) componentPanel[0].getComponent(i);
				checkboxBorders.setState(false);
				for (int p = 0; p < dataField.length; p++) {
					if (dataField[p].toUpperCase().contains("BOUNDS")) {
						if (dataField[p + 1].charAt(0) == ' ') {
							checkboxBorders.setState(true);
							break;
						}
					} else {
						continue;
					}
				}
				break;
			default:
			}
		}

		// Set panelMatrix and insert function names
		panelMatrixData = new String[totalAmountFunctions];
		panelMatrixFunctionTypes = new String[totalAmountFunctions];

		char[] matrixNameAndType = null;
		int index = 0;

		for (int i = startIndexRestrictions; i < stopIndexRestrictions; i++) {
			matrixNameAndType = dataField[i].toCharArray();

			// Get function type and insert it into panelMatrixFunctionTypes
			// array
			switch (matrixNameAndType[1]) {
			case 'N':
				panelMatrixFunctionTypes[index] = "-->";
				break;
			case 'L':
				panelMatrixFunctionTypes[index] = "<=";
				break;
			case 'G':
				panelMatrixFunctionTypes[index] = ">=";
				break;
			case 'E':
				panelMatrixFunctionTypes[index] = "=";
				break;
			default:
				break;
			}
			// Get function name
			String functionName = "";
			for (int k = 4; k < matrixNameAndType.length; k++) {
				if (matrixNameAndType[k] != ' ') {
					functionName = functionName + Character.toString(matrixNameAndType[k]);
				} else {
					break;
				}
			}
			panelMatrixData[index] = functionName;
			index++;
		}

		// Import of variables into panelMatrixData array

		// Today:
		panelMatrixData = addVariables(dataField, amountRestrictions, panelMatrixData);
		panelMatrixData = addFunctionType(panelMatrixData, panelMatrixFunctionTypes);
		panelMatrixData = addRhs(panelMatrixData, dataField, amountRestrictions);

		

		Component[] panelMatrix = new Component[componentPanel[1].getComponentCount()];
		panelMatrix[0] = componentPanel[1].getComponent(0);
		JScrollPane scrollPaneMatrix = (JScrollPane) panelMatrix[0];
		viewport = scrollPaneMatrix.getViewport();
		myTable = (JTable) viewport.getView();
		DefaultTableModel matrixTableModel = (DefaultTableModel) myTable.getModel();

		// Set amount of restrictions
		matrixTableModel.setRowCount(totalAmountFunctions);
		// Set amount of variables
		matrixTableModel.setColumnCount(amountVariables + 3);
		// Header der Tabelle holen
		Enumeration<TableColumn> tcs;
		JTableHeader th;
		TableColumnModel tcm;
		TableColumn tc;
		th = myTable.getTableHeader();
		tcm = th.getColumnModel();
		tcs = tcm.getColumns();
		tcs.nextElement().setHeaderValue("------");

		// Paste variable names into the matrix header
		String[] variableNames = getVariableNames(dataField, amountRestrictions);

		for (int i = 0; i < variableNames.length; i++) {
			tc = tcs.nextElement();
			tc.setHeaderValue(variableNames[i]);
		}

		tc = tcs.nextElement();
		tc.setHeaderValue("");
		// Die Spalte mit ComboBox belegen, gilt f�r ganze Spalte
		tc.setCellEditor(new DefaultCellEditor(optionComboCell));
		tc = tcs.nextElement();
		tc.setHeaderValue("b");

		// Headerdaten schreiben (Matrixdaten schreiben)
		for (int r = 0; r < stopIndexRestrictions - startIndexRestrictions; r++) {
			String[] matrixElement = panelMatrixData[r].trim().split(";");

			// c < Variable Amount + 3
			for (int c = 0; c < amountVariables + 3; c++) {
				matrixTableModel.setValueAt((String) matrixElement[c], r, c);
			}
		}

		String[] panelBordersData = getPanelBorderMatrix(dataField, amountRestrictions);
		int borderRows = panelBordersData.length;

		

		// Komponente der Tabelle f�r die Datenbeschreibung ausw�hlen
		Component[] panelBorders = new Component[componentPanel[2].getComponentCount()];
		panelBorders[0] = componentPanel[2].getComponent(0);
		JScrollPane scrollPaneBorders = (JScrollPane) panelBorders[0];
		JViewport bordersViewport = scrollPaneBorders.getViewport();
		JTable bordersMyTable = (JTable) bordersViewport.getView();
		DefaultTableModel bordersTableModel = (DefaultTableModel) bordersMyTable.getModel();

		bordersTableModel.setRowCount(borderRows);

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
		for (int i = 0; i < borderRows; i++) {
			bordersTableModel.setValueAt("X" + (i + 1), i, 0);
		}

		for (int r = 0; r < borderRows; r++) {
			String[] bordersElement = panelBordersData[r].split(";");
			for (int c = 0; c < 4; c++) {
				bordersTableModel.setValueAt((String) bordersElement[c], r, c);
			}
		}
		
		try {
			internalFrame = InternalFrame.createFrame(internalFrame.getName(), 1000, controller, configurator).getInteralFrame();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String[] checkForInteger(String[] datafield) {
		String marker = "MARKER";
		integerYesNo = false;
		for (int i = 0; i < datafield.length; i++) {
			if (datafield[i].contains(marker)) {
				datafield = removeCurrentIndice(datafield, i);
				integerYesNo = true;
			}
		}
		return datafield;
	}

	private String[] removeCurrentIndice(String[] datafield, int index) {
		for (int i = index; index < datafield.length; index++) {
			if (index < datafield.length) {
				datafield[i] = datafield[i + 1];
			}
		}
		return datafield;
	}

	int getAmountVariables(String[] dataField, int amountRestrictions) {
		int startIndexVariables = 0;
		int stopIndexVariables = dataField.length;
		int amountVariables = 0;
		String[] variableNames = new String[10000];
		int counter = 0;

		for (int i = 0; i < stopIndexVariables; i++) {

			// DATAFIELD
			if (dataField[i].trim().equals("COLUMNS")) {
				startIndexVariables = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("RHS")) {
				stopIndexVariables = i;
			}
		}

		for (int i = startIndexVariables; i < stopIndexVariables; i++) {
			char[] matrixColumnAmountValue = null;
			matrixColumnAmountValue = dataField[i].toCharArray();

			String currentVariableName = String.valueOf(matrixColumnAmountValue[4])
					+ String.valueOf(matrixColumnAmountValue[5]) + String.valueOf(matrixColumnAmountValue[6])
					+ String.valueOf(matrixColumnAmountValue[7]) + String.valueOf(matrixColumnAmountValue[8])
					+ String.valueOf(matrixColumnAmountValue[9]) + String.valueOf(matrixColumnAmountValue[10])
					+ String.valueOf(matrixColumnAmountValue[11]);
			currentVariableName = currentVariableName.trim();
			if (stringExistInArray(currentVariableName, variableNames) == false) {
				variableNames[counter] = currentVariableName;
				counter++;
			}
		}

		int i = 0;

		while (variableNames[i] != null && !variableNames[i].isEmpty()) {
			amountVariables++;
			i++;
		}
		return amountVariables;
	}

	boolean stringExistInArray(String currentVariableName, String[] variableNames) {

		for (int i = 0; i < variableNames.length; i++) {
			if (currentVariableName.equals(variableNames[i])) {
				return true;
			}
		}
		return false;
	}

	boolean containsBounds(String[] dataField) {
		for (int i = 0; i < dataField.length; i++) {
			if (dataField[i].trim().equals("BOUNDS")) {
				return true;
			}
		}
		return false;
	}

	String[] addVariables(String[] dataField, int amountRestrictions, String[] panelMatrixData) {

		String[] variableNames = getVariableNames(dataField, amountRestrictions);
		String[] restrictionNames = getRestrictionNames(dataField, amountRestrictions);

		for (int i = 0; i < panelMatrixData.length; i++) {

			for (int v = 0; v < variableNames.length; v++) {
				panelMatrixData[i] = panelMatrixData[i] + ";"
						+ getVariableValue(restrictionNames[i], variableNames[v], dataField);
				if (v == variableNames.length - 1) {
					break;
				}
			}
		}
		return panelMatrixData;
	}

	String[] getVariableNames(String[] dataField, int amountRestrictions) {
		int startIndexVariables = 0;
		int stopIndexVariables = dataField.length;

		int counter = 0;

		for (int i = 0; i < stopIndexVariables; i++) {

			// DATAFIELD
			if (dataField[i].trim().equals("COLUMNS")) {
				startIndexVariables = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("RHS")) {
				stopIndexVariables = i;
			}
		}

		String[] variableNames = new String[getAmountVariables(dataField, amountRestrictions)];

		for (int i = startIndexVariables; i < stopIndexVariables; i++) {
			char[] matrixColumnAmountValue = null;
			matrixColumnAmountValue = dataField[i].toCharArray();
			String currentVariableName = "";

			for (int x = 4; x < 11; x++) {
				currentVariableName = currentVariableName + String.valueOf(matrixColumnAmountValue[x]);
			}

			currentVariableName = currentVariableName.trim();
			if (stringExistInArray(currentVariableName, variableNames) == false) {
				variableNames[counter] = currentVariableName;
				counter++;
			}
		}
		return variableNames;
	}

	String[] getRestrictionNames(String[] dataField, int amountRestrictions) {
		int startIndexRestrictions = 0;
		int stopIndexRestrictions = dataField.length;
		String[] restrictionNames = new String[(amountRestrictions + 1)];
		int counter = 0;

		for (int i = 0; i < stopIndexRestrictions; i++) {

			// DATAFIELD
			if (dataField[i].trim().equals("ROWS")) {
				startIndexRestrictions = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("COLUMNS")) {
				stopIndexRestrictions = i;
			}
		}

		for (int i = startIndexRestrictions; i < stopIndexRestrictions; i++) {
			char[] matrixColumnAmountValue = null;
			matrixColumnAmountValue = dataField[i].toCharArray();
			String currentVariableName = "";

			for (int x = 4; x < matrixColumnAmountValue.length; x++) {
				currentVariableName = currentVariableName + String.valueOf(matrixColumnAmountValue[x]);
			}

			currentVariableName = currentVariableName.trim();
			if (stringExistInArray(currentVariableName, restrictionNames) == false) {
				restrictionNames[counter] = currentVariableName;
				counter++;
			}
		}
		return restrictionNames;
	}

	String getVariableValue(String restrictionName, String variableName, String[] dataField) {
		String value = "";
		int stopIndexVariables = dataField.length;
		int startIndexVariables = 0;
		char[] matrixColumnAmountValue = null;

		// Check indices for the variables
		for (int i = 0; i < stopIndexVariables; i++) {

			// DATAFIELD
			if (dataField[i].trim().equals("COLUMNS")) {
				startIndexVariables = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("RHS")) {
				stopIndexVariables = i;
			}
		}

		for (int i = startIndexVariables; i < stopIndexVariables; i++) {

			matrixColumnAmountValue = dataField[i].toCharArray();

			if (matrixColumnAmountValue[14] != ' ' && matrixColumnAmountValue.length < 40) {

				// Search for the searched Value by identifying the restriction
				// and variable
				String row = "";

				for (Character c : matrixColumnAmountValue)
					row += c.toString();

				if (row.contains(restrictionName) && row.contains(variableName)) {
					// Copy string from left to get the Value
					value = getValueOfFirstCell(matrixColumnAmountValue);
					return value;
				}

			} else if (matrixColumnAmountValue.length > 40) {
				char[] firstCell = Arrays.copyOfRange(matrixColumnAmountValue, 0, 38);

				String row = "";

				for (Character c : firstCell)
					row += c.toString();

				if (row.contains(restrictionName) && row.contains(variableName)) {
					// Copy string from left to get the Value
					value = getValueOfFirstCell(firstCell);
					return value;
				}
				char[] secondCell = Arrays.copyOfRange(matrixColumnAmountValue, 0, matrixColumnAmountValue.length);

				row = "";

				for (Character c : secondCell)
					row += c.toString();

				if (row.contains(restrictionName) && row.contains(variableName)) {
					// Copy string from left to get the Value
					value = getValueOfSecondCell(secondCell);
					return value;
				}
			}
		}
		return "0";
	}

	String getValueOfFirstCell(char[] firstCell) {
		String result = "";

		for (int i = 24; i < firstCell.length; i++) {
			result = result + String.valueOf(firstCell[i]);
		}
		result = result.trim();
		return result;
	}

	String getValueOfSecondCell(char[] secondCell) {
		String result = "";

		for (int i = 49; i < secondCell.length; i++) {
			result = result + String.valueOf(secondCell[i]);
		}
		result = result.trim();
		return result;
	}

	String[] addFunctionType(String[] panelMatrixData, String[] panelMatrixFunctionTypes) {
		for (int i = 0; i < panelMatrixData.length; i++) {
			panelMatrixData[i] = panelMatrixData[i] + ";" + panelMatrixFunctionTypes[i];
		}
		return panelMatrixData;
	}

	String[] addRhs(String[] panelMatrixData, String[] dataField, int amountRestrictions) {
		// Add RHS to panelmatrixdata array
		int startIndexRhs = 0;
		int stopIndexRhs = dataField.length;

		for (int i = 0; i < stopIndexRhs; i++) {

			// DATAFIELD
			if (dataField[i].trim().equals("RHS")) {
				startIndexRhs = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("RANGES") ^ dataField[i].trim().equals("BOUNDS")
					^ dataField[i].trim().equals("SOS") ^ dataField[i].trim().equals("ENDATA")) {
				stopIndexRhs = i;
			}
		}

		String[] restrictionNames = getRestrictionNames(dataField, amountRestrictions);

		for (int i = 1; i < panelMatrixData.length; i++) {
			panelMatrixData[i] = panelMatrixData[i] + ";"
					+ getRhsValue(stopIndexRhs, startIndexRhs, dataField, restrictionNames[i]);
		}

		if (dataField[0].toUpperCase().contains("MIN")) {
			panelMatrixData[0] = panelMatrixData[0] + ";" + "min !";
		} else {
			panelMatrixData[0] = panelMatrixData[0] + ";" + "max !";
		}

		return panelMatrixData;
	}

	String getRhsValue(int stopIndexRhs, int startIndexRhs, String[] dataField, String restrictionName) {
		String result;

		for (int i = startIndexRhs; i < stopIndexRhs; i++) {

			char[] matrixColumnAmountValue = dataField[i].toCharArray();

			if (matrixColumnAmountValue[14] != ' ' && matrixColumnAmountValue.length < 40) {

				// Search for the searched Value by identifying the restriction
				// and variable
				String row = "";

				for (Character c : matrixColumnAmountValue)
					row += c.toString();

				if (row.contains(restrictionName)) {
					// Copy string from left to get the Value
					result = getValueOfFirstCell(matrixColumnAmountValue);
					return result;
				}

			} else if (matrixColumnAmountValue.length > 40) {
				char[] firstCell = Arrays.copyOfRange(matrixColumnAmountValue, 0, 38);

				String row = "";

				for (Character c : firstCell)
					row += c.toString();

				if (row.contains(restrictionName)) {
					// Copy string from left to get the Value
					result = getValueOfFirstCell(firstCell);
					return result;
				}
				char[] secondCell = Arrays.copyOfRange(matrixColumnAmountValue, 0, matrixColumnAmountValue.length);

				row = "";

				for (Character c : secondCell)
					row += c.toString();

				if (row.contains(restrictionName)) {
					// Copy string from left to get the Value
					result = getValueOfSecondCell(secondCell);
					return result;
				}
			}
		}
		return "0";
	}

	String[] getPanelBorderMatrix(String[] dataField, int amountRestrictions) {
		int borderstable_start = 0;
		// DATAFIELD
		int borderstable_end = dataField.length;

		for (int i = 0; i < borderstable_end; i++) {
			if (dataField[i].trim().equals("BOUNDS")) {
				borderstable_start = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("type") ^ dataField[i].trim().equals("SOS")
					^ dataField[i].trim().equals("ENDATA")) {
				borderstable_end = i;
			}
		}

		String[] panelBordersDataDefualt = getVariableNames(dataField, amountRestrictions);
		for (int i = 0; i < panelBordersDataDefualt.length; i++) {
			if (integerYesNo) {
				panelBordersDataDefualt[i] = panelBordersDataDefualt[i] + ";;;Nein";
			} else {
				panelBordersDataDefualt[i] = panelBordersDataDefualt[i] + ";;;Ja";
			}
		}

		if (containsBounds(dataField)) {
			String[] panelBordersDataCustom = getVariableNames(dataField, amountRestrictions);
			panelBordersDataCustom = clean(panelBordersDataCustom);

			for (int i = 0; i < panelBordersDataCustom.length; i++) {
				if (integerYesNo) {
					panelBordersDataCustom[i] = panelBordersDataCustom[i] + ";"
							+ getBoundVariableValue(dataField, panelBordersDataCustom[i], "LO") + ";"
							+ getBoundVariableValue(dataField, panelBordersDataCustom[i], "UP") + ";Ja";
				} else {
					panelBordersDataCustom[i] = panelBordersDataCustom[i] + ";"
							+ getBoundVariableValue(dataField, panelBordersDataCustom[i], "LO") + ";"
							+ getBoundVariableValue(dataField, panelBordersDataCustom[i], "UP") + ";Nein";
				}
			}
			String[] variableNames = getVariableNames(dataField, amountRestrictions);
			return mergeArrays(panelBordersDataDefualt, panelBordersDataCustom, variableNames);
		} else {
			return panelBordersDataDefualt;
		}
	}

	private String[] mergeArrays(String[] panelBordersDataDefualt, String[] panelBordersDataCustom,
			String[] variableNames) {

		for (int i = 0; i < panelBordersDataCustom.length; i++) {
			for (int k = 0; k < variableNames.length; k++)
				if (panelBordersDataCustom[i].toLowerCase().contains(variableNames[k].toLowerCase())) {
					// Merge Array
					panelBordersDataDefualt[k] = panelBordersDataCustom[i];
				}
		}
		return panelBordersDataDefualt;
	}

	public static String[] clean(final String[] v) {
		List<String> list = new ArrayList<String>(Arrays.asList(v));
		list.removeAll(Collections.singleton(null));
		return list.toArray(new String[list.size()]);
	}

	String[] getBoundVariables(String[] dataField) {
		// String[] boundVarriables =
		int borderstable_start = 0;
		int counter = 0;
		int arrayLength = 0;
		int borderstable_end = dataField.length;

		for (int i = 0; i < borderstable_end; i++) {
			if (dataField[i].trim().equals("BOUNDS")) {
				borderstable_start = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("type") ^ dataField[i].trim().equals("SOS")
					^ dataField[i].trim().equals("ENDATA")) {
				borderstable_end = i;
			}
		}

		arrayLength = borderstable_end - borderstable_start;

		String[] variableNames = new String[arrayLength];

		for (int i = borderstable_start; i < borderstable_end; i++) {
			char[] matrixColumnAmountValue = null;
			matrixColumnAmountValue = dataField[i].toCharArray();
			String currentVariableName = "";

			for (int x = 14; x < 21; x++) {
				currentVariableName = currentVariableName + String.valueOf(matrixColumnAmountValue[x]);
			}

			currentVariableName = currentVariableName.trim();
			if (stringExistInArray(currentVariableName, variableNames) == false) {
				variableNames[counter] = currentVariableName;
				counter++;
			}
		}
		return variableNames;
	}

	String getBoundVariableValue(String[] dataField, String variable, String upperOrLower) {
		String value = "";
		int borderstable_start = 0;
		int borderstable_end = dataField.length;

		for (int i = 0; i < borderstable_end; i++) {
			if (dataField[i].trim().equals("BOUNDS")) {
				borderstable_start = i + 1;
			}
			// DATAFIELD
			if (dataField[i].trim().equals("type") ^ dataField[i].trim().equals("SOS")
					^ dataField[i].trim().equals("ENDATA")) {
				borderstable_end = i;
			}
		}

		for (int i = borderstable_start; i < borderstable_end; i++) {
			char[] matrixColumnAmountValue = dataField[i].toCharArray();
			if (matrixColumnAmountValue[14] != ' ' && matrixColumnAmountValue.length < 40) {

				String row = "";

				for (Character c : matrixColumnAmountValue)
					row += c.toString();

				if (row.contains(variable) && row.contains(upperOrLower)) {
					value = getValueOfFirstCell(matrixColumnAmountValue);
				}
			}
		}
		return value;
	}
}
