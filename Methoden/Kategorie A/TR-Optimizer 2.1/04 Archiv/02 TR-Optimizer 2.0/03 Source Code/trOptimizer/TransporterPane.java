package trOptimizer;

/**
 * <p>Title: TransporterPane</p>
 * <p>Description: Die Klasse TransporterPane dient als Zeichenfläche speziell für die Transporter.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JPanel;

public class TransporterPane extends JPanel {

  //Klassenattribute
  private Graphics2D g2 = null;
  private Vector transporter;
  private Image img_transporter;

  /**
   * Der Konstruktor initialisiert die Klassenattribute
   */
  public TransporterPane() {
    transporter = new Vector();
    img_transporter = Toolkit.getDefaultToolkit().getImage("lkw.jpg");


  }

  /**
   * Die Methode paint ist eine Java-Standard-Methode und wird hier mit den gewünschten Eigenschaften überschrieben.
   * Die Methode wir vom Betriebssystem automatisch aufgerufen bzw. kann durch update bzw. repaint auch explizit
   * aufgerufen werden.
   *
   * @param g ein Graphics-Objekt
   */

  public void paint( Graphics g ) {
    g2 = ( Graphics2D )g;
    g2.drawLine( 0, 0, 508, 0 );
    g2.drawLine( 0, 0, 0, 68 );
    g2.drawLine( 0, 68, 508, 68 );
    g2.drawLine( 508, 0, 508, 68 );
/*
    for( int i = 0; i < transporter.size(); i++ ) {
      Transporter t = ( Transporter )transporter.get( i );
      GradientPaint greentodarkGray = new GradientPaint( ( float )t.getX(), ( float )t.getY(), Color.lightGray,
          ( float )t.getX() + ( float )t.getBreite(), ( float )t.getY(), Color.darkGray );
      g2.setPaint( greentodarkGray );
      g2.fillRoundRect( ( int )t.getX(), ( int )t.getY() - 10, ( int )t.getBreite(), ( int )t.getHoehe(), 10, 10 );
      g2.setPaint( Color.black );
      g2.drawString( t.getName(), ( int )t.getX(), ( ( int )t.getY() - 15 ) );
      g2.drawString( "Kap:" + String.valueOf( t.getKapazitaet() ), ( int )t.getX(), ( ( int )t.getY() + 15 ) );
      g2.drawString( "Kos:" + String.valueOf( t.getKosten_pro_km() ), ( int )t.getX(), ( ( int )t.getY() + 25 ) );

    }
*/
    for( int i = 0; i < transporter.size(); i++ ) {
    	System.out.println(transporter.get( i ));
      Transporter t = ( Transporter )transporter.get( i );
      g2.drawImage( img_transporter, (int)t.getX(), ((int)t.getY()-14), null);
      g2.setPaint( Color.black );
      g2.drawString( t.getName(), ( int )t.getX(), ( ( int )t.getY() - 19 ) );
      g2.drawString( "Kap:" + String.valueOf( t.getKapazitaet() ), ( int )t.getX(), ( ( int )t.getY() + 17 ) );
      g2.drawString( "Kos:" + String.valueOf( t.getKosten_pro_km() ), ( int )t.getX(), ( ( int )t.getY() + 27) );

    }
  }

  /**
   * Diese Methode setzt die aktuellen Transporter.
   * @param t ein Vector mit den aktuellen Transportern
   */
  public void setTransporter( Vector t ) {
    transporter = t;
  }

}