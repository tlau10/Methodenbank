package portfolio.view.util;


import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

public class DoubleNumberField extends JTextField {

    private Toolkit toolkit;
    private NumberFormat doubleFormatter;
    private Locale mLocale;

    private int mCurrentNumberOfSeparators;

    /**
     * creates an instance of text field that only accepts integers
     */
    public DoubleNumberField(double value, int columns) {
        super(columns);

        // initialize
        mCurrentNumberOfSeparators = 0;
        toolkit = Toolkit.getDefaultToolkit();
        mLocale = Locale.US;
        doubleFormatter = NumberFormat.getNumberInstance(mLocale);
        setValue(value);
    }



    public double getValue() {
      // at first set the number of separators
      checkSeparators();
      double retVal = 0;

      try {
          retVal = doubleFormatter.parse(getText()).doubleValue();
      } catch (ParseException e) {
          // This should never happen because insertString allows
          // only properly formatted data to get in the field.
          //toolkit.beep();
      }
      return retVal;
    }

    public void setValue(double value) {
        setText(doubleFormatter.format(value));
        checkSeparators();
//        String newValue = Double.toString(value);
//        System.out.println("new Value: " + newValue);
    }


    private void checkSeparators() {
      if( getText().indexOf(".") != -1 ||
          getText().indexOf(",") != -1 )
        mCurrentNumberOfSeparators = 1;
      else
        mCurrentNumberOfSeparators = 0;
    }


    protected Document createDefaultModel() {
        return new DoubleNumberDocument();
    }



    protected class DoubleNumberDocument extends PlainDocument {

        public void insertString( int offs, String str, AttributeSet a)
                              throws BadLocationException
        {
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;

            for (int i = 0; i < result.length; i++) {
                if (Character.isDigit(source[i]))
                    result[j++] = source[i];
                else if ( String.valueOf( source[i] ).equalsIgnoreCase("," ) ||
                          String.valueOf( source[i] ).equalsIgnoreCase("." ) )
                {
                  if( mCurrentNumberOfSeparators == 0 ) {
                    result[j++] = source[i];
                    mCurrentNumberOfSeparators =1;
                  }
                }
                else {
                    // toolkit.beep();
                    // System.err.println("WholeNumberField.java->DoubleNumberDocument.insertString: " + source[i]);
                }
            }
            super.insertString(offs, new String(result, 0, j), a);
        }

        /**
         * we need to know when the separator (, or .) has been removed in order
         * to update our "mCurrentNumberOfSeparators" variable.
         * @param offs
         * @param len
         * @throws BadLocationException
         */
        public void remove(int offs, int len) throws BadLocationException {
          String removeText = this.getText(offs, len);
          if( removeText.indexOf(".") != -1 ||
              removeText.indexOf(",") != -1 )
            mCurrentNumberOfSeparators = 0;

          super.remove(offs, len);
        }
    }



// Sample code - parsing a whole number
/**
            ParsePosition pos = new ParsePosition( 0 );
            Number num = NumberFormat.getInstance( mLocale ).parse( str, pos );
            System.out.println("Locale: " + mLocale.toString());

            int k = pos.getIndex();
            if( k < str.length() ) {
            System.out.println( "The string has been parsed up to char " + k );
            System.out.println( "Illegal character: '" + str.charAt( k ) + "'" );
            }
            else
            System.out.println( "The string has been parsed up to the end." );

            System.out.println( "In Italy the parsing of " + str + " returns " + num );
*/

}