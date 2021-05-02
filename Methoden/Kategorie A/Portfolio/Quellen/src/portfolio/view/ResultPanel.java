/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import portfolio.model.Portfolio;


public class ResultPanel extends JPanel {

  JPanel mainPanel = new JPanel();
  JTabbedPane jTabbedPane1 = new JTabbedPane();

  JScrollPane scrollPanelLPModell = new JScrollPane();
  JScrollPane scrollPanelPortfolio = new JScrollPane();
  JScrollPane scrollPanelQualitaet = new JScrollPane();

  JTextArea lpmodellArea = new JTextArea();
  ResultPortfolioPanel portfolioPanel = new ResultPortfolioPanel();
  ResultQualitaetspruefungPanel qualiPanel = new ResultQualitaetspruefungPanel();


  public void activateTabbedPanePortfolio() {
    jTabbedPane1.setSelectedIndex(0);
  }

  public void activateTabbedPaneQualitaetspruefung() {
    jTabbedPane1.setSelectedIndex(2);
  }

  /**
   * constructor
   */
  public ResultPanel() {
    try {
      jbInit();
    }
    catch( Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * initialize the GUI
   * @throws Exception
   */
  private void jbInit() throws Exception {

    scrollPanelPortfolio.setBorder(null);
    scrollPanelPortfolio.setDoubleBuffered(true);
    scrollPanelLPModell.setBorder(null);
    scrollPanelLPModell.setDoubleBuffered(true);
    scrollPanelQualitaet.setBorder(null);
    scrollPanelQualitaet.setDoubleBuffered(true);

    lpmodellArea.setDoubleBuffered(true);

    jTabbedPane1.add(scrollPanelPortfolio, "Portfolio");
    scrollPanelPortfolio.getViewport().add(portfolioPanel);
    jTabbedPane1.add(scrollPanelLPModell, "LP-Modell");
    scrollPanelLPModell.getViewport().add(lpmodellArea, null);
    jTabbedPane1.add(scrollPanelQualitaet, "Qualitätsprüfung");
    scrollPanelQualitaet.getViewport().add(qualiPanel);

    mainPanel.setMinimumSize(new Dimension(400, 417));
    mainPanel.setPreferredSize(new Dimension(400, 417));
    mainPanel.setLayout(new BorderLayout() );
    mainPanel.add(jTabbedPane1,  BorderLayout.CENTER);

    this.setLayout(new FlowLayout() );
    this.setBorder(BorderFactory.createTitledBorder(" Ergebnisse "));
    this.add(mainPanel);
  }


  /**
   * resets the whole panel
   */
  public void reset() {
    portfolioPanel.clearTableData();
    lpmodellArea.setText("");
  }


  public void setLPModell(Vector lpModellLines) {
    lpmodellArea.setText("");

    if( lpModellLines != null )
    {
      Iterator i = lpModellLines.iterator();
      while( i.hasNext() )
        lpmodellArea.append( i.next().toString() + "\n" );

    }
  }


  public void setLPModellAppend(String lpModell) {
    if( lpModell == null )
      lpmodellArea.setText("");
    else
      lpmodellArea.append(lpModell);
  }


  public void setPortfolio(Portfolio[] portfolio) {
    if( portfolio == null )
      portfolioPanel.clearTableData();
    else
      portfolioPanel.setTableData(portfolio);
  }

  public void addPortfolio(Portfolio portfolio) {
    if( portfolio != null )
      portfolioPanel.addRow(portfolio);
  }


  public void setGesamtRendite(double value) {
      portfolioPanel.setGesamtRendite(value);
  }

  public double getGesamtRendite() {
      return portfolioPanel.getGesamtRendite();
  }


  public ResultQualitaetspruefungPanel getQualitaetspruefungPanel() {
    return qualiPanel;
  }

  public ResultPortfolioPanel getResultPortfolioPanel() {
    return portfolioPanel;
  }
}