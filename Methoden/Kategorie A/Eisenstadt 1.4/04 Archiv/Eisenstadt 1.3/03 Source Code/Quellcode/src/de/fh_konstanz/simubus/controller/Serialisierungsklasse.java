package de.fh_konstanz.simubus.controller;

import java.io.Serializable;

import de.fh_konstanz.simubus.model.SimuGraphModel;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;

/**
 * Die Klasse <code>Serialisierungsklasse</code> enthaelt Objekte, die gespeichert
 * werden sollen.
 * 
 * @author unknown
 *
 */
class Serialisierungsklasse implements Serializable
{

   /** ID fuer Serialisierung */
   private static final long serialVersionUID  = 6037265650559782633L;

   /** enthaelt alle Strassen, Haltestellen, usw. */
   private Strassennetz      strassennetz      = null;

   /** enthaelt Defaulteinstellungen fuer Simulation und Optimierung */
   private SimuKonfiguration simuKonfiguration = null;

   /** Karte */
   private SimuGraphModel    graphModel        = null;

   /** enthaelt Planquadrate mit entsprechenden Eigenschaften */
   private VirtualGrid       virtualGrid       = null;

   /**
    * liefert Defaulteinstellungen fuer Simulation und Optimierung
    * @return Defaulteinstellungen fuer Simulation und Optimierung
    */
   public SimuKonfiguration getSimuKonfiguration()
   {
      return simuKonfiguration;
   }

   /**
    * setzt die Defaulteinstellungen fuer Simulation und Optimierung
    * @param simuKonfiguration Defaulteinstellungen fuer Simulation und Optimierung
    */
   public void setSimuKonfiguration( SimuKonfiguration simuKonfiguration )
   {
      this.simuKonfiguration = simuKonfiguration;
   }

   /**
    * liefert das Strassennetz
    * @return das Strassennetz
    */
   public Strassennetz getStrassennetz()
   {
      return strassennetz;
   }

   /**
    * setzt das Strassennetz
    * @param strassennetz das Strassennetz 
    */
   public void setStrassennetz( Strassennetz strassennetz )
   {
      this.strassennetz = strassennetz;
   }

   /**
    * liefert die Karte mit allen Haltestellen, etc.
    * @return Karte mit allen Haltestellen, etc.
    */
   public SimuGraphModel getGraphModel()
   {
      return graphModel;
   }

   /**
    * setzt die Karte mit allen Haltestellen, etc.
    * @param graphModel die Karte mit allen Haltestellen, etc.
    */
   public void setGraphModel( SimuGraphModel graphModel )
   {
      this.graphModel = graphModel;
   }

   /**
    * liefert alle Planquadrate mit seinen Eigenschaften
    * @return alle Planquadrate mit seinen Eigenschaften
    */
   public VirtualGrid getVirtualGrid()
   {
      return virtualGrid;
   }

   /**
    * setzt alle Planquadrate mit seinen Eigenschaften
    * @param virtualGrid alle Planquadrate mit seinen Eigenschaften
    */
   public void setVirtualGrid( VirtualGrid virtualGrid )
   {
      this.virtualGrid = virtualGrid;
   }
}