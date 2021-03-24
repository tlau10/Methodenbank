package graph;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Dialog1 extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();

  Frame1 tmpFrame;
  CDataSource ls_helper1 = new CDataSource();
  JFileChooser jFileChooser1 = new JFileChooser("Daten");

  public Dialog1(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Dialog1() {
    this(null, "", false);
  }

  public Dialog1(Frame1 frameRef)
  {
    tmpFrame = frameRef;
  }

  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(jFileChooser1, BorderLayout.CENTER);
  }

/**********************************************************************
 * zeige LOAD-Dialog
 */

  String zeigeLoadDialog()
  {
    try
    {
      if (JFileChooser.APPROVE_OPTION == jFileChooser1.showOpenDialog(this))
      {
      }
      else
      {
      }

    }
    catch (java.lang.NullPointerException e)
    {
      System.out.println("keine Datei ausgewählt");
    }
    return (jFileChooser1.getSelectedFile().getPath());
  }


/*******************************************************************
 * zeige Save As - Dialog
 */

  String zeigeSavaAsDialog()
  {
    try
    {
      if (JFileChooser.APPROVE_OPTION == jFileChooser1.showSaveDialog(this))
      {
      }
    }
    catch (java.lang.NullPointerException e)
    {
      System.out.println("NullPointer im Save - As Dialog Aufruf");
    }
    return (jFileChooser1.getSelectedFile().getPath());
  }

/*******************************************************************
 * class end
 */
}