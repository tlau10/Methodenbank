package view;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.help.JHelpIndexNavigator;
import javax.help.JHelpNavigator;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdom.Element;

import model.XmlFileFilter;
import controller.ApplicationDiaetplaner;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: MainFrame
 * </p>
 * <p>
 * Description: Diese Klasse erzeugt das Hauptfenster der Methode und die
 * Menüleiste
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -4857086542044198370L;

	private int calories_;

	private JPanel contentPane;
	private JPanel jPanelInsertAttributes;

	private JMenuBar jMenuBar = new JMenuBar();

	private JMenu jMenuFile = new JMenu();
	private JMenu jMenuFoods = new JMenu();
	private JMenu jMenuMatrix = new JMenu();
	private JMenu jMenuHelp = new JMenu();
	private JMenu jMenuSettings = new JMenu();

	private JMenuItem jMenuItemExit = new JMenuItem();
	private JMenuItem jMenuItemAddFoods = new JMenuItem();
	private JMenuItem jMenuItemDeleteFoods = new JMenuItem();
	private JMenuItem jMenuItemLoadFoods = new JMenuItem();
	private JMenuItem jMenuItemSaveFoods = new JMenuItem();
	private JMenuItem jMenuItemAbout = new JMenuItem();
	private JMenuItem jMenuItemSetPaths = new JMenuItem();
	private boolean selectFoodMenu = false;

	private JCheckBoxMenuItem jCheckBoxMenuItemShowMatrix = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem jCheckBoxMenuItemCplex = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem jCheckBoxMenuItemLpSolve = new JCheckBoxMenuItem();

	private JLabel statusBar = new JLabel();

	private BorderLayout borderLayout1 = new BorderLayout();

	private Vector<Object> allEatables_;
	private ApplicationDiaetplaner app_;

	// Erzeugt das Fenster
	public MainFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			createMenuBar();
		} catch (Exception e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}
	}

	public void showErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage);
	}

	public MainFrame(Vector<Object> allEatables, ApplicationDiaetplaner app) {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			allEatables_ = allEatables;
			app_ = app;
			createMenuBar();
		} catch (Exception e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}
	}

	public void closechild(int jPanel, JPanel target, int calories,
			Vector<Object> resultVector) {
		contentPane.remove(target);
		if (jPanel == 1)// Weiter zu 2
		{
			calories_ = calories;

			jPanelInsertAttributes = new SelectFoodFrame(allEatables_,
					calories_, this);
			jMenuFoods.setEnabled(false);
			selectFoodMenu = true;
			this.repaint();
			contentPane.add(jPanelInsertAttributes, BorderLayout.CENTER);
			this.validate();
		}
		if (jPanel == 2)// Weiter zu 3
		{
			ResultFrame myJPanelAusgabe = new ResultFrame(resultVector,
					app_.getMenuCalories(), calories_, this);
			contentPane.add(myJPanelAusgabe, BorderLayout.CENTER);
			this.validate();
		}
		if ((jPanel == 3) || (jPanel == 4) || (jPanel == 5))// Zurück
		{
			jMenuFoods.setEnabled(true);
			selectFoodMenu = false;
			jPanelInsertAttributes = new StartFrame(this);
			contentPane.add(jPanelInsertAttributes, BorderLayout.CENTER);
			this.validate();
		}
		if (jPanel == 6 || jPanel == 7) {
			contentPane.add(jPanelInsertAttributes, BorderLayout.CENTER);
			jMenuSettings.setEnabled(true);
			this.validate();
		}
		jPanel = 0;
		this.repaint();
	}

	// Erzeugt das Menü
	private void createMenuBar() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		this.getContentPane().setBackground(SystemColor.control);
		this.setSize(new Dimension(800, 600));
		this.setTitle("Ernährungsplaner 2.0");
		statusBar.setText("Copyright (c) 2015");

		contentPane.setMaximumSize(new Dimension(2147483647, 2147483647));
		contentPane.setMinimumSize(new Dimension(640, 480));
		contentPane.setPreferredSize(new Dimension(640, 180));
		contentPane.add(statusBar, BorderLayout.SOUTH);

		createMenuFile();
		createMenuFoods();
		createMenuHelp();
		createMenuMatrix();
		createMenuSettings();

		jMenuBar.add(jMenuFile);
		jMenuBar.add(jMenuFoods);
		jMenuBar.add(jMenuSettings);
		jMenuBar.add(jMenuMatrix);
		jMenuBar.add(jMenuHelp);

		this.setJMenuBar(jMenuBar);

		jPanelInsertAttributes = new StartFrame(this);

		contentPane.add(jPanelInsertAttributes, BorderLayout.CENTER, 1);
		this.validate();
	}

	private void createMenuFile() {
		jMenuFile.setText("Ernährungsplan");
		jMenuItemExit.setText("Exit");
		jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuItemExit_actionPerformed(e);
			}
		});
		jMenuFile.add(jMenuItemExit);
	}

	private void createMenuFoods() {
		jMenuFoods.setAlignmentX((float) 0.5);
		jMenuFoods.setText("Lebensmittel");
		jMenuItemLoadFoods.setText("Lebensmittel-Liste importieren");
		jMenuItemSaveFoods.setText("Lebensmittel-Liste exportieren");
		jMenuItemLoadFoods
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jMenuItemLoadFoods_actionPerformed(e);
					}
				});
		jMenuItemSaveFoods
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jMenuItemSaveFoods_actionPerformed(e);
					}
				});
		jMenuItemAddFoods.setText("Lebensmittel hinzufügen");
		jMenuItemAddFoods
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jMenuItemAddFoods_actionPerformed(e);
					}
				});
		jMenuItemDeleteFoods.setVerifyInputWhenFocusTarget(true);
		jMenuItemDeleteFoods.setActionCommand("");
		jMenuItemDeleteFoods.setText("Lebensmittel löschen");
		jMenuItemDeleteFoods
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jMenuItemDeleteFoods_actionPerformed(e);
					}
				});
		jMenuFoods.add(jMenuItemLoadFoods);
		jMenuFoods.add(jMenuItemSaveFoods);
		jMenuFoods.add(jMenuItemAddFoods);
		jMenuFoods.add(jMenuItemDeleteFoods);
	}

	private void createMenuSettings() {
		jMenuItemSetPaths.setText("Solverpfad ändern");
		jMenuItemSetPaths
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jMenuItemSetPaths_actionPerformed(e);
					}
				});
		jCheckBoxMenuItemCplex.setEnabled(true);
		jCheckBoxMenuItemCplex.setArmed(false);
		jCheckBoxMenuItemCplex.setText("Cplex verwenden");
		jCheckBoxMenuItemCplex
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jCheckBoxMenuItemCplex_actionPerformed(e);
					}
				});

		jCheckBoxMenuItemLpSolve.setEnabled(true);
		jCheckBoxMenuItemLpSolve.setArmed(true);
		jCheckBoxMenuItemLpSolve.setState(true);
		jCheckBoxMenuItemLpSolve.setText("LP_Solve verwenden");
		jCheckBoxMenuItemLpSolve
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jCheckBoxMenuItemLpSolve_actionPerformed(e);
					}
				});

		jMenuSettings.setText("Einstellungen");
		jMenuSettings.add(jMenuItemSetPaths);
		jMenuSettings.add(jCheckBoxMenuItemLpSolve);
		jMenuSettings.add(jCheckBoxMenuItemCplex);
	}

	private void createMenuHelp() {
		jMenuItemAbout.setText("About");
		jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuItemAbout_actionPerformed(e);
			}
		});
		jMenuHelp.setText("Hilfe");
		jMenuHelp.add(jMenuItemAbout);
	}

	private void createMenuMatrix() {
		jMenuMatrix.setVerifyInputWhenFocusTarget(true);
		jMenuMatrix.setText("Matrix");
		jCheckBoxMenuItemShowMatrix.setEnabled(true);
		jCheckBoxMenuItemShowMatrix.setArmed(false);
		app_.setPrintMatrix(false);
		jCheckBoxMenuItemShowMatrix.setText("Anzeigen");
		jCheckBoxMenuItemShowMatrix
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jCheckBoxMenuItemShowMatrix_actionPerformed(e);
					}
				});
		jMenuMatrix.add(jCheckBoxMenuItemShowMatrix);
	}

	private void jMenuItemExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuItemExit_actionPerformed(null);
		}
	}

	private void jCheckBoxMenuItemShowMatrix_actionPerformed(ActionEvent e) {

		if (jCheckBoxMenuItemShowMatrix.getState()) {
			jCheckBoxMenuItemShowMatrix.setArmed(false);
			app_.setPrintMatrix(true);
		} else {
			jCheckBoxMenuItemShowMatrix.setArmed(true);
			app_.setPrintMatrix(false);
		}
	}

	private void jCheckBoxMenuItemCplex_actionPerformed(ActionEvent e) {
		if (jCheckBoxMenuItemCplex.getState()) {
			jCheckBoxMenuItemCplex.setArmed(false);

			jCheckBoxMenuItemLpSolve.setArmed(true);
			jCheckBoxMenuItemLpSolve.setState(false);

			app_.setSolverToCalculate("Cplex");
		} else {
			jCheckBoxMenuItemCplex.setArmed(true);

			jCheckBoxMenuItemLpSolve.setArmed(false);
			jCheckBoxMenuItemLpSolve.setState(true);

			app_.setSolverToCalculate("LP_Solve");
		}
	}

	private void jCheckBoxMenuItemLpSolve_actionPerformed(ActionEvent e) {
		if (jCheckBoxMenuItemLpSolve.getState()) {
			jCheckBoxMenuItemLpSolve.setArmed(false);

			jCheckBoxMenuItemCplex.setArmed(true);
			jCheckBoxMenuItemCplex.setState(false);

			app_.setSolverToCalculate("LP_Solve");
		} else {
			jCheckBoxMenuItemLpSolve.setArmed(false);
			jCheckBoxMenuItemLpSolve.setState(true);

			app_.setSolverToCalculate("LP_Solve");
		}
	}

	protected void jMenuItemSetPaths_actionPerformed(ActionEvent e) {
		enableAllButtons();
		jMenuSettings.setEnabled(false);
		SetPathFrame jPanelSetPaths = new SetPathFrame(this);
		contentPane.remove(1);
		contentPane.add(jPanelSetPaths, BorderLayout.CENTER, 1);
		this.validate();
	}

	// Zeigt das Hilfe-Fenster an
	private void jMenuItemAbout_actionPerformed(ActionEvent e) {
		JHelp helpViewer = null;
		try {
			// Hauptfenster in der nächsten Zeile ersetzen durch aktuellen
			// Klassennamen
			ClassLoader cl = MainFrame.class.getClassLoader();
			URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
			helpViewer = new JHelp(new HelpSet(cl, url));
			// Darzustellendes Kapitel festlegen, ID muss im XML existieren!

			Enumeration<?> eNavigators = helpViewer.getHelpNavigators();
			while (eNavigators.hasMoreElements()) {
				JHelpNavigator nav = (JHelpNavigator) eNavigators.nextElement();
				if (nav instanceof JHelpIndexNavigator) {
					helpViewer.removeHelpNavigator(nav);
				}
			}
		} catch (Exception ex) {
			ErrorMessages.throwErrorMessage("API Help Set nicht gefunden");
		}

		JFrame frame = new JFrame();
		frame.setTitle("Hilfe zum Ernährungsplaner 2.0");
		frame.setSize(800, 600);
		frame.getContentPane().add(helpViewer);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private void jMenuItemLoadFoods_actionPerformed(ActionEvent e) {
		try {
			// Ein FileChooser Dialog wird konfiguriert und angezeigt
			String filename = "Daten";
			JFileChooser fc = new JFileChooser(filename);
			XmlFileFilter myFilter = new XmlFileFilter();
			fc.addChoosableFileFilter(myFilter);

			fc.showOpenDialog(new JFrame());
			File selFile = fc.getSelectedFile();
			if (selFile == null) {
				return;
			} else {
				filename = selFile.getAbsolutePath();
				System.out.println("Filename" + filename);
				app_.readFromXmlFile(filename);
				allEatables_ = app_.getAllEatables();
				JOptionPane.showMessageDialog(null,
						"Lebensmittelliste importiert!", "Hinweis",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Import fehlgeschlagen. Gültige Datei ausgewählt?",
					"Hinweis", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void jMenuItemSaveFoods_actionPerformed(ActionEvent e) {
		// Ein FileChooser Dialog wird konfiguriert und angezeigt
		String filename = File.separator + "xml";
		JFileChooser fc = new JFileChooser(new File(filename));
		XmlFileFilter myFilter = new XmlFileFilter();
		fc.addChoosableFileFilter(myFilter);
		fc.showSaveDialog(new JFrame());
		File selFile = fc.getSelectedFile();
		if (selFile == null) {
			return;
		} else {
			System.out.println("selFile" + selFile.getAbsolutePath());
			filename = selFile.getAbsolutePath();

			try {
				app_.saveToXmlFile(filename);
				JOptionPane.showMessageDialog(null, "Export abgeschlossen!",
						"Hinweis", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Export fehlgeschlagen.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}

	private void jMenuItemAddFoods_actionPerformed(ActionEvent e) {
		contentPane.remove(1);
		enableAllButtons();
		jMenuFoods.setEnabled(false);
		AddFoodFrame jPanelAddFoods = new AddFoodFrame(this, allEatables_);
		contentPane.add(jPanelAddFoods, BorderLayout.CENTER, 1);
		this.validate();
	}

	private void jMenuItemDeleteFoods_actionPerformed(ActionEvent e) {
		contentPane.remove(1);
		enableAllButtons();
		jMenuFoods.setEnabled(false);
		DeleteFoodFrame jPanelDeleteFoods = new DeleteFoodFrame(this,
				allEatables_);
		contentPane.add(jPanelDeleteFoods, BorderLayout.CENTER, 1);
		this.validate();
	}

	private void enableAllButtons() {
		if (!selectFoodMenu) {
			jMenuFoods.setEnabled(true);
		}
		jMenuFile.setEnabled(true);
		jMenuHelp.setEnabled(true);
		jMenuSettings.setEnabled(true);
		jMenuMatrix.setEnabled(true);
	}
	
	public Vector<Object> getAllEatables() {
		return app_.getAllEatables();
	}
	
	public boolean deleteEatable(Element element) {
		return app_.deleteEatable(element);
	}
	
	public String[] getGroupName() {
		return app_.getGruppenName();
	}
	
	public Vector<Object> executeCalculation (Vector<Object> choosenEatables, int calories) {
		return app_.executeCalculation(choosenEatables, calories);
	}
	
	public void addEatable(Element newEatable) {
		app_.addEatable(newEatable);
	}
	
	public Element newEatable (String name, int groupID, String amount, String calories) {
		return app_.newEatable(name, groupID, amount, calories);
	}
}
