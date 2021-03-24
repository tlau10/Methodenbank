package de.fh_konstanz.simubus.controller;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.GraphConstants;

import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.view.ZieleCell;

/**
 * Die Klasse <code>TargetListener</code> reagiert darauf, falls ein Ziel
 * in das Modell eingefuegt, verschoben oder geloescht wird.
 * 
 * @author Daniel Weber, Ingo Kroh
 * @version 1.0 (06.06.2006)
 */

public class TargetListener implements GraphModelListener
{

   public void graphChanged( GraphModelEvent arg0 )
   {
      Object[] changed = arg0.getChange().getChanged();
      Object[] deleted = arg0.getChange().getRemoved();

      Planquadrat[][] planquadrate = VirtualGrid.getInstance().getPlanquadrate();
      if ( deleted != null )
      {
         for ( int i = 0; i < deleted.length; i++ )
         {
            if ( deleted[ i ] instanceof ZieleCell )
            {
               ZieleCell z = (ZieleCell) deleted[ i ];
               planquadrate[ z.getKoordinaten().x ][ z.getKoordinaten().y ].setEmpty();
            }
         }
      }
      else
      {
         for ( int i = 0; i < changed.length; i++ )
         {
            if ( changed[ i ].toString() != null )
            {
               if ( changed[ i ] instanceof ZieleCell )
               {
                  ZieleCell z = (ZieleCell) changed[ i ];

                  planquadrate = VirtualGrid.getInstance().getPlanquadrate();

                  for ( int j = 0; j < planquadrate.length; j++ )
                  {
                     for ( int k = 0; k < planquadrate[ j ].length; k++ )
                     {
                        Rectangle2D rec = GraphConstants.getBounds( ( (ZieleCell) changed[ i ] )
                              .getAttributes() );

                        Rectangle2D.Double r = planquadrate[ j ][ k ].getBounds();

                        if ( r.contains( new Point2D.Double( rec.getCenterX(), rec.getCenterY() ) ) )
                        {
                           Ziel ziel = new Ziel( planquadrate[ j ][ k ].getPlanquadratCoordinates(), z
                                 .getId() );
                           planquadrate[ j ][ k ].setZiel();
                           Strassennetz.getInstance().addZiel( ziel );
                           planquadrate[ j ][ k ].setID( ( (ZieleCell) changed[ i ] ).getId() );

                           z.setKoordinaten( planquadrate[ j ][ k ].getPlanquadratX(), planquadrate[ j ][ k ]
                                 .getPlanquadratY() );
                          
                        }
                        else if ( planquadrate[ j ][ k ].isZiel == true
                              && planquadrate[ j ][ k ].getID() == ( (ZieleCell) changed[ i ] ).getId() )
                        {

                           planquadrate[ j ][ k ].setEmpty();
                           Strassennetz.getInstance().removeZiel(
                                 new Ziel( planquadrate[ j ][ k ].getPlanquadratCoordinates(), z.getId() ) );
                           
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
