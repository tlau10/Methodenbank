//*******************************************************************
//*		plo_HilfeDialog.java										*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Beschreibt die Vorgehensweise im Wagner Whitin LP	*
//*		Hilfe des P.L.O.-Systems									*
//*																	*
//*		Version: 1.1												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Enumeration;
import javax.help.*;
import javax.swing.*;

public class plo_HilfeDialog extends JDialog {
	private static final long serialVersionUID = 4094819326016122411L;
	
	//Komponenten des Dialogs
	private JInternalFrame hilfeFrame;
	private JPanel hilfePanel;
	private JLabel hilfeLabel1;
	private JLabel hilfeLabel2;
	private JLabel hilfeLabel3;
	private JLabel hilfeLabel4;
	private JLabel hilfeLabel5;
	private JLabel hilfeLabel7;
	private JLabel hilfeLabel8;
	private JLabel hilfeLabel9;
	private JLabel hilfeLabel10;
	private JLabel hilfeLabel11;
	private JLabel hilfeLabel12;
	private JLabel hilfeLabel13;
	private JLabel hilfeLabel14;
	private JLabel hilfeLabel15;
	private JLabel hilfeLabel16;
	private JLabel hilfeLabel17;
	private JLabel hilfeLabel18;
	private JLabel hilfeLabel19;
	private JLabel hilfeLabel20;
	private JLabel hilfeLabel21;
	private JLabel hilfeLabel22;
	private JLabel hilfeLabel23;
	private JLabel hilfeLabel24;
	private JLabel hilfeLabel25;
	private JLabel hilfeLabel26;
	
	private final JButton jb_Ok;
	private GridBagLayout hilfeGridBagLayout;
	private GridBagConstraints hilfeGridBagConstraints;

	// Konstruktor
	public plo_HilfeDialog() {
		// Initialisieren des Referenzobjekts fuer dispose()-Aufrufe
		final plo_HilfeDialog ref = this;

		hilfeFrame = new JInternalFrame();
		this.setTitle("P.L.O.-Hilfe");
		hilfeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		hilfePanel		= new JPanel();
		hilfeLabel1 	= new JLabel("   Periodenorientierte Lagerhaltungs Optimierung");
		hilfeLabel2 	= new JLabel(" ");
		hilfeLabel3 	= new JLabel("------------------------------------------------------------------------------------------ ");
		hilfeLabel4 	= new JLabel("   Neue Modelle erstellen");
		hilfeLabel5 	= new JLabel("------------------------------------------------------------------------------------------ ");
		hilfeLabel7 	= new JLabel("   1.   Klicken Sie auf Lagermodell --> Neues Modell.");
		hilfeLabel8 	= new JLabel(" 	  2.   Nun geben Sie die Anzahl der Perioden ein.");
		hilfeLabel9 	= new JLabel("   3.   Im nächsten Schritt tippen Sie alle Daten ein.");
		hilfeLabel10 	= new JLabel("   4.   Jetzt klicken Sie auf Loesung --> Optimale Loesung.");
		hilfeLabel11 	= new JLabel("   5.   Die Lösung mit den optimalen Lossgroessen erscheint.");
		hilfeLabel12 	= new JLabel(" ");
		hilfeLabel13 	= new JLabel("------------------------------------------------------------------------------------------ ");
		hilfeLabel14 	= new JLabel("   Modelle speichern");
		hilfeLabel15 	= new JLabel("------------------------------------------------------------------------------------------ ");
		hilfeLabel16 	= new JLabel("   1.   Klicken Sie auf Lagermodell --> Modell speichern.");
		hilfeLabel17 	= new JLabel("   2.   Nun waehlen Sie einen Speicherort und bestaetigen mit OK.");
		hilfeLabel18 	= new JLabel(" ");
		hilfeLabel19 	= new JLabel("------------------------------------------------------------------------------------------ ");
		hilfeLabel20 	= new JLabel("   Modelle laden");
		hilfeLabel21 	= new JLabel("------------------------------------------------------------------------------------------ ");
		hilfeLabel22 	= new JLabel("   1.   Gespeicherte Modelle können geladen werden, indem Sie auf ");
		hilfeLabel23 	= new JLabel("          Lagermodell --> Modell laden klicken.");
		hilfeLabel24	= new JLabel("   2.   Waehlen Sie das zu oeffnende Modell und bestaetigen mit OK.");
		hilfeLabel25	= new JLabel(" ");
		hilfeLabel26 	= new JLabel("   Vielen Dank!   ");

		jb_Ok 			= new JButton("     Ok     ");
		jb_Ok.setSize(150, 50);
		
		hilfeGridBagLayout = new GridBagLayout();
		hilfeGridBagConstraints = new GridBagConstraints();
		
		hilfePanel.setLayout(hilfeGridBagLayout);

		// Erstellen der hilfeGridBagConstraints
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel1, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel2, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel3, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel4, 0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel5, 0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel7, 0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel8, 0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel9, 0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel10, 0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel11, 0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel12, 0, 10, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel13, 0, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel14, 0, 12, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel15, 0, 13, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel16, 0, 14, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel17, 0, 15, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel18, 0, 16, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel19, 0, 17, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel20, 0, 18, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel21, 0, 19, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel22, 0, 20, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel23, 0, 21, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel24, 0, 22, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel25, 0, 23, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel26, 0, 24, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));
		this.buildConstraints(hilfeGridBagConstraints, jb_Ok, 0, 25, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5, 0, 5, 0));

		this.setModal(true);
		this.setSize(480, 800);
		this.setLocation(200, 100);
		hilfePanel.add(hilfeFrame);
		hilfePanel.add(hilfeLabel1);
		hilfePanel.add(hilfeLabel2);
		hilfePanel.add(hilfeLabel3);
		hilfePanel.add(hilfeLabel4);
		hilfePanel.add(hilfeLabel5);
		hilfePanel.add(hilfeLabel7);
		hilfePanel.add(hilfeLabel8);
		hilfePanel.add(hilfeLabel9);
		hilfePanel.add(hilfeLabel10);
		hilfePanel.add(hilfeLabel11);
		hilfePanel.add(hilfeLabel12);
		hilfePanel.add(hilfeLabel13);
		hilfePanel.add(hilfeLabel14);
		hilfePanel.add(hilfeLabel15);
		hilfePanel.add(hilfeLabel16);
		hilfePanel.add(hilfeLabel17);
		hilfePanel.add(hilfeLabel18);
		hilfePanel.add(hilfeLabel19);
		hilfePanel.add(hilfeLabel20);
		hilfePanel.add(hilfeLabel21);
		hilfePanel.add(hilfeLabel22);
		hilfePanel.add(hilfeLabel23);
		hilfePanel.add(hilfeLabel24);
		hilfePanel.add(hilfeLabel25);
		hilfePanel.add(hilfeLabel26);
		hilfePanel.add(jb_Ok);

		ActionListener plo_HilfeDiaButListener = new ActionListener() {
			public void actionPerformed(ActionEvent aevt) {
				JButton compare = new JButton();
				compare = (JButton) aevt.getSource();
				if (compare.equals(jb_Ok)) {
					ref.dispose();
				}
			}
		};

		//Listener zum Abfangen von Return-Tastendruck
		KeyListener plo_HilfeDiaKeyListener = new KeyListener()
		{
			public void keyPressed(KeyEvent kevt) //
			{
				int compare = kevt.getKeyCode();
				
				//if you click enter
				if (compare == KeyEvent.VK_ENTER)
				{
					//close message
					ref.dispose();
				}
			}

			public void keyReleased(KeyEvent kevt) {

			}

			public void keyTyped(KeyEvent kevt) {

			}
		};

		jb_Ok.addActionListener(plo_HilfeDiaButListener);
		jb_Ok.addKeyListener(plo_HilfeDiaKeyListener);

		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(hilfePanel);
		this.setVisible(true);
	}//ENDE:Konstruktor

	void jMenuHelpAbout_actionPerformed(ActionEvent e) {JHelp helpViewer = null;
		try {
			//Hauptfenster in der nächsten Zeile ersetzen durch aktuellen Klassennamen
			ClassLoader cl = plo_HilfeDialog.class.getClassLoader();
			URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
			helpViewer = new JHelp(new HelpSet(cl, url));
			//Darzustellendes Kapitel festlegen, ID muss im XML existieren!
			
			//helpViewer.getsetCurrentID("Simple.Introduction");

			Enumeration<?> eNavigators = helpViewer.getHelpNavigators();
			while (eNavigators.hasMoreElements()) {
				JHelpNavigator nav = (JHelpNavigator) eNavigators.nextElement();
				if (nav instanceof JHelpIndexNavigator) {
					helpViewer.removeHelpNavigator(nav);
				}
			}
		} 
		catch (Exception ex) {
			System.err.println("API Help Set not found");
		}

		JFrame frame = new JFrame();
		frame.setTitle("Hilfe zu Wagner Within LP");
		frame.setSize(800, 600);
		frame.getContentPane().add(helpViewer);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}

	// Hilfsmehtode
	public void buildConstraints(GridBagConstraints c, Component com, int x, int y, int width, int height, int fill, int ank, int pdx, int pdy, Insets ins) {
		// Hilfsmethode zur besseren Erstellung von Komponenten in GridBagConstraints
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		c.fill = fill;
		c.anchor = ank;
		c.ipadx = pdx;
		c.ipady = pdy;
		c.insets = ins;
		hilfeGridBagLayout.setConstraints(com, c);
	}
}