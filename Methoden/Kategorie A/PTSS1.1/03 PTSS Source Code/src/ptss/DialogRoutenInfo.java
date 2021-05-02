package ptss;

import javax.swing.*;
import java.awt.*;
//import com.borland.jbcl.layout.*;
import java.awt.event.*;


/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

public class DialogRoutenInfo extends JDialog
{
//  private Color color1;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JButton jButton1 = new JButton();
  private FlowLayout flowLayout1 = new FlowLayout();
  public  JTextPane jTextPane1 = new JTextPane();
  private JScrollPane jScrollPane1 = new JScrollPane(jTextPane1);


  public DialogRoutenInfo()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception
  {
 //   color1 = this.getForeground();
    this.setModal(true);
    this.setTitle("RoutenInfo");
    this.getContentPane().setLayout(borderLayout1);
    jPanel3.setLayout(borderLayout2);
    jPanel2.setLayout(flowLayout1);
    jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jTextPane1.setPreferredSize(new Dimension(200, 200));
    jTextPane1.setText("jTextPane1");
    this.getContentPane().add(jPanel2,  BorderLayout.SOUTH);
    jPanel2.add(jButton1, null);
    this.getContentPane().add(jPanel3, BorderLayout.CENTER);
//    jPanel3.add(jTextPane1,  BorderLayout.CENTER);
    jPanel3.add(jScrollPane1,  BorderLayout.CENTER);
  }

  public void setText(String s){
    jTextPane1.setText(s);
  }

  void jButton1_actionPerformed(ActionEvent e) {
    this.dispose();
  }
}