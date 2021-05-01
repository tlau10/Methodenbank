package de.fh_konstanz.simubus.view;

import org.jgraph.graph.DefaultEdge;

/**
 * Die Klasse <code>StrassenEdge</code> entspricht einer Strasse auf der
 * Karte.
 * 
 * @author Daniel Weber
 * @version 1.0 (04.07.2006)
 * 
 */
public class StrassenEdge extends DefaultEdge
{

   /**
	 * 
	 */
	private static final long serialVersionUID = -294511328077728970L;

/** Name der Strasse */
   private String name;

   /** ID der Strasse */
   private int    id;

   /**
    * liefert die ID der Strasse
    * 
    * @return ID der Strasse
    */
   public int getId()
   {
      return id;
   }

   /**
    * setzt die ID der Strasse
    * 
    * @param id
    *           ID der Strasse
    */
   public void setId( int id )
   {
      this.id = id;
   }

   /**
    * liefert den Namen der Strasse
    * 
    * @return Name der Strasse
    */
   public String getName()
   {
      return name;
   }

   /**
    * setzt den Namen der Strasse
    * 
    * @param name
    *           Name der Strasse
    */
   public void setName( String name )
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return name;
   }
   
   public String getToolTipString()
   {
      String toolTipText = "<html>Klicken und halten um die Straße zu verschieben<br>UMSCHALT + Klicken um einen Knotenpunkt einzufügen";
      return toolTipText;
   }
}
