//*******************************************************************
//*		plo_MenuBarListener.java									*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthuelt die Klasse des Ereignislisteners			*
//*		fuer Menueereignisse im Applikationsmenue					*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_MenuBarListener  ***---------------------------------
public class plo_MenuBarListener implements ItemListener, ActionListener
{
	private plo_MenuBar root;										//Referenzobjekt fuer Aufrufe im zugehoerigen MenueBalken

//*** Konstruktor  ***------------------------------------------------
	public plo_MenuBarListener(plo_MenuBar bar)
	{
		root = bar;													//Initialisieren des Refernzobjektes

	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** Ereignismethoden  ***-------------------------------------------
	public void actionPerformed(ActionEvent aevt)					//Bei Action Events im MenueBalken
	{
		JMenuItem compare = new JMenuItem();						//Feststellen, welcher Menuepunkt geklickt wurde
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
		if(compare.equals(root.getMi_NachfrageEinfuegen()))			//Bei Klick auf mi_NachfrageEinfuegen
		{
			root.getBase().nachfrageEinfuegen();						//Aufruf von "nachfrageEinfuegen" im Hauptfenster
		}
		if(compare.equals(root.getMi_NachfrageEntfernen()))			//Bei Klick auf mi_NachfrageEntfernen
		{
			root.getBase().nachfrageEntfernen();					//Aufruf von "nachfrageEntfernen" im Hauptfenster
		}
		if(compare.equals(root.getMi_AllesAendern()))				//Bei Klick auf mi_AllesAendern
		{
			root.getBase().allesAendern();							//Aufruf von "allesAendern" im Hauptfenster
		}
		if(compare.equals(root.getMi_Defaultkosten()))				//Bei Klick auf mi_Defaultkosten
		{
			root.getBase().defaultkosten();							//Aufruf von "defaultkosten" im Hauptfenster
		}
		if(compare.equals(root.getMi_OptimaleLoesung()))				//Bei Klick auf mi_OptimaleLoesung
		{
			root.getBase().optimaleLoesung();						//Aufruf von "optimaleLoesung" im Hauptfenster
		}
		if(compare.equals(root.getMi_LoesungSpeichern()))			//Bei Klick auf mi_LoesungSpeichern
		{
			root.getBase().loesungSpeichern();						//Aufruf von "loesungSpeichern" im Hauptfenster
		}
		if(compare.equals(root.getMi_SolverConfigAendern()))			//Bei Klick auf mi_SolverPfad
		{
			root.getBase().solverConfigAendern();					//Aufruf von "solverPfad" im Hauptfenster
		}
		if(compare.equals(root.getMi_LoesungDrucken()))				//Bei Klick auf mi_LoesungDrucken
		{
			root.getBase().loesungDrucken();							//Aufruf von "loesungDrucken" im Hauptfenster
		}
		if(compare.equals(root.getMi_PloHilfe()))					//Bei Klick auf mi_PloHilfe
		{
			root.getBase().ploHilfe();								//Aufruf von "ploHilfe" im Hauptfenster
		}
		if(compare.equals(root.getMi_ueber()))						//Bei Klick auf mi_ueber
		{
			root.getBase().ueber();									//Aufruf von "ueber" im Hauptfenster
		}
	}

	public void itemStateChanged(ItemEvent ievt)
	{

	}
//*** Ende Ereignismethoden  ***--------------------------------------
}
//*** Ende Klasse plo_MenuBarListener  ***----------------------------