package de.fh_konstanz.simubus.view;

import java.awt.Point;

import org.jgraph.graph.DefaultGraphCell;

/**
 * Die Klasse <code>HaltestellenCell</code> markiert eine Haltestelle auf der Karte.
 * 
 * @author Daniel Weber
 * @version 1.0 (04.07.2006)
 *
 */
public class HaltestellenCell extends DefaultGraphCell
{

   /**
	 * 
	 */
	private static final long serialVersionUID = -5432945718502198269L;

/** Name der   Haltestelle */
   private String name;

   /** ID der Haltestelle */
   private int    id;

   /** Kapazitaet der Haltestelle */
   private int    kapazitaet;

   /** Koordinaten der Haltestelle */
   private Point  koordinaten = null;

   /**
    * Konstruktor
    * 
    * @param id ID der Haltestelle
    * @param name Name der Haltestelle
    * @param kapazitaet Kapazitaet der Haltestelle
    */
   public HaltestellenCell( int id, String name, int kapazitaet )
   {
      koordinaten = new Point();
      this.id = id;
      this.name = name;
      this.kapazitaet = kapazitaet;
   }

   /**
    * liefert die ID der Haltestelle
    * @return ID der Haltestelle
    */
   public int getId()
   {
      return id;
   }

   /**
    * setzt die ID der Haltestelle
    * @param id ID der Haltestelle
    */
   public void setId( int id )
   {
      this.id = id;
   }

   /**
    * liefert den Namen der Haltestelle
    * @return Name der Haltestelle
    */
   public String getName()
   {
      return name;
   }

   /**
    * setzt den Namen der Haltestelle
    * @param name Name der Haltestelle
    */
   public void setName( String name )
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return "";
   }

   /**
    * liefert die Koordinaten der Haltestelle
    * @return Koordinaten der Haltestelle
    */
   public Point getKoordinaten()
   {
      return koordinaten;
   }

   /**
    * setzt die Koordinaten der Haltestelle
    * @param x X-Koordinate der Haltestelle
    * @param y Y-Koordinate der Haltestelle
    */
   public void setKoordinaten( int x, int y )
   {
      this.koordinaten.x = x;
      this.koordinaten.y = y;
   }

   /**
    * liefert eine Beschreibung der Haltestelle
    * @return Beschreibung der Haltestelle
    */
   public String getToolTipString()
   {
      String toolTipText = "<html>Haltestellen-ID: " + this.id + "<p>Haltestellen-Name: " + this.name +"<br>Klicken und Halten um zu verschieben";
      return toolTipText;
   }

   /**
    * liefert die Kapazitaet der Haltestelle
    * @return Kapazitaet der Haltestelle
    */
   public int getKapazitaet()
   {
      return kapazitaet;
   }

   /**
    * setzt die Kapazitaet der Haltestelle
    * @param kapazitaet Kapazitaet der Haltestelle
    */
   public void setKapazitaet( int kapazitaet )
   {
      this.kapazitaet = kapazitaet;
   }
}
