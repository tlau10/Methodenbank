package start;
//*******************************************************************
//*		plo.java													*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Startapplikation						 			*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
import javax.swing.*;
import plo_System.*;

//*** Klasse plo ***--------------------------------------------------
public class plo
{

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