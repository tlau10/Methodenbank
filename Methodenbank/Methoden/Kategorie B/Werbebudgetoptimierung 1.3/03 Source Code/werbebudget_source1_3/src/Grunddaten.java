/*
    Klasse Grunddaten

    Klasse erzeugt einen Panel in einem ScrollPane welcher
    die Eingabemaske fr die Grunddaten darstellt
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Grunddaten implements ActionListener {

	// statische Werte, um die Zuordnung zu vereinfachen
	public final static int WEITER = 1;
	public final static int NEU = 2;

	// Java-Swing Komponenten zur Darstellung
	private JScrollPane grunddatenPane;
	private JPanel grunddatenPanel;
	private JPanel optimierungsPanel;
	private JPanel budgetPanel;
	private JPanel kundenPanel;
	private JLabel budgetLabel;
	private JTextField budgetField;
	private JLabel fernsehLabel;
	private JTextField fernsehField;
	private JLabel radioLabel;
	private JTextField radioField;
	private JLabel zeitschriftLabel;
	private JTextField zeitschriftField;
	private JLabel sonstigeLabel;
	private JTextField sonstigeField;
	private JLabel anzahlKundenLabel;
	private JTextField anzahlKundenField;
	private JLabel anzahlGeschlechtLabel;
	private JTextField anzahlGeschlechtField;
	private ButtonGroup anzahlGeschlechtButtonGroup;
	private JRadioButton maennlichRadioButton;
	private JRadioButton weiblichRadioButton;
	private ButtonGroup optimierungsButtonGroup;
	private JRadioButton minimalBudget;
	private JRadioButton maximalKunden;
	private JButton weiterButton;
	private JButton neuButton;

	// Verwaltungsklasse
	private WerbeBudgetApplication wb;

	// Konstruktor
	public Grunddaten(WerbeBudgetApplication w) {
		wb = w;

		grunddatenPanel = new JPanel();
		grunddatenPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(2, 2, 2, 2);
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.SOUTHWEST;

		optimierungsPanel = new JPanel();
		optimierungsPanel.setLayout(new GridBagLayout());
		optimierungsPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Optimierungskriterium"));

		budgetPanel = new JPanel();
		budgetPanel.setLayout(new GridBagLayout());
		budgetPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Budget und Werbemedien"));

		kundenPanel = new JPanel();
		kundenPanel.setLayout(new GridBagLayout());
		kundenPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Kundendaten"));

		budgetLabel = new JLabel("Maximal zur Verf\u00fcgung stehendes Budget: ");
		budgetField = new JTextField(10);
		fernsehLabel = new JLabel("Anzahl Fernsehanstalten: ");
		fernsehField = new JTextField(3);
		radioLabel = new JLabel("Anzahl Radiosender: ");
		radioField = new JTextField(3);
		zeitschriftLabel = new JLabel("Anzahl Zeitschriften: ");
		zeitschriftField = new JTextField(3);
		sonstigeLabel = new JLabel("Sonstige Medien: ");
		sonstigeField = new JTextField(3);
		anzahlKundenLabel = new JLabel(
				"Anzahl Kunden die erreicht werden sollen: ");
		anzahlKundenField = new JTextField(8);
		anzahlGeschlechtLabel = new JLabel("davon ");
		anzahlGeschlechtField = new JTextField(8);
		anzahlGeschlechtButtonGroup = new ButtonGroup();
		maennlichRadioButton = new JRadioButton("m\u00e4nnlich");
		maennlichRadioButton.setSelected(true);
		weiblichRadioButton = new JRadioButton("weiblich");

		weiterButton = new JButton("weiter >>");
		weiterButton.setActionCommand(String.valueOf(WEITER));
		weiterButton.addActionListener(this);

		neuButton = new JButton("Felder leeren");
		neuButton.setActionCommand(String.valueOf(NEU));
		neuButton.addActionListener(this);

		anzahlGeschlechtButtonGroup.add(maennlichRadioButton);
		anzahlGeschlechtButtonGroup.add(weiblichRadioButton);

		optimierungsButtonGroup = new ButtonGroup();
		minimalBudget = new JRadioButton("Minimierung des Budgets");
		maximalKunden = new JRadioButton("Maximierung der potentiellen K\u00e4ufer");
		optimierungsButtonGroup.add(minimalBudget);
		optimierungsButtonGroup.add(maximalKunden);
		minimalBudget.setSelected(true);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		budgetPanel.add(budgetLabel, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(budgetField, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(fernsehLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(fernsehField, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(radioLabel, c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(radioField, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(zeitschriftLabel, c);

		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(zeitschriftField, c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(sonstigeLabel, c);

		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(sonstigeField, c);

		c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		budgetPanel.add(neuButton, c);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		grunddatenPanel.add(budgetPanel, c);
		c.anchor = GridBagConstraints.SOUTHWEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		optimierungsPanel.add(minimalBudget, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		optimierungsPanel.add(maximalKunden, c);

		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		grunddatenPanel.add(new JLabel(" "), c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		grunddatenPanel.add(optimierungsPanel, c);
		c.anchor = GridBagConstraints.SOUTHWEST;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		kundenPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		kundenPanel.add(anzahlKundenLabel, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		kundenPanel.add(anzahlKundenField, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		kundenPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		kundenPanel.add(anzahlGeschlechtLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		kundenPanel.add(anzahlGeschlechtField, c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		kundenPanel.add(maennlichRadioButton, c);

		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		kundenPanel.add(weiblichRadioButton, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		grunddatenPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		grunddatenPanel.add(kundenPanel, c);
		c.anchor = GridBagConstraints.SOUTHWEST;

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		grunddatenPanel.add(new JLabel(" "), c);

		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		grunddatenPanel.add(weiterButton, c);

		grunddatenPane = new JScrollPane(grunddatenPanel);
		grunddatenPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		grunddatenPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	// gibt den erzeugten ScrollPane zurck
	public JScrollPane getGrunddaten() {
		return grunddatenPane;
	}

	// Verarbeitung der Ereignisse
	public void actionPerformed(ActionEvent e) {
		if (Integer.parseInt(e.getActionCommand()) == WEITER) {
			wb.resetSolverDaten();
			wb.getIndexfeld().disable();

			wb.getSolverDaten().setAnzahlFernsehanstalten(
					fernsehField.getText());
			wb.getSolverDaten().setAnzahlRadiosender(radioField.getText());
			wb.getSolverDaten().setAnzahlZeitschriften(
					zeitschriftField.getText());
			wb.getSolverDaten()
					.setAnzahlSonstigeMedien(sonstigeField.getText());

			if (wb.getSolverDaten().getAnzahlFernsehanstalten() == 0
					&& wb.getSolverDaten().getAnzahlRadiosender() == 0
					&& wb.getSolverDaten().getAnzahlZeitschriften() == 0
					&& wb.getSolverDaten().getAnzahlSonstigeMedien() == 0) {
				JOptionPane.showMessageDialog(null, "Keine Medien eingegeben",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}

			fernsehField.setEnabled(false);
			radioField.setEnabled(false);
			zeitschriftField.setEnabled(false);
			sonstigeField.setEnabled(false);

			if (saveOptions() == false)
				return;

			wb.modifyIndexFeld();
		}

		if (Integer.parseInt(e.getActionCommand()) == NEU) {
			wb.newMedien();

			fernsehField.setEnabled(true);
			fernsehField.setText("");
			radioField.setEnabled(true);
			radioField.setText("");
			zeitschriftField.setEnabled(true);
			zeitschriftField.setText("");
			sonstigeField.setEnabled(true);
			sonstigeField.setText("");
		}
	}

	public void resetInputs() {
		fernsehField.setEnabled(true);
		fernsehField.setText("");
		radioField.setEnabled(true);
		radioField.setText("");
		zeitschriftField.setEnabled(true);
		zeitschriftField.setText("");
		sonstigeField.setEnabled(true);
		sonstigeField.setText("");
		budgetField.setText("");
		anzahlKundenField.setText("");
		anzahlGeschlechtField.setText("");
	}

	public boolean setInputs(SolverDaten tmpdata) {
		budgetField.setText("" + tmpdata.getBudget());
		fernsehField.setText("" + tmpdata.getAnzahlFernsehanstalten());
		radioField.setText("" + tmpdata.getAnzahlRadiosender());
		zeitschriftField.setText("" + tmpdata.getAnzahlZeitschriften());
		sonstigeField.setText("" + tmpdata.getAnzahlSonstigeMedien());
		anzahlKundenField.setText("" + tmpdata.getAnzahlKunden());
		anzahlGeschlechtField.setText("" + tmpdata.getAnzahlMaennlicheKunden());
		if (tmpdata.getOptimierung() == 0) {
			minimalBudget.setSelected(true);
		} else {
			maximalKunden.setSelected(true);
		}

		if (this.saveOptions()) {
			wb.resetSolverDaten();
			wb.getIndexfeld().disable();

			wb.getSolverDaten().setAnzahlFernsehanstalten(
					fernsehField.getText());
			wb.getSolverDaten().setAnzahlRadiosender(radioField.getText());
			wb.getSolverDaten().setAnzahlZeitschriften(
					zeitschriftField.getText());
			wb.getSolverDaten()
					.setAnzahlSonstigeMedien(sonstigeField.getText());

			if (wb.getSolverDaten().getAnzahlFernsehanstalten() == 0
					&& wb.getSolverDaten().getAnzahlRadiosender() == 0
					&& wb.getSolverDaten().getAnzahlZeitschriften() == 0
					&& wb.getSolverDaten().getAnzahlSonstigeMedien() == 0) {
				JOptionPane.showMessageDialog(null,
						"Fehler beim lesen der Datei", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			fernsehField.setEnabled(false);
			radioField.setEnabled(false);
			zeitschriftField.setEnabled(false);
			sonstigeField.setEnabled(false);

			if (saveOptions() == false) {
				JOptionPane.showMessageDialog(null,
						"Fehler beim lesen der Datei", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			wb.modifyIndexFeld();
		}
		return true;
	}

	// liest die eingegebenen Werte
	public boolean saveOptions() {
		if (wb.getSolverDaten().setBudget(budgetField.getText()) == false) {
			JOptionPane.showMessageDialog(null,
					"Kein bzw. fehlerhafter Wert f\u00fcr Budget eingegeben",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		wb.getSolverDaten().setAnzahlKunden(anzahlKundenField.getText());

		if (maennlichRadioButton.isSelected()) {
			wb.getSolverDaten().setAnzahlMaennlicheKunden(
					anzahlGeschlechtField.getText());
		}

		if (weiblichRadioButton.isSelected()) {
			wb.getSolverDaten().setAnzahlWeiblicheKunden(
					anzahlGeschlechtField.getText());
		}

		if (minimalBudget.isSelected()) {
			wb.getSolverDaten().setOptimierung(0);
		}

		if (maximalKunden.isSelected()) {
			wb.getSolverDaten().setOptimierung(1);
		}

		if (wb.getSolverDaten().getAnzahlKunden() == 0) {
			JOptionPane.showMessageDialog(null,
					"Keine Kundenanzahl eingegeben", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
