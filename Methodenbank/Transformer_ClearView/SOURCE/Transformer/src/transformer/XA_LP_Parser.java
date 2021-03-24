/*
 * Created on 14.04.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package transformer;

import interfaces.Parser;

import java.util.StringTokenizer;

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
public class XA_LP_Parser implements Parser {

	/* (non-Javadoc)
	 * @see interfaces.Parser#parse(java.lang.String)
	 */
	public Document parse(String data) {

		Document result = new DocumentFactory().createDocument();
		Element root = result.addElement("LinearProgram");
		root.addAttribute("sourceFormat", "XA Equation Style");
		
		Element input = root.addElement("Input");

		data = RegularExpressionHelper.removeR(data);

		// String in Zeilen aufsplitten
		String[] s =
			RegularExpressionHelper.getStringArray(
				new StringTokenizer(data, "\n"));
		
		//**************************************************************************************
		// EINGABEDATEN ERMITTELN
			
		// Zielfunktion und Restriktionen finden
		RE re = new RE("\\s+((((\\+|-)\\s)?)(\\.|\\d+)(\\.*)(\\d*)\\sx\\d+)+");

		String[] strings = re.grep(s);

		re = new RE("(\\s*)");

		String[] rest = null;

		Element constraint = null;
		Element objectiveFunction = null;
		Element lastElement = null;
		
		boolean changed = false;
		
		// Restriktionen und Zielfunktion nach XML schreiben
		for (int i = 0; i < strings.length; i++) {
			
			// Restriktion
			if (strings[i].indexOf("R") != -1) {
				changed = true;
				String temp =
					strings[i].substring(
						strings[i].indexOf("R"),
						strings[i].indexOf(":"));
				constraint = input.addElement("Constraint");
				constraint.addAttribute("name", temp);
				lastElement = constraint;
				rest =
					RegularExpressionHelper.getStringArray(
						new StringTokenizer(strings[i], " "));

				Element variable = null;

				for (int j = 0; j < rest.length; j++) {
					if (rest[j].equals("+") || rest[j].equals("-"));
					else if (rest[j].startsWith("R"));
					else if (rest[j].startsWith("x")) {
						variable.addAttribute("name", rest[j]);
					} else if (
						rest[j].startsWith("<")
							|| rest[j].startsWith(">")
							|| rest[j].startsWith("=")) {
						constraint.addElement("Operator").setText(rest[j]);
					}
					// Wert schreiben
					else if (constraint.elements("Operator").size() == 0) {
						if ((j - 1) >= 0 && rest[j - 1].equals("-")) {
							variable = constraint.addElement("Variable");
							variable.setText(rest[j - 1] + rest[j]);
						} else {
							variable = constraint.addElement("Variable");
							variable.setText(rest[j]);
						}
					} else {
						if ((j - 1) >= 0 && rest[j - 1].equals("-")) {
							variable = constraint.addElement("b-Value");
							variable.setText(rest[j - 1] + rest[j]);
						} else {
							variable = constraint.addElement("b-Value");
							variable.setText(rest[j]);
						}
					}
				}

			}
			// Fortsetzung der letzten Zeile
			else if(changed){
				
				if(lastElement.equals(objectiveFunction)) {
					rest =
							RegularExpressionHelper.getStringArray(
							new StringTokenizer(strings[i], " "));

					Element variable = null;
					for (int j = 0; j < rest.length; j++) {
						if (rest[j].equals("+") || rest[j].equals("-"));					
						else if (rest[j].startsWith("x")) {
							variable.addAttribute("name", rest[j]);
						}					 
						// Wert schreiben
						else {
							if ((j - 1) >= 0 && rest[j - 1].equals("-")) {
								variable = constraint.addElement("Variable");
								variable.setText(rest[j - 1] + rest[j]);
							} 
							else {
								variable = objectiveFunction.addElement("Variable");
								variable.setText(rest[j]);
							}					
						}
					}
				}
				else {
					rest =
						RegularExpressionHelper.getStringArray(
							new StringTokenizer(strings[i], " "));

					Element variable = null;

					for (int j = 0; j < rest.length; j++) {
						if (rest[j].equals("+") || rest[j].equals("-"));
						else if (rest[j].startsWith("R"));
						else if (rest[j].startsWith("x")) {
							variable.addAttribute("name", rest[j]);
						} else if (
							rest[j].startsWith("<")
								|| rest[j].startsWith(">")
								|| rest[j].startsWith("=")) {
							constraint.addElement("Operator").setText(rest[j]);
						}
						// Wert schreiben
						else if (constraint.elements("Operator").size() == 0) {
							if ((j - 1) >= 0 && rest[j - 1].equals("-")) {
								variable = constraint.addElement("Variable");
								variable.setText(rest[j - 1] + rest[j]);
							} else {
								variable = constraint.addElement("Variable");
								variable.setText(rest[j]);
							}
						} else {
							if ((j - 1) >= 0 && rest[j - 1].equals("-")) {
								variable = constraint.addElement("b-Value");
								variable.setText(rest[j - 1] + rest[j]);
							} else {
								variable = constraint.addElement("b-Value");
								variable.setText(rest[j]);
							}
						}
					}

				}
			}	
			// Zielfunktion
			else {
				objectiveFunction = input.addElement("ObjectiveFunction");			
				lastElement = objectiveFunction;	
				changed = true;
				rest =
					RegularExpressionHelper.getStringArray(
						new StringTokenizer(strings[i], " "));

				Element variable = null;

				for (int j = 0; j < rest.length; j++) {
					if (rest[j].equals("+") || rest[j].equals("-"));					
					else if (rest[j].startsWith("x")) {
						variable.addAttribute("name", rest[j]);
					} 
					// Wert schreiben
					else {
						if ((j - 1) >= 0 && rest[j - 1].equals("-")) {
							variable = constraint.addElement("Variable");
							variable.setText(rest[j - 1] + rest[j]);
						} else {
							variable = objectiveFunction.addElement("Variable");
							variable.setText(rest[j]);
						}					
					}
				}
			}			
		}
		// Optimierungsart setzen
		re = new RE("^\\.\\.OBJECTIVE .*");

		String[] optimization = re.grep(s);
			
		if(optimization [0].endsWith("MAXIMIZE"))
			objectiveFunction.addElement("Optimization").setText("MAXIMIZE");
		else
			objectiveFunction.addElement("Optimization").setText("MINIMIZE");
			
		// Grenzen setzen				
		Element bound = null;
		
		re = new RE("^((\\s*)x\\d+[>=|<=].*)");

		strings = re.grep(s);	
		
		for(int i=0;i<strings.length;i++) {
			bound = input.addElement("Bound");
			
			if(strings[i].indexOf(">") != -1) {
				bound.addAttribute("name", strings[i].substring(strings[i].indexOf("x"), strings[i].indexOf(">")));
				Element operator = bound.addElement("Operator");
				operator.setText(strings[i].substring(strings[i].indexOf(">"), strings[i].indexOf("=")+1));				
			}
			else if(strings[i].indexOf("<") != -1) {
				bound.addAttribute("name", strings[i].substring(strings[i].indexOf("x"), strings[i].indexOf("<")));
				Element operator = bound.addElement("Operator");
				operator.setText(strings[i].substring(strings[i].indexOf("<"), strings[i].indexOf("=")+1));				
			}
			Element value = bound.addElement("Value");
			value.setText(strings[i].substring(strings[i].indexOf("=")+1, strings[i].length()));
		}
		
		//**************************************************************************************
		// ERGEBNISSE ERMITTELN	
		Element output = root.addElement("Output");

		// Meldungen suchen
		re = new RE("^(\\s)*U N B O U N D E D   V A R I A B L E(.)*");	
		strings = re.grep(s);
		
		if(strings.length > 0) {
			strings[0].trim();
			if(strings[0].lastIndexOf("x") != -1)
				output.addElement("Message").setText("Unbeschraenkte Variable " + strings[0].substring(strings[0].lastIndexOf("x"), strings[0].length()));
			else
				output.addElement("Message").setText("Unbeschraenkte Variable " + strings[0].substring(strings[0].lastIndexOf("R"), strings[0].length()));
		}
		
		re = new RE("^(\\s)*N O    F E A S I B L E    S O L U T I O N(.)*");
		strings = re.grep(s);
		
		if(strings.length > 0) {
			strings[0].trim();
			output.addElement("Message").setText("Keine Loesung");
		}
		
		// Optimale Lösung suchen				
		re = new RE("^(\\s)*O P T I M A L   S O L U T I O N ---> OBJECTIVE(.)*");		
		strings = re.grep(s);
		
		if(strings.length > 0) {
			strings[0].trim();
			output.addElement("ObjectiveFunction-Value").setText(strings[0].substring(strings[0].lastIndexOf(" ")+1, strings[0].length()));
		}
		
		// Variablenwerte suchen
		String [] variables = RegularExpressionHelper.getStringArray(new StringTokenizer(data, "|IU"));
		
		re = new RE("^(\\s+)(X|x)(\\d*)(\\s*)(-*)(\\.|\\d*)\\.(\\d*)(\\s*)(-*)(\\d*)\\.(\\d*)");
		
		strings = re.grep(variables);
		
		for(int i=0; i<strings.length; i++) {			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			if(tokens.length == 3) {
				Element value = output.addElement("Variable-Value");
				value.addAttribute("name", tokens[0]);
				value.setText(tokens[1]);
			}
		}

		// Reduced-Costs-Namen suchen
		// U kann auch Trennzeichen sein
		variables = RegularExpressionHelper.getStringArray(new StringTokenizer(data, "|IU"));
		re = new RE("(.*)(\\s+)(X|x)(\\d*)(\\s*)(-*)(\\d*)\\.(\\d*)(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		String [] names = re.grep(variables);	
		
		// U als Trennzeichen ignorieren
		variables = RegularExpressionHelper.getStringArray(new StringTokenizer(data, "|I"));
		
		// Reduced-Costs suchen
		re = new RE("^(\\s+)REDUCED COST(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		
		strings = re.grep(variables);						
		
		for(int i=0; i<strings.length; i++) {			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			if(tokens.length == 3) {				
				Element value = output.addElement("ReducedCost");
				String [] tempTokens = null;
				tempTokens = RegularExpressionHelper.getStringArray(new StringTokenizer(names[i/*/2*/], " "));
				 								
				if(tempTokens[0].startsWith("X") || tempTokens[0].startsWith("x") ) {
					value.addAttribute("name", tempTokens[0]);				
				}
				else {
					value.addAttribute("name", tempTokens[1]);					
				} 
				/*if(i%2 == 0)*/
				value.addAttribute("name", tempTokens[0]);
				/*else if(tempTokens.length >= 4)
					value.addAttribute("name", tempTokens[4]);*/
					
				
				value.setText(tokens[2]);
			}
		}

		// Dual-Namen suchen
		re = new RE("^(\\s+)R(\\d*)(\\s*)(-*)(\\d*)((,(\\d*))*)\\.(\\d*)(\\s*)(.*)(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		names = re.grep(variables);	
		
		// Dual-Werte suchen
		re = new RE("^(\\s+)DUAL VALUE(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		
		strings = re.grep(variables);						
		
		for(int i=0; i<strings.length; i++) {			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			if(tokens.length == 3) {				
				Element value = output.addElement("Dual-Value");
				value.addAttribute("name", RegularExpressionHelper.getStringArray(new StringTokenizer(names[i], " "))[0]);
				value.setText(tokens[2]);
			}
		}
		
		return result;
	}

}
