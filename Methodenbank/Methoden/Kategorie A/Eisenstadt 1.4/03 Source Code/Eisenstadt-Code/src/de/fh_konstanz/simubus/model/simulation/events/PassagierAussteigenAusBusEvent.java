package de.fh_konstanz.simubus.model.simulation.events;

import de.fh_konstanz.simubus.model.simulation.AussteigenCondition;
import de.fh_konstanz.simubus.model.simulation.BusSimulationModel;
import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.model.simulation.entities.Passagier;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

/**
 * Diese Klasse dient zur Erzeugung eines PassagierAussteigenAusBus Ereignisses
 * User: tklein, smuell, pstader Date: 11.06.2006
 */
public class PassagierAussteigenAusBusEvent extends Event
{
   BusSimulationModel _model;

   int                index;

   /**
    * PassagierAussteigenAusBusEvent Konstruktor.
    * 
    * Erzeugt ein neues PassagierAussteigenAusBusEvent Ereignis durch Aufruf des
    * Kontruktors der Superklasse
    * 
    * @param owner
    *           Das Model, zu dem das Ereignis gehört
    * @param name
    *           Der Name des Models
    * @param showInTrace
    *           Flag welches angibt, ob dieses Model Ausgaben in das Trace File
    *           schreiben soll
    * 
    */
   public PassagierAussteigenAusBusEvent( Model owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
      _model = (BusSimulationModel) owner;
      this.index = 0;
   }

   /**
    * Die Ereignis Routine bescheibt, wie Passagiere den Bus verlassen
    * 
    * Es wird geprüft, ob die Zielhaltestelle der Passagiere mit der aktuellen
    * Haltestelle übereinstimmt. Wenn ja, dann werden die entsprechenden
    * Passagiere der Warteschlange im Bus entnommen (=steigen aus) und verlassen
    * das System.
    * 
    * Anschließend wird ein neues PassagierEinsteigenInBus Ereignis geplant.
    */
   public void eventRoutine( Entity who )
   {
      Bus b = (Bus) who;
      AussteigenCondition condition = new AussteigenCondition( b.get_currentHaltestelle(), _model,
            "AussteigenCondition", true );

      System.out.println( "Bus: " + b.getName() + " ist an HS: " + b.get_currentHaltestelle().getName() );
      Passagier p;
      boolean bool = true;
      while ( bool )
      {
         // Nur Passagiere aus der Queue holen, die hier austeigen wollen
         p = (Passagier) b.get_passagierQueue().first( condition );
         if ( p != null )
         {
            // Diese Passagiere aus der Quere im Bus rausholen
            b.get_passagierQueue().remove( p );
            System.out.println( "Passagier hat Bus: " + b.getName() + " verlassen  ZielHS des Passagiers: "
                  + p.get_ziel().getName() );
            ++index;

         }
         else
         {
            bool = false;
         }
      }

      SimuControlPanel panel = SimuControlPanel.getInstance();
      panel.setSimuPassagierSteigenAus( index );

      PassagierEinsteigenInBusEvent pEinsteigen = new PassagierEinsteigenInBusEvent( this._model,
            "Passagiere steigen in den Bus ein", true );
      pEinsteigen.schedule( b, new SimTime( 0.0 ) );
   }
}
