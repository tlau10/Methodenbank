package de.fh_konstanz.simubus.model.simulation;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.model.simulation.events.BusAnkunftAnHSEreignis;
import de.fh_konstanz.simubus.model.simulation.events.PassagierGeneratorEvent;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.view.SimuPanel;
import desmoj.core.dist.IntDistUniform;
import desmoj.core.dist.RealDistExponential;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

/**
 * Das ist die Model Klasse. Sie ist die Hauptklasse einer Ereignisorientierten
 * Bussimulation Sie wird von der Klasse SimuButtonListener angelegt und mit dem
 * Experiment verknüpft.
 */

public class BusSimulationModel extends Model
{
   private RealDistExponential    _passagierAnkunftszeit;

   private IntDistUniform         _passagierStartHaltestelle;

   private IntDistUniform         _passagierZielHaltestelle;

   private File                   outputFile;

   private FileWriter             writer;

   private Experiment             experiment;

   //private Model                  model;

   private Strassennetz           strassennetz;

   public ArrayList<BusLinie>     buslinien;

   public ArrayList<HaltestelleJ> _hstellen;

   private ArrayList<Bus>         busse;

   public BusSimulationModel( Model owner, String name, boolean showInReport, boolean showIntrace,
         Experiment ex )
   {
      super( owner, name, showInReport, showIntrace );
      _hstellen = new ArrayList<HaltestelleJ>();
      buslinien = new ArrayList<BusLinie>();
      //this.model = owner;
      this.experiment = ex;
      this.busse = new ArrayList<Bus>();
      this.strassennetz = Strassennetz.getInstance();

      File aFile = new File( "Reports" );
      String p = aFile.getAbsolutePath();

      if ( aFile.exists() )
      {

      }
      else
      {
         aFile.mkdir();
      }

      outputFile = new File( p + "\\warnungen.txt" );

      try
      {
         writer = new FileWriter( this.outputFile );
      }
      catch ( Exception e )
      {
    	  Logger.getInstance().log( e.getMessage() );
      }
   }

   /*
    * @param args
    */
   /*
    * public static void main(String[] args) { BusSimulationModel model = new
    * BusSimulationModel(null, "BusGeschichten", true, true); // 2.Parameter:
    * Startzeit, 3.Parameter: Einheit der SimTime -> 2 = min Experiment exp =
    * new Experiment("Bus Simulations Experiment", "01.01.2006 06:00", 2);
    * //Experiment exp = new Experiment("Bus Simulations Experiment");
    * model.experiment = exp; // Objekt Experiment kann SimTime in RealTime
    * Objekte umwandeln
    * 
    * model.connectToExperiment(exp);
    * 
    * exp.setShowProgressBar(true);
    * 
    * exp.tracePeriod(new SimTime(0.0), new SimTime(360.0)); exp.debugPeriod(new
    * SimTime(0.0), new SimTime(360.0)); //exp.setDelayInMillis(1000); //
    * Wartezeit in ms zwischen EventScheduling -> 1s
    * 
    * exp.setOutputTimeFormat("hh:mm:ss"); // Ausgabeformat für Realtime in Form
    * von SimpleDateFormat exp.stop(new SimTime(360)); // 360 min = 6 Std. ab
    * Startzeitpunkt = Endzeitpunkt
    * 
    * exp.start();
    * 
    * exp.report();
    * 
    * try { model.writer.close(); } catch(Exception e) {
    * Logger.getInstance().log(e.getMessage()); }
    * 
    * exp.finish(); }
    */

   @Override
   public String description()
   {
      return "Diese Simulation beschreibt eine Bussimulation";
   }

   /**
    * doInitalSchedules
    * 
    * erzeugt HaltestelleJ Objekte aus den Haltestelle Objekte der Optimierung
    * benötigt aufgrund des Konstruktors der Entity Klasse HaltestelleJ analoges
    * gilt für die BusLinie
    */
   @Override
   public void doInitialSchedules()
   {

      // über Instanz Strassennetz bekommen wir alle definiert Linien
      // über Linien bekommen wir alle Haltestellen
      ArrayList<Linie> linien = strassennetz.getArrayBuslinie();

      // temp Array Haltestelle
      ArrayList<Haltestelle> array = new ArrayList<Haltestelle>();

      //int index = 0;
      for ( Linie l : linien )
      {
         // erzeuge Objekt BusLinie
         BusLinie linie = new BusLinie( l.getId(), l.getMaxKapBusPassagiere(), l.getFrequenz(), l
               .getTeilstrecken() );

         // iteriere sämtliche Haltestellen einer Linie
         for ( Haltestelle h : l.getHaltestellen() )
         {
            // damit eine Haltestelle nicht mehrfach angelegt wird
            if ( !array.contains( h ) )
            {

               array.add( h );

               // erzeuge HaltestelleJ Objekt
               HaltestelleJ hj = new HaltestelleJ( this, "Haltestelle" + h.getID(), true, new Point2D.Double(
                     h.getPixelXCoordinate(), h.getPixelYCoordinate() ), h.getKapazitaet() );
               _hstellen.add( hj );
               linie.setHaltestelle( hj ); // füge HaltestelleJ der BusLinie
                                             // hinzu

            }
         }
         buslinien.add( linie );
         array.clear();

      }

      // Start des Passagiergenerators
      PassagierGeneratorEvent pg = new PassagierGeneratorEvent( this, "PassagierGenerator wird gestartet",
            true );
      pg.schedule( new SimTime( 0.0 ) );

      // int index = 1;
      int i = 0;
      // Die erste Haltestelle jeder BusLinie wird gesetzt und pro BusLinie ein
      // Busobjekt erzeugt
      // und BusAnkunftAnHSEreignis geplant
      for ( BusLinie b : buslinien )
      {

         // Erste Haltestelle wird für Simulationsstart gesetzt
         BusAnkunftAnHSEreignis bankunft = new BusAnkunftAnHSEreignis( this, "1", true );
         List<HaltestelleJ> arrayHaltestellen = b.getHaltestellen();
         HaltestelleJ ersteHaltestelle = arrayHaltestellen.get( 0 );

         // Map <Integer, HaltestelleJ> h = (Map)b.getHaltestellen();
         // HaltestelleJ haltestelle = h.get(1);
         Bus bus = new Bus( i++, ersteHaltestelle, b, this, "Bus" + b.getId(), true, b.getMaxKapPassagiere() );
         // bankunft.schedule(new Bus(ersteHaltestelle, b, this, "Bus"
         // +b.getId(), true, b.getMaxKapPassagiere()),
         // new SimTime(5));
         bankunft.schedule( bus, new SimTime( 5 ) );
         busse.add( bus );
      }
      SimuPanel.getInstance().startAnimation( this );
   }

   public ArrayList<Bus> getBusse()
   {
      return busse;
   }

   public int get_passagierStartHaltestelle()
   {
      return (int) _passagierStartHaltestelle.sample();
   }

   public int get_passagierZielHaltestelle()
   {
      return (int) _passagierZielHaltestelle.sample();
   }

   @Override
   public void init()
   {
      /**
       * Zufallstrom für Ankünfte von Kunden. Alle 0,5 Minuten im Schnitt soll
       * einer kommen
       */
      _passagierAnkunftszeit = new RealDistExponential( this, "PassagierAnkunftstrom", 0.2, true, false );
      _passagierAnkunftszeit.setNonNegative( true );

      /**
       * Zufallszahlenstrom zur Erzeugung der StartHaltestelle eines Passagiers.
       * Einsteigen geht nur bei Haltestelle 1, 2 oder 3.
       */
      // _passagierStartHaltestelle = new IntDistUniform(this,
      // "PassagierStartHaltestelle", 1, 3, true, false );
      /**
       * Zufallszahlenstrom zur Erzeugung der Zielhaltestelle eines Passagiers.
       * Ziele können nur Haltestellen 3, 4 oder 5 sein.
       */
      // _passagierZielHaltestelle = new IntDistUniform(this,
      // "PassagierZielHaltestelle", 3, 5, true, false);
   }

   public double get_passagierAnkunftszeit()
   {
      return _passagierAnkunftszeit.sample();
   }

   // wird sowohl für zufällige Ermittlung von Start sowie Ziel HS genutzt
   public double getZufallszahlPassagiereStartHS()
   {
      return Math.random();
   }

   public boolean writeInLog( String text )
   {
      try
      {

         this.writer.append( text + "\n" );

         return true;

      }
      catch ( Exception e )
      {
    	  Logger.getInstance().log( e.getMessage() );
      }

      return false;
   }

   public String getRealTime( SimTime s )
   {
      return this.experiment.toTrueTime( s );
   }

   public void closeLog()
   {
      try
      {
         this.writer.close();
      }
      catch ( Exception e )
      {
    	  Logger.getInstance().log( e.getMessage() );
      }
   }

}
