//*******************************************************************
//*																	*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse für den Datentyp, welcher	    *
//*		die Nachfrqagen eines Modells verwaltet		  				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*     Version 1.2 												*
//*     von Marco Weiß und Jenne Justin								*
//*																	*
//*******************************************************************

package plo_System;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

//*** Klasse plo_Ergebnisfenster ****************************************************************************************************************

public class plo_Ergebnisfenster extends JInternalFrame{
	private static final long serialVersionUID = -8567292923384503888L;
	final JLabel ef_Periode1;										//Beschriftungskomponenten der Tabelle
	final JLabel ef_Bestellmenge1;									//des Ergebnisfensters
	final JLabel ef_Lagermenge1;									
	final JLabel ef_Fehlmenge1;										
	final JLabel ef_Lagerhaltungskosten1;							
	final JLabel ef_Periode2;										
	final JLabel ef_Bestellmenge2;									
	final JLabel ef_Lagermenge2;									
	final JLabel ef_Fehlmenge2;										
	final JLabel ef_Lagerhaltungskosten2;							
	final JLabel ef_Gesamtkosten;									
	private static JTextField tf_Gesamtkosten;						

	private Vector[] ergebnisVector;								//Vector um das Ergebnis des Solveraufrufs aufzunehmen

	private static periodenErgebnis[] ef_PeriodenErgebnisListe;		//Ergebnis-Array
	private static int anzahlPeriodenErgebnisse;					//Anzahl der Ergebnisse
	private plo_Ef_SubwindowListener ef_Listener;					//Ereignis-Listener des Ergebnisfensters
	private boolean ef_IsSaved;										//boolsche Variable zur Bestimmung, ob die Loesung bereits abgespeicher wurde
	private static plo_Eingabemaske ef_ZugehoerigeEingabemaske;		//Referenzobjekt auf das Fenster des zugehoerigen Modells
	private plo_Hauptfenster root;									//Referenzobjekt auf das Hauptfenster

	JPanel plo_ErgebnisPanel;										//Darstellungspanel
	JScrollPane ef_ErgebnisScrollPane;								//Scrollpane fuer das Darstellungspanel
	GridBagLayout ef_GridBagLayout;									//Layout
	GridBagConstraints ef_GridBagConstraints;						//Layout-Constraints

	
//*** Konstruktor  ********************************************************************************************
	public plo_Ergebnisfenster(/*int anzahlPeriodenErgebnisse */plo_Hauptfenster base, String title, boolean resizable, boolean maximizable, boolean closable, boolean iconifiable, plo_Eingabemaske parent)
	{
		this.setTitle(title+" - Lösung");							//Initialisieren der Parameter des
		this.setSize(600, 387);										//Ergebnisfensters
		this.setMaximizable(maximizable);							
		this.setResizable(resizable);								
		this.setIconifiable(iconifiable);
		this.setClosable(closable);
		this.setRoot(base);
		Insets standard = new Insets(3, 5, 3, 5);					//Erstellen des Standardinsets
		ergebnisVector = new Vector[2];
		ergebnisVector[0] = new Vector();
		ergebnisVector[1] = new Vector();

		anzahlPeriodenErgebnisse = 10;								//Anzahl der Ergebnisse

		ef_Listener = new plo_Ef_SubwindowListener(this.getRoot(), this);	//Ergebnislistener des Fensters
		ef_IsSaved = false;													//Bool-Variable die bestimmt, ob das Ergebnisfenster bereits gespeichert wurde
		ef_ZugehoerigeEingabemaske = parent;								//Initialisieren des Referenzobjets auf das Modell

		//Initialisieren der Beschriftungslabels
		ef_Periode1 = new JLabel("Periode");						
		ef_Bestellmenge1 = new JLabel("Bestellmenge");			
		ef_Lagermenge1 = new JLabel("Lagermenge");				
		ef_Fehlmenge1 = new JLabel("Fehlmenge");				
		ef_Lagerhaltungskosten1 = new JLabel("Lagerhaltungs-");	
		ef_Periode2 = new JLabel(" ");							
		ef_Bestellmenge2 = new JLabel(" ");						
		ef_Lagermenge2 = new JLabel(" ");						
		ef_Fehlmenge2= new JLabel(" ");							
		ef_Lagerhaltungskosten2 = new JLabel("kosten");			
		ef_Gesamtkosten = new JLabel("Gesamtkosten");			
		tf_Gesamtkosten = new JTextField("0", 10);

		periodenErgebnis ef_erg;									
		ef_PeriodenErgebnisListe = new periodenErgebnis[anzahlPeriodenErgebnisse];	//Erstellen des Ergebnis-Arrays

		ef_GridBagLayout = new GridBagLayout();						//Erstellen des Layouts
		ef_GridBagConstraints = new GridBagConstraints();			//Erstellen der Constraints

		plo_ErgebnisPanel = new JPanel(ef_GridBagLayout);			//Erstellen des Darstellungspanels
		plo_ErgebnisPanel.setLayout(ef_GridBagLayout);

		//Erstellend der Constraints fuer die Beschriftungskomponenten
		this.buildConstraints(ef_GridBagConstraints, ef_Periode1, 0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(ef_GridBagConstraints, ef_Bestellmenge1, 2, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	
		this.buildConstraints(ef_GridBagConstraints, ef_Lagermenge1, 4, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(ef_GridBagConstraints, ef_Fehlmenge1, 6, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(ef_GridBagConstraints, ef_Lagerhaltungskosten1, 8, 0, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));
		this.buildConstraints(ef_GridBagConstraints, ef_Periode2, 0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(ef_GridBagConstraints, ef_Bestellmenge2, 2, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	
		this.buildConstraints(ef_GridBagConstraints, ef_Lagermenge2, 4, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(ef_GridBagConstraints, ef_Fehlmenge2, 6, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(ef_GridBagConstraints, ef_Lagerhaltungskosten2, 8, 1, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));

		//Hinzufuegen der Beschriftungskomponenten zum Darstellungspanel
		plo_ErgebnisPanel.add(ef_Periode1);							
		plo_ErgebnisPanel.add(ef_Bestellmenge1);					
		plo_ErgebnisPanel.add(ef_Lagermenge1);						
		plo_ErgebnisPanel.add(ef_Fehlmenge1);						
		plo_ErgebnisPanel.add(ef_Lagerhaltungskosten1);				
		plo_ErgebnisPanel.add(ef_Periode2);							
		plo_ErgebnisPanel.add(ef_Bestellmenge2);					
		plo_ErgebnisPanel.add(ef_Lagermenge2);						
		plo_ErgebnisPanel.add(ef_Fehlmenge2);						
		plo_ErgebnisPanel.add(ef_Lagerhaltungskosten2);				
		int i;														
		for(i = 2; i < anzahlPeriodenErgebnisse; i++)			//Initialisieren des Arrays mit
		{
			ef_erg = new periodenErgebnis();						//neuen Ergebnissen
			ef_PeriodenErgebnisListe[i-2] = new periodenErgebnis();	

			//Erstellen der Constraints fuer die Textfelder der Ergebnisse
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Nummer(), 0, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);			
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Bestellmenge(), 2, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);		
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Lagermenge(), 4, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);		
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Fehlmenge(), 6, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);		
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Lagerhaltungskosten(), 8, i, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);
			
			//Hinzufuegen der Textfelder zum Darstellungspanel
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Nummer());					
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Bestellmenge());			
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Lagermenge());				
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Fehlmenge());				
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Lagerhaltungskosten());
		}

		//Erstellen der Constraints fuer Gesamtkostenbeschriftung
		this.buildConstraints(ef_GridBagConstraints, ef_Gesamtkosten, 2, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, standard);	
		//und Gesamtkostentextfeld
		this.buildConstraints(ef_GridBagConstraints, tf_Gesamtkosten, 4, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, standard);	
		//Hinzufuegen von Gesamtkostenbeschriftung und
		plo_ErgebnisPanel.add(ef_Gesamtkosten);						
		//Textfeld zum Darstellungspanel
		plo_ErgebnisPanel.add(tf_Gesamtkosten);						

		//Initialisieren des Scrollpane mit dem Darstellungspanel
		this.getContentPane().add(ef_ErgebnisScrollPane = new JScrollPane(plo_ErgebnisPanel));	
		this.addInternalFrameListener(ef_Listener);
	}
//*** Ende Konstruktor  ***********************************************************************************************

	
//*** AlternativerKonstruktor  ****************************************************************************************
	public plo_Ergebnisfenster(int anzPerErg, plo_Hauptfenster base, String title, boolean resizable, boolean maximizable, boolean closable, boolean iconifiable, plo_Eingabemaske parent, periodenErgebnis[] ErgebnisListe)
	{
		double sum = 0.0;											//Variable zum Aufsummieren der Lagerkosten
		this.setTitle(title);										//Initialisieren der Parameter des
		this.setSize(600, 387);										//Ergebnisfensters
		this.setMaximizable(maximizable);							
		this.setResizable(resizable);								
		this.setIconifiable(iconifiable);
		this.setClosable(closable);
		this.setRoot(base);
		Insets standard = new Insets(3, 5, 3, 5);					//Erstellen des Standardinsets

		ergebnisVector = new Vector[2];
		ergebnisVector[0] = new Vector();
		ergebnisVector[1] = new Vector();

		anzahlPeriodenErgebnisse = anzPerErg;						//Anzahl der Ergebnisse

		ef_Listener = new plo_Ef_SubwindowListener(this.getRoot(), this);	//Ergebnislistener des Fensters
		ef_IsSaved = false;													//Bool-Variable die bestimmt, ob das Ergebnisfenster bereits gespeichert wurde
		ef_ZugehoerigeEingabemaske = parent;								//Initialisieren des Referenzobjets auf das Modell

		ef_Periode1 = new JLabel("Periode");						//Initialisieren der Beschriftungslabels
		ef_Bestellmenge1 = new JLabel("Bestellmenge");				
		ef_Lagermenge1 = new JLabel("Lagermenge");					
		ef_Fehlmenge1 = new JLabel("Fehlmenge");					
		ef_Lagerhaltungskosten1 = new JLabel("Lagerhaltungs-");		
		ef_Periode2 = new JLabel(" ");								
		ef_Bestellmenge2 = new JLabel(" ");							
		ef_Lagermenge2 = new JLabel(" ");							
		ef_Fehlmenge2= new JLabel(" ");								
		ef_Lagerhaltungskosten2 = new JLabel("kosten");				
		ef_Gesamtkosten = new JLabel("Gesamtkosten");				
		tf_Gesamtkosten = new JTextField("0", 10);					
		ef_GridBagLayout = new GridBagLayout();						//Erstellen des Layouts
		ef_GridBagConstraints = new GridBagConstraints();			//Erstellen der Constraints

		plo_ErgebnisPanel = new JPanel(ef_GridBagLayout);			//Erstellen des Darstellungspanels
		plo_ErgebnisPanel.setLayout(ef_GridBagLayout);				

		//Erstellend der Constraints fuer die Beschriftungskomponenten
		this.buildConstraints(ef_GridBagConstraints, ef_Periode1, 0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(ef_GridBagConstraints, ef_Bestellmenge1, 2, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	
		this.buildConstraints(ef_GridBagConstraints, ef_Lagermenge1, 4, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(ef_GridBagConstraints, ef_Fehlmenge1, 6, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(ef_GridBagConstraints, ef_Lagerhaltungskosten1, 8, 0, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));
		this.buildConstraints(ef_GridBagConstraints, ef_Periode2, 0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(ef_GridBagConstraints, ef_Bestellmenge2, 2, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	//
		this.buildConstraints(ef_GridBagConstraints, ef_Lagermenge2, 4, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		//
		this.buildConstraints(ef_GridBagConstraints, ef_Fehlmenge2, 6, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		//
		this.buildConstraints(ef_GridBagConstraints, ef_Lagerhaltungskosten2, 8, 1, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));

		//Hinzufuegen der Beschriftungskomponenten zum Darstellungspanel
		plo_ErgebnisPanel.add(ef_Periode1);							
		plo_ErgebnisPanel.add(ef_Bestellmenge1);					
		plo_ErgebnisPanel.add(ef_Lagermenge1);						
		plo_ErgebnisPanel.add(ef_Fehlmenge1);						
		plo_ErgebnisPanel.add(ef_Lagerhaltungskosten1);				
		plo_ErgebnisPanel.add(ef_Periode2);							
		plo_ErgebnisPanel.add(ef_Bestellmenge2);					
		plo_ErgebnisPanel.add(ef_Lagermenge2);						
		plo_ErgebnisPanel.add(ef_Fehlmenge2);						
		plo_ErgebnisPanel.add(ef_Lagerhaltungskosten2);				

		periodenErgebnis ef_erg;									
		ef_PeriodenErgebnisListe = new periodenErgebnis[anzahlPeriodenErgebnisse];	//Erstellen des Ergebnis-Arrays

		int i;														
		for(i = 2; i < (anzahlPeriodenErgebnisse+2); i++)			//Initialisieren des Arrays mit
		{
			ef_erg = new periodenErgebnis();						//neuen Ergebnissen
			ef_PeriodenErgebnisListe[i-2] = new periodenErgebnis();	
//			System.out.println(i+".Ergebnis: Nummer: "+ErgebnisListe[i-2].getTf_NummerText()+" Bestellmenge: "+ErgebnisListe[i-2].getTf_BestellmengeText()+" Lagermenge: "+ErgebnisListe[i-2].getTf_LagermengeText()+" Fehlmenge: "+ErgebnisListe[i-2].getTf_FehlmengeText()+" Lagerhaltungskosten: "+ErgebnisListe[i-2].getTf_LagerhaltungskostenText());
			setEf_PeriodenErgebnisListe((i-2), ErgebnisListe[i-2]);
			Double sumTemp = new Double(ErgebnisListe[i-2].getTf_LagerhaltungskostenText());
			sum += sumTemp.doubleValue();
//			System.out.println(i+".Ergebnis: Nummer: "+getEf_PeriodenErgebnisListe(i-2).getTf_NummerText()+" Bestellmenge: "+getEf_PeriodenErgebnisListe(i-2).getTf_BestellmengeText()+" Lagermenge: "+getEf_PeriodenErgebnisListe(i-2).getTf_LagermengeText()+"Lagerhaltungskosten: "+getEf_PeriodenErgebnisListe(i-2).getTf_LagerhaltungskostenText()+" Bestellkosten: "+getEf_PeriodenErgebnisListe(i-2).getTf_LagerhaltungskostenText());

			//Erstellen der Constraints fuer die Textfelder der Ergebnisse
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Nummer(), 0, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);			
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Bestellmenge(), 2, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);		
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Lagermenge(), 4, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);		
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Fehlmenge(), 6, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);		
			this.buildConstraints(ef_GridBagConstraints, getEf_PeriodenErgebnisListe(i-2).getTf_Lagerhaltungskosten(), 8, i, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);
			
			//Hinzufuegen der Textfelder zum Darstellungspanel
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Nummer());					
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Bestellmenge());			
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Lagermenge());				
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Fehlmenge());				
			plo_ErgebnisPanel.add(getEf_PeriodenErgebnisListe(i-2).getTf_Lagerhaltungskosten());	
		}
		this.buildConstraints(ef_GridBagConstraints, ef_Gesamtkosten, 2, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, standard);	//Erstellen der Constraints fuer Gesamtkostenbeschriftung
		this.buildConstraints(ef_GridBagConstraints, tf_Gesamtkosten, 4, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, standard);	//und Gesamtkostentextfeld

		Double sumDouble = new Double(sum);
		tf_Gesamtkosten.setText(sumDouble.toString());
		plo_ErgebnisPanel.add(ef_Gesamtkosten);						//Hinzufuegen von Gesamtkostenbeschriftung und
		plo_ErgebnisPanel.add(tf_Gesamtkosten);						//Textfeld zum Darstellungspanel

		this.getContentPane().add(ef_ErgebnisScrollPane = new JScrollPane(plo_ErgebnisPanel));	//Initialisieren des Scrollpane mit dem Darstellungspanel
		this.addInternalFrameListener(ef_Listener);					//Hinzufuegen des Listeners
	}
//*** Ende alternativer Konstruktor  ******************************************************************************
	
	
//*** Hilfsmethode ***********************************************************************************************
	
	//Hilfsmethode zur besseren Erzeugung von Komponenten in GridbagConstrains
	public void buildConstraints(GridBagConstraints c, Component com, int x, int y, int width, int height, int fill, int ank, int pdx, int pdy, Insets ins)
	{																
		c.gridx = x;												
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		c.fill = fill;
		c.anchor = ank;
		c.ipadx = pdx;
		c.ipady = pdy;
		c.insets = ins;
		ef_GridBagLayout.setConstraints(com, c);
	}
//*** Ende der Hilfsmethode **************************************************************************************

	
//*** SET-Methoden  **********************************************************************************************
	
	//Methode zum Setzen des Gesamtkostentextes
	public void setTf_GesamtkostenText(String elem)					
	{
		tf_Gesamtkosten.setText(elem);
	}

	//Methode zum Setzen der isSaved-Variablen
	public void setEf_IsSaved(boolean elem)							
	{
		ef_IsSaved = elem;
	}

	//Methode zum Setzen des Referenzobjekts auf das Modell
	public void setEf_ZugehoerigeEingabemaske(plo_Eingabemaske elem)	
	{
		ef_ZugehoerigeEingabemaske = elem;
	}

	//Methode zum Setzen des Referenzobjekts auf das Hauptfenster
	public void setRoot(plo_Hauptfenster elem)						
	{
		root = elem;
	}

	//Methode zum Setzen der Ergebnisanzahl
	public void setAnzahlPeriodenErgebnisse(int elem)				
	{
		anzahlPeriodenErgebnisse = elem;
	}

	//Methode zum Setzen eines Elements an eine per Index spezifizierten Stelle des Ergebnis-Arrays
	public void setEf_PeriodenErgebnisListe(int index, periodenErgebnis elem)
	{
		ef_PeriodenErgebnisListe[index] = elem;
	}

	public void setErgebnisVector(Vector[] elem)
	{
		ergebnisVector = elem;
	}
//*** Ende SET-Methoden  *********************************************************************************

	
//*** GET-Methoden  *************************************************************************************
	
	//Methode zur Rueckgabe des Gesamtkostentextes
	public static String getTf_GesamtkostenText()							
	{
		return tf_Gesamtkosten.getText();
	}

	//Methode zur Rueckgabe isSaved-Variablen
	public boolean getEf_IsSaved()									
	{
		return ef_IsSaved;
	}

	//Methode zur Rueckgabe Referenzobjekts auf das Modell
	public static plo_Eingabemaske getEf_ZugehoerigeEingabemaske()			
	{
		return ef_ZugehoerigeEingabemaske;
	}

	//Methode zur Rueckgabe des Referenzobjekts auf das Hauptfenster
	public plo_Hauptfenster getRoot()								
	{
		return root;
	}

	//Methode zur Rueckgabe der Ergebnisanzahl
	public int getAnzahlPeriodenErgebnisse()						
	{
		return anzahlPeriodenErgebnisse;
	}

	//Methode zur Rueckgabe eines Elements an eine per Index spezifizierten Stelle des Ergebnis-Arrays
	public static periodenErgebnis getEf_PeriodenErgebnisListe(int index)	
	{
		return ef_PeriodenErgebnisListe[index];
	}

	public Vector[] getErgebnisVector()
	{
		return ergebnisVector;
	}
//*** Ende GET-Methoden  *****************************************************************************************

	
//*** Speichermethode  *******************************************************************************************
	
	//Methode zum Abspeichern einer errechneten Loesung in einer txt-Datei,deren Name und Pfad aus einem 
	//Dateidialog ermittelt wurden. Erstellen und Intialisieren eines Ausgabestromobjektes
	public void loesungSpeichern(String full, String file)					
	{																
		try															
		{															
			BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream(full));
		}
		catch (FileNotFoundException fnfe)
		{	}

		try
		{
			BufferedWriter w = new BufferedWriter(new FileWriter(file));	//oeffnen des Ausgabestroms
			String buffer = new String("Errechnete Lösung für Modell \""+this.getEf_ZugehoerigeEingabemaske().getTitle()+"\"");
			w.write(buffer, 0, buffer.length());					//Zeilenweises Auslesen der Textefeldinhalte des
			w.newLine();											//Ergebnisfensters
			buffer = new String("");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("Periode      	Bestell-  	Lager-  	Fehlmenge  	Lagerhaltungs-");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("				Menge       	Menge     	kosten");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("-----------------------------------------------------------------------------");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			for(int i = 0; i < anzahlPeriodenErgebnisse;i++)
			{
				buffer = new String(this.getEf_PeriodenErgebnisListe(i).getTf_NummerText()+"\t\t"+
									this.getEf_PeriodenErgebnisListe(i).getTf_BestellmengeText()+"\t\t"+
									this.getEf_PeriodenErgebnisListe(i).getTf_LagermengeText()+"\t\t"+
									this.getEf_PeriodenErgebnisListe(i).getTf_FehlmengeText()+"\t\t"+
									this.getEf_PeriodenErgebnisListe(i).getTf_LagerhaltungskostenText());
				w.write(buffer, 0, buffer.length());
				w.newLine();
			}
			buffer = new String("-----------------------------------------------------------------------------");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("\t\tGesamtkosten:	"+this.getTf_GesamtkostenText());
			w.write(buffer, 0, buffer.length());
			w.newLine();
			w.close();									//Schliessen des Ausgabestroms
		}
		catch (IOException ioe)
		{	}
	}
//*** Ende Speichermethode  *****************************************************************************************
	
	
//*** Druck-Funktion ************************************************************************************************
	public static void modellDruck() {
				JFrame x;
				x = new JFrame();

			    TextPrintable printer = new TextPrintable();

			    printer.addString(getEf_ZugehoerigeEingabemaske().getTitle() + " - Lösung");
			    printer.addString("-----------------------------------------------------------------------------------------------------------");
			    printer.addLeerzeile();
			    printer.addString("Periode          Bestell-          Lager-           	Fehlmenge            	Lagerhaltungs-");
			    printer.addString("                      menge            menge        				                                kosten");
			    printer.addLeerzeile();
			    printer.addLeerzeile();
			    for (int i = 0; i < anzahlPeriodenErgebnisse; i++) {
			    	printer.addString(getEf_PeriodenErgebnisListe(i).getTf_NummerText()+"                    "+ 
			    					  getEf_PeriodenErgebnisListe(i).getTf_BestellmengeText()+"                " + 
			    					  getEf_PeriodenErgebnisListe(i).getTf_LagermengeText() +"                 " + 
			    					  getEf_PeriodenErgebnisListe(i).getTf_FehlmengeText()+ "                          " +
									  getEf_PeriodenErgebnisListe(i).getTf_LagerhaltungskostenText());
			    	printer.addLeerzeile();
				}
			    
			    printer.addLeerzeile();
			    printer.addLeerzeile();
			    printer.addString("-----------------------------------------------------------------------------------------------------------");
			    printer.addLeerzeile();
			    printer.addString("Gesamtkosten:    " + getTf_GesamtkostenText());
			    TextPrintable.addTab();
			    printer.druckeSeite(x, "testeDrucken", false);
		}
//*** Ende der Druck-Funktion **************************************************************************************

}

//*** Ende Klasse plo_Ergebnisfenster **********************************************************************************************************************