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
import javax.swing.*;

//*** Klasse plo_MenuBar  ************************************************************************************************************
public class plo_MenuBar extends JMenuBar{

	private static final long serialVersionUID = -6159898198181651674L;
   
   //Referenzobjekt fuer Hierarchieschritte nach oben
	private plo_Hauptfenster base;									

   //Menues
	private JMenu m_Lagermodell;									
	private JMenu m_Bearbeiten;
	private JMenu m_Loesung;
	private JMenu m_Solver;
	private JMenu m_Hilfe;

   //Einzelne Menuepunkte
	private JMenuItem mi_NeuesModell;								
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
//	private JMenuItem mi_handbuch;

	private plo_MenuBarListener menuItemListener;
	
	
//*** Konstruktor  **********************************************************************************
	public plo_MenuBar(plo_Hauptfenster main)
	{
		//Initialisieren des Referenzobjektes
		base = main;												

		menuItemListener = new plo_MenuBarListener(this);

		//Initialisieren der Menues
		m_Lagermodell = new JMenu("Lagermodell");					
		m_Bearbeiten = new JMenu("Bearbeiten");
		m_Loesung = new JMenu("Lösung");
		m_Solver = new JMenu("Solver");
		m_Hilfe = new JMenu("Hilfe");

		//Initialisieren der Menuepunkte
		mi_NeuesModell = new JMenuItem("Neues Modell");				
		mi_ModellLaden = new JMenuItem("Modell laden");
		mi_ModellSpeichern = new JMenuItem("Model speichern");
		mi_ModellSpeichern.setEnabled(false);
		mi_ModellDrucken = new JMenuItem("Modell drucken");
		mi_ModellDrucken.setEnabled(false);

		mi_NachfrageEinfuegen = new JMenuItem("Nachfrage einfügen");
		mi_NachfrageEinfuegen.setEnabled(false);
		mi_NachfrageEntfernen = new JMenuItem("Nachfrage entfernen");
		mi_NachfrageEntfernen.setEnabled(false);
		mi_AllesAendern = new JMenuItem("Alles Ändern");
		mi_AllesAendern.setEnabled(false);
		mi_Defaultkosten = new JMenuItem("Defaultkosten");

		mi_OptimaleLoesung = new JMenuItem("Optimale Lösung berechnen");
		mi_OptimaleLoesung.setEnabled(false);
		mi_LoesungDrucken = new JMenuItem("Lösung drucken");
		mi_LoesungDrucken.setEnabled(false);
		mi_LoesungSpeichern = new JMenuItem("Lösung speichern");
		mi_LoesungSpeichern.setEnabled(false);

		mi_SolverConfigAendern = new JMenuItem("Solver Konfiguration Ändern");
		mi_SolverConfigAendern.setEnabled(true);

		mi_PloHilfe = new JMenuItem("PLO-Hilfe");
		mi_ueber = new JMenuItem("Über");
//		mi_handbuch = new JMenuItem("Benutzerhandbuch");

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
		mi_LoesungDrucken.addActionListener(menuItemListener); //+++++
		mi_LoesungDrucken.addItemListener(menuItemListener); //+++++
		mi_LoesungSpeichern.addActionListener(menuItemListener);
		mi_LoesungSpeichern.addItemListener(menuItemListener);
		mi_SolverConfigAendern.addActionListener(menuItemListener);
		mi_SolverConfigAendern.addItemListener(menuItemListener);
		mi_PloHilfe.addActionListener(menuItemListener);
		mi_PloHilfe.addItemListener(menuItemListener);
		mi_ueber.addActionListener(menuItemListener);
		mi_ueber.addItemListener(menuItemListener);
//		mi_handbuch.addActionListener(menuItemListener);
//		mi_handbuch.addItemListener(menuItemListener);
		
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
//		m_Hilfe.add(mi_handbuch);

		this. add(m_Lagermodell);									//Hinzufuegen der Menues an den
		this. add(m_Bearbeiten);									//Menubar
		this. add(m_Loesung);
		this. add(m_Solver);
		this. add(m_Hilfe);
	}
//*** Ende Konstruktor **************************************************************************

	
//*** KEINE SET-Methoden  ***********************************************************************


//*** GET-Methoden  *****************************************************************************
	
	//Methode zur Rueckgabe des Referenzobjekts auf das Hauptobjekt
	public plo_Hauptfenster getBase()								
	{
		return base;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_NeuesModell
	public JMenuItem getMi_NeuesModell()							
	{
		return mi_NeuesModell;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_ModellLaden
	public JMenuItem getMi_ModellLaden()							
	{
		return mi_ModellLaden;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_ModellSpeichern
	public JMenuItem getMi_ModellSpeichern()						
	{
		return mi_ModellSpeichern;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_ModellDrucken
	public JMenuItem getMi_ModellDrucken()							
	{
		return mi_ModellDrucken;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_NachfrageEinfuegen
	public JMenuItem getMi_NachfrageEinfuegen()						
	{
		return mi_NachfrageEinfuegen;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_NachfrageEntfernen
	public JMenuItem getMi_NachfrageEntfernen()						
	{
		return mi_NachfrageEntfernen;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_AllesAendern
	public JMenuItem getMi_AllesAendern()							
	{
		return mi_AllesAendern;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_Defaultkosten
	public JMenuItem getMi_Defaultkosten()							
	{
		return mi_Defaultkosten;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_OptimaleLoesung
	public JMenuItem getMi_OptimaleLoesung()							
	{
		return mi_OptimaleLoesung;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_LoesungDrucken
	public JMenuItem getMi_LoesungDrucken()							
	{
		return mi_LoesungDrucken;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_LoesungSpeichern
	public JMenuItem getMi_LoesungSpeichern()						
	{
		return mi_LoesungSpeichern;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_SolverPfad
	public JMenuItem getMi_SolverConfigAendern()						
	{
		return mi_SolverConfigAendern;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_PloHilfe
	public JMenuItem getMi_PloHilfe()								
	{
		return mi_PloHilfe;
	}

	//Methode zur Rueckgabe des Menueobjektes mi_ueber
	public JMenuItem getMi_ueber()									
	{
		return mi_ueber;
	}
	
//	public JMenuItem getMi_handbuch()
//	{
//		return mi_handbuch;
//	}

//*** Ende GET-Methoden  *****************************************************************************
	
}
//*** Ende Klasse plo_MenuBar  ************************************************************************************