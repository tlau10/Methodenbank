//*******************************************************************
//*		plo_MenuBar.java											*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Göltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse des Drop-Down- Menüs im 		*
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
	private plo_Hauptfenster base;									//Referenzobjekt für Hierarchieschritte nach oben

	private JMenu m_Lagermodell;									//Menüs
	private JMenu m_Bearbeiten;
	private JMenu m_Lösung;
	private JMenu m_Solver;
	private JMenu m_Hilfe;

	private JMenuItem mi_NeuesModell;								//Einzelne Menüpunkte
	private JMenuItem mi_ModellLaden;
	private JMenuItem mi_ModellSpeichern;
	private JMenuItem mi_ModellDrucken;

	private JMenuItem mi_NachfrageEinfügen;
	private JMenuItem mi_NachfrageEntfernen;
	private JMenuItem mi_AllesÄndern;
	private JMenuItem mi_Defaultkosten;

	private JMenuItem mi_OptimaleLösung;
	private JMenuItem mi_LösungDrucken;
	private JMenuItem mi_LösungSpeichern;

	private JMenuItem mi_SolverConfigÄndern;

	private JMenuItem mi_PloHilfe;
	private JMenuItem mi_Über;

	private plo_MenuBarListener menuItemListener;
//*** Konstruktor  ***------------------------------------------------
	public plo_MenuBar(plo_Hauptfenster main)
	{
		base = main;												//Initialisieren des Referenzobjektes

		menuItemListener = new plo_MenuBarListener(this);

		m_Lagermodell = new JMenu("Lagermodell");					//Initialisieren der Menüs
		m_Bearbeiten = new JMenu("Bearbeiten");
		m_Lösung = new JMenu("Lösung");
		m_Solver = new JMenu("Solver");
		m_Hilfe = new JMenu("Hilfe");

		mi_NeuesModell = new JMenuItem("Neues Modell");				//Initialisieren der Menüpunkte
		mi_ModellLaden = new JMenuItem("Modell laden");
		mi_ModellSpeichern = new JMenuItem("Model Speichern");
		mi_ModellSpeichern.setEnabled(false);
		mi_ModellDrucken = new JMenuItem("Modell Drucken");
		mi_ModellDrucken.setEnabled(false);

		mi_NachfrageEinfügen = new JMenuItem("Nachfrage einfügen");
		mi_NachfrageEinfügen.setEnabled(false);
		mi_NachfrageEntfernen = new JMenuItem("Nachfrage entfernen");
		mi_NachfrageEntfernen.setEnabled(false);
		mi_AllesÄndern = new JMenuItem("Alles ändern");
		mi_AllesÄndern.setEnabled(false);
		mi_Defaultkosten = new JMenuItem("Defaultkosten");

		mi_OptimaleLösung = new JMenuItem("Optimale Lösung");
		mi_OptimaleLösung.setEnabled(false);
		mi_LösungDrucken = new JMenuItem("Lösung drucken");
		mi_LösungDrucken.setEnabled(false);
		mi_LösungSpeichern = new JMenuItem("Lösung speichern");
		mi_LösungSpeichern.setEnabled(false);

		mi_SolverConfigÄndern = new JMenuItem("Solver Konfiguration ändern");
		mi_SolverConfigÄndern.setEnabled(true);

		mi_PloHilfe = new JMenuItem("PLO-Hilfe");
		mi_Über = new JMenuItem("Über...");

		mi_NeuesModell.addActionListener(menuItemListener);			//Action- und Itemlistener an die
		mi_NeuesModell.addItemListener(menuItemListener);			//Menüpunkte binden
		mi_ModellLaden.addActionListener(menuItemListener);
		mi_ModellLaden.addItemListener(menuItemListener);
		mi_ModellSpeichern.addActionListener(menuItemListener);
		mi_ModellSpeichern.addItemListener(menuItemListener);
		mi_ModellDrucken.addActionListener(menuItemListener);
		mi_ModellDrucken.addItemListener(menuItemListener);
		mi_NachfrageEinfügen.addActionListener(menuItemListener);
		mi_NachfrageEinfügen.addItemListener(menuItemListener);
		mi_NachfrageEntfernen.addActionListener(menuItemListener);
		mi_NachfrageEntfernen.addItemListener(menuItemListener);
		mi_AllesÄndern.addActionListener(menuItemListener);
		mi_AllesÄndern.addItemListener(menuItemListener);
		mi_Defaultkosten.addActionListener(menuItemListener);
		mi_Defaultkosten.addItemListener(menuItemListener);
		mi_OptimaleLösung.addActionListener(menuItemListener);
		mi_OptimaleLösung.addItemListener(menuItemListener);
		mi_LösungDrucken.addActionListener(menuItemListener);
		mi_LösungDrucken.addItemListener(menuItemListener);
		mi_LösungSpeichern.addActionListener(menuItemListener);
		mi_LösungSpeichern.addItemListener(menuItemListener);
		mi_SolverConfigÄndern.addActionListener(menuItemListener);
		mi_SolverConfigÄndern.addItemListener(menuItemListener);
		mi_PloHilfe.addActionListener(menuItemListener);
		mi_PloHilfe.addItemListener(menuItemListener);
		mi_Über.addActionListener(menuItemListener);
		mi_Über.addItemListener(menuItemListener);

		m_Lagermodell.add(mi_NeuesModell);							//Binden der Lagermodell-Menüpunkte
		m_Lagermodell.addSeparator();								//an das Lagermodellmenü
		m_Lagermodell.add(mi_ModellLaden);
		m_Lagermodell.add(mi_ModellSpeichern);
		m_Lagermodell.add(mi_ModellDrucken);

		m_Bearbeiten.add(mi_NachfrageEinfügen);						//Binden der Bearbeiten-Menüpunkte
		m_Bearbeiten.add(mi_NachfrageEntfernen);					//an das Bearbeitenmenü
		m_Bearbeiten.addSeparator();
		m_Bearbeiten.add(mi_AllesÄndern);
		m_Bearbeiten.addSeparator();
		m_Bearbeiten.add(mi_Defaultkosten);

		m_Lösung.add(mi_OptimaleLösung);							//Binden der Lösungs-Menüpunkte
		m_Lösung.addSeparator();									//an das Lösungsmenü
		m_Lösung.add(mi_LösungDrucken);
		m_Lösung.add(mi_LösungSpeichern);

		m_Solver.add(mi_SolverConfigÄndern);						//Binden der Solver-Menüpunkte an
																	//an das Solvermenü

		m_Hilfe.add(mi_PloHilfe);									//Binden der Hilfe_Menüpunkte
		m_Hilfe.addSeparator();										//an das Hilfemenü
		m_Hilfe.add(mi_Über);

		this. add(m_Lagermodell);									//Hinzufügen der Menüs an den
		this. add(m_Bearbeiten);									//Menubar
		this. add(m_Lösung);
		this. add(m_Solver);
		this. add(m_Hilfe);
	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** SET-Methoden  ***-----------------------------------------------

//*** Ende SET-Methoden  ***------------------------------------------

//*** GET-Methoden  ***-----------------------------------------------
	public plo_Hauptfenster getBase()								//Methode zur Rückgabe des Referenzobjekts auf das Hauptobjekt
	{
		return base;
	}

	public JMenuItem getMi_NeuesModell()							//Methode zur Rückgabe des Menüobjektes mi_NeuesModell
	{
		return mi_NeuesModell;
	}

	public JMenuItem getMi_ModellLaden()							//Methode zur Rückgabe des Menüobjektes mi_ModellLaden
	{
		return mi_ModellLaden;
	}

	public JMenuItem getMi_ModellSpeichern()						//Methode zur Rückgabe des Menüobjektes mi_ModellSpeichern
	{
		return mi_ModellSpeichern;
	}

	public JMenuItem getMi_ModellDrucken()							//Methode zur Rückgabe des Menüobjektes mi_ModellDrucken
	{
		return mi_ModellDrucken;
	}

	public JMenuItem getMi_NachfrageEinfügen()						//Methode zur Rückgabe des Menüobjektes mi_NachfrageEinfügen
	{
		return mi_NachfrageEinfügen;
	}

	public JMenuItem getMi_NachfrageEntfernen()						//Methode zur Rückgabe des Menüobjektes mi_NachfrageEntfernen
	{
		return mi_NachfrageEntfernen;
	}

	public JMenuItem getMi_AllesÄndern()							//Methode zur Rückgabe des Menüobjektes mi_AllesÄndern
	{
		return mi_AllesÄndern;
	}

	public JMenuItem getMi_Defaultkosten()							//Methode zur Rückgabe des Menüobjektes mi_Defaultkosten
	{
		return mi_Defaultkosten;
	}

	public JMenuItem getMi_OptimaleLösung()							//Methode zur Rückgabe des Menüobjektes mi_OptimaleLösung
	{
		return mi_OptimaleLösung;
	}

	public JMenuItem getMi_LösungDrucken()							//Methode zur Rückgabe des Menüobjektes mi_LösungDrucken
	{
		return mi_LösungDrucken;
	}

	public JMenuItem getMi_LösungSpeichern()						//Methode zur Rückgabe des Menüobjektes mi_LösungSpeichern
	{
		return mi_LösungSpeichern;
	}

	public JMenuItem getMi_SolverConfigÄndern()						//Methode zur Rückgabe des Menüobjektes mi_SolverPfad
	{
		return mi_SolverConfigÄndern;
	}

	public JMenuItem getMi_PloHilfe()								//Methode zur Rückgabe des Menüobjektes mi_PloHilfe
	{
		return mi_PloHilfe;
	}

	public JMenuItem getMi_Über()									//Methode zur Rückgabe des Menüobjektes mi_Über
	{
		return mi_Über;
	}

//*** Ende GET-Methoden  ***------------------------------------------
}
//*** Ende Klasse plo_MenuBar  ***------------------------------------