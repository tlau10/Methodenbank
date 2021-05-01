package de.fh_konstanz.simubus.model;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

public class Passagier extends SimProcess {

	private Model myModel;

	private Haltestelle ort;

	private Haltestelle naechstesZiel;

	private Haltestelle endZiel;

	private Buslinie naechsteLinie = null;

	private SimTime startzeit;

	/**
	 * Gibt die Zeit, die der Passagier zum Aussteigen braucht, zurück. Vorerst
	 * ist diese immer 0.
	 * 
	 * @return aussteigezeit
	 */
	public SimTime berechneAussteigeZeit() {
		return new SimTime(0);
	}

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
	public Passagier(Model owner, String name, boolean showInTrace,
			Haltestelle ort, Haltestelle endZiel) {
		super(owner, name, showInTrace);
		this.endZiel = endZiel;
		this.ort = ort;
		// store a reference to the model this VC is associated with
		myModel = (OPNVModel) owner;
	}

	@Override
	public void lifeCycle() {
		// Der Passagier kommt im System an
		startzeit = currentTime();
		while (ort != endZiel) {
			// Er schaut auf den Fahrplan und sucht sich seine Route.
			// Sein Endziel kennt er schon.
			Fahranweisung fahranweisung = ort.getFahranweisung(endZiel);
			naechstesZiel = fahranweisung.getAussteigeHaltestelle();
			naechsteLinie = fahranweisung.getLinie();
			
			// Er reiht sich in die Warteschlange für die richtige Buslinie
			// ein
			// und wartet brav
			ort.getEinsteigeWarteschlangen().get(naechsteLinie).insert(this);

/*			System.out.println(currentTime() + " " + System.currentTimeMillis()
					/ 1000 + ":\tPassagier " + getName() + " mit Ziel \""
					+ endZiel + "\" reiht sich ein für "
					+ naechsteLinie.getName() + " an der Haltestelle "
					+ ort.getName()); // XXX
*/
			passivate();

			// Der Bus ist angekommen und der Passagier ist mit Aussteigen
			// dran
			ort = naechstesZiel;

		}
		// Der Passagier verlässt das System
		double fahrzeit = SimTime.diff(startzeit, currentTime()).getTimeValue();
		Statistik.getInstance().registriereReisezeit(fahrzeit);
/*		System.out.println(currentTime() + " " + System.currentTimeMillis()
				/ 1000 + ":\tPassagier " + getName() + " mit Ziel \"" + endZiel
				+ "\" ist angekommen. (fahrzeit: " + fahrzeit + " )"); // XXX */
	}

	/**
	 * @return Returns the naechstesZiel.
	 */
	public Haltestelle getNaechstesZiel() {
		return naechstesZiel;
	}

	/**
	 * @param naechstesZiel
	 *            The naechstesZiel to set.
	 */
	public void setNaechstesZiel(Haltestelle naechstesZiel) {
		this.naechstesZiel = naechstesZiel;
	}
}
