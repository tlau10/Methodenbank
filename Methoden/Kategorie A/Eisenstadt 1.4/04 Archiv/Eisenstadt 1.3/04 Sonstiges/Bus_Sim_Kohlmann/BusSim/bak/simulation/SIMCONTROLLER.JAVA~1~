package Simulation;

import java.util.ArrayList;

import SimulationVisuell.SimuStatistikView;
import SimulationVisuell.SimuViewController;

public class SimController 
{
    private MatrixController matrixController;
    private PersonenController personenController;
    private BusController busController;
    private SimuViewController simuViewController;
    private SimuStatistikView _simuStatView;

	
    public SimController()
    {
    	matrixController = new MatrixController();

    	personenController = new PersonenController(matrixController);
    	busController = new BusController(matrixController,personenController);
    	simuViewController = new SimuViewController(matrixController,this, busController); 
    	simuViewController.erzeugeSimuStatistik("Statistik");
    	this._simuStatView = simuViewController.getStatistikView();
    	uebergabeViewStatistikanBusController();
    	uebergabeViewStatistikanPersonController();
    }
    
  

	public void starte()
    {
    	simuViewController.erzeugeSimuView("Simulation");
    	simuViewController.erzeugeMenu();
		
    	simuViewController.definiereSimuView(1000,700,150,150);
    	simuViewController.anzeigenSimuView(true);

    }
    
    
    public void uebergabeViewStatistikanPersonController()
    {
    	personenController.setViewStatistik(simuViewController.getStatistikView());
    }
	   
    
    public void uebergabeViewStatistikanBusController()    
    {   	
    	busController.setViewStatistik(simuViewController.getStatistikView());
	 
    }
	    
    //M A T R I X   VERWALTUNG
    public void erzeugeMatrix(int x, int y)
    {
    	matrixController.initialisiereMatrix(x,y);
    }
    
    private ArrayList holeQuellListe()
    {
    	return matrixController.holeQuellenListe();
    }
    
    private ArrayList holeZielListe()
    {
    	return matrixController.holeZielListe();
    }
    
    public void erzeugeQuellListe()
    {
    	matrixController.erzeugeQuellListe();
    }
    
    public void erzeugeZielListe()
    {
    	matrixController.erzeugeZielListe();
    }
    
    
    //Z U R   P E R S O N E N E R Z E U G U N G
    
    public void erzeugePersonenWS()
    {
    	personenController.erzeugePersonenWS(holeQuellListe());
    }
    
    public void erzeugePersonen(double Zeitpunkt)
    {
    	personenController.erzeugePersonen(Zeitpunkt,holeZielListe());
    }
    
    
    //Z U R     B U S V E R W A L T U N G
    
    public void plaziereBusse(double d) 
    {
    	busController.plaziereBusse(holeQuellListe(),d);
	}
    
    public ArrayList holeBusListeimSystem()
    {
    	return busController.getBusListe();
    }
    
    public void uebergebeBusListe(ArrayList busListe) 
	{
		busController.setBusliste(busListe);
	}

	public void bewegeBusse(double d) 
	{
		busController.busBewegen(d);
	}
	
	public void zeichneBusse()
	{
		simuViewController.zeichneBusse();
	}
    
    	
    //W E G E M A T R I X
    
    public void erzeugeWegeMatrix()
    {
    	matrixController.erzeugeWegeMatrix();
    }
    
    
    //S I M U L A T I O N
    
    public void vorbereitenSimulation()
    {
    	erzeugeQuellListe();
		erzeugeZielListe();
    	erzeugePersonenWS();
    	modifizierePlanquadrateListe();
		erzeugeWegeMatrix();
	}
    
    public void Simulation(double z)
    {
      	String zeitpunktmeldung ="-------------------------    ZEITPUNKT: " + z + " --------------------------"; 
    	simuViewController.zeigeZeitpunktStatistik(zeitpunktmeldung);
    	_simuStatView.gebeEventsAus("Anzahl der Busse im System: " + _simuStatView.getAnzahlBusseimSystem());
       	erzeugePersonen(z);
    	_simuStatView.gebeEventsAus("Anzahl der Personen im System: " + _simuStatView.getAnzahlPersonenimSystem());
    	plaziereBusse(z);
    	bewegeBusse(z);
    	zeichneBusse();
    	_simuStatView.gebeEventsAus(" ");
    	//_simuStatView.gebeEventsAus("-------------------------------------------------------------------------");
    	simuViewController.anzeigenSimuStatistik(true);
    }
    
    //NUR ZUM TESTEN
    public void druckePlanquadrate()
    {
    	matrixController.druckeListe(matrixController.holePlanquadratListe());
    }
    
    public void modifizierePlanquadrateListe() 
	{
		matrixController.modifizierePlanquadratListe();
	}
    
    
    public void druckeQuellen()
    {
    	matrixController.druckeListe(matrixController.holeQuellenListe());
    }
    
    public void druckeZiele()
    {
    	matrixController.druckeListe(matrixController.holeZielListe());
    }
    
    public void zeigeMeldunginStatistik(String ausgabe)
    {
    	simuViewController.zeigeZeitpunktStatistik(ausgabe);
    }
}
