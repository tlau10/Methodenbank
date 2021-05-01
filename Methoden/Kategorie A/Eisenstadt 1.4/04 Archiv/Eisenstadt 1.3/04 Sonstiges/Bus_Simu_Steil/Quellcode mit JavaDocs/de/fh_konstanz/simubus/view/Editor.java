package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.fh_konstanz.simubus.model.Buslinie;
//import de.fh_konstanz.simubus.model.Fahranweisung;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.KeineVerbindungException;
import de.fh_konstanz.simubus.model.OPNVModel;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Statistik;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.model.statistik.Haltestellenstatistik;
import de.fh_konstanz.simubus.model.statistik.Linienstatistik;

import de.fh_konstanz.simubus.controller.FieldListener;
import de.fh_konstanz.simubus.controller.PathFinder;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.SimTime;

/**
 * @author Robert Audersetz
 */
public class Editor extends JFrame{
	private SimuKonfiguration config;
	private Gesamtfahrplan plan;
	private Strassennetz netz;

	private int sizeOfEditor;
	private int sizeOfField;		
	private int anzahlFelder;

	private PathFinder pathFinder;
	private Field[][] field;
	private Field current;
	private Field markedHaltestelle;
	private Field markedZiel;
	
	private ArrayList<Buslinie> busLinien;
	private ArrayList<Field> strassenListe;	
	private ArrayList<Haltestelle> halteStellen;

	private ControlPanel control;
	private KartePanel karte;

	private JPanel main;
	
	private JPanel info;
	private JLabel position;

	private ArrayList<JLabel> indexHArray;
	private ArrayList<JLabel> indexVArray;

	private final Font indexLabelFont = new Font("Verdana", Font.PLAIN, 8);
	private final Font positionFont = new Font("Verdana", Font.BOLD, 10);
	
	private static Editor instance;

	
	public static Editor getInstance() {
		if (instance == null)
			instance = new Editor();
		
		return instance;
	}

	public void initialize() {
		config  = SimuKonfiguration.getInstance();

		sizeOfEditor = config.getResPanel();
		sizeOfField = config.getFeldElementGroesse();		
		anzahlFelder = sizeOfEditor/(1+sizeOfField);

		netz = Strassennetz.getInstance();
		plan = Gesamtfahrplan.getInstance();
		
		busLinien = new ArrayList<Buslinie>(plan.getBuslinien());
		halteStellen = new ArrayList<Haltestelle>(netz.getAlleHaltestellen());
	}
	
	private Editor() {				
		super ("Bus Simulation");
		initialize();

		getContentPane().setLayout(null);		
		
		main = new JPanel();
		main.setLayout(null);
		main.setMinimumSize(new Dimension(sizeOfEditor, sizeOfEditor));
		main.setPreferredSize(new Dimension(sizeOfEditor, sizeOfEditor));
		main.setBounds(15, 15, sizeOfEditor, sizeOfEditor);
		
		control = new ControlPanel(this);
		control.setLayout(null);
		control.setMinimumSize(new Dimension(340, config.getResPanel()));
		control.setPreferredSize(new Dimension(340, config.getResPanel()));
		control.setBounds(25+config.getResPanel(), 15, 340, config.getResPanel());
		
		// Alle Felder darstellen und zweidimensionales Array füllen
		paintFields();

		// Labels für Zeilen und Spalten
		showFieldLabels();

		// Hintergrundkarte laden
		loadMap();

		info = new JPanel();
		info.setLayout(null);
		info.setMinimumSize(new Dimension(100, 15));
		info.setPreferredSize(new Dimension(100, 15));
		info.setBounds(0, 13+sizeOfEditor, sizeOfEditor, 15);

		position = new JLabel("(15,10)");
		position.setForeground(Color.GRAY);
		position.setFont(positionFont);
		position.setBounds(sizeOfEditor/2, 2, 50, 12);
		info.add(position);			
		
		getContentPane().add(main);
		getContentPane().add(control);
		getContentPane().add(info);
		
	}
	
	/**
	 * Liefert alle Planquadraten
	 * 
	 * @return Liefert das zweidimensionale Field-Feld
	 */
	public Field[][] getField() {
		return field;
	}

	/**
	 * Passt die Grösse des Hintergrund pixelgenau an
	 *
	 */
	public void updateMapSize() {
		karte.setSizePanel((sizeOfField+1)*anzahlFelder);
	}
	
	/**
	 * Lädt eine beliebige Grafikdatei (Stadtplan, etc.) in den Hintergrund
	 *
	 */
	public void loadMap() {
		int exakteGrösse = (sizeOfField+1)*anzahlFelder;
		karte = new KartePanel(exakteGrösse);
		karte.setBounds(0, 0, exakteGrösse, exakteGrösse);
		main.add(karte);
	}

	/**
	 * Zeigt unter dem Editor die aktuelle Cursor-Position an
	 * 
	 * @param point Aktuelles Planquadrat
	 */	
	public void displayActualPosition(Point point) {
		position.setText("");
		position.setText("(" +point.x +"," +point.y +")");
	}
	
	/**
	 * Zeigt die Labels für Zeilen und Spalten an 
	 */
	private void showFieldLabels() {
		if (indexHArray != null) {
			for (int i=0; i<indexHArray.size(); i++) {
				getContentPane().remove(indexHArray.get(i));
				getContentPane().remove(indexVArray.get(i));
			}
		}
		indexHArray = new ArrayList<JLabel>(anzahlFelder);
		indexVArray = new ArrayList<JLabel>(anzahlFelder);
		
		for (int index=0; index<anzahlFelder; index++) {
			JLabel indexLabel;

			// Horizontal
			indexLabel = new JLabel(String.valueOf(index));
			indexLabel.setForeground(Color.GRAY);
			indexLabel.setFont(indexLabelFont);
			indexLabel.setBounds(((sizeOfField+1)*index)+18, 2, sizeOfField+1, 12);
			getContentPane().add(indexLabel);
			indexHArray.add(index, indexLabel);

			// Vertikal
			indexLabel = new JLabel(String.valueOf(index));
			indexLabel.setForeground(Color.GRAY);
			indexLabel.setFont(indexLabelFont);
			indexLabel.setBounds(2, ((sizeOfField+1)*index)+16, 13, 12);
			getContentPane().add(indexLabel);
			indexVArray.add(index, indexLabel);
		}
		
		getContentPane().repaint();
	}
	
	/**
	 * Für alle Buslinien wird der optimale Pfad ermittelt und im Linien-Objekt gespeichert
	 */
	public boolean startSearch() {
		
		Point start;
		Point ziel;
		int kosten = 0;
		
		if (plan.getBuslinien() != null) {
			Iterator<Buslinie> busse = plan.getBuslinien().iterator();
			
			// Alle Buslinien-Objelte werden aus der HashMap gelesen und abgearbeitet		
			while (busse.hasNext()) {
	
				Buslinie linie = busse.next();
				linie.clearPath();
				pathFinder = new PathFinder(field, anzahlFelder);
				kosten = 0;
				
				List<Haltestelle> punkte = linie.getHaltestellen();
				// Der Weg von Haltestelle zu Haltestelle wird berechnet und die Teilergebnisse dem Pfad der Linie hinzugefügt
				
				for (int i=0; i<punkte.size()-1; i++) {
					try {
						start = punkte.get(i).getKoordinaten();
						ziel = punkte.get(i+1).getKoordinaten();

						// Im Pathfinder wird, damit es bei den Teilabschnitten nicht zu Punktüberschneidungen kommt
						// (startpunkt von teilstrecke 2 == endpunnkt der teilstrecke 1), der Startpunkt im pathFinder
						// nicht in die Pfadliste der Linie aufgenommen. Damit der allererste Startpunkt aber hinzugefügt
						// wird, wird diese if-Abfrage benötigt
						if (i == 0) {
							linie.addToPfad(start);
							linie.setZeitBisHaltestelle(punkte.get(i), kosten);
						}
						
						pathFinder.setStartAndZiel(start, ziel);
						kosten += pathFinder.startSearch();
						double geschw = config.getBusGeschwindigkeitInKmH()*1000/60;
						linie.setZeitBisHaltestelle(punkte.get(i+1), (kosten*config.getFeldElementGroesseInM())/(geschw*10));
						System.out.println(linie.getZeitBisHaltestelle(punkte.get(i+1)));
			
						pathFinder.buildPath(linie);
					} catch(Exception e) {
						System.out.println(punkte.get(i).getName() +"(" +punkte.get(i).getKoordinaten()+")\n" +e.getMessage());
						JOptionPane.showMessageDialog(this, "Fehler bei der Wegfindung der Busse! Strassennetz eventuell unvollständig!", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}			
			}
		}
	
		return true;
		// Tobi 18.6. Fahranweisungstest
/*		Strassennetz sn = Strassennetz.getInstance();
		sn.initHaltestellen();
		Haltestelle hs1, hs2;
		if (sn.getAlleHaltestellen().size() >= 2) {
			Iterator<Haltestelle> it = sn.getAlleHaltestellen().iterator();
			hs1= it.next();
			hs2 = it.next();
			try {
				Fahranweisung fa1 = hs2.getFahranweisung(hs1);
				System.out.println("Fahranweisung von " + hs2 + " nach " + hs1 + ":");
				System.out.println(fa1.getLinie());
				System.out.println(fa1.getAussteigeHaltestelle());
				
				Fahranweisung fa2 = hs1.getFahranweisung(hs2);
				System.out.println("Fahranweisung von " + hs1 + " nach " + hs2 + ":");
				System.out.println(fa2.getLinie());
				System.out.println(fa2.getAussteigeHaltestelle());
			}
			catch (RuntimeException re) {
				re.printStackTrace();
			}
		}*/
	}
	
	/**
	 * Zeichnet die Felder neu (bei Feldgrösse-Änderung) oder komplett neu (falls noch nicht erstellt). 
	 * Das zweidimensionale Field-Array wird dabei gefüllt oder umkopiert.
	 */
	public void paintFields() {
		sizeOfField = config.getFeldElementGroesse();
		
		// Falls die bestehende Feld-Map aktualisiert werden muss
		if (field != null) {
			int anzahlFelderNeu = sizeOfEditor/(1+sizeOfField);
			Field [][] fieldNeu = new Field[anzahlFelderNeu][anzahlFelderNeu];
			
			// Falls neue Feldgrösse grösser (Anzahl Fehler kleiner) ist als die zuvor eingestellte wird 
			// der kopierbare Teil ins neue Feld kopiert. Die Felder, die abgeschnitten werden, gehen verloren.
			if (anzahlFelderNeu < anzahlFelder) {
				for (int j = 0; j<anzahlFelder; j++) {
					for (int i = anzahlFelderNeu; i<anzahlFelder; i++) {
						main.remove(field[i][j]);
					}
				}
				for (int i = 0; i<anzahlFelder; i++) {
					for (int j = anzahlFelderNeu; j<anzahlFelder; j++) {
						main.remove(field[i][j]);
					}
				}
				for (int j = 0; j<anzahlFelderNeu; j++) {
					for (int i = 0; i<anzahlFelderNeu; i++) {
						field[i][j].setBounds(1*i+sizeOfField*i, 1*j+sizeOfField*j, sizeOfField, sizeOfField);
						fieldNeu[i][j] = field[i][j];
					}
				}
			}
			// Falls neue Feldgrösse kleiner (Anzahl Fehler grösser) ist als die zuvor eingestellte wird 
			// das alte Feld komplett ins neue Feld kopiert. Die zusätzlichen Felder sind leer.
			else {
				for (int j = 0; j<anzahlFelderNeu; j++) {
					for (int i = anzahlFelder; i<anzahlFelderNeu; i++) {
						Field newField = new Field(i, j);
						newField.setBounds(1*i+sizeOfField*i, 1*j+sizeOfField*j, sizeOfField, sizeOfField);
						newField.addMouseListener(FieldListener.getInstance());
						fieldNeu[i][j] = newField;
						main.add(newField, 0);
					}
				}
				for (int i = 0; i<anzahlFelderNeu; i++) {
					for (int j = anzahlFelder; j<anzahlFelderNeu; j++) {
						if (fieldNeu[i][j] == null) {
							Field newField = new Field(i, j);
							newField.setBounds(1*i+sizeOfField*i, 1*j+sizeOfField*j, sizeOfField, sizeOfField);
							newField.addMouseListener(FieldListener.getInstance());
							fieldNeu[i][j] = newField;
							main.add(newField, 0);
						}
					}
				}
				for (int j = 0; j<anzahlFelder; j++) {
					for (int i = 0; i<anzahlFelder; i++) {
						field[i][j].setBounds(1*i+sizeOfField*i, 1*j+sizeOfField*j, sizeOfField, sizeOfField);
						fieldNeu[i][j] = field[i][j];
					}
				}
			}

			anzahlFelder = anzahlFelderNeu;
			field = fieldNeu;
			showFieldLabels();
			main.repaint();
			
		}
		else {
			anzahlFelder = sizeOfEditor/(1+sizeOfField);
			field = new Field[anzahlFelder][anzahlFelder];			
			
			for (int j = 0; j<anzahlFelder; j++) {
				for (int i = 0; i<anzahlFelder; i++) {
					Field newField = new Field(i, j);
					newField.setBounds(1*i+sizeOfField*i, 1*j+sizeOfField*j, sizeOfField, sizeOfField);
					newField.addMouseListener(FieldListener.getInstance());
					field[i][j] = newField;
					main.add(newField);
				}
			}
		}		
	}
	
	// Ist nach dem Laden einer gespeicherten Daten notwendig!
	public void updateEditorAfterFileLoad() {
		initialize();
		main.removeAll();
		field = null;
		paintFields();
		showFieldLabels();
		strassenListe = netz.getStrassenListe();
		// Die Strassenliste muss neu erstellt werden, da die alte nicht mehr verwendet werden kann.
		ArrayList<Field> neu = new ArrayList<Field>();
		
		for (int i=0; i<strassenListe.size(); i++) {
			Field temp = strassenListe.get(i);
			field[temp.x][temp.y].setStreet();
			if (temp.hasHaltestelle())
				field[temp.x][temp.y].setHaltestelle(temp.getHaltestelle());
			
			neu.add(field[temp.x][temp.y]);
		}
		
		netz.setStrassenListe(neu);
		
		// Die Ziele müssen auch wieder aktualisiert werden (für die Darstellung)
		ArrayList<Ziel> ziele = netz.getZiele();
		for (int i=0; i<ziele.size(); i++) {
			Ziel temp = ziele.get(i);
			field[temp.getX()][temp.getY()].setZiel(temp);
		}
		
		loadMap();
		main.repaint();
	}
	
	// Nach Ausführen vom OR-Teil notwendig!
	public void updateEditorAfterOR() {
		netz = Strassennetz.getInstance();
		halteStellen = new ArrayList<Haltestelle>(netz.getAlleHaltestellen());
		
		for (int i=0; i<halteStellen.size(); i++) {
			Haltestelle temp = halteStellen.get(i);
			Point p = temp.getKoordinaten();
			field[p.x][p.y].setHaltestelle(temp);
		}
	}
	
	
	public void deleteMarkedField(Field clicked) {
		if (markedHaltestelle != null && markedHaltestelle == clicked) {
			unmarkField();
			markedHaltestelle = null;
		}
		if (markedZiel != null && markedZiel == clicked) {
			unmarkField();
			markedZiel = null;
		}
	}
	
	public void unmarkField() {
		if (markedHaltestelle != null)  {
			markedHaltestelle.highlightHaltestelle(false);
		}
		if (markedZiel != null)  {
			markedZiel.highlightZiel(false);
		}		
	}
	
	public void markField(int x, int y) {		
		unmarkField();
		
		field[x][y].highlightHaltestelle(true);
		markedHaltestelle = field[x][y];		
	}

	public void markZielField(int x, int y) {		
		unmarkField();
		
		field[x][y].highlightZiel(true);
		markedZiel = field[x][y];		
	}
	
	
	public void drawLine(ArrayList<Point> pfad, Color linienFarbe, int linienNummer) {
		
		int pfadLaenge = pfad.size();
		int x[] = new int[pfadLaenge];
		int y[] = new int[pfadLaenge];

		Graphics g = main.getGraphics();
		g.setColor(linienFarbe);
		
		for (int c=0; c<pfadLaenge; c++) {
			Point point = pfad.get(c);
			
			x[c] = field[point.x][point.y].getX()+linienNummer;
			y[c] = field[point.x][point.y].getY()+10;
		}
		g.drawPolyline(x, y, pfadLaenge);
	}

	public boolean startSimulation() {
		OPNVModel model = new OPNVModel(null, "Bussystem-Modell", true, true);
		Experiment exp = new Experiment("Bussystem-Experiment");
		model.connectToExperiment(exp);
		try {
			Strassennetz.getInstance().initHaltestellen(model);
			exp.stop(new SimTime(24*60));
			exp.tracePeriod(new SimTime(0), new SimTime(24*60));
			exp.debugPeriod(new SimTime(0), new SimTime(24*60));
			exp.setShowProgressBar(false);
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			exp.start();
			exp.report();
			exp.finish();
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			System.out.println("Durschnittliche Fahrzeit: "
					+ Statistik.getInstance().getDurchschnittlicheReisezeit());//XXX
			System.out.println("Linienstatistiken: "); //XXX
			System.out.println("==================");//XXX
			for (Buslinie linie : Gesamtfahrplan.getInstance().getBuslinien()) {
				System.out.println(linie + ":");
				Linienstatistik ls = Statistik.getInstance().getLinienstatistik(linie);
				for (Haltestelle hs : linie.getHaltestellen()) {
					System.out.println("Passagiere auf Streckenabschnitt ab " + hs + ":");
					System.out.println("Durschnitt: "
							+ ls.getDurschnittPassagiereAufStreckenabschnitt(hs));
					System.out.println("Maximum: "
							+ ls.getMaxPassagiereAufStreckenabschnitt(hs));
					System.out.println("Minimum: "
							+ ls.getMinPassagiereAufStreckenabschnitt(hs));
				}
			} //XXX
			System.out.println();
			System.out.println("Haltestellenstatistiken: "); //XXX
			System.out.println("========================");//XXX
			for (Haltestelle hs : Strassennetz.getInstance().getAlleHaltestellen()){
				Haltestellenstatistik hss
						= Statistik.getInstance().getHaltestellenstatistik(hs);
				System.out.println(hs + ":");
				System.out.println("Anzahl der Busse, die an der Haltestelle gehalten haben: "
						+ hss.getBusZaehler());
				System.out.println("Durschnittlich wartende Passagiere: "
						+ hss.getDurchschnittlichWartendePassagiere());
				System.out.println("Durschnittliche Wartezeit: "
						+ hss.getDurschnittlicheWartezeit());
			} //XXX
		} catch (KeineVerbindungException e) {
			JOptionPane.showMessageDialog(this, "Keine Verbindung von "
					+ e.getQuellhaltestelle() + " nach "
					+ e.getZielhaltestelle(), "Busnetz unvollständig!",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
}
