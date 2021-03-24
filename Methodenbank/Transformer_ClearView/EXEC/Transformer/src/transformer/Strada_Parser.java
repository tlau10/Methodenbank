/*
 * Created on 14.04.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package transformer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
public class Strada_Parser implements Parser {

	/* (non-Javadoc)
	 * @see interfaces.Parser#parse(java.lang.String)
	 */
	public Document parse(String data) {
		Document result = new DocumentFactory().createDocument();
		
		data = data.replaceAll("\t", " ");
		
		Element root = result.addElement("LinearProgram");
		root.addAttribute("sourceFormat", "Strada");
				
		Element input = root.addElement("Input");
				
		data = RegularExpressionHelper.removeR(data);
				
		// String in Zeilen aufsplitten
		String[] s =
			RegularExpressionHelper.getStringArray(
				new StringTokenizer(data, "\n"));
				
		//**************************************************************************************
		// EINGABEDATEN ERMITTELN
		
		RE re = new RE("\\s+(X\\d+|S\\d+)+\\s+MYRHS");
		
		String[] strings = re.grep(s);
		Vector variableNames = new Vector();
		
		if(strings.length > 0) {
			String [] temp = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[0], " "));
			
			for(int i=0; i<temp.length; i++) {
				if(temp[i].startsWith("X"))
					variableNames.add(temp[i]);
			}
		}
					
		// Zielfunktion und Restriktionen finden
		re = new RE("(R\\d+|ZF)(\\s+(-*)\\d+.\\d+){2,}");

		strings = re.grep(s);
		Element constraint = null;
		Element variable = null;
		boolean notAll = false;
		int counter = 0;
		if(strings.length > 0)
			notAll = true;
			
		Map structureVariables = new HashMap();
		
		while(notAll) {
			
			if(strings[counter].startsWith("ZF")) {
			 
				constraint = input.addElement("ObjectiveFunction");
				
				String [] temp = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[counter], " "));				
				
				for(int i=0;i<variableNames.size();i++) {
					
					variable = constraint.addElement("Variable");
					variable.addAttribute("name", (String)variableNames.get(i) );
					
					double offset = 0;
					
					Iterator iteratorConstraint = input.elementIterator("Constraint");
					while(iteratorConstraint.hasNext()) {
						Element nextConstraint = (Element) iteratorConstraint.next();
						
						Iterator iteratorVariable = nextConstraint.elementIterator("Variable");
						
						while(iteratorVariable.hasNext()) {
							Element nextVariable = (Element) iteratorVariable.next();
							if(nextVariable.attributeValue("name").equals(variable.attributeValue("name"))) {
								if(nextConstraint.element("Operator").getText().equals(">=")) {
									offset += 1000 * Double.parseDouble(nextVariable.getText());
								}
							}
						}
					}
					if(temp[i+1].startsWith("-")) {
						temp[i+1] = temp[i+1].replaceFirst("-", "");
					}

					variable.setText(new Double(Double.parseDouble(temp[i+1]) - offset).toString());

				}
				
				// Optimierungsart suchen
				re = new RE("SOLUTION \\(");
				strings = re.grep(s);
				
				for(int i=0;i<strings.length; i++) {
					if(strings[i].substring(strings[i].indexOf("(")+1, strings[i].lastIndexOf(")")).equals("Maximized"))
						constraint.addElement("Optimization").setText("MAXIMIZE");
					else
						constraint.addElement("Optimization").setText("MINIMIZE");	
				}
								
				notAll = false;
			}				
			// Restriktion
			else {	
				
				String [] temp = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[counter], " "));
				constraint = input.addElement("Constraint");
				constraint.addAttribute("name", strings[counter].substring(0, strings[counter].indexOf(" ")));
				for(int i=0;i<variableNames.size();i++) {
					
					variable = constraint.addElement("Variable");
					variable.addAttribute("name", (String)variableNames.get(i) );
					variable.setText(temp[i+1]);
				}
				
				boolean a = false;
				boolean b = false;
				
				// Operator bestimmmen
				for(int i=variableNames.size()+1;i<temp.length-1;i++) {
					if(temp[i].startsWith("1.0")) {
						
						// Position der Strukturvariablen merken!
						structureVariables.put(strings[counter].substring(0, strings[counter].indexOf(" ")), new Integer(i));
						a = true;
					}
					if(temp[i].startsWith("-1.0"))
						b = true;	
				}
								
				if(b)				
					constraint.addElement("Operator").setText(">=");
				else if (a)	
					constraint.addElement("Operator").setText("<=");				
								
				// B-Value setzen
				constraint.addElement("b-Value").setText(temp[temp.length-1]);				
				
			}
			counter ++;
		}
		
		//**************************************************************************************
		// ERGEBNISSE ERMITTELN		
		Element output = root.addElement("Output");
		
		// Optimale Lösung suchen						
		re = new RE("^(\\s)*O P T I M A L   S O L U T I O N ---> OBJECTIVE(.)*");		
		strings = re.grep(s);
		
		if(strings.length > 0) {
			strings[0].trim();
			output.addElement("ObjectiveFunction-Value").setText(strings[0].substring(strings[0].lastIndexOf(" ")+1, strings[0].length()));
		}

		// Variablen-Werte suchen
		re = new RE("^((x|X)(\\d+)(\\s+(-*)\\d+.\\d+))");
		strings = re.grep(s);
		
		String [] temp = null;
		
		for(int i=0;i<strings.length;i++) {
			temp = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));
			
			if(temp.length == 2) {			
				Element value = output.addElement("Variable-Value");
				value.addAttribute("name", temp[0]);
				value.setText(temp[1]);	
			}
		}
		
		// 0-Ergebnisse setzen
		for(int i=0; i<variableNames.size();i++) {
			
			boolean set = false;
			
			Iterator resultIterator = output.elementIterator("Variable-Value");
			while(resultIterator.hasNext()) {
				if(((Element)resultIterator.next()).attributeValue("name").equals((String)variableNames.get(i)))
					set = true;		
			}
			
			if(!set) {
				Element value = output.addElement("Variable-Value");
				value.addAttribute("name", (String)variableNames.get(i));
				value.setText("0");	
			}
		}
		
		// Zielfunktionszeile suchen
		re = new RE("^(ZF)(\\s+(-*)\\d+.\\d+){2,}");
		strings = re.grep(s);		
		if(strings.length-1 >= 0)
			temp = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[strings.length-1], " "));		

		// Reduced-Costs setzen
		for(int i=0; i<variableNames.size();i++) {
			Element reducedCost = output.addElement("Reduced-Cost");
			reducedCost.addAttribute("name", (String)variableNames.get(i));
			reducedCost.setText(temp[i+1]);
		}

		// Dual-Werte setzen
		Iterator dualIterator = input.elementIterator("Constraint");
						
		while (dualIterator.hasNext()) {		
			Element next = (Element) dualIterator.next();
			
			int i = ((Integer) structureVariables.get(next.attributeValue("name"))).intValue();
											
			Element dual = output.addElement("Dual-Value");
			dual.addAttribute("name", next.attributeValue("name"));
			dual.setText(temp[i]);				
			
		}
		return result;
	}

}
