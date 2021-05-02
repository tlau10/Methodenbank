package com.htwg.powerlp.file;

import java.io.File;
import java.io.IOException;

/**
 * @author schobast
 *
 */
public abstract class AbstractFileManager {
	
	/**
	 * @author schobast
	 *
	 */
	public enum SupportedFormats{
		MPS, MAT, MPS_FREE, MPS_FIXED;
	}
	
	/**
	 * 
	 */
	protected File createdFile;
	
	/**
	 * 
	 */
	protected String trgPath;
	/**
	 * 
	 */
	public AbstractFileManager(String trgPath) {
		this.trgPath = trgPath;
	}
	
	/**
	 * @return
	 */
	public File getCreatedFile() {
		return createdFile;
	}
	
	/**
	 * @param content
	 * @throws IOException
	 */
	public abstract void createFile(String content) throws IOException;
	
	/**
	 * @return
	 */
	public abstract String getExtension();


}
