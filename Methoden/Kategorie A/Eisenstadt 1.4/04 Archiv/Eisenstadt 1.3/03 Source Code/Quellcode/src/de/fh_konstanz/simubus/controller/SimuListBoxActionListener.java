package de.fh_konstanz.simubus.controller;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.WindowConstants;

import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.view.BuslinieDetail;
import de.fh_konstanz.simubus.view.OptiControlPanel;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import de.fh_konstanz.simubus.view.SimuPanel;

/**
 * @author Robert Audersetz
 * 
 * Listener fuer ListBoxen in Datenansicht.
 */
public class SimuListBoxActionListener extends MouseAdapter {

	private SimuControlPanel simuControlPanel = null;

	private OptiControlPanel optiControlPanel = null;

	/**
	 * Konstruktor
	 * 
	 * @param simuPanel Panel fuer Einstellungen zur Simulation
	 */
	public SimuListBoxActionListener(SimuControlPanel simuPanel) {
		this.simuControlPanel = simuPanel;
	}

	/**
	 * Konstruktor
	 * 
	 * @param optiControlPanel Panel fuer Einstellungen zur Optimierung
	 */
	public SimuListBoxActionListener(OptiControlPanel optiControlPanel) {
		this.optiControlPanel = optiControlPanel;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

		JList list = (JList) evt.getSource();
		String listName = list.getName();

		// Liste mit allen Buslinien
		if (listName.equals("buslinien")) {
			if (list.getMaxSelectionIndex() != -1) {
				Linie bus = (Linie) list.getSelectedValue();

				if (evt.getClickCount() == 2
						|| evt.getButton() == MouseEvent.BUTTON3) {
					BuslinieDetail f = new BuslinieDetail(bus);
					f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
					f.setSize(new Dimension(600, 600));
					f.setBounds(300, 100, 600, 600);
					f.setTitle("Details von Buslinie: " + bus.getId());
					f.setResizable(false);
					f.setVisible(true);
				}
				if (evt.getClickCount() == 1
						|| evt.getButton() == MouseEvent.BUTTON3) {
					simuControlPanel.updateTeilstreckenList();
					SimuPanel.getInstance().selectAllLinien();
				}
			}
		} else if (listName.equals("teilstrecken")) {
			if (evt.getClickCount() == 1
					|| evt.getButton() == MouseEvent.BUTTON3) {
				simuControlPanel.getSelectedTeilstrecke();
				simuControlPanel.updateTeilstrecke();
			}
		}
		
	}

}
