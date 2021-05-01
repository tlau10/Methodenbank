package de.fh_konstanz.simubus.controller;

import java.awt.geom.Rectangle2D;

import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.EdgeView;

import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.view.GesperrteFelderEdge;
import de.fh_konstanz.simubus.view.SimuGraph;
import de.fh_konstanz.simubus.view.SimuPanel;

/**
 * Die Klasse <code>LockedPlanquadratListener</code> reagiert auf Ereignisse,
 * wenn ein gesperrtes Feld hinzugefuegt, verschoben oder geloescht wird.
 * 
 * @author Michael Franz
 * @version 1.0 (29.06.2006)
 */

public class LockedPlanquadratListener implements GraphModelListener {

	private Strassennetz netz = Strassennetz.getInstance();

	public void graphChanged(GraphModelEvent arg0) {
		SimuGraph graph = SimuPanel.getInstance().getGraph();

		Object[] changed = arg0.getChange().getChanged();
		Object[] deleted = arg0.getChange().getRemoved();
		Planquadrat[][] planquadrate = VirtualGrid.getInstance().getPlanquadrate();
		if (deleted != null) {
			for (int i = 0; i < deleted.length; i++) {
				if (deleted[i] instanceof GesperrteFelderEdge) {
					for (int j = 0; j < planquadrate.length; j++) {
						for (int k = 0; k < planquadrate[j].length; k++) {
							GesperrteFelderEdge edge = (GesperrteFelderEdge) deleted[i];
							if (planquadrate[j][k].isGesperrt && (planquadrate[j][k].getID() == edge.getId())) {

								netz.removeFeldGesperrt(planquadrate[j][k]);

								planquadrate[j][k].setEmpty();
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < changed.length; i++) {
			if (changed[i].toString() != null) {
				if (changed[i] instanceof GesperrteFelderEdge) {
					GesperrteFelderEdge edge = (GesperrteFelderEdge) changed[i];

					for (int j = 0; j < planquadrate.length; j++) {
						for (int k = 0; k < planquadrate[j].length; k++) {
							EdgeView edgeView = MyEdgeHandle.getInstance().getEdgeView();
							Rectangle2D r = planquadrate[j][k].getBounds();

							if (edgeView.intersects(graph, r) == true) {

								if (!planquadrate[j][k].isGesperrt) {
									netz.addFeldGesperrt(planquadrate[j][k]);

									planquadrate[j][k].setID(edge.getId());
									planquadrate[j][k].setGesperrt();
								}

							} else {
								if (planquadrate[j][k].isGesperrt) {
									netz.removeFeldGesperrt(planquadrate[j][k]);
									planquadrate[j][k].setEmpty();

								}
							}
						}
					}
				}
			}
		}
	}
}
