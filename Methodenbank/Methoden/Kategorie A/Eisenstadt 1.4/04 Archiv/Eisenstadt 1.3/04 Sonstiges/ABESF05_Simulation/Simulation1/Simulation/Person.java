package Simulation;

public class Person 
{
    private double ZeitpunktEintritt;
    private double ZeitpunktAustritt;
    private int IDQuelle;
    private int IDZielHaltestelle;
    private int IDZiel;
    private int PersonID;
    
    public Person()
    {
    	
    }
    
    public Person(double eintritt, int idQ, int idZ, int idZH)
    {
    	ZeitpunktEintritt = eintritt;
    	ZeitpunktAustritt = 0;
    	IDQuelle = idQ;
    	IDZiel = idZ;
    	IDZielHaltestelle = idZH;
    }
    
    public double holeZeitpunktEintritt()
    {
    	return ZeitpunktEintritt;
    }
    
    public double holeZeitpunktAustritt()
    {
    	return ZeitpunktAustritt;
    }
    
    public int holeIDQuelle()
    {
    	return IDQuelle;
    }
    
    public int holeIDZiel()
    {
    	return IDZiel;
    }
    
    public int holeIDZielHaltestelle()
    {
    	return IDZielHaltestelle;
    }
    
}
