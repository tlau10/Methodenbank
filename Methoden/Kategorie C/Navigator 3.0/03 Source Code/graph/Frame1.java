package graph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import com.borland.jbcl.layout.*;

import java.math.*;
import javax.swing.border.*;

import java.io.*;
import java.lang.*;
import com.borland.datastore.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Frame1 extends JFrame {


  String tmpDatei = null;
  CDataSource ls_helper = new CDataSource(this);
  Dialog1 frstDialog = new Dialog1();
  Dialog2 scndDialog = new Dialog2();


  int actionType=0;

  Kante myKante;
  Kante tempKante;

  JPanel contentPane;
  Knoten currentKnoten;
  Element the;
  ElementController jPanel1 = new ElementController();
  Route myRoute = new Route(this);

  ButtonGroup buttonGroup1 = new ButtonGroup();
  JPanel jPanel2 = new JPanel();
  JToggleButton jToggleButton4 = new JToggleButton();
  JToggleButton jToggleButton3 = new JToggleButton();
  JToggleButton jToggleButton2 = new JToggleButton();
  JToggleButton jToggleButton1 = new JToggleButton();
  JToggleButton jToggleButton5 = new JToggleButton();
  BorderLayout borderLayout1 = new BorderLayout();

  JPanel jPanel3 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  XYLayout xYLayout1 = new XYLayout();
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JMenu jMenu2 = new JMenu();
  JMenuItem jMenuItem3 = new JMenuItem();
  JMenuItem jMenuItem4 = new JMenuItem();
  JMenuItem jMenuItem5 = new JMenuItem();
  JMenuItem jMenuItem6 = new JMenuItem();
  JMenuItem jMenuItem7 = new JMenuItem();
  JMenuItem jMenuItem9 = new JMenuItem();
  JMenuItem jMenuItem1 = new JMenuItem();

  //Construct the frame
  public Frame1() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setJMenuBar(jMenuBar1);
    this.setSize(new Dimension(674, 564));
    this.setTitle("Navigator 3");
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        this_mouseReleased(e);
      }
    });
    jPanel1.setBackground(Color.white);
    jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jPanel1_mousePressed(e);
      }
    });
    /*jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jPanel1_mousePressed(e);
      }
    });
    jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jPanel1_mousePressed(e);
      }
    });
    jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        jPanel1_mouseClicked(e);
      }
    });*/
    jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        jPanel1_mouseReleased(e);
      }
    });
    /*jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        jPanel1_mousePressed(e);
      }
    });*/
    jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        jPanel1_mouseDragged(e);
      }
    });
    jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        jPanel1_mouseClicked(e);
      }
    });
    jToggleButton4.setText("löschen");
    jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jToggleButton4_actionPerformed(e);
      }
    });
    jToggleButton3.setText("neue Kante");
    jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jToggleButton3_actionPerformed(e);
      }
    });
    jToggleButton2.setText("neuer Knoten");
    jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jToggleButton2_actionPerformed(e);
      }
    });
    jToggleButton1.setText("bewegen");
    jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jToggleButton1_actionPerformed(e);
      }
    });
    jToggleButton5.setText("Einstellungen");
    jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jToggleButton5_actionPerformed(e);
      }
    });
    jPanel3.setBackground(SystemColor.activeCaptionBorder);
    jPanel3.setMinimumSize(new Dimension(50, 50));
    jPanel3.setPreferredSize(new Dimension(50, 50));
    jPanel3.setLayout(xYLayout1);
    jLabel2.setToolTipText("");
    jPanel5.setBackground(SystemColor.inactiveCaptionBorder);
    jPanel4.setBackground(SystemColor.scrollbar);
    jButton1.setToolTipText("");
    jButton1.setText("Drucken");

    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setText("Route");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel2.setBackground(UIManager.getColor("activeCaptionBorder"));
    jMenu1.setText("Datei");
    jMenu2.setText("Hilfe");
    jMenuItem3.setText("Hilfe");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem3_actionPerformed(e);
      }
    });
    jMenuItem4.setText("?");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem4_actionPerformed(e);
      }
    });
    jMenuItem5.setText("Neu");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem5_actionPerformed(e);
      }
    });
    jMenuItem6.setText("Öffnen");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem6_actionPerformed(e);
      }
    });
    jMenuItem7.setText("Speichern");
    jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem7_actionPerformed(e);
      }
    });
    jMenuItem9.setText("Beenden");
    jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem9_actionPerformed(e);
      }
    });
    jMenuItem1.setText("Speichern unter");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem1_actionPerformed(e);
      }
    });
    jPanel2.add(jToggleButton1, null);
    jPanel2.add(jToggleButton2, null);
    jPanel2.add(jToggleButton3, null);
    jPanel2.add(jToggleButton4, null);
    jPanel2.add(jToggleButton5, null);
    jPanel2.add(jButton1, null);
    jPanel2.add(jButton2, null);
    contentPane.add(jPanel4, BorderLayout.WEST);
    contentPane.add(jPanel5, BorderLayout.EAST);
    contentPane.add(jPanel3, BorderLayout.NORTH);
    jPanel3.add(jLabel2,   new XYConstraints(12, 19, -1, -1));
    contentPane.add(jPanel1, BorderLayout.CENTER);
    contentPane.add(jPanel2, BorderLayout.SOUTH);
    buttonGroup1.add(jToggleButton5);
    buttonGroup1.add(jToggleButton1);
    buttonGroup1.add(jToggleButton2);
    buttonGroup1.add(jToggleButton3);
    buttonGroup1.add(jToggleButton4);
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    jMenu2.add(jMenuItem3);
    jMenu2.add(jMenuItem4);
    jMenu1.add(jMenuItem5);
    jMenu1.add(jMenuItem6);
    jMenu1.add(jMenuItem7);
    jMenu1.add(jMenuItem1);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem9);
    jLabel2.setText("Statistik: 0 Knoten, 0 Kanten werden dargestellt.");
  }
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void jToggleButton1_actionPerformed(ActionEvent e) {
    actionType =0;
  }

  void jToggleButton2_actionPerformed(ActionEvent e) {
    actionType=1;
  }

  void jToggleButton3_actionPerformed(ActionEvent e) {
    actionType=2;
  }

  // JPanel Event
  void jPanel1_mouseClicked(MouseEvent e) {

    if (actionType==1) {
      int theNewKnotenId = jPanel1.getNewKnotenId();
      jPanel1.add(new Knoten(e.getX(),e.getY(),"Knoten"+theNewKnotenId,theNewKnotenId,jPanel1));    // neuen knoten im elementcontroller anlegen
      jLabel2.setText("Statistik: " + jPanel1.getStatistik());                        // statistik auslesen
      this.repaint();
    }
    if (actionType==0) {
      jPanel1.findByPos(e.getX(), e.getY());
    }
    if (actionType==3) {
      jPanel1.removeElement(e.getX(), e.getY());                                      // knoten löschen
      jLabel2.setText("Statistik: " + jPanel1.getStatistik());                        // statistik auslesen
    }
  }

  void jPanel1_mouseDragged(MouseEvent e) {
    // Knoten verschieben
    if (actionType==0) {
      if (the != null) {
        if (the.getType() ==1) {
          ((Knoten)the).setPos(e.getX(),e.getY());
          this.repaint();
        }
      }
    }
    if (actionType==2) {
      if (tempKante != null) {
        tempKante.setSimPos(e.getX(),e.getY());
        jPanel1.paintKante(tempKante);
      }
    }
  }

  void jPanel1_mousePressed(MouseEvent e) {


    if (actionType==0) {
      the = jPanel1.findByPos(e.getX(), e.getY());
      if (the != null) {
        if (the.getType()==1) {
          //System.out.println("Knoten pressed");
        }
        if (the.getType()==2) {
          //System.out.println("Kante pressed");
        }
      }
    }
    if (actionType==4) {
      the = jPanel1.findByPos(e.getX(), e.getY());
      if (the != null) {
        if (the.getType()==1) {
          //System.out.println("Knoten pressed");
          the.showPropertyPage();
          this.repaint();
        }
        if (the.getType()==2) {
          //System.out.println("Kante pressed");
          the.showPropertyPage();
          this.repaint();
        }
      }
    }
    if (actionType==1) {
      // neuer knoten
    }
    if (actionType==2) {
      // neue Kante
      the = jPanel1.findByPos(e.getX(), e.getY());    // element auf das gelickt wurde ermitteln
      if (the != null) {                              // wenn überhaupt auf ein element geklickt wurde
        if (the.getType()==1) {                       // muss vom typ knoten sein
          tempKante = new Kante((Knoten)the);         // temporäre neue kante anlegen
        }
      }
    }
  }

  void this_mouseReleased(MouseEvent e) {
  }

  void jPanel1_mouseReleased(MouseEvent e) {

  //if (e.isPopupTrigger()) jPopupMenu1.show(e.getComponent(), e.getX(), e.getY());

    if ((actionType==2) && (tempKante != null)) {       // wenn status auf kante erstellen steht und eine tempKante da ist
      // es wurde begonnen eine kante zu erstellen

      Element theReleased;
      theReleased = jPanel1.findByPos(e.getX(), e.getY());
      if (theReleased != null) {

        if ((the.getType()==1) && (theReleased != the)) {
          // maus wurde auf einem knoten losgelassen
          //System.out.println("Kante in Elementliste übernehmen");
          tempKante.setDestKnoten((Knoten)theReleased);

          if ((jPanel1.findKante(tempKante.a,tempKante.b)!=null) ||
             ((jPanel1.findKante(tempKante.b,tempKante.a))!=null)) {     // prüfen ob die kante schon angelegt wurde
          }
          else {
            jPanel1.add(tempKante);                             // temp. kante in den elementcontroller aufnehmen
            jLabel2.setText("Statistik: " + jPanel1.getStatistik());                        // statistik auslesen
          }
          jPanel1.paintKante(null);
          tempKante=null;
          this.repaint();
        }
      }
      else {
        jPanel1.paintKante(null);
        tempKante=null;
      }
    }
  }

  void jToggleButton4_actionPerformed(ActionEvent e) {
    actionType=3;
  }

  void jToggleButton5_actionPerformed(ActionEvent e) {
    actionType=4;
  }

  // drucken
  // (Beispiel für das Anlegen der Knoten und Kanten in der ElementController Datenstruktur
  void jButton1_actionPerformed(ActionEvent e) {
    PrintUtilities.printComponent(jPanel1);
  }

  // DEMO FUNKTION
  private void ladenBeispiel() {
    jPanel1.removeAllElments();
    //knoten anlegen
    jPanel1.add(new Knoten(100,100,"Tobias",1,jPanel1));
    jPanel1.add(new Knoten(250,250,"Christian",2,jPanel1));
    jPanel1.add(new Knoten(100,350,"Knoten 3",3,jPanel1));
    jPanel1.add(new Knoten(500,250,"Knoten 4",4,jPanel1));
    //kante anlegen
    jPanel1.add(new Kante(jPanel1.findById("x1"),jPanel1.findById("x2"),10,20));
    jPanel1.add(new Kante(jPanel1.findById("x2"),jPanel1.findById("x3"),10,20));
    jPanel1.add(new Kante(jPanel1.findById("x3"),jPanel1.findById("x4"),10,20));
    //statistik anzeigen
    jLabel2.setText("Statistik: " + jPanel1.getStatistik());
    //alles darstellen
    this.repaint();
  }

  // DEMO FUNKTION
  public void holeElementeBeispiel() {
    for (int i=0; i<jPanel1.getElementCount();i++) {
      if ( ((Element)jPanel1.getElement(i)).getType() == 1) {
        //knoten
        System.out.println( "Knoten "+((Knoten)jPanel1.getElement(i)).getName() );
      }
      else {
        //kante
        System.out.println( "Kante x"+((Kante)jPanel1.getElement(i)).getA().getId()  + "- x" +((Kante)jPanel1.getElement(i)).getB().getId() );
      }
    }
  }



  void jButton2_actionPerformed(ActionEvent e) {

    // Combobox2 mit Elementen füllen
    myRoute.jComboBox2.removeAllItems();
    for (int i=0;i<jPanel1.eList.size();i++) {
      if (((Element)jPanel1.eList.get(i)).getType()==1) {
        //knoten
        myRoute.jComboBox2.addItem(myRoute.makeObj(((Knoten)jPanel1.eList.get(i)).getName())) ;
      }
    }
    // Combobox1 mit Elementen füllen
    myRoute.jComboBox1.removeAllItems();
    for (int i=0;i<jPanel1.eList.size();i++) {
      if (((Element)jPanel1.eList.get(i)).getType()==1) {
        //knoten
        myRoute.jComboBox1.addItem(myRoute.makeObj(((Knoten)jPanel1.eList.get(i)).getName())) ;
      }
    }

    myRoute.show();
  }

/************************************************************************
 * Öffnen - Aufruf
 */

  void jMenuItem6_actionPerformed(ActionEvent e) {

    jPanel1.removeAllElments();
    this.repaint();
    tmpDatei = null;
    try
    {
      tmpDatei = frstDialog.zeigeLoadDialog();
      ls_helper.openFile(tmpDatei);
      jLabel2.setText("Statistik: " + jPanel1.getStatistik());
    }
    catch (java.lang.NullPointerException ex)
    {
      System.out.println("laden: tmpDatei:"+tmpDatei);
    }
  }

/***********************************************************************
 * Speichern - Aufruf
 */

  void jMenuItem7_actionPerformed(ActionEvent e)
  {
  try
  {
//    holeElementeBeispiel();
    if (tmpDatei == null)
    {
      tmpDatei = frstDialog.zeigeSavaAsDialog();
    }
    ls_helper.saveFile(tmpDatei);
  }
  catch (java.lang.NullPointerException ex)
  {
    System.out.println("abbruch");
  }

  }

/***********************************************************************
 * Beenden - Aufruf
 */

  void jMenuItem9_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

/***********************************************************************
 * Neu - Aufruf
 */

  void jMenuItem5_actionPerformed(ActionEvent e) {

    tmpDatei = null;
    jPanel1.removeAllElments();
    jLabel2.setText("Statistik: " + jPanel1.getStatistik());
    this.repaint();
  }


/***********************************************************************
 * Speichern unter - Aufruf
 */

  void jMenuItem1_actionPerformed(ActionEvent e)
  {
    try
    {
      tmpDatei = frstDialog.zeigeSavaAsDialog();
      ls_helper.saveFile(tmpDatei);
    }
    catch (java.lang.NullPointerException ex)
    {
      System.out.println("Dateifehler");
    }

  }


/************************************************************************
 * ? - Dialog
 */

  void jMenuItem4_actionPerformed(ActionEvent e) {
  scndDialog.show();

  }

  void jMenuItem3_actionPerformed(ActionEvent e) {



  }

/************************************************************************
 * ende der klasse
 */
}