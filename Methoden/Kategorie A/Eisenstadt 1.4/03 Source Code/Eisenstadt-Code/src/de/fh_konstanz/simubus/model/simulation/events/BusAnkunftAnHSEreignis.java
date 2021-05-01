package de.fh_konstanz.simubus.model.simulation.events;

import javax.swing.SwingUtilities;

import de.fh_konstanz.simubus.model.simulation.BusSimulationModel;
import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.SimTime;

/**
 * Diese Klasse dient zur Erzeugung eines BusAnkunftAnHSEreignis Ereignisses
 * User: tklein, smuell, pstader Date: 11.06.2006
 */
public class BusAnkunftAnHSEreignis extends Event
{

   private BusSimulationModel _model;

   private HaltestelleJ       current;

   /**
    * BusAnkunftAnHSEreignis Konstruktor.
    * 
    * Erzeugt ein neues BusAnkunftAnHSEreignis Ereignis durch Aufruf des
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
   public BusAnkunftAnHSEreignis( BusSimulationModel owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
      this._model = (BusSimulationModel) owner;
   }

   /**
    * Die Ereignis Routine bescheibt, was passiert wenn ein Bus an einer
    * Haltestelle ankommt.
    * 
    * Es wird geprüft, ob die maxKapazität der Haltestelle für Busse erreicht.
    * Wenn ja, dann wird der Bus in die zugehörige Warteschlange VOR der
    * Haltestelle eingefügt. Wenn die maxKapazität nicht erreicht ist, dann wird
    * die Kapazität erhöht und ein neues PassagierAussteigenAusBus Ereignis
    * geplant.
    */
   public void eventRoutine( Entity who )
   {
      final Bus b = (Bus) who;

      //String time = ( (BusSimulationModel) this._model ).getRealTime( this._model.currentTime() );
      // this._model.writeInLog(b.get_buslinie().getId() +" Ankunft Bus: "
      // +time);

      // Wir holen uns die aktuelle Haltestelle
      current = b.get_currentHaltestelle();
      b.setPositionX( (int) current.getPoint().getX() );
      b.setPositionY( (int) current.getPoint().getY() );
      // Jede Haltestelle hat genau Platz für zwei Busse
      if ( current.get_anzahlbusseinhs() < current.getMaxKap() )
      {
         // Wir haben Platz in der Haltestelle
         current.addBus();

         final SimuControlPanel p = SimuControlPanel.getInstance();
         SwingUtilities.invokeLater( new Runnable()
         {

            public void run()
            {
               p.setSimu( b.getName(), current.getName(), b.get_passagierQueue().length(), current
                     .get_passagierWarteschlange().length() );

            }
         } );

         // Jetzt steigen Passagiere aus -> PassagierAussteigenAusBusEvent
         // planen
         PassagierAussteigenAusBusEvent pAussteigen = new PassagierAussteigenAusBusEvent( this._model,
               "Passagiere steigen aus dem Bus", true );
         pAussteigen.schedule( b, new SimTime( 0.0 ) );
      }
      else
      // Bus muss vor Haltebucht warten -> Einfügen in Queue
      {
         current.get_busWarteschlange().insert( b );

         /*
         String time2 = ( (BusSimulationModel) this._model ).getRealTime( this._model.currentTime() );
         
         boolean wert = ( (BusSimulationModel) this._model ).writeInLog( "Bus: " + b.getName()
               + " wurde an HS: " + current.getName() + "zur Zeit: " + time2 + " abgewiesen" );
         */
         Logger.getInstance().log( "Bus muss WARTEN !!!!!!!!!!!!!!!!" );
      }

   }
}
