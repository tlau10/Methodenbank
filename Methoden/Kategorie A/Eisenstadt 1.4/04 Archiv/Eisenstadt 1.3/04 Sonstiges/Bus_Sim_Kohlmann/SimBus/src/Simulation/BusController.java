package Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import SimulationVisuell.SimuStatistikView;

public class BusController 
{
	private MatrixController matrixController;
	private PersonenController personenController;
	
	private ArrayList busListe;
	private ArrayList busseImSystem;
	private Test_ID testID;
	private SimuStatistikView simuStatView;

		
	public BusController(MatrixController matrixC,PersonenController personenC)
	{
		matrixController = matrixC;
		personenController = personenC;
		
		busListe = new ArrayList();
		busseImSystem = new ArrayList();
		testID = new Test_ID();
		

	}
	
	public void plaziereBusse(ArrayList quellen,double d) 
	{
		Iterator it = busListe.iterator();
		
		while(it.hasNext()) 
		{
			Bus b = (Bus) it.next();
    		
			if(d%b.getInterval()==0)
    		{
    			testID.setId();
				BusImSystem busimtmp = new BusImSystem();
				busimtmp.setBus(b);
    			
				busimtmp.setStartZeit((int)d);
				busimtmp.setBewegung(false);
				busimtmp.setBewegungszeitpunkt(d + 1);
				simuStatView.setAnzahlBusseGesamt();
				simuStatView.setAnzahlBusseimSystem();
				
				
				
				personenController.beladeBus(busimtmp);
				busseImSystem.add(busimtmp);
			}		
		}		
	}
  
	
	public ArrayList getBusListe()
	{
		return this.busListe;
	}
	
	public ArrayList getBusseImSystemListe()
	{
		return this.busseImSystem;
	}
	
	public boolean busBewegen(double aktuelleSimuZeit) 
	{
		Iterator it = busseImSystem.iterator();
		Iterator it2 = busseImSystem.iterator();
			
		while(it2.hasNext())
		{
			BusImSystem b = (BusImSystem) it2.next();
			System.out.println("Bus ID: " + b.getId() + " aktIndex: " + b.getAktuellerIndex() + 
					" nZ: " + b.getBewegungsZeitpunkte() + " ist aktiv " + b.isInBetrieb() + 
					" hat " + b.getPersonenWs().size() + " Personen");
			//simuStatView.gebeEventsAus("Bus ID: " + b.getId() + " aktIndex: " + b.getAktuellerIndex() + 
				//	" nZ: " + b.getBewegungsZeitpunkte() + " ist aktiv " + b.isInBetrieb() + 
					//" hat " + b.getPersonenWs().size() + " Personen");
		}
		System.out.println("------------------------");
		
		//simuStatView.gebeEventsAus("Anzahl Busse derzeit im System: " + simuStatView.getAnzahlBusseimSystem());
			
		while(it.hasNext()) 
		{
			BusImSystem b = (BusImSystem) it.next();
			boolean bew = true;
			// Bus fährt los
			// wenn Bewegung == false 
			// und die aktuelle Zeit der gesetzten Zeit entspricht
			
			if ((b.isBewegung() == false) && (b.getBewegungsZeitpunkte() == aktuelleSimuZeit))
			{
				double naechsterZeitpunkt =  ermittleAbstaendeRoute(b,aktuelleSimuZeit);
				b.setBewegung(true);
				b.setBewegungszeitpunkt(naechsterZeitpunkt);
			}
			
			// Bus erreicht Planquadrat
			// wenn Bewegung == true 
			// und aktuelle Zeit == gesetzte Zeit
			
			if ((b.isBewegung() == true) && (b.getBewegungsZeitpunkte() == aktuelleSimuZeit))
			{
				// Planquadrat erreicht Index kann hochgesetzt werden
				b.setAktuellerIndex();
				
				// Wenn Bus auf dem letzten Element seiner Route angekommen ist
				// kann er auf ausser Betrieb gesetzt werden
				if (b.istLetztesRoutenElement())
				{
					
					b.setInBetrieb(false);
					simuStatView.removeAnzahlBusimSystem();
					simuStatView.gebeEventsAus("Bus "+ b.getId()+" hat das System verlassen");
				}
				
				// Wenn Bus auf einer Haltstelle steht wird angehalten
				if (b.istHaltestelle())
				{
					
					simuStatView.gebeEventsAus("Bus "+ b.getId()+" hält an Haltestelle " + b.getAktuellerPlanquadrat().holeID());
					b.setBewegung(false);
					b.setBewegungszeitpunkt(aktuelleSimuZeit + 1);
					
					// Wenn Personen an dieser Haltestelle sind werden diese aufgenommen
					personenController.entladeBus(b);
					personenController.beladeBus(b);
				}
				
				// Wenn Bus auf keiner Haltestelle steht wird weitergefahren
				else 
				{
					if (b.isInBetrieb())
					{
						double naechsterZeitpunkt =  ermittleAbstaendeRoute(b,aktuelleSimuZeit);
						b.setBewegung(true);
						b.setBewegungszeitpunkt(naechsterZeitpunkt);
					}
				}
			}
		}
		return true;
	}
	
	public void setBusliste(ArrayList busliste)
	{
		this.busListe = busliste;
	}
	
	public double ermittleAbstaendeRoute(BusImSystem busimtmp,double d)
	{
		Planquadrat StartPQ;
		Planquadrat NextPQ;
		int nextPQID;
		
		StartPQ = busimtmp.getAktuellerPlanquadrat();
		
		if(busimtmp.getRoute().size() > busimtmp.getAktuellerIndex()+1)
		{
			NextPQ = busimtmp.getNaechstesPlanquadrat();
			return (d + ermittleAbstand(StartPQ,NextPQ.holeID()));
		}
		
		return 0;
	}
	
	public double ermittleAbstand(Planquadrat StartPQ, int nextRoute)
	{
		if(matrixController.holeNachbarnNord(StartPQ.holeID()).holeID()==nextRoute)
		{
			return StartPQ.holeAbstandNord();
		}
		
		if(matrixController.holeNachbarnNordOst(StartPQ.holeID()).holeID() == nextRoute)
		{
			return StartPQ.holeAbstandNordOst();
		}
		
		if(matrixController.holeNachbarnOst(StartPQ.holeID()).holeID()==nextRoute)
		{
			return StartPQ.holeAbstandOst();
		}
		
		if(matrixController.holeNachbarnSuedOst(StartPQ.holeID()).holeID()==nextRoute)
		{
			return StartPQ.holeAbstandSuedOst();
		}
		
		if(matrixController.holeNachbarnSued(StartPQ.holeID()).holeID()==nextRoute)
		{
			return StartPQ.holeAbstandSued();
		}
		
		if(matrixController.holeNachbarnSuedWest(StartPQ.holeID()).holeID()==nextRoute)
		{
			return StartPQ.holeAbstandSuedWest();
		}
		
		if(matrixController.holeNachbarnWest(StartPQ.holeID()).holeID()==nextRoute)
		{
			return StartPQ.holeAbstandWest();
		}
		
		if(matrixController.holeNachbarnNordWest(StartPQ.holeID()).holeID()==nextRoute)
		{
			return StartPQ.holeAbstandNordWest();
		}
		
		return 0;
	}
	
	
	public int erzeugeBus() 
	{
		Bus b = new Bus(busListe.size());
		busListe.add(b);
		
		return b.getId();
	}
	
	public void setBusseimSystemAusView(ArrayList _busliste)
	{
		busListe = _busliste;
	}
	
	public void setViewStatistik(SimuStatistikView statistikView) 
	{
		this.simuStatView = statistikView;
	}
}
