package de.fh_konstanz.simubus.controller;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.GraphConstants;

import de.fh_konstanz.simubus.model.GesperrteHaltestelle;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.view.GesperrteFelderEdge;
import de.fh_konstanz.simubus.view.GesperrteHaltestelleCell;
import de.fh_konstanz.simubus.view.HaltestellenCell;
import de.fh_konstanz.simubus.view.LinienEdge;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import de.fh_konstanz.simubus.view.SimuGraph;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.StrassenEdge;
import de.fh_konstanz.simubus.view.ZieleCell;

/**
 * Die Klasse <code>LineListener</code> reagiert auf Ereignisse, wenn dem
 * Modell eine Linie hinzugefuegt wird.
 * 
 * @author Daniel Weber, Ingo Kroh
 * @version 1.0 (03.07.2006)
 * 
 */
public class LineListener implements GraphModelListener
{

   public void graphChanged( GraphModelEvent arg0 )
   {
	  Object[] changed = arg0.getChange().getChanged();
	  Object[] deleted = arg0.getChange().getRemoved();
	  
	  //get the values of StrassenEdge and GesperrteFelderEdge for XMLFileManager
	  XMLFileManager manager = XMLFileManager.getInstance();
	  if(changed != null && changed.length > 0){
		  for(int i = 0; i<changed.length; i++){
			  if(changed[i] instanceof StrassenEdge){
				  StrassenEdge theEdge = (StrassenEdge)changed[i];  
				  manager.setStreetArray(theEdge);
			  }
			  else if(changed[i] instanceof GesperrteFelderEdge){
				  GesperrteFelderEdge theGesperrteEdge = (GesperrteFelderEdge) changed[i];
				  manager.setGesperrteFelderArray(theGesperrteEdge);
			  }
			  else if(changed[i] instanceof HaltestellenCell){
				  HaltestellenCell theHaltestellenEdge = (HaltestellenCell) changed[i];
				  //create Haltestelle
				  Haltestelle h = new Haltestelle(theHaltestellenEdge.getKoordinaten());
				  h.setID(theHaltestellenEdge.getId());
				  h.setKapazitaet(theHaltestellenEdge.getKapazitaet());
				  h.setName(theHaltestellenEdge.getName());
	              manager.setHaltestellenArray(h);
			  }
			  else if(changed[i] instanceof GesperrteHaltestelleCell){
				  GesperrteHaltestelleCell theGesperrteHaltestellenEdge = (GesperrteHaltestelleCell) changed[i];
				  //create gesperrte Haltestelle
				  GesperrteHaltestelle h = new GesperrteHaltestelle(theGesperrteHaltestellenEdge.getKoordinaten(),theGesperrteHaltestellenEdge.getId());
	              manager.setGesperrteHaltestellenArray(h);
			  }
			  else if(changed[i] instanceof ZieleCell){
				  ZieleCell theZieleEdge = (ZieleCell) changed[i];
				  //create Ziel
				  Ziel ziel = new Ziel( theZieleEdge.getKoordinaten(), theZieleEdge.getId() );
				  manager.setZieleArray(ziel);
			  }
		  }
	  }
	  
	  //if Linie was deleted, remove the Edge from Array in XMLFileManager
      if(deleted != null && deleted.length > 0){
		  for(int i = 0; i<deleted.length; i++){
			  if(deleted[i] instanceof StrassenEdge){
				  StrassenEdge theEdge = (StrassenEdge)deleted[i];  
				  manager.deleteStreetArray(theEdge);
			  }
			  else if(deleted[i] instanceof GesperrteFelderEdge){
				  GesperrteFelderEdge theGesperrteEdge = (GesperrteFelderEdge) deleted[i];
				  manager.deleteGesperrteFelderArray(theGesperrteEdge);
			  }
			  else if(deleted[i] instanceof HaltestellenCell){
				  HaltestellenCell theHaltestellenEdge = (HaltestellenCell) deleted[i];
				  // create Haltestelle
				  Haltestelle h = new Haltestelle(theHaltestellenEdge.getKoordinaten());
				  h.setID(theHaltestellenEdge.getId());
				  h.setKapazitaet(theHaltestellenEdge.getKapazitaet());
				  h.setName(theHaltestellenEdge.getName());
				  manager.deleteHaltestellenArray(h);
			  }
			  else if(deleted[i] instanceof GesperrteHaltestelleCell){
				  GesperrteHaltestelleCell theGesperrteHaltestellenEdge = (GesperrteHaltestelleCell) deleted[i];
				  // create gesperrte Haltestelle
				  GesperrteHaltestelle h = new GesperrteHaltestelle(theGesperrteHaltestellenEdge.getKoordinaten(),theGesperrteHaltestellenEdge.getId());
	              manager.deleteGesperrteHaltestellenArray(h);
			  }
			  else if(deleted[i] instanceof ZieleCell){
				  ZieleCell theZieleEdge = (ZieleCell) deleted[i];
				  //create Ziel
				  Ziel ziel = new Ziel( theZieleEdge.getKoordinaten(), theZieleEdge.getId() );
				  manager.deleteZieleArray(ziel);
			  }
			  
		  }
	  }
	  
      
      for ( int i = 0; i < changed.length; i++ )
      {
         if ( changed[ i ].toString() != null )
         {
            if ( changed[ i ] instanceof LinienEdge )
            {
               SimuGraph graph = SimuPanel.getInstance().getGraph();
               SimuControlPanel simuControl = SimuControlPanel.getInstance();
               Linie buslinie = null;
               if(simuControl.getSelectedBuslinie() != null){
            	   buslinie = simuControl.getSelectedBuslinie();
            	   Logger.getInstance().log("removeAllTeilstrecken");
            	   buslinie.removeAllTeilstrecken();
               }
               else{
            	   buslinie = SimuPanel.getInstance().getBusLinie();
            	   Logger.getInstance().log("removeAllTeilstrecken");
            	   buslinie.removeAllTeilstrecken();
               }
                             
               int actSize = 0;

               Object[] obj = graph.getGraphLayoutCache().getCells( false, false, false, true );
               TreeMap<Integer, LinienEdge> linienMap = new TreeMap<Integer, LinienEdge>();
               for ( int j = 0; j < obj.length; j++ )
               {
                  if ( obj[ j ] instanceof LinienEdge )
                  {
                     LinienEdge edge = (LinienEdge) obj[ j ];

                     if ( edge.toString().contains( buslinie.getId() ) )
                     {
                        String s = edge.toString().replace( buslinie.getId() + "_", "" );
                        int id = new Integer( s );
                        linienMap.put( id, edge );
                     }
                  }

               }
               for ( int j = 0; j < linienMap.size(); j++ )
               {
                  LinienEdge e = linienMap.get( j );
                  if ( GraphConstants.getPoints( e.getAttributes() ) != null )
                  {
                     Object[] points = GraphConstants.getPoints( e.getAttributes() ).toArray();
                     for ( int k = 0; k < points.length; k++ )
                     {
                        if ( k + 1 < points.length )
                        {
                           Point2D start = (Point2D) points[ k ];
                           Point2D ende = (Point2D) points[ k + 1 ];
                           Planquadrat[][] planquadrate = VirtualGrid.getInstance().getPlanquadrate();

                           start = getPoint( planquadrate, start );
                           ende = getPoint( planquadrate, ende );

                           buslinie.setPunkte( actSize + 1, start, ende );
                           if(simuControl.getSelectedBuslinie() != null){
                        	   simuControl.updateTeilstreckenList();
                               simuControl.updateTeilstrecke();
                           }
                           else{
                        	   simuControl.updateTeilstreckenListForLoading(buslinie);
                               simuControl.updateTeilstreckeForLoading(buslinie);
                           }
                           
                           
                           actSize++;

                           HaltestellenCell h1 = (HaltestellenCell) graph.getModel().getParent(
                                 graph.getModel().getSource( e ) );
                           HaltestellenCell h2 = (HaltestellenCell) graph.getModel().getParent(
                                 graph.getModel().getTarget( e ) );

                           addHaltestelle( buslinie, h1 );
                           addHaltestelle( buslinie, h2 );

                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void addHaltestelle( Linie buslinie, HaltestellenCell haltestellenCell )
   {
      Collection<Haltestelle> haltestellen = Strassennetz.getInstance().getAlleHaltestellen();

      Iterator<Haltestelle> hIt = haltestellen.iterator();

      while ( hIt.hasNext() == true )
      {
         Haltestelle h = hIt.next();

         if ( h.getID() == haltestellenCell.getId() )
         {
            buslinie.addHaltestelle( h );
         }
      }
   }

   public Point2D getPoint( Planquadrat[][] planquadrate, Point2D aPoint )
   {
      for ( int m = 0; m < planquadrate.length; m++ )
      {
         boolean found = false;

         for ( int n = 0; n < planquadrate[ m ].length; n++ )
         {
            if ( planquadrate[ m ][ n ].getBounds().contains( aPoint ) == true )
            {
               aPoint = planquadrate[ m ][ n ].getPixelCoordinates();
               found = true;
               break;
            }
         }

         if ( found == true )
         {
            break;
         }
      }

      return aPoint;
   }
}
