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

//*** Klasse Matrix ****************************************************************************************************************************
public class plo_Matrix
{
	private String[][] core;					//Zweidimensionales Array zur Aufnahme der Daten
	private int anzahlZeilen;
	private int anzahlSpalten;
	private int upperboundZeilen;
	private int upperboundSpalten;
	private int periodenAnzahl;


//*** Konstruktor *************************************************************************************
	public plo_Matrix(int x, int y, int upperS, int upperZ)
	{
		this.setUpperboundZeilen(upperZ);							//Einlesen der oberen Dimensions-
		this.setUpperboundSpalten(upperS);							//beschraenkungen
		this.setAnzahlZeilen(x);									//Speichern der Zeilenzahl
		this.setAnzahlSpalten(y);									//Speichern der Spaltenzahl

		if(x > this.getUpperboundSpalten())							//Falls die eingegebenen Dimensionen die oberen
		{															//Dimensionsbeschraenkungen uebersteigen, werden die oberen
			this.setAnzahlSpalten(this.getUpperboundSpalten());		//Beschraenkungen als Dimensionen gesetzt
		}
		if(y > this.getUpperboundZeilen())
		{
			this.setAnzahlZeilen(this.getUpperboundZeilen());
		}
		core = new String[this.getAnzahlZeilen()][this.getAnzahlSpalten()];	//Array wird mit seinen neuen Abmessungen initialisiert
		for(int i = 0; i < x; i++)									        //und mit 0 besetzt
		{
			for(int j = 0; j < y; j++)
			{
				core[i][j] = new String("0");
			}
		}
		this.setPeriodenAnzahl(0);
	}
//*** Ende Konstruktor *************************************************************************************

	
//*** SET-Methoden ****************************************************************************************
	
	//Methode zum Setzen des Wertes an einer bestimmten Stelle des Arrays,bestimmt durch zwei Indizes
	public void setCoreElement(int x, int y, String elem)			
	{																
		core[x][y] = new String(elem);
	}

	//Methode zum Setzen der Zeilenzahl der Matrix
	public void setAnzahlZeilen(int elem)							
	{
		anzahlZeilen = elem;
	}

	//Methode zum Setzen der Spaltenzahl der Matrix
	public void setAnzahlSpalten(int elem)							
	{
		anzahlSpalten = elem;
	}

	//Methode zum Setzen der oberen Zeilenzahlbeschraenkung
	public void setUpperboundZeilen(int elem)						
	{
		upperboundZeilen = elem;
	}

	//Methode zum Setzen der oberen Spaltenzahlbeschraenkung
	public void setUpperboundSpalten(int elem)						
	{
		upperboundSpalten = elem;
	}

	//Methode zum Setzen der Periodenanzahl
	public void setPeriodenAnzahl(int elem)							
	{
		periodenAnzahl = elem;
	}
//*** Ende SET-Methoden *************************************************************************************

	
//*** GET-Methoden ***************************************************************************************
	
	//Methode zur Rueckgabe des Wertes an einer bestimmten Stelle des Arrays,bestimmt durch zwei Indizes
	public String getCoreElement(int x, int y)						
	{																
		return core[x][y];
	}

	//Methode zur Rueckgabe der Zeilenzahl der Matrix
	public int getAnzahlZeilen()									
	{
		return anzahlZeilen;
	}

	//Methode zur Rueckgabe der Spaltenzahl der Matrix
	public int getAnzahlSpalten()									
	{
		return anzahlSpalten;
	}

	//Methode zur Rueckgabe der oberen Zeilenzahlbeschraenkung
	public int getUpperboundZeilen()								
	{
		return upperboundZeilen;
	}

	//Methode zur Rueckgabe der oberen Spaltenzahlbeschraenkung
	public int getUpperboundSpalten()								
	{
		return upperboundSpalten;
	}

	//Methode zur Rueckgabe der Periodenanzahl
	public int getPeriodenAnzahl()									
	{
		return periodenAnzahl;
	}
//*** Ende GET-Methoden ****************************************************************************************
	
}

//*** Ende Matrix Klasse *************************************************************************************************************