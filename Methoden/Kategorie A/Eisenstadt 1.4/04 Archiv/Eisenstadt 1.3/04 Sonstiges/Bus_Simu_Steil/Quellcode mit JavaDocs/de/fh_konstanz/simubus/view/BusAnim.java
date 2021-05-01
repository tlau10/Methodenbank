package de.fh_konstanz.simubus.view;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuKonfiguration;



public class BusAnim implements Runnable{
	
	private AnimationView anim;
	private Buslinie linie;
	private String busname;
	private int pause;
	private int loop;
	private ArrayList<Point> pfad;
	private List<Haltestelle> haltestelle;
	private boolean allDone = false;
	private double fahrzeit;
	private int factor;
	
	private Thread th;
	
	public BusAnim(AnimationView anim, Buslinie linie, String busname){
		this.anim = anim;
		this.linie = linie;
		this.busname = busname;
		this.haltestelle = linie.getHaltestellen();
		
		this.pfad = linie.getPfad();
		this.fahrzeit = linie.getZeitBisHaltestelle(haltestelle.get(haltestelle.size()-1)).getTimeValue();
		this.factor = SimuKonfiguration.getInstance().getSimulationsgeschwindigkeit();
		this.loop = 0;
	}
		
		
	public void run(){
		pause = (int) (fahrzeit*60*1000) / ((pfad.size())*factor);
		
		while(true){
			if (allDone)
				return;
			
			if (pfad.size() > loop) {
				anim.setAktuellePosition(this, pfad.get(loop++));
			}

			else if (loop > pfad.size()) {
				anim.removeBusAnim(this, busname);
				anim.repaint();
				return;
			}

			// Bevor der Bus das System verlässt, wird er für 2sec auf der Zielposition gezeichnet 
			else if (pfad.size() == loop) {
				anim.setAktuellePosition(this, pfad.get(loop-1));
				loop++;
				pause = 2000;
			}
			
			
			anim.repaint();			
			
			try{
				Thread.sleep(pause);
			}
			catch (InterruptedException e){				
			}
		}
	}
	
	public void start(){
		th = new Thread(this);
		th.start();
	}
	
	// Wenn das Animationsfenster geschlossen wird, werden alle Threads gestoppt
	public void stopThread() {
		allDone = true;
		// Nötig???
		th.interrupt();		
		Thread.currentThread().interrupt();		
	}
}
