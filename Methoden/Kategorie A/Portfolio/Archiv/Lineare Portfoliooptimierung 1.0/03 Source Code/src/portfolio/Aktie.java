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
public class Aktie {
    private String name;    // Aktienname
    private Vector werte;   // Kurswerte der Aktien
    private Vector einzelRendite; // Einzelrenditen der Kurswerte
    private double rendite; // Gesamtdurchschnittsrendite der Aktie

    public Aktie()
    {
        werte = new Vector();
        einzelRendite = new Vector();
    }

    public void setName(String n)
    {
        name = n;
    }

    public void addWert(String w)
    {
        try
        {
            Double d = new Double(w);
            werte.add(d);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setRendite(double r)
    {
        rendite = r;
    }

    public double getRendite()
    {
        return rendite;
    }

    public double getWert(int pos)
    {
        return ((Double)werte.get(pos)).doubleValue();
    }

    public int getLaenge()
    {
        return werte.size();
    }

    public String getName()
    {
        return name;
    }

    public void addEinzelRendite(double w)
    {
        try
        {
            Double d = new Double(w);
            einzelRendite.add(d);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void clearEinzelRendite()
    {
        einzelRendite.clear();
    }

    public double getEinzelRendite(int pos)
    {
        return ((Double)einzelRendite.get(pos)).doubleValue();
    }

    public int getLaengeEinzelRendite()
    {
        return einzelRendite.size();
    }
}