
package Dakin;
// my execute program
import java.io.*;
import java.lang.*;
import java.util.*;

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
	private matrix myMatrix;


	// default Constructor
	public LPsolve()
	{
		// some initializing
		progarray       = new String[2];
		
		////////////////////////////////////////////////////////
		// SS13 ==> Pfad wird bei TreeDakin aus der Eingabetabelle mit dem Filereader ausgelesen.
		// Anschlieﬂend wird die Methode setPath() aufgerufen, die den richtigen Pfad einsetzt.
		// Update jetzt auch bei Solverpfad setzen!!!
		////////////////////////////////////////////////////////
		//lpSolvePath     = "c:\\temp\\dakin\\";
		//lpSolvePath     = "L:\\Besf\\SOLVER\\LP_SOLVE\\EXEC\\";
		//lpSolvePath     = "Y:\\BESF\\Solver\\LP_Solve\\Exec\\";
		//lpSolveExecPath = lpSolvePath+lpSolveExec;
		
		lpSolveExec     = "lp_solve.exe";
		
		myStringVector  = new Vector();
		lpSolveOutput   = new StringBuffer();
		myMatrix        = null;
		_ERROR_MSG      = "ERROR: ";
		_WARNING_MSG		= "WARNING: ";

	}


	// MAIN
	public static void main (String[] args) {
		LPsolve calc = new LPsolve();
		if( calc.calculateLPsolve() ) {
			calc.parseData();
			calc.printVector();
		}
	}


	// this is the main-method you have to call from other objects!!
	public Vector calculate(matrix m)
	{
		myMatrix=m;
//		myMatrix.printMatrix();
		if(myMatrix==null)
			return myStringVector;
		if(calculateLPsolve() )	 {
			parseData();
		}
		return myStringVector;
	}

	// set the path to the 'lpsolve.exe'
	public void setPath(String s)
	{
		lpSolvePath = s;
		lpSolveExecPath = lpSolvePath + lpSolveExec;
		return;
	}

	public void printVector()
	{
		for(int i=0;i<myStringVector.size();i++)
			System.out.println( "Vector["+i+"]:"+ myStringVector.get(i) );

		System.out.println( "---------------------");
	}

	//*************************************************************************
	// private processing functions
	//*************************************************************************



	//*************************************************************************
	// call to the lp-solve
	//		(using input and output stream)
	private boolean calculateLPsolve()
	{
		// if there are not data --> return
		String dataSet = createLPsolveDataSet();
	  	if(dataSet.equals(""))
	  		return false;

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
	    	myStringVector.add(0,"ERROR");
	    	myStringVector.add(1,_ERROR_MSG);
	    	return false;
		}

		return true;
	} // ### END-calculateLPsolve



	// create string from the matrix
	//	with this string the LPSolve would be called
	private String createLPsolveDataSet()
	{
		String data   = "";
		String tmp    = "";
		String colon  = ";";
		String varX   = "x";
		String plus   = "+";
		String minmax = "";

		if( myMatrix==null)
		{
			return "";
		}
		else
		{
			for(int i=0;i<myMatrix.getNumRows();i++)
			{
				for(int j=0;j<myMatrix.getNumCols();j++)
				{
					if( i==0 &&	j==(myMatrix.getNumCols()-2) )
							j++;
					if( i==0 && j==(myMatrix.getNumCols()-1) )
							minmax=myMatrix.getValueAt(i,j).substring(0, 3) +":";
					else  // other rows
					{
						if( i>0 && j>=(myMatrix.getNumCols()-2) )
							data += myMatrix.getValueAt(i,j);
						else
							data += myMatrix.getValueAt(i,j) + varX +(j+1);
					}
					if(j<(myMatrix.getNumCols()-3) )
						data+=plus;
				} // end inner-FOR-loop
				data +=colon;
			} // end outer-FOR-loop
			data = minmax+data;
		} //end else
		return data;
	}

/* Example for the lpsolve
	max:1x1+2x2;
	3x1+2x2<12;
	1x1+3x2<9;
	1x1+0x2>0;
	0x1+1x2>0;
*/


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
				}	catch ( NumberFormatException e) {
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

} //*** END-class LPsolve
