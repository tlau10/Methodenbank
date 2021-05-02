//*******************************************************************
//*																	*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthï¿½lt die Klasse fï¿½r den Datentyp, welcher	    *
//*		die Nachfrqagen eines Modells verwaltet		  				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*     Version 1.2 												*
//*     von Marco Weiï¿½ und Jenne Justin								*
//*																	*
//*******************************************************************

package plo_System;
import java.io.*;
import java.io.FileWriter;
import java.awt.*;
import java.beans.PropertyVetoException;

import javax.swing.*;
import java.util.*;
import SolverCaller.*;


//*** Klasse plo_Hauptfenster ************************************************************************************************************************
public class plo_Hauptfenster extends JFrame{
	
	private static final long serialVersionUID = 2232057737828909617L;
	private plo_MenuBar plo_HauptFensterMenue;						//Menuebalken des Hauptfensters
	public plo_HauptFensterWindowListener plo_MainListener;			//Listener des Menues
	public plo_Hauptfenster baseWindow;								

	private int anzahlEingabemasken;								//Anzahl der vorhandenen Eingabemaskenfenster
	private int anzahlErgebnisfenster;								//Anzahl der vorhandenen Ergebnisfenster
	private int anzahlFenster;										//Anzahl der Fenster gesamt
	private int anzahlNachfragen;									//Anzahl Nachfragen die im naechsten zu erstellenden Modell verwendet werden soll
	private String sPfad;											//Pfad des Solvers
	private String sName;											//Dateiname des Solvers
	private String arbeitsverzeichnis;								//Arbeitsverzeichnis, in welchem der Solver Arbeiten soll

	private int neueX;												//XKoordinate fuer ein neues Fenster
	private int neueY;												//YKoordinate fuer ein neues Fenster

	private double defaultBestellkosten;							//Defaultwert fuer die Bestellkosten
	private double defaultLagerkosten;								//Defaultwert fuer die Lagerkosten

	private SolverDriver solverDriver;								//DriverObjekt fuer den Solveraufruf.

	public JDesktopPane desk;										//Desktop-Oberflaeche innerhalb des Hauptfensters

	private plo_Eingabemaske[] eingabemaskenListe;					//Array der intern vorhandenen Eingabemasken
	private plo_Ergebnisfenster[] ergebnisfensterListe;				//Array der intern vorhandenen Ergebnisfenster

	private plo_Eingabemaske internalFocus;							//Eingabemaske, die zuletzt aktiviert wurde
	private FileDialog dialog;										//Dateidialog-Objekt

//*** Konstruktor  ***------------------------------------------------
	public plo_Hauptfenster(String title)
	{
		this.setTitle(title);										//Festlegen der Grundpameter des Hauptfensters
		this.setSize(700, 600);										
		this.setVisible(true);										

		plo_HauptFensterMenue = new plo_MenuBar(this);				//Initialisieren des Menues
		plo_MainListener = new plo_HauptFensterWindowListener();	//Initialisieren des MenueListeners
		baseWindow = this;											

		this.setJMenuBar(plo_HauptFensterMenue);					//Einbinden des Menues
		this.addWindowListener(plo_MainListener);					//Einbinden des Menuelisteners

		anzahlEingabemasken = 0;									//Initialisieren der Variablen
		anzahlErgebnisfenster = 0;									
		anzahlFenster = 0;											
		neueX = 0;													
		neueY = 0;												
		defaultBestellkosten = 20;									
		defaultLagerkosten = (0.1);								
		arbeitsverzeichnis = new String("");						
		sName = new String("");										
		sPfad = new String("");										

		eingabemaskenListe = new plo_Eingabemaske[9];				//Eingabe auf 9 Modelle gleichzeitig beschraenkt
		ergebnisfensterListe = new plo_Ergebnisfenster[9];			

		this.solverIniEinlesen();									//Einlesen der Standart-Solverdaten aus der Datei "solverini.txt"

		desk = new JDesktopPane();									//Initialisieren der Desktopoberflaeche
		this.getContentPane().add(desk);							//Hinzufuegen der Desktopoberflaeche

	}
//*** Ende Konstruktor  *************************************************************************************

	
//*** SET-Methoden ******************************************************************************************
	
	//Methode zum Setzen der Anzahl der Eingabemasken
	public void setAnzahlEingabemasken(int elem)					
	{
		anzahlEingabemasken = elem;
	}

	//Methode zum Setzen der Anzahl der Ergebnisfenster
	public void setAnzahlErgebnisfenster(int elem)					
	{
		anzahlErgebnisfenster = elem;
	}

	//Methode zum Setzen der Anzahl der gesamt vorhandenen Fenster
	public void setAnzahlFenster(int elem)							
	{
		anzahlFenster = elem;
	}

	//Methode zum Setzen der Puffergroesse anzahlNachfragen
	public void setAnzahlNachfragen(int elem)						
	{
		anzahlNachfragen = elem;
	}

	//Methode zum Setzen der XKoordinate fuer neue Fenster
	public void setNeueX(int elem)									
	{
		neueX = elem;
	}

	//Methode zum Setzen der YKoordinate fuer neue Fenster
	public void setNeueY(int elem)									
	{
		neueY = elem;
	}

	//Methode zum Setzen der Default-Bestellkosten
	public void setDefaultBestellkosten(double elem)				
	{
		defaultBestellkosten = elem;
	}

	//Methode zum Setzen der Default-Lagerkosten
	public void setDefaultLagerkosten(double elem)					
	{
		defaultLagerkosten = elem;
	}

	//Methode zum Setzen einer Eingabemaske an der durch einen Index bezeichneten Stelle im Eingabemasken-Array
	public void setEingabemaskenListe(int index, plo_Eingabemaske elem)	
	{
		elem.setFensterIndex(index);
		eingabemaskenListe[index] = new plo_Eingabemaske(this, 0, " ", false, false, true, true);
		eingabemaskenListe[index] = elem;
		this.setErgebnisfensterListe(index, elem.getEm_ZugehoerigesErgebnisfenster(), elem);
	}

	//Methode zum Setzen eines Ergebnisfensters an der durch einen Index bezeichneten Stelle im Ergebnisfenster-Array
	public void setErgebnisfensterListe(int index, plo_Ergebnisfenster elem, plo_Eingabemaske parent)	
	{
		ergebnisfensterListe[index] = new plo_Ergebnisfenster(this, " ", false, false, true, true, parent);
		ergebnisfensterListe[index] = elem;	
	}

	//Methode zum Setzen einer Eingabemaske in den internen Fokus
	public void setInternalFocus(plo_Eingabemaske elem)				
	{
		internalFocus = elem;
	}

	//Methode zum Setzen des Dateidialogs
	public void setDialog(FileDialog elem)							
	{
		dialog = elem;
	}

	//Methode zum Setzen des Solverpfads
	public void setSolverPfad(String elem)							
	{
		sPfad = elem;
	}

	//Methode zum Setzen des Arbeitsverzeichnisses
	public void setArbeitsverzeichnis(String elem)					
	{
		arbeitsverzeichnis = elem;
	}

	//Methode zum Setzen des Solvernamens
	public void setSolverName(String elem)							
	{
		sName = elem;
	}

	public void setSolverDriver(SolverDriver elem)
	{
		solverDriver = elem;
	}
//*** Ende SET-Methoden ***************************************************************************************

//*** GET-Methoden *******************************************************************************************
	
	//Methode zur Rueckgabe der Anzahl der Eingabemasken
	public int getAnzahlEingabemasken()								
	{
		return anzahlEingabemasken;
	}

	//Methode zur Rueckgabe der Anzahl der Ergebnisfenster
	public int getAnzahlErgebnisfenster()							
	{
		return anzahlErgebnisfenster;
	}

	//Methode zur Rueckgabe der Anzahl der Fenster im gesamten System
	public int getAnzahlFenster()									
	{
		return anzahlFenster;
	}

	//Methode zur Rueckgabe der Puffergroesse
	public int getAnzahlNachfragen()								
	{
		return anzahlNachfragen;
	}

	//Methode zur Rueckgabe der XKoordinate fuer neue Fenster
	public int getNeueX()											
	{
		return neueX;
	}

	//Methode zur Rueckgabe der YKoordinate fuer neue Fenster
	public int getNeueY()											
	{
		return neueY;
	}

	//Methode zur Rueckgabe der Defaultbestellkosten
	public double getDefaultBestellkosten()							
	{
		return defaultBestellkosten;
	}

	//Methode zur Rueckgabe der Defaultlagerkosten
	public double getDefaultLagerkosten()							
	{
		return defaultLagerkosten;
	}

	//Methode zur Rueckgabe einer Eingabemaske an der durch einen Index bezeichneten Stelle im Eingabemasken-Array
	public plo_Eingabemaske getEingabemaskenListe(int index)		
	{
		return eingabemaskenListe[index];
	}

	//Methode zur Rueckgabe eines Ergebnisfensters an der durch einen Index bezeichneten Stelle im Ergebnisfenster-Array
	public plo_Ergebnisfenster getErgebnisfensterListe(int index)	
	{
		return ergebnisfensterListe[index];
	}

	//Methode zur Rueckgabe der Eingabemaske im internen Fokus
	public plo_Eingabemaske getInternalFocus()						
	{
		return internalFocus;
	}

	//Methode zur Rueckgabe des Hauptfenstermenues
	public plo_MenuBar getPlo_HauptFensterMenue()					
	{
		return plo_HauptFensterMenue;
	}

	//Methode zur Rueckgabe des Dateidialogs
	public FileDialog getDialog()									
	{
		return dialog;
	}

	//Methode zur Rueckgabe des Solverpfades
	public String getSolverPfad()									
	{
		return sPfad;
	}

	//Methode zur Rueckgabe des Arbeitsverzeichnisses
	public String getArbeitsverzeichnis()							
	{
		return arbeitsverzeichnis;
	}

	//Methode zur Rueckgabe des Solvernamens
	public String getSolverName()									
	{
		return sName;
	}

	public SolverDriver getSolverDriver()
	{
		return solverDriver;
	}
//*** Ende GET-Methoden ****************************************************************************************

	
//*** Ereignis-Methoden ****************************************************************************************
	
	//Methode zum Start des Dialogs fuer die Erstellung eines neuen Modells
	public void neuesModellDialog()									
	{																
		plo_AnzahlNachfragenDialog dialog = new plo_AnzahlNachfragenDialog(this, 0, "");	//Der Eingabedialog zur Ermittlung der Anzahl der zu erwartenden Nachfragen wird gestartet
		this.setAnzahlNachfragen(dialog.getInt_AnzahlNachfragen());	//Das Ergebnis des Dialogs wird ausgelesen und in den Puffer geladen
	}

	public void neuesModell(int elem, int mode, String name)		//Methode zur Erstellung eines neune Modells
	{																
		String modellname = new String("Modell"+(this.getAnzahlEingabemasken()+1)); //Generierung des Defaultnamens fuer das Modell
		if(mode == 1)												//Sollte die Methode aufgerufen werden, um ein vorhandenes Modell neu anzuzeigen
		{															
			modellname = new String(name);							//Initialisierung des Modellnamens mit dem alten Namen
		}

		//Im Eingabemasken-Array wird ein neues Objekt erzeugt
		eingabemaskenListe[this.getAnzahlEingabemasken()] = new plo_Eingabemaske(this, 0, " ", false, false, true, true);					
		this.setEingabemaskenListe(this.getAnzahlEingabemasken(), new plo_Eingabemaske(this, elem, modellname, false, false, true, true));	
		if(mode == 1)
		{
			Point point = new Point();
			point = this.getInternalFocus().getLocation();
			Integer tempX = new Integer((int)point.getX());
			Integer tempY = new Integer((int)point.getY());
			this.setNeueX(tempX.intValue());
			this.setNeueY(tempY.intValue());
		}
		//Auch ein neues Ereignisfenster wird erzeugt
		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());							
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());							
		if(this.getNeueX() > 100)									//Neu Kalibrierung der Koordinaten fuer neue Fenster
		{															
			this.setNeueX(0);										
		}															
		else														
		{															
			this.setNeueX(this.getNeueX()+40);						
		}															
		this.setNeueY(this.getNeueY()+40);							
		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setVisible(true);			//Sichtbar machen der Eingabemaske
		this.setInternalFocus(this.getEingabemaskenListe(this.getAnzahlEingabemasken())); 	//Neue Maske erhaelt automatisch den Fokus
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setVisible(false);		//Verstecken des noch leeren Ergebnisfensters
		this.desk.add(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));			//Hinzufuegen der neuen Fenster zum
		this.desk.moveToFront(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));	//Nach vorne bringen des Eingabefensters
		this.desk.add(this.getErgebnisfensterListe(this.getAnzahlEingabemasken()));			//Desktop des Hauptfensters

		this.getPlo_HauptFensterMenue().getMi_ModellSpeichern().setEnabled(true);			//Anpassen der Menueoptionen
//		this.getPlo_HauptFensterMenue().getMi_ModellDrucken().setEnabled(true);				
		this.getPlo_HauptFensterMenue().getMi_NachfrageEinfuegen().setEnabled(true);		
		if((this.getEingabemaskenListe(this.getAnzahlEingabemasken()).getAnzahlNachfragen()) > 1)	
			{																				
			this.getPlo_HauptFensterMenue().getMi_NachfrageEntfernen().setEnabled(true);	
		}																				
		this.getPlo_HauptFensterMenue().getMi_AllesAendern().setEnabled(true);			
		this.getPlo_HauptFensterMenue().getMi_OptimaleLoesung().setEnabled(true);		

		this.setAnzahlEingabemasken(this.getAnzahlEingabemasken()+1);						//Erhoehen der Fensterzaehlvariablen
		this.setAnzahlErgebnisfenster(this.getAnzahlErgebnisfenster()+1);				
	}

	
//*** Methode zum Einlesen ****************************************************************************************
	public void modellLaden()										
	{																
		dialog = new FileDialog(this, "Lagermodell auswählen:", FileDialog.LOAD);	//oeffnen des Ladedialogs zur Gewinnung
		dialog.setVisible(true);													//der Pfade und Dateinamen
		String file = new String(dialog.getFile());									//Auslesen der Pfade und
		String dir = new String(dialog.getDirectory());								//Dateinamen
		String ges = new String(dir+file);							
		String modellName = new String();							
		Integer intPuffer = new Integer("0");						

		try															//Definiern des Eingabestroms zur zu ladenden Datei
		{															
			BufferedInputStream bis = new BufferedInputStream (new FileInputStream(ges));
		}
		catch (FileNotFoundException fnfe)
		{	}
		try
		{
			BufferedReader r = new BufferedReader(new java.io.FileReader(ges));	//oeffnen des Stroms
			StringBuffer temp = new StringBuffer();					//Einlesen der ersten paar
			String compare = new String("\t");						//Zeilen zur Gewinnung des Modellnamens
			String transfer = new String();							//und der Anzahl der Nachfragen
			String buffer = new String(r.readLine());				
			modellName = new String(r.readLine());				
			buffer = new String(r.readLine());					
			buffer = new String(r.readLine());					
			intPuffer = new Integer(r.readLine());				
		}
		catch (IOException ioe)
		{	}
//*** Ende Methode einlesen  ****************************************************************************************
		
		
//*** Erstellen des neuen Modells  ****************************************************************************************
		eingabemaskenListe[this.getAnzahlEingabemasken()] = new plo_Eingabemaske(this, 0, " ", false, false, true, true);									
		this.setEingabemaskenListe(this.getAnzahlEingabemasken(), new plo_Eingabemaske(this, intPuffer.intValue(), modellName, false, false, true, true));
		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());											
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());											
		if(this.getNeueX() > 100)									//Anpassen der neuen Koordinaten
		{															
			this.setNeueX(0);										
		}															
		else													
		{															
			this.setNeueX(this.getNeueX()+40);						
		}															
		this.setNeueY(this.getNeueY()+40);							

		this.setNeueY(this.getNeueY()+40);

		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).modellLaden(ges);			//Starten der Lademethode

		this.getPlo_HauptFensterMenue().getMi_ModellSpeichern().setEnabled(true);			//Anpassen des Menues
//		this.getPlo_HauptFensterMenue().getMi_ModellDrucken().setEnabled(true);
		this.getPlo_HauptFensterMenue().getMi_NachfrageEinfuegen().setEnabled(true);	
		if((this.getEingabemaskenListe(this.getAnzahlEingabemasken()).getAnzahlNachfragen()) > 1)	
		{																							
			this.getPlo_HauptFensterMenue().getMi_NachfrageEntfernen().setEnabled(true);				
		}																							
		this.getPlo_HauptFensterMenue().getMi_AllesAendern().setEnabled(true);				
		this.getPlo_HauptFensterMenue().getMi_OptimaleLoesung().setEnabled(true);				

		this.desk.add(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));			//Hinzufuegen des neuen Modells zum Desktop
		this.desk.moveToFront(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));	//Nach vorne bringen des Eingabefensters
		this.desk.add(this.getErgebnisfensterListe(this.getAnzahlEingabemasken()));			//des Hauptfensters

		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setVisible(true);			
		
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setVisible(false);		

		this.setInternalFocus(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));	

		this.setAnzahlEingabemasken(this.getAnzahlEingabemasken()+1);						//Anpassen der Zaehlvariablen
		this.setAnzahlErgebnisfenster(this.getAnzahlErgebnisfenster()+1);					
	}
//*** Ende neues Modell  ****************************************************************************************

//*** Modell drucken  *******************************************************************************************
	public void modellDrucken()										
	{																

	}
// *** Ende modell Drucken ****************************************************************************************
	
// *** Modell Speichern  ****************************************************************************************	
		//Methode zum Speichern eines Modells in eine txt-Datei
	public void modellSpeichern()									
	{																
		dialog = new FileDialog(this, "Lagermodell auswählen:", FileDialog.SAVE);	//Starten des Dateidialogs zur Gewinnung von Pfaden und Dateinamen
		dialog.setVisible(true);									
		String file = new String(dialog.getFile());					//Gewinnen der Pfade
		String dir = new String(dialog.getDirectory());				//und Dateinamen
		String ges = new String(dir+file);							

		this.getInternalFocus().modellSpeichern(ges, file);			//Aufrufen der Speichermethode 
		this.getInternalFocus().setTitle(file);						//Aendern des Modellnamen in den Ausgewaehlten Speichernamen
	}
//*** Ende modell Speichern ****************************************************************************************
	
//***weitere Nachfrage einfügen ****************************************************************************************
	//Methode um in ein bestehendes Modell eine weitere Nachfrage einzufuegen
	public void nachfrageEinfuegen()								
	{						
		/**
		 * edited by roland und sonja
		 * bestell und lagerkosten abgreifen
		 */
		double bestellkosten = this.defaultBestellkosten;
		double lagerkosten = this.defaultLagerkosten;
		this.getInternalFocus().nachfrageEinfuegen(bestellkosten, lagerkosten);			//Aufruf der Einfuegemethode
	}
// ***Ende Nachfrage einfügen ****************************************************************************************
	
	
// ***Nachfrage löschen ****************************************************************************************	
	//Methode um aus einem bestehenden Modell eine Nachfrage zu loeschen
	public void nachfrageEntfernen()								
	{																
		plo_NachfrageEntfernenDialog dialog = new plo_NachfrageEntfernenDialog(this);	//Aufrufen des Dialogs zur Gewinnung der zu loeschenden Nachfragenummer
	}
// ***Ende Nachfrage löschen  ****************************************************************************************
	

//*** Methode um ein neues Fenster mit bestehendem Titel und einer bestehenden Nachfrageliste zu initialisieren
	public void initializeNew(double x, double y, int nummer, plo_Eingabemaske itself, plo_Hauptfenster root, int anzNach, String title, boolean resizable, boolean maximizable, boolean closable, boolean iconifiable, nachfrage[] liste)
	{																
		itself = new plo_Eingabemaske(root, anzNach, title, resizable, maximizable, closable, iconifiable, liste);	//Erstellen des neuen Modells
		itself.setVisible(true);									//Initialisieren der Fenster-Parameter
		itself.setLocation((int)x, (int)y);							
		itself.getEm_ZugehoerigesErgebnisfenster().setLocation((int)x, (int)y);	
		itself.getEm_ZugehoerigesErgebnisfenster().setVisible(false);

		this.setEingabemaskenListe(nummer, itself);					
		this.setErgebnisfensterListe(nummer, itself.getEm_ZugehoerigesErgebnisfenster(), itself);	
		this.desk.add(this.getEingabemaskenListe(nummer));			//Hinzufuegen des Fensters zum Desktop
		this.desk.moveToFront(this.getEingabemaskenListe(nummer));	//Nach vorne bringenen des Eingabefensters
		this.desk.add(this.getErgebnisfensterListe(nummer));		
	}
//***ende ****************************************************************************************
	
//*** Methode um das einlesen eines Modells neu zu starten ****************************************************************************************
	public void allesAendern()										
	{																
		plo_Eingabemaske ref= new plo_Eingabemaske(this, 0, "temp", false, false, true, true);
		ref = this.getInternalFocus();
		this.getInternalFocus().getEm_ZugehoerigesErgebnisfenster().dispose();										//Altes Ergebnisfenster schliessen
		plo_AnzahlNachfragenDialog dialog = new plo_AnzahlNachfragenDialog(this, 1, this.getInternalFocus().getTitle());//Neues Modell-Objekt erstellen
		this.setAnzahlNachfragen(dialog.getInt_AnzahlNachfragen());														//Starten des Anzahl Nachfragendialogs
		/**
		 * edited by roland
		 * In ref wird der aktuelle Fokus erfasst, dieser ist das aktuell angeklickte Fenster.
		 * Auskommentieren des Schließaufrufst (dispose) weil altes Fenster nicht geschlossen werden soll.
		 * => Gelöster Bug für das Fach ALO.
		 * ref.dispose();																				
		 */
	}
//*** ende  ****************************************************************************************
	
	
//***Methode zum Aufrufen des Defaultkostendialogs ****************************************************************************************
	public void defaultkosten()										
	{																
		plo_DefaultkostenDialog kosten = new plo_DefaultkostenDialog(this);	//erstellen des Dialogs
	}
//*** ende  ****************************************************************************************
	
//***Methode zum Anstossen der Loesungsfunktion ****************************************************************************************
	public void optimaleLoesung()									
	{																
		Thread curr = Thread.currentThread();
		String inFile = new String(this.getInternalFocus().getTitle()+".lp");
		String outFile = new String(this.getInternalFocus().getTitle()+".out");

		this.getInternalFocus().produceMatrix();					//Erstellen der Matrix im Modellfenster
		this.getInternalFocus().matrixToLpModell();

		periodenErgebnis[] liste = new periodenErgebnis[10];
		for(int i = 0; i < 10; i++)
		{
			liste[i] = new periodenErgebnis();
		}

		//XAParser myParser = new XAParser();				//Erstellen eins ParserObjektes
		LPSolveParser myParser = new LPSolveParser();
		//XASolver mySolver = new XASolver( inFile, outFile, arbeitsverzeichnis, sPfad, sName, myParser );		//Erstellen des Solvers
        LPSolver mySolver = new LPSolver( inFile, outFile, arbeitsverzeichnis, sPfad, sName, myParser );

		int anzahlVariablen = (this.getInternalFocus().getEm_Core().getAnzahlSpalten()-2);

		this.setSolverDriver(new SolverDriver(mySolver,					//Erstellen des SolverDrivers und damit des Result-Vektors
		this.getInternalFocus().getEm_Core().getAnzahlZeilen(),
		this.getInternalFocus().matrixToLpModell(), anzahlVariablen));

		int errCo = this.getSolverDriver().getErrorCode();
		String errStr = this.getSolverDriver().getErrorMsg();
		if(errCo == 0)
		{
			int anz = this.getInternalFocus().getEm_Core().getPeriodenAnzahl();		//this.produceAnzahlPerioden(this.getSolverDriver().getPrimalResult());
			String titleBuffer = new String(this.getInternalFocus().getEm_ZugehoerigesErgebnisfenster().getTitle());

			this.getInternalFocus().setEm_ZugehoerigesErgebnisfenster(new plo_Ergebnisfenster(anz, this, titleBuffer, false, false, true, true,
			this.getInternalFocus(), 
			this.produceErgebnisArray(this.getSolverDriver().getPrimalResult())));

	//		this.getPlo_HauptFensterMenue().getMi_LoesungSpeichern().setEnabled(true);			//Anpassen des Loesungsmenues
			this.getPlo_HauptFensterMenue().getMi_LoesungDrucken().setEnabled(true);
			this.getInternalFocus().getEm_ZugehoerigesErgebnisfenster().setVisible(true);
			this.getInternalFocus().getEm_ZugehoerigesErgebnisfenster().moveToFront();
			try {
				this.getInternalFocus().setIcon(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.desk.add(this.getInternalFocus().getEm_ZugehoerigesErgebnisfenster());
		}
	}
//*** Lï¿½sung drucken - Druck-Funktion Methode wird aufgrufen *********************************************************************
	public void loesungDrucken()										
	{																
		plo_System.plo_Ergebnisfenster.modellDruck();
	}
// *** ende  ****************************************************************************************
	
	
//Methode um die Loesung in einer txt-Datei zu speichern ****************************************************************************************
	public void loesungSpeichern()									
	{																
		dialog = new FileDialog(this, "Lösung auswählen:", FileDialog.SAVE);	//oeffnen des Dateidialogs
		dialog.setVisible(true);									
		String file = new String(dialog.getFile());					//Gewinnung der Pfade und Dateiname
		String dir = new String(dialog.getDirectory());				
		String ges = new String(dir+file);							

		this.getInternalFocus().getEm_ZugehoerigesErgebnisfenster().loesungSpeichern(ges, file);	//Aufruf der Speichermethode des Ergebnisfensters
	}
// *** ende ****************************************************************************************
	
//*** Methode zum aufrufen des Solverkonfigurationsdialogs ****************************************************************************************
	public void solverConfigAendern()							
	{																
		plo_SolverDatenDialog daten = new plo_SolverDatenDialog(this);	//Anlegen des Dialogobjekts
	}
// *** ende  ****************************************************************************************
	
//***Methode zum Aufrufen des Hilfe-Dialogs ****************************************************************************************
	public void ploHilfe()												
	{																
		plo_HilfeDialog dialog = new plo_HilfeDialog();				
	}
//***ende  ****************************************************************************************
	
//***Methode zum Aufrufen des Info-Dialogs ****************************************************************************************
	public void ueber()												
	{																
		plo_ueberDialog dialog = new plo_ueberDialog();				
	}
//***ende  ****************************************************************************************

////***Benutzerhandbuch wird eingebunden**************************************************************************************************
//	public void handbuch() {
//			try {
//	             Desktop.getDesktop().open(new File("C:/Users/kat20/Desktop/Wagner-Whitin LP 1.2/02 Dokumentation/Bentzerhandbuch_Wagner-Whitin_LP.pdf"));
//	         } catch (IOException e1) {
//	        
//	             e1.printStackTrace();
//	         }
//	}
//	*** ende ******************************************************************************************************************
	
	
//*** Ende Ereignis-Methoden *********************************************************************************************************************

	
//*** Methode zum Pruefen der Gueltigkeit des Menues ************************************************************************
	public void checkValidWindows()									//Methode um zu pruefen, ob genuegend Fenster
	{																//geoeffnet sind um das Menue gueltig zu halten.
		if(this.getAnzahlEingabemasken() == 0)						//Ist keine Eingabemaske mehr geoeffnet
		{															//werden die entsprechenden Menuepunkte deaktiviert
			this.getPlo_HauptFensterMenue().getMi_ModellSpeichern().setEnabled(false);		
	//		this.getPlo_HauptFensterMenue().getMi_ModellDrucken().setEnabled(false);		
			this.getPlo_HauptFensterMenue().getMi_NachfrageEinfuegen().setEnabled(false);		
			this.getPlo_HauptFensterMenue().getMi_NachfrageEntfernen().setEnabled(false);	
			this.getPlo_HauptFensterMenue().getMi_AllesAendern().setEnabled(false);			
			this.getPlo_HauptFensterMenue().getMi_OptimaleLoesung().setEnabled(false);	
		}
		if(this.getAnzahlErgebnisfenster() == 0)					//Ist kein Ergebnisfenster mehr geoeffnet
		{															//werden die entsprechenden Menuepunkte deaktiviert
//			this.getPlo_HauptFensterMenue().getMi_LoesungSpeichern().setEnabled(false);		
//			this.getPlo_HauptFensterMenue().getMi_LoesungDrucken().setEnabled(false);			
		}
	}
//*** Ende der Methode zum Pruefen der Gueltigkeit des Menues ******************************************************************************

	
//*** Methode zum Pruefen der Gueltigkeit des Menues **************************************************************************************
	public void solverIniEinlesen()									//Methode zum Einlesen der Parameter sPfad, sName, und arbeitsverzeichnis
	{																//aus der Datei "solverini.txt"
		try															//Anlegen eines Stromobjekts fuer
		{															//die Datei "solverini.txt"
			BufferedInputStream bis = new BufferedInputStream (new FileInputStream("solverini.txt"));
		}
		catch (FileNotFoundException fnfe){	
			
			//Falls keine solverini.txt exestiert, wird eine mit vordefenierten Werten erzeugt 
			//und die Methode zum einlesen wird erneut durchlaufen
			try {
				
				File file = new File("solverini.txt");
				FileWriter writer = new FileWriter(file);
				// Text wird in den Stream geschrieben
				writer.write("Working Directory:");
				// Platformunabhï¿½ngiger Zeilenumbruch wird in den Stream geschrieben
				writer.write(System.getProperty("line.separator"));
				// Text wird in den Stream geschrieben       
				writer.write("c:\\temp");
				// Platformunabhï¿½ngiger Zeilenumbruch wird in den Stream geschrieben
				writer.write(System.getProperty("line.separator"));
				// Text wird in den Stream geschrieben       
				writer.write("Solver Directory:");
				// Platformunabhï¿½ngiger Zeilenumbruch wird in den Stream geschrieben
				writer.write(System.getProperty("line.separator"));
				// Text wird in den Stream geschrieben       
				writer.write("C:\\Methodenbank\\Solver\\LP_Solve\\Exec");
				// Platformunabhï¿½ngiger Zeilenumbruch wird in den Stream geschrieben
				writer.write(System.getProperty("line.separator"));
				// Text wird in den Stream geschrieben       
				writer.write("Solver Name:");
				// Platformunabhï¿½ngiger Zeilenumbruch wird in den Stream geschrieben
				writer.write(System.getProperty("line.separator"));
				// Text wird in den Stream geschrieben       
				writer.write("lp_solve.exe");
				writer.flush();
				
				solverIniEinlesen();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try
		{
			BufferedReader r = new BufferedReader(new java.io.FileReader("solverini.txt"));	//oeffnen des Eingastroms zur Inidatei
			String buffer = new String();						//Einlesen der Zeilen
			buffer = new String(r.readLine());					
			buffer = new String();								
			buffer = new String(r.readLine());					
			if (buffer.isEmpty()) {
				this.setArbeitsverzeichnis("c:\\temp");			//Setzen des Pfads im Hauptfenster
			}else{
				this.setArbeitsverzeichnis(buffer);				//Setzen des Pfads im Hauptfenster
			}
			
			buffer = new String();								
			buffer = new String(r.readLine());					
			buffer = new String();								
			buffer = new String(r.readLine());					
			if (buffer.isEmpty()) {
				this.setSolverPfad("C:\\Methodendatenbank\\Solver\\LP_Solve\\Exec");
			}else{
				this.setSolverPfad(buffer);						//Setzen de sPfads im Hauptfenster
			}
										
			buffer = new String();								
			buffer = new String(r.readLine());					
			buffer = new String();								
			buffer = new String(r.readLine());					
			if (buffer.isEmpty()) {
				this.setSolverName("lp_solve.exe");				//Setzen des Solvernamens im Hauptfenster
			}else{
				this.setSolverName(buffer);						//Setzen des Solvernamens im Hauptfenster
			}
			
			r.close();
		}
		catch (IOException ioe)
		{
			/**
			 * edited by roland und sonja
			 * niemals einen leeren catch block coden ;) !
			 */
			ioe.printStackTrace();
		}


	}
//*** Ende der Methode zum Pruefen der Gueltigkeit des Menues ******************************************************************************

//*** Methode zur Gewinnung des Ergebnisarrays *******************************************************************************
	public periodenErgebnis[] produceErgebnisArray(Vector[] result)		//Methode um aus dem Ergebnis Vector das gueltige periodenErgebnis-Array auszulesen
	{
		int indexBuffer = 0;											//Hilfsvariablen
		StringBuffer temp = new StringBuffer();
		Integer intTemp = new Integer("0");
		Object objectTempLager = new Object();
		Object objectTempBestell = new Object();
		int bestellBool = 0;
		Double doubleTempLager = new Double("0");
		Double doubleTempBestell = new Double("0");
		Double doubleLagerkosten = new Double("0");
		Double doubleBestellkosten = new Double("0");
		Double ergebnis = new Double("0");
		String tempLager = new String("");
		String tBestell = new String("");
		String tempKosten = new String("");
		String lagerkosten = new String("");
		String bestellkosten = new String("");
		periodenErgebnis[] retArray = new periodenErgebnis[this.getInternalFocus().getEm_Core().getPeriodenAnzahl()];	//Anlage des RueckgabeArrays
		for(int i = 0; i < this.getInternalFocus().getEm_Core().getPeriodenAnzahl(); i++)	//Fuellen des RueckgabeArrays
		{
			bestellBool = 0;
			int anz = this.getInternalFocus().getEm_Core().getPeriodenAnzahl();
			intTemp = new Integer(i+1);
			retArray[i]  = new periodenErgebnis();															//Anlegen eines neuen Ergebnisses
			tempLager = new String("x"+(i+1+anz));															//Anlage Der sTrings mit den Variablennamen der Lager- und Bestellkosten der entsprechenden
			tBestell = new String("x"+(i+1));																//Perioden.
			lagerkosten = new String(this.getInternalFocus().getEm_Core().getCoreElement(0,(i+anz)));		//Auslesen der Lagerkosten aus der Matrix
			bestellkosten = new String(this.getInternalFocus().getEm_Core().getCoreElement(0,(i+(2*anz)))); //Auslesen der Bestellkosten aus der Matrix
			doubleBestellkosten = new Double(bestellkosten);							//Umwandlung der Kostenstrings
			doubleLagerkosten = new Double(lagerkosten);							    //in Doublewerte zur Berechnung

//			System.out.println("Durchgang "+i+" tempLager: "+tempLager);
			indexBuffer = result[0].indexOf((Object)tempLager);							//Bestimmen des Indexes der ersten Variablen
			objectTempLager = result[1].get(indexBuffer);								//Auslesen des Wertes der Variablen
//			System.out.println(result[1].get(indexBuffer).toString());
			temp = new StringBuffer(objectTempLager.toString());
//			System.out.println("temp: "+temp.toString());
//          temp.delete(0, 2);
//			System.out.println("temp: "+temp.toString());
			doubleTempLager = new Double(temp.toString());								//Umwandlung des Wertes in einen DoubleWert zur Berechnung
			tempLager = new String(temp.toString());
//			System.out.println("Durchgang "+i+" indexBuffer: "+indexBuffer);
//			System.out.println("Durchgang "+i+" tempLager(found): "+tempLager);

//			System.out.println("Durchgang "+i+" tBestell: "+tBestell);
			indexBuffer = result[0].indexOf((Object)tBestell);							//Bestimmen des Indexes der zweiten Variablen
			objectTempBestell = result[1].get(indexBuffer);								//Auslesen des Wertes der Variablen
//			System.out.println(result[1].get(indexBuffer).toString());
			temp = new StringBuffer(objectTempBestell.toString());
//			System.out.println("temp: "+temp.toString());
///  		temp.delete(0, 2);
//			System.out.println("temp: "+temp.toString());
			doubleTempBestell = new Double(temp.toString());							//Umwandlung des Wertes in einen DoubleWert zur Berechnung
			tBestell = new String(temp.toString());
//			System.out.println("Durchgang "+i+" indexBuffer: "+indexBuffer);
//			System.out.println("Durchgang "+i+" tBestell: "+tBestell);
			
			//Fuellen der Textfelder des jeweiligen Periodenergebnisses
			retArray[i].setTf_NummerText(intTemp.toString());												
			retArray[i].setTf_BestellmengeText(tBestell);		
			retArray[i].setTf_LagermengeText(tempLager);
			retArray[i].setTf_FehlmengeText("0");
			
			
			
			if(doubleTempBestell.doubleValue() > 0.0)
			{
				bestellBool = 1;
			}
			ergebnis = new Double((doubleTempLager.doubleValue()*doubleLagerkosten.doubleValue())+(bestellBool*doubleBestellkosten.doubleValue()));
//			System.out.println("ergebnis: "+ergebnis.toString()+" doubleTempLager: "+doubleTempLager.doubleValue()+" doubleLagerkosten: "+doubleLagerkosten.toString()+" doubleBestellkosten: "+doubleBestellkosten.toString()+" bestellBool: "+bestellBool);
			tempKosten = new String(ergebnis.toString());													//Berechnen der Periodenkosten
			retArray[i].setTf_LagerhaltungskostenText(tempKosten);											//Eintragen der Gesamtkosten
		}
		return retArray;
	}
//*** Ende der Methode zur Gewinnung des Ergebnisarrays ********************************************************************

 }

//*** Ende Klasse plo_Hauptfenster *****************************************************************************************************************************************************