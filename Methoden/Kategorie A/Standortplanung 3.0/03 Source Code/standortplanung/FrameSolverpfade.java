package standortplanung;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>
 * Überschrift: Standortplanung
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author Manuel Thoma, Markus Klemens
 * @version 3.0
 */

public class FrameSolverpfade extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfLPSolve;
	private JTextField tfXA;
	private JTextField tfMOPS;
	private JTextField tfTEMP;
	private File dateiLPSolve;
	private File dateiXA;
	private File dateiMOPS;
	private File dateiTEMP;
	private String datei = new String();
	private String pfad = new String();

	/**
	 * Launch the application.
	 */
	public void createFrameSolverpfade() {
		try {
			FrameSolverpfade frame = new FrameSolverpfade();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FrameSolverpfade() throws IOException {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Einstellungen der Pfade");
		setBounds(100, 100, 434, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfLPSolve = new JTextField();
		tfLPSolve.setBounds(27, 63, 322, 20);
		contentPane.add(tfLPSolve);
		tfLPSolve.setColumns(10);

		tfXA = new JTextField();
		tfXA.setVisible(false);
		tfXA.setBounds(348, 252, 56, 20);
		contentPane.add(tfXA);
		tfXA.setColumns(10);

		tfMOPS = new JTextField();
		tfMOPS.setBounds(27, 109, 322, 20);
		contentPane.add(tfMOPS);
		tfMOPS.setColumns(10);

		tfTEMP = new JTextField();
		tfTEMP.setColumns(10);
		tfTEMP.setBounds(27, 157, 322, 20);
		contentPane.add(tfTEMP);

		// Textfelder mit dem momentanen Solverpfad
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					".\\solverpath.txt"));
			datei = IniLaden.laden("solverpath.txt");
			for (int i = 0; i < 4; i++) {
				if (i == 0) { 
					pfad = IniLaden.tokenize(datei, "LP-Solve");
					tfLPSolve.setText(pfad);
					continue;
				}
				if (i == 1) {
					pfad = IniLaden.tokenize(datei, "XA");
					tfXA.setText(pfad);
					continue;
				}
				if (i == 2) {
					pfad = IniLaden.tokenize(datei, "MOPS");
					tfMOPS.setText(pfad);
					continue;
				}

				if (i == 3) {
					pfad = IniLaden.tokenize(datei, "TEMP");
					tfTEMP.setText(pfad);
					continue;
				}
				// unnötige Zeilen, die aber aus dem File gelesen werden müssen
				// br.readLine();
				continue;
			}
			br.close();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null,
					"Überprüfen Sie die Solverpfade auf Fehler oder fehlende solverpath-Datei", "Achtung",
					JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
		}

		// Label LP-Solve
		JLabel labelLpsolvePfad = new JLabel("LP-Solve Pfad:");
		labelLpsolvePfad.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelLpsolvePfad.setBounds(27, 48, 155, 14);
		contentPane.add(labelLpsolvePfad);

		// Label XA
		JLabel labelXaPfad = new JLabel("XA Pfad:");
		labelXaPfad.setVisible(false);
		labelXaPfad.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelXaPfad.setBounds(338, 254, 80, 14);
		contentPane.add(labelXaPfad);

		// Label Mops
		JLabel labelMopsPfad = new JLabel("MOPS Pfad:");
		labelMopsPfad.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelMopsPfad.setBounds(27, 94, 118, 14);
		contentPane.add(labelMopsPfad);

		// Label Arbeitsverzeichnis
		JLabel lblArbeitsverzeichnis = new JLabel("Arbeitsverzeichnis:");
		lblArbeitsverzeichnis.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblArbeitsverzeichnis.setBounds(27, 140, 118, 14);
		contentPane.add(lblArbeitsverzeichnis);

		// Button Speichern
		JButton buttonSpeichern = new JButton("Speichern");
		buttonSpeichern.setFont(new Font("Tahoma", Font.PLAIN, 10));
		buttonSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(
							".\\solverpath.txt"));
					bw.write("LP-Solve:" + tfLPSolve.getText() + ";");
					bw.newLine();
					bw.write("XA:" + tfXA.getText() + ";");
					bw.newLine();
					bw.write("MOPS:" + tfMOPS.getText() + ";");
					bw.newLine();
					bw.write("TEMP:" + tfTEMP.getText() + ";");
					bw.close();
					dispose();
				} catch (IOException e) {
					System.out.println("Fehler");
					e.printStackTrace();
				}
			}
		});
		buttonSpeichern.setBounds(27, 188, 89, 23);
		contentPane.add(buttonSpeichern);

		// Button Abbrechen
		JButton btnAbrechen = new JButton("Abbrechen");
		btnAbrechen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAbrechen.setBounds(126, 188, 89, 23);
		contentPane.add(btnAbrechen);

		// Label Header
		JLabel labelÜberschrift = new JLabel(
				"Bitte geben Sie hier die richtigen Pfade ein:");
		labelÜberschrift.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelÜberschrift.setBounds(27, 15, 350, 14);
		contentPane.add(labelÜberschrift);

		// Pfadbutton LP-Solve
		JButton lpsolvePfadButton = new JButton("...");
		lpsolvePfadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser oeffnen = new JFileChooser(".");

				// Filter, dass nur Textdateien angezeigt werden
				oeffnen.setAcceptAllFileFilterUsed(false); // Alle-Dateien zur
															// Auswahl
															// ausblenden
				oeffnen.setFileFilter(new FileFilter() {
					public boolean accept(File file) {
						return file.isDirectory()
								|| file.getName().toLowerCase()
										.endsWith(".exe");
					}

					public String getDescription() {
						return "Anwendung (.exe)";
					}
				});

				int rückgabewert = oeffnen.showOpenDialog(null);
				if (rückgabewert == JFileChooser.APPROVE_OPTION) {
					dateiLPSolve = oeffnen.getSelectedFile();
					tfLPSolve.setText(dateiLPSolve.toString());
				}
				if (rückgabewert == JFileChooser.CANCEL_OPTION) {
					// Abbrechen wurde gedrückt
				}
			}
		});
		lpsolvePfadButton.setBounds(359, 62, 38, 23);
		contentPane.add(lpsolvePfadButton);

		// Pfadbutton XA
		JButton xaPfadButton = new JButton("...");
		xaPfadButton.setVisible(false);
		xaPfadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser oeffnen = new JFileChooser(".");

				// Filter, dass nur Textdateien angezeigt werden
				oeffnen.setAcceptAllFileFilterUsed(false); // Alle-Dateien zur
															// Auswahl
															// ausblenden
				oeffnen.setFileFilter(new FileFilter() {
					public boolean accept(File file) {
						return file.isDirectory()
								|| file.getName().toLowerCase()
										.endsWith(".exe");
					}

					public String getDescription() {
						return "Anwendung (.exe)";
					}
				});

				int rückgabewert = oeffnen.showOpenDialog(null);
				if (rückgabewert == JFileChooser.APPROVE_OPTION) {
					dateiXA = oeffnen.getSelectedFile();
					tfXA.setText(dateiXA.toString());
				}
				if (rückgabewert == JFileChooser.CANCEL_OPTION) {
					// Abbrechen wurde gedrückt
				}
			}
		});
		xaPfadButton.setBounds(338, 251, 38, 23);
		contentPane.add(xaPfadButton);

		// Pfadbutton MOPS
		JButton mopsPfadButton = new JButton("...");
		mopsPfadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser oeffnen = new JFileChooser(".");

				// Filter, dass nur Textdateien angezeigt werden
				oeffnen.setAcceptAllFileFilterUsed(false); // Alle-Dateien zur
															// Auswahl
															// ausblenden
				oeffnen.setFileFilter(new FileFilter() {
					public boolean accept(File file) {
						return file.isDirectory()
								|| file.getName().toLowerCase()
										.endsWith(".exe");
					}

					public String getDescription() {
						return "Anwendung (.exe)";
					}
				});

				int rückgabewert = oeffnen.showOpenDialog(null);
				if (rückgabewert == JFileChooser.APPROVE_OPTION) {
					dateiMOPS = oeffnen.getSelectedFile();
					tfMOPS.setText(dateiMOPS.toString());
				}
				if (rückgabewert == JFileChooser.CANCEL_OPTION) {
					// Abbrechen wurde gedrückt
				}
			}
		});
		mopsPfadButton.setBounds(359, 108, 38, 23);
		contentPane.add(mopsPfadButton);

		// Pfad Arbeitsverzeichnis
		JButton tempPfadButton = new JButton("...");
		tempPfadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser oeffnen = new JFileChooser();

				// Filter, dass nur Verzeichnisse ausgewaehlt werden koennen
				oeffnen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				oeffnen.setFileFilter(new FileFilter() {
					public boolean accept(File file) {
						return file.isDirectory();
					}

					public String getDescription() {
						return "Verzeichnis";
					}
				});

				int rückgabewert = oeffnen.showOpenDialog(null);
				if (rückgabewert == JFileChooser.APPROVE_OPTION) {
					dateiTEMP = oeffnen.getSelectedFile();
					tfTEMP.setText(dateiTEMP.toString());
				}
				if (rückgabewert == JFileChooser.CANCEL_OPTION) {
					// Abbrechen wurde gedrückt
				}
			}
		});
		tempPfadButton.setBounds(359, 156, 38, 23);
		contentPane.add(tempPfadButton);

		// Frame zentriert ausrichten
		setLocationRelativeTo(null);
	}
	
	public JTextField getTfTEMP() {
		return tfTEMP;
	}

	public void setTfTEMP(JTextField tfTEMP) {
		this.tfTEMP = tfTEMP;
	}
}
