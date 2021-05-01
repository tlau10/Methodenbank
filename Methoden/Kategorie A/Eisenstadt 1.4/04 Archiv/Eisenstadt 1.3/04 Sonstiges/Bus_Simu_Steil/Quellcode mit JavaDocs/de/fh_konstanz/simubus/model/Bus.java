
package de.fh_konstanz.simubus.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;


public class Bus extends SimProcess {

	private Haltestelle letzteHaltestelle;
	
	private Haltestelle naechsteHaltestelle;

	private SimTime fahrzeitZurNaechstenHS;
	
	private OPNVModel myModel;

	private Buslinie linie;
	
	private Map<Haltestelle,Queue> aussteigeSchlangen = new HashMap<Haltestelle, Queue>();
	
	private Set<BusObserver> observers = new HashSet<BusObserver>();
	

	/**
	 * Constructor of the van carrier process Used to create a new bus.
	 * 
	 * @param owner
	 *            the model this process belongs to
	 * @param name
	 *            this Bus's name
	 * @param showInTrace
	 *            flag to indicate if this process shall produce output for the
	 *            trace
	 */
	public Bus(Model owner, String name, boolean showInTrace, Buslinie linie) {
		super(owner, name, showInTrace);
		this.linie = linie;
		for (Haltestelle hs : linie.getHaltestellen()) {
			aussteigeSchlangen.put(hs, new Queue(getModel(),
					"Aussteigeschlange für " + hs, false, false));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see desmoj.core.simulator.SimProcess#lifeCycle()
	 */
	public void lifeCycle() {
		List<Haltestelle> hstellen = linie.getHaltestellen();
		SimuResults results = SimuResults.getInstance(); 
		
		if (hstellen.size() < 2) {
			throw new RuntimeException(
					"Linie muss mindestens zwei Haltestellen haben.");
		}
		
		// Fahre solange es noch Haltestellen gibt
		for (Haltestelle hs : hstellen) {
			// ermittle nächste Haltestelle
			letzteHaltestelle = hs;

			results.setPassVonHaltestelle(currentTime().getTimeValue(), hs, hs.getWartendePassagiereAnzahl());

			System.out.println(currentTime() + " "
					+ System.currentTimeMillis() / 1000
					+ ":\tBus \"" + getName() + "\" ist an der Haltestelle."
					+ letzteHaltestelle.getName() + " angekommen"); //XXX
			
			//###############
			//############### Aussteigen
			//###############
			
			//	Hole zuständige Aussteige-Warteschlange
			Queue aussteigeSchlange = aussteigeSchlangen.get(letzteHaltestelle);
			
			//Lasse Passagiere aussteigen
			/*
			for (Passagier myPassagier=null;!aussteigeSchlange.isEmpty();  myPassagier = (Passagier) aussteigeSchlange.first()) {
				aussteigeSchlange.remove(myPassagier);
				myPassagier.activate(myPassagier.berechneAussteigeZeit());
			}
			*/
			while(!aussteigeSchlange.isEmpty()) {
				Passagier myPassagier = (Passagier) aussteigeSchlange.first();
				aussteigeSchlange.remove(myPassagier);
				myPassagier.activate(myPassagier.berechneAussteigeZeit());
			}
			
			//###############
			//############### Einsteigen
			//###############

			//Hole zuständige Einsteige-Warteschlange
			Queue einsteigeSchlange = letzteHaltestelle
					.getEinsteigeWarteschlangen().get(linie);

			// Lasse Passagiere einsteigen
			int counter = 0; //XXX
			while (!einsteigeSchlange.isEmpty()) {
				Passagier myPassagier = (Passagier) einsteigeSchlange.first();
				einsteigeSchlange.remove(myPassagier);
				
/*				System.out.println(currentTime() + " "
						+ System.currentTimeMillis() / 1000
						+ ":\tPassagier " + myPassagier.getName() 
						+ "\" steigt ein in " + getName()); //XXX
	*/			
				aussteigeSchlangen.get(myPassagier.getNaechstesZiel()).insert(myPassagier);
//				System.out.println(counter++); //XXX
			}
			
			// aktualisiere Statistik
			Statistik.getInstance().getHaltestellenstatistik(letzteHaltestelle)
					.inkrementiereBusZaehler();
			int passInBus = 0;
			for (Queue q : aussteigeSchlangen.values()) {
				passInBus += q.length();
			}
			Statistik.getInstance().getLinienstatistik(linie)
					.aktualisierePassagiereAufStreckenabschnitt(
							letzteHaltestelle, passInBus);
			System.out.println("Anzahl Passagier im Bus " +getName() +": " +passInBus);
			results.setPassVonBus(currentTime().getTimeValue(), getName(), passInBus);
			
			// falls letzte Haltestelle erreicht, aufhören
			if (linie.getHaltestellen().indexOf(letzteHaltestelle) >= linie.getHaltestellen().size() - 1) {
				break;
			}
			
			// ermittle Fahrzeit zur nächsten Haltestelle
			fahrzeitZurNaechstenHS
				= linie.zeitZurNaechstenHaltestelle(letzteHaltestelle);
			
			// teile Observern mit, dass Bus losfährt
			fireBusFaehrtLos();
			hold(new SimTime(fahrzeitZurNaechstenHS));
		}
		
		System.out.println(currentTime() + " "
				+ System.currentTimeMillis() / 1000
				+ ":\tBus \""
				+ getName() + "\" verlässt das System."); //XXX
	}

	public Haltestelle getLetzteHaltestelle() {
		return letzteHaltestelle;
	}
	
	public Haltestelle getNaechsteHaltestelle() {
		return naechsteHaltestelle;
	}

	public Buslinie getLinie() {
		return linie;
	}

	public Map<Haltestelle, Queue> getAussteigeSchlangen() {
		return aussteigeSchlangen;
	}

	public void setAussteigeSchlangen(Map<Haltestelle, Queue> aussteigeSchlangen) {
		this.aussteigeSchlangen = aussteigeSchlangen;
	}

	public void addObserver(BusObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(BusObserver observer) {
		observers.remove(observer);
	}
	
	public void fireBusFaehrtLos(){
		for (BusObserver observer : observers) {
			observer.busFaehrtLos(this);
		}
	}

	public SimTime getFahrzeitZurNaechstenHS() {
		return fahrzeitZurNaechstenHS;
	}

	public void setFahrzeitZurNaechstenHS(SimTime fahrzeitZurNaechstenHS) {
		this.fahrzeitZurNaechstenHS = fahrzeitZurNaechstenHS;
	}
}
