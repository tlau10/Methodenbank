package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Daten Klasse Knoten</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Knoten{

  private String name;
  private int x, y, id;
  private boolean isMarked = false;
  private boolean start, ziel = false;
  private int simuId = -1;
  private int eventAnzahl = 1000;
  private int eventProEinheit = 5;
  public  int scheduledEvents = 0;

  public Knoten(int x1, int y1, String name1, int id1) {
    x = x1;
    y = y1;
    name = name1;
    id=id1;
  }

  public boolean isMarked() {
    return isMarked;
  }

  public boolean isStart() {
    return start;
  }

  public boolean isZiel() {
    return ziel;
  }

  public void setMarked(boolean m) {
    isMarked = m;
  }

  public void setPos(int x1, int y1) {
    x=x1;
    y=y1;
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

  public void setId(int i){
    id =i;
  }

  public String getName() {
    return name;
  }

  public void setName(String s){
    name = s;
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

  public boolean getStart (){
    return start;
  }
  public boolean getZiel (){
    return ziel;
  }
  public void setStart (){
    start = true;
    ziel = false;
  }
  public void setZiel (){
      ziel = true;
      start = false;
  }
  public void unSetStart(){
    start = false;
  }
  public void unSetZiel(){
  ziel = false;
  }
 public void setSimuId(int i){
   simuId = i;
 }
 public int getSimuId(){
   return simuId;
 }

 public int getEventAnzahl(){
   return eventAnzahl;
 }
 public int getEventProEinheit(){
   return eventProEinheit;
 }
 public void setEventAnzahl(int i){
   eventAnzahl = i;
 }
 public void setEventProEinheit(int i){
   eventProEinheit = i;
 }
}
