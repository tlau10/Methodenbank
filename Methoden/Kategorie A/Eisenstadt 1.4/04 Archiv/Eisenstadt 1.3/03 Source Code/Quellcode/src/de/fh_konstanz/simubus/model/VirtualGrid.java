package de.fh_konstanz.simubus.model;

import java.io.Serializable;

/**
 * Die Klasse <code>VirtualGrid</code> beinhaltet eine Matrix, welche aus
 * allen Planquadraten besteht.
 * 
 * @author Michael Franz version 1.0 22.06.2006
 */

public class VirtualGrid implements Serializable
{

   /**
	 * 
	 */
	private static final long serialVersionUID = -8168514668857833574L;

/**
    * Instanz der Matrix mit Planquadraten
    */
   private static VirtualGrid instance;

   /**
    * Konfigurationseinstellungen, die benötigt werden um die Grösse der Matrix
    * festzulegen
    */
   private SimuKonfiguration  config;

   /**
    * Grösse des Editors
    */
   private int                sizeOfEditor;

   /**
    * Pixelgrösse eines Planquadrates
    */
   private int                sizeOfField;

   /**
    * Anzahl der Felder, die die Matrix hat
    */
   private int                anzahlFelder;

   /**
    * Matrix mit Planquadraten
    */
   private Planquadrat[][]    planquadrate;

   /**
    * Konstruktor, Aufbau der Matrix mit den Einstellungen aus Simukonfiguartion
    * 
    */
   private VirtualGrid()
   {
      config = SimuKonfiguration.getInstance();
      sizeOfEditor = config.getResPanel();
      sizeOfField = config.getFeldElementGroesse();
      anzahlFelder = sizeOfEditor / ( sizeOfField );
      planquadrate = new Planquadrat[anzahlFelder][anzahlFelder];

      for ( int j = 0; j < anzahlFelder; j++ )
      {
         for ( int i = 0; i < anzahlFelder; i++ )
         {
            planquadrate[ i ][ j ] = new Planquadrat( i, j );
         }
      }
   }

   /**
    * Singelton
    * 
    * @return liefert die Instanz des Virtual Grid
    */
   public static VirtualGrid getInstance()
   {
      if ( instance == null )
      {
         instance = new VirtualGrid();
      }

      return instance;
   }

   /**
    * liefert die Matrix mit Planquadraten
    * 
    * @return Planquadratmatrix
    */
   public Planquadrat[][] getPlanquadrate()
   {
      return planquadrate;
   }

   /**
    * liefert die Grösse der Matrix
    * 
    * @return Anzahl der Felder
    */
   public int getAnzFelder()
   {
      return this.anzahlFelder;
   }

   public static void setInstance( VirtualGrid virtualGrid )
   {
      instance = virtualGrid;

   }

   public void reset()
   {
      instance = new VirtualGrid();

   }

}