package de.fh_konstanz.simubus.model.simulation.events;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Teilstrecke;
import de.fh_konstanz.simubus.model.simulation.BusLinie;
import de.fh_konstanz.simubus.model.simulation.BusSimulationModel;
import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

/**
 * Diese Klasse dient zur Erzeugung eines BusAbfahrtVonHaltestelle Ereignisses
 * User: tklein, smuell Date: 11.06.2006
 */
public class BusAbfahrtVonHaltestelleEvent extends Event
{
   private BusSimulationModel _model;

   private double             fahrGeschwindigkeit;

   /**
    * BusAbfahrtVonHaltestelleEvent Konstruktor.
    * 
    * Erzeugt ein neues BusAbfahrtVonHaltestelle Ereignis durch Aufruf des
    * Kontruktors der Superklasse
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
   public BusAbfahrtVonHaltestelleEvent( Model owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
      this._model = (BusSimulationModel) owner;
      this.fahrGeschwindigkeit = 0;

   }

   /**
    * Die Ereignis Routine bescheibt, was passiert wenn ein Bus an der
    * Haltestelle abf�hrt.
    * 
    * Es wird die n�chste Haltestelle gesucht, welche der Bus anf�hrt und die
    * zugeh�rige Fahrtzeit bis dieser Haltestelle berechnet. Dann wird ein neues
    * BusAnkunftAnHaltestelle Ereignis erzeugt.
    * 
    * Wenn in der Warteschlange vor der Haltestelle Busse stehen (da max
    * Kapazit�t der Haltestelle erreicht ist), dann wird der n�chste Bus
    * entnommen und ein neues PassagierAussteige Ereignis erzeugt.
    * 
    */
   public void eventRoutine( Entity who )
   {
      Bus b = (Bus) who;
      BusLinie linie = b.get_buslinie(); // hole zugeh�rige Buslinie zum Bus

      HaltestelleJ haltest = b.get_currentHaltestelle(); // ben�tigt um zu
      // pr�fen ob in der WS
      // vor HS Busse warten

      BusAnkunftAnHSEreignis bAnkunft = new BusAnkunftAnHSEreignis( this._model, "Bus kommt an HS an", true );
      List<HaltestelleJ> array = (List<HaltestelleJ>) linie.getHaltestellen();

      // HashMap <Integer, HaltestelleJ> linienMap = (HashMap)
      // linie.getHaltestellen(); // hole zugeh�rige HashMap
      // String moep = linienMap.keySet().toString();

      // current(d.h. n�chste) HS muss f�r jeden Bus bei Abfahrt von aktueller
      // HS gesetzt werden
      for ( int index = 0; index < array.size(); index++ )
      {
         HaltestelleJ hs = (HaltestelleJ) array.get( index ); // wir gehen der
         // Reihe nach
         // alle HS der
         // Buslinie durch

         if ( hs.equals( b.get_currentHaltestelle() ) ) // die HS stimmten
         // �berein
         {
            if ( index + 1 >= array.size() ) // wir sind an der letzten HS der
            // Buslinie angekommen
            {
               b.set_currentHaltestelle( array.get( 0 ) ); // setze wieder die
               // erste HS der
               // Buslinie

               double wiederkehr = linie.getWiederkehrzeit();

               if ( wiederkehr == 0 ) // Bus f�hrt im Kreis und bleibt IMMER im
               // System
               {
                  double meterAnzahl = this.berechnungZeitHS( haltest, b.get_currentHaltestelle(), linie );
                  double sec = meterAnzahl / ( this.fahrGeschwindigkeit * 1000 / 3600 ); // Anzahl
                  // Sekunden
                  // die
                  // der
                  // Bus
                  // bis
                  // zur
                  // n�chsten
                  // HS
                  // ben�tigt

                  // String time =
                  // this._model.getRealTime(this._model.currentTime());
                  // this._model.writeInLog(linie.getId() + " Meter bis zur
                  // n�chsten HS: " +meterAnzahl + " Sekunden bis zur n�chsten
                  // HS: " +sec + " Jetzt ist: " +time);
                  bAnkunft.schedule( b, new SimTime( sec / 60 ) ); // Plane
                  // n�chstes
                  // Ankunftsereignis
                  // in
                  // Minuten
                  // bAnkunft.schedule(b, new SimTime(10));
               }
               else
               {
                  bAnkunft.schedule( b, new SimTime( linie.getWiederkehrzeit() ) ); // Wiedereintrittszeit
                  // des
                  // Busses
                  // im
                  // System
                  // beachten
                  // System.out.println(linie.getWiederkehrzeit());
                  // String time =
                  // this._model.getRealTime(this._model.currentTime());
                  // this._model.writeInLog("Bus f�hrt ab: " +time);
               }
            }
            else
            // wir setzen die n�chste HS mittels der Liste
            {
               b.set_currentHaltestelle( array.get( index + 1 ) );

               // Wir berechnen unsere gesamten Teilstrecken zwischen der
               // aktuellen HS und der n�chsten in Meter
               double meterAnzahl = this.berechnungZeitHS( haltest, b.get_currentHaltestelle(), linie );
               double sec = meterAnzahl / ( this.fahrGeschwindigkeit * 1000 / 3600 ); // Anzahl
               // Sekunden
               // die
               // der
               // Bus
               // bis
               // zur
               // n�chsten
               // HS
               // ben�tigt

               // String time =
               // this._model.getRealTime(this._model.currentTime());
               // this._model.writeInLog(linie.getId() +" Meter bis zur n�chsten
               // HS: " +meterAnzahl + " Sekunden bis zur n�chsten HS: " +sec +
               // " Jetzt ist: " +time);

               bAnkunft.schedule( b, new SimTime( sec / 60 ) ); // Plane
               // n�chstes
               // Ankunftsereignis
               // in Minuten
               // bAnkunft.schedule(b, new SimTime(10));
            }
            break;
         }
      }

      // Update der Simulationsanzeige im entsprechendem Panel
      SimuControlPanel panel = SimuControlPanel.getInstance();
      panel.updateVisualisierung( b.get_passagierQueue().length(), haltest.get_passagierWarteschlange()
            .length() );

      // In WS vor HS befindet sich mindest 1 Bus
      if ( !haltest.get_busWarteschlange().isEmpty() )
      {
         // der erste Bus aus WS wird geholt
         Bus bus = (Bus) haltest.get_busWarteschlange().first();
         PassagierAussteigenAusBusEvent pAussteigen = new PassagierAussteigenAusBusEvent( this._model,
               "Passagiere steigen aus", true );
         pAussteigen.schedule( bus, new SimTime( 0.0 ) ); // Passagiere
         // steigen sofort
         // aus

      }
      else
      {
         // anzahl Busse in HS wird reduziert
         haltest.removeBus();
      }

   }

   /**
    * Berechnet die Abstand in Meter, die der Bus bis zu seiner n�chsten
    * Haltestelle ben�tigt. Dazu sucht er sich die Teilstrecken bis zur n�chsten
    * Haltestelle und addiert die Distanzen.
    * 
    * @param s
    *           Die aktuelle Haltestelle, an der der Bus steht
    * @param z
    *           Die n�chste Haltestelle, die der Bus anfahren muss
    * @param line
    *           Die Linie, auf der der Bus f�hrt
    * @return Distanz bis zur n�chsten Haltestelle in Meter
    */
   @SuppressWarnings("rawtypes")
public double berechnungZeitHS( HaltestelleJ s, HaltestelleJ z, BusLinie line )
   {
      // HS-Koordinaten
      Point2D start = s.getPoint();
      Point2D ziel = z.getPoint();

      List<Teilstrecke> array = line.getTeilstrecken(); // alle Teilstrecken
      // auf einer Linie

      Iterator iter = array.iterator();
      double distanz = 0;
      while ( iter.hasNext() )
      {
         Teilstrecke t = (Teilstrecke) iter.next();
         Point2D startT = t.getStart();
         int x = (int) startT.getX(); // x-Koordinate der Teilstrecke
         int y = (int) startT.getY(); // y- " " "

         // Koordinatenvergleich zwischen Startpunkt der Teilstrecke und unserer
         // StartHS
         if ( ( x + 6 ) == start.getX() && ( ( y - 7 ) == start.getY() ) )
         {
            distanz += t.getLaenge();

            // Ermittle die letzte Teilstrecke vor der n�chsten HS
            while ( iter.hasNext() )
            {
               Teilstrecke tZiel = (Teilstrecke) iter.next();
               Point2D endeT = tZiel.getStart();
               int xEnde = (int) endeT.getX();
               int yEnde = (int) endeT.getY();

               // wenn die Teilstrecke != die Koordinaten der n�chsten HS hat,
               // erh�he Pixeldistanz
               // Koordinaten der Haltestelle weicht von der Koordinaten der
               // Teilstrecke im y- Wert ab.
               // Deshalb Korrektur des y-Wertes!
               if ( !( ( xEnde + 6 ) == ziel.getX() && ( ( yEnde - 7 ) == ziel.getY() ) ) )
               {
                  distanz += t.getLaenge();
               }
               else
               // Teilstrecke beginnt bei den gleichen Koordinaten wie unsere
               // n�chste HS -> fertig
               {
                  break;

               }
            }

            break;
         }
      }

      SimuKonfiguration simuConfig = SimuKonfiguration.getInstance();
      this.fahrGeschwindigkeit = simuConfig.getBusGeschwindigkeitInKmH(); // hole
      // Fahrgeschwindigkeit
      // Bsp.
      // 50km/h

      double groesseJeFeldPixel = simuConfig.getFeldElementGroesse(); // L�nge
      // bzw.
      // Breite
      // eines
      // "K�stchens"
      // in
      // Pixel
      double groesseRasterMeter = simuConfig.getFeldElementGroesseInM(); // L�nge
      // bzw.
      // Breite
      // eines
      // K�stchens
      // in
      // Meter

      double meterZuNaechsterHS = distanz / groesseJeFeldPixel * groesseRasterMeter;

      return meterZuNaechsterHS;
   }

}
