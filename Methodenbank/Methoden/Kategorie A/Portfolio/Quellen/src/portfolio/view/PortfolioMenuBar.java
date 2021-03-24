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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

import portfolio.business.*;


public class PortfolioMenuBar extends JMenuBar implements ActionListener {

  public static final String ACTION_COMMAND_HELP_MENU_ABOUT = "HelpMenuAbout";
  public static final String ACTION_COMMAND_FILE_MENU_OPEN = "FileMenuOpen";
  public static final String ACTION_COMMAND_FILE_MENU_EXIT = "FileMenuExit";

  private ActionListener[] registeredListeners;

  // FILE-Menu
  private JMenu jMenuFile = new JMenu();
  private JMenuItem jMenuFileExit = new JMenuItem();
  private JMenuItem jMenuFileOpen = new JMenuItem();
  // HELP-Menu
  private JMenu jMenuHelp = new JMenu();
  private JMenuItem jMenuHelpAbout = new JMenuItem();

  private Frame mParentFrame;


  /**
   * constructor
   */
  public PortfolioMenuBar(Frame mainFrame) {

    mParentFrame = mainFrame;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * initialize the GUI
   */
  private void jbInit() throws Exception {

    // FILE-Menu
    jMenuFile.setText("Datei");
    jMenuFileExit.setText("Beenden");
    jMenuFileExit.setActionCommand(ACTION_COMMAND_FILE_MENU_EXIT);
    jMenuFileExit.addActionListener(this);

    jMenuFileOpen.setText("Öffnen");
    jMenuFileOpen.setActionCommand(ACTION_COMMAND_FILE_MENU_OPEN);
    jMenuFileOpen.addActionListener(this);

    // HELP-Menu
    jMenuHelp.setText("Hilfe");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.setActionCommand(ACTION_COMMAND_HELP_MENU_ABOUT);
    jMenuHelpAbout.addActionListener(this);

    jMenuFile.add(jMenuFileOpen);
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    this.add(jMenuFile);
    this.add(jMenuHelp);

  }// jbInit




  /**
   * Help | About action performed
   */
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
      Hauptfenster_AboutBox dlg = new Hauptfenster_AboutBox(mParentFrame);
      Dimension dlgSize = dlg.getPreferredSize();
      Dimension frmSize = mParentFrame.getSize();
      Point loc = mParentFrame.getLocation();
      dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
      dlg.setModal(true);
      dlg.show();
  }


  /**
   * File | Exit action performed
   */
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
      System.exit(0);
  }


  /**
   *
   * @param e
   */
  public void actionPerformed(ActionEvent e) {

    if( e.getActionCommand().equalsIgnoreCase(ACTION_COMMAND_FILE_MENU_EXIT) ) {
      jMenuFileExit_actionPerformed(e);
    }
    else if( e.getActionCommand().equalsIgnoreCase(ACTION_COMMAND_FILE_MENU_OPEN) ) {
      // do nothing -> is handled by Hauptfenster
    }
    else if( e.getActionCommand().equalsIgnoreCase(ACTION_COMMAND_HELP_MENU_ABOUT) ) {
      jMenuHelpAbout_actionPerformed(e);
    }
    else
      System.err.println("PortfolioMenuBar.java->actionPerformed(): unknown action command " + e.getActionCommand());

    // now notify all registered listeners
    if( registeredListeners != null )
      for( int i=0 ; i<registeredListeners.length; i++ )
        registeredListeners[i].actionPerformed(e);

  }


  /**
   * to register an action listener from outside
   * @param objToRegister
   */
  public void register(ActionListener objToRegister)
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