package de.fh_konstanz.simubus.model.optimierung;

import de.fh_konstanz.simubus.model.Planquadrat;

/**
 * Die Klasse <code>StreetDetails</code> besteht aus einem Planquadrat und ein
 * Feld welches die Zeit zum Ziel speichert.
 * 
 * @author Michael Franz
 * @version 1.0 (24.05.2006)
 */

public class StreetDetails
{
   /** Strassenfeld */
   private Planquadrat planquadrat;

   /** Zeit vom Zielfeld zum Strassenfeld */
   private double      zeit;

   /**
    * Konstruktor
    * 
    * @param aPlanquadrat
    *           Strassenfeld
    * @param aZeit
    *           Zeit vom Zielfeld zum Strassenfeld
    */

   public StreetDetails( Planquadrat aPlanquadrat, double aZeit )
   {
      planquadrat = aPlanquadrat;
      zeit = aZeit;
   }

   /**
    * liefert das Strassenfeld
    * 
    * @return Strassenfeld
    */
   public Planquadrat getPlanquadrat()
   {
      return planquadrat;
   }

   /**
    * liefert die Zeit vom Ziel zum Strassenfeld
    * 
    * @return Zeit vom Ziel zum Strassenfeld
    */
   public double getZeit()
   {
      return zeit;
   }
}
