//*******************************************************************
//*		plo_Hauptfenster.java										*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Göltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse für das Applikationsfenster	*
//*		und die Verwaltung der Solveraufrufe						*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import SolverCaller.*;


//*** Klasse plo_Hauptfenster ****************************************
public class plo_Hauptfenster extends JFrame
{
	private plo_MenuBar plo_HauptFensterMenü;						//Menübalken des Hauptfensters
	public plo_HauptFensterWindowListener plo_MainListener;			//Listener des Menüs
	public plo_Hauptfenster baseWindow;								//

	private int anzahlEingabemasken;								//Anzahl der vorhandenen Eingabemaskenfenster
	private int anzahlErgebnisfenster;								//Anzahl der vorhandenen Ergebnisfenster
	private int anzahlFenster;										//Anzahl der Fenster gesamt
	private int anzahlNachfragen;									//Anzahl Nachfragen die im nächsten zu erstellenden Modell verwendet werden soll
	private String sPfad;											//Pfad des Solvers
	private String sName;											//Dateiname des Solvers
	private String arbeitsverzeichnis;								//Arbeitsverzeichnis, in welchem der Solver Arbeiten soll

	private int neueX;												//XKoordinate für ein neues Fenster
	private int neueY;												//YKoordinate für ein neues Fenster

	private double defaultBestellkosten;							//Defaultwert für die Bestellkosten
	private double defaultLagerkosten;								//Defaultwert für die Lagerkosten

	private SolverDriver solverDriver;								//DriverObjekt für den Solveraufruf.

	public JDesktopPane desk;										//Desktop-Oberfläche innerhalb des Hauptfensters

	private plo_Eingabemaske[] eingabemaskenListe;					//Array der intern vorhandenen Eingabemasken
	private plo_Ergebnisfenster[] ergebnisfensterListe;				//Array der intern vorhandenen Ergebnisfenster

	private plo_Eingabemaske internalFocus;							//Eingabemaske, die zuletzt aktiviert wurde
	private FileDialog dialog;										//Dateidialog-Objekt

//*** Konstruktor  ***------------------------------------------------
	public plo_Hauptfenster(String title)
	{
		this.setTitle(title);										//Festlegen der Grundpameter des Hauptfensters
		this.setSize(700, 600);										//
		this.setVisible(true);										//

		plo_HauptFensterMenü = new plo_MenuBar(this);				//Initialisieren des Menüs
		plo_MainListener = new plo_HauptFensterWindowListener();	//Initialisieren des MenüListeners
		baseWindow = this;											//

		this.setJMenuBar(plo_HauptFensterMenü);						//Einbinden des Menüs
		this.addWindowListener(plo_MainListener);					//Einbinden des Menülisteners

		anzahlEingabemasken = 0;									//Initialisieren der Variablen
		anzahlErgebnisfenster = 0;									//
		anzahlFenster = 0;											//
		neueX = 0;													//
		neueY = 0;													//
		defaultBestellkosten = 20;									//
		defaultLagerkosten = (0.1);									//
		arbeitsverzeichnis = new String("");						//
		sName = new String("");										//
		sPfad = new String("");										//

		eingabemaskenListe = new plo_Eingabemaske[9];				//Eingabe auf 9 Modelle gleichzeitig beschränkt
		ergebnisfensterListe = new plo_Ergebnisfenster[9];			//

		this.solverIniEinlesen();									//Einlesen der Standart-Solverdaten aus der Datei "solverini.txt"

		desk = new JDesktopPane();									//Initialisieren der Desktopoberfläche
		this.getContentPane().add(desk);							//Hinzufügen der Desktopoberfläche

	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** SET-Methoden ***------------------------------------------------
	public void setAnzahlEingabemasken(int elem)					//Methode zum Setzen der Anzahl der Eingabemasken
	{
		anzahlEingabemasken = elem;
	}

	public void setAnzahlErgebnisfenster(int elem)					//Methode zum Setzen der Anzahl der Ergebnisfenster
	{
		anzahlErgebnisfenster = elem;
	}

	public void setAnzahlFenster(int elem)							//Methode zum Setzen der Anzahl der gesamt vorhandenen Fenster
	{
		anzahlFenster = elem;
	}

	public void setAnzahlNachfragen(int elem)						//Methode zum Setzen der Puffergröße anzahlNachfragen
	{
		anzahlNachfragen = elem;
	}

	public void setNeueX(int elem)									//Methode zum Setzen der XKoordinate für neue Fenster
	{
		neueX = elem;
	}

	public void setNeueY(int elem)									//Methode zum Setzen der YKoordinate für neue Fenster
	{
		neueY = elem;
	}

	public void setDefaultBestellkosten(double elem)				//Methode zum Setzen der Default-Bestellkosten
	{
		defaultBestellkosten = elem;
	}

	public void setDefaultLagerkosten(double elem)					//Methode zum Setzen der Default-Lagerkosten
	{
		defaultLagerkosten = elem;
	}

	public void setEingabemaskenListe(int index, plo_Eingabemaske elem)	//Methode zum Setzen einer Eingabemaske an der durch einen Index bezeichneten Stelle im Eingabemasken-Array
	{
		elem.setFensterIndex(index);
		eingabemaskenListe[index] = new plo_Eingabemaske(this, 0, " ", false, false, true, true);
		eingabemaskenListe[index] = elem;
		this.setErgebnisfensterListe(index, elem.getEm_ZugehörigesErgebnisfenster(), elem);
	}

	public void setErgebnisfensterListe(int index, plo_Ergebnisfenster elem, plo_Eingabemaske parent)	//Methode zum Setzen eines Ergebnisfensters an der durch einen Index bezeichneten Stelle im Ergebnisfenster-Array
	{
		ergebnisfensterListe[index] = new plo_Ergebnisfenster(this, " ", false, false, true, true, parent);
		ergebnisfensterListe[index] = elem;
	}

	public void setInternalFocus(plo_Eingabemaske elem)				//Methode zum Setzen einer Eingabemaske in den internen Fokus
	{
		internalFocus = elem;
	}

	public void setDialog(FileDialog elem)							//Methode zum Setzen des Dateidialogs
	{
		dialog = elem;
	}

	public void setSolverPfad(String elem)							//Methode zum Setzen des Solverpfads
	{
		sPfad = elem;
	}

	public void setArbeitsverzeichnis(String elem)					//Methode zum Setzen des Arbeitsverzeichnisses
	{
		arbeitsverzeichnis = elem;
	}

	public void setSolverName(String elem)							//Methode zum Setzen des Solvernamens
	{
		sName = elem;
	}

	public void setSolverDriver(SolverDriver elem)
	{
		solverDriver = elem;
	}
//*** Ende SET-Methoden ***-------------------------------------------

//*** GET-Methoden ***------------------------------------------------
	public int getAnzahlEingabemasken()								//Methode zur Rückgabe der Anzahl der Eingabemasken
	{
		return anzahlEingabemasken;
	}

	public int getAnzahlErgebnisfenster()							//Methode zur Rückgabe der Anzahl der Ergebnisfenster
	{
		return anzahlErgebnisfenster;
	}

	public int getAnzahlFenster()									//Methode zur Rückgabe der Anzahl der Fenster im gesamten System
	{
		return anzahlFenster;
	}

	public int getAnzahlNachfragen()								//Methode zur Rückgabe der Puffergröße
	{
		return anzahlNachfragen;
	}

	public int getNeueX()											//Methode zur Rückgabe der XKoordinate für neue Fenster
	{
		return neueX;
	}

	public int getNeueY()											//Methode zur Rückgabe der YKoordinate für neue Fenster
	{
		return neueY;
	}

	public double getDefaultBestellkosten()							//Methode zur Rückgabe der Defaultbestellkosten
	{
		return defaultBestellkosten;
	}

	public double getDefaultLagerkosten()							//Methode zur Rückgabe der Defaultlagerkosten
	{
		return defaultLagerkosten;
	}

	public plo_Eingabemaske getEingabemaskenListe(int index)		//Methode zur Rückgabe einer Eingabemaske an der durch einen Index bezeichneten Stelle im Eingabemasken-Array
	{
		return eingabemaskenListe[index];
	}

	public plo_Ergebnisfenster getErgebnisfensterListe(int index)	//Methode zur Rückgabe eines Ergebnisfensters an der durch einen Index bezeichneten Stelle im Ergebnisfenster-Array
	{
		return ergebnisfensterListe[index];
	}

	public plo_Eingabemaske getInternalFocus()						//Methode zur Rückgabe der Eingabemaske im internen Fokus
	{
		return internalFocus;
	}

	public plo_MenuBar getPlo_HauptFensterMenü()					//Methode zur Rückgabe des Hauptfenstermenüs
	{
		return plo_HauptFensterMenü;
	}

	public FileDialog getDialog()									//Methode zur Rückgabe des Dateidialogs
	{
		return dialog;
	}

	public String getSolverPfad()									//Methode zur Rückgabe des Solverpfades
	{
		return sPfad;
	}

	public String getArbeitsverzeichnis()							//Methode zur Rückgabe des Arbeitsverzeichnisses
	{
		return arbeitsverzeichnis;
	}

	public String getSolverName()									//Methode zur Rückgabe des Solvernamens
	{
		return sName;
	}

	public SolverDriver getSolverDriver()
	{
		return solverDriver;
	}
//*** Ende GET-Methoden ***-------------------------------------------

//*** Ereignis-Methoden ***-------------------------------------------
	public void neuesModellDialog()									//Methode zum Start des Dialogs für die Erstellung eines neuen Modells
	{																//
		plo_AnzahlNachfragenDialog dialog = new plo_AnzahlNachfragenDialog(this, 0, "");	//Der Eingabedialog zur Ermittlung der Anzahl der zu erwartenden Nachfragen wird gestartet
		this.setAnzahlNachfragen(dialog.getInt_AnzahlNachfragen());	//Das Ergebnis des Dialogs wird ausgelesen und in den Puffer geladen
	}

	public void neuesModell(int elem, int mode, String name)		//Methode zur Erstellung eines neune Modells
	{																//
		String modellname = new String("Modell"+(this.getAnzahlEingabemasken()+1)); //Generierung des Defaultnamens für das Modell
		if(mode == 1)												//Sollte die Methode aufgerufen werden, um ein vorhandenes Modell neu anzuzeigen...
		{															//
			modellname = new String(name);							//Initialisierung des Modellnamens mit dem alten Namen
		}

		eingabemaskenListe[this.getAnzahlEingabemasken()] = new plo_Eingabemaske(this, 0, " ", false, false, true, true);					//Im Eingabemasken-Array wird ein neues Objekt erzeugt
		this.setEingabemaskenListe(this.getAnzahlEingabemasken(), new plo_Eingabemaske(this, elem, modellname, false, false, true, true));	//
		if(mode == 1)
		{
			Point point = new Point();
			point = this.getInternalFocus().getLocation();
			Integer tempX = new Integer((int)point.getX());
			Integer tempY = new Integer((int)point.getY());
			this.setNeueX(tempX.intValue());
			this.setNeueY(tempY.intValue());
		}
		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());							//Auch ein neues Ereignisfenster wird erzeugt
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());							//
		if(this.getNeueX() > 100)									//Neu Kalibrierung der Koordinaten für neue Fenster
		{															//
			this.setNeueX(0);										//
		}															//
		else														//
		{															//
			this.setNeueX(this.getNeueX()+40);						//
		}															//
		this.setNeueY(this.getNeueY()+40);							//
		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setVisible(true);		//Sichtbar machen der Eingabemaske
		this.setInternalFocus(this.getEingabemaskenListe(this.getAnzahlEingabemasken())); //Neue Maske erhält automatisch den Fokus
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setVisible(false);	//Verstecken des noch leeren Ergebnisfensters
		this.desk.add(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));		//Hinzufügen der neuen Fenster zum
		this.desk.moveToFront(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));	//Nach vorne bringen des Eingabefensters
		this.desk.add(this.getErgebnisfensterListe(this.getAnzahlEingabemasken()));		//Desktop des Hauptfensters

		this.getPlo_HauptFensterMenü().getMi_ModellSpeichern().setEnabled(true);		//Anpassen der Menüoptionen
//		this.getPlo_HauptFensterMenü().getMi_ModellDrucken().setEnabled(true);			//
		this.getPlo_HauptFensterMenü().getMi_NachfrageEinfügen().setEnabled(true);		//
		if((this.getEingabemaskenListe(this.getAnzahlEingabemasken()).getAnzahlNachfragen()) > 1)	//
		{																				//
			this.getPlo_HauptFensterMenü().getMi_NachfrageEntfernen().setEnabled(true);	//
		}																				//
		this.getPlo_HauptFensterMenü().getMi_AllesÄndern().setEnabled(true);			//
		this.getPlo_HauptFensterMenü().getMi_OptimaleLösung().setEnabled(true);			//

		this.setAnzahlEingabemasken(this.getAnzahlEingabemasken()+1);					//Erhöhen der Fensterzählvariablen
		this.setAnzahlErgebnisfenster(this.getAnzahlErgebnisfenster()+1);				//
	}

	public void modellLaden()										//Methode zum Einlesen
	{																//
		dialog = new FileDialog(this, "Lagermodell auswählen:", FileDialog.LOAD);	//Öffnen des Ladedialogs zur Gewinnung
		dialog.setVisible(true);									//der Pfade und Dateinamen
		String file = new String(dialog.getFile());					//Auslesen der Pfade und
		String dir = new String(dialog.getDirectory());				//Dateinamen
		String ges = new String(dir+file);							//
		String modellName = new String();							//
		Integer intPuffer = new Integer("0");						//

		try															//Definiern des Eingabestroms zur zu ladenden
		{															//Datei
			BufferedInputStream bis = new BufferedInputStream (new FileInputStream(ges));
		}
		catch (FileNotFoundException fnfe)
		{	}
		try
		{
			BufferedReader r = new BufferedReader(new java.io.FileReader(ges));	//Öffnen des Stroms
			StringBuffer temp = new StringBuffer();					//Einlesen der ersten paar
			String compare = new String("\t");						//Zeilen zur Gewinnung des Modellnamens
			String transfer = new String();							//und der Anzahl der Nachfragen
			String buffer = new String(r.readLine());				//
			modellName = new String(r.readLine());					//
			buffer = new String(r.readLine());						//
			buffer = new String(r.readLine());						//
			intPuffer = new Integer(r.readLine());					//
		}
		catch (IOException ioe)
		{	}

		eingabemaskenListe[this.getAnzahlEingabemasken()] = new plo_Eingabemaske(this, 0, " ", false, false, true, true);									//Erstellen des neuen Modells
		this.setEingabemaskenListe(this.getAnzahlEingabemasken(), new plo_Eingabemaske(this, intPuffer.intValue(), modellName, false, false, true, true));	//
		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());											//
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setLocation(this.getNeueX(), this.getNeueY());											//
		if(this.getNeueX() > 100)									//Anpassen der neuen Koordinaten
		{															//
			this.setNeueX(0);										//
		}															//
		else														//
		{															//
			this.setNeueX(this.getNeueX()+40);						//
		}															//
		this.setNeueY(this.getNeueY()+40);							//

		this.setNeueY(this.getNeueY()+40);

		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).modellLaden(ges);	//Starten der Lademethode

		this.getPlo_HauptFensterMenü().getMi_ModellSpeichern().setEnabled(true);	//Anpassen des Menüs
//		this.getPlo_HauptFensterMenü().getMi_ModellDrucken().setEnabled(true);
		this.getPlo_HauptFensterMenü().getMi_NachfrageEinfügen().setEnabled(true);	//
		if((this.getEingabemaskenListe(this.getAnzahlEingabemasken()).getAnzahlNachfragen()) > 1)	//
		{																							//
			this.getPlo_HauptFensterMenü().getMi_NachfrageEntfernen().setEnabled(true);				//
		}																							//
		this.getPlo_HauptFensterMenü().getMi_AllesÄndern().setEnabled(true);				//
		this.getPlo_HauptFensterMenü().getMi_OptimaleLösung().setEnabled(true);				//

		this.desk.add(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));			//Hinzufügen des neuen Modells zum Desktop
		this.desk.moveToFront(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));	//Nach vorne bringen des Eingabefensters
		this.desk.add(this.getErgebnisfensterListe(this.getAnzahlEingabemasken()));			//des Hauptfensters

		this.getEingabemaskenListe(this.getAnzahlEingabemasken()).setVisible(true);			//
		this.getErgebnisfensterListe(this.getAnzahlEingabemasken()).setVisible(false);		//

		this.setInternalFocus(this.getEingabemaskenListe(this.getAnzahlEingabemasken()));	//

		this.setAnzahlEingabemasken(this.getAnzahlEingabemasken()+1);						//Anpassen der Zählvariablen
		this.setAnzahlErgebnisfenster(this.getAnzahlErgebnisfenster()+1);					//
	}

	public void modellDrucken()										//
	{																//

	}

	public void modellSpeichern()									//Methode zum Speichern eines Modells in eine txt-Datei
	{																//
		dialog = new FileDialog(this, "Lagermodell auswählen:", FileDialog.SAVE);	//Starten des Dateidialogs zur Gewinnung von Pfaden und Dateinamen
		dialog.setVisible(true);									//
		String file = new String(dialog.getFile());					//Gewinnen der Pfade
		String dir = new String(dialog.getDirectory());				//und Dateinamen
		String ges = new String(dir+file);							//

		this.getInternalFocus().modellSpeichern(ges, file);			//Aufrufen der Speichermethode
		this.getInternalFocus().setTitle(file);						//Ändern des Modellnamen in den Ausgewählten Speichernamen
	}

	public void nachfrageEinfügen()									//Methode um in ein bestehendes Modell eine weitere Nachfrage einzufügen
	{																//
		this.getInternalFocus().nachfrageEinfügen();				//Aufruf der Einfügemethode
	}

	public void nachfrageEntfernen()								//Methode um aus einem bestehenden Modell eine Nachfrage zu löschen
	{																//
		plo_NachfrageEntfernenDialog dialog = new plo_NachfrageEntfernenDialog(this);	//Aufrufen des Dialogs zur Gewinnung der zu löschenden Nachfragenummer
	}

	public void initializeNew(double x, double y, int nummer, plo_Eingabemaske itself, plo_Hauptfenster root, int anzNach, String title, boolean resizable, boolean maximizable, boolean closable, boolean iconifiable, nachfrage[] liste)
	{																//Methode um ein neues Fenster mit bestehendem Titel und
																	//einer bestehenden Nachfrageliste zu initialisieren
		itself = new plo_Eingabemaske(root, anzNach, title, resizable, maximizable, closable, iconifiable, liste);	//Erstellen des neuen Modells
		itself.setVisible(true);									//Initialisieren der Fenster-Parameter
		itself.setLocation((int)x, (int)y);							//
		itself.getEm_ZugehörigesErgebnisfenster().setLocation((int)x, (int)y);	//
		itself.getEm_ZugehörigesErgebnisfenster().setVisible(false);//

		this.setEingabemaskenListe(nummer, itself);					//
		this.setErgebnisfensterListe(nummer, itself.getEm_ZugehörigesErgebnisfenster(), itself);	//
		this.desk.add(this.getEingabemaskenListe(nummer));			//Hinzufügen des Fensters zum Desktop
		this.desk.moveToFront(this.getEingabemaskenListe(nummer));	//Nach vorne bringenen des Eingabefensters
		this.desk.add(this.getErgebnisfensterListe(nummer));		//
	}

	public void allesÄndern()										//Methode um das einlesen eines Modells neu zu starten
	{																//
		plo_Eingabemaske ref= new plo_Eingabemaske(this, 0, "temp", false, false, true, true);
		ref = this.getInternalFocus();
		this.getInternalFocus().getEm_ZugehörigesErgebnisfenster().dispose();											//Altes Ergebnisfenster schließen
		plo_AnzahlNachfragenDialog dialog = new plo_AnzahlNachfragenDialog(this, 1, this.getInternalFocus().getTitle());//Neues Modell-Objekt erstellen
		this.setAnzahlNachfragen(dialog.getInt_AnzahlNachfragen());	//Starten des Anzahl Nachfragendialogs
		ref.dispose();							//Schließen des alten Fensters
	}

	public void defaultkosten()										//Methode zum Aufrufen des Defaultkostendialogs
	{																//
		plo_DefaultkostenDialog kosten = new plo_DefaultkostenDialog(this);	//erstellen des Dialogs
	}

	public void optimaleLösung()									//Methode zum Anstoßen der Lösungsfunktion
	{																//
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
		//XASolver mySolver = new XASolver( inFile, outFile, arbeitsverzeichnis, sPfad, sName, myParser );	//Erstellen des Solvers
                LPSolver mySolver = new LPSolver( inFile, outFile, arbeitsverzeichnis, sPfad, sName, myParser );

		int anzahlVariablen = (this.getInternalFocus().getEm_Core().getAnzahlSpalten()-2);

		this.setSolverDriver(new SolverDriver(mySolver,					//Erstellen des SolverDrivers und damit des Result-Vektors
			this.getInternalFocus().getEm_Core().getAnzahlZeilen(),
			this.getInternalFocus().matrixToLpModell(), anzahlVariablen));

		int errCo = this.getSolverDriver().getErrorCode();
		String errStr = this.getSolverDriver().getErrorMsg();

		if(errCo == 0)
		{
			int anz = this.getInternalFocus().getEm_Core().getPeriodenAnzahl();//this.produceAnzahlPerioden(this.getSolverDriver().getPrimalResult());
			String titleBuffer = new String(this.getInternalFocus().getEm_ZugehörigesErgebnisfenster().getTitle());

			this.getInternalFocus().setEm_ZugehörigesErgebnisfenster(new plo_Ergebnisfenster(anz, this, titleBuffer, false, false, true, true,
											this.getInternalFocus(), this.produceErgebnisArray(this.getSolverDriver().getPrimalResult())));

			this.getPlo_HauptFensterMenü().getMi_LösungSpeichern().setEnabled(true);		//Anpassen des Lösungsmenüs
	//		this.getPlo_HauptFensterMenü().getMi_LösungDrucken().setEnabled(true);
			this.getInternalFocus().getEm_ZugehörigesErgebnisfenster().setVisible(true);
			this.getInternalFocus().getEm_ZugehörigesErgebnisfenster().moveToFront();
			this.desk.add(this.getInternalFocus().getEm_ZugehörigesErgebnisfenster());
		}
	}

	public void lösungDrucken()										//
	{																//

	}

	public void lösungSpeichern()									//Methode um die Lösung in einer
	{																//txt-Datei zu speichern
		dialog = new FileDialog(this, "Lösung auswählen:", FileDialog.SAVE);	//Öffnen des Dateidialogs
		dialog.setVisible(true);									//
		String file = new String(dialog.getFile());					//Gewinnung der Pfade und Dateiname
		String dir = new String(dialog.getDirectory());				//
		String ges = new String(dir+file);							//

		this.getInternalFocus().getEm_ZugehörigesErgebnisfenster().lösungSpeichern(ges, file);	//Aufruf der Speichermethode des Ergebnisfensters
	}

	public void solverConfigÄndern()								//Methode zum aufrufen des Solverkonfigurationsdialogs
	{																//
		plo_SolverDatenDialog daten = new plo_SolverDatenDialog(this);//Anlegen des Dialogobjekts
	}

	public void ploHilfe()											//Methode zum Aufrufen des Hilfe-Dialogs
	{																//
		plo_HilfeDialog dialog = new plo_HilfeDialog();				//
	}

	public void über()												//Methode zum Aufrufen des Info-Dialogs
	{																//
		plo_ÜberDialog dialog = new plo_ÜberDialog();				//
	}

//*** Ende Ereignis-Methoden ***--------------------------------------

//*** Methode zum Prüfen der Gültigkeit des Menüs ***-----------------
	public void checkValidWindows()									//Methode um zu prüfen, ob genügend Fenster
	{																//geöffnet sind um das Menü gültig zu halten.
		if(this.getAnzahlEingabemasken() == 0)						//Ist keine Eingabemaske mehr geöffnet
		{															//werden die entsprechenden Menüpunkte deaktiviert
			this.getPlo_HauptFensterMenü().getMi_ModellSpeichern().setEnabled(false);		//
			this.getPlo_HauptFensterMenü().getMi_ModellDrucken().setEnabled(false);			//
			this.getPlo_HauptFensterMenü().getMi_NachfrageEinfügen().setEnabled(false);		//
			this.getPlo_HauptFensterMenü().getMi_NachfrageEntfernen().setEnabled(false);	//
			this.getPlo_HauptFensterMenü().getMi_AllesÄndern().setEnabled(false);			//
			this.getPlo_HauptFensterMenü().getMi_OptimaleLösung().setEnabled(false);		//
		}
		if(this.getAnzahlErgebnisfenster() == 0)					//Ist kein Ergebnisfenster mehr geöffnet
		{															//werden die entsprechenden Menüpunkte deaktiviert
			this.getPlo_HauptFensterMenü().getMi_LösungSpeichern().setEnabled(false);		//
			this.getPlo_HauptFensterMenü().getMi_LösungDrucken().setEnabled(false);			//
		}
	}
//*** Ende der Methode zum Prüfen der Gültigkeit des Menüs ***--------

//*** Methode zum Prüfen der Gültigkeit des Menüs ***-----------------
	public void solverIniEinlesen()									//Methode zum Einlesen der Parameter sPfad, sName, und arbeitsverzeichnis
	{																//aus der Datei "solverini.txt"
		try															//Anlegen eines Stromobjekts für
		{															//die Datei "solverini.txt"
			BufferedInputStream bis = new BufferedInputStream (new FileInputStream("solverini.txt"));
		}
		catch (FileNotFoundException fnfe)
		{	}
		try
		{
			BufferedReader r = new BufferedReader(new java.io.FileReader("solverini.txt"));	//Öffnen des Eingastroms zur Inidatei
			String buffer = new String();						//Einlesen der Zeilen
			buffer = new String(r.readLine());					//
			buffer = new String();								//
			buffer = new String(r.readLine());					//
			this.setArbeitsverzeichnis(buffer);					//Setzen des Pfads im Hauptfenster
			buffer = new String();								//
			buffer = new String(r.readLine());					//
			buffer = new String();								//
			buffer = new String(r.readLine());					//
			this.setSolverPfad(buffer);							//Setzen de sPfads im Hauptfenster
			buffer = new String();								//
			buffer = new String(r.readLine());					//
			buffer = new String();								//
			buffer = new String(r.readLine());					//
			this.setSolverName(buffer);							//Setzen des Solvernamens im Hauptfenster
		}
		catch (IOException ioe)
		{	}


	}
//*** Ende der Methode zum Prüfen der Gültigkeit des Menüs ***--------

//*** Methode zur Gewinnung des Ergebnisarrays ***-------------------
	public periodenErgebnis[] produceErgebnisArray(Vector[] result)		//Methoder um aus dem Ergebnis Vector das gültige periodenErgebnis-Array auszulesen
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
		periodenErgebnis[] retArray = new periodenErgebnis[this.getInternalFocus().getEm_Core().getPeriodenAnzahl()];	//Anlage des RückgabeArrays
		for(int i = 0; i < this.getInternalFocus().getEm_Core().getPeriodenAnzahl(); i++)	//Füllen des RückgabeArrays
		{
			bestellBool = 0;
			int anz = this.getInternalFocus().getEm_Core().getPeriodenAnzahl();
			intTemp = new Integer(i+1);
			retArray[i]  = new periodenErgebnis();						//Anlegen eines neuen Ergebnisses
			tempLager = new String("x"+(i+1+anz));							//Anlage Der sTrings mit den Variablennamen der Lager- und Bestellkosten der entsprechenden
			tBestell = new String("x"+(i+1));	//Perioden.
			lagerkosten = new String(this.getInternalFocus().getEm_Core().getCoreElement(0,(i+anz)));				//Auslesen der Lagerkosten aus der Matrix
			bestellkosten = new String(this.getInternalFocus().getEm_Core().getCoreElement(0,(i+(2*anz)))); //Auslesen der Bestellkosten aus der Matrix
			doubleBestellkosten = new Double(bestellkosten);			//Umwandlung der Kostenstrings
			doubleLagerkosten = new Double(lagerkosten);				//in Doublewerte zur Berechnung

//			System.out.println("Durchgang "+i+" tempLager: "+tempLager);
			indexBuffer = result[0].indexOf((Object)tempLager);			//Bestimmen des Indexes der ersten Variablen
			objectTempLager = result[1].get(indexBuffer);				//Auslesen des Wertes der Variablen
//			System.out.println(result[1].get(indexBuffer).toString());
			temp = new StringBuffer(objectTempLager.toString());
//			System.out.println("temp: "+temp.toString());
///*!!!*/		temp.delete(0, 2);
//			System.out.println("temp: "+temp.toString());
			doubleTempLager = new Double(temp.toString());	//Umwandlung des Wertes in einen DoubleWert zur Berechnung
			tempLager = new String(temp.toString());
//			System.out.println("Durchgang "+i+" indexBuffer: "+indexBuffer);
//			System.out.println("Durchgang "+i+" tempLager(found): "+tempLager);

//			System.out.println("Durchgang "+i+" tBestell: "+tBestell);
			indexBuffer = result[0].indexOf((Object)tBestell);		//Bestimmen des Indexes der zweiten Variablen
			objectTempBestell = result[1].get(indexBuffer);				//Auslesen des Wertes der Variablen
//			System.out.println(result[1].get(indexBuffer).toString());
			temp = new StringBuffer(objectTempBestell.toString());
//			System.out.println("temp: "+temp.toString());
///*!!!*/		temp.delete(0, 2);
//			System.out.println("temp: "+temp.toString());
			doubleTempBestell = new Double(temp.toString());//Umwandlung des Wertes in einen DoubleWert zur Berechnung
			tBestell = new String(temp.toString());
//			System.out.println("Durchgang "+i+" indexBuffer: "+indexBuffer);
//			System.out.println("Durchgang "+i+" tBestell: "+tBestell);

			retArray[i].setTf_NummerText(intTemp.toString());			//Füllen der Textfelder des jeweiligen Periodenergebnisses
			retArray[i].setTf_BestellmengeText(tBestell);
			retArray[i].setTf_LagermengeText(tempLager);
			retArray[i].setTf_FehlmengeText("0");
			if(doubleTempBestell.doubleValue() > 0.0)
			{
				bestellBool = 1;
			}
			ergebnis = new Double((doubleTempLager.doubleValue()*doubleLagerkosten.doubleValue())+(bestellBool*doubleBestellkosten.doubleValue()));
			System.out.println("ergebnis: "+ergebnis.toString()+" doubleTempLager: "+doubleTempLager.doubleValue()+" doubleLagerkosten: "+doubleLagerkosten.toString()+" doubleBestellkosten: "+doubleBestellkosten.toString()+" bestellBool: "+bestellBool);
			tempKosten = new String(ergebnis.toString());				//Berechnen der Periodenkosten
			retArray[i].setTf_LagerhaltungskostenText(tempKosten);		//Eintragen der Gesamtkosten
		}
		return retArray;
	}
//*** Ende der Methode zur Gewinnung des Ergebnisarrays ***-----------
}
//*** Ende Klasse plo_Hauptfenster ***********************************