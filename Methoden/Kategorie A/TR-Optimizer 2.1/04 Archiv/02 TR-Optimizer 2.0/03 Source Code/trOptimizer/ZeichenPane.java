package trOptimizer;

/**
 * <p>Title: ZeichenPane</p>
 * <p>Description: Die Klasse ZeichePane stellt das eigentliche Zeichenfeld dar. In ihr werden die Produzente, Empfänger und Strecken gezeichnet.</p>
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

public class ZeichenPane extends JPanel {

  //Klassenattribute
  private Vector empfaenger;
  private Vector produzenten;

  private Vector strecken;
  private Graphics2D g2;
  private boolean printLine = false;
  private int lineX1, lineY1, lineX2, lineY2;
  private TransopController controller;
  private Image img_kunde;
  private Image img_prod;

  /**
   * Der Konstruktor initialisiert die Klassenattribute.
   */
  public ZeichenPane() {
    g2 = null;
    empfaenger = new Vector();
    produzenten = new Vector();
    strecken = new Vector();
    img_kunde = Toolkit.getDefaultToolkit().getImage( "kunde.jpg" );
    img_prod = Toolkit.getDefaultToolkit().getImage( "produzent.jpg" );


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

    String tmp = "";


    g2.drawLine( 0, 0, 508, 0 );
    g2.drawLine( 0, 0, 0, 400 );
    g2.drawLine( 508, 0, 508, 400 );
    // temporäre linien zeichnen
    if( printLine ) {
      g2.setPaint( Color.black );
      g2.drawLine( lineX1, lineY1, lineX2, lineY2 );

    }
    printLine = false;

    // strecken zeichnen
    for( int i = 0; i < strecken.size(); i++ ) {
      Strecke s = ( Strecke )strecken.get( i );
      if( s.getId() != 0 ) {
        g2.setPaint( Color.black );
        g2.drawLine( s.getX1Koordinate(), s.getY1Koordinate(), s.getX2Koordinate(), s.getY2Koordinate() );
        g2.drawString( String.valueOf( s.getEntfernung() ), ( s.getX2Koordinate() + s.getX1Koordinate() ) / 2,
            ( s.getY2Koordinate() + s.getY1Koordinate() ) / 2 );
      }
    }

    // empfänger zeichnen
/*
    for( int i = 0; i < empfaenger.size(); i++ ) {
      Empfaenger e = ( Empfaenger )empfaenger.get( i );
      //g2.fill( e );
      g2.setPaint( Color.red );
      g2.fill3DRect( ( int )e.getX(), ( int )e.getY(), ( int )e.getBreite(), ( int )e.getHoehe(), true );
      g2.setPaint( Color.black );
      g2.drawString( e.getName(), ( int )e.getX(), ( ( int )e.getY() - 5 ) );
      g2.drawString( String.valueOf( e.getBenoetigteMenge() ), ( int )e.getX(), ( ( int )e.getY() + 35 ) );
      g2.setPaint( Color.red );
    }
*/

    for( int i = 0; i < empfaenger.size(); i++ ) {
      Empfaenger e = ( Empfaenger )empfaenger.get( i );
      g2.drawImage( img_kunde, (int)e.getX(), (int)e.getY(), null);
      g2.setPaint( Color.black );
      g2.drawString( e.getName(), ( int )e.getX(), ( ( int )e.getY() - 5 ) );
      g2.drawString( String.valueOf( e.getBenoetigteMenge() ), ( int )e.getX(), ( ( int )e.getY() + 35 ) );
    }

    // produzenten zeichnen
/*
    for( int i = 0; i < produzenten.size(); i++ ) {
      Produzent p = ( Produzent )produzenten.get( i );
      GradientPaint bluetodarkGray = new GradientPaint( ( float )p.getX(), ( float )p.getY(), Color.blue,
          ( float )p.getX() + ( float )p.getBreite(), ( float )p.getY(), Color.darkGray );
      g2.setPaint( bluetodarkGray );

      g2.fillOval( ( int )p.getX(), ( int )p.getY(), ( int )p.getBreite(), ( int )p.getHoehe() );
      g2.setPaint( Color.black );
      g2.drawString( p.getName(), ( int )p.getX(), ( ( int )p.getY() - 5 ) );
      g2.drawString( String.valueOf( p.getLieferMenge() ), ( int )p.getX(), ( ( int )p.getY() + 35 ) );
    }
*/
    for( int i = 0; i < produzenten.size(); i++ ) {
     Produzent p = ( Produzent )produzenten.get( i );
      g2.drawImage( img_prod, (int)p.getX(), (int)p.getY(), null);
      g2.setPaint( Color.black );
      g2.drawString( p.getName(), ( int )p.getX(), ( ( int )p.getY() - 5 ) );
      g2.drawString( String.valueOf( p.getLieferMenge() ), ( int )p.getX(), ( ( int )p.getY() + 35 ) );
    }

  }

  /**
   * Diese Methode setzt die aktuellen Empfänger.
   * @param _empfaenger Vector mit den aktuellen Empfängern
   */
  public void setEmpfaenger( Vector _empfaenger ) {
    empfaenger = _empfaenger;
  }

  /**
   * Diese Methode setzt die aktuellen Produzenten.
   * @param _produzenten Vector mit den aktuellen Produzenten
   */
  public void setProduzenten( Vector _produzenten ) {
    produzenten = _produzenten;
  }

  /**
   * Diese Methode setzt die aktuellen Strecken.
   * @param _strecken Vector mit den aktuellen Strecken
   */

  public void setStrecken( Vector _strecken ) {
    strecken = _strecken;
  }

  /**
   * Diese Methode wird im TransportController für die printTempLine benötigt, damit die Anwendung weiss, ob
   * sie die Linie zeichen darf, oder nicht.
   */
  public void setPrintLine() {
    printLine = true;
  }

  /**
   * Diese Methode setzt die Koordinaten der Linie, die später zu einer Strecke umgewandelt wird.
   * @param x1 - Koordinate
   * @param y1 - Koordinate
   * @param x2 - Koordinate
   * @param y2 - Koordinate
   */
  public void setLineKoordinaten( int x1, int y1, int x2, int y2 ) {
    lineX1 = x1;
    lineY1 = y1;
    lineX2 = x2;
    lineY2 = y2;
  }
}