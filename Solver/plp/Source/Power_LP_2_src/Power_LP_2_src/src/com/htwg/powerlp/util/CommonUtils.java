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
package com.htwg.powerlp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author schobast
 *
 */
public class CommonUtils {

	/**
	 * 
	 */
	private static final String TRANS_XSL_NAME = "trans.xsl";

	/**
	 * 
	 */
	private static final String IS_PREFIX = "is";

	/**
	 * Searches for getter representation of @param solverName, executes it and @return value
	 *  
	 * @param solverName
	 * @param cfg
	 * @return true if solver is enabled
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static boolean isSolverActive(String solverName, Configurator cfg)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<? extends Object> container = cfg.getClass();
		Method[] methods = container.getMethods();
		for (Method method : methods) {
			String[] split = method.toString().split("\\.");
			if (split[split.length - 1].startsWith(IS_PREFIX)) {
				if (split[split.length - 1].toLowerCase().contains(solverName.toLowerCase())) {
					return ((boolean) method.invoke(cfg, new Object[] {}));
				}
			}
		}
		return false;
	}

	/**
	 * @param src
	 * @param trg
	 * @throws FileNotFoundException 
	 * @throws TransformerException 
	 */
	public static void transform(String src, String trg) throws FileNotFoundException, TransformerException {
		// XML-Transformation
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = factory.newTransformer(new StreamSource(loadResourceAsStream(TRANS_XSL_NAME)));
		// ConfiguratorSingleton.getInstance().getCplexDir() +
		// "\\PowerLPResult"
		FileOutputStream fileOutput = new FileOutputStream(trg);
		StreamResult result = new StreamResult(fileOutput);
		// new File(ConfiguratorSingleton.getInstance().getCplexDir() +
		// "\\PowerLPResult.xml")
		StreamSource source = new StreamSource(new File(src));
		transformer.transform(source, result);
	}

	/**
	 * @param resPath
	 * @return
	 */
	public static InputStream loadResourceAsStream(String resPath) {
		ClassLoader classLoader = CommonUtils.class.getClassLoader();
		return classLoader.getResourceAsStream(resPath);
	}
	
	public static URL loadResourceAsUrl(String resPath) {
		ClassLoader classLoader = CommonUtils.class.getClassLoader();
		return classLoader.getResource(resPath);
	}
	
	public static ImageIcon loadIcon(String resName){
        URL imgURL = CommonUtils.loadResourceAsUrl(resName);
        if(imgURL != null)
            return new ImageIcon(imgURL);
        else
            return null;
    }

	public static boolean isFilePresent(String path){
		if (new File(path).exists()) {
			return true;
		}
		return false;
	}

}
