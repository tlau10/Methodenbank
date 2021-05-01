package iterator.tableau;

import java.math.BigDecimal;
import java.text.DecimalFormat;


import org.apache.log4j.Logger;
import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Rational;
import org.jscience.mathematics.number.Real;

public class TableauFunctions {

	private static Logger logger = Logger.getLogger(TableauDouble.class);
	private static DecimalFormat f = new DecimalFormat("#0.0000"); 
	
	public static String tableauToString(TableauDTO tableau) {
		String out = new String();

		 out += "Tableau (" + tableau.getId() + ") -  A("+tableau.getAMatrixRestriction()+"/"+tableau.getAMatrixVariable()+"), b("+tableau.getBVektor().length+"), z("+tableau.getZielfunktion().length+")\r\n";
		 
		 // set A-Matrix and b-Vektor
		 for (int i = 0; i < tableau.getAMatrixRestriction(); i++) {

			for (int j = 0; j < tableau.getAMatrixVariable() ; j++) {
				out += f.format(tableau.getaMatrix()[j][i]) + " |";
				
				if(j == (tableau.getAMatrixVariable() - 1)){
					out += f.format(tableau.getBVektor()[i]) + " \r\n";
				}
			}
		}
		
		 out += "\r\n";
		 
		// set Zielfunktion
		for (int i = 0; i < tableau.getZielfunktion().length; i++) {
			out += f.format(tableau.getZielfunktion()[i]) + " |";
		}
		
		out += f.format(tableau.getZielfunktionskoeffizient());
		out += "\r\n   >> this tableau is rounded!";
		
		return out;
	}
	
	public static double[] parseBdArray(BigDecimal[] array){
		double[] doubleArray = new double[array.length];
		
		for (int i = 0; i < doubleArray.length; i++) {
			doubleArray[i] = array[i].doubleValue();
		}
		
		return doubleArray;
	}

	public static double[][] parseBd2NdArray(BigDecimal[][] array){
		if(array[0] == null){
			return null;
		}
		
		double[][] doubleArray = new double[array.length][array[0].length];
		
		for (int i = 0; i < doubleArray.length; i++) {
			for (int j = 0; j < doubleArray[0].length; j++) {
				doubleArray[i][j] = array[i][j].doubleValue();
			}
		}
		
		return doubleArray;
	}
	
	public static BigDecimal[] parseDoubleArray(double[] array){
		BigDecimal[] bdArray = new BigDecimal[array.length];
		for (int i = 0; i < array.length; i++) {
			bdArray[i] = new BigDecimal(array[i]);
		}
		return bdArray;
	}
	
	public static BigDecimal[][] parseDouble2NdArray(double[][] array){
		if(array[0] == null){
			return null;
		}
		BigDecimal[][] bdArray = new BigDecimal[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				bdArray[i][j] = new BigDecimal(array[i][j]);
			}
		}
		return bdArray;
	}
	
	public static Rational[] parseStringArrayToRational(String[] array){
		Rational[] bdArray = new Rational[array.length];
		for (int i = 0; i < array.length; i++) {
			bdArray[i] = parseRational(array[i]);
		}
		return bdArray;
	}
	
	public static Rational[][] parseString2NdArrayToRational(String [][] array){
		if(array[0] == null){
			return null;
		}
		Rational[][] bdArray = new Rational[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				bdArray[i][j] = parseRational(array[i][j]);
			}
		}
		return bdArray;
	}
	
	public static String[] parseRationalArrayToString(Rational[] array){
		String[] stringArray = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			stringArray[i] = parseString(array[i]);
		}
		return stringArray;
	}
	
	public static String[][] parseRational2NdArrayToString(Rational[][] aMatrix){
		if(aMatrix[0] == null){
			return null;
		}
		String[][] stringArray = new String[aMatrix.length][aMatrix[0].length];
		for (int i = 0; i < aMatrix.length; i++) {
			for (int j = 0; j < aMatrix[0].length; j++) {
				stringArray[i][j] = parseString(aMatrix[i][j]);
			}
		}
		return stringArray;
	}
	
	public static String parseString(Rational in){
		if(in == null){
			return "0";
		}
		
		//Prüfe ob 0 (0/1) oder Ganzzahl (x/1)
		if(in.toString().contains("/1")){
			return in.toString().replace("/1", "");
		}
		return in.toString();
	}
	
	public static Rational parseRational(String in){
		logger.debug("  >> parsing String ("+in+") to Rational");
		
		if(in.equals("0")){
			return Rational.ZERO;
		}
		
		if (in.contains(".")){
			Real real = Real.valueOf(in);
	    	
	    	LargeInteger zaehler = LargeInteger.ZERO;
	    	LargeInteger nenner = LargeInteger.ONE;


	    	String s = real.toString().replace("E"+real.getExponent(), "");	
	    	zaehler = LargeInteger.valueOf(s);
	        nenner = LargeInteger.valueOf("1");
	        
	        for (int i = 0; i < s.substring(s.length() + real.getExponent(), s.length()).length(); i++) {
	    		nenner = LargeInteger.valueOf(nenner.toString() + "0");
	    	}
	        
	        
	        logger.debug("  >> String is Decimal " + Rational.valueOf(zaehler, nenner).toString());
			return Rational.valueOf(zaehler,nenner);
		}

		return Rational.valueOf(in);
    	
	}
	
	
	/**************************************************************************************/
	// Big Decimal Converter
	
	public static String parseString(BigDecimal in){
		//keine Nachkommastellen vorhanden?
		if(in.precision() == 1){
			

		}
		
		return in.toString();
	}
	public static BigDecimal parseBigDecimal(String in){
		return new BigDecimal(in);
	}
	
	public static BigDecimal[] parseStringArray(String[] array){
		BigDecimal[] bdArray = new BigDecimal[array.length];
		for (int i = 0; i < array.length; i++) {
			bdArray[i] = parseBigDecimal(array[i]);
		}
		return bdArray;
	}
	
	public static BigDecimal[][] parseString2NdArray(String [][] array){
		if(array[0] == null){
			return null;
		}
		BigDecimal[][] bdArray = new BigDecimal[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				bdArray[i][j] = parseBigDecimal(array[i][j]);
			}
		}
		return bdArray;
	}
	
	public static String[] parseBdArrayToString(BigDecimal[] array){
		String[] stringArray = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			stringArray[i] = parseString(array[i]);
		}
		return stringArray;
	}
	
	public static String[][] parseBd2NdArrayToString(BigDecimal[][] aMatrix){
		if(aMatrix[0] == null){
			return null;
		}
		String[][] stringArray = new String[aMatrix.length][aMatrix[0].length];
		for (int i = 0; i < aMatrix.length; i++) {
			for (int j = 0; j < aMatrix[0].length; j++) {
				stringArray[i][j] = parseString(aMatrix[i][j]);
			}
		}
		return stringArray;
	}
	


}
