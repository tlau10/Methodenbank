package hotelbelegung;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.JPanel;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JMonthChooser;

/**
 * Überschrift: Hotelbelegung Beschreibung: Copyright: Copyleft (c) 2014
 * Organisation: HTWG
 * 
 * @author Volker Wohlleber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003
 * @Version 2.0.1 Christian Gruhler SS08
 * @Version 4.0 Vitaliy Davats, Dominique Lebert, Manuel Falkenstein WS2013/14
 */


public class Frame1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private Date aktDatum = new Date();
	
	/**
	 * Variablen um Textfelder anzulegen
	 */
	
	private JPanel TextArea;
	private JPanel TextArea2;
	public JTextArea jTextArea;
	public JTextArea jTextArea2;
	
	/**
	 * Hier werden die Variablen für die Parameter "Hochsaison" deklariert, die wir
	 * im Einstellungsfesnter verändern können. Dabei werden auch die einzelnen 
	 * Textfelder Variablen deklariert. Für die jeweiligen Parameter haben
	 * wir Default Werte festgelegt.
	 */
	
	private JTextField ZimmeranzahlKat1JTextField_Hoch;
	private JTextField ZimmeranzahlKat2JTextField_Hoch;
	private JTextField ZimmeranzahlKat3JTextField_Hoch;
	public int zimmerAnzahlKat1_Hoch = 150;
	private int zimmerAnzahlKat2_Hoch = 300;
	private int zimmerAnzahlKat3_Hoch = 250;
	private JTextField ZimmerpreisKat1JTextField_Hoch;
	private JTextField ZimmerpreisKat2JTextField_Hoch;
	private JTextField ZimmerpreisKat3JTextField_Hoch;
	private int zimmerPreisKat1_Hoch = 200;
	private int zimmerPreisKat2_Hoch = 100;
	private int zimmerPreisKat3_Hoch = 80;
	private JTextField DeckungsbeitragKat1JTextField_Hoch;
	private JTextField DeckungsbeitragKat2JTextField_Hoch;
	private JTextField DeckungsbeitragKat3JTextField_Hoch;
	private int deckungsBeitragKat1_Hoch = 90;
	private int deckungsBeitragKat2_Hoch = 40;
	private int deckungsBeitragKat3_Hoch = 25;
	private JTextField ZimmerbelegungKat1JTextField_Hoch;
	private JTextField ZimmerbelegungKat2JTextField_Hoch;
	private JTextField ZimmerbelegungKat3JTextField_Hoch;
	private int zimmerBelegungKat1_Hoch = 60;
	private int zimmerBelegungKat2_Hoch = 85;
	private int zimmerBelegungKat3_Hoch = 70;
	private JTextField SpontanbuchungenKat1JTextField_Hoch;
	private JTextField SpontanbuchungenKat2JTextField_Hoch;
	private JTextField SpontanbuchungenKat3JTextField_Hoch;
	private int spontanBuchungenKat1_Hoch = 30;
	private int spontanBuchungenKat2_Hoch = 20;
	private int spontanBuchungenKat3_Hoch = 10;

	/**
	 * Hier werden die Variablen für die Parameter "Saison" deklariert, die wir
	 * im Einstellungsfesnter verändern können. Dabei werden auch die einzelnen 
	 * Textfelder Variablen deklariert. Für die jeweiligen Parameter haben
	 * wir Default Werte festgelegt.
	 */
	
	private JTextField ZimmeranzahlKat1JTextField_Neben;
	private JTextField ZimmeranzahlKat2JTextField_Neben;
	private JTextField ZimmeranzahlKat3JTextField_Neben;
	public int zimmerAnzahlKat1_Neben = 50;
	private int zimmerAnzahlKat2_Neben = 150;
	private int zimmerAnzahlKat3_Neben = 125;
	private JTextField ZimmerpreisKat1JTextField_Neben;
	private JTextField ZimmerpreisKat2JTextField_Neben;
	private JTextField ZimmerpreisKat3JTextField_Neben;
	private int zimmerPreisKat1_Neben = 100;
	private int zimmerPreisKat2_Neben = 50;
	private int zimmerPreisKat3_Neben = 40;
	private JTextField DeckungsbeitragKat1JTextField_Neben;
	private JTextField DeckungsbeitragKat2JTextField_Neben;
	private JTextField DeckungsbeitragKat3JTextField_Neben;
	private int deckungsBeitragKat1_Neben = 45;
	private int deckungsBeitragKat2_Neben = 20;
	private int deckungsBeitragKat3_Neben = 12;
	private JTextField ZimmerbelegungKat1JTextField_Neben;
	private JTextField ZimmerbelegungKat2JTextField_Neben;
	private JTextField ZimmerbelegungKat3JTextField_Neben;
	private int zimmerBelegungKat1_Neben = 30;
	private int zimmerBelegungKat2_Neben = 40;
	private int zimmerBelegungKat3_Neben = 35;
	private JTextField SpontanbuchungenKat1JTextField_Neben;
	private JTextField SpontanbuchungenKat2JTextField_Neben;
	private JTextField SpontanbuchungenKat3JTextField_Neben;
	private int spontanBuchungenKat1_Neben = 20;
	private int spontanBuchungenKat2_Neben = 15;
	private int spontanBuchungenKat3_Neben = 5;

	
	
	/**
	 * Werte dür die Default-Sasion
	 */
	
	public int zimmerAnzahlKat1 = 0;
	private int zimmerAnzahlKat2 = 0;
	private int zimmerAnzahlKat3 = 0;

	private int zimmerPreisKat1 = 0;
	private int zimmerPreisKat2 = 0;
	private int zimmerPreisKat3 = 0;

	private int deckungsBeitragKat1 = 0;
	private int deckungsBeitragKat2 = 0;
	private int deckungsBeitragKat3 = 0;

	private int zimmerBelegungKat1 = 0;
	private int zimmerBelegungKat2 = 0;
	private int zimmerBelegungKat3 = 0;

	private int spontanBuchungenKat1 = 0;
	private int spontanBuchungenKat2 = 0;
	private int spontanBuchungenKat3 = 0;
	

	
	
	private JTabbedPane RegisterJTabbedPane;
	private JPanel BelegungJPanel;

	private JPanel EinstellungenJPanel;
	private JPanel ReservDatumJPanel;
	private JCalendar calendar;
	private Date buchungsDatum;
	private int buchungsMonth;
	private JComboBox<String> DauerJComboBox;
	private int buchungsDauer;
	private JComboBox<String> KategorieJComboBox;
	private int zimmerKategorie;



	// Intervale für die Saisons deklarieren

	private JMonthChooser monthChooser_Saison_von;
	private JMonthChooser monthChooser_Saison_bis;
	private JMonthChooser monthChooser_Hoch_Saison_von;
	private JMonthChooser monthChooser_Hoch_Saison_bis;
	private int Saison_von = 4; // 01.Mai
	private int Saison_bis = 9; // 01.Oktober
	private int Hoch_Saison_von = 6; // 01.Juli
	private int Hoch_Saison_bis = 8; // 01.September

	private Solver mySolver = new LP_Solve();
	private Belegung myBelegung = new Belegung(this);
	private Manager myManager = new Manager(this, mySolver, myBelegung);

	// Hier wird der Pfad für das Arbeitsverzeichnis festgelegt.
	private JTextField ArbeitsverzeichnisJTextField;
	private String arbeitsVerzeichnis;
	private JTextField SolverpfadJTextField;
	private String solverPfad;

	/** Den Frame konstruieren */
	public Frame1() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Initialisierung der Komponenten */
	private void init() throws Exception {
		this.setSize(getToolkit().getScreenSize());
		this.setTitle("Hotel-Manager");
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		// Menü erstellen - Datei
		JMenuItem jMenuFileExit = new JMenuItem();
		jMenuFileExit.setText("Beenden");
		jMenuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuFileExit_actionPerformed(e);
			}
		});
		JMenu jMenuFile = new JMenu();
		jMenuFile.setText("Datei");
		jMenuFile.add(jMenuFileExit);

		// Hilfe
		JMenuItem jMenuHelpAbout = new JMenuItem();
		jMenuHelpAbout.setText("Info");
		jMenuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jMenuHelpAbout_actionPerformed(e);
			}
		});
		JMenu jMenuHelp = new JMenu();
		jMenuHelp.setText("Hilfe");
		jMenuHelp.add(jMenuHelpAbout);

		// *******************************************
		// Menüleiste
		// ******************************************
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.add(jMenuFile);
		jMenuBar.add(jMenuHelp);
		jMenuBar.add(jMenuHelp);
		this.setJMenuBar(jMenuBar);

		// *********************************
		// Reiter-PANELS
		TitledBorder ParameterTitledBorder = new TitledBorder(
				BorderFactory.createEtchedBorder(Color.white, new Color(148,
						145, 140)), "Parameter");

		// ********************
		// Reservierungswunsch
		// ********************
		JLabel AnreisedatumJLabel = new JLabel();
		AnreisedatumJLabel.setText("Reservierungswunsch:");
		AnreisedatumJLabel.setBounds(new Rectangle(10, 10, 120, 29));

		// JCalendar
		calendar = new JCalendar();
		calendar.setBounds(100, 50, 300, 300);
		calendar.setBorder(BorderFactory.createEtchedBorder());
		// calendar.setLocale("German (Germany)");

		// Aufenthaltsdauer
		JLabel AufenthaltsdauerJLabel = new JLabel();
		AufenthaltsdauerJLabel.setText("Aufenthaltsdauer:");
		AufenthaltsdauerJLabel.setBounds(new Rectangle(20, 380, 100, 30));

		DauerJComboBox = new JComboBox<String>();
		DauerJComboBox.setBounds(new Rectangle(120, 380, 120, 30));
		DauerJComboBox.addItem("Anzahl der Tage");
		// DauerJComboBox.addItem("---------------");
		for (int i = 1; i <= 14; i++) {
			DauerJComboBox.addItem(String.valueOf(i));
		}

		// Zimmerkategorie
		JLabel ZimmerkategorieJLabel = new JLabel();
		ZimmerkategorieJLabel.setText("Zimmerkategorie:");
		ZimmerkategorieJLabel.setBounds(new Rectangle(250, 380, 100, 30));

		KategorieJComboBox = new JComboBox<String>();
		KategorieJComboBox.setBounds(new Rectangle(350, 380, 120, 30));
		KategorieJComboBox.addItem("Zimmerkategorie");
		// KategorieJComboBox.addItem("---------------");
		KategorieJComboBox.addItem("1");
		KategorieJComboBox.addItem("2");
		KategorieJComboBox.addItem("3");

		// "Anfrage starten" Button
		JButton AnfrageStartenJButton = new JButton();
		AnfrageStartenJButton.setBackground(UIManager.getColor("info"));
		AnfrageStartenJButton.setFont(new java.awt.Font("Dialog", 1, 14));
		AnfrageStartenJButton.setText("Anfrage starten");
		AnfrageStartenJButton.setBounds(new Rectangle(310, 460, 170, 35));
		AnfrageStartenJButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AnfrageStartenJButton_actionPerformed(e);
					}
				});

		// CalendarJPanel
		JPanel CalendarJPanel = new JPanel();
		CalendarJPanel.setBorder(BorderFactory.createEtchedBorder());
		CalendarJPanel.setBounds(new Rectangle(10, 10, 500, 600));
		CalendarJPanel.setLayout(null);

		CalendarJPanel.add(AnreisedatumJLabel, null);
		CalendarJPanel.add(calendar);
		CalendarJPanel.add(AufenthaltsdauerJLabel, null);
		CalendarJPanel.add(DauerJComboBox, null);
		CalendarJPanel.add(ZimmerkategorieJLabel, null);
		CalendarJPanel.add(KategorieJComboBox, null);
		CalendarJPanel.add(AnfrageStartenJButton, null);

		// ReservDatumJPanel-Panel hinzufügen
		ReservDatumJPanel = new JPanel();
		ReservDatumJPanel.setLayout(null);
		ReservDatumJPanel.setBackground(new Color(212, 225, 223));

		ReservDatumJPanel.add(CalendarJPanel, null);

		/**
		 * Registrierungsfenster Belegung
		 */
		
		ServiceLevelPanel Kategorie1ServiceLevelPanel = new ServiceLevelPanel(
				myManager, myBelegung, zimmerAnzahlKat1, aktDatum, 10, 1);
		ServiceLevelPanel Kategorie2ServiceLevelPanel = new ServiceLevelPanel(
				myManager, myBelegung, zimmerAnzahlKat2, aktDatum, 10, 2);
		ServiceLevelPanel Kategorie3ServiceLevelPanel = new ServiceLevelPanel(
				myManager, myBelegung, zimmerAnzahlKat3, aktDatum, 10, 3);

		BelegungJPanel = new JPanel();
		BelegungJPanel.setLayout(new GridLayout(2, 2));
		BelegungJPanel.setBackground(new Color(212, 225, 223));
		BelegungJPanel.add(Kategorie1ServiceLevelPanel);
		BelegungJPanel.add(Kategorie2ServiceLevelPanel);
		BelegungJPanel.add(Kategorie3ServiceLevelPanel);
		
		
		/**
		 *  Registierungsfenster Einstellungen
		 */
		
		/**
		 * Hier werden die Paramter für das Einstellungsfenster erstellt, in dem wir den Pfad für 
		 * den Solver angeben und wo unserer Datein (Hotel.out und Hotel.lp) für den LP-Ansatz 
		 * gespeichert werden. Dabei erstellen wir zwei JLabel (SolverpfadJLabel und ArbeitsverzeichnisJLabel) die über
		 * den beiden Textfeldern (SolverpfadJTextField und ArbeitsverzeichnisJTextField) liegen.
		 */
	
		JLabel SolverpfadJLabel = new JLabel();
		SolverpfadJLabel.setText("Pfad zu lp_solve.exe: ");
		SolverpfadJLabel.setBounds(new Rectangle(34, 21, 107, 17));

		SolverpfadJTextField = new JTextField();
		solverPfad = ("C:\\Methodendatenbank\\Solver\\LP_Solve\\Exec\\"); //Default Solver Pfad
		
		SolverpfadJTextField.setText(solverPfad);
		SolverpfadJTextField.setBounds(new Rectangle(34, 49, 357, 27));

		JLabel ArbeitsverzeichnisJLabel = new JLabel();
		ArbeitsverzeichnisJLabel.setText("Arbeitsverzeichnis:");
		ArbeitsverzeichnisJLabel.setBounds(new Rectangle(34, 120, 117, 16));

		ArbeitsverzeichnisJTextField = new JTextField();
		arbeitsVerzeichnis = ("C:\\Temp\\");
		ArbeitsverzeichnisJTextField.setText(arbeitsVerzeichnis);
		ArbeitsverzeichnisJTextField.setBounds(new Rectangle(35, 144, 355, 27));

		JPanel ParameterPfadeJPanel = new JPanel();
		ParameterPfadeJPanel.setBorder(ParameterTitledBorder);
		ParameterPfadeJPanel.setBounds(new Rectangle(20, 11, 466, 189));
		ParameterPfadeJPanel.setLayout(null);
		ParameterPfadeJPanel.add(SolverpfadJLabel, null);
		ParameterPfadeJPanel.add(SolverpfadJTextField, null);
		ParameterPfadeJPanel.add(ArbeitsverzeichnisJLabel, null);
		ParameterPfadeJPanel.add(ArbeitsverzeichnisJTextField, null);

		JLabel lblLbesfsolverlpsolveexec = new JLabel();
		lblLbesfsolverlpsolveexec
				.setText("z.B. L:\\Besf\\Solver\\LP_Solve.20\\EXEC\\ ");
		lblLbesfsolverlpsolveexec.setBounds(new Rectangle(34, 21, 344, 17));
		lblLbesfsolverlpsolveexec.setBounds(172, 21, 248, 17);
		ParameterPfadeJPanel.add(lblLbesfsolverlpsolveexec);

	
		
		/** 
		 * 
		 * Im Folgenden werden die Felder für die Parameter "Hochsaison" im Einstellungsfenster
		 * angelegt. Dabei werden die Felder jeweils für die Katergorie 1, 2 und 3 erzeugt.
		 * Diese bestizten jeweils ein Feld für Zimmeranzahl, Zimmerpreis, Deckungsbeitrag,
		 * Zimmerbelegung und Spontanbuchungen 
		 * 
		 * */
		
		JLabel Zimmerkategorie1JLabel_Hoch = new JLabel();
		Zimmerkategorie1JLabel_Hoch.setText("Kategorie 1");
		Zimmerkategorie1JLabel_Hoch.setBounds(new Rectangle(150, 35, 100, 25));
		JLabel Zimmerkategorie2JLabel_Hoch = new JLabel();
		Zimmerkategorie2JLabel_Hoch.setText("Kategorie 2");
		Zimmerkategorie2JLabel_Hoch.setBounds(new Rectangle(230, 35, 100, 25));
		JLabel Zimmerkategorie3JLabel_Hoch = new JLabel();
		Zimmerkategorie3JLabel_Hoch.setText("Kategorie 3");
		Zimmerkategorie3JLabel_Hoch.setBounds(new Rectangle(310, 35, 100, 25));

		JLabel ZimmeranzahlJLabel_Hoch = new JLabel();
		ZimmeranzahlJLabel_Hoch.setText("Zimmeranzahl");
		ZimmeranzahlJLabel_Hoch.setBounds(new Rectangle(35, 70, 150, 17));

		ZimmeranzahlKat1JTextField_Hoch = new JTextField();
		ZimmeranzahlKat1JTextField_Hoch
				.setBounds(new Rectangle(150, 65, 50, 25));
		ZimmeranzahlKat1JTextField_Hoch.setText(String
				.valueOf(zimmerAnzahlKat1_Hoch));
		ZimmeranzahlKat2JTextField_Hoch = new JTextField();
		ZimmeranzahlKat2JTextField_Hoch
				.setBounds(new Rectangle(230, 65, 50, 25));
		ZimmeranzahlKat2JTextField_Hoch.setText(String
				.valueOf(zimmerAnzahlKat2_Hoch));
		ZimmeranzahlKat3JTextField_Hoch = new JTextField();
		ZimmeranzahlKat3JTextField_Hoch
				.setBounds(new Rectangle(310, 65, 50, 25));
		ZimmeranzahlKat3JTextField_Hoch.setText(String
				.valueOf(zimmerAnzahlKat3_Hoch));

		JLabel ZimmerpreisJLabel_Hoch = new JLabel();
		ZimmerpreisJLabel_Hoch.setText("Zimmerpreis");
		ZimmerpreisJLabel_Hoch.setBounds(new Rectangle(35, 105, 150, 17));

		ZimmerpreisKat1JTextField_Hoch = new JTextField();
		ZimmerpreisKat1JTextField_Hoch.setText(String
				.valueOf(zimmerPreisKat1_Hoch));
		ZimmerpreisKat1JTextField_Hoch
				.setBounds(new Rectangle(150, 100, 50, 25));
		ZimmerpreisKat2JTextField_Hoch = new JTextField();
		ZimmerpreisKat2JTextField_Hoch.setText(String
				.valueOf(zimmerPreisKat2_Hoch));
		ZimmerpreisKat2JTextField_Hoch
				.setBounds(new Rectangle(230, 100, 50, 25));
		ZimmerpreisKat3JTextField_Hoch = new JTextField();
		ZimmerpreisKat3JTextField_Hoch.setText(String
				.valueOf(zimmerPreisKat3_Hoch));
		ZimmerpreisKat3JTextField_Hoch
				.setBounds(new Rectangle(310, 100, 50, 25));

		JLabel DeckungsbeitragJLabel_Hoch = new JLabel();
		DeckungsbeitragJLabel_Hoch.setText("Deckungsbeitrag");
		DeckungsbeitragJLabel_Hoch.setBounds(new Rectangle(35, 140, 150, 17));

		DeckungsbeitragKat1JTextField_Hoch = new JTextField();
		DeckungsbeitragKat1JTextField_Hoch.setText(String
				.valueOf(deckungsBeitragKat1_Hoch));
		DeckungsbeitragKat1JTextField_Hoch.setBounds(new Rectangle(150, 135,
				50, 25));
		DeckungsbeitragKat2JTextField_Hoch = new JTextField();
		DeckungsbeitragKat2JTextField_Hoch.setText(String
				.valueOf(deckungsBeitragKat2_Hoch));
		DeckungsbeitragKat2JTextField_Hoch.setBounds(new Rectangle(230, 135,
				50, 25));
		DeckungsbeitragKat3JTextField_Hoch = new JTextField();
		DeckungsbeitragKat3JTextField_Hoch.setText(String
				.valueOf(deckungsBeitragKat3_Hoch));
		DeckungsbeitragKat3JTextField_Hoch.setBounds(new Rectangle(310, 135,
				50, 25));

		JLabel ZimmerbelegungJLabel_Hoch = new JLabel();
		ZimmerbelegungJLabel_Hoch.setText("Zimmerbelegung %");
		ZimmerbelegungJLabel_Hoch.setBounds(new Rectangle(35, 175, 150, 17));

		ZimmerbelegungKat1JTextField_Hoch = new JTextField();
		ZimmerbelegungKat1JTextField_Hoch.setText(String
				.valueOf(zimmerBelegungKat1_Hoch));
		ZimmerbelegungKat1JTextField_Hoch.setBounds(new Rectangle(150, 170, 50,
				25));
		ZimmerbelegungKat2JTextField_Hoch = new JTextField();
		ZimmerbelegungKat2JTextField_Hoch.setText(String
				.valueOf(zimmerBelegungKat2_Hoch));
		ZimmerbelegungKat2JTextField_Hoch.setBounds(new Rectangle(230, 170, 50,
				25));
		ZimmerbelegungKat3JTextField_Hoch = new JTextField();
		ZimmerbelegungKat3JTextField_Hoch.setText(String
				.valueOf(zimmerBelegungKat3_Hoch));
		ZimmerbelegungKat3JTextField_Hoch.setBounds(new Rectangle(310, 170, 50,
				25));

		JLabel SpontanbuchungenJLabel_Hoch = new JLabel();
		SpontanbuchungenJLabel_Hoch.setText("Spontanbuchungen %");
		SpontanbuchungenJLabel_Hoch.setBounds(new Rectangle(35, 210, 150, 17));

		SpontanbuchungenKat1JTextField_Hoch = new JTextField();
		SpontanbuchungenKat1JTextField_Hoch.setText(String
				.valueOf(spontanBuchungenKat1_Hoch));
		SpontanbuchungenKat1JTextField_Hoch.setBounds(new Rectangle(150, 205,
				50, 25));
		SpontanbuchungenKat2JTextField_Hoch = new JTextField();
		SpontanbuchungenKat2JTextField_Hoch.setText(String
				.valueOf(spontanBuchungenKat2_Hoch));
		SpontanbuchungenKat2JTextField_Hoch.setBounds(new Rectangle(230, 205,
				50, 25));
		SpontanbuchungenKat3JTextField_Hoch = new JTextField();
		SpontanbuchungenKat3JTextField_Hoch.setText(String
				.valueOf(spontanBuchungenKat3_Hoch));
		SpontanbuchungenKat3JTextField_Hoch.setBounds(new Rectangle(310, 205,
				50, 25));

		JPanel ParameterZimmerJPanel_Hoch = new JPanel();
		ParameterZimmerJPanel_Hoch.setLayout(null);
		ParameterZimmerJPanel_Hoch.setBounds(new Rectangle(0, 38, 461, 244));
		ParameterZimmerJPanel_Hoch.setBorder(ParameterTitledBorder);
		ParameterZimmerJPanel_Hoch.add(Zimmerkategorie1JLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(Zimmerkategorie2JLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(Zimmerkategorie3JLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmeranzahlJLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmeranzahlKat1JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmeranzahlKat2JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmeranzahlKat3JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerpreisJLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerpreisKat1JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerpreisKat2JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerpreisKat3JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(DeckungsbeitragJLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch
				.add(DeckungsbeitragKat1JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch
				.add(DeckungsbeitragKat2JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch
				.add(DeckungsbeitragKat3JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerbelegungJLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerbelegungKat1JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerbelegungKat2JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(ZimmerbelegungKat3JTextField_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(SpontanbuchungenJLabel_Hoch, null);
		ParameterZimmerJPanel_Hoch.add(SpontanbuchungenKat1JTextField_Hoch,
				null);
		ParameterZimmerJPanel_Hoch.add(SpontanbuchungenKat2JTextField_Hoch,
				null);
		ParameterZimmerJPanel_Hoch.add(SpontanbuchungenKat3JTextField_Hoch,
				null);
		
		
		
		/** 
		 * 
		 * Im Folgenden werden die Felder für die Parameter "Saison" im Einstellungsfenster
		 * angelegt. Dabei werden die Felder jeweils für die Katergorie 1, 2 und 3 erzeugt.
		 * Diese bestizten jeweils ein Feld für Zimmeranzahl, Zimmerpreis, Deckungsbeitrag,
		 * Zimmerbelegung und Spontanbuchungen 
		 * 
		 * */
		
		JLabel Zimmerkategorie1JLabel_Neben = new JLabel();
		Zimmerkategorie1JLabel_Neben.setText("Kategorie 1");
		Zimmerkategorie1JLabel_Neben.setBounds(new Rectangle(150, 35, 100, 25));
		JLabel Zimmerkategorie2JLabel_Neben = new JLabel();
		Zimmerkategorie2JLabel_Neben.setText("Kategorie 2");
		Zimmerkategorie2JLabel_Neben.setBounds(new Rectangle(230, 35, 100, 25));
		JLabel Zimmerkategorie3JLabel_Neben = new JLabel();
		Zimmerkategorie3JLabel_Neben.setText("Kategorie 3");
		Zimmerkategorie3JLabel_Neben.setBounds(new Rectangle(310, 35, 100, 25));

		JLabel ZimmeranzahlJLabel_Neben = new JLabel();
		ZimmeranzahlJLabel_Neben.setText("Zimmeranzahl");
		ZimmeranzahlJLabel_Neben.setBounds(new Rectangle(35, 70, 150, 17));

		ZimmeranzahlKat1JTextField_Neben = new JTextField();
		ZimmeranzahlKat1JTextField_Neben.setBounds(new Rectangle(150, 65, 50,
				25));
		ZimmeranzahlKat1JTextField_Neben.setText(String
				.valueOf(zimmerAnzahlKat1_Neben));
		ZimmeranzahlKat2JTextField_Neben = new JTextField();
		ZimmeranzahlKat2JTextField_Neben.setBounds(new Rectangle(230, 65, 50,
				25));
		ZimmeranzahlKat2JTextField_Neben.setText(String
				.valueOf(zimmerAnzahlKat2_Neben));
		ZimmeranzahlKat3JTextField_Neben = new JTextField();
		ZimmeranzahlKat3JTextField_Neben.setBounds(new Rectangle(310, 65, 50,
				25));
		ZimmeranzahlKat3JTextField_Neben.setText(String
				.valueOf(zimmerAnzahlKat3_Neben));

		JLabel ZimmerpreisJLabel_Neben = new JLabel();
		ZimmerpreisJLabel_Neben.setText("Zimmerpreis");
		ZimmerpreisJLabel_Neben.setBounds(new Rectangle(35, 105, 150, 17));

		ZimmerpreisKat1JTextField_Neben = new JTextField();
		ZimmerpreisKat1JTextField_Neben.setText(String
				.valueOf(zimmerPreisKat1_Neben));
		ZimmerpreisKat1JTextField_Neben.setBounds(new Rectangle(150, 100, 50,
				25));
		ZimmerpreisKat2JTextField_Neben = new JTextField();
		ZimmerpreisKat2JTextField_Neben.setText(String
				.valueOf(zimmerPreisKat2_Neben));
		ZimmerpreisKat2JTextField_Neben.setBounds(new Rectangle(230, 100, 50,
				25));
		ZimmerpreisKat3JTextField_Neben = new JTextField();
		ZimmerpreisKat3JTextField_Neben.setText(String
				.valueOf(zimmerPreisKat3_Neben));
		ZimmerpreisKat3JTextField_Neben.setBounds(new Rectangle(310, 100, 50,
				25));

		JLabel DeckungsbeitragJLabel_Neben = new JLabel();
		DeckungsbeitragJLabel_Neben.setText("Deckungsbeitrag");
		DeckungsbeitragJLabel_Neben.setBounds(new Rectangle(35, 140, 150, 17));

		DeckungsbeitragKat1JTextField_Neben = new JTextField();
		DeckungsbeitragKat1JTextField_Neben.setText(String
				.valueOf(deckungsBeitragKat1_Neben));
		DeckungsbeitragKat1JTextField_Neben.setBounds(new Rectangle(150, 135,
				50, 25));
		DeckungsbeitragKat2JTextField_Neben = new JTextField();
		DeckungsbeitragKat2JTextField_Neben.setText(String
				.valueOf(deckungsBeitragKat2_Neben));
		DeckungsbeitragKat2JTextField_Neben.setBounds(new Rectangle(230, 135,
				50, 25));
		DeckungsbeitragKat3JTextField_Neben = new JTextField();
		DeckungsbeitragKat3JTextField_Neben.setText(String
				.valueOf(deckungsBeitragKat3_Neben));
		DeckungsbeitragKat3JTextField_Neben.setBounds(new Rectangle(310, 135,
				50, 25));

		JLabel ZimmerbelegungJLabel_Neben = new JLabel();
		ZimmerbelegungJLabel_Neben.setText("Zimmerbelegung %");
		ZimmerbelegungJLabel_Neben.setBounds(new Rectangle(35, 175, 150, 17));

		ZimmerbelegungKat1JTextField_Neben = new JTextField();
		ZimmerbelegungKat1JTextField_Neben.setText(String
				.valueOf(zimmerBelegungKat1_Neben));
		ZimmerbelegungKat1JTextField_Neben.setBounds(new Rectangle(150, 170,
				50, 25));
		ZimmerbelegungKat2JTextField_Neben = new JTextField();
		ZimmerbelegungKat2JTextField_Neben.setText(String
				.valueOf(zimmerBelegungKat2_Neben));
		ZimmerbelegungKat2JTextField_Neben.setBounds(new Rectangle(230, 170,
				50, 25));
		ZimmerbelegungKat3JTextField_Neben = new JTextField();
		ZimmerbelegungKat3JTextField_Neben.setText(String
				.valueOf(zimmerBelegungKat3_Neben));
		ZimmerbelegungKat3JTextField_Neben.setBounds(new Rectangle(310, 170,
				50, 25));

		JLabel SpontanbuchungenJLabel_Neben = new JLabel();
		SpontanbuchungenJLabel_Neben.setText("Spontanbuchungen %");
		SpontanbuchungenJLabel_Neben.setBounds(new Rectangle(35, 210, 150, 17));

		SpontanbuchungenKat1JTextField_Neben = new JTextField();
		SpontanbuchungenKat1JTextField_Neben.setText(String
				.valueOf(spontanBuchungenKat1_Neben));
		SpontanbuchungenKat1JTextField_Neben.setBounds(new Rectangle(150, 205,
				50, 25));
		SpontanbuchungenKat2JTextField_Neben = new JTextField();
		SpontanbuchungenKat2JTextField_Neben.setText(String
				.valueOf(spontanBuchungenKat2_Neben));
		SpontanbuchungenKat2JTextField_Neben.setBounds(new Rectangle(230, 205,
				50, 25));
		SpontanbuchungenKat3JTextField_Neben = new JTextField();
		SpontanbuchungenKat3JTextField_Neben.setText(String
				.valueOf(spontanBuchungenKat3_Neben));
		SpontanbuchungenKat3JTextField_Neben.setBounds(new Rectangle(310, 205,
				50, 25));

		JPanel ParameterZimmerJPanel_Neben = new JPanel();
		ParameterZimmerJPanel_Neben.setLocation(0, 38);
		ParameterZimmerJPanel_Neben.setLayout(null);
		ParameterZimmerJPanel_Neben.setBorder(ParameterTitledBorder);
		ParameterZimmerJPanel_Neben.add(Zimmerkategorie1JLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(Zimmerkategorie2JLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(Zimmerkategorie3JLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmeranzahlJLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmeranzahlKat1JTextField_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmeranzahlKat2JTextField_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmeranzahlKat3JTextField_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmerpreisJLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmerpreisKat1JTextField_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmerpreisKat2JTextField_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmerpreisKat3JTextField_Neben, null);
		ParameterZimmerJPanel_Neben.add(DeckungsbeitragJLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(DeckungsbeitragKat1JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(DeckungsbeitragKat2JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(DeckungsbeitragKat3JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(ZimmerbelegungJLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(ZimmerbelegungKat1JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(ZimmerbelegungKat2JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(ZimmerbelegungKat3JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(SpontanbuchungenJLabel_Neben, null);
		ParameterZimmerJPanel_Neben.add(SpontanbuchungenKat1JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(SpontanbuchungenKat2JTextField_Neben,
				null);
		ParameterZimmerJPanel_Neben.add(SpontanbuchungenKat3JTextField_Neben,
				null);

		/**
		 * Hier wird der Button "Übernehmen" erstellt, welcher sich im Einstellungsfenster befindet,
		 * um die Werte der Parameter (falls geändert wird) zu übernehmen.
		 **/
		
		JButton EinstellungenUebernehmenJButton = new JButton();
		EinstellungenUebernehmenJButton.setBackground(SystemColor.info);
		EinstellungenUebernehmenJButton.setFont(new java.awt.Font("Dialog", 1,
				14));
		EinstellungenUebernehmenJButton.setText("Übernehmen");
		EinstellungenUebernehmenJButton.setBounds(new Rectangle(183, 540, 123,
				27));
		EinstellungenUebernehmenJButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jButtonEinst_actionPerformed(e);
					}
				});

		/** *
		 * 
		 * Hier wird das Panel für das Einstellungsfenster erstellt.
		 * 
		 **/
		
		EinstellungenJPanel = new JPanel();
		EinstellungenJPanel.setLayout(null);
		EinstellungenJPanel.setBackground(new Color(212, 225, 223));

		
		/** 
		 * 
		 * Parameter für Pfade und Zimmer und Button zu Parameter-Panel
		 * hinzufügen
		 * 
		 * */
		 
		JPanel ParameterJPanel = new JPanel();
		ParameterJPanel.setBorder(BorderFactory.createEtchedBorder());
		ParameterJPanel.setBounds(new Rectangle(13, 14, 515, 600));
		ParameterJPanel.setLayout(null);
		ParameterJPanel.setLayout(null);
		
		
		/**
		 * Parameter-Panel zu Einstellungen-Panel hinzufügen
		 */
		ParameterJPanel.add(ParameterPfadeJPanel);

		// S.Reiter
		JTabbedPane S_reiter = new JTabbedPane(JTabbedPane.TOP);
		S_reiter.setBounds(20, 219, 466, 310);
		S_reiter.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	
		ParameterJPanel.add(S_reiter);

		/**
		 * 
		 * Hier wird das Panel für die Hochsaison im Einstellungsfenster anlegen. 
		 * Dazu wird noch ein JLable hinzugefügt, in dem man festlegen kann, wann 
		 * die jeweilige Saison beginnt und endet. Die Monate für die Saison werden 
		 * über den JMonthChooser gesteuert.
		 * 
		 ***/ 


		JPanel Saison = new JPanel();
		Saison.setBackground(new Color(212, 225, 223));
		S_reiter.addTab("Saison", null, Saison, null);

		JPanel Saison_Datum = new JPanel();
		Saison_Datum.setBounds(0, 0, 461, 40);
		Saison_Datum.setBorder(BorderFactory.createEtchedBorder());
		ParameterZimmerJPanel_Neben.setSize(new Dimension(461, 244));
		Saison.setLayout(null);
		Saison.add(Saison_Datum);
		Saison_Datum.setLayout(null);

		JLabel S_festlegen = new JLabel();
		S_festlegen.setBounds(57, 10, 86, 14);
		S_festlegen.setText("Saison festlegen: ");
		Saison_Datum.add(S_festlegen);

		JLabel von1 = new JLabel();
		von1.setBounds(148, 10, 18, 14);
		von1.setText("von");
		Saison_Datum.add(von1);

		monthChooser_Saison_von = new JMonthChooser();
		monthChooser_Saison_von.setBounds(171, 7, 98, 20);
		monthChooser_Saison_von.setMonth(Saison_von);
		Saison_Datum.add(monthChooser_Saison_von);

		JLabel bis1 = new JLabel();
		bis1.setBounds(274, 10, 72, 14);
		bis1.setText("bis (nicht inkl.) ");
		Saison_Datum.add(bis1);

		monthChooser_Saison_bis = new JMonthChooser();
		monthChooser_Saison_bis.setBounds(351, 7, 98, 20);
		monthChooser_Saison_bis.setMonth(Saison_bis);
		Saison_Datum.add(monthChooser_Saison_bis);
		Saison.add(ParameterZimmerJPanel_Neben);

		/**
		 * 
		 * Hier wird das Panel für die Hochsaison im Einstellungsfenster anlegen.
		 * Dazu wird noch ein JLable hinzugefügt, in dem man festlegen kann, wann 
		 * die jeweilige Saison beginnt und endet. Die Monate für die Saison werden 
		 * über den JMonthChooser gesteuert.
		 * 
		 ***/ 

		JPanel H_Saison = new JPanel();
		H_Saison.setBackground(new Color(212, 225, 223));
		S_reiter.addTab("Hochsaison", null, H_Saison, null);

		JPanel H_Saison_Datum = new JPanel();
		H_Saison_Datum.setBounds(0, 0, 461, 40);
		H_Saison_Datum.setBorder(BorderFactory.createEtchedBorder());

		H_Saison.setLayout(null);
		H_Saison.add(H_Saison_Datum);
		H_Saison_Datum.setLayout(null);

		JLabel HS_festlegen = new JLabel();
		HS_festlegen.setBounds(57, 10, 86, 14);
		HS_festlegen.setText("Saison festlegen: ");
		H_Saison_Datum.add(HS_festlegen);

		JLabel von2 = new JLabel();
		von2.setBounds(148, 10, 18, 14);
		von2.setText("von");
		H_Saison_Datum.add(von2);

		monthChooser_Hoch_Saison_von = new JMonthChooser();
		monthChooser_Hoch_Saison_von.setBounds(171, 7, 98, 20);
		monthChooser_Hoch_Saison_von.setMonth(Hoch_Saison_von);
		H_Saison_Datum.add(monthChooser_Hoch_Saison_von);

		JLabel bis2 = new JLabel();
		bis2.setBounds(274, 10, 72, 14);
		bis2.setText("bis (nicht inkl.) ");
		H_Saison_Datum.add(bis2);

		monthChooser_Hoch_Saison_bis = new JMonthChooser();
		monthChooser_Hoch_Saison_bis.setBounds(351, 7, 98, 20);
		monthChooser_Hoch_Saison_bis.setMonth(Hoch_Saison_bis);
		H_Saison_Datum.add(monthChooser_Hoch_Saison_bis);
		H_Saison.add(ParameterZimmerJPanel_Hoch);

		/**
		* Hier wird der Button angelegt, um die Parameter in der Einstellung zu übernehmen
		***/
		
		ParameterJPanel.add(EinstellungenUebernehmenJButton);

		EinstellungenJPanel.add(ParameterJPanel, null);

		/*********************************
		* LP Solve Ausagabe anlegen
		**********************************/
		
		RegisterJTabbedPane = new JTabbedPane();
		RegisterJTabbedPane.setBackground(new Color(149, 195, 206));
		RegisterJTabbedPane.setFont(new java.awt.Font("SansSerif", 1, 14));
		RegisterJTabbedPane.addTab("Reservierung", null, ReservDatumJPanel,
				null);
		RegisterJTabbedPane.addTab("Belegung", null, BelegungJPanel, null);
		RegisterJTabbedPane.addTab("Einstellungen", null, EinstellungenJPanel,
				null);

		contentPane.add(RegisterJTabbedPane, BorderLayout.CENTER);

		/************************************************************************************************
		* TextArea anlegen zum lesen von LP-Ansatz:
		*************************************************************************************************
		* Mit TextArea wird ein neues Panel(Reiter) erstellt.
		*
		* Mit setLayout wird ein Layout definiert.
		*
		* Als weiterer Schritt wird mit jTextArea ein Textfeld erstellt. Danach
		* wird mit scrollPane
		*
		* ein Scroll-Funktion auf das TextArea gelegt, damit man bei großen
		* Texten aus nach
		*
		* unten, oben, recht und links scrollen kann. Das gleiche wie oben
		* beschrieben gilt für TextArea2
		*
		***********************************************************************************************/

		TextArea = new JPanel();
		TextArea2 = new JPanel();
		TextArea.setLayout(new BorderLayout(0, 0));
		TextArea2.setLayout(new BorderLayout(0, 0));
		jTextArea = new JTextArea(150, 150);
		jTextArea2 = new JTextArea(150, 150);
		JScrollPane scrollPane = new JScrollPane(jTextArea);
		JScrollPane scrollPane2 = new JScrollPane(jTextArea2);
		TextArea.add(scrollPane);
		TextArea2.add(scrollPane2);

	}

	/** Aktion Datei | Beenden durchgeführt */
	public void jMenuFileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * Überschrieben, so dass eine Beendigung beim Schließen des Fensters
	 * möglich ist.
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuFileExit_actionPerformed(null);
		}
	}

	private String monat(int i) {
		switch (i) {
		case -1:
			return "Dezember";
		case 0:
			return "Januar";
		case 1:
			return "Februar";
		case 2:
			return "März";
		case 3:
			return "April";
		case 4:
			return "Mai";
		case 5:
			return "Juni";
		case 6:
			return "Juli";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "Oktober";
		case 10:
			return "November";
		case 11:
			return "Dezember";

		default:
			return "Error";
		}
	}

	/** Anfrage starten */
	void AnfrageStartenJButton_actionPerformed(ActionEvent e) {
		// korrekte Eingaben in den DropDown-Listen überprüfen
		buchungsDatum = calendar.getDate();
		buchungsMonth = calendar.getMonthChooser().getMonth();

		if (DauerJComboBox.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null,
					"Achtung: Aufenthaltsdauer nicht ausgewählt.");
			return;
		}
		if (KategorieJComboBox.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null,
					"Achtung: Kategorie nicht ausgewählt.");
			return;
		}

		if (aktDatum.compareTo(buchungsDatum) > 0) {
			JOptionPane
					.showMessageDialog(
							null,
							"Achtung: Buchungsdatum nicht möglich, da das Datum in der Vergangabheit liegt.");
			return;
		}
		if (monthChooser_Saison_von.getMonth() < monthChooser_Saison_bis
				.getMonth()) {
			if (buchungsMonth < monthChooser_Saison_von.getMonth()
					|| buchungsMonth >= monthChooser_Saison_bis.getMonth()) {
				JOptionPane
						.showMessageDialog(
								null,
								"Achtung:"
										+ buchungsMonth
										+ " Buchungsdatum nicht möglich, da zu dieser Zeit das Hotel geschlossen ist. Saison von  "
										+ monat(monthChooser_Saison_von
												.getMonth())
										+ " bis Ende "
										+ monat(monthChooser_Saison_bis
												.getMonth() - 1) + "!");
				return;
			}
		}
		if (monthChooser_Saison_von.getMonth() > monthChooser_Saison_bis
				.getMonth()) {
			if (buchungsMonth < monthChooser_Saison_von.getMonth()
					&& buchungsMonth >= monthChooser_Saison_bis.getMonth()) {
				JOptionPane
						.showMessageDialog(
								null,
								"Achtung:"
										+ buchungsMonth
										+ " Buchungsdatum nicht möglich, da zu dieser Zeit das Hotel geschlossen ist. Saison von  "
										+ monat(monthChooser_Saison_von
												.getMonth())
										+ " bis Ende "
										+ monat(monthChooser_Saison_bis
												.getMonth() - 1) + "!");
				return;
			}
		}
		if (buchungsMonth >= monthChooser_Hoch_Saison_von.getMonth()
				&& buchungsMonth < monthChooser_Hoch_Saison_bis.getMonth()) {

			zimmerAnzahlKat1 = zimmerAnzahlKat1_Hoch;
			zimmerAnzahlKat2 = zimmerAnzahlKat2_Hoch;
			zimmerAnzahlKat3 = zimmerAnzahlKat3_Hoch;

			zimmerPreisKat1 = zimmerPreisKat1_Hoch;
			zimmerPreisKat2 = zimmerPreisKat2_Hoch;
			zimmerPreisKat3 = zimmerPreisKat3_Hoch;

			deckungsBeitragKat1 = deckungsBeitragKat1_Hoch;
			deckungsBeitragKat2 = deckungsBeitragKat2_Hoch;
			deckungsBeitragKat3 = deckungsBeitragKat3_Hoch;

			zimmerBelegungKat1 = zimmerBelegungKat1_Hoch;
			zimmerBelegungKat2 = zimmerBelegungKat2_Hoch;
			zimmerBelegungKat3 = zimmerBelegungKat3_Hoch;

			spontanBuchungenKat1 = spontanBuchungenKat1_Hoch;
			spontanBuchungenKat2 = spontanBuchungenKat2_Hoch;
			spontanBuchungenKat3 = spontanBuchungenKat3_Hoch;

			//JOptionPane.showMessageDialog(null,	"Buchungszeitraum in Hoch_Saison.");
		} else {
			zimmerAnzahlKat1 = zimmerAnzahlKat1_Neben;
			zimmerAnzahlKat2 = zimmerAnzahlKat2_Neben;
			zimmerAnzahlKat3 = zimmerAnzahlKat3_Neben;

			zimmerPreisKat1 = zimmerPreisKat1_Neben;
			zimmerPreisKat2 = zimmerPreisKat2_Neben;
			zimmerPreisKat3 = zimmerPreisKat3_Neben;

			deckungsBeitragKat1 = deckungsBeitragKat1_Neben;
			deckungsBeitragKat2 = deckungsBeitragKat2_Neben;
			deckungsBeitragKat3 = deckungsBeitragKat3_Neben;

			zimmerBelegungKat1 = zimmerBelegungKat1_Neben;
			zimmerBelegungKat2 = zimmerBelegungKat2_Neben;
			zimmerBelegungKat3 = zimmerBelegungKat3_Neben;

			spontanBuchungenKat1 = spontanBuchungenKat1_Neben;
			spontanBuchungenKat2 = spontanBuchungenKat2_Neben;
			spontanBuchungenKat3 = spontanBuchungenKat3_Neben;

			//JOptionPane.showMessageDialog(null, "Buchungszeitraum in Saison.");
		}

		buchungsDauer = DauerJComboBox.getSelectedIndex();
		zimmerKategorie = KategorieJComboBox.getSelectedIndex();

		// alle Werte für Optimierung setzen und Modell erstellen
		myManager.generateModel();

		/************************************************************************************************
		* Datei einlesen: LP-Ansatz:
		*************************************************************************************************
		* Als erstes definieren wird einen String, welchen unseren Pfad angibt, in der unsere Datei
		* 
		* liegt, die wir auslesen möchten. In unserem Fall möchten wir die hotel.lp Datei auslesen.
		*
		* Sollte in Zukunft eine andere Datei ausgelesen werden, so muss dieser Pfad dem String angepasst
		*
		* werden. Als nächsten Schritt erzeugen wir ein Objekt ladeDatei aus der Klasse DateiLaden.
		*
		* Nun wird ein String auslesen definiert, welches aus der Klasse die Funktion Laden verwendet
		*
		* und diese unseren Pfad beinhaltet. Für das zweite Fenster (TextArea2) definieren wir einen weiteren
		*
		* String, welcher den Pfad für die hotel.out Datei zeigt. Die TextArea wird mittels RegisterJTabbedPane.add() dem Frame hinzugefügt.
		*
		* Dann wird genau so weiter gemacht, wie für die hotel.lp Datei.
		*
		**********************************************************************************************/

		String hotelLP = "C:\\Temp\\hotel.lp";
		String holtelOUT = "C:\\Temp\\hotel.out";
		DateiLaden ladeDateiLP = new DateiLaden();
		DateiLaden ladeDateiOUT = new DateiLaden();
		String auslesenLP = ladeDateiLP.Laden(hotelLP);
		String auslesenOUT = ladeDateiOUT.Laden(holtelOUT);

		RegisterJTabbedPane.addTab("LP-Solve: LP Restriktionen", null,
				TextArea, null);
		RegisterJTabbedPane
				.addTab("LP-Solve: LP Lösung", null, TextArea2, null);

		/***********************************************************************************************
		* Datei in setText() einfügen:
		**********************************************************************************************
		* Hier wird dem Textfeld jTextArea der String auslesen übergeben, welcher den LP-Ansatz beinhaltet
		*
		* Dieser String (LP-Ansatz) wird nun in unserer TextArea ausgegeben. In TextArea2 setzen wir den
		*
		* String für auslesenOUT ein, der die Datei hotel.out ließt.
		*
		**********************************************************************************************/

		jTextArea.setText(auslesenLP);
		jTextArea2.setText(auslesenOUT);

	}

	/** Übernahme der geänderten Daten ins Programm */
	void jButtonEinst_actionPerformed(ActionEvent e) {

		try {
			boolean korrekteWerte = true;

			// /////
			//
			// / Werte prüfen
			//
			// /////
			// Saison
			int von = monthChooser_Saison_von.getMonth();
			int bis = monthChooser_Saison_bis.getMonth();
			int H_von = monthChooser_Hoch_Saison_von.getMonth();
			int H_bis = monthChooser_Hoch_Saison_bis.getMonth();

			if (von < bis) {
				if (!(von <= H_von && H_von < H_bis && H_bis <= bis)) {
					JOptionPane
							.showMessageDialog(null,
									"Fehlermeldung: Die Hochsaison muss innerhalb der Saison sein!");
					korrekteWerte = false;
				}
			}
			if (von > bis) {
				if (!((von <= H_von && H_von < H_bis)
						|| (von <= H_von && H_bis < H_von && bis >= H_bis) || (bis >= H_bis && H_bis > H_von))) {
					JOptionPane
							.showMessageDialog(null,
									"Fehlermeldung: Die Hochsaison muss innerhalb der Saison sein!");
					korrekteWerte = false;
				}
			}
			// Hoch
			if (Integer
					.parseInt(this.ZimmeranzahlKat1JTextField_Hoch.getText()) < 1
					|| Integer.parseInt(this.ZimmeranzahlKat2JTextField_Hoch
							.getText()) < 1
					|| Integer.parseInt(this.ZimmeranzahlKat3JTextField_Hoch
							.getText()) < 1) {
				JOptionPane.showMessageDialog(null,
						"Sie müssen mindestens 1 Zimmer pro Kategorie wählen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.ZimmerpreisKat1JTextField_Hoch.getText()) < 10
					|| Integer.parseInt(this.ZimmerpreisKat2JTextField_Hoch
							.getText()) < 10
					|| Integer.parseInt(this.ZimmerpreisKat3JTextField_Hoch
							.getText()) < 10) {
				JOptionPane.showMessageDialog(null,
						"Sie müssen mindestens 10 Euro pro Kategorie wählen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.DeckungsbeitragKat1JTextField_Hoch
					.getText()) < 0
					|| Integer.parseInt(this.DeckungsbeitragKat1JTextField_Hoch
							.getText()) > Integer
							.parseInt(this.ZimmerpreisKat1JTextField_Hoch
									.getText())) {
				JOptionPane
						.showMessageDialog(null,
								"Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.DeckungsbeitragKat2JTextField_Hoch
					.getText()) < 0
					|| Integer.parseInt(this.DeckungsbeitragKat2JTextField_Hoch
							.getText()) > Integer
							.parseInt(this.ZimmerpreisKat2JTextField_Hoch
									.getText())) {
				JOptionPane
						.showMessageDialog(null,
								"Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.DeckungsbeitragKat3JTextField_Hoch
					.getText()) < 0
					|| Integer.parseInt(this.DeckungsbeitragKat3JTextField_Hoch
							.getText()) > Integer
							.parseInt(this.ZimmerpreisKat3JTextField_Hoch
									.getText())) {
				JOptionPane
						.showMessageDialog(null,
								"Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.SpontanbuchungenKat1JTextField_Hoch
					.getText()) < 0
					|| Integer
							.parseInt(this.SpontanbuchungenKat1JTextField_Hoch
									.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Spontanbuchungen Kat1 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.SpontanbuchungenKat2JTextField_Hoch
					.getText()) < 0
					|| Integer
							.parseInt(this.SpontanbuchungenKat2JTextField_Hoch
									.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Spontanbuchungen Kat2 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.SpontanbuchungenKat3JTextField_Hoch
					.getText()) < 0
					|| Integer
							.parseInt(this.SpontanbuchungenKat3JTextField_Hoch
									.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Spontanbuchungen Kat3 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.ZimmerbelegungKat1JTextField_Hoch
					.getText()) < 0
					|| Integer.parseInt(this.ZimmerbelegungKat1JTextField_Hoch
							.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Zimmerbelegung Kat1 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.ZimmerbelegungKat2JTextField_Hoch
					.getText()) < 0
					|| Integer.parseInt(this.ZimmerbelegungKat2JTextField_Hoch
							.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Zimmerbelegung Kat2 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.ZimmerbelegungKat3JTextField_Hoch
					.getText()) < 0
					|| Integer.parseInt(this.ZimmerbelegungKat3JTextField_Hoch
							.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Zimmerbelegung Kat3 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}
			// Neben
			if (Integer.parseInt(this.ZimmeranzahlKat1JTextField_Neben
					.getText()) < 10
					|| Integer.parseInt(this.ZimmeranzahlKat2JTextField_Neben
							.getText()) < 10
					|| Integer.parseInt(this.ZimmeranzahlKat3JTextField_Neben
							.getText()) < 10) {
				JOptionPane
						.showMessageDialog(null,
								"Sie müssen mindestens 10 Zimmer pro Kategorie wählen.");
				korrekteWerte = false;
			}

			if (Integer
					.parseInt(this.ZimmerpreisKat1JTextField_Neben.getText()) < 10
					|| Integer.parseInt(this.ZimmerpreisKat2JTextField_Neben
							.getText()) < 10
					|| Integer.parseInt(this.ZimmerpreisKat3JTextField_Neben
							.getText()) < 10) {
				JOptionPane.showMessageDialog(null,
						"Sie müssen mindestens 10 Euro pro Kategorie wählen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.DeckungsbeitragKat1JTextField_Neben
					.getText()) < 0
					|| Integer
							.parseInt(this.DeckungsbeitragKat1JTextField_Neben
									.getText()) > Integer
							.parseInt(this.ZimmerpreisKat1JTextField_Neben
									.getText())) {
				JOptionPane
						.showMessageDialog(null,
								"Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.DeckungsbeitragKat2JTextField_Neben
					.getText()) < 0
					|| Integer
							.parseInt(this.DeckungsbeitragKat2JTextField_Neben
									.getText()) > Integer
							.parseInt(this.ZimmerpreisKat2JTextField_Neben
									.getText())) {
				JOptionPane
						.showMessageDialog(null,
								"Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.DeckungsbeitragKat3JTextField_Neben
					.getText()) < 0
					|| Integer
							.parseInt(this.DeckungsbeitragKat3JTextField_Neben
									.getText()) > Integer
							.parseInt(this.ZimmerpreisKat3JTextField_Neben
									.getText())) {
				JOptionPane
						.showMessageDialog(null,
								"Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.SpontanbuchungenKat1JTextField_Neben
					.getText()) < 0
					|| Integer
							.parseInt(this.SpontanbuchungenKat1JTextField_Neben
									.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Spontanbuchungen Kat1 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.SpontanbuchungenKat2JTextField_Neben
					.getText()) < 0
					|| Integer
							.parseInt(this.SpontanbuchungenKat2JTextField_Neben
									.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Spontanbuchungen Kat2 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.SpontanbuchungenKat3JTextField_Neben
					.getText()) < 0
					|| Integer
							.parseInt(this.SpontanbuchungenKat3JTextField_Neben
									.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Spontanbuchungen Kat3 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.ZimmerbelegungKat1JTextField_Neben
					.getText()) < 0
					|| Integer.parseInt(this.ZimmerbelegungKat1JTextField_Neben
							.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Zimmerbelegung Kat1 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.ZimmerbelegungKat2JTextField_Neben
					.getText()) < 0
					|| Integer.parseInt(this.ZimmerbelegungKat2JTextField_Neben
							.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Zimmerbelegung Kat2 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			if (Integer.parseInt(this.ZimmerbelegungKat3JTextField_Neben
					.getText()) < 0
					|| Integer.parseInt(this.ZimmerbelegungKat3JTextField_Neben
							.getText()) > 100) {
				JOptionPane
						.showMessageDialog(null,
								"Achtung: Zimmerbelegung Kat3 % muss zwischen 0 und 100 liegen.");
				korrekteWerte = false;
			}

			/**
			*
			* Hier werden die Korrekte Werte für die Parameter übernehmen
			*
			*/
			
			if (korrekteWerte) {
				// Hoch
				zimmerAnzahlKat1_Hoch = Integer
						.parseInt(this.ZimmeranzahlKat1JTextField_Hoch
								.getText());
				zimmerAnzahlKat2_Hoch = Integer
						.parseInt(this.ZimmeranzahlKat2JTextField_Hoch
								.getText());
				zimmerAnzahlKat3_Hoch = Integer
						.parseInt(this.ZimmeranzahlKat3JTextField_Hoch
								.getText());

				zimmerPreisKat1_Hoch = Integer
						.parseInt(this.ZimmerpreisKat1JTextField_Hoch.getText());
				zimmerPreisKat2_Hoch = Integer
						.parseInt(this.ZimmerpreisKat2JTextField_Hoch.getText());
				zimmerPreisKat3_Hoch = Integer
						.parseInt(this.ZimmerpreisKat3JTextField_Hoch.getText());

				deckungsBeitragKat1_Hoch = Integer
						.parseInt(this.DeckungsbeitragKat1JTextField_Hoch
								.getText());
				deckungsBeitragKat2_Hoch = Integer
						.parseInt(this.DeckungsbeitragKat2JTextField_Hoch
								.getText());
				deckungsBeitragKat3_Hoch = Integer
						.parseInt(this.DeckungsbeitragKat3JTextField_Hoch
								.getText());

				zimmerBelegungKat1_Hoch = Integer
						.parseInt(this.ZimmerbelegungKat1JTextField_Hoch
								.getText());
				zimmerBelegungKat2_Hoch = Integer
						.parseInt(this.ZimmerbelegungKat2JTextField_Hoch
								.getText());
				zimmerBelegungKat3_Hoch = Integer
						.parseInt(this.ZimmerbelegungKat3JTextField_Hoch
								.getText());

				spontanBuchungenKat1_Hoch = Integer
						.parseInt(this.SpontanbuchungenKat1JTextField_Hoch
								.getText());
				spontanBuchungenKat2_Hoch = Integer
						.parseInt(this.SpontanbuchungenKat2JTextField_Hoch
								.getText());
				spontanBuchungenKat3_Hoch = Integer
						.parseInt(this.SpontanbuchungenKat3JTextField_Hoch
								.getText());

				// Neben
				zimmerAnzahlKat1_Neben = Integer
						.parseInt(this.ZimmeranzahlKat1JTextField_Neben
								.getText());
				zimmerAnzahlKat2_Neben = Integer
						.parseInt(this.ZimmeranzahlKat2JTextField_Neben
								.getText());
				zimmerAnzahlKat3_Neben = Integer
						.parseInt(this.ZimmeranzahlKat3JTextField_Neben
								.getText());

				zimmerPreisKat1_Neben = Integer
						.parseInt(this.ZimmerpreisKat1JTextField_Neben
								.getText());
				zimmerPreisKat2_Neben = Integer
						.parseInt(this.ZimmerpreisKat2JTextField_Neben
								.getText());
				zimmerPreisKat3_Neben = Integer
						.parseInt(this.ZimmerpreisKat3JTextField_Neben
								.getText());

				deckungsBeitragKat1_Neben = Integer
						.parseInt(this.DeckungsbeitragKat1JTextField_Neben
								.getText());
				deckungsBeitragKat2_Neben = Integer
						.parseInt(this.DeckungsbeitragKat2JTextField_Neben
								.getText());
				deckungsBeitragKat3_Neben = Integer
						.parseInt(this.DeckungsbeitragKat3JTextField_Neben
								.getText());

				zimmerBelegungKat1_Neben = Integer
						.parseInt(this.ZimmerbelegungKat1JTextField_Neben
								.getText());
				zimmerBelegungKat2_Neben = Integer
						.parseInt(this.ZimmerbelegungKat2JTextField_Neben
								.getText());
				zimmerBelegungKat3_Neben = Integer
						.parseInt(this.ZimmerbelegungKat3JTextField_Neben
								.getText());

				spontanBuchungenKat1_Neben = Integer
						.parseInt(this.SpontanbuchungenKat1JTextField_Neben
								.getText());
				spontanBuchungenKat2_Neben = Integer
						.parseInt(this.SpontanbuchungenKat2JTextField_Neben
								.getText());
				spontanBuchungenKat3_Neben = Integer
						.parseInt(this.SpontanbuchungenKat3JTextField_Neben
								.getText());

				// Saison Interval

				Saison_von = monthChooser_Saison_von.getMonth();
				Saison_bis = monthChooser_Saison_bis.getMonth();
				Hoch_Saison_von = monthChooser_Hoch_Saison_von.getMonth();
				Hoch_Saison_bis = monthChooser_Hoch_Saison_bis.getMonth();

				arbeitsVerzeichnis = this.ArbeitsverzeichnisJTextField
						.getText();

				solverPfad = this.SolverpfadJTextField.getText();

				this.RegisterJTabbedPane.addNotify();
			}
		} catch (NumberFormatException n) {
			JOptionPane.showMessageDialog(null,
					"Achtung: Ein Wert ist nicht korrekt eingeben.");
			return;
		}
	}

	public void lpSolveScript() {
		String av = arbeitsVerzeichnis;
		DateiLaden laden = new DateiLaden();
		String komplScript = laden.Laden(av + "hotel.out");
		System.out.println("übergabe-------");
		System.out.println(komplScript);
		// txtrHallo.setText(komplScript);

		// LpSolveTextField.setText(komplScript);
	}

	/**
	 * 
	 * @return
	 */
	public String getArbeitsVerzeichnis() {
		return arbeitsVerzeichnis;
	}

	/**
	 * 
	 * @return
	 */
	public Date getBuchungsDatum() {
		return buchungsDatum;
	}

	/**
	 * 
	 * @return
	 */
	public int getBuchungsDauer() {
		return buchungsDauer;
	}

	/**
	 * 
	 * @return
	 */
	public int getDeckungsBeitragKat1() {
		return deckungsBeitragKat1;
	}

	/**
	 * 
	 * @return
	 */
	public int getDeckungsBeitragKat2() {
		return deckungsBeitragKat2;
	}

	/**
	 * 
	 * @return
	 */
	public int getDeckungsBeitragKat3() {
		return deckungsBeitragKat3;
	}

	/**
	 * 
	 * @return
	 */
	public String getSolverPfad() {
		return solverPfad;
	}

	/**
	 * 
	 * @return
	 */
	public int getSpontanBuchungenKat1() {
		return spontanBuchungenKat1;
	}

	/**
	 * 
	 * @return
	 */
	public int getSpontanBuchungenKat2() {
		return spontanBuchungenKat2;
	}

	/**
	 * 
	 * @return
	 */
	public int getSpontanBuchungenKat3() {
		return spontanBuchungenKat3;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerAnzahlKat1() {
		return zimmerAnzahlKat1;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerAnzahlKat2() {
		return zimmerAnzahlKat2;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerAnzahlKat3() {
		return zimmerAnzahlKat3;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerBelegungKat1() {
		return zimmerBelegungKat1;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerBelegungKat2() {
		return zimmerBelegungKat2;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerBelegungKat3() {
		return zimmerBelegungKat3;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerKategorie() {
		return zimmerKategorie;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerPreisKat1() {
		return zimmerPreisKat1;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerPreisKat2() {
		return zimmerPreisKat2;
	}

	/**
	 * 
	 * @return
	 */
	public int getZimmerPreisKat3() {
		return zimmerPreisKat3;
	}

	/**
	 * 
	 * @return
	 */
	public JPanel getBelegungJPanel() {
		return BelegungJPanel;
	}

	/**
	 * 
	 * @return
	 */
	public JTabbedPane getRegisterJTabbedPane() {
		return RegisterJTabbedPane;
	}

	/**
	 * 
	 * @return
	 */
	public JPanel getEinstellungenJPanel() {
		return EinstellungenJPanel;
	}

	/**
	 * 
	 * @return
	 */
	public Manager getMyManager() {
		return myManager;
	}
}
