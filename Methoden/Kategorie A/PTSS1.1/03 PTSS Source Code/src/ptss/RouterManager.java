package ptss;

import java.awt.Cursor;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: Erstellt LP-Modell nach Kirchhoff-Regel </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Thomas Geldner
 * @version 1.0
 */
/*********************************************************************
 * CVS-Info (Nicht manuell ändern, da von CVS verwaltet)
 * $Author: mje $
 * $Date: 2003/01/11 19:09:39 $
 * $Revision: 1.11 $
 * $Id: RouterManager.java,v 1.11 2003/01/11 19:09:39 mje Exp $
 */

import java.util.*;
import SolverCaller.*;
import javax.swing.*;
//import Simulation.*;

// RoutenManager als Thread umgeschrieben
// ansonsten wird alles blockiert solange route gerechnet wird

// Progress Window zählt einfach ins unendliche ...
// hier könnte der tatsächliche "Progess" stehen


public class RouterManager extends Thread
{
  static String eingabeDatei;
  static String ausgabeDatei;
  static String solverPfad;
  static String workDir;
  static String solverName;
  static String[] LPAnsatz;
  static BatchSolver bs;
  static SolverDriver driver;

  static Knoten tmpKnoten;
  static Kante tmpKante;
  static String trenn = "_";
  static double optimum;
  static private RoutenListe routenListe;
  static private Vector<Route> routenAlleListe;
  static Route route;
  static boolean doOnlyOneRoute = true;


 // static Progress progressWindow;  // added mje
  static KnotenListe KnotenListe;  // added mje
  static KantenListe KantenListe;  // added mje
  static int anzahlRouten;         // added mje
  static ModellierView mView;

//******************************************************************************
  public synchronized void setSolverLPSolve(){
    eingabeDatei = "lp_eingabe.lp";
    ausgabeDatei = "lp_ausgabe.txt";
    solverPfad   = "bin\\";
    workDir      = "temp\\";
    solverName   = "LP_SOLVE.EXE";
    bs = new LPSolver(eingabeDatei, ausgabeDatei, workDir,
                      solverPfad, solverName, new LPSolveParser() );
  }
//******************************************************************************
  public synchronized void setSolverXA(){
    eingabeDatei = "xa_eingabe.lp";
    ausgabeDatei = "xa_ausgabe.txt";
    solverPfad   = "bin\\";
    workDir      = "temp\\";
    solverName   = "XA.EXE";
    bs = new XASolver(eingabeDatei, ausgabeDatei, workDir,
                      solverPfad, solverName, new XAParser() );
  }
//******************************************************************************
  public RouterManager(){
    if(routenListe == null)
     routenListe = new RoutenListe();
 }
//******************************************************************************
  // für Thread
  public RouterManager(KnotenListe _KnotenListe,KantenListe _KantenListe, ModellierView mView){   // added mje
  KnotenListe = _KnotenListe;
  KantenListe = _KantenListe;
  if(routenListe == null)
    routenListe = new RoutenListe();
  this.mView = mView;
  }
//******************************************************************************
// Thread run überschrieben
  public synchronized  void run(){  // added mje
    if(doOnlyOneRoute)
    doEineRoute();
    else
    doAlleRouten();
  }
//******************************************************************************
  public synchronized void setRoutenListe( RoutenListe routenListe ){
    this.routenListe = new RoutenListe();
    this.routenListe = routenListe;
  }
  
//******************************************************************************
  
  	
  	public synchronized double getGesamtkostenOfRoute(Route aRoute)
  	{
  		double tmpGesamtkosten = 0;
  		
  		aRoute.resetKante();
  		aRoute.resetKnoten();
        while(aRoute.hasNextKante()){
            Kante tmpKante = KantenListe.getKanteById(aRoute.nextKante());
            tmpGesamtkosten += tmpKante.getGewA();
          }
		
  		return tmpGesamtkosten;
  	}
  	
  	public synchronized Vector<Route> getRoutenAlleListe()
  	{
  		return routenAlleListe;
  	}
  	
	public synchronized boolean FindAlleRouten(int nVonKnotenId, Route aRoute)
	{
		Knoten tmpNachKnoten;
		
		for (Iterator iterNach = KnotenListe.iterator(); iterNach.hasNext(); )
		{
			tmpNachKnoten = (Knoten) iterNach.next();
			
			int nKantenId = KantenListe.getKanteIdByKnotenId(nVonKnotenId, tmpNachKnoten.getId()); 
			if( nKantenId != -1 )
			{
//				if( aRoute.existTeilAsKante(nKantenId))
//					continue;
				if( aRoute.existTeilAsKnote(tmpNachKnoten.getId()) )
					continue;
				
				Route tmpRoute = aRoute.copy();
				
				tmpRoute.addTeilRoute(nKantenId, nVonKnotenId, tmpNachKnoten.getId());
				
				if( tmpNachKnoten.isZiel() )
				{
					tmpRoute.setStart(KnotenListe.getStartKnotenId());
					tmpRoute.setZiel(KnotenListe.getZielKnotenId());
					tmpRoute.sort();
					
					this.routenAlleListe.add(tmpRoute);
				}
				else
				{
					FindAlleRouten(tmpNachKnoten.getId(), tmpRoute);
				}
			}
			
		}
		 
		return true;
	}
	
  
  public synchronized void doAlleRouten(){
	 
	 routenAlleListe = new Vector<Route>();
	 routenAlleListe.clear();

	 mView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	 
	 Route tmpRoute = new Route();
	 FindAlleRouten(KnotenListe.getStartKnotenId(), tmpRoute);
	 
	 mView.setCursor(Cursor.getDefaultCursor());

	 mView.repaint();
  }
  //
  
//******************************************************************************
  public synchronized void doEineRoute(){

       routenListe.clearRoutes();

      //Solver setzen
      setSolverLPSolve();
      route = new Route();
      //Solver anstossen
      if( runLPSolve(KnotenListe,  KantenListe) != 0 ){
        JOptionPane.showMessageDialog(null,
                                      "Es konnte keine Route berechnet werden\n\n" + driver.getErrorMsg(),
                                      " Fehler", JOptionPane.ERROR_MESSAGE );
      }

    // vorhergehende Route in Grafik demarkieren
    KantenListe.clearPath();

    // Route in Grafik markieren
    if(doOnlyOneRoute){
      route.resetKante();
      while(route.hasNextKante()){
        KantenListe.getKanteById(route.nextKante()).setPath(true);
      }
    }
    mView.paint(mView.getGraphics());
    mView.repaint();
    routenListe.display();
  }
//******************************************************************************
  public int runLPSolve(KnotenListe KnotenListe, KantenListe KantenListe){
    // LP-Ansatz Formulieren
    LPAnsatz = new String[KnotenListe.getSize()+1];

    // Zielfunktion erstellen
    String zf = "min: ";
    // Alle Kanten traversieren und gewichtete Kanten in ZF aufnehmen
    for (Iterator iter = KantenListe.iterator(); iter.hasNext(); ) {
      tmpKante = (Kante) iter.next();

      // Nur Kanten mit beidseitigem Gewicht beachten
      if ( (tmpKante.getGewA() != 0) ||  (tmpKante.getGewB() != 0))
      {
        Knoten tmpKnotenA = KnotenListe.getKnotenById(tmpKante.getKnotenA());
        Knoten tmpKnotenB = KnotenListe.getKnotenById(tmpKante.getKnotenB());

        //1) Kante ist direkt mit Ziel verbunden -> nur eingehende Wege in ZF aufnehmen
        if( (tmpKnotenA.isZiel()) || (tmpKnotenB.isZiel()) ){
          if( tmpKnotenA.isZiel() && tmpKante.getGewB()!=0 ){
            zf += " + " + tmpKante.getGewB() + " x" + tmpKante.getKnotenB() + trenn + "" + tmpKante.getKnotenA();
          }
          if( tmpKnotenB.isZiel() && tmpKante.getGewA()!=0){
            zf += " + " + tmpKante.getGewA() + " x" + tmpKante.getKnotenA() + trenn + "" + tmpKante.getKnotenB();
          }
        }
        //2) Kante ist direkt mit Start verbunden -> nur ausgehende Wege in ZF aufnehmen
        else if( (tmpKnotenA.isStart()) || (tmpKnotenB.isStart()) ){
          if( tmpKnotenA.isStart() && tmpKante.getGewA()!=0){
            zf += " + " + tmpKante.getGewA() + " x" + tmpKante.getKnotenA() + trenn + tmpKante.getKnotenB();
          }
          if( tmpKnotenB.isStart() && tmpKante.getGewB()!=0){
            zf += " + " + tmpKante.getGewB() + " x" + tmpKante.getKnotenB() + trenn + tmpKante.getKnotenA();
          }
        }
        //3) Normale Kante, d.h. nicht mit Start oder Ziel verbunden
        else {
          if(tmpKante.getGewA()!=0)
            zf += " + " + tmpKante.getGewA() + " x" + tmpKante.getKnotenA() + trenn + tmpKante.getKnotenB();
          if(tmpKante.getGewB()!=0)
            zf += " + " + tmpKante.getGewB() + " x" + tmpKante.getKnotenB() + trenn +  tmpKante.getKnotenA();
        }
      }
    }
    zf += ";";
    LPAnsatz[0] = zf;
    System.out.println( zf );
//==============================================================================
    // Restriktionen erstellen; je Knoten eine Gleichung
    String res = "";
    int i = 1;
    // Alle Knoten traversieren
    for (Iterator iter = KnotenListe.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      res += "KN" + tmpKnoten.getId();
      if ( tmpKnoten.getStart() ) res += "s";
      if ( tmpKnoten.getZiel() ) res += "z";
      res += ": ";

      // Alle Kanten traversieren, die beim aktuellen Knoten zu oder abgehen
      for (Iterator iter1 = KantenListe.iterator(); iter1.hasNext(); ) {
        tmpKante = (Kante) iter1.next();

        // Restriktionen für Vermittlungsknoten erstellen
        if (!tmpKnoten.isStart() && !tmpKnoten.isZiel()){

          Knoten tmpKnotenA = KnotenListe.getKnotenById(tmpKante.getKnotenA());
          Knoten tmpKnotenB = KnotenListe.getKnotenById(tmpKante.getKnotenB());

          //1) Knoten ist Ziel -> nur in Ziel eingehende Wege in Restriktion aufnehmen
          if( (tmpKnotenA.isZiel()) || (tmpKnotenB.isZiel()) ){
            if( (tmpKante.getKnotenB() == tmpKnoten.getId()) && tmpKante.getGewB() !=0 )
              res += " -  x" + tmpKante.getKnotenB() +  trenn + "" + tmpKante.getKnotenA();
            if( (tmpKante.getKnotenA() == tmpKnoten.getId()) && tmpKante.getGewA() !=0 )
              res += " -  x" + tmpKante.getKnotenA() +  trenn + "" + tmpKante.getKnotenB();

          }
          //2) Knoten ist Start ->  nur aus Start ausgehende Wege in Restriktion aufnehmen
          else if( (tmpKnotenA.isStart()) || (tmpKnotenB.isStart()) ){
            if( (tmpKante.getKnotenB() == tmpKnoten.getId()) && tmpKante.getGewA() !=0 )
              res += " +  x" + tmpKante.getKnotenA() +  trenn + tmpKante.getKnotenB();
            if( (tmpKante.getKnotenA() == tmpKnoten.getId()) && tmpKante.getGewB() !=0 )
              res += " +  x" + tmpKante.getKnotenB() +  trenn + tmpKante.getKnotenA();
          }
          else{ // Knoten ist weder Start noch Ziel
            if( (tmpKante.getKnotenB() == tmpKnoten.getId()) && tmpKante.getGewA() !=0 )
              res += " +  x" + tmpKante.getKnotenA() +  trenn + tmpKante.getKnotenB();
            if( (tmpKante.getKnotenB() == tmpKnoten.getId()) && tmpKante.getGewB() !=0 )
              res += " -  x" + tmpKante.getKnotenB() +  trenn + tmpKante.getKnotenA();
            if( (tmpKante.getKnotenA() == tmpKnoten.getId()) && tmpKante.getGewA() !=0 )
              res += " -  x" + tmpKante.getKnotenA() +  trenn + tmpKante.getKnotenB();
            if( (tmpKante.getKnotenA() == tmpKnoten.getId()) && tmpKante.getGewB() !=0 )
              res += " +  x" + tmpKante.getKnotenB() +  trenn + tmpKante.getKnotenA();
          }
        }

        // Restriktion für den Startknoten erstellen
        else if (tmpKnoten.isStart()){
          if( (tmpKante.getKnotenA() == tmpKnoten.getId()) && tmpKante.getGewA() !=0 )
            res += " +  x" + tmpKante.getKnotenA() + trenn + tmpKante.getKnotenB();
          if( (tmpKante.getKnotenB() == tmpKnoten.getId()) && tmpKante.getGewB() !=0 )
            res += " +  x" + tmpKante.getKnotenB() +  trenn + tmpKante.getKnotenA();
        }

        // Restriktion für den Zielknoten erstellen
        else if (tmpKnoten.isZiel()){
          if( (tmpKante.getKnotenA() == tmpKnoten.getId()) && tmpKante.getGewB() !=0 )
            res += " +  x" + tmpKante.getKnotenB() +  trenn + "" + tmpKante.getKnotenA();
          if( (tmpKante.getKnotenB() == tmpKnoten.getId()) && tmpKante.getGewA() !=0 )
            res += " +  x" + tmpKante.getKnotenA() +  trenn + "" + tmpKante.getKnotenB();
        }
      }

      if(tmpKnoten.isStart() || tmpKnoten.isZiel())
        res += " >= 1;";
      else
        res += " >= 0;";

      LPAnsatz[i] = res;
      System.out.println( res );
      res = ""; i++;
    }
//==============================================================================
    // Solver starten
    driver = new SolverDriver( bs, LPAnsatz.length, LPAnsatz);

    // Fehlercode auslesen.
    // falls errorcode = 0 -> kein Fehler.
    // falls errorcode < 0 -> Fehler! Fehlertext ist gesetzt.
    // falls errorcode > 0 -> 1 : infeasable
    //                        2 : unbounded
    if(driver.getErrorCode() != 0)
      return driver.getErrorCode();

//    System.out.println(driver.getErrorMsg());

    // Ergebnissse aus Solver holen (Allgemein !!!)
    optimum = driver.getOptimum();
//    System.out.println("Z:" + driver.getOptimum());
    Vector[] Results = bs.getPrimalResults();
    Iterator iterName = Results[0].iterator();
    Iterator iterValue = Results[1].iterator();

    //Lösungen aus Solverausgabe von LP-Solve extrahieren (Spezifisch !!!)
    String n, v, a, b; int j=0;
    route = new Route();
    while( iterName.hasNext() ){
      n = iterName.next().toString();
      v = iterValue.next().toString();

      n = n.substring(1);// x abschneiden
      j = n.indexOf("_");// Trennzeichen für A->B
      a = n.substring(0, j);// Name der Verbindung holen
      b = n.substring(j+1);// Wert der Verbindung holen

      // Kanten auf dem Pfad markieren
      if ( v.compareTo("1.0") == 0) // 0=true
      {
        route.addTeilRoute( KantenListe.getKanteIdByKnotenId( Integer.parseInt(a), Integer.parseInt(b) ), Integer.parseInt(a), Integer.parseInt(b) );
        System.out.println("N:"+ n + " V:" + v + " A:" + a + " B:" + b);
        // KantenListe.getKanteById(KantenListe.getKanteIdByKnotenId( Integer.parseInt(a), Integer.parseInt(b) )).setPath(true);
        System.err.println("(Markieren von Kante mit ID=" + KantenListe.getKanteIdByKnotenId( Integer.parseInt(a), Integer.parseInt(b) ) + " OK)");
      }
    }

    route.setStart(KnotenListe.getStartKnotenId());
    route.setZiel(KnotenListe.getZielKnotenId());
    route.sort();

    // neue Route in Liste aufnehmen
    routenListe.addRoute(route);

    return driver.getErrorCode();
  }
//******************************************************************************
  public  synchronized double getOptimum(){ return optimum;}
//******************************************************************************
  public synchronized  boolean doPlausiCheck(KnotenListe KnotenListe, KantenListe KantenListe){
    Knoten tmpKnoten;
    Kante tmpKante;
    boolean hasError = false;
    boolean hasStart = false;
    boolean hasZiel = false;

    // Test: Es gibt keine Knoten (und folglich auch keine Kanten)
    if (KnotenListe.getSize() == 0) {
      JOptionPane.showMessageDialog(null,
                                    "Es gibt keine Knoten",
                                    " Fehler",
                                    JOptionPane.ERROR_MESSAGE );
      hasError = true;
      return true;
    }

    // Test: Haben alle Knoten mindestens eine Verbindung
    for(Iterator iter = KnotenListe.iterator(); iter.hasNext();){
      tmpKnoten = (Knoten) iter.next();
      if(!KantenListe.existsKnotenRef(tmpKnoten.getId())){
        JOptionPane.showMessageDialog(null,
                                      "Knoten \""+ tmpKnoten.getName()+"\" ist ohne Verbindung(en)",
                                      " Fehler",
                                      JOptionPane.ERROR_MESSAGE );
        hasError = true;
        return true;
      }
      if(tmpKnoten.isStart()) hasStart = true;
      if(tmpKnoten.isZiel()) hasZiel = true;
    }

    // Test: Gibt es Start und Ziel
    if(hasStart == false ){
      JOptionPane.showMessageDialog(null,
                                    "Es gibt keinen Start-Knoten",
                                    " Fehler",
                                    JOptionPane.ERROR_MESSAGE );
      hasError = true;
      return true;
    }
    if(hasZiel == false ){
      JOptionPane.showMessageDialog(null,
                                    "Es gibt keinen Ziel-Knoten",
                                    " Fehler",
                                    JOptionPane.ERROR_MESSAGE );
      hasError = true;
      return true;
    }

    // Test: Haben alle Kanten wenigstens in einer Richtung ein Gewicht
    for(Iterator iter = KantenListe.iterator(); iter.hasNext();){
      tmpKante = (Kante) iter.next();
      if ((tmpKante.getGewA() == 0) && (tmpKante.getGewB() == 0))
        JOptionPane.showMessageDialog(null,
                                      "Kante \""+ tmpKante.getName()+"\" ist ohne Gewicht\nund wird nicht berücksichtigt",
                                      " Warnung",
                                      JOptionPane.WARNING_MESSAGE  );
    }
    return hasError;
  }
//******************************************************************************
  public int getCalculatedRouten(){ // added mje
    return anzahlRouten;
  }
//******************************************************************************
  public synchronized  void setDoOnlyOneRoute(){
    doOnlyOneRoute = true;
  }
//******************************************************************************
  public synchronized  void setDoAllRoute(){
    doOnlyOneRoute = false;
  }
//******************************************************************************
  public synchronized  RoutenListe getRoutenListe(){
  return routenListe;
  }
}
/*********************************************************************
 * $Log: RouterManager.java,v $
 * Revision 1.11  2003/01/11 19:09:39  mje
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.10  2002/12/20 01:39:40  mje
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.9  2002/12/11 11:37:47  mje
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.8  2002/12/04 21:35:01  tge
 * Fehler mit berücksichtigung Start/Ziel in ZF und RES behoben
 *
 * Revision 1.7  2002/12/03 11:47:49  mje
 * interne Ids unique gemacht ....
 * löschen angepasst ...
 * bug mit route weiterhin vorhanden ....
 *
 * Revision 1.6  2002/11/27 15:45:35  mje
 * hmh mit cvs gespielt :-)
 *
 * Revision 1.5  2002/11/25 17:24:23  tge
 * Fehler bei Kante ohne Gewicht behoben
 *
 * Revision 1.4  2002/11/25 00:14:41  tge
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.3  2002/11/23 14:06:57  tge
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.2  2002/11/18 18:38:17  mje
 * <Kein Kommentar eingegeben>
 *
 * Revision 1.1  2002/11/18 16:39:26  tge
 * Init by TGE
 *
 */
//*********************************************************************

