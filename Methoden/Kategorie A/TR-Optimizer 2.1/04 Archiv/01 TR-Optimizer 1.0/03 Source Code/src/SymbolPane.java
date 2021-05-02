/**
 * <p>Title: SymbolPane</p>
 * <p>Description: Die Klasse SymbolPane ist die rechte Symbol-Fläche. Über sie können durch doppelklick auf das jeweilige Icon neue Objekte erzeugt werden.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class SymbolPane extends JPanel {

  //Klassenattribute
  private Graphics2D g2 = null;

  /**
   * Standardkonstruktor
   *
   */
  public SymbolPane() {}

  /**
   * Die Methode paint ist eine Java-Standard-Methode und wird hier mit den gewünschten Eigenschaften überschrieben.
   * Die Methode wir vom Betriebssystem automatisch aufgerufen bzw. kann durch update bzw. repaint auch explizit
   * aufgerufen werden.
   *
   * @param g ein Graphics-Objekt
   */
  public void paint( Graphics g ) {
    g2 = ( Graphics2D )g;
    g2.setPaint( Color.white );

    g2.fill3DRect( 0, 0, 75, 1000, true );

    g2.setPaint( Color.black );
    g2.drawString( "Empfänger", 3, 30 );
    g2.setPaint( Color.red );
    Empfaenger e = new Empfaenger( 20, 45, 0 );
    g2.fill3DRect( ( int )e.getX(), ( int )e.getY(), ( int )e.getBreite(), ( int )e.getHoehe(), true );

    g2.setPaint( Color.black );
    g2.drawString( "Produzent", 3, 120 );
    Produzent produzent = new Produzent( 20, 135, 0 );
    GradientPaint bluetodarkGray = new GradientPaint( ( float )produzent.getX(), ( float )produzent.getY(), Color.blue,
        ( float )produzent.getX() + ( float )produzent.getBreite(), ( float )produzent.getY(), Color.darkGray );
    g2.setPaint( bluetodarkGray );
    g2.fill( produzent );

    g2.setPaint( Color.black );
    g2.drawString( "Transporter", 1, 210 );
    Transporter t = new Transporter( 17, 225, 0 );
    GradientPaint greenToDarkGray = new GradientPaint( ( float )t.getX(), ( float )t.getY(), Color.lightGray,
        ( float )t.getX() + ( float )t.getBreite(), ( float )t.getY(), Color.darkGray );
    g2.setPaint( greenToDarkGray );
    g2.fillRoundRect( ( int )t.getX(), ( int )t.getY(), ( int )t.getBreite(), ( int )t.getHoehe(), 10, 10 );

    g2.setPaint( Color.black );
    g2.drawString( "Auswahl", 2, 280 );
    g2.drawString( "durch", 2, 295 );
    g2.drawString( "doppelklick", 2, 310 );
    //g2.drawString( "bzw.", 2, 310 );
    //g2.drawString( "rechtsklick", 2, 320 );
    g2.drawString( "auf das", 2, 325 );
    g2.drawString( "jeweilige", 2, 340 );
    g2.drawString( "Icon", 2, 355 );

  }
}