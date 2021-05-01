package de.fh_konstanz.simubus.controller;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.fh_konstanz.simubus.view.SimuPanel;

/**
 * Die Klasse <code>HaltestelleTableController</code> reagiert darauf, wenn sich
 * der Inhalt der Haltestellen-Tabelle aendert.
 * 
 * @author Daniel Weber
 * @version 1.0 (03.07.2006)
 *
 */
public class HaltestellenTableController implements TableModelListener, ListSelectionListener {

	public void tableChanged(TableModelEvent evt) {
		// Fall wird nicht berücksichtigt, da er nicht auftritt
	}

	public void valueChanged(ListSelectionEvent evt) {
		ListSelectionModel lsm = (ListSelectionModel) evt.getSource();
		SimuPanel simu = SimuPanel.getInstance();
		int index = lsm.getMinSelectionIndex() + 1;
		simu.selectHaltestelle(String.valueOf(index));
	}

}
