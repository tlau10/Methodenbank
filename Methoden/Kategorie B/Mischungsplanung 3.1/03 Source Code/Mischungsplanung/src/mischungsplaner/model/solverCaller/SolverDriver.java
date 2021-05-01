package mischungsplaner.model.solverCaller;
import java.util.*;

/**
 * Das Programm ist der Treiber für das Solver Aufruf Packet. Ein Client Programm
 * kann den Treiber verwenden und die Anwendung zu nutzen.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:
 * @author Helmut Lindinger
 * @version 1.0
 */
public class SolverDriver implements IErrorMessage
{
  private BatchSolver solver;
  private Vector[] primalErgebnis;
  private Vector[] dualErgebnis;
  private double optimum;
  private int errorcode;
  private String errmsg;

  /**
   * Konstruktor. Legt ein neues Objekt von Typ SolverDriver an.
   * Hier wird ein User definiertes LP Model erwartet. D.h. die Methode
   * void addLPModel(String lpmodel[], int anzVar) 
   * in Klasse BatchSolver muß in der verwendeteten Solverklasse realisiert
   * werden.
   * @param solver Der Solver der verwendet wird
   * @param anzLPZeilen Anzahl der erwarteten LP Zeilen
   * @param lpZeilen Ein Stringfeld mit den LP-Zeilen
   * @param anzVar Anzahl der Variablen im Model (x0,x1,..,xn)
  */
  public SolverDriver( BatchSolver solver,
                       int anzLPZeilen, String[] lpZeilen, int anzVar )
  {
    // Solver und Parser anlegen..
    this.solver = solver;
    // LP Model einfuegen
    solver.addLPModel(lpZeilen,anzVar);
    // Eingabedatei erzeugen..
    solver.writeLPModel();
    // Solver aufrufen..
    errorcode = solver.solve();
    // Fehlermessage setzten
    errmsg = solver.getErrorMsg();
    // Fehlercode auswerten und Ergebnisse setzten
    starteAuswertung(errorcode);
  }
   
   /**
   * Konstruktor. Legt ein neues Objekt von Typ SolverDriver an.
   * Hier wird ein solverspezifisches LP Model erwartet. D.h. die Syntax
   * des Models muß dem des verwendeten Solvers entsprechen. 
   * @param solver Der Solver der verwendet wird
   * @param anzLPZeilen Anzahl der erwarteten LP Zeilen
   * @param lpZeilen Ein Stringfeld mit den LP-Zeilen
   */
   public SolverDriver( BatchSolver solver,
                       int anzLPZeilen, String[] lpZeilen)
   {
    // Solver und Parser anlegen..
    this.solver = solver;

    // LP Model einfuegen
    solver.addLPModel(lpZeilen);

    // Eingabedatei erzeugen..
    solver.writeLPModel();

    // Solver aufrufen..
    errorcode = solver.solve();

    // Fehlermessage setzten
    errmsg = solver.getErrorMsg();

    // Fehlercode auswerten und Ergebnisse setzten
    starteAuswertung(errorcode);
  }

  /**
  * Ermittelt das Primalergebnis.
  * @return Zwei dimensionales Vektor-Feld. Der erste Vektor beinhaltet
  * die Variablennamen (Objekttyp String). Der zweite Vektor beinhaltet
  * die zugehörigen Werte (Objekttyp Double).
  */
  public Vector[] getPrimalResult()   { return primalErgebnis; }

  /**
  * METHODE NOCH NICHT IMPLEMENTIERT (Liefert immer NULL).
  *
  * Ermittelt das Dualergebnis.
  * @return Zwei dimensionales Vektor-Feld. Der erste Vektor beinhaltet
  * die Variablennamen (Objekttyp String). Der zweite Vektor beinhaltet
  * die zugehörigen Werte (Objekttyp Double).
  */
  public Vector[] getDualResult()     { return dualErgebnis; }

  /**
  * List das Optimum aus.
  * @return Optimum
  */
  public double getOptimum()          { return optimum; }

  /**
  * Liest den Fehlercode aus, der nach dem Aufruf des Solver gesetzt wurde.
  * @return Fehlernummer<br>
  * gleich  0  - kein Fehler<br>
  * kleiner 0  - Fehler! Fehlertext kann ermittelt werden.<br>
  * größer  0:<br>
  * Falls Fehlercode 1 -> infeasable problem<br>
  * Falls Fehlercode 2 -> unbounded solution<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public int getErrorCode()       { return errorcode; }

  /**
  * Kam es zu einem Fehler, kann mit dieser Methode der Fehlertext ausgelesen werden.
  * @return Der Fehlertext
  */
  public String getErrorMsg()     { return errmsg; }

  // ----------------------------------------------------------------------
  // Private Methoden
  //-----------------------------------------------------------------------
  // Setzt die Rückgabewert wie z.B. Optimum, Primalergebnisse usw.
  private void starteAuswertung(int errorcode)
  {
    // Solveraufruf auf Fehler prüfen..
    if (errorcode >= 0)
     {  // Kein Fehler. Variablen / Lösungen auslesen...
        if (errorcode == 0)
        {
          primalErgebnis = solver.getPrimalResults();
          dualErgebnis = solver.getDualResults();
          optimum = solver.getOptimum();
        }
        else
        {
          primalErgebnis = null;
          dualErgebnis = null;
        }
      }
  }
} /*class */