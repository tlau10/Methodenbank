package de.fh_konstanz.simubus.model.optimierung;

/**
 * <p>�berschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: FH-Konstanz</p>
 * @author Christian Steil & Ruping Hua
 * @version 1.3
 */
import java.util.*;

public class Result
{
   private List busStops_;
   private Iterator it_;
   private boolean reachedAllTargets_=false;
   private double summeLaufzeit = 0;
   
   public Result()
   {
      busStops_ = new ArrayList();
      it_=busStops_.iterator();
   }

   public void addBusstop(Busstop aBusstop)
   {
      busStops_.add(aBusstop);
      it_=busStops_.iterator();
   }

   // *** Is something in the List? ***
   public boolean hasNextBusstop()
   {
     if(it_.hasNext()==false)
     {
        it_=busStops_.iterator();
        return false;
     }
     else return it_.hasNext();
   }

    // *** Return a busstop, otherwise null ***
   public Busstop getNextBusstop()
   {
      if (it_.hasNext()) return (Busstop) it_.next();
      else return null;
   }

   public boolean reachedAllTargets()
   {
      return reachedAllTargets_;
   }

   public void setReachedAllTargets(boolean value)
   {
      reachedAllTargets_=value;
   }
   
   public String toString() {
	   String ja_nein;
	   if (reachedAllTargets_)
		   ja_nein = "Ja";
	   else
		   ja_nein = "Nein";
	   return busStops_.size() +" Haltestellen [Alle Ziele: " +ja_nein +"]";
   }

   public boolean laufzeitKleinerAls(Result res) {
	   if (res.getSummeLaufzeit() > this.getSummeLaufzeit())
		   return true;
	   
	   return false;
   }
   
	public double getSummeLaufzeit() {
		return summeLaufzeit;
	}
	
	public void setSummeLaufzeit(double summeLaufzeit) {
		this.summeLaufzeit = summeLaufzeit;
	}

}
