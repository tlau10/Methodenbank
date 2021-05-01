package de.fh_konstanz.simubus.util;


/**
 * Stop-Uhr und Zeit-Funktionen.
 * Wandelt einen lomg-timestamp in eine lesbare Zeichenkette.
 * 
 * @author sidler
 *
 */
public class DateUtil {

	private static DateUtil currentInstance = null;
	private long startTime;
	private long endTime;
	
	private DateUtil() {
		this.endTime = 0;
		this.endTime = 0;	
	}
	
	public static DateUtil getInstance() {
		if(DateUtil.currentInstance == null)
			DateUtil.currentInstance = new DateUtil();
		
		return DateUtil.currentInstance;
	}
	
	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void setEndTime() {
		this.endTime = System.currentTimeMillis();
	}
	
	public String getTimeDiffAsString() {
		long timeDiff = this.endTime - this.startTime;
	    
	    return this.makeTimeReadable(timeDiff);
	}
	
	public String getTimeDiffExtended() {
		long timeDiff = this.endTime - this.startTime;
		timeDiff *= 1.08;
		
		return this.makeTimeReadable(timeDiff);
		
	}
	
	public String makeTimeReadable(long time) {
		long SECMILLIS  = 1000;
	    long MINMILLIS  = SECMILLIS*60;
	    long HOURMILLIS = MINMILLIS*60;
	    long DAYMILLIS  = HOURMILLIS*24;
	    
	    
		String diff = "";
		
		if (time >= HOURMILLIS) {
		    diff += (time % DAYMILLIS ) / HOURMILLIS +"h ";
		}
		if (time >= MINMILLIS) {
		    diff += (time % HOURMILLIS) / MINMILLIS +"m ";
		}
		if (time >= SECMILLIS) {
		    diff += (time % MINMILLIS) / SECMILLIS +"s ";
		}
		
		return diff;
	}
	
	
}
