package portfolio.view.util;


import javax.swing.*;
import javax.swing.text.*;

import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

public class WholeNumberField extends JTextField {

    private Toolkit toolkit;
    private NumberFormat integerFormatter;
    private Locale mLocale;

    /**
     * creates an instance of text field that only accepts integers
     */
    public WholeNumberField(int value, int columns) {
        super(columns);
        toolkit = Toolkit.getDefaultToolkit();
        mLocale = Locale.US;
        integerFormatter = NumberFormat.getNumberInstance(mLocale);
        integerFormatter.setParseIntegerOnly(true);
        setValue(value);
    }


    public int getValue() {
        int retVal = 0;
        try {
            retVal = integerFormatter.parse(getText()).intValue();
        } catch (ParseException e) {
            // This should never happen because insertString allows
            // only properly formatted data to get in the field.
            //toolkit.beep();
        }
        return retVal;
    }

    public void setValue(int value) {
        setText(integerFormatter.format(value));
    }

    protected Document createDefaultModel() {
        return new WholeNumberDocument();
    }

    protected class WholeNumberDocument extends PlainDocument {
        public void insertString(int offs,
                                 String str,
                                 AttributeSet a)
                throws BadLocationException {
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;

            for (int i = 0; i < result.length; i++) {
                if (Character.isDigit(source[i]))
                    result[j++] = source[i];
                else {
                    // toolkit.beep();
                    // System.err.println("WholeNumberField.java->insertString: " + source[i]);
                }
            }
            super.insertString(offs, new String(result, 0, j), a);
        }
    }



    protected class DoubleNumberDocument extends PlainDocument {
        public void insertString( int offs,
                                  String str,
                                  AttributeSet a)
                              throws BadLocationException
        {
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;

            ParsePosition pos = new ParsePosition( 0 );
            Number num = NumberFormat.getInstance( mLocale ).parse( str, pos );

            int i = pos.getIndex();
            if( i < str.length() ) {
            System.out.println( "The string has been parsed up to char " + i );
            System.out.println( "Illegal character: '" + str.charAt( i ) + "'" );
            }
            else
            System.out.println( "The string has been parsed up to the end." );

            System.out.println( "In Italy the parsing of " + str + " returns " + num );

/*            for (int i = 0; i < result.length; i++) {
                if (Character.isDigit(source[i]))
                    result[j++] = source[i];
                else {
                    // toolkit.beep();
                    // System.err.println("WholeNumberField.java->DoubleNumberDocument.insertString: " + source[i]);
                }
            }
*/
//            super.insertString(offs, new String(result, 0, j), a);
        }

    }

}
