package Simulation;

import java.util.ArrayList;

public class Bus 
{
	private int id;
	private int linieID;
	private int kapazitaet;
	private int intervall;
	private ArrayList route;
	private ArrayList busHaltestellenListe;
	
	public Bus()
	{
		
	}
	
	public Bus(int _id)
	{
		id = _id;
		route = new ArrayList();
		busHaltestellenListe = new ArrayList();
	}
	
	public boolean addRoute(Planquadrat planquadratObjekt) 
	{
		if (route.add(planquadratObjekt))
		{
			return true;
		}
		return false;
	}
	
	
	public boolean addHaltestelle(Planquadrat planquadratObjekt) 
	{
		if(planquadratObjekt!=null)
		{
			this.busHaltestellenListe.add(planquadratObjekt);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int _id) 
	{
		id = _id;
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

	public ArrayList getBusHaltestellenListe() 
	{
		return busHaltestellenListe;
	}

	public void setBusHaltestellenListe(ArrayList _busHaltestellenListe) 
	{
		busHaltestellenListe = _busHaltestellenListe;
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

	public void setLinieID(int linieID) 
	{
		this.linieID = linieID;
	}
	
	public void deleteRoute() 
	{
		route = new ArrayList();
	}
	
	public void deleteHaltestellen() 
	{
		busHaltestellenListe = new ArrayList();
	}
}
