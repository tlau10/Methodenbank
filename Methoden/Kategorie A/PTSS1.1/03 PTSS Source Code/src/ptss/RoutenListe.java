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
 * $Date: 2003/01/11 19:09:39 $
 * $Revision: 1.2 $
 * $Id: RoutenListe.java,v 1.2 2003/01/11 19:09:39 mje Exp $
 */

import java.util.*;

public class RoutenListe {

  private Vector routenListe = new Vector();
//******************************************************************************
  public RoutenListe() {}
//******************************************************************************
  public synchronized void addRoute( Route route ){
    routenListe.add( route );
  }
//******************************************************************************
  public synchronized Route getRoute(int startKnotenId, int zielKnotenId){
    Route tmpRoute;
    for (Iterator iter = routenListe.iterator(); iter.hasNext(); ) {
     tmpRoute = (Route) iter.next();
     if(tmpRoute.getStart() == startKnotenId && tmpRoute.getZiel() == zielKnotenId)
     return tmpRoute;
   }
    return null;
  }
//******************************************************************************
  public synchronized boolean isEmpty(){
      return routenListe.isEmpty();
  }
//******************************************************************************
  public synchronized boolean existsRoute(int startKnotenId, int zielKnotenId){
    Route tmpRoute;
    if((routenListe == null) || (routenListe.isEmpty())){
      return false;
    }
    for (Iterator iter = routenListe.iterator(); iter.hasNext(); ) {
     tmpRoute = (Route) iter.next();
     if((tmpRoute.getStart() == startKnotenId) && (tmpRoute.getZiel() == zielKnotenId))
       return true;
   }
    return false;
  }
//******************************************************************************
  public synchronized void clearRoutes(){
 /*
    Route tmpRoute;
    for (Iterator iter = routenListe.iterator(); iter.hasNext(); ) {
     iter.remove();
   }
*/
    routenListe.clear();
  }
//******************************************************************************
  public synchronized void display(){
    System.out.println("[RoutenListe][display]");
    Route tmpRoute;
    for (Iterator iter = routenListe.iterator(); iter.hasNext(); ) {
      tmpRoute = (Route) iter.next();
      tmpRoute.display();
    }
  }
}
/*********************************************************************
 * $Log: RoutenListe.java,v $
 * Revision 1.2  2003/01/11 19:09:39  mje
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.1  2002/12/20 01:39:10  mje
 * <Kein Kommentar eingegeben>
 *
 */
