package de.fh_konstanz.simubus.model.optimierung;

/**
 * Die Klasse <code>BasicVariable</code> entspricht einer Basisvariable im
 * Operations Research.
 * 
 * @author Ingo Kroh
 * @version 1.0 (17.05.2006)
 */

public class BasicVariable
{
   /** Spalte der Basisvariable */
   private int    column = -1;

   /** Zeile, in der die Basisvariable eine Eins hat */
   private int    row    = -1;

   /** Wert der Basisvariable */
   private double value  = -1;

   /**
    * Konstruktor
    * 
    * @param aRow
    *           Zeile, in der die Basisvariable eine Eins hat
    * @param aColumn
    *           Spalte der Basisvariable
    * @param aValue
    *           Wert der Basisvariable
    */
   public BasicVariable( int aRow, int aColumn, double aValue )
   {
      row = aRow;
      column = aColumn;
      value = aValue;
   }

   /**
    * liefert die Spalte der Basisvariable
    * 
    * @return Spalte der Basisvariable
    */
   public int getColumn()
   {
      return column;
   }

   /**
    * liefert die Zeile, in der die Basisvariable eine Eins hat
    * 
    * @return Zeile, in der die Basisvariable eine Eins hat
    */
   public int getRow()
   {
      return row;
   }

   /**
    * liefert den Wert der Basisvariable
    * 
    * @return Wert der Basisvariable
    */
   public double getValue()
   {
      return value;
   }

   @Override
   public boolean equals( Object obj )
   {
      boolean result = false;

      if ( obj instanceof BasicVariable )
      {
         if ( ( (BasicVariable) obj ).column == column && ( (BasicVariable) obj ).row == row )
         {
            result = true;
         }
      }

      return result;
   }

   @Override
   public int hashCode()
   {
      String hash = String.valueOf( row ) + String.valueOf( column );
      return Integer.parseInt( hash );
   }

   @Override
   public String toString()
   {
      return "[BasicVariable: [row: " + row + "] [column: " + column + "] [value: " + value + "] ]";
   }

}
