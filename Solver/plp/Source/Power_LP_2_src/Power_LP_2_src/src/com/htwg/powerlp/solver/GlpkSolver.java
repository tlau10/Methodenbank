package com.htwg.powerlp.solver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.htwg.powerlp.file.MpsFileManager;
import com.htwg.powerlp.util.Configurator;

/**
 * @author schobast
 *
 */
public class GlpkSolver extends AbstractSolver {

	/**
	 * 
	 */
	public static final String GLPK_EXE = "glpsol.exe";
	/**
	 * 
	 */
	private static final String MIP_SENSITIVITY_ERROR = "Cannot produce sensitivity analysis report for interior-point or MIP solution";
	/**
	 * 
	 */
	private String[] outfiles;

	/**
	 * 
	 */
	private boolean isMipModel;

	/**
	 * @param inputFile
	 * @param outputFile
	 * @param type
	 */
	public GlpkSolver(String content, ObjectiveType type, Configurator configurator) {
		super(content, type, configurator);
		this.workingDirectory = configurator.getGlpkDir();
		this.fileManager = new MpsFileManager(workingDirectory);
		this.inputFile = this.workingDirectory + configurator.getModelFile();
		this.outputFile = this.workingDirectory + configurator.getResultFile();
		outfiles = new String[] { this.workingDirectory + configurator.getResultFile() + "_sensitivity",
				this.workingDirectory + configurator.getResultFile() };
		String objType = "--min";
		if (type == ObjectiveType.MAX) {
			objType = "--max";
		}
		this.solverParams = new String[] { workingDirectory + GLPK_EXE, "--freemps",
				inputFile + fileManager.getExtension(), objType, "--ranges", outfiles[0], "-o", outfiles[1] };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.powerlp.solver.AbstractSolver#execRuntimeSolver()
	 */
	@Override
	protected String execRuntimeSolver() throws IOException {
		Process p = Runtime.getRuntime().exec(solverParams, new String[] {}, new File(workingDirectory));
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String in = null, ret = "";
		while ((in = br.readLine()) != null) {
			if (in.contains(MIP_SENSITIVITY_ERROR)) {
				isMipModel = true;
			}
			ret += in + "\r\n";
		}
		br.close();
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.powerlp.solver.AbstractSolver#solve()
	 */
	@Override
	public String solve() {
		generateModelFile();
		try {
			execRuntimeSolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
		do {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!filesExists());
		String ret = "";
		for (String string : outfiles) {
			try {
				ret += readFile(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		clean(outfiles);
		clean(inputFile);
		return ret;
	}

	private boolean filesExists() {
		if (isMipModel) {
			return new File(outfiles[1]).exists();
		} else {
			return (new File(outfiles[0]).exists() && new File(outfiles[1]).exists());
		}
	}
}
