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

public class Adapter
{
   private Ring myRing_;
   private Solver mySolver_;

   public Adapter()
   {
      myRing_=new Ring();
      if (myRing_==null) System.out.println ("Internal storage could not be applied in Adapter.class"); //Exception
   }

   public void addTimeRelation(Point targetPoint, int passenger, Point busstopPoint, double timeBusstopTarget)
   {
      if (busstopPoint!=null && targetPoint!=null && passenger>0 && passenger<900000 && timeBusstopTarget>0 && timeBusstopTarget<900000)
      {
        Busstop myBusstop = new Busstop(busstopPoint);
        Target myTarget = new Target(targetPoint,passenger);
        TimeRelation myTimeRelation = new TimeRelation(myBusstop,myTarget,timeBusstopTarget);
        myRing_.addTimeRelation(myTimeRelation);
      }
   }

   public void estimateOptimum(int maxTime, int minBusstops)
   {
      if(maxTime>0 && maxTime<900000 && minBusstops>0 && minBusstops<11)
      {
         mySolver_=new Solver(myRing_);
         if (mySolver_!=null) mySolver_.setMinimumBusstops(minBusstops);
         else System.out.println ("Internal storage could not be applied in Adapter.class"); //Exception
         mySolver_.estimateOptimum(maxTime);
      }
   }

   public boolean hasNextResult()
   {
      if(mySolver_!=null) return mySolver_.hasNextResult();
      else return false;
   }

   public Result getNextResult()
   {
      if(mySolver_!=null) return mySolver_.getNextResult();
      else return null;
   }


   
   public Adapter startOptimierung()
   {

       Adapter aAdapter = new Adapter();


       /*int busstops=100,targets=100;
       int target[]=new int[targets];
       int bus[]=new int[busstops];
       int passenger[]=new int[targets];
       int time[]=new int[busstops*targets];

       for(int j=0;j<targets;j++)
       {
         target[j]=j*100;
         passenger[j]=j;
       }

       for(int j=0;j<busstops;j++)
       bus[j]=j;

       for(int i=0;i<busstops*targets;i++)
       time[i]=1;*/

       int t=0;
       int z=0;
       double summeLaufzeit=0,summeKapazitaet=0,gesamtZielPassagiere=0;

       int target[]={100,200,300,400,500,600,700,800};
       int passenger[]={300,400,200,50,900,350,40,100}; //=2340
       int bus[]={10,20,30,40,50};
       int time[]={2,6,12,11,6,
                   4, 4,8,7,5,
                   8,3,4,6,7,
                   12,6,2,2,6,
                   10,9,6,3,2,
                   6,8,8,6,2,
                   8,6,5,5,4,
                   5,2,7,9,8};

       for(int x=0;x<target.length;x++)
       {
          for(int i=0;i<bus.length;i++)
          {
             aAdapter.addTimeRelation(new Point(target[x],target[x]),passenger[x],new Point(bus[i],bus[i]),time[t]);
             t++;
          }
        }

      for (int i=0;i<passenger.length;i++)
		  gesamtZielPassagiere=gesamtZielPassagiere+passenger[i];

	  aAdapter.estimateOptimum(6, 1);
	  /*      while(z<13)
      {
          aAdapter.estimateOptimum(z,1);
          while(aAdapter.hasNextResult())
          {
             summeLaufzeit=0;
             summeKapazitaet=0;
             System.out.println("_______________________________________________");
             System.out.println("Max Laufzeit: "+z+" ");
             System.out.println("");
             Result aResult=aAdapter.getNextResult();
             while(aResult.hasNextBusstop())
             {
                Busstop aBusstop = aResult.getNextBusstop();
                System.out.print("X"+(1+aBusstop.getValue()));
                System.out.print("    Max.Kapazitaet/Tag: "+(aBusstop.getPassenger()));
                System.out.println("    Passagiere*Zeit: "+(aBusstop.getRunTime()));
                summeLaufzeit=summeLaufzeit+aBusstop.getRunTime();
                summeKapazitaet=summeKapazitaet+aBusstop.getPassenger();
             }
             System.out.println("");
             System.out.println("Alle Ziele erreicht?: "+aResult.reachedAllTargets());
             System.out.print("Summe Max.Kapazitaet: "+summeKapazitaet +"    Passagiere, die in den Ring wollen: "+gesamtZielPassagiere);
             System.out.println("    = "+(summeKapazitaet-gesamtZielPassagiere));
             System.out.println("Summe Laufzeit: "+summeLaufzeit);

          }
          z++;
      }*/
	  return aAdapter;
   }

}
