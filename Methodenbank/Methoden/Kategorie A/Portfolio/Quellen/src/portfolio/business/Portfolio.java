/**
 * Überschrift:   Programm zur linearen Portfoliooptimierung
 * Beschreibung:
 * Copyright:     Copyright (c) 2002
 * Organisation:  FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
package portfolio.business;

import java.util.*;


public class Portfolio
{
    private static boolean ZUFALLSAKTIENLISTE_AKTIVIERT;

    private Vector aktienliste;
    private Hashtable aktienHashVolleNamen;
    private Vector datum;
    private int schrittweite;

    private static Random random; // zentrale Random-Instanz
    private Vector aktienlisteInput;

    private long vergleichszeitraumTage;
    private Long startzeit;
    private Long endzeit;


    /**
     * constructor
     * @param a
     * @param d
     */
    public Portfolio(Vector a, Hashtable aktienHash, Vector d)
    {
        ZUFALLSAKTIENLISTE_AKTIVIERT = false;
        aktienliste = a;
        datum = d;
        this.aktienHashVolleNamen = aktienHash; //tp
        startzeit = (Long)datum.firstElement();
        endzeit = (Long)datum.lastElement();
        aktienlisteInput = a;
        vergleichszeitraumTage = 365 *24 * 60 * 60 * 1000;
    }


////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//////////////////// SETTER & GETTER ///////////////////////////////////////////
    /**
     *
     * @param vergleichszeitraumTageInMillis
     */
    public void setVergleichszeitraumTage(long vergleichszeitraumTage)
    {
      this.vergleichszeitraumTage = vergleichszeitraumTage *24 * 60 * 60 * 1000;
    }

    public void setAnalyseDatumVon(long von)
    {
      this.startzeit = new Long(von);
    }

    public void setAnalyseDatumBis(long bis)
    {
      this.endzeit = new Long(bis);
    }

    public Date getAnalyseDatumVon()
    {
      Date newDate = new Date( this.startzeit.longValue() );
      return newDate;
    }

    public Date getAnalyseDatumBis()
    {
      Date newDate = new Date( this.endzeit.longValue() );
      return newDate;
    }

    public void setSchrittweite(int s)
    {
        schrittweite = s;
    }

    public Vector getAktienListe()
    {
        return aktienliste;
    }


    public void berechneRendite()
    {
        Hashtable hash = new Hashtable();
        Vector myAktienliste = getAktienListe();

        for(int f =0; f<datum.size(); f++)
        {
          //hash.put(key datum, value fortlaufendeNummer);
          hash.put( ((Long)datum.get(f)), new Integer(f));
        }

        for (int k =0; k<myAktienliste.size(); k++)
        {
          int index =0;
          double rendite=0;
          double aktienrendite=0;
          double durchschnitt=0;
          Aktie temp = (Aktie)myAktienliste.get(k);
          temp.clearEinzelRendite();

          int anz=0;

            //Vector datum in der eingegebenen Schrittweite durchlaufen
            //Dabei werden nur die Aktienkurse berücksichtig, die im eingegebenen Zeitraum liegen
            for (int i=0; i<datum.size(); (i+=schrittweite) )
            {
              Long d =((Long)datum.get(i));
                //nur wenn Datum im eingegebenen Bereich liegt
                if (d.compareTo(startzeit) >= 0 && d.compareTo(endzeit) <= 0)
                {
                  Long long2 = new Long(d.longValue()+vergleichszeitraumTage); // berechne Datum des Vergleichswertes
                  // Falls Datum nicht in Hashtable -> Rest der Funktion überspringen
                  if( hash.containsKey(long2) )
                  {
                    anz++;

                    index = ((Integer)hash.get(long2)).intValue();
                    rendite=temp.getWert(index)/(temp.getWert(i)/100)-100;
                    temp.addEinzelRendite(rendite); // Für Typ4 Modell
                    aktienrendite += rendite;
                  }
                }
              }
            aktienrendite = aktienrendite/anz;
            temp.setRendite(aktienrendite);
        }
    }



  public void erzeugeAktienlisteZufall(int AnzahlZufälligAuszuwählenderAktien)
    {
      Vector aktienlisteNeu = new Vector();
      Vector Restvektor = new Vector();
      Restvektor = aktienliste; // Aktien werden hieraus übernommen

      Vector Indexes = new Vector(); //Hilfsvektor mit der Info ob Aktie schon in ZufallAuswahl drin ist
      Double voll = new Double(1); //da aktienInputVektor beim poppen vom Restvektor gelöscht wurde
      Double leer = new Double(0);

      for(int i=0; i<aktienliste.size(); i++) //init voller Vektor
        Indexes.add(i,voll);

      Aktie aktie;
      int maxsize=aktienliste.size();
      int randomAktie=0;

      if (maxsize<AnzahlZufälligAuszuwählenderAktien)
        AnzahlZufälligAuszuwählenderAktien=maxsize;//es gibt garnicht so viele aktien wie ausgewählt

      for(int i=0; i<AnzahlZufälligAuszuwählenderAktien; i++)
      {
        //zufallsaktienindex//////////////////////////
        do{
          if (random == null)
          {
              random = new Random();
          }
          double randomNumber = random.nextDouble();
          int max=Indexes.size();
          randomAktie=(int)Math.floor(randomNumber*max);
        } while(Indexes.get(randomAktie).equals(leer));
        //ende zufallsaktienindex/////////////////////

        aktie = (Aktie)Restvektor.get(randomAktie); //hole Aktie aus TempVektor
        Indexes.remove(randomAktie);                //lösche entsprechender Index der gepoppten Aktie
        Indexes.add(randomAktie,leer);

        aktienlisteNeu.add(aktie);                 //baue neuen ZufallsauswahlVektor
      }
      aktienliste=aktienlisteNeu;                  //zusammengebauter Vektor ist die neue AktienListe
      aktienlisteNeu = null;
      Restvektor = null;
      Indexes = null;
    }


    /**
     * setze die aktienliste auf die ursprüngliche zurück
     */
    public void resetAktienliste() {
      if( aktienlisteInput != null && aktienliste != null )
        this.aktienliste = this.aktienlisteInput;
    }

    /**
     * liefert die Hashtable, die als Key das Aktienkürzel und als Wert den
     * vollen Namen der Aktie beinhaltet.
     * @return
     */
    public Hashtable getAktienHashVolleNamen() {
      return aktienHashVolleNamen;
    }
}