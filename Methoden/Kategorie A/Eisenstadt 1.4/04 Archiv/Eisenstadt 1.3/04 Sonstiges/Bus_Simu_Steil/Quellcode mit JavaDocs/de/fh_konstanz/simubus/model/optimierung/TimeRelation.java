package de.fh_konstanz.simubus.model.optimierung;

/**
 * <p>�berschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: FH-Konstanz</p>
 * @author Christian Steil & Ruping Hua
 * @version 1.3
 */

public class TimeRelation
{
   private Target target_;
   private Busstop busstop_;
   private double time_;

   public TimeRelation(Busstop aBusstop, Target aTarget, double aTime)
   {
      busstop_=aBusstop;
      target_=aTarget;
      time_=aTime;
   }

   public Busstop getBusstop()
   {
      return busstop_;
   }

   public Target getTarget()
   {
      return target_;
   }

   public double getTime()
   {
      return time_;
   }
}
