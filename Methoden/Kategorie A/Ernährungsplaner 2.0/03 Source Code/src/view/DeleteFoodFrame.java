package view;

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
 * Class: DeleteFoodFrame
 * </p>
 * <p>
 * Description: Zeigt das Fenster an um Lebensmittel zu löschen
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
public class DeleteFoodFrame extends JPanel {

	private static final long serialVersionUID = -5497297063646792564L;
	private MainFrame parentFrame_;
	private JLabel jLabelAuswaehlenText = new JLabel();
	private XYLayout xYLayout1 = new XYLayout();
	private JList<Object> jListZuLoeschen;
	private JButton jButtonLoeschen = new JButton();
	private JButton jButtonSchliessen = new JButton();
	private JListModelFactory myJListModelFactory;

	public DeleteFoodFrame(MainFrame parentFrame,
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
		myJListModelFactory = new JListModelFactory(parentFrame_.getAllEatables());
		jListZuLoeschen = new JList<Object>(myJListModelFactory.createAll());
		jListZuLoeschen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane myJScrollPane1 = new JScrollPane(jListZuLoeschen);

		jLabelAuswaehlenText.setBorder(BorderFactory.createRaisedBevelBorder());
		jLabelAuswaehlenText.setToolTipText("");
		jLabelAuswaehlenText.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAuswaehlenText.setText("Wählen sie das zu löschende Element aus");
		this.setLayout(xYLayout1);
		xYLayout1.setWidth(523);
		xYLayout1.setHeight(477);
		jButtonLoeschen.setText("Löschen");
		jButtonLoeschen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonDelete_actionPerformed(e);
			}
		});
		jButtonSchliessen.setText("Schliessen");
		jButtonSchliessen
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jButtonClose_actionPerformed(e);
					}
				});
		this.add(jButtonLoeschen, new XYConstraints(220, 400, 90, -1));
		this.add(myJScrollPane1, new XYConstraints(140, 70, 260, 300));
		this.add(jLabelAuswaehlenText, new XYConstraints(140, 30, 260, -1));
		this.add(jButtonSchliessen, new XYConstraints(310, 400, 90, -1));
	}

	private void jButtonDelete_actionPerformed(ActionEvent e) {

		int sel = jListZuLoeschen.getSelectedIndex();
		ListModel<Object> lm = jListZuLoeschen.getModel();
		Element elem;
		int ID;
		Integer temp;
		String value;
		String strID;

		value = (String) lm.getElementAt(sel);
		strID = value.substring(0, value.indexOf(")"));
		temp = new Integer(strID);
		ID = temp.intValue();

		elem = (Element) parentFrame_.getAllEatables().get(ID);

		if (parentFrame_.deleteEatable(elem) == true) {
			JOptionPane.showMessageDialog(null, "Das Lebensmittel " + value
					+ " wurde erfolgreich geloescht!");
			myJListModelFactory = new JListModelFactory(parentFrame_.getAllEatables());
			jListZuLoeschen.setListData(myJListModelFactory.createAll());
		} else {
			JOptionPane
					.showMessageDialog(
							null,
							"Das Lebensmittel "
									+ value
									+ " konnte nicht gelöscht werden - bitte beachten: In jeder Gruppe muss ein Lebensmittel vorhanden sein!");
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		parentFrame_.closechild(5, this, 0, null);
	}

}
