package Simulation;

import java.util.ArrayList;
import java.util.Iterator;

import SimulationVisuell.SimuStatistikView;

public class PersonenWS 
{
    private ArrayList personenListe;
    private Planquadrat planQuadrat;
    private PersonenFabrik personenFabrik;
    private Zufallszahl z;
    private MatrixController matrixController;
    private SimuStatistikView _simuStatView;
    
   
    public PersonenWS(Planquadrat _q, MatrixController m)
    {
    	matrixController = m;
    	planQuadrat = (Planquadrat)_q;
    	personenListe = new ArrayList();
    	z = new Zufallszahl();
    }
    
    public void erzeuge(double Zeitpunkt, ArrayList ziele, SimuStatistikView simuStatView)
    {
    	
    	this._simuStatView = simuStatView;
    	personenFabrik = new PersonenFabrik();
    	    	
    	double Anzahl = z.normalverteilteZufallszahl(planQuadrat.holeEWPersonenKommen(),planQuadrat.holeSAPersonenKommen());
    	
    	for (int i = 0; i < Math.round(Anzahl); i++)
    	{
    		int zielID = zielAuswahl(ziele);
    		double zielHaltestelleArray[] = matrixController.ermittleBesteHaltestelle(zielID);
			int zielHaltestelle = (int)zielHaltestelleArray[0];
    		
    		Person p = personenFabrik.erzeuge(Zeitpunkt,planQuadrat.holeID(),
    				zielID,zielHaltestelle);
    		personenListe.add(p);
    		simuStatView.setAnzahlPersonenimSystem();
    		simuStatView.setAnzahlPersonenGesamt();
    		
    	}
    	//this._simuStatView.gebeEventsAus((int)Anzahl +" Personen wurden erzeugt");
    }
    
    public Planquadrat getPlanquadrat()
    {
    	return planQuadrat;
    }
    
    public ArrayList holePersonenListe()
    {
    	return personenListe;
    }
    
    public int zielAuswahl(ArrayList ziele)
    {
    	Iterator it = ziele.iterator();
    	
    	double wertZiel = 0;
		double neuesWertZiel = 0;
		int gewaehltesZiel = 0;
		
    	while (it.hasNext())
    	{
    		Planquadrat ziel = (Planquadrat) it.next();
    		
    		if (gewaehltesZiel == 0)
    		{
    			wertZiel = z.normalverteilteZufallszahl(ziel.holeEWPersonenZiel(),
    					ziel.holeSAPersonenZiel());
    			gewaehltesZiel = ziel.holeID();
    		}
    		else
    		{
    			neuesWertZiel = z.normalverteilteZufallszahl(ziel.holeEWPersonenZiel(),
    					ziel.holeSAPersonenZiel());
    			
    			if (neuesWertZiel > wertZiel)
    			{
    				gewaehltesZiel = ziel.holeID();
    			}
    		}
    	}
    	
    	return gewaehltesZiel;
    }

	public Planquadrat getPlanQuadrat() {
		return planQuadrat;
	}

	public void setPlanQuadrat(Planquadrat planQuadrat) {
		this.planQuadrat = planQuadrat;
	}
}
