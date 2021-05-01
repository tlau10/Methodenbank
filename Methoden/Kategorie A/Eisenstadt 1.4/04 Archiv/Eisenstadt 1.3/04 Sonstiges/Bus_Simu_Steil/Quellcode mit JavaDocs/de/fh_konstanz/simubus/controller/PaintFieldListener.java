package de.fh_konstanz.simubus.controller;
import java.awt.Checkbox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


/*
 * Created on 05.05.2005
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
public class PaintFieldListener implements ItemListener{
	
	
	public void itemStateChanged(ItemEvent evt) {

		String sourceName = ((Checkbox) evt.getSource()).getName();
		if (sourceName.equals("auswahl"))
			FieldListener.setEvent(FieldListener.AUSWAHL);
		else if (sourceName.equals("paintHaltestelle"))
			FieldListener.setEvent(FieldListener.HALTESTELLE);
		else if (sourceName.equals("paintStrasse"))
			FieldListener.setEvent(FieldListener.STRASSE);
		else if (sourceName.equals("paintZiel"))
			FieldListener.setEvent(FieldListener.ZIEL);
	}

	public PaintFieldListener() {
	}

}
