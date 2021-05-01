package de.fh_konstanz.simubus.view;

import org.jgraph.graph.DefaultEdge;

/**
 * Die Klasse <code>LinienEdge</code> entspricht einer Linie auf
 * der Karte.
 * 
 * @author Daniel Weber
 * @version 1.0 (04.07.2006)
 * 
 */
public class LinienEdge extends DefaultEdge
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 1426972608177431526L;

/** Name der Linie */
   private String name;

   /** ID der Linie */
   private int    id;

   /**
    * liefert die ID der Linie
    * 
    * @return ID der Linie
    */
   public int getId()
   {
      return id;
   }

   /**
    * setzt die ID der Linie
    * 
    * @param id
    *           ID der Linie
    */
   public void setId( int id )
   {
      this.id = id;
   }

   /**
    * liefert den Namen der Linie
    * 
    * @return Name der Linie
    */
   public String getName()
   {
      return name;
   }

   /**
    * setzt den Namen der Linie
    * 
    * @param name
    *           Name der Linie
    */
   public void setName( String name )
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return name + "_" + String.valueOf( id );
   }
}
