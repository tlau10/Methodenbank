package view;

import java.awt.*;

import javax.swing.*;

import com.borland.jbcl.layout.*;

import java.util.Vector;

import org.jdom.Element;

import java.awt.event.*;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: AddFoodFrame
 * </p>
 * <p>
 * Description: Zeigt das Fenster an, um Lebensmittel hinzuzufügen
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */
public class AddFoodFrame extends JPanel {
	private static final long serialVersionUID = -7723139152988777727L;
	private JLabel jLabelHeading = new JLabel();
	private XYLayout xYLayout1 = new XYLayout();
	private JLabel jLabelName = new JLabel();
	private JTextField jTextFieldName = new JTextField();
	private JLabel jLabelGroup = new JLabel();
	private JLabel jLabelFoodGroup = new JLabel();
	private JComboBox<String> jComboBoxGroup;
	private JLabel jLabelAmount = new JLabel();
	private JTextField jTextFieldAmount = new JTextField();
	private JLabel jLabelCalories = new JLabel();
	private JTextField jTextFieldCalories = new JTextField();

	private MainFrame parentFrame_;
	private JButton jButtonSubmit = new JButton();
	private JButton jButtonUndo = new JButton();
	private JButton jButtonClose = new JButton();

	public AddFoodFrame(MainFrame parentFrame,
			Vector<Object> allEatables) {
		try {
			parentFrame_ = parentFrame;
			
			jbInit();
		} catch (Exception ex) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}
	}

	private void jbInit() throws Exception {
		jComboBoxGroup = new JComboBox<String>(parentFrame_.getGroupName());
		jComboBoxGroup.setSelectedIndex(0);

		jLabelHeading.setFont(new java.awt.Font("SansSerif", 1, 14));
		jLabelHeading.setBorder(BorderFactory.createRaisedBevelBorder());
		jLabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelHeading
				.setText("Biite geben Sie die Daten des neuen Elements ein");
		this.setLayout(xYLayout1);
		jLabelName.setBorder(BorderFactory.createEtchedBorder());
		jLabelName.setMaximumSize(new Dimension(34, 15));
		jLabelName.setToolTipText("");
		jLabelName.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelName.setText("Name");
		jTextFieldName.setText("");
		jLabelGroup.setBorder(BorderFactory.createEtchedBorder());
		jLabelGroup.setText("Lebensmittelgruppe");
		jLabelFoodGroup.setBorder(BorderFactory.createEtchedBorder());
		jLabelFoodGroup.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelFoodGroup.setText("Lebensmittelgruppe");
		jComboBoxGroup.setEditable(false);
		jLabelAmount.setBorder(BorderFactory.createEtchedBorder());
		jLabelAmount.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAmount.setText("Menge (gr/ml) )");
		jTextFieldAmount.setText("");
		jLabelCalories.setBorder(BorderFactory.createEtchedBorder());
		jLabelCalories.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelCalories.setText("Kalorien");
		jTextFieldCalories.setText("");
		jButtonSubmit.setText("Übernehmen");
		jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonSubmit_actionPerformed(e);
			}
		});
		jButtonUndo.setActionCommand("jButton1");
		jButtonUndo.setText("Verwerfen");
		jButtonUndo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonUndo_actionPerformed(e);
			}
		});
		jButtonClose.setText("Schliessen");
		jButtonClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		this.add(jLabelHeading, new XYConstraints(60, 34, 433, 28));
		this.add(jTextFieldName, new XYConstraints(199, 132, 159, -1));
		this.add(jLabelName, new XYConstraints(143, 134, 47, 21));
		this.add(jLabelFoodGroup, new XYConstraints(84, 177, 105, 21));
		this.add(jComboBoxGroup, new XYConstraints(202, 176, 157, -1));
		this.add(jLabelAmount, new XYConstraints(85, 222, 101, -1));
		this.add(jTextFieldAmount, new XYConstraints(201, 222, 154, -1));
		this.add(jLabelCalories, new XYConstraints(87, 270, 97, -1));
		this.add(jTextFieldCalories, new XYConstraints(202, 268, 152, -1));
		this.add(jButtonClose, new XYConstraints(444, 354, -1, -1));
		this.add(jButtonSubmit, new XYConstraints(336, 355, -1, -1));
		this.add(jButtonUndo, new XYConstraints(232, 355, -1, -1));
	}

	private Element Entries2Element() {
		int groupID;
		String name, amount, calories;
		name = jTextFieldName.getText();
		groupID = jComboBoxGroup.getSelectedIndex();
		amount = jTextFieldAmount.getText();
		calories = jTextFieldCalories.getText();
		return parentFrame_.newEatable(name, groupID, amount, calories);
	}

	private void clearentries() {
		jTextFieldName.setText("");
		jComboBoxGroup.setSelectedIndex(0);
		jTextFieldAmount.setText("");
		jTextFieldCalories.setText("");
	}

	private boolean checkString4Int(String testString) {
		char cTmp;
		for (int i = 0; i < testString.length(); i++) {
			cTmp = testString.charAt(i);
			String c = String.valueOf(cTmp);

			if ((c.equals("0")) || (c.equals("1")) || (c.equals("2"))
					|| (c.equals("3")) || (c.equals("4")) || (c.equals("5"))
					|| (c.equals("6")) || (c.equals("7")) || (c.equals("8"))
					|| (c.equals("9"))) {
				// " guter Fall "
			} else
				return false;
		}
		return true;
	}

	private void jButtonUndo_actionPerformed(ActionEvent e) {
		this.clearentries();
	}

	private boolean alreadyexists(Element elem) {
		for (int i = 0; i < parentFrame_.getAllEatables().size(); i++) {
			Element myElement = (Element) parentFrame_.getAllEatables().get(i);

			if (myElement.getChild("Name").getText().toLowerCase()
					.equals(elem.getChild("Name").getText().toLowerCase()))
				return true;
		}
		return false;
	}

	private void jButtonSubmit_actionPerformed(ActionEvent e) {
		if ((!jTextFieldName.getText().equals(""))
				&& (!jTextFieldAmount.getText().equals(""))
				&& (!jTextFieldCalories.getText().equals(""))) {
			if ((checkString4Int(jTextFieldAmount.getText()))
					&& (checkString4Int(jTextFieldCalories.getText()))) {

				Element newElem = this.Entries2Element();
				if (!(this.alreadyexists(newElem))) {
					parentFrame_.addEatable(newElem);

					JOptionPane.showMessageDialog(null, "Lebensmittel "
							+ jTextFieldName.getText() + " erfasst!");
				} else
					JOptionPane.showMessageDialog(null,
							"Lebensmittel existiert schon!");
			} else
				JOptionPane.showMessageDialog(null,
						"Nur ganze Zahlen als Eingabe erlaubt!");
		} else
			JOptionPane.showMessageDialog(null,
					"Es müssen alle Felder ausgefüllt werden!");
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.clearentries();
		parentFrame_.closechild(4, this, 0, null);
	}

}
