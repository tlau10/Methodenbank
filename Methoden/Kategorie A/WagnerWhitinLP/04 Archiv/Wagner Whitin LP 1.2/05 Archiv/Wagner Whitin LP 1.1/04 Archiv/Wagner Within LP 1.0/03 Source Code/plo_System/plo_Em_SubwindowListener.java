//*******************************************************************
//*		plo_Em_SubwindowListener.java								*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Göltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Listener zur Ereignisverwaltung		*
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
	private plo_Hauptfenster root;									//Referenzobjekte für Aufrufe
	private plo_Eingabemaske rootEm;								//in übergeordneten Hierarchieschichten
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
	public plo_Hauptfenster getRoot()								//Methode zur Rückgabe des Referenzobjektes auf das Hauptfenster
	{
		return root;
	}

	public plo_Eingabemaske getRootEm()								//Methode zur Rückgabe des Referenzobjektes auf das Eingabemaskenfenster
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
		this.getRoot().checkValidWindows();							//und zum die Menüs in gültigem Zustand zu halten
	}																//die Prüfmethode "checkValidWindows" im Hauptfenster angestoßen

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
		this.getRoot().setInternalFocus((plo_Eingabemaske)ifevt.getSource());					//Wird das der Zustand des Hauptmenüs
		if(this.getRootEm().getEm_IsOptimized() == true)										//dem Status der Eingabemaske angepaßt
		{																						//
			this.getRoot().getPlo_HauptFensterMenü().getMi_LösungDrucken().setEnabled(true);	//
			this.getRoot().getPlo_HauptFensterMenü().getMi_LösungSpeichern().setEnabled(true);	//
		}																						//
		this.getRoot().getPlo_HauptFensterMenü().getMi_ModellSpeichern().setEnabled(true);		//
//		this.getRoot().getPlo_HauptFensterMenü().getMi_ModellDrucken().setEnabled(true);		//
		this.getRoot().getPlo_HauptFensterMenü().getMi_NachfrageEinfügen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMenü().getMi_NachfrageEntfernen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMenü().getMi_AllesÄndern().setEnabled(true);			//
		this.getRoot().getPlo_HauptFensterMenü().getMi_OptimaleLösung().setEnabled(true);		//
	}																							//

	public void internalFrameDeactivated(InternalFrameEvent ifevt)
	{

	}
//*** Ende Ereignismethoden ***---------------------------------------
}
//*** Klasse plo_Em_SubwindowListener ***-----------------------------