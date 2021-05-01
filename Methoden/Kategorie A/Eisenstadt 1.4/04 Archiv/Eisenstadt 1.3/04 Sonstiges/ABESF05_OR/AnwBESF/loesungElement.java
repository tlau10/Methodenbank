/*
 * Created on 12.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package AnwBESF;

/**
 * @author jensk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class loesungElement {
	
	private String halteStelle;
	private String anzahlPersonen;
	
	public loesungElement(String hs, String aP)	
	{
		halteStelle = hs;
		anzahlPersonen = aP;
	}
	
	public String getHalteStelle()
	{
		return halteStelle;
	}
	
	public String getAnzahlPersonen()
	{
		return anzahlPersonen;
	}
	

}
