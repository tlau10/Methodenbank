//*******************************************************************
//*		plo_MenuBar.java											*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Klasse des Drop-Down- Men�s im 		*
//*		Applikationshauptfenster									*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_MenuBar  ***----------------------------------------
public class plo_MenuBar extends JMenuBar
{
	private plo_Hauptfenster base;									//Referenzobjekt f�r Hierarchieschritte nach oben

	private JMenu m_Lagermodell;									//Men�s
	private JMenu m_Bearbeiten;
	private JMenu m_L�sung;
	private JMenu m_Solver;
	private JMenu m_Hilfe;

	private JMenuItem mi_NeuesModell;								//Einzelne Men�punkte
	private JMenuItem mi_ModellLaden;
	private JMenuItem mi_ModellSpeichern;
	private JMenuItem mi_ModellDrucken;

	private JMenuItem mi_NachfrageEinf�gen;
	private JMenuItem mi_NachfrageEntfernen;
	private JMenuItem mi_Alles�ndern;
	private JMenuItem mi_Defaultkosten;

	private JMenuItem mi_OptimaleL�sung;
	private JMenuItem mi_L�sungDrucken;
	private JMenuItem mi_L�sungSpeichern;

	private JMenuItem mi_SolverConfig�ndern;

	private JMenuItem mi_PloHilfe;
	private JMenuItem mi_�ber;

	private plo_MenuBarListener menuItemListener;
//*** Konstruktor  ***------------------------------------------------
	public plo_MenuBar(plo_Hauptfenster main)
	{
		base = main;												//Initialisieren des Referenzobjektes

		menuItemListener = new plo_MenuBarListener(this);

		m_Lagermodell = new JMenu("Lagermodell");					//Initialisieren der Men�s
		m_Bearbeiten = new JMenu("Bearbeiten");
		m_L�sung = new JMenu("L�sung");
		m_Solver = new JMenu("Solver");
		m_Hilfe = new JMenu("Hilfe");

		mi_NeuesModell = new JMenuItem("Neues Modell");				//Initialisieren der Men�punkte
		mi_ModellLaden = new JMenuItem("Modell laden");
		mi_ModellSpeichern = new JMenuItem("Model Speichern");
		mi_ModellSpeichern.setEnabled(false);
		mi_ModellDrucken = new JMenuItem("Modell Drucken");
		mi_ModellDrucken.setEnabled(false);

		mi_NachfrageEinf�gen = new JMenuItem("Nachfrage einf�gen");
		mi_NachfrageEinf�gen.setEnabled(false);
		mi_NachfrageEntfernen = new JMenuItem("Nachfrage entfernen");
		mi_NachfrageEntfernen.setEnabled(false);
		mi_Alles�ndern = new JMenuItem("Alles �ndern");
		mi_Alles�ndern.setEnabled(false);
		mi_Defaultkosten = new JMenuItem("Defaultkosten");

		mi_OptimaleL�sung = new JMenuItem("Optimale L�sung");
		mi_OptimaleL�sung.setEnabled(false);
		mi_L�sungDrucken = new JMenuItem("L�sung drucken");
		mi_L�sungDrucken.setEnabled(false);
		mi_L�sungSpeichern = new JMenuItem("L�sung speichern");
		mi_L�sungSpeichern.setEnabled(false);

		mi_SolverConfig�ndern = new JMenuItem("Solver Konfiguration �ndern");
		mi_SolverConfig�ndern.setEnabled(true);

		mi_PloHilfe = new JMenuItem("PLO-Hilfe");
		mi_�ber = new JMenuItem("�ber...");

		mi_NeuesModell.addActionListener(menuItemListener);			//Action- und Itemlistener an die
		mi_NeuesModell.addItemListener(menuItemListener);			//Men�punkte binden
		mi_ModellLaden.addActionListener(menuItemListener);
		mi_ModellLaden.addItemListener(menuItemListener);
		mi_ModellSpeichern.addActionListener(menuItemListener);
		mi_ModellSpeichern.addItemListener(menuItemListener);
		mi_ModellDrucken.addActionListener(menuItemListener);
		mi_ModellDrucken.addItemListener(menuItemListener);
		mi_NachfrageEinf�gen.addActionListener(menuItemListener);
		mi_NachfrageEinf�gen.addItemListener(menuItemListener);
		mi_NachfrageEntfernen.addActionListener(menuItemListener);
		mi_NachfrageEntfernen.addItemListener(menuItemListener);
		mi_Alles�ndern.addActionListener(menuItemListener);
		mi_Alles�ndern.addItemListener(menuItemListener);
		mi_Defaultkosten.addActionListener(menuItemListener);
		mi_Defaultkosten.addItemListener(menuItemListener);
		mi_OptimaleL�sung.addActionListener(menuItemListener);
		mi_OptimaleL�sung.addItemListener(menuItemListener);
		mi_L�sungDrucken.addActionListener(menuItemListener);
		mi_L�sungDrucken.addItemListener(menuItemListener);
		mi_L�sungSpeichern.addActionListener(menuItemListener);
		mi_L�sungSpeichern.addItemListener(menuItemListener);
		mi_SolverConfig�ndern.addActionListener(menuItemListener);
		mi_SolverConfig�ndern.addItemListener(menuItemListener);
		mi_PloHilfe.addActionListener(menuItemListener);
		mi_PloHilfe.addItemListener(menuItemListener);
		mi_�ber.addActionListener(menuItemListener);
		mi_�ber.addItemListener(menuItemListener);

		m_Lagermodell.add(mi_NeuesModell);							//Binden der Lagermodell-Men�punkte
		m_Lagermodell.addSeparator();								//an das Lagermodellmen�
		m_Lagermodell.add(mi_ModellLaden);
		m_Lagermodell.add(mi_ModellSpeichern);
		m_Lagermodell.add(mi_ModellDrucken);

		m_Bearbeiten.add(mi_NachfrageEinf�gen);						//Binden der Bearbeiten-Men�punkte
		m_Bearbeiten.add(mi_NachfrageEntfernen);					//an das Bearbeitenmen�
		m_Bearbeiten.addSeparator();
		m_Bearbeiten.add(mi_Alles�ndern);
		m_Bearbeiten.addSeparator();
		m_Bearbeiten.add(mi_Defaultkosten);

		m_L�sung.add(mi_OptimaleL�sung);							//Binden der L�sungs-Men�punkte
		m_L�sung.addSeparator();									//an das L�sungsmen�
		m_L�sung.add(mi_L�sungDrucken);
		m_L�sung.add(mi_L�sungSpeichern);

		m_Solver.add(mi_SolverConfig�ndern);						//Binden der Solver-Men�punkte an
																	//an das Solvermen�

		m_Hilfe.add(mi_PloHilfe);									//Binden der Hilfe_Men�punkte
		m_Hilfe.addSeparator();										//an das Hilfemen�
		m_Hilfe.add(mi_�ber);

		this. add(m_Lagermodell);									//Hinzuf�gen der Men�s an den
		this. add(m_Bearbeiten);									//Menubar
		this. add(m_L�sung);
		this. add(m_Solver);
		this. add(m_Hilfe);
	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** SET-Methoden  ***-----------------------------------------------

//*** Ende SET-Methoden  ***------------------------------------------

//*** GET-Methoden  ***-----------------------------------------------
	public plo_Hauptfenster getBase()								//Methode zur R�ckgabe des Referenzobjekts auf das Hauptobjekt
	{
		return base;
	}

	public JMenuItem getMi_NeuesModell()							//Methode zur R�ckgabe des Men�objektes mi_NeuesModell
	{
		return mi_NeuesModell;
	}

	public JMenuItem getMi_ModellLaden()							//Methode zur R�ckgabe des Men�objektes mi_ModellLaden
	{
		return mi_ModellLaden;
	}

	public JMenuItem getMi_ModellSpeichern()						//Methode zur R�ckgabe des Men�objektes mi_ModellSpeichern
	{
		return mi_ModellSpeichern;
	}

	public JMenuItem getMi_ModellDrucken()							//Methode zur R�ckgabe des Men�objektes mi_ModellDrucken
	{
		return mi_ModellDrucken;
	}

	public JMenuItem getMi_NachfrageEinf�gen()						//Methode zur R�ckgabe des Men�objektes mi_NachfrageEinf�gen
	{
		return mi_NachfrageEinf�gen;
	}

	public JMenuItem getMi_NachfrageEntfernen()						//Methode zur R�ckgabe des Men�objektes mi_NachfrageEntfernen
	{
		return mi_NachfrageEntfernen;
	}

	public JMenuItem getMi_Alles�ndern()							//Methode zur R�ckgabe des Men�objektes mi_Alles�ndern
	{
		return mi_Alles�ndern;
	}

	public JMenuItem getMi_Defaultkosten()							//Methode zur R�ckgabe des Men�objektes mi_Defaultkosten
	{
		return mi_Defaultkosten;
	}

	public JMenuItem getMi_OptimaleL�sung()							//Methode zur R�ckgabe des Men�objektes mi_OptimaleL�sung
	{
		return mi_OptimaleL�sung;
	}

	public JMenuItem getMi_L�sungDrucken()							//Methode zur R�ckgabe des Men�objektes mi_L�sungDrucken
	{
		return mi_L�sungDrucken;
	}

	public JMenuItem getMi_L�sungSpeichern()						//Methode zur R�ckgabe des Men�objektes mi_L�sungSpeichern
	{
		return mi_L�sungSpeichern;
	}

	public JMenuItem getMi_SolverConfig�ndern()						//Methode zur R�ckgabe des Men�objektes mi_SolverPfad
	{
		return mi_SolverConfig�ndern;
	}

	public JMenuItem getMi_PloHilfe()								//Methode zur R�ckgabe des Men�objektes mi_PloHilfe
	{
		return mi_PloHilfe;
	}

	public JMenuItem getMi_�ber()									//Methode zur R�ckgabe des Men�objektes mi_�ber
	{
		return mi_�ber;
	}

//*** Ende GET-Methoden  ***------------------------------------------
}
//*** Ende Klasse plo_MenuBar  ***------------------------------------