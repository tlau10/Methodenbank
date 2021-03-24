/*
 * Created on 14.04.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package util;

import java.util.StringTokenizer;

/**
 * @author Raetz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RegularExpressionHelper {
	
	public static boolean contains(String data, String pattern) {
		
		if(pattern.length()>data.length())
			return false;
		
		boolean matches;
		
		for(int i=0;i<data.length()-pattern.length();i++) {
			matches = data.regionMatches(true, i, pattern, 0, pattern.length());
			if(matches)
				return true;
		}			
		
		return false;
	}
	
	public static String removeFormatingChars(String data) {
		String result = data.replaceAll("\n", "");
		result = result.replaceAll("\r", "");
		result = result.replaceAll("\t", " ");
		return result;
	}
	
	public static String removeR(String data) {
		return data.replaceAll("\r", "");
	}
	
	public static String [] getStringArray(StringTokenizer tokenizer) {
		String [] result = new String [tokenizer.countTokens()];
		
		for(int i=0; i<result.length; i++)
			result[i] = tokenizer.nextToken();
		
		return result;
	}
}
