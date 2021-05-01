package de.fh_konstanz.simubus.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;

import de.fh_konstanz.simubus.controller.HaltestellenTableController;
import de.fh_konstanz.simubus.controller.IntegerEditor;
import de.fh_konstanz.simubus.controller.SimuButtonListener;
import de.fh_konstanz.simubus.controller.SimuListBoxActionListener;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.HaltestellenTable;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.SimpleDate;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Teilstrecke;
import de.fh_konstanz.simubus.util.LayoutUtil;

/**
 * Die Klasse <code>SimuControlPanel</code> zeigt Informationen über
 * eingezeichnete Haltestellen und Buslinien und stellt Einstellmöglichkeiten
 * für die Simulation zur Verfügung. Während der Simulation bekommt der Benutzer
 * Informationen zum Ablauf. Zusätzlich besteht die Möglichkeit sich nach der
 * Simulation diverse Auswertungen anzeigen zu lassen.
 * 
 * @author Daniel Weber
 * @version 1.0 (01.07.2005)
 * 
 */

public class SimuControlPanel extends JPanel {
	/** ID für Serialisierung */
	private static final long serialVersionUID = -6367521922850785842L;

	/**
	 * TabbedPane für
	 */
	private JTabbedPane tp;

	/**
	 * Eine JTable für die Haltestellen
	 */
	private JTable haltestellenTable;

	/**
	 * Button um eine neue Buslinie zu erzeugen
	 */
	private JButton linieNeu;

	/**
	 * Button um eine Buslinie zu löschen
	 */
	private JButton linieLoeschen;

	/**
	 * Button um eine selektierte Buslinie zu bearbeiten
	 */
	private JButton linieBearbeiten;

	/**
	 * Textfeld für die Breite einer Teilstrecke
	 */
	private JTextField breite;

	/**
	 * Textfeld für die erlaubte Geschwindigkeit einer Teilstrecke
	 */
	private JTextField geschwindigkeit;

	/**
	 * Label für Textfeld breite
	 */
	private JLabel breiteLabel;

	/**
	 * Label für ComboBox cbStartzeit
	 */
	private JLabel startZeitLabel;

	/**
	 * Label für ComboBox cbEndzeit
	 */
	private JLabel endZeitLabel;

	/**
	 * Label für Textfeld geschwindigkeit
	 */
	private JLabel geschwindigkeitLabel;

	/**
	 * JList für die Buslinien
	 */
	@SuppressWarnings("rawtypes")
	private JList linienList;

	/**
	 * JList für die Teilstrecken
	 */
	@SuppressWarnings("rawtypes")
	private JList teilstreckeList;

	/**
	 * Singleton Instanz
	 */
	private static SimuControlPanel instance = null;

	/**
	 * ComboBox für Startzeit der Simulation
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox cbStartzeit = null;

	/**
	 * ComboBox für Endzeit der Simulation
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox cbEndzeit = null;

	/**
	 * ComboBox für die Geschwindigkeit der Simulation
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox cbSimuGeschwindigkeit = null;

	/**
	 * ArrayList mit Haltestellen
	 */
	private ArrayList<Haltestelle> halteStellen;

	/**
	 * Label zum Anzeigen der Linie
	 */
	private JLabel lblLinie;

	/**
	 * Label zum Anzeigen der Linien-ID
	 */
	private JLabel linieID;

	/**
	 * Label zum Anzeigen der Haltestelle
	 */
	private JLabel lblHS;

	/**
	 * Label zum Anzeigen der Haltestellen-ID
	 */
	private JLabel hsID;

	/**
	 * Label für Warteschlange im Bus
	 */
	private JLabel lblQueueLengthImBus;

	/**
	 * Label zum Anzeigen der Warteschlangen-Länge im Bus
	 */
	private JLabel QueueLengthBus;

	/**
	 * Label für Warteschlange an Haltestelle
	 */
	private JLabel lblQueueLengthHS;

	/**
	 * Label zum Anzeigen der Warteschlangen-Länge
	 */
	private JLabel QueueLengthHS;

	/**
	 * Label für Anzahl aussteigender Passagiere
	 */
	private JLabel lblanzPassagiereSteigenAus;

	/**
	 * Label zum Anzeigen der Anzahl aussteigender Passagiere
	 */
	private JLabel lblanzPassagiereSteigenEin;

	/**
	 * Label für Anzahl einsteigender Passagiere
	 */
	private JLabel anzPassagiereSteigenAus;

	/**
	 * Label zum Anzeigen der Anzahl einsteigender Passagiere
	 */
	private JLabel anzPassagiereSteigenEin;

	/**
	 * liefert ein <code>SimuControlPanel</code>-Objekt
	 * 
	 * @return ein <code>SimuControlPanel</code>-Objekt
	 */
	public static SimuControlPanel getInstance() {
		if (instance == null) {
			instance = new SimuControlPanel();
		}
		return instance;
	}

	/**
	 * initialisiert einige Objekte
	 */
	public void initialize() {
		halteStellen = new ArrayList<Haltestelle>(Strassennetz.getInstance()
				.getAlleHaltestellen());
	}

	/**
	 * Konstruktor
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SimuControlPanel() {
		this.initialize();
		this.setLayout(new BorderLayout());

		this.setMinimumSize(new Dimension(100, SimuKonfiguration.getInstance()
				.getResPanel()));
		this.setPreferredSize(new Dimension(100, SimuKonfiguration
				.getInstance().getResPanel()));
		this.setBounds(25 + SimuKonfiguration.getInstance().getResPanel(), 15,
				100, SimuKonfiguration.getInstance().getResPanel());

		JPanel haltestellenPane = new JPanel();

		HaltestellenTable model = HaltestellenTable.getInstance();
		model.setHaltestellen(halteStellen);
		haltestellenTable = new JTable(model, null);
		haltestellenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		haltestellenTable.getSelectionModel().addListSelectionListener(
				new HaltestellenTableController());
		haltestellenTable.getModel().addTableModelListener(
				new HaltestellenTableController());
		haltestellenTable.setDefaultEditor(Integer.class, new IntegerEditor(0,
				100));
		haltestellenTable.getColumnModel().getColumn(4).setCellEditor(
				new IntegerEditor(0, 100));
		// haltestellenTable.setPreferredSize(new Dimension(100, 100));
		haltestellenTable.setLocation(0, 0);
		JScrollPane hsPane = new JScrollPane(haltestellenTable);
		hsPane.setPreferredSize(new Dimension(330, SimuKonfiguration
				.getInstance().getResPanel() - 100));
		hsPane.setAutoscrolls(true);
		haltestellenPane.add(hsPane, BorderLayout.CENTER);
		linieLoeschen = new JButton("Löschen");
		linieNeu = new JButton("Hinzufügen");
		linieBearbeiten = new JButton("Bearbeiten");
		// linieNeu.setPreferredSize( new Dimension(
		// linieLoeschen.getPreferredSize() ) );

		JPanel linienPane = new JPanel();
		// linienPane.setPreferredSize(new Dimension(330, config.getResPanel() -
		// 100));

		JPanel lPane = new JPanel();
		lPane.setBorder(BorderFactory.createTitledBorder("Linien"));
		linienPane.add(lPane, BorderLayout.NORTH);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc;
		lPane.setLayout(gbl);
		lPane.setPreferredSize(new Dimension(330, 250));
		linienList = new JList(Strassennetz.getInstance().getArrayBuslinie()
				.toArray());
		linienList.setName("buslinien");
		linienList.addMouseListener(new SimuListBoxActionListener());
		linienList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane linienScrollPane = new JScrollPane(linienList);
		linienScrollPane.setPreferredSize(new Dimension(150, 200));
		linienScrollPane.setAutoscrolls(true);
		linieLoeschen.setActionCommand("removeBuslinie");
		linieLoeschen.addMouseListener(new SimuButtonListener());
		linieLoeschen.setPreferredSize(new Dimension(100, 25));
		linieNeu.setActionCommand("addBuslinie");
		linieNeu.addMouseListener(new SimuButtonListener());
		linieNeu.setPreferredSize(new Dimension(100, 25));
		linieBearbeiten.setActionCommand("editBuslinie");
		linieBearbeiten.addMouseListener(new SimuButtonListener());
		linieBearbeiten.setPreferredSize(new Dimension(100, 25));
		gbc = makegbc(0, 0, 1, 4);
		gbc.weightx = 100;
		gbc.weighty = 100;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(linienScrollPane, gbc);
		lPane.add(linienScrollPane);

		gbc = makegbc(1, 0, 2, 1);
		gbc.weightx = 80;
		gbc.fill = GridBagConstraints.NONE;
		gbl.setConstraints(linieNeu, gbc);
		lPane.add(linieNeu);

		gbc = makegbc(1, 1, 2, 1);
		gbc.weightx = 80;
		gbc.fill = GridBagConstraints.NONE;
		gbl.setConstraints(linieBearbeiten, gbc);
		lPane.add(linieBearbeiten);

		gbc = makegbc(1, 2, 2, 1);
		gbc.weightx = 80;
		gbc.fill = GridBagConstraints.NONE;
		gbl.setConstraints(linieLoeschen, gbc);
		lPane.add(linieLoeschen);

		JPanel streckePane = new JPanel();
		streckePane.setBorder(BorderFactory
				.createTitledBorder("Streckenabschnitte"));
		streckePane.setPreferredSize(new Dimension(330, 300));
		GridBagLayout gbl2 = new GridBagLayout();
		GridBagConstraints gbc2;
		streckePane.setLayout(gbl2);
		linienPane.add(streckePane, BorderLayout.SOUTH);

		teilstreckeList = new JList();
		teilstreckeList.setName("teilstrecken");

		teilstreckeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		teilstreckeList.addMouseListener(new SimuListBoxActionListener());
		JScrollPane teilstreckeScrollPane = new JScrollPane(teilstreckeList);
		teilstreckeScrollPane.setPreferredSize(new Dimension(150, 200));
		teilstreckeScrollPane.setAutoscrolls(true);

		gbc2 = makegbc(0, 0, 1, 3);
		gbc2.weightx = 900;
		gbc2.weighty = 500;
		gbc2.fill = GridBagConstraints.BOTH;
		gbl2.setConstraints(teilstreckeScrollPane, gbc2);
		streckePane.add(teilstreckeScrollPane);

		JButton loeschen = new JButton("Abschnitt löschen");
		loeschen.setActionCommand("deleteTeilstrecke");
		loeschen.addMouseListener(new SimuButtonListener());
		loeschen.setToolTipText("Es kann immer nur die zuletzt hinzugefügte Teilstrecke einer Linie gelöscht werden");
		gbc2 = makegbc(1, 0, 2, 1);
		gbc2.weightx = 50;
		gbc2.fill = GridBagConstraints.NONE;
		gbl2.setConstraints(loeschen, gbc2);
		streckePane.add(loeschen);
		teilstreckeList.setToolTipText("<html>Um eine Teilstrecke hinzuzufügen, die Linie auswählen und mit gedrückter <br> Maustaste die Starthaltestelle mit der Zielhaltestelle verbinden");
		ToolTipManager.sharedInstance().registerComponent(teilstreckeList);
		gbc2 = makegbc(0, 4, 0, 0);
		gbc2.weightx = 100;
		gbc2.weighty = 100;
		gbc2.fill = GridBagConstraints.BOTH;
		JPanel eigenschaften = new JPanel();
		eigenschaften.setBorder(BorderFactory
				.createTitledBorder("Streckeneigenschaften"));
		gbl2.setConstraints(eigenschaften, gbc2);
		// streckePane.add(eigenschaften);

		GridBagLayout gbl3 = new GridBagLayout();
		GridBagConstraints gbc3;
		eigenschaften.setLayout(gbl3);
		breiteLabel = new JLabel("Breite");
		gbc3 = makegbc(0, 0, 1, 1);
		gbc3.fill = GridBagConstraints.NONE;
		gbl3.setConstraints(breiteLabel, gbc3);
		eigenschaften.add(breiteLabel);

		breite = new JTextField();
		gbc3 = makegbc(1, 0, 1, 1);
		gbc3.weightx = 80;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbl3.setConstraints(breite, gbc3);
		eigenschaften.add(breite);

		geschwindigkeitLabel = new JLabel("Geschwindigkeit");
		gbc3 = makegbc(0, 1, 1, 1);
		gbc3.fill = GridBagConstraints.NONE;
		gbl3.setConstraints(geschwindigkeitLabel, gbc3);
		eigenschaften.add(geschwindigkeitLabel);

		geschwindigkeit = new JTextField();
		gbc3 = makegbc(1, 1, 1, 1);
		gbc3.weightx = 80;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbl3.setConstraints(geschwindigkeit, gbc3);
		eigenschaften.add(geschwindigkeit);

		JPanel simulationseinstellungenPane = new JPanel();
		JPanel simulationPane = new JPanel();

		// ***********************************************************

		JButton startSimu = new JButton("Start Simulation");
		startSimu.setActionCommand("startSimu");
		startSimu.addMouseListener(new SimuButtonListener());
		startSimu.setPreferredSize(new Dimension(150, 25));
		startSimu.setBounds(10, 10, 150, 25);

		JButton reportsSimu = new JButton("Reports anzeigen");
		reportsSimu.setActionCommand("reportsSimu");
		reportsSimu.addMouseListener(new SimuButtonListener());
		reportsSimu.setPreferredSize(new Dimension(150, 25));
		reportsSimu.setBounds(10, 10, 150, 25);

		startZeitLabel = new JLabel("Startzeit");
		endZeitLabel = new JLabel("Endzeit");

		cbStartzeit = new JComboBox();
		cbStartzeit.setEditable(false);
		cbStartzeit.setName("startzeit");
		cbStartzeit.setBounds(20, 30, 280, 20);
		cbStartzeit.setEnabled(true);

		cbEndzeit = new JComboBox();
		cbEndzeit.setEditable(false);
		cbEndzeit.setName("endzeit");
		cbEndzeit.setBounds(20, 30, 280, 20);
		cbEndzeit.setEnabled(true);

		for (int i = 6; i <= 23; i++) {

			cbStartzeit.addItem(new SimpleDate(i, 0));
			cbEndzeit.addItem(new SimpleDate(i, 0));
			cbStartzeit.addItem(new SimpleDate(i, 30));
			cbEndzeit.addItem(new SimpleDate(i, 30));
		}

		// setz Default Value der cbEndzeit auf 18:00 Uhr
		cbEndzeit.setSelectedIndex(24);

		cbSimuGeschwindigkeit = new JComboBox();
		cbSimuGeschwindigkeit.setEditable(false);
		cbSimuGeschwindigkeit.setName("Simulationsgeschwindigkeit");
		cbSimuGeschwindigkeit.setBounds(20, 30, 280, 20);
		cbSimuGeschwindigkeit.setEnabled(true);

		cbSimuGeschwindigkeit.addItem("langsam");
		cbSimuGeschwindigkeit.addItem("mittel");
		cbSimuGeschwindigkeit.addItem("schnell");

		GridBagLayout gbl4 = new GridBagLayout();
		GridBagLayout gbl5 = new GridBagLayout();

		JPanel simPaneStart = new JPanel();
		simPaneStart.setBorder(BorderFactory
				.createTitledBorder("Simulationsstart"));
		simPaneStart.setPreferredSize(new Dimension(330, 100));
		simulationPane.add(simPaneStart);

		// Simulationsablauf
		JPanel simPane = new JPanel();
		simPane
				.setBorder(BorderFactory
						.createTitledBorder("Simulationsablauf"));
		simPane.setPreferredSize(new Dimension(330, 320));
		GridBagLayout gbl6 = new GridBagLayout();
		simulationPane.add(simPane);

		JPanel simPaneAuswertungen = new JPanel();
		simPaneAuswertungen.setBorder(BorderFactory
				.createTitledBorder("Simulationsauswertungen"));
		simPaneAuswertungen.setPreferredSize(new Dimension(330, 100));
		simulationPane.add(simPaneAuswertungen);

		// Labels für Simulationsmitschnitt in Oberfläche
		this.lblLinie = new JLabel("Linie: ");
		this.linieID = new JLabel("ID");

		this.lblHS = new JLabel("Haltestelle: ");
		this.hsID = new JLabel("ID");

		this.lblQueueLengthImBus = new JLabel("Anzahl Personen im Bus: ");
		this.QueueLengthBus = new JLabel("Länge");

		this.lblQueueLengthHS = new JLabel("Anzahl wartende Personen HS: ");
		this.QueueLengthHS = new JLabel("Länge");

		// this.ankunft = new JLabel("Busankunftsereignis");

		this.lblanzPassagiereSteigenAus = new JLabel(
				"Anzahl aussteigender Passagiere: ");
		this.lblanzPassagiereSteigenEin = new JLabel(
				"Anzahl einsteigender Passagiere: ");

		this.anzPassagiereSteigenAus = new JLabel("0");
		this.anzPassagiereSteigenEin = new JLabel("0");

		this.anzPassagiereSteigenAus.setVisible(false);
		this.anzPassagiereSteigenEin.setVisible(false);

		LayoutUtil.addComponent(simPaneStart, gbl4, startSimu, 0, 0, 1, 1, 10,
				10, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPaneAuswertungen, gbl5, reportsSimu, 0, 0,
				1, 1, 10, 10, GridBagConstraints.NONE,
				GridBagConstraints.NORTHWEST);
		simPane.setLayout(gbl6);
		// simulationseinstellungenPane.setLayout(gbl5);

		JPanel simPaneUhrzeit = new JPanel();
		simPaneUhrzeit.setBorder(BorderFactory
				.createTitledBorder("Simulationsdauer"));
		simPaneUhrzeit.setPreferredSize(new Dimension(330, 100));
		GridBagLayout gbl7 = new GridBagLayout();
		simulationseinstellungenPane.add(simPaneUhrzeit);

		JPanel simPaneGeschwindigkeit = new JPanel();
		simPaneGeschwindigkeit.setBorder(BorderFactory
				.createTitledBorder("Simulationsgeschwindigkeit"));
		simPaneGeschwindigkeit.setPreferredSize(new Dimension(330, 100));
		simulationseinstellungenPane.add(simPaneGeschwindigkeit);

		LayoutUtil.addComponent(simPaneUhrzeit, gbl7, startZeitLabel, 0, 0, 1,
				1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPaneUhrzeit, gbl7, cbStartzeit, 1, 0, 1, 1,
				0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPaneUhrzeit, gbl7, endZeitLabel, 2, 0, 1, 1,
				0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPaneUhrzeit, gbl7, cbEndzeit, 3, 0, 1, 1, 0,
				0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPaneGeschwindigkeit, gbl7,
				cbSimuGeschwindigkeit, 0, 1, 1, 1, 0, 0,
				GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

		// Tab für Simulation
		LayoutUtil.addComponent(simPane, gbl6, lblLinie, 0, 0, 1, 1, 0, 0,
				GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPane, gbl6, linieID, 1, 0, 1, 1, 0, 0,
				GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPane, gbl6, lblHS, 0, 1, 1, 1, 0, 0,
				GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPane, gbl6, hsID, 1, 1, 1, 1, 0, 0,
				GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

		LayoutUtil.addComponent(simPane, gbl6, this.lblQueueLengthImBus, 0, 2,
				1, 1, 0, 0, GridBagConstraints.NONE,
				GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPane, gbl6, this.QueueLengthBus, 1, 2, 1, 1,
				0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

		LayoutUtil.addComponent(simPane, gbl6, this.lblQueueLengthHS, 0, 3, 1,
				1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPane, gbl6, this.QueueLengthHS, 1, 3, 1, 1,
				0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

		LayoutUtil.addComponent(simPane, gbl6, this.lblanzPassagiereSteigenAus,
				0, 4, 1, 1, 0, 0, GridBagConstraints.NONE,
				GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPane, gbl6, this.anzPassagiereSteigenAus, 1,
				4, 1, 1, 0, 0, GridBagConstraints.NONE,
				GridBagConstraints.NORTHWEST);

		LayoutUtil.addComponent(simPane, gbl6, this.lblanzPassagiereSteigenEin,
				0, 5, 1, 1, 0, 0, GridBagConstraints.NONE,
				GridBagConstraints.NORTHWEST);
		LayoutUtil.addComponent(simPane, gbl6, this.anzPassagiereSteigenEin, 1,
				5, 1, 1, 0, 0, GridBagConstraints.NONE,
				GridBagConstraints.NORTHWEST);
		// *************************************************************

		this.tp = new JTabbedPane();
		this.tp.addTab("Haltestellen", haltestellenPane);
		this.tp.addTab("Linien", linienPane);
		this.tp
				.addTab("Simulationseinstellungen",
						simulationseinstellungenPane);
		this.tp.addTab("Simulation", simulationPane);
		this.add(tp, BorderLayout.CENTER);
	}

	/**
	 * liefert ein GridBagConstraints Objekt
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	private GridBagConstraints makegbc(int x, int y, int width, int height) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.insets = new Insets(1, 1, 1, 1);
		return gbc;
	}

	/**
	 * liefert die in der JList selektierte Buslinie
	 * 
	 * @return die in der JList selektierte Buslinie
	 */
	public Linie getSelectedBuslinie() {
		return (Linie) linienList.getSelectedValue();
	}

	/**
	 * Berechnet das Intervall in Minuten zwischen Start und Endzeit aus
	 * Simulationseinstellung Ist Zeitdauer Null oder Negativ werden 360 Minuten
	 * zurückgegeben.
	 */
	public int getTimeDifference() {
		SimpleDate sdstart = (SimpleDate) cbStartzeit.getSelectedItem();
		SimpleDate sdende = (SimpleDate) cbEndzeit.getSelectedItem();
		int h = sdende.getStunde() - sdstart.getStunde();
		int m = (h * 60) - sdstart.getMinuten() + sdende.getMinuten();
		if (m <= 0) {
			return 360;
		} else {
			return m;
		}
	}

	/**
	 * Gibt formatierten String mit Tagesdatum und vom Benutzer ausgewählter
	 * Startzeit aus Simulationseinstellung für Startzeit der Simulation zurück.
	 */
	public String getSelectedStartpunkt() {
		SimpleDate sd = (SimpleDate) cbStartzeit.getSelectedItem();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date now = new Date();
		String s = df.format(now);
		return s + " " + sd.toString();
	}

	/**
	 * liefert die selektierte Simulationsgeschwindigkeit
	 * 
	 * @return die selektierte Simulationsgeschwindigkeit
	 */
	public int getSimulationsgeschwindigkeit() {
		String geschwindigkeit = (String) cbSimuGeschwindigkeit
				.getSelectedItem();

		if (geschwindigkeit.endsWith("langsam")) {
			return 600;
		} else if (geschwindigkeit.endsWith("mittel")) {
			return 150;
		} else if (geschwindigkeit.endsWith("schnell")) {
			return 10;
		}
		// default
		return 1000;
	}

	/**
	 * liefert die in der JList selektierte Teilstrecke
	 * 
	 * @return die in der JList selektierte Teilstrecke
	 */
	public Teilstrecke getSelectedTeilstrecke() {
		return (Teilstrecke) teilstreckeList.getSelectedValue();
	}

	/**
	 * aktualisiert die Buslinien-JList
	 */
	@SuppressWarnings("unchecked")
	public void updateBuslinienList() {
		linienList.removeAll();
		linienList.setListData(Strassennetz.getInstance().getArrayBuslinie()
				.toArray());
		if (linienList.getModel().getSize() > 0)
			linienList.setSelectedIndex(linienList.getLastVisibleIndex());
	}

	/**
	 * aktualisiert die Teilstrecken-JList
	 */
	@SuppressWarnings("unchecked")
	public void updateTeilstreckenList() {
		teilstreckeList.removeAll();
		if (this.getSelectedBuslinie() != null) {
			teilstreckeList.setListData(this.getSelectedBuslinie()
					.getTeilstrecken().toArray());
			if (teilstreckeList.getModel().getSize() > 0)
				teilstreckeList.setSelectedIndex(teilstreckeList
						.getLastVisibleIndex());
		}
	}

	@SuppressWarnings("unchecked")
	public void updateTeilstreckenListForLoading(Linie linie) {
		teilstreckeList.removeAll();
		teilstreckeList.setListData(linie.getTeilstrecken().toArray());
		if (teilstreckeList.getModel().getSize() > 0)
			teilstreckeList.setSelectedIndex(teilstreckeList
					.getLastVisibleIndex());
	}
	
	public void updateTeilstreckeForLoading(Linie linie) {
//		breite.setText(String
//				.valueOf(this.getSelectedTeilstrecke().getBreite()));
//		geschwindigkeit.setText(String.valueOf(this.getSelectedTeilstrecke()
//				.getGeschwindigkeit()));
	}


	/**
	 * aktualisiert die selektierte Teilstrecke
	 */
	public void updateTeilstrecke() {
//		breite.setText(String
//				.valueOf(this.getSelectedTeilstrecke().getBreite()));
//		geschwindigkeit.setText(String.valueOf(this.getSelectedTeilstrecke()
//				.getGeschwindigkeit()));
	}

	// nachfolgende Methoden updaten den Simulationsablauf in Oberfläche
	public void setSimu(String linieID, String hsID, int busPersonen,
			int hsPersonen) {
		this.linieID.setText(linieID);
		this.hsID.setText(hsID);
		this.QueueLengthBus.setText(String.valueOf(busPersonen));
		this.QueueLengthHS.setText(String.valueOf(hsPersonen));
	}

	public void setSimuPassagierSteigenAus(int i) {
		this.anzPassagiereSteigenAus.setVisible(true);
		this.anzPassagiereSteigenAus.setText(String.valueOf(i));
	}

	public void setSimuPassagiereSteigenEin(int j) {
		this.anzPassagiereSteigenEin.setVisible(true);
		this.anzPassagiereSteigenEin.setText(String.valueOf(j));
	}

	public void updateVisualisierung(int anzPersBus, int anzPersHS) {
		this.QueueLengthBus.setText(String.valueOf(anzPersBus));
		this.QueueLengthHS.setText(String.valueOf(anzPersHS));
		this.anzPassagiereSteigenAus.setVisible(false);
		this.anzPassagiereSteigenEin.setVisible(false);

	}
}