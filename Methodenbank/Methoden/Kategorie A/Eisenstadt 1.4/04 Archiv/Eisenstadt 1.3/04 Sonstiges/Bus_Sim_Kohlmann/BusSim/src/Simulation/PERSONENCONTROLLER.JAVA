package Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import SimulationVisuell.SimuStatistikView;

public class PersonenController 
{
    private ArrayList personenWSListe;
    private PersonenFabrik personenFabrik;
    private MatrixController matrixController;
    private SimuStatistikView simuStatView;
    private int AnzahlPersonenGeradeimSystem=0;

    
    public PersonenController(MatrixController m)
    {
    	matrixController = m;
    	
    }
    
    public void erzeugePersonenWS(ArrayList quellen)
    {
    	personenWSListe = new ArrayList();
    	
    	Iterator it = quellen.iterator();
    	while (it.hasNext())
    	{
    		Planquadrat q = (Planquadrat) it.next();
    		PersonenWS personenWS = new PersonenWS(q, matrixController);
    		
	
    		personenWSListe.add(personenWS);	
    	}
    }
    
    public void erzeugePersonen(double Zeitpunkt, ArrayList ziele)
    {
    	Iterator it = personenWSListe.iterator();
    	
    	while (it.hasNext())
    	{
    		PersonenWS pWS = (PersonenWS) it.next();
    		
    		pWS.erzeuge(Zeitpunkt,ziele,simuStatView);
    		
    	}
    }
    
    public ArrayList getPersonenWSListe() 
    {
		return personenWSListe;
	}

	public void setViewStatistik(SimuStatistikView statistikView) 
	{
		this.simuStatView = statistikView;
	}
	
	public void beladeBus(BusImSystem b) 
	{
		// Belade Personen in Bus 
		Planquadrat pQ = b.getAktuellerPlanquadrat();
		int anzahlZustieg=0;
		int HSid=0;
		
		Iterator it = personenWSListe.iterator();
		
		while (it.hasNext())
		{
			PersonenWS pWS = (PersonenWS) it.next();
			
			if (pWS.getPlanquadrat() == b.getAktuellerPlanquadrat())
			{
				ArrayList personenListe = pWS.holePersonenListe();
				
				Iterator it2 = personenListe.iterator();
				
				while (it2.hasNext())
				{
					Person p = p = (Person) it2.next();
					
					b.fuegePersonHinzu(p);
					anzahlZustieg++;
				}
			}
		}
		
		if(anzahlZustieg >0)
		{
			simuStatView.gebeEventsAus(anzahlZustieg + " Personen sind eingestiegen");
		}
	}
	
	public void entladeBus(BusImSystem b) 
	{
		// Entladen, wenn Personen im Bus, die an der aktuellen Haltestelle heraus 
		// wollen
		
		Planquadrat pQ = b.getAktuellerPlanquadrat();
		ArrayList pL = b.getPersonenWs();
		ArrayList pL2 = new ArrayList();
		Iterator it = pL.iterator();
		int anzahlAusstieg=0;
		int HSid=0;
		
		while (it.hasNext())
		{
			Person p = (Person) it.next();
			
			if (p.holeIDZielHaltestelle() == pQ.holeID())
			{
				pL2.add(p);
				anzahlAusstieg++;
				simuStatView.removeAnzahlPersonimSystem();
				HSid=p.holeIDZielHaltestelle();
			}
		}
		
		b.entfernePersonen(pL2);
		if(anzahlAusstieg>0)
		{
			simuStatView.gebeEventsAus(anzahlAusstieg +" Personen sind an Haltstelle " + HSid +" ausgestiegen");
		}
		
	}
}
