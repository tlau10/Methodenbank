package de.fh_konstanz.simubus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.view.View;

/**
 * Die Klasse <code>JToggleButtonListener</code> reagiert auf Ereignisse, 
 * wenn ein JToggleButton aus der Quickleiste gew�hlt wurde.
 * 
 * @author Michael Litera
 * @version 1.0 (01.06.2006)
 *
 */
public class JToggleButtonListener implements ActionListener
{
	public void actionPerformed(ActionEvent event) {
		View view = View.getInstance();
		Logger.getInstance().log("JToggleButtonLister");
		
		
		if(event.getActionCommand().equals("Haltestelle einf�gen")){
			 view.ChangeJToggleButtons(event);
		}
		else if(event.getActionCommand().equals("Ziele einf�gen")){
			view.ChangeJToggleButtons(event);
		}
		else if(event.getActionCommand().equals("Strasse einf�gen")){
			view.ChangeJToggleButtons(event);
		}
		else if(event.getActionCommand().equals("gesperrte Strasse einf�gen")){
			view.ChangeJToggleButtons(event);
		}
		else if(event.getActionCommand().equals("gesperrte Haltestelle einf�gen")){
			view.ChangeJToggleButtons(event);
		}
		else if(event.getActionCommand().equals("loeschen")){
			view.ChangeJToggleButtons(event);
		}
	}
}
