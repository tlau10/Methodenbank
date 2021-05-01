package com.htwg.powerlp.view;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.htwg.powerlp.controller.Controller;
import com.htwg.powerlp.util.Configurator;

/**
 * Hier wird das Main-Fenster erstellt und es findet die Programmausführung
 * statt
 * 
 * @author Teamprojekt WS 2015/2016 (Ducho, Keller, Lagun, Lu, Pllana)
 * @version 1.0
 */

public class ContainerFrame {

	// ---------------------------------------------------------------------------------------
	// Initialisieren der Variablen
	// ---------------------------------------------------------------------------------------
	private JFrame jfMainFrame;
	private Controller controller;
	private JDesktopPane panelFrames = new JDesktopPane();
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmClose;
	private JMenuItem mntmSave;
	private JRadioButtonMenuItem rdbtnmntmBeenden;
	private JMenuItem mntmSaveAs;
	private JSeparator sepFileOptionsEnd;
	private JMenu mnNewMenu;
	private JMenuItem mntmClone;
	private JMenuItem mntmUserMan;
	private JMenuItem mntmInfo;
	private JMenu mnHelpFile;
	private JPanel panelTools;
	private JButton btnNew;
	private JButton btnOpen;
	private JButton btnSave;
	private JButton btnDuplicate;

	private Configurator configurator;

	/**
	 * Sets the window visible
	 */
	public void show() {
		this.jfMainFrame.setVisible(true);
	}

	/**
	 * @param configurator
	 */
	public ContainerFrame(Controller controller) {
		this.controller = controller;
		this.configurator = controller.getConfigurator();
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		// Anlegen des Mainfensters und bestimmen des Namens, der Grösse etc.
		jfMainFrame = new JFrame();
		jfMainFrame.setIconImage(configurator.getApplicationIcon().getImage());
		jfMainFrame.setTitle("Power LP");
		jfMainFrame.setName("jfMainFrame");
		jfMainFrame.setBounds(200, 200, 1372, 1042);
		jfMainFrame
				.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		// Layout des Hauptfensters bestimmen
		jfMainFrame.getContentPane().setLayout(
				new MigLayout("", "[377.00px,grow]", "[43px][grow]"));

		// Anlegen des Menüs im Hauptfenster und Einfügen der Unterpunkte
		menuBar = new JMenuBar();
		menuBar.setName("menuBar");
		jfMainFrame.setJMenuBar(menuBar);

		mnFile = new JMenu("Datei");
		mnFile.setName("mnFile");
		menuBar.add(mnFile);

		mntmNew = new JMenuItem("Neu");
		mntmNew.setName("mntmNew");
		mntmNew.setIcon(new ImageIcon(ContainerFrame.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		mnFile.add(mntmNew);

		mntmOpen = new JMenuItem("\u00D6ffnen");
		mntmOpen.setName("mntmOpen");
		mntmOpen.setIcon(new ImageIcon(
				ContainerFrame.class
						.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		mnFile.add(mntmOpen);

		mntmClose = new JMenuItem("Schlie\u00DFen");
		mntmClose.setName("mntmClose");
		mnFile.add(mntmClose);

		mntmSave = new JMenuItem("Speichern");
		mntmSave.setName("mntmSave");
		mntmSave.setIcon(new ImageIcon(ContainerFrame.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		mnFile.add(mntmSave);

		mntmSaveAs = new JMenuItem("Speichern unter...");
		mntmSaveAs.setName("mntmSaveAs");
		mnFile.add(mntmSaveAs);

		rdbtnmntmBeenden = new JRadioButtonMenuItem("Beenden");
		rdbtnmntmBeenden.setName("rdbtnmntmBeenden");
		mnFile.add(rdbtnmntmBeenden);

		sepFileOptionsEnd = new JSeparator();
		sepFileOptionsEnd.setName("septFileOptionsEnd");
		mnFile.add(sepFileOptionsEnd);

		mnNewMenu = new JMenu("Bearbeiten");
		mnNewMenu.setName("mnNewMenu");
		menuBar.add(mnNewMenu);

		mntmClone = new JMenuItem("Duplizieren");
		mntmClone.setName("mntmPaste");
		mnNewMenu.add(mntmClone);

		mnHelpFile = new JMenu("Hilfe");
		mnHelpFile.setName("mnFenster");
		menuBar.add(mnHelpFile);

		mntmUserMan = new JMenuItem("Benutzerhandbuch");
		mntmUserMan.setName("mntmUserMan");
		mnHelpFile.add(mntmUserMan);

		mntmInfo = new JMenuItem("Info");
		mntmInfo.setName("mntmInfo");
		mnHelpFile.add(mntmInfo);

		// Anlegen des Panels für Buttonanordnung
		panelTools = new JPanel();
		panelTools.setName("panelTools");
		jfMainFrame.getContentPane().add(panelTools, "cell 0 0,grow");
		// Layout des Panels für die Buttonverteilung. In dieser Layoutform:
		// ("",Zeilen Vektor, Spalten Vektor)
		panelTools
				.setLayout(new MigLayout(
						"",
						"[39.00px][39.00px][39.00px][39.00px][39.00px][39.00px][39.00px][39.00px][][][][][][][][][][][][][][][][][][grow][][][][][grow]",
						"[43px,grow]"));

		// Anlegen der Buttons in das Panel (panelTools)
		btnNew = new JButton("");
		btnNew.setName("btnNew");
		// Damit wird der Button in das Layout eingefügt, Ausdruck "cell 0 0,
		// grow" bedeutet das
		// Einfügen in die erste Spalte und erste Zeile des Layoutitters. Grow
		// bedeutet
		// das Ausfüllen der Gitterzelle mit diesem Element
		panelTools.add(btnNew, "cell 0 0,grow");
		btnNew.setToolTipText("Neu");
		btnNew.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnNew.setIcon(new ImageIcon(ContainerFrame.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		btnNew.setBounds(6, 6, 19, 19);

		btnOpen = new JButton("");
		btnOpen.setName("btnOpen");
		// Erklärung, siehe oben
		panelTools.add(btnOpen, "cell 1 0,grow");
		btnOpen.setToolTipText("\u00D6ffnen");
		btnOpen.setIcon(new ImageIcon(
				ContainerFrame.class
						.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		btnOpen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		btnSave = new JButton("");
		btnSave.setName("btnSave");
		// Erklärung, siehe oben
		panelTools.add(btnSave, "cell 2 0,grow");
		btnSave.setToolTipText("Speichern");
		btnSave.setIcon(new ImageIcon(ContainerFrame.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		btnSave.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnSave.setBounds(6, 6, 19, 19);

		btnDuplicate = new JButton("");
		btnDuplicate.setName("btnDuplicate");
		// Erklärung, siehe oben
		panelTools.add(btnDuplicate, "cell 4 0, grow");
		btnDuplicate.setToolTipText("Duplizieren");
		btnDuplicate.setIcon(new ImageIcon(ContainerFrame.class
				.getResource("/sun/print/resources/duplex.png")));
		btnDuplicate.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnDuplicate.setBounds(6, 6, 19, 19);

		// Anlegen des Panels für die Frames (Unterfenster)
		jfMainFrame.getContentPane().add(panelFrames, "cell 0 1,grow");
		panelFrames.setLayout(null);
		panelFrames.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		panelFrames.setName("panelFrames");
		// Erzeugen des ersten Frames (InternalFrame)
		// try {
		// panelFrames.add(controller.createNewFrame(++internalFrameNumber,
		// panelFrames));
		// } catch (IllegalAccessException | IllegalArgumentException |
		// InvocationTargetException e2) {
		// e2.printStackTrace();
		// }

		// Listener für alle Elemente (übersichtshalber alle an einem Ort)
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Einfügen des neuen Frames und in den Vordergrund bringen
				try {
					panelFrames.add(controller.createNewFrame(panelFrames));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
				panelFrames.getSelectedFrame().toFront();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Speichern der Framedaten
				controller.save(jfMainFrame);
			}
		});

		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Öffnen eines Panels (mit den Werten aus einer Datei) und in
				// den Vordergrund bringen
				JInternalFrame internalFrame = controller.open(panelFrames);
				if (internalFrame != null) {
					panelFrames.add(internalFrame);
					panelFrames.getSelectedFrame().toFront();
				}
			}
		});

		rdbtnmntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Schliessen des Programms
				controller.close(jfMainFrame);
			}
		});

		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Speichern der Werte eines Frames unter
				controller.saveAs(null, jfMainFrame, panelFrames);
			}
		});

		mntmClone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Duplizieren eines Frames
				controller.duplicateFrame(panelFrames);
			}
		});

		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Öffnen eines Panels (mit den Werten aus einer Datei) und in
				// den Vordergrund bringen
				JInternalFrame internalFrame = controller.open(panelFrames);
				if (internalFrame != null) {
					panelFrames.add(internalFrame);
					panelFrames.getSelectedFrame().toFront();
				}
			}
		});

		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Einfügen des neuen Frames und in den Vordergrund bringen
				try {
					panelFrames.add(controller.createNewFrame(panelFrames));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					e1.printStackTrace();
				}
				panelFrames.getSelectedFrame().toFront();
			}
		});

		mntmUserMan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Öffnen des Benutezrhandbuchs in Form einer PDF Datei mit
				// einem Standardnamen
				try {
					// Projektordner wird zurückgegeben
					Desktop.getDesktop().open(
							new File(configurator.getHelpFileName()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						configurator.getInfoMessage(), "Info",
						JOptionPane.INFORMATION_MESSAGE,
						configurator.getApplicationIcon());
			}
		});

		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Speichern der Werte des Frames in einer Datei
				controller.save(jfMainFrame);
			}
		});

		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Schliessen eines Frames
				controller.closeInternalFrame(panelFrames);
			}
		});

		btnDuplicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Duplizieren eines Frames
				controller.duplicateFrame(panelFrames);
			}
		});

		jfMainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Close application window
				controller.close(jfMainFrame);
			}
		});
		JInternalFrame frame = controller.open(panelFrames,
				configurator.getDefaultFile());
		panelFrames.add(frame);
		panelFrames.getSelectedFrame().toFront();
	}
}
