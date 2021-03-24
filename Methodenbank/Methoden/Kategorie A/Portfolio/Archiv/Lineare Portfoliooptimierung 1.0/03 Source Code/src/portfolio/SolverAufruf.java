package portfolio;

/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
import java.io.*;
import java.util.*;

public class SolverAufruf extends Thread
{


    private Process solver;
    Vector lpmodell;
    Vector ergebnis;
    PrintStream eingabe;
    BufferedReader rueckgabe;
    BufferedReader fehler;

    public SolverAufruf()
    {


    }



    public Vector starteSolver(Vector lpmodell)
    {
        ergebnis= new Vector();
        this.lpmodell = lpmodell;
        try
        {
            solver = Runtime.getRuntime().exec("LP_SOLVE.EXE");
            eingabe = new PrintStream(new BufferedOutputStream(solver.getOutputStream()));
            fehler = new BufferedReader(new InputStreamReader(solver.getErrorStream()));
            rueckgabe = new BufferedReader(new InputStreamReader(solver.getInputStream()));
            String s ="";
            String f ="";
            Iterator iterator = lpmodell.iterator();
            while(iterator.hasNext())
            {
                eingabe.println((String)iterator.next());
            }
            eingabe.flush();
            eingabe.close();

            while ( (s = rueckgabe.readLine()) != null )
            {
                ergebnis.add(s);
            }


            if(ergebnis.isEmpty())
            {
                while ( (f = fehler.readLine()) != null )
                {
                    ergebnis.add(s);
                }
            }


        rueckgabe.close();
        fehler.close();
        }

        catch ( IOException ioe )
        {
            ioe.printStackTrace();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
        return ergebnis;
    }
}