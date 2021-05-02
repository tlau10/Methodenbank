package de.fh_konstanz.simubus.view;

import java.util.ArrayList;
import java.util.Set;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuResults;


public class AnimThread implements Runnable{
	
	private AnimationView anim;
	private Gesamtfahrplan plan = Gesamtfahrplan.getInstance();
	
	private boolean allDone = false;
	private Thread th;
	private SimuResults results;
	private ArrayList<Integer> zeiten;
	
	private int i = 0;
	private int index = 0;
	
	public AnimThread(AnimationView anim){
		this.anim = anim;
		results = SimuResults.getInstance();
		zeiten = results.getEreignisZeitpunkte();
	}
		
		
	public void run(){
		while(true){
			if (allDone)
				return;
			
			i = zeiten.get(index);
			
			if (zeiten.contains(i)) {
				System.out.println("Zeit: " +i);
				ArrayList<String> list = results.getNeueBusse(i);
				if (list != null) {
					Buslinie linie;
					for (String busname : list) {
						linie = results.getBuslinieVonBus(busname);
						anim.addBusAnim(linie, busname);
					}
				}

				Set<String> busseMitPass = results.getBusseMitPassagiere(i);
				if (busseMitPass != null) {
					for (String busname : busseMitPass) {
						System.out.println(busname +" : " +results.getPassVonBus(i, busname));
						anim.setAnzahlPassagierVonBus(busname, results.getPassVonBus(i, busname));					
					}
				}
				
				Set<Haltestelle> haltestellenMitPass = results.getHaltestellenMitPassagiere(i);
				if (haltestellenMitPass != null) {
					for (Haltestelle h : haltestellenMitPass) {
						System.out.println(h.getName() +" : " +results.getPassVonHaltestellen(i, h));
						anim.setAnzahlPassagierVonHaltestelle(h, results.getPassVonHaltestellen(i, h));
					}
				}				
			}
			
			if (zeiten.size() == index)
				return;
			
			try{
				Thread.sleep(zeiten.get(index+1) - zeiten.get(index++));
			}
			catch (InterruptedException e){				
			}
		}
	}
	
	public void start(){
		th = new Thread(this);
		th.start();
	}
	
	// Wenn das Animationsfenster geschlossen wird, wird der Thread gestoppt
	public void stopThread() {
		allDone = true;
		// Nötig???
		th.interrupt();		
		Thread.currentThread().interrupt();		
	}
}
