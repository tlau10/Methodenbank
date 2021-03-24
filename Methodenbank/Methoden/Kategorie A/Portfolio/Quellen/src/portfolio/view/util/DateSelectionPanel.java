/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.view.util;

import java.beans.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.border.*;
import com.toedter.calendar.*;
import com.toedter.components.*;


public class DateSelectionPanel extends JPanel {

  private JTextField dateField;
  private Date dateFromDateField;
  private JButton buttonShowCalender;
  private Calendar calendar;

  private Frame parentFrame;
  private String dialogTitle;

  /**
   * constructor
   * @param parentFrame
   */
  public DateSelectionPanel(Frame parentFrame) {

    this.parentFrame = parentFrame;
    this.dialogTitle = "Datum auswählen";
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * constructor
   * @param parentFrame
   * @param title
   */
  public DateSelectionPanel(Frame parentFrame, String title) {

    this.parentFrame = parentFrame;
    this.dialogTitle = title;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * initialize the GUI
   * @throws Exception
   */
  private void jbInit() throws Exception {

    dateField = new JTextField();
    dateField.setEditable(false);

    buttonShowCalender = new JButton("...");
    buttonShowCalender.setPreferredSize(new Dimension(20,20));
    buttonShowCalender.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CalendarDialog dlg = new CalendarDialog(parentFrame, dialogTitle);
        dlg.show();
      }
    });
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BorderLayout(10,10));
    controlPanel.add(dateField, BorderLayout.CENTER);
    controlPanel.add(buttonShowCalender, BorderLayout.EAST);

    calendar = Calendar.getInstance();
    setDateField(calendar.getTime());

    this.setLayout(new BorderLayout());
    //this.setMinimumSize(new Dimension(180,25) );
    this.setPreferredSize(new Dimension(180,25) );
    this.add(controlPanel, BorderLayout.NORTH);
  }


  public Date getDate() {
    return dateFromDateField;
  }

  public void setDate(Date newDate) {
    setDateField(newDate);

  }

  private void setDateField(Date theDate) {
    dateFromDateField = theDate;
    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
    dateField.setText(df.format(theDate));
    calendar.setTime(theDate);
  }

  /**
   * Dialog class that shows the calendar if the button was pressed
   * <p>Title: </p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2003</p>
   * <p>Company: </p>
   * @author Matthias Gommeringer
   * @version 1.0
   */
  class CalendarDialog extends JDialog {

    private JPanel calendarPanel;
    private JCalendar jcalendar;
    private JButton okButton;
    private JButton cancelButton;

    /**
     * constructor
     * @param parent
     * @param title
     */
    public CalendarDialog(Frame parent, String title) {
      super(parent, title);
      init();
    }

    /**
     * initialize the GUI
     */
    private void init() {
      jcalendar = new JCalendar(JMonthChooser.NO_SPINNER);
      jcalendar.setBorder(new EmptyBorder(10, 10, 10, 10));
      jcalendar.setCalendar(calendar);

      JPanel calendarPanel = new JPanel(new BorderLayout() );
      calendarPanel.add(jcalendar, BorderLayout.CENTER);

      okButton = new JButton("Ok");
      okButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          setDateField(jcalendar.getCalendar().getTime());
          closeDialog();
        }
      });
      cancelButton = new JButton("Abbrechen");
      cancelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          closeDialog();
        }
      });

      JPanel buttonPanel = new JPanel( new FlowLayout() );
      buttonPanel.add(okButton);
      buttonPanel.add(cancelButton);

      this.getContentPane().setLayout(new BorderLayout(10,10) );
      this.getContentPane().add(calendarPanel, BorderLayout.CENTER);
      this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

      Dimension dlgSize = this.getPreferredSize();
      Dimension frmSize = parentFrame.getSize();
      Point loc = parentFrame.getLocation();
      this.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
      this.setModal(true);
      this.pack();
    }

    private void closeDialog() {
      this.dispose();
    }

  }

}