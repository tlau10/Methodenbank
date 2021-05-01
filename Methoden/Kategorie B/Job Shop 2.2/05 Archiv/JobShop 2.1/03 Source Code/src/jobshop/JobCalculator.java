/*
 * Bernd Haertenstein
 * 
 * Copyright (c) 2011-2015 University of Applied Science Konstanz. All Rights Reserved.
 * 
 * Version 	1.0	WS10/11
 * Author	Bernd Haertenstein
 * 
 * Version	1.1	WS14/15
 * Author	Dennis Klein, Sebastian Stephan
 * 
 */
package jobshop;

import lpsolve.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 * Create the linear programming model. As calculator lpSolve is used.
 * The optimum result will be saved as ResultList and returned to JobShopView.
 */
public class JobCalculator {

	private int sumDauer;
	private int anzM;
	private int anzP;
    private String separator;
    private String dirName;
    private File file = null; 
    private File file2 = null;
    public FileWriter writer;

	public JobCalculator() {
		this.sumDauer = 0;
		this.anzM = 0;
		this.anzP = 0;
		this.separator = " ";
		this.dirName = "./";
    }
    
    /* Create the lp-model. */
    public ResultList calculate(List<List<JFormattedTextField>> jFTFieldList) {
		this.sumDauer = this.getMaxDuration(jFTFieldList);
		this.anzM = jFTFieldList.size();
		this.anzP = jFTFieldList.get(0).size();
		List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
		matrix.addAll(this.getDurationRestrictions(jFTFieldList));
		matrix.addAll(this.getMachineRestrictions());
		matrix.addAll(this.getProductRestrictions());
		matrix.addAll(this.getRewardRestrictions());
		for (Map<String, String> map : matrix) {
			System.out.print(map.get("function"));
		    System.out.print(map.get("operator") + this.separator);
		    System.out.println(map.get("result"));
		}
		
		return this.getResult(matrix);
    }

    private ResultList analyseResult(double[] tmpResArr) {
		int anzVariablen = this.anzP * this.anzM * this.sumDauer;
		int maxDauer = 0;
		List<Double> tmpRes = new ArrayList<Double>();
		
		for (double d : tmpResArr) {
		    tmpRes.add(d);
		}
		
		/* Variables with value 1 show the instant of time (z), which produce the product (p) on machine (m). */
		List<Result> sort = new ArrayList<Result>();
		for (int m = 1; m <= this.anzM; m++) {
		    List<Double> schnipp = tmpRes.subList((m - 1) * this.anzP * this.sumDauer, m * this.anzP * this.sumDauer);
		    	for (int p = 1; p <= this.anzP; p++) {
		    		List<Double> schnapp = schnipp.subList((p - 1) * this.sumDauer, p * this.sumDauer);
		    			for (int z = 1; z <= this.sumDauer; z++) {
		    				if (schnapp.get(z - 1) == 1) {
								Result zmp = new Result(z, m, p);
								sort.add(zmp);
								if(maxDauer < z) {
								    maxDauer = z;
								}
		    				}
		    			}
		    	}
		}
		
		ResultList result = new ResultList(anzM, anzP, maxDauer, sort);
		System.out.println("aM: " + anzM + " aP: " + anzP + "maxD: " + maxDauer);
		
		for (Result result1 : sort) {
		    System.out.print("M" + result1.maschine);
		    System.out.print(" P" + result1.produkt);
		    System.out.println(" Z" + result1.zeitpunkt);
		}
		return result;
	}

    /* Create restriction: Products should be produces in sequence. */
    private List<Map<String, String>> getRewardRestrictions() {
		int anzRestriktionen = this.sumDauer * this.anzP * this.anzM;
		int anzVariablen = this.anzP * this.anzM * this.sumDauer;
		List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
		
		for (int i = 1; i <= anzRestriktionen; i++) {
		    Map<String, String> rest = new HashMap<String, String>();
		    String variables = "";
		    for (int j = 1; j <= anzVariablen * 2; j++) {
		    	if (j <= anzVariablen && (j == i || j == i + 1 && i % this.sumDauer > 0)) {
		    		variables += "1" + this.separator;
		    	} else if (j == i + anzVariablen) {
		    		variables += "-2" + this.separator;
		    	} else {
		    		variables += "0" + this.separator;
		    	}
		    }
		    
		    rest.put("function", variables);	// left (x-)side of the restriction "x1+x2+x3" = y
		    rest.put("operator", ">=");			// center of the restriction x1+x2+x3 "=" y
		    rest.put("result", "0");			// right (y-)side of the restriction x1+x2+x3 = "y"
		    matrix.add(rest);
		}
		
		return matrix;
    }

    /* Create restriction: For each time period just one product can be produced on one machine. */
    private List<Map<String, String>> getProductRestrictions() {
		int anzRestriktionen = this.sumDauer * this.anzP;
		int anzVariablen = this.anzP * this.anzM * this.sumDauer;
		int aktuelleZE = 1;
		int aktuellesP = 1;
		List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
	
		for (int i = 1; i <= anzRestriktionen; i++) {
		    Map<String, String> rest = new HashMap<String, String>();
		    String variables = "";
		    
		    	for (int j = 1; j <= anzVariablen * 2; j++) {
		    		if (j <= anzVariablen && (j % (this.sumDauer * this.anzP) == aktuelleZE + (aktuellesP - 1) * this.sumDauer
		    			|| (this.sumDauer == aktuelleZE && anzRestriktionen == i && j % (this.sumDauer * this.anzP) == 0))) {
		    			variables += "1" + this.separator;
		    		} else {
		    			variables += "0" + this.separator;
		    		}
		    	}
		    	if (aktuelleZE == this.sumDauer) {
			    	aktuelleZE = 0;
			    	aktuellesP++;
			    }
		    
		    aktuelleZE++;
		    rest.put("function", variables);	// left (x-)side of the restriction "x1+x2+x3" = y
		    rest.put("operator", "<=");			// center of the restriction x1+x2+x3 "=" y
		    rest.put("result", "1");			// right (y-)side of the restriction x1+x2+x3 = "y"
		    matrix.add(rest);
		}
		
		return matrix;
    }

    /* Create restriction: Only one machine can work on one product pro time period. */
    private List<Map<String, String>> getMachineRestrictions() {
		List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
		int anzRestriktionen = this.sumDauer * this.anzM;
		int anzVariablen = this.anzP * this.anzM * this.sumDauer;
		int aktuelleZE = 1;
		int aktuelleM = 1;
	
		for (int i = 1; i <= anzRestriktionen; i++) {
		    Map<String, String> rest = new HashMap<String, String>();
		    String variables = "";
		    
		    for (int j = 1; j <= anzVariablen * 2; j++) {
		    	if (j <= anzVariablen && (j % this.sumDauer == aktuelleZE || (this.sumDauer == aktuelleZE && j % this.sumDauer == 0))
		    		&& j <= aktuelleM * this.sumDauer * this.anzP && j > (aktuelleM - 1) * this.sumDauer * this.anzP) {
		    		variables += "1" + this.separator;
		    	} else {
		    		variables += "0" + this.separator;
		    	}
		    }
		    
		    if (aktuelleZE == this.sumDauer) {
				aktuelleZE = 0;
				aktuelleM++;
		    }
		    
		    aktuelleZE++;
		    rest.put("function", variables);	// left (x-)side of the restriction "x1+x2+x3" = y
		    rest.put("operator", "<=");			// center of the restriction x1+x2+x3 "=" y
		    rest.put("result", "1");			// right (y-)side of the restriction x1+x2+x3 = "y"
		    matrix.add(rest);
		}
		
		return matrix;
    }

    /* Create restriction: Method reads the input from the UI and calculates the minimum time period. */
    private List<Map<String, String>> getDurationRestrictions(List<List<JFormattedTextField>> jFTFieldList) {
    	List<String> values = new ArrayList();
    	
    	for (List<JFormattedTextField> jFormattedTextFields : jFTFieldList) {
    		for (JFormattedTextField jFormattedTextField : jFormattedTextFields) {
    			values.add(jFormattedTextField.getText());
    		}
    	}
    	
		int anzRestriktionen = this.anzP * this.anzM;
		int anzVariablen = this.anzP * this.anzM * this.sumDauer;
		List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
		
		for (int i = 1; i <= anzRestriktionen; i++) {
		    Map<String, String> rest = new HashMap<String, String>();
		    String variables = "";
		    
		    for (int j = 1; j <= anzVariablen * 2; j++) {
		    	if (j <= anzVariablen && j <= i * this.sumDauer && j > (i - 1) * this.sumDauer) {
		    		variables += "1" + this.separator;
		    	} else {
		    		variables += "0" + this.separator;
		    	}
		    }
		    
		    rest.put("function", variables);			// left (x-)side of the restriction "x1+x2+x3" = y
		    /* An error occurs if operator is "=". So the operator is set with ">=" It doesn't matter.
		     * In correct model, operator have to be "=". 
		     */
		    rest.put("operator", ">=");					// center of the restriction x1+x2+x3 "=" y , 
		    rest.put("result", values.get(i - 1));		// right (y-)side of the restriction x1+x2+x3 = "y"
		    matrix.add(rest);
		}
		
		return matrix;
    }
    
    /* Calculate the maximum processing time. */
    private int getMaxDuration(List<List<JFormattedTextField>> jFTFieldList) {
    	int sum = 0;
    	
    	for (List<JFormattedTextField> jFormattedTextFields : jFTFieldList) {
    		for (JFormattedTextField jFormattedTextField : jFormattedTextFields) {
    			Number no = (Number) jFormattedTextField.getValue();
    			if (no == null) {
    				no = 0;
    			}
    			
    			sum += no.intValue();
    		}
    	}
    	
    	return sum;
    }

    private ResultList getResult(List<Map<String, String>> matrix) {
	try {
	    int anzRestriktionen = this.anzP * this.anzM * (1 + this.sumDauer) + (this.anzM + this.anzP) * this.sumDauer;
	    int anzVariablen = this.anzP * this.anzM * this.sumDauer * 2;
	    LpSolve solver = LpSolve.makeLp(anzRestriktionen, anzVariablen);	// Create a problem with 4 variables and 0 constraints

	    /* adding constraints to the lp-model (solver). */
	    for (Map<String, String> restriktion : matrix) {
	    	if (restriktion.get("operator").equals(">=")) {
	    		solver.strAddConstraint(restriktion.get("function"), LpSolve.GE, Integer.valueOf(restriktion.get("result")));
	    	} else if (restriktion.get("operator").equals("<=")) {
	    		solver.strAddConstraint(restriktion.get("function"), LpSolve.LE, Integer.valueOf(restriktion.get("result")));
	    	}
	    }

	    /* sets the objective function */
	    String variables = "";
	    
	    for (int j = 1; j <= this.anzP * this.anzM * 2; j++) {
	    	for (int k = 1; k <= this.sumDauer; k++) {
	    		if(j <= this.anzP * this.anzM) {
	    			variables += (k * 5) + this.separator;
	    		} else {
	    			variables += "-1" + this.separator;
	    		}
	    	}
	    }
	    
	    solver.strSetObjFn(variables);
	    solver.setMinim();				// minimize

	    /* Sets all variables as integer. */
	    for (int i = 1; i <= anzVariablen; i++) {
	    	solver.setInt(i, true);
	    }
	    System.out.println("jetzt wird analysiert");
	    solver.solve();		// solve the lp-model

	    /* Save the lp-model as a text-file in the project directory.
	     * Code extended at 12.01.2015 by Sebastian Stephan
	     */
	    try {
 			file = File.createTempFile("LP-Modell", ".txt", new File(dirName));
 			writer = new FileWriter(file);
 			for (Map<String, String> map : matrix) {
 				writer.write(map.get("function"));
 				writer.write(map.get("operator") + this.separator);
 				writer.write(map.get("result"));
 				writer.write("\r\n");
 			}
 			writer.flush();
 			writer.close();
 		} catch (IOException e) {
 			e.printStackTrace();
 			System.out.println("File konnte nicht erstellt werden!");
 		}

 		/* Save the solution from lpsolve in a separate file in the project directory.
 		 * Following code extended at 12.01.2015 by Sebastian Stephan
 		 */
 		try {
			file2 = File.createTempFile("LP-Solve",".txt", new File(dirName));
			solver.setOutputfile(file2.getName());
			solver.printObjective();		// print lpsolve solution (output) in command box, text-file and cmd
			solver.printSolution(1);		// print lpsolve solution (output) in command box, text-file and cmd
			solver.printConstraints(1);		// print lpsolve solution (output) in command box, text-file and cmd
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File konnte nicht erstellt werden!");
		}
 		
 		JOptionPane.showMessageDialog(null, "Das LP-Modell sowie der Lösungsvektor wurden im Ordner \n"+ file.getAbsolutePath() + "\n temporär gespeichert.");
 		file.deleteOnExit();
 		file2.deleteOnExit();
 		
	    double[] var = solver.getPtrVariables();
	    /* Delete the problem, clear memory and analyse result. */
	    solver.deleteLp();
	    ResultList result = this.analyseResult(var);
	    return result;
	} catch (LpSolveException e) {
	    e.printStackTrace();
	}
	
	return null;
    }
}