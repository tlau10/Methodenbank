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
import javax.swing.*;

//*** Klasse periodenErgebnis **************************************************************************************
public class periodenErgebnis
{
	private JTextField tf_Nummer;						//Textfelder zur Aufnahme der Daten
	private JTextField tf_Bestellmenge;
	private JTextField tf_Lagermenge;
	private JTextField tf_Fehlmenge;
	private JTextField tf_Lagerhaltungskosten;

//***** Konstruktor  *******************************************************************************
	public periodenErgebnis()
	{
		tf_Nummer = new JTextField ("0", 5);				//Initialisierung der Textfelder
		tf_Bestellmenge = new JTextField ("0", 10);
		tf_Lagermenge = new JTextField ("0", 10);
		tf_Fehlmenge = new JTextField ("0", 10);
		tf_Lagerhaltungskosten = new JTextField ("0", 10);
	}
//***** Ende Konstruktor  **************************************************************************

	
//***** SET-Methoden  ******************************************************************************
   //Methode zum Setzen des Textes im Textfeld Periodennummer
	public void setTf_NummerText(String elem)						
	{
		this.tf_Nummer.setText(elem);
	}

  //Methode zum Setzen des Textes im Textfeld Periodenbestellmenge
	void setTf_BestellmengeText(String elem)						
	{
		this.tf_Bestellmenge.setText(elem);
	}
	
  //Methode zum Setzen des Textes im Textfeld Periodenlagermenge
	void setTf_LagermengeText(String elem)							
	{
		Double doubleTemp = new Double(elem);		//Angezeigt wird in diesem Textfeld lediglich positiver Lagerbestand
		if(doubleTemp.doubleValue() > 0)			//Zu diesem Zweck wird der eventuelle negative Bestand abgefangen
		{											//in seinen Betrag umgewandelt und im Textfeld Fehlmenge vermerkt
			this.tf_Lagermenge.setText(elem);
		}
		else
		{
			Double buffer = new Double((doubleTemp.doubleValue() * (-1)));
			setTf_FehlmengeText(buffer.toString());
		}
	}

  //Methode zum Setzen des Textes im Textfeld PeriodenFehl
	void setTf_FehlmengeText(String elem)							
	{
		Double doubleTemp = new Double(elem);			//Sollte in dieses Textfeld ein positver Wert > 0 eingetragen werden
		this.tf_Fehlmenge.setText(elem);				//Wird der Inhalt des Feldes Lagermenge = 0 gesetzt um das Modell korrekt
		if(doubleTemp.doubleValue() > 0)				//zu halten
		{
			this.setTf_LagermengeText("0");
		}
	}

  //Methode zum Setzen des Textes im Textfeld Periodenlagerhaltungskosten
	void setTf_LagerhaltungskostenText(String elem)					
	{
		this.tf_Lagerhaltungskosten.setText(elem);
	}
//***** Ende SET-Methoden ******************************************************************************

	
//***** GET-Methoden  **********************************************************************************
  //Methode zur Rueckgabe des Textes im Textfeld Periodennummer	
	public String getTf_NummerText()									
	{
		return this.tf_Nummer.getText();
	}

  //Methode zur Rueckgabe des Textes im Textfeld Periodenbestellmenge
	public String getTf_BestellmengeText()								
	{
		return this.tf_Bestellmenge.getText();
	}
	
  //Methode zur Rueckgabe des Textes im Textfeld Periodenlagermenge
	public String getTf_LagermengeText()							
	{
		return this.tf_Lagermenge.getText();
	}

  //Methode zur Rueckgabe des Textes im Textfeld Periodenfehlmenge
	public String getTf_FehlmengeText()									
	{
		return this.tf_Fehlmenge.getText();
	}

  //Methode zur Rueckgabe des Textes im Textfeld Periodenlagerhaltungskosten
	public String getTf_LagerhaltungskostenText()						
	{
		return this.tf_Lagerhaltungskosten.getText();
	}
	
  //Methode zur Rueckgabe des Textfeldes Periodennummer
	public JTextField getTf_Nummer()									
	{
		return this.tf_Nummer;
	}
	
  //Methode zur Rueckgabe des Textfeldes Periodenbestellmenge
	public JTextField getTf_Bestellmenge()								
	{
		return this.tf_Bestellmenge;
	}

  //Methode zur Rueckgabe des Textfeldes Periodenlagermenge
	public JTextField getTf_Lagermenge()								
	{
		return this.tf_Lagermenge;
	}

  //Methode zur Rueckgabe des Textfeldes Periodenfehlmenge
	public JTextField getTf_Fehlmenge()									
	{
		return this.tf_Fehlmenge;
	}

  //Methode zur Rueckgabe des Textfeldes Periodenlagerhaltungskosten
	public JTextField getTf_Lagerhaltungskosten()						
	{
		return this.tf_Lagerhaltungskosten;
	}
//***** Ende GET-Methoden  ***************************************************************************
	
}

//***** Ende Klasse periodenErgebnis *******************************************************************************
