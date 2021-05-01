package de.fh_konstanz.simubus.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jgraph.graph.GraphConstants;

import de.fh_konstanz.simubus.model.GesperrteHaltestelle;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.SimuGraphModel;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Teilstrecke;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.model.simulation.entities.Bus;
import de.fh_konstanz.simubus.model.simulation.entities.HaltestelleJ;
import de.fh_konstanz.simubus.util.ImageUtil;
import de.fh_konstanz.simubus.util.Logger;
import de.fh_konstanz.simubus.util.OrUtil;
import de.fh_konstanz.simubus.view.BusPanel;
import de.fh_konstanz.simubus.view.GesperrteFelderEdge;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.StrassenEdge;
import de.fh_konstanz.simubus.view.View;
import desmoj.core.simulator.SimTime;

/**
 * Die Klasse <code>XMLFileManager</code> legt die angelegte Umgebung in ein
 * XML- Format ab.
 * 
 * @author Johannes Frei, Dominik Heller, Daniel Merkle
 * @version 1.0
 * 
 */
public class XMLFileManager {

	// member variables
	private static Strassennetz myStreetNet = null;
	private SimuKonfiguration mySimulationConfig = null;
	private SimuGraphModel mySimulationGrapicModel = null;
	private VirtualGrid myVirtualGrid = null;
	private Document myXMLDocument = null;
	private Map<String, StrassenEdge> streetArray = null;
	private Map<String, GesperrteFelderEdge> gesperrteFelderArray = null;
	private static XMLFileManager myInstance = null;
	private ArrayList<Haltestelle> haltestellenArray = null;
	private ArrayList<GesperrteHaltestelle> gesperrteHaltestellenArray = null;
	private ArrayList<Ziel> zieleArray = null;

	/**
	 * Constructor
	 * 
	 */
	private XMLFileManager() {
		// set Variables
		myStreetNet = Strassennetz.getInstance();
		mySimulationConfig = SimuKonfiguration.getInstance();
		mySimulationGrapicModel = SimuGraphModel.getInstance();
		myVirtualGrid = VirtualGrid.getInstance();
		streetArray = new HashMap<String, StrassenEdge>();
		gesperrteFelderArray = new HashMap<String, GesperrteFelderEdge>();
		haltestellenArray = new ArrayList<Haltestelle>();
		gesperrteHaltestellenArray = new ArrayList<GesperrteHaltestelle>();
		zieleArray = new ArrayList<Ziel>();
	}

	/**
	 * getInstance
	 * 
	 * @return
	 */
	public static XMLFileManager getInstance() {
		if (myInstance == null) {
			myInstance = new XMLFileManager();
		}
		return myInstance;
	}

	/**
	 * saveAsXML speichert die Umgebung über das überlieferte File in XML ab
	 * 
	 * @param file
	 * @return boolean
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean saveAsXML(File file) throws FileNotFoundException, IOException {

		// get XMLDocument
		Document xmlDocument = this.getXMLDocument(file);

		XMLOutputter out = new XMLOutputter();
		out.setFormat(Format.getPrettyFormat().setEncoding("iso-8859-1"));

		// init writer
		FileWriter writer = new FileWriter(file);

		try {

			out.output(xmlDocument, writer);
			// close writer
			writer.close();

			JOptionPane.showMessageDialog(View.getInstance(),
					"Das XML- File " + file.getAbsolutePath() + " wurde erfolgreich gespeichert!", "Datei speichern",
					JOptionPane.DEFAULT_OPTION);
			Logger.getInstance().log("Das XML- File " + file.getAbsolutePath() + " wurde erfolgreich gespeichert.");
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							View.getInstance(), "Das XML- File " + file.getAbsolutePath()
									+ " konnte nicht gespeichert werden!" + " Fehler in der XML- Instanz!",
							"Datei speichern", JOptionPane.DEFAULT_OPTION);
			Logger.getInstance().log("Das XML- File " + file.getAbsolutePath() + " konnte nicht gespeichert werden.");
			return false;
		}

		return true;
	}

	/**
	 * getXMLDocument: liefert die Umgebung als XML- Dokument
	 * 
	 * @param XML-
	 *            File, das abgespeichert werden soll
	 * @return rootElement
	 */
	@SuppressWarnings("rawtypes")
	private Document getXMLDocument(File file) {
		// rootElement
		Element rootElement = null;
		// path to Application
		File path = new File(System.getProperty("user.dir"));

		try {
			// create Root Element
			rootElement = new Element("Bussimulation");

			// declare Namespace
			Namespace XSI_NAMESPACE = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			// add Namespaces to RootElement
			rootElement.addNamespaceDeclaration(XSI_NAMESPACE);
			rootElement.setAttribute("noNamespaceSchemaLocation",
					path.getCanonicalPath() + "\\xmlFiles\\xsd\\busSimulation.xsd",
					Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));

			myXMLDocument = new Document(rootElement);

		} catch (Exception e) {
			// document could not be created
			Logger.getInstance().log("Das Xml Dokument " + file.getAbsolutePath() + " konnte nicht erzeugt werden!");
		}

		// if document not null, create XmlDocument
		if (myXMLDocument != null) {

			// create Element Strassennetz
			Element streetNetElement = new Element("Strassennetz");
			rootElement.addContent(streetNetElement);
			// <Strassennetz>

			// -------------------- HALTESTELLEN ---------------------
			Collection<Haltestelle> cHaltestellen = this.getHaltestellenArray();

			// if Haltestellen exists
			if (cHaltestellen.size() > 0) {
				// create Element Haltestellen
				Element haltestellenElement = new Element("Haltestellen");
				streetNetElement.addContent(haltestellenElement);
				// <Haltestellen>

				// go through all Haltestellen
				for (Haltestelle haltestelle : cHaltestellen) {
					// create Haltestelle as Element
					Element haltestelleElement = this.createHaltestelleElement(haltestelle);
					// add Haltestelle to Haltestellen
					haltestellenElement.addContent(haltestelleElement);
				}
				// </Haltestellen>
			}

			// create Element Planquadrate
			Element planquadrateElement = new Element("Planquadrate");
			streetNetElement.addContent(planquadrateElement);
			// <Planquadrate>

			// ------------------------- STRASSEN -----------------------------
			HashMap<String, StrassenEdge> mStreets = (HashMap<String, StrassenEdge>) this.getStreetArray();

			Set set = mStreets.keySet();
			Iterator iter = set.iterator();

			while (iter.hasNext()) {
				StrassenEdge strasse = mStreets.get(iter.next());
				int Id = strasse.getId();
				// <PlanquadratStrasse>
				Element planquadratList = new Element("PlanquadratStrasse");
				planquadratList.setAttribute("id", String.valueOf(Id));

				Object[] points = GraphConstants.getPoints(strasse.getAttributes()).toArray();
				for (int i = 0; i < points.length; i++) {
					if (i + 1 < points.length) { // create start and end point
						Point2D start = (Point2D) points[i];
						Point2D ende = (Point2D) points[i + 1];
						// create strassenElement
						Element streetElement = this.createStrasseElement(start, ende, Id);
						// add Street to Planquadrat
						planquadratList.addContent(streetElement);
					}
				}
				// </PlanquadratStrasse>
				planquadrateElement.addContent(planquadratList);
			}

			// ------------------------- GESPERRTE STRASSEN
			// -----------------------------
			HashMap<String, GesperrteFelderEdge> mBarredWay = (HashMap<String, GesperrteFelderEdge>) this
					.getGesperrteFelderArray();

			Set barredWaySet = mBarredWay.keySet();
			Iterator barredWayIter = barredWaySet.iterator();

			while (barredWayIter.hasNext()) {
				GesperrteFelderEdge gesperrteFelder = mBarredWay.get(barredWayIter.next());
				int Id = gesperrteFelder.getId();
				// <PlanquadratGesperrteStrasse>
				Element planquadratList = new Element("PlanquadratGesperrteStrasse");
				planquadratList.setAttribute("id", String.valueOf(Id));

				Object[] points = GraphConstants.getPoints(gesperrteFelder.getAttributes()).toArray();
				for (int i = 0; i < points.length; i++) {
					if (i + 1 < points.length) { // create start and end point
						Point2D start = (Point2D) points[i];
						Point2D ende = (Point2D) points[i + 1];
						// create strassenElement
						Element bWayElement = this.createGesperreteStrasseElement(start, ende, Id);
						// add Street to Planquadrat
						planquadratList.addContent(bWayElement);
					}
				}
				// </PlanquadratStrasse>
				planquadrateElement.addContent(planquadratList);
			}

			// </Planquadrate>

			// ------------------------- ZIELE -----------------------------
			// get a List of all Goals
			Collection<Ziel> cGoals = this.getZieleArray();
			// if goals exists
			if (cGoals.size() > 0) {
				Element allGoalsElement = new Element("Ziele");
				streetNetElement.addContent(allGoalsElement);
				// <Ziele>

				// go through all goals
				for (Ziel goal : cGoals) {
					// create goal as Element
					Element goalElement = this.createGoalElement(goal);
					// add goal to Ziele
					allGoalsElement.addContent(goalElement);
				}
				// </Ziele>
			}

			// <Linien>
			// ------------------------- LINIEN -----------------------------
			// get a List of all Linien
			Collection<Linie> cLinien = myStreetNet.getArrayBuslinie();
			// if linien exits
			if (cLinien.size() > 0) {
				Element allLinienElement = new Element("Linien");
				streetNetElement.addContent(allLinienElement);

				// go through all linien
				for (Linie linie : cLinien) {
					// create linie as Element
					Element linieElement = this.createLinieElement(linie);
					// add linie to Linien
					allLinienElement.addContent(linieElement);
				}
			}
			// </Linien>

			// <GesperrteFelder>
			// ------------------------- GESPERRTE HALTESTELLEN
			// -----------------------------
			// get a List of all GesperrteFelder
			Collection<GesperrteHaltestelle> cBarredHaltestellen = this.getGesperrteHaltestellenArray();
			// if barredHaltestellen exits
			if (cBarredHaltestellen.size() > 0) {
				Element allBarredHaltestellen = new Element("GesperrteFelder");
				streetNetElement.addContent(allBarredHaltestellen);

				// go through all barredFields
				for (GesperrteHaltestelle barredHaltestelle : cBarredHaltestellen) {
					// create barredHaltestelle as Element
					Element barredHaltestelleElement = this.createBarredHaltestelle(barredHaltestelle);
					// add gesperrte Haltestelle to Gesperrte Felder
					allBarredHaltestellen.addContent(barredHaltestelleElement);
				}
			}
			// </GesperrteFelder>

			// </Strassennetz>

			// <SimuKonfiguration>
			// ------------------------- SIMUKONFIGURATION
			// -----------------------------
			// create Element SimuKonfiguration
			Element simuConfigElement = this.createSimuConfigElement();
			rootElement.addContent(simuConfigElement);
			// </SimuKonfiguration>

			// <SimuPanel>
			// ------------------------- SIMUPANEL -----------------------------
			// create Element SimuPanel
			Element simuPanelElement = this.createSimuPanelElement();
			rootElement.addContent(simuPanelElement);
			// </SimuPanel>

			// <VirtualGrid>
			// ------------------------- VIRTUAL GRID
			// -----------------------------
			// create Element VirtualGrid
			Element virtualGrid = new Element("VirtualGrid");
			virtualGrid.addContent("-- gets Data from SimuKonfiguration --");

			// </VirtualGrid>
			rootElement.addContent(virtualGrid);

		}

		return myXMLDocument; // return xmlDocument
	}

	/**
	 * createSimuPanelElement, erstellt das Element SimuPanel mit den nötigen
	 * Daten, Rückgabe des Elements simuPanel
	 * 
	 * @return Element
	 */
	private Element createSimuPanelElement() {
		// get Instance of SimuPanel
		SimuPanel simuPanel = SimuPanel.getInstance();
		// create SimuPanel as Element
		Element simuPanelElement = new Element("SimuPanel");

		// create Child GroesseEditor
		Element sizeOfEditor = new Element("GroesseEditor");
		sizeOfEditor.addContent(String.valueOf(SimuKonfiguration.getInstance().getResPanel()));

		// create Child GroesseFelder
		Element sizeOfFields = new Element("GroesseFelder");
		sizeOfFields.addContent(String.valueOf(SimuKonfiguration.getInstance().getFeldElementGroesse()));

		// create Child AnzahlFelder
		Element anzahlFelder = new Element("AnzahlFelder");
		int sizeOne = SimuKonfiguration.getInstance().getResPanel();
		int sizeTwo = SimuKonfiguration.getInstance().getFeldElementGroesse();
		anzahlFelder.addContent(String.valueOf(sizeOne / (sizeTwo)));

		// create Child BackgroundImage
		Element background = new Element("BackgroundImage");
		Image image = simuPanel.getBackgroundImage();
		ImageIcon icon = new ImageIcon(image);
		background.setAttribute("url", ImageUtil.getImageUrl("Eisenstadt.jpg").getFile());
		background.setAttribute("width", String.valueOf(icon.getIconWidth()));
		background.setAttribute("height", String.valueOf(icon.getIconHeight()));

		// create Child Haltestellen
		Element haltestellen = new Element("Haltestellen");
		Collection<Haltestelle> cHaltestellen = simuPanel.getAllHaltestellen();
		if (cHaltestellen.size() > 0) {
			for (Haltestelle haltestelle : cHaltestellen) {
				Element haltestelleElement = new Element("Haltestelle");
				haltestelleElement.setAttribute("id", String.valueOf(haltestelle.getID()));
				haltestellen.addContent(haltestelleElement);
			}
		}

		// create Child BusPanels
		Element busPanelsElement = new Element("BusPanels");
		Vector<BusPanel> busPanels = simuPanel.getBusPanels();
		if (busPanels.size() > 0) {
			for (BusPanel busPanel : busPanels) {
				Element busPanelElement = this.createBusPanelElement(busPanel);
				busPanelsElement.addContent(busPanelElement);
			}
		}

		// create Child BenoetigteBusse
		Element benoetigteBusse = new Element("BenoetigteBusse");
		Collection<Bus> cBusse = simuPanel.getAllBusse();
		if (cBusse.size() > 0) {
			for (Bus bus : cBusse) {
				Element busElement = new Element("Bus");
				busElement.setAttribute("id", String.valueOf(bus.getId()));
				benoetigteBusse.addContent(busElement);
			}
		}

		// add Childs
		simuPanelElement.addContent(sizeOfEditor);
		simuPanelElement.addContent(sizeOfFields);
		simuPanelElement.addContent(anzahlFelder);
		simuPanelElement.addContent(background);
		simuPanelElement.addContent(haltestellen);
		simuPanelElement.addContent(busPanelsElement);
		simuPanelElement.addContent(benoetigteBusse);

		return simuPanelElement;
	}

	/**
	 * createBusPanelElement, erzeugt das Element BusPanel mit den nötigen
	 * Daten, Rückgabe des Elements busPanelElement
	 * 
	 * @param busPanel
	 * @return Element
	 */
	private Element createBusPanelElement(BusPanel busPanel) {
		// create BusPanel as Element
		Element busPanelElement = new Element("BusPanel");

		// create Child Image
		Element imageElement = new Element("Image");
		imageElement.setAttribute("url", ImageUtil.getImageUrl("bus.png").getFile());
		Image image = Toolkit.getDefaultToolkit().getImage(ImageUtil.getImageUrl("bus.png"));
		ImageIcon icon = new ImageIcon(image);
		imageElement.setAttribute("width", String.valueOf(icon.getIconWidth()));
		imageElement.setAttribute("height", String.valueOf(icon.getIconHeight()));

		// create Child Bus
		Element bus = new Element("Bus");
		bus.setAttribute("id", String.valueOf(busPanel.getBus().getId()));// attribute
																			// id

		// create Child BusLinie
		Element busLinie = new Element("BusLinie");
		busLinie.setAttribute("id", String.valueOf(busPanel.getBus().get_buslinie().getId()));

		// create Child HaltestellenJ
		Element haltestellenJ = new Element("HaltestellenJ");
		Collection<HaltestelleJ> haltJ = busPanel.getBus().get_buslinie().getHaltestellen();
		if (haltJ.size() > 0) {
			for (HaltestelleJ hJ : haltJ) {
				Element haltestelleJElement = this.createHaltestelleJElement(hJ);
				haltestellenJ.addContent(haltestelleJElement);
			}
		}

		// create Child MaxKapazitaet
		Element maxKap = new Element("MaxKapazitaet");
		maxKap.addContent(String.valueOf(busPanel.getBus().get_buslinie().getMaxKapPassagiere()));

		// create Child Teilstrecken
		Element teilstrecken = new Element("Teilstrecken");
		if (busPanel.getBus().get_buslinie().getTeilstrecken().size() > 0) {
			// go through all teilstrecken
			for (Teilstrecke teilstrecke : busPanel.getBus().get_buslinie().getTeilstrecken()) {
				Element teilstreckeElement = this.createTeilstreckeElement(teilstrecke);
				teilstrecken.addContent(teilstreckeElement); // add
																// Teilstrecke
			}
		}

		// add Childs
		busLinie.addContent(haltestellenJ);
		busLinie.addContent(maxKap);
		busLinie.addContent(teilstrecken);

		// create Child HaltestelleJ
		Element haltestelleJElement = this.createHaltestelleJElement(busPanel.getBus().get_currentHaltestelle());

		// create Child PassagierQueue
		Element passagierQueue = new Element("PassagierQueue");
		// Child Name
		Element Name = new Element("Name");
		Name.addContent(busPanel.getBus().get_passagierQueue().getName());
		// Child DurchschnittsLaenge
		Element durchschnittsLaenge = new Element("DurchschnittsLaenge");
		durchschnittsLaenge.addContent(Double.toString(busPanel.getBus().get_passagierQueue().averageLength()));
		// Child DurchschnittsWarteZeit
		Element durchschnittsWarteZeit = new Element("DurchschnittsWarteZeit");
		SimTime averageWaitTime = busPanel.getBus().get_passagierQueue().averageWaitTime();
		durchschnittsWarteZeit.addContent(averageWaitTime.toString());
		// Child WarteschlangenLimit
		Element warteSchlangenLimit = new Element("PassagierQueueLimit");
		warteSchlangenLimit.addContent(String.valueOf(busPanel.getBus().get_passagierQueue().getQueueLimit()));
		// Child WarteschlangenStrategie
		Element warteSchlangenStrategie = new Element("PassagierQueueStrategie");
		warteSchlangenStrategie.addContent(busPanel.getBus().get_passagierQueue().getQueueStrategy());

		// add Childs
		passagierQueue.addContent(Name);
		passagierQueue.addContent(durchschnittsLaenge);
		passagierQueue.addContent(durchschnittsWarteZeit);
		passagierQueue.addContent(warteSchlangenLimit);
		passagierQueue.addContent(warteSchlangenStrategie);

		// create Child MaxKapazitaet
		Element maxKapazitaet = new Element("MaxKapazitaet");
		maxKapazitaet.addContent(String.valueOf(busPanel.getBus().getMaxKap()));

		// create Child Position
		Element position = new Element("Position");
		Integer x = busPanel.getBus().getPositionX();
		Integer y = busPanel.getBus().getPositionY();
		position.setAttribute("xValue", String.valueOf(OrUtil.getPixelXCoordinate(x)));
		position.setAttribute("yValue", String.valueOf(OrUtil.getPixelXCoordinate(y)));

		// add Childs
		bus.addContent(busLinie);
		bus.addContent(haltestelleJElement);
		bus.addContent(passagierQueue);
		bus.addContent(maxKapazitaet);
		bus.addContent(position);

		// add Childs
		busPanelElement.addContent(imageElement);
		busPanelElement.addContent(bus);

		return busPanelElement;
	}

	/**
	 * createHaltestelleJElement, erzeugt das Element HaltestelleJ, Rückgabe des
	 * Elements haltestelleJ
	 * 
	 * @param hJ
	 * @return Element
	 */
	private Element createHaltestelleJElement(HaltestelleJ hJ) {
		// create haltestelleJ as Element
		Element haltestelleJ = new Element("HaltestelleJ");

		// create Child BusWarteschlangeQueue
		Element busWarteschlangeQueue = new Element("BusWarteschlangeQueue");
		// Child Name
		Element Name = new Element("Name");
		Name.addContent(hJ.get_busWarteschlange().getName());
		// Child DurchschnittsLaenge
		Element durchschnittsLaenge = new Element("DurchschnittsLaenge");
		durchschnittsLaenge.addContent(Double.toString(hJ.get_busWarteschlange().averageLength()));
		// Child DurchschnittsWarteZeit
		Element durchschnittsWarteZeit = new Element("DurchschnittsWarteZeit");
		SimTime averageWaitTime = hJ.get_busWarteschlange().averageWaitTime();
		durchschnittsWarteZeit.addContent(averageWaitTime.toString());
		// Child WarteschlangenLimit
		Element warteSchlangenLimit = new Element("WarteschlangenLimit");
		warteSchlangenLimit.addContent(String.valueOf(hJ.get_busWarteschlange().getQueueLimit()));
		// Child WarteschlangenStrategie
		Element warteSchlangenStrategie = new Element("WarteschlangenStrategie");
		warteSchlangenStrategie.addContent(hJ.get_busWarteschlange().getQueueStrategy());

		// add Childs
		busWarteschlangeQueue.addContent(Name);
		busWarteschlangeQueue.addContent(durchschnittsLaenge);
		busWarteschlangeQueue.addContent(durchschnittsWarteZeit);
		busWarteschlangeQueue.addContent(warteSchlangenLimit);
		busWarteschlangeQueue.addContent(warteSchlangenStrategie);

		// create Child PassagierWarteschlangeQueue
		Element passagierWarteschlangeQueue = new Element("PassagierWarteschlangeQueue");
		// Child Name
		Element passWartQueueName = new Element("Name");
		passWartQueueName.addContent(hJ.get_passagierWarteschlange().getName());
		// Child DurchschnittsLaenge
		Element passDurchschnittsLaenge = new Element("DurchschnittsLaenge");
		passDurchschnittsLaenge.addContent(Double.toString(hJ.get_passagierWarteschlange().averageLength()));
		// Child DurchschnittsWarteZeit
		Element passDurchschnittsWarteZeit = new Element("DurchschnittsWarteZeit");
		SimTime averageWTime = hJ.get_passagierWarteschlange().averageWaitTime();
		passDurchschnittsWarteZeit.addContent(averageWTime.toString());
		// Child WarteschlangenLimit
		Element passWarteSchlangenLimit = new Element("WarteschlangenLimit");
		passWarteSchlangenLimit.addContent(String.valueOf(hJ.get_passagierWarteschlange().getQueueLimit()));
		// Child WarteschlangenStrategie
		Element passWarteSchlangenStrategie = new Element("WarteschlangenStrategie");
		passWarteSchlangenStrategie.addContent(hJ.get_passagierWarteschlange().getQueueStrategy());

		// add Childs
		passagierWarteschlangeQueue.addContent(passWartQueueName);
		passagierWarteschlangeQueue.addContent(passDurchschnittsLaenge);
		passagierWarteschlangeQueue.addContent(passDurchschnittsWarteZeit);
		passagierWarteschlangeQueue.addContent(passWarteSchlangenLimit);
		passagierWarteschlangeQueue.addContent(passWarteSchlangenStrategie);

		// create Child Point
		Element pointElement = new Element("Point");
		double x = hJ.getPoint().getX();
		double y = hJ.getPoint().getY();
		pointElement.setAttribute("xValue", String.valueOf(x));
		pointElement.setAttribute("yValue", String.valueOf(y));

		// create Child BusEinheiten
		Element busEinheiten = new Element("BusEinheiten");
		busEinheiten.addContent(String.valueOf(hJ.get_anzahlbusseinhs()));

		// create Child MaxKapazitaet
		Element maxKapazitaet = new Element("MaxKapazitaet");
		maxKapazitaet.addContent(String.valueOf(hJ.getMaxKap()));

		// add Childs
		haltestelleJ.addContent(busWarteschlangeQueue);
		haltestelleJ.addContent(passagierWarteschlangeQueue);
		haltestelleJ.addContent(pointElement);
		haltestelleJ.addContent(busEinheiten);

		return haltestelleJ;
	}

	/**
	 * createSimuConfigElement, erzeugt das Element SimuKonfiguration mit
	 * nötigen Daten, Rückgabe des Elements SimuKonfiguration
	 * 
	 * @return Element
	 */
	private Element createSimuConfigElement() {
		// create SimuConfigElement as Element
		Element simuConfigElement = new Element("SimuKonfiguration");

		// create Child AktuelleAufloesung
		Element resActualElement = new Element("AktuelleAufloesung");
		Integer resHeight = SimuKonfiguration.getInstance().getResHeight();
		Integer resWidth = SimuKonfiguration.getInstance().getResWidth();
		resActualElement.setAttribute("resWidth", resWidth.toString());
		resActualElement.setAttribute("resHeight", resHeight.toString());

		// create Child GroessePlanquadrat
		Element sizePlanquadratElement = new Element("GroessePlanquadrat");
		Integer feldGroesse = SimuKonfiguration.getInstance().getFeldElementGroesse();
		sizePlanquadratElement.addContent(feldGroesse.toString());

		// create Child EinstellungenFrame
		Element einstellungenFrame = new Element("EinstellungenFrame");
		Dimension dimension = SimuKonfiguration.getInstance().getEinstellungenFrameDimension();
		Integer width = dimension.width;
		Integer height = dimension.height;
		einstellungenFrame.setAttribute("width", width.toString());
		einstellungenFrame.setAttribute("height", height.toString());

		// create Child GroesseFeldelement
		Element sizeFieldElement = new Element("GroesseFeldelement");
		double size = SimuKonfiguration.getInstance().getFeldElementGroesseInM();
		sizeFieldElement.addContent(String.valueOf(size));

		// create Child BusGeschwindigkeit
		Element busGeschwindigkeit = new Element("BusGeschwindigkeit");
		double speedBus = SimuKonfiguration.getInstance().getBusGeschwindigkeitInKmH();
		busGeschwindigkeit.addContent(String.valueOf(speedBus));

		// create Child GehGeschwindigkeit
		Element geschwindigkeitPeople = new Element("GehGeschwindigkeit");
		double speedPeople = SimuKonfiguration.getInstance().getGehGeschwindigkeitInKmH();
		geschwindigkeitPeople.addContent(String.valueOf(speedPeople));

		// add Childs to simuConfigElement
		simuConfigElement.addContent(resActualElement);
		simuConfigElement.addContent(sizePlanquadratElement);
		simuConfigElement.addContent(einstellungenFrame);
		simuConfigElement.addContent(sizeFieldElement);
		simuConfigElement.addContent(busGeschwindigkeit);
		simuConfigElement.addContent(geschwindigkeitPeople);

		return simuConfigElement;

	}

	/**
	 * createGesperreteStrasseElement, erzeugt ein gesperrtes Strassen Element
	 * 
	 * @param start
	 * @param end
	 * @param id
	 * @return Element
	 */
	private Element createGesperreteStrasseElement(Point2D start, Point2D end, int id) {
		Element barredWayElement = new Element("GesperrteStrasse");
		barredWayElement.setAttribute("id", String.valueOf(id));// attribute id
		barredWayElement.setAttribute("art", "isGesperrt");// attribute art

		// create Child GesamtKosten
		Element gesamtKostenElement = new Element("GesamtKosten");
		Integer iF = 0;
		gesamtKostenElement.addContent(iF.toString());

		// create Child KostenStartBisPunkt
		Element kostenStartBisPunktElement = new Element("KostenStartBisPunkt");
		Integer iG = 0;
		kostenStartBisPunktElement.addContent(iG.toString());

		// create Child KostenPunktBisZiel
		Element kostenPunktBisZielElement = new Element("KostenPunktBisZiel");
		Integer iH = 0;
		kostenPunktBisZielElement.addContent(iH.toString());

		// create Child Point
		Element barredWayPointElement = new Element("Point");
		Point2D pointStart = start;
		int xStart = (int) pointStart.getX();
		int yStart = (int) pointStart.getY();

		Point2D pointEnd = end;
		int xEnd = (int) pointEnd.getX();
		int yEnd = (int) pointEnd.getY();

		barredWayPointElement.setAttribute("xStart", String.valueOf(xStart));
		barredWayPointElement.setAttribute("yStart", String.valueOf(yStart));
		barredWayPointElement.setAttribute("xEnd", String.valueOf(xEnd));
		barredWayPointElement.setAttribute("yEnd", String.valueOf(yEnd));

		// create Child Feldgroesse
		Element feldgroesseElement = new Element("Feldgroesse");
		double iWidth = 13.0;
		double iHeight = 13.0;
		feldgroesseElement.setAttribute("width", String.valueOf(iWidth));
		feldgroesseElement.setAttribute("height", String.valueOf(iHeight));

		// add Childs to Element streetElement
		barredWayElement.addContent(gesamtKostenElement);
		barredWayElement.addContent(kostenStartBisPunktElement);
		barredWayElement.addContent(kostenPunktBisZielElement);
		barredWayElement.addContent(barredWayPointElement);
		barredWayElement.addContent(feldgroesseElement);

		return barredWayElement;
	}

	/**
	 * createBarredHaltestelle, erzeugt das Element GesperrtesFeld Rückgabe des
	 * Elements barredField
	 * 
	 * @param barredHaltestelle
	 * @return Element
	 */
	private Element createBarredHaltestelle(GesperrteHaltestelle barredHaltestelle) {
		// create Element GesperrtesFeld
		Element barredField = new Element("GesperrtesFeld");
		Integer id = barredHaltestelle.getId();
		barredField.setAttribute("id", id.toString());// attribute id

		// create Child Name
		Element name = new Element("Name");
		name.addContent(barredHaltestelle.getName());

		// create Child Point
		Element pointElement = new Element("Point");
		Point point = barredHaltestelle.getKoordinaten();
		Integer xValue = OrUtil.getPixelXCoordinate(point.x);
		Integer yValue = OrUtil.getPixelXCoordinate(point.y);
		pointElement.setAttribute("xValue", xValue.toString());
		pointElement.setAttribute("yValue", yValue.toString());

		// add Childs to GesperrtesFeld
		barredField.addContent(name);
		barredField.addContent(pointElement);

		return barredField;
	}

	/**
	 * createLinieElement, erzeugt das Element Linie, Rückgabe des Elements
	 * linieElement
	 * 
	 * @param linie
	 * @return Element
	 */
	private Element createLinieElement(Linie linie) {
		// create Element Linie
		Element linieElement = new Element("Linie");
		String id = linie.getId();
		linieElement.setAttribute("id", id);

		// create Child Haltestellen
		Element haltestellenElement = new Element("Haltestellen");
		if (linie.getHaltestellen().size() > 0) {
			// go through all haltestellen
			for (Haltestelle haltestelle : linie.getHaltestellen()) {
				Element haltestelleElement = new Element("Haltestelle");
				Integer iID = haltestelle.getID();
				haltestelleElement.setAttribute("id", iID.toString());
				haltestellenElement.addContent(haltestelleElement); // add
																	// Haltestelle
			}
		}

		// create Child Teilstrecken
		Element teilstreckenElement = new Element("Teilstrecken");
		if (linie.getTeilstrecken().size() > 0) {
			// go through all teilstrecken
			for (Teilstrecke teilstrecke : linie.getTeilstrecken()) {
				Element teilstreckeElement = this.createTeilstreckeElement(teilstrecke);
				teilstreckenElement.addContent(teilstreckeElement); // add
																	// Teilstrecke
			}
		}

		// create Child Color
		Element colorElement = new Element("Color");
		Color linienFarbe = linie.getLinienfarbe();
		colorElement.addContent(String.valueOf(linienFarbe.getRGB()));

		// create Child MaxKapazitaetPassagiere
		Element maxKapacity = new Element("MaxKapazitaetPassagiere");
		maxKapacity.addContent(String.valueOf(linie.getMaxKapBusPassagiere()));

		// create Child Wiederkehrzeit
		Element wiederkehrZeit = new Element("Wiederkehrzeit");
		wiederkehrZeit.addContent(String.valueOf(linie.getFrequenz()));

		// add Childs to Linie
		linieElement.addContent(haltestellenElement);
		linieElement.addContent(teilstreckenElement);
		linieElement.addContent(colorElement);
		linieElement.addContent(maxKapacity);
		linieElement.addContent(wiederkehrZeit);

		return linieElement;
	}

	/**
	 * createTeilstreckeElement, erzeugt das Element Teilstrecke, Rückgabe des
	 * Elements teilstreckeElement
	 * 
	 * @param teilstrecke
	 * @return Element
	 */
	private Element createTeilstreckeElement(Teilstrecke teilstrecke) {
		// create Element Teilstrecke
		Element teilstreckeElement = new Element("Teilstrecke");
		Integer id = teilstrecke.getId();
		teilstreckeElement.setAttribute("id", id.toString());// attribute id

		// create Child Name
		Element nameElement = new Element("Name");
		nameElement.addContent(teilstrecke.toString());

		// create Child StartPoint
		Element startPointElement = new Element("StartPoint");
		Point2D start = teilstrecke.getStart();
		double xValue = start.getX();
		double yValue = start.getY();
		startPointElement.setAttribute("xValue", String.valueOf(xValue));
		startPointElement.setAttribute("yValue", String.valueOf(yValue));

		// create Child EndPoint
		Element endPointElement = new Element("EndPoint");
		Point2D end = teilstrecke.getEnde();
		double xValueEnd = end.getX();
		double yValueEnd = end.getY();
		endPointElement.setAttribute("xValue", String.valueOf(xValueEnd));
		endPointElement.setAttribute("yValue", String.valueOf(yValueEnd));

		// create Child Geschwindigkeit
		Element geschwindigkeit = new Element("Geschwindigkeit");
		Integer iGeschwind = teilstrecke.getGeschwindigkeit();
		geschwindigkeit.addContent(iGeschwind.toString());

		// create Child Breite
		Element breite = new Element("Breite");
		Integer iBreite = teilstrecke.getBreite();
		breite.addContent(iBreite.toString());

		// create Child Laenge
		Element laenge = new Element("Laenge");
		double iLaenge = teilstrecke.getLaenge();
		laenge.addContent(String.valueOf(iLaenge));

		// add Childs to Teilstrecke
		teilstreckeElement.addContent(nameElement);
		teilstreckeElement.addContent(startPointElement);
		teilstreckeElement.addContent(endPointElement);
		teilstreckeElement.addContent(geschwindigkeit);
		teilstreckeElement.addContent(breite);
		teilstreckeElement.addContent(laenge);

		return teilstreckeElement;
	}

	/**
	 * createGoalElement, erzeugt das Element Ziel mit den nötigen Daten,
	 * Rückgabe des Elements Ziel
	 * 
	 * @param goal
	 * @return Element
	 */
	private Element createGoalElement(Ziel goal) {
		// create Element Ziel
		Element goalElement = new Element("Ziel");
		Integer id = goal.getId();
		goalElement.setAttribute("id", id.toString());// attribute id

		// create Child Name
		Element nameElement = new Element("Name");
		nameElement.addContent(goal.toString());

		// create Child Point
		Element goalPointElement = new Element("Point");
		Point point = goal.getZiel();
		Integer xValue = OrUtil.getPixelXCoordinate(point.x);
		Integer yValue = OrUtil.getPixelXCoordinate(point.y);
		goalPointElement.setAttribute("xValue", xValue.toString());
		goalPointElement.setAttribute("yValue", yValue.toString());

		// add Childs to goalElement
		goalElement.addContent(nameElement);
		goalElement.addContent(goalPointElement);

		return goalElement;
	}

	/**
	 * createStrasseElement, erzeugt ein Strassen Element,
	 * 
	 * @param start
	 * @param end
	 * @param id
	 * @return Element
	 */
	private Element createStrasseElement(Point2D start, Point2D end, int id) {
		Element streetElement = new Element("Strasse");
		streetElement.setAttribute("id", String.valueOf(id));// attribute id
		streetElement.setAttribute("art", "isStreet");// attribute art

		// create Child GesamtKosten
		Element gesamtKostenElement = new Element("GesamtKosten");
		Integer iF = 0;
		gesamtKostenElement.addContent(iF.toString());

		// create Child KostenStartBisPunkt
		Element kostenStartBisPunktElement = new Element("KostenStartBisPunkt");
		Integer iG = 0;
		kostenStartBisPunktElement.addContent(iG.toString());

		// create Child KostenPunktBisZiel
		Element kostenPunktBisZielElement = new Element("KostenPunktBisZiel");
		Integer iH = 0;
		kostenPunktBisZielElement.addContent(iH.toString());

		// create Child Point
		Element streetPointElement = new Element("Point");
		Point2D pointStart = start;
		int xStart = (int) pointStart.getX();
		int yStart = (int) pointStart.getY();

		Point2D pointEnd = end;
		int xEnd = (int) pointEnd.getX();
		int yEnd = (int) pointEnd.getY();

		streetPointElement.setAttribute("xStart", String.valueOf(xStart));
		streetPointElement.setAttribute("yStart", String.valueOf(yStart));
		streetPointElement.setAttribute("xEnd", String.valueOf(xEnd));
		streetPointElement.setAttribute("yEnd", String.valueOf(yEnd));

		// create Child Feldgroesse
		Element feldgroesseElement = new Element("Feldgroesse");
		double iWidth = 13.0;
		double iHeight = 13.0;
		feldgroesseElement.setAttribute("width", String.valueOf(iWidth));
		feldgroesseElement.setAttribute("height", String.valueOf(iHeight));

		// add Childs to Element streetElement
		streetElement.addContent(gesamtKostenElement);
		streetElement.addContent(kostenStartBisPunktElement);
		streetElement.addContent(kostenPunktBisZielElement);
		streetElement.addContent(streetPointElement);
		streetElement.addContent(feldgroesseElement);

		return streetElement;
	}

	/**
	 * createHaltestelleElement erzeugt das Element Haltestelle, liefert als
	 * Rückgabewert das Element haltestelleElement
	 * 
	 * @param haltestelle
	 * @return Element
	 */
	private Element createHaltestelleElement(Haltestelle haltestelle) {

		// create Element Haltestelle
		Element haltestelleElement = new Element("Haltestelle");
		Integer id = haltestelle.getID();
		haltestelleElement.setAttribute("id", id.toString());// attribute id

		// create Child Name
		Element haltestelleNameElement = new Element("Name");
		haltestelleNameElement.addContent(haltestelle.getName());

		// create Child Kapazitaet
		Element haltestelleKapazitaetElement = new Element("Kapazitaet");
		Integer kapazitaet = haltestelle.getKapazitaet();
		haltestelleKapazitaetElement.addContent(kapazitaet.toString());

		// create Child Point
		Element haltestellePointElement = new Element("Point");
		Integer xValue = haltestelle.getPixelXCoordinate();
		Integer yValue = haltestelle.getPixelYCoordinate();
		haltestellePointElement.setAttribute("xValue", xValue.toString());
		haltestellePointElement.setAttribute("yValue", yValue.toString());

		// add Childs to Element haltestelleElement
		haltestelleElement.addContent(haltestelleNameElement);
		haltestelleElement.addContent(haltestelleKapazitaetElement);
		haltestelleElement.addContent(haltestellePointElement);

		return haltestelleElement;
	}

	/**
	 * getSimulationConfig
	 * 
	 * @return
	 */
	public SimuKonfiguration getSimulationConfig() {
		return mySimulationConfig;
	}

	/**
	 * setSimulationConfig
	 * 
	 * @param simulationConfig
	 */
	public void setSimulationConfig(SimuKonfiguration simulationConfig) {
		this.mySimulationConfig = simulationConfig;
	}

	/**
	 * getSimulationGrapicModel
	 * 
	 * @return
	 */
	public SimuGraphModel getSimulationGrapicModel() {
		return mySimulationGrapicModel;
	}

	/**
	 * setSimulationGrapicModel,
	 * 
	 * @param simulationGrapicModel
	 */
	public void setSimulationGrapicModel(SimuGraphModel simulationGrapicModel) {
		this.mySimulationGrapicModel = simulationGrapicModel;
	}

	/**
	 * getStreetNet,
	 * 
	 * @return
	 */
	public Strassennetz getStreetNet() {
		return myStreetNet;
	}

	/**
	 * setStreetNet,
	 * 
	 * @param streetNet
	 */
	public void setStreetNet(Strassennetz streetNet) {
		XMLFileManager.myStreetNet = streetNet;
	}

	/**
	 * getVirtualGrid,
	 * 
	 * @return
	 */
	public VirtualGrid getVirtualGrid() {
		return myVirtualGrid;
	}

	/**
	 * setVirtualGrid
	 * 
	 * @param virtualGrid
	 */
	public void setVirtualGrid(VirtualGrid virtualGrid) {
		this.myVirtualGrid = virtualGrid;
	}

	/**
	 * getStreetArray
	 * 
	 * @return
	 */
	public Map<String, StrassenEdge> getStreetArray() {
		return streetArray;
	}

	/**
	 * populate the Map streetArray
	 * 
	 * @param theEdge
	 */
	public void setStreetArray(StrassenEdge theEdge) {
		if (!streetArray.containsKey(String.valueOf(theEdge.getId()))) {
			streetArray.put(String.valueOf(theEdge.getId()), theEdge);
		}
	}

	/**
	 * getGesperrteFelderArray
	 * 
	 * @return
	 */
	public Map<String, GesperrteFelderEdge> getGesperrteFelderArray() {
		return gesperrteFelderArray;
	}

	/**
	 * populate the Map gesperrteFelderArray
	 * 
	 * @param gesperrteFelderArray
	 */
	public void setGesperrteFelderArray(GesperrteFelderEdge theGesperrteEdge) {
		if (!gesperrteFelderArray.containsKey(String.valueOf(theGesperrteEdge.getId()))) {
			gesperrteFelderArray.put(String.valueOf(theGesperrteEdge.getId()), theGesperrteEdge);
		}
	}

	/**
	 * resetPlanquadrate leert alle Strassen und gesperrte Strassen,
	 * 
	 * @return void
	 */
	public void resetPlanquadrate() {
		gesperrteFelderArray.clear();
		streetArray.clear();
	}

	/**
	 * deleteStreetArray, deletes an StrassenEdge from the streetArray,
	 * 
	 * @param theEdge
	 */
	public void deleteStreetArray(StrassenEdge theEdge) {
		streetArray.remove(String.valueOf(theEdge.getId()));

	}

	/**
	 * deleteGesperrteFelderArray, deletes an GesperrteFelderEdge from the
	 * gesperrteFelderArray,
	 * 
	 * @param theGesperrteEdge
	 */
	public void deleteGesperrteFelderArray(GesperrteFelderEdge theGesperrteEdge) {
		gesperrteFelderArray.remove(String.valueOf(theGesperrteEdge.getId()));

	}

	/**
	 * setHaltestellenArray, setzt Haltestellen in einem Array
	 * 
	 * @param h
	 */
	public void setHaltestellenArray(Haltestelle h) {
		// check if Haltestelle is in Array
		for (int i = 0; i < haltestellenArray.size(); i++) {
			// Haltestelle allready exists
			if (haltestellenArray.get(i).getID() == h.getID()) {
				return;
			}
		}
		haltestellenArray.add(h);
	}

	/**
	 * deleteHaltestellenArray, löscht eine Haltestelle in einem Array
	 * 
	 * @param h
	 */
	public void deleteHaltestellenArray(Haltestelle h) {
		for (int i = 0; i < haltestellenArray.size(); i++) {
			if (haltestellenArray.get(i).getID() == h.getID()) {
				Logger.getInstance().log("Haltestelle: " + h.getID() + " deleted!");
				haltestellenArray.remove(i);
			}
		}
	}

	/**
	 * getHaltestellenArray
	 * 
	 * @return
	 */
	public ArrayList<Haltestelle> getHaltestellenArray() {
		return haltestellenArray;
	}

	/**
	 * setGesperrteHaltestellenArray, setzt gesperrte Haltestellen in einem
	 * Array
	 * 
	 * @param h
	 */
	public void setGesperrteHaltestellenArray(GesperrteHaltestelle h) {
		// check if gesperrte Haltestelle is in Array
		for (int i = 0; i < gesperrteHaltestellenArray.size(); i++) {
			// gesperrte Haltestelle allready exists
			if (gesperrteHaltestellenArray.get(i).getId() == h.getId()) {
				return;
			}
		}
		gesperrteHaltestellenArray.add(h);
	}

	/**
	 * deleteGesperrteHaltestellenArray, löscht eine gesperrte Haltestelle in
	 * einem Array
	 * 
	 * @param h
	 */
	public void deleteGesperrteHaltestellenArray(GesperrteHaltestelle h) {
		for (int i = 0; i < gesperrteHaltestellenArray.size(); i++) {
			if (gesperrteHaltestellenArray.get(i).getId() == h.getId()) {
				Logger.getInstance().log("Gesperrte Haltestelle: " + h.getId() + " deleted!");
				gesperrteHaltestellenArray.remove(i);
			}
		}
	}

	/**
	 * getGesperrteHaltestellenArray
	 * 
	 * @return
	 */
	public ArrayList<GesperrteHaltestelle> getGesperrteHaltestellenArray() {
		return gesperrteHaltestellenArray;
	}

	/**
	 * setZieleArray, setzt Ziele in einem Array
	 * 
	 * @param z
	 */
	public void setZieleArray(Ziel z) {
		// check if Ziel is in Array
		for (int i = 0; i < zieleArray.size(); i++) {
			// Ziel allready exists
			if (zieleArray.get(i).getId() == z.getId()) {
				return;
			}
		}
		zieleArray.add(z);
	}

	/**
	 * deleteZieleArray, löscht ein Ziel in einem Array
	 * 
	 * @param z
	 */
	public void deleteZieleArray(Ziel z) {
		for (int i = 0; i < zieleArray.size(); i++) {
			if (zieleArray.get(i).getId() == z.getId()) {
				Logger.getInstance().log("Ziel: " + z.getId() + " deleted!");
				zieleArray.remove(i);
			}
		}
	}

	/**
	 * getGesperrteHaltestellenArray
	 * 
	 * @return
	 */
	public ArrayList<Ziel> getZieleArray() {
		return zieleArray;
	}

	public void resetHaltestellen() {
		haltestellenArray.clear();
		gesperrteHaltestellenArray.clear();
	}

	public void resetZiele() {
		zieleArray.clear();
	}

}
