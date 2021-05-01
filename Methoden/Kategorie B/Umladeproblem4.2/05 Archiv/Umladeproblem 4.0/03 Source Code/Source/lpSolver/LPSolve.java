package lpSolver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;


public class LPSolve {

	private String _ERROR_MSG;
	// private String _WARNING_MSG;
	private StringBuffer lpSolveOutput = new StringBuffer();
	private Vector myStringVector = new Vector();

	SolverPath solverPath = new SolverPath();

	public String lpSolve(String dataSet) {

		String path = "";

		try {
			path = solverPath.getLPSolverPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(path);

			// get the streams
			InputStream proc_in = proc.getInputStream();
			InputStream prog_err = proc.getErrorStream();
			int exit = 0;
			boolean processEnded = false;
			// push the data to the lpsolve
			OutputStream proc_out = proc.getOutputStream();
			DataOutputStream out_s = new DataOutputStream(proc_out);
			try {
				// System.err.println( "dataSet-Laenge: " + dataSet.length() );
				out_s.writeBytes(dataSet);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			out_s.close();
			proc_out.close();

			// fetch the output from the LPsolve
			lpSolveOutput = new StringBuffer();
			while (!processEnded) {
				try {
					exit = proc.exitValue();
					processEnded = true;
				} catch (IllegalThreadStateException e) {
				} // still running

				int n = proc_in.available();
				if (n > 0) {
					byte[] pbytes = new byte[n];
					proc_in.read(pbytes);
					lpSolveOutput.append(new String(pbytes));
				}
				n = prog_err.available();
				if (n > 0) {
					byte[] pbytes = new byte[n];
					prog_err.read(pbytes);
					_ERROR_MSG += new String(pbytes);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			} // ### END-while ###
			System.err.println("Loesung: " + "\n" + lpSolveOutput);
			// close/destroy all handles
			prog_err.close();
			proc_in.close();
			proc.destroy();
		} catch (IOException e) {

			_ERROR_MSG += "\nIO error: " + e;
			myStringVector.add(0, "error");
			myStringVector.add(1, _ERROR_MSG);

		}
		return lpSolveOutput.toString();
	}
	
}
