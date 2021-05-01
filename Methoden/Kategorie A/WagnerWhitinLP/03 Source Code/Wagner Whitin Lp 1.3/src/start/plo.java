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

package start;
import javax.swing.*;
import plo_System.*;

//***** Klasse plo ********************************************************************************************************
public class plo {

//*** Konstruktor ***-------------------------------------------------
	public plo()
	{

	}
//*** Ende Konstruktor ***--------------------------------------------

//*** Main-Methode ***------------------------------------------------
	public static void main (String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e) {}
		plo_Hauptfenster mainWindow = new plo_Hauptfenster("Periodenorientierte Lagerhaltungs Optimierung");
		mainWindow.setSize(700, 600);
		mainWindow.setVisible(true);

	}
//*** Ende Main-Methode ***-------------------------------------------
}
//*** Ende Klasse plo ***---------------------------------------------