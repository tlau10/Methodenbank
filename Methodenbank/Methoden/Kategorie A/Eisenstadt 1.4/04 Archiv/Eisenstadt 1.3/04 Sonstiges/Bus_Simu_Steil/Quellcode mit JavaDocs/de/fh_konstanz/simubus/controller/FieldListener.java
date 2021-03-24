package de.fh_konstanz.simubus.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.view.ControlPanel;
import de.fh_konstanz.simubus.view.Field;
import de.fh_konstanz.simubus.view.Editor;

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
public class FieldListener extends MouseAdapter {

	public final static int AUSWAHL = 1;
	public final static int STRASSE = 2;
	public final static int HALTESTELLE = 3;
	public final static int ZIEL = 4;
	
	private static FieldListener instance;
	private static int toPaint = AUSWAHL;
	
	private FieldListener() {
	}
	
	public static FieldListener getInstance() {
		if (instance == null)
			instance = new FieldListener();
		
		return instance;
	}
	
	public static void setEvent(int toPaint_) {
		toPaint = toPaint_;
	}
	
	public void mouseEntered(MouseEvent evt) {
		Field actual = (Field) evt.getSource();
		Editor editor = Editor.getInstance();
		editor.displayActualPosition(actual.getKoordinaten());
	}
	
	public void mouseClicked(MouseEvent evt) {

		Field actual = (Field) evt.getSource();
		ControlPanel panel = ControlPanel.getInstance();
		Editor editor = Editor.getInstance();
		
		if (toPaint == AUSWAHL) {
			if (actual.hasHaltestelle()) {
				panel = ControlPanel.getInstance();
				panel.setSelectedHaltestelle(actual.getHaltestelle());
				panel.markHaltestelleOnField();
			}
			else {
				editor.unmarkField();
			}
		}
		else if (toPaint == HALTESTELLE) {
			Strassennetz netz = Strassennetz.getInstance();
			Haltestelle h;
			
			if (actual.hasHaltestelle()) {
				h = actual.getHaltestelle();
				netz.removeHaltestelle(h);
				
				for (Buslinie linie : Gesamtfahrplan.getInstance()
						.getBuslinien()) {
					linie.removeHaltestelle(h);
				}
				panel.updateBushaltestellenList();
				editor.deleteMarkedField(actual);
				actual.setStreet();
			}
			else if (actual.isStreet) {
				h = new Haltestelle(actual.getKoordinaten().x, actual.getKoordinaten().y);
				h.setName("Haltestelle ["+actual.getKoordinaten().x+","+actual.getKoordinaten().y+"]");
				netz.addHaltestelle(h);

				actual.setHaltestelle(h);
			}
						
			panel.setList();
		}
		else if (toPaint == STRASSE) {
			Strassennetz netz = Strassennetz.getInstance();
			
			if (actual.isStreet) {
				if (actual.hasHaltestelle()) {
					
					netz.removeHaltestelle(actual.getHaltestelle());
					panel.setList();
				}
				netz.removeStrasse(actual.getKoordinaten());
				netz.removeStrasse(actual);
				actual.setField();
			}
			else if (!actual.isZiel){
				netz.addStrasse(actual.getKoordinaten());
				netz.addStrasse(actual);
				actual.setStreet();
			}
		}
		else if (toPaint == ZIEL) {
			Strassennetz netz = Strassennetz.getInstance();
			
			if (actual.isZiel) {
				netz.removeZiel(actual.getZiel());
				actual.setField();
			}
			else {
				if (!actual.isStreet) {
					Ziel neu = new Ziel(actual.getKoordinaten());
					netz.addZiel(neu);
					actual.setZiel(neu);
				}
			}
		}
		
	}
}
