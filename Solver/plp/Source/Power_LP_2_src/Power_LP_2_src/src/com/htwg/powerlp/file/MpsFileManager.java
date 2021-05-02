package com.htwg.powerlp.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.htwg.powerlp.util.Configurator;

/**
 * @author schobast
 *
 */
public class MpsFileManager extends AbstractFileManager {

	/**
	 * 
	 */
	private final static String EXTENSION = ".mps";

	/**
	 * @param trgPath
	 */
	public MpsFileManager(String trgPath) {
		super(trgPath);
		this.createdFile = new File(trgPath + EXTENSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.htwg.powerlp.util.AbstractFileManager#createFile()
	 */
	@Override
	public void createFile(String content) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(createdFile));
		br.write(content);
		br.flush();
		br.close();
	}

	@Override
	public String getExtension() {
		return EXTENSION;
	}

	/**
	 * Converts mps file to LPSolve generated mps file in FREE or FIXED format
	 * @throws IOException
	 */
	public static String createMPSFile(Configurator cfg, String in, String out)
			throws IOException {
		String outformat = "-wmps";
		if (SupportedFormats.valueOf(cfg.getSaveMpsFormat().toUpperCase()) == SupportedFormats.MPS_FREE) {
			outformat = "-wfmps";
		}
		String[] params = new String[] { cfg.getLpSolveDir() + cfg.getLPSolveExe(), "-mps", in, outformat, out, "-parse_only" };
		Process p = Runtime.getRuntime().exec(params, new String[] {}, new File(cfg.getLpSolveDir()));
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null, ret = "";
		while ((line = br.readLine()) != null) {
			ret += line + "\r\n";
		}
		br.close();
		return ret;
	}

}
