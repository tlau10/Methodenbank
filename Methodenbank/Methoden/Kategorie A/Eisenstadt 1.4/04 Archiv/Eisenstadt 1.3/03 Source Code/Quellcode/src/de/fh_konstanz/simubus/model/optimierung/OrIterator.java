package de.fh_konstanz.simubus.model.optimierung;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JProgressBar;

import Jama.Matrix;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.util.OrOptiIterator;

/**
 * Die Klasse <code>OrIterator</code> loest OR-Modelle. Sie verwendet dafuer
 * die Simplexmethode zur Umformung der Matrix.
 * 
 * @author Ingo Kroh
 * @version 1.0 (11.05.2006)
 */

public class OrIterator
{
   /** Matrix, die das OR-Modell beinhaltet */
   public Matrix             matrix         = null;

   /** Liste mit allen alternativen Loesungen */
   private Set<BasicSolution> basicSolutions = new HashSet<BasicSolution>();;

   private int                count          = 0;

   /**
    * Konstruktor
    * 
    * @param aMatrix
    *           OR-Modell in Form eines zwei-dimensionalen Array
    */
   public OrIterator( double[][] aMatrix )
   {
      matrix = new Matrix( aMatrix );
	   Logger.getInstance().log("new OrIterator, matrix dimension "+matrix.getRowDimension()+"x"+matrix.getColumnDimension());
   }

   /**
    * ueberprueft ob sich noch negative Werte in der Zielfunktion befinden
    * 
    * @return true, falls negative Werte in der Zielfunktion, sonst false
    */
   private boolean isOptimal()
   {
      boolean result = true;

      Double[] targetFunction = getTargetFunction();

      for ( int i = 0; i < targetFunction.length - 1; i++ )
      {
         if ( targetFunction[ i ].doubleValue() < 0 )
         {
            result = false;
            break;
         }
      }

      return result;
   }

   /**
    * Ueberprueft ob die uebergebene Spalte ein Einheitsvektor ist
    * 
    * @param aColumn
    *           zu ueberpruefende Spalte
    * @return true, wenn Spalte ein Einheitsvektor ist, sonst false
    */
   private boolean isUnitVector( int aColumn )
   {
      boolean result = true;

      int oneCount = 0;

      for ( int i = 0; i < matrix.getRowDimension(); i++ )
      {
         double value = matrix.get( i, aColumn );

         if ( value == 1 )
         {
            oneCount++;
         }
         else if ( value == 0 )
         {
            // alles ok
         }
         else
         {
            result = false;
            break;
         }
      }

      if ( result == true && oneCount != 1 )
      {
         result = false;
      }

      return result;
   }

   /**
    * ermittelt die Pivot-Spalte
    * 
    * @return Index der Pivotspalte falls diese negative Werte hat, sonst -1
    */
   private int getPivotColumn()
   {
      int result = -1;

      Double[] targetFunction = getTargetFunction();

      double last = 0;

      for ( int i = 0; i < targetFunction.length - 1; i++ )
      {
         if ( targetFunction[ i ].doubleValue() < last )
         {
            last = targetFunction[ i ];
            result = i;
         }
      }

      return result;
   }

   /**
    * ermittelt die Pivot-Zeile
    * 
    * @param aPivotColumn
    *           die Pivot-Spalte
    * @return Index der Pivot-Zeile
    */
   private int getPivotRow( int aPivotColumn )
   {
      double bi = matrix.get( 0, matrix.getColumnDimension() - 1 );
      double ai = matrix.get( 0, aPivotColumn );

      double last;
      int result = 0;

      if ( ai > 0 )
      {
         last = bi / ai;
      }
      else
      {
         last = 100000;
      }

      for ( int i = 1; i < matrix.getRowDimension() - 1; i++ )
      {
         bi = matrix.get( i, matrix.getColumnDimension() - 1 );
         ai = matrix.get( i, aPivotColumn );

         if ( ( ai > 0 ) && ( bi / ai < last ) )
         {
            result = i;
            last = bi / ai;
         }
      }

      return result;
   }

   /**
    * loest das OR-Modell
    * 
    * @param detailProgressBar
    * 
    */
   public Set<BasicSolution> solve( JProgressBar detailProgressBar )
   {
      while ( isOptimal() == false )
      {
         int column = getPivotColumn();
         int row = getPivotRow( column );

         iterate( row, column );
      }
     
      
      this.matrix = OrOptiIterator.getInstance().fillMinimalMatrix(this.matrix);
     
      /*
      for(int i=0; i< this.matrix.getArray().length; i++)
    	  optiIt.printMatrix(optiIt.createTwoDimMatrixFromRow(this.matrix.getArray()[i]));
      */
      //basislösungen erzeugen
      getSolution( detailProgressBar );
      /*
      System.out.println("b vektor");
      for (int i = 0; i < getBVector().length; i++) 
		System.out.print(" "+getBVector()[i]);
	   */
	
      return basicSolutions;
   }

   /**
    * Formt die Matrix so um, dass ein Einheitsvektor in der Spalte
    * <code>aColumn</code> entsteht. Die Eins ist dabei in der Zeile
    * <code>aRow</code>.
    * 
    * @param aRow
    *           Index fuer Zeile
    * @param aColumn
    *           Index fuer Spalte
    */
   private void iterate( int aRow, int aColumn )
   {
      double value = matrix.get( aRow, aColumn );

      for ( int i = 0; i < matrix.getColumnDimension(); i++ )
      {
         double newValue = matrix.get( aRow, i ) * ( 1 / value );
         matrix.set( aRow, i, newValue );
      }

      for ( int i = 0; i < aRow; i++ )
      {
         value = matrix.get( i, aColumn ) * ( -1 );

         for ( int j = 0; j < matrix.getColumnDimension(); j++ )
         {
            double newValue = ( matrix.get( aRow, j ) * value ) + matrix.get( i, j );
            matrix.set( i, j, newValue );
         }
      }

      for ( int i = aRow + 1; i < matrix.getRowDimension(); i++ )
      {
         value = matrix.get( i, aColumn ) * ( -1 );

         for ( int j = 0; j < matrix.getColumnDimension(); j++ )
         {
            double newValue = ( matrix.get( aRow, j ) * value ) + matrix.get( i, j );
            matrix.set( i, j, newValue );
         }
      }
   }

   /**
    * Transformiert die Matrix so, das fuer jede Restriktion eine
    * Ueberschreitungs- und M-Variable eingefuegt wird (Noetig fuer >=
    * Restriktionen). Weiterhin wird eine Voriteration durchgefuehrt, dass die
    * Matrix dann geloest werden kann.
    * 
    */
   public void transform()
   {
      int numberOfRestrictions = matrix.getRowDimension() - 1;

      
      //numberOfRestrictions * 2 wegen M-Variable
      Matrix tmpMatrix = new Matrix( matrix.getRowDimension(), matrix.getColumnDimension()
            + ( numberOfRestrictions * 2 ) );

      tmpMatrix.setMatrix( 0, matrix.getRowDimension() - 1, 0, matrix.getColumnDimension() - 2, 
    		  matrix.getMatrix( 0, matrix.getRowDimension() - 1, 0, matrix.getColumnDimension() - 2 ) );

      this.insertMVariables( tmpMatrix );

      this.setBVector( tmpMatrix );

      matrix = tmpMatrix;

      this.preIterate();

   }

   /**
    * Kopiert den B-Vektor der Ausgangsmatrix in die Zielmatrix. Nur fuer
    * <code>transform()</code>-Methode gedacht!!
    * 
    * @param aTmpMatrix
    *           die Zielmatrix
    */
   private void setBVector( Matrix aTmpMatrix )
   {
      Double[] bVector = getBVector();

      for ( int i = 0; i < aTmpMatrix.getRowDimension(); i++ )
      {
         aTmpMatrix.set( i, aTmpMatrix.getColumnDimension() - 1, bVector[ i ] );
      }
   }

   /**
    * Fuegt Ueberschreitungs- und M-Variablen in die Zielmatrix ein. Nur fuer
    * <code>transform()</code>-Methode gedacht!!
    * 
    * @param aTmpMatrix
    *           die Zielmatrix
    */
   private void insertMVariables( Matrix aTmpMatrix )
   {
      int row = 0;

      for ( int i = matrix.getColumnDimension() - 1; i < aTmpMatrix.getColumnDimension() - 1; i += 2 )
      {
         aTmpMatrix.set( row, i, -1 );
         aTmpMatrix.set( row, i + 1, 1 );
         aTmpMatrix.set( aTmpMatrix.getRowDimension() - 1, i + 1, 1000 );
         row++;
      }
   }

   /**
    * fuehrt eine Vor-Iteration der M-Variablen durch. Nur fuer
    * <code>transform()</code>-Methode gedacht!!
    * 
    */
   private void preIterate()
   {
      int row = matrix.getRowDimension() - 2;

      for ( int i = matrix.getColumnDimension() - 2; row >= 0; i -= 2 )
      {
         iterate( row, i );
         row--;
      }
   }

   /**
    * liefert eine Kopie der Matrix
    * 
    * @return eine Kopie der Matrix
    */
   public double[][] getMatrix()
   {
      return matrix.getArrayCopy();
   }

   /**
    * gibt die Matrix auf der Konsole aus
    * 
    */
   public void printMatrix()
   {
      matrix.print( 7, 1 );
   }

   /**
    * ermittelt alle Loesungen des OR-Modell
    * 
    * @param detailProgressBar
    * 
    */
   private void getSolution( JProgressBar detailProgressBar )
   {
      List<Integer> unitVectors = getUnitVectors();

      long numberOfCombinations = getNumberOfCombinations( unitVectors );
      Logger.getInstance().log("nrOfCombinations: "+new Long(numberOfCombinations).toString() );
      detailProgressBar.setMaximum( 100 );

      Logger.getInstance().log("anzahl basisvars: "+ (matrix.getRowDimension() - 1));
      BasicVariable[] solution = new BasicVariable[matrix.getRowDimension() - 1];

      for ( int i = 0; i < unitVectors.size(); i++ )
      {
         int column = unitVectors.get( i );
         int row = getRowIndex( column, 1 );
         
         double matrixValue = matrix.get( row, matrix.getColumnDimension() - 1 );
         
         /** Workaround für Matrix -1 Bug **/
         
         
         if(matrixValue == -1.0 )
        	 matrixValue = 1.0;
         
         /** TODO: TESTEN **/
		
         if ( row == 0 )
         {
        
            solution[row] = new BasicVariable( row + 1, column + 1, matrixValue );
            BasicVariable[] arrayTemp =  new BasicVariable[] { solution[row] };
           
            searchAlternatives( unitVectors, solution, row + 1, detailProgressBar, numberOfCombinations );
            
            /** TODO Testen: Auch die erste Lösunge sollte verwende werden **/
            basicSolutions.add( new BasicSolution( arrayTemp ) );
            
            /**** ***/
         }
      }
   }

   private long getNumberOfCombinations( List<Integer> unitVectors )
   {
      long result = getRowCount( 0, unitVectors, 1 );

      for ( int i = 1; i < matrix.getRowDimension() - 1; i++ )
      {
         result *= getRowCount( i, unitVectors, 1 );
      }

      return result;
   }

   private int getRowCount( int aRow, List<Integer> columns, int aDigit )
   {
      int result = 0;

      for ( int i = 0; i < columns.size(); i++ )
      {
         if ( matrix.get( aRow, columns.get( i ) ) == aDigit )
         {
            result++;
         }
      }

      return result;
   }

   /**
    * Rekursive Methode zur Ermittlung aller alternativen Loesungen
    * 
    * @param aUnitVectors
    *           Liste mit allen Einheitsvektoren
    * @param aSolution
    *           Liste mit Basisvariablen
    * @param aRow
    *           Zeile, in der nach Alternativen gesucht werden soll
    * @param detailProgressBar
    */
   private void searchAlternatives( List<Integer> aUnitVectors, BasicVariable[] aSolution, int aRow,
         JProgressBar detailProgressBar, long aNumberOfCombinations )
   {
      if ( aRow < matrix.getRowDimension() - 1 )
      {
         for ( int i = 0; i < aUnitVectors.size(); i++ )
         {
            int column = aUnitVectors.get( i );
            int row = getRowIndex( column, 1 );

            if ( row == aRow )
            {
            	double matrixValue = matrix.get( row, matrix
                        .getColumnDimension() - 1 );
            	
            	/** TODO workaround **/
            	if(matrixValue == 0.0)
            		matrixValue = 1.0;
            	
               aSolution[ row ] = new BasicVariable( row + 1, column + 1, matrixValue );

               searchAlternatives( aUnitVectors, aSolution, aRow + 1, detailProgressBar,
                     aNumberOfCombinations );
            }
         }
      }
      else
         
      {
         if ( basicSolutions == null )
         {
            basicSolutions = new HashSet<BasicSolution>();
         }
         
         basicSolutions.add( new BasicSolution( aSolution ) );
         count++;

         detailProgressBar.setValue( (int) ( 100 * count / aNumberOfCombinations ) );
      }

   }

   /**
    * liefert den Zeilenindex der uebergebenen Zahl in der uebergebenen Spalte.
    * Bei mehrmaligem Vorkommen wird der zuerst gefundene Index
    * zurueckgeliefert.
    * 
    * @param aColumn
    *           Spalte in der gesucht werden soll
    * @param aDigit
    *           Zahl die gesucht werden soll
    * @return Zeilenindex falls Zahl gefunden, ansonsten -1
    */
   private int getRowIndex( int aColumn, double aDigit )
   {
      int result = -1;

      for ( int j = 0; j < matrix.getRowDimension(); j++ )
      {
         if ( matrix.get( j, aColumn ) == aDigit )
         {
            result = j;
            break;
         }
      }

      return result;
   }

   /**
    * liefert eine <code>List</code> mit den Indizes aller Einheitsvektoren.
    * 
    * @return eine <code>List</code> mit den Indizes aller Einheitsvektoren
    */
   private List<Integer> getUnitVectors()
   {
      List<Integer> unitVectors = new ArrayList<Integer>();

      for ( int i = 0; i < matrix.getColumnDimension() - 1; i++ )
      {
         if ( isUnitVector( i ) == true )
         {
            unitVectors.add( i );
         }
      }
      return unitVectors;
   }

   /**
    * liefert die Zielfunktion des OR-Modell
    * 
    * @return Zielfunktion des OR-Modell
    */
   private Double[] getTargetFunction()
   {
      List<Double> result = new ArrayList<Double>();

      for ( int i = 0; i < matrix.getColumnDimension(); i++ )
      {
         result.add( matrix.get( matrix.getRowDimension() - 1, i ) );
      }

      return result.toArray( new Double[result.size()] );
   }

   /**
    * liefert den B-Vektor des OR-Modell
    * 
    * @return B-Vektor des OR-Modell
    */
   private Double[] getBVector()
   {
      List<Double> result = new ArrayList<Double>();

      for ( int i = 0; i < matrix.getRowDimension(); i++ )
      {
         result.add( matrix.get( i, matrix.getColumnDimension() - 1 ) );
      }

      return result.toArray( new Double[result.size()] );
   }

   public static void main( String[] args )
   {
      /*double m[][] =
      {
      { 3, 2, 1, 0, 12 },
      { 1, 3, 0, 1, 9 },
      { -1, -2, 0, 0, 0 } };

      double[][] m2 =
      {
            { 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                  0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                  0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                  0, 0, 0, 0 } };
*/
      double[][] m3 =
      {
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                  0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                  0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                  0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                  0, 0, 0, 0 } };

      OrIterator it = new OrIterator( m3 );

      it.printMatrix();

      it.transform();

      it.printMatrix();

      Set<BasicSolution> basicSolutions = it.solve( null );

      it.printMatrix();

      Iterator<BasicSolution> solutionIterator = basicSolutions.iterator();

      while ( solutionIterator.hasNext() == true )
      {
         BasicSolution b = solutionIterator.next();

         BasicVariable[] v = b.getBasicVariables();

         for ( int j = 0; j < v.length; j++ )
         {
        	 //
         }
      }
   }
}
