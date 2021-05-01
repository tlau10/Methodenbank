package planer;

import java.util.*;
import java.io.*;

public class Matrix
{
	private String[][] core;	//Zweidimensionales Array zur Aufnahme der Daten
	private int anzahlZeilen;
	private int anzahlSpalten;
	private int upperboundZeilen;
	private int upperboundSpalten;


	public Matrix(int zeilen, int spalten, int upperZ, int upperS)
	{
		this.setUpperboundZeilen(upperZ);	//Einlesen der oberen Dimensions-
		this.setUpperboundSpalten(upperS);	//beschränkungen
		this.setAnzahlZeilen(zeilen);	        //Speichern der Zeilenzahl
		this.setAnzahlSpalten(spalten);		//Speichern der Spaltenzahl

		if(spalten > this.getUpperboundSpalten())	//Falls die eingegebenen Dimensionen die oberen
		{					//Dimensionsbeschränkungen übersteigen, werden die oberen
			this.setAnzahlSpalten(this.getUpperboundSpalten());		//Beschränkungen als Dimensionen gesetzt
		}
		if(zeilen > this.getUpperboundZeilen())
		{
			this.setAnzahlZeilen(this.getUpperboundZeilen());
		}
		core = new String[this.getAnzahlZeilen()][this.getAnzahlSpalten()];	//Array wird mit seinen neuen Abmessungen initialisiert
		for(int i = 0; i < zeilen; i++)						//und mit "" besetzt
		{
			for(int j = 0; j < spalten; j++)
			{
                          if (j == anzahlSpalten - 2)
                          core [i][j] = new String("=");
                          else
                          core[i][j] = new String("0");
			}
		}
	}


	public void setElement(int zeile, int spalte, String elem) throws DiaetplanerException	//Methode zum Setzen des Wertes an einer bestimmten Stelle des Arrays,

	{	if (zeile < anzahlZeilen && spalte < anzahlSpalten) //Wenn der Wert in die Matrix passt      //bestimmt durch zwei Indizes
		core[zeile][spalte] = new String(elem);
                else //Array vergrössern
                {
                  //Pruefen, ob obere Grenzen verletzt sind
                  if(zeile >= upperboundZeilen || spalte >= upperboundSpalten)
                    throw new DiaetplanerException("Grenzen der Matrix ueberschritten");
                  int anzahlZeilenNeu = anzahlZeilen;
                  if(zeile >= anzahlZeilen)
                    anzahlZeilenNeu = zeile +1;
                  if(spalte >= anzahlSpalten)
                    throw new DiaetplanerException("Spalten der Matrix dürfen nach initialisieren" +
                                                   " der Matrix nicht mehr verändert werden");
                  String [][] core2 = new String [anzahlZeilenNeu] [anzahlSpalten];
                      //Array initalisieren
                      for(int i = 0; i < anzahlZeilenNeu; i++)						//und mit "" besetzt
                      {
                        for(int j = 0; j < anzahlSpalten; j++)
                        {
                                //Vorletzte Spalte mit "=" initialisieren
                                if (j == anzahlSpalten - 2)
                                  core2 [i][j] = new String("=");
                                else
                                core2 [i][j] = new String("0");
                        }
                      }
                    //Alte Werte kopieren
                    for(int i = 0; i < anzahlZeilen; i++)						//und mit "" besetzt
                      {
                        for (int j = 0; j < anzahlSpalten; j++)
                        {
                          core2[i][j] = core[i][j];
                        }
                      }
                    anzahlZeilen = anzahlZeilenNeu;
                    core = core2;
                    //Neuen Wert setzen
                    core[zeile][spalte] = new String(elem);


                }
	}

        public void setElement(int zeile, int spalte, int elem)	throws DiaetplanerException//Methode zum Setzen des Wertes an einer bestimmten Stelle des Arrays,
        {      //bestimmt durch zwei Indizes
          if (zeile < anzahlZeilen && spalte < anzahlSpalten) //Wenn der Wert in die Matrix passt      //bestimmt durch zwei Indizes
          core[zeile][spalte] = new String(Integer.toString(elem));
          else //Array vergrössern
          {
            //Pruefen, ob obere Grenzen verletzt sind
            if(zeile >= upperboundZeilen || spalte >= upperboundSpalten)
              throw new DiaetplanerException("Grenzen der Matrix ueberschritten");
            int anzahlZeilenNeu = anzahlZeilen;
            if(zeile >= anzahlZeilen)
              anzahlZeilenNeu = zeile +1;
            if(spalte >= anzahlSpalten)
              throw new DiaetplanerException("Spalten der Matrix dürfen nach initialisieren" +
                                             " der Matrix nicht mehr verändert werden");
            String [][] core2 = new String [anzahlZeilenNeu] [anzahlSpalten];
                //Array initalisieren
                for(int i = 0; i < anzahlZeilenNeu; i++)						//und mit "" besetzt
                {
                  for(int j = 0; j < anzahlSpalten; j++)
                  {
                          //Vorletzte Spalte mit "=" initialisieren
                          if (j == anzahlSpalten - 2)
                            core2 [i][j] = new String("=");
                          else
                          core2 [i][j] = new String("0");
                  }
                }
              //Alte Werte kopieren
              for(int i = 0; i < anzahlZeilen; i++)						//und mit "" besetzt
                {
                  for (int j = 0; j < anzahlSpalten; j++)
                  {
                    core2[i][j] = core[i][j];
                  }
                }
              anzahlZeilen = anzahlZeilenNeu;
              core = core2;
              //Neuen Wert setzen
              core[zeile][spalte] = new String(Integer.toString(elem));


          }


        }

        public void setElement(int zeile, int spalte, double elem) throws DiaetplanerException	//Methode zum Setzen des Wertes an einer bestimmten Stelle des Arrays,
       {							//bestimmt durch zwei Indizes

          if (zeile < anzahlZeilen && spalte < anzahlSpalten) //Wenn der Wert in die Matrix passt      //bestimmt durch zwei Indizes
          core[zeile][spalte] = new String(Double.toString(elem));
          else //Array vergrössern
          {
            //Pruefen, ob obere Grenzen verletzt sind
            if(zeile >= upperboundZeilen || spalte >= upperboundSpalten)
              throw new DiaetplanerException("Grenzen der Matrix ueberschritten");
            int anzahlZeilenNeu = anzahlZeilen;
            if(zeile >= anzahlZeilen)
              anzahlZeilenNeu = zeile +1;
            if(spalte >= anzahlSpalten)
              throw new DiaetplanerException("Spalten der Matrix dürfen nach initialisieren" +
                                             " der Matrix nicht mehr verändert werden");
            String [][] core2 = new String [anzahlZeilenNeu] [anzahlSpalten];
                //Array initalisieren
                for(int i = 0; i < anzahlZeilenNeu; i++)						//und mit "" besetzt
                {
                  for(int j = 0; j < anzahlSpalten; j++)
                  {
                          //Vorletzte Spalte mit "=" initialisieren
                          if (j == anzahlSpalten - 2)
                            core2 [i][j] = new String("=");
                          else
                          core2 [i][j] = new String("0");
                  }
                }
              //Alte Werte kopieren
              for(int i = 0; i < anzahlZeilen; i++)						//und mit "" besetzt
                {
                  for (int j = 0; j < anzahlSpalten; j++)
                  {
                    core2[i][j] = core[i][j];
                  }
                }
              anzahlZeilen = anzahlZeilenNeu;
              core = core2;
              //Neuen Wert setzen
              core[zeile][spalte] = new String(Double.toString(elem));


          }

       }


        public void setAnzahlZeilen(int elem)			//Methode zum Setzen der Zeilenzahl der Matrix
	{
		anzahlZeilen = elem;
	}

	public void setAnzahlSpalten(int elem)			//Methode zum Setzen der Spaltenzahl der Matrix
	{
		anzahlSpalten = elem;
	}

	public void setUpperboundZeilen(int elem)		//Methode zum Setzen der oberen Zeilenzahlbeschränkung
	{
		upperboundZeilen = elem;
	}

	public void setUpperboundSpalten(int elem)		//Methode zum Setzen der oberen Spaltenzahlbeschränkung
	{
		upperboundSpalten = elem;
	}

	public String getElement(int zeile, int spalte)		//Methode zur Rückgabe des Wertes an einer bestimmten Stelle des Arrays,
	{							//bestimmt durch zwei Indizes
		return core[zeile][spalte];
	}

        public int getElementInt(int zeile, int spalte)		//Methode zur Rückgabe des Wertes an einer bestimmten Stelle des Arrays,
        {          //bestimmt durch zwei Indizes
                return Integer.parseInt(core[zeile][spalte]);
        }


	public int getAnzahlZeilen()				//Methode zur Rückgabe der Zeilenzahl der Matrix
	{
		return anzahlZeilen;
	}

	public int getAnzahlSpalten()				//Methode zur Rückgabe der Spaltenzahl der Matrix
	{
		return anzahlSpalten;
	}

	public int getUpperboundZeilen()								//Methode zur Rückgabe der oberen Zeilenzahlbeschränkung
	{
		return upperboundZeilen;
	}

	public int getUpperboundSpalten()								//Methode zur Rückgabe der oberen Spaltenzahlbeschränkung
	{
		return upperboundSpalten;
	}

}