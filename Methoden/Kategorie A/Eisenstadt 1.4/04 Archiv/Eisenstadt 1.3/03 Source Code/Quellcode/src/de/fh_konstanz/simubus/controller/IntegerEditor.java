package de.fh_konstanz.simubus.controller;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import de.fh_konstanz.simubus.util.Logger;

/**
 * Implements a cell editor that uses a formatted text field to edit Integer
 * values.
 */
public class IntegerEditor extends DefaultCellEditor
{
   /**
	 * 
	 */
	private static final long serialVersionUID = -7817936490536462998L;

	JFormattedTextField ftf;

   NumberFormat        integerFormat;

   private Integer     minimum, maximum;


   public IntegerEditor( int min, int max )
   {
      super( new JFormattedTextField() );
      ftf = (JFormattedTextField) getComponent();
      minimum = new Integer( min );
      maximum = new Integer( max );

      // Set up the editor for the integer cells.
      integerFormat = NumberFormat.getIntegerInstance();
      NumberFormatter intFormatter = new NumberFormatter( integerFormat );
      intFormatter.setFormat( integerFormat );
      intFormatter.setMinimum( minimum );
      intFormatter.setMaximum( maximum );

      ftf.setFormatterFactory( new DefaultFormatterFactory( intFormatter ) );
      ftf.setValue( minimum );
      ftf.setHorizontalAlignment( SwingConstants.TRAILING );
      ftf.setFocusLostBehavior( JFormattedTextField.PERSIST );

      // React when the user presses Enter while the editor is
      // active. (Tab is handled as specified by
      // JFormattedTextField's focusLostBehavior property.)
      ftf.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), "check" );
      ftf.getActionMap().put( "check", new AbstractAction()
      {
         /**
		 * 
		 */
		private static final long serialVersionUID = -7366961680616690244L;

		public void actionPerformed( ActionEvent e )
         {
            if ( !ftf.isEditValid() )
            { // The text is invalid.
               if ( userSaysRevert() )
               { // reverted
                  ftf.postActionEvent(); // inform the editor
               }
            }
            else
               try
               { // The text is valid,
                  ftf.commitEdit(); // so use it.
                  ftf.postActionEvent(); // stop editing
               }
               catch ( java.text.ParseException exc )
               {
               }
         }
      } );
   }

   // Override to invoke setValue on the formatted text field.
   @Override
   public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row,
         int column )
   {
      JFormattedTextField ftf = (JFormattedTextField) super.getTableCellEditorComponent( table, value,
            isSelected, row, column );
      ftf.setValue( value );
      return ftf;
   }

   // Override to ensure that the value remains an Integer.
   @Override
   public Object getCellEditorValue()
   {
      JFormattedTextField ftf = (JFormattedTextField) getComponent();
      Object o = ftf.getValue();
      if ( o instanceof Integer )
      {
         return o;
      }
      else if ( o instanceof Number )
      {
         return new Integer( ( (Number) o ).intValue() );
      }
      else
      {
        
         Logger.getInstance().log("getCellEditorValue: o isn't a Number" );
         
         try
         {
            return integerFormat.parseObject( o.toString() );
         }
         catch ( ParseException exc )
         {
        	 Logger.getInstance().log( "getCellEditorValue: can't parse o: " + o );
            return null;
         }
      }
   }

   // Override to check whether the edit is valid,
   // setting the value if it is and complaining if
   // it isn't. If it's OK for the editor to go
   // away, we need to invoke the superclass's version
   // of this method so that everything gets cleaned up.
   @Override
   public boolean stopCellEditing()
   {
      JFormattedTextField ftf = (JFormattedTextField) getComponent();
      if ( ftf.isEditValid() )
      {
         try
         {
            ftf.commitEdit();
         }
         catch ( java.text.ParseException exc )
         {
         }

      }
      else
      { // text is invalid
         if ( !userSaysRevert() )
         { // user wants to edit
            return false; // don't let the editor go away
         }
      }
      return super.stopCellEditing();
   }

   /**
    * Lets the user know that the text they entered is bad. Returns true if the
    * user elects to revert to the last good value. Otherwise, returns false,
    * indicating that the user wants to continue editing.
    */
   protected boolean userSaysRevert()
   {
      Toolkit.getDefaultToolkit().beep();
      ftf.selectAll();
      Object[] options =
      { "Bearbeiten", "Zurücksetzen" };
      int answer = JOptionPane
            .showOptionDialog( SwingUtilities.getWindowAncestor( ftf ),
                  "Es können nur ganzzahlige Werte zwischen " + minimum + " und " + maximum
                        + " eingegeben werden.", "Eingabe nicht zulässig", JOptionPane.YES_NO_OPTION,
                  JOptionPane.ERROR_MESSAGE, null, options, options[ 1 ] );

      if ( answer == 1 )
      { // Revert!
         ftf.setValue( ftf.getValue() );
         return true;
      }
      return false;
   }
}