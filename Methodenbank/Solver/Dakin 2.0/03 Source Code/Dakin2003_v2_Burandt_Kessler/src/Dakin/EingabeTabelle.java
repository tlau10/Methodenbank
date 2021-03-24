package Dakin;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;

import com.lowagie.text.SimpleTable;

import java.util.EventObject;
import java.util.Vector;
import java.util.StringTokenizer;

// ActionListener um bei Aktualisierung der Zeilenanzahl die Tabelle
// zu aktualisieren
class TextFieldRowsAction implements ActionListener {
	TextFieldRowsAction(EingabeTabelle et) {
		eingabeTabelle = et;
	}

	public void actionPerformed(ActionEvent e) {
		int tmp = eingabeTabelle.dm.numRows;
		try {
			Integer iTmp = new Integer(
					eingabeTabelle.anzRowsTextField.getText());
			eingabeTabelle.dm.numRows = iTmp.intValue();
			if (eingabeTabelle.dm.numRows < 2)
				eingabeTabelle.dm.numRows = 2;
			eingabeTabelle.updateTable();
		} catch (Exception exception) {
			eingabeTabelle.dm.numRows = tmp;
		}
	}

	private EingabeTabelle eingabeTabelle;
}

// ActionListener um bei Aktualisierung der Spaltenanzahl die Tabelle
// zu aktualisieren
class TextFieldColsAction implements ActionListener {
	TextFieldColsAction(EingabeTabelle et) {
		eingabeTabelle = et;
	}

	public void actionPerformed(ActionEvent e) {
		int tmp = eingabeTabelle.dm.numCols;
		try {
			Integer iTmp = new Integer(
					eingabeTabelle.anzColsTextField.getText());
			eingabeTabelle.dm.numCols = iTmp.intValue();
			if (eingabeTabelle.dm.numCols < 3)
				eingabeTabelle.dm.numCols = 3;
			eingabeTabelle.updateTable();
		} catch (Exception exception) {
			eingabeTabelle.dm.numCols = tmp;
		}
	}

	private EingabeTabelle eingabeTabelle;
}

// ActionListener für den Start der Berechnung
class eingabeFertigButtonActionListener implements ActionListener {
	eingabeFertigButtonActionListener(EingabeTabelle et) {
		eingabeTabelle = et;
		this.ausgabeBaum = eingabeTabelle.ausgabeBaum;
		this.ausgabeVisualisierung = eingabeTabelle.ausgabeVisualisierung;
	}

	public void actionPerformed(ActionEvent e) {
		treeDakin = null;
		try {
			if (eingabeTabelle.solverPfad == "") {
				JOptionPane.showMessageDialog(null,
						"Bitte setzen Sie zuerst den Solverpfad", "Solverpfad",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (eingabeTabelle.rbMax.isSelected()) {

				eingabeTabelle.dm.datenMatrix.setMaxMin("max.");
			} else {

				eingabeTabelle.dm.datenMatrix.setMaxMin("min.");
			}
			
			treeDakin = new TreeDakin(eingabeTabelle.dm.datenMatrix,
					eingabeTabelle.solverPfad);
			// ////////////////////////////////////////////////////////////
			// SS13 --> Code kann entfernt werden, alte Darstellung.			
			/*
			 * JInternalFrame[] allFrames = eingabeTabelle.getDesktopPane()
			 * .getAllFrames();
			 * 
			 * int frameNum = 0;
			 * 
			 * if (allFrames[1].getTitle() == "Ausgabe") frameNum = 1; if
			 * (allFrames[2].getTitle() == "Ausgabe") frameNum = 2;
			 * 
			 * allFrames[frameNum].setIcon(false);
			 */
			// ////////////////////////////////////////////////////////////

			// die root der Ausgabe bekannt machen
			DakinAusgabe tmp = (DakinAusgabe) ausgabeBaum;
			tmp.setTree(treeDakin);

			// /////////////////////////////////////////////
			// SS13 ==> Baum-Ausgabe Tab aktiviere
			eingabeTabelle.hauptfenster.tabEnableAt(1, true);
			eingabeTabelle.hauptfenster.tabAnsichtWechseln(1);

			// Wenn die Anzahl der Restriktionen > 2 oder die Anzahl der
			// Variablen > 2
			// und die Visualisierung erwünscht ist,
			// dann keine Visualisierung möglich
			if ((treeDakin.getMatrix().getNumCols() > 4 || treeDakin
					.getMatrix().getNumRows() > 3)
					&& eingabeTabelle.chBox.isSelected() == true) {
				JOptionPane
						.showMessageDialog(
								null,
								"Die Visualisierung des Lösungsraumes ist nur \n mit 2 Variablen und 2 Restriktionen möglich!",
								"Info", JOptionPane.PLAIN_MESSAGE, null);
				// ////////////////////////////////////////////////////
				// SS13 ==> Visualiserungs-Tab deaktivieren
				eingabeTabelle.hauptfenster.tabEnableAt(2, false);
				// In diesem Fall soll nix visualisiert werden
				return;
			}
			// Wenn Visualisierung überhaupt nicht erwünscht
			if (eingabeTabelle.chBox.isSelected() == false) {
				// ////////////////////////////////////////////////////
				// SS13 ==> Visualiserungstab dissablen
				eingabeTabelle.hauptfenster.tabEnableAt(2, false);
				return;
			}
			
			// ////////////////////////////////////////////////////////////
			// SS13 --> Code kann entfernt werden, alte Darstellung.	
			/*
			 * frameNum = 0; if (allFrames[1].getTitle() == "Loesungsraum")
			 * frameNum = 1; if (allFrames[2].getTitle() == "Loesungsraum")
			 * frameNum = 2; allFrames[frameNum].setIcon(false);
			 * allFrames[frameNum].show();
			 */
			// ////////////////////////////////////////////////////////////

			// Das richtige Objekt zuweisen
			Visualisiere visObj = (Visualisiere) ausgabeVisualisierung;

			// ////////////////////////////////////////////////////////////////////
			// SS13 ==> Test auf die erste Visualisierung, wenn ja ==> nix
			// wenn nein ==> visualisierungsObjekte.clear()
			// ===> Fehlerbehebung, dass bei einer neuen Visualisierung nur
			// genau die Punkte geladen und die alten gelöscht werden.
			if (visObj.getErsteVisualisierung() == false) {
				visObj.testAufLetzteIteration();
			}

			// sucht immer ein geschwisterpaar nach der Breitensuche
			visObj.setVisualisierungsObjekte(visualierungsObjekte);
			visObj.setAlleErgebnisVektoren(alleErgebnisVektoren);
			findeBranchingNachBreitensuche(treeDakin, visObj);

			// Rufe Update für den Wurzelknoten auf
			visObj.update();
			// Erfolgreiche Visualisierung --> Visualisierungs-Tab aktivieren
			eingabeTabelle.hauptfenster.tabEnableAt(2, true);

			if (eingabeTabelle.rbMin.isSelected()) {
				JOptionPane
						.showMessageDialog(
								null,
								"Die Visualisierung des Lösungsraumes kann bei 'Minimierung' ungenau sein.\n"
										+ "Der Lösungsraum 'ins Unendliche' wird nicht korrekt dargestellt!",
								"Achtung!", JOptionPane.INFORMATION_MESSAGE,
								null);
			}

		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Fehler in der Anwendung",
					"Fehler in der Anwendung", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, exception.toString() + ", "
					+ exception.getSuppressed());
			exception.printStackTrace();
		}

		return;
	}

	public void findeBranchingNachBreitensuche(TreeDakin treeDakin,
			Visualisiere visObj) {
		Vector r1 = null;
		Vector r2 = null;
		Vector r3 = null;

		TreeDakin tempTree;
		// Queue, in der jeweils die Kindknoten zwischengespeichert werden
		Vector schlange = new Vector();
		// füge Wurzelknoten in die Queue ein
		if (treeDakin != null)
			schlange.add(treeDakin);
		int i = 1;
		while (schlange.size() != 0) {
			Vector ergebnisVektoren = new Vector();
			Vector zusaetzlicheRestriktionen = new Vector();
			tempTree = (TreeDakin) schlange.get(0);

			// falls vorhanden, füge linkes Kind an Ende der Schlange ein
			if (tempTree.getLeft() != null) {
				schlange.add(tempTree.getLeft());
			}
			// falls vorhanden, füge rechtes Kind an Ende der Schlange ein
			if (tempTree.getRight() != null) {
				schlange.add(tempTree.getRight());
			}
			// Wenn Wurzel
			if (tempTree.getRestriction() == null) {
				if (tempTree.getData().get(0) != "ERROR") {
					// aktellen Knoten den ergebnisVektoren hinzufügen
					ergebnisVektoren.add(tempTree.getData());
				}

				// Koordinaten für die Visualisierung berechnen lassen
				visualierungsObjekte.add(visObj.berechneKoordinaten(
						ergebnisVektoren, null, tempTree.getMatrix()));
				alleErgebnisVektoren.add(ergebnisVektoren);
				// lösche das erste Element aus der Schlange
				schlange.remove(0);
			}
			// Wenn es nicht um den Wurzelknoten handelt
			else {
				// Wenn beim Rechten Kind angelangt, behandle linkes u. rechtes
				// Kind
				if (i % 2 == 0) {
					// Wenn beide Knoten nicht infeasible sind, dann füge beide
					// ergebnisVektoren hinzu
					// auch die notwenndigen zusätzlichen Restriktionen werden
					// hinzugefügt

					if (tempTree.getData().get(0) != "ERROR"
							&& tempTree.getFather().getLeft().getData().get(0) != "ERROR") {
						// Geschwisterknoten hinzufügen
						ergebnisVektoren.add(tempTree.getFather().getLeft()
								.getData());
						// aktuellen Knoten hinzufügen
						ergebnisVektoren.add(tempTree.getData());

						// füge die Restriktion des aktuellen Knotens hinzu
						r1 = getRestriktionAlsVektor(tempTree.getRestriction());
						zusaetzlicheRestriktionen.add(r1);
						// füge den Geschwisterknoten hinzu
						r2 = getRestriktionAlsVektor(tempTree.getFather()
								.getLeft().getRestriction());
						zusaetzlicheRestriktionen.add(r2);
						// falls Restriktion des Vaters vorhanden, dessen
						// Restriktion hinzufügen
						if (tempTree.getFather().getRestriction() != null) {
							r3 = getRestriktionAlsVektor(tempTree.getFather()
									.getRestriction());
							zusaetzlicheRestriktionen.add(r3);
						}
					}
					// Wenn rechter Knoten nicht infeasible, aber
					// Geschwisterknoten

					else if (tempTree.getData().get(0) != "ERROR"
							&& tempTree.getFather().getLeft().getData().get(0) == "ERROR") {
						ergebnisVektoren.add(tempTree.getData());
						// füge die Restriktion des aktuellen Knotens hinzu
						r1 = getRestriktionAlsVektor(tempTree.getRestriction());
						zusaetzlicheRestriktionen.add(r1);
						if (tempTree.getFather().getRestriction() != null) {
							r3 = getRestriktionAlsVektor(tempTree.getFather()
									.getRestriction());
							zusaetzlicheRestriktionen.add(r3);
						}
					}
					// Wenn rechter Knoten infeasible, aber Geschwisterknoten
					// nicht
					else if (tempTree.getData().get(0) == "ERROR"
							&& tempTree.getFather().getLeft().getData().get(0) != "ERROR") {
						ergebnisVektoren.add(tempTree.getFather().getLeft()
								.getData());
						r2 = getRestriktionAlsVektor(tempTree.getFather()
								.getLeft().getRestriction());
						zusaetzlicheRestriktionen.add(r2);
						if (tempTree.getFather().getRestriction() != null) {
							r3 = getRestriktionAlsVektor(tempTree.getFather()
									.getRestriction());
							zusaetzlicheRestriktionen.add(r3);
						}
					}

					// Koordinaten für die Visualisierung berechnen lassen
					visualierungsObjekte.add(visObj.berechneKoordinaten(
							ergebnisVektoren, zusaetzlicheRestriktionen,
							tempTree.getMatrix()));

					alleErgebnisVektoren.add(ergebnisVektoren);
				}
				i++;
				// erstes Element aus der Schlange entferenen
				schlange.remove(0);
			}
		}
	}

	public Vector getRestriktionAlsVektor(String restriktion) {
		if (restriktion == null)
			return null;
		StringTokenizer stringTok = new StringTokenizer(restriktion);
		int j = 0;
		Vector restriktionAlsVektor = new Vector();
		while (stringTok.hasMoreTokens()) {
			if (j == 0) {
				if (stringTok.nextToken().equals(new String("x1"))) {
					restriktionAlsVektor.add(0, new String("1"));
					restriktionAlsVektor.add(1, new String("0"));
				} else {
					restriktionAlsVektor.add(0, new String("0"));
					restriktionAlsVektor.add(1, new String("1"));
				}

			} else
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

	private JPanel ausgabeBaum;
	private JPanel ausgabeVisualisierung;

	public Vector getVisualisierungsObjekte() {
		return visualierungsObjekte;
	}
}

class RowHeaderRenderer extends JLabel implements ListCellRenderer {

	RowHeaderRenderer(JTable table) {
		header = table.getTableHeader();
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(LEFT);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText((value == null) ? "" : value.toString());
		return this;
	}

	private JTableHeader header;

}

class dakinTableModel extends AbstractTableModel {
	public dakinTableModel() {
		super();
		datenMatrix = new matrix();
	}

	public int getColumnCount() {
		return numCols;
	}

	public int getRowCount() {
		return numRows;
	}

	public Object getValueAt(int row, int col) {
		return datenMatrix.getValueAt(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		if (row == 0 && col == getColumnCount() - 2)
			return false;
		if (row == 0 && col == getColumnCount() - 1)
			return false;

		return true;
	}

	public void updateTable() {
		int i;
		int anz;

		if (datenMatrix.getNumRows() < numRows) {
			anz = numRows - datenMatrix.getNumRows();

			for (i = 0; i < anz; i++)
				datenMatrix.addRow();
		} else if (datenMatrix.getNumRows() > numRows) {
			anz = datenMatrix.getNumRows() - numRows;

			for (i = anz; i > 0; i--)
				datenMatrix.removeRow();
		}

		if (datenMatrix.getNumCols() < numCols) {
			anz = numCols - datenMatrix.getNumCols();

			for (i = 0; i < anz; i++)
				datenMatrix.addColumn();
		} else if (datenMatrix.getNumCols() > numCols) {
			anz = datenMatrix.getNumCols() - numCols;

			for (i = anz; i > 0; i--)
				datenMatrix.removeColumn();
		}
		// ////////////////////////////////////////
		// SS13 --> (nur Kommentar) Erneuert das AbstractListModel!
		fireTableStructureChanged();
		return;
	}

	public String getColumnName(int col) {
		if (col == numCols - 1)
			return new String("b");
		if (col == numCols - 2)
			return new String("");
		return new String("x" + (col + 1));
	}

	public void setValueAt(Object aValue, int row, int col) {
		// checking for valid inputs
		String validSigns[] = {"<", ">", "<=", ">=" };

		int i;
		boolean flag = false;

		if (col != getColumnCount() - 2
				&& !(col == getColumnCount() - 1 && row == 0)) {
			flag = true;

			try {
				Double.valueOf((String) aValue);
			} catch (NumberFormatException nfe) {
				flag = false;
			}
		} else if (col == getColumnCount() - 2 && row != 0) {
			for (i = 0; i < validSigns.length; i++)
				if (((String) aValue).equals(validSigns[i]))
					flag = true;
		} else if (col == getColumnCount() - 1 && row == 0) {
			flag = true;
		}
		// ////////////////////////////////////////////////
		// SS13 ==> Begrenzung der Eingabemöglichkeiten
		// Abgleich auf "=" wurde entfernt, da dies nicht
		// für den Nutzer mööglich sein soll
		// ////////////////////////////////////////////////

		if (flag == false) {
			JOptionPane.showMessageDialog(null,
					"Kein gueltiger Eingabewert.\n" +
					"Erlaubte Eingaben: '<=', '<', '>=', '>'!\n" +
					"Zellenwert wird geloescht",
					"Falsche Eingabe", JOptionPane.ERROR_MESSAGE);
			aValue = new String("");
		}

		datenMatrix.setValueAt(row, col, (String) aValue);
		fireTableDataChanged();
	}

	public int numRows;
	public int numCols;

	public matrix datenMatrix;
}

// ////////////////////////////////////////////////
// SS13 ==> Klassen wurden von InternalFrames zu JPanels geändert
// für die Darstellung in den Tabs
// ////////////////////////////////////////////////
public class EingabeTabelle extends JPanel {

	public EingabeTabelle(dakin haupt, JPanel eingabe, JPanel ausgabeBaum,
			JPanel ausgabeVisualisierung) {
		super();
		this.hauptfenster = haupt;

		this.eingabe = eingabe;
		this.ausgabeBaum = ausgabeBaum;
		this.ausgabeVisualisierung = ausgabeVisualisierung;

		dm = new dakinTableModel();

		dm.numRows = 4;
		dm.numCols = 4;

		ListModel lm = new AbstractListModel() {
			public int getSize() {
				return dm.numRows;
			}

			public Object getElementAt(int index) {
				if (index == 0)
					return new String("Zielfunktion");
				return new String("Restriktion" + index);

			}
		};

		// /////////////////////////////////////////////
		// NEU SS13 --> Überschreiben des prepareEditor() der Tabelle
		// ==> beim Klick in die Tabelle auf die Zelle wird die Zahl
		// überschrieben wenn man etwas eintippt.
		// Verbesserung der Benutzerfreundlichkeit! Früher wurden
		// die Zahlen einfach angehängt und man musste die
		// vorhandenen Werte erst entfernen

		final JTable table = new JTable(dm) {
			public Component prepareEditor(TableCellEditor editor, int row,
					int column) {
				Component comp = super.prepareEditor(editor, row, column);
				if (comp instanceof JTextComponent) {
					JTextComponent textComp = (JTextComponent) comp;
					textComp.selectAll();
					return textComp;
				}
				return comp;
			}
		};

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionBackground(Color.orange);
		table.setRowHeight(25);

		// /////////////////////////////
		// NEU SS13 --> 2 Varianten den Bug für die Tabellenbearbeitung zu
		// beheben,
		// damit die Werte ohne "enter" übernommen werden.
		// 1.: Swing-Client-Property
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		// 2.: --> nicht so schön bei der Bearbeitung, Doppelklick geht nicht
		// mehr!
		// Aber "schönere" Variante. Dennoch wird die obere verwendet.
		/*
		 * table.addFocusListener(new FocusListener() {
		 * 
		 * @Override public void focusLost(FocusEvent arg0) {
		 * table.getCellEditor().stopCellEditing(); }
		 * 
		 * @Override public void focusGained(FocusEvent arg0) { Auto-generated
		 * method stub
		 * 
		 * } });
		 */
		// table.setFont(new Font("Serif", Font.TYPE1_FONT, 22));

		JList rowHeader = new JList(lm);

		rowHeader.setFixedCellWidth(100);

		rowHeader.setFixedCellHeight(table.getRowHeight());
		// ///////////////////////////////////
		// SS13 ==> Kann auskommentiert werden, damit die Tabelle einheitlicher
		// aussieht!
		// Zwischen den Zellen ist kein Abstand vorhanden!
		// + table.getRowMargin());
		rowHeader.setCellRenderer(new RowHeaderRenderer(table));

		// ////////////////////////////////////////////////////////////
		// SS13 ==> Background auf "getBackground()" setzen, damit das weiße
		// unterhalb des rowHeader verschwindet!!!
		// ////////////////////////////////////////////////////////////
		rowHeader.setBackground(getBackground());

		scroll = new JScrollPane(table);
		scroll.setRowHeaderView(rowHeader);

		JPanel oberesPanel = new JPanel();
		JPanel oberesPanelLinks = new JPanel();
		JPanel oberesPanelRechts = new JPanel();
		JPanel oberesPanelMitte = new JPanel();

		oberesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		oberesPanel.add(oberesPanelLinks);
		oberesPanel.add(oberesPanelMitte);
		oberesPanel.add(oberesPanelRechts);

		JPanel oberesPanelLinksOben = new JPanel();
		JPanel oberesPanelLinksUnten = new JPanel();

		oberesPanelLinks.setLayout(new GridLayout(1, 2));
		oberesPanelLinks.add(oberesPanelLinksOben);
		oberesPanelLinks.add(oberesPanelLinksUnten);

		anzRowsTextField = new JTextField("" + dm.numRows, 5);
		anzRowsTextField.addActionListener(new TextFieldRowsAction(this));

		oberesPanelLinksOben.add(new JLabel("Anzahl Reihen:", JLabel.RIGHT));
		oberesPanelLinksOben.add(anzRowsTextField, BorderLayout.NORTH);

		anzColsTextField = new JTextField("" + dm.numCols, 5);
		anzColsTextField.addActionListener(new TextFieldColsAction(this));

		oberesPanelLinksUnten.add(new JLabel("Anzahl Spalten:", JLabel.RIGHT));
		oberesPanelLinksUnten.add(anzColsTextField, BorderLayout.SOUTH);

		eingabeFertigButton = new JButton("Berechnen");
		eingabeFertigButton
				.addActionListener(new eingabeFertigButtonActionListener(this));

		// TM
		oberesPanelRechts.setLayout(new GridLayout(2, 1));
		chBox = new JCheckBox("Visualisierung");
		chBox.setSelected(true);
		oberesPanelRechts.add(chBox, BorderLayout.NORTH);

		oberesPanelRechts.add(eingabeFertigButton, BorderLayout.SOUTH);
		// oberesPanelRechts.add(eingabeFertigButton, BorderLayout.WEST);

		// min/max Button einfuegen in oberesPanelMitte
		ButtonGroup radioBottonGroup = new ButtonGroup();
		rbMax = new JRadioButton("Maximierung");

		rbMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dm.setValueAt(new String("max."), 0, dm.getColumnCount() - 1);
			}
		});

		rbMin = new JRadioButton("Minimierung");

		rbMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dm.setValueAt(new String("min."), 0, dm.getColumnCount() - 1);
			}
		});

		radioBottonGroup.add(rbMax);
		radioBottonGroup.add(rbMin);

		rbMax.setSelected(true);

		oberesPanelMitte.setLayout(new GridLayout(2, 1));
		oberesPanelMitte.add(rbMax);
		oberesPanelMitte.add(rbMin);

		// ////////////////////////////////////////////////
		// NEU SS13 ==> Pfad wird beim Start mit einem FileReader
		// aus einer TextDatei "neben" dem Tool ausgelesen.
		// ////////////////////////////////////////////////
		try {
			PathReader pfad = new PathReader();
			solverPfad = pfad.getPfad();
			// System.out.println(solverPfad);
			// Pfad wird zur Kontrolle für den Benutzerangezeigt.
			lblSolverPfad = new JLabel("Solverpfad: " + solverPfad
					+ "lp_solve.exe       ");
		} catch (IOException e) {
			// Fehler beim Einlesen (Datei nicht gefunden)
			lblSolverPfad = new JLabel(
					"Die Pfad-Datei konnte nicht gefunden werden!!! Bitte Pfad manuell einstellen!");
		} catch (Exception e) {
			// Sonstiger Fehler
			JOptionPane
					.showMessageDialog(
							null,
							"Fehler beim Laden des Solverpfades! - Bitte Pfad manuell einstellen!\n" +
							"Meldung: " + e + "\n" +
							"Bitte die Pfad-Datei überprüfen!",
							"Solverpfad laden - fehlgeschlagen",
							JOptionPane.ERROR_MESSAGE);
			lblSolverPfad = new JLabel("Fehler beim Laden der Pfad-Datei! - Bitte Pfad manuell einstellen!");
		}

		oberesPanel.add(lblSolverPfad);

		setLayout(new BorderLayout());
		add(oberesPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

		updateTable();
	}

	public void updateTable() {
		dm.updateTable();
		scroll.repaint();

		return;
	}

	// Methode um beim neuen Setzen des Solverpfades das Lable zu aktualisieren
	public void updateLblSolverPfad() {
		lblSolverPfad.setText("Solverpfad: " + solverPfad
				+ "lp_solve.exe       ");
	}

	// TM
	public JCheckBox chBox;
	public JRadioButton rbMax;
	public JRadioButton rbMin;
	public String solverPfad = "";
	public dakinTableModel dm;
	public JTextField anzRowsTextField;
	public JTextField anzColsTextField;
	public JLabel lblSolverPfad;
	public JButton eingabeFertigButton;

	public JScrollPane scroll;

	public dakin hauptfenster;

	public JPanel eingabe;
	public JPanel ausgabeBaum;
	public JPanel ausgabeVisualisierung;
}
