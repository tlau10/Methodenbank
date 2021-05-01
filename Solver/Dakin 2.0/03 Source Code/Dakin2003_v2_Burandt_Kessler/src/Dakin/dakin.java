package Dakin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

//import org.omg.CORBA.PUBLIC_MEMBER;

//import java.awt.event.*;
import java.io.*;
import java.net.URI;

class ActionPfadSolverPfad implements ActionListener {
	private EingabeTabelle eingabeFenster;

	// Konstruktor
	public ActionPfadSolverPfad(JPanel d) {
		eingabeFenster = (EingabeTabelle) d;
	}

	public void actionPerformed(ActionEvent e) {
		if (eingabeFenster.solverPfad.equals("")) {
			eingabeFenster.solverPfad = ".";
		}
		JFileChooser fileChooser = new JFileChooser(eingabeFenster.solverPfad);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Solver Pfad setzen");

		// ////////////////////////////////////////////////////////////
		// NEU SS13 --> nur lp_solve.exe-Dateien anzeigen.
		// Damit NUR der richtige Solver ausgewählt werden kann.
		// ////////////////////////////////////////////////////////////
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith("lp_solve.exe")
						|| f.isDirectory();
			}

			public String getDescription() {
				return "LP-Solve(lp_solve.exe)";
			}
		});

		// "Alle Dateien" Filter deaktivieren
		fileChooser.setAcceptAllFileFilterUsed(false);

		// ///////////////////////////////////////////////////////////

		if (fileChooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
			try {
				eingabeFenster.solverPfad = fileChooser.getCurrentDirectory()
						.toString() + "\\";
				eingabeFenster.updateLblSolverPfad();
				System.out.println(eingabeFenster.solverPfad);
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null,
						"Ein Dateifehler ist aufgetaucht",
						"Fehler beim Pfad setzen", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

class ActionDateiOeffnen implements ActionListener {
	// //////////////////////////////////////////////////////
	// SS13 ==> Ab jetzt funktioniert die Darstellung in Tabs
	// Diese Codes können gelöscht werden.
	// private JDesktopPane Desktop;

	public ActionDateiOeffnen(JPanel ef, JFrame haupt) {
		eingabeTabelle = (EingabeTabelle) ef;
		// //////////////////////////////////////////////////////
		// SS13 ==> Ab jetzt funktioniert die Darstellung in Tabs
		// Diese Codes können gelöscht werden.
		// Desktop = d;
		// //////////////////////////////////////////////////////
		
		// //////////////////////////////////////////////////////
		// SS13 ==> NEU: für den Ansichtswechsel in den Tabs!!!
		// //////////////////////////////////////////////////////
		hauptfenster = (dakin) haupt;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser("../daten");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Datei Oeffnen");

		if (fileChooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
			try {
				File datei = new File(fileChooser.getSelectedFile().toString());
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(datei)));

				int i, rows, cols;

				rows = Integer.valueOf(br.readLine().substring(5)).intValue();
				cols = Integer.valueOf(br.readLine().substring(5)).intValue();

				eingabeTabelle.anzRowsTextField.setText(String.valueOf(rows));
				eingabeTabelle.anzColsTextField.setText(String.valueOf(cols));

				eingabeTabelle.anzRowsTextField.postActionEvent();
				eingabeTabelle.anzColsTextField.postActionEvent();

				for (int r = 0; r < eingabeTabelle.dm.datenMatrix.getNumRows(); r++)
					for (int c = 0; c < eingabeTabelle.dm.datenMatrix
							.getNumCols(); c++)
						eingabeTabelle.dm.datenMatrix.setValueAt(r, c, br
								.readLine().substring(5));

				br.close();

				// setzen der Min/Max RadioButtons
				if (((String) eingabeTabelle.dm.datenMatrix.getValueAt(0,
						eingabeTabelle.dm.datenMatrix.getNumCols() - 1))
						.equals((String) "max."))
					eingabeTabelle.rbMax.setSelected(true);
				else
					eingabeTabelle.rbMin.setSelected(true);

				eingabeTabelle.updateTable();

				// /////////////////////////////////////////////////////////////////
				// SS13 ==> In Tabs auf die EingabeTabelle springen.
				// Die restlichen Reiter werden deaktiviert, bis die erste
				// Berechnung erfolgt ist.
				// /////////////////////////////////////////////////////////////////
				hauptfenster.tabAnsichtWechseln(0);
				hauptfenster.tabEnableAt(1, false);
				hauptfenster.tabEnableAt(2, false);
				
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null,
						"Ein Dateifehler ist aufgetaucht",
						"Fehler beim Oeffnen", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// //////////////////////////////////////////////////////////////////
	// SS13 ==> Kurz geänderter Code für die alte Darstellung.
	// Kann entfernt werden, da nun immer auf das richtige "Tab" 
	// gesprungen wird.
	// ///////////////////////////////////////////////

	/*
	 * public void EingabeTabelleOeffnen() { JInternalFrame[] allFrames =
	 * Desktop.getAllFrames();
	 * 
	 * try { int frameNum = 0;
	 * 
	 * if (allFrames[1].getTitle() == "Eingabe") frameNum = 1; if
	 * (allFrames[2].getTitle() == "Eingabe") frameNum = 2;
	 * 
	 * allFrames[frameNum].setIcon(false); allFrames[frameNum].show(); } catch
	 * (Exception exception) { }
	 * 
	 * }
	 */
	private EingabeTabelle eingabeTabelle;
	private dakin hauptfenster;
}

class ActionDateiSpeichern implements ActionListener {

	public ActionDateiSpeichern(JPanel ef) {
		eingabeTabelle = (EingabeTabelle) ef;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Datei Speichern");

		// ////////////////////////////////////////////////////////////
		// NEU SS13 --> nur als .exe oder .dat-Dateien speichern!!!
		// Zur Übersicht, damit nicht irgendeine Endung benutzt werden kann
		// ////////////////////////////////////////////////////////////
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".dat")
						|| f.getName().toLowerCase().endsWith(".txt")
						|| f.isDirectory();
			}

			public String getDescription() {
				return "LP-Solve(*.dat/ *.txt)";
			}
		});

		// "Alle Dateien" Filter deaktivieren
		fileChooser.setAcceptAllFileFilterUsed(false);

		// ///////////////////////////////////////////////////////////

		if (fileChooser.showSaveDialog(null) != JFileChooser.CANCEL_OPTION) {
			try {
				File datei = new File(fileChooser.getSelectedFile().toString());
				FileOutputStream fos = new FileOutputStream(datei);

				fos.write(((String) "Rows:").getBytes());
				fos.write(String.valueOf(
						eingabeTabelle.dm.datenMatrix.getNumRows()).getBytes());
				fos.write(((String) "\n").getBytes());

				fos.write(((String) "Cols:").getBytes());
				fos.write(String.valueOf(
						eingabeTabelle.dm.datenMatrix.getNumCols()).getBytes());
				fos.write(((String) "\n").getBytes());

				for (int r = 0; r < eingabeTabelle.dm.datenMatrix.getNumRows(); r++)
					for (int c = 0; c < eingabeTabelle.dm.datenMatrix
							.getNumCols(); c++) {
						fos.write(((String) "r").getBytes());
						fos.write(String.valueOf(r).getBytes());
						fos.write(((String) "c").getBytes());
						fos.write(String.valueOf(c).getBytes());
						fos.write(((String) ":").getBytes());
						fos.write(eingabeTabelle.dm.datenMatrix
								.getValueAt(r, c).getBytes());
						fos.write(((String) "\n").getBytes());
					}

				fos.close();
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null,
						"Ein Dateifehler ist aufgetaucht",
						"Fehler beim Schreiben", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private EingabeTabelle eingabeTabelle;
}

class ActionDateiBeenden implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}

class ActionInfoEntwickler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		JOptionPane
				.showMessageDialog(
						null,
						"FH Konstanz\nSommersemester 2000\n\nAndreas Gossmann\nRainer Faller\nHenrik Feidner\nHarald Woelfle\n\nMigration auf Windows 7 - SS13:\n- Überarbeitung der Benutzeroberfläche\n- Aktualisierung der Visualisierungsbibliothek JFreeChart (1.0.14)\n- Beheben von Bugs\n- Lauffähigkeit auf Windows 7 ermöglichen\nStephan Keßler\nBjörn Burandt",
						"Info", JOptionPane.PLAIN_MESSAGE, null);
	}
}

//////////////////////////////////////////////////////////////
// NEU SS13 --> Als Hilfe eine HTML-Datei, die im Webbrowser geladen werden kann
//////////////////////////////////////////////////////////////
class Hilfe implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		// Desktopobjekt holen
		Desktop desktop = Desktop.getDesktop();
		// Adresse mit Standardbrowser anzeigen
		URI uri;
		try {
			uri = new URI("Programmhilfe_Dakin2003.html");
			desktop.browse(uri);
		} catch (Exception oError) {
			JOptionPane.showMessageDialog(null, oError, "Info",
					JOptionPane.PLAIN_MESSAGE, null);
		}
	}
}

// /////////////////////////////////////////////////////////////////////////////
// SS13 ==> Funktionen der alten Anzeigefenster werden entfernt, wird nun 
// alles in Tabs gehandhabt.
///////////////////////////////////////////////////////////////////////////////

/*
 * class ActionFensterAusgabefenster implements ActionListener { public
 * ActionFensterAusgabefenster(JDesktopPane d) { Desktop = d; }
 * 
 * public void actionPerformed(ActionEvent e) { JInternalFrame[] allFrames =
 * Desktop.getAllFrames();
 * 
 * try { int frameNum = 0;
 * 
 * if (allFrames[1].getTitle() == "Ausgabe") frameNum = 1; if
 * (allFrames[2].getTitle() == "Ausgabe") frameNum = 2;
 * 
 * allFrames[frameNum].setIcon(false); allFrames[frameNum].show(); } catch
 * (Exception exception) { } }
 * 
 * private JDesktopPane Desktop; }
 */

// /////////////////////////////////////////////////////////////////////////////
// SS13 ==> In Tabs!!!
// /////////////////////

/*
 * class ActionFensterVisualisiereFenster implements ActionListener { public
 * ActionFensterVisualisiereFenster(JDesktopPane d) { Desktop = d; }
 * 
 * public void actionPerformed(ActionEvent e) { JInternalFrame[] allFrames =
 * Desktop.getAllFrames();
 * 
 * try { int frameNum = 0;
 * 
 * if (allFrames[1].getTitle() == "Loesungsraum") frameNum = 1; if
 * (allFrames[2].getTitle() == "Loesungsraum") frameNum = 2;
 * 
 * allFrames[frameNum].setIcon(false); allFrames[frameNum].show(); } catch
 * (Exception exception) { } }
 * 
 * private JDesktopPane Desktop; }
 */

// /////////////////////////////////////////////////////////////////////////////
// SS13 ==> In Tabs!!!
// /////////////////////

/*
 * class ActionFensterEingabefenster implements ActionListener { public
 * ActionFensterEingabefenster(JDesktopPane d) { Desktop = d; }
 * 
 * public void actionPerformed(ActionEvent e) { JInternalFrame[] allFrames =
 * Desktop.getAllFrames();
 * 
 * try { int frameNum = 0;
 * 
 * if (allFrames[1].getTitle() == "Eingabe") frameNum = 1; if
 * (allFrames[2].getTitle() == "Eingabe") frameNum = 2;
 * 
 * allFrames[frameNum].setIcon(false); allFrames[frameNum].show(); } catch
 * (Exception exception) { } }
 * 
 * private JDesktopPane Desktop; }
 */

public class dakin extends JFrame {

	// //////////////////////////////////////////////////////
	// SS13 ==> Ab jetzt funktioniert die Darstellung in Tabs
	// Diese Codes können gelöscht werden.
	// private JDesktopPane Desktop;

	// //////////////////////////////////////////////////////
	// SS13 ==> JTabbedPane für die Tab-Darstellung
	private JTabbedPane tabbedPane;
	
	// Klassen der Ausgabe sind jetzt in JPanels, damit diese
	// als Reiter den Tabs hinzugefügt werden können
	private JPanel eingabe;
	private JPanel ausgabeBaum;
	private JPanel ausgabeVisualisierung;
	
	private JScrollPane scrollBaum;

	public dakin() {
		super("Dakin Eingabetabelle");

		setSize(1200, 700);
		setLocation(0, 0);

		// //////////////////////////////////////////////////////
		// SS13 ==> Ab jetzt funktioniert die Darstellung in Tabs
		// Diese Codes können gelöscht werden. ALT!!!
		
		// Desktop = new JDesktopPane();
		// getContentPane().add(Desktop);
		// Desktop = new JDesktopPane();
		// Desktop.setBounds(10, 11, 414, 240);
		// getContentPane().add(Desktop);
		// //////////////////////////////////////////////////////
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		// Initalisieren der Panel-Objekte
		ausgabeBaum = new DakinAusgabe();
		ausgabeVisualisierung = new Visualisiere();
		eingabe = new EingabeTabelle(this, eingabe, ausgabeBaum,
				ausgabeVisualisierung);

		ausgabeBaum.setAutoscrolls(true);
		
		// Hinzufügen der einzelnen Tabs und Beschriftungen setzen
		tabbedPane.addTab("Eingabe", null, eingabe, null);
		tabbedPane.addTab("Ausgabe - Baum", null, ausgabeBaum, null);
		tabbedPane.addTab("Ausgabe - Visualisierung", null,
				ausgabeVisualisierung, null);

		// Zu Beginn wird NUR die EingabeTabelle angezeigt, die restlichen
		// Reiter sind bis zur Berechnung leer.
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);

		// MenuBar einfuegen
		JMenuBar jmb = new JMenuBar();
		JMenuItem item;

		// Datei Menu einfuegen
		JMenu Datei = new JMenu("Datei");

		Datei.add(item = new JMenuItem("Oeffnen"));
		item.addActionListener(new ActionDateiOeffnen(eingabe, this));
		Datei.add(item = new JMenuItem("Speichern als..."));
		item.addActionListener(new ActionDateiSpeichern(eingabe));
		Datei.addSeparator();
		Datei.add(item = new JMenuItem("Beenden"));
		item.addActionListener(new ActionDateiBeenden());

		jmb.add(Datei);

		// //////////////////////////////////////////////////////
		// SS13 ==> Da nun alles in den Tabs funktioniert, wird
		// dieses Menü nicht mehr benötigt.
		// Fenster Menu einfuegen
		/*
		 * JMenu Fenster = new JMenu("Fenster");
		 * 
		 * Fenster.add(item = new JMenuItem("Eingabefenster"));
		 * item.addActionListener(new ActionFensterEingabefenster(Desktop));
		 * Fenster.add(item = new JMenuItem("Ausgabefenster"));
		 * item.addActionListener(new ActionFensterAusgabefenster(Desktop));
		 * Fenster.add(item = new JMenuItem("Visualisierungsfenster"));
		 * item.addActionListener(new
		 * ActionFensterVisualisiereFenster(Desktop));
		 * 
		 * jmb.add(Fenster);
		 */
		// //////////////////////////////////////////////////////
		
		// Pfad Menu einfuegen
		// Hier kann bei fehlenden Solver der Pfad noch während der 
		// Laufzeit geändert werden
		JMenu Pfad = new JMenu("Pfad");

		Pfad.add(item = new JMenuItem("Solverpfad setzen"));
		item.addActionListener(new ActionPfadSolverPfad(eingabe));

		jmb.add(Pfad);

		// Info Menu einfuegen
		JMenu Info = new JMenu("Info");

		Info.add(item = new JMenuItem("Hilfe"));
		item.addActionListener(new Hilfe());
		Info.add(item = new JMenuItem("Über die Entwickler"));
		item.addActionListener(new ActionInfoEntwickler());

		jmb.add(Info);

		getContentPane().add(jmb, BorderLayout.NORTH);

	}
	// //////////////////////////////////////////////////////
	// SS13 ==> Neue Methoden um zB nach dem Berechnen auf
	// einen neuen Reiter in der Tab-Ansicht zu wechseln
	public void tabAnsichtWechseln(int i) {
		tabbedPane.setSelectedIndex(i);
	}

	// Neue Methode um verschiedene Tabs zu aktivieren und deaktivieren
	public void tabEnableAt(int i, boolean value) {
		tabbedPane.setEnabledAt(i, value);
	}

	// //////////////////////////////////////////////////////
	public static void main(String[] args) {
		dakin frame = new dakin();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		// ////////////////////////////////////////////////
		// SS13 ==> BUG-Info kann weg, Eingabe funktioniert jetzt ohne
		// "return"!!! --> siehe EingabeTabelle()!
		/*
		 * JOptionPane .showMessageDialog( null,
		 * "Nach der letzten Eingabe in die Matrix muss \"return\" gedrueckt werden!"
		 * , "Bug-Info", JOptionPane.PLAIN_MESSAGE, null);
		 */
		// ////////////////////////////////////////////////
	}

}