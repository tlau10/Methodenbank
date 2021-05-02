package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Thomas Geldner
 * @version 1.0
 */
/*********************************************************************
 * CVS-Info (Nicht manuell ändern, da von CVS verwaltet)
 * $Author: mje $
 * $Date: 2003/01/11 19:09:38 $
 * $Revision: 1.2 $
 * $Id: Route.java,v 1.2 2003/01/11 19:09:38 mje Exp $
 */

import java.util.*;

public class Route {

      //int test = 0;
//******************************************************************************
  private class TeilRoute {
    public int kanteId;
    public int vonKnotenId;
    public int nachKnotenId;

    TeilRoute(){}
    TeilRoute( int kanteId, int vonKnoten, int nachKnoten ){
        this.kanteId = kanteId;
        this.vonKnotenId = vonKnoten;
        this.nachKnotenId = nachKnoten;
    }
  }
//******************************************************************************
  private int startKnotenId;
  private int zielKnotenId;
  private int teilRoutenCursor;
  private Vector teilRoutenListe;
//******************************************************************************
  public Route() {
    teilRoutenCursor = 0;
    teilRoutenListe = new Vector();
  }
//******************************************************************************
  public synchronized  void addTeilRoute( int kanteId, int vonKnotenId, int nachKnotenId ){
    teilRoutenListe.add( new TeilRoute( kanteId, vonKnotenId, nachKnotenId ) );
  }
  
  
//Tan
  public synchronized Route copy()
  {
	  Route tmpRoute = new Route();
	  
	  tmpRoute.startKnotenId = this.startKnotenId;
	  tmpRoute.zielKnotenId = this.zielKnotenId;
	  tmpRoute.teilRoutenCursor = this.teilRoutenCursor;
	  
	  for (Iterator iter = teilRoutenListe.iterator(); iter.hasNext(); ) {
		  tmpRoute.teilRoutenListe.add((TeilRoute) iter.next());
      }
	  
	  return tmpRoute;
  }
  
  public synchronized boolean existTeilAsKante(int kanteId)
  {
	   TeilRoute tmpTeilRoute = new TeilRoute();  
	   
	    for (Iterator iter = teilRoutenListe.iterator(); iter.hasNext(); ) {
	        tmpTeilRoute = (TeilRoute) iter.next();
	        
	        if( tmpTeilRoute.kanteId == kanteId )
	        	return true;
	      }
	    
	    return false;
  }
  
  public synchronized boolean existTeilAsKnote(int knoteId)
  {
	   TeilRoute tmpTeilRoute = new TeilRoute();  
	   
	    for (Iterator iter = teilRoutenListe.iterator(); iter.hasNext(); ) {
	        tmpTeilRoute = (TeilRoute) iter.next();
	        
	        if( tmpTeilRoute.nachKnotenId == knoteId || tmpTeilRoute.vonKnotenId == knoteId )
	        	return true;
	      }
	    
	    return false;
  }  
//
  
//******************************************************************************
// Kantenoperationen
//******************************************************************************
  public synchronized boolean hasNextKante(){
    if(teilRoutenCursor < teilRoutenListe.size()){
    return true;
    }else return false;
  }
//******************************************************************************
  public synchronized int nextKante(){
    TeilRoute tmpteilRoute = (TeilRoute) teilRoutenListe.get(teilRoutenCursor);
    teilRoutenCursor++;
    return tmpteilRoute.kanteId;
  }
//******************************************************************************
// Knotenoperationen
//******************************************************************************
  public synchronized  boolean hasNextKnoten(){
    if(teilRoutenCursor < teilRoutenListe.size()){
    return true;
    }else return false;
  }
//******************************************************************************
  public synchronized int nextKnoten(){
    if(teilRoutenCursor < teilRoutenListe.size()){
      TeilRoute tmpteilRoute = (TeilRoute) teilRoutenListe.get(teilRoutenCursor);
      teilRoutenCursor++;
//      System.out.println("nextKnoten: " + tmpteilRoute.vonKnotenId);
      return tmpteilRoute.vonKnotenId;
    } else {
      TeilRoute tmpteilRoute = (TeilRoute) teilRoutenListe.get(teilRoutenCursor-1);
//      System.out.println("nextKnoten: " + tmpteilRoute.vonKnotenId);
      return tmpteilRoute.nachKnotenId;
    }
  }
//******************************************************************************
  public synchronized int getAktKnoten(){
    TeilRoute tmpteilRoute = (TeilRoute) teilRoutenListe.get(teilRoutenCursor -1);
    return tmpteilRoute.nachKnotenId;
  }
//******************************************************************************
  public synchronized int getAktKante(){
      TeilRoute tmpteilRoute = (TeilRoute) teilRoutenListe.get(0);
//      System.out.println("FolgeKante: " + tmpteilRoute.kanteId);
      return tmpteilRoute.kanteId;
  }
//******************************************************************************
  public synchronized int getAktKante2(){
      TeilRoute tmpteilRoute = (TeilRoute) teilRoutenListe.get(teilRoutenCursor -1);
//      System.out.println("FolgeKante: " + tmpteilRoute.kanteId);
      return tmpteilRoute.kanteId;
  }
//******************************************************************************
  public void resetKante(){
    teilRoutenCursor = 0;
  }
//******************************************************************************
  public void resetKnoten(){
    teilRoutenCursor = 0;
  }
//******************************************************************************
  public synchronized int getStart(){
    return startKnotenId;
  }
//******************************************************************************
  public int getZiel(){
    return zielKnotenId;
  }
//******************************************************************************
  public synchronized void setStart( int startKnotenId ){
    this.startKnotenId = startKnotenId;
  }
//******************************************************************************
  public void setZiel( int zielKnotenId ){
    this.zielKnotenId = zielKnotenId;
  }
//******************************************************************************
  public synchronized void sort(){

   Vector tmpTeilRoutenListe = new Vector();
   TeilRoute tmpToSortTeilRoute = new TeilRoute();
   int tmpVonKnoten;

   tmpVonKnoten = startKnotenId;

    while(teilRoutenListe.size() != 0){
      //  System.out.println("gefangen");
      search: {
        for (Iterator iter = teilRoutenListe.iterator(); iter.hasNext(); ) {
          tmpToSortTeilRoute = (TeilRoute) iter.next();
          if(tmpToSortTeilRoute.vonKnotenId == tmpVonKnoten){
            tmpTeilRoutenListe.add(tmpToSortTeilRoute);
            tmpVonKnoten = tmpToSortTeilRoute.nachKnotenId;
            teilRoutenListe.remove(tmpToSortTeilRoute);
            break search;
          }
        }
        break;
      }
    }
  teilRoutenListe = tmpTeilRoutenListe;
  }
//******************************************************************************
  public void display(){
    TeilRoute tmpTeilRoute;
    System.out.print("[Route]");
    System.out.print("[Start:" + startKnotenId + "]");
    System.out.print("[Ziel:" + zielKnotenId + "]");
    for (Iterator iter = teilRoutenListe.iterator(); iter.hasNext(); ) {
      tmpTeilRoute = (TeilRoute) iter.next();
      System.out.print(tmpTeilRoute.kanteId);
      System.out.print("(" + tmpTeilRoute.vonKnotenId + "," + tmpTeilRoute.nachKnotenId + ")");
      if( iter.hasNext())
              System.out.print("->");
    }
    System.out.println();
  }
//******************************************************************************
  public Iterator iterator(){
    return teilRoutenListe.iterator();
  }
}
/*********************************************************************
 * $Log: Route.java,v $
 * Revision 1.2  2003/01/11 19:09:38  mje
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.1  2002/12/20 01:39:05  mje
 * <Kein Kommentar eingegeben>
 *
 */
