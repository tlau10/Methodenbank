package hotelbelegung;
import java.math.*;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH
 * @author
 * @version 1.0
 */


public class Normalverteilung implements IFZufallszahlen{

  public Normalverteilung() {
  }

    /** Anzahl ZufallsZahlen pro Summe */
    private int n = 12;
    private double varianz = 0;
    private double erwartungswert = 0;

    public Normalverteilung(double varianz, double erwartungswert)
    {
	this.varianz=varianz;
	this.erwartungswert=erwartungswert;
    }

    public void setVarErw(double varianz, double erwartungswert)
    {
	this.varianz=varianz;
	this.erwartungswert=erwartungswert;
    }
    /**
     * Konstruktor
     * @param   double varianz  die Varianz
     * @param   double erwartungswert   der Erwartungswert
     * @param   int n  Anzahl Zufallszahlen pro Summe fuer eine Zuf.Zahl.
     */
    public Normalverteilung(double varianz, double erwartungswert, int n)
    {
	this.varianz=varianz;
	this.erwartungswert=erwartungswert;
	this.n = n;
    }

    /** Generiere eine Normalverteilte Zufallszahl */
    public double nextDouble()
    {
	double x;
	double z;
	double summe = 0;

	for(int i=0; i<n; i++)
	{
	    summe += Math.random();
	}
	x = (summe-( ((double) n) /2 ) )/(Math.sqrt( ((double) n) / 12) );
	z = varianz*x+erwartungswert;
        if(z<=0) { z=0.01; }
	return z;
    }


}