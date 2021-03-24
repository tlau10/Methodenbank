/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.fh_konstanz.simubus.view;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import de.fh_konstanz.simubus.controller.ButtonListener;
import de.fh_konstanz.simubus.controller.FieldSizeListener;
import de.fh_konstanz.simubus.controller.FileButtonListener;
import de.fh_konstanz.simubus.controller.ListBoxActionListener;
import de.fh_konstanz.simubus.controller.PaintFieldListener;
import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;

/**
 * @author Robert Audersetz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlPanel extends JPanel{
	
	private final Font fLabel = new Font("Arial", Font.BOLD, 12);
	
	private JButton dateiOeffnen;
	private JButton dateiSpeichern;
	private JButton mapOeffnen;
	private JButton einstellungen;
	private JButton info;
	
	private JLabel label;
	private JButton startButton;
	private JButton orButton;
	private JList buslinien;
	private CheckboxGroup cGroup;
	private Checkbox cAuswahl;
	private Checkbox cHaltestelle;
	private Checkbox cStrasse;
	private Checkbox cZiel;
	
	private JList haltestellen;
	private JLabel lBushaltestelle;
	private JList busHaltestellen;
	
	private JButton addBuslinie;
	private JButton removeBuslinie;
	
	private JButton addBusHaltestelle;
	private JButton removeBusHaltestelle;

//	private JButton moveUpBusHaltestelle;
//	private JButton moveDownBusHaltestelle;
	
	private JLabel lSizeField;
	private JComboBox cSizeField;
	private JLabel lRealSizeField;
	private JTextField tRealSizeField;
	
	private Editor editor;
	private Strassennetz netz;
	private Gesamtfahrplan plan;
	private SimuKonfiguration config;
	
	private static ControlPanel p;
	
	public static ControlPanel getInstance() {
		return p;
	}
	
	public void initialize() {
		config = SimuKonfiguration.getInstance();
		netz = Strassennetz.getInstance();
		plan = Gesamtfahrplan.getInstance();
	}
	
	public ControlPanel(Editor editor) {
		this.editor = editor;
		p = this;
		
		initialize();
		
		// Buttons		
		dateiOeffnen = new JButton();
		dateiOeffnen.setIcon(new ImageIcon("open.png"));
		dateiOeffnen.setActionCommand("oeffnen");
		dateiOeffnen.addMouseListener(new FileButtonListener());
		dateiOeffnen.setBounds(5, 0, 40, 40);
		dateiOeffnen.setToolTipText("Datei öffnen");
		this.add(dateiOeffnen);
		
		dateiSpeichern = new JButton();
		dateiSpeichern.setIcon(new ImageIcon("close.png"));
		dateiSpeichern.setActionCommand("speichern");
		dateiSpeichern.addMouseListener(new FileButtonListener());
		dateiSpeichern.setBounds(65, 0, 40, 40);
		dateiSpeichern.setToolTipText("Datei speichern");
		this.add(dateiSpeichern);
		
		mapOeffnen = new JButton();
		mapOeffnen.setIcon(new ImageIcon("map.png"));
		mapOeffnen.setActionCommand("map");
		mapOeffnen.addMouseListener(new FileButtonListener());
		mapOeffnen.setBounds(125, 0, 40, 40);
		mapOeffnen.setToolTipText("Stadtplan laden");
		this.add(mapOeffnen);
		
		einstellungen = new JButton();
		einstellungen.setIcon(new ImageIcon("config.png"));
		einstellungen.setActionCommand("einstellungen");
		einstellungen.addMouseListener(new FileButtonListener());
		einstellungen.setBounds(180, 0, 40, 40);
		einstellungen.setToolTipText("Einstellungen");
		this.add(einstellungen);

		info = new JButton();
		info.setIcon(new ImageIcon("iconInfo.png"));
		info.setActionCommand("info");
		info.addMouseListener(new FileButtonListener());
		info.setBounds(235, 0, 40, 40);
		info.setToolTipText("Über Bussimulation");
		this.add(info);
		
		// Buslinien			
		label = new JLabel("Buslinien");
		label.setFont(fLabel);
		label.setBounds(5, 80, 100, 20);
		this.add(label);

		buslinien = new JList(plan.getBuslinien().toArray());
		buslinien.setName("buslinien");
		buslinien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		buslinien.addMouseListener(new ListBoxActionListener(this));
		if (buslinien.getModel().getSize() > 0)
			buslinien.setSelectedIndex(0);

		JScrollPane spb = new JScrollPane(buslinien); 
		spb.setBounds(5, 105, 145, 150+config.getResBoxAddition());
		spb.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		this.add(spb);
		
	
		// Buslinien Buttons
		
		addBuslinie = new JButton("+");
		addBuslinie.setMargin(new Insets(0, 0, 0, 0));
		addBuslinie.setActionCommand("addBuslinie");
		addBuslinie.setBounds(10, 260+config.getResBoxAddition(), 20, 20);
		addBuslinie.setToolTipText("Buslinie hinzufügen");
		addBuslinie.addMouseListener(new ButtonListener());
		this.add(addBuslinie);
		
		removeBuslinie = new JButton("-");
		removeBuslinie.setMargin(new Insets(0, 0, 0, 0));
		removeBuslinie.setActionCommand("removeBuslinie");
		removeBuslinie.setBounds(35, 260+config.getResBoxAddition(), 20, 20);
		if (buslinien.getModel().getSize() == 0 || buslinien.getSelectedIndex() == -1)
			removeBuslinie.setEnabled(false);
		removeBuslinie.setToolTipText("Buslinie entfernen");
		removeBuslinie.addMouseListener(new ButtonListener());
		this.add(removeBuslinie);

		

		// Pinsel für die Map
		
		label = new JLabel("Pinsel");
		label.setFont(fLabel);
		label.setBounds(190, 80, 100, 20);
		this.add(label);

		cGroup = new CheckboxGroup();

		cAuswahl = new Checkbox("Auswahl", cGroup, true);
		cAuswahl.setBounds(200, 105, 120, 20);
		cAuswahl.setName("auswahl");
		cAuswahl.addItemListener(new PaintFieldListener());
		this.add(cAuswahl);
		
		cStrasse = new Checkbox("Strasse", cGroup, false);
		cStrasse.setBounds(200, 130, 120, 20);
		cStrasse.setName("paintStrasse");
		cStrasse.addItemListener(new PaintFieldListener());
		this.add(cStrasse);

		cHaltestelle = new Checkbox("Haltestelle", cGroup, false);
		cHaltestelle.setBounds(200, 155, 120, 20);
		cHaltestelle.setName("paintHaltestelle");
		cHaltestelle.addItemListener(new PaintFieldListener());
		this.add(cHaltestelle);

		cZiel = new Checkbox("Ziel", cGroup, false);
		cZiel.setBounds(200, 180, 120, 20);
		cZiel.setName("paintZiel");
		cZiel.addItemListener(new PaintFieldListener());
		this.add(cZiel);


		// Alle Haltestellen
		
		label = new JLabel("Haltestellen");
		label.setFont(fLabel);
		label.setBounds(5, 300+config.getResBoxAddition(), 100, 20);
		this.add(label);
		
		haltestellen = new JList(netz.getAlleHaltestellen().toArray());
		haltestellen.setName("haltestellen");
		haltestellen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		haltestellen.addMouseListener(new ListBoxActionListener(this));

		JScrollPane sph = new JScrollPane(haltestellen);
		sph.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		sph.setBounds(5, 325+config.getResBoxAddition(), 145, 225+config.getResBoxAddition());
		
		this.add(sph);
	

		// Bushaltestellen
		
		lBushaltestelle = new JLabel("Bushaltestellen");
		lBushaltestelle.setFont(fLabel);
		lBushaltestelle.setBounds(190, 300+config.getResBoxAddition(), 100, 20);
		this.add(lBushaltestelle);
		
		busHaltestellen = new JList(netz.getAlleHaltestellen().toArray());
		busHaltestellen.setName("bushaltestellen");
		busHaltestellen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		busHaltestellen.addMouseListener(new ListBoxActionListener(this));

		JScrollPane spbh = new JScrollPane(busHaltestellen);
		spbh.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		spbh.setBounds(190, 325+config.getResBoxAddition(), 145, 200+config.getResBoxAddition());
		this.add(spbh);

		
		
		// Buttons, um Haltestellen zu einer Buslinie hinzuzufügen
		addBusHaltestelle = new JButton(">");
		addBusHaltestelle.setMargin(new Insets(0, 0, 0, 0));
		addBusHaltestelle.setActionCommand("addBushaltestelle");
		addBusHaltestelle.setBounds(160, 400+config.getResBoxAddition(), 20, 20);
		addBusHaltestelle.setEnabled(false);
		addBusHaltestelle.setToolTipText("Haltestelle hinzufügen");
		addBusHaltestelle.addMouseListener(new ButtonListener());
		this.add(addBusHaltestelle);
		
		removeBusHaltestelle = new JButton("<");
		removeBusHaltestelle.setMargin(new Insets(0, 0, 0, 0));
		removeBusHaltestelle.setActionCommand("removeBushaltestelle");
		removeBusHaltestelle.setBounds(160, 430+config.getResBoxAddition(), 20, 20);
		removeBusHaltestelle.setEnabled(false);
		removeBusHaltestelle.setToolTipText("Haltestelle entfernen");
		removeBusHaltestelle.addMouseListener(new ButtonListener());
		this.add(removeBusHaltestelle);
		

/*		
		// Buttons, um Bushaltestellen zu verschieben um somit die Reihenfolge zu verändern
		moveUpBusHaltestelle = new JButton("+");
		moveUpBusHaltestelle.setMargin(new Insets(0, 0, 0, 0));
		moveUpBusHaltestelle.setActionCommand("moveup");
		moveUpBusHaltestelle.setBounds(240, 530+config.getResBoxAddition()+config.getResBoxAddition(), 20, 20);	
		if (busHaltestellen.getModel().getSize() == 0 || busHaltestellen.getSelectedIndex() == -1)
			moveUpBusHaltestelle.setEnabled(false);
		moveUpBusHaltestelle.setToolTipText("Bushaltestelle nach oben verschieben");
		this.add(moveUpBusHaltestelle);
		
		moveDownBusHaltestelle = new JButton("-");
		moveDownBusHaltestelle.setMargin(new Insets(0, 0, 0, 0));
		moveDownBusHaltestelle.setActionCommand("movedown");
		moveDownBusHaltestelle.setBounds(265, 530+config.getResBoxAddition()+config.getResBoxAddition(), 20, 20);
		if (busHaltestellen.getModel().getSize() == 0 || busHaltestellen.getSelectedIndex() == -1)
			moveDownBusHaltestelle.setEnabled(false);
		moveDownBusHaltestelle.setToolTipText("Bushaltestelle nach unten verschieben");
		this.add(moveDownBusHaltestelle);

	*/	

		// Einstellung der Feldgrösse
		
		lSizeField = new JLabel("Grösse eines Feldelements");
		lSizeField.setFont(fLabel);
		lSizeField.setBounds(5, 570+config.getResBoxAddition()+config.getResBoxAddition(), 190, 20);
		lSizeField.setToolTipText("Grösse der Feldelemente in Pixel");
		this.add(lSizeField);
		
		Vector<Integer> sizes = new Vector<Integer>(25);
		
		for (int i=10; i<=35; i++)
			sizes.add(i);
//			cSizeField.addItem(Integer.valueOf(i));
		cSizeField = new JComboBox(sizes);
		cSizeField.setBounds(170, 570+config.getResBoxAddition()+config.getResBoxAddition(), 55, 24);
		cSizeField.setToolTipText("Grösse der Feldelemente in Pixel");
		cSizeField.setSelectedIndex(config.getFeldElementGroesse()-10);
		cSizeField.addActionListener(new FieldSizeListener());

		this.add(cSizeField);

		
		orButton = new JButton("Optimierung");
		orButton.setActionCommand("startOR");
		orButton.addMouseListener(new ButtonListener());
		orButton.setBounds(50, 615+config.getResBoxAddition()+config.getResBoxAddition(), 110, 22);
		this.add(orButton);
		
		startButton = new JButton("Simulation");
		startButton.setActionCommand("startSearch");
		startButton.addMouseListener(new ButtonListener());
		startButton.setBounds(180, 615+config.getResBoxAddition()+config.getResBoxAddition(), 100, 22);
		this.add(startButton);

	}
	
	// Aktualisiert die Haltestellenliste
	public void setList() {
		haltestellen.removeAll();
		haltestellen.setListData(netz.getAlleHaltestellen().toArray());
	}
		
	public void updateBuslinienList() {
		buslinien.removeAll();
		buslinien.setListData(plan.getBuslinien().toArray());
		if (buslinien.getModel().getSize() > 0)
			buslinien.setSelectedIndex(buslinien.getLastVisibleIndex());
	}

	public void updateHaltestellenListen() {
		haltestellen.removeAll();
		if (netz.getAlleHaltestellen() != null)
			haltestellen.setListData(netz.getAlleHaltestellen().toArray());
		
		Buslinie bus = (Buslinie) buslinien.getSelectedValue();
		busHaltestellen.removeAll();
		if (bus != null && bus.getHaltestellen() != null)
			busHaltestellen.setListData(bus.getHaltestellen().toArray());
	}

	public void updateBushaltestellenList() {
		busHaltestellen.removeAll();
		
		Buslinie bus = (Buslinie) buslinien.getSelectedValue();
		
		if (bus != null)
			busHaltestellen.setListData(bus.getHaltestellen().toArray());
	}

	public Buslinie getSelectedBuslinie() {
		return (Buslinie) buslinien.getSelectedValue();
	}

	public Haltestelle getSelectedHaltestelle() {
		if (addBusHaltestelle.isEnabled() )
			return (Haltestelle) haltestellen.getSelectedValue();
		else
			return (Haltestelle) busHaltestellen.getSelectedValue();
		
	}

	public void setSelectedHaltestelle(Haltestelle haltestelle) {
		haltestellen.setSelectedValue(haltestelle, true);
		addBusHaltestelle.setEnabled(true);
	}

	public Haltestelle getSelectedBusHaltestelle() {
		return (Haltestelle) busHaltestellen.getSelectedValue();
	}

	public void configHaltestelleButtons(boolean busListActive) {
		addBusHaltestelle.setEnabled(busListActive);
		removeBusHaltestelle.setEnabled(!busListActive);
		
/*		int size = busHaltestellen.getModel().getSize();
		int pos = busHaltestellen.getSelectedIndex();
		
		if (size == 0) {
			moveUpBusHaltestelle.setEnabled(false);
			moveDownBusHaltestelle.setEnabled(false);
		}
		else if (pos != -1){			
			if (pos == 0) {
				moveUpBusHaltestelle.setEnabled(false);
				moveDownBusHaltestelle.setEnabled(true);
			}
			else if (pos+1 == size) {
				moveUpBusHaltestelle.setEnabled(true);
				moveDownBusHaltestelle.setEnabled(false);				
			}
			else {
				moveUpBusHaltestelle.setEnabled(true);
				moveDownBusHaltestelle.setEnabled(true);							
			}
		}
*/		
	}
	
	public void setDeleteButtonStatus() {
		int size = buslinien.getModel().getSize();
		int pos = buslinien.getSelectedIndex();
		
		if (size == 0)
			removeBuslinie.setEnabled(false);
		else if (pos == -1)	
			removeBuslinie.setEnabled(false);
		else
			removeBuslinie.setEnabled(true);
	}
	
	
	
	
	public void markHaltestelleOnField() {
		Point p = getSelectedHaltestelle().getKoordinaten();
		editor.markField(p.x, p.y);
	}
	

	public void updateAfterFileload() {		
		initialize();

		cSizeField.setSelectedItem(config.getFeldElementGroesse());
		updateHaltestellenListen();
		updateBuslinienList();
		
		if (buslinien.getModel().getSize() > 0)
			buslinien.setSelectedIndex(0);
		
		updateBushaltestellenList();
	}
}
