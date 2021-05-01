package com.htwg.powerlp.solver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.htwg.powerlp.file.AbstractFileManager;
import com.htwg.powerlp.file.MatFileManager;
import com.htwg.powerlp.util.Configurator;

/**
 * @author schobast
 * 
 */
public class WeidenauerSolver extends AbstractSolver {
	/**
	 * 
	 */
	public static final String WEIDENAUER_EXE = "lp.exe";
	/**
	 * 
	 */
	private static final String TEMP_FILE = "temp.CMD";

	/**
	 * Command file arguments
	 */
	private static final String[] COMMANDS = new String[] { "LOADMATRIX ", "",
			"SAVEBASIS", "GLOBAL", "SAVESOLUTION", "SAVEINTEGER", "QUIT" };

	/**
	 * @param inputFile
	 * @param outputFile
	 * @param type
	 */
	public WeidenauerSolver(String content, ObjectiveType type,
			Configurator configurator) {
		super(content, type, configurator);
		this.workingDirectory = configurator.getWeidenauerDir();
		this.inputFile = workingDirectory + configurator.getModelFile();
		this.fileManager = new MatFileManager(inputFile);
		this.solverParams = new String[] { workingDirectory + WEIDENAUER_EXE,
				TEMP_FILE };
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.powerlp.solver.AbstractSolver#solve()
	 */
	@Override
	public String solve() {
		File file = generateModelFile();
		try {
			createCommandFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			execRuntimeSolver();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = null;
		try {
			content = getSolutionContent(workingDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.delete();
		new File(TEMP_FILE).delete();
		return content;
	}

	private String getSolutionContent(String wd) throws IOException {
		String ret = "";
		File[] files = new File(wd).listFiles();
		for (File file : files) {
			if (file.getName().endsWith("CMP")) {
				file.delete();
			} else if (file.getName().endsWith("BSS")) {
				file.delete();
			} else if (file.getName().endsWith("LOS")) {
				ret += readFile(file.getAbsolutePath()) + "\n";
				if (!model.isMIP()) {
					String cut = ret.replace("INTEGER SOLUTION", "SOLUTION"); 
					System.out.println(cut);
					ret = cut;
				}
			} else if (file.getName().endsWith("INT")) {
				file.delete();

			} else if (file.getName().endsWith("PSE")) {
				file.delete();
			} else if (file.getName().endsWith(fileManager.getExtension())) {
				file.delete();
			}
		}
		return ret;
	}

	/**
	 * Creates Weidenauer specific command file
	 * 
	 * @throws IOException
	 */
	private void createCommandFile() throws IOException {
		File file = new File(workingDirectory + "\\" + TEMP_FILE);
		if (file.exists()) {
			file.delete();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		for (int i = 0; i < COMMANDS.length; i++) {
			String line = null;
			if (i == 0) {
				line = COMMANDS[i] + new File(inputFile).getName()
						+ fileManager.getExtension();
			} else if (i == 1) {
				if (type == ObjectiveType.MAX) {
					line = "MAXIMISE";
				} else {
					line = "MINIMISE";
				}
			} else {
				line = COMMANDS[i];
			}
			bw.write(line + "\r\n");
		}
		bw.close();
	}

	@Override
	protected File generateModelFile() {
		AbstractFileManager fileManager = new MatFileManager(inputFile);
		try {
			fileManager.createFile(model.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileManager.getCreatedFile();
	}
}
