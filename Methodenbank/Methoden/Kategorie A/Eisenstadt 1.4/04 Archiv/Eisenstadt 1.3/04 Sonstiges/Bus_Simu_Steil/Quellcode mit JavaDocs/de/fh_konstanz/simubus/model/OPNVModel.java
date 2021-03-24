
package de.fh_konstanz.simubus.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;
import example.VanCarrier.ProcessesExample;


public class OPNVModel extends Model {

	private Map<Haltestelle, List<PassagierBetrittHaltestelleEvent>> passagierAnHaltestelleEvents;
	
	public OPNVModel(Model owner, String modelName, boolean showInReport,
			boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
		passagierAnHaltestelleEvents = new HashMap<Haltestelle,List<PassagierBetrittHaltestelleEvent>>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see desmoj.core.simulator.Model#description()
	 */
	public String description() {		
		return "SimuBUS Eistenstadt";
	}

	/**
	 * Hier werden die statischen Teile des Models initialisert. Wird
	 * automatisch von der Methode <code>connectToExperiment</code> aufgerufen.
	 * Da wir die statischen Modellelemente in Singletons verpackt haben, bleibt
	 * die Methode leer. Sollte vielleicht mal refaktorisiert werden.
	 * @throws KeineVerbindungException 
	 */
	public void init() {
		Statistik.getInstance().init();
	}
	
	/**
	 * Hier werden die dynamischen Anteile der Simulation, sprich die Prozesse,
	 * angestoßen. Wird automatisch aufgerufen von der <code>start</code>-
	 * Methode des zugehörigen Experiments
	 */
	public void doInitialSchedules() {
		// Aktiviere Busgeneratoren
		for (Buslinie linie : Gesamtfahrplan.getInstance().getBuslinien()) {
			BusGenerator bg = linie.getBusGenerator(this);
			bg.activate(new SimTime(0));
		}		
	}

	/**
	 * nur zu testzwecken drin. dafür können aber auch unittests verwendet werden
	 * @deprecated
	 */
	public static void main(java.lang.String[] args) {

	    // create model and experiment
	    OPNVModel model = new OPNVModel(null, 
	                          "Simple Process-Oriented Van Carrier Model", true, true);
	       // null as first parameter because it is the main model and has no mastermodel
	    Experiment exp = new Experiment("ProcessesExampleExperiment");
	       // ATTENTION, since the name of the experiment is used in the names of the 
	       // output files, you have to specify a string that's compatible with the 
	       // filename constraints of your computer's operating system.

	    // ... main() continued

	    // connect both
	    model.connectToExperiment(exp);
	    // ... main() continued

	    // set experiment parameters
	    exp.setShowProgressBar(true);  // display a progress bar (or not)
	    exp.stop(new SimTime(1500));   // set end of simulation at 1500 time units
	    exp.tracePeriod(new SimTime(0.0), new SimTime(100));
	                                               // set the period of the trace
	    exp.debugPeriod(new SimTime(0.0), new SimTime(50));   // and debug output
	       // ATTENTION!
	       // Don't use too long periods. Otherwise a huge HTML page will
	       // be created which crashes Netscape :-)
	    // ... main() continued

	    // start the experiment at simulation time 0.0
	    exp.start();

	    // --> now the simulation is running until it reaches its end criterion
	    // ...
	    // ...
	    // <-- afterwards, the main thread returns here

	    // generate the report (and other output files)
	    exp.report();

	    // stop all threads still alive and close all output files
	    exp.finish();
	 }
}
