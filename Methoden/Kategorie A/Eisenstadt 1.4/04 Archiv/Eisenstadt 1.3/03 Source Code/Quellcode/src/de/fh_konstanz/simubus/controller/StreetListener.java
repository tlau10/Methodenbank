package de.fh_konstanz.simubus.controller;

import java.awt.geom.Rectangle2D;

import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.EdgeView;

import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.view.GesperrteFelderEdge;
import de.fh_konstanz.simubus.view.SimuGraph;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.StrassenEdge;

/**
 * Die Klasse <code>StreetListener</code> reagiert darauf, falls eine Strasse
 * in das Modell eingefuegt, verschoben oder geloescht wird.
 * 
 * @author Daniel Weber, Ingo Kroh
 * @version 1.0 (06.06.2006)
 */

public class StreetListener implements GraphModelListener
{

   public void graphChanged( GraphModelEvent arg0 )
   {
      SimuGraph graph = SimuPanel.getInstance().getGraph();
      Object[] changed = arg0.getChange().getChanged();
      Object[] deleted = arg0.getChange().getRemoved();

      Planquadrat[][] planquadrate = VirtualGrid.getInstance().getPlanquadrate();
      if ( deleted != null )
      {
         for ( int i = 0; i < deleted.length; i++ )
         {
            if ( deleted[ i ] instanceof StrassenEdge )
            {
               for ( int j = 0; j < planquadrate.length; j++ )
               {
                  for ( int k = 0; k < planquadrate[ j ].length; k++ )
                  {
                     StrassenEdge edge = (StrassenEdge) deleted[ i ];
                     if ( planquadrate[ j ][ k ].isStreet
                           && ( planquadrate[ j ][ k ].getID() == edge.getId() ) )
                     {

                        Strassennetz.getInstance().removeStrasse( planquadrate[ j ][ k ] );

                        planquadrate[ j ][ k ].setEmpty();
                     }
                  }
               }
            }
         }
      }

      for ( int i = 0; i < changed.length; i++ )
      {
         if ( changed[ i ].toString() != null )
         {
            if ( changed[ i ] instanceof StrassenEdge )
            {
               StrassenEdge edge = (StrassenEdge) changed[ i ];

               for ( int j = 0; j < planquadrate.length; j++ )
               {
                  for ( int k = 0; k < planquadrate[ j ].length; k++ )
                  {
                     EdgeView edgeView = MyEdgeHandle.getInstance().getEdgeView();
                     Rectangle2D r = planquadrate[ j ][ k ].getBounds();

                     if ( edgeView.intersects( graph, r ) == true )
                     {
                        if ( planquadrate[ j ][ k ].isStreet == false )
                        {
                           Strassennetz.getInstance().addStrasse( planquadrate[ j ][ k ] );

                           planquadrate[ j ][ k ].setID( edge.getId() );
                           planquadrate[ j ][ k ].setStreet();
                        }

                     }
                     else
                     {
                        if ( planquadrate[ j ][ k ].isStreet == true && ( planquadrate[ j ][ k ].getID() == edge.getId() ) )
                        {
                           Strassennetz.getInstance().removeStrasse( planquadrate[ j ][ k ] );
                           planquadrate[ j ][ k ].setEmpty();
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
