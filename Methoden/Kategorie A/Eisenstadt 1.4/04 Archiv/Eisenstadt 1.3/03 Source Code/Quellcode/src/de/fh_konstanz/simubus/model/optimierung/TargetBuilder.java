package de.fh_konstanz.simubus.model.optimierung;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.model.Ziel;

/**
 * Die Klasse <code>TargetBuilder</code> berechnet zu jedem Ziel den Weg zu
 * den einzelnen Strassenstücken, welche aus Planquadraten besteht, zudem wird
 * die Zeit zu jedem Planquadrat berechnet.
 * 
 * @author Michael Franz
 * @version 1.0 (17.05.2006)
 */

public class TargetBuilder
{
   /** Liste aller Planquadrate die als Strasse gekennzeichnet sind */
   private ArrayList<Planquadrat> strassenliste = new ArrayList<Planquadrat>();

   /** Liste aller Planquadrate die als Ziele gekennzeichnet sind */
   private ArrayList<Ziel>        zielliste     = new ArrayList<Ziel>();

   /**
    * Referenz auf Strassennetz, worin sämtliche Listen wie Zielliste,
    * Haltestellenliste und Linien gespeichert sind
    */
   private Strassennetz           netz;

   /** Referenz auf die Einstellungen des Programms */
   private SimuKonfiguration      config;

   /**
    * Referenz auf den Pathfinder, der den kürzesten Weg zwischen Ziel und
    * Haltestelle sucht
    */
   private PathfinderOR           pathFinder;

   /**
    * Referenz auf die Planquadrat-Matrix, in der alle Planquadrate gespeichert
    * sind.
    */
   private Planquadrat[][]        planquadrate;

   /**
    * Grösse der Matrix, anzahlFelder * anzahlFelder
    */
   private int                    anzahlFelder;

   /**
    * temporäres Planquadrat zum Zwischenspeichern, welches Planquadrat am
    * nächsten zum Ziel liegt
    */
   private Planquadrat            tmpPlanquadrat;

   /**
    * temporäres Zeitfeld zum Zwischenspeichern, welches Planquadrat mit der
    * kürzesten Zeit zum Ziel liegt
    */
   private double                 tmpZeit;

   /**
    * Feld mit der Zeit, die vom Benutzer vorgegeben wird, welche Zeit von einer
    * Haltestelle zu einem Ziel angemessen ist
    */
   private int                    restGehZeit;

   /**
    * Konstruktor
    * 
    * @param aGehzeit
    *           Zeit, die vom Benutzer vorgegeben wird, welche Zeit von einer
    *           Haltestelle zu einem Ziel angemessen ist
    */
   public TargetBuilder( int aGehzeit )
   {
      restGehZeit = aGehzeit;
   }

   /**
    * vergleicht die gespeicherte Zeit mit der Zeit des Planquadrates, das
    * übergeben wurde. Ist die Zeit kürzer, so wird das Planquadrat und die Zeit
    * temporär gespeichert.
    * 
    * @param planquadrat
    *           Planquadrat, das aufgenommen wird, wenn die Zeit kürzer ist
    * @param zeit
    *           Zeit, die verglichen wird, um das Planquadrat, mit der kürzesten
    *           Entfernung zu speichern.
    */
   private void lookForShortest( Planquadrat planquadrat, double zeit )
   {
      if ( tmpPlanquadrat != null )
      {
         if ( zeit < tmpZeit )
         {
            tmpPlanquadrat = planquadrat;
            tmpZeit = zeit;
         }
      }
      else
      {
         tmpPlanquadrat = planquadrat;
         tmpZeit = zeit;
      }
   }

   /**
    * stellt eine Liste von Zielen und potentiellen Haltestellen zusammen, die
    * mittels der Klasse PathfinderOR den kürzesten Weg sowie die Zeit zwischen
    * Zielen und potentiellen Haltestellen berechnet.
    * 
    * @param detailProgressBar
    *           ProgressBar fuer Fortschritt der Wegsuche
    * 
    * @return Liste mit TargetDetails, diese beinhalten die Ziele, die
    *         potentiellen Haltestellen soeir die Zeiten von Zielen zu den
    *         potentiellen Haltestellen.
    */
   public ArrayList<TargetDetails> buildTargetDetails( JProgressBar detailProgressBar )
   {

      ArrayList<TargetDetails> targetDetailsListe = new ArrayList<TargetDetails>();
      VirtualGrid virtualGrid = VirtualGrid.getInstance();
      config = SimuKonfiguration.getInstance();
      int kosten = 0;
      double persMeter = 0;
      double geschMeter = 0;
      double zeit = 0;
      boolean notInRange = false;
      TargetDetails targetDetail;
      StreetDetails streetDetail;
      Planquadrat startPlanquadrat;
      Planquadrat zielPlanquadrat;

      netz = Strassennetz.getInstance();
      zielliste = netz.getZiele();
      strassenliste = netz.getStrassenListePlanquadrat();

      planquadrate = virtualGrid.getPlanquadrate();
      anzahlFelder = virtualGrid.getAnzFelder();

      pathFinder = new PathfinderOR( planquadrate, anzahlFelder );
      Point start;
      Point ziel;

      detailProgressBar.setMaximum( zielliste.size() );

      for ( int j = 0; j < zielliste.size(); j++ )
      {
         notInRange = false;
         tmpPlanquadrat = null;
         ArrayList<StreetDetails> out_str_liste = new ArrayList<StreetDetails>();
         start = zielliste.get( j ).getZiel();

         for ( int i = 0; i < strassenliste.size(); i++ )
         {
            kosten = 0;

            ziel = strassenliste.get( i ).getPlanquadratCoordinates();
            pathFinder.setStartAndZiel( start, ziel );
            kosten = pathFinder.startSearch();
            // Wegstrecke die laut Einstellungen eine Person in einer Minute
            // zurücklegt
            persMeter = config.getGehGeschwindigkeitInKmH() * 1000 / 60;
            // berechnete Wegstrecke von Pathfinder entsprechend dem Massstab
            geschMeter = (double) kosten / 10 * config.getFeldElementGroesseInM();
            zeit = ( geschMeter / persMeter );
            zielPlanquadrat = new Planquadrat( ziel.x, ziel.y );
            streetDetail = new StreetDetails( zielPlanquadrat, zeit );
            if ( zeit <= ( restGehZeit ) )
            {
               out_str_liste.add( streetDetail );
            }
            else
            {
               lookForShortest( zielPlanquadrat, zeit );
            }
         }

         if ( out_str_liste.isEmpty() )
         {
            streetDetail = new StreetDetails( tmpPlanquadrat, tmpZeit );
            out_str_liste.add( streetDetail );
            notInRange = true;
         }

         startPlanquadrat = new Planquadrat( start.x, start.y );
         targetDetail = new TargetDetails( startPlanquadrat, out_str_liste, notInRange );
         targetDetailsListe.add( targetDetail );

         detailProgressBar.setValue( detailProgressBar.getValue() + 1 );
      }

      return targetDetailsListe;
   }
}
