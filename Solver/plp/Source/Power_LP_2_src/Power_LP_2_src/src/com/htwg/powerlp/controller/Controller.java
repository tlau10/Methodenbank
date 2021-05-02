package com.htwg.powerlp.controller;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.htwg.powerlp.file.MpsParser;
import com.htwg.powerlp.solver.AbstractSolver;
import com.htwg.powerlp.solver.AbstractSolver.ObjectiveType;
import com.htwg.powerlp.solver.SolverFactory;
import com.htwg.powerlp.solver.SupportedSolver;
import com.htwg.powerlp.util.Configurator;
import com.htwg.powerlp.view.InternalFrame;
import com.htwg.powerlp.view.JInternalFrameWrapper;
import com.htwg.powerlp.view.RepaintReason;

/**
 * Controller class for PowerLP application
 * 
 * @author Teamprojekt WS 2015/2016 (Ducho, Keller, Lagun, Lu, Pllana)
 * @version 1.0
 * 
 * @author Bastian Schoettle
 * @version 2.0
 */

public class Controller {

	//private String fileData;
	private JTableHeader th;
	private TableColumnModel tcm;
	private Enumeration<TableColumn> tcs;
	private TableColumn tc;
	private MpsParser mpsParser;
	private int frameCount = 0;
	private boolean isInitallySolved;
	private Configurator configurator;
	private String mpsFileContent;
	private ObjectiveType objectiveType;
	
	public static int INTERNAL_NUMBER = 1;

	/**
	 * 
	 */
	public Controller(Configurator configurator) {
		this.configurator = configurator;
		this.mpsParser = new MpsParser();
		this.isInitallySolved = false;
	}

	/**
	 * Creates new internal frame
	 * 
	 * @param internalFrameNumber
	 *            fortlaufende Nummer der internal Frames (im Titel)
	 * @param panelFrames
	 *            Fenster f�r die Frame-Wiedergabe
	 * @return neues Unterfenster
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public JInternalFrame createNewFrame(
			JDesktopPane panelFrames) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		deselectAllFrames(panelFrames);
		INTERNAL_NUMBER++;
		int boundFactor = 30 * INTERNAL_NUMBER;
		JInternalFrameWrapper wrapper = InternalFrame.createFrame("NEU"
				+ (++frameCount), boundFactor, this, configurator);
		panelFrames.setSelectedFrame(wrapper.getInteralFrame());
		toBackAllFrames(panelFrames);
		return wrapper.getInteralFrame();
	}

	/**
	 * Aktualisieren der Matrix-Tabelle
	 * 
	 * @param model
	 *            Model der zu schreibenden Tabelle
	 * @param table
	 *            die zu schreibende Tabelle
	 * @param yAmount
	 *            Anzahl der Reihen der Tabelle (Anzahl der Restriktionen plus
	 *            Header)
	 * @param xAmount
	 *            Anzahl der Spalten der Tabelle (Anzahl der Variablen plus 3
	 *            feste Spalten)
	 * @param scrollPane
	 *            erm�glicht die Scrollbarkeit der Tabelle
	 * @param optionComboCell
	 *            Auswahl von groessergleich/kleinegleich etc.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void repaintXYTable(DefaultTableModel model, JTable table,
			int yAmount, int xAmount, JScrollPane scrollPane,
			JComboBox<String> optionComboCell, RepaintReason r) {
		if (r == RepaintReason.REPAINT_VAR_RED) {
			Vector storedData = new Vector();
			Vector data = model.getDataVector();
			for (int i = 0; i < data.size(); i++) {
				Vector oldRow = new Vector<>();
				for (int j = 0; j < ((Vector) data.get(i)).size(); j++) {
					// Vector row = new Vector<>();
					// copyVector(row,(Vector) );

					String copy = (String) ((Vector) data.get(i)).get(j);
					oldRow.add(copy);
				}
				storedData.add(oldRow);
			}
			model.setColumnCount(model.getColumnCount() - 1);
			model.fireTableStructureChanged();
			String[] colIdents = getColumnIdents(model);
			Vector newData = new Vector();
			for (int i = 0; i < storedData.size(); i++) {
				Vector newRow = new Vector();
				for (int j = 0; j < ((Vector) storedData.get(i)).size(); j++) {
					if (j == ((Vector) storedData.get(i)).size() - 3) {
						j++;
					}
					String s = (String) ((Vector) storedData.get(i)).get(j);
					newRow.add(s);
				}
				newData.add(newRow);
			}
			model.setDataVector(newData, arrayToVector(colIdents));
			addComboCell(optionComboCell, table);
			model.fireTableDataChanged();
		} else if (r == RepaintReason.REPAINT_VAR_INC) {
			Vector storedData = new Vector();
			Vector data = model.getDataVector();
			for (int i = 0; i < data.size(); i++) {
				Vector oldRow = new Vector<>();
				for (int j = 0; j < ((Vector) data.get(i)).size(); j++) {
					String copy = (String) ((Vector) data.get(i)).get(j);
					oldRow.add(copy);
				}
				storedData.add(oldRow);
			}
			model.setColumnCount(model.getColumnCount() + 1);
			model.fireTableStructureChanged();

			model.fireTableStructureChanged();
			String[] colIdents = getColumnIdents(model);
			Vector newData = new Vector();
			for (int i = 0; i < storedData.size(); i++) {
				Vector newRow = new Vector();
				for (int j = 0; j < ((Vector) storedData.get(i)).size(); j++) {
					String s = (String) ((Vector) storedData.get(i)).get(j);
					if (j == model.getColumnCount() - 3) {
						newRow.add("");
						newRow.add(s);
					} else {
						newRow.add(s);
					}
				}
				newData.add(newRow);
			}
			model.setDataVector(newData, arrayToVector(colIdents));
			model.fireTableStructureChanged();
			addComboCell(optionComboCell, table);
			model.fireTableDataChanged();
		} else if (r == RepaintReason.REPAINT_CONST_RED) {
			Vector storedData = new Vector();
			Vector data = model.getDataVector();
			for (int i = 0; i < data.size(); i++) {
				Vector oldRow = new Vector<>();
				for (int j = 0; j < ((Vector) data.get(i)).size(); j++) {
					String copy = new String(
							(String) ((Vector) data.get(i)).get(j));
					oldRow.add(copy);
				}
				storedData.add(oldRow);
			}
			model.setColumnCount(model.getColumnCount());

			String[] colIdents = getColumnIdents(model);
			Vector newData = new Vector();
			for (int i = 0; i < storedData.size() - 1; i++) {
				Vector newRow = new Vector();
				for (int j = 0; j < ((Vector) storedData.get(i)).size(); j++) {
					String s = (String) ((Vector) storedData.get(i)).get(j);
					newRow.add(s);
				}
				newData.add(newRow);
			}
			model.setDataVector(newData, arrayToVector(colIdents));
			model.fireTableStructureChanged();
			addComboCell(optionComboCell, table);
			model.fireTableDataChanged();
		} else if (r == RepaintReason.REPAINT_INIT) {
			// th = table.getTableHeader();
			// tcm = th.getColumnModel();
			// tcs = tcm.getColumns();
			model.setColumnCount(0);
			model.addColumn("------");
			model.addColumn("X1", new Vector<>());
			model.addColumn("X2", new Vector<>());
			model.addColumn("");
			model.addColumn("b", new Vector<>());
			model.setRowCount(xAmount + 1);
			model.setValueAt("ZF", 0, 0);
			for (int i = 1; i < xAmount + 1; i++) {
				model.setValueAt("R" + i, i, 0);
			}
			model.setValueAt((String) "-->", 0, yAmount + 1);
			table.getTableHeader().setReorderingAllowed(false);
			model.fireTableStructureChanged();
			table.getColumnModel().getColumn(table.getColumnCount() - 2)
					.setCellEditor(new DefaultCellEditor(optionComboCell));
			optionComboCell.setSelectedIndex(1);
			// tcs.nextElement().setHeaderValue("------");
			// for (int i = 0; i < yAmount; i++) {
			// tc = tcs.nextElement();
			// tc.setHeaderValue("X" + (i + 1));
			// }
//			tc = tcs.nextElement();
//			tc.setCellEditor(new DefaultCellEditor(optionComboCell));
//			tc.setHeaderValue("");
//			tc = tcs.nextElement();
//			tc.setHeaderValue("b");

		} else if (r == RepaintReason.REPAINT_CONST_INC) {
			Vector storedData = new Vector();
			Vector data = model.getDataVector();
			for (int i = 0; i < data.size(); i++) {
				Vector oldRow = new Vector<>();
				for (int j = 0; j < ((Vector) data.get(i)).size(); j++) {
					String copy = new String(
							(String) ((Vector) data.get(i)).get(j));
					oldRow.add(copy);
				}
				storedData.add(oldRow);
			}
			model.setColumnCount(model.getColumnCount());

			Vector newData = new Vector();
			for (int i = 0; i < storedData.size(); i++) {
				Vector newRow = new Vector();
				for (int j = 0; j < ((Vector) storedData.get(i)).size(); j++) {
					String s = (String) ((Vector) storedData.get(i)).get(j);
					newRow.add(s);
				}
				newData.add(newRow);
			}
			String[] colIdents = getColumnIdents(model);
			Vector v = new Vector<>();
			for (int i = 0; i < colIdents.length; i++) {
				if (i == 0) {
					v.add("R" + storedData.size());
				} else if (i == colIdents.length - 2) {
					v.add("<=");
				} else {
					v.add("");
				}
			}
			newData.add(v);
			model.setDataVector(newData, arrayToVector(colIdents));
			model.fireTableStructureChanged();
			addComboCell(optionComboCell, table);
			model.fireTableDataChanged();
		}
		table.repaint();
	}

	private String[] getColumnIdents(TableModel model) {
		String[] colIdents = new String[model.getColumnCount()];
		for (int i = 0; i < colIdents.length; i++) {
			if (i == 0) {
				colIdents[i] = "------";
			} else if (i == colIdents.length - 2) {
				colIdents[i] = "";
			} else if (i == colIdents.length - 1) {
				colIdents[i] = "b";
			} else {
				colIdents[i] = "X" + (i);
			}
		}
		return colIdents;
	}

	private void addComboCell(JComboBox<String> optionComboCell, JTable table) {
		table.getColumnModel()
				.getColumn(table.getColumnModel().getColumnCount() - 2)
				.setCellEditor(new DefaultCellEditor(optionComboCell));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Vector arrayToVector(String[] idents) {
		Vector v = new Vector();
		for (int i = 0; i < idents.length; i++) {
			v.add(idents[i]);
		}
		return v;
	}

	/**
	 * Min oder Max in die Tabellenzelle f�r die Zielfunktion einf�gen
	 * 
	 * @param model
	 *            Model der Tabelle
	 * @param table
	 *            Tabelle
	 * @param optionComboMinMax
	 *            Combobox f�r Min oder Max
	 */
	// ---------------------------------------------------------------------------------------
	// Min-Max einf�gen in die Tabellenzelle (Zielfunktion)
	// ---------------------------------------------------------------------------------------
	public void minMaxTableWriter(JSpinner js, DefaultTableModel model,
			JTable table, JComboBox<String> optionComboMinMax) {
		// Setzten eines Wertes aus der Combo-Box in eine bestimmte Zelle der
		// Tabelle

		model.setValueAt((String) optionComboMinMax.getSelectedItem(), 0,
				model.getColumnCount() - 1);
		table.repaint();
	}

	public Configurator getConfigurator() {
		return configurator;
	}

	/**
	 * Aktualisieren der Grenzen-Integer-Tabelle
	 * 
	 * @param model
	 *            Model der Tabelle
	 * @param table
	 *            Tabelle
	 * @param xAmount
	 *            Reihen der Tabelle
	 * @param scrollPane
	 *            erm�glicht die Scrollbarkeit der Tabelle
	 * @param optionComboCell
	 *            ComboCell f�r Integer ja/nein
	 */
	// ---------------------------------------------------------------------------------------
	// Aktualisieren der Grenzen-Integer-Tabelle
	// ---------------------------------------------------------------------------------------
	public void repaintXTable(DefaultTableModel model, JTable table,
			int xAmount, JScrollPane scrollPane,
			JComboBox<String> optionComboCell) {
		// Belegen der Tabelle mit Daten/Headernnamen etc.
		// Gesamtanzahl der Reihen festlegen
		model.setRowCount(xAmount);
		th = table.getTableHeader();
		tcm = th.getColumnModel();
		tcs = tcm.getColumns();

		// Header beschreiben (Standardwerte)
		tcs.nextElement().setHeaderValue("");
		tcs.nextElement().setHeaderValue("Lower");
		tcs.nextElement().setHeaderValue("Upper");
		tc = tcs.nextElement();
		tc.setHeaderValue("Integer");
		// Die Spalte mit ComboBox belegen, gilt f�r ganze Spalte
		tc.setCellEditor(new DefaultCellEditor(optionComboCell));

		// Reihennamen belegen
		for (int i = 0; i < xAmount; i++) {
			model.setValueAt("X" + (i + 1), i, 0);
		}
		for (int i = 0; i < xAmount; i++) {
			model.setValueAt("Nein", i, 3);
		}
	}

	/**
	 * Ein- bzw. Ausblenden der Grenzen-Integer-Tabelle
	 * 
	 * @param table
	 *            Tabelle
	 * @param Status
	 *            Status, ob Checkbox angeklickt oder nicht
	 */
	// ---------------------------------------------------------------------------------------
	// Ein- bzw. Ausblenden der Grenzen-Integer-Tabelle
	// ---------------------------------------------------------------------------------------
	public void setPanelVisiblility(JPanel panel, Boolean Status) {
		if (Status) {
			panel.setVisible(true);
		} else {
			panel.setVisible(false);
		}
	}

	// ---------------------------------------------------------------------------------------
	// Ein- bzw. Ausblenden des 2ten L�sungsfensters (nicht relevant)
	// ---------------------------------------------------------------------------------------
	@SuppressWarnings("deprecation")
	public void setTabIVisiblility(Boolean Status, JTabbedPane tabbedPaneSolver2) {
		if (Status) {
			tabbedPaneSolver2.enable();
			tabbedPaneSolver2.repaint();
		} else {
			tabbedPaneSolver2.disable();
			tabbedPaneSolver2.repaint();
		}
	}

	/**
	 * Erm�glicht das Speichern unter ...
	 * 
	 * @param path
	 *            Pfad wo die Datei gespeichert werden soll
	 * @param jfMainFrame
	 *            Hauptframe
	 * @param panelFrames
	 *            Fenster f�r die Frame-Wiedergabe
	 * @return false oder true
	 */
	// ---------------------------------------------------------------------------------------
	// Speichern unter
	// ---------------------------------------------------------------------------------------
	public boolean saveAs(String path, JFrame jfMainFrame,
			JDesktopPane panelFrames) {
		// Daten aus Internalframe holen und fuer den .mps (standardisiertes
		// Format) vorbereiten
		JInternalFrame internalFrame = panelFrames.getSelectedFrame();
		if (internalFrame == null) {
			return false;
		}
		String filename = internalFrame.getTitle();
		String mpsExtension = ".mps";
		if (filename.toLowerCase().contains(mpsExtension.toLowerCase()) == false) {
			filename = filename + mpsExtension;
		}

		JFileChooser chooser;
		String data = mpsParser.prepareMPSFormat(internalFrame);
		// Bestimmen der Datei und ihrem Pfad (Dialog zum Ausw‰hlen) zum
		// Speichern der .mps Daten
		if (path == null) {
			path = System.getProperty("user.home");
		}
		File file = new File(path.trim());
		chooser = new JFileChooser(path);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		FileNameExtensionFilter plainFilter = new FileNameExtensionFilter(
				"mps", "mps");
		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
		chooser.setFileFilter(plainFilter);
		chooser.setDialogTitle("Speichern unter");
		chooser.setVisible(true);
		chooser.setSelectedFile(new File(filename));
		int result = chooser.showSaveDialog(jfMainFrame);
		// Wenn Dialog best‰tigt wird, werden die Daten in die oben erstellte
		// Datei
		// geschrieben. Dabei wird zeilenweise geschrieben. Wenn nicht
		// best‰tigt
		// wird Dialog geschlossen ohne weitere Ausf¸hrungen
		if (result == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().toString();
			file = new File(path);
			if (plainFilter.accept(file)) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.write(data);
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				setInternalFrameTitle(internalFrame, path);
			}
			chooser.setVisible(false);
			return true;

		}
		chooser.setVisible(false);
		return false;
	}

	/**
	 * Opens selected file
	 * 
	 * @param panelFrames
	 *            Fenster f�r die Frame-Wiedergabe
	 * @return neues Unterfenster
	 */
	public JInternalFrame open(JDesktopPane panelFrames) {
		// Add filter for mps files
		FileNameExtensionFilter plainFilter = new FileNameExtensionFilter(
				"mps", "mps");
		JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
		chooser.setFileFilter(plainFilter);
		File selectedFile = null;
		JInternalFrame internalFrame = null;
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			FileReader fr;
			// Lesen der Data aus dem gew�hlten File, zeilenweise
			String data = "";
			try {
				selectedFile = chooser.getSelectedFile();
				fr = new FileReader(selectedFile);
				BufferedReader br = new BufferedReader(fr);
				String zeile = "";
				try {
					while ((zeile = br.readLine()) != null) {
						data = data + zeile + "\r\n";
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			// Alle Frames abw�hlen, damit die vorhandenen Frames nicht
			// "einfrieren" (werden nicht mehr als Frame erkannt)
			deselectAllFrames(panelFrames);
			// Erzeugen des neuen Frames und schreiben der Daten aus der .lpi
			// Datei in das Frame
			JInternalFrameWrapper wrapper = null;
			try {
				wrapper = InternalFrame.createFrame(
						selectedFile.getAbsolutePath(), result, this,
						configurator);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			internalFrame = wrapper.getInteralFrame();
			panelFrames.setSelectedFrame(internalFrame);
			toBackAllFrames(panelFrames);
			try {

				OpenMps test = new OpenMps();
				test.openContext(internalFrame, data,
						wrapper.getMatrixOptions(),
						wrapper.getMatrixOptionsInteger(), configurator, this);
			} catch (Exception e) {

			}
		} else {
			// Beim Abbrechen des Dialogs wird eine null Refrenz ausgegeben
			internalFrame = null;
		}
		return internalFrame;
	}

	public JInternalFrame open(JDesktopPane panelFrames, String path) {
		JInternalFrame internalFrame = null;
		// Alle Frames abw�hlen, damit die vorhandenen Frames nicht
		// "einfrieren" (werden nicht mehr als Frame erkannt)
		deselectAllFrames(panelFrames);
		// Erzeugen des neuen Frames und schreiben der Daten aus der .lpi
		// Datei in das Frame
		JInternalFrameWrapper wrapper = null;
		File selectedFile = new File(path);
		FileReader fr;
		// Lesen der Data aus dem gew�hlten File, zeilenweise
		String data = "";
		try {
			fr = new FileReader(selectedFile);
			BufferedReader br = new BufferedReader(fr);
			String zeile = "";
			try {
				while ((zeile = br.readLine()) != null) {
					data = data + zeile + "\r\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		try {
			wrapper = InternalFrame.createFrame(selectedFile.getAbsolutePath(),
					0, this, configurator);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			e1.printStackTrace();
		}
		internalFrame = wrapper.getInteralFrame();
		panelFrames.setSelectedFrame(internalFrame);
		toBackAllFrames(panelFrames);
		try {

			OpenMps test = new OpenMps();
			test.openContext(internalFrame, data, wrapper.getMatrixOptions(),
					wrapper.getMatrixOptionsInteger(), configurator, this);
		} catch (Exception e) {

		}
		// Beim Abbrechen des Dialogs wird eine null Refrenz ausgegeben
		return internalFrame;
	}

	/**
	 * Erm�glicht das Speichern
	 * 
	 * @param jfMainFrame
	 *            Hauptfenster
	 */
	// ---------------------------------------------------------------------------------------
	// Speichern
	// ---------------------------------------------------------------------------------------
	public void save(JFrame jfMainFrame) {
		JDesktopPane panelFrames = null;
		JInternalFrame internalFrame = null;
		// Die Referenz des angew�hlten internalFrames in eine Variable
		// speichern
		Component[] mfComponent = new Component[jfMainFrame.getContentPane()
				.getComponentCount()];
		for (int i = 0; i < jfMainFrame.getContentPane().getComponentCount(); i++) {
			mfComponent[i] = jfMainFrame.getContentPane().getComponent(i);
			if (mfComponent[i].getName().equals("panelFrames")) {
				panelFrames = (JDesktopPane) mfComponent[i];
				internalFrame = panelFrames.getSelectedFrame();
			}
		}
		if (internalFrame == null) {
			return;
		}
		if (internalFrame.getTitle().equals(configurator.getDefaultFile())) {
			saveAs(null, jfMainFrame, panelFrames);
		}
		// Den Pfad (gleichzeitig Title des internalFrames) einer File Variable
		// �bergeben
		File checkFile = new File(internalFrame.getTitle());
		if (checkFile.exists() == false) {
			saveAs(null, jfMainFrame, panelFrames);
		}
		// Wenn File existiert werden die Daten des InternalFrames ausgelesen
		// und die Datei
		// wird mit aktuellen Daten �berschieben
		else {
			String path = internalFrame.getTitle();
			File file = new File(path);
			String data = mpsParser.prepareMPSFormat(internalFrame);
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(data);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			setInternalFrameTitle(panelFrames.getSelectedFrame(), path);

		}
	}

	/*
	 * 
	 * private void saveFile(String tempMps, String path) { try {
	 * MpsFileManager.createMPSFile(configurator, tempMps, path); } catch
	 * (IOException e) { e.printStackTrace(); } File f = new File(tempMps); if
	 * (f.exists()) { f.delete(); } }
	 */

	/**
	 * Schlie�en des Unterfensters
	 * 
	 * @param panelFrames
	 *            Fenster f�r die Frame-Wiedergabe
	 */
	// ---------------------------------------------------------------------------------------
	// InternalFrame Schliessen
	// ---------------------------------------------------------------------------------------
	public void closeInternalFrame(JDesktopPane panelFrames) {
		// Schliessen des InternalFrames mit dem Befehl dispose
		if (panelFrames.getSelectedFrame() == null) {
			return;
		}
		if (panelFrames.getSelectedFrame().getName().contains("NEU")) {
			INTERNAL_NUMBER--;
		}
		panelFrames.getSelectedFrame().dispose();
	}

	/**
	 * Solves linear program model
	 * 
	 * @param internalFrame
	 *            Unterfenster
	 * @return true (wichtig f�r weitere Verarbeitung)
	 */
	public void solve(JInternalFrame internalFrame) {
		if (isInitallySolved) {
			String id = getActiveSolver(internalFrame);
			if (id != null) {
				tabsSwitcher(internalFrame, Integer.parseInt(id));
			}
		}
		MpsParser parse = new MpsParser();
		// Die Daten aus dem Frame werden f�r das MPS Format vorbreitet
		mpsFileContent = parse.prepareMPSFormat(internalFrame);
		// Pr�fen ob MPS minieren oder maximieren beinhaltet
		// (wichtig f�r Parser und ihre Aufrufe)
		objectiveType = ObjectiveType.MIN;
		if (mpsFileContent.contains("MAXIMIZE")) {
			objectiveType = ObjectiveType.MAX;
		}
		// Default solver is LP_Solve
		AbstractSolver solver = SolverFactory.createSolver(
				SupportedSolver.LP_SOLVE, mpsFileContent, objectiveType,
				configurator);

		String ret = solver.solve();
		// Select corresponding pane to display solver result
		JScrollPane field = getAssociatedTab(internalFrame,
				SupportedSolver.LP_SOLVE.getId());
		JViewport viewport = field.getViewport();
		JTextPane solverDataPane = (JTextPane) viewport.getComponent(0);
		solverDataPane.setText(ret);
		solverDataPane.setEditable(false);
		activateSolverTabs(internalFrame);
		isInitallySolved = true;
	}

	private String getActiveSolver(JInternalFrame internalFrame) {
		JScrollPane mainScroller = (JScrollPane) internalFrame.getContentPane()
				.getComponent(0);
		JViewport viewport = mainScroller.getViewport();
		JPanel mainPanel = (JPanel) viewport.getView();
		JPanel[] componentPanel = new JPanel[mainPanel.getComponentCount()];
		JPanel solverPane = null;
		for (int i = 0; i < componentPanel.length; i++) {
			componentPanel[i] = (JPanel) mainPanel.getComponent(i);
			if (mainPanel.getComponent(i).getName().equals("panelSolver")) {
				solverPane = (JPanel) mainPanel.getComponent(i);
				break;
			}
		}
		JTabbedPane tabbedPane = null;
		for (int i = 0; i < solverPane.getComponentCount(); i++) {
			if (solverPane.getComponent(i).getName().equals("tabbedPaneSolver")) {
				tabbedPane = (JTabbedPane) solverPane.getComponent(i);
			}
		}
		return tabbedPane.getSelectedComponent().getName();
	}

	private JScrollPane getAssociatedTab(JInternalFrame internalFrame, int id) {
		JScrollPane mainScroller = (JScrollPane) internalFrame.getContentPane()
				.getComponent(0);
		JViewport viewport = mainScroller.getViewport();
		JPanel mainPanel = (JPanel) viewport.getView();
		JPanel[] componentPanel = new JPanel[mainPanel.getComponentCount()];
		JPanel solverPane = null;
		for (int i = 0; i < componentPanel.length; i++) {
			componentPanel[i] = (JPanel) mainPanel.getComponent(i);
			if (mainPanel.getComponent(i).getName().equals("panelSolver")) {
				solverPane = (JPanel) mainPanel.getComponent(i);
				break;
			}
		}
		JTabbedPane tabbedPane = null;
		for (int i = 0; i < solverPane.getComponentCount(); i++) {
			if (solverPane.getComponent(i).getName().equals("tabbedPaneSolver")) {
				tabbedPane = (JTabbedPane) solverPane.getComponent(i);
			}
		}
		tabbedPane.getSelectedComponent().getName();
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			int compId = Integer.parseInt(tabbedPane.getComponent(i).getName());
			if (compId == id) {
				return (JScrollPane) tabbedPane.getComponentAt(i);
			}
		}
		return null;
	}

	/**
	 * Activates solver tabs if licensed
	 * 
	 * @param internalFrame
	 */
	private void activateSolverTabs(JInternalFrame internalFrame) {
		// TODO: Improve implementation
		JScrollPane mainScroller = (JScrollPane) internalFrame.getContentPane()
				.getComponent(0);
		if (mainScroller == null) {
			return;
		}
		JViewport viewport = mainScroller.getViewport();
		JPanel mainPanel = (JPanel) viewport.getView();
		JPanel[] componentPanel = new JPanel[mainPanel.getComponentCount()];
		JPanel solverPane = null;
		for (int i = 0; i < componentPanel.length; i++) {
			componentPanel[i] = (JPanel) mainPanel.getComponent(i);
			if (mainPanel.getComponent(i).getName().equals("panelSolver")) {
				solverPane = (JPanel) mainPanel.getComponent(i);
				break;
			}
		}
		JTabbedPane tabbedPane = null;
		for (int i = 0; i < solverPane.getComponentCount(); i++) {
			if (solverPane.getComponent(i).getName().equals("tabbedPaneSolver")) {
				tabbedPane = (JTabbedPane) solverPane.getComponent(i);
			}
		}
		tabbedPane.setEnabled(true);
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			int id = Integer.parseInt(tabbedPane.getComponent(i).getName());
			if (id == SupportedSolver.CPLEX.getId()) {
				if (!configurator.getCplexLicenseEnabled()) {
					tabbedPane.setEnabledAt(i, false);
				}
			} else if (id == SupportedSolver.GUROBI.getId()) {
				if (!configurator.getGurobiLicenseEnabled()) {
					tabbedPane.setEnabledAt(i, false);
				}
			}
		}
	}

	/**
	 * Duplizieren eines Unterfensters
	 * 
	 * @param number
	 *            Nummer des Unterfensters (Titel)
	 * @param name
	 *            Name des Unterfensters (Titel)
	 * @param panelFrames
	 *            Fenster f�r die Frame-Wiedergabe
	 */
	public void duplicateFrame(JDesktopPane panelFrames) {
		// Gr�sse des Gesamtfensters ermitteln
		int fullWidth = panelFrames.getWidth();
		int fullHeight = panelFrames.getHeight();
		// Array f�r 2 internal Frames anlegen
		JInternalFrame[] framesArray = new JInternalFrame[2];
		// Das zu duplizierende internal Frame (angew�hltes) in Array
		// speichern
		framesArray[0] = panelFrames.getSelectedFrame();
		String name = framesArray[0].getName();
		if (name.contains("\\")) {
			String[] split = name.split("\\\\");
			name = split[split.length-1];
		}
		// Data aus dem zu duplizierenden internal Frame speichern
		if (framesArray[0] == null) {
			return;
		}
		String data = MatrixContentHandler
				.prepareInternalFrameContext(framesArray[0]);
		// Abw�hlen der Frames (Erkl�rung siehe oben)
		deselectAllFrames(panelFrames);
		// neuen Internal Frame erstellen und die Daten speichern
		int boundFactor = 30;
		JInternalFrameWrapper wrapper = null;
		try {
			wrapper = InternalFrame.createFrame(name+"_dupliziert", boundFactor,
					this, configurator);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		JInternalFrame interalFrame = wrapper.getInteralFrame();
		panelFrames.setSelectedFrame(interalFrame);
		String[] split = data.split("\r\n");
		data = "";
		for (int i = 1; i < split.length; i++) {
			data = data + split[i] + ("\r\n");
		}
		// Das Duplikat in das Array speichern
		framesArray[1] = panelFrames.getSelectedFrame();
		// Das Duplikat mit Daten belegen
		MatrixContentHandler.openContext(framesArray[1], data,
				wrapper.getMatrixOptions(), wrapper.getMatrixOptionsInteger());
		panelFrames.add(framesArray[1]);
		// Die Gr�sse der zwei internal Frames bestimmen
		int startX = 0;
		int startY = 0;
		for (int i = 0; i < framesArray.length; i++) {
			framesArray[i].setBounds(startX, startY, fullWidth / 2, fullHeight);
			startX = fullWidth / 2;
		}
		// Beide Frames so setzen, dass sie im Vordergrund stehen und das
		// zu duplizierendes internal Frame angew�hlt bleibt
		panelFrames.setSelectedFrame(framesArray[1]);
		panelFrames.getSelectedFrame().toFront();

		panelFrames.setSelectedFrame(framesArray[0]);
		panelFrames.getSelectedFrame().toFront();
	}

	/**
	 * Ergebnisse der Solver lesen
	 * 
	 * @param path
	 *            Pfad, an dem die Ergebnisse abrufbar sind
	 * @param solverName
	 *            von welchem Solver das Ergebnis gelesen werden soll
	 * @return die Daten aus der Ergebnisdatei
	 */
	public void deleteLogGurobi() {
		// Datei l�schen
		File log = new File(configurator.getGurobiDir() + "gurobi.log");
		try {
			Files.deleteIfExists(log.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// / ----------------------------------
	// / L�sungsdatei f�r Gurobi erstellen
	// / Log file + .sol file
	// / ----------------------------------
	public String createGurobiSolFile() {
		// Endergebnis
		String finalSol = "";

		// Log Datei von Gurobi lesen
		String logData = "";
		String logPath = configurator.getGurobiDir() + "gurobi.log";
		File log = new File(logPath);

		if (log.exists()) {
			FileReader logReader;
			try {
				logReader = new FileReader(logPath);
				BufferedReader gbrbr = new BufferedReader(logReader);
				String line = "";
				try {
					while ((line = gbrbr.readLine()) != null) {
						logData = logData + line + "\r\n";
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					gbrbr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
		} else {
			logData = "";
		}

		// .sol Datei von Gurobi lesen
		String solData = "";
		String solPath = configurator.getGurobiDir() + "PowerLPResult.sol";
		File sol = new File(solPath);

		if (sol.exists()) {
			FileReader solReader;
			try {
				solReader = new FileReader(solPath);
				BufferedReader gbrbr = new BufferedReader(solReader);
				String zeile = "";
				try {
					while ((zeile = gbrbr.readLine()) != null) {
						solData = solData + zeile + "\r\n";
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					gbrbr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		} else {
			solData = "";
		}

		finalSol = logData + "\r\n" + solData;

		if (finalSol.equals("\r\n")) {
			finalSol = "Gurobi konnte nicht ausgef�hrt werden. Bitte �berpr�fen Sie den Pfad der Lizenz.";
		}

		return finalSol;

	}

	/**
	 * Switch solver tab and resolve model with new solver type instance
	 * 
	 * @param internalFrame
	 *            active sub-window
	 * 
	 */
	public void tabsSwitcher(JInternalFrame internalFrame, int solverId) {
		MpsParser parse = new MpsParser();
		// Die Daten aus dem Frame werden f�r das MPS Format vorbreitet
		mpsFileContent = parse.prepareMPSFormat(internalFrame);
		// Pr�fen ob MPS minieren oder maximieren beinhaltet
		// (wichtig f�r Parser und ihre Aufrufe)

		AbstractSolver solver = null;
		for (SupportedSolver s : SupportedSolver.values()) {
			if (s.getId() == solverId) {
				solver = SolverFactory.createSolver(s, mpsFileContent,
						objectiveType, configurator);
				break;
			}
		}
		String ret = solver.solve();
		JScrollPane field = getAssociatedTab(internalFrame, solverId);
		JViewport viewport = field.getViewport();
		JTextPane solverDataPane = (JTextPane) viewport.getComponent(0);
		solverDataPane.setText(ret);
		solverDataPane.setEditable(false);
	}

	/**
	 * Schlie�en des Hauptfensters (Abfrage, ob InternalFrames gespeichert
	 * werden sollen)
	 * 
	 * @param jfMainFrame
	 *            Hauptfenster
	 */
	// ---------------------------------------------------------------------------------------
	// Schlie�end des Hauptfensters (Abfrage, ob InternalFrames gespeichert
	// werden sollen)
	// ---------------------------------------------------------------------------------------
	public void close(JFrame jfMainFrame) {
		JInternalFrame internalFrame = null;
		File file = null;
		JDesktopPane panelFrames = null;
		// Das angew�hlte Internal Frame wird ermittelt
		Component[] mfComponent = new Component[jfMainFrame.getContentPane()
				.getComponentCount()];
		for (int i = 0; i < jfMainFrame.getContentPane().getComponentCount(); i++) {
			mfComponent[i] = jfMainFrame.getContentPane().getComponent(i);
			if (mfComponent[i].getName().equals("panelFrames")) {
				panelFrames = (JDesktopPane) mfComponent[i];
				internalFrame = panelFrames.getSelectedFrame();
			}
		}
		if (internalFrame == null) {
			System.exit(JFrame.EXIT_ON_CLOSE);
		}
		Component frame = null;
		// Der Dialog zum Beenden wird mit Ja/Nein/Abbrechen Option aufgerufen
		int selectedItem = JOptionPane.showConfirmDialog(frame,
				"Wollen Sie diese Datei speichern?", "Beenden",
				JOptionPane.YES_NO_CANCEL_OPTION);
		// Wenn Ja Speicher oder Speichern unter, je nach Existenz der Datei
		if (selectedItem == JOptionPane.YES_OPTION) {
			file = new File(internalFrame.getTitle());
			if (!Files.exists(file.toPath(), LinkOption.NOFOLLOW_LINKS)) {
				saveAs(internalFrame.getTitle(), jfMainFrame, panelFrames);
			} else {
				save(jfMainFrame);
			}
			System.exit(JFrame.EXIT_ON_CLOSE);
		}
		if (selectedItem == JOptionPane.NO_OPTION) {
			System.exit(JFrame.EXIT_ON_CLOSE);
		}
		if (selectedItem == JOptionPane.CANCEL_OPTION) {

		}
	}

	/**
	 * Titel des Frames bearbeiten (wenn Datei gespeichert wurde nicht mehr
	 * "NEU1" sondern "pfad"
	 * 
	 * @param internalFrame
	 *            aktives Unterfenster
	 * @param title
	 *            Titel des Unterfensters
	 */
	// ---------------------------------------------------------------------------------------
	// Titel des Frames bearbeiten
	// ---------------------------------------------------------------------------------------
	public void setInternalFrameTitle(JInternalFrame internalFrame, String title) {
		internalFrame.setTitle(title);
	}

	/**
	 * Alle Unterfenster abw�hlen
	 * 
	 * @param panelFrames
	 *            Fenster f�r die Frame-Wiedergabe
	 */
	// ---------------------------------------------------------------------------------------
	// Alle Frames abw�hlen
	// ---------------------------------------------------------------------------------------
	public void deselectAllFrames(JDesktopPane panelFrames) {
		// Alle Frames aus dem Desktop Pane werden abgew�hlt
		for (int i = 0; i < panelFrames.getAllFrames().length; i++) {
			try {
				panelFrames.getAllFrames()[i].setSelected(false);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Alle Unterfenster in der Reihenfolge zur�cksetzen (damit neu erstelltes
	 * Unterfenster sichtbar ist)
	 * 
	 * @param panelFrames
	 *            Fenster f�r die Frame-Wiedergabe
	 */
	public void toBackAllFrames(JDesktopPane panelFrames) {
		// Alle Frames aus dem Desktop Pane nacheinander nach hinten setzen
		for (int i = 0; i < panelFrames.getAllFrames().length; i++) {
			panelFrames.getAllFrames()[i].toBack();
		}
	}
}
