package opsa;
// my execute program
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;

public class LPsolve
{
    private String[] progarray;
    private String _ERROR_MSG;
    private String _WARNING_MSG;
    private String lpSolvePath;
    private String lpSolveExec;
    private static String lpSolveExecPath;
    private StringBuffer lpSolveOutput;
    private Vector myStringVector;
    String dataSet="";

    // default Constructor
    public LPsolve(String in_data, String lpPath)
    {
      this.lpSolveExecPath=lpPath;
      dataSet=in_data;
      /* Example for the lpsolve
      max:1x1+2x2;
      3x1+2x2<12;
      1x1+3x2<9;
      1x1+0x2>0;
      0x1+1x2>0;
      */
      // some initializing
      progarray       = new String[2];
      //lpSolvePath     = "D:\\Neptun\\Lehre\\besf\\solver\\lp_solve\\exec\\";
      //lpSolvePath     = ".";
      //lpSolveExec     = "\\lp_solve.exe";
      //lpSolveExecPath = lpSolvePath+lpSolveExec;
      myStringVector  = new Vector();
      lpSolveOutput   = new StringBuffer();
      //myMatrix        = null;
      _ERROR_MSG      = "ERROR: ";
      _WARNING_MSG    = "WARNING: ";
      if(calculateLPsolve()==true)
      {
        parseData();
        printVector();
      }
    }
    public void printVector()
    {
        for(int i=0;i<myStringVector.size();i++)
                System.out.println( "Vector["+i+"]:"+ myStringVector.get(i) );
        System.out.println( "---------------------");
    }
    // call to the lp-solve
    //		(using input and output stream)
    private boolean calculateLPsolve()
    {
      if(dataSet.equals("")){
        System.out.println("keine Daten an LPsolve");
        return false;
      }
      BufferedReader p_inBuffer;
      String proc_input_string = "";
      try
      {
        //create the process
        progarray[0] = lpSolveExecPath;
        Process proc = Runtime.getRuntime().exec(progarray[0]);
        //get the streams
        InputStream  proc_in  = proc.getInputStream();
        InputStream  prog_err = proc.getErrorStream();
        int exit              = 0;
        boolean processEnded  = false;

        //push the data to the lpsolve
        OutputStream proc_out  = proc.getOutputStream();
        DataOutputStream out_s = new DataOutputStream(proc_out);
        out_s.writeBytes(dataSet);
        out_s.close();
        proc_out.close();

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
          catch(InterruptedException e) { }
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

        StringTokenizer st = new StringTokenizer(lpSolveOutput.toString() );
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if( token.equals("Value"))
                    valuekey=1;

            if(valuekey==1)
            {
                try{
                    tmp = Double.parseDouble(token);
                    savekey=1;
                }catch ( NumberFormatException e) { }
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
    public Vector getMyStringVector()
    {
      return myStringVector;
    }
} //*** END-class LPsolve
