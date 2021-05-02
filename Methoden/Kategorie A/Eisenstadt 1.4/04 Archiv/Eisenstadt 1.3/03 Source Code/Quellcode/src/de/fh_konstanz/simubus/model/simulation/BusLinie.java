package de.fh_konstanz.simubus.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.fh_konstanz.simubus.model.Teilstrecke;
import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;

/**
 * Die Klasse kapselt alle Informatione, welche zu einer
 * Buslinie gehoeren
 * @author Ingo Kroh
 * @version 1.0 (09.06.2006)
 */

public class BusLinie
{
   // private Map<Integer, HaltestelleJ> haltestellen = null;
   private List<HaltestelleJ> arrayHaltestellen;

   private String             id = null;

   private int                maxKapBus;

   private double             wiederkehrzeit;

   private List<Teilstrecke>  teilstrecken;

   public BusLinie()
   {
   }

   /**
    * 
    * @param aId Eindeutige id 
    * @param kap Maximale Kapazität des Busses für Passagiere
    * @param wiederkehr Zeit, die vergeht, bis ein Bus nach Verlassen des Systems wieder ins System eintritt
    * @param teilstrecke Liste mit allen Teilstrecken
    */
   public BusLinie( String aId, int kap, double wiederkehr, List<Teilstrecke> teilstrecke )
   {
      id = aId;
      this.arrayHaltestellen = new ArrayList<HaltestelleJ>();
      this.maxKapBus = kap;
      this.wiederkehrzeit = wiederkehr;
      this.teilstrecken = teilstrecke;
      // haltestellen = new HashMap<Integer, HaltestelleJ>();
   }

   /**
    * Setzt eine Haltestelle, damit diese 
    * von der Buslinie angefahren wird
    * 
    * @param aHaltestelle Ein Haltestelle
    */
   public void setHaltestelle( HaltestelleJ aHaltestelle )
   {
      this.arrayHaltestellen.add( aHaltestelle );
      // haltestellen.put( this.haltestellen.size() +1 , aHaltestelle );

   }

   /**
    * @return Liefert alle Haltellen, welche die Buslinie anfaehrt
    */
   public List<HaltestelleJ> getHaltestellen()
   {
      return this.arrayHaltestellen;
   }

   /*
    * public Map<Integer, HaltestelleJ> getHaltestellen() { return
    * haltestellen; }
    */
   /**
    * @return Die eindeutige Haltestellen id
    */
   public String getId()
   {
      return id;
   }

   /**
    * 
    * @return Liefert die max. Kapazität an Passagieren,
    * welche der Bus auf dieser Linie befoerdern kann
    */
   public int getMaxKapPassagiere()
   {
      return this.maxKapBus;
   }

   /**
    * Zeit, die vergeht, bis ein Bus 
    * nach Verlassen des Systems wieder ins System eintritt 
    * @return Die Wiederkehrzeit
    */
   public double getWiederkehrzeit()
   {
      return this.wiederkehrzeit;
   }

   /**
    * 
    * @return alle Teilstrecken
    */
   public List<Teilstrecke> getTeilstrecken()
   {
      return this.teilstrecken;
   }
}
