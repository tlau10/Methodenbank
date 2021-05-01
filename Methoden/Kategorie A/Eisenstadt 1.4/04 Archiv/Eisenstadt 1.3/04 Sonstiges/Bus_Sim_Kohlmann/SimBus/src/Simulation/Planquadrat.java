package Simulation;

public class Planquadrat  
{
	private int id;
	private int xKoordinate;
	private int yKoordinate;
	private String name;
    
	private double abstandNord;
	private double abstandOst;
	private double abstandWest;
	private double abstandSued;
	private double abstandNordWest;
	private double abstandNordOst;
	private double abstandSuedWest;
	private double abstandSuedOst;

	private boolean Haltestelle;
	private double EWPersonenKommen;
	private double SAPersonenKommen;
	private double EWPersonenZiel;
	private double SAPersonenZiel;
			
	public Planquadrat(int _id, int x, int y) 
	{
		id = _id;
		xKoordinate = x;
		yKoordinate = y;
			
		abstandNord = 0;
		abstandOst = 0;
		abstandSued = 0;
		abstandWest = 0;
		abstandNordOst = 0;
		abstandSuedOst = 0;
		abstandNordWest = 0;
		abstandSuedWest = 0;
		
		EWPersonenKommen = 0;
		SAPersonenKommen = 0;
		EWPersonenZiel = 0;
		SAPersonenZiel = 0;
	}
	
	
	public boolean holeHaltestelle()
	{
		return Haltestelle;
	}
	
	public void setzeHaltestelle(boolean h)
	{
		Haltestelle = h;
	}
	
	
	public double holeEWPersonenKommen()
	{
		return EWPersonenKommen;
	}
	
	public double holeSAPersonenKommen()
	{
		return SAPersonenKommen;
	}
	
	
	public void setzeEWPersonenKommen(double a)
	{
		EWPersonenKommen = a;
	}
	
	public void setzeSAPersonenKommen(double a)
	{
		SAPersonenKommen = a;
	}
	
	public void setzeEWPersonenZiel(double ew)
	{
		EWPersonenZiel = ew;
	}
	
	public void setzeSAPersonenZiel(double sa)
	{
		SAPersonenZiel = sa;
	}
	
	public double holeEWPersonenZiel()
	{
		return EWPersonenZiel;
	}
	
	public double holeSAPersonenZiel()
	{
		return SAPersonenZiel;
	}
	
	public int holeID()
    {
    	return id;
    }
    
    public int holeX() 
    {        
        return xKoordinate;
    } 

    public int holeY() 
    {        
        return yKoordinate;
    }
    
    public String holeName()
    {
    	return name;
    }
    
    public void setzeName(String _name)
    {
    	name = _name;
    }
    
    public double holeAbstandNord()
    {
            return abstandNord;
    }
    
    public double holeAbstandOst()
    {
            return abstandOst;
    }
    
    public double holeAbstandWest()
    {
            return abstandWest;
    }
   
    public double holeAbstandSued()
    {
            return abstandSued;
    }
    
    public double holeAbstandSuedWest() 
    { 
    	return abstandSuedWest;   
    }
    
    public double holeAbstandSuedOst()  
    {
    	return abstandSuedOst;    
    }  
    
    public double holeAbstandNordWest() 
    {
    	return abstandNordWest;   
    } 
    
    public double holeAbstandNordOst()  
    { 
    	return abstandNordOst;    
    } 
    
    public void setzeAbstandNord(double wert) 
    { 
    	abstandNord = wert;  
    }
    
    public void setzeAbstandSued(double wert) 
    { 
    	abstandSued = wert;  
    }
    
    public void setzeAbstandOst(double wert)  
    { 
    	abstandOst = wert;   
    }
    
    public void setzeAbstandWest(double wert) 
    { 
    	abstandWest = wert;   
    }
        
    public void setzeAbstandSuedOst(double wert)
    { 
    	abstandSuedOst = wert;  
    }
    
    public void setzeAbstandSuedWest(double wert)
    { 
    	abstandSuedWest = wert;  
    }
    
    public void setzeAbstandNordWest(double wert)
    { 
    	abstandNordWest = wert;  
    }
    
    public void setzeAbstandNordOst(double wert)
    { 
    	abstandNordOst = wert;  
    }
	
}
