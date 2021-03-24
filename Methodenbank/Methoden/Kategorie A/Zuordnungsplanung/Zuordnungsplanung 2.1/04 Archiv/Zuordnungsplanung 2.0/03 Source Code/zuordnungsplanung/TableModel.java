

package zuordnungsplanung;

import javax.swing.table.AbstractTableModel;

/**
 * <p>Title: Zuordnungsplanung V 1.0</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Patrick Badent
 * @version 1.0
 */



public class TableModel extends AbstractTableModel
{

  private int rows = Variables.startWithRows;
  private int columns = Variables.startWithColumns;
  private boolean maximize;

  private int rowsMax = Variables.dataRowsMax;
  private int columnsMax = Variables.dataColumnsMax;

  private Object data[][] = new Object[rowsMax+1][columnsMax+1];

  //Constructor, use when Datei->Neu
  public TableModel(int rows, int columns, boolean maximize)
  {
    this.setRowCount(rows);
    this.setColumnCount(columns);
    this.maximize = maximize;

    //data Initiailiseren
   for (int i=0;i<columnsMax+1;i++)
   {
     for (int j = 0; j < rowsMax + 1; j++)
     {
       data[i][j] = "";
     }
  }
    //data mit Überschriften füllen
    for (int i=1; i<=columnsMax; i++)
      data[0][i] = "Stelle " + String.valueOf(i);
    for (int i=1; i<=rowsMax; i++)
      data[i][0] = "Bewerber " + String.valueOf(i);
  }


  //use when you have opend a datamodel
  public void setDataModel(DataModel dataModel)
  {
    this.setRowCount(dataModel.getRows());
    this.setColumnCount(dataModel.getColumns());
    maximize = dataModel.getMaximize();

    //Werte von dataModel in data schreiben
    for(int i=0; i<rows; i++)
      for(int j=0; j<columns; j++)
        data[i][j] = dataModel.getValueAt(i,j);

    //update GUI
    this.fireTableDataChanged();
    this.fireTableRowsUpdated(rows,columns);
  }

  //use when you wonna save save the current data
  public DataModel getDataModel()
  {
    //create new dataModel
    DataModel dataModel = new DataModel(rows,columns,maximize);

    //copy this.data to datamodel.data
    for (int i=0; i<rows; i++)
      for (int j=0; j<columns; j++)
        dataModel.setValueAt(data[i][j],i,j);
    return dataModel;
  }

  @Override
public String getColumnName(int column)
  {
      return "";
  }

  @Override
public int getRowCount()
  {
    return rows;
  }

  @Override
public int getColumnCount()
  {
    return columns;
  }

  public boolean getMaximize()
  {
    return maximize;
  }

  public void setMaximize(boolean maximize)
  {
    this.maximize=maximize;
  }

  @Override
public Object getValueAt(int row, int column)
  {
    return data[row][column];
  }


  @Override
public boolean isCellEditable(int row, int column)
  {
    if (row==0 && column==0)
      return false;
    else
      return true;
  }

  @Override
public void setValueAt(Object value, int row, int column)
  {
    if(row!=0 && column!=0 && !pruefeStringAufInt(value.toString(),true))//if nicht Headlines und nicht Integer
    {
      Variables.jOptionPane_IntergerWarning(value.toString());
      return;
    }
    this.data[row][column] = value;
    fireTableCellUpdated(row, column);
  }

  public Object[] getRowVector()
  {
    Object rowVector[] = new Object[rows];
    for (int i=0; i<rows; i++)
      rowVector[i] = data[i][0];
    return rowVector;
  }

  public Object[] getColumnVector()
  {
    Object columnVector[] = new Object[rows];
    for (int i=0; i<rows; i++)
      columnVector[i] = data[0][i];
    return columnVector;
  }

  @Override
public Class getColumnClass(int column)
  {
    return getValueAt(0,column).getClass();
  }

  public void setColumnCount(int columns)
  {
    this.columns=columns;
    this.fireTableStructureChanged();
  }

  public void setRowCount(int rows)
  {
    this.rows=rows;
    this.fireTableStructureChanged();
  }

  //private void updateTabelModelFromDataModel()
  {

  }

  //private void storeDataToData()
  {

  }

  public boolean pruefeStringAufInt(String s, boolean allowNull)
  {
    if (allowNull && s.equals(""))
      return true;
    try
    {
      Integer.parseInt(s);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

}