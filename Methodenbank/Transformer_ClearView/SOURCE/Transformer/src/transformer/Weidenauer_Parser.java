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
public class Weidenauer_Parser implements Parser {

	/* (non-Javadoc)
	 * @see interfaces.Parser#parse(java.lang.String)
	 */
	public Document parse(String data) {
		Document result = new DocumentFactory().createDocument();
		Element root = result.addElement("LinearProgram");
		root.addAttribute("sourceFormat", "Weidenauer Optimizer");
						
		//Element input = root.addElement("Input");
				
		data = RegularExpressionHelper.removeR(data);
				
		// String in Zeilen aufsplitten
		String[] s =
			RegularExpressionHelper.getStringArray(
				new StringTokenizer(data, "\n"));
		
		String [] strings;
		String [] tokens;
		RE re;
						
		//**************************************************************************************
		// EINGABEDATEN ERMITTELN
						
		Element output = root.addElement("Output");
						
		re = new RE("^R(\\d)+(\\s)+");
		strings = re.grep(s);
		
		re = new RE("^ZF(\\s)+");
		strings = re.grep(s);
		
		if(strings.length >= 1) {
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[0], " "));
			
			if(tokens.length>=3) {
				String value = tokens[2];
				value = value.replaceAll("-", "");
				output.addElement("ObjectiveFunction-Value").setText(value);
			}
							
		}
				
		re = new RE("^X(\\d)+(\\s)+(\\w\\w)(\\s{0,9})(-?)(\\d)+");
		strings = re.grep(s);
		
		for(int i=0; i<strings.length; i++) {						
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));			
			
			if(tokens.length>=3) {
				Element var = output.addElement("Variable-Value");
				var.addAttribute("name", tokens[0].trim());
				var.setText(tokens[2].trim());
			}
		}
		
		re = new RE("^X(\\d)+(\\s)+(..)(\\s{10,})(-?)(\\d)+");
		strings = re.grep(s);
		
		for(int i=0; i<strings.length; i++) {						
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));			
			
			if(tokens.length>=3) {
				Element var = output.addElement("Variable-Value");
				var.addAttribute("name", tokens[0].trim());
				var.setText("0");
			}
		}
		
		re = new RE("^R(\\d)+(\\s)+(..)(\\s)+(-?)(\\d)+");
		strings = re.grep(s);
		
		for(int i=0; i<strings.length; i++) {						
			tokens = RegularExpressionHelper.getStringArray(new StringTokenizer(strings[i], " "));			
			
			if(tokens.length>=6) {
				Element var = output.addElement("Dual-Value");
				var.addAttribute("name", tokens[0].trim());
				var.setText(tokens[6].trim());
			}
			if(tokens.length>=4) {
				Element var = output.addElement("Slack");
				var.addAttribute("name", tokens[0].trim());
				var.setText(tokens[3].trim());
			}

		}
		
		return result;
	}

}
