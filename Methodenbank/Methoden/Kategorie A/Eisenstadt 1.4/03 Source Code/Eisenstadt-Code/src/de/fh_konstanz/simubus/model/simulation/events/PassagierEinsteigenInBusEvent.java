package de.fh_konstanz.simubus.model.simulation.events;

import java.util.ArrayList;
import java.util.Iterator;

import de.fh_konstanz.simubus.model.simulation.BusSimulationModel;
import de.fh_konstanz.simubus.model.simulation.EinsteigenCondition;
import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.model.simulation.entities.Passagier;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

/**
 * Diese Klasse dient zur Erzeugung eines PassagierEinsteigenInBus Ereignisses
 * User: tklein, smuell Date: 11.06.2006
 */
public class PassagierEinsteigenInBusEvent extends Event
{
   private BusSimulationModel _model;

   private int                index;

   /**
    * PassagierEinsteigenInBusEvent Konstruktor.
    * 
    * Erzeugt ein neues PassagierEinsteigenInBus Ereignis durch Aufruf des
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
   public PassagierEinsteigenInBusEvent( Model owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
      _model = (BusSimulationModel) owner;
      this.index = 0;
   }

   /**
    * Die Ereignis Routine bescheibt, was passiert wenn ein Passagier in den Bus
    * einsteigt.
    * 
    * Es wird die nächste Haltestelle gesucht, welche der Bus anfährt und die
    * zugehörige Fahrtzeit bis dieser Haltestelle berechnet. Dann wird ein neues
    * BusAnkunftAnHaltestelle Ereignis erzeugt.
    * 
    * Wenn in der Warteschlange vor der Haltestelle Busse stehen (da max
    * Kapazität der Haltestelle erreicht ist), dann wird der nächste Bus
    * entnommen und ein neues PassagierAussteige Ereignis erzeugt.
    * 
    */
   @SuppressWarnings("rawtypes")
public void eventRoutine( Entity who )
   {
      Bus b = (Bus) who;
      HaltestelleJ current = b.get_currentHaltestelle();
      ArrayList<Passagier> array = new ArrayList<Passagier>();

      EinsteigenCondition condition = new EinsteigenCondition( b.get_buslinie(), _model,
            "EinsteigenCondition", true );

      Passagier p;
      boolean bool = true;

      // Methode .first(Conditon) gibt immer das erste Objekt in der Queue
      // zurück, wenn die Condition für dieses Objekt == true
      // wenn die Conditon == false, liefert die Methode null
      while ( bool )
      {
         p = (Passagier) current.get_passagierWarteschlange().first( condition );
         if ( p != null )
         {

            // Falls Passagier nicht eingefügt werden kann, wegen maxKapazität
            // -> Rückgabewert "false"
            if ( b.get_passagierQueue().insert( p ) != false )
            {
               current.get_passagierWarteschlange().remove( p );
               System.out.println( "Passagier ist in Bus: " + b.getName() + " eingestiegen." );
               ++index;
            }
            else
            {
               // Passagier muss temporär zwischengespeichert werden
               // sonst wird die Queue nicht vollständig durchlaufen
               array.add( p );
               current.get_passagierWarteschlange().remove( p );

               String time = this._model.getRealTime( this._model.currentTime() );
               this._model.writeInLog( "Fahrgast: " + p.getName() + " wurde an HS: "
                     + current.getName() + " von Buslinie: " + b.getName() + "zur Zeit: " + time
                     + " abgewiesen" );
               System.out.println( "Fahrgast wurde abgewiesen" );

            }

         }
         else
         // es gibt kein weiteres Element mit der Condition in der Queue
         {
            bool = false;
            Iterator iter = array.iterator();
            while ( iter.hasNext() )
            { // Passagiere die aufgrund von maximaler Kapazität des Busses
               // abgewiesen wurden und temporär
               // gespeichert wurden, werden wieder in die Queue eingefügt
               current.get_passagierWarteschlange().insert( (Passagier) iter.next() );
            }
         }
      }

      // Anzeige im Panel wird aktualisiert
      SimuControlPanel panel = SimuControlPanel.getInstance();
      panel.setSimuPassagiereSteigenEin( index );

      // Plane nächstes BusAbfahrtVonHaltestelle-Ereignis
      BusAbfahrtVonHaltestelleEvent bAbfahrt = new BusAbfahrtVonHaltestelleEvent( this._model,
            "Bus fährt von HS ab", true );
      bAbfahrt.schedule( b, new SimTime( 0.0 ) );

   }
}
