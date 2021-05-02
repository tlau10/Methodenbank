package de.fh_konstanz.simubus.model.simulation;

import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.model.simulation.entities.Passagier;
import desmoj.core.simulator.Condition;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;

/**
 * Ueber das Condition Objekt kann aus einer Warteschlange gezielt Objekte
 * entnommen werden In unserem Fall wollen wir nur Passagiere die mit einer
 * bestimmten Linie fahren wollen.
 */
public class AussteigenCondition extends Condition
{

   private HaltestelleJ _hstelle;

   /**
    * AussteigenCondition Konstruktor.
    * 
    * Erzeugt eine neue AussteigenCondition durch Aufruf des Kontruktors der
    * Superklasse
    * 
    * @param ziel
    *           aktuelle Haltestelle
    * @param owner
    *           Das Model, zu dem das Ereignis gehört
    * @param name
    *           Der Name des Models
    * @param showInTrace
    *           Flag welches angibt, ob dieses Model Ausgaben in das Trace File
    *           schreiben soll
    * 
    */
   public AussteigenCondition( HaltestelleJ ziel, Model owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
      _hstelle = ziel;
   }

   public boolean check()
   {
      return false;
   }

   /**
    * Prüft, ob die aktuelle Haltestelle mit der Zielhaltestelle des Passagiers
    * übereinstimmt
    * 
    * 
    * @param e
    *           Ist ein Passagier Objekt
    * @return true wenn aktuelle Haltestelle == Zielhaltestelle des Passagiers
    * @return false wenn !=
    */
   @Override
   public boolean check( Entity e )
   {
      Passagier p = (Passagier) e;
      if ( p.get_ziel() == _hstelle )
      {
         return true;
      }
      else
      {
         return false;
      }
   }

}
