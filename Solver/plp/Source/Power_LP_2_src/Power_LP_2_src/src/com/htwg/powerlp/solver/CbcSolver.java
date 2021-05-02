/*************************************************************************
 * 
 * CONFIDENTIAL
 * __________________
 * 
 *  [2016] Bastian Schoettle & Tim Schoettle
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Bastian Schoettle & Tim Schoettle and his suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Bastian Schoettle & Tim Schoettle
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Bastian Schoettle & Tim Schoettle.
 *
 */
package com.htwg.powerlp.solver;

import java.io.IOException;

import com.htwg.powerlp.file.MpsFileManager;
import com.htwg.powerlp.util.Configurator;

/**
 * @author schobast
 */
public class CbcSolver extends AbstractSolver {

	public static final String CBC_EXE = "cbc.exe";

	/**
	 * @param content
	 * @param type
	 * @param configurator
	 */
	public CbcSolver(String content, ObjectiveType type, Configurator configurator) {
		super(content, type, configurator);
		this.workingDirectory = configurator.getCbcDir();
		this.fileManager = new MpsFileManager(workingDirectory);
		this.inputFile = this.workingDirectory + configurator.getModelFile();
		this.outputFile = this.workingDirectory + configurator.getResultFile();
		this.solverParams = new String[] { workingDirectory + CBC_EXE, inputFile + fileManager.getExtension(),
				"-" + type.toString().toLowerCase(), "-solve", "-solution", configurator.getResultFile() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.powerlp.solver.AbstractSolver#solve()
	 */
	@Override
	public String solve() {
		this.inputFile = generateModelFile().getAbsolutePath();
		try {
			execRuntimeSolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String ret = null;
		try {
			ret = readFile(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clean(inputFile);
		return ret;
	}

}
