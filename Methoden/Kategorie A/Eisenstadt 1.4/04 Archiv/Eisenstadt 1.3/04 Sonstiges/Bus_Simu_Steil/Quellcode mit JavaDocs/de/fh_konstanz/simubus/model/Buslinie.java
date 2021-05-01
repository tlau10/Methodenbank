package de.fh_konstanz.simubus.model;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

/**
 * Enthält Informationen über eine Buslinie, z.B. den Namen und wann sie an
 * welchen Haltestellen hält.
 */
public class Buslinie implements Serializable {
	private String name;

	private List<Haltestelle> haltestellen = new ArrayList<Haltestelle>();;

	private transient Map<Haltestelle, SimTime> zeitBisHaltestelle
			= new HashMap<Haltestelle, SimTime>();

	private ArrayList<Point> pfad = new ArrayList<Point>();;

	private Color linienFarbe;

	private Map<Ziel, Integer> zielgewichtungen = new HashMap<Ziel, Integer>();

	private int passagiereProTag;

	private double frequenz = 30; //TODO einstellbar machen auf der GUI
	
	private double startzeit;

	public Buslinie() {
	}

	public Buslinie(String name) {
		this.name = name;
	}

	public List<Haltestelle> getHaltestellen() {
		return haltestellen;
	}

	public void setHaltestellen(List<Haltestelle> haltestellen) {
		this.haltestellen = haltestellen;
	}

	public void addHaltestelle(Haltestelle haltestelle) {
		haltestellen.add(haltestelle);
	}

	public void removeHaltestelle(Haltestelle haltestelle) {
		haltestellen.remove(haltestelle);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addToPfad(Point point) {
		this.pfad.add(point);
	}
	
	public void setPfad(ArrayList<Point> pfad) {
		this.pfad.addAll(pfad);
	}

	public ArrayList<Point> getPfad() {
		return pfad;
	}

	public void clearPath() {
		pfad.clear();
	}
	
	public void deleteHaltestellen() {
		haltestellen.clear();
	}
	
	/**
	 * @return Returns the startPunkt.
	 */
	public Haltestelle getStartPunkt() {
		if (haltestellen.size() == 0)
			return null;

		return haltestellen.get(0);

	}

	/**
	 * @return Returns the zielPunkt.
	 */
	public Haltestelle getZielPunkt() {
		if (haltestellen.size() == 0)
			return null;

		return haltestellen.get(haltestellen.size() - 1);
	}

	/**
	 * @return Returns the linienFarbe.
	 */
	public Color getLinienFarbe() {
		return linienFarbe;
	}

	/**
	 * @param linienFarbe
	 *            The linienFarbe to set.
	 */
	public void setLinienFarbe(Color linienFarbe) {
		this.linienFarbe = linienFarbe;
	}

	/**
	 * @param ausgangshaltestelle
	 * @return die Haltestelle, die nach der Ausgangshaltestelle angefahren
	 *         wird.<br>
	 *         null wenn die Ausgangshaltestelle die letzte der Linie ist.
	 */
	public Haltestelle getNext(Haltestelle ausgangshaltestelle) {
		int i = haltestellen.indexOf(ausgangshaltestelle);
		if (i == -1) {
			throw new RuntimeException(
					"Die Ausgangshaltestelle wird von dieser Buslinie nicht angefahren.");
		} else if (i == haltestellen.size() - 1) {
			return null;
		} else {
			return haltestellen.get(i + 1);
		}
	}

	/**
	 * Berechnet die Zeit die ein Bus dieser Linie von der angegebenen zur
	 * nächsten Haltestelle der Linie braucht
	 * 
	 * @param startHaltestelle
	 * @return Zeit in Minuten
	 */
	public SimTime zeitZurNaechstenHaltestelle(Haltestelle startHaltestelle) {
		Haltestelle naechsteHaltestelle = getNext(startHaltestelle);
		if (naechsteHaltestelle == null) {
			throw new RuntimeException("Die angegebene Starthaltestelle ist"
					+ " die letzte der Linie");
		} else {
			return zeitZwischenHaltestellen(startHaltestelle,
					naechsteHaltestelle);
		}

	}

	/**
	 * Berechnet die Zeit die ein Bus der Linie braucht um von der angegebenen
	 * Starthaltestelle zur angegebenen Zielhaltestelle zu fahren
	 * 
	 * @param starthaltestelle
	 * @param zielhaltestelle
	 */
	public SimTime zeitZwischenHaltestellen(Haltestelle starthaltestelle,
			Haltestelle zielhaltestelle) {
		if (haltestellen.indexOf(starthaltestelle) >= haltestellen
				.indexOf(zielhaltestelle)) {
			throw new RuntimeException(this + ": " +
					"Fehler: Starthaltestelle " + starthaltestelle
					+ " liegt nicht vor Zielhaltestelle " + zielhaltestelle);
		}
		SimTime zeitBisStarthaltestelle = zeitBisHaltestelle
				.get(starthaltestelle);
		SimTime zeitBisZielHaltestelle = zeitBisHaltestelle
				.get(zielhaltestelle);
		if (zeitBisStarthaltestelle == null || zeitBisZielHaltestelle == null) {
			System.out.println("[WARNING] Zeit zwischen den Haltestellen "
					+ starthaltestelle + " und " + zielhaltestelle
					+ " konnte nicht berechnet werden");
			return new SimTime(1);
		} else {
			return SimTime
					.diff(zeitBisStarthaltestelle, zeitBisZielHaltestelle);
		}
	}

	public String toString() {
		return name;
	}


	/**
	 * Legt fest welcher Anteil der Passagiere, die das System in dieser
	 * Buslinie betreten, ein bestimmtes Ziel hat. Die Gewichtung ist immer
	 * relativ zu den anderen Zielen zu sehen. Haben zwei Ziele die Gewichtung
	 * x, so kommt das auf das selbe raus als ob beide ein Vielfaches von x als
	 * Gewichtung haben. In beiden Fällen fährt jeweils die Hälfte der
	 * Passagiere zu den beiden Zielen.
	 */
	public void setZielgewichtung(Ziel ziel, int gewichtung) {
		zielgewichtungen.put(ziel, gewichtung);
	}

	/**
	 * Siehe setZielgewichtung.
	 */
	public int getZielgewichtung(Ziel ziel) {
		if (!zielgewichtungen.containsKey(ziel))
			return 0;
		return zielgewichtungen.get(ziel);
	}

	/**
	 * Gibt zurück welcher Anteil der Passagiere dieser Linie ein bestimmtes
	 * Ziel haben
	 * 
	 * @return anteil Zahl zwischen 0 (keine Passagiere mit dem angebenen Ziel)
	 *         und 1 (alle Passagiere haben das angegebene Ziel)
	 */
	public double getZielWahrscheinlichkeit(Ziel ziel) {
		double summeGewichtungen = 0;
		for (double gewichtung : zielgewichtungen.values()) {
			summeGewichtungen += gewichtung;
		}
		if (summeGewichtungen == 0)
			return 0;
		
		return getZielgewichtung(ziel) / summeGewichtungen;
	}

	/**
	 * Gibt die Anzahl der Passagiere zurück, die an einem Tag mit der Buslinie
	 * unterwegs sind.
	 * 
	 * @return passagiereProTag
	 */
	public int getPassagiereProTag() {
		return passagiereProTag;
	}

	/**
	 * Legt die Anzahl der Passagiere fest, die an einem Tag mit der Buslinie
	 * unterwegs sind.
	 * 
	 * @param passagiereProTag
	 */
	public void setPassagiereProTag(int passagiereProTag) {
		this.passagiereProTag = passagiereProTag;
	}

	/**
	 * Gibt den zeitlichen Abstand, in dem die Busse der Linie starten zurück.
	 * 
	 * @return frequenz Zeit zwischen zwei Bussen in Minuten
	 */
	public double getFrequenz() {
		return frequenz;
	}

	/**
	 * Legt den zeitlichen Abstand, in dem die Busse der Linie starten fest.
	 * 
	 * @param frequenz
	 *            Zeit zwischen zwei Bussen in Minuten
	 */
	public void setFrequenz(double frequenz) {
		this.frequenz = frequenz;
	}

	/**
	 * Legt die Zeit fest, die ein Bus der Linie vom Startpunkt bis zur
	 * Zielhaltestelle braucht
	 * 
	 * @param haltestelle
	 * @param zeit
	 *            in Minuten
	 */
	public void setZeitBisHaltestelle(Haltestelle haltestelle, double zeit) {
		zeitBisHaltestelle.put(haltestelle, new SimTime(zeit));
	}
	
	public SimTime getZeitBisHaltestelle(Haltestelle haltestelle) {
		return zeitBisHaltestelle.get(haltestelle);
	}

	/**
	 * Legt die Zeit fest, die ein Bus der Linie vom Startpunkt bis zur
	 * Zielhaltestelle braucht
	 * 
	 * @param haltestelle
	 * @param zeit
	 *            in Minuten
	 */
	public void setZeitBisHaltestelle(Haltestelle haltestelle, SimTime zeit) {
		zeitBisHaltestelle.put(haltestelle, zeit);
	}

	/**
	 * Gibt zurück, wie viele Minuten nach Simulationsstart der erste Bus dieser
	 * Linie losfährt.
	 * 
	 * @return startzeit
	 */
	public double getStartzeit() {
		return startzeit;
	}

	/**
	 * Legt fest, wie viele Minuten nach Simulationsstart der erste Bus dieser
	 * Linie losfährt.
	 * 
	 * @param startzeit
	 */
	public void setStartzeit(double startzeit) {
		this.startzeit = startzeit;
	}
	
	/**
	 * Gibt einen Busgenerator zurück, der während der Simulation die Busse der
	 * Linie generiert.
	 * @param model das Model zu dem der Busgenerator gehören soll
	 * @return busgenerator
	 */
	public BusGenerator getBusGenerator(Model model) {
		return new BusGenerator(model, name + " Busgenerator", true, this);
	}

	/**
	 * Berechnet, wie viele Busse der Linie pro Tag fahren.
	 * 
	 * @return busseProTag
	 */
	public int getBusseProTag() {
		SimuKonfiguration conf = SimuKonfiguration.getInstance();
		return (int) ((conf.getEndezeit() - conf.getStartzeit())
				/ frequenz) + 1;
	}
	
	/**
	 * Berechnet, wie viele Passagiere pro Bus ins System kommen
	 */
	public double getPassagiereProBus() {
		return (double)getPassagiereProTag() / (double)getBusseProTag();
	}
	
	private void readObject(ObjectInputStream ois) throws IOException
	  {
	    try
	    {
	      ois.defaultReadObject();
		  zeitBisHaltestelle = new HashMap<Haltestelle, SimTime>();
	    }
	    catch ( ClassNotFoundException e )
	    {
	      throw new IOException("Klasse nicht gefunden.");
	    }
	  }
}
