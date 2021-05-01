package de.fh_konstanz.simubus.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.util.ImageUtil;
/**
 * Die Klasse <code>BusPanel</code> wird fuer die Animation der Simulation
 * benoetigt.
 * 
 * @author Daniel Weber
 * @version 1.0 (04.07.2005)
 *
 */
public class BusPanel extends JComponent
{
   /** Bild fuer Bus */
   private Image             busImage         = null;

   private BufferedImage     offImage         = null;

   private Graphics          offGraphics      = null;

   /** ein Bus-Objekt */
   private Bus               myBus;

   private static final long serialVersionUID = 5193322306873370395L;

   public BusPanel( Bus aBus )
   {
      this.busImage = Toolkit.getDefaultToolkit().getImage( ImageUtil.getImageUrl( "bus.png" ) );
      this.myBus = aBus;
      this.setDoubleBuffered( true );
      this.setVisible( true );
   }

   @Override
   public void paintComponent( Graphics g )
   {
      offImage = new BufferedImage( this.getSize().width, this.getSize().height, BufferedImage.TRANSLUCENT );
      offGraphics = offImage.getGraphics();
      this.drawBus( offGraphics );
      g.drawImage( offImage, 0, 0, null );
   }

   /**
    * zeichnet den Bus auf der Karte
    * @param g
    */
   private void drawBus( Graphics g )
   {
      g.drawImage( busImage, 0, 0, this );
   }

   /**
    * liefert das Bus-Objekt
    * @return Bus-Objekt
    */
   public Bus getBus()
   {
      return this.myBus;
   }
}
