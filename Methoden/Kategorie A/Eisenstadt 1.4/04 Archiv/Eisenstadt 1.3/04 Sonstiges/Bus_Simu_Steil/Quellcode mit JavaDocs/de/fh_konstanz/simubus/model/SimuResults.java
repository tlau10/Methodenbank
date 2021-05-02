package de.fh_konstanz.simubus.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimuResults {

	private static SimuResults instance;
	private ArrayList<Integer> zeiten;
	
	private Map<Integer, ArrayList<String>> neueBusse;
	private Map<String, Buslinie> busLinien;
	
	private Map<Integer, Map<String, Integer>> passBus;
	
	private Map<Integer, Map<Haltestelle, Integer>> passHaltestellen;
	
	private int factor;
	

	public SimuResults() {
		this.zeiten = new ArrayList<Integer>();
		this.neueBusse = new HashMap<Integer, ArrayList<String>>();
		this.busLinien = new HashMap<String, Buslinie>();
		
		this.passBus = new HashMap<Integer, Map<String, Integer>>();
		
		this.passHaltestellen = new HashMap<Integer, Map<Haltestelle, Integer>>();
		
		this.factor = SimuKonfiguration.getInstance().getSimulationsgeschwindigkeit();
	}
	
	public static SimuResults getInstance() {
		if (instance == null) {
			instance = new SimuResults();
		}
		return instance;
	}
	
	
	public void addEreignisZeitpunkt(double timestamp) {
		
		int zeit = getIntTime(timestamp);
		zeiten.add(zeit);
	}

	public void addNeuerBusZeitpunkt(double timestamp, Buslinie linie, String name) {
		int zeit = getIntTime(timestamp);
		ArrayList<String> temp = neueBusse.get(zeit);
		if (temp == null) {
			temp = new ArrayList<String>();
		}
		temp.add(name);

		neueBusse.put(zeit, temp);
		busLinien.put(name, linie);
		
		if (!zeiten.contains(zeit))
			zeiten.add(zeit);
	}

	public void setPassVonBus(double timestamp, String busname, int anzahlPass) {
		int zeit = getIntTime(timestamp);
		
		Map<String, Integer> map = passBus.get(zeit);
		if (map == null) {
			map = new HashMap<String, Integer>();
		}
		
		map.put(busname, anzahlPass);
		System.out.println("MAP: "+zeit +": " +busname +" : " +anzahlPass);
		passBus.put(zeit, map);
		
		if (!zeiten.contains(zeit))
			zeiten.add(zeit);
	}
	
	public void setPassVonHaltestelle(double timestamp, Haltestelle haltestelle, int anzahlPass) {
		int zeit = getIntTime(timestamp);
		
		Map<Haltestelle, Integer> map = passHaltestellen.get(zeit);
		if (map == null) {
			map = new HashMap<Haltestelle, Integer>();
		}
		
		map.put(haltestelle, anzahlPass);
		System.out.println("MAP: "+zeit +": " +haltestelle +" : " +anzahlPass);
		passHaltestellen.put(zeit, map);
		
		if (!zeiten.contains(zeit))
			zeiten.add(zeit);
	}
	
	
	public int getPassVonBus(int zeit, String busname) {
		return passBus.get(zeit).get(busname);
	}
	
	public int getPassVonHaltestellen(int zeit, Haltestelle haltestelle) {
		return passHaltestellen.get(zeit).get(haltestelle);
	}
	
	
	private int getIntTime(double timestamp) {
		return (int) (timestamp*60*1000)/factor;
	}
	
	public ArrayList<String> getNeueBusse(int zeit) {
		return neueBusse.get(zeit);
	}
	
	public Buslinie getBuslinieVonBus(String busname) {
		return busLinien.get(busname);
	}
	
	public ArrayList<Integer> getEreignisZeitpunkte() {
		return zeiten;
	}
	
	public Set<String> getBusseMitPassagiere(int timestamp) {
		if (passBus.get(timestamp) == null)
			return null;
		
		return passBus.get(timestamp).keySet();
	}

	public Set<Haltestelle> getHaltestellenMitPassagiere(int timestamp) {
		if (passHaltestellen.get(timestamp) == null)
			return null;
		
		return passHaltestellen.get(timestamp).keySet();
	}

	public void reset() {
		zeiten = new ArrayList<Integer>();
	}
}
