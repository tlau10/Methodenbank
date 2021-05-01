/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;


public class Hauptfenster_AboutBox extends JDialog implements ActionListener {

  private JTextPane jTextPane1 = new JTextPane();

    JPanel panel1 = new JPanel();
    JPanel insetsPanel1 = new JPanel();
    JButton button1 = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    String copyright = "Copyright (c) 2002";
    String comments = "";


    /**
     * constructor
     * @param parent
     */
    public Hauptfenster_AboutBox(Frame parent) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        pack();
    }


    /**
     * Component initialization
     */
    private void jbInit() throws Exception  {

      String infoText = new String("Programm zur linearen Portfoliooptimierung\n\n\n" +
                         " Version 1.0 - erstellt von \n" +
                         " Frank Mayer \n" +
                         " Wiebke Bang \n\n\n" +
                         " Version 2.0 - weiterentwickelt von \n" +
                         " Tobias Pfau \n" +
                         " Christoph Koch \n" +
                         " Matthias Gommeringer \n" );

      jTextPane1 = new JTextPane();
      jTextPane1.setEditable(false);
      jTextPane1.setBackground(this.getBackground());
      jTextPane1.setFont(button1.getFont());

      // align the text center
      MutableAttributeSet attr = new SimpleAttributeSet();
      StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
      jTextPane1.setParagraphAttributes(attr, false);
      jTextPane1.setText(infoText);

      button1.setText("Ok");
      button1.addActionListener(this);
      insetsPanel1.add(button1, null);
      panel1.setLayout(borderLayout1);
      panel1.add(insetsPanel1, BorderLayout.SOUTH);
      panel1.add(jTextPane1, BorderLayout.CENTER);
      panel1.setPreferredSize(new Dimension(300, 300));
      panel1.setMinimumSize(new Dimension(300, 300));

      this.setTitle("About");
      this.setResizable(true);
      this.getContentPane().add(panel1, BorderLayout.CENTER);
    }


    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            dispose();
        }
        super.processWindowEvent(e);
    }


    /**Close the dialog on a button event*/
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            dispose();
        }
    }
}