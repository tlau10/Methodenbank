package de.fh_konstanz.simubus.model.simulation.entities;

import de.fh_konstanz.simubus.model.simulation.BusLinie;
import de.fh_konstanz.simubus.view.SimuPanel;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;

/**
 * Die Klasse kapselt alle Daten einer Entity Bus
 * User: Phil Date: 11.06.2006
 */
public class Bus extends Entity
{

   private BusLinie     _buslinie;

   private HaltestelleJ _currentHaltestelle = null;

   protected Queue      _passagierQueue;

   private int          maxKap;

   private int          positionX;

   private int          positionY;

   private int          id;

   /**
    * 
    * @return Eindeutige id des Busses
    */
   public int getId()
   {
      return id;
   }

   /**
    * Setzt die id des Busses
    * @param id Eindeutige id
    */
   public void setId( int id )
   {
      this.id = id;
   }

   /**
    * @return Die X Position des Busses
    */
   public int getPositionX()
   {
      return positionX;
   }

   /**
    * Setzt die X Position des Busses
    * @param positionX X Wert der Position
    */
   public void setPositionX( int positionX )
   {
      this.positionX = positionX;
      // SimuPanel.getInstance().updateView(this);
   }

   /**
    * @return Die Y Position des Busses
    */
   public int getPositionY()
   {
      return positionY;
   }

   /**
    * Setzt die Y Position des Busses
    * @param positionY Y Wert der Position
    */
   public void setPositionY( int positionY )
   {
      this.positionY = positionY;
      SimuPanel.getInstance().updateView( this );
   }

   /**
    * Alle Passagiere im Bus werden in einer
    * Warteschlange "im Bus" abgebildet
    * 
    * @return Die Passagier-Warteschlange im Bus 
    */
   public Queue get_passagierQueue()
   {
      return _passagierQueue;
   }

   /**
    * 
    * @param _passagierQueue Die Passagier-Warteschlange
    */
   public void set_passagierQueue( Queue _passagierQueue )
   {
      this._passagierQueue = _passagierQueue;
   }

   /**
    * 
    * @param i keine Ahnung TODO
    * @param haltestelle Starthaltestelle
    * @param buslinie Buslinie, auf der der Bus faehrt 
    * @param owner Das Model, zu dem das Ereignis gehört
    * @param name  Der Name des Models
    * @param showInTrace Flag welches angibt, ob dieses Model Ausgaben in das Trace File
    *           schreiben soll
    * @param maxKap Maxmimale Kapazitaet des Busses fuer Passagiere
    */
   public Bus( int i, HaltestelleJ haltestelle, BusLinie buslinie, Model owner, String name,
         boolean showInTrace, int maxKap )
   {
      super( owner, name, showInTrace );
      _buslinie = buslinie;
      this._currentHaltestelle = haltestelle;
      this.maxKap = maxKap;
      // _passagierQueue = new Queue(owner, "Passagierqueue in Bus " + name,
      // true, true);
      // Parameterwerte: 0 -> FIFO Prinzip, maxKap wird in LinienDetails gesetzt
      _passagierQueue = new Queue( owner, "Passagierqueue in Bus", 0, this.maxKap, true, true );

   }

   /**
    * @return die aktuelle Haltestelle, an welcher der Bus steht
    */
   public HaltestelleJ get_currentHaltestelle()
   {
      return _currentHaltestelle;
   }

   /** 
    * Setzt die aktuelle Haltestelle
    * 
    * @param _currentHaltestelle die aktuelle Haltestelle
    */
   public void set_currentHaltestelle( HaltestelleJ _currentHaltestelle )
   {
      this._currentHaltestelle = _currentHaltestelle;
   }

   /**
    * 
    * @return Buslinie, auf welcher der Bus faehrt
    */
   public BusLinie get_buslinie()
   {
      return _buslinie;
   }

   /**
    * Setzt die Buslinie, auf welcher der Bus fahren soll
    * @param _buslinie Buslinie
    */
   public void set_buslinie( BusLinie _buslinie )
   {
      this._buslinie = _buslinie;
   }
   
   /**
    * getMaxKap
    * @return
    */
   public int getMaxKap(){
	   return this.maxKap;
   }
   

}
