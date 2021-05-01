package de.fh_konstanz.simubus.model.simulation.entities;

import java.awt.geom.Point2D;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;

/**
 * Die Klasse kapselt alle Daten, die zu einer 
 * Haltestelle gehoeren. Da DesmoJ eine Vererbung von Entity 
 * erfordert, wurde die Klasse HaltestelleJ erstellt und im 
 * Code ein Mapping aller Haltestellen Objekte auf HaltestellenJ
 * Objekte vorgenommen. 
 * 
 * @author Tobias Klein
 *
 */
public class HaltestelleJ extends Entity
{
   protected Queue _busWarteschlange;

   protected Queue _passagierWarteschlange;

   private Point2D koordinaten;

   private int     _anzahlbusseinhs;

   private int     maxKap;

   /**
    * 
    * @param owner Das Model, zu dem das Ereignis gehört
    * @param name Der Name des Models
    * @param showInTrace Flag welches angibt, ob dieses Model Ausgaben in das Trace File
    *           schreiben soll
    * @param koordinaten Koordinaten der Haltestelle
    * @param kap Max. Kapazitaet an Bussen in der Haltebucht
    */
   public HaltestelleJ( Model owner, String name, boolean showInTrace, Point2D koordinaten, int kap )
   {
      super( owner, name, showInTrace );
      // Initialisieren der Queues
      // TODO Warteschlange fuer Passagiere ist unbegrenzt.
      _passagierWarteschlange = new Queue( owner, "Haltestelle: " + name + "PassagierQueue", true, true );
      _busWarteschlange = new Queue( owner, "´Haltestelle " + name + " BusQueue", true, true );
      this._anzahlbusseinhs = 0;
      this.maxKap = kap;
      this.koordinaten = koordinaten;

   }

   /**
    * 
    * @return Anzahl Busse in der Haltebucht
    */
   public int get_anzahlbusseinhs()
   {
      return _anzahlbusseinhs;
   }

   /**
    * 
    * @return max. Kapazitaet fuer Busse in der Haltebucht
    */
   public int getMaxKap()
   {
      return this.maxKap;
   }

   /**
    * Erhoeht die Anzahl der Busse in der Haltebucht um 1 
    */
   public void addBus()
   {
      _anzahlbusseinhs += 1;
   }

   /**
    * Verringert die Anzahl der Busse in der Haltebucht um 1 
    *
    */
   public void removeBus()
   {
      _anzahlbusseinhs -= 1;
   }


   /**
    * 
    * @return Warteschlange mit wartenden Bussen
    * 
    */
   public Queue get_busWarteschlange()
   {
      return _busWarteschlange;
   }

   /**
    * 
    * @return Warteschlange mit Passagieren an der Haltestelle
    */
   public Queue get_passagierWarteschlange()
   {
      return _passagierWarteschlange;
   }

  /**
   * 
   * @return Die Koordinaten der Haltestelle
   */
   public Point2D getPoint()
   {
      return this.koordinaten;
   }

}
