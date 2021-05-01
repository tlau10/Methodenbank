package de.fh_konstanz.simubus.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import de.fh_konstanz.simubus.model.SimuKonfiguration;

public class MapCreator {
	private SimuKonfiguration config;
	private static MapCreator instance;
	private Image image;
	
	private final String standardMap = "gras.jpg";
	private String imageLocation;
	
	private MapCreator() {
	}
	
	public static MapCreator getInstance() {
		if (instance == null)
			instance = new MapCreator();
		
		return instance;
	}
	
	private void createImage() {
		config = SimuKonfiguration.getInstance();
		this.imageLocation = config.getMapLocation();
		
		try {
			image = ImageIO.read(new File(imageLocation));
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Bilddatei konnte nicht geladen werden! Standard wird verwendet. \nError: " +e.getMessage(), "Simulation", JOptionPane.ERROR_MESSAGE);
			
			try {
				imageLocation = standardMap;
				image = ImageIO.read(new File(imageLocation));
			}
			catch (IOException e1) {}
		}	
	}
	
	public void updateMap() {
		config = SimuKonfiguration.getInstance();
		this.imageLocation = config.getMapLocation();
		createImage();
	}
	
	public Image getMap() {
		
		if (image == null)
			createImage();
		
		return image;
	}
	
	public boolean isStandardMap() {
		if (standardMap.equals(imageLocation))
				return true;
		
		return false;			
	}
}
