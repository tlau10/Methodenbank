package de.fh_konstanz.simubus.controller;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.GraphConstants;

import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.HaltestellenTable;
import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.view.HaltestellenCell;

/**
 * Die Klasse <code>HaltestelleListener</code> reagiert darauf, wenn eine
 * Haltestelle dem Modell hinzugefuegt wird, eine Haltestelle verschoben wird
 * oder eine Haltestelle geloescht wird.
 * 
 * @author Daniel Weber, Ingo Kroh
 * @version 1.0 (22.06.2006)
 */

public class HaltestelleListener implements GraphModelListener {

	public void graphChanged(GraphModelEvent e) {
		Object[] changed = e.getChange().getChanged();
		Object[] deleted = e.getChange().getRemoved();

		Planquadrat[][] planquadrate = VirtualGrid.getInstance().getPlanquadrate();

		if (deleted != null) {

			for (int i = 0; i < deleted.length; i++) {
				if (deleted[i] instanceof HaltestellenCell) {
					HaltestellenCell z = (HaltestellenCell) deleted[i];
					planquadrate[z.getKoordinaten().x][z.getKoordinaten().y].setEmpty();

					for (int j = 0; j < planquadrate.length; j++) {
						for (int k = 0; k < planquadrate[j].length; k++) {
							Strassennetz.getInstance()
									.removeHaltestelle(new Haltestelle(planquadrate[j][k].getPlanquadratCoordinates()));

							if (planquadrate[j][k].getBefore() == Planquadrat.IS_EMPTY) {
								Strassennetz.getInstance().removeStrasse(planquadrate[j][k]);
							}
						}
					}

				}
			}

		} else {
			for (int i = 0; i < changed.length; i++) {
				if (changed[i].toString() != null) {
					if (changed[i] instanceof HaltestellenCell) {
						HaltestellenCell haltestelleCell = (HaltestellenCell) changed[i];

						for (int j = 0; j < planquadrate.length; j++) {
							for (int k = 0; k < planquadrate[j].length; k++) {
								Rectangle2D rec = GraphConstants
										.getBounds(((HaltestellenCell) changed[i]).getAttributes());

								Rectangle2D.Double r = planquadrate[j][k].getBounds();

								if (r.contains(new Point2D.Double(rec.getCenterX(), rec.getCenterY()))) {
									Haltestelle h = new Haltestelle(haltestelleCell.getId(),
											planquadrate[j][k].getPlanquadratX(), planquadrate[j][k].getPlanquadratY(),
											haltestelleCell.getName(), haltestelleCell.getKapazitaet());

									planquadrate[j][k].setHaltestelle();

									Strassennetz.getInstance().addHaltestelle(h);

									if (Strassennetz.getInstance().getStrassenListePlanquadrat()
											.contains(planquadrate[j][k]) == false) {
										Strassennetz.getInstance().addStrasse(planquadrate[j][k]);
									}

									planquadrate[j][k].setID(((HaltestellenCell) changed[i]).getId());
									haltestelleCell.setKoordinaten(planquadrate[j][k].getPlanquadratX(),
											planquadrate[j][k].getPlanquadratY());

								} else if (planquadrate[j][k].isHaltestelle == true
										&& planquadrate[j][k].getID() == ((HaltestellenCell) changed[i]).getId()) {

									planquadrate[j][k].setEmpty();
									Strassennetz.getInstance().removeHaltestelle(
											new Haltestelle(planquadrate[j][k].getPlanquadratCoordinates()));

									if (planquadrate[j][k].getBefore() == Planquadrat.IS_EMPTY) {
										Strassennetz.getInstance().removeStrasse(planquadrate[j][k]);
									}

								}
								ArrayList<Haltestelle> halteStellen = new ArrayList<Haltestelle>(
										Strassennetz.getInstance().getAlleHaltestellen());
								HaltestellenTable.getInstance().setHaltestellen(halteStellen);
								HaltestellenTable.getInstance().fireTableDataChanged();
							}
						}
					}
				}
			}
		}
	}
}
