package iterator.gui.swing;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Mainframe {

	private JFrame mainframe;
	private JTable maintableau;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
					Mainframe window = new Mainframe();
					window.mainframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainframe() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		mainframe = new JFrame();
		mainframe.setBounds(100, 100, 1000, 751);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.getContentPane().setLayout(null);
		
		JInternalFrame internalFrame_main = new JInternalFrame("Tableau");
		internalFrame_main.setResizable(true);
		internalFrame_main.setMaximizable(true);
		internalFrame_main.setClosable(true);
		internalFrame_main.setBounds(81, 64, 721, 482);
		mainframe.getContentPane().add(internalFrame_main);
		internalFrame_main.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_table = new JScrollPane();
		scrollPane_table.setBounds(10, 56, 685, 354);
		internalFrame_main.getContentPane().add(scrollPane_table);
		
		maintableau = new JTable();
		maintableau.setCellSelectionEnabled(true);
		maintableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		maintableau.setModel(new DefaultTableModel(
			new Object[][] {
				{"Restriktion 1", null, null, null},
				{"Restriktion 2", null, null, null},
				{"Zielfunktion", null, null, null},
			},
			new String[] {
				"Restriktionen", "x1", "x2", "b"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, Double.class, Double.class, Double.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		maintableau.setRowHeight(30);
		maintableau.getColumnModel().getColumn(0).setResizable(false);
		scrollPane_table.setViewportView(maintableau);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 410, 705, 43);
		internalFrame_main.getContentPane().add(layeredPane);
		
		JLabel lblSpinnerRow = new JLabel("Zeilen");
		lblSpinnerRow.setBounds(10, 17, 29, 14);
		layeredPane.add(lblSpinnerRow);
		
		JSpinner spinnerRow = new JSpinner();
		spinnerRow.setBounds(42, 14, 46, 20);
		layeredPane.add(spinnerRow);
		
		JSpinner spinnerColumn = new JSpinner();
		spinnerColumn.setBounds(160, 14, 46, 20);
		layeredPane.add(spinnerColumn);
		
		JLabel lblSpinnerColumn = new JLabel("Spalten");
		lblSpinnerColumn.setBounds(121, 17, 46, 14);
		layeredPane.add(lblSpinnerColumn);
		
		JCheckBox chk_newTableau = new JCheckBox("Neues Fenster");
		chk_newTableau.setBounds(225, 13, 97, 23);
		layeredPane.add(chk_newTableau);
		
		JLabel lblTableauInfo = new JLabel("infolabel...");
		lblTableauInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTableauInfo.setBounds(328, 17, 367, 14);
		layeredPane.add(lblTableauInfo);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(0, 0, 705, 56);
		internalFrame_main.getContentPane().add(layeredPane_1);
		
		JButton btnPivot = new JButton("Pivot");
		btnPivot.setBounds(10, 11, 92, 34);
		layeredPane_1.add(btnPivot);
		
		JButton btnIteration = new JButton("Iteration");
		btnIteration.setBounds(113, 11, 92, 34);
		layeredPane_1.add(btnIteration);
		
		JButton btnOptimum = new JButton("Optimum");
		btnOptimum.setBounds(215, 11, 92, 34);
		layeredPane_1.add(btnOptimum);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 984, 21);
		mainframe.getContentPane().add(menuBar);
		
		JMenu menuFile = new JMenu("Datei");
		menuBar.add(menuFile);
		
		JMenuItem mntmNeu = new JMenuItem("Neu...");
		menuFile.add(mntmNeu);
		
		JSeparator separator = new JSeparator();
		menuFile.add(separator);
		
		JMenuItem mntmLaden = new JMenuItem("Laden");
		menuFile.add(mntmLaden);
		
		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		menuFile.add(mntmSpeichern);
		
		JMenu menuIterator = new JMenu("Iterator");
		menuBar.add(menuIterator);
		
		JMenu menuHelp = new JMenu("Hilfe");
		menuBar.add(menuHelp);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Tutorial");
		menuHelp.add(mntmNewMenuItem);
		
		JCheckBoxMenuItem chckbxmntmDebugmodus = new JCheckBoxMenuItem("Debugmodus");
		menuHelp.add(chckbxmntmDebugmodus);
		internalFrame_main.setVisible(true);
	}
	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
