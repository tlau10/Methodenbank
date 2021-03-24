/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ButtonPanel extends JPanel implements ActionListener {

  public static final String ACTION_COMMAND_START = "ButtonPanelStart";
  public static final String ACTION_COMMAND_PRUEFE_QUALITAET = "ButtonPanelPruefeQualitaet";

  JButton buttonStart = new JButton();
  JButton buttonPruefeQualitaet = new JButton();

  private ActionListener[] registeredListeners;

  /**
   * constructor
   */
  public ButtonPanel() {
    try {
        jbInit();
    }
    catch(Exception e) {
        e.printStackTrace();
    }
  }


  /**
   * Component initialization
   */
  private void jbInit() throws Exception  {

    buttonStart.setText("Start");
    buttonStart.setPreferredSize(new Dimension(109, 27));
    buttonStart.setActionCommand(ACTION_COMMAND_START);
    buttonStart.addActionListener( this );
    buttonPruefeQualitaet.setText("Prüfe Qualität");
    buttonPruefeQualitaet.setActionCommand(ACTION_COMMAND_PRUEFE_QUALITAET);
    buttonPruefeQualitaet.addActionListener( this );
    JPanel buttonPanel = new JPanel( new FlowLayout());
    buttonPanel.add(buttonStart);
    buttonPanel.add(buttonPruefeQualitaet);

    this.add(buttonPanel);

  }


  /**
   * implement ActionListener
   */
  public void actionPerformed(ActionEvent e) {
    // System.out.println("ButtonPanel.java->action performed " + e.getActionCommand() );

    if(registeredListeners != null)
      for( int i=0; i<registeredListeners.length; i++)
        registeredListeners[i].actionPerformed(e);
  }


  public void addActionListener(ActionListener objToRegister)
  {
    if( registeredListeners == null )
    {
      registeredListeners = new ActionListener[1];
      registeredListeners[0] = objToRegister;
    }
    else
    {
      int oldLength = registeredListeners.length;
      ActionListener[] oldListeners = registeredListeners;
      registeredListeners = new ActionListener[ oldLength + 1 ];
      for( int i=0; i<oldListeners.length; i++ )
      {
        registeredListeners[i] = oldListeners[i];
      }
      // now register the new object
      registeredListeners[registeredListeners.length-1] = objToRegister;
    }
  }

}