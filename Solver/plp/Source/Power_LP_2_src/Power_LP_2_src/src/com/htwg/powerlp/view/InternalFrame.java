package com.htwg.powerlp.view;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import com.htwg.powerlp.controller.Controller;
import com.htwg.powerlp.solver.SupportedSolver;
import com.htwg.powerlp.util.CommonUtils;
import com.htwg.powerlp.util.Configurator;
import com.htwg.powerlp.view.MatrixTable.TableType;

/**
 * Hier wird das Style des InternalFrames festgelegt
 * 
 * @author Teamprojekt WS 2015/2016 (Ducho, Keller, Lagun, Lu, Pllana)
 * @version 1.0
 * 
 *          Internal frame factory to create internal frames
 * 
 * @author Bastian Schoettle
 * @version 2.0
 */

public class InternalFrame {

	private MatrixTable tableValuesRestrictions;
	private JSpinner spinnerAmountRestrictions;
	private JSpinner spinnerAmountValue;
	private Controller controls;
	private JScrollPane scrollPaneValuesRestrictions;
	private MatrixTable tableBorders;
	private JComboBox<String> optionsMatrix;
	private JScrollPane scrollPaneBorders;
	private JComboBox<String> optionsInteger;
	private JComboBox<String> comboBoxMinMax;
	private JPanel panelBorders;
	private Checkbox checkboxBorders;
	private JInternalFrame internalFrame;
	private Configurator configurator;
	private int variableCount;
	private int restrictionCount;
	/**
	 * Private constructor to avoid instantiation
	 */
	private InternalFrame(String frameName, int boundFactor,
			Controller con, Configurator conf)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		this.controls = con;
		variableCount = 0;
		this.configurator = conf;
		// Parameter des Frames anlegen und bestimmen
		String name;
		if (frameName.equals(conf.getDefaultFile())) {
			name = new File(frameName).getName();
		} else {
			name = frameName;
		}
		setInternalFrame(new JInternalFrame(name));
		getInternalFrame().setName(frameName);

		// Beim Erstellen werden die Faktoren ber�cksichtigt, so dass Fenster
		// versetzt erscheint
		getInternalFrame().setBounds(76 + boundFactor, 34 + boundFactor,
				1200 + boundFactor, 800 + boundFactor);
		getInternalFrame().setResizable(true);
		getInternalFrame().setMaximizable(true);
		getInternalFrame().setIconifiable(true);
		getInternalFrame().setClosable(true);
		getInternalFrame().setVisible(true);
		getInternalFrame().setAutoscrolls(true);
		getInternalFrame().getContentPane().setLayout(
				new MigLayout("", "grow", "grow"));
		// Internalframe scrollbar implementieren
		JPanel mainPanel = new JPanel();
		JScrollPane mainScroller = new JScrollPane(mainPanel);
		getInternalFrame().getContentPane().add(mainScroller, "grow");

		// Layout f�r das Frame. In dieser Layoutform: ("",Zeilenvektor,
		// Spaltenvektor)
		mainPanel.setLayout(new MigLayout("", "[800,grow][325]",
				"[120][350,grow][450,grow]"));

		// Anlegen des Panels f�r allgemeine Informationen (Anzahl Variablen,
		// Restriktionen, MinMax etc.)
		JPanel panelInformations = new JPanel();
		mainPanel.add(panelInformations, "cell 0 0 2 1,grow");

		// Layout f�r das Panel. In dieser Layoutform: ("",Zeilenvektor,
		// Spaltenvektor)
		panelInformations.setLayout(new MigLayout("",
				"[120][110.00][40][100][110][40][40][40][][40][][][][]",
				"[26px]"));
		panelInformations.setName("panelInformation");

		// Hinzuf�gen der Elemente in das Panel (panelInformation)
		// Spinner Restriktionen (Tabellen-Reihen) mit seinem Label erstellen
		JLabel lblAmountRestrictions = new JLabel("Anzahl Restriktionen");
		lblAmountRestrictions.setName("lblAmountRestrictions");
		panelInformations.add(lblAmountRestrictions, "cell 0 0");

		spinnerAmountValue = new JSpinner();
		spinnerAmountValue.setModel(new SpinnerNumberModel(new Integer(2),
				new Integer(1), null, new Integer(1)));
		spinnerAmountValue.setName("spinnerAmountValue");
		spinnerAmountValue.setEditor(new JSpinner.DefaultEditor(spinnerAmountValue));
		panelInformations.add(spinnerAmountValue, "cell 4 0,grow");

		// Spinner Variablen (Tabellen-Spalten) mit seinem Label erstellen
		JLabel lblAmountValues = new JLabel("Anzahl Variablen");
		lblAmountValues.setName("lblAmountValues");
		panelInformations.add(lblAmountValues, "cell 3 0");

		spinnerAmountRestrictions = new JSpinner();
		
		spinnerAmountRestrictions.setModel(new SpinnerNumberModel(
				new Integer(2), new Integer(1), null, new Integer(1)));
		spinnerAmountRestrictions.setName("spinnerAmountRestrictions");
		spinnerAmountRestrictions.setEditor(new JSpinner.DefaultEditor(spinnerAmountRestrictions));
		panelInformations.add(spinnerAmountRestrictions, "cell 1 0,grow");

		// Erstellen der ComboBox f�r Min Max Auswahl
		comboBoxMinMax = new JComboBox<String>();
		comboBoxMinMax.addItem("max !");
		comboBoxMinMax.addItem("min !");
		comboBoxMinMax.setSelectedIndex(0);
		comboBoxMinMax.setName("comboBoxMinMax");
		panelInformations.add(comboBoxMinMax, "cell 6 0");

		// Button L�sen anlegen
		JButton btnSolve = new JButton("L\u00F6sen");
		btnSolve.setName("btnSolve");
		panelInformations.add(btnSolve, "cell 8 0");

		// Ein- bzw. Ausblenden der Grenzen-Integer-Tabelle CheckBox
		checkboxBorders = new Checkbox("Bounds/Integer");
		checkboxBorders.setState(true);
		checkboxBorders.setName("checkboxBorders");
		panelInformations.add(checkboxBorders, "cell 13 0");

		// Anlegen und Initialisieren der Matrix-Tabelle
		// Initialisieren der ComboBox Optionen f�r Spalte "" (Spalte vor der
		// "b" Spalte)
		setOptionsMatrix(new JComboBox<String>());
		//getOptionsMatrix().addItem("-->");
		getOptionsMatrix().addItem(">=");
		getOptionsMatrix().addItem("<=");
		getOptionsMatrix().addItem("=");
		getOptionsMatrix().setSelectedIndex(1);
		getOptionsMatrix().setName("optionsMatrix");
		// Anlegen des Panels f�r die Matrix-Tabelle
		JPanel panelMatrix = new JPanel();
		mainPanel.add(panelMatrix, "cell 0 1, grow");
		panelMatrix.setLayout(new MigLayout("", "[760.00,grow][]", "[grow]"));

		panelMatrix.setName("panelMatrix");
		// Anlegen der Matrix-Tabelle. Initialisiert mit Anzahl Spalten und
		// Zeilen, damit erster Aufruf erfolgreich ist
		tableValuesRestrictions = new MatrixTable(2, 4, TableType.RESTRICTION);
		tableValuesRestrictions.setName("tableValuesRestrictions");

		// Anlegen des Scrollpanels, damit die Tabelle darin gespeichert wird
		// und Scrollbar erscheint
		scrollPaneValuesRestrictions = new JScrollPane(
				tableValuesRestrictions);
		scrollPaneValuesRestrictions.setName("scrollPaneValuesRestrictions");
		scrollPaneValuesRestrictions
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableValuesRestrictions.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableValuesRestrictions.setRowHeight(18);
		tableValuesRestrictions.setVisible(true);
		panelMatrix.add(scrollPaneValuesRestrictions, "grow");

		// Erster Aufruf zum Initialiseren der Tabelle
		variableCount = (int) spinnerAmountValue.getValue();
		controls.repaintXYTable(
				(DefaultTableModel) tableValuesRestrictions.getModel(),
				tableValuesRestrictions,
				(Integer) spinnerAmountValue.getValue(),
				(Integer) spinnerAmountRestrictions.getValue(),
				scrollPaneValuesRestrictions, getOptionsMatrix(), RepaintReason.REPAINT_INIT);

		// Erstes Setzen der Standard ComboboxWerte
		getOptionsMatrix().setSelectedIndex(2);
		for (int i = 0; i < (Integer) spinnerAmountRestrictions.getValue(); i++) {
			tableValuesRestrictions.setValueAt(
					(String) getOptionsMatrix().getSelectedItem(), i + 1,
					(Integer) spinnerAmountValue.getValue() + 1);
		}
		getOptionsMatrix().remove(0);
		// optionsMatrix.setSelectedIndex(0);

		// Anlegen und Initialisieren der Grenzen-Integer-Tabelle
		// Initialisieren der ComboBox Optionen f�r Spalte "Integer"
		setOptionsInteger(new JComboBox<String>());
		getOptionsInteger().addItem("Ja");
		getOptionsInteger().addItem("Nein");
		getOptionsInteger().setSelectedIndex(1);
		getOptionsInteger().setName("optionsInteger");

		// Anlegen des Panels f�r die Grenzen-Integer-Tabelle
		panelBorders = new JPanel();
		panelBorders.setName("panelBorders");
		mainPanel.add(panelBorders, "cell 1 1,grow");
		panelBorders.setLayout(new MigLayout("", "[grow]", "[grow]"));

		// Anlegen der Grenzen-Integer-Tabelle. Initialisiert mit Anzahl
		// Spalten und Zeilen, damit erster Aufruf erfolgreich ist (4-Spalten
		// sind Pflicht)
		tableBorders = new MatrixTable(4, 4, TableType.BOUND);
		tableBorders.setName("tableBorders");

		// Anlegen des Scroll Panels, damit die Tabelle darin gespeichert wird
		// und Scrollbar erscheint
		scrollPaneBorders = new JScrollPane(tableBorders);
		scrollPaneBorders.setName("scrollPaneBorders");
		panelBorders.add(scrollPaneBorders, "cell 0 0, grow");
		scrollPaneBorders
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableBorders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableBorders.setRowHeight(18);
		tableBorders.setVisible(true);
		panelBorders.add(scrollPaneBorders, "grow");

		// Erster Aufruf zum Initialiseren der Tabelle
		controls.repaintXTable((DefaultTableModel) tableBorders.getModel(),
				tableBorders, (Integer) spinnerAmountValue.getValue(),
				scrollPaneBorders, getOptionsInteger());
		// Erster Aufruf zum Speichern des Min Max Wertes in der Tabelle
		controls.minMaxTableWriter(spinnerAmountRestrictions,
				(DefaultTableModel) tableValuesRestrictions.getModel(),
				tableValuesRestrictions, comboBoxMinMax);
		// Erster Aufruf und Abfrage der CheckBox f�r das Ein- bzw. Ausblenden
		// der
		// Grenzen-Integer-Tabelle
		controls.setPanelVisiblility(panelBorders, checkboxBorders.getState());

		// Anlegen und Initialisieren des Panels f�r Solver Tabbed Frames
		JPanel panelSolver = new JPanel();
		mainPanel.add(panelSolver, "cell 0 2 2 1,grow");
		MigLayout panelSolverLayout = new MigLayout("", "[grow]", "[grow]");
		panelSolver.setLayout(panelSolverLayout);
		panelSolver.setName("panelSolver");

		// Farbe f�r Tabbed Frames bestimmen und ihre Komponente anlegen
		Color bg = Color.white;
		JTabbedPane tabbedPaneSolver = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneSolver.setName("tabbedPaneSolver");
		tabbedPaneSolver.setEnabled(false);
		panelSolver.add(tabbedPaneSolver, "cell 0 0, grow");
		tabbedPaneSolver.setBackground(bg);

		fillSolverTabs(tabbedPaneSolver, configurator);

		// Den erstellten Internalframe anzeigen
		getInternalFrame().setVisible(true);

		// Listener f�r alle Elemente (�bersichtshalber alle an einem Ort)
		spinnerAmountValue.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				RepaintReason reason;
				if ((int)spinner.getValue() > variableCount) {
					reason = RepaintReason.REPAINT_VAR_INC;
				} else {
					reason = RepaintReason.REPAINT_VAR_RED;
				}
				variableCount = (int)spinner.getValue();
				// Die Matrix-Tabelle und Grenzen-Integer-Tabelle aktualisieren
				controls.repaintXYTable(
						(DefaultTableModel) tableValuesRestrictions.getModel(),
						tableValuesRestrictions,
						(Integer) spinnerAmountValue.getValue(),
						(Integer) spinnerAmountRestrictions.getValue(),
						scrollPaneValuesRestrictions, getOptionsMatrix(), reason);

				controls.repaintXTable(
						(DefaultTableModel) tableBorders.getModel(),
						tableBorders, (Integer) spinnerAmountValue.getValue(),
						scrollPaneBorders, getOptionsInteger());
			}
		});

		spinnerAmountRestrictions.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				RepaintReason reason;
				if ((int) spinner.getValue() > restrictionCount) {
					reason = RepaintReason.REPAINT_CONST_INC;
				} else {
					reason = RepaintReason.REPAINT_CONST_RED;
				}
				restrictionCount = (int)spinner.getValue();
				// Die Matrix-Tabelle aktualisieren
				controls.repaintXYTable(
						(DefaultTableModel) tableValuesRestrictions.getModel(),
						tableValuesRestrictions,
						(Integer) spinnerAmountValue.getValue(),
						(Integer) spinnerAmountRestrictions.getValue(),
						scrollPaneValuesRestrictions, getOptionsMatrix(), reason);
			}
		});

		comboBoxMinMax.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// Schreiben des MinMAx in eine Zelle der MAtrix-Tabelle
				controls.minMaxTableWriter(spinnerAmountRestrictions,
						(DefaultTableModel) tableValuesRestrictions.getModel(),
						tableValuesRestrictions, comboBoxMinMax);
			}
		});

		checkboxBorders.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// Grenzen-Integer-Tabelle ein- bzw. ausblenden
				controls.setPanelVisiblility(panelBorders,
						checkboxBorders.getState());
			}
		});

		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Solve instance
				tableValuesRestrictions.clearSelection();
				controls.solve(getInternalFrame());
			}
		});

		tabbedPaneSolver.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				JTabbedPane source = (JTabbedPane) event.getSource();
				System.out.println(source.getSelectedIndex()
						+ " "
						+ configurator.getIndexSolverMapping().get(
								source.getSelectedIndex()));
				// Switch tabs
				controls.tabsSwitcher(getInternalFrame(), configurator
						.getIndexSolverMapping().get(source.getSelectedIndex()));
			}
		});

	}

	/**
	 * 
	 * Factory method to create sub windows
	 * 
	 * @param frameName
	 * @param boundFactor
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static JInternalFrameWrapper createFrame(String frameName,
			int boundFactor, Controller controls, Configurator configurator)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		InternalFrame frame = new InternalFrame(frameName, boundFactor, controls, configurator);
		return new JInternalFrameWrapper(frame);
	}

	/**
	 * Create solver pane
	 * 
	 * @param displayName
	 * @param internalId
	 */
	private JScrollPane createSolverScrollPane(int internalId) {
		JTextPane solverDataPane = new JTextPane();
		solverDataPane.setText(" ");
		solverDataPane.setFont(new Font("", 0, 14));
		JScrollPane solverPane = new JScrollPane(solverDataPane);
		solverPane.setName(String.valueOf(internalId));
		return solverPane;
	}

	/**
	 * Generates tab for each configured solver Checks if Clpex and Gurobi
	 * licenses are valid
	 * 
	 * @param tabbedPaneSolver
	 * @param configurator
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void fillSolverTabs(JTabbedPane tabbedPaneSolver,
			Configurator configurator) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// Iterate over solvers
		int i = 0;
		for (SupportedSolver solver : SupportedSolver.values()) {
			// if solver is active
			if (CommonUtils.isSolverActive(solver.getSolver(), configurator)) {
				JScrollPane solverPane = createSolverScrollPane(solver.getId());
				if (solver == SupportedSolver.CPLEX) {
					String message = null;
					if (!CommonUtils.isFilePresent(configurator.getCplexDir()
							+ configurator.getCplexLicenseFile())) {
						System.out.println("Unable to find license file:"
								+ configurator.getCplexDir()
								+ configurator.getCplexLicenseFile());
						message = String.format(
								configurator.getMissingLicenseMessage(),
								solver.getSolver());
						tabbedPaneSolver.addTab(solver.getSolver(), null,
								solverPane, message);
						configurator.setClplexLicenseValid(false);
					} else {
						tabbedPaneSolver.addTab(solver.getSolver(), null,
								solverPane, message);
						configurator.setClplexLicenseValid(true);
					}
					configurator.addIndexMapping(i, solver.getId());
				} else if (solver == SupportedSolver.GUROBI) {
					String message = null;
					if (!CommonUtils.isFilePresent(configurator.getGurobiDir()
							+ configurator.getGurobiLicenseFile())) {
						message = String.format(
								configurator.getMissingLicenseMessage(),
								solver.getSolver());
						tabbedPaneSolver.addTab(solver.getSolver(), null,
								solverPane, message);
						configurator.setGurobiLicenseValid(false);
					} else {
						tabbedPaneSolver.addTab(solver.getSolver(), null,
								solverPane, message);
						configurator.setGurobiLicenseValid(true);
					}
					configurator.addIndexMapping(i, solver.getId());
				} else {
					tabbedPaneSolver.addTab(solver.getSolver(), null,
							solverPane, null);
					configurator.addIndexMapping(i, solver.getId());
				}
				i++;
			}
		}
	}

	public JInternalFrame getInternalFrame() {
		return internalFrame;
	}

	public void setInternalFrame(JInternalFrame internalFrame) {
		this.internalFrame = internalFrame;
	}

	public JComboBox<String> getOptionsMatrix() {
		return optionsMatrix;
	}

	public void setOptionsMatrix(JComboBox<String> optionsMatrix) {
		this.optionsMatrix = optionsMatrix;
	}

	public JComboBox<String> getOptionsInteger() {
		return optionsInteger;
	}

	public void setOptionsInteger(JComboBox<String> optionsInteger) {
		this.optionsInteger = optionsInteger;
	}
}
