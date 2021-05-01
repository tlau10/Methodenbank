package de.fh_konstanz.simubus.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelEvent.GraphModelChange;
import org.jgraph.graph.EdgeView;

import de.fh_konstanz.simubus.model.GesperrteHaltestelle;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.Planquadrat;
import de.fh_konstanz.simubus.model.SimuGraphModel;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Teilstrecke;
import de.fh_konstanz.simubus.model.VirtualGrid;
import de.fh_konstanz.simubus.model.Ziel;

import de.fh_konstanz.simubus.model.optimierung.PathfinderOR;
import de.fh_konstanz.simubus.view.SimuControlPanel;

import de.fh_konstanz.simubus.view.SimuGraph;
import de.fh_konstanz.simubus.view.SimuPanel;

/**
 * Die Klasse <code>XMLFileLoader</code> lädt das XML- Dokument in die aktuelle Umgebung
 * 
 * @author Johannes Frei, Dominik Heller, Daniel Merkle
 * @version 1.0
 * 
 */
public class XMLFileLoader {

	//member Variables
	private Document myXMLDocument = null;
	private Set<Haltestelle> myHaltestellen = null;
	private ArrayList<Ziel> myZiele = null;
	private List<Planquadrat> myPlanquadrate = null;
	private SimuKonfiguration myConfig = null;
	private List<GesperrteHaltestelle> myGesperrteHaltestellen = null;

	//initialize variables
	protected XMLFileLoader(File file) throws JDOMException, IOException {
		this.myXMLDocument = new SAXBuilder().build(file);
		this.myHaltestellen = new HashSet<Haltestelle>();
		this.myZiele = new ArrayList<Ziel>();
		this.myPlanquadrate = new ArrayList<Planquadrat>();
		this.myConfig = SimuKonfiguration.getInstance();
		this.myGesperrteHaltestellen = new ArrayList<GesperrteHaltestelle>();
	}

	/**
	 * loadHaltestelleFromXML,
	 * lädt alle Haltestellen aus einem XML- File
	 * Rückgabe eines Sets an Haltestellen
	 * 
	 * @return Set<Haltestelle>
	 */
	public Set<Haltestelle> loadHaltestelleFromXML() {
		//get Rootelement
		Element root = myXMLDocument.getRootElement();

		Strassennetz net = Strassennetz.getInstance();
		
		//proof if Haltestellen exists
		Element Strassennetz = root.getChild("Strassennetz");
		if(Strassennetz == null){ return null; }
		Element halteStellen = Strassennetz.getChild("Haltestellen");
		if (halteStellen == null){ return null; }
		
		//get Childs
		List childs = halteStellen.getChildren();
		//if not empty
		if (!childs.isEmpty()) {
			Iterator iter = childs.iterator(); //iterator
			//go through
			while (iter.hasNext()) {
				Element haltestelle = (Element) iter.next();
				Integer id = Integer.parseInt(haltestelle.getAttribute("id")
						.getValue());
				String name = haltestelle.getChild("Name").getText();
				Integer kapazitaet = Integer.parseInt(haltestelle.getChild(
						"Kapazitaet").getText());
				Integer xValue = Integer.parseInt(haltestelle.getChild("Point")
						.getAttribute("xValue").getValue());
				Integer yValue = Integer.parseInt(haltestelle.getChild("Point")
						.getAttribute("yValue").getValue());
				Haltestelle hs = new Haltestelle(id.intValue(), xValue
						.intValue(), yValue.intValue(), name, kapazitaet
						.intValue());
				myHaltestellen.add(hs);
				
			}
			return myHaltestellen;
		}

		return null;
	}

	/**
	 * Diese Methode setzt im {@link de.fh_konstanz.simubus.view.SimuPanel} alle
	 * Attribute aus den gespeicherten XML Files.
	 * 
	 * @param sp
	 * @see de.fh_konstanz.simubus.view.SimuPanel
	 */
	public void initSimuPanel(SimuPanel sp) {
		
		//load SimuKonfiguration
		this.loadSimuConfiguration();
		
		// load Streets
		this.loadPlanquadrateWithPropertyStreet();
		
		// load Barred Ways
		this.loadPlanquadrateWithPropertyBarredWay();
		
		//load Haltestellen
		if (this.loadHaltestelleFromXML() != null) {
			Iterator iter = myHaltestellen.iterator();
			while (iter.hasNext()) {
				Haltestelle h = (Haltestelle) iter.next();
				sp.insertHaltestelle(h.getKoordinaten(), h.getName());
			}
		}
		
		//	load Ziele
		if(this.loadZieleFromXML() != null){
			Iterator zielIter = this.myZiele.iterator();
			while (zielIter.hasNext()) {
				Ziel z = (Ziel) zielIter.next();
				sp.insertZiel(z.getZiel());
			}
		}
		
		//load gesperrte Haltestellen
		if(this.loadPlanquadrateGesperrteFelder() != null){
			Iterator gesperrteFelderIter = myGesperrteHaltestellen.iterator();
			while(gesperrteFelderIter.hasNext()){
				GesperrteHaltestelle gh = (GesperrteHaltestelle) gesperrteFelderIter.next();
				sp.insertGesperrteHaltestelle(gh.getKoordinaten());
			}
		}
		
		// load all Linien between Haltestellen
		this.loadLinienFromXML();

		SimuGraphModel.setInstance( SimuGraphModel.getInstance() );
		VirtualGrid.setInstance( VirtualGrid.getInstance() );
		
	}
	
	/**
	 * loadZieleFromXML,
	 * lädt alle Ziele aus einer XML- Instanz,
	 * Rückgabe aller Ziel als eine ArrayList
	 * 
	 * @return ArrayList<Ziel>
	 */
	public ArrayList<Ziel> loadZieleFromXML() {
		Strassennetz streetNet = Strassennetz.getInstance();
		
		//root Element
		Element root = myXMLDocument.getRootElement();
		
		//proof if Ziele exist
		Element Strassennetz = root.getChild("Strassennetz");
		if(Strassennetz == null){ return null; }
		Element ziele = Strassennetz.getChild("Ziele");
		if(ziele == null){ return null; }
		
		//get Childs
		List childs = ziele.getChildren();
		//if exits
		if (!childs.isEmpty()) {

			Iterator iter = childs.iterator();
			// go through
			while (iter.hasNext()) {
				Element ziel = (Element) iter.next();
				Integer id = Integer.parseInt(ziel.getAttributeValue("id"));
				String name = ziel.getChildText("Name");
				Integer xValue = Integer.parseInt(ziel.getChild("Point")
						.getAttributeValue("xValue"));
				Integer yValue = Integer.parseInt(ziel.getChild("Point")
						.getAttributeValue("yValue"));
				Point p = new Point(xValue.intValue(), yValue.intValue());
				Ziel z = new Ziel(p, id.intValue());
				z.setName(name);
				myZiele.add(z);
				
			}
			return myZiele;
		}
		return null;
	}
	
	/**
	 * loadSimuConfiguration,
	 * lädt die nötigen Daten für die SimuKonfiguration,
	 * 
	 * @return void
	 */
	public void loadSimuConfiguration(){
		// get RootElement
		Element root = myXMLDocument.getRootElement();
		Element SimuConfig = root.getChild("SimuKonfiguration");
		if(SimuConfig == null){ return; }
		
		Element AktuelleAufloesung = SimuConfig.getChild("AktuelleAufloesung");
	    Integer resWidth = Integer.parseInt(AktuelleAufloesung.getAttributeValue("resWidth"));
		Integer resHeight = Integer.parseInt(AktuelleAufloesung.getAttributeValue("resHeight"));
		Element GroessePlanquadrat = SimuConfig.getChild("GroessePlanquadrat");
		Element EinstellungenFrame = SimuConfig.getChild("EinstellungenFrame");
		Integer Width = Integer.parseInt(EinstellungenFrame.getAttributeValue("width"));
		Integer Height = Integer.parseInt(EinstellungenFrame.getAttributeValue("height"));
		Element GroesseFeldelement = SimuConfig.getChild("GroesseFeldelement");
		Double sizeFieldElement = Double.parseDouble(GroesseFeldelement.getText());
		Element BusGeschwindigkeit = SimuConfig.getChild("BusGeschwindigkeit");
		Double busSpeed = Double.parseDouble(BusGeschwindigkeit.getText());
		Element GehGeschwindigkeit = SimuConfig.getChild("GehGeschwindigkeit");
		Double gehSpeed = Double.parseDouble(GehGeschwindigkeit.getText());
		
		if(resWidth == 1024){this.myConfig.setActiveResolutionFromCombo(0); }
		else{
			this.myConfig.setActiveResolutionFromCombo(1);
		}

		this.myConfig.setBusGeschwindigkeitInKmH(busSpeed);
		this.myConfig.setFeldElementGroesseInM(sizeFieldElement);
		this.myConfig.setGehGeschwindigkeitInKmH(gehSpeed);
		
	}
	
	/**
	 * loadPlanquadrateWithPropertyBarredWay,
	 * lädt alle gesperrte Strassen, die in ein XML- File geschrieben wurden,
	 * 
	 * @return void
	 */
	public void loadPlanquadrateWithPropertyBarredWay(){
		// get RootElement
		Element root = myXMLDocument.getRootElement();
		
		//proof if gesperrte Strassen exists
		Element streetNet = root.getChild("Strassennetz");
		if(streetNet == null){ return; }
		Element planQuadrate = streetNet.getChild("Planquadrate");
		if(planQuadrate == null){ return; }
		
		List children = planQuadrate.getChildren("PlanquadratGesperrteStrasse");

		//if PlanquadratStrasse exist
		if (!children.isEmpty()) {

			Iterator iter = children.iterator();
			//go through
			while (iter.hasNext()) {
				//Element PlanquadratStrasse as Element
				Element plan = (Element) iter.next();
				//get Childs from...
				List childs = plan.getChildren("GesperrteStrasse");
				//iterator...
				Iterator iteratorChilds = childs.iterator();
				//go through Childs 
				while(iteratorChilds.hasNext()){
					//get Element Strasse
					Element planquadrat = (Element) iteratorChilds.next();
					Integer id = Integer.parseInt(planquadrat.getAttributeValue("id"));
					
					String art = planquadrat.getAttributeValue("art");
					Integer gesKosten = Integer.parseInt(planquadrat.getChildText("GesamtKosten"));
					Integer kostenStartBisPunkt = Integer.parseInt(planquadrat.getChildText("KostenStartBisPunkt"));
					Integer kostenPunktBisZie = Integer.parseInt(planquadrat.getChildText("KostenPunktBisZiel"));
					//get start Point
					Integer xStart = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("xStart"));
					Integer yStart = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("yStart"));
					//get end Point
					Integer xEnd = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("xEnd"));
					Integer yEnd = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("yEnd"));
					
					//create Planquadrat
					Planquadrat p = new Planquadrat(xStart.intValue(), yStart.intValue());
					p.setID(id.intValue()); //set ID
					// the art
					p.setGesperrt();
					
					//get Feldgroesse
					Double var = Double.parseDouble(planquadrat.getChild("Feldgroesse").getAttributeValue("width"));
					int width =  var.intValue();
					//set the values on Planquadrat
					p.setSizeOfField(width);
					p.setValues(gesKosten.intValue(), kostenStartBisPunkt.intValue(), kostenPunktBisZie.intValue());
					p.setXEnd(xEnd.intValue());
					p.setYEnd(yEnd.intValue());
					
					myPlanquadrate.add(p); //fill List with Planquadrats
										
					//set the Points
					Point2D startPoint = new Point2D.Double(p.getX(), p.getY());
					Point2D endPoint = new Point2D.Double(p.getXEnd(), p.getYEnd());
					
					SimuPanel.getInstance().insertGesperrteStrasseAfterLoading(startPoint, endPoint);
				}
				myPlanquadrate.clear();
			}
		}
		
	}

	/**
	 * loadPlanquadrateWithPropertyStreet,
	 * lädt alle Strassen, die in ein XML- File geschrieben wurden,
	 * 
	 * @return void
	 */
	public void  loadPlanquadrateWithPropertyStreet() {
		//get RootElement
		Element root = myXMLDocument.getRootElement();
		
		//proof if Strassen exist
		Element streetNet = root.getChild("Strassennetz");
		if(streetNet == null){ return; }
		Element planQuadrate = streetNet.getChild("Planquadrate");
		if(planQuadrate == null){ return; }
		
		List children = planQuadrate.getChildren("PlanquadratStrasse");

		//if PlanquadratStrasse exist
		if (!children.isEmpty()) {

			Iterator iter = children.iterator();
			//go through
			while (iter.hasNext()) {
				//Element PlanquadratStrasse as Element
				Element plan = (Element) iter.next();
				//get Childs from...
				List childs = plan.getChildren("Strasse");
				//iterator...
				Iterator iteratorChilds = childs.iterator();
				//go through Childs 
				while(iteratorChilds.hasNext()){
					//get Element Strasse
					Element planquadrat = (Element) iteratorChilds.next();
					Integer id = Integer.parseInt(planquadrat.getAttributeValue("id"));
					
					String art = planquadrat.getAttributeValue("art");
					Integer gesKosten = Integer.parseInt(planquadrat.getChildText("GesamtKosten"));
					Integer kostenStartBisPunkt = Integer.parseInt(planquadrat.getChildText("KostenStartBisPunkt"));
					Integer kostenPunktBisZie = Integer.parseInt(planquadrat.getChildText("KostenPunktBisZiel"));
					//get start Point
					Integer xStart = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("xStart"));
					Integer yStart = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("yStart"));
					//get end Point
					Integer xEnd = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("xEnd"));
					Integer yEnd = Integer.parseInt(planquadrat.getChild("Point").getAttributeValue("yEnd"));
					
					//create Planquadrat
					Planquadrat p = new Planquadrat(xStart.intValue(), yStart.intValue());
					p.setID(id.intValue()); //set ID
					// the art
					p.setStreet();
					
					//get Feldgroesse
					Double var = Double.parseDouble(planquadrat.getChild("Feldgroesse").getAttributeValue("width"));
					int width =  var.intValue();
					//set the values on Planquadrat
					p.setSizeOfField(width);
					p.setValues(gesKosten.intValue(), kostenStartBisPunkt.intValue(), kostenPunktBisZie.intValue());
					p.setXEnd(xEnd.intValue());
					p.setYEnd(yEnd.intValue());
					
					myPlanquadrate.add(p); //fill List with Planquadrats
										
					//set the Points
					Point2D startPoint = new Point2D.Double(p.getX(), p.getY());
					Point2D endPoint = new Point2D.Double(p.getXEnd(), p.getYEnd());
					
					//set the SimuPanel
					SimuPanel.getInstance().insertStrasseAfterLoading(startPoint, endPoint);
					
					
				}
				myPlanquadrate.clear();
			}
		}
		
	}
	
	/**
	 * lädt alle Linien zwischen Haltestellen
	 * 
	 * @return void
	 */
	public void loadLinienFromXML(){
		
		//variables
		List<Integer> aHaltestellen = new ArrayList<Integer>();
		List<Haltestelle> hList = new ArrayList<Haltestelle>();
		List<Teilstrecke> aTeilstrecken = new ArrayList<Teilstrecke>();
		
		Linie linie; 
		
		//Rootelement
		Element root = myXMLDocument.getRootElement();
		
		//vproof if Linien exist
		Element streetNet = root.getChild("Strassennetz");
		if(streetNet == null){ return; }
		Element linien = streetNet.getChild("Linien");
		if(linien == null){ return; }
	
		//list of all Linien Elements
		List linienList = linien.getChildren();
		if(linienList == null){return;}
		
		//<Linie>
		if (!linienList.isEmpty()) {
			Iterator iter = linienList.iterator();
			while(iter.hasNext()){
				//<Linie>
				Element line = (Element) iter.next();
				String id = line.getAttributeValue("id");  // get Id of Linie
				
				linie = new Linie(id);
				
				// <Haltestellen>
				List haltestellenOfLinie = line.getChild("Haltestellen").getChildren("Haltestelle");
                //fill the Array of Haltestellen
				if (!haltestellenOfLinie.isEmpty()) {
					Iterator iterator = haltestellenOfLinie.iterator(); //iterator
					//go through
					while (iterator.hasNext()) {
						Element haltestelle = (Element) iterator.next();
						Integer idOfHaltestelle = Integer.parseInt(haltestelle.getAttribute("id").getValue());
						aHaltestellen.add(idOfHaltestelle);
					}
				
					//load Haltestellen from XML
					Iterator i = myHaltestellen.iterator();
					while (i.hasNext()) {
						Haltestelle h = (Haltestelle) i.next();
						Integer iId = h.getID(); //get Id of Haltestelle
						if(aHaltestellen.contains(iId)){
							hList.add(h);
						}
					}
				}
				
				// <Teilstrecken>
				List teilstreckenOfLinie = line.getChild("Teilstrecken").getChildren("Teilstrecke");
				// fill the Array of Teilstrecken
				if (!teilstreckenOfLinie.isEmpty()) {
					Iterator iterator = teilstreckenOfLinie.iterator(); //iterator
					//go through
					while (iterator.hasNext()) {
						Element teilstrecke = (Element) iterator.next();
						Integer idOfTeilstrecke = Integer.parseInt(teilstrecke.getAttribute("id").getValue());
						String name = teilstrecke.getChildText("Name");
						Double xValueStart = Double.parseDouble(teilstrecke.getChild("StartPoint").getAttributeValue("xValue"));
						Double yValueStart = Double.parseDouble(teilstrecke.getChild("StartPoint").getAttributeValue("yValue"));
						Double xValueEnd = Double.parseDouble(teilstrecke.getChild("EndPoint").getAttributeValue("xValue"));
						Double yValueEnd = Double.parseDouble(teilstrecke.getChild("EndPoint").getAttributeValue("yValue"));
						Integer speed = Integer.parseInt(teilstrecke.getChildText("Geschwindigkeit"));
						Integer breite = Integer.parseInt(teilstrecke.getChildText("Breite"));
						Double laenge = Double.parseDouble(teilstrecke.getChildText("Laenge"));
						
						//startPoint, endPoint
						Point2D startPoint = new Point(xValueStart.intValue(), yValueStart.intValue());
						Point2D endPoint = new Point(xValueEnd.intValue(), yValueEnd.intValue());
						
						Teilstrecke t = new Teilstrecke(idOfTeilstrecke, name, startPoint, endPoint, speed, breite);
						aTeilstrecken.add(t);
						linie.setPunkte(idOfTeilstrecke, startPoint, endPoint);
					}
				}
				
				//Color
				String color = line.getChildText("Color");
				
				Color c = Color.decode(color);
				Integer kapPassagiere = Integer.parseInt(line.getChildText("MaxKapazitaetPassagiere"));
				Double wiederkehr = Double.parseDouble(line.getChildText("Wiederkehrzeit"));
				
				//create Linie
				linie.setHaltestellen(hList);
				linie.setTeilstrecken(aTeilstrecken);
				linie.setLinienfarbe(c);
				linie.setFrequenz(wiederkehr);
				linie.setMaxKapBusPassagiere(kapPassagiere);
				
				
				//Connect Linie with Haltestellen
				SimuPanel.getInstance().connectHsWithLine(linie);
				
				aTeilstrecken.clear();
				hList.clear();
				
				
			}
		}
		return;  // no Lines
	}

	
	/**
	 * läd alle gesperrten Haltestellen aus dem XML-File
	 * 
	 * @return List
	 */
	public List<GesperrteHaltestelle> loadPlanquadrateGesperrteFelder(){
		//Rootelement
		Element root = myXMLDocument.getRootElement();
		
		//proof if gesperrte Haltestellen exist
		Element streetNet = root.getChild("Strassennetz");
		if(streetNet == null){ return null; }
		Element gesperrteFelder = streetNet.getChild("GesperrteFelder");
		if(gesperrteFelder == null){ return null; }
		List gesperrteFelderList = gesperrteFelder.getChildren();
		if(gesperrteFelderList == null){return null;}
		
		//<GesperrteFelder>
		Iterator gesperrteFelderIter = gesperrteFelderList.iterator();
		
		while(gesperrteFelderIter.hasNext()){
			
			//<GesperrtesFeld>
			Element gesperrtesFeld = (Element) gesperrteFelderIter.next();
			//get the values
			Integer id = Integer.parseInt(gesperrtesFeld.getAttributeValue("id"));
			//no Child availabale
			String name = gesperrtesFeld.getChildText("Name");
			
			Integer xValue = Integer.parseInt(gesperrtesFeld.getChild("Point")
					.getAttribute("xValue").getValue());
			Integer yValue = Integer.parseInt(gesperrtesFeld.getChild("Point")
					.getAttribute("yValue").getValue());
			
			//set values on GesperrteHaltestelle
			Point point = new Point(xValue.intValue(), yValue.intValue());
			GesperrteHaltestelle gesperrteHaltestelle = new GesperrteHaltestelle(point, id.intValue());
			gesperrteHaltestelle.setName(name);
			myGesperrteHaltestellen.add(gesperrteHaltestelle);
						
		}
		return myGesperrteHaltestellen;
	}
	
	
}
