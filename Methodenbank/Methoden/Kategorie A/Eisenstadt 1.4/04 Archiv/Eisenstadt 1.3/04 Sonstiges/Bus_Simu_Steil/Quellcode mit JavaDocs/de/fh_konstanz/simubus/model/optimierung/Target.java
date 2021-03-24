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


public class Target
{
   //Helper-Attribut (easier to indentify the target). Default: "0"
   private int value_;
   //Coordinate, that indentify the target and shows his Position in the Field
   private Point coordinate_;
   //Count of Passenger, they try to reach the destination
   private int passenger_;

   public Target(Point aCoordinate, int aPassenger)
   {
      coordinate_=aCoordinate;
      passenger_= aPassenger;
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

   public int getPassenger()
   {
      return passenger_;
   }

   // ********** OTHER *************


}















