package planer;
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

public class JPanelAuswahl extends JPanel {

	String[] gruppenName;
	ApplicationDiaetplaner ap_;
	Vector allEatables_;
	Vector chosenEatables;
	JListModelFactory myJListModelFactory;
	int calories_;
	JLabel jLabelAnsage = new JLabel();
	XYLayout xYLayout1 = new XYLayout();
	DiaetplanerException myDiaetplanerException;
	mainFrame parentFrame_;

	JLabel jLabelFruehstueck = new JLabel();
	JLabel jLabelFruehstueckMilchprodukte = new JLabel();
	JLabel jLabelFruehstueckCerealien = new JLabel();
	JLabel jLabelFruehstueckBackwaren = new JLabel();
	JLabel jLabelFruehstueckGetraenk = new JLabel();
	JLabel jLabelMittagEssen = new JLabel();
	JLabel jLabelhauptgericht = new JLabel();
	JLabel jLabelBeilage = new JLabel();
	JLabel jLabelDessert = new JLabel();
	JLabel jLabelMittagessenGetraenk = new JLabel();
	JLabel jLabelAbendessen = new JLabel();
	JLabel jLabelBrotBelag = new JLabel();
	JLabel jLabelAbendessenBackwaren = new JLabel();
	JLabel jLabelAbendessenGetraenk = new JLabel();
	JButton jButtonWeiter2 = new JButton();
	TitledBorder titledBorder1;

	// Zum desginer machen
	/*
	 * JList jList1= new JList() ; String listData[] = {"1) Shinguz", "2)
	 * Glapum'tianer", "3) Suffus", "4) Zypanon", "5) Tschung","6) dadada","7)
	 * sdfsdfdf" }; JList jList2 = new JList(listData); JList jList3 = new
	 * JList(); JList jList4 = new JList(); JList jList5 = new JList(); JList
	 * jList6 = new JList(); JList jList7 = new JList(); JList jList8 = new
	 * JList(); JList jList9= new JList(); JList jList10= new JList(); JList
	 * jList11= new JList();
	 */

	// falls man Factory benutzt
	JList jList1;
	JList jList2;
	JList jList3;
	JList jList4;
	JList jList5;
	JList jList6;
	JList jList7;
	JList jList8;
	JList jList9;
	JList jList10;
	JList jList11;

	public JPanelAuswahl(Vector allEatables, ApplicationDiaetplaner ap,
			int kalorien, mainFrame parentFrame) {
		try {

			allEatables_ = allEatables;
			ap_ = ap;
			myJListModelFactory = new JListModelFactory(ap_.getAllEatables());
			chosenEatables = (Vector) allEatables_.clone();
			calories_ = kalorien;
			parentFrame_ = parentFrame;
			jbInit();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {

		// Factory init
		jList1 = new JList(myJListModelFactory.createGroup(0));
		jList2 = new JList(myJListModelFactory.createGroup(1));
		jList3 = new JList(myJListModelFactory.createGroup(2));
		jList4 = new JList(myJListModelFactory.createGroup(3));
		jList5 = new JList(myJListModelFactory.createGroup(4));
		jList6 = new JList(myJListModelFactory.createGroup(5));
		jList7 = new JList(myJListModelFactory.createGroup(6));
		jList8 = new JList(myJListModelFactory.createGroup(7));
		jList9 = new JList(myJListModelFactory.createGroup(8));
		jList10 = new JList(myJListModelFactory.createGroup(9));
		jList11 = new JList(myJListModelFactory.createGroup(10));

		// GruppennamenArray von Application holen
		gruppenName = ap_.getGruppenName();

		titledBorder1 = new TitledBorder("");
		jLabelAnsage.setFont(new java.awt.Font("SansSerif", 1, 12));
		jLabelAnsage.setBorder(BorderFactory.createRaisedBevelBorder());
		jLabelAnsage.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAnsage.setText("Wählen Sie nun die gewünschten Gerichte aus");
		this.setLayout(xYLayout1);
		jLabelFruehstueck.setBorder(BorderFactory.createEtchedBorder());
		jLabelFruehstueck.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelFruehstueck.setText("Fruehstück");
		jLabelFruehstueckMilchprodukte.setBorder(BorderFactory
				.createEtchedBorder());
		jLabelFruehstueckMilchprodukte.setDebugGraphicsOptions(0);
		jLabelFruehstueckMilchprodukte.setVerifyInputWhenFocusTarget(true);
		jLabelFruehstueckMilchprodukte
				.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelFruehstueckMilchprodukte.setText(gruppenName[0]);
		jLabelFruehstueckCerealien
				.setBorder(BorderFactory.createEtchedBorder());
		jLabelFruehstueckCerealien
				.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelFruehstueckCerealien.setText(gruppenName[1]);
		jLabelFruehstueckBackwaren
				.setBorder(BorderFactory.createEtchedBorder());
		jLabelFruehstueckBackwaren
				.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelFruehstueckBackwaren.setText("Backwaren");
		jLabelFruehstueckGetraenk.setBorder(BorderFactory.createEtchedBorder());
		jLabelFruehstueckGetraenk.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelFruehstueckGetraenk.setText("Getränk");

		// Für jede JList eine SCrollpane erschaffen und zuweisen
		JScrollPane myJScrollPane1 = new JScrollPane(jList1);
		JScrollPane myJScrollPane2 = new JScrollPane(jList2);
		JScrollPane myJScrollPane3 = new JScrollPane(jList3);
		JScrollPane myJScrollPane4 = new JScrollPane(jList4);
		JScrollPane myJScrollPane5 = new JScrollPane(jList5);
		JScrollPane myJScrollPane6 = new JScrollPane(jList6);
		JScrollPane myJScrollPane7 = new JScrollPane(jList7);
		JScrollPane myJScrollPane8 = new JScrollPane(jList8);
		JScrollPane myJScrollPane9 = new JScrollPane(jList9);
		JScrollPane myJScrollPane10 = new JScrollPane(jList10);
		JScrollPane myJScrollPane11 = new JScrollPane(jList11);

		xYLayout1.setWidth(781);
		xYLayout1.setHeight(533);
		jLabelMittagEssen.setBorder(BorderFactory.createEtchedBorder());
		jLabelMittagEssen.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMittagEssen.setText("Mittagessen");
		jLabelhauptgericht.setBorder(BorderFactory.createEtchedBorder());
		jLabelhauptgericht.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelhauptgericht.setText(gruppenName[4]);
		jLabelBeilage.setBorder(BorderFactory.createEtchedBorder());
		jLabelBeilage.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBeilage.setText(gruppenName[5]);
		jLabelDessert.setBorder(BorderFactory.createEtchedBorder());
		jLabelDessert.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDessert.setText(gruppenName[6]);
		jLabelMittagessenGetraenk.setBorder(BorderFactory.createEtchedBorder());
		jLabelMittagessenGetraenk.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMittagessenGetraenk.setText("Getränk");
		jLabelAbendessen.setBorder(BorderFactory.createEtchedBorder());
		jLabelAbendessen.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAbendessen.setText("Abendessen");
		jLabelBrotBelag.setEnabled(true);
		jLabelBrotBelag.setBorder(BorderFactory.createEtchedBorder());
		jLabelBrotBelag.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBrotBelag.setText(gruppenName[8]);
		jLabelAbendessenBackwaren.setBorder(BorderFactory.createEtchedBorder());
		jLabelAbendessenBackwaren.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAbendessenBackwaren.setText("Backwaren");
		jLabelAbendessenGetraenk.setBorder(BorderFactory.createEtchedBorder());
		jLabelAbendessenGetraenk.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAbendessenGetraenk.setText("Getränk");
		jButtonWeiter2.setBorder(BorderFactory.createRaisedBevelBorder());
		jButtonWeiter2.setText("Weiter zum Ergebnis");
		jButtonWeiter2
				.addActionListener(new JPanelAuswahl_jButtonWeiter2_actionAdapter(
						this));
		this.add(myJScrollPane1, new XYConstraints(87, 106, 171, 95));
		this.add(jLabelFruehstueckCerealien,
				new XYConstraints(264, 86, 158, 18));
		this
				.add(jLabelFruehstueckGetraenk, new XYConstraints(587, 83, 164,
						-1));
		this.add(jLabelAnsage, new XYConstraints(247, 29, 310, 31));
		// this.add(jList2, new XYConstraints(264, 107, 156, 64));

		this.add(myJScrollPane2, new XYConstraints(264, 107, 156, 93));
		this.add(myJScrollPane3, new XYConstraints(427, 106, 155, 95));
		this.add(myJScrollPane4, new XYConstraints(589, 105, 165, 95));
		this.add(jLabelFruehstueckMilchprodukte, new XYConstraints(87, 86, 172,
				-1));
		this.add(jLabelFruehstueckBackwaren,
				new XYConstraints(429, 86, 154, -1));
		this.add(myJScrollPane9, new XYConstraints(261, 375, 162, 94));
		this.add(myJScrollPane10, new XYConstraints(432, 375, 151, 95));
		this.add(myJScrollPane11, new XYConstraints(589, 374, 168, 97));
		this.add(jLabelBrotBelag, new XYConstraints(263, 346, 162, -1));
		this.add(jLabelAbendessenBackwaren,
				new XYConstraints(434, 347, 153, -1));
		this
				.add(jLabelAbendessenGetraenk, new XYConstraints(590, 347, 166,
						-1));
		this.add(myJScrollPane7, new XYConstraints(432, 239, 153, 92));
		this.add(myJScrollPane8, new XYConstraints(591, 241, 166, 90));
		this.add(jLabelMittagessenGetraenk,
				new XYConstraints(591, 215, 167, -1));
		this.add(jLabelDessert, new XYConstraints(434, 214, 152, -1));
		this.add(myJScrollPane6, new XYConstraints(269, 242, 155, 90));
		this.add(myJScrollPane5, new XYConstraints(87, 242, 172, 88));
		this.add(jLabelBeilage, new XYConstraints(267, 214, 156, -1));
		this.add(jLabelhauptgericht, new XYConstraints(87, 215, 171, -1));
		this.add(jLabelFruehstueck, new XYConstraints(8, 147, 67, -1));
		this.add(jLabelMittagEssen, new XYConstraints(8, 277, 68, 21));
		this.add(jLabelAbendessen, new XYConstraints(5, 401, 74, -1));
		this.add(jButtonWeiter2, new XYConstraints(592, 491, 166, 32));

	}

	public void createChosenVector() {

		this.subtractJListElems(jList1);
		this.subtractJListElems(jList2);
		this.subtractJListElems(jList3);
		this.subtractJListElems(jList4);
		this.subtractJListElems(jList5);
		this.subtractJListElems(jList6);
		this.subtractJListElems(jList7);
		this.subtractJListElems(jList8);
		this.subtractJListElems(jList9);
		this.subtractJListElems(jList10);
		this.subtractJListElems(jList11);

		for (int i = 0; i < chosenEatables.size(); i++) {
			if (chosenEatables.get(i) != null) {
				Element myElement = (Element) chosenEatables.get(i);
				String testtemp = myElement.getChild("Name").getText();

			}
		}

	}

	public void subtractJListElems(JList thisJList) {
		int numbend;
		int ID;
		Integer temp;
		String value;
		String strID;
		int lmSize;
		ListModel lm = thisJList.getModel();
		int[] sel = thisJList.getSelectedIndices();
		boolean isSelected = false;
		lmSize = lm.getSize();

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

	void jButtonWeiter2_actionPerformed(ActionEvent e) {
		this.createChosenVector();
		Vector ergebnis;
		try {
			ergebnis = (Vector) ap_.executeCalculation(chosenEatables,
					calories_);
			parentFrame_.closechild(2, this, 0, ergebnis);

		} catch (DiaetplanerException el) {
			el.getMessage();
		}

	}

}

class JPanelAuswahl_jButtonWeiter2_actionAdapter implements
		java.awt.event.ActionListener {
	JPanelAuswahl adaptee;

	JPanelAuswahl_jButtonWeiter2_actionAdapter(JPanelAuswahl adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonWeiter2_actionPerformed(e);
	}
}
