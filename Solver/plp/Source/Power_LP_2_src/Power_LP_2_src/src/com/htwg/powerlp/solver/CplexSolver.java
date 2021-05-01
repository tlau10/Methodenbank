package com.htwg.powerlp.solver;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import com.htwg.powerlp.file.MpsFileManager;
import com.htwg.powerlp.util.CommonUtils;
import com.htwg.powerlp.util.Configurator;

/**
 * @author schobast
 *
 */
public class CplexSolver extends AbstractSolver {
	
	public static final String CPLEX_EXE = "cplex.exe";

	/**
	 * @param inputFile
	 * @param outputFile
	 */
	public CplexSolver(String content, ObjectiveType type, Configurator configurator) {
		super(content, type, configurator);
		this.workingDirectory = configurator.getCplexDir();
		this.fileManager = new MpsFileManager(workingDirectory);
		this.inputFile = this.workingDirectory + configurator.getModelFile();
		this.outputFile = this.workingDirectory + configurator.getResultFile();
		this.solverParams = new String[] {workingDirectory + CPLEX_EXE, "-c","read ", inputFile + fileManager.getExtension(), "change sense ZF " + type.toString().toLowerCase() ,
				"optimize", "write " + outputFile + " sol" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.solver.AbstractSolver#solve()
	 */
	@Override
	public String solve() {
		generateModelFile();
		String ret = null;
		try {
			ret = this.execRuntimeSolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String transOut = outputFile + "_trans";
		try {
			CommonUtils.transform(outputFile, transOut);
		} catch (FileNotFoundException | TransformerException e) {
			e.printStackTrace();
		}
		try {
			ret = readFile(transOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clean(inputFile);
		clean(outputFile);
		clean("cplex.log");
		return ret;
	}
	
	

}
