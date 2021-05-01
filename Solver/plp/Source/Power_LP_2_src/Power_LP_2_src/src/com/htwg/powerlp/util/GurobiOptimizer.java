package com.htwg.powerlp.util;

import gurobi.*;

public class GurobiOptimizer {

	public void grbOptimize(String filePathMPS, String solverPathGurobi) {
		try {
			// Create gurobi environment object - Wird f�r die Arbeit mit Gurobi
			// gebraucht.
			GRBEnv env = new GRBEnv("src/GurobiLog");
			// Erstellung einer Variablen zum Ausf�hren von Methoden
			// �bergabe der MPS Datei die gelesen werden soll
			GRBModel model = new GRBModel(env, filePathMPS);
			// Optimierung der MPS Datei - Minimierung ist standard, Maximierung
			// muss explizit im MPS-File angegeben werden
			model.optimize();
			// Erstellung der L�sungsdatei
			model.write(solverPathGurobi + "PowerLPResult.sol");
		} catch (GRBException e) {
			e.printStackTrace();
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	public void grbSensitivity(String filePathMPS, String solverPathGurobi) {
		// Create gurobi environment object - Wird f�r die Arbeit mit Gurobi
		// gebraucht.
		try {
			GRBEnv env = new GRBEnv();
			// Erstellung einer Variablen zum Ausf�hren von Methoden
			// �bergabe der MPS Datei die gelesen werden soll
			GRBModel model = new GRBModel(env, filePathMPS);
			
		} catch (GRBException e) {
			e.printStackTrace();
		}

	}
}
