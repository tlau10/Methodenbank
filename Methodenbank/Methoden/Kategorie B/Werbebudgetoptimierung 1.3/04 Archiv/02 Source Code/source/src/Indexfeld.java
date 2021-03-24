/*
    Klasse Indexfeld

    Die Klasse erzeugt eine Komponente, welcher die einzelnen Medien-
    komponenten enthlt.
 */

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class Indexfeld extends JPanel {

	/**
	 * Serilizable Version ID for Class Version
	 */
	private static final long serialVersionUID = 4312671353324750620L;

	// die zu erzeugende Component
	private JTabbedPane tp;

	// die einzelnen Medien
	private Grunddaten grunddaten;
	private Medium fernsehanstalten;
	private Medium radiosender;
	private Medium zeitschriften;
	private Medium sonstigeMedien;

	// Verwaltungsklasse
	private WerbeBudgetApplication wb;

	// Konstruktor
	public Indexfeld(WerbeBudgetApplication w) {
		wb = w;

		tp = new JTabbedPane();

		grunddaten = new Grunddaten(wb);
		fernsehanstalten = new Fernsehanstalten(wb);
		radiosender = new Radiosender(wb);
		zeitschriften = new Zeitschriften(wb);
		sonstigeMedien = new SonstigeMedien(wb);

		tp.addTab("Grunddaten", grunddaten.getGrunddaten());
		tp.addTab("Fenrsehanstalten", fernsehanstalten.getMedium());
		tp.addTab("Radiosender", radiosender.getMedium());
		tp.addTab("Zeitschriften", zeitschriften.getMedium());
		tp.addTab("sonstige Medien", sonstigeMedien.getMedium());

		tp.setEnabledAt(1, false);
		tp.setEnabledAt(2, false);
		tp.setEnabledAt(3, false);
		tp.setEnabledAt(4, false);

		tp.setTabPlacement(SwingConstants.TOP);
	}

	// gibt die erzeugte Komponente zurck
	public JTabbedPane getJTappedPane() {
		return tp;
	}

	// berprft ob ein Medium gerade anklickbar ist
	public boolean getEnabled(int index) {
		return tp.isEnabledAt(index);
	}

	// setzt die Medien auf disabled
	public void disable() {
		tp.setEnabledAt(1, false);
		tp.setEnabledAt(2, false);
		tp.setEnabledAt(3, false);
		tp.setEnabledAt(4, false);
	}

	// setzt die Medien auf enabled
	public void enableAll() {
		tp.setEnabledAt(1, true);
		tp.setEnabledAt(2, true);
		tp.setEnabledAt(3, true);
		tp.setEnabledAt(4, true);
	}

	// folgende Methoden setzen ein Medium disabled oder enabled
	public void setEnabledFernsehen(boolean status) {
		tp.setEnabledAt(1, status);
	}

	public void setEnabledRadio(boolean status) {
		tp.setEnabledAt(2, status);
	}

	public void setEnabledZeitschrift(boolean status) {
		tp.setEnabledAt(3, status);
	}

	public void setEnabledSonstige(boolean status) {
		tp.setEnabledAt(4, status);
	}

	public boolean setEnabled(int tap, boolean status) {
		try {
			tp.setEnabledAt(tap, status);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	// zeigt das entsprechende Medium an
	public void setSelectedIndex(int index) {
		tp.setSelectedIndex(index);
	}

	// folgende Methoden geben die einzelnen Medien zurck
	public Grunddaten getGrunddaten() {
		return grunddaten;
	}

	public Medium getFernsehanstalten() {
		return fernsehanstalten;
	}

	public Medium getRadiosender() {
		return radiosender;
	}

	public Medium getZeitschriften() {
		return zeitschriften;
	}

	public Medium getSonstigeMedien() {
		return sonstigeMedien;
	}
}
