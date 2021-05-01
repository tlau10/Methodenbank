package de.fh_konstanz.simubus.view;

import java.awt.Point;

import org.jgraph.graph.DefaultGraphCell;

/**
 * Die Klasse <code>ZieleCell</code> entspricht einem Ziel auf der Karte.
 * 
 * @author Daniel Weber
 * @version 1.0 (04.07.2006)
 * 
 */
public class ZieleCell extends DefaultGraphCell
{

   /**
	 * 
	 */
	private static final long serialVersionUID = -5688276761658456729L;

/** Name des Ziel */
   private String name;

   /** ID der Ziel */
   private int    id;

   /** Koordinaten des Ziel */
   private Point  koordinaten = new Point();

   /**
    * liefert die ID des Ziel
    * @return ID des Ziel
    */
   public int getId()
   {
      return id;
   }

   /**
    * setzt die ID des Ziel
    * @param id ID des Ziel
    */
   public void setId( int id )
   {
      this.id = id;
   }

   /**
    * liefert den Namen des Ziel
    * @return Name des Ziel
    */
   public String getName()
   {
      return name;
   }

   /**
    * setzt den Namen des Ziel
    * @param name Name des Ziel
    */
   public void setName( String name )
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return this.name;
   }

   /**
    * liefert die Koordinaten des Ziel
    * @return Koordinaten des Ziel
    */
   public Point getKoordinaten()
   {
      return koordinaten;
   }

   /**
    * setzt die Koordinaten des Ziel
    * @param x X-Koordinate des Ziel
    * @param y Y-Koordinate des Ziel
    */
   public void setKoordinaten( int x, int y )
   {
      this.koordinaten.x = x;
      this.koordinaten.y = y;
   }
   
   public String getToolTipString()
   {
      String toolTipText = "<html>Ziel "+this.id+"<br>Klicken und Halten um zu verschieben";
      return toolTipText;
   }
}
