package de.fh_konstanz.simubus.model;

import java.awt.Point;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

/**
 * Generiert Busse und Passagiere während der Simulation
 *
 * Funktionsbeschreibung
 */
public class BusGenerator extends SimProcess {
	// Die Buslinie aus der der Busgenerator die Information bezieht, wann er
	// einen Bus starten muss und mit welchen Passagieren
	private Buslinie linie;

	public BusGenerator(Model model, String name, boolean showInTrace,
			Buslinie linie) {
		super(model, name, showInTrace);
		this.linie = linie;
	}

	/**
	 * Generiert in festen Zeitintervallen neue Busse und die zugehörigen Passagiere
	 */
	@Override
	public void lifeCycle() {
		SimuKonfiguration conf = SimuKonfiguration.getInstance();
		hold(new SimTime(conf.getStartzeit() + linie.getStartzeit()));
		int counter = 0;
		SimuResults results = SimuResults.getInstance(); 
		
		while (currentTime().getTimeValue() < conf.getEndezeit()) {
			counter++;
			Passagier entity=null;
			
			
			
			//
			// Generiere Passagiere, die das System in diesem Bus betreten.
			// (vor dem Bus selbst, damit diese noch "einsteigen" können.)
			//
			/* Da sich rechnerisch oft keine runden Passagierzahlen pro Bus
			ergeben, aber nur ganze Passagiere erzeugt werden können, muss
			der Zufallsgenerator entscheiden ob auf- oder abgerundet wird.
			Je größer der Nachkommabereich, desto wahrscheinlicher ist es,
			dass aufgerundet wird */
			double rest = linie.getPassagiereProBus() % 1;
			int passagierAnz;
			if (rest < Math.random()) {
				// abrunden
				passagierAnz = (int) linie.getPassagiereProBus();
			} else {
				// aufrunden
				passagierAnz = ((int) linie.getPassagiereProBus()) + 1;
			}
			
			
			
			
			// Für die Zuweisung der Ziele zu den Passagieren kommt wieder der
			// Zufall ins Spiel
			for (int i = 0; i < passagierAnz; i++) {
				double zufallszahl = Math.random();
				double summierteZielWahrscheinlichkeiten = 0;
				// Für den Fall, dass in der Linie gar keine Zielgewichtungen
				// definiert wurden, wird erstmal die Starthaltestelle als
				// Zielhaltestelle festgelegt
				Haltestelle zielhaltestelle = linie.getHaltestellen().get(0);
				for (Ziel ziel : Strassennetz.getInstance().getZiele()){
					summierteZielWahrscheinlichkeiten
						+=linie.getZielWahrscheinlichkeit(ziel);
					if (zufallszahl < summierteZielWahrscheinlichkeiten) {
						zielhaltestelle = Strassennetz.getInstance()
								.getHaltestelleFuerZiel(
										new Point(ziel.getX(), ziel.getY()));
						break;
					}
				}
				
				Passagier myPassagier = new Passagier(getModel(), "", false,
						linie.getHaltestellen().get(0), zielhaltestelle);
				
				if (entity==null) 
					myPassagier.activateAfter(this);
				else 
					myPassagier.activateAfter(entity);
				
				entity=myPassagier;
				
				//myPassagier.activateBefore(bus);
/*				System.out.println(currentTime() + " "
						+ System.currentTimeMillis() / 1000
						+ ":\tPassagier " + myPassagier.getName() + " mit Ziel \""
						+ zielhaltestelle + "\" wurde an Haltestelle "
						+ linie.getHaltestellen().get(0) + "generiert."); //XXX */
			}
			

			//
			// Generiere Bus
			//
			Bus bus = new Bus(getModel(), "Bus Nr. " + counter + " der Linie "
					+ linie.getName(), true, linie);
			
			if (entity == null)
				bus.activateAfter(this);
			else
				bus.activateAfter(entity);
			//bus.activate(new SimTime(0));
			System.out.println(currentTime() + " " + System.currentTimeMillis() / 1000 + ":\tBus der Linie \""
					+ linie.getName() + "\" wurde generiert."); //XXX
			
			results.addNeuerBusZeitpunkt(currentTime().getTimeValue(), linie, bus.getName());
			
			hold(new SimTime(linie.getFrequenz()));
			
			
			
		} 
	}
}
