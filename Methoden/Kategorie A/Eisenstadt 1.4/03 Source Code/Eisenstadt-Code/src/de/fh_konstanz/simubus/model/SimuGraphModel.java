package de.fh_konstanz.simubus.model;

import java.io.Serializable;

import org.jgraph.graph.DefaultGraphModel;

import de.fh_konstanz.simubus.view.SimuPanel;

/**
 * Die Klasse <code>SimuGraphModel</code> implementiert das Model eines Graphen
 * auf derOberfläche.
 * 
 * 
 * @author Ingo Kroh, Michael Franz, Daniel Weber
 * 
 */
public class SimuGraphModel extends DefaultGraphModel implements Serializable {

	/**
	 * ID für Serialisierung
	 */
	private static final long serialVersionUID = -4005313924522580788L;

	/**
	 * Singleton Instanz
	 */
	private static SimuGraphModel instance;

	/**
	 * liefert ein <code>SimuGraphModel</code>-Objekt
	 * 
	 * @return ein <code>SimuGraphModel</code>-Objekt
	 */
	public static SimuGraphModel getInstance() {
		if (instance == null) {
			instance = new SimuGraphModel();
		}
		return instance;
	}

	/**
	 * setzt die Singleton Instanz
	 * 
	 * @param graphModel
	 */
	public static void setInstance(SimuGraphModel graphModel) {
		SimuGraphModel.instance = graphModel;
		SimuPanel.getInstance().setGraphModel(instance);
		// SimuPanel.getInstance().getGraph().getGraphLayoutCache().reload();
	}

}
