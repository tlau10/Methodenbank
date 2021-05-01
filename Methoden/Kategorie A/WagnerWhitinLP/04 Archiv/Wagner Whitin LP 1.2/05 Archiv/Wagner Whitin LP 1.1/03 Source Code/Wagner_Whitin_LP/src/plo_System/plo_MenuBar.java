//*******************************************************************
//*		plo_MenuBar.java											*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthuelt die Klasse des Drop-Down- Menues im 		*
//*		Applikationshauptfenster									*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import javax.swing.*;

//*** Klasse plo_MenuBar  ***----------------------------------------
public class plo_MenuBar extends JMenuBar{

	private static final long serialVersionUID = -6159898198181651674L;

	private plo_Hauptfenster base;									//Referenzobjekt fuer Hierarchieschritte nach oben

	private JMenu m_Lagermodell;									//Menues
	private JMenu m_Bearbeiten;
	private JMenu m_Loesung;
	private JMenu m_Solver;
	private JMenu m_Hilfe;

	private JMenuItem mi_NeuesModell;								//Einzelne Menuepunkte
	private JMenuItem mi_ModellLaden;
	private JMenuItem mi_ModellSpeichern;
	private JMenuItem mi_ModellDrucken;

	private JMenuItem mi_NachfrageEinfuegen;
	private JMenuItem mi_NachfrageEntfernen;
	private JMenuItem mi_AllesAendern;
	private JMenuItem mi_Defaultkosten;

	private JMenuItem mi_OptimaleLoesung;
	private JMenuItem mi_LoesungDrucken;
	private JMenuItem mi_LoesungSpeichern;

	private JMenuItem mi_SolverConfigAendern;

	private JMenuItem mi_PloHilfe;
	private JMenuItem mi_ueber;

	private plo_MenuBarListener menuItemListener;
//*** Konstruktor  ***------------------------------------------------
	public plo_MenuBar(plo_Hauptfenster main)
	{
		base = main;												//Initialisieren des Referenzobjektes

		menuItemListener = new plo_MenuBarListener(this);

		m_Lagermodell = new JMenu("Lagermodell");					//Initialisieren der Menues
		m_Bearbeiten = new JMenu("Bearbeiten");
		m_Loesung = new JMenu("Lösung");
		m_Solver = new JMenu("Solver");
		m_Hilfe = new JMenu("Hilfe");

		mi_NeuesModell = new JMenuItem("Neues Modell");				//Initialisieren der Menuepunkte
		mi_ModellLaden = new JMenuItem("Modell laden");
		mi_ModellSpeichern = new JMenuItem("Model speichern");
		mi_ModellSpeichern.setEnabled(false);
		mi_ModellDrucken = new JMenuItem("Modell drucken");
		mi_ModellDrucken.setEnabled(false);

		mi_NachfrageEinfuegen = new JMenuItem("Nachfrage einfügen");
		mi_NachfrageEinfuegen.setEnabled(false);
		mi_NachfrageEntfernen = new JMenuItem("Nachfrage entfernen");
		mi_NachfrageEntfernen.setEnabled(false);
		mi_AllesAendern = new JMenuItem("Alles ändern");
		mi_AllesAendern.setEnabled(false);
		mi_Defaultkosten = new JMenuItem("Defaultkosten");

		mi_OptimaleLoesung = new JMenuItem("Optimale Lösung berechnen");
		mi_OptimaleLoesung.setEnabled(false);
		mi_LoesungDrucken = new JMenuItem("Lösung drucken");
		mi_LoesungDrucken.setEnabled(false);
		mi_LoesungSpeichern = new JMenuItem("Lösung speichern");
		mi_LoesungSpeichern.setEnabled(false);

		mi_SolverConfigAendern = new JMenuItem("Solver Konfiguration ändern");
		mi_SolverConfigAendern.setEnabled(true);

		mi_PloHilfe = new JMenuItem("PLO-Hilfe");
		mi_ueber = new JMenuItem("Über");

		mi_NeuesModell.addActionListener(menuItemListener);			//Action- und Itemlistener an die
		mi_NeuesModell.addItemListener(menuItemListener);			//Menuepunkte binden
		mi_ModellLaden.addActionListener(menuItemListener);
		mi_ModellLaden.addItemListener(menuItemListener);
		mi_ModellSpeichern.addActionListener(menuItemListener);
		mi_ModellSpeichern.addItemListener(menuItemListener);
		mi_ModellDrucken.addActionListener(menuItemListener);
		mi_ModellDrucken.addItemListener(menuItemListener);
		mi_NachfrageEinfuegen.addActionListener(menuItemListener);
		mi_NachfrageEinfuegen.addItemListener(menuItemListener);
		mi_NachfrageEntfernen.addActionListener(menuItemListener);
		mi_NachfrageEntfernen.addItemListener(menuItemListener);
		mi_AllesAendern.addActionListener(menuItemListener);
		mi_AllesAendern.addItemListener(menuItemListener);
		mi_Defaultkosten.addActionListener(menuItemListener);
		mi_Defaultkosten.addItemListener(menuItemListener);
		mi_OptimaleLoesung.addActionListener(menuItemListener);
		mi_OptimaleLoesung.addItemListener(menuItemListener);
		mi_LoesungDrucken.addActionListener(menuItemListener);
		mi_LoesungDrucken.addItemListener(menuItemListener);
		mi_LoesungSpeichern.addActionListener(menuItemListener);
		mi_LoesungSpeichern.addItemListener(menuItemListener);
		mi_SolverConfigAendern.addActionListener(menuItemListener);
		mi_SolverConfigAendern.addItemListener(menuItemListener);
		mi_PloHilfe.addActionListener(menuItemListener);
		mi_PloHilfe.addItemListener(menuItemListener);
		mi_ueber.addActionListener(menuItemListener);
		mi_ueber.addItemListener(menuItemListener);

		m_Lagermodell.add(mi_NeuesModell);							//Binden der Lagermodell-Menuepunkte
		m_Lagermodell.addSeparator();								//an das Lagermodellmenue
		m_Lagermodell.add(mi_ModellLaden);
		m_Lagermodell.add(mi_ModellSpeichern);
		m_Lagermodell.add(mi_ModellDrucken);

		m_Bearbeiten.add(mi_NachfrageEinfuegen);						//Binden der Bearbeiten-Menuepunkte
		m_Bearbeiten.add(mi_NachfrageEntfernen);					//an das Bearbeitenmenue
		m_Bearbeiten.addSeparator();
		m_Bearbeiten.add(mi_AllesAendern);
		m_Bearbeiten.addSeparator();
		m_Bearbeiten.add(mi_Defaultkosten);

		m_Loesung.add(mi_OptimaleLoesung);							//Binden der Loesungs-Menuepunkte
		m_Loesung.addSeparator();									//an das Loesungsmenue
		m_Loesung.add(mi_LoesungDrucken);
		m_Loesung.add(mi_LoesungSpeichern);

		m_Solver.add(mi_SolverConfigAendern);						//Binden der Solver-Menuepunkte an
																	//an das Solvermenue

		m_Hilfe.add(mi_PloHilfe);									//Binden der Hilfe_Menuepunkte
		m_Hilfe.addSeparator();										//an das Hilfemenue
		m_Hilfe.add(mi_ueber);

		this. add(m_Lagermodell);									//Hinzufuegen der Menues an den
		this. add(m_Bearbeiten);									//Menubar
		this. add(m_Loesung);
		this. add(m_Solver);
		this. add(m_Hilfe);
	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** SET-Methoden  ***-----------------------------------------------

//*** Ende SET-Methoden  ***------------------------------------------

//*** GET-Methoden  ***-----------------------------------------------
	public plo_Hauptfenster getBase()								//Methode zur Rueckgabe des Referenzobjekts auf das Hauptobjekt
	{
		return base;
	}

	public JMenuItem getMi_NeuesModell()							//Methode zur Rueckgabe des Menueobjektes mi_NeuesModell
	{
		return mi_NeuesModell;
	}

	public JMenuItem getMi_ModellLaden()							//Methode zur Rueckgabe des Menueobjektes mi_ModellLaden
	{
		return mi_ModellLaden;
	}

	public JMenuItem getMi_ModellSpeichern()						//Methode zur Rueckgabe des Menueobjektes mi_ModellSpeichern
	{
		return mi_ModellSpeichern;
	}

	public JMenuItem getMi_ModellDrucken()							//Methode zur Rueckgabe des Menueobjektes mi_ModellDrucken
	{
		return mi_ModellDrucken;
	}

	public JMenuItem getMi_NachfrageEinfuegen()						//Methode zur Rueckgabe des Menueobjektes mi_NachfrageEinfuegen
	{
		return mi_NachfrageEinfuegen;
	}

	public JMenuItem getMi_NachfrageEntfernen()						//Methode zur Rueckgabe des Menueobjektes mi_NachfrageEntfernen
	{
		return mi_NachfrageEntfernen;
	}

	public JMenuItem getMi_AllesAendern()							//Methode zur Rueckgabe des Menueobjektes mi_AllesAendern
	{
		return mi_AllesAendern;
	}

	public JMenuItem getMi_Defaultkosten()							//Methode zur Rueckgabe des Menueobjektes mi_Defaultkosten
	{
		return mi_Defaultkosten;
	}

	public JMenuItem getMi_OptimaleLoesung()							//Methode zur Rueckgabe des Menueobjektes mi_OptimaleLoesung
	{
		return mi_OptimaleLoesung;
	}

	public JMenuItem getMi_LoesungDrucken()							//Methode zur Rueckgabe des Menueobjektes mi_LoesungDrucken
	{
		return mi_LoesungDrucken;
	}

	public JMenuItem getMi_LoesungSpeichern()						//Methode zur Rueckgabe des Menueobjektes mi_LoesungSpeichern
	{
		return mi_LoesungSpeichern;
	}

	public JMenuItem getMi_SolverConfigAendern()						//Methode zur Rueckgabe des Menueobjektes mi_SolverPfad
	{
		return mi_SolverConfigAendern;
	}

	public JMenuItem getMi_PloHilfe()								//Methode zur Rueckgabe des Menueobjektes mi_PloHilfe
	{
		return mi_PloHilfe;
	}

	public JMenuItem getMi_ueber()									//Methode zur Rueckgabe des Menueobjektes mi_ueber
	{
		return mi_ueber;
	}

//*** Ende GET-Methoden  ***------------------------------------------
}
//*** Ende Klasse plo_MenuBar  ***------------------------------------