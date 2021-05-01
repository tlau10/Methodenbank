/*
    Class WerbeBudgetApplication

    Diese Klasse verwaltet die Applikation und gibt das Ergebnis aus
 */

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class WerbeBudgetApplication extends JFrame implements ActionListener {

	/**
	 * Serilizable Version ID for Class Version
	 */
	private static final long serialVersionUID = 1629768147238415734L;
	// statische Variablen zur einfacheren Zuordnung
	public final static int BERECHNEN = 1;
	public final static int FERNSEHEN = 11;
	public final static int RADIO = 12;
	public final static int ZEITSCHRIFT = 13;
	public final static int SONSTIGE = 14;

	// Komponenten der Applikation
	private Menue menue;
	private Indexfeld indexfeld;
	private JButton berechnen;
	private SolverDaten solverDaten;
	private JPanel contentPane;

	// Den Frame konstruieren
	public WerbeBudgetApplication() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Initialisierung der Komponenten
	private void init() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		this.setSize(new Dimension(750, 550));
		this.setTitle("WerbeBudgetOptimierung");

		menue = new Menue(this);
		contentPane.add(menue.getMenuBar(), "North");

		indexfeld = new Indexfeld(this);
		contentPane.add(indexfeld.getJTappedPane(), "Center");

		solverDaten = new SolverDaten();

		berechnen = new JButton("Berechnen >>>");
		berechnen.setActionCommand(String.valueOf(BERECHNEN));
		berechnen.addActionListener(this);
		berechnen.setEnabled(false);
		contentPane.add(berechnen, "South");
	}

	// lscht die aktuellen Solverdaten
	public void resetSolverDaten() {
		solverDaten = new SolverDaten();
		berechnen.setEnabled(false);
	}

	// aktualisiert das Indexfeld
	public void modifyIndexFeld() {
		int status = 0;

		if (solverDaten.getAnzahlFernsehanstalten() > 0) {
			indexfeld.getFernsehanstalten().initMedium();
			indexfeld.setEnabledFernsehen(true);
			status = 1;
		}

		if (solverDaten.getAnzahlRadiosender() > 0) {
			indexfeld.getRadiosender().initMedium();
			indexfeld.setEnabledRadio(true);
			if (status == 0) {
				status = 2;
			}
		}

		if (solverDaten.getAnzahlZeitschriften() > 0) {
			indexfeld.getZeitschriften().initMedium();
			indexfeld.setEnabledZeitschrift(true);
			if (status == 0) {
				status = 3;
			}
		}

		if (solverDaten.getAnzahlSonstigeMedien() > 0) {
			indexfeld.getSonstigeMedien().initMedium();
			indexfeld.setEnabledSonstige(true);
			if (status == 0) {
				status = 4;
			}
		}

		if (status != 0) {
			indexfeld.setSelectedIndex(status);
		}
	}

	// gibt an welches Medium gerade angezeigt werden soll
	public void setIndexfeld(int index) {
		indexfeld.setSelectedIndex(0);
		indexfeld.setSelectedIndex(index - 10);
	}

	public void newMedien() {
		berechnen.setEnabled(false);
		indexfeld.disable();
	}

	// bestimmt nchstes Ereignis
	public void next() {
		if (solverDaten.testKostenSet() == false) {
			berechnen.setEnabled(false);

			if (indexfeld.getEnabled(1) == true
					&& solverDaten.testFernsehKostenSet() == false) {
				indexfeld.setSelectedIndex(1);
			} else if (indexfeld.getEnabled(2) == true
					&& solverDaten.testRadioKostenSet() == false) {
				indexfeld.setSelectedIndex(2);
			} else if (indexfeld.getEnabled(3) == true
					&& solverDaten.testZeitschriftenKostenSet() == false) {
				indexfeld.setSelectedIndex(3);
			} else if (indexfeld.getEnabled(4) == true
					&& solverDaten.testSonstigeKostenSet() == false) {
				indexfeld.setSelectedIndex(4);
			}

			return;
		} else {
			berechnen.setEnabled(true);
		}
	}

	// diese Methode zeigt die Lsung an in einem Frame
	@SuppressWarnings({ "deprecation", "deprecation" })
	public boolean showSolution() {
		boolean status = true;

		File solution = new File("C:/Temp/Solver.out");

		// testet ob die Ausgabedatei vollstndig ist. Falls die
		// Datei gerade generiert wird, wird in Zeitabstnden von 200 ms
		// geprft, ob der Solver fertig ist. Nach 100 erfolglosen
		// Versuchen, also 20 Sekunden wird das Programm abgebrochen
		if (solution.exists()) {
			int counter = 0;
			while (solution.length() == 0 && counter < 100) {
				solution = new File("C:/Temp/Solver.out");
				try {
					Thread.sleep(200);
				} catch (Exception e) {
				}

				counter++;
			}

			if (counter == 100)
				status = false;
		} else {
			JOptionPane.showMessageDialog(null,
					"Kann Datei Solver.out nicht fiden. Exit(1)", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		// Ausgabedatei des Solvers einlesen und Ergebnis anzeigen
		FileReader in = null;
		String dataString = null;
		String lineString = null;
		try {
			in = new FileReader(solution);
			int size = (int) solution.length();
			char[] data = new char[size];
			int chars_read = 0;

			while (chars_read < size) {
				chars_read += in.read(data, chars_read, size - chars_read);
			}

			dataString = new String(data);
			if (dataString.substring(0, 26)
					.equals("This problem is infeasible"))
				status = false;

			if (dataString.substring(0, 25).equals("This problem is unbounded"))
				status = false;
		} catch (Exception ex) {
			status = false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex2) {
			}
		}

		JFrame solFrame = new JFrame("Loesung");
		solFrame.getContentPane().setLayout(new GridLayout());

		JPanel solPanel = new JPanel();
		solPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(2, 2, 2, 2);
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.SOUTHWEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;

		if (status == false) {
			solPanel.add(new JLabel("Das eingegebene Problem ist unlsbar"), c);
		} else {
			if (dataString != null) {
				int pos = 0;
				int temp;
				int[] x = new int[solverDaten.getGesamtAnzahlKategorien()];

				lineString = dataString.substring(dataString.lastIndexOf(' ',
						dataString.indexOf('\n', pos)) + 1, dataString.indexOf(
						'\n', pos) - 1);
				pos = dataString.indexOf('\n', pos) + 1;

				for (int i = 0; i < x.length; i++) {
					lineString = dataString.substring(dataString.indexOf('x',
							pos) + 1, dataString.indexOf(' ', pos));
					temp = Integer.parseInt(lineString);
					lineString = dataString.substring(dataString.lastIndexOf(
							' ', dataString.indexOf('\n', pos)) + 1, dataString
							.indexOf('\n', pos) - 1);
					x[temp - 1] = Integer.parseInt(lineString);
					pos = dataString.indexOf('\n', pos) + 1;
				}

				solverDaten.setSolverOutDaten(x);

				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 1;
				c.gridheight = 1;

				if (solverDaten.getOptimierung() == 0) {
					solPanel
							.add(
									new JLabel(
											"Minimierung des Budgets ergab folgende Loesung: "),
									c);
					c.gridy++;
					solPanel.add(new JLabel(
							"=========================================== "), c);
					c.gridy++;
					solPanel.add(new JLabel(" "), c);
				} else if (solverDaten.getOptimierung() == 1) {
					solPanel
							.add(
									new JLabel(
											"Maximierung der zu erreichenden Kunden ergab folgende Loesung: "),
									c);
					c.gridy++;
					solPanel
							.add(
									new JLabel(
											"========================================================= "),
									c);
					c.gridy++;
					solPanel.add(new JLabel(" "), c);
				}

				c.gridy++;
				solPanel
						.add(
								new JLabel(
										"Maximal zur Verfuegung stehendes Budget zur Verteilung der Werbeeinheiten: "),
								c);
				c.gridx++;
				solPanel.add(
						new JLabel(String.valueOf(solverDaten.getBudget())), c);
				c.gridx--;
				c.gridy++;
				solPanel
						.add(
								new JLabel(
										"Mindestanzahl Kunden, welche durch die Werbeeinheiten erreicht werden sollen: "),
								c);
				c.gridx++;
				solPanel.add(new JLabel(String.valueOf(solverDaten
						.getAnzahlKunden())), c);
				c.gridx--;
				c.gridy++;
				solPanel.add(new JLabel(" "), c);

				int savex = c.gridx;
				int savey = c.gridy;

				JPanel resultPanel = new JPanel();
				resultPanel.setLayout(new GridBagLayout());
				resultPanel.setBorder(new TitledBorder(new MatteBorder(2, 2, 2,
						2, Color.white), "Ergebnis"));

				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 1;
				c.gridheight = 1;
				resultPanel.add(new JLabel(" "), c);

				c.gridy++;
				resultPanel.add(new JLabel("Verwendetes Budget: "), c);
				c.gridx++;
				resultPanel.add(new JLabel(String.valueOf(solverDaten
						.getSolverBudget())), c);
				c.gridx--;
				c.gridy++;
				resultPanel.add(new JLabel("Erreichte Kunden: "), c);
				c.gridx++;
				resultPanel.add(new JLabel(String.valueOf(solverDaten
						.getSolverKunden())), c);
				c.gridy--;

				c.gridx = savex;
				c.gridy = savey + 1;
				savey = c.gridy;
				solPanel.add(resultPanel, c);

				c.gridy++;
				solPanel.add(new JLabel(" "), c);

				savex = c.gridx;
				savey = c.gridy;

				int zaehler = 0;

				JPanel solverPanel = new JPanel();
				solverPanel.setLayout(new GridBagLayout());
				solverPanel.setBorder(new TitledBorder(new EtchedBorder(),
						"Daten fuer optimiertes Budget"));

				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 1;
				c.gridheight = 1;

				if (solverDaten.getAnzahlFernsehanstalten() > 0) {
					solverPanel.add(new JLabel(" "), c);
					c.gridy++;
					solverPanel.add(new JLabel("Fernsehanstalten:"), c);
					c.gridy++;
					solverPanel.add(new JLabel("==============="), c);
					c.gridx++;
					c.gridx++;
					c.gridx++;
					solverPanel.add(new JLabel("    Kosten: "), c);
					c.gridx++;
					solverPanel.add(new JLabel("    Kunden: "), c);
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridy++;

					int index = 0;

					for (int i = 0; i < solverDaten.getAnzahlFernsehanstalten(); i++) {
						index = i + 1;
						int kategorie = 0;

						solverPanel.add(new JLabel("Fernsehanstalt " + index
								+ ": "), c);
						c.gridx++;

						for (int j = 0; j < solverDaten
								.getAnzahlFernsehanstaltenKategorien(index); j++) {
							kategorie++;
							solverPanel.add(new JLabel("Kategorie " + kategorie
									+ ": "), c);
							c.gridx++;
							solverPanel.add(new JLabel("" + x[zaehler]
									+ " Einheit(en)"), c);
							c.anchor = GridBagConstraints.SOUTHEAST;
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten
													.getFernsehanstaltenKosten(
															i, j))), c);
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten
													.getFernsehanstaltenKunden(
															i, j))), c);
							c.gridx--;
							c.gridx--;
							c.anchor = GridBagConstraints.SOUTHWEST;
							zaehler++;
							c.gridx--;
							;
							c.gridy++;
						}
						c.gridx--;
						solverPanel.add(new JLabel("-----"), c);
						c.gridy++;
					}
				}

				if (solverDaten.getAnzahlRadiosender() > 0) {
					solverPanel.add(new JLabel(" "), c);
					c.gridy++;
					solverPanel.add(new JLabel("Radiosender:"), c);
					c.gridy++;
					solverPanel.add(new JLabel("============"), c);
					c.gridx++;
					c.gridx++;
					c.gridx++;
					solverPanel.add(new JLabel("    Kosten: "), c);
					c.gridx++;
					solverPanel.add(new JLabel("    Kunden: "), c);
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridy++;

					int index = 0;

					for (int i = 0; i < solverDaten.getAnzahlRadiosender(); i++) {
						index = i + 1;
						int kategorie = 0;

						solverPanel.add(new JLabel("Radiosender " + index
								+ ": "), c);
						c.gridx++;

						for (int j = 0; j < solverDaten
								.getAnzahlRadiosenderKategorien(index); j++) {
							kategorie++;
							solverPanel.add(new JLabel("Kategorie " + kategorie
									+ ": "), c);
							c.gridx++;
							solverPanel.add(new JLabel("" + x[zaehler]
									+ " Einheit(en)"), c);
							c.anchor = GridBagConstraints.SOUTHEAST;
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten.getRadiosenderKosten(
													i, j))), c);
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten.getRadiosenderKunden(
													i, j))), c);
							c.gridx--;
							c.gridx--;
							c.anchor = GridBagConstraints.SOUTHWEST;
							zaehler++;
							c.gridx--;
							;
							c.gridy++;
						}
						c.gridx--;
						solverPanel.add(new JLabel("-----"), c);
						c.gridy++;
					}
				}

				if (solverDaten.getAnzahlZeitschriften() > 0) {
					solverPanel.add(new JLabel(" "), c);
					c.gridy++;
					solverPanel.add(new JLabel("Zeitschriften:"), c);
					c.gridy++;
					solverPanel.add(new JLabel("=============="), c);
					c.gridx++;
					c.gridx++;
					c.gridx++;
					solverPanel.add(new JLabel("    Kosten: "), c);
					c.gridx++;
					solverPanel.add(new JLabel("    Kunden: "), c);
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridy++;

					int index = 0;

					for (int i = 0; i < solverDaten.getAnzahlZeitschriften(); i++) {
						index = i + 1;
						int kategorie = 0;

						solverPanel.add(new JLabel("Zeitschrift " + index
								+ ": "), c);
						c.gridx++;

						for (int j = 0; j < solverDaten
								.getAnzahlZeitschriftenKategorien(index); j++) {
							kategorie++;
							solverPanel.add(new JLabel("Kategorie " + kategorie
									+ ": "), c);
							c.gridx++;
							solverPanel.add(new JLabel("" + x[zaehler]
									+ " Einheit(en)"), c);
							c.anchor = GridBagConstraints.SOUTHEAST;
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten
													.getZeitschriftenKosten(i,
															j))), c);
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten
													.getZeitschriftenKunden(i,
															j))), c);
							c.gridx--;
							c.gridx--;
							c.anchor = GridBagConstraints.SOUTHWEST;
							zaehler++;
							c.gridx--;
							;
							c.gridy++;
						}
						c.gridx--;
						solverPanel.add(new JLabel("-----"), c);
						c.gridy++;
					}
				}

				if (solverDaten.getAnzahlSonstigeMedien() > 0) {
					solverPanel.add(new JLabel(" "), c);
					c.gridy++;
					solverPanel.add(new JLabel("Sonstige Medien:"), c);
					c.gridy++;
					solverPanel.add(new JLabel("================"), c);
					c.gridx++;
					c.gridx++;
					c.gridx++;
					solverPanel.add(new JLabel("    Kosten: "), c);
					c.gridx++;
					solverPanel.add(new JLabel("    Kunden: "), c);
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridx--;
					c.gridy++;

					int index = 0;

					for (int i = 0; i < solverDaten.getAnzahlSonstigeMedien(); i++) {
						index = i + 1;
						int kategorie = 0;

						solverPanel
								.add(new JLabel("Medium " + index + ": "), c);
						c.gridx++;

						for (int j = 0; j < solverDaten
								.getAnzahlSonstigeMedienKategorien(index); j++) {
							kategorie++;
							solverPanel.add(new JLabel("Kategorie " + kategorie
									+ ": "), c);
							c.gridx++;
							solverPanel.add(new JLabel("" + x[zaehler]
									+ " Einheit(en)"), c);
							c.anchor = GridBagConstraints.SOUTHEAST;
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten
													.getSonstigeMedienKosten(i,
															j))), c);
							c.gridx++;
							solverPanel.add(new JLabel("    "
									+ String.valueOf(x[zaehler]
											* solverDaten
													.getSonstigeMedienKunden(i,
															j))), c);
							c.gridx--;
							c.gridx--;
							c.anchor = GridBagConstraints.SOUTHWEST;
							zaehler++;
							c.gridx--;
							;
							c.gridy++;
						}
						c.gridx--;
						solverPanel.add(new JLabel("-----"), c);
						c.gridy++;
					}
				}

				c.gridx = savex;
				c.gridy = savey + 1;
				savey = c.gridy;
				solPanel.add(solverPanel, c);
			}
		}

		JScrollPane scrollpane = new JScrollPane(solPanel);
		scrollpane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		solFrame.getContentPane().add(scrollpane);

		if (status == false) {
			solFrame.setSize(300, 100);
		} else {
			solFrame.pack();
		}

		solFrame.show();

		return true;
	}

	public void neu() {
		indexfeld.setSelectedIndex(0);
		indexfeld.disable();
		indexfeld.getGrunddaten().resetInputs();
		indexfeld.getFernsehanstalten().resetInputs();
		indexfeld.getZeitschriften().resetInputs();
		indexfeld.getRadiosender().resetInputs();
		indexfeld.getSonstigeMedien().resetInputs();
		solverDaten = new SolverDaten();
		berechnen.setEnabled(false);
	}

	@SuppressWarnings("deprecation")
	public void sichern() {

		FileDialog f = new FileDialog(this, "Daten speichern", FileDialog.SAVE);
		f.setFile("*.wbo");

		f.setFilenameFilter(new java.io.FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (dir.getName().endsWith(".wbo")) {
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
				out.writeObject(this.getSolverDaten());
				out.flush();
				out.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Bitte einen gültigen Dateinamen eingeben!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void verlassen() {
		System.exit(0);
	}

	@SuppressWarnings("deprecation")
	public void oeffnen() {
		FileDialog f = new FileDialog(this, "Datei Oeffnen", FileDialog.LOAD);
		f.setFile("*.wbo");

		f.setFilenameFilter(new java.io.FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (dir.getName().endsWith(".wbo")) {
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
				SolverDaten tmpdata = (SolverDaten) in.readObject();
				in.close();
				this.setInputs(tmpdata);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Bitte einen gültigen Dateinamen eingeben!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// folgende Methoden sollen die Ereignisse des Menues implementierne
	// wurde wegen Zeitmangels nicht realisiert und sollte bei Erweiterung
	// des Programmes realisiert werden
	public void schliessen() {
	}

	public void kopieren() {
	}

	public void ausschneiden() {
	}

	public void einfuegen() {
	}

	public void hilfe() {
	}

	// gibt Indexfeld Zurck
	public Indexfeld getIndexfeld() {
		return indexfeld;
	}

	// gibt die SolverDaten zurck
	public SolverDaten getSolverDaten() {
		return solverDaten;
	}

	// Aktion Datei | Beenden durchgefhrt
	public void jMenuFileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	// berschrieben, so dass eine Beendigung beim Schlieen des Fensters mglich
	// ist.
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			jMenuFileExit_actionPerformed(null);
		}
	}

	// Verarbeiten der Ereignisse
	public void actionPerformed(ActionEvent e) {
		indexfeld.setSelectedIndex(0);

		indexfeld.getGrunddaten().saveOptions();

		if (Integer.parseInt(e.getActionCommand()) == BERECHNEN) {
			if (solverDaten.testKostenSet() == false) {
				JOptionPane
						.showMessageDialog(
								null,
								"Es wurden nicht alle Kosten fr die Werbeeinheiten eingegeben!",
								"Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			solverDaten.setSolverInput();
			showSolution();
		}
	}

	private void setInputs(SolverDaten tmpdata) {
		indexfeld.setSelectedIndex(0);
		indexfeld.disable();
		if (!indexfeld.getGrunddaten().setInputs(tmpdata))
			return;

		if (tmpdata.getAnzahlFernsehanstalten() > 0) {
			indexfeld.getFernsehanstalten().setInputs(tmpdata);
		}
		if (tmpdata.getAnzahlZeitschriften() > 0) {
			indexfeld.getZeitschriften().setInputs(tmpdata);
		}
		if (tmpdata.getAnzahlRadiosender() > 0) {
			indexfeld.getRadiosender().setInputs(tmpdata);
		}
		if (tmpdata.getAnzahlSonstigeMedien() > 0) {
			indexfeld.getSonstigeMedien().setInputs(tmpdata);
		}

	}
}
