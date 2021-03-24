package graph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Kante implements Element {

  Knoten a,b,sim;
  int g1,g2;
  double c,m,y,diff;
  boolean isMarked=false;
  KanteEinstellungen thePropertyDialog = new KanteEinstellungen(this);

  public Kante(Knoten a1) {
    a=a1;
    b=a1;
    g1=0;
    g2=0;
    sim = new Knoten(a1.getX(), a1.getY(),-1,null);
    //System.out.println("neue Kante");
  }


  // konstruktor für das anlegen über die klasse cdatasource
  public Kante(Knoten a1, Knoten b1, int mg1, int mg2) {
    a=a1;
    b=b1;
    g1=mg1;
    g2=mg2;
  }

  public int getG1() {
    return g1;
  }

  public int getG2() {
    return g2;
  }

  public Knoten getA() {
    return a;
  }

  public Knoten getB() {
    return b;
  }

  public int getType() {
    return 2;
  }

  // gibt die Gewichtung zurück, je reihenfolge der knoten
  public int getGewichtung(Knoten a1, Knoten b1) {
    if (a1==a) {
      // a1 ist knoten a (a->b)
      return g1;
    }
    else {
      // a1 ist knoten b (b->a)
      return g2;
    }
  }

  public void setDestKnoten(Knoten b1) {
    b=b1;
  }

  public void setMarked(boolean m) {
    isMarked = m;
  }

  public void paintMe(Graphics g)  {

    int mdiff,ax,ay,bx,by,tx,ty;
    String mString = new String();

    if (isMarked==true) {
      g.setColor(Color.red);
    }
    else {
      g.setColor(Color.blue);
    }

    if (a==b) {
      // zeichnen beim drag'n drop
      g.drawLine(a.getX(),a.getY(),sim.getX(),sim.getY());
    }
    else {
      // kante ist verbunden zwischen zwei knoten
      g.drawLine(a.getX(),a.getY(),b.getX(),b.getY());

      if (g1>0) {
        // gewichtung bei knoten a zeichnen
        ax=a.getX();
        ay=a.getY();
        bx=b.getX();
        by=b.getY();
        by=(ay+by)/2;
        bx=(ax+bx)/2;
        while ((abs(bx-ax)>60) || (abs(by-ay)>60)) {
          by=(ay+by)/2;
          bx=(ax+bx)/2;
        }
        g.drawString(mString.valueOf(g1),bx,by );
      }
      if (g2>0) {
        ax=a.getX();
        ay=a.getY();
        bx=b.getX();
        by=b.getY();
        ay=(ay+by)/2;
        ax=(ax+bx)/2;
        while ((abs(bx-ax)>60) || (abs(by-ay)>60)) {
          ay=(ay+by)/2;
          ax=(ax+bx)/2;
        }
        g.drawString(mString.valueOf(g2),ax,ay );
      }
    }
  }

  public int abs(int temp) {
    if (temp < 0) temp = temp * (-1);
    return temp;
  }

  public boolean isInPos(int x1, int y1) {

    //System.out.println("A:" + a.getX() + " " + a.getY()+ " B:" + b.getX() + " " + b.getY() );

    // Wertebereich prüfen

    if (a.getX() < b.getX()) {
      if ((x1<a.getX()) || (x1>b.getX())) return false;
    }
    else {
      if ((x1>a.getX()) || (x1<b.getX())) return false;
    }

    if (a.getY() < b.getY()) {
      if ((y1<a.getY()) || (y1>b.getY())) return false;
    }
    else {
      if ((y1>a.getY()) || (y1<b.getY())) return false;
    }

    // innerhalb des Wertebereiches prüfen ob der Klick auf der Kante lag

    m=((double)a.getY()-(double)b.getY())/((double)a.getX()-(double)b.getX());
    c=(-1.0)*((double)m*(double)a.getX())+(double)a.getY();
   // System.out.println("m=" + m + " c=" + c );
    y=((double)m*(double)x1)+(double)c;
    diff=y-y1;

    if (diff < 0) diff=(double)diff*(double)(-1.0);

  // System.out.println("diff " + x1 + " " + y1 + " " + diff);

    if (diff <=5.0) {
     // System.out.println("kante erkannt");
      return true;
    }

    return false;
  }

  public void setSimPos(int x, int y) {
    sim.setPos(x,y);
  }

  public void showPropertyPage() {
    thePropertyDialog.setModal(true);
    thePropertyDialog.setTitle("Einstellungen für diese Kante");
    thePropertyDialog.jTextField1.setText(new String().valueOf(g1));
    thePropertyDialog.jTextField2.setText(new String().valueOf(g2));
    thePropertyDialog.jLabel5.setText(a.name + " (x" + a.id + ")");
    thePropertyDialog.jLabel6.setText(b.name + " (x" + b.id + ")");
    thePropertyDialog.jLabel3.setText("Gewichtung von x" + a.id + " nach x"+b.id);
    thePropertyDialog.jLabel4.setText("Gewichtung von x" + b.id + " nach x"+a.id);
    thePropertyDialog.jLabel7.setText(" ");
    thePropertyDialog.show();
  }
}