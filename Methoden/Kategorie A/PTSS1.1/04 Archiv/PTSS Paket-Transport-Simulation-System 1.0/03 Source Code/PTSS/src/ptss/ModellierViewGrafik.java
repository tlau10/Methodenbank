package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Grafik Darstellung der Modellierung</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import java.awt.font.*;
import java.awt.geom.*;

public class ModellierViewGrafik extends JPanel {

  private JPopupMenu jPopupMenu1 = new JPopupMenu();
  private int innerOval = 10;
  private int outerOval = 12;
  private Image  myImage;

  private ModellierManager mManager;
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenuItem jMenuItem3 = new JMenuItem();
  private JMenuItem jMenuItem4 = new JMenuItem();
  private JMenuItem jMenuItem5 = new JMenuItem();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JLabel aktionLabel;
  private boolean beschriftung = true;

  // Variablen für String Rotation ... bzw Kantenbeschriftung
  private Font font = new Font("Systems",Font.ROMAN_BASELINE, 10);
  private double radian = Math.PI / 180;
  private double angle;
  private AttributedString as;
  private AttributedCharacterIterator aci;
  private AffineTransform fontAT;
  private int StringX,StringY,StringXt,StringYt;
  private KnotenListe kList;
  private KantenListe kantenListe;
  private int tAX,tAY,tBX,tBY;

  public ModellierViewGrafik(ModellierManager mM, JLabel l) {
    mManager = mM;
    aktionLabel = l;
    myImage = new ImageIcon("./daten/europaKarte.gif").getImage();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setBackground(Color.white);
    this.setBorder(BorderFactory.createEtchedBorder());

    this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        this_mouseDragged(e);
      }
    });

    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
      }
      public void mouseReleased(MouseEvent e) {
        this_mouseReleased(e);
      }
    });
//    jPopupMenu1.setSize(200,200);

    jMenuItem2.setText("Knoten bewegen");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem2_actionPerformed(e);
      }
    });
    jMenuItem3.setText("Eigenschaften anzeigen");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem3_actionPerformed(e);
      }
    });
    jMenuItem4.setText("Eigenschaften editieren");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem4_actionPerformed(e);
      }
    });
    jMenuItem5.setText("Element löschen");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem5_actionPerformed(e);
      }
    });
    jMenuItem1.setText("neues Element");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem1_actionPerformed(e);
      }
    });
    jPopupMenu1.add(jMenuItem1);
    jPopupMenu1.add(jMenuItem2);
    jPopupMenu1.add(jMenuItem3);
    jPopupMenu1.add(jMenuItem4);
    jPopupMenu1.add(jMenuItem5);
    jPopupMenu1.addSeparator();


  }

  public void paint(Graphics g2) {

    Graphics2D g = (Graphics2D) g2;  // zum weichzeichnen
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    super.paintComponent(g);

    Knoten tmpKnoten;
    Kante  tmpKante;

    KnotenListe knotenListe = mManager.getKnotenListe();
    KantenListe kantenListe = mManager.getKantenListe();

    g.drawImage(myImage,0,0,this);

    // Kanten zeichnen

    for (Iterator iter = kantenListe.iterator(); iter.hasNext(); ) {
      tmpKante = (Kante) iter.next();
      g.setColor(Color.blue);
      g.setStroke(new BasicStroke(2)); // Liniendicke
      if(tmpKante.isMarked() )
        g.setColor(Color.red);
      if(tmpKante.isPfad()){
        g.setColor(Color.pink);
        if(tmpKante.isMarked() )
          g.setColor(Color.red);
          g.setStroke(new BasicStroke(5));
      }
      g.drawLine( (  knotenListe.getKnotenById(tmpKante.getKnotenA()).getX()  ),
                  (  knotenListe.getKnotenById(tmpKante.getKnotenA()).getY() ),
                  (  knotenListe.getKnotenById(tmpKante.getKnotenB()).getX() ),
                  (  knotenListe.getKnotenById(tmpKante.getKnotenB()).getY() ) );


      if (beschriftung == true){ // Kantenbeschrifung

        // Variablen initialisierung
        as = new AttributedString(tmpKante.getName());
        fontAT = new AffineTransform();

        tAX =  knotenListe.getKnotenById(tmpKante.getKnotenA()).getX();
        tAY =  knotenListe.getKnotenById(tmpKante.getKnotenA()).getY();
        tBX =  knotenListe.getKnotenById(tmpKante.getKnotenB()).getX();
        tBY =  knotenListe.getKnotenById(tmpKante.getKnotenB()).getY();

        //berechne Mittelpunkt der Kante
        if  (tAX <= tBX ) {
          StringXt =tBX -tAX;
          StringX = StringXt;
          StringX /= 2; StringX += tAX;
        }
        else{
          StringXt = tAX-tBX;
          StringX = StringXt;
          StringX /= 2; StringX += tBX;
        }

        if ( tAY <= tBY ) {
          StringYt = tBY-tAY;
          StringY = StringYt;
          StringY /= 2; StringY += tAY;
        }
        else{
          StringYt = tAY-tBY;
          StringY = StringYt;
          StringY /= 2; StringY += tBY ;
        }

        // rotiere Text
        if (tBX > tAX){ // schrift immmer von links nach rechts auf der kante
          angle = Math.atan2(tBY-tAY,tBX-tAX );
        }
        else{
          angle = (Math.atan2(tAY-tBY,tAX-tBX) );
        }
        angle = angle/radian; // atan2 zu Grad umrechnen

        fontAT.rotate(Math.toRadians(angle) );
        font = font.deriveFont(fontAT);
        as.addAttribute(TextAttribute.FONT, font, 0, tmpKante.getName().length());
        aci = as.getIterator();
        g.setColor(Color.black);

        g.drawString(aci, StringX,StringY); //rotierten string zeichnen
      }
    }

    g.setStroke(new BasicStroke(2)); // Liniendicke

    // Knoten zeichnen
    for (Iterator iter = knotenListe.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();

      if (tmpKnoten.getStart() ) {
        g.setColor(Color.yellow);
        g.fillOval(tmpKnoten.getX()-(outerOval+2)/2,tmpKnoten.getY()
                   -(outerOval+2)/2,outerOval+2,outerOval+2);
      }
      else if (tmpKnoten.getZiel() ) {
        g.setColor(Color.cyan);
        g.fillOval(tmpKnoten.getX()-(outerOval+2)/2,tmpKnoten.getY()
                   -(outerOval+2)/2,outerOval+2,outerOval+2);
      }
      else if (tmpKnoten.isMarked() ){
        g.setColor(Color.red);
        g.fillOval(tmpKnoten.getX()-(outerOval+2)/2,tmpKnoten.getY()
                   -(outerOval+2)/2,outerOval+2,outerOval+2);
      }
      else{
        g.setColor(Color.red);
        g.drawOval(tmpKnoten.getX()-outerOval/2,tmpKnoten.getY()
                   -outerOval/2,outerOval,outerOval);
        g.setColor(Color.green);
        g.fillOval(tmpKnoten.getX()-innerOval/2,tmpKnoten.getY()
                   -innerOval/2,innerOval,innerOval);
      }
      g.setColor(Color.black);
      if(beschriftung == true)
        g.drawString(tmpKnoten.getName(),tmpKnoten.getX()-15,tmpKnoten.getY()+15);
    }

    // tmpKante zeichnen
    if (mManager.tmpKanteX > -1){
      g.setColor(Color.blue);
      g.drawLine(mManager.mouseX,mManager.mouseY,mManager.tmpKanteX,mManager.tmpKanteY);
    }
  }


  void this_mouseClicked(MouseEvent e) {
    if (e.getModifiers() == e.BUTTON1_MASK) // nur linke Maustaste
      mManager.modellierViewMouseClicked(e.getX(),e.getY());
    this.repaint();
  }

  void this_mouseReleased(MouseEvent e) {
    if(e.isPopupTrigger()){ // nur rechte Maustaste
      jPopupMenu1.show(e.getComponent(),e.getX(),e.getY());
    }
    else // linke Maustaste "losgelassen"
      mManager.modellierViewMouseReleased(e.getX(), e.getY());
    this.repaint();
  }

  void this_mouseDragged(MouseEvent e) {
    if (e.getModifiers() == e.BUTTON1_MASK){
      int x = e.getX();
      int y = e.getY();

      // Bildschirmkoordinaten mit Weltkoordinaten Maus überprüfen
      if (x > this.getWidth() -outerOval/2)
        x = this.getWidth()-outerOval/2;
      if (y > this.getHeight() -outerOval/2)
        y = this.getHeight()-outerOval/2;
      if (x < 0+outerOval/2)
        x = 0+outerOval/2;
      if (y < 0+outerOval/2)
        y = 0+outerOval/2;

      mManager.modellierViewMouseDragged(x,y);
    }
    this.repaint();
  }

  void jMenuItem1_mouseClicked(MouseEvent e) {
    mManager.updateButtonState(1);
  }

  void jMenuItem2_mouseClicked(MouseEvent e) {
    mManager.updateButtonState(2);
  }

  void jMenuItem3_mouseClicked(MouseEvent e) {
    mManager.updateButtonState(3);
  }

  public void setManager (ModellierManager m) {
    mManager = m;
  }

  public ModellierManager getManager () {
     return mManager;
  }

  void jMenuItem1_actionPerformed(ActionEvent e) {
    mManager.updateButtonState(1);
    aktionLabel.setText("neues Element");
  }

  void jMenuItem2_actionPerformed(ActionEvent e) {
    mManager.updateButtonState(2);
    aktionLabel.setText("Knoten bewegen");
  }

  void jMenuItem3_actionPerformed(ActionEvent e) {
    mManager.updateButtonState(3);
    aktionLabel.setText("Eigens. anzeigen");
  }

  void jMenuItem4_actionPerformed(ActionEvent e) {
    mManager.updateButtonState(4);
    aktionLabel.setText("Eigens. editieren");
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {
    mManager.updateButtonState(5);
    aktionLabel.setText("Element löschen");
  }

  public void setBeschriftung(boolean b){
    beschriftung = b;
  }

  public void setImage(String path){
    if (path != null){
         myImage = new ImageIcon(path).getImage().getScaledInstance(this.getWidth()
             ,this.getHeight(),myImage.SCALE_FAST);
      //   myImage = new ImageIcon(path).getImage();
    }
  }
} // Ende Visualisierung