/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
package portfolio.business;

import java.io.*;
import java.util.*;

import portfolio.model.Portfolio;


public class SolverAufruf extends Thread
{
  private static final String[] SOLVER_CONST_OTHERS = { "feasible", "solution" };
  private static final String SOLVER_CONST_OBJ_FUNC = "objective function";
  private Solution theSolution;
  private Vector errorLinesCollector = new Vector();


    /**
     * constructor
     */
    public SolverAufruf()
    {

    }


    /**
     * start the solver
     * @param lpmodell
     * @return
     */
    public Solution starteSolver(Vector lpmodell)
    {
        theSolution = new Solution();

        try
        {
            Process solver = Runtime.getRuntime().exec("LP_SOLVE.EXE");
            PrintStream eingabe = new PrintStream(new BufferedOutputStream(solver.getOutputStream()));
            BufferedReader rueckgabe = new BufferedReader(new InputStreamReader(solver.getInputStream()));
            BufferedReader fehler = new BufferedReader(new InputStreamReader(solver.getErrorStream()));

            Iterator iterator = lpmodell.iterator();
            while(iterator.hasNext())
            {
                eingabe.println((String)iterator.next());
            }

            eingabe.flush();
            eingabe.close();

            String s ="";
            while ( (s = rueckgabe.readLine()) != null )
            {
              checkValueLine(s);
            }
            rueckgabe.close();
            if( errorLinesCollector != null  &&  errorLinesCollector.size() > 0 )
              theSolution.setErrorLinesFromSolver(errorLinesCollector);


            String f ="";
            if( theSolution.getAllPortfolioModels() == null )
            {
              Vector errorVector = new Vector();
              while ( (f = fehler.readLine()) != null )
              {
                    errorVector.add(f);
              }
              if( theSolution.getErrorLinesFromSolver() != null )
                theSolution.getErrorLinesFromSolver().add(errorVector);
              else
                theSolution.setErrorLinesFromSolver(errorVector);
            }
            fehler.close();

        }

        catch ( IOException ioe )
        {
            ioe.printStackTrace();
        }

        return theSolution;
    }


    /**
     * checks a line that was read from the solver and adds it to the solution
     * @param lineToCheck
     */
    private void checkValueLine(String lineToCheck) {
      // 1. Objective function
      // if we have the value of the objective function
      if( lineToCheck.indexOf(SOLVER_CONST_OBJ_FUNC) > -1 ) {
        StringTokenizer tokenizer = new StringTokenizer(lineToCheck, " ");
        while( tokenizer.hasMoreTokens() ) {
          String currentToken = tokenizer.nextToken();
          try {
            double d = Double.parseDouble( currentToken );
            theSolution.setValueOfObjectiveFunction(d);
            break;
          }
          catch( NumberFormatException nfe ) {
            // if it is no number, simply continue with the loop
            continue;
          }
        }// while
      }// if

      // 2.
      else if( lineToCheck.indexOf(LPModell.RENDITE_NEGATIVE) > -1 ) {
        StringTokenizer tokenizer = new StringTokenizer(lineToCheck, " ");
        String name = tokenizer.nextToken();
        Double renditeNegative = new Double( tokenizer.nextToken() );
        theSolution.setRenditeNegative(renditeNegative.doubleValue());
      }
      // 3.
      else if( lineToCheck.indexOf(LPModell.RENDITE_POSITIVE) > -1 ) {
        StringTokenizer tokenizer = new StringTokenizer(lineToCheck, " ");
        String name = tokenizer.nextToken();
        Double renditePositive = new Double( tokenizer.nextToken() );
        theSolution.setRenditePositive(renditePositive.doubleValue());
      }
      // 4.
      else if( lineToCheck.indexOf(LPModell.RENDITE_VERFEHLT) > -1 ) {
        StringTokenizer tokenizer = new StringTokenizer(lineToCheck, " ");
        String name = tokenizer.nextToken();
        Double renditeVerfehlt = new Double( tokenizer.nextToken() );
        theSolution.setRenditeVerfehlt(renditeVerfehlt.doubleValue());
      }
      // 5.
      else if( lineToCheck.indexOf(LPModell.RENDITE_NEGATIVE_ZEITPUNKT_T) > -1 ) {
        StringTokenizer tokenizer = new StringTokenizer(lineToCheck, " ");
        String name = tokenizer.nextToken();
        Double renditeN = new Double( tokenizer.nextToken() );
        theSolution.setRenditeNegativZumZeitpunktT(renditeN.doubleValue());
      }
      // 6.
      else if (lineToCheck.indexOf(LPModell.RENDITE_MINIMAL) > -1) {
        StringTokenizer tokenizer = new StringTokenizer(lineToCheck, " ");
        String name = tokenizer.nextToken();
        Double renditeTyp4 = new Double( tokenizer.nextToken() );
        theSolution.setRenditeTyp4(renditeTyp4.doubleValue());
      }
      // 7.
      else if (lineToCheck.indexOf(LPModell.ABS_ABW_V) > -1 || lineToCheck.indexOf(LPModell.ABS_ABW_W) > -1 ){
        //nichts
      }

      // 8. the normal case: we get a rendite for a share
      else {
        StringTokenizer tokenizer = new StringTokenizer(lineToCheck, " ");
        String name = tokenizer.nextToken();
        String sz_anteilAmPortf = tokenizer.nextToken();
        try {
          Double anteilAmPortf = new Double( sz_anteilAmPortf );
          // die rendite wird nachträglich im Hauptfenster gesetzt
          Portfolio portfModel = new Portfolio(name, null, anteilAmPortf, null);
          theSolution.addPortfolioModel(portfModel);
          // System.out.println("SolverAufruf.java->checkValueLine(): added portfmodel: name=" + name + "; anteil=" + anteilAmPortf);
        }
        catch( NumberFormatException nfe ) {
          // if it is no number, we can be sure that an error has happened
          errorLinesCollector.add( new String(lineToCheck) );
        }
      }
    }
}