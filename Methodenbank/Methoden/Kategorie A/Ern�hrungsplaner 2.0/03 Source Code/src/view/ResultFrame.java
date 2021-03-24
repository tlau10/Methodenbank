package view;

import java.awt.*;

import javax.swing.*;

import com.borland.jbcl.layout.*;

import controller.JListModelFactory;

import java.util.Vector;

import org.jdom.Element;

import java.awt.event.*;
/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: ResultFrame
 * </p>
 * <p>
 * Description: Zeigt das Ergebnisfenster an
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
public class ResultFrame extends JPanel {

	private static final long serialVersionUID = -9042216392364972182L;
	private JListModelFactory myJListModelFactory;
	private Vector<Object> resultVector_;
	private MainFrame parentFrame_;
	private int calorieNeeds_;
	private JLabel jLabelHeading = new JLabel();
	private XYLayout xYLayout1 = new XYLayout();
	private JLabel jLabelBreakfast = new JLabel();
	private JLabel jLabelLunch = new JLabel();
	private JLabel jLabelDinner = new JLabel();
	private JLabel jLabelOverallCalories = new JLabel();
	private JButton jButtonContinue = new JButton();
	private JList<String> jListBreakfast = new JList<String>();
	private JList<String> jListLunch = new JList<String>();
	private JList<String> jListDinner = new JList<String>();
	private JLabel jLabelBreakfastCalories = new JLabel();
	private JLabel jLabelLunchCalories = new JLabel();
	private JLabel jLabelDinnerCalories = new JLabel();
	private JLabel jLabelEnergyNeeds = new JLabel();

	public ResultFrame(Vector<Object> resultVector, double resultCalories,
			int energyNeeds, MainFrame parentFrame) {
		try {
			calorieNeeds_ = energyNeeds;
			resultVector_ = resultVector;
			parentFrame_ = parentFrame;
			jbInit();
		} catch (Exception ex) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void jbInit() throws Exception {
		myJListModelFactory = new JListModelFactory(resultVector_);
		this.setMenuCalories();
		jLabelEnergyNeeds
				.setText("Tagesbedarf: " + calorieNeeds_ + " Kalorien");

		jListBreakfast = new JList(
				myJListModelFactory.createMenue("Fruehstueck"));
		jListLunch = new JList(myJListModelFactory.createMenue("Mittagessen"));
		jListDinner = new JList(myJListModelFactory.createMenue("Abendessen"));
		JScrollPane jScrollPaneBreakfast = new JScrollPane(jListBreakfast);
		JScrollPane jScrollPaneLunch = new JScrollPane(jListLunch);
		JScrollPane jScrollPaneDinner = new JScrollPane(jListDinner);

		jLabelHeading.setFont(new java.awt.Font("SansSerif", 1, 14));
		jLabelHeading.setForeground(Color.black);
		jLabelHeading.setBorder(BorderFactory.createRaisedBevelBorder());
		jLabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelHeading.setText("Ihr mittels LP-Solve berechnetes Tagesmenü");
		this.setLayout(xYLayout1);
		jLabelBreakfast.setForeground(Color.black);
		jLabelBreakfast.setBorder(BorderFactory.createEtchedBorder());
		jLabelBreakfast.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBreakfast.setText("Fruehstücksmenü");

		jLabelLunch.setBorder(BorderFactory.createEtchedBorder());
		jLabelLunch.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLunch.setText("Mittagsmenü");

		jLabelDinner.setBorder(BorderFactory.createEtchedBorder());
		jLabelDinner.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDinner.setText("Abendessen");

		xYLayout1.setWidth(690);
		xYLayout1.setHeight(513);
		jLabelOverallCalories.setFont(new java.awt.Font("SansSerif", 1, 15));
		jLabelOverallCalories.setForeground(Color.black);
		jLabelOverallCalories.setBorder(BorderFactory.createEtchedBorder());
		jLabelOverallCalories.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelOverallCalories
				.setHorizontalTextPosition(SwingConstants.TRAILING);
		jButtonContinue.setText("Ende");
		jButtonContinue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonContinue_actionPerformed(e);
			}
		});
		jLabelLunchCalories.setHorizontalAlignment(SwingConstants.LEADING);
		jLabelEnergyNeeds.setFont(new java.awt.Font("SansSerif", 1, 15));
		jLabelEnergyNeeds.setBorder(BorderFactory.createEtchedBorder());
		this.add(jLabelHeading, new XYConstraints(137, 24, 395, -1));
		this.add(jScrollPaneBreakfast, new XYConstraints(280, 80, 240, 80));
		this.add(jScrollPaneLunch, new XYConstraints(280, 180, 240, 80));
		this.add(jScrollPaneDinner, new XYConstraints(280, 280, 240, 80));
		this.add(jLabelBreakfast, new XYConstraints(130, 110, 120, 25));
		this.add(jLabelLunch, new XYConstraints(130, 210, 120, 25));
		this.add(jLabelDinner, new XYConstraints(130, 310, 120, 25));
		this.add(jLabelBreakfastCalories, new XYConstraints(540, 110, 140, 25));
		this.add(jLabelLunchCalories, new XYConstraints(540, 210, 140, 25));
		this.add(jLabelDinnerCalories, new XYConstraints(540, 310, 140, 25));
		this.add(jButtonContinue, new XYConstraints(614, 485, -1, -1));
		this.add(jLabelOverallCalories, new XYConstraints(530, 410, 200, 25));
		this.add(jLabelEnergyNeeds, new XYConstraints(280, 410, 200, 25));

	}

	private void setMenuCalories() {
		int menueFCalories = 0;
		int menueMCalories = 0;
		int menueACalories = 0;
		int elemGruppe, c;
		Integer temp;

		for (int i = 0; i < resultVector_.size(); i++) {
			if (resultVector_.get(i) != null) {
				Element myElement = (Element) resultVector_.get(i);
				temp = new Integer(myElement.getChild("GruppenID").getText());
				elemGruppe = temp.intValue();

				if (elemGruppe < 4) {
					temp = new Integer(myElement.getChild("Kalorien").getText());
					c = temp.intValue();
					menueFCalories += c;
				}
				if ((elemGruppe > 3) && (elemGruppe < 8)) {
					temp = new Integer(myElement.getChild("Kalorien").getText());
					c = temp.intValue();
					menueMCalories += c;
				}
				if (elemGruppe > 7) {
					temp = new Integer(myElement.getChild("Kalorien").getText());
					c = temp.intValue();
					menueACalories += c;
				}
			}
		}
		c = menueFCalories + menueMCalories + menueACalories;
		jLabelBreakfastCalories = new JLabel("Frühstück:   " + menueFCalories
				+ " Kalorien");
		jLabelLunchCalories = new JLabel("Mittagessen: " + menueMCalories
				+ " Kalorien");
		jLabelDinnerCalories = new JLabel("Abendessen:  " + menueACalories
				+ " Kalorien");
		jLabelOverallCalories = new JLabel(" Gesamt:      " + c + " Kalorien");
	}

	private void jButtonContinue_actionPerformed(ActionEvent e) {
		parentFrame_.closechild(3, this, 0, null);
	}
}
