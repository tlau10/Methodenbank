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
import java.io.*;
import java.awt.*;
import javax.swing.*;


//*** Klasse plo_Eingabemaske *****************************************************************************************************
public class plo_Eingabemaske extends JInternalFrame{
	private static final long serialVersionUID = 1640625390039498501L;
  //Beschriftungskomponenten fuer die dargestellte Tabelle
	final JLabel em_NachfrageNr1;									
	final JLabel em_Nachfragemenge1;								
	final JLabel em_Nachfrageperiode1;								
	final JLabel em_Bestellkosten1;									
	final JLabel em_Lagerkosten1;									
	final JLabel em_NachfrageNr2;									
	final JLabel em_Nachfragemenge2;								
	final JLabel em_Nachfrageperiode2;								
	final JLabel em_Bestellkosten2;									
	final JLabel em_Lagerkosten2;									

	private plo_Em_SubwindowListener em_Listener;					//Listener fuer Fensterereignisse
	private boolean em_IsSaved;										//Variable, die anzeigt, ob ein Modell schon abgespeichert worden ist
	private boolean em_IsOptimized;									//Variable, die anzeigt, ob ein Modell schon geloest worden ist
	private plo_Ergebnisfenster em_ZugehoerigesErgebnisfenster;		//Referenzvariable des zu einem Modell gehoerenden Ergebnisfensters
	private int anzahlNachfragen;									//Anzahl der Nachfragen des Modells
	private nachfrage[] em_NachfragenListe;							//Array der Nachfragen
	private plo_Hauptfenster root;									//Referenzobjekt auf das Hauptfenster
	private plo_Matrix em_Core;										//LP-Matrix des Modells
	private int fensterIndex;										//Ordnungsindex des Modells unter den geoeffneten Modellen im Hauptfenster

	JPanel plo_EingabePanel;										//Panel, welches den Komponenten zu grunde liegt
	JScrollPane em_EingabeScrollPane;								//Scrollpane um laengere Modelle zu realisieren

	GridBagLayout em_GridBagLayout;									//Layout der Eingabemaske
	GridBagConstraints em_GridBagConstraints;						//Constraints des Layouts

//*** Konstruktor  ***------------------------------------------------
	public plo_Eingabemaske(plo_Hauptfenster base, int anzNach, String title, boolean resizable, boolean maximizable, boolean closable, boolean iconifiable)
	{
	  //Positionierung und Initialisierung des Fensters
		this.setTitle(title);										
		this.setSize(575, 387);										
		this.setMaximizable(maximizable);
		this.setResizable(resizable);
		this.setIconifiable(iconifiable);
		this.setClosable(closable);
		this.setRoot(base);

		Insets standard = new Insets(3, 5, 3, 5);					//Definition eines Standart-Insets

		anzahlNachfragen = anzNach;
		fensterIndex = 0;

		em_Listener = new plo_Em_SubwindowListener(this.getRoot(), this);	//Erstellen des Listeners
		em_IsSaved = false;													//Initialisieren der Boolvariablen
		em_IsOptimized = false;												
		em_ZugehoerigesErgebnisfenster = new plo_Ergebnisfenster(base, title+" - Ergebnis", false, false, true, true, this);	//Erstellen des Ergebnisreferenzobjektes
		em_ZugehoerigesErgebnisfenster.setVisible(true);																		

	  //Initialisieren der Labels
		em_NachfrageNr1 = new JLabel("Nachfrage-");					
		em_Nachfragemenge1 = new JLabel("Nachfrage-");				
		em_Nachfrageperiode1 = new JLabel("Nachfrage-");			
		em_Bestellkosten1 = new JLabel("Bestellkosten");
		em_Lagerkosten1 = new JLabel("Lagerkosten");
		em_NachfrageNr2 = new JLabel("Nr.");
		em_Nachfragemenge2 = new JLabel("menge");
		em_Nachfrageperiode2 = new JLabel("periode");
		em_Bestellkosten2 = new JLabel(" ");
		em_Lagerkosten2 = new JLabel("pro Einheit/Periode");

	  //Erstellen des Layouts
		em_GridBagLayout = new GridBagLayout();						
		em_GridBagConstraints = new GridBagConstraints();			

	  //Erstellen des Panels mit dem Layout
		plo_EingabePanel = new JPanel(em_GridBagLayout);			
		plo_EingabePanel.setLayout(em_GridBagLayout);				

	   //Anordnen der Komponenten im Layout
		this.buildConstraints(em_GridBagConstraints, em_NachfrageNr1, 0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(em_GridBagConstraints, em_Nachfragemenge1, 2, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(em_GridBagConstraints, em_Nachfrageperiode1, 4, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	
		this.buildConstraints(em_GridBagConstraints, em_Bestellkosten1, 6, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(em_GridBagConstraints, em_Lagerkosten1, 8, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			

		this.buildConstraints(em_GridBagConstraints, em_NachfrageNr2, 0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(em_GridBagConstraints, em_Nachfragemenge2, 2, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(em_GridBagConstraints, em_Nachfrageperiode2, 4, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	
		this.buildConstraints(em_GridBagConstraints, em_Bestellkosten2, 6, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(em_GridBagConstraints, em_Lagerkosten2, 8, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			

	   //Hinzufuegen der Komponenten zum Panel
		plo_EingabePanel.add(em_NachfrageNr1);						
		plo_EingabePanel.add(em_Nachfragemenge1);					
		plo_EingabePanel.add(em_Nachfrageperiode1);					
		plo_EingabePanel.add(em_Bestellkosten1);					
		plo_EingabePanel.add(em_Lagerkosten1);						
		plo_EingabePanel.add(em_NachfrageNr2);						
		plo_EingabePanel.add(em_Nachfragemenge2);					
		plo_EingabePanel.add(em_Nachfrageperiode2);					
		plo_EingabePanel.add(em_Bestellkosten2);					
		plo_EingabePanel.add(em_Lagerkosten2);						

	   //Initialisieren der Nachfragenliste
		em_NachfragenListe = new nachfrage[anzahlNachfragen];		
		nachfrage em_nach;											
		Double dTemp = new Double(0);

		int i;
		for(i = 2; i < (anzahlNachfragen+2); i++)					//Hinzufuegen der leeren Nachfragen in der
		{															//Anzahl, die vom Benutzer eingegeben worden ist.
			em_nach = new nachfrage();								
			dTemp = new Double(this.getRoot().getDefaultBestellkosten());
			em_nach.setTf_BestellkostenText(dTemp.toString());
			dTemp = new Double(this.getRoot().getDefaultLagerkosten());
			em_nach.setTf_LagerkostenText(dTemp.toString());
		 	em_nach.setTf_NummerText(String.valueOf(i-1));			//Setzen der Nachfragennummer und Periode aufsteigend
			em_nach.setTf_PeriodeText(String.valueOf(i-1));

			this.setEm_NachfragenListe((i-2), em_nach);

		   //Erstellen der Constraints fuer die Textfelder der jeweiligen Nachfrage
			this.buildConstraints(em_GridBagConstraints, em_nach.getTf_Nummer(), 0, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);							
			this.buildConstraints(em_GridBagConstraints, em_nach.getTf_Menge(), 2, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);								
			this.buildConstraints(em_GridBagConstraints, em_nach.getTf_Periode(), 4, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);							
			this.buildConstraints(em_GridBagConstraints, em_nach.getTf_Bestellkosten(), 6, i, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);						
			this.buildConstraints(em_GridBagConstraints, em_nach.getTf_Lagerkosten(), 8, i, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);	
			
		   //Hinzufuegen der Textfelder zum Darstellungspanel
			plo_EingabePanel.add(em_nach.getTf_Nummer());			
			plo_EingabePanel.add(em_nach.getTf_Menge());			
			plo_EingabePanel.add(em_nach.getTf_Periode());			
			plo_EingabePanel.add(em_nach.getTf_Bestellkosten());	
			plo_EingabePanel.add(em_nach.getTf_Lagerkosten());		
		}


		plo_EingabePanel.setMinimumSize(new Dimension(555, 387));

	   //Hinzufuegen des Scrollpanes mit dem Darstellungspanels
		this.getContentPane().add(em_EingabeScrollPane = new JScrollPane(plo_EingabePanel));	
	   //Hinzufuegen des Listeners
		this.addInternalFrameListener(em_Listener);												
	}
//***** Ende Konstruktor  ********************************************************************************

//***** Alternativer Konstruktor *************************************************************************
	
  //Alternativer Konstruktor, fuer den Fall, dass ein vorhandenes Modell, mit Name und bereits vorhandener
  //Nachfragenliste neu dargestellt werden soll Initialisierung der Fenster-Parameter
	
	public plo_Eingabemaske(plo_Hauptfenster base, int anzNach, String title, boolean resizable, boolean maximizable, boolean closable, boolean iconifiable, nachfrage[] liste)
	{																
		this.setSize(575, 387);										
		this.setMaximizable(maximizable);							
		this.setResizable(resizable);								
		this.setIconifiable(iconifiable);
		this.setClosable(closable);
		this.setRoot(base);

	   //Erstellung eines Standartinsets
		Insets standard = new Insets(3, 5, 3, 5);					

		anzahlNachfragen = anzNach;
		fensterIndex = 0;

	   //Erstellen des Listeners
		em_Listener = new plo_Em_SubwindowListener(this.getRoot(), this);	
		em_IsSaved = false;											//initialisieren der Bool-Variablen
		em_IsOptimized = false;												
       //Initialisieren des Referenzobjekts
		em_ZugehoerigesErgebnisfenster = new plo_Ergebnisfenster(base, title+" - Ergebnis", false, false, true, true, this);	
		em_ZugehoerigesErgebnisfenster.setVisible(true);																		

	   //Initialisieren der Beschriftungslabels
		em_NachfrageNr1 = new JLabel("Nachfrage-");					
		em_Nachfragemenge1 = new JLabel("Nachfrage-");				
		em_Nachfrageperiode1 = new JLabel("Nachfrage-");			
		em_Bestellkosten1 = new JLabel("Bestellkosten");
		em_Lagerkosten1 = new JLabel("Lagerkosten");
		em_NachfrageNr2 = new JLabel("Nr.");
		em_Nachfragemenge2 = new JLabel("menge");
		em_Nachfrageperiode2 = new JLabel("periode");
		em_Bestellkosten2 = new JLabel(" ");
		em_Lagerkosten2 = new JLabel("pro Einheit/Periode");

	   //Initialisieren des Layouts	
		em_GridBagLayout = new GridBagLayout();						
		em_GridBagConstraints = new GridBagConstraints();			

	   //Erstellen des Darstellungspanels mit dem entsprechenden Layout
		plo_EingabePanel = new JPanel(em_GridBagLayout);			
		plo_EingabePanel.setLayout(em_GridBagLayout);				

	   //Erstellen der Constraints fuer die	Darstellungskomponenten
		this.buildConstraints(em_GridBagConstraints, em_NachfrageNr1, 0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(em_GridBagConstraints, em_Nachfragemenge1, 2, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(em_GridBagConstraints, em_Nachfrageperiode1, 4, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	
		this.buildConstraints(em_GridBagConstraints, em_Bestellkosten1, 6, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		
		this.buildConstraints(em_GridBagConstraints, em_Lagerkosten1, 8, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(em_GridBagConstraints, em_NachfrageNr2, 0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			
		this.buildConstraints(em_GridBagConstraints, em_Nachfragemenge2, 2, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		//
		this.buildConstraints(em_GridBagConstraints, em_Nachfrageperiode2, 4, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));	//
		this.buildConstraints(em_GridBagConstraints, em_Bestellkosten2, 6, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));		//
		this.buildConstraints(em_GridBagConstraints, em_Lagerkosten2, 8, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 5, 0, new Insets(0,5,0,5));			//

	   //Hinzufuegen der Komponenten zum Darstellungpanel
		plo_EingabePanel.add(em_NachfrageNr1);						
		plo_EingabePanel.add(em_Nachfragemenge1);					
		plo_EingabePanel.add(em_Nachfrageperiode1);					
		plo_EingabePanel.add(em_Bestellkosten1);
		plo_EingabePanel.add(em_Lagerkosten1);
		plo_EingabePanel.add(em_NachfrageNr2);
		plo_EingabePanel.add(em_Nachfragemenge2);
		plo_EingabePanel.add(em_Nachfrageperiode2);
		plo_EingabePanel.add(em_Bestellkosten2);
		plo_EingabePanel.add(em_Lagerkosten2);

	   //Kopieren des uebergebenen Nachfragen-Arrays in	das Array des Fensters
		em_NachfragenListe = new nachfrage[anzahlNachfragen];		
		for(int j = 0; j < anzahlNachfragen; j++)					
		{															
			em_NachfragenListe[j] = new nachfrage();
			em_NachfragenListe[j] = liste[j];
		}
		Integer intTemp;

		int i;
		for(i = 0; i < (anzahlNachfragen); i++)				//Hinzufuegen der Nachfragen zum Panel
		{													
			intTemp = new Integer(i+1);
			this.getEm_NachfragenListe(i).setTf_NummerText(intTemp.toString());

		  //Erstellen der Constraints fuer die Textfelder der Nachfragen
			this.buildConstraints(em_GridBagConstraints, this.getEm_NachfragenListe(i).getTf_Nummer(), 0, i+2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);							
			this.buildConstraints(em_GridBagConstraints, this.getEm_NachfragenListe(i).getTf_Menge(), 2, i+2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);								
			this.buildConstraints(em_GridBagConstraints, this.getEm_NachfragenListe(i).getTf_Periode(), 4, i+2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);							
			this.buildConstraints(em_GridBagConstraints, this.getEm_NachfragenListe(i).getTf_Bestellkosten(), 6, i+2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);						
			this.buildConstraints(em_GridBagConstraints, this.getEm_NachfragenListe(i).getTf_Lagerkosten(), 8, i+2, GridBagConstraints.NONE, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, standard);	
		
		  //Hinzufuegen der Textfelder zum Darstellungspanel
			plo_EingabePanel.add(this.getEm_NachfragenListe(i).getTf_Nummer());			
			plo_EingabePanel.add(this.getEm_NachfragenListe(i).getTf_Menge());			
			plo_EingabePanel.add(this.getEm_NachfragenListe(i).getTf_Periode());		
			plo_EingabePanel.add(this.getEm_NachfragenListe(i).getTf_Bestellkosten());	
			plo_EingabePanel.add(this.getEm_NachfragenListe(i).getTf_Lagerkosten());	
		}

		plo_EingabePanel.setMinimumSize(new Dimension(555, 387));

	   //Hinzufuegen des ScrollPanes mit dem Darstellungspanel
		this.getContentPane().add(em_EingabeScrollPane = new JScrollPane(plo_EingabePanel));	
	   //Hinzufuegen des Listeners
		this.addInternalFrameListener(em_Listener);												
	}
//***** Ende Alternativer Konstruktor  ***********************************************************************************

  //Hilfsmethode zur Erstellung der Constraints
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
		em_GridBagLayout.setConstraints(com, c);
	}

	
//***** SET-Methoden  ***********************************************************************************
	
   //Methode zum Setzen des boolschen isSaved-Wertes
	public void setEm_IsSaved(boolean elem)							
	{
		em_IsSaved = elem;
	}

   //Methode zum Setzen des boolschen isOptimized-Wertes
	public void setEm_IsOptimized(boolean elem)						
	{
		em_IsOptimized = elem;
	}

   //Methode zum Setzen des Referenzobjektes auf das Ergebnisfenster des Modells
	public void setEm_ZugehoerigesErgebnisfenster(plo_Ergebnisfenster elem)	
	{
		em_ZugehoerigesErgebnisfenster = elem;
	}

   //Methode zum Setzen der Nachfragenanzahl
	public void setAnzahlNachfragen(int elem)						
	{
		anzahlNachfragen = elem;
	}

   //Methode zum Setzen des Referenzobjektes auf das Hauptfenster
	public void setRoot(plo_Hauptfenster elem)						
	{

		root = elem;
	}

   //Methode zum Setzen der Nachfrage in der Nachfragenliste an einem Index
	public void setEm_NachfragenListe(int index, nachfrage elem)	
	{
		em_NachfragenListe[index] = new nachfrage();
		em_NachfragenListe[index] = elem;
	}

   //Methode zum Setzen der LP-Matrix des Modells
	public void setEm_Core(plo_Matrix elem)							
	{
		em_Core = elem;
	}

   //Methode zum Setzen des Fensterindexes
	public void setFensterIndex(int elem)							
	{
		fensterIndex = elem;
	}
//***** Ende SET-Methoden  *************************************************************************

	
//***** GET-Methoden  ******************************************************************************
	
  //Methode zur Rueckgabe des boolschen isSaved-Wertes
	public boolean getEm_IsSaved()									
	{
		return em_IsSaved;
	}

   //Methode zur Rueckgabe des boolschen isOptimized-Wertes
	public boolean getEm_IsOptimized()								
	{
		return em_IsOptimized;
	}

   //Methode zur Rueckgabe des Referenzobjektes auf das Ergebnisfenster des Modells
	public plo_Ergebnisfenster getEm_ZugehoerigesErgebnisfenster()	
	{
		return em_ZugehoerigesErgebnisfenster;
	}

   //Methode zur Rueckgabe der Nachfragenanzahl
	public int getAnzahlNachfragen()								
	{
		return anzahlNachfragen;
	}

   //Methode zur Rueckgabe des Referenzobjektes auf das Hauptfenster
	public plo_Hauptfenster getRoot()								
	{
		return root;
	}

   //Methode zur Rueckgabe der Nachfrage in der Nachfragenliste an einem Index
	public nachfrage getEm_NachfragenListe(int index)				
	{
		return em_NachfragenListe[index];
	}

   //Methode zur Rueckgabe der LP-Matrix des Modells
	public nachfrage[] getEm_GesamteNachfragenListe()
	{
		return em_NachfragenListe;
	}

   //
	public plo_Matrix getEm_Core()									
	{
		return em_Core;
	}

   //Methode zur Rueckgabe des Fensterindexes
	public int getFensterIndex()									
	{
		return fensterIndex;
	}
//***** Ende GET-Methoden  ****************************************************************************
	
	
//***** Methode zum Abspeichern der Daten  ************************************************************
	
   //Methode zum Abspeichern eines Modells als txt-Datei, unter einem durch Dateidialog gewonnenen Pfad und Dateinamen
	public void modellSpeichern(String full, String dateiname)		
	{																
		Integer intBuffer = new Integer(anzahlNachfragen);	
		
		System.out.println(full);
		System.out.println(dateiname);
		
//    //Erstellen des Stromobjektes fuer die Dateiausgabe
//		try															
//		{															
//			BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream(full));
//		}
//		catch (FileNotFoundException fnfe)
//		{	}

		try
		{
			BufferedWriter w = new BufferedWriter(new FileWriter(full));	//oeffnen bzw. Erstellen der ++++++ statt "full" stand "dateiname"
			String buffer = new String("Modell:");						//der entsperchenden Ausgabedatei mittels des
			w.write(buffer, 0, buffer.length());						//Stromobjektes mit anschliessender
			w.newLine();												//zeilenweiser Ausgabe der Modelldaten
			buffer = new String(dateiname);								
			w.write(buffer, 0, buffer.length());						
			w.newLine();
			buffer = new String("");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("Anzahl der Nachfragen:");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String(intBuffer.toString());
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("Nachfrage-      Nachfrage-  	Nachfrage-  	Lagerkosten  	Bestellkosten");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("Nr              Menge       	Periode     	pro Einheit/Periode");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("-----------------------------------------------------------------------------");
			w.write(buffer, 0, buffer.length());
			w.newLine();
		   
		   //Ausgabe der Inhalte der Nachfragentexfelder
			for(int i = 0; i < anzahlNachfragen;i++)				
			{														
				buffer = new String(this.getEm_NachfragenListe(i).getTf_NummerText()+"\t\t"+
									this.getEm_NachfragenListe(i).getTf_MengeText()+"\t\t"+
									this.getEm_NachfragenListe(i).getTf_PeriodeText()+"\t\t"+
									this.getEm_NachfragenListe(i).getTf_BestellkostenText()+"\t\t"+
									this.getEm_NachfragenListe(i).getTf_LagerkostenText());
				w.write(buffer, 0, buffer.length());
				w.newLine();
			}
			buffer = new String("-----------------------------------------------------------------------------");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			w.close();									//Schliessen das Ausgabestroms
		}
		catch (IOException ioe)
		{	}
	}
//***** Ende Methode zum Abspeichern der Daten  *********************************************************************

	
//***** Methode zum Laden einer Textdatei  *************************************************************************
	
   //Methode zum Laden eines Modells aus einer txt-Datei, mittels eines durch einen Dateidialog ermittelten Dateinamens
	public void modellLaden(String dateiname)							
	{																	
	   //Erstellen eines Eingabestromobjektes mit dem eingegebenen Dateinamen
		try															
		{																
			BufferedInputStream bis = new BufferedInputStream (new FileInputStream(this.getTitle()));
		}
		catch (FileNotFoundException fnfe)
		{	}
		try																
		{																
			String modellName = new String();						//Initialisiern von Pufferobjekten
			Integer intPuffer = new Integer("0");					//fuer das Einlesen

		   //oeffnen der Datei mittels des Eingabestroms und zeilenweises auslesen der Daten
			BufferedReader r = new BufferedReader(new FileReader(dateiname));	
			StringBuffer temp = new StringBuffer();						
			String compare = new String("\t");							
			String buffer = new String(r.readLine());
			modellName = new String(r.readLine());
			buffer = new String(r.readLine());
			buffer = new String(r.readLine());
			intPuffer = new Integer(r.readLine());
			buffer = new String(r.readLine());
			buffer = new String(r.readLine());
			buffer = new String(r.readLine());
			buffer = new String(r.readLine());

		   //Zeilenweises Einlesen der Daten in die Textfelder der Nachfragen in der Nachfrageliste des Modells
			for (int i = 0; i < intPuffer.intValue(); i++)				
			{															
				int j = 0;												
				buffer = new String(r.readLine());

				while(buffer.charAt(j) != compare.charAt(0))
				{
					temp.append(buffer.charAt(j));
					j++;
				}
				this.getEm_NachfragenListe(i).setTf_NummerText(temp.toString());
				while(buffer.charAt(j) == compare.charAt(0))
				{
					j++;
				}
				temp = new StringBuffer();

				while(buffer.charAt(j) != compare.charAt(0))
				{
					temp.append(buffer.charAt(j));
					j++;
				}
				this.getEm_NachfragenListe(i).setTf_MengeText(temp.toString());
				while(buffer.charAt(j) == compare.charAt(0))
				{
					j++;
				}
				temp = new StringBuffer();

				while(buffer.charAt(j) != compare.charAt(0))
				{
					temp.append(buffer.charAt(j));
					j++;
				}
				this.getEm_NachfragenListe(i).setTf_PeriodeText(temp.toString());
				while(buffer.charAt(j) == compare.charAt(0))
				{
					j++;
				}
				temp = new StringBuffer();

				while(buffer.charAt(j) != compare.charAt(0))
				{
					temp.append(buffer.charAt(j));
					j++;
				}
				this.getEm_NachfragenListe(i).setTf_BestellkostenText(temp.toString());
				while(buffer.charAt(j) == compare.charAt(0))
				{
					j++;
				}
				temp = new StringBuffer();

				while(j < buffer.length())
				{
					if(buffer.charAt(j) != compare.charAt(0))
					{
						temp.append(buffer.charAt(j));
						j++;
					}
				}
				this.getEm_NachfragenListe(i).setTf_LagerkostenText(temp.toString());

				temp = new StringBuffer();

			}
		}
		catch (IOException ioe)
		{	}
	}
//***** Ende der Methode zum Laden einer Textdatei  *********************************************************************
	

//***** Methode zum Einfuegen einer weiteren Nachfrage  *****************************************************************
   
   //Methode um am Ende des vorhandenen Modells eine neue Nachfrage anzuhaengen
	public void nachfrageEinfuegen()									
	{																
	   //Erstellen eines neuen, um 1 laengeren Nachfrage-Arrays
		nachfrage[] tempListe = new nachfrage[anzahlNachfragen+1];	
		
	   //Initialisiern des neuen Arrays
		for(int i = 0; i < (anzahlNachfragen+1); i++)				
		{															
			tempListe[i] = new nachfrage();							
		}				

	   //Kopieren der alten Nachfragen in das neue Array,so dass die letzte Nachfrage leer bleibt
		for(int i = 0; i < anzahlNachfragen; i++)					
		{															
			tempListe[i] = em_NachfragenListe[i];					
		}															
		
	   //Neu initialisieren der Nachfragenliste des Modells mit einer zusaetzlichen Nachfrage. Danach werden die Nachfragen aus
		em_NachfragenListe = new nachfrage[anzahlNachfragen+1];		
		for(int i = 0; i < anzahlNachfragen; i++)					
		{															//dem PufferArray in das Nachfragarray kopiert
			 this.setEm_NachfragenListe(i, tempListe[i]);			
		}															

		anzahlNachfragen += 1;										//Erhoehung er Anzahl der Nachfragen um 1

	   //Erstellen eines neuen Modells mittels des alternativen Konstruktors
		this.getRoot().initializeNew(this.getLocation().getX(), this.getLocation().getY(), this.getFensterIndex(), this, this.getRoot(), anzahlNachfragen, this.getTitle(), false, false, true, true, tempListe);	
	   //Schliessen des Ergebnisfensters
		this.getEm_ZugehoerigesErgebnisfenster().dispose();			
	   //Schliessen des alten Modells
		this.dispose();												
	}
//***** Ende der Methode zum Einfuegen einer weiteren Nachfrage ***************************************************************

//***** Methode zum Entfernen einer bestimmten Nachfrage **********************************************************************
	
   //Methode zum Entfernen einer bestimmten Nachfrage aus der Liste des Modells
	public void nachfrageEntfernen(int index)						
	{																
	   //Kopieren aller Nachfragen der Liste vom Index+1 auf die Nachfrage an der Stelle Index, beginnend, mit dem Index, der geloescht werden soll
		for(int i = index-1; i < anzahlNachfragen-1; i++)			
		{															
			em_NachfragenListe[i] = em_NachfragenListe[i+1];		
		}															
	   //Loeschen der letzten Nachfrage in der Liste und Verringerung der gesamten Nachfragenanzahl
		em_NachfragenListe[anzahlNachfragen-1] = null;				
		anzahlNachfragen -= 1;										
	   //Erstellen eines neuen Modells mit dem alternativen Konstruktor
		this.getRoot().initializeNew(this.getLocation().getX(), this.getLocation().getY(), this.getFensterIndex(), this, this.getRoot(), anzahlNachfragen, this.getTitle(), false, false, true, true, em_NachfragenListe);	
	   //Schliessen des verbundenen Ergebnisfensters
		this.getEm_ZugehoerigesErgebnisfenster().dispose();			
	   //Schliessen des alten Modells
		this.dispose();												
	}
//***** Ende der Methode zum Entfernen einer bestimmten Nachfrage  *******************************************************************

	
//***** Methoden zum Erzeugen der Matrix ********************************************************************************************

   //Methode zur erstellen des LP-Ansatzes des Modells in der zugehoerigen Matrix
	public void produceMatrix()										
	{																
		int max = 0;
		int spaltenzahl = 0;
		int zeilenzahl = 0;
	   //Indikatorvariable um anzuzeigen, ob ein Matrixstring veraendert wurde
		boolean isSet = false;										
		Integer intTemp;
		Double doubleTemp;
		String buffer = new String("");
	  
	   //Ermittlung der nötigen Periodenzahl durch Gewinnung der, Nachfrage mit der hoechsten Periodenzahl
		for(int i = 0; i < this.getAnzahlNachfragen();i++)			
		{															
			buffer = new String(this.getEm_NachfragenListe(i).getTf_PeriodeText());
			intTemp = new Integer(buffer);
			if(intTemp.intValue() > max)
			{
				max = intTemp.intValue();
			}
		}
		
	   //Ermittlung der Spalten- und Zeilenzahl
		spaltenzahl = ((4 * max)+2);		//Berechnung der Spaltenzahl: 1x Periodenanzahl(fuer die Spalten der Lagerkosten), 1x (fuer die Spalte der Bestellkosten), 1x (fuer dei komplette Winkelhalbierende Permutatuionsmatrix)

		zeilenzahl = ((2 * max) +1);		//Berechnung der Zeilenzahl: 1x max (fuer das System mit den Schaltervariablen der Lagerkosten), 1x max (fuer das System mit den Schaltervariablen der Bestellkosten)und 1 fuer die Zielfunktion.
																	
		this.setEm_Core(new plo_Matrix(zeilenzahl, spaltenzahl, 500, 100));
		this.getEm_Core().setPeriodenAnzahl(max);

		int index = (spaltenzahl - 2);
		this.getEm_Core().setCoreElement(0, index, "");		//Setzen des B-Vektors in der Zielfunktion
		this.getEm_Core().setCoreElement(0, index+1, "");
		
		for(int i = 1; i < zeilenzahl; i++)					//Setzen der noetigen STANDARD-Werte fuer den B-Vektor
		{												
			if((i > 0) && (i <= max))
			{
				this.getEm_Core().setCoreElement(i, index, "=");
				this.getEm_Core().setCoreElement(i, index+1, "0");
			}
			if((i > max) && (i < ((2*max)+1)))					
			{
				this.getEm_Core().setCoreElement(i, index, "<");
				this.getEm_Core().setCoreElement(i, index+1, "0");
			}
		}

		for(int i = max; i < (2*max);i++)					//Setzen der Lagerkostenwerte fuer die zweiten (max)-Spalten der Zielfunktion
		{													//Durchlauf der Matrix in den Spalten max bis (2*max)-1
			isSet = false;
			intTemp = new Integer("0");
			doubleTemp = new Double("0");
			
			for(int j = 0; j < this.getAnzahlNachfragen(); j++)		//Durchlaufen der Nachfragenliste
			{
				doubleTemp = new Double(this.getEm_NachfragenListe(j).getTf_LagerkostenText());
				if(doubleTemp.doubleValue() != this.getRoot().getDefaultLagerkosten())	//Wenn sich die Lagerkosten von den Defaultlagerkosten unterscheiden...
				{
					intTemp = new Integer(this.getEm_NachfragenListe(j).getTf_PeriodeText());
					if((intTemp.intValue()-1) == (i-max))			//und der Laufindex gerade auf die dazugehoerige Periodennummer zeigt
					{												//wird der String in der Matrix an der Stelle i in Zeile 0  auf den neuen Lagerkostenwert gesetzt
						this.getEm_Core().setCoreElement(0, i, this.getEm_NachfragenListe(j).getTf_LagerkostenText());
						System.out.println("We take the first again!");
						isSet = true;
					}
				}
			}
			if(isSet == false)										//Ist ein Matrixstring noch nciht gesetzt worden
			{														//wird der Defaultksotenwert eingesetzt
				doubleTemp = new Double (this.getRoot().getDefaultLagerkosten());
				buffer = new String(doubleTemp.toString());
				this.getEm_Core().setCoreElement(0, i, buffer);
			}
		}

		for(int i = (2*max); i < (3*max); i++)						//Setzen der Bestellkosten fuer die dritten max Spalten der Zielfunktion
		{															//Durchlauf der Matrix in den Spalten 2*max - (3*max)-1
			isSet = false;
			intTemp = new Integer("0");
			doubleTemp = new Double("0");
			
			for(int j = 0; j < this.getAnzahlNachfragen(); j++)		//Durchlaufen der Nachfragenliste
			{
				doubleTemp = new Double(this.getEm_NachfragenListe(j).getTf_BestellkostenText());
//				System.out.println("doubleTemp in matrixProduction: "+doubleTemp.toString());
				if(doubleTemp.doubleValue() != this.getRoot().getDefaultBestellkosten())	//Wenn sich die Lagerkosten von den DefaultBestellkosten unterscheiden...
				{
					buffer = new String(doubleTemp.toString());
					intTemp = new Integer(this.getEm_NachfragenListe(j).getTf_PeriodeText());
					if((intTemp.intValue()-1) == (i-(2*max)))					//und der Laufindex gerade auf die dazugehoerige Periodennummer zeigt
					{															//wird der String in der Matrix an der Stelle i in Zeile 0  auf den neuen Bestellkostenwert gesetzt
						this.getEm_Core().setCoreElement(0, i, buffer/*this.getEm_NachfragenListe(j).getTf_BestellkostenText()*/);
						isSet = true;
					}
				}
			}
			if(isSet == false)										//Ist ein Matrixstring noch nicht gesetzt worden
			{														//wird der Defaultksotenwert eingesetzt
				doubleTemp = new Double (this.getRoot().getDefaultBestellkosten());
				buffer = new String(doubleTemp.toString());
				this.getEm_Core().setCoreElement(0, i, buffer);
			}
		}


	  //Setzen der 2 in der Zielfunktion von 3*max bis 4*max
		for(int i = (3*max); i < (4*max); i++)						
		{
			this.getEm_Core().setCoreElement(0, i, "2");
		}

		int[] geordneteNachfragen = new int[max];							//Ordnungsarray, welches die Stellung der Nachfragen unter
		for(int i = 0; i < max; i++)										//Staeckweiser Periodeninkrementierung wiederspiegelt, d.h. die
		{																	//Stellen im Array der Perioden, zu denen keine Nachfragen existieren
			geordneteNachfragen[i] = 0;										//werden 0 gesetzt
		}

		int[] Periodennummernfolge = new int[this.getAnzahlNachfragen()];	//Array, welches die vorkommenden Periodennummern unter den Nachfragen
		for(int i = 0; i < this.getAnzahlNachfragen(); i++)					//in aufsteigender Folge ordnet. Die Folge dieses Arrays bestimmt auch die Folge
		{																	//des Nachfragennummern- und Mengen-arrays, d.h. Die Nachfrage, welche zuerst nachgefragt
			Periodennummernfolge[i] = 0;									//wird steht mit ihrer Nachfragenummer und der -Menge auch in den beiden folgenden Arrays
		}																	//an erster Stelle

		int[] Nachfragenummernfolge = new int[this.getAnzahlNachfragen()];	//Array der Nachfragennummern, geordnet nach den zugehoerigen Nachfrageperioden der Nachfrage
		for(int i = 0; i < this.getAnzahlNachfragen(); i++)
		{
			Nachfragenummernfolge[i] = 0;
		}

		int[] Mengenfolge = new int[this.getAnzahlNachfragen()];			//Array der Nachfragemengen, geordnet nach den zugehoerigen NachfragePerioden der Nachfrage
		for(int i = 0; i < this.getAnzahlNachfragen(); i++)
		{
			Mengenfolge[i] = 0;
		}

		int orderBufferPeriode = 0;
		int orderBufferPeriode2 = 0;
		int orderBufferNummer = 0;
		int orderBufferMenge = 0;
		int minPeriode = 999;
		int minNr = 0;
		int minMenge = 0;
		int nextVal = 1;
		int nextNach = 0;
		int arrayCounter = 0;
		Integer intTemp2 = new Integer("0");
		Integer intTemp3 = new Integer("0");

		for(int i = 0; i < this.getAnzahlNachfragen(); i++)			//Ermittlung der kleinsten Periodenzahl in den Nachfragen
		{
			intTemp = new Integer(this.getEm_NachfragenListe(i).getTf_PeriodeText());
			if(intTemp.intValue() < minPeriode)
			{
				minPeriode = intTemp.intValue();
				intTemp2 = new Integer(this.getEm_NachfragenListe(i).getTf_NummerText());
				minNr = intTemp2.intValue();
				intTemp3 = new Integer(this.getEm_NachfragenListe(i).getTf_MengeText());
				minMenge = intTemp3.intValue();
			}
		}

		Periodennummernfolge[0] = minPeriode;
		Nachfragenummernfolge[0] = minNr;
		Mengenfolge[0] = minMenge;

		orderBufferPeriode = 999;
		orderBufferPeriode2 = minPeriode;

		for(int i = 1; i < this.getAnzahlNachfragen(); i++)			//Ermittlung der restlichen Ordnung unter den Nachfragen
		{
			for(int j = 0; j < this.getAnzahlNachfragen(); j++)
			{
				intTemp = new Integer(this.getEm_NachfragenListe(j).getTf_PeriodeText());
				if((intTemp.intValue() + minPeriode) < (orderBufferPeriode + minPeriode))
				{
					if(intTemp.intValue() > orderBufferPeriode2)
					{
						orderBufferPeriode = intTemp.intValue();
						intTemp2 = new Integer(this.getEm_NachfragenListe(j).getTf_NummerText());
						orderBufferNummer = intTemp2.intValue();
						intTemp3 = new Integer(this.getEm_NachfragenListe(j).getTf_MengeText());
						orderBufferMenge = intTemp3.intValue();
					}
				}
			}
			Periodennummernfolge[i] = orderBufferPeriode;			//Nach jedem Durchlauf, bei dem das naechstgroessere Minimum gefunden wurde
			orderBufferPeriode2 = orderBufferPeriode;				//werden dessen zugehoerige Menge, Periode und Nummer in die Arrays geschrieben
			orderBufferPeriode = 999;
			Nachfragenummernfolge[i] = orderBufferNummer;
			Mengenfolge[i] = orderBufferMenge;
		}

		nextVal = 1;
		nextNach = Periodennummernfolge[0];
		arrayCounter = 0;

		for(int i = 0; (i < max) && (arrayCounter < this.getAnzahlNachfragen()); i++)	//Auffuellen der Stellen im Gesamtperiodenarray, in denen keine Nachfrage statt findet mit 0
		{																				
			if(nextNach == nextVal)
			{
				geordneteNachfragen[i] = Nachfragenummernfolge[arrayCounter];
				arrayCounter++;
				if(arrayCounter < this.getAnzahlNachfragen())
				{
					nextNach = Periodennummernfolge[arrayCounter];
				}
			}
			else if(nextNach > nextVal)
			{
				geordneteNachfragen[i] = 0;
			}
			nextVal++;
		}

		int kumulierungsPuffer = 0;
		int temp = 0;

		for(int i = 1; i <= max; i++)						//Einfuegen der kummulierten Mengen in den B-Vektor der Zeilen 1 bis Periodenanzahl
		{
			temp = geordneteNachfragen[i-1];
			if(temp == 0)
			{
			}
			else
			{
				for(int j = 0; j < this.getAnzahlNachfragen();j++)
				{
					if((i == Periodennummernfolge[j]) && (temp == Nachfragenummernfolge[j]))
					{
						kumulierungsPuffer += Mengenfolge[j];
					}
				}
			}
			intTemp = new Integer(kumulierungsPuffer);
			this.getEm_Core().setCoreElement(i, (spaltenzahl-1), intTemp.toString());
		}

		produceDiagonal(false, this.getEm_Core(), max,		((2*max)-1), 	1, 			max, 		1, 		"-1");			//-1 Diagonale in den Spalten der Lagerkosten und den ersten max Zeilen
		produceDiagonal(false, this.getEm_Core(), 0, 		(max-1), 		1, 			max, 		max, 	"1");			//Erstellung der ersten Permutation, in welcher alle Elemente unterhalb der Diagonalen 1 sind in den ersten max Zeilen und 1-max Spalten der Permutationen

		produceDiagonal(false, this.getEm_Core(), 0,		(max-1), 		max+1, 		(2*max), 	1, 		"1");			//Erstellen der 1er-Diagonale zur Kopplung der Bestellkosten-Restriktionen
		produceDiagonal(false, this.getEm_Core(), (3*max),	(4*max-1), 		1, 			max, 		1, 		"1");			//Erstellen der 1er-Diagonale unter den 2er-Variablen der Zielfunktion

		produceDiagonal(false, this.getEm_Core(), (2*max),	((3*max)-1), 	(max+1), 	(2*max), 	1, 		"-100000");		//-100000 Diagonale in den Spalten der Bestellkosten und den Zeilen max+1 bis 2*max und 2*max+1 bis 3*max

	  //Ausgabe der Matrix zu Kontrollzwecken
		try
		{
			BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream(this.getRoot().getArbeitsverzeichnis()));
		}
		catch (FileNotFoundException fnfe)
		{	}

		try
		{
			String outLine = new String("");
			StringBuffer outBuffer = new StringBuffer("");
			BufferedWriter w = new BufferedWriter(new FileWriter("matrixlog.txt"));

			for(int i = 0; i < zeilenzahl; i++)
			{
				outBuffer = new StringBuffer("");
				for(int j = 0; j < spaltenzahl; j++)
				{
					outBuffer.append(this.getEm_Core().getCoreElement(i, j));
					outBuffer.append(" ");
				}
				outLine = new String(outBuffer);
				w.write(outLine, 0, outBuffer.length());
				w.newLine();
			}

			w.close();
		}
		catch (IOException ioe)
		{	}
	}
//***** Ende der Methoden zum Erzeugen der Matrix ***************************************************************

	
//***** Methode zur Erzeugung und Fuellung einer Matrixdiagonalen variabler Hoehe *******************************
void produceDiagonal(boolean print, plo_Matrix workOn, int leftBound, int rightBound, int upperBound, int lowerBound, int height, String fill)
{
	int relativI = 0;
	int relativJ = 0;

	for(int i = upperBound; i < lowerBound+1; i++)
	{
		StringBuffer temp = new StringBuffer("");
		relativJ = 0;
		for(int j = leftBound; j < rightBound+1; j++)
		{
			if(print == true)
			{
				temp.append("("+i+"/"+j+")("+relativI+"/"+relativJ+"),");
			}
			for(int heightCount = 0; heightCount < height; heightCount++)
			{
				if(print == true)
				{
					temp.append("["+i+"-"+heightCount+","+(i+heightCount)+"-"+lowerBound+"]");
				}
				if(((i + heightCount) <= lowerBound) && (relativI == relativJ))
				{
					if(print == true)
					{
						System.out.println("Signal1");
					}
					workOn.setCoreElement((i+heightCount), j, fill);
				}
			}
			relativJ++;
		}
		relativI++;
		if(print == true)
		{
			System.out.println(temp.toString());
		}
	}
}
//***** Ende der Methode zur Erzeugung und Fuellung einer Matrixdiagonalen variabler Hoehe*************************


//***** Methode zu Umwandlung der Matrix *************************************************************************
  //Methode um die Matrix in ein String-Array zu ueberfuehren
	public String[] matrixToLpModell()					
	{
		StringBuffer tempBuffer = new StringBuffer("");
		String temp = new String("");
		String[] lpModell = new String[this.getEm_Core().getAnzahlZeilen()];

		for(int i = 0; i < this.getEm_Core().getAnzahlZeilen(); i++)
		{
			tempBuffer = new StringBuffer("");
			for(int j = 0; j < this.getEm_Core().getAnzahlSpalten(); j++)
			{
				if(j < (this.getEm_Core().getAnzahlSpalten()-2))
				{
					temp = new String("x"+(j+1));
					tempBuffer.append(this.getEm_Core().getCoreElement(i, j));
					tempBuffer.append(temp);
					if(j < (this.getEm_Core().getAnzahlSpalten()-3))
					{
						tempBuffer.append(" + ");
					}
				}
				else
				{
					tempBuffer.append(" ");
					tempBuffer.append(this.getEm_Core().getCoreElement(i, j));
				}
			}
			lpModell[i] = new String(tempBuffer.toString());
		}


		try
		{
			BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream(this.getRoot().getArbeitsverzeichnis()));
		}
		catch (FileNotFoundException fnfe)
		{	}

		try
		{
			String outLine = new String("");
			StringBuffer outBuffer = new StringBuffer("");
			BufferedWriter w = new BufferedWriter(new FileWriter("lpModell.txt"));

			for(int i = 0; i < this.getEm_Core().getAnzahlZeilen(); i++)
			{
				w.write(lpModell[i]);
				w.newLine();
			}

			w.close();
		}
		catch (IOException ioe)
		{	}


		return lpModell;
	}
//***** Ende der Methode zu Umwandlung der Matrix ******************************************************

}

//***** Ende Klasse plo_Eingabemaske *******************************************************************