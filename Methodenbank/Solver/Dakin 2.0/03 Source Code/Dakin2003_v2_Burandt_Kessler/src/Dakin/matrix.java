package Dakin;
import java.util.Vector;
import java.io.*;

public class matrix
{
	matrix()
	{
		row=0;
		col=0;

		daten=new Vector();

		maxmin=new String("max.");
	}

	matrix(matrix m)
	{
		row=0;
		col=0;
		maxmin=m.getMaxMin();

		daten=new Vector();
		for (int i=0; i<m.getNumRows();i++)
			addRow();
		for(int i=0; i<m.getNumCols(); i++)
			addColumn();
		for (int i=0; i<m.getNumRows();i++)
			for(int j=0; j<m.getNumCols(); j++)
				setValueAt(i,j,m.getValueAt(i,j));
	}

	public void addColumn()
	{
		Vector tmp=new Vector();

		int i;

		for (i=0; i<row; i++)
			tmp.add(i,new String(""));

		daten.add(col,tmp);

		col++;

		if (col>=3)
			for (i=0; i<row; i++)
		{
			{
				setValueAt(i, col-1, getValueAt(i,col-2));
				setValueAt(i, col-2, getValueAt(i,col-3));
				setValueAt(i,col-3,new String(""));
			}
		if (row>=1)
		{
			setValueAt(0,col-2, new String("-->"));
			setValueAt(0,col-1, maxmin);
		}
		}

		return;
	}

	public void removeColumn()
	{
		if (col>=4)
		for (int i=0; i<row; i++)
		{
			setValueAt(i, col-3, getValueAt(i,col-2));
			setValueAt(i, col-2, getValueAt(i,col-1));
		}

		if (col>0) {
			col--;
			daten.removeElementAt(col);
		}

		return;
	}

	public void addRow()
	{
		Vector tmp=new Vector();

		for (int i=0; i<col; i++) {
				tmp=(Vector)daten.elementAt(i);
				tmp.add(row, new String(""));
		}

		if (col>=3) {
			setValueAt(0,col-2, new String("-->"));
			setValueAt(0,col-1, maxmin);
		}

		row++;

		return;
	}

	public void removeRow()
	{
		Vector tmp=new Vector();

		if (row>0)
		{
			row--;

			for (int i=0; i<col; i++) {
				tmp=(Vector)daten.elementAt(i);
				tmp.removeElementAt(row);
			}
		}
		return;
	}

	public String getValueAt(int r, int c)
	{
		if (r<row && c<col) {
			Vector tmp=new Vector();
			tmp=(Vector)daten.elementAt(c);
			return (String)tmp.elementAt(r);
		}
		return null;
	}

	public void setValueAt(int r, int c, String aValue)
	{

		if (r<row && c<col) {
			Vector tmp=new Vector();

			tmp=(Vector)daten.elementAt(c);

			tmp.setElementAt(aValue,r);

			daten.setElementAt(tmp,c);
		}
		return;
	}

	public int getNumRows()
	{
		return row;
	}

	public int getNumCols()
	{
		return col;
	}

	public String getMaxMin()
	{
		return maxmin;
	}
        public void setMaxMin(String _maxmin)
        {
                maxmin = _maxmin;
	}

	public void printMatrix()
	{
		for (int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
				System.out.print(getValueAt(i,j));
			System.out.println(" ");
		}
		return;
	}

	public void loadMatrix()
	{
		try {
                //	File f = new File("/home/feidner/daten/absf/eigene","matrix.txt");
			String s,t;
			File f = new File("matrix.txt");
			FileReader in = new FileReader(f);
			int size = (int) f.length();
			char [] data = new char[size];
			char [] help;
			int j=0,k=0,l=0, gelesen = 0;
			s = new String();
			while (gelesen < size)
			{
				gelesen += in.read(data,gelesen, size-gelesen);
				s += new String(data);
			}

			this.addRow(); // erste Zeile hinzufuegen
			for (int i=0; i< s.length();i++)
			{
				if (s.charAt(i) == '\n')
				{
					this.addRow(); // bei jedem newline eine weitere zeile in die matrix schiessen
					l++;
					k=0;
				}
				if (s.charAt(i) == ';')
				{
					if (l ==0)
						this.addColumn(); // erste Zeile hinzufuegen
					help = new char[j];
					s.getChars(i-j,i,help,0);
					t = new String(help);
					t=t.replace('\n',' ');
					t=t.trim();
				//	this.setValueAt(l,k++,t);
					this.setValueAt(k++,l,t);
					j=0;
				}
				else
					j++;
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	private String maxmin;

	private int row;
	private int col;

	private Vector daten;
}