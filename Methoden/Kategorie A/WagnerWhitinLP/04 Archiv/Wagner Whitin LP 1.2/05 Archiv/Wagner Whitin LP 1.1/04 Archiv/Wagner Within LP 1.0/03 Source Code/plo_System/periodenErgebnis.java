//*******************************************************************
//*		periodenErgebnis.java										*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Göltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse für den Datentyp, welcher 		*
//*		die Berechnungsergebnisse für eine bestimmte Periode		*
//*		verwaltet													*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse periodenErgebnis ****************************************
public class periodenErgebnis
{
	private JTextField tf_Nummer;									//Textfelder zur Aufnahme der Daten
	private JTextField tf_Bestellmenge;
	private JTextField tf_Lagermenge;
	private JTextField tf_Fehlmenge;
	private JTextField tf_Lagerhaltungskosten;

//*** Konstruktor  ***------------------------------------------------
	public periodenErgebnis()
	{
		tf_Nummer = new JTextField ("0", 5);						//Initialisierung der Textfelder
		tf_Bestellmenge = new JTextField ("0", 10);
		tf_Lagermenge = new JTextField ("0", 10);
		tf_Fehlmenge = new JTextField ("0", 10);
		tf_Lagerhaltungskosten = new JTextField ("0", 10);
	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** SET-Methoden  ***-----------------------------------------------
	public void setTf_NummerText(String elem)						//Methode zum Setzen des Textes im Textfeld Periodennummer
	{
		this.tf_Nummer.setText(elem);
	}

	void setTf_BestellmengeText(String elem)						//Methode zum Setzen des Textes im Textfeld Periodenbestellmenge
	{
		this.tf_Bestellmenge.setText(elem);
	}

	void setTf_LagermengeText(String elem)							//Methode zum Setzen des Textes im Textfeld Periodenlagermenge
	{
		Double doubleTemp = new Double(elem);						//Angezeigt wird in diesem Textfeld lediglich positiver Lagerbestand
		if(doubleTemp.doubleValue() > 0)							//Zu diesem Zweck wird der eventuelle negative Bestand abgefangen
		{															//in seinen Betrag umgewandelt und im Textfeld Fehlmenge vermerkt
			this.tf_Lagermenge.setText(elem);
		}
		else
		{
			Double buffer = new Double((doubleTemp.doubleValue() * (-1)));
			setTf_FehlmengeText(buffer.toString());
		}
	}

	void setTf_FehlmengeText(String elem)							//Methode zum Setzen des Textes im Textfeld PeriodenFehl
	{
		Double doubleTemp = new Double(elem);						//Sollte in dieses Textfeld ein positver Wert > 0 eingetragen werden
		this.tf_Fehlmenge.setText(elem);							//Wird der Inhalt des Feldes Lagermenge = 0 gesetzt um das Modell korrekt
		if(doubleTemp.doubleValue() > 0)							//zu halten
		{
			this.setTf_LagermengeText("0");
		}
	}

	void setTf_LagerhaltungskostenText(String elem)					//Methode zum Setzen des Textes im Textfeld Periodenlagerhaltungskosten
	{
		this.tf_Lagerhaltungskosten.setText(elem);
	}
//*** Ende SET-Methoden  ***------------------------------------------

//*** GET-Methoden  ***-----------------------------------------------
	public String getTf_NummerText()									//Methode zur Rückgabe des Textes im Textfeld Periodennummer
	{
		return this.tf_Nummer.getText();
	}

	public String getTf_BestellmengeText()								//Methode zur Rückgabe des Textes im Textfeld Periodenbestellmenge
	{
		return this.tf_Bestellmenge.getText();
	}

	public String getTf_LagermengeText()								//Methode zur Rückgabe des Textes im Textfeld Periodenlagermenge
	{
		return this.tf_Lagermenge.getText();
	}

	public String getTf_FehlmengeText()									//Methode zur Rückgabe des Textes im Textfeld Periodenfehlmenge
	{
		return this.tf_Fehlmenge.getText();
	}

	public String getTf_LagerhaltungskostenText()						//Methode zur Rückgabe des Textes im Textfeld Periodenlagerhaltungskosten
	{
		return this.tf_Lagerhaltungskosten.getText();
	}

	public JTextField getTf_Nummer()									//Methode zur Rückgabe des Textfeldes Periodennummer
	{
		return this.tf_Nummer;
	}

	public JTextField getTf_Bestellmenge()								//Methode zur Rückgabe des Textfeldes Periodenbestellmenge
	{
		return this.tf_Bestellmenge;
	}

	public JTextField getTf_Lagermenge()								//Methode zur Rückgabe des Textfeldes Periodenlagermenge
	{
		return this.tf_Lagermenge;
	}

	public JTextField getTf_Fehlmenge()									//Methode zur Rückgabe des Textfeldes Periodenfehlmenge
	{
		return this.tf_Fehlmenge;
	}

	public JTextField getTf_Lagerhaltungskosten()						//Methode zur Rückgabe des Textfeldes Periodenlagerhaltungskosten
	{
		return this.tf_Lagerhaltungskosten;
	}
//*** Ende GET-Methoden  ***------------------------------------------
}
//*** Ende Klasse periodenErgebnis ***********************************
