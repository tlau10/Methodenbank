package de.fh_konstanz.simubus.model.optimierung;

/**
 * <p>�berschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: FH-Konstanz</p>
 * @author Christian Steil & Ruping Hua
 * @version 1.3
 */

import java.awt.Point;

public class Busstop
{
   //Helper-Attribut (easier to indentify the target). Default: "0"
   private int value_;
   //Coordinate, that indentify the target and shows his Position in the Field
   private Point coordinate_;
   private int passenger_=0;
   private double runtime_=0;

   public Busstop(Point aCoordinate)
   {
      coordinate_=aCoordinate;
      value_=0;
   }

   // ********** GET/SET *************

   public void setValue(int aValue)
   {
      value_=aValue;
   }

   public int getValue()
   {
      return value_;
   }

   public Point getCoordinate()
   {
      return coordinate_;
   }

   // ********** OTHER *************

   public void reset()
   {
      passenger_=0;
      runtime_=0;
   }

   public void setPassenger(int p)
   {
      passenger_=p;
   }

   public int getPassenger()
   {
      return passenger_;
   }

    public void addRunTime(double r)
   {
      runtime_=runtime_+r;
   }

   public double getRunTime()
   {
      return runtime_;
   }

}












