package portfolio.business;

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
    private Hashtable aktienHashVolleNamen;
    public Aktienparser()
    {
        aktienliste = new Vector();
        datum = new Vector();
        aktienHashVolleNamen = new Hashtable();
    }

    /**
     * main Methode - nur zum Testen
     * @param args
     */
    public static void main(String[] args)
    {
        // Nur zum Testen
        Aktienparser aktienparser1 = new Aktienparser();
        File f = new File("Qualsich.csv");
        aktienparser1.parseFile(f);
    }


    /**
     * parse the given file
     * @param f
     * @return
     */
    public Portfolio parseFile(File f)
    {
        String currentLine;

        try{
            BufferedReader in = new BufferedReader(new FileReader(f));
            while((currentLine = in.readLine()) != null)
            {
              // Zeile mit den Aktiennamen
                if(currentLine.startsWith(";"))
                {
                    String zahl=null;
                    String temp=null;
                    String temp2=null;
                    int z = 0;
                    StringTokenizer tokenizer = new StringTokenizer(currentLine);
                    while(tokenizer.hasMoreTokens())
                    {
                         Aktie a = new Aktie();
                         z++;
                         if(z<10)
                         {
                            zahl="000";
                            zahl = zahl + zahl.valueOf(z);

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
                         temp = tokenizer.nextToken(";");
                         // temp contains the real name of the stock (Aktie)
                         a.setName(temp);

                         // Leerzeichen, +, -, * entfernen sonst funktioniert LPSOLVE nicht.
                        temp2 = "";
                        for(int ws=0; ws < temp.length(); ws++)
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
                                    temp2 += temp.charAt(ws);
                                    break;
                            }
                        }
                        String ganzerName = temp; //tp
                        temp = temp2;
                        temp = temp.substring(0,4);
                        temp += zahl;
                        a.setNameSolver(temp);
                        aktienliste.add(a);
                        aktienHashVolleNamen.put(temp, ganzerName); //tp
                    }
                }

                // Zeile mit Werten
                else
                {
                    StringTokenizer tokenizer = new StringTokenizer(currentLine, ";");
                    int i = 0;
                    int k = 0;
                    Aktie currentAktie;
                    while(tokenizer.hasMoreTokens())
                    {
                        if(i==0)
                        {
                            try{
                              Long lDatum = new Long(tokenizer.nextToken());
                              datum.add(lDatum);
                              i=1;
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                          //Double d = new Double(tokenizer.nextToken(";").replace(',','.'));
                          //datum.add(d);
                          currentAktie = (Aktie)aktienliste.get(k);
                          currentAktie.addWert(tokenizer.nextToken().replace(',','.'));
                          k++;
                        }
                    }
                }
            }
            in.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return new Portfolio(aktienliste, aktienHashVolleNamen, datum);
    }

}