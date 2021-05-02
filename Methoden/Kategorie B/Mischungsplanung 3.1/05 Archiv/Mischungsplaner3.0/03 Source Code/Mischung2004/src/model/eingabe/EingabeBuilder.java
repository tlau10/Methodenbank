/*
 * EingabeBuilder.java
 *
 * Created on 15. Mai 2004, 12:13
 */

package model.eingabe;

import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xml.serialize.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Der EingabeBuilder ist für das Laden und Speichern der Eingabe Objekte
 * verantwortlich. Die Eingabe Objekte werden ins XML Format übertragen.
 * Für die XML Verarbeitung verwenden wir das W3C DOM.
 * Den Xerces Parser benötigen wir für die Serializierung der Eingabe Objekte.
 * @author  hmaass
 */
public class EingabeBuilder {
    private Document eingabeDokument;
    /** Creates a new instance of EingabeBuilder */
    public EingabeBuilder() {

    }
    /**
     * erzeugt ein Dokument aus dem Eingabe Objekt. Anschliessend wird 
     * das Dokument in die übergebene Datei im XML Format abgespeichert.
     * @param eingabe Das Eingabe Objekt, das abgespeichert werden soll.
     * @param file    Die Datei, in der abgespeichert wird.
     */
    public void saveEingabe(Eingabe eingabe, File file) throws EingabeBuilderException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db  = dbf.newDocumentBuilder();
            Document dokument       = db.newDocument();
            Node eingabeNode        = dokument.createElement("eingabe");
            Node anforderungenNode  = dokument.createElement("anforderungen");
            Node sortenNode         = dokument.createElement("sorten");
            
            eingabeNode.appendChild(anforderungenNode);
            eingabeNode.appendChild(sortenNode);
            dokument.appendChild(eingabeNode);
            
            /** Anforderungen in das DOM einbauen */
            Iterator itAnforderungen = eingabe.getAnforderungen();
            while (itAnforderungen.hasNext()) {
                Anforderung anforderung = (Anforderung) itAnforderungen.next();
                Node anforderungNode = dokument.createElement("anforderung");
                Node idNode          = dokument.createElement("id");
                Node idTextNode      = dokument.createTextNode(Integer.toString(anforderung.getId()));
                idNode.appendChild(idTextNode);
                
                Node nameNode        = dokument.createElement("name");
                Node nameTextNode    = dokument.createTextNode(anforderung.getName());
                nameNode.appendChild(nameTextNode);
                
                Node wertNode        = dokument.createElement("wert");
                Node wertTextNode    = dokument.createTextNode(Double.toString(anforderung.getWert()));
                wertNode.appendChild(wertTextNode);
                
                anforderungNode.appendChild(idNode);
                anforderungNode.appendChild(nameNode);
                anforderungNode.appendChild(wertNode);
                /** hänge die aktuelle Anforderung an den anforderungen Knoten an*/
                anforderungenNode.appendChild(anforderungNode);
            }
            /** Sorten in das DOM einbauen*/
            Iterator itSorten = eingabe.getSorten();
            while (itSorten.hasNext()) {
                Sorte sorte = (Sorte) itSorten.next();
                
                Node sorteNode  = dokument.createElement("sorte");
                // id
                Node idNode     = dokument.createElement("id");
                Node idTextNode = dokument.createTextNode(Integer.toString(sorte.getId()));
                idNode.appendChild(idTextNode);
                
                // preis
                Node preisNode      = dokument.createElement("preis");
                Node preisTextNode  = dokument.createTextNode(Double.toString(sorte.getPreis()));
                preisNode.appendChild(preisTextNode);
                
                // id und preis an sorte anhängen
                sorteNode.appendChild(idNode);
                sorteNode.appendChild(preisNode);
                
                Iterator itErfuellungAnforderungen = sorte.getErfuellungAnforderungen();
                while (itErfuellungAnforderungen.hasNext()) {
                    ErfuellungAnforderung ea = (ErfuellungAnforderung) itErfuellungAnforderungen.next();
                    
                    Node erfuellungAnforderungNode = dokument.createElement("erfuellungAnforderung");
                    
                    // anforderungId
                    Node anforderungIdNode         = dokument.createElement("erfuellungAnforderungId");
	            Node anforderungIdTextNode     = dokument.createTextNode(Integer.toString(ea.getAnforderungId()));
                    anforderungIdNode.appendChild(anforderungIdTextNode);
                    
                    // wert
                    Node wertNode     = dokument.createElement("wert");
                    Node wertTextNode = dokument.createTextNode(Double.toString(ea.getWert()));
                    wertNode.appendChild(wertTextNode);
                    
                    erfuellungAnforderungNode.appendChild(anforderungIdNode);
                    erfuellungAnforderungNode.appendChild(wertNode);
                    
                    sorteNode.appendChild(erfuellungAnforderungNode);
                    ea.getWert();
                }
                // Die sorte unter dem sorten Knoten ablegen
                sortenNode.appendChild(sorteNode);
            }
            
            display(dokument.getChildNodes(),0);
            OutputStream os = new FileOutputStream(file);
            OutputFormat of = new OutputFormat(dokument);
            of.setLineSeparator(LineSeparator.Windows);
            of.setIndenting(true);
            of.setLineWidth(0);             
            of.setPreserveSpace(true);
            
            Serializer s   = new XMLSerializer(os,of);
            DOMSerializer ds = s.asDOMSerializer();
            
            ds.serialize(dokument);
         } catch (Exception e) {
             e.printStackTrace(System.err);
             throw new EingabeBuilderException(e.getMessage());

         }
    }
    
    /**
     * parst das übergebene XML File und erzeugt ein Eingabe Objekt, 
     * welches zurückgegeben wird. 
     * @file Die XML Datei mit den Eingaben.
     */
    public Eingabe loadEingabe(File file) throws EingabeBuilderException {
        /** eine DokumentBuilderFactory über eine statische Methode holen */
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       factory.setIgnoringElementContentWhitespace(true);
       factory.setIgnoringComments(true);
        
        try {
	      /** einen DocumentBuilder über die Fabrik holen */
            DocumentBuilder builder = factory.newDocumentBuilder();

	      /** das XML File über den Builder parsen */
            eingabeDokument = builder.parse(file);
            eingabeDokument.getDocumentElement().normalize();
            Eingabe eingabe = new Eingabe();
            
            /** Die anforderungen parsen */
            NodeList anforderungListe = eingabeDokument.getElementsByTagName("anforderung");
            for (int i=0; i<anforderungListe.getLength(); i++) {
                Node anforderungNode              = anforderungListe.item(i);
                NodeList anforderungChildrenNodes = anforderungNode.getChildNodes();
                Anforderung anforderung           = new Anforderung();
                
                for (int r=0;r<anforderungChildrenNodes.getLength(); r++) {
                    Node anforderungChildNode = anforderungChildrenNodes.item(r);
                    String nodeName  = anforderungChildNode.getNodeName();
                    
                    if (anforderungChildNode.hasChildNodes()) {
                        String nodeValue = anforderungChildNode.getFirstChild().getNodeValue();
                        if (nodeName.equals("id")) {
                            anforderung.setId(Integer.parseInt(nodeValue));
                        } else if (nodeName.equals("name")) {
                            anforderung.setName(nodeValue);
                        } else if (nodeName.equals("wert")) {
                            anforderung.setWert(Double.parseDouble(nodeValue));
                        }
                    }
                }
                eingabe.addAnforderung(anforderung);
            }
            
            /** Die Sorten Parsen */
            NodeList sortenListe = eingabeDokument.getElementsByTagName("sorte");
            for (int i=0; i<sortenListe.getLength();i++) {
                Node sorteNode = sortenListe.item(i);
                NodeList sorteChildrenNodes = sorteNode.getChildNodes();
                Sorte sorte = new Sorte();
                for (int r=0;r<sorteChildrenNodes.getLength();r++) {
                    Node sorteChildNode = sorteChildrenNodes.item(r);
   
                    if (sorteChildNode.hasChildNodes() ) {
                        String nodeName  = sorteChildNode.getNodeName();
                        String nodeValue = sorteChildNode.getFirstChild().getNodeValue();
                        if (nodeName.equals("id")) {
                            sorte.setId(Integer.parseInt(nodeValue));
                        } else if (nodeName.equals("preis")) {
                            sorte.setPreis(Double.parseDouble(nodeValue));
			} else if (nodeName.equals("erfuellungAnforderung")) {
                            NodeList erfuellungAnforderungChildrenNodes = sorteChildNode.getChildNodes();
                            ErfuellungAnforderung ea = new ErfuellungAnforderung();
                            sorte.addErfuellungAnforderung(ea);
                            for (int s=0;s<erfuellungAnforderungChildrenNodes.getLength();s++) {
                                Node erfuellungAnforderungChildNode = erfuellungAnforderungChildrenNodes.item(s);
                                if (erfuellungAnforderungChildNode.hasChildNodes()) {
                                    nodeName  = erfuellungAnforderungChildNode.getNodeName();
                                    nodeValue = erfuellungAnforderungChildNode.getFirstChild().getNodeValue();
                                    if (nodeName.equals("anforderungId")) {
                                        ea.setAnforderungId(Integer.parseInt(nodeValue));
                                    } else if (nodeName.equals("wert")) {
                                        ea.setWert(Double.parseDouble(nodeValue));
                                    }
                                }
                            }
                        }
                   }
               
               }
                  eingabe.addSorte(sorte);
            }

           display(eingabeDokument.getChildNodes(), 0);
            return eingabe;
        } catch(Exception e) {
            e.printStackTrace(System.err);
            throw new EingabeBuilderException(e.getMessage());
        } 
    }
    
    
      /** private Testmethode zum Anzeigen des DOM Baums
       */
      private void display(NodeList aNodeList, int depth) {
      /** depthString um die Tiefe der Ebene zu verdeutlichen */
      StringBuffer depthString = new StringBuffer();
      for (int i=0;i<depth;i++) {
        depthString.append("   ");
      }
      depthString.append("+ ");

      /** die aktuelle Knotenliste traversieren*/
      for (int i=0;i<aNodeList.getLength();i++) {
           Node node       = aNodeList.item(i);
           String name     = node.getNodeName();
           if (name.startsWith("#")) {
               System.out.println(depthString + name + ": " + node.getNodeValue());
           } else {
                System.out.println(depthString + "ELEMENT: " + name );
           }
           NodeList children = node.getChildNodes();
            
           /** Rekursiv den Baum traversieren */
           if (children.getLength() != 0) {
                depth++;
                display(children, depth);
            }
        }
    }

    
}
