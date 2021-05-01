package view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import com.borland.jbcl.layout.*;

import controller.EnergyNeeds;

import javax.swing.JPanel;
/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: StartFrame
 * </p>
 * <p>
 * Description: Zeigt das Startfenster an
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */
public class StartFrame extends JPanel {

	private static final long serialVersionUID = 1141508643580483305L;
	private MainFrame parentFrame_;

	private JTextField jTextFieldHeight = new JTextField();

	private JTextField jTextFieldWeight = new JTextField();
	private JTextField jTextFieldAge = new JTextField();
	private JLabel jLabelHeight = new JLabel("Grösse");
	private JLabel jLabelWeight = new JLabel("Gewicht");
	private JLabel jLabelAge = new JLabel("Alter");

	private JLabel jLabelHeading = new JLabel();

	private int calories = 0;

	private XYLayout xYLayout1 = new XYLayout();
	private JButton jButtonContinue = new JButton();
	private JLabel jLabelWork = new JLabel();
	private String[] sWork = { "Leicht", "Mittelschwer", "Schwer" };
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JComboBox jComboBoxWork = new JComboBox(sWork);
	private JLabel jLabelGender = new JLabel();
	private String[] sGender = { "männlich", "weiblich" };
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JComboBox jComboBoxGender = new JComboBox(sGender);
	private JLabel jLabelBMIHeading = new JLabel();
	private JLabel jLabelBMIInfo = new JLabel();
	private JLabel jLabelBMI = new JLabel();
	private JLabel jLabelMoreLessBMI = new JLabel();
	private JLabel jLabelEnergy = new JLabel();

	public StartFrame() {
		try {

			jbInit();
		} catch (Exception ex) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}
	}

	public StartFrame(MainFrame diaetParent) {
		try {

			parentFrame_ = diaetParent;
			jbInit();

		} catch (Exception ex) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}

	}

	private void setCalories(int cal) {

		calories = cal;
	}

	@SuppressWarnings("deprecation")
	private void jbInit() throws Exception {

		this.setLayout(xYLayout1);
		this.setFont(new java.awt.Font("Dialog", 0, 20));
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setDebugGraphicsOptions(0);
		this.setMaximumSize(new Dimension(300, 150));
		this.setMinimumSize(new Dimension(300, 150));
		this.setNextFocusableComponent(jTextFieldWeight);
		this.setPreferredSize(new Dimension(723, 557));
		jLabelHeight.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelHeight.setText("Grösse ( in Zentimetern )");
		jLabelWeight.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelWeight.setText("Gewicht ( in Kilogramm)");
		jLabelAge.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAge.setText("Alter ( in Jahren )");
		jTextFieldWeight.setMaximumSize(new Dimension(10, 30));
		jTextFieldWeight.setSize(10, 10);
		jTextFieldWeight.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				jTextFieldGewicht_keyReleased(e);
			}
		});

		jTextFieldHeight.setText("170");
		jTextFieldWeight.setText("66");
		jTextFieldAge.setText("33");

		jLabelHeading.setFont(new java.awt.Font("SansSerif", 1, 12));
		jLabelHeading.setBorder(BorderFactory.createRaisedBevelBorder());
		jLabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelHeading.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabelHeading.setText("Bitte machen sie hier Angaben zu Ihrer Person");

		jTextFieldHeight.setMaximumSize(new Dimension(10, 30));
		jTextFieldHeight.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				jTextFieldGroesse_keyReleased(e);
			}
		});
		jTextFieldAge.setMaximumSize(new Dimension(10, 30));
		jTextFieldAge.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				jTextFieldAlter_keyReleased(e);
			}
		});

		jButtonContinue.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonContinue.setText("Weiter");
		jButtonContinue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonWeiter_actionPerformed(e);
			}
		});
		xYLayout1.setWidth(614);
		xYLayout1.setHeight(395);
		jLabelWork.setText("Taetigkeit");
		jLabelGender.setText("Geschlecht");
		jLabelBMIHeading.setFont(new java.awt.Font("SansSerif", 1, 14));
		jLabelBMIHeading.setText("Ihr Body-Mass-Index: ");
		jLabelBMIInfo
				.setText("<HTML> &lt;19 Untergewicht<br>19 - 25 Normalgewicht <br> &gt;25  "
						+ "   Übergewicht </HMTL>");
		jLabelBMI.setFont(new java.awt.Font("SansSerif", 3, 16));
		jLabelBMI.setText("");
		jLabelEnergy.setFont(new java.awt.Font("SansSerif", 1, 14));
		jLabelEnergy.setText("Energiebedarf: ");
		jComboBoxWork.addItemListener(new java.awt.event.ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				jComboBoxTaetig_itemStateChanged(e);

			}
		});
		jComboBoxGender.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				jComboBoxGeschl_itemStateChanged(e);
			}
		});

		this.add(jLabelHeading, new XYConstraints(56, 39, 306, 39));
		this.add(jLabelHeight, new XYConstraints(37, 101, 152, 34));
		this.add(jLabelWeight, new XYConstraints(30, 141, 162, 34));
		this.add(jTextFieldAge, new XYConstraints(202, 191, 46, -1));
		this.add(jTextFieldHeight, new XYConstraints(202, 109, 45, 22));
		this.add(jTextFieldWeight, new XYConstraints(202, 150, 45, 22));
		this.add(jLabelAge, new XYConstraints(30, 182, 132, 34));
		this.add(jComboBoxWork, new XYConstraints(130, 233, 122, 22));
		this.add(jComboBoxGender, new XYConstraints(129, 279, 122, -1));
		this.add(jLabelGender, new XYConstraints(51, 277, 64, 23));
		this.add(jLabelWork, new XYConstraints(53, 229, 64, 31));
		this.add(jLabelBMIHeading, new XYConstraints(311, 99, 157, 33));
		this.add(jLabelBMIInfo, new XYConstraints(356, 133, 147, 63));
		this.add(jLabelBMI, new XYConstraints(468, 96, 120, 35));
		this.add(jLabelMoreLessBMI, new XYConstraints(313, 215, 261, 52));
		this.add(jLabelEnergy, new XYConstraints(54, 340, 207, 37));
		this.add(jButtonContinue, new XYConstraints(416, 341, -1, -1));
		updateScreen();
	}

	private void jButtonWeiter_actionPerformed(ActionEvent e) {

		if (calories > 0) {
			parentFrame_.closechild(1, this, calories, null);
		} else {
			JOptionPane.showMessageDialog(null,
					"Es müssen alle Felder ausgefüllt werden!");
		}

	}

	private void updateScreen() {
		setCalories(0);
		int height = 0;
		int weight = 0;
		int age = 0;
		int energyNeeds = 0; // energiebedarf
		double bmi = 0;
		jLabelMoreLessBMI.setText("");
		jLabelEnergy.setText("Energiebedarf:");
		jLabelBMI.setText("");

		try {
			if (jTextFieldHeight.getText().length() > 0)
				height = new Integer(jTextFieldHeight.getText()).intValue();
			if (jTextFieldWeight.getText().length() > 0)
				weight = new Integer(jTextFieldWeight.getText()).intValue();
			if (jTextFieldAge.getText().length() > 0)
				age = new Integer(jTextFieldAge.getText()).intValue();
		} catch (Exception e) {
			jLabelBMI.setText("");
			jLabelEnergy.setText("Energiebedarf:");
			height = 0;
			weight = 0;
			age = 0;
		}

		if (height > 0 && weight > 0) {
			bmi = EnergyNeeds.bmi(weight, height);
			String str = new String(String.valueOf(bmi));
			if (str.length() < 5)
				jLabelBMI.setText(String.valueOf(bmi)
						.substring(0, str.length()));
			else
				jLabelBMI.setText(String.valueOf(bmi).substring(0, 5));
		}

		if (bmi < 19 && bmi > 1) {
			jLabelMoreLessBMI
					.setText("<html>Sie haben Untergewicht, für die Berechnung des Energiebedarfs wird deshalb das Normalgewicht (Körpergröße - 100) als Grundlage verwendet.</html>");
			weight = height - 100;
		}

		if (bmi > 25) {
			jLabelMoreLessBMI
					.setText("<html>Sie haben Übergewicht, für die Berechnung des Energiebedarfs wird deshalb das Normalgewicht (Körpergröße - 100) als Grundlage verwendet.</html>");
			weight = height - 100;
		}

		if (height > 0 && weight > 0 && age > 0) {
			double work = 1;
			switch (jComboBoxWork.getSelectedIndex()) {
			case 0:
				work = 0.5;
				break;
			case 1:
				work = 0.75;
				break;
			case 2:
				work = 1;
				break;
			}
			String gender;
			if (jComboBoxGender.getSelectedIndex() == 0)
				gender = "m";
			else
				gender = "w";

			energyNeeds = EnergyNeeds.energyNeeds(weight, height, age, work,
					gender);
			setCalories(energyNeeds);
			jLabelEnergy.setText("Energiebedarf: " + energyNeeds + " Kcal");
		}

	}

	private void jTextFieldGroesse_keyReleased(KeyEvent e) {
		updateScreen();
	}

	private void jTextFieldGewicht_keyReleased(KeyEvent e) {
		updateScreen();

	}

	private void jTextFieldAlter_keyReleased(KeyEvent e) {
		updateScreen();

	}

	private void jComboBoxTaetig_itemStateChanged(ItemEvent e) {
		updateScreen();
	}

	private void jComboBoxGeschl_itemStateChanged(ItemEvent e) {
		updateScreen();

	}

}
