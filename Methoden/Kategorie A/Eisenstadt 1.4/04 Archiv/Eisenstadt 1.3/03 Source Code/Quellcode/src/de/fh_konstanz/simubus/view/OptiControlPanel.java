package de.fh_konstanz.simubus.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import sun.awt.image.ToolkitImage;
import sun.awt.image.URLImageSource;
import de.fh_konstanz.simubus.controller.TableListSelectionListener;
import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.model.optimierung.BasicSolution;
import de.fh_konstanz.simubus.model.optimierung.BasicVariable;
import de.fh_konstanz.simubus.model.optimierung.Result;
import de.fh_konstanz.simubus.model.optimierung.TargetBuilder;
import de.fh_konstanz.simubus.model.optimierung.TargetDetails;
import de.fh_konstanz.simubus.util.DateUtil;
import de.fh_konstanz.simubus.util.ImageUtil;
import de.fh_konstanz.simubus.util.OrOptiIterator;
import de.fh_konstanz.simubus.util.OrUtil;

/**
 * Die Klasse <code>OptiControlPanel</code> stellt Einstellmoeglichkeiten fuer
 * die Optimierung zur Verfuegung. Nach der Optimierung werden die einzelnen
 * Loesungen mit zusaetzlichen Informationen angezeigt.
 * 
 * @author Ingo Kroh
 * @version 1.0 (01.07.2005)
 * 
 * @author Philipp Hofmann
 * @version 1.1 (5.1.2007) Der Benutzer wird nun gewarnt, wenn ein Ziel nicht
 *          mit der Gehzeit erreicht werden kann
 * 
 */
public class OptiControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Button zum Starten der Optimierung */
	private JButton bStarten = null;

	/** ComboBox zum Einstellen der maximalen Restgehzeit */
	private JComboBox maxComboBox = null;

	/** Label zum Anzeigen von Informationen einer Loesung */
	// private JLabel solutionInfoLabel = null;
	/** Ueberschrift fuer ComboBox */
	private JLabel maxRestgehLabel = null;

	/** Panel fuer Einstellungen */
	private JPanel configPanel = null;

	/** Stop Button */
	private JButton stopButton = null;

	/** Panel fuer Loesungen */
	private JPanel solutionPanel = null;

	/** Panel fuer Informationen zur Loesung */
	private JPanel solutionInfoPanel = null;

	/** ComboBox mit Loesungen */
	// private JComboBox solutionComboBox = null;
	/** Singleton Instanz */
	private static OptiControlPanel instance = null;

	/** ProgressBar fuer Gesamtfortschritt */
	private JProgressBar progressBar = null;

	/** Fenster fuer Fortschritt der Optimierung */
	private JFrame progressFrame = null;

	/** Label zum Anzeigen der momentan ausgefuehrten Aktion */
	private JLabel progressInfo = null;

	/** ProgressBar fuer momentan ausgefuehrte Aktion */
	private JProgressBar detailProgressBar = null;

	/** Spalten Namen von die Lösungs Tabelle */
	private Object[] resultColumns = { "Lösung", "Datum", "Uhrzeit",
			"Gesamtzeit", "Durchschnittszeit" };

	/** Lösungs Liste */
	private List<Result> resultList = null;

	/** Anzahl von Spalten */
	private int columns[] = { 0, 1, 2, 3, 4 };

	/** Spalten Breite */
	private int columnWidths[] = { 50, 51, 60, 55, 100 };

	/** Ob die Spalten Grösse veränderbar ist */
	private boolean resizables[] = { true, true, true, true, true };

	/** Instanz von Spalten Eigenschafts Klasse */
	private ColumnProperties columnWidth;

	/** Lösungs Panel Breite */
	private int solutionPanelWidth = 400;

	/** Lösungs Info Panel Breite */
	private int solutionInfoPanelWidth = 400;

	/** Überprüfung ob die Simulation beendet wurde */
	private boolean simulationStopped = false;

	/** Überprüfung ob die Optimierung gestartet wurde */
	private boolean optimierungRun = true;

	/** Instanz von Optimierungs Start Klasse */
	private StartOptimierung startOptimierung = new StartOptimierung();

	/** Überprüfung ob optimierung gestoppt wurde */
	private boolean optimierungInterrupted = true;

	/** enthaelt alle Strassen, Haltestellen, usw. */
	// private Strassennetz netz = null;
	/**
	 * liefert ein <code>OptiControlPanel</code>-Objekt
	 * 
	 * @return ein <code>OptiControlPanel</code>-Objekt
	 */
	public static OptiControlPanel getInstance() {
		if (instance == null) {
			instance = new OptiControlPanel();
		}

		return instance;
	}

	/**
	 * Konstruktor
	 * 
	 */
	private OptiControlPanel() {

		SimuKonfiguration config = SimuKonfiguration.getInstance();
		this.setLayout(null);

		this.setMinimumSize(new Dimension(150, config.getResPanel()));
		this.setPreferredSize(new Dimension(150, config.getResPanel()));
		this
				.setBounds(25 + config.getResPanel(), 15, 100, config
						.getResPanel());

		configPanel = new JPanel();
		configPanel.setLayout(null);
		configPanel
				.setBorder(BorderFactory.createTitledBorder("Einstellungen"));
		configPanel.setSize(new Dimension(400, 90));
		this.add(configPanel);

		solutionPanel = new JPanel();
		solutionPanel.setLayout(new GridLayout());
		solutionPanel.setBorder(BorderFactory.createTitledBorder("Ergebnisse"));
		solutionPanel.setBounds(0, 100, solutionPanelWidth, 300);
		this.add(solutionPanel);

		solutionInfoPanel = new JPanel();
		solutionInfoPanel.setLayout(new GridLayout());
		solutionInfoPanel.setBorder(BorderFactory
				.createTitledBorder("Ergebnis-Informationen"));
		solutionInfoPanel.setBounds(0, 410, solutionInfoPanelWidth, 200);
		this.add(solutionInfoPanel);

		maxRestgehLabel = new JLabel("Maximale Restgehzeit in Minuten");
		maxRestgehLabel.setName("maxRestgeh");
		maxRestgehLabel.setBounds(20, 20, 200, 20);

		configPanel.add(maxRestgehLabel);

		maxComboBox = new JComboBox();
		maxComboBox.setBounds(20, 50, 50, 20);

		for (int i = 1; i < 11; i++) {
			maxComboBox.addItem(i);
		}

		configPanel.add(maxComboBox);

		bStarten = new JButton("Optimierung starten");
		bStarten.setBounds(100, 620, 150, 25);
		bStarten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				/**
				 * Alte Lösungen aus Tabelle entfernen
				 */
				OptiControlPanel.getInstance().resetSolutions();

				// Startzeit setzen
				DateUtil du = DateUtil.getInstance();
				du.setStartTime();

				Strassennetz netz = Strassennetz.getInstance();
				if (netz.isEmpty() == false) {
					TargetBuilder targetBuilder = new TargetBuilder(
							(Integer) maxComboBox.getSelectedItem());
					List<TargetDetails> targets = targetBuilder
							.buildTargetDetails(detailProgressBar);
					boolean goOn = true;
					for (TargetDetails target : targets) {
						if (target.getNotInRange()) {
							if (JOptionPane
									.showOptionDialog(
											progressFrame,
											" Nicht alle Ziele können in der vorgegebenen Zeit erreicht werden. Wollen Sie trotzdem weitermachen und einen Minimalwert benutzen?",
											"Warnung",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null,
											null, null) == JOptionPane.YES_OPTION) {
								goOn = true;
							} else {
								goOn = false;
							}
							break;
						}
					}

					if (goOn) {
						progressFrame.setVisible(true);

						final Thread t = new Thread(new Runnable() {

							public void run() {
								startOptimierung.run();

								if (optimierungRun == true
										&& simulationStopped == false) {
									optimierungRun = false;
									createResultTable();
								}

								if (SimuKonfiguration.getInstance()
										.checkSound()) {
									// make a signal after the optimizing
									File audioFile = new File(System
											.getProperty("user.dir")
											+ "/sound/bus_horn.wav");
									try {
										AudioInputStream in = AudioSystem
												.getAudioInputStream(audioFile);
										DataLine.Info info = new DataLine.Info(
												Clip.class, in.getFormat());
										Clip clip = (Clip) AudioSystem
												.getLine(info);
										clip.open(in);
										clip.start(); // start die audiofile
									} catch (Exception e) {
										e.printStackTrace();
									}
								}

								progressFrame.setVisible(false);

								// dauer ausgeben
								/*
								 * DateUtil du = DateUtil.getInstance();
								 * du.setEndTime(); JOptionPane
								 * .showMessageDialog( View.getInstance(),
								 * "Benötigte Zeit:
								 * "+du.getTimeDiffAsString()+"\n" + "Erweiterte
								 * Zeit: "+du.getTimeDiffExtended(), "Info",
								 * JOptionPane.INFORMATION_MESSAGE);
								 */

								// Endszeit setzen
								if (simulationStopped == false) {
									DateUtil du = DateUtil.getInstance();
									du.setEndTime();
									JOptionPane.showMessageDialog(
											View.getInstance(),
											// Benötigte Zeit:
											// "+du.getTimeDiffAsString()+"\n" +
											// "Erweiterte Zeit:
											// "+du.getTimeDiffExtended(),
											// "Info",
											// JOptionPane.INFORMATION_MESSAGE);
											"Benötigte Zeit für die Berechnung: "
													+ du.getTimeDiffAsString()
													+ "\n",
											"Dauer von Operation Research",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									simulationStopped = false;
								}

							}
						});

						// stopButton ActionListener
						stopButton.addActionListener(new ActionListener() {
							@SuppressWarnings("deprecation")
							public void actionPerformed(ActionEvent e) {
								if (optimierungInterrupted == true
										&& JOptionPane
												.showOptionDialog(
														progressFrame,
														"Wollen Sie Sie die Optimierung abbrechen?",
														"Abbrechen",
														JOptionPane.YES_NO_OPTION,
														JOptionPane.QUESTION_MESSAGE,
														null, null, null) == JOptionPane.YES_OPTION) {
									simulationStopped = true;
									t.interrupt();
									progressFrame.setVisible(false);
									startOptimierung.interrupt();
									OptiControlPanel.getInstance()
											.resetSolutions();
									optimierungInterrupted = false;
								}
							}
						});

						t.start();
					}
				} else {
					JOptionPane
							.showMessageDialog(
									View.getInstance(),
									"Für die Optimierung fehlt entweder ein Ziel oder eine Strasse",
									"Bus Simulation", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		this.add(bStarten);

		// Stop Funktion einbauen
		stopButton = new JButton("Optimierung stoppen");
		stopButton.setSize(new Dimension(100, 20));
		stopButton.setToolTipText("Optimierung stoppen");
		stopButton.setName("Optimierung stoppen");

		progressInfo = new JLabel();
		progressInfo.setSize(300, 25);

		progressBar = new JProgressBar(0, 4);
		progressBar.setStringPainted(true);
		detailProgressBar = new JProgressBar();
		detailProgressBar.setStringPainted(true);

		progressFrame = new JFrame("Fortschritt Optimierung");
		progressFrame.setIconImage(new ToolkitImage(new URLImageSource(
				ImageUtil.getImageUrl("haltestelle.png"))));
		GridLayout gl = new GridLayout(5, 1, 0, 5);
		progressFrame.setLayout(gl);

		progressFrame.setResizable(false);
		progressFrame.setBounds(300, 250, 350, 200);
		progressFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		progressFrame.add(new JLabel("Gesamtfortschritt"));
		progressFrame.add(progressBar);
		progressFrame.add(progressInfo);
		progressFrame.add(detailProgressBar);
		progressFrame.add(stopButton);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		SwingUtilities.updateComponentTreeUI(progressFrame);
		// netz = Strassennetz.getInstance();

	}

	/**
	 * Ermittelt die optimale Anzahl und Positionen der Haltestellen
	 * 
	 * @return true, wenn keine Fehler bei der Otimierung aufgetreten sind,
	 *         sonst false
	 */
	public final class StartOptimierung extends Thread {

		public void run() {

			try {
				optimierungInterrupted = true;
				columnWidth = new ColumnProperties(columns, columnWidths,
						resizables);

				progressBar.setValue(0);
				detailProgressBar.setValue(0);

				// TODO
				progressInfo
						.setText("Ermittle gültige Positionen für Haltestellen...");
				TargetBuilder targetBuilder = new TargetBuilder(
						(Integer) maxComboBox.getSelectedItem());
				List<TargetDetails> targets = targetBuilder
						.buildTargetDetails(detailProgressBar);
				progressBar.setValue(progressBar.getValue() + 1);

				progressInfo.setText("Erstelle OR-Modell ...");
				detailProgressBar.setValue(0);
				double[][] orModel = OrUtil.createOrMatrix(targets,
						detailProgressBar);

				// OrOptiIterator it = OrOptiIterator.getInstance(orModel);
				OrOptiIterator it = OrOptiIterator
						.createNewSingletonInstance(orModel);

				it.transform();
				progressBar.setValue(progressBar.getValue() + 1);

				progressInfo.setText("Löse OR-Modell ...");
				detailProgressBar.setValue(0);
				Set<BasicSolution> basicSolutions = it.solve(detailProgressBar);
				progressBar.setValue(progressBar.getValue() + 1);
				progressInfo.setText("Sortiere Lösungen ...");
				resultList = OrUtil.createResultList(basicSolutions, targets);

				/**
				 * Falls es keine Lösungen gegeben hat
				 */
				if (resultList.size() == 0) {
					JOptionPane.showMessageDialog(View.getInstance(),
							"Keine Lösungen für die aktuelle Optimierung");
					optimierungRun = false;
					return;
				}

				Collections.sort(resultList);

				addResultId(resultList);

				detailProgressBar.setValue(detailProgressBar.getMaximum());
				progressBar.setValue(progressBar.getValue() + 1);

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(View.getInstance(),
						"Bei der Optimierung ist folgender Fehler aufgetreten: "
								+ e.getMessage(), "Bus Simulation",
						JOptionPane.ERROR_MESSAGE);
				optimierungRun = false;
				return;
			}

			optimierungRun = true;
		}

	}

	/**
	 * fuegt Loesungen eine ID hinzu
	 * 
	 * @param resultList
	 *            Liste mit Loesungen
	 */
	private void addResultId(List<Result> resultList) {
		Iterator<Result> resultIterator = resultList.iterator();

		int i = 1;

		while (resultIterator.hasNext() == true) {
			resultIterator.next().setId(String.valueOf(i++));
		}
	}

	/**
	 * aktualisiert die Haltestellen auf der Karte
	 * 
	 */
	public void updateEditor(int resultId) {

		if (resultId <= resultList.size()) {

			Result result = (Result) resultList.get(resultId);

			if (result != null) {
				BasicSolution basicSolution = result.getBasicSolution();
				BasicVariable[] v = basicSolution.getBasicVariables();

				Strassennetz.getInstance().resetHaltestellen();
				SimuPanel.getInstance().removeHaltestellen();
				VirtualGrid virtualGrid = VirtualGrid.getInstance();
				Planquadrat[][] planquadrate = virtualGrid.getPlanquadrate();

				for (int i = 0; i < v.length; i++) {
					if (v[i].getValue() == 1) {
						int y = OrUtil.getYCoordinate(v[i]);
						int x = OrUtil.getXCoordinate(v[i], y);

						SimuPanel.getInstance().insertHaltestelle(
								planquadrate[x][y].getPixelCoordinates(),
								"Haltestelle " + (i + 1));
					}
				}
			}
		}

	}

	/**
	 * Lösungs Tabelle erzeugen
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void createResultTable() {
		solutionPanel.removeAll();
		solutionInfoPanel.removeAll();

		Object[][] rowData = null;
		rowData = new String[resultList.size()][resultColumns.length];

		for (int i = 0; i < resultList.size(); i++) {

			Result result = (Result) resultList.get(i);
			String date = "";
			String time = "";

			if (result != null) {

				rowData[i][0] = String.valueOf(result.getId());

				GregorianCalendar resultDate = result.getDateAndTime();

				date = resultDate.get(Calendar.DATE) + "."
						+ (resultDate.get(Calendar.MONTH) + 1) + "."
						+ resultDate.get(Calendar.YEAR);
				rowData[i][1] = date;

				time = resultDate.get(Calendar.HOUR_OF_DAY) + ":"
						+ resultDate.get(Calendar.MINUTE) + ":"
						+ resultDate.get(Calendar.SECOND);

				rowData[i][2] = time;

				rowData[i][3] = String.valueOf(result.getTotalTime());
				rowData[i][4] = String.valueOf(result.getAverageTime());

			}

		}

		Table resultTable = new Table(solutionPanel.getWidth(), resultColumns);
		resultTable.setRowData(rowData);

		solutionPanel.add(resultTable.getScrollPane("Ergebnisse"));
		JTable table = resultTable.getTable();
		resultTable.changeColumnWidth(columnWidth);
		this.revalidate();
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new TableListSelectionListener(table,
						solutionInfoPanel, resultList));

		/**
		 * Erste Lösung auswählen lassen
		 */
		listSelectionModel.setSelectionInterval(0, 0);

		table.addMouseListener(new SelectTable(resultTable));

	}

	/**
	 * Lösungen löschen, Lösung und Lösungs Info Panel auch löschen
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void resetSolutions() {
		
		if (resultList != null) {
			this.resultList.clear();
		}

		this.solutionPanel.removeAll();
		this.solutionInfoPanel.removeAll();
		this.disable();
		this.repaint();
	}

}