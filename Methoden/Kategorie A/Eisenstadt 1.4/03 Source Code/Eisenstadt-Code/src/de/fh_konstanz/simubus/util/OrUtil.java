package de.fh_konstanz.simubus.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JProgressBar;

import de.fh_konstanz.simubus.model.GesperrteHaltestelle;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.model.optimierung.BasicSolution;
import de.fh_konstanz.simubus.model.optimierung.BasicVariable;
import de.fh_konstanz.simubus.model.optimierung.Result;
import de.fh_konstanz.simubus.model.optimierung.StreetDetails;
import de.fh_konstanz.simubus.model.optimierung.TargetDetails;

/**
 * Die Klasse <code>OrUtil</code> stellt Hilfsmethoden fuer die Optimierung
 * zur Verfuegung.
 * 
 * @author Ingo Kroh
 * @version 1.0 (23.05.2006)
 */

public class OrUtil
{

   /**
    * Konstruktor
    * 
    */
   private OrUtil()
   {
      // keine Instanziierung
   }

   /**
    * erstellt anhand von Informationen zu Zielen eine OR-Matrix.
    * 
    * @param aTargetDetails
    *           Liste mit Informationen zu Zielen
    * @param detailProgressBar
    * @return OR-Matrix
    */
   public static double[][] createOrMatrix( List<TargetDetails> aTargetDetails, JProgressBar detailProgressBar )
   {
      Strassennetz netz = Strassennetz.getInstance();
      VirtualGrid virtualGrid = VirtualGrid.getInstance();
      int anzahlFelder = virtualGrid.getAnzFelder();
      Logger.getInstance().log("createOrMatrix");

      Collection<Haltestelle> haltestellen = netz.getAlleHaltestellen();
      Logger.getInstance().log("anzahl Haltestellen: "+haltestellen.size());
      ArrayList<GesperrteHaltestelle> gesperrteHaltestellen = netz.getAlleGesperrteHaltestellenliste();
      Logger.getInstance().log("anzahl GesperrteHaltestellen: "+gesperrteHaltestellen.size());

      Logger.getInstance().log("neues orModel anlegen: ziele x anzahlFelder: "+(aTargetDetails.size() + 1)+"x"+(anzahlFelder * anzahlFelder));
      double[][] orModel = new double[aTargetDetails.size() + 1][anzahlFelder * anzahlFelder];

      detailProgressBar.setMaximum( aTargetDetails.size() );

      for ( int i = 0; i < aTargetDetails.size(); i++ )
      {
    	 Logger.getInstance().log("durchlauf target nr "+i);
         TargetDetails details = aTargetDetails.get( i );
         List<StreetDetails> strassen = details.getStrassenliste();

         for ( int j = 0; j < strassen.size(); j++ )
         {
        	Logger.getInstance().log("durchlauf strasse nr "+j); 
            StreetDetails strasse = strassen.get( j );

            if(gesperrteHaltestellen.size() == 0)
            {
            	orModel[ i ][ strasse.getPlanquadrat().getPlanquadratX()
                  + ( anzahlFelder * strasse.getPlanquadrat().getPlanquadratY() ) ] = 1;
            }
            else
            {
            	//boolean check = false;
            	for(GesperrteHaltestelle gesperrteHaltestelle : gesperrteHaltestellen)
            	{
            		double x = strasse.getPlanquadrat().getPlanquadratX();
            		double y = strasse.getPlanquadrat().getPlanquadratY();
            		if(gesperrteHaltestelle.getKoordinaten().getX() == x && gesperrteHaltestelle.getKoordinaten().getY() == y)
            		{
            			orModel[ i ][ strasse.getPlanquadrat().getPlanquadratX()
            		                  + ( anzahlFelder * strasse.getPlanquadrat().getPlanquadratY() ) ] = 0;
            			
            			break;
            		}
            		else
            		{
            			orModel[ i ][ strasse.getPlanquadrat().getPlanquadratX()
            		                  + ( anzahlFelder * strasse.getPlanquadrat().getPlanquadratY() ) ] = 1;
            		}
            	}
            }

            if ( haltestellen.contains( new Haltestelle( strasse.getPlanquadrat().getPlanquadratX(), strasse
                  .getPlanquadrat().getPlanquadratY() ) ) == true )
            {
               orModel[ aTargetDetails.size() ][ strasse.getPlanquadrat().getPlanquadratX()
                     + ( anzahlFelder * strasse.getPlanquadrat().getPlanquadratY() ) ] = 0.1;
            }
            else
            {
               orModel[ aTargetDetails.size() ][ strasse.getPlanquadrat().getPlanquadratX()
                     + ( anzahlFelder * strasse.getPlanquadrat().getPlanquadratY() ) ] = 1;
            }

         }

         orModel[ i ][ ( anzahlFelder * anzahlFelder ) - 1 ] = 1;

         detailProgressBar.setValue( detailProgressBar.getValue() + 1 );
      }

      //print(aTargetDetails.size() + 1, anzahlFelder * anzahlFelder, orModel);
      
      return orModel;
   }
   /*
   private static void print(int x, int y, double[][] orModel)
   {
	   String text = "<html><body><table border='1' cellspacing='0' cellpadding='0'>";
	   for(int i = 0; i<x;i++)
	   {
		   text += "<tr>";
		   for(int j = 0; j < y; j++)
		   {
			  if(orModel[i][j] == 1.0)
			  {
				  text += "<td style='color:#ff0000'>"+orModel[i][j]+"</td>";
			  }
			  else
			  {
				  text += "<td>"+orModel[i][j]+"</td>";
			  }
		   }
		   text += "</tr>";
	   }
	   text += "</table></body></html>";
	   
	   String dateiName = "c:\\Test.txt";
	   try
	   {
	    FileOutputStream schreibeStrom = 
	                     new FileOutputStream(dateiName);
	    for (int i=0; i < text.length(); i++){
	      schreibeStrom.write((byte)text.charAt(i));
	    }
	    schreibeStrom.close();
	   }
	   catch(Exception e)
	   {
		   
	   }
	    Logger.getInstance().log("Datei ist geschrieben!");

   }
   */
   
   /**
    * erstellt eine Liste mit Ergebnissen der Optimierung. Dazu werden die
    * Ergebnisse der Optimierung mit den Informationen der Zielinformationen
    * erweitert.
    * 
    * @param aBasicSolutions
    *           Liste mit Basisloesungen
    * @param aTargetDetails
    *           Liste mit Zielinformationen
    * @return Liste mit Ergebnissen der Optimierung
    */
   public static List<Result> createResultList( Set<BasicSolution> aBasicSolutions,
         List<TargetDetails> aTargetDetails )
   {
      List<Result> resultList = new ArrayList<Result>();
      Iterator<BasicSolution> solutionIterator;
      
      
//		try {
			solutionIterator = aBasicSolutions.iterator();
//		} catch (Exception e) {
			//wenn keine Lösung gefunden wurde
//			return resultList;
			
//		}
			
			
	if(!solutionIterator.hasNext())
		   Logger.getInstance().log("NULL POINTER EXCEPTION", Logger.LEVEL_FATALERROR);	
			

      while ( solutionIterator.hasNext() == true )
      {
         double totalTime = 0;
         BasicSolution solution = solutionIterator.next();
         BasicVariable[] variables = solution.getBasicVariables();
         Result result = new Result( solution );

         for ( int i = 0; i < variables.length; i++ )
         {
            int y = getYCoordinate( variables[ i ] );
            int x = getXCoordinate( variables[ i ], y );

            for ( int j = 0; j < aTargetDetails.size(); j++ )
            {
               TargetDetails details = aTargetDetails.get( j );
               List<StreetDetails> streets = details.getStrassenliste();

               for ( int k = 0; k < streets.size(); k++ )
               {
                  StreetDetails street = streets.get( k );

                  if ( street.getPlanquadrat().getPlanquadratX() == x
                        && street.getPlanquadrat().getPlanquadratY() == y )
                  {
                     totalTime += street.getZeit();
                     result.addTargetDetails( details );
                  }
               }
            }
         }
         result.setTotalTime( totalTime );
         result.setAverageTime( totalTime / result.getTargetDetails().length );
         resultList.add( result );
      }

      return resultList;
   }

   /**
    * ermittelt die Zeit von einem Ziel zu einer Haltestelle
    * 
    * @param aBasicSolution
    *           Basisloesung der Optimierung
    * @param aTargetDetails
    *           Informationen zum Ziel
    * @return Zeit von einem Ziel zu einer Haltestelle
    */
   public static double getTime( BasicSolution aBasicSolution, TargetDetails aTargetDetails )
   {
      double time = 0;

      BasicVariable[] variables = aBasicSolution.getBasicVariables();

      for ( int i = 0; i < variables.length; i++ )
      {
         int y = getYCoordinate( variables[ i ] );
         int x = getXCoordinate( variables[ i ], y );

         List<StreetDetails> streets = aTargetDetails.getStrassenliste();

         for ( int k = 0; k < streets.size(); k++ )
         {
            StreetDetails street = streets.get( k );

            if ( street.getPlanquadrat().getPlanquadratX() == x
                  && street.getPlanquadrat().getPlanquadratY() == y )
            {
               time = street.getZeit();

            }
         }

      }

      return time;
   }

   /**
    * liefert die X-Koordinate zu einer Basisvariablen
    * 
    * @param aBasicVariable
    *           die Basisvariable
    * @param aYCoordinate
    *           die Y-Koordinate der Basisvariablen
    * @return X-Koordinate zu einer Basisvariablen
    */
   public static int getXCoordinate( BasicVariable aBasicVariable, int aYCoordinate )
   {
      VirtualGrid virtualGrid = VirtualGrid.getInstance();

      int x = ( aBasicVariable.getColumn() - ( aYCoordinate * virtualGrid.getAnzFelder() ) ) - 1;

      return x;
   }

   /**
    * liefert die Y-Koordinate zu einer Basisvariablen
    * 
    * @param aBasicVariable
    *           die Basisvariable
    * @return Y-Koordinate zu einer Basisvariablen
    */
   public static int getYCoordinate( BasicVariable aBasicVariable )
   {
      VirtualGrid virtualGrid = VirtualGrid.getInstance();

      int y = aBasicVariable.getColumn() / virtualGrid.getAnzFelder();

      return y;
   }

   /**
    * liefert die pixelgenaue X-Koordinate zur X-Koordinate in der Matrix
    * 
    * @param aPlanquadratXCoordinate
    *           X-Koordinate in der Matrix
    * @return pixelgenaue X-Koordinate zur X-Koordinate in der Matrix
    */
   public static int getPixelXCoordinate( int aPlanquadratXCoordinate )
   {
      int sizeOfField = SimuKonfiguration.getInstance().getFeldElementGroesse();
      return sizeOfField * aPlanquadratXCoordinate + ( sizeOfField / 2 );
   }

   /**
    * liefert die pixelgenaue Y-Koordinate zur Y-Koordinate in der Matrix
    * 
    * @param aPlanquadratYCoordinate
    *           Y-Koordinate in der Matrix
    * @return pixelgenaue Y-Koordinate zur Y-Koordinate in der Matrix
    */
   public static int getPixelYCoordinate( int aPlanquadratYCoordinate )
   {
      int sizeOfField = SimuKonfiguration.getInstance().getFeldElementGroesse();
      return sizeOfField * aPlanquadratYCoordinate + ( sizeOfField / 2 );
   }

   /**
    * liefert die X-Koordinate in der Matrix zur pixelgenauen X-Koordinate
    * 
    * @param aPixelXCoordinate
    *           X-Koordinate im Panel
    * @return X-Koordinate in der Matrix
    */
   public static int getPlanquadratXCoordinate( int aPixelXCoordinate )
   {
      int sizeOfField = SimuKonfiguration.getInstance().getFeldElementGroesse();
      return aPixelXCoordinate / sizeOfField;
   }

   /**
    * liefert die Y-Koordinate in der Matrix zur pixelgenauen Y-Koordinate
    * 
    * @param aPixelYCoordinate
    *           Y-Koordinate im Panel
    * @return Y-Koordinate in der Matrix
    */
   public static int getPlanquadratYCoordinate( int aPixelYCoordinate )
   {
      int sizeOfField = SimuKonfiguration.getInstance().getFeldElementGroesse();
      return aPixelYCoordinate / sizeOfField;
   }

   public static void main( String[] args )
   {
	   Logger.getInstance().log( new Integer(getPlanquadratXCoordinate( 71 )).toString() );
	   Logger.getInstance().log( new Integer(getPlanquadratYCoordinate( 44 )).toString() );
   }

}
