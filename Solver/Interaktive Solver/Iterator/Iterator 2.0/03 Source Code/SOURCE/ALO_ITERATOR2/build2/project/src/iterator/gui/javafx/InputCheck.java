package iterator.gui.javafx;

import iterator.Fascade;

public class InputCheck {
	private static String regexDecimal = "(-)?\\d+\\.\\d+";
	private static String regexFraction = "(-)?\\d+/(-)?\\d+";
	private static String regexNumber = "(-)?\\d+";
		
	public static boolean isValidTableauInput(String input){
		
    	
		if(input.matches(regexDecimal) || input.matches(regexFraction) || input.matches(regexNumber)){
			return true;
		}
		
		return false;
	}

}
