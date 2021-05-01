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

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

/**
 * Check the input from the user. Only numbers between [0,99] are allowed.
 */
public class JobShopNumberFormatter extends NumberFormatter {

    JobShopNumberFormatter(NumberFormat format) {
    	super(format);
    }

    /*
     * Method revised at 12.01.2015 by Dennis Klein
     */
    public Object stringToValue(String s) throws ParseException {
    	Number number = null;
    	
    	if(s.matches("\\d*")) {
    		if(s.length() > 2) {
    			number = 99;
    		} else {
    			number = (Number)super.stringToValue(s);
    		}
    	} else {
    		JOptionPane.showMessageDialog(null, "Sie müssen eine Ganzzahl eingeben");
    		number = 0;
    	}
    	
    	return number;
    }
}
