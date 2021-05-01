/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.business;

import java.util.Vector;

import portfolio.model.Portfolio;


public class Solution {

  private double valueOfObjectiveFunction;
  private double renditePositive;
  private double renditeNegative;
  private double renditeVerfehlt;
  private double renditeNegativZumZeitpunktT;
  private double renditeTyp4;
  private Vector allPortfolioModels;
  private Vector errorLinesFromSolver;


  /**
   * constructor
   */
  public Solution() {
    allPortfolioModels = new Vector();
  }

  public double getValueOfObjectiveFunction() {
    return valueOfObjectiveFunction;
  }
  public void setValueOfObjectiveFunction(double valueOfObjectiveFunction) {
    this.valueOfObjectiveFunction = valueOfObjectiveFunction;
  }

  public Vector getAllPortfolioModels() {
    return allPortfolioModels;
  }
  public void addPortfolioModel(Portfolio portfolioModelToAdd) {
    if( portfolioModelToAdd != null )
      this.allPortfolioModels.add( portfolioModelToAdd );
  }
  public double getRenditeNegative() {
    return renditeNegative;
  }
  public void setRenditeNegative(double renditeNegative) {
    this.renditeNegative = renditeNegative;
  }
  public double getRenditePositive() {
    return renditePositive;
  }
  public void setRenditePositive(double renditePositive) {
    this.renditePositive = renditePositive;
  }
  public double getRenditeVerfehlt() {
    return renditeVerfehlt;
  }
  public void setRenditeVerfehlt(double renditeVerfehlt) {
    this.renditeVerfehlt = renditeVerfehlt;
  }

  public void setRenditeTyp4(double renditeTyp4) {
    this.renditeTyp4 = renditeTyp4;
  }

  public double getRenditeTyp4() {
    return this.renditeTyp4;
  }

  public double getRenditeNegativZumZeitpunktT() {
    return renditeNegativZumZeitpunktT;
  }
  public void setRenditeNegativZumZeitpunktT(double renditeNegativZumZeitpunktT) {
    this.renditeNegativZumZeitpunktT = renditeNegativZumZeitpunktT;
  }

  public Vector getErrorLinesFromSolver() {
    return errorLinesFromSolver;
  }
  public void setErrorLinesFromSolver(Vector errorLinesFromSolver) {
    this.errorLinesFromSolver = errorLinesFromSolver;
  }
  public boolean hasErrorHappened() {
    if( errorLinesFromSolver != null && errorLinesFromSolver.size() > 0 )
      return true;
    else
      return false;
  }



}