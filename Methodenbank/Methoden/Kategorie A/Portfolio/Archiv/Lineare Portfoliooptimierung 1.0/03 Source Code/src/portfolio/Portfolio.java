package portfolio;

/**
 * Überschrift:   Programm zur linearen Portfoliooptimierung
 * Beschreibung:
 * Copyright:     Copyright (c) 2002
 * Organisation:  FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
import java.util.*;
public class Portfolio
{

    private Vector aktienliste;
    private Vector datum;
    private int schrittweite;
    Date startzeit = new Date();
    Date endzeit = new Date();

    public Portfolio(Vector a, Vector d)
    {
        aktienliste = a;
        datum = d;
    }

    public void setSchrittweite(int s)
    {
        schrittweite = s;
    }

    public void berechneRendite()
    {
        int index =0;
        double durchschnitt=0;
        double rendite=0;
        double aktienrendite=0;
        double durchschnitt2=0;
        Hashtable hash;
        hash = new  Hashtable();

        for(int f =0;f<datum.size();f++)
        {
            hash.put(((Double)datum.get(f)),new Integer(f));
        }

        Double d;
        Double d2;
        Aktie temp;
        for (int k =0; k<aktienliste.size(); k++)
        {
            rendite=0;
            aktienrendite=0;
            durchschnitt=0;
            index =0;
            temp = (Aktie)aktienliste.get(k);
            temp.clearEinzelRendite();

            int anz=0;
            for (int i=0;(i<datum.size());(i+=schrittweite))
            {
                anz++;
                d=((Double)datum.get(i));
                d2 = new Double(d.doubleValue()+365); // berechne Datum des Vergleichswertes
                if(!hash.containsKey(d2)) // Falls Datum nicht in Hashtable -> Rest der Funktion überspringen
                {
                    anz--;
                    continue;
                }
                Integer integer;

                integer = (Integer)hash.get(d2);
                index = integer.intValue();
                rendite=temp.getWert(index)/(temp.getWert(i)/100)-100;
                temp.addEinzelRendite(rendite); // Für Typ4 Modell
                aktienrendite += rendite;
            }
            aktienrendite = aktienrendite/anz;
            temp.setRendite(aktienrendite);
        }
    }

    public Vector getAktienListe()
    {
        return aktienliste;
    }
}