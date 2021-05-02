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

public class Knoten implements Element {

  String name = new String("Name");
  String tmpStr = new String("?");
  int x,y,id;
  boolean isMarked=false;
  ElementController myEc;
  KnotenEinstellungen thePropertyDialog = new KnotenEinstellungen(this);

  public Knoten(int x1, int y1, String name1, int id1) {
    x = x1;
    y = y1;
    name = name1;
    id=id1;
  }

  public Knoten(int x1, int y1, int id1, ElementController ec) {
    x = x1;
    y = y1;
    id= id1;
    myEc=ec;
  }

  public Knoten(int x1, int y1, String mName, int id1, ElementController ec) {
    x = x1;
    y = y1;
    id= id1;
    myEc=ec;
    name = mName;
  }

  public void setMarked(boolean m) {
    isMarked = m;
  }

  public void setPos(int x1, int y1) {
    x=x1;
    y=y1;
  }

  public int getType() {
    return 1;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getId() {
    return id;
  }

  public String name() {
    return name;
  }

  public String getName() {
    return name;
  }

  public void paintMe(Graphics g)  {

    if (isMarked==true) {
      g.setColor(Color.red);
    }
    else {
      g.setColor(Color.blue);
    }

    g.fillOval(x-20,y-20,40,40);
    g.setColor(Color.white);
    //g.drawString("x"+tmpStr.valueOf(id),x-8,y+3);
    g.setColor(Color.black);
    g.drawString(name,x-8,y+3);
    g.setColor(Color.blue);
  }


  public boolean isInPos(int x1, int y1) {
    int dx, dy, radius;
    dx = x-x1;
    dy = y-y1;
    radius=20;

    if (dx<0) dx=dx*(-1);     // aus negativen werten positive machen
    if (dy<0) dy=dy*(-1);

    if ((dx < radius) && (dy < radius))
      return true;
    else
      return false;
  }

  public void showPropertyPage() {
    thePropertyDialog.setModal(true);
    thePropertyDialog.jTextField1.setText(name);
    thePropertyDialog.jTextField2.setText(tmpStr.valueOf(id));
    thePropertyDialog.setTitle("Einstellungen für diesen Knoten");
    thePropertyDialog.jLabel3.setText(" ");
    thePropertyDialog.setOriId(id);
    thePropertyDialog.show();

   //thePropertyDialog.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }

}