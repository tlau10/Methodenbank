package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Label;
import java.awt.Scrollbar;

public class Kurzinfo extends JFrame {

	private JPanel contentPane;

	/**
	 * �ffnet ein neues Fenster
	 */
	public static void main(String[] args) {
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Kurzinfo frame = new Kurzinfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellt den Rahmen und setzt den Inhalt
	 */
	public Kurzinfo() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		//Gr��e des Fensters
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//Fenstername
		this.setTitle("Wagner-Whitin Kurzinfo");

		//Inhalt der Hilfeseite
		JLabel text1 = new JLabel ("<html><p>"
				+ "<strong><u>1. Hilfe zum Tool</u></strong></p>"
				+ "<p>Hier eine kurze Erkl�rung/Hilfe zum Tool (f�r ausf�hrliche Informationen bitte das Benutzerhandbuch aufrufen)</p><br/><br/>"
				+ "<p><strong>1.1. </strong><strong><u>Neues Modell erstellen</u></strong></p>"
				+ "<p>  - Eingabe von Bestellkostensatz, Anzahl der Perioden und dem Lagerkostensatz</p>"
				+ "<p>  - Fixe oder variable Lagerkosten festlegen durch aktivieren des Hakens</p>"
				+ "<p>  - Auf �Weiter!� klicken</p>"
				+ "<p>  - Bedarfe eintragen (und bei Bedarf var. Lagerkosten)</p><br/><br/>"
				+ "<p><strong>1.2. </strong><strong><u>Datei speichern</u></strong></p>"
				+ "<p>  - Men�balken --&gt; �Datei� --&gt; �speichern�</p>"
				+ "<p>  - Pfad und Dateiname ausw�hlen</p><br/><br/>"
				+ "<p><strong>1.3. </strong><strong><u>Datei Laden</u></strong></p>"
				+ "<p>  - Men�balken --&gt; �Datei� --&gt; �Laden�</p>"
				+ "<p>  - Pfad und Datei ausw�hlen<br/><br/></p><br/>"
				+ "<p><strong>1.4. </strong><strong><u>Benutzerhandbuch</u></strong></p>"
				+ "<p>  - Im Men�punkt �Info� ist das Benutzerhandbuch zu finden</p>"
				+ "<p>  - Hier kann mehr �ber die komplette Methode erfahren werden</p></html>");
		//Gr��e und Ausrichtung des Textfeldes (hier mittig)
		text1.setBounds(100, 100, 300, 300);
		contentPane.add(text1,BorderLayout.CENTER);

	}

}
