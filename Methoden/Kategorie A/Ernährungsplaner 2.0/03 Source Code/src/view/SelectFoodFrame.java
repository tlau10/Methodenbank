package view;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.jdom.Element;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

import controller.JListModelFactory;
/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: SelectFoodFrame
 * </p>
 * <p>
 * Description: Zeigt das Fenster zum auswählen der Lebensmittel an
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
public class SelectFoodFrame extends JPanel {

	private static final long serialVersionUID = 1789893933314262728L;
	private String[] groupName;
	private Vector<Object> allEatables_;
	private Vector<Object> chosenEatables;
	private JListModelFactory myJListModelFactory;
	private int calories_;
	private JLabel jLabelHeading = new JLabel();
	private XYLayout xYLayout1 = new XYLayout();
	private MainFrame parentFrame_;

	private JLabel jLabelBreakfast = new JLabel();
	private JLabel jLabelBreakfastMilk = new JLabel();
	private JLabel jLabelBreakfastCereals = new JLabel();
	private JLabel jLabelBreakfastBakery = new JLabel();
	private JLabel jLabelBreakfastDrink = new JLabel();
	private JLabel jLabelLunchFood = new JLabel();
	private JLabel jLabelMainDish = new JLabel();
	private JLabel jLabelSideDish = new JLabel();
	private JLabel jLabelDessert = new JLabel();
	private JLabel jLabelLunchDrink = new JLabel();
	private JLabel jLabelDinner = new JLabel();
	private JLabel jLabelBreadSide = new JLabel();
	private JLabel jLabelDinnerBakery = new JLabel();
	private JLabel jLabelDinnerDrink = new JLabel();
	private JButton jButtonContinue = new JButton();

	// falls man Factory benutzt
	private JList<String> jListBreakfastMilk;
	private JList<String> jListBreakfastCereals;
	private JList<String> jListBreakfastBakery;
	private JList<String> jListBreakfastDrinks;
	private JList<String> jListMainDish;
	private JList<String> jListSideDish;
	private JList<String> jListDessert;
	private JList<String> jListLunchDrinks;
	private JList<String> jListBreadSide;
	private JList<String> jListDinnerBakery;
	private JList<String> jListDinnerDrink;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SelectFoodFrame(Vector<Object> allEatables, int kalorien, MainFrame parentFrame) {
		try {

			allEatables_ = allEatables;
			myJListModelFactory = new JListModelFactory(parentFrame.getAllEatables());
			chosenEatables = (Vector) allEatables_.clone();
			calories_ = kalorien;
			parentFrame_ = parentFrame;
			jbInit();

		} catch (Exception ex) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void jbInit() throws Exception {

		// Factory init
		jListBreakfastMilk = new JList(myJListModelFactory.createGroup(0));
		jListBreakfastCereals = new JList(myJListModelFactory.createGroup(1));
		jListBreakfastBakery = new JList(myJListModelFactory.createGroup(2));
		jListBreakfastDrinks = new JList(myJListModelFactory.createGroup(3));
		jListMainDish = new JList(myJListModelFactory.createGroup(4));
		jListSideDish = new JList(myJListModelFactory.createGroup(5));
		jListDessert = new JList(myJListModelFactory.createGroup(6));
		jListLunchDrinks = new JList(myJListModelFactory.createGroup(7));
		jListBreadSide = new JList(myJListModelFactory.createGroup(8));
		jListDinnerBakery = new JList(myJListModelFactory.createGroup(9));
		jListDinnerDrink = new JList(myJListModelFactory.createGroup(10));

		// GruppennamenArray von Application holen
		groupName = parentFrame_.getGroupName();

		new TitledBorder("");
		jLabelHeading.setFont(new java.awt.Font("SansSerif", 1, 12));
		jLabelHeading.setBorder(BorderFactory.createRaisedBevelBorder());
		jLabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelHeading.setText("Wählen Sie nun die gewünschten Gerichte aus");
		this.setLayout(xYLayout1);
		jLabelBreakfast.setBorder(BorderFactory.createEtchedBorder());
		jLabelBreakfast.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBreakfast.setText("Fruehstück");
		jLabelBreakfastMilk.setBorder(BorderFactory.createEtchedBorder());
		jLabelBreakfastMilk.setDebugGraphicsOptions(0);
		jLabelBreakfastMilk.setVerifyInputWhenFocusTarget(true);
		jLabelBreakfastMilk.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBreakfastMilk.setText(groupName[0]);
		jLabelBreakfastCereals.setBorder(BorderFactory.createEtchedBorder());
		jLabelBreakfastCereals.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBreakfastCereals.setText(groupName[1]);
		jLabelBreakfastBakery.setBorder(BorderFactory.createEtchedBorder());
		jLabelBreakfastBakery.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBreakfastBakery.setText("Backwaren");
		jLabelBreakfastDrink.setBorder(BorderFactory.createEtchedBorder());
		jLabelBreakfastDrink.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBreakfastDrink.setText("Getränk");

		// Für jede JList eine SCrollpane erschaffen und zuweisen
		JScrollPane myJScrollPaneBreakfastMilk = new JScrollPane(
				jListBreakfastMilk);
		JScrollPane myJScrollPaneBreakfastCereals = new JScrollPane(
				jListBreakfastCereals);
		JScrollPane myJScrollPaneBreakfastBakery = new JScrollPane(
				jListBreakfastBakery);
		JScrollPane myJScrollPaneBreakfastDrinks = new JScrollPane(
				jListBreakfastDrinks);
		JScrollPane myJScrollPaneMainDish = new JScrollPane(jListMainDish);
		JScrollPane myJScrollPaneSideDish = new JScrollPane(jListSideDish);
		JScrollPane myJScrollPaneDessert = new JScrollPane(jListDessert);
		JScrollPane myJScrollPaneLunchDrink = new JScrollPane(jListLunchDrinks);
		JScrollPane myJScrollPaneBreadSide = new JScrollPane(jListBreadSide);
		JScrollPane myJScrollPaneDinnerBakery = new JScrollPane(
				jListDinnerBakery);
		JScrollPane myJScrollPaneDinnerDrink = new JScrollPane(jListDinnerDrink);

		xYLayout1.setWidth(781);
		xYLayout1.setHeight(533);
		jLabelLunchFood.setBorder(BorderFactory.createEtchedBorder());
		jLabelLunchFood.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLunchFood.setText("Mittagessen");
		jLabelMainDish.setBorder(BorderFactory.createEtchedBorder());
		jLabelMainDish.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMainDish.setText(groupName[4]);
		jLabelSideDish.setBorder(BorderFactory.createEtchedBorder());
		jLabelSideDish.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelSideDish.setText(groupName[5]);
		jLabelDessert.setBorder(BorderFactory.createEtchedBorder());
		jLabelDessert.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDessert.setText(groupName[6]);
		jLabelLunchDrink.setBorder(BorderFactory.createEtchedBorder());
		jLabelLunchDrink.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLunchDrink.setText("Getränk");
		jLabelDinner.setBorder(BorderFactory.createEtchedBorder());
		jLabelDinner.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDinner.setText("Abendessen");
		jLabelBreadSide.setEnabled(true);
		jLabelBreadSide.setBorder(BorderFactory.createEtchedBorder());
		jLabelBreadSide.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBreadSide.setText(groupName[8]);
		jLabelDinnerBakery.setBorder(BorderFactory.createEtchedBorder());
		jLabelDinnerBakery.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDinnerBakery.setText("Backwaren");
		jLabelDinnerDrink.setBorder(BorderFactory.createEtchedBorder());
		jLabelDinnerDrink.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDinnerDrink.setText("Getränk");
		jButtonContinue.setBorder(BorderFactory.createRaisedBevelBorder());
		jButtonContinue.setText("Weiter zum Ergebnis");
		jButtonContinue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonContinue_actionPerformed(e);
			}
		});
		this.add(myJScrollPaneBreakfastMilk,
				new XYConstraints(87, 106, 171, 95));
		this.add(jLabelBreakfastCereals, new XYConstraints(264, 86, 158, 18));
		this.add(jLabelBreakfastDrink, new XYConstraints(587, 83, 164, -1));
		this.add(jLabelHeading, new XYConstraints(247, 29, 310, 31));

		this.add(myJScrollPaneBreakfastCereals, new XYConstraints(264, 107,
				156, 93));
		this.add(myJScrollPaneBreakfastBakery, new XYConstraints(427, 106, 155,
				95));
		this.add(myJScrollPaneBreakfastDrinks, new XYConstraints(589, 105, 165,
				95));
		this.add(jLabelBreakfastMilk, new XYConstraints(87, 86, 172, -1));
		this.add(jLabelBreakfastBakery, new XYConstraints(429, 86, 154, -1));
		this.add(myJScrollPaneBreadSide, new XYConstraints(261, 375, 162, 94));
		this.add(myJScrollPaneDinnerBakery,
				new XYConstraints(432, 375, 151, 95));
		this.add(myJScrollPaneDinnerDrink, new XYConstraints(589, 374, 168, 97));
		this.add(jLabelBreadSide, new XYConstraints(263, 346, 162, -1));
		this.add(jLabelDinnerBakery, new XYConstraints(434, 347, 153, -1));
		this.add(jLabelDinnerDrink, new XYConstraints(590, 347, 166, -1));
		this.add(myJScrollPaneDessert, new XYConstraints(432, 239, 153, 92));
		this.add(myJScrollPaneLunchDrink, new XYConstraints(591, 241, 166, 90));
		this.add(jLabelLunchDrink, new XYConstraints(591, 215, 167, -1));
		this.add(jLabelDessert, new XYConstraints(434, 214, 152, -1));
		this.add(myJScrollPaneSideDish, new XYConstraints(269, 242, 155, 90));
		this.add(myJScrollPaneMainDish, new XYConstraints(87, 242, 172, 88));
		this.add(jLabelSideDish, new XYConstraints(267, 214, 156, -1));
		this.add(jLabelMainDish, new XYConstraints(87, 215, 171, -1));
		this.add(jLabelBreakfast, new XYConstraints(8, 147, 67, -1));
		this.add(jLabelLunchFood, new XYConstraints(8, 277, 68, 21));
		this.add(jLabelDinner, new XYConstraints(5, 401, 74, -1));
		this.add(jButtonContinue, new XYConstraints(592, 491, 166, 32));

	}

	private void createChosenVector() {

		this.subtractJListElems(jListBreakfastMilk);
		this.subtractJListElems(jListBreakfastCereals);
		this.subtractJListElems(jListBreakfastBakery);
		this.subtractJListElems(jListBreakfastDrinks);
		this.subtractJListElems(jListMainDish);
		this.subtractJListElems(jListSideDish);
		this.subtractJListElems(jListDessert);
		this.subtractJListElems(jListLunchDrinks);
		this.subtractJListElems(jListBreadSide);
		this.subtractJListElems(jListDinnerBakery);
		this.subtractJListElems(jListDinnerDrink);

		for (int i = 0; i < chosenEatables.size(); i++) {
			if (chosenEatables.get(i) != null) {
				Element myElement = (Element) chosenEatables.get(i);
				myElement.getChild("Name").getText();

			}
		}

	}

	private void subtractJListElems(JList<?> thisJList) {
		int ID;
		Integer temp;
		String value;
		String strID;
		ListModel<?> lm = thisJList.getModel();
		int[] sel = thisJList.getSelectedIndices();
		boolean isSelected = false;
		lm.getSize();

		for (int i = 0; i < lm.getSize(); ++i) {
			value = (String) lm.getElementAt(i);
			strID = value.substring(0, value.indexOf(")"));
			temp = new Integer(strID);
			ID = temp.intValue();

			for (int j = 0; j < sel.length; ++j) {
				if (sel[j] == i) {
					isSelected = true;
				}
			}
			if (!isSelected) {

				chosenEatables.remove(ID);
				chosenEatables.add(ID, null);

			}

			isSelected = false;
		}

	}

	private void jButtonContinue_actionPerformed(ActionEvent e) {
		this.createChosenVector();
		Vector<Object> result;

		result = (Vector<Object>) parentFrame_.executeCalculation(chosenEatables,
				calories_);
		parentFrame_.closechild(2, this, 0, result);

	}

}
