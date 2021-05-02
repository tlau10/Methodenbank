package de.fh_konstanz.simubus.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;

/**
 * Eine Bushaltestelle
 */
public class Haltestelle implements Raeumlichkeit, Serializable {
	private Map<Haltestelle, Fahranweisung> zielFahranweisungMap
	 = new HashMap<Haltestelle, Fahranweisung>();

	private transient Map<Buslinie, Queue> einsteigeWarteschlangen
			= new HashMap<Buslinie, Queue>();

	private Point koordinaten;

	private String name;

	public Haltestelle() {
		
	}
	
	public int getWartendePassagiereAnzahl() {
		int anzahl=0;
		for (Queue schlange:einsteigeWarteschlangen.values()) {
			anzahl+=schlange.length();
		}
		return anzahl;		
	}

	public Haltestelle(int x, int y) {
		this();
		koordinaten = new Point(x, y);
	}
	
	public Haltestelle(int x, int y, String name) {
		this(x, y);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getKoordinaten() {
		return koordinaten;
	}

	public void setKoordinaten(Point koordinaten) {
		this.koordinaten = koordinaten;
	}

	public Map<Buslinie, Queue> getEinsteigeWarteschlangen() {
		return einsteigeWarteschlangen;
	}

	/**
	 * Gibt die die Warteschlange für die angegebene Buslinie zurück
	 * 
	 * @param linie
	 * @return Warteschlange
	 */
	public Queue getEinsteigeWarteschlange(Buslinie linie) {
		return einsteigeWarteschlangen.get(linie);
	}

	public void setEinsteigeWarteschlangen(
			Map<Buslinie, Queue> einsteigeWarteschlangen) {
		this.einsteigeWarteschlangen = einsteigeWarteschlangen;
	}

	/**
	 * Gibt bei Angabe des Endziels eines Passagiers eine Fahranweisung zurück,
	 * die enhält, welche Linie er als nächstes nehmen muss und bis zu welcher
	 * Haltestelle
	 * 
	 * @param endZiel
	 *            das Ziel des Passagiers
	 */
	public Fahranweisung getFahranweisung(Haltestelle endZiel) {
		Fahranweisung fahranweisung = zielFahranweisungMap.get(endZiel);
		if (fahranweisung == null) {
			throw new RuntimeException("Für das gewählte Ziel existiert keine"
					+ " Fahranweisung");
		}
		return fahranweisung;
	}

	/**
	 * Berechnet die Diagonaldistanz zum angegebenen <code>Point</code>.
	 * 
	 * @param punkt
	 * @return diagonaldistanz in Planquadraten
	 */
	public double diagonalDistanz(Point punkt) {
		int xDist = Math.abs(koordinaten.x - punkt.x);
		int yDist = Math.abs(koordinaten.y - punkt.y);

		int kleinereDist;
		int groessereDist;

		if (xDist <= yDist) {
			kleinereDist = xDist;
			groessereDist = yDist;
		} else {
			kleinereDist = yDist;
			groessereDist = xDist;
		}

		return kleinereDist * Math.sqrt(2) + (groessereDist - kleinereDist);
	}

	public String toString() {
		return name;
	}

	/**
	 * Sollte vor der Simulation aufgerufen wurden, aber erst nachdem alle
	 * Buslinien vollständig definiert wurden. Initialisiert
	 * Einsteigewarteschlangen und Fahranweisungen
	 * @throws KeineVerbindungException 
	 */
	public void init(Model model) throws KeineVerbindungException {
		initEinsteigeSchlangen(model);
		initFahranweisungen();
	}
	
	public void initEinsteigeSchlangen(Model model) {
		einsteigeWarteschlangen	= new HashMap<Buslinie, Queue>();
		for (Buslinie linie : Gesamtfahrplan.getInstance().getLinien(this)) {
			einsteigeWarteschlangen.put(linie, new Queue(model,
					"Einsteigeschlange an der Haltestelle " + this
					+ " für " + linie , true, true));
		}
	}
	
	/**
	 * Berechnet für jede Haltestelle die Fahranweisung die ein Passagier
	 * bekommt, wenn er von der aktuellen Haltestelle zu dieser Haltestelle
	 * fahren will
	 * @throws KeineVerbindungException 
	 */
	public void initFahranweisungen() throws KeineVerbindungException {
		for (Haltestelle zielhaltestelle
				: Strassennetz.getInstance().getAlleHaltestellen()) {
			PriorityQueue<Suchknoten> openList = new PriorityQueue<Suchknoten>();
			Set<Suchknoten> closedList = new HashSet<Suchknoten>();
			Suchknoten startknoten = new Suchknoten(this, null, null, 0,
					zielhaltestelle);
			Suchknoten aktuellerKnoten;

			//
			// Pathfinding nach Dijkstra
			//
			
			aktuellerKnoten = startknoten;
			do {
				/* Für jeden Nachfolger von aktuellerKnoten ...*/
				for (Suchknoten nachfolger : aktuellerKnoten.getNachfolger()) {
					/* Falls ein Knoten mit gleicher Bushaltestelle und linie
					 * sich bereits in der closedList befindet, ignorieren */
					if (closedList.contains(nachfolger)) {
						continue;
					}
					/*
					 * Falls ein Knoten mit der selben Haltestelle und Buslinie
					 * sich schon in der openList befindet, muss geprüft werden,
					 * ob der neue Pfad kuerzer und der Knoten in der openList
					 * ggf. ersetz werden. */
					else if (openList.contains(nachfolger)) {
						Iterator<Suchknoten> it = openList.iterator();
						while (it.hasNext()) {
							Suchknoten kOpen = it.next();
							if (kOpen.haltestelle == nachfolger.haltestelle
								&& kOpen.linie == nachfolger.linie
								&& nachfolger.f < kOpen.f) {
								it.remove();
								openList.add(nachfolger);
								break;
							}
						}
					}
					/* Falls noch kein entsprechender Knoten in der openList
					 * vorhanden ist, wird er hinzugefuegt.
					 */
					else {
						openList.add(nachfolger);
					}
				}

				/* fuege aktuellerKnoten der closedList hinzu */
				closedList.add(aktuellerKnoten);
				
				/*
				 * Knoten mit den niedrigsten Kosten aus der openList holen
				 */
				aktuellerKnoten = openList.poll();
			} while (aktuellerKnoten != null
					&& aktuellerKnoten.haltestelle != zielhaltestelle);
			
			/* Wenn aktuellerKnoten die zielhaltestelle enthält ist der kürzeste
			 * Pfad gefunden und es können Fahranweisungen daraus generiert
			 * werden
			 */
			if (aktuellerKnoten != null) {
				aktuellerKnoten.traceBack();
			} else {
				/* ist kein Knoten vorhanden, gibt es keinen Weg */
				if (aktuellerKnoten == null) {
					throw new KeineVerbindungException(this, zielhaltestelle);
				}
			}
		}
	}

	/**
	 * @author Tobias Lott Wird zu Berechnung der Fahranweisungen mittels
	 *         Dijkstra-Algorithmus verwendet.
	 */
	private class Suchknoten implements Comparable<Suchknoten> {
		private Haltestelle haltestelle;

		private Buslinie linie;

		private Suchknoten vorgaenger;

		private Set<Suchknoten> nachfolger;

		/*
		 * Kosten von Startknoten bis zu diesem Knoten (benötigte Zeit in
		 * Minuten)
		 */
		private double f;

		private Haltestelle zielHaltestelle;

		// pauschaler Schätzwert für die Zeit, die beim Umsteigen auf den Bus
		// gewartet wird. Sollte irgendwann mal exakt berechnet werden
		private static final int WARTEZEIT_AUF_BUS = 5;

		/**
		 * Konstruktor
		 * 
		 * @param haltestelle
		 * @param linie
		 *            ist beim Startknoten null
		 * @param vorgaenger
		 *            ist beim Startknoten null
		 * @param f
		 *            Gesamtkosten bis zu diesem Knoten
		 * @param zielHaltestelle
		 */
		public Suchknoten(Haltestelle haltestelle, Buslinie linie,
				Suchknoten vorgaenger, double f, Haltestelle zielHaltestelle) {
			if (haltestelle == null) {
				throw new RuntimeException("haltestelle darf nicht null sein.");
			} else if (zielHaltestelle == null) {
				throw new RuntimeException(
						"zielHaltestelle darf nicht null sein.");
			} else {
				this.haltestelle = haltestelle;
				this.linie = linie;
				this.vorgaenger = vorgaenger;
				this.f = f;
				this.zielHaltestelle = zielHaltestelle;
				nachfolger = new HashSet<Suchknoten>();
			}
		}

		/**
		 * Wenn der optimale Pfad zu einem Knoten gefunden wurde, wird er mit
		 * dieser Methode rückwärts durchlaufen und die entsprechenden
		 * Fahranweisungen daraus generiert
		 */
		public void traceBack() {
			Suchknoten knoten = this;
			Buslinie linie = this.linie;
			Haltestelle haltestelle = this.haltestelle;

			while (knoten.vorgaenger != null) {
				linie = knoten.linie;
				if (knoten.vorgaenger.linie != null
						&& knoten.vorgaenger.linie != knoten.linie) {
					haltestelle = knoten.vorgaenger.haltestelle;
				}
				knoten = knoten.vorgaenger;
			}

			zielFahranweisungMap.put(this.haltestelle, new Fahranweisung(linie,
					haltestelle));
		}

		/**
		 * Findet alle Nachfolger des Suchknotens. Ein Nachfolger unterscheidet
		 * sich entweder durch die Buslinie oder die Haltestelle vom aktuellen
		 * Knoten
		 */
		public Set<Suchknoten> getNachfolger() {
			// Linienwechsel
			for (Buslinie linie : Gesamtfahrplan.getInstance().getLinien(
					haltestelle)) {
				if (linie != this.linie) {
					nachfolger.add(new Suchknoten(haltestelle, linie, this, f
							+ WARTEZEIT_AUF_BUS, zielHaltestelle));
				}
			}

			/*
			 * nächste Haltestelle der aktuellen Linie anfahren (bei Startknoten
			 * nicht möglich, da keine Linie vorhanden)
			 */
			if (linie != null) {
				Haltestelle naechsteHaltestelle = linie.getNext(haltestelle);
				if (naechsteHaltestelle != null) {
					nachfolger.add(new Suchknoten(naechsteHaltestelle, linie,
							this, f
									+ linie.zeitZurNaechstenHaltestelle(
											haltestelle).getTimeValue(),
							zielHaltestelle));
				}
			}

			return nachfolger;
		}

		/**
		 * @param k
		 * @return negativen Integer, wenn der Suchknoten naeher am Startknoten
		 *         als der uebergebene Suchknoten ist 0, wenn der Suchknoten
		 *         gleich weit vom Startknoten entfernt ist wie der uebergebene
		 *         Suchknoten positiven Integer, wenn der Suchknoten weiter weg
		 *         vom Startknoten als der uebergebene Suchknoten ist.
		 */
		public int compareTo(Suchknoten k) {
			return (int) (f - k.f);
		}

		public boolean equals(Object obj) {
			if (obj != null
					&& obj instanceof Suchknoten
					&& ((Suchknoten) obj).haltestelle == this.haltestelle
					&& ((Suchknoten) obj).linie == this.linie) {
				return true;
			} else {
				return false;
			}
		}
		
		public int hashCode() {
			int hashCode = haltestelle.hashCode() / 2;
			if (linie != null) hashCode += linie.hashCode() / 2;
			return hashCode;
		}
	}
	
}
