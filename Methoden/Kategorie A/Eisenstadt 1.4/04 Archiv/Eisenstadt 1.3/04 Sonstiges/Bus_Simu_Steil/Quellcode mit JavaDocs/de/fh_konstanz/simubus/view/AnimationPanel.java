/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Statistik;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.statistik.Haltestellenstatistik;
import de.fh_konstanz.simubus.model.statistik.Linienstatistik;

/**
 * @author Robert Audersetz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AnimationPanel extends JPanel{
	
	private final Font fLabel = new Font("Arial", Font.BOLD, 12);
	
	private JLabel label;
	private JButton startButton;
	private JButton orButton;
	private JList buslinien;
	
	private JList haltestellen;

	private JTable tableBuslinie;
	private JTable tableHaltestelle;
	private JScrollPane sp;
	
	private	Vector<Vector> vec;
	private	Vector<String> colBuslinie;
	private	Vector<String> colHaltestelle;
	
	private AnimationView editor;
	private Strassennetz netz;
	private Gesamtfahrplan plan;
	private SimuKonfiguration config;
	
	private static AnimationPanel p;
	
	public static AnimationPanel getInstance() {
		return p;
	}
	
	public void initialize() {
		config = SimuKonfiguration.getInstance();
		netz = Strassennetz.getInstance();
		plan = Gesamtfahrplan.getInstance();
		vec = new Vector<Vector>();
		colBuslinie = new Vector<String>(4);
		colBuslinie.add("Streckenabschnitt ab");
		colBuslinie.add("Durchschnitt");
		colBuslinie.add("Max");
		colBuslinie.add("Min");

		colHaltestelle = new Vector<String>(3);
		colHaltestelle.add("Haltestelle");
		colHaltestelle.add("Busse");
		colHaltestelle.add("Wartende Pass.");
		colHaltestelle.add("Wartezeit");
	}
	
	public AnimationPanel(AnimationView editor) {
		this.editor = editor;
		p = this;
		
		initialize();
		
		// Buslinien
			
		label = new JLabel("Buslinien");
		label.setFont(fLabel);
		label.setBounds(30, 20, 100, 20);
		this.add(label);

		buslinien = new JList(plan.getBuslinien().toArray());
		buslinien.setName("buslinien");
		buslinien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		buslinien.addMouseListener(new ListBoxListener());
		if (buslinien.getModel().getSize() > 0)
			buslinien.setSelectedIndex(0);
		
		JScrollPane spb = new JScrollPane(buslinien); 
		spb.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		spb.setBounds(30, 45, 145, 200+config.getResBoxAddition());		
		this.add(spb);
		

		// Alle Haltestellen
		
		label = new JLabel("Haltestellen");
		label.setFont(fLabel);
		label.setBounds(200, 20, 100, 20);
		this.add(label);
		
		haltestellen = new JList(new String[] {"Buslinienhaltestellen", "Alle Haltestellen"});
		haltestellen.setName("haltestellen");
		haltestellen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		haltestellen.addMouseListener(new ListBoxListener());

		JScrollPane sph = new JScrollPane(haltestellen); 
		sph.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		sph.setBounds(200, 45, 155, 100);		
		this.add(sph);
	
		showLinienstatistik();
	}
	

	public void showLinienstatistik() {
		Buslinie linie = (Buslinie) buslinien.getSelectedValue();
		Linienstatistik ls = Statistik.getInstance().getLinienstatistik(linie);
		vec.removeAllElements();
		Vector<String> row;
		
		if (sp != null)
			this.remove(sp);

		for (Haltestelle hs : linie.getHaltestellen()) {
			row = new Vector<String>();
			
			row.add(hs.toString());
			row.add(String.valueOf(ls.getDurschnittPassagiereAufStreckenabschnitt(hs)));
			row.add(String.valueOf(ls.getMaxPassagiereAufStreckenabschnitt(hs)));
			row.add(String.valueOf(ls.getMinPassagiereAufStreckenabschnitt(hs)));
			
			vec.add(row);
		}
		
		tableBuslinie = new JTable(vec, colBuslinie);
		tableBuslinie.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		tableBuslinie.getColumnModel().getColumn(0).setMinWidth(135);
		tableBuslinie.getColumnModel().getColumn(1).setMinWidth(100);
		sp = new JScrollPane(tableBuslinie);
		sp.setBounds(15, 275+config.getResBoxAddition(), 345, 355+config.getResBoxAddition());
		this.add(sp);
	}
	
	
	public void showHaltestellenstatistik() {
		
		vec.removeAllElements();
		
		if (sp != null)
			this.remove(sp);
		
		Vector<String> row;
		
		if (haltestellen.getSelectedIndex() == 0) {
			for (Haltestelle hs : ((Buslinie) buslinien.getSelectedValue()).getHaltestellen()){
				row = new Vector<String>();
				Haltestellenstatistik hss = Statistik.getInstance().getHaltestellenstatistik(hs);
				
				row.add(hs.toString());
				row.add(String.valueOf(hss.getBusZaehler()));
				row.add(String.valueOf(hss.getDurchschnittlichWartendePassagiere()));
				row.add(String.valueOf(hss.getDurschnittlicheWartezeit()));
				
				vec.add(row);
			}
		}
		else if (haltestellen.getSelectedIndex() == 1) {
			for (Haltestelle hs : Strassennetz.getInstance().getAlleHaltestellen()){
				row = new Vector<String>();
				Haltestellenstatistik hss = Statistik.getInstance().getHaltestellenstatistik(hs);
				
				row.add(hs.toString());
				row.add(String.valueOf(hss.getBusZaehler()));
				row.add(String.valueOf(hss.getDurchschnittlichWartendePassagiere()));
				row.add(String.valueOf(hss.getDurschnittlicheWartezeit()));
				
				vec.add(row);
			}
		}
		
		tableHaltestelle = new JTable(vec, colHaltestelle);
		tableHaltestelle.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		tableHaltestelle.getColumnModel().getColumn(0).setMinWidth(120);
		tableHaltestelle.getColumnModel().getColumn(1).setMaxWidth(50);
		tableHaltestelle.getColumnModel().getColumn(3).setMaxWidth(60);
		sp = new JScrollPane(tableHaltestelle);
		sp.setBounds(15, 275+config.getResBoxAddition(), 345, 355+config.getResBoxAddition());
		this.add(sp);
	}
	
	
	private class ListBoxListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent evt) {	

			JList list = (JList) evt.getSource();
			String listName = list.getName();		
			
			// Liste mit allen Buslinien
			if (listName.equals("buslinien")) {
				showLinienstatistik();
				haltestellen.clearSelection();
			}
			else if (listName.equals("haltestellen")) {
				showHaltestellenstatistik();
			} 
		}
	}
}
