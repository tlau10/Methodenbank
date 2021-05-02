package zuordnungsplanung;

/**
 * <p>Title: Zuordnungsplanung V 1.0</p>
 * <p>Description: DataModel is represent a allocation problem (engl. Zuordnungsproblem).</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Patrick Badent
 * @version 1.0
 */


public class DataModel
{

  private int rows;
  private int columns;
  private boolean maximize;
  Object data[][];


  public DataModel(int rows, int columns, boolean maximize)
  {
    this.rows = rows;
    this.columns = columns;
    this.maximize = maximize;
    data = new Object[rows+1][columns+1];
  }

  public int getRows()
  {
    return rows;
  }

  public int getColumns()
  {
    return columns;
  }

  public boolean getMaximize()
  {
    return maximize;
  }

  public void setMaximize(boolean maximize)
  {
    this.maximize = maximize;
  }

  public void setValueAt(Object data, int row, int column)
  {
    this.data[row][column] = data;
  }

  public Object getValueAt(int row, int column)
  {
      return data[row][column];
  }

  public void setRowCount(int rows)
  {
    this.rows = rows;
  }

  public void setColumnCount(int columns)
  {
    this.columns = columns;
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
    Object columnVector[] = new Object[columns];
    for (int i=0; i<columns; i++)
      columnVector[i] = data[0][i];
    return columnVector;
  }

  public void print()
  {
    System.out.println("Max " + maximize);
    System.out.println("r " + rows);
    System.out.println("c " + columns);
    for(int i=0;i<rows;i++)
      for(int j=0;j<columns;j++)
        System.out.println(data[i][j]);
  }

}