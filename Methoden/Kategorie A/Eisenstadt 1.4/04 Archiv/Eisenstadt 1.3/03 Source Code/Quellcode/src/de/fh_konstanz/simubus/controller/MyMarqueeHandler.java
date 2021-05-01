package de.fh_konstanz.simubus.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;

import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.HaltestellenTable;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.view.OptiControlPanel;
import de.fh_konstanz.simubus.view.PopUpMenu;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import de.fh_konstanz.simubus.view.SimuGraph;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.View;

/**
 * Die Klasse <code>MyMarqueeHandler</code> reagiert auf Maus-Ereignisse, wie
 * das Selektieren von Knoten und Kanten. Ebenso reagiert diese Klasse auf
 * Änderungen, wenn mit der Maus über eine Haltestelle gefahren wird, sowie das
 * Verbinden zweier Haltestellen.
 * 
 * @author Daniel Weber
 * 
 */
public class MyMarqueeHandler extends BasicMarqueeHandler {
	
	/** Der Startpunkt wenn zwei Haltestellen verbunden werden */ 
	private Point2D start;
	
	/** Der aktuelle Punkt, wenn zwei Haltestellen verbunden werden*/
	private Point2D current;

	/** Der aktuelle Port */
	private PortView port;
	
	/** Der erste Port */
	private PortView firstPort;
	
	// Override to Gain Control (for PopupMenu and ConnectMode)
	@Override
	public boolean isForceMarqueeEvent(MouseEvent e) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
		
		if (e.isShiftDown())
			return false;
		// If Right Mouse Button we want to Display the PopupMenu
		if (SwingUtilities.isRightMouseButton(e))
			// Return Immediately
			return true;
		// Find and Remember Port
		port = getSourcePortAt(e.getPoint());
		// If Port Found and in ConnectMode (=Ports Visible)
		if (port != null && graph.isPortsVisible())
			return true;
		// Else Call Superclass
		return super.isForceMarqueeEvent(e);
	}

	// Display PopupMenu or Remember Start Location and First Port
	@Override
	public void mousePressed(final MouseEvent e) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
//		SimuControlPanel simuControlPanel = SimuControlPanel.getInstance();

		/**
		 * reagiere auf die linke Maustaste bei gesetzem JToggleButton
		 * @author Michael Litera
		 */
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (port != null && graph.isPortsVisible()) {
				// Remember Start Location
				start = graph.toScreen(port.getLocation());
				// Remember First Port
				firstPort = port;
			}
			else {
				Logger.getInstance().log("Postion pressed Left Mouse: " + e.getX() +":" + e.getY());
	
				//JTabbedPane linien = findJPanelWithName(simuControlPanel, "simuControlPanel");
				
				JToggleButton zieleEinfuegen = findJToggleButtonWithName(View.getInstance()
						.getContentPane(), "Ziele einfügen");
				JToggleButton haltestellenEinfuegen = findJToggleButtonWithName(View.getInstance()
						.getContentPane(), "Haltestelle einfügen");
				JToggleButton strasseEinfuegen = findJToggleButtonWithName(View.getInstance()
						.getContentPane(), "Strasse einfügen");
				JToggleButton strasseGesperrt = findJToggleButtonWithName(View.getInstance()
						.getContentPane(), "gesperrte Strasse einfügen");
				JToggleButton halteStellegesperrt = findJToggleButtonWithName(View.getInstance()
						.getContentPane(), "gesperrte Haltestelle einfügen");
				JToggleButton loeschen = findJToggleButtonWithName(View.getInstance()
						.getContentPane(), "loeschen");			
				
				try {
					//if (simuPanel.isVisible() == true &&  linien.getSelectedIndex()!=1){
//						if (true){
							
						if (zieleEinfuegen.isSelected()){
							Logger.getInstance().log("JToggleButton: Ziele einfügen aktiviert");
							simuPanel.insertZiel( e.getPoint() );	
						}
						else if (haltestellenEinfuegen.isSelected()){
							Logger.getInstance().log("JToggleButton: Haltestellen einfügen aktviert");
							
							//Haltestellen einfügen
							Collection<Haltestelle> h = Strassennetz.getInstance().getAlleHaltestellen();
				            int id = h.size() + 1;
				            simuPanel.insertHaltestelle( e.getPoint(), "Haltestelle " + id );						
						}
						else if (strasseEinfuegen.isSelected()){
							Logger.getInstance().log("Button: Haltestellen einfügen aktviert");
							
							// Strasse hinzufügen
				            simuPanel.insertStrasse(  e.getPoint() );						
						}
						else if (strasseGesperrt.isSelected()){
							Logger.getInstance().log("Button: gesperrte Strasse einfügen aktviert");
							
							// Strasse hinzufügen
				            simuPanel.insertGesperrteFelder(  e.getPoint() );						
						}
						else if (halteStellegesperrt.isSelected()){
							Logger.getInstance().log("Button: gesperrte Haltestellen einfügen aktviert");
							
							// Strasse hinzufügen
				            simuPanel.insertGesperrteHaltestelle(  e.getPoint() );						
						}
						// Bei Doppelklick löschen
						
						//if (e.getClickCount()>1){
						else if (loeschen.isSelected()){
								Logger.getInstance().log("Button: loeschen");
							
								// löschen
								if ( !graph.isSelectionEmpty() ){
					                  Object[] cells = graph.getSelectionCells();
					                  cells = graph.getDescendants( cells );
					                  graph.getModel().remove( cells );
					               }
							}
						//}
//					}
					
				} catch (RuntimeException e1) {
					Logger.getInstance().log("Fehler bei der Durchsuchung des Panels nach JToggleButtons" +
							" in MyMargueeHandler,java");
				}
			}
 		}
		if (SwingUtilities.isRightMouseButton(e)) {
			// Find Cell in Model Coordinates
			Object cell = graph.getFirstCellForLocation(e.getX(), e.getY());
			// Create PopupMenu for the Cell
			PopUpMenu popup = new PopUpMenu();
			JPopupMenu menu = popup.createPopupMenu(e.getPoint(), cell);
			// Display PopupMenu
			menu.show(graph, e.getX(), e.getY());
			// Else if in ConnectMode and Remembered Port is Valid
		} 
//		else if (port != null && graph.isPortsVisible()) {
//			// Remember Start Location
//			start = graph.toScreen(port.getLocation());
//			// Remember First Port
//			firstPort = port;
//		} 
		else {
			// Call Superclass
			super.mousePressed(e);
		}
	}

	// Find Port under Mouse and Repaint Connector
	@Override
	public void mouseDragged(MouseEvent e) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
		// If remembered Start Point is Valid
		if (start != null) {
			// Fetch Graphics from Graph
			Graphics g = graph.getGraphics();
			// Reset Remembered Port
			PortView newPort = getTargetPortAt(e.getPoint());
			// Do not flicker (repaint only on real changes)
			if (newPort == null || newPort != port) {
				// Xor-Paint the old Connector (Hide old Connector)
				paintConnector(Color.black, graph.getBackground(), g);
				// If Port was found then Point to Port Location
				port = newPort;
				if (port != null)
					current = graph.toScreen(port.getLocation());
				// Else If no Port was found then Point to Mouse Location
				else
					current = graph.snap(e.getPoint());
				// Xor-Paint the new Connector
				paintConnector(graph.getBackground(), Color.black, g);
			}
		} else {
			;
		}
		// Call Superclass
		super.mouseDragged(e);
	}

	public PortView getSourcePortAt(Point2D point) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
		// Disable jumping
		graph.setJumpToDefaultPort(false);
		PortView result;
		try {
			// Find a Port View in Model Coordinates and Remember
			result = graph.getPortViewAt(point.getX(), point.getY());
		} finally {
			graph.setJumpToDefaultPort(true);
		}
		return result;
	}

	// Find a Cell at point and Return its first Port as a PortView
	public PortView getTargetPortAt(Point2D point) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
		// Find a Port View in Model Coordinates and Remember
		PortView result = null;
		try{
			result = graph.getPortViewAt(point.getX(), point.getY());
		}catch(Exception e){
			
		}
		return result;
	}

	// Connect the First Port and the Current Port in the Graph or Repaint
	@Override
	public void mouseReleased(MouseEvent e) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();

		// If Valid Event, Current and First Port
		if (e != null && port != null && firstPort != null && firstPort != port) {
			// Then Establish Connection

			simuPanel.connect((Port) firstPort.getCell(), (Port) port.getCell());
			e.consume();
			// Else Repaint the Graph
		} else {
			// graph.setGridEnabled(true);
			ArrayList<Haltestelle> halteStellen = new ArrayList<Haltestelle>(
					Strassennetz.getInstance().getAlleHaltestellen());
			HaltestellenTable hs = HaltestellenTable.getInstance();
			hs.setHaltestellen(halteStellen);
			// hs.fireTableDataChanged();
			graph.repaint();
		}
		// Reset Global Vars
		firstPort = port = null;
		start = current = null;
		// Call Superclass
		super.mouseReleased(e);
	}

	// Show Special Cursor if Over Port
	@Override
	public void mouseMoved(MouseEvent e) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
		// Check Mode and Find Port
		if (e != null && getSourcePortAt(e.getPoint()) != null
				&& graph.isPortsVisible()) {
			// Set Cusor on Graph (Automatically Reset)
			// Logger.getInstance().log(e.getX());
			graph.setCursor(new Cursor(Cursor.HAND_CURSOR));
			// Consume Event
			// Note: This is to signal the BasicGraphUI's
			// MouseHandle to stop further event processing.
			e.consume();
		} else
			simuPanel.displayActualPosition(e.getPoint());
		// Call Superclass
		super.mouseMoved(e);
	}

	// Use Xor-Mode on Graphics to Paint Connector
	protected void paintConnector(Color fg, Color bg, Graphics g) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
		// Set Foreground
		g.setColor(fg);
		// Set Xor-Mode Color
		g.setXORMode(bg);
		// Highlight the Current Port
		paintPort(graph.getGraphics());
		// If Valid First Port, Start and Current Point
		if (firstPort != null && start != null && current != null)
			// Then Draw A Line From Start to Current Point
			g.drawLine((int) start.getX(), (int) start.getY(), (int) current
					.getX(), (int) current.getY());
	}

	// Use the Preview Flag to Draw a Highlighted Port
	protected void paintPort(Graphics g) {
		SimuPanel simuPanel = SimuPanel.getInstance();
		SimuGraph graph = simuPanel.getGraph();
		// If Current Port is Valid
		if (port != null) {
			// If Not Floating Port...
			boolean o = (GraphConstants.getOffset(port.getAllAttributes()) != null);
			// ...Then use Parent's Bounds
			Rectangle2D r = (o) ? port.getBounds() : port.getParentView()
					.getBounds();
			// Scale from Model to Screen
			r = graph.toScreen((Rectangle2D) r.clone());
			// Add Space For the Highlight Border
			r.setFrame(r.getX() - 3, r.getY() - 3, r.getWidth() + 6, r
					.getHeight() + 6);

			// Paint Port in Preview (=Highlight) Mode
			graph.getUI().paintCell(g, port, r, true);
		}
	}
	
	/**
	 * sucht im Container nach JToggleButton
	 * 
	 * @param Container
	 * @param SearchName of JToggleButton
	 * @return JToggleButton
	 * @author Michael Litera
	 * @version 1.0 (01.06.2006)
	 */
	public JToggleButton findJToggleButtonWithName(Container container, String jToggleButtonText) {
	    Component[] components = container.getComponents();

	    JToggleButton buttonToFind = null;
	    for (int i = 0; i < components.length; i++) {
	      Component component = components[i];

	      // if the component is a container, find out if it contains the JButton
	      if (component instanceof Container) {
	        buttonToFind = findJToggleButtonWithName((Container) component, jToggleButtonText);
	        if (buttonToFind != null) break;
	      }

	      // if the component is a JButton, find out if it contains the text
	      if (component instanceof JToggleButton) {
	        JToggleButton button = (JToggleButton) component;
	        // compare the name with that passed in
	        if ( button.getName().equals(jToggleButtonText) ) {
	          buttonToFind = button;
	          break;
	        }
	      }
	    }
	    return buttonToFind;
	  }
	
//	public JTabbedPane findJPanelWithName(Container container, String JPanelText) {
//	    Component[] components = container.getComponents();
//
//	    JTabbedPane TabToFind = null;
//	    for (int i = 0; i < components.length; i++) {
//	      Component component = components[i];
//
//	      // if the component is a container, find out if it contains the JButton
//	      if (component instanceof Container) {
//	        TabToFind = findJPanelWithName((Container) component, JPanelText);
//	        if (TabToFind != null) break;
//	      }
//
//	      // if the component is a JButton, find out if it contains the text
//	      if (component instanceof JTabbedPane) {
//	    	  JTabbedPane tab = (JTabbedPane) component;
//	        // compare the name with that passed in
//	        if ( tab.getName().equals(JPanelText) ) {
//	          TabToFind = tab;
//	          break;
//	        }
//	      }
//	    }
//	    return TabToFind;
//	  }

}