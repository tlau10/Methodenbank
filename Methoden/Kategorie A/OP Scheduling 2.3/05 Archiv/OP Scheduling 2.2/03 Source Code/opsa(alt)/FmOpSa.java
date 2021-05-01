package opsa;
import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.help.JHelpIndexNavigator;
import javax.help.JHelpNavigator;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/*
 * Title: OP-Scheduling
 * erweitert Juni 2003
 * @author Nina Bruch, Katharina Dammeier
 * @version 2.0
 */

public class FmOpSa extends JFrame {
	/**
	 * Serial ID für Serialisierung
	 */
	private static final long serialVersionUID = 8486512971829949483L;
	Button buttonOpt = new Button();
	JPanel contentPane;
	int defaultPeriode = 0;
	Vector<Object> ergebnisse = new Vector<Object>();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenuFile = new JMenu();
	JMenuItem jMenuFileExit = new JMenuItem();
	JMenu jMenuHelp = new JMenu();
	JMenu jMenuOption = new JMenu();
	JMenuItem jMenuHelpAbout = new JMenuItem();
	JMenuItem jMenuItemNew = new JMenuItem();
	JMenuItem jMenuItemOpen = new JMenuItem();
	JMenuItem jMenuItemSave = new JMenuItem();
	JMenuItem jMenuItemPath = new JMenuItem();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTable jTableOperation = null;

	private WorkData workData = new WorkData();

	private int operationsAnz;
	private int periodeAnz;
	private int saalAnz;
	String[] tbOp_columnNames = { "Nr.", "Dauer(min)", "von Periode",
			"bis Periode", "Fachrichtung" };

	DefaultTableModel tbOpModel;
	TextArea textAreaInfo = new TextArea();
	LPData theLpdata;
	Vector<Object> vectorErgebnisse = new Vector<Object>();
	Vector<Object> vectorOperation = new Vector<Object>();

	Vector<Object> vectorPeriodeSaal = new Vector<Object>();
	XYLayout xYLayout1 = new XYLayout();

	/** Construct the frame */
	public FmOpSa() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void buttonOpt_actionPerformed(ActionEvent e) {
		if (tbOpModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this,
					"Bitte wahlen Sie new von Menu aus.", "Info",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (tbOpModel.getValueAt(tbOpModel.getRowCount() - 1, tbOpModel
				.getColumnCount() - 2) == null) {
			JOptionPane.showMessageDialog(this,
					"Bitte geben Sie Daten in die Tabelle Operation ein.",
					"Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// lese die Daten ein und erzeuge Objekts von Operationen
		if (this.setOperations() == true) {
			// erstelle die txt-Datei
			Ini ini = new Ini();
			if (theLpdata == null){
				theLpdata = new LPData(ini.getDefauldPeriode(), saalAnz);
			}
			if (theLpdata != null) {
				// NK Anzahl der OPSle muss mitbergeben werden
				//Set Ini if Changed
				theLpdata.setLPSolvePath(ini.getLPsolvePath());
				if (theLpdata.initialisierung(vectorOperation,
						vectorPeriodeSaal) == true) {
					theLpdata.parseData();
					this.setInfo();
				}
			}
		} else {// abbrechen
			return;
		}
	}

	/** Component initialization */
	private void jbInit() throws Exception {
		// setIconImage(Toolkit.getDefaultToolkit().createImage(FmOpSa.class.getResource("[Your
		// Icon]")));
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(xYLayout1);
		this.setSize(new Dimension(615, 585));
		this.setTitle("OP-Scheduling");
		jMenuFile.setText("Datei");
		jMenuOption.setText("Optionen");
		jMenuFileExit.setText("Beenden");
		jMenuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuFileExit_actionPerformed(e);
			}
		});
		jMenuHelp.setText("Hilfe");
		jMenuHelpAbout.setText("Ueber");
		jMenuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuHelpAbout_actionPerformed(e);
			}
		});
		textAreaInfo.setText("");
		buttonOpt.setFont(new java.awt.Font("Dialog", 1, 12));
		buttonOpt.setLabel("Optimieren");
		buttonOpt.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonOpt_actionPerformed(e);
			}
		});
		jMenuItemNew.setText("Neu");
		jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuItemNew_actionPerformed(e);
			}
		});

		jMenuItemOpen.setText("Oeffnen");
		jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuItemOpen_actionPerformed(e);
			}
		});

		jMenuItemSave.setText("Speichern");
		jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuItemSave_actionPerformed(e);
			}
		});

		jMenuItemPath.setText("Solver Pfad");
		jMenuItemPath.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuItemPath_actionPerformed(e);
			}
		});
		jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
		jLabel1.setText("Operation");
		jMenuFile.add(jMenuItemNew);
		jMenuFile.add(jMenuItemOpen);
		jMenuFile.add(jMenuItemSave);
		jMenuFile.add(jMenuFileExit);
		jMenuHelp.add(jMenuHelpAbout);
		jMenuOption.add(jMenuItemPath);
		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuOption);
		jMenuBar1.add(jMenuHelp);
		contentPane.add(jScrollPane1, new XYConstraints(5, 37, 500, 222));
		contentPane.add(jLabel1, new XYConstraints(8, 15, -1, -1));
		contentPane.add(textAreaInfo, new XYConstraints(1, 272, 600, 309));
		contentPane.add(buttonOpt, new XYConstraints(516, 235, -1, -1));
		this.resetTableModel();
		this.setJMenuBar(jMenuBar1);
		jTableOperation = new JTable(tbOpModel);
		jTableOperation.setCellSelectionEnabled(true);
		jScrollPane1.getViewport().add(jTableOperation, null);
	}

	/** File | Exit action performed */
	public void jMenuFileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/** Help | About action performed */
	@SuppressWarnings("deprecation")
	public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
//		FmOpSa_AboutBox dlg = new FmOpSa_AboutBox(this);
//		Dimension dlgSize = dlg.getPreferredSize();
//		Dimension frmSize = getSize();
//		Point loc = getLocation();
//		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
//				(frmSize.height - dlgSize.height) / 2 + loc.y);
//		dlg.setModal(true);
//		dlg.show();
		
		JHelp helpViewer = null;
		try {
			// Hauptfenster in der n�chsten Zeile ersetzen durch aktuellen
			// Klassennamen
			ClassLoader cl = FmOpSa.class.getClassLoader();
			URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
			helpViewer = new JHelp(new HelpSet(cl, url));
			// Darzustellendes Kapitel festlegen, ID muss im XML existieren!
			// helpViewer.getsetCurrentID("Simple.Introduction");

			Enumeration eNavigators = helpViewer.getHelpNavigators();
			while (eNavigators.hasMoreElements()) {
				JHelpNavigator nav = (JHelpNavigator) eNavigators.nextElement();
				if (nav instanceof JHelpIndexNavigator) {
					helpViewer.removeHelpNavigator(nav);
				}
			}
		} catch (Exception ex) {
			System.err.println("API Help Set not found");
		}

		JFrame frame = new JFrame();
		frame.setTitle("Hilfe zu OP-Scheduling");
		frame.setSize(800, 600);
		frame.getContentPane().add(helpViewer);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		
	}

	@SuppressWarnings("deprecation")
	void jMenuItemNew_actionPerformed(ActionEvent e) {
		textAreaInfo.setText("");
		tbOpModel.setNumRows(0);
		DlgConfig theDlgConifg = new DlgConfig(this);
		theDlgConifg.setLocation(250, 300);
		theDlgConifg.setSize(400, 230);
		theDlgConifg.show();
	}

	@SuppressWarnings("deprecation")
	void jMenuItemSave_actionPerformed(ActionEvent e) {
		FileDialog f = new FileDialog(this, "Daten speichern", FileDialog.SAVE);
		f.setFile("*.opsa");

		f.setFilenameFilter(new java.io.FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (dir.getName().endsWith(".opsa")) {
					return true;
				} else {
					return false;
				}
			}
		});
		f.show();
		String filename = f.getFile();
		String directory = f.getDirectory();
		if (filename != null) {
			try {
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(directory + filename));
				this.workData.setWorkData(this);
				out.writeObject(this.workData);
				out.flush();
				out.close();
			} catch (IOException exception) {
				JOptionPane.showMessageDialog(null,
						"Bitte einen gültigen Dateinamen eingeben!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@SuppressWarnings("deprecation")
	void jMenuItemOpen_actionPerformed(ActionEvent e) {
		FileDialog f = new FileDialog(this, "Datei Oeffnen", FileDialog.LOAD);
		f.setFile(".opsa");

		f.setFilenameFilter(new java.io.FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (dir.getName().endsWith(".opsa")) {
					return true;
				} else {
					return false;
				}
			}
		});
		f.show();
		String filename = f.getFile();
		String directory = f.getDirectory();
		if (filename != null) {
			try {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(directory + filename));
				this.workData = (WorkData) in.readObject();
				in.close();
				this.workData.setFmOpSaInputs(this);
			} catch (IOException exception) {
				JOptionPane.showMessageDialog(null,
						"Bitte einen gültigen Dateinamen eingeben!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				exception.printStackTrace();
			} catch (ClassNotFoundException exception) {
				exception.printStackTrace();
			}
		}
		
	}

	@SuppressWarnings("deprecation")
	void jMenuItemPath_actionPerformed(ActionEvent e) {
		PathOption framePathOption = new PathOption(this);
		framePathOption.setLocation(250, 300);
		framePathOption.setSize(337, 150);
		framePathOption.show();
	}

	void jMenuItemOpt_actionPerformed(ActionEvent e) {
		buttonOpt_actionPerformed(e);
	}

	/** Overridden so we can exit when window is closed */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuFileExit_actionPerformed(null);
		}
	}

	public void reset() {
		if (vectorOperation.size() != 0)
			vectorOperation.removeAllElements();
		if (vectorPeriodeSaal.size() != 0)
			vectorPeriodeSaal.removeAllElements();
		if ((vectorErgebnisse.size()) != 0)
			vectorErgebnisse.removeAllElements();
	}

	public void resetTableModel() {
		tbOpModel = new DefaultTableModel(tbOp_columnNames, 0);
	}

	public void setDefaultTableOperation(int rowCount) {
		tbOpModel.setRowCount(rowCount);
		for (int i = 0; i < rowCount; i++)
			tbOpModel.setValueAt("" + (i + 1), i, 0);
	}

	public void setInfo() {
		textAreaInfo.setText(theLpdata.getErgebnisse() + "\n");
	}

	public boolean setOperations() {
		this.reset();
		int ID = 0, anfang = 0, ende = 0, dauer = 0;
		String merk = "";
		for (int i = 0; i < tbOpModel.getRowCount(); i++) {
			try {
				ID = Integer.parseInt((String) tbOpModel.getValueAt(i, 0));
				dauer = Integer.parseInt((String) tbOpModel.getValueAt(i, 1));
				anfang = Integer.parseInt((String) tbOpModel.getValueAt(i, 2));
				ende = Integer.parseInt((String) tbOpModel.getValueAt(i, 3));
				if ((String) tbOpModel.getValueAt(i, 4) != null)
					merk = (String) tbOpModel.getValueAt(i, 4);
				else
					merk = "";

				if (anfang <= 0 || ende <= 0 || anfang > periodeAnz
						|| ende > periodeAnz) {
					JOptionPane.showMessageDialog(this, "Maxperiode ist: "
							+ periodeAnz + "\n keine negative Zahl", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				vectorOperation
						.add(new Operation(ID, anfang, ende, dauer, merk));
			} catch (Exception e1) {
				JOptionPane
						.showMessageDialog(
								this,
								e1.toString()
										+ "\nEingabe bei der Operation falsch\nOder Enter vergessen!",
								"Info", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
//		int periodeID = 0;
		// Initialisierung Vektorstring
		for (int j = 0; j < periodeAnz; j++) {
			vectorPeriodeSaal.add(new periodSaal((j + 1), saalAnz));
		}
		return true;
	}

	public void setOperationsAnz(int anz) {
		operationsAnz = anz;
	}

	public void setPeriodeAnz(int anz) {
		periodeAnz = anz;
	}

	public void setSaalAnz(int in_saalAnz) {
		saalAnz = in_saalAnz;
	}

	public int getOperationsAnz() {
		return operationsAnz;
	}

	public int getPeriodeAnz() {
		return periodeAnz;
	}

	public int getSaalAnz() {
		return saalAnz;
	}
}