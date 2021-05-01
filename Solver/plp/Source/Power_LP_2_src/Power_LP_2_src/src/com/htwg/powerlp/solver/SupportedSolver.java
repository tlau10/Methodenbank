package com.htwg.powerlp.solver;

/**
 * Lists supported solvers 
 * 
 * @author Bastian Schoettle
 *
 */
public enum SupportedSolver {

	LP_SOLVE("LP_Solve", 1), GLPK("GLPK", 2), WEIDENAUER("Weidenauer", 3), CPLEX("ILOG_CPLEX", 4),GUROBI("Gurobi",5), CBC("CBC",6);

	private String solver;
	private int id;

	/**
	 * @param solver 
	 * @param id
	 */
	private SupportedSolver(String solver, int id) {
		this.solver = solver;
		this.id = id;
	}
	
	/**
	 * @return solver id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getSolver() {
		return solver;
	}
}