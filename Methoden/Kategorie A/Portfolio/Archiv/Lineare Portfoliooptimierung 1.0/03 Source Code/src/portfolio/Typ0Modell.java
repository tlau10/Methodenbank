package portfolio;

/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
import java.util.*;
public class Typ0Modell implements LPModell {
    private Vector aktienliste;
    private double anteilsbegrenzung;
    private Vector modell;

    public Typ0Modell()
    {
        anteilsbegrenzung=0.4;
    }

    public void setAnteilsBegrenzung(double b)
    {
        anteilsbegrenzung=b;
    }

    public void setMuMin(double m)
    {
        // Nichts zu tun
    }

    public Vector erstelleModell(Vector aktienliste)
    {

        modell = new Vector();
        this.aktienliste = aktienliste;
        Aktie aktie = null;
        String zielfunktion = new String("max: ");
        String temp = "";
        String r0 = new String("R0:  ");
        String r1 = new String("R1:  ");

        Enumeration e = aktienliste.elements();
        if(e.hasMoreElements())
        {
            aktie=(Aktie)e.nextElement();
            temp = temp + aktie.getRendite() + " " + aktie.getName();
            r0 = r0 + aktie.getName();
        }
        while(e.hasMoreElements())
        {
            aktie=(Aktie)e.nextElement();
            if(aktie.getRendite()>=0)
            {
                temp = temp + " + " + aktie.getRendite() + " " + aktie.getName();
            }
            else
            {
                temp = temp + " " + aktie.getRendite() + " " + aktie.getName();
            }
            r0=r0  + " + " + aktie.getName();

        }
        zielfunktion = zielfunktion + temp + ";";
        r0=r0 + " = 1;";
        r1=r1 + temp + " - portfmu = 0;";

        modell.add(zielfunktion);
        modell.add(r0);
        modell.add(r1);

        e = aktienliste.elements();
        while(e.hasMoreElements())
        {

            aktie=(Aktie)e.nextElement();
            modell.add(aktie.getName() + " <= " + anteilsbegrenzung + ";");
        }
        return modell;
    }
}