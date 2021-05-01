package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.UndoableEditEvent;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.CellHandle;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.GraphUndoManager;
import org.jgraph.graph.Port;
import org.jgraph.graph.VertexView;

import de.fh_konstanz.simubus.controller.HaltestelleListener;
import de.fh_konstanz.simubus.controller.LineListener;
import de.fh_konstanz.simubus.controller.LockedHaltestellenListener;
import de.fh_konstanz.simubus.controller.LockedPlanquadratListener;
import de.fh_konstanz.simubus.controller.MyEdgeHandle;
import de.fh_konstanz.simubus.controller.MyMarqueeHandler;
import de.fh_konstanz.simubus.controller.StreetListener;
import de.fh_konstanz.simubus.controller.TargetListener;
import de.fh_konstanz.simubus.controller.XMLFileManager;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.HaltestellenTable;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.SimuGraphModel;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Teilstrecke;
import de.fh_konstanz.simubus.model.simulation.BusSimulationModel;
import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.util.ImageUtil;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.util.OrUtil;

/**
 * Die Klasse <code>SimuPanel</code> beinhaltet den Graphen, mit dem man die
 * Strassen, Ziele, Haltestellen, Gesperrte Felder und Buslinien zeichnen kann.
 * 
 * @author Daniel Weber, Johannes Frei, Dominik Heller, Daniel Merkle
 * @version 1.1 (01.07.2005)
 */

public class SimuPanel extends JLayeredPane {

	private boolean loadingState = false;

	/** ID für Serialisierung */
	private static final long serialVersionUID = 1908289614775965819L;

	/** Die Größe des Editors */
	private int sizeOfEditor;

	/** Die Größe der einzelnen Felder */
	private int sizeOfField;

	/** Die Anzahl der Felder im Editor */
	private int anzahlFelder;

	private Linie busLinie;

	/** Singleton instance */
	private static SimuPanel instance = null;

	/** Die geladene Stadtkarte */
	private Image background = null;

	/** Der Graph */
	private SimuGraph graph;

	/** Panel auf dem gezeichnet wird */
	private JPanel sPane;

	/** ArrayList mit allen im Strassennetz gespeicherten Haltestellen */
	private ArrayList<Haltestelle> halteStellen;

	/** Panel zum Anzeigen der aktuellen Cursor-Position der Maus */
	private JPanel info;

	/** Label für Anzeige der aktuellen Cursor-Position der Maus */
	private JLabel position;

	protected Action remove, undo, redo;

	/** UndoManager */
	protected GraphUndoManager undoManager;

	/** Vector mit den für die Animation benötigten BusPanels */
	private Vector<BusPanel> busPanels = new Vector<BusPanel>();

	/** Liste mit allen für die Simulation benötigten Busse */
	private ArrayList<Bus> busse = new ArrayList<Bus>();

	/** Das Model des Graphen */
	private SimuGraphModel graphModel;

	private ArrayList<Teilstrecke> ts;

	/**
	 * liefert ein <code>SimuPanel</code>-Objekt
	 * 
	 * @return ein <code>SimuPanel</code>-Objekt
	 */
	public static SimuPanel getInstance() {
		if (instance == null) {
			instance = new SimuPanel();
		}
		return instance;
	}

	/**
	 * initialisiert einige Objekte
	 */
	public void initialize() {
		sizeOfEditor = SimuKonfiguration.getInstance().getResPanel();
		sizeOfField = SimuKonfiguration.getInstance().getFeldElementGroesse();
		anzahlFelder = sizeOfEditor / (sizeOfField);
		halteStellen = new ArrayList<Haltestelle>(Strassennetz.getInstance()
				.getAlleHaltestellen());
	}

	/**
	 * Konstruktor
	 */
	private SimuPanel() {
		this.initialize();
		this.setLayout(null);
		this.background = Toolkit.getDefaultToolkit().getImage(
				ImageUtil.getImageUrl("Eisenstadt.jpg"));

		HaltestellenTable.getInstance().setHaltestellen(halteStellen);
		graphModel = SimuGraphModel.getInstance();
		this.graph = createGraph();
		this.graph.setModel(graphModel);
		this.graph.setPreferredSize(new Dimension(this.anzahlFelder
				* (this.sizeOfField), this.anzahlFelder * (this.sizeOfField)));
		this.graph.setInvokesStopCellEditing(true);
		this.graph.setDragEnabled(true);
		this.graph.setDropEnabled(true);
		this.graph.setJumpToDefaultPort(true);
		this.graph.setPortsVisible(true);
		this.graph.setBackgroundImage(background);
		this.graph.setGridMode(SimuGraph.LINE_GRID_MODE);
		this.graph.setGridColor(Color.BLACK);
		this.graph.setGridSize(this.sizeOfField);
		this.graph.setGridEnabled(true);
		this.graph.setGridVisible(true);
		this.graph.setMarqueeHandler(createMarqueeHandler());
		this.graph.getModel().addGraphModelListener(new StreetListener());
		this.graph.getModel().addGraphModelListener(new LineListener());
		this.graph.getModel().addGraphModelListener(new TargetListener());
		this.graph.getModel().addGraphModelListener(new HaltestelleListener());
		this.graph.getModel().addGraphModelListener(
				new LockedHaltestellenListener());
		this.graph.getModel().addGraphModelListener(
				new LockedPlanquadratListener());

		undoManager = new GraphUndoManager() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4867921179632055211L;

			// Override Superclass
			public void undoableEditHappened(UndoableEditEvent e) {
				// First Invoke Superclass
				super.undoableEditHappened(e);
				// Then Update Undo/Redo Buttons
				// updateHistoryButtons();
			}
		};
		graph.getModel().addUndoableEditListener(undoManager);

		this.sPane = new JPanel();
		this.sPane.add(graph);
		this.sPane.setPreferredSize(new Dimension(sizeOfEditor, sizeOfEditor));
		this.sPane.setBounds(0, 0, sizeOfEditor - 5, sizeOfEditor);

		this.info = new JPanel();
		this.info.setLayout(null);
		this.info.setMinimumSize(new Dimension(200, 15));
		this.info.setPreferredSize(new Dimension(200, 15));
		this.info.setBounds(0, 13 + sizeOfEditor, sizeOfEditor, 25);

		this.position = new JLabel("(15,10)");
		this.position.setForeground(Color.GRAY);
		this.position.setFont(new Font("Verdana", Font.BOLD, 10));
		this.position.setBounds(sizeOfEditor / 2, 2, 150, 12);
		this.info.add(position);

		this.add(sPane, new Integer(0));
		this.add(info);
	}

	/**
	 * Startet die Animation der Bussimulation
	 * 
	 * @param bsm
	 *            ein BusSimulationModel
	 */
	public void startAnimation(BusSimulationModel bsm) {
		busse = bsm.getBusse();

		for (int i = 0; i < busse.size(); i++) {
			BusPanel busPanel = new BusPanel(busse.get(i));
			busPanels.add(busPanel);
			Logger.getInstance().log(
					"Bus-Position: " + busse.get(i).getPositionX() + " "
							+ busse.get(i).getPositionY());
			// busPanel.setBounds( busse.get( i ).getPositionX(), busse.get( i
			// ).getPositionY(), 100, 100 );
			this.add(busPanel, new Integer(100), new Integer(1));
		}
	}

	/**
	 * Aktualisiert die visuelle Position des Busses, wenn dieser eine
	 * Haltestelle anfährt
	 * 
	 * @param aBus
	 */
	public void updateView(Bus aBus) {
		for (int i = 0; i < busPanels.size(); i++) {
			if (busPanels.get(i).getBus().getId() == aBus.getId()) {
				busPanels.get(i).setBounds(aBus.getPositionX(),
						aBus.getPositionY(), 100, 100);
			}
		}
	}

	/**
	 * Setzt das Model des Graphen
	 * 
	 * @param graphModel
	 */
	public void setGraphModel(SimuGraphModel graphModel) {
		this.graphModel = graphModel;
		graph.setModel(graphModel);
		graph.getGraphLayoutCache().reload();
		initialize();
		HaltestellenTable.getInstance().setHaltestellen(halteStellen);
		// hs.setInstance(new HaltestellenTable(halteStellen));
		SimuControlPanel.getInstance().updateBuslinienList();
		SimuControlPanel.getInstance().updateTeilstreckenList();
		// hs.fireTableDataChanged();
	}

	/**
	 * liefert ein SimuGraph-Objekt
	 * 
	 * @return ein SimuGraph-Objekt
	 */
	public SimuGraph getGraph() {
		return this.graph;
	}

	/**
	 * Setzt das Grid auf sichtbar/unsichtbar
	 * 
	 * @param b
	 */
	public void setGridVisible(boolean b) {
		this.graph.setGridVisible(b);
	}

	/**
	 * Setzt und visualisiert die aktuelle Position des Maus-Cursors
	 * 
	 * @param point
	 */
	public void displayActualPosition(Point point) {
		position.setText("");
		position.setText("(" + point.x + "," + point.y + ")");
	}

	/**
	 * erzeugt und liefert ein SimuGraph-Objekt
	 * 
	 * @return
	 */
	protected SimuGraph createGraph() {
		SimuGraph graph = new SimuGraph();
		graph.getGraphLayoutCache().setFactory(new DefaultCellViewFactory() {

			private static final long serialVersionUID = -7422338291913100623L;

			@Override
			protected VertexView createVertexView(Object cell) {
				return super.createVertexView(cell);
			}

			// Override Superclass Method to Return Custom EdgeView
			@Override
			protected EdgeView createEdgeView(Object cell) {

				// Return Custom EdgeView
				return new EdgeView(cell) {

					private static final long serialVersionUID = -4899617520907587696L;

					/**
					 * Returns a cell handle for the view.
					 */
					@Override
					public CellHandle getHandle(GraphContext context) {
						return new MyEdgeHandle(this, context);
					}

				};
			}
		});
		return graph;
	}

	/**
	 * Fügt dem Graphen einen Haltestelle-Knoten hinzu
	 * 
	 * @param point
	 */
	public void insertHaltestelle(Point2D point, String name) {
		HaltestellenCell vertex = createHaltestellenCell(
				halteStellen.size() + 1, name, 1);
		vertex.getAttributes().applyMap(
				createCellAttributes(point, "Haltestelle"));
		graph.getGraphLayoutCache().insert(vertex);
		int x = OrUtil.getPlanquadratXCoordinate((int) graph.getCellBounds(
				vertex).getCenterX());
		int y = OrUtil.getPlanquadratYCoordinate((int) graph.getCellBounds(
				vertex).getCenterY());
		Haltestelle h = new Haltestelle(halteStellen.size() + 1, x, y, name, 1);
		halteStellen.add(h);
		HaltestellenTable.getInstance().setHaltestellen(halteStellen);
		HaltestellenTable.getInstance().fireTableDataChanged();
	}

	/**
	 * entfernt alle Haltestellen
	 */
	public void removeHaltestellen() {
		Object[] haltestellen = graph.getGraphLayoutCache().getCells(false,
				true, false, false);

		for (int i = 0; i < haltestellen.length; i++) {
			if (haltestellen[i] instanceof HaltestellenCell) {

				graph.getModel().remove(new Object[] { haltestellen[i] });
			}
		}

		halteStellen.clear();
		HaltestellenTable.getInstance().fireTableDataChanged();
	}

	/**
	 * entfernt die übergebene Haltestelle
	 * 
	 * @param h
	 */
	public void removeHaltestelle(Haltestelle h) {
		Strassennetz.getInstance().resetHaltestellen();
		halteStellen.remove(h);
		Object o[] = graph.getGraphLayoutCache().getCells(false, true, false,
				false);
		for (int i = h.getID() - 1; i < halteStellen.size(); i++) {
			for (int j = 0; j < o.length; j++) {
				if (o[j] instanceof HaltestellenCell) {
					HaltestellenCell hCell = (HaltestellenCell) o[j];
					if (halteStellen.get(i).getID() == hCell.getId()) {
						hCell.setId(hCell.getId() - 1);
					}
				}
			}
			halteStellen.get(i).setID(halteStellen.get(i).getID() - 1);
		}
		for (int i = 0; i < halteStellen.size(); i++) {
			Strassennetz.getInstance().addHaltestelle(halteStellen.get(i));
		}
		HaltestellenTable.getInstance().fireTableDataChanged();
	}

	/**
	 * entfernt alles vom Panel
	 */
	public void resetPanel() {
		Logger.getInstance().log("Reset Panel");
		// Strassennetz.reset();
		Strassennetz netz = Strassennetz.getInstance();
		netz.reset();

		OptiControlPanel optiControlPanel = OptiControlPanel.getInstance();
		optiControlPanel.resetSolutions();

		SimuControlPanel controlPanel = SimuControlPanel.getInstance();
		SimuPanel simuPanel = SimuPanel.getInstance();

		HaltestellenTable haltestellenTable = HaltestellenTable.getInstance();

		controlPanel.updateTeilstreckenList();
		controlPanel.updateBuslinienList();

		Object[] cells = simuPanel.getGraph().getGraphLayoutCache().getCells(
				true, true, true, true);

		simuPanel.getGraph().getModel().remove(cells);

		halteStellen = new ArrayList<Haltestelle>(netz.getAlleHaltestellen());

		haltestellenTable.setHaltestellen(halteStellen);
		haltestellenTable.fireTableDataChanged();

		// reset Streets and barred Ways
		XMLFileManager.getInstance().resetPlanquadrate();

		// reset Ziele and Haltestellen (Gesperrt und normale)
		XMLFileManager.getInstance().resetHaltestellen();
		XMLFileManager.getInstance().resetZiele();
	}

	/**
	 * Fügt dem Graphen einen Ziel-Knoten hinzu
	 * 
	 * @param point
	 */
	public void insertZiel(Point2D point) {

		int anzahlZiele = 0;
		ArrayList<ZieleCell> zieleListe = this.getZieleCells();
		if (!zieleListe.isEmpty()) {
			anzahlZiele = zieleListe.size();
		}
		ZieleCell vertex = createZieleCell(anzahlZiele);

		vertex.getAttributes().applyMap(createCellAttributes(point, "Ziel"));
		graph.getGraphLayoutCache().insert(vertex);

	}

	/**
	 * Fügt dem Graphen einen für eine Haltestelle gesperrten Knoten hinzu
	 * 
	 * @param point
	 *            author Philipp Hofmann
	 */
	public void insertGesperrteHaltestelle(Point2D point) {
		int anzahlGesperrteHaltestelle = 0;
		ArrayList<GesperrteHaltestelleCell> gesperrteHaltestellenListe = this
				.getGesperrteHaltestellenCells();
		if (!gesperrteHaltestellenListe.isEmpty()) {
			anzahlGesperrteHaltestelle = gesperrteHaltestellenListe.size();
		}
		GesperrteHaltestelleCell vertex = this
				.createGesperrteHaltestelleCell(anzahlGesperrteHaltestelle);

		vertex.getAttributes().applyMap(
				createCellAttributes(point, "gesperrteHaltestelle"));
		graph.getGraphLayoutCache().insert(vertex);
	}

	/**
	 * Fügt dem Graphen eine Strassen-Kante hinzu Default Strasse wird eingefügt
	 * mit Standardlänge
	 * 
	 * @param point
	 */
	public void insertStrasse(Point2D point) {
		int anzahlStrassen = 0;
		List<StrassenEdge> strassenListe = this.getStrassenEdges();
		if (!strassenListe.isEmpty()) {
			anzahlStrassen = strassenListe.size();
		}
		StrassenEdge edge = new StrassenEdge();
		edge.setName("Strasse");
		edge.setId(anzahlStrassen);
		edge.getAttributes().applyMap(createEdgeAttributes(point, "Strasse"));
		graph.getGraphLayoutCache().insertEdge(edge, null, null);
	}

	/**
	 * insertStrasseAfterLoading, fügt eine Strasse in das Virtual Grid ein,
	 * benötigt als Übergabeparameter den Start und den Endpunkt der Strasse
	 * 
	 * @param point
	 * @param endPoint
	 */
	public void insertStrasseAfterLoading(Point2D point, Point2D endPoint) {
		int anzahlStrassen = 0;
		List<StrassenEdge> strassenListe = this.getStrassenEdges();
		if (!strassenListe.isEmpty()) {
			anzahlStrassen = strassenListe.size();
		}
		StrassenEdge edge = new StrassenEdge();
		edge.setName("Strasse");
		edge.setId(anzahlStrassen);
		edge.getAttributes()
				.applyMap(
						this.createEdgeAttributesForLoading(point, endPoint,
								"Strasse"));
		graph.getGraphLayoutCache().insertEdge(edge, null, null);
	}

	/**
	 * insertGesperrteStrasseAfterLoading, fügt eine gesperrte Strasse in das
	 * Virtual Grid ein, benötigt als Übergabeparameter den Start und den
	 * Endpunkt der gesperrten Strasse
	 * 
	 * @param point
	 * @param endPoint
	 */
	public void insertGesperrteStrasseAfterLoading(Point2D point,
			Point2D endPoint) {
		int anzahlStrassen = 0;
		List<GesperrteFelderEdge> strassenListe = this
				.getGesperrteFelderEdges();
		if (!strassenListe.isEmpty()) {
			anzahlStrassen = strassenListe.size();
		}
		GesperrteFelderEdge edge = new GesperrteFelderEdge();
		edge.setName("GesperrteFelder");
		edge.setId(anzahlStrassen);
		edge.getAttributes().applyMap(
				this.createEdgeAttributesForLoading(point, endPoint,
						"GesperrteFelder"));
		graph.getGraphLayoutCache().insertEdge(edge, null, null);
	}

	/**
	 * Fügt dem Graphen eine "Gesperrte Felder"-Kante hinzu.
	 * 
	 * @param point
	 */
	public void insertGesperrteFelder(Point2D point) {
		int anzahlGesperrteFelder = 0;
		List<GesperrteFelderEdge> gesperrteFelderListe = this
				.getGesperrteFelderEdges();
		if (!gesperrteFelderListe.isEmpty()) {
			anzahlGesperrteFelder = gesperrteFelderListe.size();
		}
		GesperrteFelderEdge edge = new GesperrteFelderEdge();
		edge.setName("GesperrteFelder");
		edge.setId(anzahlGesperrteFelder);
		edge.getAttributes().applyMap(
				createEdgeAttributes(point, "GesperrteFelder"));
		graph.getGraphLayoutCache().insertEdge(edge, null, null);
	}

	/**
	 * Liefert als Rückgabewert eine Map mit den generierten Attributen fuer die
	 * Knoten
	 * 
	 * @param point
	 * @param s
	 * @return Map mit den generierten Attributen fuer die Knoten
	 */
	public Map createCellAttributes(Point2D point, String s) {
		Map map = new Hashtable();
		if (graph != null) {
			point = graph.snap((Point2D) point.clone());
		} else {
			point = (Point2D) point.clone();
		}
		if (s.compareTo("Haltestelle") == 0) {
			Icon icon = new ImageIcon(ImageUtil
					.getImageUrl("haltestelle_klein.png"));
			GraphConstants.setIcon(map, icon);
			// GraphConstants.setBounds( map, new Rectangle2D.Double(
			// point.getX(),
			// point.getY(), 0, 0 ) );
			GraphConstants.setAutoSize(map, true);
		} else if (s.compareTo("gesperrteHaltestelle") == 0) {
			Icon icon = new ImageIcon(ImageUtil
					.getImageUrl("ghaltestelle_klein.png"));
			GraphConstants.setIcon(map, icon);
			// GraphConstants.setBounds( map, new Rectangle2D.Double(
			// point.getX(),
			// point.getY(), 0, 0 ) );
			GraphConstants.setAutoSize(map, true);
		} else if (s.compareTo("optimierteHaltestelle") == 0) {
			GraphConstants.setLineWidth(map, 4);
			GraphConstants.setBackground(map, Color.RED);
		} else if (s.compareTo("optimierteZiele") == 0) {
			GraphConstants.setLineWidth(map, 2);
			GraphConstants.setBackground(map, Color.RED);
		} else if (s.compareTo("") == 0) {
			Color color = new Color(0x00ffffff, true);
			GraphConstants.setBackground(map, color);
		} else if(s.compareTo("deselect") == 0) {
			GraphConstants.setLineWidth(map, 0);
			GraphConstants.setBackground(map, Color.YELLOW);
		}			
		else {
			GraphConstants.setBackground(map, Color.YELLOW);
		}
		GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(),
				point.getY(), this.sizeOfField, this.sizeOfField));
		GraphConstants.setEditable(map, false);
		GraphConstants.setSizeable(map, false);
		if (s.compareTo("") == 0) {
			Color color = new Color(0x00ffffff, true);
			GraphConstants.setBorderColor(map, color);
		} else {
			GraphConstants.setBorderColor(map, Color.black);
		}
		GraphConstants.setResize(map, false);
		GraphConstants.setOpaque(map, true);
		return map;
	}

	/**
	 * Liefert als Rückgabewert einen neuen Knoten (Haltestelle).
	 * 
	 * @param i
	 * @return
	 */
	private HaltestellenCell createHaltestellenCell(int i, String name,
			int kapazitaet) {
		HaltestellenCell cell = new HaltestellenCell(i, name, kapazitaet);
		DefaultPort port = new DefaultPort();
		GraphConstants.setOffset(port.getAttributes(), new Point(0,
				GraphConstants.PERMILLE / 2));
		Point portPoint = new Point(GraphConstants.PERMILLE / 2,
				GraphConstants.PERMILLE);
		cell.addPort(portPoint, port);
		return cell;
	}

	/**
	 * Liefert als Rückgabewert einen neuen Ziel-Knoten.
	 * 
	 * @param i
	 * @return
	 */
	private ZieleCell createZieleCell(int i) {
		ZieleCell cell = new ZieleCell();
		cell.setName("Z");
		cell.setId(i);
		return cell;
	}

	private DefaultGraphCell createPortCell() {
		return new DefaultGraphCell();
	}

	/**
	 * Liefert als Rückgabewert einen neuen GesperrteHaltestelle-Knoten.
	 * 
	 * @param i
	 * @return
	 */
	private GesperrteHaltestelleCell createGesperrteHaltestelleCell(int i) {
		GesperrteHaltestelleCell cell = new GesperrteHaltestelleCell();
		cell.setName("");
		cell.setId(i);
		return cell;
	}

	/**
	 * Liefert als Rückgabewert eine Map mit den generierten Attributen der
	 * Linien
	 * 
	 * @param point
	 * @param s
	 * @return
	 */
	private Map createEdgeAttributes(Point2D point, String s) {
		Map map = new Hashtable();

		Linie linie = SimuControlPanel.getInstance().getSelectedBuslinie();

		if (s.compareTo("Strasse") == 0 || s.compareTo("GesperrteFelder") == 0) {
			if (graph != null) {
				point = graph.snap((Point2D) point.clone());
			} else {
				point = (Point2D) point.clone();
			}
			List<Point2D> list = new ArrayList<Point2D>();
			Double x = point.getX() + 100;
			Double y = point.getY() + 100;
			Point2D.Double pointEnd = new Point2D.Double();
			pointEnd.setLocation(x, y);
			list.add(point);
			list.add(pointEnd);
			Point2D.Double labelPosition = new Point2D.Double();
			labelPosition.setLocation(graph.getWidth() * 100,
					graph.getHeight() * 100);
			GraphConstants.setPoints(map, list);
			GraphConstants.setLabelPosition(map, labelPosition);
			GraphConstants.setEditable(map, false);
			GraphConstants.setLineEnd(map, GraphConstants.ARROW_NONE);
			// GraphConstants.setLabelAlongEdge(map, true);
			if (s.compareTo("Strasse") == 0) {
				GraphConstants.setLineColor(map, Color.GRAY);
			} else {
				GraphConstants.setLineColor(map, Color.BLUE);
			}
		} else {
			GraphConstants.setLabelAlongEdge(map, true);
			GraphConstants.setLineColor(map, linie.getLinienfarbe());
			GraphConstants.setLineEnd(map, GraphConstants.ARROW_CLASSIC);
		}

		GraphConstants.setLineWidth(map, 4);
		GraphConstants.setConnectable(map, true);
		GraphConstants.setDisconnectable(map, true);
		return map;
	}

	/**
	 * createEdgeAttributesForLoading, erzeugt eine Map mit nötigen Attributen
	 * für die Objekte, benötigt den Start und Endpunkt des Objektes, sowie
	 * einen String zur Identifikation,
	 * 
	 * @param point
	 * @param endPoint
	 * @param s
	 * @return
	 */
	private Map createEdgeAttributesForLoading(Point2D point, Point2D endPoint,
			String s) {
		Map map = new Hashtable();

		Linie linie = SimuControlPanel.getInstance().getSelectedBuslinie();

		if (s.compareTo("Strasse") == 0 || s.compareTo("GesperrteFelder") == 0) {
			if (graph != null) {
				point = graph.snap((Point2D) point.clone());
				endPoint = graph.snap((Point2D) endPoint.clone());
			} else {
				point = (Point2D) point.clone();
				endPoint = (Point2D) endPoint.clone();
			}
			List<Point2D> list = new ArrayList<Point2D>();

			list.add(point);
			list.add(endPoint);
			Point2D.Double labelPosition = new Point2D.Double();
			labelPosition.setLocation(graph.getWidth() * 100,
					graph.getHeight() * 100);
			GraphConstants.setPoints(map, list);
			GraphConstants.setLabelPosition(map, labelPosition);
			GraphConstants.setEditable(map, false);
			GraphConstants.setLineEnd(map, GraphConstants.ARROW_NONE);

			if (s.compareTo("Strasse") == 0) {
				GraphConstants.setLineColor(map, Color.GRAY);
			} else {
				GraphConstants.setLineColor(map, Color.BLUE);
			}
		} else {
			GraphConstants.setLabelAlongEdge(map, true);
			GraphConstants.setLineColor(map, linie.getLinienfarbe());
			GraphConstants.setLineEnd(map, GraphConstants.ARROW_CLASSIC);
		}

		GraphConstants.setLineWidth(map, 4);
		GraphConstants.setConnectable(map, true);
		GraphConstants.setDisconnectable(map, true);
		return map;
	}

	/**
	 * Liefert als Rückgabewert eine Map mit den generierten Attributen der
	 * Linien
	 * 
	 * @param point
	 * @param s
	 * @return
	 */
	private Map createEdgeAttributesForLoadingALine(Point2D point, String s,
			Linie linie) {
		Map map = new Hashtable();

		if (s.compareTo("Strasse") == 0 || s.compareTo("GesperrteFelder") == 0) {
			if (graph != null) {
				point = graph.snap((Point2D) point.clone());
			} else {
				point = (Point2D) point.clone();
			}
			List<Point2D> list = new ArrayList<Point2D>();
			Double x = point.getX() + 100;
			Double y = point.getY() + 100;
			Point2D.Double pointEnd = new Point2D.Double();
			pointEnd.setLocation(x, y);
			list.add(point);
			list.add(pointEnd);
			Point2D.Double labelPosition = new Point2D.Double();
			labelPosition.setLocation(graph.getWidth() * 100,
					graph.getHeight() * 100);
			GraphConstants.setPoints(map, list);
			GraphConstants.setLabelPosition(map, labelPosition);
			GraphConstants.setEditable(map, false);
			GraphConstants.setLineEnd(map, GraphConstants.ARROW_NONE);
			// GraphConstants.setLabelAlongEdge(map, true);
			if (s.compareTo("Strasse") == 0) {
				GraphConstants.setLineColor(map, Color.GRAY);
			} else {
				GraphConstants.setLineColor(map, Color.BLUE);
			}
		} else {
			GraphConstants.setLabelAlongEdge(map, true);
			GraphConstants.setLineColor(map, linie.getLinienfarbe());
			GraphConstants.setLineEnd(map, GraphConstants.ARROW_CLASSIC);
		}

		GraphConstants.setLineWidth(map, 4);
		GraphConstants.setConnectable(map, true);
		GraphConstants.setDisconnectable(map, true);
		return map;
	}

	/**
	 * Erzeugt eine Neue Linie im Simu Panel
	 * 
	 * @return Linie
	 */
	public Linie createNewLine(Linie bus) {
		Linie linie = new Linie(bus.getId()/*
											 * "Linie " + String.valueOf(
											 * Strassennetz.getInstance().getArrayBuslinie().size() +
											 * 1 )
											 */);

		Color color = bus.getLinienfarbe();
		linie.setLinienfarbe(color);
		linie.setFrequenz(bus.getFrequenz());
		linie.setMaxKapBusPassagiere(bus.getMaxKapBusPassagiere());

		Strassennetz.getInstance().addLinie(linie);
		SimuControlPanel.getInstance().updateBuslinienList();

		return linie;
	}

	/**
	 * connectHsWithLine, zeichnet eine Linie zwischen Haltestellen
	 * 
	 * @param buslinie
	 */
	public void connectHsWithLine(Linie buslinie) {
		MyMarqueeHandler myMarquee = new MyMarqueeHandler();
		List<Teilstrecke> teilStrecken = buslinie.getTeilstrecken();

		this.createNewLine(buslinie);
		int anzahlTeilstrecken = 0;

		// if more than one Teilstrecke
		if (teilStrecken.size() > 1) {
			// Array for the ports
			ArrayList<Port> tArray = new ArrayList<Port>();
			// go through each Teilstrecke and get the right ports
			for (Teilstrecke teilStrecke : teilStrecken) {
				// get the points of teistrecke
				Point2D s = teilStrecke.getStart();
				Point2D e = teilStrecke.getEnde();

				Port target = null;
				Port source = null;

				// start is Haltestelle
				if (myMarquee.getSourcePortAt(s) != null) {
					source = (Port) myMarquee.getSourcePortAt(s).getCell();
					boolean exists = false;
					for (int i = 0; i < tArray.size(); i++) {
						if (tArray.get(i).getAttributes() == source
								.getAttributes()) {
							exists = true;
						}
					}
					if (exists == false) {
						tArray.add(source);
					}
				}

				// end is Haltestelle
				if (myMarquee.getTargetPortAt(e) != null) {
					target = (Port) myMarquee.getTargetPortAt(e).getCell();
					boolean exists = false;
					for (int i = 0; i < tArray.size(); i++) {
						if (tArray.get(i).getAttributes() == target
								.getAttributes()) {
							exists = true;
						}
					}
					if (exists == false) {
						tArray.add(target);
					}
				}
			}

			// go through the right ports and connect line with haltestellen
			for (int i = 0; i < tArray.size() - 1; i++) {
				Port source = tArray.get(i);
				Port target = tArray.get(i + 1);

				this.connectSavedHaltestellen(source, target, buslinie,
						anzahlTeilstrecken);
				anzahlTeilstrecken++;
			}

		} else {// there is one TeilStrecke
			Point2D start = teilStrecken.get(0).getStart();
			Point2D end = teilStrecken.get(0).getEnde();

			Port source = (Port) myMarquee.getSourcePortAt(start).getCell();
			Port target = (Port) myMarquee.getTargetPortAt(end).getCell();

			this.connectSavedHaltestellen(source, target, buslinie,
					anzahlTeilstrecken);
		}

	}

	/**
	 * Verbindet zwei gespeicherte Haltestellen-Knoten über ihre Ports.
	 * 
	 * @param source
	 * @param target
	 */
	public void connectSavedHaltestellen(Port source, Port target, Linie bus,
			int anzahl) {
		if (Strassennetz.getInstance().getArrayBuslinie().size() != 0) {

			this.setBusLinie(bus);

			String Id = bus.getId().replaceAll("Linie ", "");
			bus.setId(bus.getId());
			LinienEdge edge = new LinienEdge();
			edge.setName("Linie " + Id);
			edge.setId(anzahl);
			edge.addPort();
			if (graph.getModel().acceptsSource(edge, source)
					&& graph.getModel().acceptsTarget(edge, target)) {
				AttributeMap edgeAttribute = new AttributeMap();
				AttributeMap m_mapAttribute = new AttributeMap();
				Object[] insert = new Object[] { edge };
				ConnectionSet cs = new ConnectionSet();
				edge.getAttributes()
						.applyMap(
								createEdgeAttributesForLoadingALine(null,
										"Linie", bus));
				edgeAttribute = edge.getAttributes();
				m_mapAttribute.put(edge, edgeAttribute);
				cs.connect(edge, source, target);
				graph.getGraphLayoutCache().insertEdge(edge, source, target);
				Object[] o = new Object[2];
				o[0] = source;
				o[1] = target;
				graph.setSelectionCell(edge);
				EdgeView edgeView = MyEdgeHandle.getInstance().getEdgeView();
				List<Point2D> points = new ArrayList<Point2D>();
				points.add(edgeAttribute.createPoint(edgeView.getPoint(0)));
				points.add(edgeAttribute.createPoint(edgeView.getPoint(1)));
				GraphConstants.setPoints(edgeAttribute, points);
				graph.getModel().insert(insert, m_mapAttribute, cs, null, null);
			}

		} else {
			JOptionPane.showMessageDialog(this,
					"Bitte wählen Sie zuerst eine Linie aus",
					"Keine Linie ausgewählt", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Verbindet zwei Haltestellen-Knoten über ihre Ports.
	 * 
	 * @param source
	 * @param target
	 */
	@SuppressWarnings("unchecked")
	public void connect(Port source, Port target) {
		SimuControlPanel simuControl = SimuControlPanel.getInstance();
		Linie bus = simuControl.getSelectedBuslinie();
		if (Strassennetz.getInstance().getArrayBuslinie().size() != 0) {
			Set s = DefaultGraphModel.getEdges(graph.getModel(), graph
					.getGraphLayoutCache().getCells(false, true, false, false));
			Object[] obj = s.toArray();
			int anzahlLinien = 0;
			for (int i = 0; i < obj.length; i++) {
				LinienEdge ed = (LinienEdge) obj[i];

				if (ed.toString().contains(bus.getId())) {
					anzahlLinien++;
				}
			}
			LinienEdge edge = new LinienEdge();
			edge.setName(bus.getId());
			edge.setId(anzahlLinien);
			edge.addPort();
			if (graph.getModel().acceptsSource(edge, source)
					&& graph.getModel().acceptsTarget(edge, target)) {
				AttributeMap edgeAttribute = new AttributeMap();
				AttributeMap m_mapAttribute = new AttributeMap();
				Object[] insert = new Object[] { edge };
				ConnectionSet cs = new ConnectionSet();
				edge.getAttributes().applyMap(
						createEdgeAttributes(null, "Linie"));
				edgeAttribute = edge.getAttributes();
				m_mapAttribute.put(edge, edgeAttribute);
				cs.connect(edge, source, target);
				graph.getGraphLayoutCache().insertEdge(edge, source, target);
				Object[] o = new Object[2];
				o[0] = source;
				o[1] = target;
				graph.setSelectionCell(edge);
				EdgeView edgeView = MyEdgeHandle.getInstance().getEdgeView();
				List<Point2D> points = new ArrayList<Point2D>();
				points.add(edgeAttribute.createPoint(edgeView.getPoint(0)));
				points.add(edgeAttribute.createPoint(edgeView.getPoint(1)));
				GraphConstants.setPoints(edgeAttribute, points);
				graph.getModel().insert(insert, m_mapAttribute, cs, null, null);
			}

		} else {
			JOptionPane.showMessageDialog(this,
					"Bitte wählen Sie zuerst eine Linie aus",
					"Keine Linie ausgewählt", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * erzeugt und liefert einen MarqueeHandler, welcher auf Benutzeraktionen
	 * reagiert
	 * 
	 * @return
	 */
	protected BasicMarqueeHandler createMarqueeHandler() {
		return new MyMarqueeHandler();
	}

	/**
	 * liefert alle selektierten Linien
	 * 
	 * @return
	 */
	private LinienEdge[] getSelectedLinie() {

		Linie bus = SimuControlPanel.getInstance().getSelectedBuslinie();

		Object[] obj = graph.getGraphLayoutCache().getCells(false, false,
				false, true);

		List<LinienEdge> selObj = new ArrayList<LinienEdge>();
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof LinienEdge) {
				if (obj[i].toString().contains(bus.getId()) == true) {
					selObj.add((LinienEdge) obj[i]);
				}
			}
		}
		return selObj.toArray(new LinienEdge[selObj.size()]);
	}

	/**
	 * Selektiert alle Kanten, die in der ListBox der selektierten Linie
	 * entsprechen
	 */
	public void selectAllLinien() {
		SimuControlPanel simuControl = SimuControlPanel.getInstance();
		Linie bus = simuControl.getSelectedBuslinie();
		Object[] obj = graph.getGraphLayoutCache().getCells(false, false,
				false, true);
		ArrayList<DefaultEdge> selObj = new ArrayList<DefaultEdge>();
		for (int i = 0; i < obj.length; i++) {
			DefaultEdge edge = (DefaultEdge) obj[i];
			if (edge.toString().compareTo("Strasse") == 0
					|| edge.toString().compareTo("GesperrteFelder") == 0) {

			} else {
				if (edge.toString().contains(bus.getId())) {
					selObj.add(edge);
				}
			}
		}
		Object[] selectObj = new Object[selObj.size()];
		for (int i = 0; i < selectObj.length; i++) {
			selectObj[i] = selObj.get(i);
		}
		graph.setSelectionCells(selectObj);
	}

	/**
	 * Selektiert die Haltestelle im Graph, die in der Tabelle markiert wurde
	 * 
	 * @param s
	 */
	public void selectHaltestelle(String s) {
		Object[] obj = graph.getGraphLayoutCache().getCells(false, true, false,
				false);
		DefaultGraphCell cell = null;
		for (int i = 0; i < obj.length; i++) {
			cell = (DefaultGraphCell) obj[i];
			if (cell.toString().compareTo(s) == 0) {
				break;
			}
		}
		graph.setSelectionCell(cell);
	}

	/**
	 * liefert eine Liste mit allen Strassen-Kanten
	 * 
	 * @return
	 */
	private List<StrassenEdge> getStrassenEdges() {
		Object[] o = graph.getGraphLayoutCache().getCells(false, false, false,
				true);
		List<StrassenEdge> strassenListe = new ArrayList<StrassenEdge>();
		for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof StrassenEdge) {
				strassenListe.add((StrassenEdge) o[i]);
			}
		}
		return strassenListe;
	}

	/**
	 * liefert eine Liste mit allen GesperteFelder-Kanten
	 * 
	 * @return
	 */
	private List<GesperrteFelderEdge> getGesperrteFelderEdges() {
		Object[] o = graph.getGraphLayoutCache().getCells(false, false, false,
				true);
		List<GesperrteFelderEdge> gesperrteFelderListe = new ArrayList<GesperrteFelderEdge>();
		for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof GesperrteFelderEdge) {
				gesperrteFelderListe.add((GesperrteFelderEdge) o[i]);
			}
		}
		return gesperrteFelderListe;
	}

	/**
	 * setzt die Farbe der selektierten Buslinie
	 * 
	 * @param aColor
	 */
	public void setColorSelectedLinie(Color aColor) {
		LinienEdge[] selectedLinien = getSelectedLinie();

		for (int i = 0; i < selectedLinien.length; i++) {
			GraphConstants.setLineColor(selectedLinien[i].getAttributes(),
					aColor);
		}
		graph.getGraphLayoutCache().reload();
		graph.repaint();

	}

	/**
	 * liefert eine Liste mit allen Ziele-Knoten
	 * 
	 * @return
	 */
	public ArrayList<ZieleCell> getZieleCells() {
		Object[] o = graph.getGraphLayoutCache().getCells(false, true, false,
				false);
		ArrayList<ZieleCell> zieleListe = new ArrayList<ZieleCell>();
		for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof ZieleCell) {
				zieleListe.add((ZieleCell) o[i]);
			}
		}
		return zieleListe;
	}

	/**
	 * liefert eine Liste mit allen Ziele-Knoten
	 * 
	 * @author Philipp Hofmann
	 * @return
	 */
	private ArrayList<GesperrteHaltestelleCell> getGesperrteHaltestellenCells() {
		Object[] o = graph.getGraphLayoutCache().getCells(false, true, false,
				false);
		ArrayList<GesperrteHaltestelleCell> gesperrteHaltestellenListe = new ArrayList<GesperrteHaltestelleCell>();
		for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof GesperrteHaltestelleCell) {
				gesperrteHaltestellenListe.add((GesperrteHaltestelleCell) o[i]);
			}
		}
		return gesperrteHaltestellenListe;
	}

	/**
	 * Entfernt die Linien vom Graph, wenn die selektierte Linie in der Listbox
	 * gelöscht wurde
	 */
	public void removeLinien() {
		SimuControlPanel simuControl = SimuControlPanel.getInstance();
		Linie bus = simuControl.getSelectedBuslinie();
		Object[] obj = graph.getGraphLayoutCache().getCells(false, false,
				false, true);
		ArrayList<DefaultEdge> selObj = new ArrayList<DefaultEdge>();
		for (int i = 0; i < obj.length; i++) {
			DefaultEdge edge = (DefaultEdge) obj[i];
			if (edge.toString().contains(bus.getId())) {
				selObj.add(edge);
			}
		}
		Object[] selectObj = new Object[selObj.size()];
		for (int i = 0; i < selectObj.length; i++) {
			selectObj[i] = selObj.get(i);
		}
		graph.getModel().remove(selectObj);
	}

	/**
	 * macht die letzte Änderung des Models oder der View rückgängig
	 */
	public void undo() {
		try {
			undoManager.undo(graph.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			// updateHistoryButtons();
		}
	}

	/**
	 * Stellt die letzte Änderung am Model oder der View wieder her
	 */
	public void redo() {
		try {
			undoManager.redo(graph.getGraphLayoutCache());
			SimuControlPanel.getInstance().updateBuslinienList();
			SimuControlPanel.getInstance().updateTeilstrecke();
			HaltestellenTable.getInstance().fireTableDataChanged();
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			// updateHistoryButtons();
		}
	}

	/**
	 * liefert die Remove-Action
	 */
	public Action getRemove() {
		return remove;
	}

	/**
	 * setzt die Remove-Action
	 */
	public void setRemove(Action remove) {
		this.remove = remove;
	}

	/**
	 * getBackgroundImage
	 * 
	 * @return
	 */
	public Image getBackgroundImage() {
		return this.background;
	}

	/**
	 * getAllHaltestellen
	 * 
	 * @return
	 */
	public Collection<Haltestelle> getAllHaltestellen() {
		return this.halteStellen;
	}

	/**
	 * getBusPanels
	 * 
	 * @return
	 */
	public Vector<BusPanel> getBusPanels() {
		return this.busPanels;
	}

	/**
	 * getAllBusse
	 * 
	 * @return
	 */
	public Collection<Bus> getAllBusse() {
		return this.busse;
	}

	/**
	 * setBusLinie
	 * 
	 * @param linie
	 */
	public void setBusLinie(Linie linie) {
		this.busLinie = linie;
	}

	/**
	 * getBusLinie
	 * 
	 * @return
	 */
	public Linie getBusLinie() {
		return this.busLinie;
	}

	public boolean checkLoading() {
		return this.loadingState;
	}

}