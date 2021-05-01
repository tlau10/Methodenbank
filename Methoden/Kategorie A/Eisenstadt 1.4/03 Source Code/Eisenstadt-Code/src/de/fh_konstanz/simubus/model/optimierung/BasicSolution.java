package de.fh_konstanz.simubus.model.optimierung;

import java.util.ArrayList;
import java.util.List;

import de.fh_konstanz.simubus.util.Logger;

/**
 * Die Klasse <code>BasicSolution</code> entspricht einer Basisloesung im
 * Operations Research.
 * 
 * @author Ingo Kroh
 * @version 1.0 (16.05.2006)
 */

public class BasicSolution
{

   /** Array mit allen Basisvariablen der Loesung */
   private BasicVariable[] basicSolution = null;

   /**
    * Konstruktor
    * 
    * @param aBasicSolution
    *           Array mit allen Basisvariablen der Loesung
    */
   public BasicSolution( BasicVariable[] aBasicSolution )
   {
      List<BasicVariable> tmp = new ArrayList<BasicVariable>();

      for ( int i = 0; i < aBasicSolution.length; i++ )
      {
    	 Logger.getInstance().log("added basicsolution: "+aBasicSolution[i]);
         if ( aBasicSolution[ i ].getValue() == 1 )
         {
            
        	 tmp.add( aBasicSolution[ i ] );
         }
      }

      basicSolution = tmp.toArray( new BasicVariable[tmp.size()] );
   }

   /**
    * liefert ein Array mit allen Basisvariablen
    * 
    * @return Array mit Basisvariablen
    */
   public BasicVariable[] getBasicVariables()
   {
      return basicSolution;
   }

   @Override
   public int hashCode()
   {
      int hash = 0;

      for ( int i = 0; i < basicSolution.length; i++ )
      {
         hash += basicSolution[ i ].hashCode();
      }
      return hash;
   }

   @Override
   public boolean equals( Object obj )
   {
      boolean result = false;

      if ( obj instanceof BasicSolution )
      {
         BasicVariable[] bs = ( (BasicSolution) obj ).basicSolution;
         if ( bs.length == basicSolution.length )
         {
            List<BasicVariable> variables = new ArrayList<BasicVariable>();

            for ( int i = 0; i < basicSolution.length; i++ )
            {
               variables.add( basicSolution[ i ] );
            }

            for ( int i = 0; i < bs.length; i++ )
            {
               if ( variables.contains( bs[ i ] ) == true )
               {
                  result = true;
               }
               else
               {
                  result = false;
                  break;
               }
            }
         }
      }
      return result;

   }

   @Override
   public String toString()
   {
      String result = "[BasicSolution: ";

      for ( int i = 0; i < basicSolution.length; i++ )
      {
         result = result.concat( basicSolution[ i ] + ", " );

      }

      result = result.concat( "]" );

      return result;
   }
}
