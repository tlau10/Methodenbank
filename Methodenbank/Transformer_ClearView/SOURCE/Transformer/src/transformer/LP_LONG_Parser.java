/*
 * Created on 04.05.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package transformer;

import java.util.StringTokenizer;
import java.util.Vector;

import interfaces.Parser;

import org.apache.regexp.RE;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import util.RegularExpressionHelper;

/**
 * @author Raetz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LP_LONG_Parser implements Parser {

	/* (non-Javadoc)
	 * @see interfaces.Parser#parse(java.lang.String)
	 */
	public Document parse(String data) {
		
		Document result = new DocumentFactory().createDocument();
		Element root = result.addElement("LinearProgram");
		root.addAttribute("sourceFormat", "LP-Solve lang");
						
		Element input = root.addElement("Input");
				
		data = RegularExpressionHelper.removeR(data);
				
		// String in Zeilen aufsplitten
		String[] s =
			RegularExpressionHelper.getStringArray(
				new StringTokenizer(data, "\n"));

		//**************************************************************************************
		// EINGABEDATEN ERMITTELN
						
		Element objectiveFunction = input.addElement("ObjectiveFunction");		
		
		String optimization = "MAXIMIZE";
		
		RE re = new RE("^Maximise(\\s)*");	
		String[] strings = re.grep(s);	
		
		if(strings.length == 0) {
			re = new RE("^Minimize(\\s)*");
			optimization = "MINIMIZE";
			strings = re.grep(s);
		}
					
		if(strings.length != 1) {
			return null;
		}
						
		String [] objectiveFunctionStringArray = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[0], " "));
						
		String [] tokens;
						
		re = new RE("^(\\s)*(x(\\d)+)+");				
		strings = re.grep(s);				
		
		int variableLine = -1;
		boolean correct = true;
		
		for(int i=0; i<strings.length; i++) {		
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			correct = true;
			for(int j=0;j<tokens.length; j++) {
				if(!tokens[j].trim().startsWith("x"))
					correct = false;
			}
			if(correct)
				variableLine = i;
		}						
		
		if(variableLine == -1)
			return null;
					
		tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[variableLine], " "));
		
		Vector variableNames = new Vector();
		
		for(int i=0; i<tokens.length; i++)
			variableNames.add(tokens[i].trim());
					
		for(int i=1; i<objectiveFunctionStringArray.length;i++) {
			Element variable = objectiveFunction.addElement("Variable");
			variable.addAttribute("name", (String)variableNames.get(i-1));
			variable.setText(objectiveFunctionStringArray[i].trim());
		}
		
		objectiveFunction.addElement("Optimization").setText(optimization);
		
		// Restriktionen finden		
		re = new RE("^(\\s)+R(\\d)+((\\s)+(-*)(\\d)*\\.(\\d)*)+(\\s)+.*(\\s)+(-*)(\\d)*\\.(\\d)*");
		
		strings = re.grep(s);
		
		for(int i=0;i<strings.length;i++) {
			
			Element constraint = input.addElement("Constraint");
			
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			constraint.addAttribute("name", tokens[0].trim());
			
			for(int j=1;j<tokens.length-2;j++) {
				Element var = constraint.addElement("Variable");
				var.addAttribute("name", (String)variableNames.get(j-1));
				var.setText(tokens[j]);
			}
			
			constraint.addElement("Operator").setText(tokens[tokens.length-2]);
			constraint.addElement("b-Value").setText(tokens[tokens.length-1]);
			
		}
		
		// Obere Grenzen finden
		re = new RE("^upbo");		
		strings = re.grep(s);
	
		if(strings.length>0) {
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[0], " "));
			
			for(int i=1; i<tokens.length;i++) {
				if(!tokens[i].trim().equals("Inf")) {
					Element bound = input.addElement("Bound");
					bound.addAttribute("name", (String) variableNames.get(i-1));	
				
					bound.addElement("Operator").setText("<=");
					bound.addElement("Value").setText(tokens[i].trim());
				}				
			}
		}
	
		// Untere Grenzen finden
		re = new RE("^lowbo");		
		strings = re.grep(s);

		if(strings.length>0) {
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[0], " "));

			for(int i=1; i<tokens.length;i++) {
				if(!tokens[i].trim().equals("0.00")) {
					Element bound = input.addElement("Bound");
					bound.addAttribute("name", (String) variableNames.get(i-1));	
				
					bound.addElement("Operator").setText(">=");
					bound.addElement("Value").setText(tokens[i].trim());
				}				
			}
		}
					
		//**************************************************************************************
		// ERGEBNISSE ERMITTELN	
		
		Element output = root.addElement("Output");
		
		// Zielfunktionswert finden
		re = new RE("^(\\s)*Value of objective function:(.)*");	
		strings = re.grep(s);	
		
		if(strings.length != 1) 
			return null;
						
		String value = strings[0].substring(strings[0].lastIndexOf(" "), strings[0].length());
		double doubleValue = Double.parseDouble(value);
		
		output.addElement("ObjectiveFunction-Value").setText(new Double(doubleValue).toString());
				
		// Variablenwerte finden
		re = new RE("^x(\\d)+");	
		strings = re.grep(s);
		
		for(int i=0; i<strings.length;i++) {
			
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
		
			if(tokens.length == 2) {	
				Element variable = output.addElement("Variable-Value");
				
				variable.setText(new Double(tokens[1]).toString());
				variable.addAttribute("name", tokens[0].trim());
			}			
		}
		
		// Dualwerte finden
		re = new RE("^R(\\d)+");	
		strings = re.grep(s);
		
		// Nur die 2. Hälfte nehmen, da in der 1. Hälfte die Werte der Restiktion stehen 
		for(int i=strings.length/2; i<strings.length;i++) {
			
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
		
			if(tokens.length == 2) {	
				Element variable = output.addElement("Dual-Value");
				
				variable.setText(new Double(tokens[1]).toString());
				variable.addAttribute("name", tokens[0].trim());
			}			
		}				
		return result;

	}

}
