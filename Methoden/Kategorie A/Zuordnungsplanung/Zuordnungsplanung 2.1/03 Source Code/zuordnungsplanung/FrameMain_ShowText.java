package zuordnungsplanung;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;


/**
 * <p>Title: FrameMain_ShowText</p>
 * <p>Description: Can display a Frame, used for all results</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Patrick Badent
 * @version 1.0
 */


public class FrameMain_ShowText extends JDialog implements ActionListener
{
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JButton button1 = new JButton();
  ImageIcon image1 = new ImageIcon();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  String text;
  String headline;
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextPane jTextPane1 = new JTextPane();

  public FrameMain_ShowText(Frame parent, String headline, String text)
  {
    super(parent);
    this.text = text;
    this.headline = headline;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
       ExceptionDialog.showExceptionDialog("Fehler in Frame " + headline,e.getMessage(),e);
    }
  }
  //Component initialization
  private void jbInit() throws Exception
  {
    this.setTitle(headline);
    this.setSize(400,800);
    panel1.setLayout(borderLayout1);
    panel2.setLayout(borderLayout2);
    button1.setText("Ok");
    button1.addActionListener(this);
    jTextPane1.setText(text);
    jTextPane1.setEditable(false);
    panel1.add(insetsPanel1, BorderLayout.SOUTH);
    insetsPanel1.add(button1, null);
    panel1.add(panel2, BorderLayout.NORTH);
    panel1.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTextPane1, null);
    this.getContentPane().add(panel1, BorderLayout.CENTER);
    setResizable(true);
  }
  //Overridden so we can exit when window is closed
  @Override
protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }
  //Close the dialog
  void cancel() {
    dispose();
  }
  //Close the dialog on a button event
  @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
      cancel();
    }
  }
}
