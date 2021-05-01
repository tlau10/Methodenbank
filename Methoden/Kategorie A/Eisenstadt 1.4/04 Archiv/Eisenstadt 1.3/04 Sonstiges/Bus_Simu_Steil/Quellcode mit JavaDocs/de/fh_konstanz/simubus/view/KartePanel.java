package de.fh_konstanz.simubus.view;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class KartePanel extends JPanel{

	private MapCreator map;
	private int sizePanel;
	
	public KartePanel(int sizePanel) {
		this.map = MapCreator.getInstance();
		this.map.updateMap();
		this.sizePanel = sizePanel;

		this.setLayout(null);
		this.setMinimumSize(new Dimension(this.sizePanel, this.sizePanel));
		this.setPreferredSize(new Dimension(this.sizePanel, this.sizePanel));	
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(map.getMap(), 0, 0, this.sizePanel, this.sizePanel, this);
	}

	public void setSizePanel(int sizePanel) {
		this.sizePanel = sizePanel;
	}
}
