//*******************************************************************
//*		plo_HauptFensterWindowListener.java							*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Klasse f�r den Listener des 			*
//*		Applikationsfensters zur Ereignisverwaltung					*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
		System.exit(0);												//Aktivieren des Schlie�en Buttons
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