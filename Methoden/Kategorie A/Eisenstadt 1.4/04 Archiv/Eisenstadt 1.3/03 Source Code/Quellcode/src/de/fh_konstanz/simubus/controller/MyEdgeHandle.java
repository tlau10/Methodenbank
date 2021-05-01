package de.fh_konstanz.simubus.controller;

import java.awt.event.MouseEvent;

import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphContext;

/**
 * Die Klasse <code>MyEdgeHandle</code> reagiert auf Maus-Ereignisse, wenn der
 * Benutzer einen Punkt auf eine Linie setzt bzw. löscht
 * 
 * @author Daniel Weber
 * 
 */
public class MyEdgeHandle extends EdgeView.EdgeHandle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1885101398046865868L;

	private static MyEdgeHandle instance;

	private EdgeView edgeView;

	public MyEdgeHandle(EdgeView edge, GraphContext ctx) {
		super(edge, ctx);
		edgeView = edge;
		instance = this;
	}

	public static MyEdgeHandle getInstance() {
		return instance;
	}

	public EdgeView getEdgeView() {
		return this.edgeView;
	}

	@Override
	public boolean isAddPointEvent(MouseEvent event) {
		// Points are Added using Shift-Click
		return event.isShiftDown();
	}

	// Override Superclass Method
	@Override
	public boolean isRemovePointEvent(MouseEvent event) {
		// Points are Removed using Shift-Click
		return event.isShiftDown();
	}

}