package de.fh_konstanz.simubus.model.optimierung;

/**
 * <p>�berschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: FH-Konstanz</p>
 * @author Christian Steil & Ruping Hua
 * @version 1.3
 */

public class Solver
{
   private Ring myRing_;
   private OperationIterator opIterator_;
   private double minimum_;

   public Solver(Ring aRing)
   {
      myRing_=aRing;
   }

   public void estimateOptimum(double time)
   {
       myRing_.init();
       opIterator_=new OperationIterator(myRing_.getTargetCount(),myRing_.getBusstopCount(),myRing_);
       opIterator_.setMinimumBusstops(minimum_);

       while(myRing_.hasNext())
       {
          TimeRelation aTimeRelation=myRing_.getNextTimeRelation();
          aTimeRelation.getBusstop().reset();
          if (aTimeRelation.getTime()<=time)
          opIterator_.setValue(aTimeRelation);
       }
       opIterator_.startEvaluation();
   }

   public void setMinimumBusstops(double m)
   {
      minimum_=m;
   }

   public Result getNextResult()
   {
      if (opIterator_.hasNext()) return (Result) opIterator_.getNextResult();
      else return null;
   }

   public boolean hasNextResult()
   {
      return opIterator_.hasNext();
   }

}
