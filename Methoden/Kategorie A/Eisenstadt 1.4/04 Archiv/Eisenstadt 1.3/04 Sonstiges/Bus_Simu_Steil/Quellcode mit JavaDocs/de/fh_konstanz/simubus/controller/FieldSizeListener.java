package de.fh_konstanz.simubus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.view.Editor;

public class FieldSizeListener implements ActionListener{
	
	private SimuKonfiguration config;
	
	public FieldSizeListener() {
	}
	
	public void actionPerformed(ActionEvent evt) {
		Editor editor = Editor.getInstance();
		int newSize = Integer.valueOf(((JComboBox) evt.getSource()).getSelectedItem().toString());
		//editor.setSizeOfField(newSize);
		config  = SimuKonfiguration.getInstance();
		config.setFeldElementGroesse(newSize);
		editor.paintFields();
		editor.updateMapSize();
	}

}
