package de.fh_konstanz.simubus.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import sun.awt.image.ToolkitImage;
import sun.awt.image.URLImageSource;
import de.fh_konstanz.simubus.controller.JToggleButtonListener;
import de.fh_konstanz.simubus.controller.MenuListener;
import de.fh_konstanz.simubus.util.ImageUtil;
import de.fh_konstanz.simubus.util.LayoutUtil;
import de.fh_konstanz.simubus.util.Logger;

/**
 * Die Klasse <code>View</code> ist das Hauptfenster der Anwendung.
 * 
 * @author Daniel Weber, Ingo Kroh
 * @version 1.0 (04.07.2006)
 * 
 * @author Michael Litera
 * @version 1.1 (01.06.2007)
 * 
 */
public class View extends JFrame implements ComponentListener {
	/** Toggle Button für Quick */
	static JToggleButton jtoggleButton = null;

	static ButtonGroup buttonGroup = null;

	/** ID fuer Serialisierung */
	private static final long serialVersionUID = 6898747058191926695L;

	/** Menue */
	private JMenuBar menubar = null;

	/** Menue-Eintrag Optimierung */
	private JMenuItem opti = null;

	/** Menue-Eintrag Simulation */
	private JMenuItem simu = null;

	/** Panel fuer Optimierungseinstellungen */
	private OptiControlPanel optiControlPanel = null;

	/** Panel mit Karte */
	private SimuPanel simuPanel = null;

	/** Panel fuer Simulationseinstellungen */
	private SimuControlPanel simuControlPanel = null;

	/** Instanz des Hauptfenster */
	private static View instance = null;

	/**
	 * liefert ein Objekt des Hauptfenster
	 * 
	 * @return Hauptfenster
	 */
	public static View getInstance() {
		if (instance == null) {
			instance = new View();
		}

		return instance;
	}

	/**
	 * Konstruktor
	 * 
	 */
	public View() {
		super("Haltestellenoptimierung - Bussimulation");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setSize(screenHeight, screenWidth);
		
		this.setIconImage(new ToolkitImage(new URLImageSource(ImageUtil
				.getImageUrl("haltestelle.png"))));

		this.setLayout(new BorderLayout());

		JPanel panel = new JPanel();

		GridBagLayout gbl = new GridBagLayout();
		panel.setLayout(gbl);

		this.optiControlPanel = OptiControlPanel.getInstance();
		this.simuPanel = SimuPanel.getInstance();
		this.simuControlPanel = SimuControlPanel.getInstance();

		this.setJMenuBar(createMenuBar());

		LayoutUtil.addComponent(panel, gbl, simuPanel, 0, 0, 1, 1, 3, 2);
		LayoutUtil.addComponent(panel, gbl, optiControlPanel, 1, 0, 1, 1, 1, 1);
		LayoutUtil.addComponent(panel, gbl, simuControlPanel, 1, 0, 1, 1, 1, 1);
		this.getContentPane().add(createToolBar(), BorderLayout.NORTH);
		this.getContentPane().add(panel, BorderLayout.CENTER);

		simuControlPanel.setVisible(false);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		SwingUtilities.updateComponentTreeUI(this);
		this.pack();
	}

	public void componentResized(ComponentEvent e) {
		Component c = e.getComponent();
		simuPanel.setSize(c.getSize());
		simuPanel.validate();
		c.repaint();
	}

	public void componentShown(ComponentEvent e) {

	}

	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	/**
	 * tauscht die Panel fuer Optimierung und Simulation aus
	 * 
	 */
	public void changePanel() {
		if (simuControlPanel.isVisible()) {

			simuControlPanel.setVisible(false);
			optiControlPanel.setVisible(true);
		} else {

			simuControlPanel.setVisible(true);
			optiControlPanel.setVisible(false);
		}
	}

	/**
	 * wertet die ActionEvents des JToggleButton aus
	 * 
	 * @author Michael Litera
	 * @version 1.0 (01.06.2006)
	 */
	public void ChangeJToggleButtons(ActionEvent event) {
		JToggleButton zieleEinfuegen = findJToggleButtonWithName(View
				.getInstance().getContentPane(), "Ziele einfügen");
		JToggleButton halteStellenEinfuegen = findJToggleButtonWithName(View
				.getInstance().getContentPane(), "Haltestelle einfügen");
		JToggleButton strasseEinfuegen = findJToggleButtonWithName(View
				.getInstance().getContentPane(), "Strasse einfügen");
		JToggleButton strasseGesperrt = findJToggleButtonWithName(View
				.getInstance().getContentPane(), "gesperrte Strasse einfügen");
		JToggleButton halteStelleGesperrt = findJToggleButtonWithName(View
				.getInstance().getContentPane(),
				"gesperrte Haltestelle einfügen");
		JToggleButton loeschen = findJToggleButtonWithName(View.getInstance()
				.getContentPane(), "loeschen");

		// Info Button
		jtoggleButton = (JToggleButton) event.getSource();
		boolean selected = jtoggleButton.getModel().isSelected();
		Logger.getInstance().log(
				"Button: " + jtoggleButton.getName() + " : " + selected + "\n");

		if (event.getActionCommand().equals("Ziele einfügen")) {
			if (selected) {
				//			   System.out.println("Action - selected= " + selected + "\n");
				halteStellenEinfuegen.setSelected(false);
				strasseEinfuegen.setSelected(false);
				strasseGesperrt.setSelected(false);
				halteStelleGesperrt.setSelected(false);
				loeschen.setSelected(false);
			}
		} else if (event.getActionCommand().equals("Haltestelle einfügen")) {
			if (selected) {
				//			   System.out.println("Action - selected= " + selected + "\n");
				zieleEinfuegen.setSelected(false);
				strasseEinfuegen.setSelected(false);
				strasseGesperrt.setSelected(false);
				halteStelleGesperrt.setSelected(false);
				loeschen.setSelected(false);
			}
		} else if (event.getActionCommand().equals("Strasse einfügen")) {
			if (selected) {
				//			   System.out.println("Action - selected= " + selected + "\n");
				zieleEinfuegen.setSelected(false);
				halteStellenEinfuegen.setSelected(false);
				strasseGesperrt.setSelected(false);
				halteStelleGesperrt.setSelected(false);
				loeschen.setSelected(false);
			}
		} else if (event.getActionCommand()
				.equals("gesperrte Strasse einfügen")) {
			if (selected) {
				//			   System.out.println("Action - selected= " + selected + "\n");
				zieleEinfuegen.setSelected(false);
				halteStellenEinfuegen.setSelected(false);
				strasseEinfuegen.setSelected(false);
				halteStelleGesperrt.setSelected(false);
				loeschen.setSelected(false);
			}
		} else if (event.getActionCommand().equals(
				"gesperrte Haltestelle einfügen")) {
			if (selected) {
				//			   System.out.println("Action - selected= " + selected + "\n");
				zieleEinfuegen.setSelected(false);
				halteStellenEinfuegen.setSelected(false);
				strasseEinfuegen.setSelected(false);
				strasseGesperrt.setSelected(false);
				loeschen.setSelected(false);
			}
		} else if (event.getActionCommand().equals("loeschen")) {
			if (selected) {
				//			   System.out.println("Action - selected= " + selected + "\n");
				zieleEinfuegen.setSelected(false);
				halteStellenEinfuegen.setSelected(false);
				strasseEinfuegen.setSelected(false);
				strasseGesperrt.setSelected(false);
				halteStelleGesperrt.setSelected(false);
			}
		}
	}

	/**
	 * erstellt das Hauptmenue
	 * 
	 * @return Hauptmenue
	 */
	private JMenuBar createMenuBar() {
		this.menubar = new JMenuBar();
		JMenu datei = new JMenu("Datei");
		datei.setMnemonic('D');
		JMenuItem mi;
		// Neu
		mi = new JMenuItem("Neu", 'n');
		mi.setActionCommand("neu");
		this.setCtrlAccelerator(mi, 'N');
		mi.addActionListener(new MenuListener());
		datei.add(mi);
		// Öffnen
		mi = new JMenuItem("Öffnen", 'f');
		mi.setActionCommand("oeffnen");
		setCtrlAccelerator(mi, 'O');
		mi.addActionListener(new MenuListener());
		datei.add(mi);
		// Speichern
		mi = new JMenuItem("Speichern", 'p');
		mi.setActionCommand("speichern");
		setCtrlAccelerator(mi, 'S');
		mi.addActionListener(new MenuListener());
		datei.add(mi);
		// Separator
		datei.addSeparator();

		// Einstellungen
		JMenuItem settings = new JMenuItem("Einstellungen");
		settings.setActionCommand("settings");
		setCtrlAccelerator(settings, 'E');
		settings.addActionListener(new MenuListener());
		datei.add(settings);
		datei.addSeparator();

		// Beenden
		mi = new JMenuItem("Beenden", 'e');
		mi.setActionCommand("beenden");
		mi.addActionListener(new MenuListener());
		datei.add(mi);

		JMenu bearbeiten = new JMenu("Bearbeiten");
		mi = new JMenuItem("Undo");
		URL undoUrl = ImageUtil.getImageUrl("undo.gif");
		ImageIcon undoIcon = new ImageIcon(undoUrl);
		mi.setIcon(undoIcon);
		mi.setActionCommand("undo");
		this.setCtrlAccelerator(mi, 'u');
		mi.addActionListener(new MenuListener());
		bearbeiten.add(mi);

		mi = new JMenuItem("Redo");
		URL redoUrl = ImageUtil.getImageUrl("redo.gif");
		ImageIcon redoIcon = new ImageIcon(redoUrl);
		mi.setIcon(redoIcon);
		mi.setActionCommand("redo");
		this.setCtrlAccelerator(mi, 'R');
		mi.addActionListener(new MenuListener());
		bearbeiten.add(mi);

		JMenu karte = new JMenu("Karte");
		JMenuItem karteLaden = new JMenuItem("Karte laden");
		karteLaden.setActionCommand("karteLaden");
		karteLaden.addActionListener(new MenuListener());
		setCtrlAccelerator(karteLaden, 'L');
		karte.add(karteLaden);

		JMenu ansicht = new JMenu("Ansicht");
		ansicht.setMnemonic('A');
		ButtonGroup bg = new ButtonGroup();
		// Haltestellenoptimierung
		opti = new JRadioButtonMenuItem("Haltestellenoptimierung", true);
		setCtrlAccelerator(opti, 'H');
		opti.addActionListener(new MenuListener());
		ansicht.add(opti);
		bg.add(opti);
		// Bussimulation
		simu = new JRadioButtonMenuItem("Bussimulation");
		setCtrlAccelerator(simu, 'S');
		simu.addActionListener(new MenuListener());
		ansicht.add(simu);
		bg.add(simu);
		ansicht.addSeparator();
		mi = new JCheckBoxMenuItem("Grid ein/ausblenden");
		setCtrlAccelerator(mi, 'G');
		((JCheckBoxMenuItem) mi).setState(true);
		mi.addActionListener(new MenuListener());
		ansicht.add(mi);
		JMenu hilfe = new JMenu("Hilfe");

		mi = new JMenuItem("Info", 'i');
		mi.setActionCommand("info");
		setCtrlAccelerator(mi, 'I');
		mi.addActionListener(new MenuListener());
		hilfe.add(mi);
		menubar.add(datei);
		menubar.add(bearbeiten);
		menubar.add(karte);
		menubar.add(ansicht);
		menubar.add(hilfe);
		return menubar;
	}

	/**
	 * setzt den Ctrl-Accelerator fuer Menuepunkt
	 * 
	 * @param mi
	 *           Menuepunkt
	 * @param acc
	 *           Taste
	 */
	private void setCtrlAccelerator(JMenuItem mi, char acc) {
		KeyStroke ks = KeyStroke.getKeyStroke(acc, Event.CTRL_MASK);
		mi.setAccelerator(ks);
	}

	/**
	 * erstellt eine Toolbar
	 * 
	 * @return eine Toolbar
	 */
	private JToolBar createToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		// Neu
		URL newUrl = ImageUtil.getImageUrl("new.gif");
		ImageIcon newIcon = new ImageIcon(newUrl);
		toolbar.add(new AbstractAction("Neu", newIcon) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -9103549238112150356L;

			public void actionPerformed(ActionEvent e) {
				SimuPanel.getInstance().resetPanel();
			}
		});
		// Undo
		URL undoUrl = ImageUtil.getImageUrl("undo.gif");
		ImageIcon undoIcon = new ImageIcon(undoUrl);
		toolbar.add(new AbstractAction("Rückgängig", undoIcon) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4189021935759269814L;

			public void actionPerformed(ActionEvent e) {
				simuPanel.undo();
			}
		});
		// Redo
		URL redoUrl = ImageUtil.getImageUrl("redo.gif");
		ImageIcon redoIcon = new ImageIcon(redoUrl);
		toolbar.add(new AbstractAction("Wiederherstellen", redoIcon) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3268492484427538933L;

			public void actionPerformed(ActionEvent e) {
				simuPanel.redo();
			}
		});

		/**
		 *  Quick JButtonToggle Leiste
		 * @author von Michael Litera
		 */
		toolbar.addSeparator();
		//buttonGroup = new ButtonGroup();

		// JToggleButton Ziele einfügen
		URL targetUrl = ImageUtil.getImageUrl("ziele.gif");
		ImageIcon zieleIcon = new ImageIcon(targetUrl);
		jtoggleButton = new JToggleButton("", zieleIcon);
		jtoggleButton.setActionCommand("Ziele einfügen");
		jtoggleButton.addActionListener(new JToggleButtonListener());
		jtoggleButton.setSelected(true);
		jtoggleButton.setName("Ziele einfügen");
		jtoggleButton.setToolTipText("Ziele einfügen");
		//buttonGroup.add(jtoggleButton);
		toolbar.add(jtoggleButton);

		// JToggleButton HalteStelle einfügen
		URL halteStelleUrl = ImageUtil.getImageUrl("haltestelle.gif");
		ImageIcon halteStelleIcon = new ImageIcon(halteStelleUrl);
		jtoggleButton = new JToggleButton("", halteStelleIcon);
		jtoggleButton.setActionCommand("Haltestelle einfügen");
		jtoggleButton.addActionListener(new JToggleButtonListener());
		jtoggleButton.setName("Haltestelle einfügen");
		jtoggleButton.setToolTipText("Haltestelle einfügen");
		// buttonGroup.add(jtoggleButton);
		toolbar.add(jtoggleButton);

		// JToggleButton Strasse einfügen
		URL strasseUrl = ImageUtil.getImageUrl("strasse.gif");
		ImageIcon strasseIcon = new ImageIcon(strasseUrl);
		jtoggleButton = new JToggleButton("", strasseIcon);
		jtoggleButton.setActionCommand("Strasse einfügen");
		jtoggleButton.addActionListener(new JToggleButtonListener());
		jtoggleButton.setName("Strasse einfügen");
		jtoggleButton.setToolTipText("Strasse einfügen");
		//	  buttonGroup.add(jtoggleButton);
		toolbar.add(jtoggleButton);

		toolbar.addSeparator();

		// JToggleButton löschen
		URL loeschenUrl = ImageUtil.getImageUrl("loeschen.gif");
		ImageIcon loeschenIcon = new ImageIcon(loeschenUrl);
		jtoggleButton = new JToggleButton("", loeschenIcon);
		jtoggleButton.setActionCommand("loeschen");
		jtoggleButton.addActionListener(new JToggleButtonListener());
		jtoggleButton.setName("loeschen");
		jtoggleButton.setToolTipText("löschen");
		//	  buttonGroup.add(jtoggleButton);
		toolbar.add(jtoggleButton);

		// JToggleButton Strasse/Felder gesperrt
		URL haltestelleGesperrtUrl = ImageUtil
				.getImageUrl("haltestellegesperrt.gif");
		ImageIcon haltestelleGesperrtIcon = new ImageIcon(
				haltestelleGesperrtUrl);
		jtoggleButton = new JToggleButton("", haltestelleGesperrtIcon);
		jtoggleButton.setActionCommand("gesperrte Haltestelle einfügen");
		jtoggleButton.addActionListener(new JToggleButtonListener());
		jtoggleButton.setName("gesperrte Haltestelle einfügen");
		jtoggleButton.setToolTipText("gesperrte Haltestellen");
		//	  buttonGroup.add(jtoggleButton);
		toolbar.add(jtoggleButton);

		// JToggleButton Haltestelle gesperrt
		URL strasseGesperrtUrl = ImageUtil.getImageUrl("strassegesperrt.gif");
		ImageIcon strasseGesperrtIcon = new ImageIcon(strasseGesperrtUrl);
		jtoggleButton = new JToggleButton("", strasseGesperrtIcon);
		jtoggleButton.setActionCommand("gesperrte Strasse einfügen");
		jtoggleButton.addActionListener(new JToggleButtonListener());
		jtoggleButton.setName("gesperrte Strasse einfügen");
		jtoggleButton.setToolTipText("gesperrte Felder");
		//	  buttonGroup.add(jtoggleButton);
		toolbar.add(jtoggleButton);

		/**
		 * Ende Quick JButtonToggle Leiste
		 */
		return toolbar;
	}

	private JToggleButton findJToggleButtonWithName(Container container,
			String jToggleButtonText) {
		Component[] components = container.getComponents();

		JToggleButton buttonToFind = null;
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];

			// if the component is a container, find out if it contains the JButton
			if (component instanceof Container) {
				buttonToFind = findJToggleButtonWithName((Container) component,
						jToggleButtonText);
				if (buttonToFind != null)
					break;
			}

			// if the component is a JButton, find out if it contains the text
			if (component instanceof JToggleButton) {
				JToggleButton button = (JToggleButton) component;
				// compare the name with that passed in
				if (button.getName().equals(jToggleButtonText)) {
					buttonToFind = button;
					break;
				}
			}
		}
		return buttonToFind;
	}
}
