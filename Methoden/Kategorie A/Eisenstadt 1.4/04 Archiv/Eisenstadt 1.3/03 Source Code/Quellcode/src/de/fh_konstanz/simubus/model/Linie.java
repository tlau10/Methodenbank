package de.fh_konstanz.simubus.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse <code>Linie</code> stellt ein Buslinie dar, welche aus mehreren
 * Linienabschnitten besteht. Genau ein Linienabschnitt verbindet 2 Haltestellen
 * und kann aus mehrere Teilstrecken bestehen, die in einer Linie abgespeichert
 * werden.
 * 
 * @author Ingo Kroh
 * @version 1.0 (09.06.2006)
 */

public class Linie implements Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = -224071698175740564L;

/**
    * Liste mit Haltestellen, die zu dieser Linie gehören
    */
   private List<Haltestelle> haltestellen = null;

   /**
    * Liste der Teilstrecken, aus der eine Linie besteht.
    */
   private List<Teilstrecke> teilstrecken = null;

   /**
    * eindeutige ID der Buslinie
    */
   private String            id           = null;

   /**
    * Linienfarbe in der die Linie auf der Oberfläche erscheint.
    */
   private Color             linienfarbe  = null;

   /**
    * maximale Kapazität der Passagiere, die ein Bus der Linie haben kann.
    */
   private int               maxKap;

   /**
    * Zeit, bis der Bus wieder in das System eintritt.
    */
   private double            wiederkehrzeit;

   /**
    * Konstruktor, hier wird eine neue Linie angelegt, zu jeder Linie wird eine
    * neue Haltestellenliste und eine neue Teilstreckenliste angelegt, des
    * Weiteren die Kapazität des Busses auf 50 Passagiere definiert und die
    * Wiederkehrzeit mit 0 vorbelegt.
    * 
    * @param aId
    *           eindeutige ID, mit der die Linie angelegt wird.
    */
   public Linie( String aId )
   {
      id = aId;
      haltestellen = new ArrayList<Haltestelle>();
      teilstrecken = new ArrayList<Teilstrecke>();

      this.maxKap = 50; // Default-Wert für maximale Kapazität des Busses
      this.wiederkehrzeit = 0;
   }

   /**
    * liefert die Liste mit den Haltestellen der Linie
    * 
    * @return Haltestellenliste
    */
   public List<Haltestelle> getHaltestellen()
   {
      return haltestellen;
   }

   /**
    * liefert die Id der Linie
    * 
    * @return Linien-ID
    */
   public String getId()
   {
      return id;
   }

   /**
    * liefert die Liste mit den Teilstrecken der Linie
    * 
    * @return Teilstreckenliste
    */
   public List<Teilstrecke> getTeilstrecken()
   {
      return teilstrecken;
   }

   /**
    * setzt die Teilstreckenliste neu
    * 
    * @param aTeilstrecke
    *           Teilstreckenliste, die neu gesetzt wird
    */
   public void setTeilstrecken( List<Teilstrecke> aTeilstrecke )
   {
      teilstrecken = aTeilstrecke;
   }

   @Override
   public int hashCode()
   {
      return id.hashCode();
   }

   @Override
   public boolean equals( Object obj )
   {
      boolean result = false;

      if ( obj instanceof Linie )
      {
         if ( ( (Linie) obj ).id.equals( id ) == true )
         {
            result = true;
         }
      }
      return result;
   }

   @Override
   public String toString()
   {
      return id;
   }

   /**
    * löscht die Liste der Teilstrecken
    */
   public void removeAllTeilstrecken()
   {
      teilstrecken.clear();

   }

   /**
    * setzt einen neuen Teilstreckenabschnitt
    * 
    * @param i
    *           ID der Teilstrecke
    * 
    * @param start
    *           Startpunkt der Teilstrecke
    * 
    * @param ende
    *           Endpunkt der Teilstrecke
    */
   public void setPunkte( int i, Point2D start, Point2D ende )
   {
      Teilstrecke teilstrecke = new Teilstrecke( i, "Teilstrecke "
            + String.valueOf( this.getTeilstrecken().size() + 1 ), start, ende, 50, 10 );
      teilstrecken.add( teilstrecke );

   }

   /**
    * liefert die Liniefarbe
    * 
    * @return Linienfarbe
    */
   public Color getLinienfarbe()
   {
      return linienfarbe;
   }

   /**
    * setzt die Linienfarbe neu
    * 
    * @param linienfarbe
    *           neue Farbe in der die Linie angezeigt werden soll.
    */
   public void setLinienfarbe( Color linienfarbe )
   {
      this.linienfarbe = linienfarbe;
   }

   /**
    * setzt die ID der Linie neu
    * 
    * @param aId
    *           Id der Linie
    */
   public void setId( String aId )
   {
      id = aId;

   }

   /**
    * fügt eine neue Haltestelle der Linie hinzu
    * 
    * @param haltestelle
    *           Haltestelle, die der Linie hinzugefügt wird.
    */
   public void addHaltestelle( Haltestelle haltestelle )
   {
      if ( haltestellen.contains( haltestelle ) == false )
      {
         haltestellen.add( haltestelle );
      }
   }

   /**
    * setzt die Haltestellenliste der Linie neu
    * 
    * @param haltestellen
    *           neue Haltestellenliste
    */
   public void setHaltestellen( List<Haltestelle> haltestellen )
   {
      this.haltestellen = haltestellen;
   }

   /**
    * setzt die Kapazität des Busses einer Linie neu
    * 
    * @param kap
    *           neue Buskapazität
    */
   public void setMaxKapBusPassagiere( int kap )
   {
      this.maxKap = kap;
   }

   /**
    * liefert die Kapazität der Busse der Linie
    * 
    * @return Buskapazität
    */
   public int getMaxKapBusPassagiere()
   {
      return this.maxKap;
   }

   /**
    * setzt die Wiederkehrzeit
    * 
    * @param time
    *           Wiederkehrzeit
    */
   public void setFrequenz( double time )
   {
      this.wiederkehrzeit = time;
   }

   /**
    * liefert die Wiederkehrzeit
    * 
    * @return Wiederkehrzeit
    */
   public double getFrequenz()
   {
      return this.wiederkehrzeit;
   }
}
