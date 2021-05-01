package de.fh_konstanz.simubus.model.simulation.events;

import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.model.simulation.entities.Passagier;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;

/**
 * Diese Klasse dient zur Erzeugung eines PassagierAnkunftAnHaltestelle
 * Ereignisses User: tklein, smuell, pstader Date: 11.06.2006
 */
public class PassagierAnkunftAnHaltestelleEvent extends Event
{

   /**
    * PassagierAnkunftAnHaltestelleEvent Konstruktor.
    * 
    * Erzeugt ein neues PassagierAnkunftAnHaltestelleEvent Ereignis durch Aufruf
    * des Kontruktors der Superklasse
    * 
    * @param owner
    *           Das Model, zu dem das Ereignis geh�rt
    * @param name
    *           Der Name des Models
    * @param showInTrace
    *           Flag welches angibt, ob dieses Model Ausgaben in das Trace File
    *           schreiben soll
    * 
    */
   public PassagierAnkunftAnHaltestelleEvent( Model owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
   }

   /**
    * Die Ereignis Routine bescheibt, was passiert wenn ein Passagier an seiner
    * Starthaltestelle ankommt.
    * 
    * Der Passagier wird in die Warteschlange der Haltestelle eingef�gt.
    * 
    */
   public void eventRoutine( Entity who )
   {
      Passagier p = (Passagier) who;
      // Referenz auf das Haltestellen Objekt holen an welcher der Passagier
      // einsteigen will
      // Seine Start Haltestelle.
      HaltestelleJ h = p.get_start();
      // An dieser Haltestelle wird der Passagier in die Queue eingef�gt.
      h.get_passagierWarteschlange().insert( p );
      sendTraceNote( "Laenge PassagierWarteschlange an Haltestelle " + h.getName() + " "
            + h.get_passagierWarteschlange().length() );

      System.out.println( "Passagier in HS: " + h.getName() + " eingef�gt. Zielhaltestelle: "
            + p.get_ziel().getName() );
   }
}
