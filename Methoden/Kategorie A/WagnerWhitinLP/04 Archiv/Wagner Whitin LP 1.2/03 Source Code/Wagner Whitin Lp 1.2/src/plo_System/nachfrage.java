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

//***** Klasse Nachfrage ****************************************************
public class nachfrage
{
	private JTextField tf_Nummer;							//Textfelder, die die Daten aufnehmen
	private JTextField tf_Menge;
	private JTextField tf_Periode;
	private JTextField tf_Bestellkosten;
	private JTextField tf_Lagerkosten;
	
//***** Konstruktor  *******************************************************
	public nachfrage()
	{
		tf_Nummer = new JTextField("0", 5);					//Initialisiern der Textfelder
		tf_Menge = new JTextField("0", 10);
		tf_Periode = new JTextField("0", 5);
		tf_Bestellkosten = new JTextField("0", 10);
		tf_Lagerkosten = new JTextField("0", 10);
	}
//***** Ende Konstruktor ***************************************************

//***** SET-Methoden *******************************************************
	
  //Methode zum Setzen des Textes im Textfeld der Nachfragennummer 
	public void setTf_NummerText(String elem)			
	{
		tf_Nummer.setText(elem);
	}
	
   //Methode zum Setzen des Textes im Textfeld der Nachfragemenge
	public void setTf_MengeText(String elem)			
	{
		tf_Menge.setText(elem);
	}
	
  //Methode zum Setzen des Textes im Textfeld der Nachfragenperiode
	public void setTf_PeriodeText(String elem)			
	{
		tf_Periode.setText(elem);
	}

  //Methode zum Setzen des Textes im Textfeld der Nachfragen-Bestellkosten
	public void setTf_BestellkostenText(String elem)		
	{
		tf_Bestellkosten.setText(elem);
	}

  //Methode zum Setzen des Textes im Textfeld der Nachfragen-Lagerkosten
	public void setTf_LagerkostenText(String elem)					
	{
		tf_Lagerkosten.setText(elem);
	}
//***** Ende SET-Methoden ********************************************************

	
//*** GET-Methoden  ************************************************************
	
  //Methode zum Rueckgabe des Textes im Textfeld der Nachfragennummer
	public String getTf_NummerText()								
	{
		return tf_Nummer.getText();
	}

  //Methode zum Rueckgabe des Textes im Textfeld der Nachfragemenge
	public String getTf_MengeText()									
	{
		return tf_Menge.getText();
	}

  //Methode zum Rueckgabe des Textes im Textfeld der Nachfrageperiode
	public String getTf_PeriodeText()								
	{
		return tf_Periode.getText();
	}

  //Methode zum Rueckgabe des Textes im Textfeld der Nachfragen-Bestellkosten
	public String getTf_BestellkostenText()							
	{
		return tf_Bestellkosten.getText();
	}

  //Methode zum Rueckgabe des Textes im Textfeld der Nachfragen-Lagerkosten
	public String getTf_LagerkostenText()							
	{
		return tf_Lagerkosten.getText();
	}

  //Methode zum Rueckgabe des TexteFeldes Nachfragennummer
	public JTextField getTf_Nummer()								
	{
		return tf_Nummer;
	}

  //Methode zum Rueckgabe des TexteFeldes Nachfragemenge
	public JTextField getTf_Menge()									
	{
		return tf_Menge;
	}

  //Methode zum Rueckgabe des TexteFeldes Nachfragenperiode
	public JTextField getTf_Periode()								
	{
		return tf_Periode;
	}

  //Methode zum Rueckgabe des TexteFeldes Nachfragen-Bestellkosten
	public JTextField getTf_Bestellkosten()							
	{
		return tf_Bestellkosten;
	}

  //Methode zum Rueckgabe des TexteFeldes Nachfragen-Lagerkosten
	public JTextField getTf_Lagerkosten()							
	{
		return tf_Lagerkosten;
	}
//*** Ende GET-Methoden  ************************************************************

}

//*** Ende Klasse Nachfrage *********************************************************
