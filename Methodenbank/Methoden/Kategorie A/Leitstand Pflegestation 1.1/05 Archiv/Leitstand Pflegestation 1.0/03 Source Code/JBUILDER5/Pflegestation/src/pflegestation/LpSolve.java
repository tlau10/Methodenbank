package pflegestation;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>Title: Leitstand Pflegestation</p>
 * <p>Description: Optimiert die Pflegevorgänge einer Krankenhausstation</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FH Konstanz</p>
 * @author Sebastian Hagen, Jonathan Feuerstein, Birgit Engler
 * @version 1.0
 */

public class LpSolve
{
    private String[] progarray;
    private String _ERROR_MSG;
    private String _WARNING_MSG;
    private String lpSolvePath;
    private String lpSolveExec;
    private static String lpSolveExecPath;
    private StringBuffer lpSolveOutput;
    private Vector myStringVector;
    private Vector myStringVectorVariable;
    Vector vectorData;


    public LpSolve(Vector in_data, String lpPath)
    {
      /* Example for the lpsolve
      max:1x1+2x2;
      3x1+2x2<12;
      1x1+3x2<9;
      1x1+0x2>0;
      0x1+1x2>0;
      */
      // some initializing
      System.out.println("\nLpsolve is trying to work it out ....");
      this.lpSolveExecPath=lpPath;
      vectorData=in_data;
      progarray       = new String[2];
      myStringVector  = new Vector();
      myStringVectorVariable = new Vector();
      lpSolveOutput   = new StringBuffer();
      _ERROR_MSG      = "ERROR: ";
      _WARNING_MSG    = "WARNING: ";
      if(this.writeLpFile()==true)
      {
          if(calculateLPsolve()==true)
          {
            parseData();
            printVector();
          }
      }
    }
    public void printVector()
    {
        System.out.println( "Ergebnis ------------------------");
        System.out.println( "Value of function: "+ myStringVector.get(0) );
        for(int i=1;i<myStringVector.size();i++)
                System.out.println( myStringVectorVariable.get(i)+":"+myStringVector.get(i) );
        System.out.println( "-----------------------------");
    }
    // call to the lp-solve
    //		(using input and output stream)
    private boolean calculateLPsolve()
    {
        //überprufen ob lpInput.lp vorhanden ist

      BufferedReader p_inBuffer;
      String proc_input_string = "";
      try
      {
        //create the process
        progarray[0] = lpSolveExecPath;
        Process proc = Runtime.getRuntime().exec(progarray[0]);
        //Process proc = Runtime.getRuntime().exec("runLPsolve.bat");
        //get the streams
        InputStream  proc_in  = proc.getInputStream();
        InputStream  prog_err = proc.getErrorStream();
        int exit              = 0;
        boolean processEnded  = false;

        //push the data to the lpsolve
        //OutputStream proc_out  = proc.getOutputStream();
        //DataOutputStream out_s = new DataOutputStream(proc_out);
        //out_s.writeBytes(data);
        //out_s.close();
        //proc_out.close();

        // fetch the output from the LPsolve

        while(!processEnded)
        {
          try {
              exit = proc.exitValue();
              processEnded = true;
          }
          catch(IllegalThreadStateException e) {
          } // still running

          int n = proc_in.available();
          if(n > 0) {
              byte[] pbytes = new byte[n];
              proc_in.read(pbytes);
              lpSolveOutput.append(new String(pbytes));
          }
          n = prog_err.available();
          if(n > 0) {
              byte[] pbytes = new byte[n];
              prog_err.read(pbytes);
              _ERROR_MSG += new String(pbytes);
          }
          try {
              Thread.sleep(10);
          }
          catch(InterruptedException e) {
          }
        } // ### END-while ###

        //close/destroy all handles
        prog_err.close();
        proc_in.close();
        proc.destroy();
        } catch (IOException e) {
          // System.err.println("exeption-error:" + e);
          _ERROR_MSG += "\nIO error: " + e;
          myStringVector.add(0,"error");
          myStringVector.add(1,_ERROR_MSG);

          return false;
        }
      return true;
    } // ### END-calculateLPsolve
    private boolean parseData()
    {
        String token = "";
        int savekey  = 0;
        int valuekey = 0; // means 'Value of objective function' comes and this means a solution is comming :-)
        int zaehler  = 0;
        double tmp   = 0;
        int variablekey = 0;
        String strTmp;

        StringTokenizer st = new StringTokenizer(lpSolveOutput.toString() );
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if( token.equals("Value"))
                    valuekey=1;
            if(token.equals("function:"))
                    variablekey=1;
            if(valuekey==1)
            {
                try{
                    tmp = Double.parseDouble(token);
                    savekey=1;
                }catch ( NumberFormatException e)
                    {
                        if(variablekey ==1)
                        {
                            strTmp = token;
                            myStringVectorVariable.add(token);
                        }
                    }
                if(savekey==1)
                {
                    myStringVector.add(zaehler,token);
                    savekey=0;
                    zaehler++;
                }
            }
        }
        valuekey=0;
        //no numbers means solution is infeasible
        if(zaehler==0)
        {
            myStringVector.add(0,"ERROR");
            //myStringVector.add(1,"infeasible");
            myStringVector.add(1,lpSolveOutput.toString());
            return false;
        }
        return true;
    }
    // get the error-messages
    public String getErrorMsg()
    {
      return _ERROR_MSG;
    }
    // get the warning-messages
    public String getWarningMsg()
    {
      return _WARNING_MSG;
    }
    public Vector getSolutionVector()
    {
      return myStringVector;
    }
    public Vector getSolutionVectorVariable()
    {
        return this.myStringVectorVariable;
    }
    public boolean writeLpFile()
    {
        if(vectorData.size()==0)
        {

            return false;
        }
        try
       {
            FileWriter out_file = new FileWriter("lpInput.LP");
            BufferedWriter writer=new BufferedWriter(out_file);
           for(int i=0;i<vectorData.size();i++)
           {
                writer.write((String)vectorData.elementAt(i));
                writer.newLine();
            }
            writer.close();
           out_file.close();
       }
       catch (Exception ex)
       {

           return false;
       }
       return true;
    }
} //*** END-class LPsolve