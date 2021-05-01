package de.fh_konstanz.simubus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import org.jdom.JDOMException;

import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.view.Info;
import de.fh_konstanz.simubus.view.SimuEinstellungen;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.View;


/**
 * Die Klasse <code>MenuListener</code> reagiert auf Ereignisse, wenn ein
 * Menuepunkt ausgewaehlt wird.
 * 
 * @author Daniel Weber
 * @version 1.0 (03.07.2006)
 *
 */
public class MenuListener implements ActionListener
{
   private SimuPanel main = SimuPanel.getInstance();
   private File actualPath = new File(System.getProperty("user.dir"));
   private String xsdFile = "";
   
   

   public void actionPerformed( ActionEvent event )
   {
      View view = View.getInstance();

      if ( event.getActionCommand().compareTo( "Haltestellenoptimierung" ) == 0 )
      {
         view.changePanel();
      }
      else if ( event.getActionCommand().compareTo( "Bussimulation" ) == 0 )
      {
         view.changePanel();
      }
      else if ( event.getActionCommand().compareTo( "Grid ein/ausblenden" ) == 0 )
      {
         SimuPanel.getInstance().setGridVisible( ( (JCheckBoxMenuItem) event.getSource() ).getState() );
      }
      else if ( event.getActionCommand().compareTo( "settings" ) == 0 )
      {
         SimuEinstellungen frame = new SimuEinstellungen();
         frame.setSize( SimuKonfiguration.getInstance().getEinstellungenFrameDimension() );
         frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
         frame.setResizable( false );
         frame.setVisible( true );
      }
      else if ( event.getActionCommand().compareTo( "karteLaden" ) == 0 )
      {
         File selected = getOpenedFile( "map" );

         if ( selected != null )
         {
            BufferedImage image = null;
            try
            {
               image = ImageIO.read( selected );
            }
            catch ( IOException e )
            {
               e.printStackTrace();
            }
            SimuPanel.getInstance().getGraph().setBackgroundImage( image );
         }
      }
      else if ( event.getActionCommand().compareTo( "oeffnen" ) == 0 )
      {
         File selected = getOpenedFile( event.getActionCommand() );

         if ( selected != null )
         {
            SimuPanel.getInstance().resetPanel();
               try {
				try {
					//first check if file is valid
					XMLFileValidation validator = new XMLFileValidation();
					this.xsdFile = actualPath.getCanonicalPath()+ "\\xmlFiles\\xsd\\busSimulation.xsd";
					
					boolean bValue = validator.validateIt(selected.getAbsolutePath(), this.xsdFile);
					if(bValue){
						DateiManager.laden( selected );
					}
					else{
						//File is not valid
						JOptionPane.showOptionDialog(
								View.getInstance(),
								"Die XML- Instanz "+selected.getAbsolutePath() +" ist nicht valide!",
								"Datei Öffnen", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, null, null);
					}
					
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

         }
      }
      else if ( event.getActionCommand().compareTo( "speichern" ) == 0 )
      {
         File selected = getOpenedFile( event.getActionCommand() );
         if ( selected != null )
         {
            String path = selected.getAbsolutePath();
            if ( !path.endsWith( ".xml" ) )
               selected = new File( path + ".xml" );
            try
            {
               DateiManager.speichern( selected );
            }
            catch ( IOException e1 )
            {
               JOptionPane.showMessageDialog( main, "Fehler beim Speichern der Datei!", "XML",
                     JOptionPane.ERROR_MESSAGE );
               e1.printStackTrace();
            }
           
         }
         
      }
      else if ( event.getActionCommand().compareTo( "beenden" ) == 0 )
      {
         System.exit( 0 );
      }
      else if ( event.getActionCommand().compareTo( "info" ) == 0 )
      {
         JFrame info = new Info();
         info.setLocation( 225, 225 );
         info.setSize( 615, 450 );
         info.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
         info.setResizable( false );
         info.setVisible( true );
      }
      else if ( event.getActionCommand().compareTo( "exportieren" ) == 0 )
      {
         File selected = getOpenedFile( event.getActionCommand() );
         if ( selected != null )
         {
            String path = selected.getAbsolutePath();
            if ( !path.endsWith( ".png" ) )
               selected = new File( path + ".png" );
            try
            {
               // simu.saveGraph( selected );
            }
            catch ( Exception e1 )
            {
               JOptionPane.showMessageDialog( view, "Fehler beim Exportieren!", "Bus Simulation",
                     JOptionPane.ERROR_MESSAGE );
               // e1.printStackTrace();
            }
         }
      }
      else if ( event.getActionCommand().compareTo( "neu" ) == 0 )
      {
         SimuPanel.getInstance().resetPanel();
      }
   }

   /**
    * oeffnet File-Dialog und gibt <code>File</code>-Objekt zurueck.
    * 
    * @param type Typ,der geoeffnet werden soll
    * @return <code>File</code>-Objekt, das geoeffnet werden soll
    */
   private File getOpenedFile( String type )
   {
      JFileChooser chooser = new JFileChooser();
      chooser.removeChoosableFileFilter( chooser.getAcceptAllFileFilter() );

      // Show open dialog; this method does not return until the dialog is
      // closed
      if ( type.equals( "oeffnen" ) )
      {
         chooser.addChoosableFileFilter( new FHFileFilter( "xml", "XML" ) );
         chooser.showOpenDialog( main );
      }
      else if ( type.equals( "map" ) )
      {
         chooser.addChoosableFileFilter( new FHFileFilter( "gif", "CompuServe GIF" ) );
         chooser.addChoosableFileFilter( new FHFileFilter( "bmp", "BMP" ) );
         chooser.addChoosableFileFilter( new FHFileFilter( "tif", "TIFF" ) );
         chooser.addChoosableFileFilter( new FHFileFilter( "png", "PNG" ) );
         chooser.addChoosableFileFilter( new FHFileFilter( "jpg", "JPEG" ) );
         chooser.showOpenDialog( main );
      }
      else if ( type.equals( "exportieren" ) )
      {
         chooser.addChoosableFileFilter( new FHFileFilter( "png", "PNG" ) );
      }
      else
      {
         chooser.addChoosableFileFilter( new FHFileFilter( "xml", "XML" ) );
         chooser.showSaveDialog( main );
      }

      return chooser.getSelectedFile();
   }

   /**
    * Filter fuer Datei-Dialog
    * 
    * @author unknown
    *
    */
   class FHFileFilter extends FileFilter
   {

      private String extension;

      private String description;

      public FHFileFilter( String extension, String description )
      {

         this.description = description;
         this.extension = "." + extension;
      }

      @Override
      public String getDescription()
      {
         return description;
      }

      @Override
      public boolean accept( File file )
      {
         if ( file == null )
            return false;

         if ( file.isDirectory() )
            return true;

         return file.getName().toLowerCase().endsWith( extension );
      }
   }
}
