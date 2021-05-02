package iterator.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import iterator.tableau.TableauDTO;


public class PersistenceFunctions {
	private static Logger logger = Logger.getLogger(PersistenceFileXml.class);
	private static PersistenceFileOri oldFile = new PersistenceFileOri();
	
	public static String readProperty(String prop){
		Properties properties = new Properties();  
		try {  
		        properties.load(new FileInputStream("config/iterator.properties")); 
		        return properties.getProperty(prop);
		} catch (IOException e) { 
			e.printStackTrace();
			return "";
		}  
	}
	
	public static boolean checkExtension(String path, String extension){
		return (path.endsWith("." + extension.toLowerCase()) || path.endsWith("." + extension.toUpperCase()));
		
	}
	
	public static String parseExtension(String path, String extension){
		if(!checkExtension(path, extension)){
			path = path + "." + extension.toLowerCase();
		}
		return path;
		
	}
	
	public static TableauDTO loadOldFile(String path){
		if(!checkExtension(path, "ORI")){
			return null;
		}
		
		logger.info("  >> old file extension (.ORI) found!");
		
		return oldFile.load(path);
		
	}
	
}
