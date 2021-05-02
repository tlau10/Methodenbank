package de.fh_konstanz.simubus.model.simulation.events;

import java.util.ArrayList;

import de.fh_konstanz.simubus.model.simulation.BusLinie;
import de.fh_konstanz.simubus.model.simulation.BusSimulationModel;
import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.model.simulation.entities.Passagier;
import de.fh_konstanz.simubus.util.Logger;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

/**
 * Diese Klasse dient zur Erzeugung von Passagieren anhand einer
 * Exponentialverteilung User: tklein, smuell, pstader Date: 11.06.2006
 */
public class PassagierGeneratorEvent extends ExternalEvent
{

   private BusSimulationModel  modelRef;

   private ArrayList<BusLinie> tempLinie;

   private HaltestelleJ        h;

   /**
    * PassagierGeneratorEvent Konstruktor.
    * 
    * Erzeugt ein neues PassagierGenerator Ereignisses durch Aufruf des
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
   public PassagierGeneratorEvent( Model owner, String name, boolean showInTrace )
   {
      super( owner, name, showInTrace );
      tempLinie = new ArrayList<BusLinie>();

   }

   /**
    * Die Ereignis Routine bescheibt, was passiert wenn ein Passagierer erzeugt
    * wird.
    * 
    * F�r den Passagier, welcher erzeugt wird, wird sowohl Start- als auch
    * Zielhaltestelle erzeugt und eine zugeh�rige BusLinie ausgew�hlt
    * 
    * Danach wird das Passagierobjekt angelegt und PassagierAnkunfts Ereignis
    * geplant mittels einer Verteilung Zum Schluss wird PassagierGenerator
    * Ereignis anhand einer Verteilung geplant.
    * 
    */
   public void eventRoutine()
   {
      BusSimulationModel model = (BusSimulationModel) getModel();
      this.modelRef = model;

      // Generiere Start + Ziel HS
      HaltestelleJ start = this.createStartHS();
      HaltestelleJ ziel = this.createZielHS( start );

      // Buslinien welche beide HS anfahren sind in tempLinie gespeichert
      // (Methode createZielHS)
      BusLinie b = this.getBuslinie( start, ziel );

      tempLinie.clear();
      Logger.getInstance().log( "StartHS: " + start.getName() + " ZielHS: " + ziel.getName() + " Buslinie: "
            + b.getId() );
      // Neuen Passagier erzeugen
      Passagier p = new Passagier( start, ziel, b, this.modelRef, "Passagier", true );

      // Neues Ankunftsevent f�r diesen Passagier erzeugen
      PassagierAnkunftAnHaltestelleEvent pankunft = new PassagierAnkunftAnHaltestelleEvent( this.modelRef,
            "Passagierankunft an HS", true );

      // Ankunft findet sofort statt
      pankunft.schedule( p, new SimTime( 0.0 ) );
      // Der n�chste Passagier wird dann abh�ngig von der Verteilung erzeugt
      schedule( new SimTime( this.modelRef.get_passagierAnkunftszeit() ) );
   }

   /**
    * Sucht sich zuf�llig aus allen Haltestellen im System eine Starthaltestelle
    * mittels einer Verteilung
    * 
    * @return Starthaltestelle
    */
   public HaltestelleJ createStartHS()
   {
      // Zuweisen der StartHS durch Verteilung
      int anzahlHS = this.modelRef._hstellen.size(); // haltestellen.size(); //
                                                      // abh�ngig von AnzahlHS
                                                      // im System
      // Logger.getInstance().log("Anzahl HS im System: " +anzahlHS);
      double erzeugteZufallszahl = this.modelRef.getZufallszahlPassagiereStartHS();// Zahl
                                                                                    // zwischen
                                                                                    // 0
                                                                                    // und
                                                                                    // 1
      Logger.getInstance().log( "Zufahllsazhl: " + erzeugteZufallszahl );

      double interval = 1 / (double) anzahlHS; // Ermittlung des Intervals z.
                                                // b. 10 HS -> Intervalgr��e 0,1
      // Logger.getInstance().log("zugeh�riges Interval: " +interval);
      //int zugehoerigeStartHS = 0; // Nr der StartHS
      double iVal = interval; // Intervall�nge muss gespeichert werden
      // Haltestelle h = new Haltestelle(this.modelRef, "StartHS", false);

      for ( int i = 0; i < anzahlHS; i++ )
      {
         if ( erzeugteZufallszahl < interval )
         {
            this.h = (HaltestelleJ) this.modelRef._hstellen.get( i );
            //zugehoerigeStartHS = i; // setze Nr der StartHS
            // Logger.getInstance().log("StartHS: " +zugehoerigeStartHS);
            break;

         }
         else
         {
            interval += iVal; // erzeugteZufallszahl liegt nicht im Interval
                              // also n�chstes Interval pr�fen
            // Logger.getInstance().log("Interval in Schleife: " +interval);
         }
      }
      return this.h;
   }

   /**
    * Erzeugt zuf�llig eine Zielhaltestelle, abh�ngig von der Starthaltestelle
    * und der Buslinie Zielhaltestelle + Starthaltestelle m�ssen von der
    * Buslinie angefahren werden
    * 
    * @param startHS Starthaltestelle
    * @return Zielhaltestelle
    */
   public HaltestelleJ createZielHS( HaltestelleJ startHS )
   {

      ArrayList<BusLinie> linie = this.modelRef.buslinien;

      // hole alle potenziellen Buslinien welche die StartHS anfahren (Start +
      // Ziel m�ssen auf einer Buslinie liegen)
      // TO DO: Umsteigen f�r Passagiere erm�glichen
      for ( BusLinie b : linie )
      {
         for ( HaltestelleJ h : b.getHaltestellen() )
         {
            if ( h.equals( startHS ) ) // Start HS wird von Buslinie bedient
            {
               tempLinie.add( b ); // speichere in ArrayList
               // break; // Linie hinzugef�gt -> breche innere Schleife ab
            }
         }
      }

      // alle Haltestellen der gerade ermittelten Buslinien werden in tempHS
      // gespeichert.
      // Es d�rfen keine doppelt gespeichert werden
      ArrayList<HaltestelleJ> tempHS = new ArrayList<HaltestelleJ>();

      for ( BusLinie b : tempLinie )
      {
         for ( HaltestelleJ h : b.getHaltestellen() )
         {
            if ( !tempHS.contains( h ) && !h.equals( startHS ) ) // keine
                                                                  // doppelten
                                                                  // HS und
                                                                  // nicht die
                                                                  // StartHS
            {
               tempHS.add( h );
            }
         }
      }

      // alle erreichbaren HS wurden nun ermittelt
      // jede HS hat die gleiche "Chance" Ziel HS zu werden -> durch Zufallszahl

      // StartHS darf nicht gleich ZielHS sein
      int anzahlHS = tempHS.size(); // Anzahl potenzielle Ziel HS

      // erzeuge wiederum eine Zufallszahl
      double erzeugteZufallszahl = modelRef.getZufallszahlPassagiereStartHS();
      double interval = 1 / (double) anzahlHS; // Ermittlung des Intervals
      double inter = interval; // Intervalgr�sse muss festgehalten werden um
                                 // hochz�hlen zu k�nnen

      // Haltestelle h = new Haltestelle(this.modelRef, "ZielHS", false );

      for ( int i = 0; i < anzahlHS; i++ )
      {
         // Zufallszahl wird einem Interval zugeordnet
         if ( erzeugteZufallszahl < interval ) // i repr�sentiert den Index der
                                                // HS in ArrayList
         {
            this.h = tempHS.get( i ); // hole HS aus ArrayLst am entsprechenden
                                       // Index
            // Start != Ziel HS
            if ( !this.h.equals( startHS ) )
            {
               return this.h;
            }
            else
            // Start und Ziel HS sind gleich
            {
               if ( i + 1 < anzahlHS ) // Check ob nach Index i noch eine HS in
                                       // ArrayList vorhanden ist
               {
                  this.h = tempHS.get( i + 1 );
                  return this.h;
               }
               else
               // i ist an letzter Stelle in ArrayList
               {
                  this.h = tempHS.get( i - 1 ); // nimm vorherige
                  return this.h;
               }
            }
         }
         else
         {
            interval += inter; // Erh�hen auf n�chsten Intervalschritt
         }
      }

      return this.h;

   }

   /**
    * Gibt die ERSTE Buslinie zur�ck welche sowohl Start als auch Ziel HS
    * anf�hrt
    * 
    * @param start
    *           Starthaltestelle
    * @param ziel
    *           Zielhaltestelle
    * @return Buslinie, die Start und Zielhaltestelle OHNE umsteigen anf�hrt
    */
   public BusLinie getBuslinie( HaltestelleJ start, HaltestelleJ ziel )
   {
      for ( BusLinie b : tempLinie )
      {
         if ( b.getHaltestellen().contains( start ) && b.getHaltestellen().contains( ziel ) )
         {
            return b;
         }
      }

      return new BusLinie();
   }

}
