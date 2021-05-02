package Dakin;

import javax.swing.JComponent;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

public class DakinKoordinate extends JComponent {
  private float x1;
  private float x2;

  public DakinKoordinate() {
    x1 = 0;
    x2 = 0;
  }

  public DakinKoordinate(float _x1, float _x2) {
    x1 = _x1;
    x2 = _x2;
  }

  public DakinKoordinate(DakinKoordinate p) {
    x1 = p.x1();
    x2 = p.x2();
  }

  public float x1() {
    return x1;
  }

  public float x2() {
    return x2;
  }

  public void setKoords(float _x1, float _x2) {
    x1 = _x1;
    x2 = _x2;
  }

  public void setx1(float _x1) {
    x1 = _x1;
  }

  public void setx2(float _x2) {
    x2 = _x2;
  }
}