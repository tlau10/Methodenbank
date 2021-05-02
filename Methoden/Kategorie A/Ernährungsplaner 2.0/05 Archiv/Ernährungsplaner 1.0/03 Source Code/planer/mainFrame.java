package planer;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
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
import javax.swing.border.TitledBorder;

public class mainFrame extends JFrame {
	int kalorien_;
	JPanel contentPane;
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenuFile = new JMenu();
	JMenuItem jMenuFileExit = new JMenuItem();
	JLabel statusBar = new JLabel();
	BorderLayout borderLayout1 = new BorderLayout();
	TitledBorder titledBorder1;
	Vector allEatables_;
	ApplicationDiaetplaner ap_;
	JPanel myJPanelEingabeWerte;
	JMenu jMenuLebensmittel = new JMenu();
	JMenuItem jMenuItemLebensmittelhinzufuegen = new JMenuItem();
	JMenuItem jMenuItemLebensmittelloeschen = new JMenuItem();

	JMenuItem jMenuItemLebensmittelLaden = new JMenuItem();
	JMenuItem jMenuItemLebensmittelSpeichern = new JMenuItem();

	JMenu jMenuMatrix = new JMenu();
	JCheckBoxMenuItem jCheckBoxMenuItemMatrixAnzeigen = new JCheckBoxMenuItem();
	JMenuItem jMenuHelpAbout = new JMenuItem();
	JMenu jMenuHelp = new JMenu();

	// Construct the frame
	public mainFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public mainFrame(Vector allEatables, ApplicationDiaetplaner ap) {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {

			allEatables_ = allEatables;
			ap_ = ap;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closechild(int jPanel, JPanel target, int kalorien,
			Vector resultVector) {
		contentPane.remove(target);
		if (jPanel == 1)// Weiter zu 2
		{
			kalorien_ = kalorien;
			myJPanelEingabeWerte = new JPanelAuswahl(allEatables_, ap_,
					kalorien_, this);
			// @TODO
			jMenuLebensmittel.setEnabled(false);
			this.repaint();
			contentPane.add(myJPanelEingabeWerte, BorderLayout.CENTER, 1);
			this.validate();
		}
		if (jPanel == 2)// Weiter zu 3
		{
			JPanelAusgabe myJPanelAusgabe = new JPanelAusgabe(resultVector, ap_
					.getMenuCalories(), kalorien_, this);
			contentPane.add(myJPanelAusgabe, BorderLayout.CENTER, 1);
			this.validate();
		}
		if ((jPanel == 3) || (jPanel == 4) || (jPanel == 5))// Zurück
		{
			jMenuLebensmittel.setEnabled(true);
			myJPanelEingabeWerte = new JPanelWerte(this);
			contentPane.add(myJPanelEingabeWerte, BorderLayout.CENTER);
			this.validate();
		}
		this.repaint();
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		titledBorder1 = new TitledBorder("");
		contentPane.setLayout(borderLayout1);
		this.getContentPane().setBackground(SystemColor.control);
		this.setSize(new Dimension(800, 600));
		this.setTitle("Ernährungsplaner 1.0");
		statusBar.setText(" ");
		jMenuFile.setText("Ernährungsplan");
		jMenuFileExit.setText("Exit");
		jMenuFileExit
				.addActionListener(new mainFrame_jMenuFileExit_ActionAdapter(
						this));
		contentPane.setMaximumSize(new Dimension(2147483647, 2147483647));
		contentPane.setMinimumSize(new Dimension(640, 480));
		contentPane.setPreferredSize(new Dimension(640, 180));
		jMenuLebensmittel.setAlignmentX((float) 0.5);
		jMenuLebensmittel.setToolTipText("");
		jMenuLebensmittel.setText("Lebensmittel");
		jMenuItemLebensmittelLaden.setText("Lebensmittel-Liste importieren");
		jMenuItemLebensmittelSpeichern
				.setText("Lebensmittel-Liste exportieren");
		jMenuItemLebensmittelLaden
				.addActionListener(new mainFrame_jMenuItemLebensmittelLaden_actionAdapter(
						this));
		jMenuItemLebensmittelSpeichern
				.addActionListener(new mainFrame_jMenuItemLebensmittelSpeichern_actionAdapter(
						this));

		jMenuItemLebensmittelhinzufuegen.setToolTipText("");
		jMenuItemLebensmittelhinzufuegen.setText("hinzufügen");
		jMenuItemLebensmittelhinzufuegen
				.addActionListener(new mainFrame_jMenuItemLebensmittelhinzufuegen_actionAdapter(
						this));
		jMenuItemLebensmittelloeschen.setVerifyInputWhenFocusTarget(true);
		jMenuItemLebensmittelloeschen.setActionCommand("");
		jMenuItemLebensmittelloeschen.setText("loeschen");
		jMenuItemLebensmittelloeschen
				.addActionListener(new mainFrame_jMenuItemLebensmittelloeschen_actionAdapter(
						this));
		jMenuMatrix.setVerifyInputWhenFocusTarget(true);
		jMenuMatrix.setText("Matrix");
		// jMenuItemMatrixAnzeigen.setMnemonic('0');
		jCheckBoxMenuItemMatrixAnzeigen.setEnabled(true);
		jCheckBoxMenuItemMatrixAnzeigen.setArmed(false);
		ap_.setPrintMatrix(false);
		jCheckBoxMenuItemMatrixAnzeigen.setText("Anzeigen");
		jCheckBoxMenuItemMatrixAnzeigen
				.addActionListener(new mainFrame_jCheckBoxMenuItemMatrixAnzeigen_actionAdapter(
						this));
		jMenuHelpAbout.setText("About");
		jMenuHelpAbout
				.addActionListener(new mainFrame_jMenuHelpAbout_actionAdapter(
						this));
		jMenuHelp.setText("Help");
		jMenuFile.add(jMenuFileExit);
		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuLebensmittel);
		jMenuBar1.add(jMenuMatrix);
		jMenuBar1.add(jMenuHelp);

		contentPane.add(statusBar, BorderLayout.SOUTH);
		jMenuLebensmittel.add(jMenuItemLebensmittelLaden);
		jMenuLebensmittel.add(jMenuItemLebensmittelSpeichern);
		jMenuLebensmittel.add(jMenuItemLebensmittelhinzufuegen);
		jMenuLebensmittel.add(jMenuItemLebensmittelloeschen);
		jMenuHelp.add(jMenuHelpAbout);
		this.setJMenuBar(jMenuBar1);
		jMenuMatrix.add(jCheckBoxMenuItemMatrixAnzeigen);

		// soll sofort sichbart sein TODO
		myJPanelEingabeWerte = new JPanelWerte(this);
		contentPane.add(myJPanelEingabeWerte, BorderLayout.CENTER);
		this.validate();

	}

	// File | Exit action performed
	public void jMenuFileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	// Help | About action performed
	// Overridden so we can exit when window is closed
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuFileExit_actionPerformed(null);
		}
	}

	void jCheckBoxMenuItemMatrixAnzeigen_actionPerformed(ActionEvent e) {

		if (jCheckBoxMenuItemMatrixAnzeigen.getState()) {
			jCheckBoxMenuItemMatrixAnzeigen.setArmed(false);
			ap_.setPrintMatrix(true);
		} else {
			jCheckBoxMenuItemMatrixAnzeigen.setArmed(true);
			ap_.setPrintMatrix(false);
		}

		// dein Einsatz Steffen.

	}

	void jMenuHelpAbout_actionPerformed(ActionEvent e) {
		JHelp helpViewer = null;
		try {
			// Hauptfenster in der nächsten Zeile ersetzen durch aktuellen
			// Klassennamen
			ClassLoader cl = mainFrame.class.getClassLoader();
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
		frame.setTitle("Hilfe zu Ernährungsplaner 1.0");
		frame.setSize(800, 600);
		frame.getContentPane().add(helpViewer);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}

	// @TODO
	void jMenuItemLebensmittelLaden_actionPerformed(ActionEvent e) {
		try {
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
					ap_.readFromXmlFile(filename);
					allEatables_ = ap_.getAllEatables();
					JOptionPane.showMessageDialog(null,
							"Lebensmittelliste importiert!", "Hinweis",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (DiaetplanerException ex) {
				JOptionPane.showMessageDialog(null,
						"Import fehlgeschlagen. Gültige Datei ausgewählt?",
						"Hinweis", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (HeadlessException ex1) {
			ex1.printStackTrace();
		}

	}

	// @TODO
	void jMenuItemLebensmittelSpeichern_actionPerformed(ActionEvent e) {
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
				ap_.saveToXmlFile(filename);
				JOptionPane.showMessageDialog(null, "Export abgeschlossen!",
						"Hinweis", JOptionPane.INFORMATION_MESSAGE);
			} catch (DiaetplanerException ex) {
				JOptionPane.showMessageDialog(null, "Export fehlgeschlagen.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}

	void jMenuItemLebensmittelhinzufuegen_actionPerformed(ActionEvent e) {
		// @TODO
		contentPane.remove(myJPanelEingabeWerte);
		jMenuLebensmittel.setEnabled(false);
		JPanelLebensmittelhinzufuegen myJPanelLebensmittelhinzufuegen = new JPanelLebensmittelhinzufuegen(
				this, ap_, allEatables_);
		contentPane
				.add(myJPanelLebensmittelhinzufuegen, BorderLayout.CENTER, 1);
		this.validate();

	}

	void jMenuItemLebensmittelloeschen_actionPerformed(ActionEvent e) {
		contentPane.remove(myJPanelEingabeWerte);
		jMenuLebensmittel.setEnabled(false);
		JPanelLebensmittelloeschen myJPanelLebensmittelloeschen = new JPanelLebensmittelloeschen(
				this, ap_, allEatables_);
		contentPane.add(myJPanelLebensmittelloeschen, BorderLayout.CENTER, 1);
		this.validate();
	}

}

class mainFrame_jMenuFileExit_ActionAdapter implements
		java.awt.event.ActionListener {
	mainFrame adaptee;

	mainFrame_jMenuFileExit_ActionAdapter(mainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuFileExit_actionPerformed(e);
	}
}

class mainFrame_jCheckBoxMenuItemMatrixAnzeigen_actionAdapter implements
		java.awt.event.ActionListener {
	mainFrame adaptee;

	mainFrame_jCheckBoxMenuItemMatrixAnzeigen_actionAdapter(mainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jCheckBoxMenuItemMatrixAnzeigen_actionPerformed(e);
	}
}

class mainFrame_jMenuHelpAbout_actionAdapter implements
		java.awt.event.ActionListener {
	mainFrame adaptee;

	mainFrame_jMenuHelpAbout_actionAdapter(mainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuHelpAbout_actionPerformed(e);
	}
}

class mainFrame_jMenuItemLebensmittelLaden_actionAdapter implements
		java.awt.event.ActionListener {
	mainFrame adaptee;

	mainFrame_jMenuItemLebensmittelLaden_actionAdapter(mainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemLebensmittelLaden_actionPerformed(e);
	}
}

class mainFrame_jMenuItemLebensmittelSpeichern_actionAdapter implements
		java.awt.event.ActionListener {
	mainFrame adaptee;

	mainFrame_jMenuItemLebensmittelSpeichern_actionAdapter(mainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemLebensmittelSpeichern_actionPerformed(e);
	}
}

class mainFrame_jMenuItemLebensmittelhinzufuegen_actionAdapter implements
		java.awt.event.ActionListener {
	mainFrame adaptee;

	mainFrame_jMenuItemLebensmittelhinzufuegen_actionAdapter(mainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemLebensmittelhinzufuegen_actionPerformed(e);
	}
}

class mainFrame_jMenuItemLebensmittelloeschen_actionAdapter implements
		java.awt.event.ActionListener {
	mainFrame adaptee;

	mainFrame_jMenuItemLebensmittelloeschen_actionAdapter(mainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemLebensmittelloeschen_actionPerformed(e);
	}
}
