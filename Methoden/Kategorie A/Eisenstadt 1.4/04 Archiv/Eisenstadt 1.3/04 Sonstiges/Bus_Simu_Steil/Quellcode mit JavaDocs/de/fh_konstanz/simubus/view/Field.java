package de.fh_konstanz.simubus.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Ziel;

/*
 * Created on 18.04.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Robert Audersetz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Field extends JPanel{
	
	private final Color streetColor = new Color(140, 145, 150, 175);
	private final Color haltestelleColor = new Color(70, 120, 160, 200);
	private final Color zielColor = new Color(250, 185, 65, 175);
	private final Color fieldColor = new Color(200, 205, 210, 155);
	private final Color highlightColor = new Color(15, 195, 215, 255);
	private final Color highlightZielColor = Color.RED;
	
	private final Font labelFont = new Font("Arial", Font.BOLD, 8);
	
	private JLabel label;
	public int f;
	public int g;
	public int h;
	public Field father;
	
//	public int rotation;
	
	public int x;
	public int y;
	public boolean isStreet = false;
	public boolean isStart = false;
	public boolean isZiel = false;
	private Haltestelle haltestelle;
	private Ziel ziel;
	private Point koordinaten;
	
	public Field(int x, int y) {
	
		this.x = x;
		this.y = y;
		koordinaten = new Point(x, y);
		this.setLayout(null);
		this.setBackground(fieldColor);
		
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(labelFont);
		this.setOpaque(false);
	}


	public void setValues(int f, int g, int h) {
		this.f = f;
		this.g = g;
		this.h = h;
	}
	
	public Haltestelle getHaltestelle() {
		return haltestelle;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void setStreet() {
		label.setText("");		
		this.remove(label);
		this.setBackground(streetColor);
		isStreet = true;
		isStart = false;
		isZiel = false;
		this.ziel = null;
		this.haltestelle = null;
	}

	public void setField() {
		label.setText("");
		this.remove(label);
		this.setBackground(fieldColor);
		isStreet = false;
		isStart = false;
		isZiel = false;
		this.haltestelle = null;
	}

	public void setStart(Color color) {
		this.setBackground(color);
		isStart = true;
		addLabel("S");
	}
	
	public void setZiel(Ziel ziel) {
		this.setBackground(zielColor);
		label.setForeground(Color.BLACK);
		isZiel = true;
		this.ziel = ziel;
		addLabel("Z");
	}

	public void setHaltestelle(Haltestelle haltestelle) {
		if (!isStreet) {
			isStreet = true;
			Strassennetz.getInstance().addStrasse(this);
		}
		this.setBackground(haltestelleColor);
		label.setForeground(Color.WHITE);
		this.haltestelle = haltestelle;
		addLabel("H");
	}

	public boolean hasHaltestelle() {
		if (haltestelle == null)
			return false;
		
		return true;
	}
	
	private void addLabel(String text) {
		label.setBounds(0, (this.getHeight()-8)/2, this.getWidth(), 10);
		label.setText(text);
		this.add(label);
	}
	
	public void highlightHaltestelle(boolean active) {
		if (active) {
			this.setBackground(highlightColor);
			label.setForeground(Color.YELLOW);
		}
		else {
			this.setBackground(haltestelleColor);
			label.setForeground(Color.WHITE);
		}
	}

	
	public void highlightZiel(boolean active) {
		if (active) {
			this.setBackground(highlightZielColor);
			label.setForeground(Color.WHITE);
		}
		else {
			this.setBackground(zielColor);
			label.setForeground(Color.BLACK);
		}
	}

	
	public void displayPath(Color color) {
		setBackground(color);
		Graphics x = this.getGraphics();
	}


	/**
	 * @return Returns the koordinaten.
	 */
	public Point getKoordinaten() {
		this.repaint();
		return koordinaten;
	}

	public Ziel getZiel() {
		return ziel;
	}
	
	/**
	 * @param koordinaten The koordinaten to set.
	 */
	public void setKoordinaten(Point koordinaten) {
		this.repaint();
		this.koordinaten = koordinaten;
	}
	
	/**
	 * Berechnet die Diagonaldistanz zum angegebenen <code>Point</code>.
	 * 
	 * @param punkt
	 * @return diagonaldistanz in Planquadraten
	 */
	public double diagonalDistanz(Point punkt) {
		int xDist = Math.abs(koordinaten.x - punkt.x);
		int yDist = Math.abs(koordinaten.y - punkt.y);

		int kleinereDist;
		int groessereDist;

		if (xDist <= yDist) {
			kleinereDist = xDist;
			groessereDist = yDist;
		} else {
			kleinereDist = yDist;
			groessereDist = xDist;
		}

		return kleinereDist * Math.sqrt(2) + (groessereDist - kleinereDist);
	}
}