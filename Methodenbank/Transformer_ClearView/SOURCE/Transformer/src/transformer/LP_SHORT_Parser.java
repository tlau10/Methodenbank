/*
 * Created on 04.05.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package transformer;

import java.util.StringTokenizer;

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
public class LP_SHORT_Parser implements Parser {

	/* (non-Javadoc)
	 * @see interfaces.Parser#parse(java.lang.String)
	 */
	public Document parse(String data) {
		Document result = new DocumentFactory().createDocument();
		Element root = result.addElement("LinearProgram");
		root.addAttribute("sourceFormat", "LP-Solve kurz");
						
		Element output = root.addElement("Output");
				
		data = RegularExpressionHelper.removeR(data);
				
		// String in Zeilen aufsplitten
		String[] s =
			RegularExpressionHelper.getStringArray(
				new StringTokenizer(data, "\n"));
						
		//**************************************************************************************
		// ERGEBNISSE ERMITTELN	
		
		// Zielfunktionswert finden
		RE re = new RE("^(\\s)*Value of objective function:(.)*");	
		String[] strings = re.grep(s);	
		
		if(strings.length != 1) 
			return null;
						
		String value = strings[0].substring(strings[0].lastIndexOf(" "), strings[0].length());
		double doubleValue = Double.parseDouble(value);
		
		output.addElement("ObjectiveFunction-Value").setText(new Double(doubleValue).toString());
				
		// Variablenwerte finden
		re = new RE("^(\\s)*x(\\d)+");	
		strings = re.grep(s);
		
		for(int i=0; i<strings.length;i++) {
			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
		
			if(tokens.length == 2) {	
				Element variable = output.addElement("Variable-Value");
				
				variable.setText(new Double(tokens[1]).toString());
				variable.addAttribute("name", tokens[0].trim());
			}			
		}
		
		// Dualwerte finden
		re = new RE("^(\\s)*R(\\d)+");	
		strings = re.grep(s);
		
		for(int i=0; i<strings.length;i++) {
			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
		
			if(tokens.length == 2) {	
				Element variable = output.addElement("Dual-Value");
				
				variable.setText(new Double(tokens[1]).toString());
				variable.addAttribute("name", tokens[0].trim());
			}			
		}				
		return result;
	}

}
