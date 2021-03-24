package trOptimizer;

/**
 * <p>Title: SymbolPane</p>
 * <p>Description: Die Klasse SymbolPane ist die rechte Symbol-Fl�che. �ber sie k�nnen durch doppelklick auf das jeweilige Icon neue Objekte erzeugt werden.</p>
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

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SymbolPane extends JPanel {

	// Klassenattribute
	private Graphics2D g2 = null;
	private Image pic1, pic2, pic3;
	int x;
	int y;
	int width;
	String s;

	/**
	 * Standardkonstruktor
	 *
	 */
	public SymbolPane() {

	}

	/**
	 * Die Methode paint ist eine Java-Standard-Methode und wird hier mit den
	 * gewuenschten Eigenschaften ueberschrieben. Die Methode wird vom
	 * Betriebssystem automatisch aufgerufen bzw. kann durch update bzw. repaint
	 * auch explizit aufgerufen werden.
	 *
	 * @param g
	 *            ein Graphics-Objekt
	 */
	public void paint(Graphics g) {
		g2 = (Graphics2D) g;
		g2.setPaint(Color.white);
		g2.fill3DRect(0, 2, 70, 1000, true);

		/*
		 * g2.setPaint( Color.black ); g2.drawString( "Empf�nger", 3, 30 );
		 * g2.setPaint( Color.red ); Empfaenger e = new Empfaenger( 20, 45, 0 );
		 * g2.fill3DRect( ( int )e.getX(), ( int )e.getY(), ( int
		 * )e.getBreite(), ( int )e.getHoehe(), true );
		 */

		Image pic1 = Toolkit.getDefaultToolkit().getImage("kunde.jpg");
		g2.setPaint(Color.black);
		Empfaenger e = new Empfaenger(26, 48, 0);
		g2.drawString("Empfaenger", 6, 30);
		g2.drawImage(pic1, (int) e.getX(), (int) e.getY(), null);

		/*
		 * g2.setPaint( Color.black ); g2.drawString( "Produzent", 3, 120 );
		 * Produzent produzent = new Produzent( 20, 135, 0 ); GradientPaint
		 * bluetodarkGray = new GradientPaint( ( float )produzent.getX(), (
		 * float )produzent.getY(), Color.blue, ( float )produzent.getX() + (
		 * float )produzent.getBreite(), ( float )produzent.getY(),
		 * Color.darkGray ); g2.setPaint( bluetodarkGray ); g2.fill( produzent
		 * );
		 */

		Image pic2 = Toolkit.getDefaultToolkit().getImage("produzent.jpg");
		g2.setPaint(Color.black);
		Produzent produzent = new Produzent(26, 138, 0);
		g2.drawString("Produzent", 11, 120);
		g2.drawImage(pic2, (int) produzent.getX(), (int) produzent.getY(), null);

		/*
		 * g2.setPaint( Color.black ); g2.drawString( "Transporter", 1, 210 );
		 * Transporter t = new Transporter( 17, 225, 0 ); GradientPaint
		 * greenToDarkGray = new GradientPaint( ( float )t.getX(), ( float
		 * )t.getY(), Color.lightGray, ( float )t.getX() + ( float
		 * )t.getBreite(), ( float )t.getY(), Color.darkGray ); g2.setPaint(
		 * greenToDarkGray ); g2.fillRoundRect( ( int )t.getX(), ( int
		 * )t.getY(), ( int )t.getBreite(), ( int )t.getHoehe(), 10, 10 );
		 */

		Image pic3 = Toolkit.getDefaultToolkit().getImage("lkw.jpg");
		g2.setPaint(Color.black);
		Transporter t = new Transporter(26, 228, 0);
		g2.drawString("Transporter", 6, 210);
		g2.drawImage(pic3, (int) t.getX(), (int) t.getY(), null);

		g2.setPaint(Color.black);
		g2.drawString("Auswahl", 14, 310);
		g2.drawString("durch", 22, 325);
		g2.drawString("Klicken", 19, 340);
		g2.drawString("auf das", 17, 355);
		g2.drawString("jeweilige", 15, 370);
		g2.drawString("Icon", 24, 385);

	}

}