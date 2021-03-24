/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.model;

import javax.swing.JPanel;

public class Portfolio extends JPanel {

  private String name; //Akti00001
  private Double anteil;
  private Double rendite;
  private String vollerName; //Aktie 1


  public Portfolio() {
  }

  public Portfolio(String name, String vollerName, Double anteil, Double rendite) {
    this.name = name;
    this.anteil = anteil;
    this.rendite = rendite;
    this.vollerName = vollerName;
  }


  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return this.name;
  }

  public void setVollerName(String vollerName) {
    this.vollerName = vollerName;
  }

  public String getVollerName() {
    if (this.vollerName == null)
      return new String("");
    else
      return this.vollerName;
  }

  public void setAnteil(Double anteil) {
    this.anteil = anteil;
  }
  public Double getAnteil() {
    return this.anteil;
  }
  public Double getRendite() {
    return rendite;
  }
  public void setRendite(Double rendite) {
    this.rendite = rendite;
  }
}