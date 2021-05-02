package com.htwg.powerlp.solver;

import java.io.IOException;

import com.htwg.powerlp.file.MpsFileManager;
import com.htwg.powerlp.util.Configurator;

/**
 * @author schobast
 *
 */
public class GurobiSolver extends AbstractSolver {

	/**
	 * 
	 */
	public static final String GUROBI_EXE = "gurobi.exe";

	/**
	 * @param inputFile
	 * @param outputFile
	 * @param type
	 */
	public GurobiSolver(String content,ObjectiveType type, Configurator configurator) {
		super("objsense \n" + type.toString().toLowerCase() + content, type, configurator);
		this.workingDirectory = configurator.getGurobiDir();
		fileManager = new MpsFileManager(workingDirectory);
		this.inputFile = this.workingDirectory + configurator.getModelFile() + fileManager.getExtension();
		this.outputFile = this.workingDirectory + configurator.getResultFile();
		this.solverParams = new String[] { workingDirectory + GUROBI_EXE, "ResultFile=" + outputFile + ".sol", inputFile};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.powerlp.solver.AbstractSolver#solve()
	 */
	@Override
	public String solve() {
		generateModelFile();
		String ret = null;
		try {
			ret = execRuntimeSolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ret = readFile(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clean(inputFile);
		return ret;
	}

}
