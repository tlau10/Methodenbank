package de.fh_konstanz.simubus.model;

/**
 * 
 * Die Klasse <code>SimpleDate</code>kapselt eine Uhrzeit
 * 
 * @author Tobias Klein
 *
 */
public class SimpleDate
{
   private int stunde  = 0;

   private int minuten = 0;

   
   /**
    * 
    * @param h Stunden
    * @param m Minuten
    */
   public SimpleDate( int h, int m )
   {
      stunde = h;
      minuten = m;
   }

   /**
    * Fuer Anzeige der Frequenz in BuslinieDetail
    * (--> die Frequenz wird intern als double Wert gespeichert) 
    * @param time Zeit als double Wert
    */
   public SimpleDate( double time )
   {
      stunde = (int) time / 60;
      minuten = (int) time - ( stunde * 60 );
   }

   /**
    * @return Uhrzeit als String im Format hh:mm
    */
   public String toString()
   {
      java.text.DecimalFormat df = new java.text.DecimalFormat( "00" );
      return df.format( stunde ) + ":" + df.format( minuten );
   }

   /**
    * 
    * @return Minuten
    */
   public int getMinuten()
   {
      return minuten;
   }

   /**
    * 
    * @param minuten Minuten
    */
   public void setMinuten( int minuten )
   {
      this.minuten = minuten;
   }

   /**
    * 
    * @return Stunden
    */
   public int getStunde()
   {
      return stunde;
   }

   /**
    * 
    * @param stunde Stunden
    */
   public void setStunde( int stunde )
   {
      this.stunde = stunde;
   }
}
