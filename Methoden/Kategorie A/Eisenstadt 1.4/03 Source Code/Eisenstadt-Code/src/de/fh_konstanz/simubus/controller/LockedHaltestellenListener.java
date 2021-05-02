package de.fh_konstanz.simubus.controller;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.GraphConstants;

import de.fh_konstanz.simubus.model.GesperrteHaltestelle;
import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.view.GesperrteHaltestelleCell;

/**
 * Die Klasse <code>LockedHaltestellenListener</code> reagiert darauf, falls
 * eine gesperrte Haltestelle in das Modell eingefuegt, verschoben oder
 * geloescht wird.
 * 
 * @author Daniel Weber, Ingo Kroh
 * @version 1.0 (06.06.2006)
 */
public class LockedHaltestellenListener implements GraphModelListener {

	public void graphChanged(GraphModelEvent arg0) {

		Object[] changed = arg0.getChange().getChanged();
		Object[] deleted = arg0.getChange().getRemoved();
		Planquadrat[][] planquadrate = VirtualGrid.getInstance().getPlanquadrate();
		if (deleted != null) {
			for (int i = 0; i < deleted.length; i++) {
				if (deleted[i] instanceof GesperrteHaltestelleCell) {
					GesperrteHaltestelleCell g = (GesperrteHaltestelleCell) deleted[i];
					planquadrate[g.getKoordinaten().x][g.getKoordinaten().y].setEmpty();
				}
			}
		} else {
			for (int i = 0; i < changed.length; i++) {
				if (changed[i].toString() != null) {
					if (changed[i] instanceof GesperrteHaltestelleCell) {
						GesperrteHaltestelleCell z = (GesperrteHaltestelleCell) changed[i];

						planquadrate = VirtualGrid.getInstance().getPlanquadrate();

						for (int j = 0; j < planquadrate.length; j++) {
							for (int k = 0; k < planquadrate[j].length; k++) {
								Rectangle2D rec = GraphConstants
										.getBounds(((GesperrteHaltestelleCell) changed[i]).getAttributes());

								Rectangle2D.Double r = planquadrate[j][k].getBounds();

								if (planquadrate[j][k].isLockedHaltestelle == true && planquadrate[j][k]
										.getID() == ((GesperrteHaltestelleCell) changed[i]).getId()) {
									GesperrteHaltestelle gesperrteHaltestelle = new GesperrteHaltestelle(
											planquadrate[j][k].getPlanquadratCoordinates(), z.getId());
									planquadrate[j][k].setEmpty();
									Strassennetz.getInstance().removeGesperrteHaltestelle(gesperrteHaltestelle);

								}

								else if (r.contains(new Point2D.Double(rec.getCenterX(), rec.getCenterY()))) {
									GesperrteHaltestelle gesperrteHaltestelle = new GesperrteHaltestelle(
											planquadrate[j][k].getPlanquadratCoordinates(), z.getId());
									planquadrate[j][k].isLockedHaltestelle();
									Strassennetz.getInstance().addGesperrteHaltestelle(gesperrteHaltestelle);
									planquadrate[j][k].setID(((GesperrteHaltestelleCell) changed[i]).getId());

									z.setKoordinaten(planquadrate[j][k].getPlanquadratX(),
											planquadrate[j][k].getPlanquadratY());

								}

							}
						}
					}
				}
			}
		}

	}

}
