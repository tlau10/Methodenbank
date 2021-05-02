package de.fh_konstanz.simubus.model.optimierung;

import java.awt.Point;
import java.util.ArrayList;

import de.fh_konstanz.simubus.model.Planquadrat;

/**
 * Die Klasse <code>PathfinderOR</code> beinhaltet den Algorithmus zur
 * Wegberechnung.
 * 
 * @author Michael Franz
 * @version 1.0 (18.05.2006)
 */

public class PathfinderOR
{
   /**
    * Referenz auf die Planquadrat-Matrix, in der alle Planquadrate gespeichert
    * sind.
    */
   private Planquadrat[][]        planquadrate;

   /**
    * Referenz auf das momentan aktive Planquadrat
    */
   private Planquadrat            current;

   /**
    * Grösse der Matrix, anzahlFelder * anzahlFelder
    */
   private int                    anzahlFelder;

   /**
    * Startpunkt, der zur Berechnung des kürzesten Weges benötigt wird.
    */
   private Point                  start;

   /**
    * Zielpunkt, der zur Berechnung des kürzesten Weges benötigt wird.
    */
   private Point                  end;

   /**
    * Liste der Planquadrate, die um den aktuellen Punkt die Möglichkeiten der
    * Punkte hält, die für den weiteren Weg relevant sein könnten .
    */
   private ArrayList<Planquadrat> aSuccessorList;

   /**
    * Liste der Planquadrate, die die relevanten Möglichkeiten hält.
    */
   private ArrayList<Planquadrat> aOpenList;

   /**
    * Liste der Planquadrate, die den bisherigen Weg beinhaltet.
    */
   private ArrayList<Planquadrat> aCloseList;

   /**
    * Gesamtkosten, die sich aus G und H zusammen setzen.
    */
   private int                    fTemp;

   /**
    * Kosten des bisherigen Weges
    */
   private int                    gTemp;

   /**
    * geschätzte Kosten bis zum Ziel
    */
   private int                    hTemp;

   /**
    * Konstruktor, die Listen, die zur Wegberechnung verwendet werden, werden
    * angelegt.
    * 
    * @param planquadrate
    *           die Planquadratmatrix wird übergeben
    * @param anzahlFelder
    *           die Matrixgrösse wird übergeben
    */
   public PathfinderOR( Planquadrat[][] planquadrate, int anzahlFelder )
   {
      this.planquadrate = planquadrate;
      this.anzahlFelder = anzahlFelder;

      aSuccessorList = new ArrayList<Planquadrat>();
      aOpenList = new ArrayList<Planquadrat>();
      aCloseList = new ArrayList<Planquadrat>();
   }

   /**
    * setzt den Start- und den Zielpunkt
    * 
    * @param start
    *           Start
    * @param ziel
    *           Ziel
    */
   public void setStartAndZiel( Point start, Point ziel )
   {
      this.start = start;
      this.end = ziel;
   }

   /**
    * sucht den Weg zwischen Start und Ziel
    * 
    * @return Gibt die Kosten des gesamten Weges zurück.
    */
   public int startSearch()
   {
	   
	   
      reset();

      current = planquadrate[ start.x ][ start.y ];
      gTemp = 0;
      hTemp = getH( start.x, start.y );
      fTemp = getF( gTemp, hTemp );
      current.setValues( fTemp, gTemp, hTemp );
      aOpenList.add( current );

      boolean found = false;
      Planquadrat successor;

      while ( !aOpenList.isEmpty() && !found )
      {
         current = getNextF();
         aOpenList.remove( current );

         generateSuccessorList();

         for ( int loop = 0; loop < aSuccessorList.size(); loop++ )
         {
            successor = aSuccessorList.get( loop );

            hTemp = getH( successor.getPlanquadratX(), successor.getPlanquadratY() );
            gTemp = getG( successor.getPlanquadratX(), successor.getPlanquadratY() );
            fTemp = getF( gTemp, hTemp );

            if ( successor.getPlanquadratX() == end.x && successor.getPlanquadratY() == end.y )
            {
               found = true;
               successor.father = current;
               successor.setValues( fTemp, gTemp, hTemp );
               break;
            }

            if ( successor.getPlanquadratX() == start.x && successor.getPlanquadratY() == start.y )
               continue;

            if ( aOpenList.contains( successor ) )
            {
               if ( fTemp >= successor.getF() )
               {
                  continue;
               }
            }

            if ( aCloseList.contains( successor ) )
            {
               if ( fTemp >= successor.getF() )
               {
                  continue;
               }
            }

            successor.father = current;
            successor.setValues( fTemp, gTemp, hTemp );
            aOpenList.add( successor );
         }

         aCloseList.add( current );
      }

      return gTemp;
   }

   /**
    * sucht das Planquadrat in der Openliste mit dem kleinsten F-Wert.
    * 
    * @return Gibt das Planquadrat mit dem kleinsten F-Wert zurück.
    */
   private Planquadrat getNextF()
   {

      int min = ( aOpenList.get( 0 ) ).getF();
      int tmp;
      Planquadrat tmpPlanquadrat;
      Planquadrat next = aOpenList.get( 0 );

      if ( aOpenList.size() > 1 )
      {
         for ( int i = 1; i < aOpenList.size(); i++ )
         {
            tmpPlanquadrat = aOpenList.get( i );
            tmp = tmpPlanquadrat.getF();
            if ( tmp < min )
            {
               min = tmp;
               next = tmpPlanquadrat;
            }
         }
      }
      return next;
   }

   /**
    * generiert eine Liste, die vom umliegenden Punkt alle möglichen Punkte
    * sucht und in die Successorliste einträgt.
    * 
    */
   private void generateSuccessorList()
   {
      aSuccessorList.clear();

      if ( current.getPlanquadratX() >= 0 && current.getPlanquadratX() < anzahlFelder
            && current.getPlanquadratY() >= 0 && current.getPlanquadratY() < anzahlFelder )
      {

         for ( int y = current.getPlanquadratY() - 1; y <= current.getPlanquadratY() + 1; y++ )
         {
            for ( int x = current.getPlanquadratX() - 1; x <= current.getPlanquadratX() + 1; x++ )
            {

               if ( x == current.getPlanquadratX() && current.getPlanquadratY() == y )
                  continue;
               else if ( x < 0 || y < 0 )
                  continue;
               else if ( x >= anzahlFelder || y >= anzahlFelder )
                  continue;
               else if ( planquadrate[ x ][ y ].isGesperrt )
                  continue;
               else if ( !aCloseList.contains( planquadrate[ x ][ y ] ) )
                  aSuccessorList.add( planquadrate[ x ][ y ] );
            }
         }

         if ( !( current.getPlanquadratY() == 0 ) )
         {
            if ( aSuccessorList.contains( planquadrate[ current.getPlanquadratX() ][ current
                  .getPlanquadratY() - 1 ] ) )
            {
               if ( !( current.getPlanquadratX() == 0 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() - 1 ][ current
                        .getPlanquadratY() - 1 ] );
               if ( !( current.getPlanquadratX() == anzahlFelder - 1 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() + 1 ][ current
                        .getPlanquadratY() - 1 ] );
            }
         }

         if ( !( current.getPlanquadratY() == anzahlFelder - 1 ) )
         {
            if ( aSuccessorList.contains( planquadrate[ current.getPlanquadratX() ][ current
                  .getPlanquadratY() + 1 ] ) )
            {
               if ( !( current.getPlanquadratX() == 0 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() - 1 ][ current
                        .getPlanquadratY() + 1 ] );
               if ( !( current.getPlanquadratX() == anzahlFelder - 1 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() + 1 ][ current
                        .getPlanquadratY() + 1 ] );
            }
         }

         if ( !( current.getPlanquadratX() == 0 ) )
         {
            if ( aSuccessorList.contains( planquadrate[ current.getPlanquadratX() - 1 ][ current
                  .getPlanquadratY() ] ) )
            {
               if ( !( current.getPlanquadratY() == 0 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() - 1 ][ current
                        .getPlanquadratY() - 1 ] );
               if ( !( current.getPlanquadratY() == anzahlFelder - 1 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() - 1 ][ current
                        .getPlanquadratY() + 1 ] );
            }
         }

         if ( !( current.getPlanquadratX() == anzahlFelder - 1 ) )
         {
            if ( aSuccessorList.contains( planquadrate[ current.getPlanquadratX() + 1 ][ current
                  .getPlanquadratY() ] ) )
            {
               if ( !( current.getPlanquadratY() == 0 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() + 1 ][ current
                        .getPlanquadratY() - 1 ] );
               if ( !( current.getPlanquadratY() == anzahlFelder - 1 ) )
                  aSuccessorList.remove( planquadrate[ current.getPlanquadratX() + 1 ][ current
                        .getPlanquadratY() + 1 ] );
            }
         }
      }
   }

   /**
    * Berechnung von F
    * 
    * @param g
    *           Kosten des bisher zurück gelegten Weges
    * @param h
    *           geschätzte Kosten bis zum Zielpunkt
    * @return Kosten des bisher zurück gelegten Weges addiert mit den
    *         geschätzten Kosten bis zum Ziel.
    */
   private int getF( int g, int h )
   {
      return g + h;
   }

   /**
    * Berechnung von G
    * 
    * @param x
    *           X-Wert des Punktes der benötigt wird um die Kosten ins nächste
    *           Feld zu bestimmen.
    * @param y
    *           Y-Wert des Punktes der benötigt wird um die Kosten ins nächste
    *           Feld zu bestimmen.
    * @return Gibt die Kosten ins nächste Feld zurück.
    */
   private int getG( int x, int y )
   {
      if ( ( Math.abs( x - current.getPlanquadratX() ) - Math.abs( y - current.getPlanquadratY() ) ) == 0 )
         return 14 + current.getG();
      else
         return 10 + current.getG();
   }

   /**
    * Berechung von H
    * 
    * @param x
    *           X-Wert des Punktes der benötigt wird um die Kosten bis zum Ziel
    *           zu schätzen.
    * @param y
    *           Y-Wert des Punktes der benötigt wird um die Kosten bis zum Ziel
    *           zu schätzen.
    * @return Gibt die geschätzten Kosten bis zum Ziel zurück.
    */
   private int getH( int x, int y )
   {
      return ( ( Math.abs( x - end.x ) + Math.abs( y - end.y ) ) ) * 10;
   }

   /**
    * leert alle Listen
    * 
    */
   private void reset()
   {
      current = null;
      aOpenList.clear();
      aCloseList.clear();
      aSuccessorList.clear();
   }

}
