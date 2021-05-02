//*******************************************************************
//*		plo_HauptFensterWindowListener.java							*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthuelt die Klasse fuer den Listener des 		*
//*		Applikationsfensters zur Ereignisverwaltung					*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.event.*;

//*** Klasse plo_HauptFensterWindowListener ***-----------------------
public class plo_HauptFensterWindowListener implements WindowListener
{

//*** Konstruktor  ***------------------------------------------------
	public plo_HauptFensterWindowListener()
	{

	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** Ereignis Methoden  ***------------------------------------------
	public void windowActivated(WindowEvent Wevt)
	{

	}

	public void windowClosed(WindowEvent Wevt)
	{

	}

	public void windowClosing(WindowEvent Wevt)						//
	{																//
		System.exit(0);												//Aktivieren des Schliessen Buttons
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
//*** Ende Ereignis Methoden  ***-------------------------------------
}
//*** Klasse plo_HauptFensterWindowListener ***-----------------------