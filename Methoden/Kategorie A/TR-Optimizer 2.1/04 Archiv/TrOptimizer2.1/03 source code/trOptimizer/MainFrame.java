package trOptimizer;

/**
 * <p>Title: MainFrame</p>
 * <p>Description: Die Klasse MainFrame stellt die Haupt-GUI der Anwendung dar. Sie enthaelt alle Komponenten zum Zeichen, etc.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Timer;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.help.JHelpIndexNavigator;
import javax.help.JHelpNavigator;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Klassenattribute
	JPanel contentPane;

	// Menubar vorerst im System erstellen
	JMenuBar jMenuBar1 = new JMenuBar();

	// Hier werden die Menüs vorerst im System erstellt
	// "Datei"
	JMenu jMenuFile = new JMenu();
	// "Datei" -> "Oeffnen"
	JMenuItem oeffnenMenue = new JMenuItem();
	private JMenuItem speichernMenue = new JMenuItem();
	private JMenuItem beendenMenue = new JMenuItem();
	private JMenuItem speichernUnter = new JMenuItem();
	private JMenuItem neuMenue = new JMenuItem();
	private JMenuItem autosaveSetting = new JMenuItem();

	// "Help"
	JMenu jMenuHelp = new JMenu();
	// "Help" -> "Über"
	JMenuItem ueberMenue = new JMenuItem();

	// "Solverpfad"
	JMenu jMenuPfad = new JMenu();
	// "Solverpfad" -> "lp_solve.exe Pfad"
	JMenuItem pfadLPSOLVE = new JMenuItem();
	// "Solverpfad" -> "MOPS Pfad"
	JMenuItem pfadMOPS = new JMenuItem();
	// "Solverpfad" -> "WEIDENAUER EXEC ORDNER Pfad"
	JMenuItem pfadWEIDENAUER = new JMenuItem();
	// "Solverpfad" -> "Pfaede Anzeige"
	JMenuItem pfadAnzeige = new JMenuItem();
	// "Solverpfad" -> "TEMP ORDNER Pfad"
	JMenuItem pfadTEMP = new JMenuItem();

	XYLayout xYLayout1 = new XYLayout();
	// JPanel mainPane = new JPanel();
	XYLayout xYLayout2 = new XYLayout();
	ZeichenPane zeichenPane = new ZeichenPane();
	SymbolPane symbolPane = new SymbolPane();
	XYLayout xYLayout5 = new XYLayout();
	JPanel ergebnisPane = new JPanel();
	XYLayout xYLayout6 = new XYLayout();
	Border border1;

	// Eigene Variablen

	TransopController controller;
	XYLayout xYLayout3 = new XYLayout();

	TransporterPane transporterPane = new TransporterPane();
	XYLayout xYLayout4 = new XYLayout();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea ergebnisTA = new JTextArea();
	JButton berechneButton = new JButton();
	JTextField gesamtTF = new JTextField();

	JLabel ergebnis = new JLabel();
	JLabel gesamt = new JLabel();

	private JMenu jMenuSolver = new JMenu();

	private JCheckBoxMenuItem jCheckBoxMenuItem1 = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem jCheckBoxMenuItem2 = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem jCheckBoxMenuItem3 = new JCheckBoxMenuItem();
	private String dateiname;

	private int solvertyp = 1;

	Object LPPfadanpassenFenster;
	private Speichern sp;
	private Oeffnen of;
	File selFile;
	File oeffneFile;

	/**
	 * Konstruktor der Klasse
	 */
	public MainFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode dient zur Initialisierung der Komponenten.
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception {

		sp = new Speichern(this);
		of = new Oeffnen(this);
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(xYLayout1);

		this.setResizable(false);
		this.setSize(new Dimension(605, 680));

		this.setTitle("TR-Optimizer 2.1");

		jMenuFile.setBackground(Color.white);
		jMenuFile.setText("Datei");

		oeffnenMenue.setBackground(Color.white);
		oeffnenMenue.setText("Oeffnen");
		oeffnenMenue.addActionListener(this);

		autosaveSetting.setBackground(Color.white);
		autosaveSetting.setText("Autosave einstellen");
		autosaveSetting.addActionListener(this);

		jMenuPfad.setBackground(Color.white);
		jMenuPfad.setText("Pfade");

		pfadLPSOLVE.setBackground(Color.white);
		pfadLPSOLVE.setText("lp_solve.exe");
		pfadLPSOLVE.addActionListener(this);

		pfadWEIDENAUER.setBackground(Color.white);
		pfadWEIDENAUER.setText("Weidenauer EXEC Ordner");
		pfadWEIDENAUER.addActionListener(this);

		pfadMOPS.setBackground(Color.white);
		pfadMOPS.setText("mops.exe");
		pfadMOPS.addActionListener(this);
		
		pfadTEMP.setBackground(Color.white);
		pfadTEMP.setText("Temp Ordner");
		pfadTEMP.addActionListener(this);
		
		pfadAnzeige.setBackground(Color.white);
		pfadAnzeige.setText("Pfade anzeigen");
		pfadAnzeige.addActionListener(this);

		jMenuHelp.setBackground(Color.white);
		jMenuHelp.setText("Hilfe");

		ueberMenue.setBackground(Color.white);
		ueberMenue.setText("Ueber");
		ueberMenue.addActionListener(this);

		contentPane.setBackground(new Color(233, 233, 233));
		contentPane.setAlignmentY((float) 0.5);
		contentPane.setBorder(BorderFactory.createEtchedBorder());

		zeichenPane.setBackground(Color.red);
		zeichenPane.setDebugGraphicsOptions(0);
		zeichenPane.setLayout(xYLayout3);
		zeichenPane.addMouseListener(new ZeichenAdapter(this));

		symbolPane.setBackground(Color.blue);
		symbolPane.setForeground(Color.black);
		symbolPane.addMouseListener(new SymbolAdapter(this));
		symbolPane.setLayout(xYLayout5);

		ergebnisPane.setBackground(new Color(192, 192, 217));
		ergebnisPane.setLayout(xYLayout6);

		transporterPane.setBackground(new Color(160, 160, 166));
		transporterPane.setLayout(xYLayout4);
		transporterPane.addMouseListener(new TransporterAdapter(this));

		jMenuBar1.setBackground(Color.white);

		ergebnisTA.setFont(new java.awt.Font("SansSerif", 1, 10));
		ergebnisTA.setEditable(false);
		ergebnisTA.setText("");

		berechneButton.setBackground(Color.white);
		berechneButton.setText("los");
		berechneButton
				.addActionListener(new MainFrame_berechneButton_actionAdapter(
						this));
		berechneButton.addActionListener(this);

		gesamtTF.setFont(new java.awt.Font("SansSerif", 1, 10));
		gesamtTF.setPreferredSize(new Dimension(57, 21));
		gesamtTF.setText("");
		gesamtTF.setEditable(false);

		ergebnis.setText("Ergebnis");

		gesamt.setText("Gesamt");

		speichernMenue.setBackground(Color.white);
		speichernMenue.setText("Speichern");
		speichernMenue.addActionListener(this);
		speichernMenue.setEnabled(true);
		
		// Tastenkombination STRG+S ermoeglicht die Durchfuehrung der
		// Speichern-Funktion
		
		speichernMenue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));

		beendenMenue.setBackground(Color.white);
		beendenMenue.setText("Beenden");
		beendenMenue.addActionListener(this);

		speichernUnter.setBackground(Color.white);
		speichernUnter.setText("Speichern unter");
		speichernUnter.addActionListener(this);

		neuMenue.setBackground(Color.white);
		neuMenue.setText("Neu");
		neuMenue.addActionListener(this);

		jMenuSolver.setBackground(Color.white);
		jMenuSolver.setText("Solver");

		jCheckBoxMenuItem1.setBackground(Color.white);
		jCheckBoxMenuItem1.setSelected(true);
		jCheckBoxMenuItem1.setText("LP-Solve");
		jCheckBoxMenuItem1
				.addItemListener(new MainFrame_jCheckBoxMenuItem1_itemAdapter(
						this));

		jCheckBoxMenuItem2.setBackground(Color.white);
		jCheckBoxMenuItem2.setText("Weidenauer");
		jCheckBoxMenuItem2
				.addItemListener(new MainFrame_jCheckBoxMenuItem2_itemAdapter(
						this));

		jCheckBoxMenuItem3.setBackground(Color.white);
		jCheckBoxMenuItem3.setEnabled(true);
		jCheckBoxMenuItem3.setText("MOPS");
		jCheckBoxMenuItem3
				.addItemListener(new MainFrame_jCheckBoxMenuItem3_itemAdapter(
						this));

		// Menüs werden offiziel optisch hinzugefügt
		jMenuFile.add(neuMenue);
		jMenuFile.add(oeffnenMenue);
		jMenuFile.add(speichernMenue);
		jMenuFile.add(speichernUnter);
		jMenuFile.add(autosaveSetting);
		jMenuFile.add(beendenMenue);

		jMenuPfad.add(pfadLPSOLVE);
		jMenuPfad.add(pfadWEIDENAUER);
		jMenuPfad.add(pfadMOPS);
		jMenuPfad.add(pfadTEMP);
		jMenuPfad.add(pfadAnzeige);

		jMenuHelp.add(ueberMenue);

		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuSolver);
		jMenuBar1.add(jMenuPfad);
		jMenuBar1.add(jMenuHelp);

		contentPane.add(zeichenPane, new XYConstraints(5, 5, 515, 400));
		contentPane.add(symbolPane, new XYConstraints(520, 3, 78, 395)); // mainPane.add(ergebnisPane,
																			// new
																			// XYConstraints(0,
																			// 500,
																			// 600,
																			// 100));
		contentPane.add(transporterPane, new XYConstraints(5, 400, 515, 70));
		contentPane.add(ergebnisPane, new XYConstraints(5, 479, 580, 148));

		ergebnisPane.add(jScrollPane1, new XYConstraints(4, 17, 505, 126));
		ergebnisPane.add(gesamtTF, new XYConstraints(515, 18, 63, 20));
		ergebnisPane.add(ergebnis, new XYConstraints(5, 0, 97, 15));
		ergebnisPane.add(gesamt, new XYConstraints(515, 0, 62, -1));

		contentPane.add(berechneButton, new XYConstraints(522, 450, 65, 20));

		jScrollPane1.getViewport().add(ergebnisTA, null);

		jMenuSolver.add(jCheckBoxMenuItem1);
		jMenuSolver.add(jCheckBoxMenuItem2);
		jMenuSolver.add(jCheckBoxMenuItem3);

		this.setJMenuBar(jMenuBar1);

		// eigene Methodenaufrufe
		controller = new TransopController(this);
		controller.setZeichenPane(zeichenPane);

		controller.setTransporterPane(transporterPane);

	}

	/**
	 * Diese Methode dient dazu, um Events des Menues abzufangen und die
	 * dazugehoerigen Aktion ausfuehren.
	 *
	 * @param e
	 *            zugehoeriges actionEvent
	 */
	public void actionPerformed(ActionEvent e) {

		// was wurde im menue ausgewaehlt?
		if (e.getActionCommand().equalsIgnoreCase("Ueber")) {
			this.ueberMenue_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Speichern")) {
			this.speichernMenue_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Beenden")) {
			this.beendenMenue_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Oeffnen")) {
			this.oeffnenMenue_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Autosave einstellen")) {
			this.autosaveSetting_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("los")) {
			this.berechneButton_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Speichern unter")) {
			this.speichernUnterMenue_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("neu")) {
			this.neuMenue_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Ueber")) {
			this.ueberMenue_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("lp_solve.exe")) {
			this.pfadLPSOLVE_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase(
				"Weidenauer EXEC Ordner")) {
			this.pfadWEIDENAUER_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Temp Ordner")) {
			this.pfadTEMP_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("mops.exe")) {
			this.pfadMOPS_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Autosave ON")) {
			this.autosaveON_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Autosave OFF")) {
			this.autosaveOFF_actionPerformed(e);
		} else if (e.getActionCommand().equalsIgnoreCase("Pfade anzeigen")) {
			this.pfadAnzeige_actionPerformed(e);
		}
		jMenuFile.updateUI();
	}

	/**
	 * Diese Methode wird aufgerufen, wenn im Menue beenden ausgewaehlt wurde.
	 * Sie beendet die Anwendung.
	 *
	 * @param e
	 */
	public void beendenMenue_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * Diese Methoden werden aufgerufen, wenn in der Menue "Pfade anzeigen"
	 * geklickt wurde. Erscheint ein Fenster um die Dateipfae anzusehen
	 * 
	 * @param e
	 */
	public void pfadAnzeige_actionPerformed(ActionEvent e) {

		JFrame LPPfadanpassenFenster = new JFrame("Pfaede anzeigen");
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new java.awt.GridLayout(4, 0, 10, 10));
		pnlCenter.setPreferredSize(new Dimension(360, 90));

		JLabel LP = new JLabel("LP_Solve.exe -> " + IniPaths.getLpSolvePath());
		JLabel Weidenauer = new JLabel("Weidenauer -> "
				+ IniPaths.getWeidenauerPath());
		JLabel MOPS = new JLabel("MOPS -> " + IniPaths.getMOPSPath());
		JLabel Temp = new JLabel("Temp Ordner -> " + IniPaths.getTempPath());

		JPanel LPpfadanpassenPanel = new JPanel();
		LPpfadanpassenPanel.add(LP);
		JPanel LPpfadanpassenPanel2 = new JPanel();
		LPpfadanpassenPanel2.add(Weidenauer);
		JPanel LPpfadanpassenPanel3 = new JPanel();
		LPpfadanpassenPanel3.add(MOPS);
		JPanel LPpfadanpassenPanel4 = new JPanel();
		LPpfadanpassenPanel4.add(Temp);

		pnlCenter.add(LPpfadanpassenPanel);
		pnlCenter.add(LPpfadanpassenPanel2);
		pnlCenter.add(LPpfadanpassenPanel3);
		pnlCenter.add(LPpfadanpassenPanel4);

		JLabel lblNorth = new JLabel();
		lblNorth.setPreferredSize(new Dimension(400, 20));
		JLabel lblWest = new JLabel();
		lblWest.setPreferredSize(new Dimension(30, 130));
		JLabel lblEast = new JLabel();
		lblEast.setPreferredSize(new Dimension(30, 130));
		JLabel lblSouth = new JLabel();
		lblSouth.setPreferredSize(new Dimension(400, 20));

		LPPfadanpassenFenster.add(pnlCenter, BorderLayout.CENTER);
		LPPfadanpassenFenster.add(lblNorth, BorderLayout.NORTH);
		LPPfadanpassenFenster.add(lblSouth, BorderLayout.SOUTH);
		LPPfadanpassenFenster.add(lblWest, BorderLayout.WEST);
		LPPfadanpassenFenster.add(lblEast, BorderLayout.EAST);

		LPPfadanpassenFenster.setLocationRelativeTo(null);
		LPPfadanpassenFenster.setSize(600, 220);
		LPPfadanpassenFenster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		LPPfadanpassenFenster.setVisible(true);

	}
	
	/**
	 * Diese Methoden werden aufgerufen, wenn im Solverpfad "TEMP ORDNER Pfad"
	 * ausgewaehlt wurde. Durch die, sind die Pfade somit dynamisch. Das
	 * bedeutet die Solver Dateien duerfen verschoben werden, soweit man noch
	 * weiss wo die Dateien sich befinden.
	 *
	 * @param e
	 */
	public void pfadTEMP_actionPerformed(ActionEvent e) {

		JFrame explorerFenster = new JFrame();
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);

		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File newTEMPPath = fc.getSelectedFile();
			IniPaths.setTempPath(newTEMPPath.getAbsolutePath());
			JOptionPane.showMessageDialog(
					explorerFenster,
					"Neuer Pfad fuer TEMP ORDNER ist :\n"
							+ IniPaths.getTempPath(), "TEMP ORDNER Pfad",
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(explorerFenster,
					"nichts wurde ausgew�hlt", "TEMP ORDNER Pfad",
					JOptionPane.WARNING_MESSAGE);
		}
		
	}

	/**
	 * Diese Methoden werden aufgerufen, wenn im Solverpfad "lp_solve.exe Pfad"
	 * ausgewaehlt wurde. Durch die, sind die Pfade somit dynamisch. Das
	 * bedeutet die Solver Dateien duerfen verschoben werden, soweit man noch
	 * weiss wo die Dateien sich befinden.
	 *
	 * @param e
	 */
	public void pfadLPSOLVE_actionPerformed(ActionEvent e) {

		JFrame explorerFenster = new JFrame();
		JFileChooser fc = new JFileChooser();

		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File newLpSolvePath = fc.getSelectedFile();
			IniPaths.setLpSolvePath(newLpSolvePath.getAbsolutePath());
			JOptionPane.showMessageDialog(
					explorerFenster,
					"Neuer Pfad fuer LP_Solve.exe Datei ist :\n"
							+ IniPaths.getLpSolvePath(), "LP_Solver.exe Pfad",
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(explorerFenster,
					"nichts wurde ausgew�hlt", "lp_solve.exe Pfad",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	/**
	 * Diese Methoden werden aufgerufen, wenn im Solverpfad "WEIDENAUER EXEC ORDNER-Pfad Pfad"
	 * ausgewaehlt wurde. Durch die, sind die Pfade somit dynamisch. Das
	 * bedeutet die Solver Dateien duerfen verschoben werden, soweit man noch
	 * weiss wo die Dateien sich befinden.
	 *
	 * @param e
	 */
	public void pfadWEIDENAUER_actionPerformed(ActionEvent e) {

		JFrame explorerFenster = new JFrame();
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);

		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File newWeidenauerPath = fc.getSelectedFile();
			IniPaths.setWeidenauerPath(newWeidenauerPath.getAbsolutePath());
			JOptionPane.showMessageDialog(
					explorerFenster,
					"Neuer Pfad fuer Weidenauer EXEC ORDNER ist :\n"
							+ IniPaths.getWeidenauerPath(), "Weidenauer Pfad",
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(explorerFenster,
					"nichts wurde ausgew�hlt", "Weidenauer Pfad",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	/**
	 * Diese Methoden werden aufgerufen, wenn im Solverpfad "MOPS Pfad"
	 * ausgewaehlt wurde. Durch die, sind die Pfade somit dynamisch. Das
	 * bedeutet die Solver Dateien duerfen verschoben werden, soweit man noch
	 * weiss wo die Dateien sich befinden.
	 *
	 * @param e
	 */
	public void pfadMOPS_actionPerformed(ActionEvent e) {

		JFrame explorerFenster = new JFrame();
		JFileChooser fc = new JFileChooser();

		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File newMOPSPath = fc.getSelectedFile();
			IniPaths.setMOPSPath(newMOPSPath.getAbsolutePath());
			JOptionPane
					.showMessageDialog(
							explorerFenster,
							"Neuer Pfad fuer MOPS.exe ist :\n"
									+ IniPaths.getMOPSPath(), "MOPS Pfad",
							JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(explorerFenster,
					"nichts wurde ausgew�hlt", "MOPS Pfad",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	/**
	 * Diese Methode wird aufgerufen, wenn im Menue hilfe->ueber ausgewaehlt
	 * wurde. Sie veranlasst das Erzeugen und erscheint die Doku.
	 * 
	 * @param e
	 */
	public void ueberMenue_actionPerformed(ActionEvent e) {

		JHelp helpViewer = null;
		try {
			// Hauptfenster in der nächsten Zeile ersetzen durch aktuellen
			// Klassennamen
			ClassLoader cl = MainFrame.class.getClassLoader();
			URL url = HelpSet.findHelpSet(cl, "helpset.hs");
			helpViewer = new JHelp(new HelpSet(cl, url));

			Enumeration<?> eNavigators = helpViewer.getHelpNavigators();
			while (eNavigators.hasMoreElements()) {
				JHelpNavigator nav = (JHelpNavigator) eNavigators.nextElement();
				if (nav instanceof JHelpIndexNavigator) {
					helpViewer.removeHelpNavigator(nav);
				}
			}
		} catch (Exception ex) {
			System.err.println("API Help Set not found");
		}

		JFrame frame = new JFrame();
		frame.setTitle("Hilfe zu TR-Optimizer 2.1");
		frame.setSize(800, 600);
		frame.getContentPane().add(helpViewer);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn das Radio-Button "Autosave ON" im
	 * Menü "Autosave einstellen" ausgewaehlt wurde. Sie schaltet die Autosave
	 * Funktion ein.
	 * 
	 * @param e
	 */

	public void autosaveON_actionPerformed(ActionEvent e) {
		JFrame autosaveFenster = new JFrame();
		GlobaleVariable.autosaveSTATUS = true;
		JOptionPane
				.showMessageDialog(autosaveFenster,
						"Autosave ist an, die Arbeit wird nach jeder 30 Sekunden gespeichert!");

		// ab hier wird die Funktion Auto speichern begonnen
		AutoSave autosave = new AutoSave();
		Timer timer = new Timer();
		autosave.setTimer(timer);
		autosave.setSp(sp);
		autosave.run();
		timer.scheduleAtFixedRate(autosave, 0,
				GlobaleVariable.autosaveSECONDS * 1000);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn das Radio-Button "Autosave OFF" im
	 * Menü "Autosave einstellen" ausgewaehlt wurde. Sie schaltet die Autosave
	 * Funktion aus.
	 * 
	 * Ein Exception java.lang.NullPointerException wird geworfen da der Timer
	 * ausgeschaltet wurde,
	 * 
	 * @param e
	 * @throws CustomException
	 */

	public void autosaveOFF_actionPerformed(ActionEvent e) {
		JFrame autosaveFenster = new JFrame();
		GlobaleVariable.autosaveSTATUS = false;
		JOptionPane.showMessageDialog(autosaveFenster,
				"Autosave ist momentan aus.");
	}

	/**
	 * Diese Methode wird aufgerufen, wenn im Menue "Autosave einstellen"
	 * ausgewaehlt wurde. Hier hat man die Moeglichkeit, die Autosave Funktion
	 * ein- oder auszuschalten.
	 * 
	 * @param e
	 */

	public void autosaveSetting_actionPerformed(ActionEvent e) {
		JFrame autosaveFenster = new JFrame();
		autosaveFenster.setTitle("Autosave einstellen");
		JPanel autosavePanel = new JPanel();
		autosavePanel.setBackground(Color.WHITE);

		ButtonGroup autosaveGroup = new ButtonGroup();
		if (GlobaleVariable.autosaveSTATUS) {
			JRadioButton autosaveON = new JRadioButton("Autosave ON", true);
			JRadioButton autosaveOFF = new JRadioButton("Autosave OFF", false);
			autosaveON.setMnemonic(KeyEvent.VK_N);
			autosaveOFF.setMnemonic(KeyEvent.VK_F);

			autosaveON.addActionListener(this);
			autosaveOFF.addActionListener(this);

			autosaveGroup.add(autosaveOFF);
			autosaveGroup.add(autosaveON);

			autosavePanel.add(autosaveOFF);
			autosavePanel.add(autosaveON);
			autosaveFenster.add(autosavePanel);
			autosaveFenster.pack();
			autosaveFenster.setLocationRelativeTo(null);
			autosaveFenster.setVisible(true);
		} else {
			JRadioButton autosaveON = new JRadioButton("Autosave ON", false);
			JRadioButton autosaveOFF = new JRadioButton("Autosave OFF", true);
			autosaveON.setMnemonic(KeyEvent.VK_N);
			autosaveOFF.setMnemonic(KeyEvent.VK_F);

			autosaveON.addActionListener(this);
			autosaveOFF.addActionListener(this);

			autosaveGroup.add(autosaveOFF);
			autosaveGroup.add(autosaveON);

			autosavePanel.add(autosaveOFF);
			autosavePanel.add(autosaveON);
			autosaveFenster.add(autosavePanel);
			autosaveFenster.pack();
			autosaveFenster.setLocationRelativeTo(null);
			autosaveFenster.setVisible(true);
		}

	}

	/**
	 * Diese Methode wird aufgerufen, wenn im Menue "speichern" ausgewaehlt
	 * wurde. Sie speichert die aktuelle Arbeit sowie aktuelle Einstellungen in
	 * einer Datei.
	 * 
	 * @param e
	 */
	public void speichernMenue_actionPerformed(ActionEvent e) {

		if (GlobaleVariable.speichernach == 1) {

			/*
			 * Die Datei, die gespeichert wird, ist die Datei, die gerade unter
			 * "speichern unter" gespeichert wurde.
			 */

			JFrame speicherndialog = new JFrame();

			String tmp = GlobaleVariable.selFile.getAbsolutePath();
			GlobaleVariable.selFile = new File(tmp);
			sp.speichernObjekt(GlobaleVariable.selFile);

			JOptionPane.showMessageDialog(speicherndialog,
					"Erfolgreich gespeichert ! Timestamp: " + new Date());

		} else if (GlobaleVariable.speichernach == 2) {

			/*
			 * Die Datei, die gespeichert wird, ist die Datei, die gerade
			 * geöffnet wurde.
			 */

			JFrame speicherndialog = new JFrame();

			String tmp = GlobaleVariable.oeffneFile.getAbsolutePath();
			GlobaleVariable.oeffneFile = new File(tmp);
			sp.speichernObjekt(GlobaleVariable.oeffneFile);

			JOptionPane.showMessageDialog(speicherndialog,
					"Erfolgreich gespeichert ! Timestamp: " + new Date());

		} else {

			/*
			 * Die Datei, die gespeichert wird, ist noch nicht festgelegt. Hier
			 * wird ein Meldungsfenster angezeigt
			 */

			JFrame speicherndialog = new JFrame();
			JOptionPane
					.showMessageDialog(speicherndialog,
							"Speichern Sie erst die Datei mit der 'Speichern unter' Funktion");

		}

	}

	/**
	 * Diese Methode wird aufgerufen, wenn im Menue "speichern unter"
	 * ausgewaehlt wurde. Sie erzeugt dazu ein neues Speichern-Objekt und
	 * speichert die aktuelle Einstellungen in einer Datei.
	 * 
	 * @param e
	 */
	public void speichernUnterMenue_actionPerformed(ActionEvent e) {
		String filename = File.separator + "transop";
		JFileChooser fc = new JFileChooser(new File(filename));

		JFrame speicherndialog = new JFrame();

		// Show save dialog; this method does not return until the dialog is
		// closed
		fc.showSaveDialog(new JFrame());
		GlobaleVariable.selFile = fc.getSelectedFile();
		String tmp = GlobaleVariable.selFile.getAbsolutePath() + ".transop";
		GlobaleVariable.selFile = new File(tmp);
		sp.speichernObjekt(GlobaleVariable.selFile);

		// Speichernach = 1 da eine Datei gerade unter neue Datei gespeichert
		// wurde, und wird benoetigt fuer die Speicher-Funktion
		GlobaleVariable.speichernach = 1;
		JOptionPane.showMessageDialog(speicherndialog,
				"Erfolgreich gespeichert ! Timestamp: " + new Date());
		this.setTitle("TR-Optimizer 2.1 - " + GlobaleVariable.selFile.getName());
	}

	/**
	 * Diese Methode wird aufgerufen, wenn im Menue oeffnen ausgewaehlt wurde.
	 * Sie erzeugt ein neues Oeffnen-Objekt und öffnet die die vom Benutzer
	 * ausgewählte Datei. (*.transop)
	 *
	 * @param e
	 */
	public void oeffnenMenue_actionPerformed(ActionEvent e) {

		// String filename = File.separator + "transop";
		JFileChooser fc = new JFileChooser(new File("Daten"));
		// fc.addChoosableFileFilter(new MyFilter());

		// Show open dialog; this method does not return until the dialog is
		// closed
		fc.showOpenDialog(new JFrame());
		GlobaleVariable.oeffneFile = fc.getSelectedFile();

		of.oeffnenObjekte(GlobaleVariable.oeffneFile);

		// Speichernach = 2 da eine Datei gerade geöffnet wurde, und wird
		// benötigt für die Speicher-Funktion
		GlobaleVariable.speichernach = 2;
		this.setTitle("TR-Optimizer 2.1 - "
				+ GlobaleVariable.oeffneFile.getName());
	}

	/**
	 * Diese Methode wird aufgerufen, wenn auf der Arbeitsflaeche der
	 * berechnen-Button gedrueckt wurde. Sie veranlasst, die Berechnung der
	 * Ergebnisse.
	 *
	 * @param e
	 */
	public void berechneButton_actionPerformed(ActionEvent e) {

		// bereits angelegte virtuelle empfaenger loeschen
		for (int i = 0; i < controller.getEmpfaenger().size(); i++) {
			if (((Empfaenger) controller.getEmpfaenger().get(i)).getId() == 0) {
				controller.getEmpfaenger().remove(i);
			}
		}

		// bereits angelegte virtuelle produzenten loeschen
		for (int i = 0; i < controller.getProduzenten().size(); i++) {
			if (((Produzent) controller.getProduzenten().get(i)).getId() == 0) {
				controller.getProduzenten().remove(i);
			}
		}

		// bereits angelegte virtuelle strecken loeschen
		for (int i = 0; i < controller.getStrecken().size(); i++) {
			if (((Strecke) controller.getStrecken().get(i)).getId() == 0) {
				controller.getStrecken().remove(i);
			}
		}

		if (jCheckBoxMenuItem1.isSelected()) {
			GlobaleVariable.solvertyp = 1;
		}
		if (jCheckBoxMenuItem2.isSelected()) {
			GlobaleVariable.solvertyp = 2;
		}
		if (jCheckBoxMenuItem3.isSelected()) {
			GlobaleVariable.solvertyp = 3;
		}

		Solve solver = new Solve(solvertyp, controller.getEmpfaenger(),
				controller.getProduzenten(), controller.getStrecken(),
				controller.getTransporter());

		String ausgabe = controller.parseErg(solver.getErgebnis());
		String gesamt = controller.berechneGesamt(solver.getErgebnis());
		ergebnisTA.setText(ausgabe);
		gesamtTF.setText(gesamt);

	}

	/**
	 * Diese Methode wird aufgerufen, wenn im Menue neu gewaehlt wurde. Sie
	 * veranlasst das loeschen der aktuellen Objekte und das Neuzeichnen einer
	 * leeren Arbeitsflaeche.
	 * 
	 * @param e
	 */
	public void neuMenue_actionPerformed(ActionEvent e) {
		controller.leereAlleVectoren();
		controller.setTransportPos(520);

		GlobaleVariable.speichernach = 0;
	}

	/**
	 * Diese Methode wird aufgerufen, wenn schliessen oben rechts gedrueckt
	 * wurde. Sie veranlasst das Beenden der Anwendung.
	 * 
	 * @param e
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		this.repaint();
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			beendenMenue_actionPerformed(null);
		}
	}

	/**
	 * Diese Methode liefert den aktuell gueltigen TransopController zurueck.
	 *
	 * @return gueltiger controller der Anwendung.
	 */
	public TransopController getController() {
		return controller;
	}

	void jCheckBoxMenuItem1_itemStateChanged(ItemEvent e) {
		if (jCheckBoxMenuItem1.isSelected()) {
			jCheckBoxMenuItem2.setSelected(false);
			jCheckBoxMenuItem3.setSelected(false);
			jCheckBoxMenuItem1.setSelected(true);
		} else if (jCheckBoxMenuItem2.isSelected()
				|| jCheckBoxMenuItem3.isSelected()) {
			jCheckBoxMenuItem1.setSelected(false);
		} else {
			jCheckBoxMenuItem1.setSelected(true);
		}
	}

	void jCheckBoxMenuItem2_itemStateChanged(ItemEvent e) {
		if (jCheckBoxMenuItem2.isSelected()) {
			jCheckBoxMenuItem1.setSelected(false);
			jCheckBoxMenuItem3.setSelected(false);
			jCheckBoxMenuItem2.setSelected(true);
		} else if (jCheckBoxMenuItem1.isSelected()
				|| jCheckBoxMenuItem3.isSelected()) {
			jCheckBoxMenuItem2.setSelected(false);
		} else {
			jCheckBoxMenuItem2.setSelected(true);
		}
	}

	void jCheckBoxMenuItem3_itemStateChanged(ItemEvent e) {
		if (jCheckBoxMenuItem3.isSelected()) {
			jCheckBoxMenuItem1.setSelected(false);
			jCheckBoxMenuItem2.setSelected(false);
			jCheckBoxMenuItem3.setSelected(true);
		} else if (jCheckBoxMenuItem1.isSelected()
				|| jCheckBoxMenuItem2.isSelected()) {
			jCheckBoxMenuItem3.setSelected(false);
		} else {
			jCheckBoxMenuItem3.setSelected(true);
		}
	}
}

class MainFrame_berechneButton_actionAdapter implements
		java.awt.event.ActionListener {
	private MainFrame adaptee;

	MainFrame_berechneButton_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.berechneButton_actionPerformed(e);
	}
}

class MainFrame_jCheckBoxMenuItem1_itemAdapter implements
		java.awt.event.ItemListener {
	private MainFrame adaptee;

	MainFrame_jCheckBoxMenuItem1_itemAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void itemStateChanged(ItemEvent e) {
		adaptee.jCheckBoxMenuItem1_itemStateChanged(e);
	}
}

class MainFrame_jCheckBoxMenuItem2_itemAdapter implements
		java.awt.event.ItemListener {
	private MainFrame adaptee;

	MainFrame_jCheckBoxMenuItem2_itemAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void itemStateChanged(ItemEvent e) {
		adaptee.jCheckBoxMenuItem2_itemStateChanged(e);
	}
}

class MainFrame_jCheckBoxMenuItem3_itemAdapter implements
		java.awt.event.ItemListener {
	private MainFrame adaptee;

	MainFrame_jCheckBoxMenuItem3_itemAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void itemStateChanged(ItemEvent e) {
		adaptee.jCheckBoxMenuItem3_itemStateChanged(e);
	}
}
