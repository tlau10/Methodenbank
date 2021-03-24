package de.fh_konstanz.simubus.model;

import java.awt.Point;
import java.io.Serializable;

import de.fh_konstanz.simubus.util.OrUtil;

/**
 * Die Klasse <code>Haltestelle</code> stellt eine Bushaltestelle dar, welche
 * ihre Daten, wie Koordinaten, Name und Kapazität speichert.
 * 
 * @author Ingo Kroh, Michael Franz, Daniel Weber
 * @version 1.0 (05.06.2006)
 */
public class Haltestelle implements Serializable
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 9208373655256467970L;

/**
    * Koordinaten der Haltestelle
    */
   private Point  koordinaten;

   /**
    * Name der Haltestelle
    */
   private String name;

   /**
    * Kapazität der Haltestelle
    */
   private int    kapazitaet;

   /**
    * ID der Haltestelle
    */
   private int    id;

   /**
    * Konstruktor, hier werden die Integer-Werte übergeben, mit der die
    * Haltestelle an den Koordinaten angelegt wird.
    * 
    * @param x
    *           X-Wert der Haltestelle
    * @param y
    *           Y-Wert der Haltestelle
    */
   public Haltestelle( int x, int y )
   {
      koordinaten = new Point( x, y );
   }

   /**
    * Konstruktor, hier wird ein <code>Point</code>,ein <code>String</code>
    * und ein Integer-Wert übergeben, mit der die Haltestelle angelegt wird,der
    * Name und die Kapazität der Haltestelle gesetzt werden.
    * 
    * @param x
    *           X-Wert der Haltestelle
    * @param y
    *           Y-Wert der Haltestelle
    * @param name
    *           Name der Haltestelle
    * @param kapa
    *           Kapazität der Haltestelle
    * 
    */

   public Haltestelle( int id, int x, int y, String name, int kapa )
   {
      this( x, y );
      this.id = id;
      this.name = name;
      this.kapazitaet = kapa;
   }

   /**
    * Konstruktor, hier wird ein <code>Point</code> übergeben, mit der die
    * Haltestelle angelegt wird.
    * 
    * @param koordinaten
    *           Point-Koordinaten der Haltestelle
    */
   public Haltestelle( Point koordinaten )
   {
      this.koordinaten = koordinaten;
   }
   
   public Haltestelle() {
	   
   }

   /**
    * 
    * @return liefert den Namen der Haltestelle
    */
   public String getName()
   {
      return name;
   }

   /**
    * setzt den Namen der Haltestelle
    * 
    * @param name
    *           übergebener String mit Haltestellenname
    */
   public void setName( String name )
   {
      this.name = name;
   }

   /**
    * 
    * @return liefert die <code>Point</code>-Koordinaten der Haltestelle
    *         zurück
    */
   public Point getKoordinaten()
   {
      return koordinaten;
   }

   /**
    * setzt die <code>Point</code>-Koordinaten der Haltestelle
    * 
    * @param koordinaten
    *           neue Koordinaten der Haltestelle
    */
   public void setKoordinaten( Point koordinaten )
   {
      this.koordinaten = koordinaten;
   }

   /**
    * setzt den X-Wert der Haltstellen-Koordinaten neu
    * 
    * @param x
    *           X-Wert der Koordinaten
    */
   public void setX( int x )
   {
      koordinaten.x = x;
   }

   /**
    * setzt den Y-Wert der Haltstellen-Koordinaten neu
    * 
    * @param y
    *           Y-Wert der Koordinaten
    */
   public void setY( int y )
   {
      koordinaten.y = y;
   }

   /**
    * setzt die Kapazität der Haltestelle neu
    * 
    * @param k
    *           Integer Wert der Kapazität
    * 
    */
   public void setKapazitaet( int k )
   {
      this.kapazitaet = k;
   }

   /**
    * liefert die Kapazität der Haltestelle
    * 
    * @return Kapazität
    */
   public int getKapazitaet()
   {
      return this.kapazitaet;
   }

   /**
    * setzt die ID der Haltestelle
    * 
    * @param id
    *           ID der Haltestelle
    */
   public void setID( int id )
   {
      this.id = id;
   }

   /**
    * liefert die ID der Haltestelle
    * 
    * @return ID der Haltestelle
    */
   public int getID()
   {
      return this.id;
   }

   /**
    * liefert die Pixel-Koordinate umgerechnet vom Planquadrat
    * 
    * @return Pixel-X-Koordinate der Haltestelle
    */
   public int getPixelXCoordinate()
   {
      return OrUtil.getPixelXCoordinate( koordinaten.x );
   }

   /**
    * liefert die Pixel-Koordinate umgerechnet vom Planquadrat
    * 
    * @return Pixel-Y-Koordinate der Haltestelle
    */
   public int getPixelYCoordinate()
   {
      return OrUtil.getPixelYCoordinate( koordinaten.y );
   }

   @Override
   public int hashCode()
   {
      return koordinaten.hashCode();
   }

   @Override
   public boolean equals( Object obj )
   {
      boolean result = false;

      if ( obj instanceof Haltestelle )
      {
         result = ( (Haltestelle) obj ).koordinaten.equals( koordinaten );
      }
      return result;
   }

   @Override
   public String toString()
   {
      return name;
   }
}
