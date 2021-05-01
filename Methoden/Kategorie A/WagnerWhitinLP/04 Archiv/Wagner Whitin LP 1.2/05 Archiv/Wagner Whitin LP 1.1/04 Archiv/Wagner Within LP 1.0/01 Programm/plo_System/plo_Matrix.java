//*******************************************************************
//*		plo_Hauptfenster.java										*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Klasse der Datenstrukur des LP-		*
//*		Ansatzes in Matrizenform									*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.util.*;
import java.io.*;

public class plo_Matrix
{
	private String[][] core;										//Zweidimensionales Array zur Aufnahme der Daten
	private int anzahlZeilen;
	private int anzahlSpalten;
	private int upperboundZeilen;
	private int upperboundSpalten;
	private int periodenAnzahl;


//*** Konstruktor ***-------------------------------------------------
	public plo_Matrix(int x, int y, int upperS, int upperZ)
	{
		this.setUpperboundZeilen(upperZ);							//Einlesen der oberen Dimensions-
		this.setUpperboundSpalten(upperS);							//beschr�nkungen
		this.setAnzahlZeilen(x);									//Speichern der Zeilenzahl
		this.setAnzahlSpalten(y);									//Speichern der Spaltenzahl

		if(x > this.getUpperboundSpalten())							//Falls die eingegebenen Dimensionen die oberen
		{															//Dimensionsbeschr�nkungen �bersteigen, werden die oberen
			this.setAnzahlSpalten(this.getUpperboundSpalten());		//Beschr�nkungen als Dimensionen gesetzt
		}
		if(y > this.getUpperboundZeilen())
		{
			this.setAnzahlZeilen(this.getUpperboundZeilen());
		}
		core = new String[this.getAnzahlZeilen()][this.getAnzahlSpalten()];	//Array wird mit seinen neuen Abmessungen initialisiert
		for(int i = 0; i < x; i++)									//und mit 0 besetzt
		{
			for(int j = 0; j < y; j++)
			{
				core[i][j] = new String("0");
			}
		}
		this.setPeriodenAnzahl(0);
	}
//*** Ende Konstruktor ***--------------------------------------------

//*** SET-Methoden ***------------------------------------------------
	public void setCoreElement(int x, int y, String elem)			//Methode zum Setzen des Wertes an einer bestimmten Stelle des Arrays,
	{																//bestimmt durch zwei Indizes
		core[x][y] = new String(elem);
	}

	public void setAnzahlZeilen(int elem)							//Methode zum Setzen der Zeilenzahl der Matrix
	{
		anzahlZeilen = elem;
	}

	public void setAnzahlSpalten(int elem)							//Methode zum Setzen der Spaltenzahl der Matrix
	{
		anzahlSpalten = elem;
	}

	public void setUpperboundZeilen(int elem)						//Methode zum Setzen der oberen Zeilenzahlbeschr�nkung
	{
		upperboundZeilen = elem;
	}

	public void setUpperboundSpalten(int elem)						//Methode zum Setzen der oberen Spaltenzahlbeschr�nkung
	{
		upperboundSpalten = elem;
	}

	public void setPeriodenAnzahl(int elem)							//Methode zum Setzen der Periodenanzahl
	{
		periodenAnzahl = elem;
	}
//*** Ende SET-Methoden ***--------------------------------------------

//*** GET-Methoden ***------------------------------------------------
	public String getCoreElement(int x, int y)						//Methode zur R�ckgabe des Wertes an einer bestimmten Stelle des Arrays,
	{																//bestimmt durch zwei Indizes
		return core[x][y];
	}

	public int getAnzahlZeilen()									//Methode zur R�ckgabe der Zeilenzahl der Matrix
	{
		return anzahlZeilen;
	}

	public int getAnzahlSpalten()									//Methode zur R�ckgabe der Spaltenzahl der Matrix
	{
		return anzahlSpalten;
	}

	public int getUpperboundZeilen()								//Methode zur R�ckgabe der oberen Zeilenzahlbeschr�nkung
	{
		return upperboundZeilen;
	}

	public int getUpperboundSpalten()								//Methode zur R�ckgabe der oberen Spaltenzahlbeschr�nkung
	{
		return upperboundSpalten;
	}

	public int getPeriodenAnzahl()									//Methode zur R�ckgabe der Periodenanzahl
	{
		return periodenAnzahl;
	}
//*** Ende GET-Methoden ***--------------------------------------------
}