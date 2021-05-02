/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
package portfolio.business;

import java.util.*;

public class Typ4Modell implements LPModell
{
    private Vector aktienliste;
    private double anteilsbegrenzung;
    private double mumin;
    private Vector modell;
    private int schrittweite =1;


    public Typ4Modell()
    {
        anteilsbegrenzung = 0.4;
        mumin = 0;
    }


    public void setAnteilsBegrenzung(double b)
    {
        anteilsbegrenzung=b;
    }

    public void setMuMin(double m)
    {
        mumin=m;
    }


    // rendiNeg + rendiPos = tatsächlich erzielte Rendite
    //     R1: µ1*aktie1 + µ2*aktie2 + ... + rendiNeg - rendiPos = 0
    //     (erforderlich wegen GoalProgramming)
    // rendVerf != 0 --> angestrebte Rendite nicht erreicht
    //     max: ... - M rendVerf ...
    //     R2: µ1*aktie1 + µ2*aktie2 + ... + rendVerf >= mumin
    //     wenn != 0, dann ist es == rendiNeg + mumin
    //     (erforderlich wegen M-Methode)
    //     ACHTUNG: Zielfunktionswert hat keine Aussage mehr
    // RNTx != 0 --> "Rendite Negativ zum Zeitpunkt x"
    //     (erforderlich wegen M-Methode)
    //     ACHTUNG: Zielfunktionswert hat keine Aussage mehr
    public Vector erstelleModell(Vector aktienliste)
    {
        modell= new Vector();
        this.aktienliste = aktienliste;
        Aktie aktie = null;
        String zielfunktion = "max: " + RENDITE_MINIMAL;
          zielfunktion +=  " - " + M_VALUE + " " + LPModell.RENDITE_VERFEHLT;
          zielfunktion +=  " - " + M_VALUE + " " + LPModell.RENDITE_NEGATIVE_ZEITPUNKT_T; //tp
          zielfunktion += ";";
        String temp = "";
        String r0 = "R0:\t";
        String r1 = "R1:\t";
        String r2 = "R2:\t";

        Enumeration e = aktienliste.elements();
        if(e.hasMoreElements())
        {
            aktie=(Aktie)e.nextElement();
            temp = temp + aktie.getRendite() + " " + aktie.getNameSolver();
            r0 = r0 + aktie.getNameSolver();
        }

        while(e.hasMoreElements())
        {
            aktie=(Aktie)e.nextElement();
            if(aktie.getRendite()>=0)
            {
                temp = temp + " + " + aktie.getRendite() + " " + aktie.getNameSolver();
            }
            else
            {
                temp = temp + " " + aktie.getRendite() + " " + aktie.getNameSolver();
            }
            r0=r0  + " + " + aktie.getNameSolver();
        }

        r0=r0 + " = 1;";
        r1=r1 + temp + " - " +
            LPModell.RENDITE_POSITIVE + " + " +
            LPModell.RENDITE_NEGATIVE + " = 0;";

        r2=r2 + temp + " + " + LPModell.RENDITE_VERFEHLT + " >= " + mumin + ";";

        modell.add(zielfunktion);
        modell.add(r0);
        modell.add(r1);
        modell.add(r2);

        String nebenbedingung = null;

        // Nebenbedingungen erzeugen
        for (int i=3,k=0;k<(aktie.getLaengeEinzelRendite());i++,k++)
        {
            Aktie aktietemp = null;
            nebenbedingung = "R" + i + ":\t";
            Enumeration enum = aktienliste.elements();

            if(enum.hasMoreElements())
            {
                // erster Wert
                aktietemp = (Aktie)enum.nextElement();
                nebenbedingung += aktietemp.getEinzelRendite(k);
                nebenbedingung += " " + aktietemp.getNameSolver();
            }

            while(enum.hasMoreElements())
            {
                aktietemp = (Aktie)enum.nextElement();
                if(aktietemp.getEinzelRendite(k)<0)
                {
                    nebenbedingung += " " + aktietemp.getEinzelRendite(k);
                }
                else
                {
                    nebenbedingung += " + " + aktietemp.getEinzelRendite(k);
                }
                nebenbedingung += " " + aktietemp.getNameSolver();
            }
            nebenbedingung += " -" + RENDITE_MINIMAL + " + " + LPModell.RENDITE_NEGATIVE_ZEITPUNKT_T + " >= 0;";
            modell.add(nebenbedingung);

        }

        // Anteilsbegrenzung
        e = aktienliste.elements();
        while(e.hasMoreElements())
        {
            aktie=(Aktie)e.nextElement();
            modell.add(aktie.getNameSolver() + " <= " + anteilsbegrenzung + ";");
        }
        return modell;
    }
}