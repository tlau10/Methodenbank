package de.fh_konstanz.simubus.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.util.ImageUtil;
import de.fh_konstanz.simubus.util.Logger;

/**
 * Die Klasse <code>PopUpMenu</code> ist ein Kontext-Menue fuer die Karte.
 * Ueber das Kontext-Menue koennen Ziel, Haltestellen, etc. eingefuegt werden.
 * 
 * @author Daniel Weber
 * @version 1.0 (04.07.2006)
 * 
 */
public class PopUpMenu
{

   /** Panel mit Karte */
   private SimuPanel simu;

   /** Graph-Modell */
   private SimuGraph graph;

   /**
    * Konstruktor
    *
    */
   public PopUpMenu()
   {
      simu = SimuPanel.getInstance();
      SimuControlPanel.getInstance();
      graph = simu.getGraph();
   }

   /**
    * oeffnet das Kontext-Menue
    * @param pt Stelle, an der das Kontext-Menue geoeffnet werden soll
    * @param cell Zelle, in der die Aktion ausgefuehrt werden soll
    * @return Kontext-Menue
    */
   public JPopupMenu createPopupMenu( final Point pt, final Object cell )
   {
      JPopupMenu menu = new JPopupMenu();
      // Insert
	      /** 
	       * einfügen von Icons zur besseren Übersicht
	       * @author Michael Litera
	       * @version  1.1 (01.06.2007)
	       */
	      URL halteStelleUrl = ImageUtil.getImageUrl( "haltestelle.gif" );
	      ImageIcon halteStelleIcon = new ImageIcon( halteStelleUrl );
	      /**
	       * 
	       */
      menu.add( new AbstractAction( "Haltestelle hinzufügen", halteStelleIcon )
      {
         private static final long serialVersionUID = -4657811569973771465L;

         public void actionPerformed( ActionEvent ev )
         {
            Collection<Haltestelle> h = Strassennetz.getInstance().getAlleHaltestellen();
            int id = h.size() + 1;
            simu.insertHaltestelle( pt, "Haltestelle " + id );
         }
      } );
      

	      /** 
	       * einfügen von Icons zur besseren Übersicht
	       * @author Michael Litera
	       * @version  1.1 (01.06.2007)
	       */
	      URL targetUrl = ImageUtil.getImageUrl( "ziele.gif" );
	      ImageIcon zieleIcon = new ImageIcon( targetUrl  );
	      /**
	       * 
	       */ 
      menu.add( new AbstractAction( "Ziel hinzufügen", zieleIcon )
      {

         private static final long serialVersionUID = -1031119201933761019L;

         public void actionPerformed( ActionEvent ev )
         {
            simu.insertZiel( pt );
         }
      	} );
      
	      /** 
	       * einfügen von Icons zur besseren Übersicht
	       * @author Michael Litera
	       * @version  1.1 (01.06.2007)
	       */
	      URL strasseUrl = ImageUtil.getImageUrl( "strasse.gif" );
	      ImageIcon strasseIcon = new ImageIcon( strasseUrl );
	      /**
	       * 
	       */ 
      menu.add( new AbstractAction( "Strasse hinzufügen", strasseIcon )
      {
         /**
          * 
          */
         private static final long serialVersionUID = -6790717910177188130L;

         public void actionPerformed( ActionEvent ev )
         {
            simu.insertStrasse( pt );
         }
      } );
      
	      /** 
	       * einfügen von Icons zur besseren Übersicht
	       * @author Michael Litera
	       * @version  1.1 (01.06.2007)
	       */
	      URL strasseGesperrtUrl = ImageUtil.getImageUrl( "strassegesperrt.gif" );
	      ImageIcon strasseGesperrtIcon = new ImageIcon(strasseGesperrtUrl );
	      /**
	       * 
	       */ 
      menu.add( new AbstractAction( "Gesperrte Felder hinzufügen", strasseGesperrtIcon )
      {
         private static final long serialVersionUID = -6363214695081392338L;

         public void actionPerformed( ActionEvent ev )
         {
            simu.insertGesperrteFelder( pt );
         }
      } );
      /**
       * @author Philipp Hofmann
       */
	      /** 
	       * einfügen von Icons zur besseren Übersicht
	       * @author Michael Litera
	       * @version  1.1 (01.06.2007)
	       */
	      URL haltestelleGesperrtUrl = ImageUtil.getImageUrl( "haltestellegesperrt.gif" );
	      ImageIcon haltestelleGesperrtIcon = new ImageIcon( haltestelleGesperrtUrl );
	      /**
	       * 
	       */
      menu.add( new AbstractAction( "Feld für Haltestelle sperren", haltestelleGesperrtIcon )
      {
          private static final long serialVersionUID = -6790717910177188130L;

          public void actionPerformed( ActionEvent ev )
          {
             simu.insertGesperrteHaltestelle( pt );
          }
       } );
      if ( !graph.isSelectionEmpty() )
      {
         menu.addSeparator();
         
	      /** 
	       * einfügen von Icons zur besseren Übersicht
	       * @author Michael Litera
	       * @version  1.1 (01.06.2007)
	       */
	         URL loeschenUrl = ImageUtil.getImageUrl( "loeschen.gif" );
	         ImageIcon loeschenIcon = new ImageIcon( loeschenUrl );
	      /**
	       * 
	       */
         menu.add( new AbstractAction( "löschen", loeschenIcon )
         {
            /**
             * 
             */
            private static final long serialVersionUID = -4170894174821784333L;

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed( ActionEvent e )
            {
               if ( !graph.isSelectionEmpty() )
               {
                  Object[] cells = graph.getSelectionCells();
                  cells = graph.getDescendants( cells );
//                   for ( int i = 0; i < cells.length; i++ )
//                   {
//                   Haltestelle h = null;
//                   if ( cells[ i ] instanceof StrassenEdge )
//                   {
//                  
//                   }
//                   else if( cells[ i ] instanceof GesperrteFelderEdge)
//                   {
//                                          
//                   }
//                   else if ( cells[ i ] instanceof HaltestellenCell )
//                   {
//                   HaltestellenCell hCell = (HaltestellenCell) cells[ i ];
//                   int id = hCell.getId();
//                   Iterator iter = halteStellen.iterator();
//                   while ( iter.hasNext() )
//                   {
//                   h = (Haltestelle) iter.next();
//                   if ( h.getID() == id )
//                   {
//                   break;
//                   }
//                   }
//                   Logger.getInstance().log( "Haltestelle löschen: " + h.getID() );
//                   simu.removeHaltestelle( h );
//                   }
//                   else if(cells[i] instanceof ZieleCell)
//                   {
//                   List<Ziel> ziele = Strassennetz.getInstance().getZiele();
//                   int id = ((ZieleCell)cells[i]).getId();
//                                          
//                   for(int j=0; j<ziele.size(); j++)
//                   {
//                   if(ziele.get(j).getId() == id)
//                   {
//                   Strassennetz.getInstance().removeZiel(ziele.get(j));
//                   break;
//                   }
//                   }
//                   }
//                   }
                 
//                  System.out.println("popup...");
                  if (graph.getSelectionCellAt(pt) instanceof ZieleCell){
//                	  System.out.println("zielecell");
                	  ZieleCell z = (ZieleCell)graph.getSelectionCellAt(pt);
                	  
                	  Strassennetz.getInstance().removeZiel(
                              new Ziel( z.getKoordinaten(), z.getId() ) );
                  }
                  
                  if (graph.getSelectionCellAt(pt) instanceof HaltestellenCell){
//                	  System.out.println("haltestellencell");
                	  HaltestellenCell z = (HaltestellenCell)graph.getSelectionCellAt(pt);
                	  
                	  Haltestelle haltestelle = new Haltestelle(z.getId(),z.getKoordinaten().x, z.getKoordinaten().y,z.getName(), z.getKapazitaet());
                	  Strassennetz.getInstance().removeHaltestelle(haltestelle);
                              
                  }
                  graph.getModel().remove( cells );
                  //graph.getGraphLayoutCache().remove( cells, true, false );
               }
            }
         } );
      }
      return menu;
   }
}
