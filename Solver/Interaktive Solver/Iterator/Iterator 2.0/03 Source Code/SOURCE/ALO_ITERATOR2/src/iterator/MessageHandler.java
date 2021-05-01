package iterator;

import iterator.persistence.PersistenceFileXml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MessageHandler {

	private static Logger logger = Logger.getLogger(PersistenceFileXml.class);
	
	private static MessageHandler handler = null;
	
	private Properties messagProps = new Properties();
	private Properties iteratorProps = new Properties();
	
	private String language = null;
	
	private MessageHandler(){
		try {  
		   
		   iteratorProps.load(new FileInputStream("config/iterator.properties"));
		   this.language = iteratorProps.getProperty("iterator.lang").toLowerCase();
		   
		   this.messagProps.load(new FileInputStream("config/language.properties"));

		   
		} catch (IOException e) { 
			logger.error("Messagehandling failed: " + e.getMessage());
		}  
	}
	
	public static MessageHandler getInstance(){
		if(handler == null){
			handler = new MessageHandler();
		}
		return handler;		
	}
	
	public String getMessage(String name){
		name = this.language + "." + name;
		String msg = this.messagProps.getProperty(name);
		//logger.debug("Message requested: " + name + ", found: " + msg);
		
		return msg;
	}
	
	public void changeLanguage(String lang){
		this.language = lang;
		this.iteratorProps.setProperty("iterator.lang", lang);
		
	}
	
}
