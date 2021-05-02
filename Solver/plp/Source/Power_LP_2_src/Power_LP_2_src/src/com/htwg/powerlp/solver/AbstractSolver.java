package com.htwg.powerlp.solver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.htwg.powerlp.file.AbstractFileManager;
import com.htwg.powerlp.file.MpsFileManager;
import com.htwg.powerlp.util.Configurator;

/**
 * Super class for all solver instances
 * 
 * @author Bastian Schoettle
 * 
 */
public abstract class AbstractSolver {

	/**
	 * @author Bastian Schoettle
	 * 
	 *         Stores objective function types (MIN | MAX)
	 * 
	 */
	public enum ObjectiveType {
		MIN, MAX;
	}

	protected class Model {

		private String content;
		private boolean isMIP;

		/**
		 * 
		 */
		public Model(String content) {
			this.content = content;
			System.out.println(content);
			if (this.getContent().contains("INTORG")) {
				this.isMIP = true;
			} else {
				this.isMIP = false;
			}
		}

		public String getContent() {
			return content;
		}

		public boolean isMIP() {
			return isMIP;
		}

	}

	/**
	 * Input model file such as .mat or .mps
	 */
	protected String inputFile;

	/**
	 * Output file to retrieve solver instance's output
	 */
	protected String outputFile;

	/**
	 * Process execution working directory
	 */
	protected String workingDirectory;

	/**
	 * Specific solver's command-line parameters
	 */
	protected String[] solverParams;

	/**
	 * Type of objective function
	 */
	protected ObjectiveType type;
	/**
	 * Instance of current session's configuration
	 */
	protected Configurator configurator;

	/**
	 * FileManager to handle file extensions
	 */
	protected AbstractFileManager fileManager;
	
	protected Model model;

	/**
	 * Creates instance of specific solver with inputFile as path to input file
	 * and outputFile for output file
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public AbstractSolver(String content, ObjectiveType type,
			Configurator configurator) {
		this.type = type;
		this.configurator = configurator;
		this.model = new Model(content);
	}

	/**
	 * Creates runtime process for solver instance and executes it with
	 * specified parameters
	 * 
	 * @return command line output as String
	 * @throws IOException
	 */
	protected String execRuntimeSolver() throws IOException {
		System.out.println(Arrays.toString(solverParams));
		Process p = Runtime.getRuntime().exec(solverParams, new String[] {},
				new File(workingDirectory));
		BufferedReader br = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String in = null, ret = "";
		while ((in = br.readLine()) != null) {
			ret += in + "\r\n";
			System.out.println(in);
		}
		br.close();
		return ret;
	}

	/**
	 * Solves problem instance
	 * 
	 * @return solver solution as String
	 */
	public abstract String solve();

	/**
	 * Saves model file to hard disk
	 * 
	 * @return recently created file
	 */
	protected File generateModelFile() {
		AbstractFileManager fileManager = new MpsFileManager(inputFile);
		try {
			fileManager.createFile(model.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileManager.getCreatedFile();
	}

	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	protected String readFile(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null, content = "";
		while ((line = br.readLine()) != null) {
			content += line + "\n";
		}
		br.close();
		file.delete();
		return content;
	}

	/**
	 * @param toClean
	 */
	protected void clean(String... toClean) {
		for (int i = 0; i < toClean.length; i++) {
			new File(toClean[i]).delete();
		}
	}
}
