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

public class Ring
{
   private int maxRow_=0, maxColumn_=3;
   private int busCount_=-1,targetCount_=-1;
   private List timeRelations_;
   private Iterator it_;
   private double myBusArray_[][];
   private double myTargetArray_[][];


   public Ring()
   {
      timeRelations_ = new ArrayList();
      it_=timeRelations_.iterator();
   }

   public void addTimeRelation(TimeRelation aTimeRelation)
   {
      timeRelations_.add(aTimeRelation);
      it_=timeRelations_.iterator();
      maxRow_++;
   }

   public void init()
   {
      myBusArray_=new double[maxRow_][maxColumn_];
      myTargetArray_=new double[maxRow_][maxColumn_];
      doInArray();
      quickSort(0,myBusArray_.length-1,myBusArray_);
      quickSort(0,myTargetArray_.length-1,myTargetArray_);
      setValue();
      searchAndDisplace();
   }

   // *** Is something in the List? ***
   public boolean hasNext()
   {
     if(it_.hasNext()==false)
     {
        it_=timeRelations_.iterator();
        return false;
     }
     else return it_.hasNext();
   }

   public double getRelationTime(int valueBus,int valueTarget)
   {
      TimeRelation aT=null;
      double time=-1;

      while(hasNext())
      {
         aT=getNextTimeRelation();
         Busstop tmpB=aT.getBusstop();
         Target tmpT=aT.getTarget();

         if (valueBus==tmpB.getValue()&& valueTarget==tmpT.getValue())
         time=aT.getTime();
      }
      return time;
   }

   // *** Return a TimeRealation, otherwise null ***
   public TimeRelation getNextTimeRelation()
   {
      if (it_.hasNext()) return (TimeRelation) it_.next();
      else return null;
   }

   public int getTargetCount()
   {
      return targetCount_;
   }

   public int getBusstopCount()
   {
      return busCount_;
   }

   // *** Helper ***
   private void showValue()
   {
      while(hasNext())
      {
         TimeRelation aT=getNextTimeRelation();
         System.out.println("Busstop Nr: "+aT.getBusstop().getValue());
         System.out.println("Target Nr: "+aT.getTarget().getValue());
      }
   }

   // *** Helper ***
   private void doInArray()
   {
      int i=0;

      while(hasNext())
      {
         TimeRelation timeRelation=getNextTimeRelation();
         myBusArray_[i][0]=timeRelation.getBusstop().getCoordinate().getX();
         myBusArray_[i][1]=timeRelation.getBusstop().getCoordinate().getY();
         myTargetArray_[i][0]=timeRelation.getTarget().getCoordinate().getX();
         myTargetArray_[i][1]=timeRelation.getTarget().getCoordinate().getY();
         i++;
      }
   }
    // *** Helper ***
   private void searchAndDisplace()
   {
      int i=0;

      while(hasNext())
      {
         TimeRelation aTimeRelation=getNextTimeRelation();

         double xBus=aTimeRelation.getBusstop().getCoordinate().getX();
         double yBus=aTimeRelation.getBusstop().getCoordinate().getY();
         aTimeRelation.getBusstop().setValue(getValue(xBus,yBus,myBusArray_));

         double xTarget=aTimeRelation.getTarget().getCoordinate().getX();
         double yTarget=aTimeRelation.getTarget().getCoordinate().getY();
         aTimeRelation.getTarget().setValue(getValue(xTarget,yTarget,myTargetArray_));
      }

   }

   // *** Helper ***
   private int getValue(double x,double y,double[][]feld)
   {
      int i=0,back=-1;
      boolean stop=false;
      while (i<maxRow_ && stop==false)
      {
         if ((feld[i][0] == x) && (feld[i][1] == y))
         {
            back=Integer.parseInt(String.valueOf(Math.round(feld[i][2])));
            stop=true;
         }
         i++;
      }
      return back;
   }

   // *** Helper ***
   private void setValue()
   {
      int i=0;
      int value=0;

      //Busstop
      myBusArray_[i][2]=value;

      while(i<maxRow_-1 && myBusArray_[i][0] != 0)
      {
         double tmpLastX=myBusArray_[i][0];
         double tmpLastY=myBusArray_[i][1];

         if ((tmpLastX==myBusArray_[i+1][0]) && (tmpLastY==myBusArray_[i+1][1])) myBusArray_[i+1][2]=value;
         else
         {
            value++;
            myBusArray_[i+1][2]=value;
         }

         i++;
      }
      busCount_=value+1;

       //Target
      i=0;
      value=0;

      myTargetArray_[i][2]=value;

      while(i<maxRow_-1 && myTargetArray_[i][0] != 0)
      {
         double tmpLastX=myTargetArray_[i][0];
         double tmpLastY=myTargetArray_[i][1];

         if ((tmpLastX==myTargetArray_[i+1][0]) && (tmpLastY==myTargetArray_[i+1][1])) myTargetArray_[i+1][2]=value;
         else
         {
            value++;
            myTargetArray_[i+1][2]=value;
         }

         i++;
      }
      targetCount_=value+1;


   }

    public Target getTarget(int value)
    {
        Target found=null;
        boolean abort=false;

        while(hasNext() && abort==false)
        {
           TimeRelation timeRelation=getNextTimeRelation();
           Target theTarget=timeRelation.getTarget();
           if (theTarget.getValue()==value)
           {
              found=theTarget;
              abort=true;
           }
        }
        //Reset
        it_=timeRelations_.iterator();
        return found;
    }

    public Busstop getBusstop(int value)
    {
        Busstop found=null;
        boolean abort=false;

        while(hasNext() && abort==false)
        {
           TimeRelation timeRelation=getNextTimeRelation();
           Busstop theBusstop=timeRelation.getBusstop();
           if (theBusstop.getValue()==value)
           {
              found=theBusstop;
              abort=true;
           }
        }
        //Reset
        it_=timeRelations_.iterator();
        return found;
    }

    // *** Helper ***
    private void quickSort(int left, int right, double[][] feld)
   {
      int i,last;

      if (left >= right) return;
      swap(left,(left+right)/2,feld);
      last = left;
      for (i = left+1; i <= right; i++)
        if (lessthan(feld[i][0],feld[left][0],feld[i][1],feld[left][1]) == true) swap(++last,i,feld);
      swap(left,last,feld);

      quickSort(left,last-1,feld);
      quickSort(last+1,right,feld);
   }

   // *** Helper ***
   private void swap(int x,int y,double[][] feld)
   {
      double tmp[][]=new double[1][2];

      tmp[0][0]=feld[x][0];
      tmp[0][1]=feld[x][1];
      feld[x][0]=feld[y][0];
      feld[x][1]=feld[y][1];
      feld[y][0]=tmp[0][0];
      feld[y][1]=tmp[0][1];
   }

   // *** Helper ***
   private boolean lessthan(double x1,double x2,double y1,double y2)
   {
      if((x1<x2)) return true;
      else if ((x1==x2) && (y1<y2)) return true;
      return false;
   }


}
