package de.fh_konstanz.simubus.model.test;

import de.fh_konstanz.simubus.model.*;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;
import junit.framework.TestCase;

public class SimulationsTest extends TestCase {
	protected Haltestelle hs00;
	protected Haltestelle hs03;
	protected Haltestelle hs33;
	protected Haltestelle hs30;
	protected Haltestelle hs60;
	protected Buslinie linie1;
	protected Buslinie linie2;
	protected Buslinie linie3;
	protected Model model;
	protected Experiment exp;
	
	public void setUp() throws Exception{
	
		Strassennetz sn = Strassennetz.getInstance();
		hs00 = new Haltestelle(0,0, "HaSt 0 0");
		sn.addHaltestelle(hs00);
		hs03 = new Haltestelle(0,3, "HaSt 0 3");
		sn.addHaltestelle(hs03);
		hs33 = new Haltestelle(3,3, "HaSt 3 3");
		sn.addHaltestelle(hs33);
		hs30 = new Haltestelle(3,0, "HaSt 3 0");
		Strassennetz.getInstance().addHaltestelle(hs30);
		hs60 = new Haltestelle(6,0, "HaSt 6 0");
		Strassennetz.getInstance().addHaltestelle(hs60);
		
		/*
		 * 00  30
		 *  |
		 *  |
		 * 03--33
		 */
		linie1 = new Buslinie("Linie 1");
		linie1.addHaltestelle(hs00);
		linie1.addHaltestelle(hs03);
		linie1.addHaltestelle(hs33);
		Gesamtfahrplan.getInstance().addLinie(linie1);
		
		/*
		 * 00--30
		 *      |
		 *      |
		 * 03  33
		 */
		linie2 = new Buslinie("Linie 2");
		linie2.addHaltestelle(hs33);
		linie2.addHaltestelle(hs30);
		linie2.addHaltestelle(hs00);
		Gesamtfahrplan.getInstance().addLinie(linie2);
		
		/*
		 * 00--30--60
		 *        /
		 *       /
		 * 03  33
		 */
		linie3 = new Buslinie("Linie 3");
		linie3.addHaltestelle(hs00);
		linie3.addHaltestelle(hs30);
		linie3.addHaltestelle(hs60);
		linie3.addHaltestelle(hs33);
		Gesamtfahrplan.getInstance().addLinie(linie3);
		
		model = new OPNVModel(null, "Bussystem-Modell", true, true);
		exp = new Experiment("Bussystem-Experiment");
		model.connectToExperiment(exp);

		assertEquals(linie1.getHaltestellen().get(0), hs00);
		assertEquals(linie1.getHaltestellen().get(1), hs03);
		assertEquals(linie1.getHaltestellen().get(2), hs33);
		assertEquals(linie2.getHaltestellen().get(0), hs33);
		assertEquals(linie2.getHaltestellen().get(1), hs30);
		assertEquals(linie2.getHaltestellen().get(2), hs00);
		assertEquals(linie3.getHaltestellen().get(0), hs00);
		assertEquals(linie3.getHaltestellen().get(1), hs30);
		assertEquals(linie3.getHaltestellen().get(2), hs60);
		assertEquals(linie3.getHaltestellen().get(3), hs33);
	}
	
	public void tearDown() throws Exception{
		super.tearDown();
		Gesamtfahrplan.reset();
		Strassennetz.reset();
	}
	
	public void testFahranweisungen() {
		// Eine Haltestelle ohne Umsteigen //
		assertEquals(hs00.getFahranweisung(hs03).getAussteigeHaltestelle(), hs03);
		assertEquals(hs00.getFahranweisung(hs03).getLinie(), linie1);
		
		assertEquals(hs03.getFahranweisung(hs33).getAussteigeHaltestelle(), hs33);
		assertEquals(hs03.getFahranweisung(hs33).getLinie(), linie1);
		
		// mit Umsteigen //
		
		// von 03 nach 00
		assertEquals(hs03.getFahranweisung(hs00).getAussteigeHaltestelle(), hs33);
		assertEquals(hs03.getFahranweisung(hs00).getLinie(), linie1);
		assertEquals(hs33.getFahranweisung(hs00).getAussteigeHaltestelle(), hs00);
		assertEquals(hs33.getFahranweisung(hs00).getLinie(), linie2);
		
		// von 33 nach 03
		assertEquals(hs33.getFahranweisung(hs03).getAussteigeHaltestelle(), hs00);
		assertEquals(hs33.getFahranweisung(hs03).getLinie(), linie2);
		assertEquals(hs00.getFahranweisung(hs03).getAussteigeHaltestelle(), hs03);
		assertEquals(hs00.getFahranweisung(hs03).getLinie(), linie1);
	}

	public void testBusgenerierung() {
		linie1.setStartzeit(1);
		linie1.setFrequenz(5);
		linie1.setPassagiereProTag(2000);
		Ziel myZiel = new Ziel(5,5);
		Strassennetz.getInstance().getZiele().add(myZiel);
		linie1.setZielgewichtung(myZiel,1);
		linie2.setStartzeit(2);
		linie2.setFrequenz(5);
		linie3.setStartzeit(3);
		linie3.setFrequenz(5);
		exp.stop(new SimTime(1001));
		exp.tracePeriod(new SimTime(0), new SimTime(100));
		exp.debugPeriod(new SimTime(0), new SimTime(100));
		exp.start();
		exp.report();
		exp.finish();
	}
}
