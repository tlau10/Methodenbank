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
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import portfolio.model.Portfolio;
import portfolio.view.util.DoubleNumberField;


public class ResultPortfolioPanel extends JPanel {

    private JTable mTable;
    private PortfolioTableModel mTableModel;
    private PortfolioTableModel mTableModelAlleAktien;
    private PortfolioTableModel mTableModelNurPortfolio;
    private JCheckBox checkBoxNurAktienportfolio = new JCheckBox();
    private JLabel jLabel2 = new JLabel();
    private JTextField textFieldGesamtRendite = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JTextField textFieldGesamtRenditeT = new JTextField();
    private JPanel typ4Panel = new JPanel ( new FlowLayout(FlowLayout.LEFT) );
    private JTextArea mErrorTextArea;

    /**
     * constructor
     */
    public ResultPortfolioPanel() {

      try {
        jbInit();
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }


    /**
     * initialize the GUI
     * @throws Exception
     */
    private void jbInit() throws Exception {

      // initialize the objective function text field

      JLabel labelObjectiveValue = new JLabel("Gesamt-Rendite");
      JPanel ueberTabellePanel = new JPanel( new GridLayout(2, 1) );
      JPanel objectiveValuePanel = new JPanel( new FlowLayout(FlowLayout.LEFT) );
      labelObjectiveValue.setText("Gesamtrendite");
      checkBoxNurAktienportfolio.setText("Nur Aktienportfolio anzeigen");
      checkBoxNurAktienportfolio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        checkBoxNurAktienportfolio_actionPerformed(e);
      }
      });
      jLabel2.setText("GE    ");
      textFieldGesamtRendite.setMinimumSize(new Dimension(5, 21));
      textFieldGesamtRendite.setPreferredSize(new Dimension(50, 21));
      textFieldGesamtRendite.setEditable(false);
      textFieldGesamtRendite.setText("0.0");
      textFieldGesamtRendite.setColumns(5);
      textFieldGesamtRendite.setHorizontalAlignment(SwingConstants.RIGHT);
      jLabel1.setText("Minimale Gesamtrendite aller Zeitpunkte");
      textFieldGesamtRenditeT.setEditable(false);
      textFieldGesamtRenditeT.setText("0.0");
      textFieldGesamtRenditeT.setColumns(5);
      textFieldGesamtRenditeT.setHorizontalAlignment(SwingConstants.RIGHT);
      objectiveValuePanel.add(labelObjectiveValue);
      objectiveValuePanel.add(textFieldGesamtRendite, null);
      objectiveValuePanel.add(jLabel2, null);
      objectiveValuePanel.add(checkBoxNurAktienportfolio, null);
      typ4Panel.add(jLabel1, null);
      typ4Panel.add(textFieldGesamtRenditeT, null);
      ueberTabellePanel.add(objectiveValuePanel, 0);
      ueberTabellePanel.add(typ4Panel, 1);
      typ4Panel.setVisible(false);

      // initialize the table
      mTableModel = new PortfolioTableModel();
      mTable = new JTable(mTableModel);

      mTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      mTable.setShowGrid(false);
      mTable.setShowHorizontalLines(true);
      mTable.setPreferredScrollableViewportSize(new Dimension(400,300));
      mTable.setRowSelectionAllowed(true);
      mTable.setColumnSelectionAllowed(false);

      // set the widths of the columns
      resetColumnWidth();

      // set the height of the rows
      mTable.setRowHeight(20);
      JScrollPane mScrollPaneTable = new JScrollPane(mTable);

      mErrorTextArea = new JTextArea();
      mErrorTextArea.setFont(textFieldGesamtRendite.getFont());
      mErrorTextArea.setBackground(labelObjectiveValue.getBackground());
      mErrorTextArea.setForeground(Color.red);
      mErrorTextArea.setEditable(false);
      mErrorTextArea.setVisible(false);
      JScrollPane scrollPaneError = new JScrollPane(mErrorTextArea);

      JPanel mResultsPanel = new JPanel(new BorderLayout());
      mResultsPanel.setBorder(BorderFactory.createTitledBorder(" Anteile der Aktien am Portfolio "));
      mResultsPanel.add(ueberTabellePanel, BorderLayout.NORTH);
      mResultsPanel.add(mScrollPaneTable, BorderLayout.CENTER);
      mResultsPanel.add(scrollPaneError, BorderLayout.SOUTH);



      this.setLayout(new BorderLayout(10,10));
      this.setMinimumSize(new Dimension(200, 200));
      this.setPreferredSize(new Dimension(200, 200));
      this.add(mResultsPanel,  BorderLayout.CENTER );
    }


    public void resetPortfolioPanel() {
      checkBoxNurAktienportfolio.setSelected(false);
      checkBoxNurAktienportfolio_actionPerformed(null);
      mTableModelAlleAktien = null;
      mTableModelNurPortfolio = null;
      if (mTableModel != null) {
        clearTableData();
        mTable.repaint();
      }
    }


    /**
     * <p>Title: PortfolioTableModel</p>
     * <p>Description: implement an own table model to add the ProgressBar to a
     * table column</p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: </p>
     * @author Matthias Gommeringer
     * @version 1.0
     */
    private class PortfolioTableModel extends AbstractTableModel
    {
      private final String[] mColumnNames = { "Kürzel", "Name", "Anteil", "Rendite" };

      // holds the data types for all our columns
      private final Class[] columnClasses = {String.class, String.class, Double.class, Double.class };

      private Vector data = new Vector();


      public static final int TABLE_COL_KUERZEL = 0;
      public static final int TABLE_COL_NAME = 1;
      public static final int TABLE_COL_ANTEIL = 2;
      public static final int TABLE_COL_RENDITE = 3;

      public static final int LENGTH_COL_KUERZEL = 75;
      public static final int LENGTH_COL_NAME = 165;
      public static final int LENGTH_COL_ANTEIL = 60;
      public static final int LENGTH_COL_RENDITE = 60;


      public int getColumnCount() {
        return mColumnNames.length;
      }

      public int getRowCount() {
        if( data != null )
          return data.size();
        else
          return 0;
      }

      public String getColumnName(int col) {
        if( mColumnNames[col] != null )
          return mColumnNames[col];
        else {
            System.err.println("ResultPortfolioPanel.java->getColumnName(): error! Invalid column!");
            return "---";
        }
      }

      public boolean isCellEditable(int row, int col) {
        return false;
      }

      public Object getValueAt(int row, int col) {
        if( row < getRowCount() && col < getColumnCount() )
        {
          Portfolio model = (Portfolio)data.get(row);
          if( col == TABLE_COL_KUERZEL )
            return model.getName();
          else if( col == TABLE_COL_ANTEIL )
            return model.getAnteil();
          else if ( col == TABLE_COL_RENDITE )
            return model.getRendite();
          else if ( col == TABLE_COL_NAME )
            return model.getVollerName();
          else
            return null;
        }
        else
          return null;
      }

      public Portfolio getRow(int row) {
        if ( row < getRowCount() && row >= 0)
          return (Portfolio)data.get(row);
        else
          return null;
      }

      // adds a row
      public void addRow(String name, String vollerName, Double anteil, Double rendite)
      {
        Portfolio model = new Portfolio(name, vollerName, anteil, rendite);
        data.addElement(model);
        // the table model is interested in changes of the rows
        fireTableRowsInserted(data.size()-1, data.size()-1);
      }

      public void setData(Portfolio[] newTableData) {
        data.removeAllElements();
        if( newTableData != null )
          data.addAll( java.util.Arrays.asList(newTableData)  );

        fireTableDataChanged();
      }

      public Class getColumnClass(int col) {
        if( col < getColumnCount() )
          return columnClasses[col];
        else
          return null;
      }
    }


    /**
     * adds a row to the table
     * @param name
     * @param anteil
     */
    public void addRow(Portfolio portfolio) {
      mTableModel.addRow(portfolio.getName(), portfolio.getVollerName(), portfolio.getAnteil(), portfolio.getRendite());
    }

    public void setTableData(Portfolio[] portfolioContent) {
      clearTableData();
      if( portfolioContent != null ) {
        mTableModel.setData(portfolioContent);
      }
      else
        System.out.println("ResultPortfolioPanel.java->setTableData(): parameter is null! cannot set!");
    }

    /**
     * removes all entries in the table
     */
    public void clearTableData() {
      mTableModel.setData(null);
      mTable.validate();
      mErrorTextArea.setText("");
    }


    public void setGesamtRendite(double value) {
      textFieldGesamtRendite.setText(new Double(value).toString());
    }

    public void setGesamtRenditeT(double value) {
      setGesamtRenditeTVisible(true);
      textFieldGesamtRenditeT.setText(new Double(value).toString());
    }

    public void setGesamtRenditeTVisible(boolean t) {
      typ4Panel.setVisible(t);
    }

    public double getGesamtRendite() {
      return new Double(textFieldGesamtRendite.getText()).doubleValue();
    }

    private void checkBoxNurAktienportfolio_actionPerformed(ActionEvent e) {
      if (!checkBoxNurAktienportfolio.isSelected())
      { //Alle Aktien sollen angezeigt werden
        if( mTableModelAlleAktien != null ) {
          mTable.setModel(mTableModelAlleAktien);
          mTable.repaint();
        }
      }
      else
      { //Nur Aktien im Portfolio anzeigen
        if (mTableModelAlleAktien == null)
        { //Nur beim ersten Aufruf
          mTableModelAlleAktien = mTableModel;
        }

        if (mTableModelNurPortfolio != null)
        { //TableModel existiert bereits und muss nur noch gesetzt werden
          mTable.setModel(mTableModelNurPortfolio);
          mTable.repaint();
        }
        else
        { //TableModel, das nur Aktien des Portfolios enthält, anlegen
          mTableModelNurPortfolio = new PortfolioTableModel();
          Portfolio tempPortfolio;
          int colAnteil = PortfolioTableModel.TABLE_COL_ANTEIL;
          int aktienImPortfolio = mTableModel.getRowCount();
          for (int i = 0; i < aktienImPortfolio; i++)
          {
            tempPortfolio = mTableModel.getRow(i);
            if (tempPortfolio.getAnteil().doubleValue() > 0)
            {
              mTableModelNurPortfolio.addRow(tempPortfolio.getName(), tempPortfolio.getVollerName(), tempPortfolio.getAnteil(), tempPortfolio.getRendite());
            }
          }

          mTable.setModel(mTableModelNurPortfolio);
          mTable.repaint();
        }
      }
      resetColumnWidth();
    }

    /**
     * set the background of the text field to a specific color
     * @param farbe
     */
    public void setTextFieldGesamtRenditeBackground(Color farbe)
    {
      textFieldGesamtRendite.setBackground(farbe);
    }

    /**
     * reset all column widths of the table
     */
    public void resetColumnWidth()
    {
      // set the widths of the columns
      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_KUERZEL).setPreferredWidth(PortfolioTableModel.LENGTH_COL_KUERZEL);
      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_NAME).setPreferredWidth(PortfolioTableModel.LENGTH_COL_NAME);
      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_ANTEIL).setPreferredWidth(PortfolioTableModel.LENGTH_COL_ANTEIL);
      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_RENDITE).setPreferredWidth(PortfolioTableModel.LENGTH_COL_RENDITE);

      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_KUERZEL).setMinWidth(PortfolioTableModel.LENGTH_COL_KUERZEL);
      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_NAME).setMinWidth(PortfolioTableModel.LENGTH_COL_NAME);
      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_ANTEIL).setMinWidth(PortfolioTableModel.LENGTH_COL_ANTEIL);
      mTable.getColumnModel().getColumn(PortfolioTableModel.TABLE_COL_RENDITE).setMinWidth(PortfolioTableModel.LENGTH_COL_RENDITE);
    }


    /**
     * displays an error in the text area
     * @param vErrorStrings
     */
    public void showError(Vector vErrorStrings) {
      Iterator i = vErrorStrings.iterator();
      while( i.hasNext() )
        mErrorTextArea.append( i.next().toString() );

      mErrorTextArea.setVisible(true);

    }

}


