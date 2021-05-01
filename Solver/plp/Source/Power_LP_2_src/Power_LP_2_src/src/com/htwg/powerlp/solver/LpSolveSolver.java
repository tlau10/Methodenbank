package com.htwg.powerlp.solver;

import java.io.IOException;

import com.htwg.powerlp.util.Configurator;

/**
 * @author Bastian Schoettle
 *
 */
public class LpSolveSolver extends AbstractSolver {

	/**
	 * @param
	 * @param outputFile
	 * @param type
	 */
	public LpSolveSolver(String content, ObjectiveType type, Configurator configurator) {
		super(content, type, configurator);
		this.workingDirectory = configurator.getLpSolveDir();
		this.inputFile = this.workingDirectory + configurator.getModelFile();
		this.outputFile = this.workingDirectory + configurator.getResultFile();
		String sense = "-max";
		if (type == ObjectiveType.MIN) {
			sense = "-min";
		}
		this.solverParams = new String[] { workingDirectory + configurator.getLPSolveExe(),  "-plp", "-s6","-S6", sense,
				"-mps", inputFile + ".mps" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.powerlp.solver.AbstractSolver#solve()
	 */
	@Override
	public String solve() {
		this.inputFile = generateModelFile().getAbsolutePath();
		String ret = null;
		try {
			ret = execRuntimeSolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clean(inputFile);
		return ret;

	}

}
