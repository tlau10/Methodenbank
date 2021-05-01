package de.fh_konstanz.simubus.model.optimierung;

import java.util.ArrayList;

import de.fh_konstanz.simubus.model.Planquadrat;

/**
 * Die Klasse <code>TargetDetails</code> beinhaltet ein Ziel-Planquadrat,
 * sowie die Strassenliste und ein Kennzeichen, welches aussagt, ob die
 * Strassen-Planquadrate innerhalb einer vorgegebenen Zeit erreicht werden
 * können.
 * 
 * 
 * @author Michael Franz
 * @version 1.0 (19.05.2006)
 */

public class TargetDetails
{
   /** Zielfeld von dem aus die Suche gestartet wird */
   private Planquadrat              planquadrat;

   /**
    * Liste der Strassenfelder, die in Reichweite der vorgegebenen Zeit erreicht
    * wird
    */
   private ArrayList<StreetDetails> strassenliste = new ArrayList<StreetDetails>();

   /**
    * Boolean der anzeigt, ob kein Strassenteil in Reichweite der vorgegebenen
    * Zeit war
    */
   private boolean                  notInRange;

   /**
    * Konstruktor
    * 
    * @param aPlanquadrat
    *           Zielfeld von dem aus die Suche gestartet wird
    * @param aStrassenliste
    *           Liste der Strassenfelder, die in Reichweite der vorgegebenen
    *           Zeit erreicht wird
    * @param aNotInRange
    *           Boolean der anzeigt, ob kein Strassenteil in Reichweite der
    *           vorgegebenen Zeit war
    */
   public TargetDetails( Planquadrat aPlanquadrat, ArrayList<StreetDetails> aStrassenliste,
         boolean aNotInRange )
   {
      planquadrat = aPlanquadrat;
      strassenliste = aStrassenliste;
      notInRange = aNotInRange;
   }

   /**
    * liefert das Zielfeld
    * 
    * @return liefert das Zielfeld
    */
   public Planquadrat getPlanquadrat()
   {
      return planquadrat;
   }

   /**
    * liefert die Strassenliste
    * 
    * @return liefert die Strassenliste
    */
   public ArrayList<StreetDetails> getStrassenliste()
   {
      return strassenliste;
   }

   /**
    * liefert true, wenn kein Strassenteil innerhalb der vorgegebenen Zeit
    * erreichbar war
    * 
    * @return true, wenn kein Strassenteil innerhalb der vorgegebenen Zeit
    *         erreichbar war
    */
   public boolean getNotInRange()
   {
      return notInRange;
   }
}
