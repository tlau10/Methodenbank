package de.fh_konstanz.simubus.controller;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.WindowConstants;

import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.view.BuslinieDetail;
import de.fh_konstanz.simubus.view.SimuControlPanel;

/**
 * @author Robert Audersetz
 * 
 *         Listener fuer ListBoxen in Datenansicht. Dieser Listener reagiert auf
 *         das Doppelklicken auf eine Buslienie in der Bussimulation und öffnet
 *         dann die Detailübersicht
 */
public class SimuListBoxActionListener extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent evt) {
		SimuControlPanel.getInstance().updateTeilstreckenList();
		@SuppressWarnings("unchecked")
		JList<Object> list = (JList<Object>) evt.getSource();
		String listName = list.getName();

		// Liste mit allen Buslinien
		if (listName.equals("buslinien")) {
			if (list.getMaxSelectionIndex() != -1) {
				Linie bus = (Linie) list.getSelectedValue();

				if (evt.getClickCount() == 2 || evt.getButton() == MouseEvent.BUTTON3) {
					BuslinieDetail f = new BuslinieDetail(bus);
					f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
					f.setSize(new Dimension(600, 600));
					f.setBounds(300, 100, 600, 600);
					f.setTitle("Details von Buslinie: " + bus.getId());
					f.setResizable(false);
					f.setVisible(true);
				}
			}
		}
	}
}
