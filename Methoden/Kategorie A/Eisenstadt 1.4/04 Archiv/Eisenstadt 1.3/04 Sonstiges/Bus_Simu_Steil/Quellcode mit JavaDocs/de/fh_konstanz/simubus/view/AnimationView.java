package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Ziel;

public class AnimationView extends JPanel{
	
	private final Color haltestelleColor = new Color(70, 120, 160, 200);
	private final Color zielColor = new Color(250, 185, 65, 255);
	private final Color fieldColor = new Color(115, 125, 135, 255);
	private final Color grasColor = new Color(200, 225, 200, 255);
	private final Image image;
	private Image haltestelleIcon;
	private Image busIcon;
	
	private int sizeOfField;
	
	private AnimationPanel panel;
	
	private Gesamtfahrplan plan;
	private Strassennetz netz;
	private SimuKonfiguration config;
	private MapCreator map;
	
	private ArrayList<Field> strassen;
//	private ArrayList<Haltestelle> haltestellen;
	private ArrayList<Ziel> ziele;

//	private int anzahlBuslinien;
//	private BusInfo[] bInfo;
//	private BusAnim[] anim;
	
	private Map<String, BusAnim> lAnim;
	private Map<String, Integer> passBus;
	private Map<BusAnim, BusInfo> busInfo;
	private Map<BusAnim, Point> busPos;
	private Map<BusAnim, Buslinie> busLinien;
	private Map<Haltestelle, HaltestelleInfo> haltestellenInfos;
	
//	private int anzahlHaltestellen;
//	private HaltestelleInfo[] hInfo;;
	
	private AnimThread animThread;
	
	public AnimationView() {
		
		plan = Gesamtfahrplan.getInstance();
		netz = Strassennetz.getInstance();
		config  = SimuKonfiguration.getInstance();
		
		setLayout(null);
		setSize(config.getResWidth(), config.getResHeight());
		setMinimumSize(new Dimension(config.getResWidth(), config.getResHeight()));
		setPreferredSize(new Dimension(config.getResWidth(), config.getResHeight()));
		setBackground(grasColor);

		panel = new AnimationPanel(this);
		panel.setLayout(null);
		panel.setMinimumSize(new Dimension(375, config.getResPanel()));
		panel.setPreferredSize(new Dimension(375, config.getResPanel()));
		panel.setBounds(config.getResPanel(), 0, 375, config.getResPanel());
		add(panel);
		
		map = MapCreator.getInstance();
		image = map.getMap();
		
		try {
			haltestelleIcon = ImageIO.read(new File("haltestelle.png"));
			busIcon = ImageIO.read(new File("bus.png"));			
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Bilddatei konnte nicht geladen werden! Standard wird verwendet. \nError: " +e.getMessage(), "Simulation", JOptionPane.ERROR_MESSAGE);
		}
		
		
		this.sizeOfField = config.getFeldElementGroesse()+1;
		strassen = netz.getStrassenListe();
		
		ziele = netz.getZiele();
		
//		anzahlBuslinien = plan.getBuslinien().size();
//		anzahlHaltestellen = netz.getAlleHaltestellen().size();
//		bInfo = new BusInfo[anzahlBuslinien];		
//		hInfo = new HaltestelleInfo[anzahlHaltestellen];
//		anim = new BusAnim[anzahlBuslinien];
		
		lAnim = new HashMap<String, BusAnim>();
		passBus = new HashMap<String, Integer>();
		busInfo = new HashMap<BusAnim, BusInfo>();
		busPos = new HashMap<BusAnim, Point>();
		busLinien = new HashMap<BusAnim, Buslinie>();
		haltestellenInfos = new HashMap<Haltestelle, HaltestelleInfo>();
		
		for (Haltestelle h : netz.getAlleHaltestellen()) {
			HaltestelleInfo hInfo = new HaltestelleInfo(h, true);
			haltestellenInfos.put(h, hInfo);
			add(hInfo);
		}
		
		
		animThread = new AnimThread(this);
		animThread.start();

	}

	public void addBusAnim(Buslinie linie, String busname) {
		BusAnim tempB = new BusAnim(this, linie, busname); 
		lAnim.put(busname, tempB);
		BusInfo tempI = new BusInfo(linie);
		busInfo.put(tempB, tempI);
		busLinien.put(tempB, linie);
		add(tempI);
		
		tempB.start();
	}
	
	public void removeBusAnim(BusAnim toDelete, String busname) {
		lAnim.remove(busname);
		busPos.remove(toDelete);
		BusInfo tempI = busInfo.get(toDelete);
		busInfo.remove(tempI);
		busLinien.remove(toDelete);
		passBus.remove(busname);
		remove(tempI);
		toDelete.stopThread();
	}
	
	public void setAnzahlPassagierVonHaltestelle(Haltestelle haltestelle, int anzahl) {
		haltestellenInfos.get(haltestelle).setPassagiere(anzahl);
	}

	public void setAnzahlPassagierVonBus(String busname, int anzahl) {
		passBus.put(busname, anzahl);
	}

	public int getAnzahlPassagierVonBus(String busname) {
		return passBus.get(busname);
	}

	public void setAktuellePosition(BusAnim toSet, Point newPos) {
		busPos.put(toSet, newPos);
	}
	
	public Point getAktuellePosition(BusAnim toGet) {
		return busPos.get(toGet);
	}
	
	public void updateHaltestelleView(Haltestelle haltestelle, int x, int y) {
		HaltestelleInfo info = haltestellenInfos.get(haltestelle); 
		info.setBounds(x+26, y+4, 100, 35);
		
//		info.setPassagiere(Math.random()*100);
	}
	
	
	// NEU!!!
	public void updateBusView(BusAnim bus, String busname, int x, int y) {
		BusInfo info = busInfo.get(bus);
		if (info == null) {
			info = new BusInfo(busLinien.get(bus));
			busInfo.put(bus, info);
			add(info);
		}
		
		info.setBounds(x-73, y-12, 70, 35);
		info.setPos(passBus.get(busname), x, y);

	}
	

	public void paintStreet(Graphics2D g2d, Field current) {
		Field[][] field = Editor.getInstance().getField();
		int x = current.x*sizeOfField;
		int y = current.y*sizeOfField;
		
		// 45 Grad
		if (strassen.contains(field[current.x+1][current.y-1])) {
			if (!strassen.contains(field[current.x+1][current.y]) && !strassen.contains(field[current.x][current.y-1])) {
				g2d.fillPolygon(	new int[]{x, x+(5*sizeOfField/10), x+sizeOfField},
								new int[]{y, y-(5*sizeOfField/10), y} , 
								3);
				g2d.fillPolygon(	new int[]{x+sizeOfField, x+(15*sizeOfField/10), x+sizeOfField},
								new int[]{y, y+(5*sizeOfField/10), y+sizeOfField} , 
								3);
			}
		}
		// 135 Grad
		if (strassen.contains(field[current.x+1][current.y+1])) {
			if (!strassen.contains(field[current.x+1][current.y]) && !strassen.contains(field[current.x][current.y+1])) {
				g2d.fillPolygon(	new int[]{x, x+sizeOfField, x+(5*sizeOfField/10)},
								new int[]{y+sizeOfField, y+sizeOfField, y+(15*sizeOfField/10)} , 
								3);
				g2d.fillPolygon(	new int[]{x+sizeOfField, x+(15*sizeOfField/10), x+sizeOfField},
								new int[]{y, y+(5*sizeOfField/10), y+sizeOfField} , 
								3);				

			}
		}
		// 225 Grad
		if (strassen.contains(field[current.x-1][current.y+1])) {
			if (!strassen.contains(field[current.x][current.y+1]) && !strassen.contains(field[current.x-1][current.y])) {
				g2d.fillPolygon(	new int[]{x, x-(5*sizeOfField/10), x},
								new int[]{y+sizeOfField, y+(5*sizeOfField/10), y} , 
								3);
				g2d.fillPolygon(	new int[]{x, x+sizeOfField, x+(5*sizeOfField/10)},
								new int[]{y+sizeOfField, y+sizeOfField, y+(15*sizeOfField/10)} , 
								3);
			}
		}
		// 315 Grad
		if (strassen.contains(field[current.x-1][current.y-1])) {
			if (!strassen.contains(field[current.x][current.y-1]) && !strassen.contains(field[current.x-1][current.y])) {
				g2d.fillPolygon(	new int[]{x, x-(5*sizeOfField/10), x},
								new int[]{y+sizeOfField, y+(5*sizeOfField/10), y} , 
								3);
				g2d.fillPolygon(	new int[]{x, x+(5*sizeOfField/10), x+sizeOfField},
								new int[]{y, y-(5*sizeOfField/10), y} , 
								3);
			}
		}
		
	}
	
	
	public Point paintHaltestelle(Graphics g2d, Point current) {
		Field[][] field = Editor.getInstance().getField();
		int x = current.x*sizeOfField;
		int y = current.y*sizeOfField;
		
		// Falls rechtes Planquadrat eine Strasse ist, Haltestelle unter CURRENT zeichnen
		if (strassen.contains(field[current.x+1][current.y])) {
			x = (x+(sizeOfField/2))-12;
			y = y+(sizeOfField)+1;
			g2d.drawImage(haltestelleIcon, x, y, 24, 24, this);
			return new Point(x, y);
		}
		// Sonst immer Haltestelle rechts von CURRENT zeichnen
		else {
			x = x+(sizeOfField)+1;
			y = (y+(sizeOfField/2))-12;
			g2d.drawImage(haltestelleIcon, x, y, 24, 24, this);
			return new Point(x, y);
		}
	}
	
	
	public void stopBusThreads() {

		animThread.stopThread();
		
		for (String busname : lAnim.keySet()) {
			lAnim.get(busname).stopThread();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, config.getResPanel(), config.getResPanel(), this);
		
		Graphics2D g2d = (Graphics2D) g;
		//Darstellung der Strassenpunkte, aber nur falls Standard-Map im Hintergrund
		if (map.isStandardMap()) {
			g2d.setColor(Color.DARK_GRAY);
			for (int s=0; s<strassen.size(); s++) {
				Field point = strassen.get(s);
				
				g2d.fillRect(point.x*sizeOfField-1, point.y*sizeOfField-1, sizeOfField+2, sizeOfField+2);
				paintStreet(g2d, point);
			}
		}		
		
		// Darstellung der Zielpunkte
		g2d.setColor(zielColor);
		for (int s=0; s<ziele.size(); s++) {
			Point point = ziele.get(s).getZiel();
			
			g2d.fillRect(point.x*sizeOfField, point.y*sizeOfField, sizeOfField, sizeOfField);
		}

		// Darstellung der Haltestellen
		g2d.setColor(haltestelleColor);
		for (Haltestelle h : haltestellenInfos.keySet()) {
			Point point = h.getKoordinaten();
			point = paintHaltestelle(g2d, point);
			updateHaltestelleView(h, point.x, point.y);
		}
		
		
		int linieNr = 0;
		Point aktuellePos = null;
		
		for (String busname : lAnim.keySet()) {
			BusAnim bus = lAnim.get(busname);
			Buslinie linie = busLinien.get(bus);
			aktuellePos = busPos.get(bus);
			if (aktuellePos != null) {
				g2d.drawImage(busIcon, aktuellePos.x*sizeOfField+2, aktuellePos.y*sizeOfField, 18, 21, this);				
				updateBusView(bus, busname, aktuellePos.x*sizeOfField+5, aktuellePos.y*sizeOfField+linieNr);
			}
		}
	}
}
