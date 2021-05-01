package de.fh_konstanz.simubus.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import de.fh_konstanz.simubus.view.Info;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.view.ControlPanel;
import de.fh_konstanz.simubus.view.Editor;
import de.fh_konstanz.simubus.view.MapCreator;
import de.fh_konstanz.simubus.view.SimuEinstellungen;


/**
 * @author Robert Audersetz
 *
 */
public class FileButtonListener extends MouseAdapter{
	
	private Editor main;
	private SimuKonfiguration config;
	private ControlPanel panel;
	
	public void mouseClicked(MouseEvent evt) {
		main = Editor.getInstance();
		config  = SimuKonfiguration.getInstance();
		
		JButton actual = (JButton) evt.getSource(); 
		String actionCmd = actual.getActionCommand();
		
		if (actionCmd.equals("oeffnen")) {
			File selected = getOpenedFile(actionCmd);
			int resolution = config.getActiveResolutionForCombo();
			
			if (selected != null) {			
				try {
					DateiManager.laden(selected);

					config  = SimuKonfiguration.getInstance();	
					config.setActiveResolutionFromCombo(resolution);
					panel = ControlPanel.getInstance();
					panel.updateAfterFileload();
					
					main.updateEditorAfterFileLoad();

				} catch (IOException e1) {
						JOptionPane.showMessageDialog(main, "Fehler beim Laden der Datei!", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
//					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(main, "Fehler beim Laden der Datei!", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
//					e1.printStackTrace();
				}
				
			}
		}
		else if (actionCmd.equals("speichern")) {
			File selected = getOpenedFile(actionCmd);
			if (selected != null) {
				String path = selected.getAbsolutePath();
				if (!path.endsWith(".bus"))
					selected = new File(path +".bus");
				try {
					DateiManager.speichern(selected);
				} catch (IOException e1) {
						JOptionPane.showMessageDialog(main, "Fehler beim Speichern der Datei!", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
//					e1.printStackTrace();
				}
			}
		}
		else if (actionCmd.equals("map")) {
			File selected = getOpenedFile(actionCmd); 
			if (selected != null) {
				MapCreator map = MapCreator.getInstance();
				config  = SimuKonfiguration.getInstance();
				config.setMapLocation(selected.getAbsolutePath());
				map.updateMap();
				main.paintFields();
			}
		}
		else if (actionCmd.equals("einstellungen")) {
			SimuEinstellungen frame = new SimuEinstellungen();
			frame.setSize(config.getEinstellungenFrameDimension());
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);
			frame.setVisible(true);		
		}
		else if (actionCmd.equals("info")) {
			JFrame info = new Info();
			info.setLocation(225, 225);
			info.setSize(615, 360);
			info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			info.setResizable(false);
			info.setVisible(true);
		}
	}

	
	public File getOpenedFile(String type) {
		JFileChooser chooser = new JFileChooser();
		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
		
		// Show open dialog; this method does not return until the dialog is
		// closed
		if (type.equals("oeffnen")) {
			chooser.addChoosableFileFilter(new FHFileFilter("bus", "Bus Simulation"));	
			chooser.showOpenDialog(main);
		}
		else if (type.equals("map")) {	
			chooser.addChoosableFileFilter(new FHFileFilter("gif", "CompuServe GIF"));
			chooser.addChoosableFileFilter(new FHFileFilter("bmp", "BMP"));
			chooser.addChoosableFileFilter(new FHFileFilter("tif", "TIFF"));
			chooser.addChoosableFileFilter(new FHFileFilter("png", "PNG"));
			chooser.addChoosableFileFilter(new FHFileFilter("jpg", "JPEG"));
			chooser.showOpenDialog(main);
		}
		else {
			chooser.addChoosableFileFilter(new FHFileFilter("bus", "Bus Simulation"));
			chooser.showSaveDialog(main);
		}
		
		return chooser.getSelectedFile();
	}

	
	public FileButtonListener() {
	}

	/**
	 * @author Robert Audersetz Definiert die Dateiendung und schränkt somit die
	 *         Dateiansicht ein.
	 */
	class FHFileFilter extends FileFilter {

		private String extension;

		private String description;

		public FHFileFilter(String extension, String description) {

			this.description = description;
			this.extension = "." + extension;
		}

		public String getDescription() {
			return description;
		}

		public boolean accept(File file) {
			if (file == null)
				return false;

			if (file.isDirectory())
				return true;

			return file.getName().toLowerCase().endsWith(extension);
		}
	}
}
