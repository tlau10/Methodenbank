package jobshop;

import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.text.NumberFormatter;

/*
 * Stellt sicher, dass die Eingaben in den Textfeldern nur Zahlen von 0 bis 99 sind.
 */
public class JobShopNumberFormatter extends NumberFormatter {

    JobShopNumberFormatter(NumberFormat format) {
	super(format);
    }

    @Override
    public Object stringToValue(String s) throws ParseException {
	Number number = null;
	if(s.matches("\\d*")) {
	    if(s.length() > 2) {
		number = 99;
	    } else {
		number = (Number)super.stringToValue(s);
	    }
	} else {
	    number = 0;
	}
	return number;
    }
}
