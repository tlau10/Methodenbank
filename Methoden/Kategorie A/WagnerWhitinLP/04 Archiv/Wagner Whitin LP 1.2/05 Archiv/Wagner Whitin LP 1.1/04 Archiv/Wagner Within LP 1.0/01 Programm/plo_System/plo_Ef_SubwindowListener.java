//*******************************************************************
//*		plo_Ef_SubwindowListener.java								*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Listener zur Ereignisverwaltung		*
//*		in den Ergebnisfenstern						 				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//*** Klasse plo_Ef_SubwindowListener ***-----------------------------
public class plo_Ef_SubwindowListener implements InternalFrameListener, AdjustmentListener
{
	private plo_Hauptfenster root;									//Referenzobjekte f�r den Methodenaufruf
	private plo_Ergebnisfenster rootEf;								//in oberen Hierarchiestufen
//*** Konstruktor ***-------------------------------------------------
	public plo_Ef_SubwindowListener(plo_Hauptfenster base, plo_Ergebnisfenster baseEf)
	{																//Initialisieren der Referenzobjekte
		this.setRoot(base);											//
		this.setRootEf(baseEf);										//
	}
//*** Ende Konstruktor ***--------------------------------------------

//*** SET-Methoden ***------------------------------------------------
	public void setRoot(plo_Hauptfenster elem)						//Methode zum Setzen des Referenzobjekts auf das Hauptfenster
	{
		root = elem;
	}

	public void setRootEf(plo_Ergebnisfenster elem)					//Methode zum Setzen des Referenzobjekts auf das zugeh�rige Ergebnisfenster
	{
		rootEf = elem;
	}
//*** Ende SET-Methoden ***-------------------------------------------

//*** GET-Methoden ***------------------------------------------------
	public plo_Hauptfenster getRoot()								//Methode zur R�ckgabe des Referenzobjekts auf das Hauptfenster
	{
		return root;
	}

	public plo_Ergebnisfenster getRootEf()							//Methode zur R�ckgabe des Referenzobjekts auf das zugeh�rige Ergebnisfenster
	{
		return rootEf;
	}
//*** Ende GET-Methoden ***-------------------------------------------

//*** Ereignis-Methoden ***-------------------------------------------
    public void internalFrameClosing(InternalFrameEvent ifevt)		//
    {

    }

	public void internalFrameClosed(InternalFrameEvent ifevt)		//Falls der Frame geschlossen wird
	{																//wird die Anzahl der Fenster in der Z�hlvariablen des
		this.getRoot().setAnzahlErgebnisfenster(this.getRoot().getAnzahlErgebnisfenster()-1);	//Hauptfensters verringert und
		this.getRoot().checkValidWindows();							//gepr�ft, ob noch genug Fenster offen sind oder ob
	}																//das Men� angepa�t werden mu�

	public void internalFrameOpened(InternalFrameEvent ifevt)		//
	{

	}

	public void internalFrameIconified(InternalFrameEvent ifevt)	//
	{

	}

	public void internalFrameDeiconified(InternalFrameEvent ifevt)	//
	{

	}

	public void internalFrameActivated(InternalFrameEvent ifevt)	//Falls der Frame fokussiert wird
	{																							//
		plo_Ergebnisfenster buffer = (plo_Ergebnisfenster)ifevt.getSource();					//Ermitteln, welches Objekt fokussiert wurde
		this.getRoot().setInternalFocus(buffer.getEf_Zugeh�rigeEingabemaske());					//Setzen des Referenzobjekts im Hauptfenster auf den aktivierten Frame
		if(this.getRootEf().getEf_Zugeh�rigeEingabemaske().getEm_IsOptimized() == true)			//Falls dieser schon gel�st worden ist
		{
			this.getRoot().getPlo_HauptFensterMen�().getMi_L�sungDrucken().setEnabled(true);	//Enable des L�sungsmen�s
			this.getRoot().getPlo_HauptFensterMen�().getMi_L�sungSpeichern().setEnabled(true);	//
		}
		this.getRoot().getPlo_HauptFensterMen�().getMi_ModellSpeichern().setEnabled(true);		//Enablen der anderen Men�s
		this.getRoot().getPlo_HauptFensterMen�().getMi_ModellDrucken().setEnabled(true);		//
		this.getRoot().getPlo_HauptFensterMen�().getMi_NachfrageEinf�gen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMen�().getMi_NachfrageEntfernen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMen�().getMi_Alles�ndern().setEnabled(true);			//
		this.getRoot().getPlo_HauptFensterMen�().getMi_OptimaleL�sung().setEnabled(true);		//
	}

	public void internalFrameDeactivated(InternalFrameEvent ifevt)								//
	{

	}

	public void adjustmentValueChanged(AdjustmentEvent aevt)									//
	{

	}
//*** Ende Ereignis-Methoden ***--------------------------------------
}
//*** Ende Klasse plo_Ef_SubwindowListener ***------------------------