package de.fh_konstanz.simubus.util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Logging-Engine
 * Nimmt Meldungen entgegen und schreibt diese wahlweise in eine Datei oder auf die Konsole
 * @author sidler
 *
 */
public class Logger {

	private static Logger instance = null;
	
	private boolean logToFile = false;
	private boolean logToConsole = false;
	
	private String logFile = "logfile.txt";
	
	
	public static final int LEVEL_DEBUG = 2;
	public static final int LEVEL_ERROR = 1;
	public static final int LEVEL_FATALERROR = 0;
	
	private int currentLoglevel = 0;
	
	private Logger() {
		this.log("logger startup");
	}
	
	public Logger clone() {
		return getInstance();
	}
	
	/**
	 * Liefert die aktuelle Instanz des Loggers
	 * @return
	 */
	public static Logger getInstance() {
		if(instance == null)
			instance = new Logger();
		
		return instance;
	}
	
	
	private String getLoglevelAsString(int loglevel) {
		
		if(loglevel == 0)
			return "FATALERROR";
		else if(loglevel == 1)
			return "ERROR";
		else if(loglevel == 2)
			return "DEBUG";
		
		return "n.a.";
	}
	
	
	/**
	 * Logs a message as a debug-message
	 * @param message
	 */
	public void log(String message) {
		if( currentLoglevel > 2 )
			return;
		
		message = getLoglevelAsString(2)+" : "+getLastMethod()+"\n\t"+message+"\r\n";
		
		if(this.logToConsole)
			this.logToConsole(message);
		
		if(this.logToFile)
			this.logToFile(message);
	}
	
	/**
	 * Logs a messag
	 * @param message
	 * @param loglevel
	 */
	public void log(String message, int loglevel) {
		
		if(loglevel < currentLoglevel )
			return;
		
		message = getLoglevelAsString(loglevel)+" : "+getLastMethod()+"\n\t"+message+"\r\n";
		
		if(this.logToConsole)
			this.logToConsole(message);
		
		if(this.logToFile)
			this.logToFile(message);
	}
	
	private String getLastMethod() {
		
		StackTraceElement[] trace = new Throwable().getStackTrace();
		StackTraceElement last = trace[2];
		return last.getClassName()+":"+last.getMethodName()+" ("+last.getLineNumber()+")";
		
	}
	
	
	private void logToConsole(String message) {
		System.out.print(message);
	}
	
	
	private void logToFile(String message) {
		String path = this.logFile;
		
		try {
			FileWriter writer = new FileWriter(path, true);
			writer.write(message);
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			this.logToConsole("error logging to file");
			e.printStackTrace();
		}
		
	}
	
	

	public boolean isLogToConsole() {
		return logToConsole;
	}

	public void setLogToConsole(boolean logToConsole) {
		this.logToConsole = logToConsole;
		this.log("Set logToConsole to "+logToConsole);
	}

	public boolean isLogToFile() {
		return logToFile;
	}

	public void setLogToFile(boolean logToFile) {
		this.logToFile = logToFile;
		this.log("Set logToFile to "+logToFile);
	}

	public int getCurrentLoglevel() {
		return currentLoglevel;
	}

	public void setCurrentLoglevel(int currentLoglevel) {
		this.currentLoglevel = currentLoglevel;
		this.log("LogLevel set to "+currentLoglevel, Logger.LEVEL_DEBUG );
	}
	
	
	
}
