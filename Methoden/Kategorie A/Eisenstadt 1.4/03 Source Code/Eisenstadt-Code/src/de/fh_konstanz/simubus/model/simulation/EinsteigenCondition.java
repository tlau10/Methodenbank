package de.fh_konstanz.simubus.model.simulation;

import de.fh_konstanz.simubus.model.simulation.entities.Passagier;
import desmoj.core.simulator.Condition;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;

/**
 * Ueber das Condition Objekt kann aus einer Warteschlange gezielt Objekte
 * entnommen werden In unserem Fall wollen wir nur Passagiere die mit einer
 * bestimmten Linie fahren wollen.
 */
public class EinsteigenCondition extends Condition
{

   private BusLinie _buslinie;

   /**
    * EinsteigenCondition Konstruktor.
    * 
    * Erzeugt eine neue EinsteigenCondition durch Aufruf des Kontruktors der
    * Superklasse
    * 
    * @param linie
    *           Die zugehörige Buslinie
    * @param owner
    *           Das Model, zu dem das Ereignis gehört
    * @param name
    *           Der Name des Models
    * @param showInTrace
    *           Flag welches angibt, ob dieses Model Ausgaben in das Trace File
    *           schreiben soll
    * 
    */
   public EinsteigenCondition( BusLinie linie, Model owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
      _buslinie = linie;
   }

   public boolean check()
   {
      return false;
   }

   /**
    * Prüft, ob die Buslinie des Passagiers, mit der er fahren muss, mit der
    * übergebenen Buslinie übereinstimmt
    * 
    * @param e
    *           Ist ein Passagier Objekt
    * @return true wenn Buslinien übereinstimmen
    * @return false wenn er mit einer anderen Buslinie fahren muss
    */
   @Override
   public boolean check( Entity e )
   {
      Passagier p = (Passagier) e;
      if ( p.get_buslinie().equals( this._buslinie ) )
      {
         return true;
      }
      else
      {
         return false;
      }
   }

}
