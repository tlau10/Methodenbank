package de.fh_konstanz.simubus.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.fh_konstanz.simubus.model.optimierung.BasicSolution;
import de.fh_konstanz.simubus.model.optimierung.BasicVariable;
import de.fh_konstanz.simubus.model.optimierung.Result;
import de.fh_konstanz.simubus.model.optimierung.TargetDetails;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.util.OrUtil;
import de.fh_konstanz.simubus.view.ColumnProperties;
import de.fh_konstanz.simubus.view.OptiControlPanel;
import de.fh_konstanz.simubus.view.SelectTable;
import de.fh_konstanz.simubus.view.Table;

/**
 * Die Klasse <code>TableListSelectionListener</code> beinhaltet die
 * L�sungsInfoTabelle. Sobald in L�sungs Tabelle auf eine Zeile geklickt wird,
 * dann wird diese Klasse angelegt und L�sungs Info Tabelle mit entsprechenden
 * Daten gef�llt.
 * 
 * @author Erkan Erpolat
 * 
 */
public class TableListSelectionListener implements ListSelectionListener {

	/** JTable Instanz von L�sungs Tabelle */
	private JTable table;

	/** Ausgew�hlte Zeile */
	private int selectedRow;

	/** Panel Instanz von L�sungs Panel */
	private JPanel resultInfoPanel;

	/** Liste mit L�sungen */
	private List<Result> results;

	/** Spalten Namen v f�r L�sungs- Info Tabelle */
	private Object[] columnNames = { "Haltestellen", "Ziele" };

	/** Spalten Nummer */
	private int columns[] = { 0, 1 };

	/** Spalten Breite */
	private int widths[] = { 0, 200 };

	/** Spalten Gr�sse �nderbar */
	private boolean resizables[] = { true, true };

	/** Table Instanz f�r L�sungs Tabelle */
	private Table resultInfoTable;

	/** Spaltenbreite und Tabellen, Spalten Eigenschaften festlegen */
	private ColumnProperties columnWidth;

	/** L�sungs ID */
	private int resultId;

	/** Instanz von OptiControlPanel */
	private OptiControlPanel optiControlPanel = OptiControlPanel.getInstance();

	/**
	 * Konstruktor. Tabellen Instanz, Panel, Ergebnisse werden gesetzt
	 * 
	 * @param table
	 * @param resultInfoPanel
	 * @param results
	 */
	public TableListSelectionListener(JTable table, JPanel resultInfoPanel,
			List<Result> results) {
		this.table = table;
		this.resultInfoPanel = resultInfoPanel;
		this.results = results;
	}

	/**
	 * Listener f�r die ausgew�hlte Zelle in L�sungsTabelle
	 * 
	 * @param e
	 */
	public void valueChanged(ListSelectionEvent e) {

		if (!e.getValueIsAdjusting()) {
			this.selectedRow = table.getSelectedRow();

			for (int i = 0; i < table.getColumnCount(); i++) {
				if (table.getColumnName(i).equals("L�sung") == true) {
					resultId = Integer.parseInt(table
							.getValueAt(selectedRow, i).toString()) - 1;
					break;
				}
			}

			resultInfoTable = new Table(resultInfoPanel.getWidth(), columnNames);
			columnWidth = new ColumnProperties(columns, widths, resizables);
			// Optimierte Haltestellen f�r die ausgew�hlte L�sung w�hlen
			optiControlPanel.updateEditor(resultId);
			this.readResultsInfo();

		}
	}

	/**
	 * L�sungs Info lesen
	 * 
	 */
	public void readResultsInfo() {

		try {

			if (results.size() > 0) {

				Result result = results.get(resultId);
				BasicSolution solution = result.getBasicSolution();
				BasicVariable[] variables = solution.getBasicVariables();
				String text = "";
				ArrayList<String> basicTextList = new ArrayList<String>();
				for (int i = 0; i < variables.length; i++) {
					if (variables[i].getValue() == 1) {
						int y = OrUtil.getYCoordinate(variables[i]);
						int x = OrUtil.getXCoordinate(variables[i], y);

						x = OrUtil.getPixelXCoordinate(x);
						y = OrUtil.getPixelYCoordinate(y);

						text = text.concat("[x: " + String.valueOf(x) + " y: "
								+ String.valueOf(y) + "]");
						basicTextList.add(text);
						text = "";
					}

				}

				ArrayList<String> targetTextList = new ArrayList<String>();
				TargetDetails[] details = result.getTargetDetails();
				for (int i = 0; i < details.length; i++) {

					text = text.concat("[x: "
							+ (details[i].getPlanquadrat().getPixelX() + 6)
							+ " y: "
							+ (details[i].getPlanquadrat().getPixelY() + 6)
							+ "]: " + OrUtil.getTime(solution, details[i])
							+ " Minuten (");

					if (details[i].getNotInRange() == true) {
						text = text.concat("gr�sser max. Restgehzeit");
					} else {
						text = text.concat("kleiner max. Restgehzeit");
					}

					text = text.concat(")");
					targetTextList.add(text);
					text = "";
				}

				this.showResultsInfo(basicTextList, targetTextList);
			}
		} catch (NullPointerException ex) {
			Logger.getInstance().log(ex.getMessage(), Logger.LEVEL_ERROR);
		}

	}

	/**
	 * L�sungs Info in Tabelle eintragen bzw. anzeigen
	 * 
	 * @param basicSolution
	 * @param targetDetails
	 */
	public void showResultsInfo(ArrayList<String> basicSolution,
			ArrayList<String> targetDetails) {

		int i = 0;
		int j = 0;
		int basicSolutionSize = basicSolution.size();
		int targetDetailsSize = targetDetails.size() + 1;
		
		Object[][] rowData = new Object[basicSolutionSize][targetDetailsSize];

		try {
			
			for (String basicText : basicSolution) {
				
				if(i <= basicSolutionSize)
					rowData[i++][0] = basicText;
				
			}

			for (String targetText : targetDetails) {
				
				if(j <= targetDetailsSize)
					rowData[j++][1] = targetText;
			
			}
			
		} catch(ArrayIndexOutOfBoundsException ex) {
			Logger.getInstance().log("OutOfBoundException", Logger.LEVEL_ERROR);
		}
		

		resultInfoPanel.removeAll();
		resultInfoTable.setRowData(rowData);
		resultInfoPanel.add(resultInfoTable.getScrollPane("ErgebnisInformationen"));
		resultInfoTable.getTable().setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ListSelectionModel listSelectionModel = resultInfoTable.getTable()
				.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new ResultInfoTableSelectionListener());

		resultInfoPanel.revalidate();
		resultInfoTable.getTable().addMouseListener(
				new SelectTable(resultInfoTable));
		resultInfoTable.changeColumnWidth(columnWidth);

	}

	/**
	 * Change Listener f�r Zeilen bzw. Zellen Selektion
	 * 
	 */
	public class ResultInfoTableSelectionListener implements
			ListSelectionListener {

		public void valueChanged(ListSelectionEvent arg0) {
			resultInfoTable.getTable().addMouseListener(
					new OptiSelectListener(resultInfoTable.getTable()));

		}

	}
}