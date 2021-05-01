package Simulation;

import java.util.ArrayList;

public class BusImSystem 
{
	private int id;
	private int linieID;
	private int kapazitaet;
	private int intervall;
	private int startZeit;
	
	private ArrayList PersonenWs;
	private ArrayList HaltestellenListe;
	private ArrayList route;
	
	private double erreichtNextPQ;
	private int aktuellerIndex;
	private boolean bewegung;
	private boolean inBetrieb;
		
	
	public BusImSystem()
	{
		PersonenWs = new ArrayList();
		aktuellerIndex = 0;
		inBetrieb = true;
	}
	
	public void setBus(Bus b)
	{
		this.setId(b.getId());
		this.setLinieID(b.getLinieID());
		this.setKapazitaet(b.getKapazitaet());
		this.setInterval(b.getInterval());
		this.setHaltstellen(b.getBusHaltestellenListe());
		this.setRoute(b.getRoute());
	}
		
	public Planquadrat getAktuellerPlanquadrat() 
	{
		return (Planquadrat)route.get(aktuellerIndex);
	}
	
	public Planquadrat getNaechstesPlanquadrat() 
	{
		return (Planquadrat)route.get(aktuellerIndex+1);
	}
	
	public boolean istHaltestelle()
	{
		return (HaltestellenListe.contains(getAktuellerPlanquadrat()));
	}
	
	public boolean istLetztesRoutenElement()
	{
		return (aktuellerIndex == (route.size() - 1));
	}

	public ArrayList getPersonenWs() 
	{
		return PersonenWs;
	}

	public void setPersonenWs(ArrayList personenWs) 
	{
		PersonenWs = personenWs;
	}

	public ArrayList getHaltestellen()
	{
		return HaltestellenListe;
	}
	
	public double getBewegungsZeitpunkte() 
	{
		return erreichtNextPQ;
	}
	
	public void fuegePersonHinzu(Person p)
	{
		PersonenWs.add(p);
	}

	public void setBewegungszeitpunkt(double zeitpunkt)
	{
		this.erreichtNextPQ = zeitpunkt;
	}

	public int getAktuellerIndex() 
	{
		return aktuellerIndex;
	}

	public void setAktuellerIndex() 
	{
		aktuellerIndex++;
	}

	public boolean isBewegung() 
	{
		return bewegung;
	}

	public void setBewegung(boolean bewegung) 
	{
		this.bewegung = bewegung;
	}
	
	public void setHaltstellen(ArrayList busHaltestellenListe) 
	{
		HaltestellenListe = busHaltestellenListe;
	}

	public boolean isInBetrieb() 
	{
		return inBetrieb;
	}

	public void setInBetrieb(boolean b) 
	{
		inBetrieb = b;
	}

	public void entfernePersonen(ArrayList p) 
	{
		PersonenWs.removeAll(p);		
	}
	
	public boolean addRoute(Planquadrat planquadratObjekt) 
	{
		if(route.add(planquadratObjekt))
		{
			return true;
		}
		return false;
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}

	public int getKapazitaet() 
	{
		return kapazitaet;
	}

	public void setKapazitaet(int _kapazitaet) 
	{
		kapazitaet = _kapazitaet;
	}

	public ArrayList getRoute() 
	{
		return route;
	}

	public void setRoute(ArrayList _route) 
	{
		route = _route;
	}

	public int getStartZeit() 
	{
		return startZeit;
	}

	public void setStartZeit(int _startZeit) 
	{
		startZeit = _startZeit;
	}
	
	public int getInterval()
	{
		return intervall;
	}
	
	public void setInterval(int _intervall)
	{
		intervall = _intervall;
	}

	public int getLinieID() 
	{
		return linieID;
	}

	public void setLinieID(int _linieID) 
	{
		linieID = _linieID;
	}
	
	public Planquadrat getRouteElement(int index)
	{
		return (Planquadrat) this.getRoute().get(index);
	}
}
