//*******************************************************************
//*		nachfrage.java												*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthaelt die Klasse fuer den Datentyp, welcher	*
//*		die Nachfrqagen eines Modells verwaltet		  				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;
import javax.swing.*;


//*** Klasse Nachfrage ***********************************************
public class nachfrage
{
	private JTextField tf_Nummer;									//Textfelder, die die Daten aufnehmen
	private JTextField tf_Menge;
	private JTextField tf_Periode;
	private JTextField tf_Bestellkosten;
	private JTextField tf_Lagerkosten;
//*** Konstruktor  ***------------------------------------------------
	public nachfrage()
	{
		tf_Nummer = new JTextField("0", 5);							//Initialisiern der Textfelder
		tf_Menge = new JTextField("0", 10);
		tf_Periode = new JTextField("0", 5);
		tf_Bestellkosten = new JTextField("0", 10);
		tf_Lagerkosten = new JTextField("0", 10);
	}
//*** Ende Konstruktor  ***-------------------------------------------

//*** SET-Methoden  ***-----------------------------------------------
	public void setTf_NummerText(String elem)						//Methode zum Setzen des Textes im Textfeld der Nachfragennummer
	{
		tf_Nummer.setText(elem);
	}

	public void setTf_MengeText(String elem)						//Methode zum Setzen des Textes im Textfeld der Nachfragemenge
	{
		tf_Menge.setText(elem);
	}

	public void setTf_PeriodeText(String elem)						//Methode zum Setzen des Textes im Textfeld der Nachfragenperiode
	{
		tf_Periode.setText(elem);
	}

	public void setTf_BestellkostenText(String elem)				//Methode zum Setzen des Textes im Textfeld der Nachfragen-Bestellkosten
	{
		tf_Bestellkosten.setText(elem);
	}

	public void setTf_LagerkostenText(String elem)					//Methode zum Setzen des Textes im Textfeld der Nachfragen-Lagerkosten
	{
		tf_Lagerkosten.setText(elem);
	}
//*** Ende SET-Methoden  ***------------------------------------------

//*** GET-Methoden  ***-----------------------------------------------
	public String getTf_NummerText()								//Methode zum Rueckgabe des Textes im Textfeld der Nachfragennummer
	{
		return tf_Nummer.getText();
	}

	public String getTf_MengeText()									//Methode zum Rueckgabe des Textes im Textfeld der Nachfragemenge
	{
		return tf_Menge.getText();
	}

	public String getTf_PeriodeText()								//Methode zum Rueckgabe des Textes im Textfeld der Nachfrageperiode
	{
		return tf_Periode.getText();
	}

	public String getTf_BestellkostenText()							//Methode zum Rueckgabe des Textes im Textfeld der Nachfragen-Bestellkosten
	{
		return tf_Bestellkosten.getText();
	}

	public String getTf_LagerkostenText()							//Methode zum Rueckgabe des Textes im Textfeld der Nachfragen-Lagerkosten
	{
		return tf_Lagerkosten.getText();
	}

	public JTextField getTf_Nummer()								//Methode zum Rueckgabe des TexteFeldes Nachfragennummer
	{
		return tf_Nummer;
	}

	public JTextField getTf_Menge()									//Methode zum Rueckgabe des TexteFeldes Nachfragemenge
	{
		return tf_Menge;
	}

	public JTextField getTf_Periode()								//Methode zum Rueckgabe des TexteFeldes Nachfragenperiode
	{
		return tf_Periode;
	}

	public JTextField getTf_Bestellkosten()							//Methode zum Rueckgabe des TexteFeldes Nachfragen-Bestellkosten
	{
		return tf_Bestellkosten;
	}

	public JTextField getTf_Lagerkosten()							//Methode zum Rueckgabe des TexteFeldes Nachfragen-Lagerkosten
	{
		return tf_Lagerkosten;
	}
//*** Ende GET-Methoden  ***------------------------------------------
}
//*** Ende Klasse Nachfrage ******************************************
