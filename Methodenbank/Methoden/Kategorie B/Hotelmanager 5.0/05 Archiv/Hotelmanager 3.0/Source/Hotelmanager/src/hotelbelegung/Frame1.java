package hotelbelegung;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;

/**
 * Überschrift:   Hotelbelegung
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author Volker Wohlleber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003
 */
//	Version 2.0.1 Christian Gruhler SS08

public class Frame1 extends JFrame {
  	private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");
  	private DateFormat compareFormat = new SimpleDateFormat("yyyyMMdd");
  	private Date aktDatum = new Date();

	private JTabbedPane RegisterJTabbedPane;
	private JPanel ReservierungJPanel;
	private JPanel BelegungJPanel;
	private JPanel EinstellungenJPanel;
	private JComboBox TagJComboBox;
	private JComboBox MonatJComboBox;
	private JComboBox JahrJComboBox;
	private Date buchungsDatum;
	private JComboBox DauerJComboBox;
	private int buchungsDauer;
	private JComboBox KategorieJComboBox;
	private int zimmerKategorie;
	private JTextField ZimmeranzahlKat1JTextField;
	private JTextField ZimmeranzahlKat2JTextField;
	private JTextField ZimmeranzahlKat3JTextField;
	public int zimmerAnzahlKat1 = 150;
	private int zimmerAnzahlKat2 = 300;
	private int zimmerAnzahlKat3 = 250;
	private JTextField ZimmerpreisKat1JTextField;
	private JTextField ZimmerpreisKat2JTextField;
	private JTextField ZimmerpreisKat3JTextField;
	private int zimmerPreisKat1 = 200;
	private int zimmerPreisKat2 = 100;
	private int zimmerPreisKat3 = 80;
	private JTextField DeckungsbeitragKat1JTextField;
	private JTextField DeckungsbeitragKat2JTextField;
	private JTextField DeckungsbeitragKat3JTextField;
	private int deckungsBeitragKat1 = 90;
	private int deckungsBeitragKat2 = 40;
	private int deckungsBeitragKat3 = 25;
	private JTextField ZimmerbelegungKat1JTextField;
	private JTextField ZimmerbelegungKat2JTextField;
	private JTextField ZimmerbelegungKat3JTextField;
	private int zimmerBelegungKat1 = 60;
	private int zimmerBelegungKat2 = 85;
	private int zimmerBelegungKat3 = 70;
	private JTextField SpontanbuchungenKat1JTextField;
	private JTextField SpontanbuchungenKat2JTextField;
	private JTextField SpontanbuchungenKat3JTextField;
	private int spontanBuchungenKat1 = 30;
	private int spontanBuchungenKat2 = 20;
	private int spontanBuchungenKat3 = 10;
	private JTextField ArbeitsverzeichnisJTextField;
	private String arbeitsVerzeichnis = new String("C:\\Temp\\");
	private JTextField SolverpfadJTextField;
							//Pfad zur lp_solve.exe angepasst
	private String solverPfad = new String("L:\\Besf\\Solver\\LP_Solve.20\\EXEC\\");

	private Solver mySolver = new LP_Solve();
	private Belegung myBelegung = new Belegung(this);
	private Manager myManager = new Manager(this, mySolver, myBelegung);
	private int jahr;

  /**Den Frame konstruieren*/
  public Frame1() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      	init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Initialisierung der Komponenten*/
  private void init() throws Exception  {
	this.setSize(getToolkit().getScreenSize());
	this.setTitle("Hotel-Manager");
	JPanel contentPane = (JPanel)this.getContentPane();
	contentPane.setLayout(new BorderLayout());


	// Menü erstellen - Datei
   	JMenuItem jMenuFileExit = new JMenuItem();
	jMenuFileExit.setText("Beenden");
	jMenuFileExit.addActionListener(new ActionListener()
	{
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
	jMenuHelpAbout.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e) {
			jMenuHelpAbout_actionPerformed(e);
		}
	});
	JMenu jMenuHelp = new JMenu();
	jMenuHelp.setText("Hilfe");
	jMenuHelp.add(jMenuHelpAbout);

	// Menüleiste
	JMenuBar jMenuBar = new JMenuBar();
	jMenuBar.add(jMenuFile);
	jMenuBar.add(jMenuHelp);
	this.setJMenuBar(jMenuBar);


	//	********************************
	// Registerpunkt Reservierung
	//*********************************
	// Heutiges Datum
	JTextField DatumJTextField = new JTextField();
	DatumJTextField.setFont(new java.awt.Font("SansSerif", 1, 16));
	DatumJTextField.setText("  " + outFormat.format(aktDatum));
	DatumJTextField.setEditable(false);
	DatumJTextField.setBounds(new Rectangle(26, 37, 107, 29));
	JPanel DatumJPanel = new JPanel();
	DatumJPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Heutiges Datum"));
	DatumJPanel.setBounds(new Rectangle(28, 24, 454, 92));
	DatumJPanel.setLayout(null);
	DatumJPanel.add(DatumJTextField, null);

	TitledBorder ParameterTitledBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Parameter");

	// Reservierungswunsch
	JLabel AnreisedatumJLabel = new JLabel();
	AnreisedatumJLabel.setText("Anreisedatum:");
	AnreisedatumJLabel.setBounds(new Rectangle(28, 33, 115, 29));
	TagJComboBox = new JComboBox();
	TagJComboBox.setBounds(new Rectangle(137, 37, 61, 23));
	TagJComboBox.addItem("Tag");
	TagJComboBox.addItem("---");
	for (int i=1; i<=31; i++)
	{
		TagJComboBox.addItem(new Integer(i));
	}
	//TagJComboBox.setSelectedIndex(2);
	MonatJComboBox = new JComboBox();
	MonatJComboBox.setBounds(new Rectangle(214, 37, 112, 23));
	MonatJComboBox.addItem("Monat");
	MonatJComboBox.addItem("-------------------------");
	MonatJComboBox.addItem("Januar");
	MonatJComboBox.addItem("Februar");
	MonatJComboBox.addItem("März");
	MonatJComboBox.addItem("April");
	MonatJComboBox.addItem("Mai");
	MonatJComboBox.addItem("Juni");
	MonatJComboBox.addItem("Juli");
	MonatJComboBox.addItem("August");
	MonatJComboBox.addItem("September");
	MonatJComboBox.addItem("Oktober");
	MonatJComboBox.addItem("November");
	MonatJComboBox.addItem("Dezember");
	//MonatJComboBox.setSelectedIndex(2);
	JahrJComboBox = new JComboBox();
	JahrJComboBox.setBounds(new Rectangle(342, 37, 82, 23));
	GregorianCalendar heute = new GregorianCalendar();
	heute.setTime(aktDatum);
	jahr = heute.get(Calendar.YEAR);
	int tmpJahr = jahr;						//Dynamisch die ComboBox füllen
	JahrJComboBox.addItem("Jahr");
	JahrJComboBox.addItem("----");
	JahrJComboBox.addItem(Integer.toString(tmpJahr++));
	JahrJComboBox.addItem(Integer.toString(tmpJahr++));
	JahrJComboBox.addItem(Integer.toString(tmpJahr++));
	JahrJComboBox.addItem(Integer.toString(tmpJahr++));
	JahrJComboBox.addItem(Integer.toString(tmpJahr++));
	//JahrJComboBox.setSelectedIndex(3);
	DauerJComboBox = new JComboBox();
	DauerJComboBox.setBounds(new Rectangle(137, 91, 121, 24));
	DauerJComboBox.addItem("Anzahl der Tage");
	DauerJComboBox.addItem("---------------");
	for (int i=1; i<=7; i++)
	{
		DauerJComboBox.addItem(new Integer(i));
	}
	//DauerJComboBox.setSelectedIndex(3);

	JLabel AufenthaltsdauerJLabel = new JLabel();
	AufenthaltsdauerJLabel.setText("Aufenthaltsdauer:");
	AufenthaltsdauerJLabel.setBounds(new Rectangle(27, 89, 124, 26));

	JLabel ZimmerkategorieJLabel = new JLabel();
	ZimmerkategorieJLabel.setText("Zimmerkategorie:");
	ZimmerkategorieJLabel.setBounds(new Rectangle(27, 120, 124, 26));

	KategorieJComboBox = new JComboBox();
	KategorieJComboBox.setBounds(new Rectangle(137, 125, 121, 24));
	KategorieJComboBox.addItem("Zimmerkategorie");
	KategorieJComboBox.addItem("---------------");
	KategorieJComboBox.addItem("1");
	KategorieJComboBox.addItem("2");
	KategorieJComboBox.addItem("3");
	//KategorieJComboBox.setSelectedIndex(2);

	JPanel ReservierungswunschJPanel = new JPanel();
	TitledBorder ReservierungswunschTitledBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Reservierungswunsch");
	ReservierungswunschJPanel.setBorder(ReservierungswunschTitledBorder);
	ReservierungswunschJPanel.setBounds(new Rectangle(28, 150, 455, 170));
	ReservierungswunschJPanel.setLayout(null);
	ReservierungswunschJPanel.add(AnreisedatumJLabel, null);
	ReservierungswunschJPanel.add(TagJComboBox, null);
	ReservierungswunschJPanel.add(MonatJComboBox, null);
	ReservierungswunschJPanel.add(JahrJComboBox, null);
	ReservierungswunschJPanel.add(AufenthaltsdauerJLabel, null);
	ReservierungswunschJPanel.add(DauerJComboBox, null);
	ReservierungswunschJPanel.add(ZimmerkategorieJLabel, null);
	ReservierungswunschJPanel.add(KategorieJComboBox, null);

	// "Anfrage starten" Button
	JButton AnfrageStartenJButton = new JButton();
	AnfrageStartenJButton.setBackground(UIManager.getColor("info"));
	AnfrageStartenJButton.setFont(new java.awt.Font("Dialog", 1, 14));
	AnfrageStartenJButton.setText("Anfrage starten");
	AnfrageStartenJButton.setBounds(new Rectangle(310, 340, 170, 35));
	AnfrageStartenJButton.addActionListener(new java.awt.event.ActionListener() {
	  public void actionPerformed(ActionEvent e) {
		AnfrageStartenJButton_actionPerformed(e);
	  }
	});

   	// alle definierten JPanels zum AnfrageEinstellungen JPanel hinzufügen
   	JPanel jPanel1 = new JPanel();
   	jPanel1.setBorder(BorderFactory.createEtchedBorder());
	jPanel1.setPreferredSize(new Dimension(100, 100));
	jPanel1.setBounds(new Rectangle(13, 14, 517, 390));

	jPanel1.setLayout(null);
	jPanel1.add(DatumJPanel,null);
	jPanel1.add(ReservierungswunschJPanel,null);
	jPanel1.add(AnfrageStartenJButton,null);


    // AnfrageEinstellungen zum Reservierung JPanel hinzufügen
	ReservierungJPanel = new JPanel();
	ReservierungJPanel.setBackground(new Color(212, 225, 223));
	ReservierungJPanel.setLayout(null);
	ReservierungJPanel.add(jPanel1, null);


	//********************************
	// Registerpunkt Belegung
	//*********************************
	ServiceLevelPanel Kategorie1ServiceLevelPanel = new ServiceLevelPanel(myManager, myBelegung, zimmerAnzahlKat1, aktDatum, 10, 1);
	ServiceLevelPanel Kategorie2ServiceLevelPanel = new ServiceLevelPanel(myManager, myBelegung, zimmerAnzahlKat2, aktDatum, 10, 2);
	ServiceLevelPanel Kategorie3ServiceLevelPanel = new ServiceLevelPanel(myManager, myBelegung, zimmerAnzahlKat3, aktDatum, 10, 3);
	//JTablePanel aJTP = new JTablePanel(Kategorie1ServiceLevelPanel, Kategorie2ServiceLevelPanel, Kategorie3ServiceLevelPanel, myBelegung.getDBZimmerKat1V(), myBelegung.getDBZimmerKat2V(), myBelegung.getDBZimmerKat3V());

	BelegungJPanel = new JPanel();
	BelegungJPanel.setLayout(new GridLayout(2,2));
	BelegungJPanel.setBackground(new Color(212, 225, 223));
	BelegungJPanel.add(Kategorie1ServiceLevelPanel);
	BelegungJPanel.add(Kategorie2ServiceLevelPanel);
	BelegungJPanel.add(Kategorie3ServiceLevelPanel);
	//BelegungJPanel.add(aJTP);

    //********************************
    // Registerpunkt Einstellungen
    //*********************************
	// Pfad-Parameter
	JLabel SolverpfadJLabel = new JLabel();
	SolverpfadJLabel.setText("Pfad zu lp_solve.exe:");
	SolverpfadJLabel.setBounds(new Rectangle(35, 38, 120, 17));
	SolverpfadJTextField = new JTextField();
	DateiLaden laden = new DateiLaden();
	String iniPfad = laden.Laden("solverpfad.ini");
	if(!iniPfad.equals("")){
		solverPfad = iniPfad;
	}
	SolverpfadJTextField.setText(solverPfad);
	SolverpfadJTextField.setBounds(new Rectangle(34, 63, 357, 27));
	JLabel ArbeitsverzeichnisJLabel = new JLabel();
	ArbeitsverzeichnisJLabel.setText("Arbeitsverzeichnis:");
	ArbeitsverzeichnisJLabel.setBounds(new Rectangle(34, 120, 117, 16));
	ArbeitsverzeichnisJTextField = new JTextField();
	ArbeitsverzeichnisJTextField.setText(arbeitsVerzeichnis);
	ArbeitsverzeichnisJTextField.setBounds(new Rectangle(35, 144, 355, 27));
	JPanel ParameterPfadeJPanel = new JPanel();
	ParameterPfadeJPanel.setBorder(ParameterTitledBorder);
	ParameterPfadeJPanel.setBounds(new Rectangle(37, 24, 430, 201));
	ParameterPfadeJPanel.setLayout(null);
	ParameterPfadeJPanel.add(SolverpfadJLabel, null);
	ParameterPfadeJPanel.add(SolverpfadJTextField, null);
	ParameterPfadeJPanel.add(ArbeitsverzeichnisJLabel, null);
	ParameterPfadeJPanel.add(ArbeitsverzeichnisJTextField, null);

	// Zimmer-Parameter
	JLabel Zimmerkategorie1JLabel = new JLabel();
	Zimmerkategorie1JLabel.setText("Kategorie 1");
	Zimmerkategorie1JLabel.setBounds(new Rectangle(150, 35, 100, 25));
	JLabel Zimmerkategorie2JLabel = new JLabel();
	Zimmerkategorie2JLabel.setText("Kategorie 2");
	Zimmerkategorie2JLabel.setBounds(new Rectangle(230, 35, 100, 25));
	JLabel Zimmerkategorie3JLabel = new JLabel();
	Zimmerkategorie3JLabel.setText("Kategorie 3");
	Zimmerkategorie3JLabel.setBounds(new Rectangle(310, 35, 100, 25));

	JLabel ZimmeranzahlJLabel = new JLabel();
	ZimmeranzahlJLabel.setText("Zimmeranzahl");
	ZimmeranzahlJLabel.setBounds(new Rectangle(35, 70, 150, 17));
	ZimmeranzahlKat1JTextField = new JTextField();
	ZimmeranzahlKat1JTextField.setBounds(new Rectangle(150, 65, 50, 25));
	ZimmeranzahlKat1JTextField.setText(String.valueOf(zimmerAnzahlKat1));
	ZimmeranzahlKat2JTextField = new JTextField();
	ZimmeranzahlKat2JTextField.setBounds(new Rectangle(230, 65, 50, 25));
	ZimmeranzahlKat2JTextField.setText(String.valueOf(zimmerAnzahlKat2));
	ZimmeranzahlKat3JTextField = new JTextField();
	ZimmeranzahlKat3JTextField.setBounds(new Rectangle(310, 65, 50, 25));
	ZimmeranzahlKat3JTextField.setText(String.valueOf(zimmerAnzahlKat3));

	JLabel ZimmerpreisJLabel = new JLabel();
	ZimmerpreisJLabel.setText("Zimmerpreis");
	ZimmerpreisJLabel.setBounds(new Rectangle(35, 105, 150, 17));
	ZimmerpreisKat1JTextField = new JTextField();
	ZimmerpreisKat1JTextField.setText(String.valueOf(zimmerPreisKat1));
	ZimmerpreisKat1JTextField.setBounds(new Rectangle(150, 100, 50, 25));
	ZimmerpreisKat2JTextField = new JTextField();
	ZimmerpreisKat2JTextField.setText(String.valueOf(zimmerPreisKat2));
	ZimmerpreisKat2JTextField.setBounds(new Rectangle(230, 100, 50, 25));
	ZimmerpreisKat3JTextField = new JTextField();
	ZimmerpreisKat3JTextField.setText(String.valueOf(zimmerPreisKat3));
	ZimmerpreisKat3JTextField.setBounds(new Rectangle(310, 100, 50, 25));

	JLabel DeckungsbeitragJLabel = new JLabel();
	DeckungsbeitragJLabel.setText("Deckungsbeitrag");
	DeckungsbeitragJLabel.setBounds(new Rectangle(35, 140, 150, 17));
	DeckungsbeitragKat1JTextField = new JTextField();
	DeckungsbeitragKat1JTextField.setText(String.valueOf(deckungsBeitragKat1));
	DeckungsbeitragKat1JTextField.setBounds(new Rectangle(150, 135, 50, 25));
	DeckungsbeitragKat2JTextField = new JTextField();
	DeckungsbeitragKat2JTextField.setText(String.valueOf(deckungsBeitragKat2));
	DeckungsbeitragKat2JTextField.setBounds(new Rectangle(230, 135, 50, 25));
	DeckungsbeitragKat3JTextField = new JTextField();
	DeckungsbeitragKat3JTextField.setText(String.valueOf(deckungsBeitragKat3));
	DeckungsbeitragKat3JTextField.setBounds(new Rectangle(310, 135, 50, 25));

	JLabel ZimmerbelegungJLabel = new JLabel();
	ZimmerbelegungJLabel.setText("Zimmerbelegung %");
	ZimmerbelegungJLabel.setBounds(new Rectangle(35, 175, 150, 17));
	ZimmerbelegungKat1JTextField = new JTextField();
	ZimmerbelegungKat1JTextField.setText(String.valueOf(zimmerBelegungKat1));
	ZimmerbelegungKat1JTextField.setBounds(new Rectangle(150, 170, 50, 25));
	ZimmerbelegungKat2JTextField = new JTextField();
	ZimmerbelegungKat2JTextField.setText(String.valueOf(zimmerBelegungKat2));
	ZimmerbelegungKat2JTextField.setBounds(new Rectangle(230, 170, 50, 25));
	ZimmerbelegungKat3JTextField = new JTextField();
	ZimmerbelegungKat3JTextField.setText(String.valueOf(zimmerBelegungKat3));
	ZimmerbelegungKat3JTextField.setBounds(new Rectangle(310, 170, 50, 25));

	JLabel SpontanbuchungenJLabel = new JLabel();
	SpontanbuchungenJLabel.setText("Spontanbuchungen %");
	SpontanbuchungenJLabel.setBounds(new Rectangle(35, 210, 150, 17));
	SpontanbuchungenKat1JTextField = new JTextField();
	SpontanbuchungenKat1JTextField.setText(String.valueOf(spontanBuchungenKat1));
	SpontanbuchungenKat1JTextField.setBounds(new Rectangle(150, 205, 50, 25));
	SpontanbuchungenKat2JTextField = new JTextField();
	SpontanbuchungenKat2JTextField.setText(String.valueOf(spontanBuchungenKat2));
	SpontanbuchungenKat2JTextField.setBounds(new Rectangle(230, 205, 50, 25));
	SpontanbuchungenKat3JTextField = new JTextField();
	SpontanbuchungenKat3JTextField.setText(String.valueOf(spontanBuchungenKat3));
	SpontanbuchungenKat3JTextField.setBounds(new Rectangle(310, 205, 50, 25));

	JPanel ParameterZimmerJPanel = new JPanel();
	ParameterZimmerJPanel.setLayout(null);
	ParameterZimmerJPanel.setBounds(new Rectangle(38, 237, 430, 260));
	ParameterZimmerJPanel.setBorder(ParameterTitledBorder);
	ParameterZimmerJPanel.add(Zimmerkategorie1JLabel, null);
	ParameterZimmerJPanel.add(Zimmerkategorie2JLabel, null);
	ParameterZimmerJPanel.add(Zimmerkategorie3JLabel, null);
	ParameterZimmerJPanel.add(ZimmeranzahlJLabel, null);
	ParameterZimmerJPanel.add(ZimmeranzahlKat1JTextField, null);
	ParameterZimmerJPanel.add(ZimmeranzahlKat2JTextField, null);
	ParameterZimmerJPanel.add(ZimmeranzahlKat3JTextField, null);
	ParameterZimmerJPanel.add(ZimmerpreisJLabel, null);
	ParameterZimmerJPanel.add(ZimmerpreisKat1JTextField, null);
	ParameterZimmerJPanel.add(ZimmerpreisKat2JTextField, null);
	ParameterZimmerJPanel.add(ZimmerpreisKat3JTextField, null);
	ParameterZimmerJPanel.add(DeckungsbeitragJLabel, null);
	ParameterZimmerJPanel.add(DeckungsbeitragKat1JTextField, null);
	ParameterZimmerJPanel.add(DeckungsbeitragKat2JTextField, null);
	ParameterZimmerJPanel.add(DeckungsbeitragKat3JTextField, null);
	ParameterZimmerJPanel.add(ZimmerbelegungJLabel, null);
	ParameterZimmerJPanel.add(ZimmerbelegungKat1JTextField, null);
	ParameterZimmerJPanel.add(ZimmerbelegungKat2JTextField, null);
	ParameterZimmerJPanel.add(ZimmerbelegungKat3JTextField, null);
	ParameterZimmerJPanel.add(SpontanbuchungenJLabel, null);
	ParameterZimmerJPanel.add(SpontanbuchungenKat1JTextField, null);
	ParameterZimmerJPanel.add(SpontanbuchungenKat2JTextField, null);
	ParameterZimmerJPanel.add(SpontanbuchungenKat3JTextField, null);

	// Übernehmen-Button
	JButton EinstellungenUebernehmenJButton = new JButton();
	EinstellungenUebernehmenJButton.setBackground(SystemColor.info);
	EinstellungenUebernehmenJButton.setFont(new java.awt.Font("Dialog", 1, 14));
	EinstellungenUebernehmenJButton.setText("Übernehmen");
	EinstellungenUebernehmenJButton.setBounds(new Rectangle(338, 520, 130, 32));
	EinstellungenUebernehmenJButton.addActionListener(new java.awt.event.ActionListener()
	{
	  public void actionPerformed(ActionEvent e) {
		jButtonEinst_actionPerformed(e);
	  }
	});

	// Parameter für Pfade und Zimmer und Button zu Parameter-Panel hinzufügen
	JPanel ParameterJPanel = new JPanel();
	ParameterJPanel.setBorder(BorderFactory.createEtchedBorder());
	ParameterJPanel.setBounds(new Rectangle(13, 14, 515, 600));
	ParameterJPanel.setLayout(null);
	ParameterJPanel.add(ParameterPfadeJPanel, null);
	ParameterJPanel.add(ParameterZimmerJPanel, null);
	ParameterJPanel.add(EinstellungenUebernehmenJButton, null);

	// Parameter-Panel zu Einstellungen-Panel hinzufügen
	EinstellungenJPanel = new JPanel();
	EinstellungenJPanel.setLayout(null);
	EinstellungenJPanel.setBackground(new Color(212, 225, 223));
	EinstellungenJPanel.add(ParameterJPanel, null);


	//********************************
	// Register anlegen
	//*********************************
	RegisterJTabbedPane = new JTabbedPane();
	RegisterJTabbedPane.setBackground(new Color(149, 195, 206));
	RegisterJTabbedPane.setFont(new java.awt.Font("SansSerif", 1, 14));
	RegisterJTabbedPane.add(ReservierungJPanel, "Reservierung");
	RegisterJTabbedPane.add(BelegungJPanel, "Belegung");
	RegisterJTabbedPane.add(EinstellungenJPanel, "Einstellungen");
    contentPane.add(RegisterJTabbedPane, BorderLayout.CENTER);
  }

  /**Aktion Datei | Beenden durchgeführt*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  /**Aktion Hilfe | Info durchgeführt*/
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    Frame1_Infodialog dlg = new Frame1_Infodialog(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }

  /**Überschrieben, so dass eine Beendigung beim Schließen des Fensters möglich ist.*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }

  /**Anfrage starten*/
  void AnfrageStartenJButton_actionPerformed(ActionEvent e) {
  // korrekte Eingaben in den DropDown-Listen überprüfen

  //dynamsiche Anpassung der Schaltjarjkorrektur
  int ausgewaehltesJahr = (JahrJComboBox.getSelectedIndex()+(jahr-2));
  boolean schaltjahr = false;

  if(ausgewaehltesJahr%4 == 0 && ausgewaehltesJahr%400 != 0){
    schaltjahr = true;
  }

  if (schaltjahr && MonatJComboBox.getSelectedIndex() == 3 && TagJComboBox.getSelectedIndex() > 30)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ungültiges Datum eingegeben. Das ist ein Schaltjahr.");
          return;
        }

        //Bug beheben bei nicht Schaltjahr und Tag 27 und 28
  if (TagJComboBox.getSelectedIndex() > 29 && MonatJComboBox.getSelectedIndex() == 3 && !schaltjahr)
  {
          JOptionPane.showMessageDialog(null, "Achtung: Ungültiges Datum eingegeben. Fehlercode: 01");
          return;
        }
  if (TagJComboBox.getSelectedIndex() == 32 && (MonatJComboBox.getSelectedIndex() == 5 ||
      MonatJComboBox.getSelectedIndex() == 7 || MonatJComboBox.getSelectedIndex() == 10 ||
      MonatJComboBox.getSelectedIndex() == 12))
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ungültiges Datum eingegeben. Fehlercode: 02");
          return;
        }
  if (TagJComboBox.getSelectedIndex() == 0 || MonatJComboBox.getSelectedIndex() == 0 ||
      JahrJComboBox.getSelectedIndex() == 0)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ihr ausgewähltes Datum ist unvollständig.");
          return;
        }
  if (TagJComboBox.getSelectedIndex() == 1 || MonatJComboBox.getSelectedIndex() == 1 ||
      JahrJComboBox.getSelectedIndex() == 1)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Ihr ausgewähltes Datum ist unvollständig.");
          return;
        }
  if (DauerJComboBox.getSelectedIndex() == 0 || DauerJComboBox.getSelectedIndex() == 1)
        {
          JOptionPane.showMessageDialog(null, "Achtung: Aufenthaltsdauer nicht ausgewählt.");
          return;
        }
	if (KategorieJComboBox.getSelectedIndex() < 2)
		  {
			JOptionPane.showMessageDialog(null, "Achtung: Kategorie nicht ausgewählt.");
			return;
		  }

	// Buchungsdatum, Buchungsdauer und Zimmerkategorie auslesen
    try {										//Dynamische Anpssung der Kontrollfunktion
    	int parseDatum = ((JahrJComboBox.getSelectedIndex()+(jahr-2))*10000)+((MonatJComboBox.getSelectedIndex()-1)*100)+(TagJComboBox.getSelectedIndex()-1);
    	buchungsDatum = compareFormat.parse(String.valueOf(parseDatum));
    	if (Integer.parseInt(compareFormat.format(buchungsDatum)) <= Integer.parseInt(compareFormat.format(aktDatum)))
        {
          JOptionPane.showMessageDialog(null, "Achtung: Buchungsdatum nicht möglich, bitte prüfen.");
          return;
        }
    	buchungsDauer = DauerJComboBox.getSelectedIndex()-1;
    	zimmerKategorie = KategorieJComboBox.getSelectedIndex()-1;

    	// alle Werte für Optimierung setzen und Modell erstellen
    	myManager.generateModel();
    }
    catch (ParseException f) {
      /* Ausnahme beim Parsen behandeln */
    }
  }

  /**Übernahme der geänderten Daten ins Programm*/
  void jButtonEinst_actionPerformed(ActionEvent e) {

    try {
	  boolean korrekteWerte = true;

	///////
	//
	/// Werte prüfen
	//
	///////
	  if(Integer.parseInt(this.ZimmeranzahlKat1JTextField.getText()) < 10 ||
	     Integer.parseInt(this.ZimmeranzahlKat2JTextField.getText()) < 10 ||
		 Integer.parseInt(this.ZimmeranzahlKat3JTextField.getText()) < 10) {
		JOptionPane.showMessageDialog(null, "Sie müssen mindestens 10 Zimmer pro Kategorie wählen.");
		korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.ZimmerpreisKat1JTextField.getText()) < 10 ||
	     Integer.parseInt(this.ZimmerpreisKat2JTextField.getText()) < 10 ||
	     Integer.parseInt(this.ZimmerpreisKat3JTextField.getText()) < 10) {
		JOptionPane.showMessageDialog(null, "Sie müssen mindestens 10 Euro pro Kategorie wählen.");
		korrekteWerte = false;
	  }



	  if(Integer.parseInt(this.DeckungsbeitragKat1JTextField.getText()) < 0 ||
	     Integer.parseInt(this.DeckungsbeitragKat1JTextField.getText()) > Integer.parseInt(this.ZimmerpreisKat1JTextField.getText())) {
			JOptionPane.showMessageDialog(null, "Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
			korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.DeckungsbeitragKat2JTextField.getText()) < 0 ||
		 Integer.parseInt(this.DeckungsbeitragKat2JTextField.getText()) > Integer.parseInt(this.ZimmerpreisKat2JTextField.getText())) {
			JOptionPane.showMessageDialog(null, "Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
			korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.DeckungsbeitragKat3JTextField.getText()) < 0 ||
		 Integer.parseInt(this.DeckungsbeitragKat3JTextField.getText()) > Integer.parseInt(this.ZimmerpreisKat3JTextField.getText())) {
			JOptionPane.showMessageDialog(null, "Der Deckungbeitrag muss zwischen 0 und dem Preis des Zimmers liegen.");
			korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.SpontanbuchungenKat1JTextField.getText()) < 0 || Integer.parseInt(this.SpontanbuchungenKat1JTextField.getText()) > 100) {
		JOptionPane.showMessageDialog(null, "Achtung: Spontanbuchungen Kat1 % muss zwischen 0 und 100 liegen.");
		korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.SpontanbuchungenKat2JTextField.getText()) < 0 || Integer.parseInt(this.SpontanbuchungenKat2JTextField.getText()) > 100) {
		JOptionPane.showMessageDialog(null, "Achtung: Spontanbuchungen Kat2 % muss zwischen 0 und 100 liegen.");
		korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.SpontanbuchungenKat3JTextField.getText()) < 0 || Integer.parseInt(this.SpontanbuchungenKat3JTextField.getText()) > 100) {
		JOptionPane.showMessageDialog(null, "Achtung: Spontanbuchungen Kat3 % muss zwischen 0 und 100 liegen.");
		korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.ZimmerbelegungKat1JTextField.getText()) < 0 || Integer.parseInt(this.ZimmerbelegungKat1JTextField.getText()) > 100) {
		JOptionPane.showMessageDialog(null, "Achtung: Zimmerbelegung Kat1 % muss zwischen 0 und 100 liegen.");
		korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.ZimmerbelegungKat2JTextField.getText()) < 0 || Integer.parseInt(this.ZimmerbelegungKat2JTextField.getText()) > 100) {
		JOptionPane.showMessageDialog(null, "Achtung: Zimmerbelegung Kat2 % muss zwischen 0 und 100 liegen.");
		korrekteWerte = false;
	  }

	  if(Integer.parseInt(this.ZimmerbelegungKat3JTextField.getText()) < 0 || Integer.parseInt(this.ZimmerbelegungKat3JTextField.getText()) > 100) {
		JOptionPane.showMessageDialog(null, "Achtung: Zimmerbelegung Kat3 % muss zwischen 0 und 100 liegen.");
		korrekteWerte = false;
	  }


	  ///////
	  //
	  /// Korrekte Werte übernehmen
	  //
	  ///////
	  if(korrekteWerte) {

		zimmerAnzahlKat1 = Integer.parseInt(this.ZimmeranzahlKat1JTextField.getText());
		zimmerAnzahlKat2 = Integer.parseInt(this.ZimmeranzahlKat2JTextField.getText());
		zimmerAnzahlKat3 = Integer.parseInt(this.ZimmeranzahlKat3JTextField.getText());

		zimmerPreisKat1 = Integer.parseInt(this.ZimmerpreisKat1JTextField.getText());
		zimmerPreisKat2 = Integer.parseInt(this.ZimmerpreisKat2JTextField.getText());
		zimmerPreisKat3 = Integer.parseInt(this.ZimmerpreisKat3JTextField.getText());

		deckungsBeitragKat1 = Integer.parseInt(this.DeckungsbeitragKat1JTextField.getText());
		deckungsBeitragKat2 = Integer.parseInt(this.DeckungsbeitragKat2JTextField.getText());
		deckungsBeitragKat3 = Integer.parseInt(this.DeckungsbeitragKat3JTextField.getText());

		zimmerBelegungKat1 = Integer.parseInt(this.ZimmerbelegungKat1JTextField.getText());
		zimmerBelegungKat2 = Integer.parseInt(this.ZimmerbelegungKat2JTextField.getText());
		zimmerBelegungKat3 = Integer.parseInt(this.ZimmerbelegungKat3JTextField.getText());

		spontanBuchungenKat1 = Integer.parseInt(this.SpontanbuchungenKat1JTextField.getText());
		spontanBuchungenKat2 = Integer.parseInt(this.SpontanbuchungenKat2JTextField.getText());
		spontanBuchungenKat3 = Integer.parseInt(this.SpontanbuchungenKat3JTextField.getText());

		arbeitsVerzeichnis = this.ArbeitsverzeichnisJTextField.getText();
		solverPfad = this.SolverpfadJTextField.getText();

		this.RegisterJTabbedPane.setSelectedComponent(this.ReservierungJPanel);
		this.RegisterJTabbedPane.addNotify();
	  }
    }
    catch (NumberFormatException n) {
      JOptionPane.showMessageDialog(null, "Achtung: Ein Wert ist nicht korrekt eingeben.");
      return;
    }
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
