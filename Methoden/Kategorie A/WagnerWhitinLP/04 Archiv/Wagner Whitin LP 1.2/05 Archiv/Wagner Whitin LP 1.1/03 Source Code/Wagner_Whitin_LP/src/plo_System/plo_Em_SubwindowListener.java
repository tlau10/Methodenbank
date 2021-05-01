//*******************************************************************
//*		plo_Em_SubwindowListener.java								*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthuelt die Listener zur Ereignisverwaltung		*
//*		in den Eeingabemasken						 				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import javax.swing.event.*;

//*** Klasse plo_Em_SubwindowListener ***-----------------------------
public class plo_Em_SubwindowListener implements InternalFrameListener
{
	private plo_Hauptfenster root;									//Referenzobjekte fuer Aufrufe
	private plo_Eingabemaske rootEm;								//in Uebergeordneten Hierarchieschichten
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
	public plo_Hauptfenster getRoot()								//Methode zur Rueckgabe des Referenzobjektes auf das Hauptfenster
	{
		return root;
	}

	public plo_Eingabemaske getRootEm()								//Methode zur Rueckgabe des Referenzobjektes auf das Eingabemaskenfenster
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
		this.getRoot().checkValidWindows();							//und zum die Menues in gueltigem Zustand zu halten
	}																//die Pruefmethode "checkValidWindows" im Hauptfenster angestossen

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
		this.getRoot().setInternalFocus((plo_Eingabemaske)ifevt.getSource());					//Wird das der Zustand des Hauptmenues
		if(this.getRootEm().getEm_IsOptimized() == true)										//dem Status der Eingabemaske angepasst
		{																						//
			this.getRoot().getPlo_HauptFensterMenue().getMi_LoesungDrucken().setEnabled(true);	//
			this.getRoot().getPlo_HauptFensterMenue().getMi_LoesungSpeichern().setEnabled(true);	//
		}																						//
		this.getRoot().getPlo_HauptFensterMenue().getMi_ModellSpeichern().setEnabled(true);		//
//		this.getRoot().getPlo_HauptFensterMenue().getMi_ModellDrucken().setEnabled(true);		//
		this.getRoot().getPlo_HauptFensterMenue().getMi_NachfrageEinfuegen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMenue().getMi_NachfrageEntfernen().setEnabled(true);	//
		this.getRoot().getPlo_HauptFensterMenue().getMi_AllesAendern().setEnabled(true);			//
		this.getRoot().getPlo_HauptFensterMenue().getMi_OptimaleLoesung().setEnabled(true);		//
	}																							//

	public void internalFrameDeactivated(InternalFrameEvent ifevt)
	{

	}
//*** Ende Ereignismethoden ***---------------------------------------
}
//*** Klasse plo_Em_SubwindowListener ***-----------------------------