package standortplanung;

import java.awt.AWTEvent;
import java.awt.Desktop;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

/**
 * <p>
 * Überschrift: Standortplanung
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author Manuel Thoma, Markus Klemens
 * @version 3.0
 */

public class HauptFrame_new extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int solverTyp = 0;
	Controller controller;
	private JPanel contentPane;
	private JTextField tEntfernung = new JTextField();
	private JTextArea taSolverAusgabe = new JTextArea();
	private JTextArea taLoesung = new JTextArea(); 
	final JSpinner sZeile = new JSpinner();
	final JSpinner sSpalte = new JSpinner();
	final JButton bLP = new JButton();
	final JButton bXA = new JButton();
	final JButton bMops = new JButton();
	private JCheckBoxMenuItem cbmLpsolve = new JCheckBoxMenuItem("LP-Solve");
	private JCheckBoxMenuItem cbmXA = new JCheckBoxMenuItem("XA");
	private JCheckBoxMenuItem cbmMops = new JCheckBoxMenuItem("Mops");
	private JTable jTable = new JTable();
	private JTable jTable2 = new JTable();
	private Tabelle tabelle = new Tabelle(Variablen.startWithRows + 1, Variablen.startWithColumns + 1, 0);
	private Tabelle tabelle2 = new Tabelle(Variablen.startWithRows + 1, Variablen.startWithColumns + 1, 0);
	private JScrollPane scrollPaneTable = new JScrollPane();
	private JScrollPane scrollPaneBerechnung = new JScrollPane();
	private JScrollPane scrollPaneSolverAusgabe = new JScrollPane();
	private JScrollPane scrollPaneLösung = new JScrollPane();
	
	// Formate um die Ausgabe des LP-Solver zu verbessern
	private Format[] formate = new Format[] { 
			new Format('x', "Standort: "),new Format('R', "")
	};
	
	
	// Den Frame erzeugen
	public HauptFrame_new(Controller controller) {
		this.controller = controller;
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Die Frame-Elemente erzeugen
	private void init() {
		setTitle("Standortplanung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 898, 644);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Frame mittig ausrichten
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 882, 21);
		contentPane.add(menuBar);
		
		JMenu mDatei = new JMenu("Datei");
		menuBar.add(mDatei);
		
		// Menü Neu
		JMenuItem mNew = new JMenuItem("Neu");
		mDatei.add(mNew);
		mNew.setText("Neu");
		mNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuNeu_actionPerformed(e);
			}
		});
		
		// Menü Öffnen
		JMenuItem mOpen = new JMenuItem("Öffnen");
		mDatei.add(mOpen);
		mOpen.setText("Öffnen...");
		mOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuOeffnen_actionPerformed(e);
			}
		});
		
		// Menü Speichern
		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuSpeichern_actionPerformed(e);
			}
		});
		mDatei.add(mntmSpeichern);
		
		//Menü Speichern unter
		JMenuItem menuItemSpeichernUnter = new JMenuItem("Speichern unter");
		menuItemSpeichernUnter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuSpeichernUnter_actionPerformed(e);
			}
		});
		mDatei.add(menuItemSpeichernUnter);
		
		// Menü Beenden
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuBeenden_actionPerformed(e);
			}
		});
		mDatei.add(mntmBeenden);
		
		JMenu mnSolver = new JMenu("Solver");
		menuBar.add(mnSolver);
		
//		final JCheckBoxMenuItem mntmLpsolve = new JCheckBoxMenuItem("LP-Solve");
		mnSolver.add(cbmLpsolve);
		cbmXA.setVisible(false);
		
//		final JCheckBoxMenuItem mntmXa = new JCheckBoxMenuItem("XA");
		mnSolver.add(cbmXA);
		
//		final JCheckBoxMenuItem mntmMops = new JCheckBoxMenuItem("MOPS");
		mnSolver.add(cbmMops);
		
		JMenu mnEinstellungen = new JMenu("Einstellungen");
		menuBar.add(mnEinstellungen);
		
		JMenuItem mntmSolverpfade = new JMenuItem("Solverpfade/Arbeitsumgebung anpassen");
		mntmSolverpfade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solverSetting();
			}
		});
		mnEinstellungen.add(mntmSolverpfade);

		cbmLpsolve.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jCheckBoxMenuLP_Solve_actionPerformed(e);
					}
				});
		
		cbmXA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jCheckBoxMenu_XA_actionPerformed(e);
			}
		});
		
		cbmMops.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jCheckBoxMenu_MOPS_actionPerformed(e);
					}
				});
		
		// Menü Hilfe
		JMenu mHelp = new JMenu("?");
		menuBar.add(mHelp);
		
		JMenuItem mntmHilfe = new JMenuItem("Hilfe");
		mntmHilfe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuHilfe_actionPerformed(e);
			}
		});
		mHelp.add(mntmHilfe);
		
		// Menü Info
		JMenuItem mInfo = new JMenuItem("Info");
		mInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info();
			}
		});
		mHelp.add(mInfo);
		
		//Button für neues File
		JButton bNewFile = new JButton();
		bNewFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonNeu_actionPerformed(e);
			}
		});
		
		bNewFile.setRolloverSelectedIcon(new ImageIcon(".\\icons\\New-File-icon-active.png"));
		bNewFile.setRolloverEnabled(true);
		bNewFile.setIcon(new ImageIcon(".\\icons\\New-File-icon.png"));
		bNewFile.setBounds(10, 32, 33, 30);
		bNewFile.setToolTipText("Neue Datei anlegen");
		contentPane.add(bNewFile);
		
		// Button, um ein File zu öffnen
		JButton bOpen = new JButton();
		bOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonOeffnen_actionPerformed(e);
			}
		});
		
		bOpen.setIcon(new ImageIcon(".\\icons\\open-icon.png"));
		bOpen.setBounds(53, 32, 33, 30);
		bOpen.setToolTipText("Datei öffnen");
		contentPane.add(bOpen);
		
		// Button, um das File zu speichern
		JButton bSave = new JButton();
		bSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonSpeichern_actionPerformed(e);
			}
		});
		
		bSave.setIcon(new ImageIcon(".\\icons\\Save-icon.png"));
		bSave.setBounds(96, 32, 32, 30);
		bSave.setToolTipText("Datei speichern");
		contentPane.add(bSave);
		
		// Button für die Einstellungen
		JButton bSettings = new JButton();
		bSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solverSetting();
			}
		});
		bSettings.setIcon(new ImageIcon(".\\icons\\settings-icon.png"));
		bSettings.setBounds(138, 32, 33, 30);
		bSettings.setToolTipText("Solverpfade/Arbeitsumgebung anpassen");
		contentPane.add(bSettings);
		
		//Button LP-Solver
		bLP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jCheckBoxMenuLP_Solve_actionPerformed(e);
			}
		});
		bLP.setIcon(new ImageIcon(".\\icons\\LP-icon.png"));
		bLP.setBounds(223, 32, 33, 30);
		bLP.setToolTipText("LP-Solve auswählen");
		contentPane.add(bLP);
		bXA.setVisible(false);
		
		//Button XA-Solver
		bXA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jCheckBoxMenu_XA_actionPerformed(e);
			}
		});
		bXA.setIcon(new ImageIcon(".\\icons\\XA-icon.png"));
		bXA.setBounds(458, 32, 33, 30);
		bXA.setToolTipText("XA auswählen");
		contentPane.add(bXA);
		
		//Button MOPS-Solver
		bMops.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jCheckBoxMenu_MOPS_actionPerformed(e);
			}
		});
		bMops.setIcon(new ImageIcon(".\\icons\\Mops-icon.png"));
		bMops.setBounds(266, 32, 33, 30);
		bMops.setToolTipText("MOPS auswählen");
		contentPane.add(bMops);
		
		//Button HTWG
		JButton bHTWG = new JButton();
		bHTWG.setBorder(null);
		bHTWG.setIcon(new ImageIcon(".\\icons\\HTWG-Logo.jpg"));
		bHTWG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				// Link zur HTWG- Website im Standardbrowser öffnen
				try{
					String url ="http://www.htwg-konstanz.de";
					URI uri = new URI(url);
					Desktop dt = Desktop.getDesktop();
					dt.browse(uri.resolve(uri));
				} catch(Exception e){
					
				}

			}
		});
		bHTWG.setBounds(670, 24, 205, 48);
		contentPane.add(bHTWG);
		
		//Label Spalten
		JLabel lSpalten = new JLabel("Spalten:");
		lSpalten.setFont(new Font("Tahoma", Font.BOLD, 12));
		lSpalten.setBounds(10, 89, 70, 14);
		contentPane.add(lSpalten);
		
		//JSpinner für Spalte
		sSpalte.setBounds(65, 87, 40, 20);
		sSpalte.setToolTipText("Spaltenangabe für Matrix");
		contentPane.add(sSpalte);
		
		//Label Zeilen
		JLabel lZeilen = new JLabel("Zeilen:");
		lZeilen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lZeilen.setBounds(119, 89, 60, 14);
		contentPane.add(lZeilen);
		
		//JSpinner für Zeile
		sZeile.setBounds(162, 87, 40, 20);
		sZeile.setToolTipText("Zeilenangabe für Matrix");
		contentPane.add(sZeile);
		
		//Button Aktualisieren (Tabelle/Matrix)
		JButton baktualisieren = new JButton("Aktualisieren");
		baktualisieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//JSpinner auf negative Zahlen prüfen
				if (negativeZahlenPruefen(sSpalte,sZeile)== 1){
					return;
				};
				
				//Tabelle erstellen
				erstelleTabelle(sSpalte,sZeile);
				
			}
		});
		baktualisieren.setFont(new Font("Tahoma", Font.PLAIN, 10));
		baktualisieren.setBounds(223, 86, 93, 23);
		baktualisieren.setToolTipText("Matrix mit Spalten und Zeilen aktualisieren");
		contentPane.add(baktualisieren);
		
		//Button Berechnen
		JButton bBerechnen = new JButton("Berechnen");
		bBerechnen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				jButtonBerechnung_actionPerformed(e);
			}
		});
		bBerechnen.setBounds(326, 86, 89, 23);
		bBerechnen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bBerechnen.setToolTipText("Berechnung starten");
		contentPane.add(bBerechnen);
		
		// Button Info für die Einheiten
		JButton btnInfoEinheiten = new JButton("");
		btnInfoEinheiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Diese Einheiten können betrachtet werden:" + "\n"
						+ "- Sekunden (sec)" + "\n"
						+ "- Minuten (min)," + "\n"
						+ "- Stunden (h)," + "\n"
						+ "- Tage (d)," + "\n"
						+ "- Meter (m)," + "\n"
						+ "- Kilometer (km)" + "\n"
						, "Einheiten", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnInfoEinheiten.setIcon(new ImageIcon(".\\icons\\info.png"));
		btnInfoEinheiten.setBounds(190, 125, 33, 30);
		contentPane.add(btnInfoEinheiten);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 73, 882, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 120, 882, 2);
		contentPane.add(separator_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(8, 180, 864, 409);
		contentPane.add(tabbedPane);
		
		// Panel für die Matrix/Tabelle
		JPanel panelMatrix = new JPanel();
		tabbedPane.addTab("Matrix", null, panelMatrix, null);
		panelMatrix.setLayout(null);		
		scrollPaneTable.setBounds(0, 0, 859, 381);
		scrollPaneTable.setToolTipText("In diesem Bereich wird die Matrix angezeigt");
		panelMatrix.add(scrollPaneTable);
		
		// Panel für die Berechnung
		JPanel panelBerechnung = new JPanel();
		tabbedPane.addTab("Restriktionen", null, panelBerechnung, null);
		panelBerechnung.setLayout(null);
		scrollPaneBerechnung.setBounds(0, 0, 859, 381);
		scrollPaneBerechnung.setToolTipText("In diesem Bereich wird die Berechnungstabelle angezeigt");
		panelBerechnung.add(scrollPaneBerechnung);
		
		// Panel für die Solver-Ausgabe
		JPanel panelSolverAusgabe = new JPanel();
		tabbedPane.addTab("Solverausgabe", null, panelSolverAusgabe, null);
		panelSolverAusgabe.setLayout(null);
		scrollPaneSolverAusgabe.setBounds(0, 0, 859, 381);
		taSolverAusgabe.setToolTipText("In diesem Bereich wird die Solverausgabe angezeigt");
		panelSolverAusgabe.add(scrollPaneSolverAusgabe);
		scrollPaneSolverAusgabe.setViewportView(taSolverAusgabe);
		
		// Panel für die Lösung
		JPanel panelLoesung = new JPanel();
		tabbedPane.addTab("Lösung", null, panelLoesung, null);
		panelLoesung.setLayout(null);
		scrollPaneLösung.setBounds(0, 0, 859, 381);
		taLoesung.setToolTipText("In diesem Bereich wird die Lösung angezeigt");
		panelLoesung.add(scrollPaneLösung);
		scrollPaneLösung.setViewportView(taLoesung);

		
		JLabel lblMaxEntfernung = new JLabel("max. Entfernung: ");
		lblMaxEntfernung.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMaxEntfernung.setBounds(10, 133, 111, 14);
		contentPane.add(lblMaxEntfernung);
		
		// Textfeld für die Eingabe der Entfernung
		tEntfernung.setBounds(126, 130, 53, 20);
		tEntfernung.setToolTipText("Angabe der max. Entfernung");
		contentPane.add(tEntfernung);
		tEntfernung.setColumns(10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 167, 882, 2);
		contentPane.add(separator_2);
	}
	
	// Überschreiben, so dass eine Beendigung beim Schließen des Fensters
	// möglich ist.
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuBeenden_actionPerformed(null);
		}
	}
	
	// Methode um die Solverpfade einzugeben
	public void solverSetting(){
		FrameSolverpfade pfadsetup;
		try {
			pfadsetup = new FrameSolverpfade();
			pfadsetup.createFrameSolverpfade();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Überprüfen Sie die Solverpfade auf Fehler",
					"Achtung", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	// Methode um die Info anzuzeigen
	public void info(){
		FrameInfo info;
		try {
			info = new FrameInfo();
			info.createFrameInfo();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null,
					"Fehler beim Ausführen des Frames",
					"Achtung", JOptionPane.WARNING_MESSAGE);
			e2.printStackTrace();
		}
	}
	
	//Methode um den JSpinner auf negative Zahlen zu prüfen
	public int negativeZahlenPruefen(JSpinner spalte, JSpinner zeile){
		if (Integer.valueOf((spalte.getValue().toString())) <= 0){
			JOptionPane.showMessageDialog(null,
					"Überprüfen Sie die Spalteneingabe!" + "\n"
					+ "Es dürfen nur Zahlen größer Null eingegeben werden.",
					"Achtung", JOptionPane.WARNING_MESSAGE);
			return 1;
		}
		
		if (Integer.valueOf((zeile.getValue().toString())) <= 0){
			JOptionPane.showMessageDialog(null,
					"Überprüfen Sie die Zeileneingabe!" + "\n"
					+ "Es dürfen nur Zahlen größer Null eingegeben werden",
					"Achtung", JOptionPane.WARNING_MESSAGE);
			return 1;
		}
		return 0;
	}
	
	//Methode, um die Tabelle/Matrix aus Auswendung zu erstellen
	private void erstelleTabelle(JSpinner spalte, JSpinner zeile) {
				
		int rows = Integer.valueOf(zeile.getValue().toString());
		int columns = Integer.valueOf(spalte.getValue().toString());
		tEntfernung.setText("");
		
		scrollPaneTable.remove(jTable);
		tabelle = new Tabelle(rows + 1, columns + 1, 0);// erstellt neue Tabelle
		jTable = new JTable(tabelle);
		jTable.setRowSelectionAllowed(false);
		scrollPaneTable.setViewportView(jTable);
		
		for (int k = 1; k < rows + 1; k++) {
			for (int l = 1; l < columns + 1; l++) {
				if (k == l) {
//					jTable.setValueAt("0", k, l);
				}
			}
		}
		
		jTable.setAutoResizeMode(0); // austellen, dass die Größe nicht automatisch angepasst wird
		jTable.getColumnModel().getColumn(0).setPreferredWidth(70); //Größe der Zellen ebstimmen
		for (int i = 1; i < tabelle.getColumnCount(); i++){
			jTable.getColumnModel().getColumn(i).setPreferredWidth(70);
		}
	}
	
	//Methode, um die Berechnungstabelle aus Matrix zu erstellen
	private void erstelleTabelleBerechnung() {
		
		scrollPaneBerechnung.remove(jTable2);
		tabelle2 = new Tabelle(tabelle.getRowCount() + 1, tabelle.getColumnCount() + 2, 0);// erstellt neue Tabelle
		jTable2 = new JTable(tabelle2) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable (int x, int y){
				return false;
			}
		};
		jTable2.setRowSelectionAllowed(false);
		scrollPaneBerechnung.setVisible(true);
		scrollPaneBerechnung.setViewportView(jTable2);
		
		//Umrechnung in 0 oder 1
		for (int i = 1; i < tabelle.getRowCount(); i++) {
			for (int j = 1; j < tabelle.getColumnCount(); j++) {
				if (Integer.parseInt(tabelle.getValueAt(i, j).
						toString()) > Integer.valueOf(tEntfernung.getText()).intValue()) {
					tabelle2.setValue2At(0, i, j);
				} else {
					tabelle2.setValue2At(1, i, j);
				}
			}
		}
		
		// Zeile hinzufügen (Zielfunktion)
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < tabelle2.getColumnCount(); j++) {
				if(j == 0){
					tabelle2.setValue2At("Zielfunktion", tabelle2.getRowCount()-1, j);
					continue;
				}
				tabelle2.setValue2At("1", tabelle2.getRowCount()-1, j);
			}
		}
		
		// Spalten hinzufügen (Zielfunktionswert)
		for (int i = 0; i < tabelle2.getRowCount(); i++) {
			for (int j = 0; j < 2; j++) {				
				if(i == 0 && j == 0){
					tabelle2.setValue2At(" ", i, tabelle2.getColumnCount()-2);
					continue;
				}
				
				if(i == 0 && j == 1){
					tabelle2.setValue2At("b", i, tabelle2.getColumnCount()-1);
					continue;
				}
				
				if(i == tabelle2.getRowCount()-1 && j == 0){
					tabelle2.setValue2At("-->", i, tabelle2.getColumnCount()-2);
					continue;
				}
				
				if(i == tabelle2.getRowCount()-1 && j == 1){
					tabelle2.setValue2At("min!", i, tabelle2.getColumnCount()-1);
					continue;
				}
				
				if(i > 0 && j == 0){
					tabelle2.setValue2At(">=", i, tabelle2.getColumnCount()-2);
					continue;
				}
				tabelle2.setValue2At("1", i, tabelle2.getColumnCount()-j);
			}
		}
		
		jTable2.setAutoResizeMode(0); // austellen, dass die Größe nicht automatisch angepasst wird
		jTable2.getColumnModel().getColumn(0).setPreferredWidth(70); //Größe der Zellen ebstimmen
		for (int i = 1; i < tabelle2.getColumnCount(); i++){
			jTable2.getColumnModel().getColumn(i).setPreferredWidth(70);
		}
	}
	
	// ******************** Starten der Berechnung
	// **********************************
	void jButtonBerechnung_actionPerformed(ActionEvent e) {

		if (solverTyp == 0) {
			JOptionPane.showMessageDialog(null,
					"Sie haben keinen Solver ausgewählt!",
					"Solver nicht gewählt", JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (int i = 1; i < tabelle.getRowCount(); i++) {
			for (int j = 1; j < tabelle.getColumnCount(); j++) {
				if (tabelle.getValueAt(i, j).toString() == "") {
					JOptionPane.showMessageDialog(null,
							"Die Matrix ist unvollständig.",
							"Daten nicht vollständig",
							JOptionPane.WARNING_MESSAGE);
					return; // zurück zur Matrix
				}
			}
		}
		if (tEntfernung.getText().length() == 0 || Integer.parseInt(tEntfernung.getText()) <= 0 ) {
			JOptionPane.showMessageDialog(null,
					"Die Entfernung muß größer 0 sein!!", "Enfernung falsch",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int distanz = Integer.valueOf(tEntfernung.getText()).intValue();
		
        //Abfangen, wenn die Tabelle beim Mops asymetrisch ist
		if (solverTyp == 1 || solverTyp == 3) {
			int differenzZeilen = 0;
			int differenzSpalten = 0;
			
			// Wenn es weniger Zeilen als Spalten sind
			if (tabelle.getRowCount() < tabelle.getColumnCount()) {
				// ausrechenen, wie viel Zeilen unterschied es sind (Anzahl
				// Spalte - Anzahl Zeilen)
				differenzZeilen = tabelle.getColumnCount()- tabelle.getRowCount();
				Tabelle tabelleCopy = new Tabelle(tabelle.getRowCount() + differenzZeilen, tabelle.getColumnCount(), 0);// erstellt neue Tabelle

				// Tabelle 1 auf TabelleCopy kopieren
				for (int i = 1; i < tabelle.getRowCount(); i++) {
					for (int j = 1; j < tabelle.getColumnCount(); j++) {
						tabelleCopy.setValueAt(tabelle.getValueAt(i, j), i, j);
					}
				}

				// aus der asymetrischen Tabelle eine symetrische machen
				for (int i = 0; i < differenzZeilen; i++) {
					for (int j = 1; j < tabelleCopy.getColumnCount(); j++) {
						tabelleCopy.setValueAt("10000000", tabelle.getRowCount()+ i, j);
					}
				}
				this.tabelle = new Tabelle(tabelleCopy.getRowCount(), tabelleCopy.getColumnCount(), 0);// überschreibt die fertige symetrische Tabelle
				
				// Tabelle zurückkopieren, da der Solver die Variable tabelle nutzt
				for (int i = 1; i < tabelleCopy.getRowCount(); i++) {
					for (int j = 1; j < tabelleCopy.getColumnCount(); j++) {
						tabelle.setValueAt(tabelleCopy.getValueAt(i, j), i, j);
					}
				}
			}
			
			// Wenn es weniger Spalten als Zeilen sind
			if (tabelle.getColumnCount() < tabelle.getRowCount()) {
				// ausrechenen, wie viel Spalten unterschied es sind (Anzahl Spalte - Anzahl Zeilen)
				differenzSpalten = tabelle.getRowCount() - tabelle.getColumnCount();
				Tabelle tabelleCopy = new Tabelle(tabelle.getRowCount(), tabelle.getColumnCount()+differenzSpalten, 0);// erstellt neue Tabelle

				// Tabelle 1 auf 2 kopieren
				for (int i = 1; i < tabelle.getRowCount(); i++) {
					for (int j = 1; j < tabelle.getColumnCount(); j++) {
						tabelleCopy.setValueAt(tabelle.getValueAt(i, j), i, j);
					}
				}

				// aus der asymetrischen Tabelle eine symetrische machen
				for (int i = 1; i < tabelleCopy.getRowCount(); i++) {
					for (int j = 0; j < differenzSpalten; j++) {
						tabelleCopy.setValueAt("10000000", i, tabelle.getColumnCount()+j);
					}
				}
				
				this.tabelle = new Tabelle(tabelleCopy.getRowCount(), tabelleCopy.getColumnCount(), 0);// überschreibt die fertige symetrische Tabelle
				
				// Tabelle zurückkopieren, da der Solver die Variable tabelle nutzt
				for (int i = 1; i < tabelleCopy.getRowCount(); i++) {
					for (int j = 1; j < tabelleCopy.getColumnCount(); j++) {
						tabelle.setValueAt(tabelleCopy.getValueAt(i, j), i, j);
					}
				}
			}
		}
		
		String result = controller.starteSolve(tabelle.getMatrix(), solverTyp,
				distanz); // 1=LP,2=XA
		jMenuAuswertung_actionPerformed("Ergebnis", result);
	}
		
	// LP-Solve wurde ausgewählt
	void jCheckBoxMenuLP_Solve_actionPerformed(ActionEvent e) {
		cbmLpsolve.setSelected(true);
		cbmXA.setSelected(false);
		cbmMops.setSelected(false);
		bLP.setIcon(new ImageIcon(".\\icons\\LP-aktiv-icon.png"));
		bXA.setIcon(new ImageIcon(".\\icons\\XA-icon.png"));
		bMops.setIcon(new ImageIcon(".\\icons\\Mops-icon.png"));
		this.solverTyp = 1;
	}

	// XA wurde ausgewählt
	void jCheckBoxMenu_XA_actionPerformed(ActionEvent e) {
		cbmLpsolve.setSelected(false);
		cbmXA.setSelected(true);
		cbmMops.setSelected(false);
		bLP.setIcon(new ImageIcon(".\\icons\\LP-icon.png"));
		bXA.setIcon(new ImageIcon(".\\icons\\XA-aktiv-icon.png"));
		bMops.setIcon(new ImageIcon(".\\icons\\Mops-icon.png"));
		this.solverTyp = 2;
	}

	// MOPS wurde ausgewählt
	void jCheckBoxMenu_MOPS_actionPerformed(ActionEvent e) {
		cbmLpsolve.setSelected(false);
		cbmXA.setSelected(false);
		cbmMops.setSelected(true);
		bLP.setIcon(new ImageIcon(".\\icons\\LP-icon.png"));
		bXA.setIcon(new ImageIcon(".\\icons\\XA-icon.png"));
		bMops.setIcon(new ImageIcon(".\\icons\\Mops-aktiv-icon.png"));
		this.solverTyp = 3;
	}
	
	// ****************************** Menü
	// ******************************************
	void jMenuAuswertung_actionPerformed(String headline, String text) {
		
		// Registerkarte SolverAusgabe
		taSolverAusgabe.setText("");
		
		// Ausgabe in der Registerkarte Berechnung
		erstelleTabelleBerechnung();
		
		// Ausgabe in der Registerkarte Solverausgabe
		
		// LP-Solve
		if(solverTyp == 1){
			String zeichenkette = "";
			
			zeichenkette = "Restriktionen des LP-Solvers: \n";
			
			try{
				BufferedReader br = new BufferedReader(new FileReader(new FrameSolverpfade().getTfTEMP().getText() + "\\lp_solve.in"));
				String zeile = null;
				while ((zeile = br.readLine()) != null) {
					zeichenkette += zeile +"\n";
				}
				taSolverAusgabe.setText(zeichenkette);
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			zeichenkette = zeichenkette + "\n \n Lösung des LP-Solvers: \n";
			
			try{
				BufferedReader br = new BufferedReader(new FileReader(new FrameSolverpfade().getTfTEMP().getText() + "\\lp_solve.out"));
				String zeile = null;
				while ((zeile = br.readLine()) != null) {
					zeichenkette += zeile +"\n";
				}
				taSolverAusgabe.setText(zeichenkette);
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		// MOPS
		if(solverTyp == 3){
			try{
				BufferedReader br = new BufferedReader(new FileReader(new FrameSolverpfade().getTfTEMP().getText() 
														+ "\\mops.lps"));
				String zeile = null;
				String zeichenkette = "";
				while ((zeile = br.readLine()) != null) {
					zeichenkette += zeile +"\n";
				}
				taSolverAusgabe.setText(zeichenkette);
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// LP-Solverausgabe "verschönern"
		if (solverTyp == 1){
			
			String neuerTeilString = "";
			
			for (int i = 0; i < text.length(); i++) {
				//Abrechen wenn die "nulls" kommen
				if (text.charAt(i)=='n' && text.charAt(i+1)=='u' && text.charAt(i+2)=='l' && text.charAt(i+3)=='l'){
					break;
				}
				// Die herumschwirrenden 1 löschen 
				if (text.charAt(i)=='1' && text.charAt(i-1)==' ' && text.charAt(i-2)==' ' && text.charAt(i-3)==' '){
					continue;
				}
				//wenn nichts zutrifft, Text übernehmen
				neuerTeilString += ersetzen(text.charAt(i));
			}
			text = neuerTeilString;			
		}
		
		// Ausgabe in der Registerkarte Lösung
		taLoesung.setText("=============== \n" + headline + " \n=============== \n" + text);
	}
	
	void jMenuNeu_actionPerformed(ActionEvent e) {
//		if (negativeZahlenPruefen(sSpalte,sZeile)== 1){
//			return;
//		};
		tEntfernung.setText("");
		scrollPaneBerechnung.setVisible(false);
		taSolverAusgabe.setText("");
		taLoesung.setText("");
		sSpalte.setValue(3);
		sZeile.setValue(3);
		erstelleTabelle(sSpalte, sZeile);
		controller.filename = null;
		this.setTitle(Variablen.toolName);
	}
	
	void jMenuOeffnen_actionPerformed(ActionEvent e) {
		try {
			Daten matrix = controller.dateiOeffnen(e);
			if (matrix != null) {
				// Löschen der bisherigen Ergebnisse
				scrollPaneBerechnung.setVisible(false);
				taSolverAusgabe.setText("");
				taLoesung.setText("");
				this.erstelleTabelle(sSpalte, sZeile);
				sSpalte.setValue(matrix.getColumns()-1);
				sZeile.setValue(matrix.getRows()-1);
				tabelle.setMatrix(matrix);
				this.tEntfernung.setText(String.valueOf(matrix
						.getEntfernung()));
			} else {
			}
		} catch (Exception eOeffnen) {
			JOptionPane.showMessageDialog(null,
					"Fehler beim Öffnen der Datei!", "Fehler",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void jMenuSpeichern_actionPerformed(ActionEvent e) {
		try {
			int distanz = Integer.valueOf(tEntfernung.getText()).intValue();
			if (controller.dateiSpeichern(e, this.tabelle.getMatrix(), distanz)) {
				this.setTitle(Variablen.toolName + " - " + controller.filename);
			} else {
			}
		} catch (NumberFormatException eMenuSpeichern) {
			JOptionPane.showMessageDialog(null,
					"Bitte geben Sie eine max. Entfernung ein!", "Fehler",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void jMenuSpeichernUnter_actionPerformed(ActionEvent e) {
		try {
			int distanz = Integer.valueOf(tEntfernung.getText()).intValue();
			if (controller.dateiSpeichernUnter(e, this.tabelle.getMatrix(), distanz)) {
				this.setTitle(Variablen.toolName + " - " + controller.filename);
			} else {
			}
		} catch (NumberFormatException eMenuSpeichern) {
			JOptionPane.showMessageDialog(null,
					"Bitte geben Sie eine max. Entfernung ein!", "Fehler",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void jMenuBeenden_actionPerformed(ActionEvent e) {
		Object[] options = { "Ja", "Nein" };
		int auswahl = JOptionPane.showOptionDialog(null,
				"Soll das Progamm beendet werden?" + "\n"
						+ "Nicht gespeicherte Daten gehen verloren!", "Beenden",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
		if (auswahl == 0)
			System.exit(0);
		else
			return;
	}
	
	void jMenuHilfe_actionPerformed(ActionEvent e) {
	try {
		Runtime.getRuntime().exec(
				"rundll32 url.dll,FileProtocolHandler "
						+ new java.io.File(".\\Programmhilfe\\Programmhilfe_Standortplanungv3.html")
								.getAbsolutePath());
	} catch (Exception ex) {
		JOptionPane.showMessageDialog(null,
				"Hilfe konnte nicht geöffnet werden!", "Fehler",
				JOptionPane.WARNING_MESSAGE);
	}
}
	
	// ************************** Buttons
	// *******************************************

	void jButtonNeu_actionPerformed(ActionEvent e) {
//		if (negativeZahlenPruefen(sSpalte,sZeile)== 1){
//			return;
//		};
		tEntfernung.setText("");
		scrollPaneBerechnung.setVisible(false);
		taSolverAusgabe.setText("");
		taLoesung.setText("");
		sSpalte.setValue(3);
		sZeile.setValue(3);
		erstelleTabelle(sSpalte, sZeile);
		controller.filename = null;
		this.setTitle(Variablen.toolName);
	}

	
	void jButtonOeffnen_actionPerformed(ActionEvent e) {
		try {
			Daten matrix = controller.dateiOeffnen(e);
			if (matrix != null) {
				// Löschen der bisherigen Ergebnisse
				scrollPaneBerechnung.setVisible(false);
				taSolverAusgabe.setText("");
				taLoesung.setText("");
				this.erstelleTabelle(sSpalte, sZeile);
				this.sSpalte.setValue(matrix.getColumns()-1);
				this.sZeile.setValue(matrix.getRows()-1);
				tabelle.setMatrix(matrix);
				this.tEntfernung.setText(String.valueOf(matrix
						.getEntfernung()));
			} else {
			}
		} catch (Exception eOeffnen) {
			JOptionPane.showMessageDialog(null,
					"Fehler beim Öffnen der Datei!", "Fehler",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	void jButtonSpeichern_actionPerformed(ActionEvent e) {
		Object[] options = { "Speichern", "Speichern unter", "Abbrechen" };
		int auswahl = JOptionPane.showOptionDialog(null,
				"Wählen Sie eine Speichermöglichkeit:", "Speichern",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);
		if (auswahl == 0){
			try {
				int distanz = Integer.valueOf(tEntfernung.getText()).intValue();
				if (controller.dateiSpeichern(e, this.tabelle.getMatrix(), distanz)) {
					this.setTitle(Variablen.toolName + " - " + controller.filename);
				} else {
				}
			} catch (NumberFormatException eMenuSpeichern) {
				JOptionPane.showMessageDialog(null,
						"Bitte geben Sie eine max. Entfernung ein!", "Fehler",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		if (auswahl == 1){
			try {
				int distanz = Integer.valueOf(tEntfernung.getText()).intValue();
				if (controller.dateiSpeichernUnter(e, this.tabelle.getMatrix(), distanz)) {
					this.setTitle(Variablen.toolName + " - " + controller.filename);
				} else {
				}
			} catch (NumberFormatException eMenuSpeichern) {
				JOptionPane.showMessageDialog(null,
						"Bitte geben Sie eine max. Entfernung ein!", "Fehler",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		if (auswahl == 2){
			return;
		}
	}
	
	private String ersetzen(char einZeichen) {
		for (Format f : formate) {
			if (einZeichen == f.c) {
				return (f.n);
			}
		}
		return "" + einZeichen;
	}
}
