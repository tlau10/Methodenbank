//*******************************************************************
//*																	*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse für den Datentyp, welcher	    *
//*		die Nachfrqagen eines Modells verwaltet		  				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*     Version 1.2 												*
//*     von Marco Weiß und Jenne Justin								*
//*																	*
//*******************************************************************

package plo_System;
import java.awt.event.*;
import javax.swing.event.*;


//***** Klasse plo_Ef_SubwindowListener ************************************************************************************************
public class plo_Ef_SubwindowListener implements InternalFrameListener, AdjustmentListener
{
  //Referenzobjekte fuer den Methodenaufruf in oberen Hierarchiestufen
	private plo_Hauptfenster root;								
	private plo_Ergebnisfenster rootEf;			
	
	
//***** Konstruktor *************************************************************************************
	
  //Initialisieren der Referenzobjekte
	public plo_Ef_SubwindowListener(plo_Hauptfenster base, plo_Ergebnisfenster baseEf)
	{																
		this.setRoot(base);											
		this.setRootEf(baseEf);										
	}
//***** Ende Konstruktor ********************************************************************************

	
//***** SET-Methoden ************************************************************************************

  //Methode zum Setzen des Referenzobjekts auf das Hauptfenster
	public void setRoot(plo_Hauptfenster elem)						
	{
		root = elem;
	}
	
  //Methode zum Setzen des Referenzobjekts auf das zugehoerige Ergebnisfenster
	public void setRootEf(plo_Ergebnisfenster elem)					
	{
		rootEf = elem;
	}
//***** Ende SET-Methoden *****************************************************************************

	
//***** GET-Methoden **********************************************************************************
	
  //Methode zur Rueckgabe des Referenzobjekts auf das Hauptfenster
	public plo_Hauptfenster getRoot()								
	{
		return root;
	}
	
  //Methode zur Rueckgabe des Referenzobjekts auf das zugehoerige Ergebnisfenster
	public plo_Ergebnisfenster getRootEf()							
	{
		return rootEf;
	}
//***** Ende GET-Methoden ******************************************************************************
	
	
//***** Ereignis-Methoden ******************************************************************************
    public void internalFrameClosing(InternalFrameEvent ifevt)		
    {
    
    }

  //Falls der Frame geschlossen wird wird die Anzahl der Fenster in der Zaehlvariablen des Hauptfensters 
  //verringert und geprueft, ob noch genug Fenster offen sind oder ob das Menue angepasst werden muss
	public void internalFrameClosed(InternalFrameEvent ifevt)		
	{																
		this.getRoot().setAnzahlErgebnisfenster(this.getRoot().getAnzahlErgebnisfenster()-1);	
		this.getRoot().checkValidWindows();							
	}																

	
	public void internalFrameOpened(InternalFrameEvent ifevt)		
	{

	}

	
	public void internalFrameIconified(InternalFrameEvent ifevt)	
	{

	}

	
	public void internalFrameDeiconified(InternalFrameEvent ifevt)	
	{

	}
	
	
  //Falls der Frame fokussiert wird
	public void internalFrameActivated(InternalFrameEvent ifevt)	
	{																							
		plo_Ergebnisfenster buffer = (plo_Ergebnisfenster)ifevt.getSource();					//Ermitteln, welches Objekt fokussiert wurde
		this.getRoot().setInternalFocus(buffer.getEf_ZugehoerigeEingabemaske());				//Setzen des Referenzobjekts im Hauptfenster auf den aktivierten Frame
		if(this.getRootEf().getEf_ZugehoerigeEingabemaske().getEm_IsOptimized() == true)		//Falls dieser schon geloest worden ist
		{
			this.getRoot().getPlo_HauptFensterMenue().getMi_LoesungDrucken().setEnabled(true);	//Enable des Loesungsmenues
			this.getRoot().getPlo_HauptFensterMenue().getMi_LoesungSpeichern().setEnabled(true);
		}
		this.getRoot().getPlo_HauptFensterMenue().getMi_ModellSpeichern().setEnabled(true);		//Enablen der anderen Menues
		this.getRoot().getPlo_HauptFensterMenue().getMi_ModellDrucken().setEnabled(true);		
		this.getRoot().getPlo_HauptFensterMenue().getMi_NachfrageEinfuegen().setEnabled(true);	
		this.getRoot().getPlo_HauptFensterMenue().getMi_NachfrageEntfernen().setEnabled(true);	
		this.getRoot().getPlo_HauptFensterMenue().getMi_AllesAendern().setEnabled(true);		
		this.getRoot().getPlo_HauptFensterMenue().getMi_OptimaleLoesung().setEnabled(true);		
	}

	
	public void internalFrameDeactivated(InternalFrameEvent ifevt)								
	{

	}

	
	public void adjustmentValueChanged(AdjustmentEvent aevt)									
	{

	}
//***** Ende Ereignis-Methoden **********************************************************************
	
}

//***** Ende Klasse plo_Ef_SubwindowListener *******************************************************************************************
