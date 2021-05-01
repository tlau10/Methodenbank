/*
    Klasse Menue

    Diese Klasse stellt das Men der Applikation dar
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


public class Menue implements ActionListener {

	// statische Variablen um die Zuordnung zu vereinfachen
	public final static int NEU = 1;
	public final static int OEFFNEN = 2;
	public final static int SCHLIESSEN = 3;
	public final static int SICHERN = 4;
	public final static int VERLASSEN = 5;
	public final static int KOPIEREN = 6;
	public final static int AUSSCHNEIDEN = 7;
	public final static int EINFUEGEN = 8;
	public final static int HILFE = 9;
	public final static int PFAD = 10;

	// Verarbeiten eines Ereignisses
	public void actionPerformed(ActionEvent e) {
		switch (Integer.parseInt(e.getActionCommand())) {
		case 1: {
			wb.neu();
			break;
		}

		case 2: {
			wb.oeffnen();
			break;
		}

		case 3: {
			wb.schliessen();
			break;
		}

		case 4: {
			wb.sichern();
			break;
		}

		case 5: {
			wb.verlassen();
			break;
		}

		case 6: {
			wb.kopieren();
			break;
		}

		case 7: {
			wb.ausschneiden();
			break;
		}

		case 8: {
			wb.einfuegen();
			break;
		}

		case 9: {
			wb.hilfe();
			break;
		}
		case 10: {
			try {
				wb.solverPfad();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}
	}

	@SuppressWarnings("unused")
	private void add(JPanel panel) {
		// TODO Auto-generated method stub
		
	}

	private WerbeBudgetApplication wb;
	private JMenuBar menuBar;

	// Konstruktor
	public Menue(WerbeBudgetApplication w) {
		wb = w;

		menuBar = new JMenuBar();

		JMenu datei = new JMenu("Datei");

		JMenuItem item;

		datei.add(item = new JMenuItem("Neu"));
		item.setActionCommand(String.valueOf(NEU));
		item.addActionListener(this);

		datei.add(item = new JMenuItem("\u00D6ffnen"));
		item.setActionCommand(String.valueOf(OEFFNEN));
		item.addActionListener(this);

		datei.add(item = new JMenuItem("Speichern"));
		item.setActionCommand(String.valueOf(SICHERN));
		item.addActionListener(this);

		datei.add(item = new JMenuItem("Solver Pfad"));
		item.setActionCommand(String.valueOf(PFAD));
		item.addActionListener(this);
		/*
		 * TODO: Muss noch Implementiert werden
		 */
		/*
		 * datei.add(item = new JMenuItem("Schliessen"));
		 * item.setActionCommand(String.valueOf(SCHLIESSEN));
		 * item.addActionListener(this);
		 */

		datei.addSeparator();

		datei.add(item = new JMenuItem("Verlassen"));
		item.setActionCommand(String.valueOf(VERLASSEN));
		item.addActionListener(this);

		menuBar.add(datei);

		/*
		 * TODO: Muss noch Implementiert werden
		 */
		/*
		 * JMenu bearbeiten = new JMenu("Bearbeiten");
		 *
		 * bearbeiten.add(item = new JMenuItem("Kopieren"));
		 * item.setActionCommand(String.valueOf(KOPIEREN));
		 * item.addActionListener(this);
		 *
		 * bearbeiten.add(item = new JMenuItem("Ausschneiden"));
		 * item.setActionCommand(String.valueOf(AUSSCHNEIDEN));
		 * item.addActionListener(this);
		 *
		 * bearbeiten.add(item = new JMenuItem("Einf\u00FCgen"));
		 * item.setActionCommand(String.valueOf(EINFUEGEN));
		 * item.addActionListener(this);
		 *
		 * menuBar.add(bearbeiten);
		 */

		/*
		 * TODO: Muss noch Implementiert werden
		 */
		
		
		
		 // Menüelemente erzeugen
		 JMenu hilfe = new JMenu("?");
		 //Untermenüelemente erzeugen
		 hilfe.add(item = new JMenuItem("Hilfe"));
		 item.setActionCommand(String.valueOf(HILFE));
		 item.addActionListener(this);
		 
		 menuBar.add(hilfe);
		 
		// JPanel panel = new JPanel();
		// JLabel label = new JLabel();
		 
		 
	}

	// gibt das Men als Swing-Komponente zurck
	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
