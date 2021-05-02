//*******************************************************************
//*		plo_MenuBarListener.java									*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Klasse des Ereignislisteners			*
//*		f�r Men�ereignisse im Applikationsmen�						*
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
	private plo_MenuBar root;										//Referenzobjekt f�r Aufrufe im zugeh�rigen Men�Balken

//*** Konstruktor  ***------------------------------------------------
	public plo_MenuBarListener(plo_MenuBar bar)
	{
		root = bar;													//Initialisieren des Refernzobjektes

	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** Ereignismethoden  ***-------------------------------------------
	public void actionPerformed(ActionEvent aevt)					//Bei Action Events im Men�Balken
	{
		JMenuItem compare = new JMenuItem();						//Feststellen, welcher Men�punkt geklickt wurde
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
		if(compare.equals(root.getMi_NachfrageEinf�gen()))			//Bei Klick auf mi_NachfrageEinf�gen
		{
			root.getBase().nachfrageEinf�gen();						//Aufruf von "nachfrageEinf�gen" im Hauptfenster
		}
		if(compare.equals(root.getMi_NachfrageEntfernen()))			//Bei Klick auf mi_NachfrageEntfernen
		{
			root.getBase().nachfrageEntfernen();					//Aufruf von "nachfrageEntfernen" im Hauptfenster
		}
		if(compare.equals(root.getMi_Alles�ndern()))				//Bei Klick auf mi_Alles�ndern
		{
			root.getBase().alles�ndern();							//Aufruf von "alles�ndern" im Hauptfenster
		}
		if(compare.equals(root.getMi_Defaultkosten()))				//Bei Klick auf mi_Defaultkosten
		{
			root.getBase().defaultkosten();							//Aufruf von "defaultkosten" im Hauptfenster
		}
		if(compare.equals(root.getMi_OptimaleL�sung()))				//Bei Klick auf mi_OptimaleL�sung
		{
			root.getBase().optimaleL�sung();						//Aufruf von "optimaleL�sung" im Hauptfenster
		}
		if(compare.equals(root.getMi_L�sungSpeichern()))			//Bei Klick auf mi_L�sungSpeichern
		{
			root.getBase().l�sungSpeichern();						//Aufruf von "l�sungSpeichern" im Hauptfenster
		}
		if(compare.equals(root.getMi_SolverConfig�ndern()))			//Bei Klick auf mi_SolverPfad
		{
			root.getBase().solverConfig�ndern();					//Aufruf von "solverPfad" im Hauptfenster
		}
		if(compare.equals(root.getMi_L�sungDrucken()))				//Bei Klick auf mi_L�sungDrucken
		{
			root.getBase().l�sungDrucken();							//Aufruf von "l�sungDrucken" im Hauptfenster
		}
		if(compare.equals(root.getMi_PloHilfe()))					//Bei Klick auf mi_PloHilfe
		{
			root.getBase().ploHilfe();								//Aufruf von "ploHilfe" im Hauptfenster
		}
		if(compare.equals(root.getMi_�ber()))						//Bei Klick auf mi_�ber
		{
			root.getBase().�ber();									//Aufruf von "�ber" im Hauptfenster
		}
	}

	public void itemStateChanged(ItemEvent ievt)
	{

	}
//*** Ende Ereignismethoden  ***--------------------------------------
}
//*** Ende Klasse plo_MenuBarListener  ***----------------------------