package de.fh_konstanz.simubus.view;

import org.jgraph.graph.DefaultEdge;

/**
 * Die Klasse <code>GesperrteFelderEdge</code> stellt eine Kante auf der Karte
 * dar. Alle Planquadrate durch die diese Kante geht werden bei der Haltestellen-
 * Optimierung als unpassierbar gesetzt.
 * 
 * @author Daniel Weber
 * @version 1.0 (04.07.2005)
 *
 */
public class GesperrteFelderEdge extends DefaultEdge
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 625222151252582473L;

/** Name der Kante */
   private String name;

   /** ID der Kante */
   private int    id;

   /**
    * liefert die ID der Kante
    * @return ID der Kante
    */
   public int getId()
   {
      return id;
   }

   /**
    * setzt die ID der Kante
    * @param id ID der Kante
    */
   public void setId( int id )
   {
      this.id = id;
   }

   /**
    * liefert den Namen der Kante
    * @return Name der Kante
    */
   public String getName()
   {
      return name;
   }

   /**
    * setzt den Namen der Kante
    * @param name Name der Kante
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
      String toolTipText = "<html>Klicken und halten um die gesperrten Felder zu verschieben<br>UMSCHALT + Klicken um einen Knotenpunkt einzufügen";
      return toolTipText;
   }
}
