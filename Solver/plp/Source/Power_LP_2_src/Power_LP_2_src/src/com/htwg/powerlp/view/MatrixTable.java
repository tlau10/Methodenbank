package com.htwg.powerlp.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 * The RXTable provides some extensions to the default JTable
 * 
 * 1) Select All editing - when a text related cell is placed in editing mode
 * the text is selected. Controlled by invoking a "setSelectAll..." method.
 * 
 * 2) reorderColumns - static convenience method for reodering table columns
 */
public class MatrixTable extends JTable {

	public enum TableType {
		RESTRICTION, BOUND;
	}

	public class NullSelectionModel implements ListSelectionModel {
		public boolean isSelectionEmpty() {
			return true;
		}

		public boolean isSelectedIndex(int index) {
			return false;
		}

		public int getMinSelectionIndex() {
			return -1;
		}

		public int getMaxSelectionIndex() {
			return -1;
		}

		public int getLeadSelectionIndex() {
			return -1;
		}

		public int getAnchorSelectionIndex() {
			return -1;
		}

		public void setSelectionInterval(int index0, int index1) {
		}

		public void setLeadSelectionIndex(int index) {
		}

		public void setAnchorSelectionIndex(int index) {
		}

		public void addSelectionInterval(int index0, int index1) {
		}

		public void insertIndexInterval(int index, int length, boolean before) {
		}

		public void clearSelection() {
		}

		public void removeSelectionInterval(int index0, int index1) {
		}

		public void removeIndexInterval(int index0, int index1) {
		}

		public void setSelectionMode(int selectionMode) {
		}

		public int getSelectionMode() {
			return SINGLE_SELECTION;
		}

		public void addListSelectionListener(ListSelectionListener lsl) {
		}

		public void removeListSelectionListener(ListSelectionListener lsl) {
		}

		public void setValueIsAdjusting(boolean valueIsAdjusting) {
		}

		public boolean getValueIsAdjusting() {
			return false;
		}
	}
	
	/** Key listener that controls row highlighting */
	class SearchingKeyAdapter extends KeyAdapter {
	    private final JTable table;
	    private int selectedRow = -1;//before start

	    public SearchingKeyAdapter(JTable table) {
	        this.table = table;
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	        String keyChar = String.valueOf(e.getKeyChar());
	        System.out.println(keyChar);
	        TableModel model = table.getModel();
	        int startRow = selectedRow;
	        if (selectedRow == model.getRowCount() - 1) {
	            startRow = -1;//Go before start
	        }
	        //Check each cell to see if it starts with typed char.
	        //if so set corresponding row selected and return.
	        for (int row = startRow+1; row < model.getRowCount(); row++) {
	            for (int col = 0; col < model.getColumnCount(); col++) {
	                String value = (String) model.getValueAt(row, col);
	                if (value != null && value.startsWith(keyChar)) {
	                    table.getSelectionModel().clearSelection();
	                    table.getColumnModel().getSelectionModel().clearSelection();
	                    table.setRowSelectionInterval(row, row);
	                    selectedRow = row;
	                    return;
	                }
	            }
	        }

	    }
	}

	private class Location {
		private int x;
		private int y;

		/**
		 * 
		 */
		public Location(int x, int y) {
			this.setX(x);
			this.setY(y);
		}

		public boolean matches(Location l) {
			if (this.getX() == l.getX() && this.getY() == l.getY()) {
				return true;
			}
			return false;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9184919545561558789L;
	private boolean isSelectAllForMouseEvent = true;
	private boolean isSelectAllForActionEvent = true;
	private boolean isSelectAllForKeyEvent = true;

	private Location lastLocation;

	private TableType type;

	private boolean locationChanged;

	private String currentSequence;
	private MatrixTable matrix;

	//
	// Constructors
	//
	/**
	 * Constructs a default <code>RXTable</code> that is initialized with a
	 * default data model, a default column model, and a default selection
	 * model.
	 */
	public MatrixTable() {
		this(null, null, null);
	}

	/**
	 * Constructs a <code>RXTable</code> that is initialized with
	 * <code>dm</code> as the data model, a default column model, and a default
	 * selection model.
	 * 
	 * @param dm
	 *            the data model for the table
	 */
	public MatrixTable(TableModel dm) {
		this(dm, null, null);
	}

	/**
	 * Constructs a <code>RXTable</code> that is initialized with
	 * <code>dm</code> as the data model, <code>cm</code> as the column model,
	 * and a default selection model.
	 * 
	 * @param dm
	 *            the data model for the table
	 * @param cm
	 *            the column model for the table
	 */
	public MatrixTable(TableModel dm, TableColumnModel cm) {
		this(dm, cm, null);
	}

	/**
	 * Constructs a <code>RXTable</code> that is initialized with
	 * <code>dm</code> as the data model, <code>cm</code> as the column model,
	 * and <code>sm</code> as the selection model. If any of the parameters are
	 * <code>null</code> this method will initialize the table with the
	 * corresponding default model. The <code>autoCreateColumnsFromModel</code>
	 * flag is set to false if <code>cm</code> is non-null, otherwise it is set
	 * to true and the column model is populated with suitable
	 * <code>TableColumns</code> for the columns in <code>dm</code>.
	 * 
	 * @param dm
	 *            the data model for the table
	 * @param cm
	 *            the column model for the table
	 * @param sm
	 *            the row selection model for the table
	 */
	public MatrixTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	public void repaintComps() {
		super.repaint();
	}

	public void locationChanged(Location lastLocation, Location currentLocation) {
		if (!locationChanged) {
			if (!lastLocation.matches(currentLocation)) {
				locationChanged = true;
			}
		}
	}

	/**
	 * Constructs a <code>RXTable</code> with <code>numRows</code> and
	 * <code>numColumns</code> of empty cells using
	 * <code>DefaultTableModel</code>. The columns will have names of the form
	 * "A", "B", "C", etc.
	 * 
	 * @param numRows
	 *            the number of rows the table holds
	 * @param numColumns
	 *            the number of columns the table holds
	 */
	public MatrixTable(int numRows, int numColumns, TableType type) {
		this(new DefaultTableModel(numRows, numColumns));
		this.type = type;
		matrix = this;
		this.setSelectAllForEdit(true);
		lastLocation = new Location(-1, -1);
		currentSequence = "";
		this.setRowSelectionAllowed(false);
		this.setColumnSelectionAllowed(false);
		this.setCellSelectionEnabled(false);
		ListSelectionModel cellSelectionModel = matrix.getSelectionModel();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						locationChanged = true;
					}
				});
		this.getColumnModel().getSelectionModel()
				.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						locationChanged = true;
					}
				});

		matrix.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
			
			@Override
			public void editingStopped(ChangeEvent e) {
				System.out.println("Done");
			}
			
			@Override
			public void editingCanceled(ChangeEvent e) {
				System.out.println("Done");
			}
		});
		matrix.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				Location l = new Location(matrix.getEditingRow(), matrix
						.getEditingColumn());
				if (l.getY() == -1) {
					return;
				}
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					System.out.println("Set to Zero");
					c = '0';
				}
				if (lastLocation.matches(l) && !locationChanged) {
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE
							|| e.getKeyChar() == '') {
						char[] array = currentSequence.toCharArray();
						String seq = "";
						for (int i = 0; i < array.length - 1; i++) {
							seq += array[i];
						}
						currentSequence = seq;
					} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						currentSequence = "";
					} else {
						currentSequence += c;
					}
					if (isCellEditable(matrix.getEditingRow(),
							matrix.getEditingColumn())) {
						matrix.setValueAt(currentSequence, matrix.editingRow,
								matrix.editingColumn);
						matrix.repaint();
					}
				} else {
					lastLocation = l;
					if (e.getKeyChar() == '') {
						currentSequence = "";
					} else {
						currentSequence = "" + c;
					}
					if (isCellEditable(matrix.getEditingRow(),
							matrix.getEditingColumn())) {
						matrix.setValueAt(currentSequence, matrix.editingRow,
								matrix.editingColumn);
					}
					locationChanged = false;
				}
				matrix.repaintComps();
				System.out.println("DEBUG:: Current Sequence: "
						+ currentSequence);
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (type == TableType.RESTRICTION
				&& (column == this.getColumnCount() - 1 && row == 0)) {
			return false;
		} else if (type == TableType.RESTRICTION
				&& (column == this.getColumnCount() - 2 && row == 0)) {
			return false;
		}
		return column > 0;
	}

	/**
	 * Constructs a <code>RXTable</code> to display the values in the
	 * <code>Vector</code> of <code>Vectors</code>, <code>rowData</code>, with
	 * column names, <code>columnNames</code>. The <code>Vectors</code>
	 * contained in <code>rowData</code> should contain the values for that row.
	 * In other words, the value of the cell at row 1, column 5 can be obtained
	 * with the following code:
	 * <p>
	 * 
	 * <pre>
	 * ((Vector) rowData.elementAt(1)).elementAt(5);
	 * </pre>
	 * <p>
	 * 
	 * @param rowData
	 *            the data for the new table
	 * @param columnNames
	 *            names of each column
	 */
	@SuppressWarnings("rawtypes")
	public MatrixTable(Vector rowData, Vector columnNames) {
		this(new DefaultTableModel(rowData, columnNames));
	}

	/**
	 * Constructs a <code>RXTable</code> to display the values in the two
	 * dimensional array, <code>rowData</code>, with column names,
	 * <code>columnNames</code>. <code>rowData</code> is an array of rows, so
	 * the value of the cell at row 1, column 5 can be obtained with the
	 * following code:
	 * <p>
	 * 
	 * <pre>
	 *  rowData[1][5];
	 * </pre>
	 * <p>
	 * All rows must be of the same length as <code>columnNames</code>.
	 * <p>
	 * 
	 * @param rowData
	 *            the data for the new table
	 * @param columnNames
	 *            names of each column
	 */
	public MatrixTable(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
	}

	//
	// Overridden methods
	//
	/*
	 * Override to provide Select All editing functionality
	 */
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean result = super.editCellAt(row, column, e);

		if (isSelectAllForMouseEvent || isSelectAllForActionEvent
				|| isSelectAllForKeyEvent) {
			selectAll(e);
		}

		return result;
	}

	/*
	 * Override to provide Select All editing functionality
	 */

	/*
	 * Select the text when editing on a text related cell is started
	 */
	private void selectAll(EventObject e) {
		final Component editor = getEditorComponent();

		if (editor == null || !(editor instanceof JTextComponent))
			return;

		if (e == null) {
			((JTextComponent) editor).selectAll();
			return;
		}

		// Typing in the cell was used to activate the editor

		if (e instanceof KeyEvent && isSelectAllForKeyEvent) {
			((JTextComponent) editor).selectAll();
			return;
		}

		// F2 was used to activate the editor

		if (e instanceof ActionEvent && isSelectAllForActionEvent) {
			((JTextComponent) editor).selectAll();
			return;
		}

		// A mouse click was used to activate the editor.
		// Generally this is a double click and the second mouse click is
		// passed to the editor which would remove the text selection unless
		// we use the invokeLater()

		if (e instanceof MouseEvent && isSelectAllForMouseEvent) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					((JTextComponent) editor).selectAll();
				}
			});
		}
	}

	//
	// Newly added methods
	//
	/*
	 * Sets the Select All property for for all event types
	 */
	public void setSelectAllForEdit(boolean isSelectAllForEdit) {
		setSelectAllForMouseEvent(isSelectAllForEdit);
		setSelectAllForActionEvent(isSelectAllForEdit);
		setSelectAllForKeyEvent(isSelectAllForEdit);
	}

	/*
	 * Set the Select All property when editing is invoked by the mouse
	 */
	public void setSelectAllForMouseEvent(boolean isSelectAllForMouseEvent) {
		this.isSelectAllForMouseEvent = isSelectAllForMouseEvent;
	}

	/*
	 * Set the Select All property when editing is invoked by the "F2" key
	 */
	public void setSelectAllForActionEvent(boolean isSelectAllForActionEvent) {
		this.isSelectAllForActionEvent = isSelectAllForActionEvent;
	}

	/*
	 * Set the Select All property when editing is invoked by typing directly
	 * into the cell
	 */
	public void setSelectAllForKeyEvent(boolean isSelectAllForKeyEvent) {
		this.isSelectAllForKeyEvent = isSelectAllForKeyEvent;
	}

	//
	// Static, convenience methods
	//
	/**
	 * Convenience method to order the table columns of a table. The columns are
	 * ordered based on the column names specified in the array. If the column
	 * name is not found then no column is moved. This means you can specify a
	 * null value to preserve the current order of a given column.
	 * 
	 * @param table
	 *            the table containing the columns to be sorted
	 * @param columnNames
	 *            an array containing the column names in the order they should
	 *            be displayed
	 */
	public static void reorderColumns(JTable table, Object... columnNames) {
		TableColumnModel model = table.getColumnModel();

		for (int newIndex = 0; newIndex < columnNames.length; newIndex++) {
			try {
				Object columnName = columnNames[newIndex];
				int index = model.getColumnIndex(columnName);
				model.moveColumn(index, newIndex);
			} catch (IllegalArgumentException e) {
			}
		}
	}
} // End of Class RXTable