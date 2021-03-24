package graph;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ElementController extends JPanel {

  Element current;
  Vector eList = new Vector();
  Kante tempKante;
  int iAnzahlKnoten=1;

  public ElementController() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void add(Element e) {
    current = e;
    eList.add(e);
  }

  public int getNewKnotenId() {
    int mid=0;
    for (int i=0;i<eList.size();i++) {
      if (((Element)eList.get(i)).getType()==1) {
        if (((Knoten)eList.get(i)).getId() > mid) mid=((Knoten)eList.get(i)).getId();
      }
    }
    return ++mid;
  }

  public void moveUp() {
    //noch zu implementieren
  }

  public void moveDown() {
    //noch zu implementieren
  }

  // für das suchen nach einer knoten ID, die als string vorliegt in der form Xn
  public Knoten findById(String sId) {

    String tmpString = new String(" ");

    for (int i=0;i<eList.size();i++) {
      if (((Element)eList.get(i)).getType()==1) {
        //knoten
        tmpString = "x" + tmpString.valueOf(((Knoten)eList.get(i)).getId());
        if ( tmpString.equals(sId)==true ) {
          return (Knoten)eList.get(i);
        }
      }
    }
    return null;
  }

  public int getElementCount() {
    return eList.size();
  }

  public Element getElement(int m) {
    return (Element)eList.get(m);
  }

  public Kante findKante(Knoten a, Knoten b) {
    for (int i=0;i<eList.size();i++) {
      if (((Element)eList.get(i)).getType()==2) {
        //Kante
        if (((((Kante)eList.get(i)).a == a) && (((Kante)eList.get(i)).b == b)) || (((Kante)eList.get(i)).a == b) && (((Kante)eList.get(i)).b == a))
          return ((Kante)eList.get(i));
      }
    }
    return null;
  }

  public Element findByPos(int x, int y) {
    //knoten
    for (int i=0;i<eList.size();i++) {
      if ((((Element)eList.get(i)).isInPos(x,y)==true)) {
        if (((Element)eList.get(i)).getType()==1) return (Element)eList.get(i);
      }
    }
    //kanten
    for (int i=0;i<eList.size();i++) {
      if ((((Element)eList.get(i)).isInPos(x,y)==true)) {
        if (((Element)eList.get(i)).getType()==2) return (Element)eList.get(i);
      }
    }
    return null;
  }

  public Element getCurrent() {
    return current;
  }

  public boolean isIdUsed(int mid) {   //false, wenn id schon vorhanden ist
    for (int i=0;i<eList.size();i++) {
      if (((Element)eList.get(i)).getType()==1) {
        //knoten
        if (((Knoten)eList.get(i)).id == mid) return true;
      }
    }
    return false;
  }

  public void removeElement(int x1, int y1) {

    current = findByPos(x1,y1);

    if (current != null) {
      // Alle Kanten zu diesem Knoten löschen
      Element tmp;
      for (int i=0;i<eList.size();i++) {
        tmp = ((Element)eList.get(i));
        if (tmp.getType()==2) {     // ist es eine Kante?
          if ((((Kante)tmp).a==current) || (((Kante)tmp).b==current)) {
            // Knoten a oder b entsprechen dem current knoten, müssen also mit gelöscht werden
            eList.remove(tmp);
            i--;
          }
        }
      }
    }
    // Knoten löschen
    eList.remove(current);
    this.repaint();
  }

  public void paintKante(Kante k) {
    tempKante = k;
    this.repaint();
  }

  public void paintMe(Graphics g) {

    //Druckbereich
    g.setColor(Color.gray);
    g.drawRect(0,0,453,695);

    //Kanten
    for (int i=0;i<eList.size();i++) {
      if (((Element)eList.get(i)).getType()==2)  ((Element)eList.get(i)).paintMe(g);
    }

    //Knoten
    for (int i=0;i<eList.size();i++) {
      if (((Element)eList.get(i)).getType()==1)  ((Element)eList.get(i)).paintMe(g);
    }

    // Draged Kante
    if (tempKante != null) tempKante.paintMe(g);
  }

  public void removeAllElments() {
    eList.removeAllElements();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintMe(g);

  }

  private void jbInit() throws Exception {
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        this_mousePressed(e);
      }
    });
  }

  void this_mousePressed(MouseEvent e) {
  }

  public String getStatistik() {
    int iKnoten=0, iKanten=0;
    for (int i=0;i<eList.size();i++) {
      if (((Element)eList.get(i)).getType()==1)
        iKnoten++;
      else
        iKanten++;
    }
    return iKnoten + " Knoten, " + iKanten + " Kanten werden dargestellt";
  }

  // alle Elemente der Liste demarkieren
  public void resetRoute() {
    for (int i=0;i<eList.size();i++) {
      ((Element)eList.get(i)).setMarked(false);
    }
  }

  public void setRouteKnoten(Knoten m) {
    ((Element)eList.get(eList.indexOf((Element)m))).setMarked(true);
  }

  public void setRouteKante(Kante m) {
    ((Element)eList.get(eList.indexOf((Element)m))).setMarked(true);
  }
//////////////////////////////////////////////////////////////////////////

public String findByName(String knotenName)
{
  Knoten myKnoten;
  int myKnotenID = 0;
  String myKnotenName;
  String myKnotenString = "x";
  for (int i=0;i<eList.size();i++)
  {
    if (((Element)eList.get(i)).getType()==1)
    {
      myKnoten=(Knoten)eList.get(i);
      myKnotenName=myKnoten.getName();
      if (myKnotenName==knotenName)
      {
        myKnotenID=myKnoten.getId();
        myKnotenString = myKnotenString+myKnotenID;
        return myKnotenString;
      }
    }
  }
  return null;
}


//////////////////////////////////////////////////////////////////////////
}