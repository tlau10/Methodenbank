package Dakin;

import java.awt.*;
import javax.swing.*;

//////////////////////////////////////////////////
// SS13 ==> Klassen wurden von InternalFrames zu JPanels geändert
// für die Darstellung in den Tabs
//////////////////////////////////////////////////
public class DakinAusgabe extends JPanel {
	private TreeDakin tD; // Wurzel eines Baumes vom Typ TreeDakin
	private TheTreeComponent tTC; // Eigene Komponente fuer Baum
	

	public DakinAusgabe() {
		super();

		tTC = new TheTreeComponent(null); // beim anlegen des Fensters
		setLayout(new BorderLayout());
		
		add(new JScrollPane(tTC, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);

	}

	// wird nach "Berechnen gerufen"
	void setTree(TreeDakin treeDakin) {
		try {
			tD = treeDakin;
			tTC.update(treeDakin);

		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}
	}
}

// Klasse von H.W.
// Abgeleitet von JPanel
class TheTreeComponent extends JPanel {
	private CRectTree rechteckBaum; // Baum
	
	public TheTreeComponent(TreeDakin dT) {
		super();
		rechteckBaum = null;
		
		if (dT != null) {
			rechteckBaum = new CRectTree(dT);
			setSize(rechteckBaum.getHeight(), rechteckBaum.getWidth());
		}
		setBackground(Color.white);
	}

	public void update(TreeDakin dT) {
		if (dT != null) {
			rechteckBaum = new CRectTree(dT);
			setBorder(BorderFactory.createLineBorder(Color.black));
			repaint();
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (rechteckBaum != null) {
			rechteckBaum.drawMe(g);
			setSize(rechteckBaum.getWidth(), rechteckBaum.getHeight());
		}
	}
}