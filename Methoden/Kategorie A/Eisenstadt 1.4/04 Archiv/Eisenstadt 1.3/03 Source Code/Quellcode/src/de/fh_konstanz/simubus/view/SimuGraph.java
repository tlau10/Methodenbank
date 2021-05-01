package de.fh_konstanz.simubus.view;

import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.ToolTipManager;

import org.jgraph.JGraph;

;
/**
 * Die Klasse <code>SimuGraph</code> ist der Graph der gezeichnet wird.
 * 
 * @author Daniel Weber
 * @version 1.0 (01.07.2005)
 */
public class SimuGraph extends JGraph {

	/**
	 * ID zur Serialisierung
	 */
	private static final long serialVersionUID = 8252248299836756788L;

	/**
	 * Das Hintergrundbild (Karte)
	 */
	private  ImageIcon background = null;

	/**
	 * die Transparenz des Hintergrundbildes
	 */
	private float bgtransparency = 0.8f;
	

	@Override
	public void updateUI() {
		setUI(new MyGraphUI());
		invalidate();
		setVisible(true);
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	/**
	 * setzt das Hintergrundbild (Karte)
	 * 
	 * @param img
	 */
	public void setBackgroundImage(Image img) {
		ImageIcon img2 = new ImageIcon(img);
		background = img2;
		invalidate();
		repaint();
	}

	/**
	 * liefert das Hintergrundbild
	 * 
	 * @return das Hintergrundbild
	 */
	public  ImageIcon getBackgroundImage() {
		return background;
	}

	/**
	 * liefert die Transparenz des Hintergrundbildes
	 * 
	 * @return die Transparenz des Hintergrundbildes
	 */
	public float getBackgroundImageTransparency() {
		return bgtransparency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgraph.JGraph#getToolTipText(java.awt.event.MouseEvent)
	 */
	public String getToolTipText(MouseEvent e) {
		if (e != null) {
			Object cell = getFirstCellForLocation(e.getX(), e.getY());
			if (cell instanceof HaltestellenCell) {
				return ((HaltestellenCell) cell).getToolTipString();
			}
		}
		return null;
	}
}
