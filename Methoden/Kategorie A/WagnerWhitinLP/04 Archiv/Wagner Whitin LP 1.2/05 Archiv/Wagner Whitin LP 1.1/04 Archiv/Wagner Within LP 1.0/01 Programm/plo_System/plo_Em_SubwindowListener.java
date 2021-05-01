//*******************************************************************
//*		plo_Em_SubwindowListener.java								*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Listener zur Ereignisverwaltung		*
//*		in den Eeingabemasken						 				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//*** Klasse plo_Em_SubwindowListener ***-----------------------------
public class plo_Em_SubwindowListener implements InternalFrameListener
{
	private plo_Hauptfenster root;									//Referenzobjekte f�r Aufrufe
	private plo_Eingabemaske rootEm;								//in �bergeordneten Hierarchieschichten
//*** Konstruktor ***-------------------------------------------------
	public plo_Em_SubwindowListener(plo_Hauptfenster base, plo_Eingabemaske baseEm)
	{
		this.setRoot(base);											//Initialisieren der Referenzobjekte
		this.setRootEm(baseEm);										//
	}
//*** Ende Konstruktor ***--------------------------------------------

//*** SET-Methoden ***------------------------------------------------
	public void setRoot(plo_Hauptfenster elem)						//Methode zum Setzen des Referenzobjektes auf das Hauptfenster
	{
		root = elem;
	}

	public void setRootEm(plo_Eingabemaske elem)					//Methode zum Setzen des Referenzobjektes auf das Eingabemaskenfenster
	{
		rootEm = elem;
	}
//*** Ende SET-Methoden ***-------------------------------------------

//*** GET-Methoden ***------------------------------------------------
	public plo_Hauptfenster getRoot()								//Methode zur R�ckgabe des Referenzobjektes auf das Hauptfenster
	{
		return root;
	}

	public plo_Eingabemaske getRootEm()								//Methode zur R�ckgabe des Referenzobjektes auf das Eingabemaskenfenster
	{
		return rootEm;
	}
//*** Ende GET-Methoden ***-------------------------------------------

//*** Ereignismethoden ***--------------------------------------------
    public void internalFrameClosing(InternalFrameEvent ifevt)
    {

    }

	public void internalFrameClosed(InternalFrameEvent ifevt)		//Wird dei Eingabemaske geschlossen
	{																//wird die Anzahl der Eingabemasken im Hauptfenster verringert
		this.getRoot().setAnzahlEingabemasken(this.getRoot().getAnzahlEingabemasken()-1);
		this.getRoot().checkValidWindows();							//und zum die Men�s in g�ltigem Zustand zu halten
	}																//die Pr�fmethode "checkValidWindows" im Hauptfenster angesto�en

	public void internalFrameOpened(InternalFrameEvent ifevt)
	{

	}

	public void internalFrameIconified(InternalFrameEvent ifevt)
	{

	}

	public void internalFrameDeiconified(InternalFrameEvent ifevt)
	{

	}

	public void internalFrameActivated(InternalFrameEvent ifevt)								//Bekommt die Eingabemaske den Fokus
	{																							//
		this.getRoot().setInternalFocus((plo_Eingabemaske)ifevt.getSource());					//Wird das der Zustand des Hauptmen�s
		if(this.getRootEm().getEm_IsOptimized() == true)										//dem Status der Eingabemaske angepa�t
		{																						//
			this.getRoot().getPlo_HauptFensterMen�().getMi_L�sungDrucken().setEnabled(true);	//
			this.getRoot().getPlo_HauptFensterMen�().getMi_L�sungSpeichern().setEnabled(true);	//
		}																						//
		this.getRoot().getPlo_HauptFensterMen�().getMi_ModellSpeichern().setEnabled(true);		//
//		this.getRoot().getPlo_HauptFensterMen�().getMi_ModellDrucken().setEnabled(true);		//
		this.getRoot().getPlo_HauptFensterMen�().getMi_NachfrageEinf�gen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMen�().getMi_NachfrageEntfernen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMen�().getMi_Alles�ndern().setEnabled(true);			//
		this.getRoot().getPlo_HauptFensterMen�().getMi_OptimaleL�sung().setEnabled(true);		//
	}																							//

	public void internalFrameDeactivated(InternalFrameEvent ifevt)
	{

	}
//*** Ende Ereignismethoden ***---------------------------------------
}
//*** Klasse plo_Em_SubwindowListener ***-----------------------------