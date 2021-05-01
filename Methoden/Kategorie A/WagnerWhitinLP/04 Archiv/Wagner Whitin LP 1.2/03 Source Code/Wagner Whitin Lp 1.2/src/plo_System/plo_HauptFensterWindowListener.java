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

//*** Klasse plo_HauptFensterWindowListener **************************************************************************************
public class plo_HauptFensterWindowListener implements WindowListener
{

//*** Konstruktor ****************************************************************
	public plo_HauptFensterWindowListener()
	{

	}
//*** Ende Konstruktor ***********************************************************

	
//*** Ereignis Methoden  *********************************************************
	public void windowActivated(WindowEvent Wevt)
	{

	}

	public void windowClosed(WindowEvent Wevt)
	{

	}

	public void windowClosing(WindowEvent Wevt)						
	{																
		System.exit(0);								//Aktivieren des Schliessen Buttons
	}

	public void windowDeactivated(WindowEvent Wevt)
	{

	}

	public void windowDeiconified(WindowEvent Wevt)
	{

	}

	public void windowIconified(WindowEvent Wevt)
	{

	}

	public void windowOpened(WindowEvent Wevt)
	{

	}
//*** Ende Ereignis Methoden  *****************************************************************
	
}

//*** Klasse plo_HauptFensterWindowListener ***********************************************************************