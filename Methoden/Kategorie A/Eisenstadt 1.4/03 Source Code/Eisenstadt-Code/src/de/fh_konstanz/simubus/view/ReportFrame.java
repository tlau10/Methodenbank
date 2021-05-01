package de.fh_konstanz.simubus.view;


import java.awt.Desktop;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;


import javax.swing.tree.DefaultMutableTreeNode;


public class ReportFrame
{

   private DefaultMutableTreeNode top                = null;
   private File                   filePath           = null;

   public ReportFrame()
   {
      top = new DefaultMutableTreeNode( "" );
      // filePath = new File("c:" + File.separator + "temp" + File.separator);
      filePath = new File( "reports" );
      this.createReportHierarchie( top, filePath );
   }

   private void createReportHierarchie( DefaultMutableTreeNode top, File file )
   {
      DefaultMutableTreeNode currentHead = top;
      File[] files = file.listFiles( new FilenameFilter()
      {
         public boolean accept( File dir, String name )
         {
            if ( name.toLowerCase().endsWith( ".html" ) || dir.isDirectory() )
               return true;
            return false;
         }
      } );
      if ( files != null )
      {
         for ( int i = 0; i < files.length; i++ )
         {
            if ( files[ i ].canRead() )
            {
               if ( files[ i ].isDirectory() )
               {
                  DefaultMutableTreeNode entry = new DefaultMutableTreeNode( files[ i ].getName() );
                  currentHead.add( entry );
                  this.createReportHierarchie( entry, files[ i ] );
               }
               else
               {
            	   Desktop desktop = Desktop.getDesktop();
            	   try {
					desktop.browse(files[i].toURI());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Fehler beim öffnen");
					e.printStackTrace();
				}
                  currentHead.add( new DefaultMutableTreeNode( files[ i ].getName().toString() ) );
               }
            }
         }
      }
   }
}