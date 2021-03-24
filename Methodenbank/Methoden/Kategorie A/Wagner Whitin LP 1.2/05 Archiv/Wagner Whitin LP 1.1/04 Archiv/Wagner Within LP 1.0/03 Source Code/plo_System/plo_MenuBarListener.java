//*******************************************************************
//*		plo_MenuBarListener.java									*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Göltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse des Ereignislisteners			*
//*		für Menüereignisse im Applikationsmenü						*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//*** Klasse plo_MenuBarListener  ***---------------------------------
public class plo_MenuBarListener implements ItemListener, ActionListener
{
	private plo_MenuBar root;										//Referenzobjekt für Aufrufe im zugehörigen MenüBalken

//*** Konstruktor  ***------------------------------------------------
	public plo_MenuBarListener(plo_MenuBar bar)
	{
		root = bar;													//Initialisieren des Refernzobjektes

	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** Ereignismethoden  ***-------------------------------------------
	public void actionPerformed(ActionEvent aevt)					//Bei Action Events im MenüBalken
	{
		JMenuItem compare = new JMenuItem();						//Feststellen, welcher Menüpunkt geklickt wurde
		compare = (JMenuItem)aevt.getSource();						//
		if(compare.equals(root.getMi_NeuesModell()))				//Bei Klick auf mi_NeuesModell
		{
			root.getBase().neuesModellDialog();						//Aufruf von "neuesModellDialog" im Hauptfenster
		}
		if(compare.equals(root.getMi_ModellLaden()))				//Bei Klick auf mi_ModellLaden
		{
			root.getBase().modellLaden();							//Aufruf von "modellLaden" im Hauptfenster
		}
		if(compare.equals(root.getMi_ModellSpeichern()))			//Bei Klick auf mi_ModellSpeichern
		{
			root.getBase().modellSpeichern();						//Aufruf von "modellSpeichern" im Hauptfenster
		}
		if(compare.equals(root.getMi_ModellDrucken()))				//Bei Klick auf mi_ModellDrucken
		{
			root.getBase().modellDrucken();							//Aufruf von "modellDrucken" im Hauptfenster
		}
		if(compare.equals(root.getMi_NachfrageEinfügen()))			//Bei Klick auf mi_NachfrageEinfügen
		{
			root.getBase().nachfrageEinfügen();						//Aufruf von "nachfrageEinfügen" im Hauptfenster
		}
		if(compare.equals(root.getMi_NachfrageEntfernen()))			//Bei Klick auf mi_NachfrageEntfernen
		{
			root.getBase().nachfrageEntfernen();					//Aufruf von "nachfrageEntfernen" im Hauptfenster
		}
		if(compare.equals(root.getMi_AllesÄndern()))				//Bei Klick auf mi_AllesÄndern
		{
			root.getBase().allesÄndern();							//Aufruf von "allesÄndern" im Hauptfenster
		}
		if(compare.equals(root.getMi_Defaultkosten()))				//Bei Klick auf mi_Defaultkosten
		{
			root.getBase().defaultkosten();							//Aufruf von "defaultkosten" im Hauptfenster
		}
		if(compare.equals(root.getMi_OptimaleLösung()))				//Bei Klick auf mi_OptimaleLösung
		{
			root.getBase().optimaleLösung();						//Aufruf von "optimaleLösung" im Hauptfenster
		}
		if(compare.equals(root.getMi_LösungSpeichern()))			//Bei Klick auf mi_LösungSpeichern
		{
			root.getBase().lösungSpeichern();						//Aufruf von "lösungSpeichern" im Hauptfenster
		}
		if(compare.equals(root.getMi_SolverConfigÄndern()))			//Bei Klick auf mi_SolverPfad
		{
			root.getBase().solverConfigÄndern();					//Aufruf von "solverPfad" im Hauptfenster
		}
		if(compare.equals(root.getMi_LösungDrucken()))				//Bei Klick auf mi_LösungDrucken
		{
			root.getBase().lösungDrucken();							//Aufruf von "lösungDrucken" im Hauptfenster
		}
		if(compare.equals(root.getMi_PloHilfe()))					//Bei Klick auf mi_PloHilfe
		{
			root.getBase().ploHilfe();								//Aufruf von "ploHilfe" im Hauptfenster
		}
		if(compare.equals(root.getMi_Über()))						//Bei Klick auf mi_Über
		{
			root.getBase().über();									//Aufruf von "über" im Hauptfenster
		}
	}

	public void itemStateChanged(ItemEvent ievt)
	{

	}
//*** Ende Ereignismethoden  ***--------------------------------------
}
//*** Ende Klasse plo_MenuBarListener  ***----------------------------