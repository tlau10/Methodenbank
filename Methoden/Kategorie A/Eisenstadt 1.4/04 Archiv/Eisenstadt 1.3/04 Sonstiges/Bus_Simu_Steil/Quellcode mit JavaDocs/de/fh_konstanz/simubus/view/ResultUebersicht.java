package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import de.fh_konstanz.simubus.controller.ListBoxActionListener;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.model.optimierung.Adapter;
import de.fh_konstanz.simubus.model.optimierung.Busstop;
import de.fh_konstanz.simubus.model.optimierung.Result;

public class ResultUebersicht extends JFrame {
	private Editor editor;
	private ControlPanel panel;
	private Strassennetz netz;
	private SimuKonfiguration config;
	
	private Adapter optim;
	
	private JPanel prepare;
	private JLabel restgeh;
	private JLabel lMinAnzahlHaltestellen;
	private JComboBox cMinAnzahlHaltestellen;
	private JLabel lGehzeit;
	private JComboBox cGehzeit;
	private JButton starten;
	
	private JPanel main;
	
	private JLabel results;
	private JList lResults;
	private JLabel lDaten;
	private JTextField tName;
	private JButton zurueck;
	
	public ResultUebersicht() {
		super ("Ermitteln der optimalen Haltestellen");
		
		getContentPane().setLayout(null);		
		
		prepare = new JPanel();
		prepare.setLayout(null);
		prepare.setMinimumSize(new Dimension(600, 600));
		prepare.setPreferredSize(new Dimension(600, 600));
		prepare.setBounds(0, 0, 600, 600);

		lMinAnzahlHaltestellen = new JLabel("Minimale Anzahl an Haltestellen");
		lMinAnzahlHaltestellen.setBounds(125, 25, 190, 20);
		lMinAnzahlHaltestellen.setToolTipText("Minimale Anzahl an Haltestellen");
		prepare.add(lMinAnzahlHaltestellen);

		cMinAnzahlHaltestellen = new JComboBox();
		for (int i=1; i<4; i++)
			cMinAnzahlHaltestellen.addItem(i);
		
		cMinAnzahlHaltestellen.setBounds(125, 50, 55, 22);
		cMinAnzahlHaltestellen.setToolTipText("Minimale Anzahl an Haltestellen aus der Optimierung");
		prepare.add(cMinAnzahlHaltestellen);
		
		
		lGehzeit = new JLabel("Maximale Restgehzeit in Minuten");
		lGehzeit.setBounds(350, 25, 150, 20);
		lGehzeit.setToolTipText("Maximale Restgehzeit in Minuten");
		prepare.add(lGehzeit);

		cGehzeit = new JComboBox();
		for (int i=1; i<26; i++)
			cGehzeit.addItem(i);
		
		cGehzeit.setBounds(350, 50, 55, 22);
		cGehzeit.setToolTipText("Maximale Restgehzeit in Minuten");
		prepare.add(cGehzeit);
		
		starten = new JButton("Optimierung starten");
		starten.setName("starten");
		starten.setBounds(200, 110, 150, 24);
		starten.addMouseListener(new ORButtonListener());
		prepare.add(starten);
		

		main = new JPanel();
		main.setLayout(null);
		main.setMinimumSize(new Dimension(600, 600));
		main.setPreferredSize(new Dimension(600, 600));
		main.setBounds(0, 0, 600, 600);
		
		results = new JLabel("Ergebnisse");
		results.setBounds(30, 15, 150, 16);
		main.add(results);

		lResults = new JList();
		lResults.setName("ergebnisse");
		lResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lResults.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		lResults.setBounds(30, 35, 190, 470);
		lResults.addMouseListener(new ListBoxActionListener(this));
		main.add(lResults);
		
		zurueck = new JButton("Zurück");
		zurueck.setName("zurueck");
		zurueck.setBounds(35, 530, 100, 24);
		zurueck.addMouseListener(new ORButtonListener());
		main.add(zurueck);

		lDaten = new JLabel();
		lDaten.setVerticalAlignment(JLabel.TOP);
		lDaten.setBounds(245, 15, 350, 580);
		main.add(lDaten);

		getContentPane().add(prepare);
	}
	
	private void changeView(boolean showresult) {
		if (showresult) {
			boolean successful;
			
			successful = startOptimierung();
			if (successful) {
				showResult();
				showInfo();
	
				getContentPane().remove(prepare);			
				getContentPane().add(main);
			}
		}
		else {
			getContentPane().remove(main);
			getContentPane().add(prepare);			
		}
		getContentPane().repaint();
		this.repaint();		
	}
	
	public void showResult() {

		Vector<Result> vec = new Vector<Result>();
		double summeLaufzeit=0;
		int i=0;
		Result res = null;
		while (optim.hasNextResult()) {
			res = optim.getNextResult();
			summeLaufzeit = 0;
			
			while(res.hasNextBusstop()) {
				Busstop aBusstop = res.getNextBusstop();
				summeLaufzeit += aBusstop.getRunTime();
	        }
			//if ( summeLaufzeit != 0.0) {
				res.setSummeLaufzeit(summeLaufzeit);
				vec.add(res);
			//}
		}
		
		
		// Liste wird sortiert
		for (int x=0; x<vec.size()-1; x++) {
			for (int y=x+1; y<vec.size(); y++) {
				if (vec.elementAt(y).laufzeitKleinerAls(vec.elementAt(x))) {
					Result temp = vec.elementAt(x);
					vec.set(x, vec.elementAt(y));
					vec.set(y, temp);
				}
			}
		}
		// Maximal 20 Ergebnisse
		if (vec.size() > 20) {
			Vector<Result> temp = new Vector<Result>(10);
			for (int h=0; h<20; h++) {
				temp.add(vec.elementAt(h));
			}
			vec = temp;
		}

		if (vec.size() > 0) {
			lResults.setListData(vec);
			lResults.setSelectedIndex(0);
			updateEditor();
		}
		else
			lDaten.setText("Keine sinnvollen Lösungen gefunden!");
	}
	
	public void showInfo() {
		
		if (lResults.getMaxSelectionIndex() > -1) {
			Result aResult = (Result) lResults.getSelectedValue();
			double summeLaufzeit=0,summeKapazitaet=0,gesamtZielPassagiere=0;
			String text = "";
			
			while(aResult.hasNextBusstop())
	        {
	           Busstop aBusstop = aResult.getNextBusstop();
			   Point p = aBusstop.getCoordinate();
			   
			   text += "<html><b>Haltestelle "+(1+aBusstop.getValue()) +" [" +p.x +"," +p.y +"]<br>"
	           		+ "<b>Max.Kapazitaet/Tag: </b>" +(aBusstop.getPassenger()) +"<br>"
	           		+ "<b>Passagiere*Zeit: </b>" +(aBusstop.getRunTime())+"<br><br>";
			   
			   summeLaufzeit += aBusstop.getRunTime();
	           summeKapazitaet += aBusstop.getPassenger();
	        }
	
			text += "<b>Alle Ziele erreicht: </b>" +aResult.reachedAllTargets() +"<br>"
	        			+ "<b>Summe Max.Kapazitaet: </b>" +String.valueOf(summeKapazitaet) +"<br>"
	        			+ "<b>Passagiere, die in den Ring wollen: </b>" +String.valueOf(gesamtZielPassagiere) +" (" +String.valueOf(summeKapazitaet-gesamtZielPassagiere) +")<br>"
	        			+ "<b>Summe Laufzeit: </b>" +String.valueOf(summeLaufzeit);
			
			lDaten.setText(text);
		}
	}
	
	public void updateEditor() {
		netz = Strassennetz.getInstance();
		panel = ControlPanel.getInstance();
		editor = Editor.getInstance();
		Gesamtfahrplan plan = Gesamtfahrplan.getInstance();
		
		Result res = (Result) lResults.getSelectedValue();
		
		if (res != null) {
			plan.removeHaltestellenAusBuslinien();
			netz.resetHaltestellen();
			
			while (res.hasNextBusstop()) {
				Busstop bs = res.getNextBusstop();
				Point p = bs.getCoordinate();
				netz.addHaltestelle(new Haltestelle(p.x, p.y, "Haltestelle " +(1+bs.getValue()) +" [" +p.x +"," +p.y +"]"));
			}
			
			editor.updateEditorAfterOR();
			panel.updateHaltestellenListen();
		}
	}

	
	
	// Ermittelt die optimale Anzahl und Positionen der Haltestellen
	private boolean startOptimierung() {
		try {
			netz = Strassennetz.getInstance();
			config = SimuKonfiguration.getInstance();
			optim = new Adapter();
			
			ArrayList<Ziel> ziele = netz.getZiele();
			ArrayList<Field> strassen = netz.getStrassenListe();
			Gesamtfahrplan plan = Gesamtfahrplan.getInstance(); 
			
			Ziel ziel;
			Field strasse;
			int anzahlPassZiel = 0;
			double distStrasseZiel;
			
			for (int i=0; i<ziele.size(); i++) {
				ziel = ziele.get(i);
				anzahlPassZiel = plan.getGesamtanzahlPassagiereZiel(ziel);
				
				for (int j=0; j<strassen.size(); j++) {
					strasse = strassen.get(j);
					distStrasseZiel = strasse.diagonalDistanz(ziel.getZiel()) * config.getFeldElementGroesseInM();
					optim.addTimeRelation(ziel.getZiel(), anzahlPassZiel, strasse.getKoordinaten(), distStrasseZiel/config.getGehGeschwindigkeitInMSec());
				}
			}
	
			optim.estimateOptimum((Integer) cGehzeit.getSelectedItem(), (Integer) cMinAnzahlHaltestellen.getSelectedItem());
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Optimierung der Haltestellen nicht möglich! Überprüfen Sie bitte die Einstellungen von jeder Buslinie.", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
		  return false;
		}
		
		return true;
	}
	

	private class ORButtonListener extends MouseAdapter{
		public void mouseClicked(MouseEvent evt) {
			String name = ((JButton) evt.getSource()).getName();
			
			if (name.equals("starten"))
				changeView(true);
			else if (name.equals("zurueck"))
				changeView(false);
		}
	}
}
