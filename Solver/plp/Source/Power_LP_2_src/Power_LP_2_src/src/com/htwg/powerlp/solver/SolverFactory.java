package com.htwg.powerlp.solver;

import com.htwg.powerlp.solver.AbstractSolver.ObjectiveType;
import com.htwg.powerlp.util.Configurator;

/**
 * @author schobast
 *
 */
public class SolverFactory {

	
	/**
	 * Private constructor to avoid instantiation
	 */
	private SolverFactory() {
	}

	/**
	 * @param supportedSolver
	 * @param type
	 * @param in
	 * @param out
	 * @return
	 */
	public static AbstractSolver createSolver(SupportedSolver supportedSolver, String content, ObjectiveType type,
			Configurator configurator) {
		AbstractSolver solver = null;
		if (supportedSolver == SupportedSolver.CPLEX) {
			solver = new CplexSolver(content, type, configurator);
		} else if (supportedSolver == SupportedSolver.GUROBI) {
			solver = new GurobiSolver(content, type, configurator);
		} else if (supportedSolver == SupportedSolver.GLPK) {
			solver = new GlpkSolver(content, type, configurator);
		} else if (supportedSolver == SupportedSolver.LP_SOLVE) {
			solver = new LpSolveSolver(content, type, configurator);
		} else if (supportedSolver == SupportedSolver.WEIDENAUER) {
			solver = new WeidenauerSolver(content, type, configurator);
		} else if (supportedSolver == SupportedSolver.CBC) {
			solver = new CbcSolver(content, type, configurator);
		}

		return solver;
	}

}
