package de.fh_konstanz.simubus.model.simulation.entities;

import de.fh_konstanz.simubus.model.simulation.BusLinie;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;

/**
 * Die Klasse kapselt alle Daten, die zu einem Passagier gehoeren
 * 
 * @author Tobias Klein
 *
 */
public class Passagier extends Entity
{

   private HaltestelleJ _start;

   private HaltestelleJ _ziel;

   private BusLinie     _buslinie;

   
   /*
    * public Passagier(BusLinie buslinie, Model owner, String name, boolean
    * showInTrace) { super(owner, name, showInTrace); _buslinie = buslinie; }
    */
  
   /**
    * 
    * @param start Starthaltestelle
    * @param ziel Zielhaltestelle
    * @param b Zugehoerige Buslinie
    * @param owner Das Model, zu dem das Ereignis gehört
    * @param name Der Name des Models
    * @param showInTrace Flag welches angibt, ob dieses Model Ausgaben in das Trace File
    *           schreiben soll
    */
   public Passagier( HaltestelleJ start, HaltestelleJ ziel, BusLinie b, Model owner, String name,
         boolean showInTrace )
   {
      super( owner, name, showInTrace );
      _start = start;
      _ziel = ziel;
      this._buslinie = b;
   }

   /**
    * 
    * @return Die Starthaltestelle des Passagiers
    */
   public HaltestelleJ get_start()
   {
      return _start;
   }


   /*
    * public Haltestelle get_naechste_Haltestelle(Haltestelle
    * aktuelleHaltestelle){ _buslinie.getHaltestellen(); }
    */

   /**
    * Setz die Starthaltestelle des Passagiers
    * 
    * @param _start Starthaltestelle
    */
   public void set_start( HaltestelleJ _start )
   {
      this._start = _start;
   }

   /**
    * 
    * @return Die Zielhaltestelle des Passagiers
    */
   public HaltestelleJ get_ziel()
   {
      return _ziel;
   }

   /**
    * Setzt die Zielhaltestelle des Passagiers
    * 
    * @param _ziel Zielhaltestelle
    */
   public void set_ziel( HaltestelleJ _ziel )
   {
      this._ziel = _ziel;
   }

   /**
    * 
    * @return Die Buslinie, mit welcher der Passagier faehrt
    */
   public BusLinie get_buslinie()
   {
      return _buslinie;
   }

   /**
    * Setzt die Buslinie, mit der er fahren muss
    * @param _buslinie Buslinie
    */
   public void set_buslinie( BusLinie _buslinie )
   {
      this._buslinie = _buslinie;
   }

}
