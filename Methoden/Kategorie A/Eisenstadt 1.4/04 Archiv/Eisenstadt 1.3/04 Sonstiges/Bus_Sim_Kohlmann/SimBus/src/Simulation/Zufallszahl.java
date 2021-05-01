package Simulation;

import java.util.Date;
import java.util.Random;

public class Zufallszahl 
{
	private Random rnd;

	public Zufallszahl()
	{
		Date datum = new Date();
		rnd = new Random((int) datum.getSeconds());
	}
	
	public double normalverteilteZufallszahl(double erwartungswert, double standardabweichung)
	{
		double meineZufallszahl = 0;
		double summe = 0;
		
		for (int i = 0; i<100; i++)
		{
			summe += rnd.nextDouble();
		}

		meineZufallszahl = Math.abs(standardabweichung*((summe - 50)/Math.sqrt(100/12))+erwartungswert);
		return meineZufallszahl;
	}
}
