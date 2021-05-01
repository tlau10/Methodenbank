package Dakin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;
import java.util.StringTokenizer;

class TextFieldRowsAction implements ActionListener
{
	TextFieldRowsAction (EingabeTabelle et)
	{
		eingabeTabelle=et;
	}

	public void actionPerformed(ActionEvent e)
	{
	    int tmp=eingabeTabelle.dm.numRows;
		try
		{
			Integer iTmp=new Integer(eingabeTabelle.anzRowsTextField.getText());
			eingabeTabelle.dm.numRows=iTmp.intValue();
			if (eingabeTabelle.dm.numRows<2)
				eingabeTabelle.dm.numRows=2;
			eingabeTabelle.updateTable();
		}
		catch (Exception exception)
		{
			eingabeTabelle.dm.numRows=tmp;
		}
	}

	private EingabeTabelle eingabeTabelle;
}

class TextFieldColsAction implements ActionListener
{
	TextFieldColsAction (EingabeTabelle et)
	{
		eingabeTabelle=et;
	}

	public void actionPerformed(ActionEvent e)
	{
	     int tmp=eingabeTabelle.dm.numCols;
	     	try
	     	{
			Integer iTmp=new Integer(eingabeTabelle.anzColsTextField.getText());
			eingabeTabelle.dm.numCols=iTmp.intValue();
			if (eingabeTabelle.dm.numCols < 3)
			 	eingabeTabelle.dm.numCols=3;
			eingabeTabelle.updateTable();
		}
		catch (Exception exception)
		{
			eingabeTabelle.dm.numCols=tmp;
		}
	}

	private EingabeTabelle eingabeTabelle;
}


class eingabeFertigButtonActionListener implements ActionListener
{
	eingabeFertigButtonActionListener(EingabeTabelle et)
	{
		eingabeTabelle=et;
	}

	public void actionPerformed(ActionEvent e)
	{
		treeDakin=null;
	try {
		if (eingabeTabelle.solverPfad==null)
		{
			JOptionPane.showMessageDialog(null, "Bitte setzen Sie zuerst den Solverpfad","Solverpfad", JOptionPane.ERROR_MESSAGE);
			//eingabeTabelle.solverPfad="c://programme//or//SOLVER//LP_solve//exec";
			eingabeTabelle.solverPfad=".";
			return;
		}

                if(eingabeTabelle.rbMax.isSelected())
                  eingabeTabelle.dm.datenMatrix.setMaxMin("max.");
                else
                  eingabeTabelle.dm.datenMatrix.setMaxMin("min.");
                treeDakin=new TreeDakin(eingabeTabelle.dm.datenMatrix,eingabeTabelle.solverPfad);

		JInternalFrame[] allFrames=eingabeTabelle.getDesktopPane().getAllFrames();

		int frameNum=0;

		if (allFrames[1].getTitle()=="Ausgabe")
	  	  frameNum=1;
                if (allFrames[2].getTitle()=="Ausgabe")
	  	  frameNum=2;

		allFrames[frameNum].setIcon(false);

		//die root der Ausgabe bekannt machen
		DakinAusgabe tmp= (DakinAusgabe) allFrames[frameNum];
		tmp.setTree(treeDakin);

                allFrames[frameNum].show();

                // Wenn die Anzahl der Restriktionen > 2 oder die Anzahl der Variablen > 2
                // und die Visualisierung erwünscht ist,
                // dann keine Visualisierung möglich
                if((treeDakin.getMatrix().getNumCols() > 4 || treeDakin.getMatrix().getNumRows() > 3) && eingabeTabelle.chBox.isSelected() == true)
                {
                  JOptionPane.showMessageDialog(null,
                        "Die Visualisierung des Lösungsraumes ist nur \n mit 2 Variablen und 2 Restriktionen möglich!",
			"Info",JOptionPane.PLAIN_MESSAGE,null );
                  // In diesem Falle soll nix visualisiert werden
                  return;
                }
                // Wenn Visualisierung überhaupt nicht erwünscht
                if(eingabeTabelle.chBox.isSelected() == false)
                  return;
                frameNum = 0;

                if (allFrames[1].getTitle()=="Loesungsraum")
                  frameNum=1;
                if (allFrames[2].getTitle()=="Loesungsraum")
                  frameNum=2;
                allFrames[frameNum].setIcon(false);
		allFrames[frameNum].show();

                Visualisiere visObj = (Visualisiere) allFrames[frameNum];


                // sucht immer ein geschwisterpaar nach der Breitensuche
                findeBranchingNachBreitensuche(treeDakin, visObj);
                visObj.setVisualisierungsObjekte(visualierungsObjekte);
                visObj.setAlleErgebnisVektoren(alleErgebnisVektoren);
                // Rufe Update für den Wurzelknoten auf
                visObj.update();

                }
		catch (Exception exception)
		{
			JOptionPane.showMessageDialog(null, "Fehler in der Anwendung","Fehler in der Anwendung", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(null, exception.toString());
                        exception.printStackTrace();
		}

		return;
	}

        public void findeBranchingNachBreitensuche(TreeDakin treeDakin, Visualisiere visObj)
        {
          Vector r1 = null;
          Vector r2 = null;
          Vector r3 = null;

          TreeDakin tempTree;
          // Queue, in der jeweils die Kindknoten zwischengespeichert werden
          Vector schlange = new Vector();

          // füge Wurzelknoten in die Queue ein
          if(treeDakin != null)
            schlange.add(treeDakin);
          int i = 1;
          while(schlange.size() != 0)
          {
            Vector ergebnisVektoren = new Vector();
            Vector zusaetzlicheRestriktionen = new Vector();
            tempTree = (TreeDakin) schlange.get(0);

            // falls vorhanden, füge linkes Kind an Ende der Schlange ein
            if(tempTree.getLeft() != null)
            {
              schlange.add(tempTree.getLeft());
            }
            // falls vorhanden, füge rechtes Kind an Ende der Schlange ein
            if(tempTree.getRight() != null)
            {
              schlange.add(tempTree.getRight());
            }

            // Wenn Wurzel
            if(tempTree.getRestriction() == null)
            {
              if(tempTree.getData().get(0) != "ERROR")
              {
                // aktellen Knoten den ergebnisVektoren hinzufügen
                ergebnisVektoren.add(tempTree.getData());
              }

              // Koordinaten für die Visualisierung berechnen lassen
              visualierungsObjekte.add(visObj.berechneKoordinaten(ergebnisVektoren, null, tempTree.getMatrix()));

              alleErgebnisVektoren.add(ergebnisVektoren);

              // lösche das erste Element aus der Schlange
              schlange.remove(0);
            }
            // Wenn es nicht um den Wurzelknoten handelt
            else
            {
              // Wenn beim Rechten Kind angelangt, behandle linkes u. rechtes Kind
              if(i % 2 == 0)
              {
                // Wenn beide Knoten nicht infeasible sind, dann füge beide ergebnisVektoren hinzu
                // auch die notwenndigen zusätzlichen Restriktionen werden hinzugefügt
                if(tempTree.getData().get(0) != "ERROR" && tempTree.getFather().getLeft().getData().get(0) != "ERROR")
                {
                  // Geschwisterknoten hinzufügen
                  ergebnisVektoren.add(tempTree.getFather().getLeft().getData());
                  // aktuellen Knoten hinzufügen
                  ergebnisVektoren.add(tempTree.getData());

                  // füge die Restriktion des aktuellen Knotens hinzu
                  r1 = getRestriktionAlsVektor(tempTree.getRestriction());
                  zusaetzlicheRestriktionen.add(r1);
                  // füge den Geschwisterknoten hinzu
                  r2 = getRestriktionAlsVektor(tempTree.getFather().getLeft().getRestriction());
                  zusaetzlicheRestriktionen.add(r2);
                  // falls Restriktion des Vaters vorhanden, dessen Restriktion hinzufügen
                  if(tempTree.getFather().getRestriction() != null)
                  {
                    r3 = getRestriktionAlsVektor(tempTree.getFather().getRestriction());
                    zusaetzlicheRestriktionen.add(r3);
                  }
                }
                // Wenn rechter Knoten nicht infeasible, aber Geschwisterknoten
                else if(tempTree.getData().get(0) != "ERROR" && tempTree.getFather().getLeft().getData().get(0) == "ERROR")
                {
                  ergebnisVektoren.add(tempTree.getData());
                  // füge die Restriktion des aktuellen Knotens hinzu
                  r1 = getRestriktionAlsVektor(tempTree.getRestriction());
                  zusaetzlicheRestriktionen.add(r1);
                  if(tempTree.getFather().getRestriction() != null)
                  {
                    r3 = getRestriktionAlsVektor(tempTree.getFather().getRestriction());
                    zusaetzlicheRestriktionen.add(r3);
                  }
                }
                // Wenn rechter Knoten infeasible, aber Geschwisterknoten nicht
                else if(tempTree.getData().get(0) == "ERROR" && tempTree.getFather().getLeft().getData().get(0) != "ERROR")
                {
                  ergebnisVektoren.add(tempTree.getFather().getLeft().getData());
                  r2 = getRestriktionAlsVektor(tempTree.getFather().getLeft().getRestriction());
                  zusaetzlicheRestriktionen.add(r2);
                  if(tempTree.getFather().getRestriction() != null)
                  {
                    r3 = getRestriktionAlsVektor(tempTree.getFather().getRestriction());
                    zusaetzlicheRestriktionen.add(r3);
                  }
                }

                // Koordinaten für die Visualisierung berechnen lassen
                visualierungsObjekte.add(visObj.berechneKoordinaten(ergebnisVektoren, zusaetzlicheRestriktionen, tempTree.getMatrix()));

                alleErgebnisVektoren.add(ergebnisVektoren);
              }
              i++;
              // erstes Element aus der Schlange entferenen
              schlange.remove(0);
            }
          }
        }
        public Vector getRestriktionAlsVektor(String restriktion)
        {
          if(restriktion == null)
            return null;
          StringTokenizer stringTok = new StringTokenizer(restriktion);
          int j = 0;
          Vector restriktionAlsVektor = new Vector();
          while(stringTok.hasMoreTokens())
          {
            if(j == 0)
            {
              if(stringTok.nextToken().equals(new String("x1")))
              {
                restriktionAlsVektor.add(0, new String("1"));
                restriktionAlsVektor.add(1, new String("0"));
              }
              else
              {
                restriktionAlsVektor.add(0, new String("0"));
                restriktionAlsVektor.add(1, new String("1"));
              }

            }
            else
              restriktionAlsVektor.add(new String(stringTok.nextToken()));
            j++;
          }
          return restriktionAlsVektor;
        }


        private Vector alleErgebnisVektoren = new Vector();
        private Vector visualierungsObjekte = new Vector();
        private Vector bereitsVisualisierteKnoten = new Vector();
        private Vector unberuecksichtigteKnoten = new Vector();
        private EingabeTabelle eingabeTabelle;
	private TreeDakin treeDakin;

        public Vector getVisualisierungsObjekte()
        {
          return visualierungsObjekte;
        }
}

class RowHeaderRenderer extends JLabel implements ListCellRenderer
{

  RowHeaderRenderer(JTable table)
  {
    header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(LEFT);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  public Component getListCellRendererComponent( JList list,
         Object value, int index, boolean isSelected, boolean cellHasFocus)
  {
	setText((value == null) ? "" : value.toString());
	return this;
  }

  private JTableHeader header;

}


class dakinTableModel extends AbstractTableModel
{
	public dakinTableModel()
	{
		super();
		datenMatrix=new matrix();
	}

    	public int getColumnCount()
    	{
    		return numCols;
    	}

    	public int getRowCount()
    	{
    		return numRows;
    	}
    	public Object getValueAt(int row, int col)
    	{
    		return datenMatrix.getValueAt( row, col);
    	}

    	public boolean isCellEditable (int row, int col)
    	{
    		if (row==0 && col==getColumnCount()-2)
    			return false;
    		if (row==0 && col==getColumnCount()-1)
    			return false;

    		return true;
    	}

    	public void updateTable()
    	{
    		int i;
    		int anz;

    		if (datenMatrix.getNumRows() < numRows)
    		{
    			anz=numRows-datenMatrix.getNumRows();

    			for (i=0;i<anz; i++)
    				datenMatrix.addRow();
    		}
    		else if (datenMatrix.getNumRows() > numRows)
    		{
    			anz=datenMatrix.getNumRows()-numRows;

    			for (i=anz; i>0; i--)
    				datenMatrix.removeRow();
    		}

    		if (datenMatrix.getNumCols() < numCols)
    		{
    			anz=numCols-datenMatrix.getNumCols();

    			for (i=0;i<anz; i++)
    				datenMatrix.addColumn();
    		}
    		else if (datenMatrix.getNumCols() > numCols)
    		{
    			anz=datenMatrix.getNumCols()-numCols;

    			for (i=anz; i>0; i--)
    				datenMatrix.removeColumn();
    		}

    		fireTableStructureChanged();
    		return;
    	}

    	public String getColumnName(int col)
    	{
    		if (col==numCols-1)
    		 	return new String("b");
    		if (col==numCols-2)
    			return new String("");
    		return new String("x"+(col+1));
    	}

    	public void setValueAt (Object aValue, int row, int col)
    	{
    		// checking for valid inputs
    		String validSigns[]={"=","<",">","<=",">="};

    		int i;
    		boolean flag=false;

    		if (col!=getColumnCount()-2  && !( col==getColumnCount()-1 && row==0))
    		{
    			flag=true;

			try
			{
				Double.valueOf((String)aValue);
			}
			catch (NumberFormatException nfe)
			{
				flag=false;
			}
    		}
    		else if (col==getColumnCount()-2 && row!=0)
    		{
    			for (i=0; i<validSigns.length; i++)
    				if (((String)aValue).equals(validSigns[i]))
	    				flag=true;
    		}
    		else if (col==getColumnCount()-1 && row==0)
    		{
    			flag=true;
    		}

    		if (flag==false)
    		{
    			JOptionPane.showMessageDialog(null, "Kein gueltiger Eingabewert. Zellenwert wird geloescht","Falsche Eingabe", JOptionPane.ERROR_MESSAGE);
    			aValue=new String("");
    		}

    		datenMatrix.setValueAt(row, col,(String)aValue);
    		fireTableDataChanged();
    	}
    	public int numRows;
  	public int numCols;

  	public matrix datenMatrix;
}

public class EingabeTabelle extends JInternalFrame {

  public EingabeTabelle() {
    super("Eingabe",true,true,true,true);
    dm=new dakinTableModel();

    dm.numRows=4;
    dm.numCols=4;

//    solverPfad = ".";
    solverPfad = "L:\\Besf\\SOLVER\\LP_SOLVE\\EXEC\\";
//      solverPfad = "C:\\temp\\Dakin\\";

    ListModel lm = new AbstractListModel()
    {
      public int getSize() { return dm.numRows; }
      public Object getElementAt(int index) {
      	if (index==0)
      		return new String("Zielfunktion");
        return new String("Restriktion" + index);
     }
    };

    JTable table = new JTable( dm );
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    /*table.addFocusListener(new FocusListener()
    {
 	public void focusGained(FocusEvent e) { return; }
 	public void focusLost(FocusEvent e)  { updateTable(); return ;}
    });
    */

    JList rowHeader = new JList(lm);
    rowHeader.setFixedCellWidth(100);

    rowHeader.setFixedCellHeight(table.getRowHeight()
                               + table.getRowMargin());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));

    scroll = new JScrollPane(table);
    scroll.setRowHeaderView(rowHeader);

    JPanel oberesPanel=new JPanel();
    JPanel oberesPanelLinks=new JPanel();
    JPanel oberesPanelRechts=new JPanel();
    JPanel oberesPanelMitte=new JPanel();

    oberesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    oberesPanel.add(oberesPanelLinks);
    oberesPanel.add(oberesPanelMitte);
    oberesPanel.add(oberesPanelRechts);

    JPanel oberesPanelLinksOben=new JPanel();
    JPanel oberesPanelLinksUnten=new JPanel();

    oberesPanelLinks.setLayout(new GridLayout(1,2));
    oberesPanelLinks.add(oberesPanelLinksOben);
    oberesPanelLinks.add(oberesPanelLinksUnten);

    anzRowsTextField=new JTextField(""+dm.numRows,5);
    anzRowsTextField.addActionListener(new TextFieldRowsAction(this));

    oberesPanelLinksOben.add(new JLabel("Anzahl Reihen:", JLabel.RIGHT));
    oberesPanelLinksOben.add(anzRowsTextField, BorderLayout.NORTH);

    anzColsTextField=new JTextField(""+dm.numCols,5);
    anzColsTextField.addActionListener(new TextFieldColsAction(this));

    oberesPanelLinksUnten.add(new JLabel("Anzahl Spalten:", JLabel.RIGHT));
    oberesPanelLinksUnten.add(anzColsTextField, BorderLayout.SOUTH);

    eingabeFertigButton=new JButton("Berechnen");
    eingabeFertigButton.addActionListener(new eingabeFertigButtonActionListener(this));

    //TM
    oberesPanelRechts.setLayout(new GridLayout(2,1));
    chBox = new JCheckBox("Visualisierung");
    chBox.setSelected(true);
    oberesPanelRechts.add(chBox, BorderLayout.NORTH);

    oberesPanelRechts.add(eingabeFertigButton, BorderLayout.SOUTH);
    //oberesPanelRechts.add(eingabeFertigButton, BorderLayout.WEST);

     // min/max Button einfuegen in oberesPanelMitte
    ButtonGroup radioBottonGroup=new ButtonGroup();
    rbMax=new JRadioButton("Maximierung");


    rbMax.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e) {
    		dm.setValueAt(new String("max."), 0, dm.getColumnCount()-1);
    	}
    });

    rbMin=new JRadioButton("Minimierung");

    rbMin.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e) {
    		dm.setValueAt(new String("min."), 0, dm.getColumnCount()-1);
    	}
    });

    radioBottonGroup.add(rbMax);
    radioBottonGroup.add(rbMin);

    rbMax.setSelected(true);


    oberesPanelMitte.setLayout(new GridLayout(2,1));
    oberesPanelMitte.add(rbMax);
    oberesPanelMitte.add(rbMin);

    JComponent c = (JComponent) getContentPane();
    c.setLayout(new BorderLayout());
    c.add(oberesPanel, BorderLayout.NORTH);
    c.add(scroll, BorderLayout.CENTER);

    setContentPane(c);
    pack();


    updateTable();
  }

  public void updateTable()
  {
  	dm.updateTable();
	scroll.repaint();

  	return;
  }

  //TM
  public JCheckBox chBox;
  public JRadioButton rbMax;
  public JRadioButton rbMin;
  public String solverPfad;
  public dakinTableModel dm;
  public JTextField anzRowsTextField;
  public JTextField anzColsTextField;
  public JButton eingabeFertigButton;
  public JScrollPane scroll;
}


