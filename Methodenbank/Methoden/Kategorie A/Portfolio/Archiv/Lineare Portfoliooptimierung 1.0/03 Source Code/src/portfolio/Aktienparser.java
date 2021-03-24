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
import java.io.*;


/*
    Liest die Werte aus einer Datei in den Speicher
*/

public class Aktienparser
{
    private Vector datum;
    private Vector aktienliste;

    public Aktienparser()
    {
        aktienliste = new Vector();
        datum = new Vector();
    }

    public static void main(String[] args)
    {
        // Nur zum Testen
        Aktienparser aktienparser1 = new Aktienparser();
        File f = new File("Qualsich.csv");
        aktienparser1.parseFile(f);
    }

    public Portfolio parseFile(File f)
    {
        String s, sa = new String();
        String s2=  new String();
        try{
            BufferedReader in = new BufferedReader(new FileReader(f));
            while((s = in.readLine())!=null)
            {

                if(s.startsWith(";"))
                {
                    String zahl=null;
                    String temp=null;
                    String temp2=null;
                    int z = 0;
                    StringTokenizer at = new StringTokenizer(s);
                    while (at.hasMoreTokens())
                    {

                         Aktie a = new Aktie();
                         z++;
                         if(z<10)
                         {
                            zahl="000";
                            zahl = zahl+zahl.valueOf(z);

                         }
                         else if(z<100)
                         {
                            zahl="00";
                            zahl = zahl+zahl.valueOf(z);

                         }
                         else if(z<1000)
                         {
                            zahl="0";
                            zahl = zahl+zahl.valueOf(z);

                         }
                         else
                         {
                            zahl="";
                            zahl = zahl+zahl.valueOf(z);
                         }
                         temp=at.nextToken(";");

                         // Leerzeichen, +, -, * entfernen sonst funktioniert LPSOLVE nicht.
                        temp2 = "";
                        for(int ws =0;ws < temp.length();ws++)
                        {
                            switch (temp.charAt(ws))
                            {
                                case ' ':
                                    break;
                                case '+':
                                    break;
                                case '-':
                                    break;
                                case '*':
                                    break;
                                default:
                                    temp2+= temp.charAt(ws);
                                    break;
                            }
                        }
                        temp = temp2;
                        temp = temp.substring(0,4);
                        temp += zahl;
                        a.setName(temp);
                        aktienliste.add(a);
                    }
                }
                else
                {
                    StringTokenizer t = new StringTokenizer(s);
                    int i = 0;
                    int k = 0;
                    Aktie temp;
                    while (t.hasMoreTokens())
                    {
                        if(i==0)
                        {
                            try{
                                Double d = new Double(t.nextToken(";").replace(',','.'));
                                datum.add(d);
                                i =1;
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        temp = (Aktie)aktienliste.get(k);
                        temp.addWert(t.nextToken(";").replace(',','.'));
                        k++;
                    }
                }
            }
            in.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return new Portfolio(aktienliste,datum);
    }

}