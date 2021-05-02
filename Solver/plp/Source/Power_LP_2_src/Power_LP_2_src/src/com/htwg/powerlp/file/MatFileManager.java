package com.htwg.powerlp.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author schobast
 *
 */
public class MatFileManager extends AbstractFileManager {

	/**
	 * 
	 */
	private final static String EXTENSION = ".mat";
	
	/**
	 * @param trgPath
	 */
	public MatFileManager(String trgPath) {
		super(trgPath);
		this.createdFile = new File(trgPath + EXTENSION);
	}

	/* (non-Javadoc)
	 * @see com.htwg.powerlp.util.AbstractFileManager#createFile()
	 */
	@Override
	public void createFile(String content) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(createdFile));
		br.write(content);
		br.flush();
		br.close();
	}

	/* (non-Javadoc)
	 * @see com.htwg.powerlp.file.AbstractFileManager#getExtension()
	 */
	@Override
	public String getExtension() {
		return EXTENSION;
	}

}
