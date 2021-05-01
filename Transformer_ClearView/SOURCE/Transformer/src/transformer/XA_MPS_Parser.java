/*
 * Created on 14.04.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package transformer;

import java.util.Iterator;
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
public class XA_MPS_Parser implements Parser {

	/* (non-Javadoc)
	 * @see interfaces.LPParser#parse(java.lang.String)
	 */
	public Document parse(String data) {
		Document result = new DocumentFactory().createDocument();
		Element root = result.addElement("LinearProgram");
		root.addAttribute("sourceFormat", "XA MPS-Format");
		
		Element input = root.addElement("Input");
		
		data = RegularExpressionHelper.removeR(data);
		
		// String in Zeilen aufsplitten
		String[] s =
			RegularExpressionHelper.getStringArray(
				new StringTokenizer(data, "\n"));
				
		//**************************************************************************************
		// EINGABEDATEN ERMITTELN
			
		// Zielfunktion und Restriktionen finden
		RE re = new RE("\\s+X\\d+\\s+\\S+\\s+(-*)\\d+\\.*\\d*\\.");

		String[] strings = re.grep(s);
		
		Element objectiveFunction = null;
		Element variable = null;
		Element constraint = null;
		
		for(int i=0; i<strings.length; i++) {
				
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));		
			
			if(tokens.length == 3) {
				
				if(tokens[1].equals("ZF")) {
					if(input.elements("ObjectiveFunction").size() == 0) {
						objectiveFunction = input.addElement("ObjectiveFunction");				
					}
					variable = objectiveFunction.addElement("Variable");
					variable.addAttribute("name", tokens[0]);
					if(tokens[2].endsWith("."))					
						variable.setText(tokens[2].substring(0, tokens[2].lastIndexOf(".")));
					else
						variable.setText(tokens[2]);
					//variable.setText(tokens[2]/*.substring(0, tokens[2].lastIndexOf("."))*/);
				}
				else {
					Element temp = null;					
					Iterator iterator = input.elementIterator("Constraint");
					
					while(iterator.hasNext()) {
						Element next = (Element) iterator.next();
						if(next.attributeValue("name").equals(tokens[1]))
							temp = next;
					}
					
					if(temp == null) {
						constraint = input.addElement("Constraint");
						constraint.addAttribute("name", tokens[1]);
						variable = constraint.addElement("Variable");
						variable.addAttribute("name", tokens[0]);
						if(tokens[2].endsWith("."))					
							variable.setText(tokens[2].substring(0, tokens[2].lastIndexOf(".")));
						else
							variable.setText(tokens[2]);
					}
					else {
						variable = temp.addElement("Variable");
						variable.addAttribute("name", tokens[0]);
						if(tokens[2].endsWith("."))					
							variable.setText(tokens[2].substring(0, tokens[2].lastIndexOf(".")));
						else
							variable.setText(tokens[2]);
					}
				}
			}
		}
		
		// Operator für die Restriktionen ermittlen
		re = new RE("\\s+[E|L|G]\\s+R\\d+");
		strings = re.grep(s);
		
		for(int i=0; i<strings.length; i++) {
			String temp = strings[i].substring(0, strings[i].lastIndexOf(" "));
			String name = strings[i].substring(strings[i].lastIndexOf(" ")+1 ,strings[i].length());
			Element tempE = null;
			Iterator iterator = input.elementIterator("Constraint");
			
			while(iterator.hasNext()) {
				Element next = (Element) iterator.next();
				if(next.attributeValue("name").equals(name))
					tempE = next;
			}
			
			if(temp.endsWith("E ")) {
				tempE.addElement("Operator").setText("=");
			}
			else if(temp.endsWith("L ")){
				tempE.addElement("Operator").setText("<=");				
			}
			else if(temp.endsWith("G ")) {
				tempE.addElement("Operator").setText(">=");
			}
		}
		
		// Werte des B-Vektors ermitteln
		re = new RE("\\s+MYRHS\\s+R\\d+\\s+(-*)\\d+\\.*\\d*\\.");
		strings = re.grep(s);
		
		for(int i=0; i<strings.length; i++) {
			String temp = strings[i].substring(strings[i].lastIndexOf("R"), strings[i].lastIndexOf(" "));
			temp = temp.trim();
			
			Element tempE = null;
			Iterator iterator = input.elementIterator("Constraint");
			
			while(iterator.hasNext()) {
				Element next = (Element) iterator.next();
				if(next.attributeValue("name").equals(temp))
					tempE = next;
			}
			
			tempE.addElement("b-Value").setText(strings[i].substring(strings[i].lastIndexOf(" ")+1, strings[i].length()-1/*lastIndexOf(".")*/));
		}
		
		// 0-Werte setzen
		
		Iterator iterator = input.elementIterator("Constraint");
		while(iterator.hasNext()) {
			Element next = (Element) iterator.next();
			if(next.elements("b-Value").size() == 0)
				next.addElement("b-Value").setText("0");
		}
		
		
		//Optimierungsart setzen
		 re = new RE("  OBJECTIVE FUNCTION IS.*");

		 String[] optimization = re.grep(s);
			
		 if(optimization [0].endsWith("MAXIMIZED.") && objectiveFunction != null)
			 objectiveFunction.addElement("Optimization").setText("MAXIMIZE");
		 else if(objectiveFunction != null)
			 objectiveFunction.addElement("Optimization").setText("MINIMIZE");

		// Grenzen setzen				
		Element bound = null;
		
		re = new RE("\\s+(UP|LO)\\s+");

		strings = re.grep(s);	
		
		for(int i=0;i<strings.length;i++) {			
			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			if(tokens.length == 4) {
				bound = input.addElement("Bound");
				if(tokens[0].startsWith("UP")) {
					bound.addAttribute("name", tokens[2]);
					bound.addElement("Operator").setText("<=");
					if(tokens[3].endsWith("."))					
						bound.addElement("Value").setText(tokens[3].substring(0, tokens[3].lastIndexOf(".")));
					else
						bound.addElement("Value").setText(tokens[3]);
				}				
				else if(tokens[0].startsWith("LO")) {
					bound.addAttribute("name", tokens[2]);
					bound.addElement("Operator").setText(">=");
					if(tokens[3].endsWith("."))					
						bound.addElement("Value").setText(tokens[3].substring(0, tokens[3].lastIndexOf(".")));
					else
						bound.addElement("Value").setText(tokens[3]);
				}
			}
		}
						
		//**************************************************************************************
		// ERGEBNISSE ERMITTELN	
		Element output = root.addElement("Output");

		// Meldungen suchen
		re = new RE("(\\s)*U N B O U N D E D   V A R I A B L E(.)*");	
		strings = re.grep(s);

		if(strings.length > 0) {
			strings[0].trim();
			output.addElement("Message").setText("Unbeschraenkte Variable " + strings[0].substring(strings[0].lastIndexOf("x"), strings[0].length()));
		}
		
		re = new RE("^(\\s)*N O    F E A S I B L E    S O L U T I O N(.)*");
		strings = re.grep(s);
		
		if(strings.length > 0) {
			strings[0].trim();
			output.addElement("Message").setText("Keine Loesung");
		}
		
		// Optimale Lösung suchen				
		re = new RE("(\\s)*O P T I M A L   S O L U T I O N ---> OBJECTIVE(.)*");		
		strings = re.grep(s);
		
		if(strings.length > 0) {
			strings[0] = strings[0].trim();
			output.addElement("ObjectiveFunction-Value").setText(strings[0].substring(strings[0].lastIndexOf(" ")+1, strings[0].length()));
		}
		
		// Variablenwerte suchen
		String [] variables = RegularExpressionHelper.getStringArray(new StringTokenizer(data, "|IU"));
		
		re = new RE("(\\s+)(X|x)(\\d*)(\\s*)(\\d*)\\.(\\d*)(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		
		strings = re.grep(variables);
		
		variables = RegularExpressionHelper.getStringArray(new StringTokenizer(data, "|I"));
		
		for(int i=0; i<strings.length; i++) {			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			if(tokens.length == 3) {
				Element value = output.addElement("Variable-Value");
				value.addAttribute("name", tokens[0]);
				value.setText(tokens[1]);
			}
			else if(tokens.length == 4) {
				Element value = output.addElement("Variable-Value");
				value.addAttribute("name", tokens[1]);
				value.setText(tokens[2]);
			}
		}

		// Reduced-Costs-Namen suchen
		re = new RE("(.*)(\\s+)(X|x)(\\d*)(\\s*)(-*)(\\d*)\\.(\\d*)(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		String [] names = re.grep(variables);	
		
		// Reduced-Costs suchen
		re = new RE("(^\\s+)REDUCED COST(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		
		strings = re.grep(variables);						
		
		for(int i=0; i<strings.length; i++) {			
			String [] tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			if(tokens.length == 3) {				
				Element value = output.addElement("ReducedCost");
				String [] tempTokens = null;
				tempTokens = RegularExpressionHelper.getStringArray(new StringTokenizer(names[i/*/2*/], " "));
				 
				/*if(i%2 == 0)*/
				if(tempTokens[0].startsWith("X") || tempTokens[0].startsWith("x") ) {
					value.addAttribute("name", tempTokens[0]);
					
				}
				else {
					value.addAttribute("name", tempTokens[1]);					
				}
				value.setText(tokens[2]);	
				/*else if(tempTokens.length >= 4)
					value.addAttribute("name", tempTokens[4]);*/
					
				
			}
		}

		// Dual-Namen suchen
		re = new RE("^(\\s+)R(\\d*)(\\s*)(-*)(\\d*)((,(\\d*))*)\\.(\\d*)(\\s*)(.*)(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		names = re.grep(variables);	
		
		// Dual-Werte suchen
		re = new RE("(^\\s+)DUAL VALUE(\\s*)(-*)(\\d*)\\.(\\d*)(.*)");
		
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
