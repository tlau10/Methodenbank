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

public class Typ1Modell implements LPModell
{

    private double anteilsbegrenzung;
    private double mumin;
    private int schrittweite = 1;


    public Typ1Modell()
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
    //     min: ... + M rendVerf
    //     R2: µ1*aktie1 + µ2*aktie2 + ... + rendVerf >= mumin
    //     wenn != 0, dann ist es == rendiNeg + mumin
    //     (erforderlich wegen M-Methode)
    //     ACHTUNG: Zielfunktionswert hat keine Aussage mehr
    public Vector erstelleModell(Vector aktienliste)
    {
        Vector modell = new Vector();
        //Vector aktienliste = aktienliste;
        Aktie aktie = null;
        String zielfunktion = "min:";
        String temp = "";
        String r0 = "R0:\t";
        String r1 = "R1:\t";
        String r2 = "R2:\t";


        Enumeration e = aktienliste.elements();
        if(e.hasMoreElements())
        {
            aktie = (Aktie)e.nextElement();
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

        modell.add(r0);
        modell.add(r1);
        modell.add(r2);



        // Varianz in Nebenbedingungen
        String nebenbedingung = null;
        double koeffizient = 1;
        koeffizient /= aktie.getLaengeEinzelRendite();
        for (int i=3,k=0;k<(aktie.getLaengeEinzelRendite());i++,k++)
        {
            if(k==0)
                zielfunktion += koeffizient + " " + ABS_ABW_W + k + " +" + koeffizient + " " + ABS_ABW_V + k;
            else
                zielfunktion += " +" +koeffizient + " " + ABS_ABW_W + k + " +" + koeffizient + " " + ABS_ABW_V + k;

            Aktie aktietemp = null;
            nebenbedingung = "R" + i + ":\t-" + ABS_ABW_W + k + " +" + ABS_ABW_V + k + " ";
            Enumeration enum = aktienliste.elements();
            while(enum.hasMoreElements())
            {
                aktietemp = (Aktie)enum.nextElement();
                if((aktietemp.getEinzelRendite(k)-aktietemp.getRendite())<0)
                {
                    nebenbedingung += " " + (aktietemp.getEinzelRendite(k)-aktietemp.getRendite());
                }
                else
                {
                    nebenbedingung += " + " + (aktietemp.getEinzelRendite(k)-aktietemp.getRendite());
                }
                nebenbedingung += " " + aktietemp.getNameSolver();
            }
            // -> ist fehlerhaft. Korrigiert 16.07.02
            //nebenbedingung += " -y >= 0;";
            nebenbedingung += " = 0;";

            modell.add(nebenbedingung);

        }
        zielfunktion += " + " + M_VALUE + " " + LPModell.RENDITE_VERFEHLT + ";";

        modell.add(0, zielfunktion); // Zielfunktion an den Anfang des LP-Modells setzen

        // Anteilsbegrenzung hinzufügen
        e = aktienliste.elements();
        while(e.hasMoreElements())
        {
            aktie=(Aktie)e.nextElement();
            modell.add(aktie.getNameSolver() + " <= " + anteilsbegrenzung + ";");
        }

        return modell;
    }



}