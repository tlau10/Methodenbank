package controller;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: EnergyNeeds
 * </p>
 * <p>
 * Description: Berechnen des Energiebedarfs
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */

public class EnergyNeeds {

	public static int energyNeeds(double weight, double height, double age,
			double work, String gender) {
		// Grundumsatz
		double gu;

		// Leistungsumsatz
		double lu;

		// Energiebedarf 0,5=leichte Tätigkeit, 0,75=mittelschwere Tätigkeit
		// 1=schwer
		double eb;
		double bmi = bmi(weight, height);

		if ((bmi > 25) || (bmi < 19))
			weight = height - 100;

		gu = basalMetabolicRate(weight, height, age, gender);
		lu = gu * work;
		eb = gu + lu;
		eb /= 4.18; // KJ in Kcal umrechnen

		return (int) Math.round(eb);
	}

	public static double bmi(double gewicht, double groesse) {
		return gewicht / (Math.pow((groesse / 100), 2));
	}

	private static double basalMetabolicRate(double weight, double height,
			double age, String gender) {
		double gu; // Grundumsatz

		if (gender.equals("m"))
			gu = 297.9
					* Math.pow(weight, 0.75)
					* (1 + 0.004 * (30 - age) + 0.010 * (height
							/ Math.pow(weight, 0.333333333) - 43.4));
		else
			gu = 275.3
					* Math.pow(weight, 0.75)
					* (1 + 0.004 * (30 - age) + 0.018 * (height
							/ Math.pow(weight, 0.333333333) - 42.1));

		return gu;
	}
}