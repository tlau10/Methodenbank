/*
 * Created on 06.04.2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.fh_konstanz.simubus.view;

import java.awt.Graphics;

import org.jgraph.JGraph;
import org.jgraph.plaf.basic.BasicGraphUI;

;
/**
 * Die Klasse <code>MyGraphUI</code> wird für das Einbinden des
 * Hintergrundbildes benötigt.
 * 
 * @author Daniel Weber
 */
public class MyGraphUI extends BasicGraphUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4405025074930864532L;

	/**
	 * liefert einen Graph
	 * @return einen Graph
	 */
	public JGraph getGraph() {
		return graph;
	}

	/**
	 * This will override the paintBackground to paint an image when it is
	 * present in GraphDesign
	 */
	@Override
	protected void paintBackground(Graphics g) {
		//Image image = ((SimuGraph) getGraph()).getBackgroundImage().getImage();
		super.paintBackground(g);
//		if (image != null) {
//			double s = graph.getScale();
//			Graphics2D g2 = (Graphics2D) g;
//			AffineTransform tmp = g2.getTransform();
//			g2.scale(s, s);
//			g2.setComposite(makeComposite(((SimuGraph) getGraph())
//					.getBackgroundImageTransparency()));// set
//			// the
//			// transparency
//			g2.drawImage(image, 0, 0, getGraph().getWidth(), getGraph()
//					.getHeight(), graph);
//			g2.setTransform(tmp);
//		}
	}

	/*
	 * wird für die Transparenz des Hintergrundbildes benötigt
	 * @param alpha
	 * @return
	 */
//	private AlphaComposite makeComposite(float alpha) {
//		int type = AlphaComposite.SRC_OVER;
//		return (AlphaComposite.getInstance(type, alpha));
//	}
}
