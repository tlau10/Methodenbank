/*
    abstrakte Klasse Medium

    Diese Klasse enthlt die Elemente, um ein Medium anzuzeigen
    Jedes Medium (Fernsehen, Radiosender, usw,) sollte diese Klasse
    erweitern und die abstrakten Methoden berschreiben
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public abstract class Medium implements ActionListener {
	// statische Werte, um die Zuordnung zu vereinfachen
	public final static int DATEN_EINGEBEN = 1;
	public final static int WEITER = 2;
	
	protected JScrollPane scrollPane;
	protected JPanel medienPanel;
	protected JPanel medienkategorienPanel;
	protected JLabel anzahlKategorienLabel;
	protected JPanel[] anzahlTabellenPanel;

	// Swing-Komponenten, welche angezeigt werden
	protected JLabel[] anzahlLabel;
	protected JTextField[] anzahlField;
	protected JButton datenEingebenButton;
	protected JButton weiterButton;
	protected JLabel maxBudgetLabel;
	protected JTextField maxBudgetField;

	// Tabellen-Array zur Dateneingabe
	protected Tabelle[] tabellen;

	// Anzahl der Medien und Kategorien
	protected int anzahlMedien;
	protected int[] anzahlMedienKategorien;

	// Verwaltungsklasse
	protected WerbeBudgetApplication wb;

	protected Medium() {
	}

	// Konstruktor
	public Medium(WerbeBudgetApplication w, String medienTitel) {
		wb = w;
			//Panel nötig, sonst ist die 2. Maske also die Medien nicht sichtbar
		medienPanel = new JPanel();
		medienPanel.setLayout(new GridBagLayout());

		medienkategorienPanel = new JPanel();
		medienkategorienPanel.setLayout(new GridBagLayout());
		medienkategorienPanel.setBorder(new TitledBorder(new EtchedBorder(),
				medienTitel));

		anzahlKategorienLabel = new JLabel(
				"Anzahl verschiedener Kostenkategorien:");

		datenEingebenButton = new JButton("Daten eingeben >>");
		datenEingebenButton.setActionCommand(String.valueOf(DATEN_EINGEBEN));
		datenEingebenButton.addActionListener(this);
		

		weiterButton = new JButton("Weiter >>");
		weiterButton.setActionCommand(String.valueOf(WEITER));
		weiterButton.addActionListener(this);

		maxBudgetLabel = new JLabel("Max. zu verwendendes Budget f\u00fcr dieses Medium: ");
		maxBudgetField = new JTextField(8);

		scrollPane = new JScrollPane(medienPanel);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	// initialisiert die Komponenten und gestaltet das Layout
	public void initMedium() {
		medienPanel.removeAll(); // Entfernt alle Komponente des JPanels!!!
		medienkategorienPanel.removeAll();
		anzahlMedien = getAnzahlMedien();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(2, 2, 2, 2);
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.SOUTHWEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		medienkategorienPanel.add(maxBudgetLabel, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		medienkategorienPanel.add(maxBudgetField, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		medienkategorienPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		medienkategorienPanel.add(anzahlKategorienLabel, c);

		if (anzahlMedien != 0) {
			anzahlLabel = new JLabel[anzahlMedien];
			anzahlField = new JTextField[anzahlMedien];
			tabellen = new Tabelle[anzahlMedien];

			int index;

			for (int i = 0; i < anzahlMedien; i++) {
				index = i + 1;
				anzahlLabel[i] = new JLabel(getMediumName() + " " + index
						+ ": ");

				c.gridx = 0;
				c.gridy = i + 3;
				c.gridwidth = 1;
				c.gridheight = 1;
				medienkategorienPanel.add(anzahlLabel[i], c);

				anzahlField[i] = new JTextField(3);

				c.gridx = 1;
				c.gridy = i + 3;
				c.gridwidth = 1;
				c.gridheight = 1;
				medienkategorienPanel.add(anzahlField[i], c);
			}

			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			medienPanel.add(medienkategorienPanel, c);

			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.gridheight = 1;
			medienPanel.add(new JLabel(" "), c);

			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 1;
			c.gridheight = 1;
			medienPanel.add(datenEingebenButton, c);
		}
	}

	// gibt ein Medium als ScrollPane zurck
	public JScrollPane getMedium() {
		return scrollPane;
	}

	public void resetInputs() {
		maxBudgetField.setText("");
	}

	protected void setAnzahlField(int index, String value) {
		this.anzahlField[index].setText(value);
	}

	protected void setTableValue(int value, int i, int j, int k) {
		tabellen[i].setValueAt(value, j, k);
	}

	protected void clickDatenEingegeben() {
		this.datenEingebenButton.doClick(100);
	}
	
	protected void clickWeiter() {
		this.weiterButton.doClick(100);
	}

	// abstrakte Methoden die von der Unterklasse berschrieben
	// werden muessen
	public abstract boolean setInputs(SolverDaten tmpdata);

	public abstract int getAnzahlMedien();

	public abstract String getMediumName();

	public abstract void setMaxBudget(String maxBudget);

	public abstract void setMedienKategorien(int i, int x, int y);

	public abstract void setMedienDaten(int i, int x, int y, String value);

	public abstract int getMyIndex();

	// Verarbeitung der Events
	public void actionPerformed(ActionEvent e) {
		if (Integer.parseInt(e.getActionCommand()) == DATEN_EINGEBEN) {
			setMaxBudget(maxBudgetField.getText());

			anzahlMedienKategorien = new int[anzahlMedien];

			// Anzahl der einzelnen Kategorien in int-Array einlesen
			for (int i = 0; i < anzahlMedien; i++) {
				try {		// ohne Try keine Zahlenzuordnung in der Tabelle von der Kategorie
					anzahlMedienKategorien[i] = Integer.parseInt(anzahlField[i]
							.getText());
				} catch (Exception ex) { 
					JOptionPane.showMessageDialog(null,
							"Nicht alle Kategorien korrekt eingegeben!",
							"Fehler", JOptionPane.ERROR_MESSAGE);
					return; 
				} 
			} 

			// vorhandene Elemente zuerst entfernen
			medienPanel.removeAll();
		
			
			// Einfgen der Tabellen
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(2, 2, 2, 2);
			c.weightx = 0;
			c.weighty = 0;
			c.anchor = GridBagConstraints.SOUTHWEST;

			anzahlTabellenPanel = new JPanel[anzahlMedien];

			int index = 0;
			for (int i = 0; i < anzahlMedien; i++) {
				index = i + 1;

				anzahlTabellenPanel[i] = new JPanel();
				anzahlTabellenPanel[i].setLayout(new GridBagLayout());
				anzahlTabellenPanel[i].setBorder(new TitledBorder(
						new EtchedBorder(), getMediumName() + " " + index));

				tabellen[i] = new Tabelle(anzahlMedienKategorien[i]);

				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 1;
				c.gridheight = 1;
				anzahlTabellenPanel[i].add(tabellen[i].getTabelle(), c);

				c.gridx = 0;
				c.gridy = (i * 2);
				c.gridwidth = 1;
				c.gridheight = 1;
				medienPanel.add(anzahlTabellenPanel[i], c);

				c.gridx = 0;
				c.gridy = (i * 2) + 1;
				c.gridwidth = 1;
				c.gridheight = 1;
				medienPanel.add(new JLabel(" "), c);
			}

			c.gridx = 1;
			c.gridy = (index - 1) * 2;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.anchor = GridBagConstraints.SOUTHWEST;
			medienPanel.add(weiterButton, c);
			
			c.gridx = 2;
			c.gridy = (index - 1) * 2;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.anchor = GridBagConstraints.SOUTHWEST;


			wb.setIndexfeld(getMyIndex());
		}

	
		
		if (Integer.parseInt(e.getActionCommand()) == WEITER) {
			// Grsse der Tabellen in SolverDaten festlegen
			// und Daten einlesen
			String value;
			if (anzahlMedienKategorien != null) {
				for (int i = 0; i < anzahlMedienKategorien.length; i++) {
					setMedienKategorien(i, anzahlMedienKategorien[i], 6);
				}

				try {
					for (int i = 0; i < anzahlMedien; i++) {
						for (int j = 0; j < anzahlMedienKategorien[i]; j++) {
							for (int k = 1; k < 7; k++) {
								value = (String) tabellen[i].getValueAt(j, k);
								setMedienDaten(i, j, k - 1, value);
							}
						}
					}
				} catch (Exception ex) { 
					JOptionPane.showMessageDialog(null,
							"Fehlerhafte Daten eingegeben!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					return; 
				} 
			}

			wb.next();
			
		
		}
	}
}
