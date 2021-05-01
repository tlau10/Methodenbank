
package de.htwg_konstanz.tpsolver.web.solver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.htwg_konstanz.tpsolver.web.solver package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ProcessXml_QNAME = new QName("http://solver.web.tpsolver.htwg_konstanz.de/", "processXml");
    private final static QName _ProcessXmlResponse_QNAME = new QName("http://solver.web.tpsolver.htwg_konstanz.de/", "processXmlResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.htwg_konstanz.tpsolver.web.solver
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProcessXmlResponse }
     * 
     */
    public ProcessXmlResponse createProcessXmlResponse() {
        return new ProcessXmlResponse();
    }

    /**
     * Create an instance of {@link ProcessXml }
     * 
     */
    public ProcessXml createProcessXml() {
        return new ProcessXml();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solver.web.tpsolver.htwg_konstanz.de/", name = "processXml")
    public JAXBElement<ProcessXml> createProcessXml(ProcessXml value) {
        return new JAXBElement<ProcessXml>(_ProcessXml_QNAME, ProcessXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solver.web.tpsolver.htwg_konstanz.de/", name = "processXmlResponse")
    public JAXBElement<ProcessXmlResponse> createProcessXmlResponse(ProcessXmlResponse value) {
        return new JAXBElement<ProcessXmlResponse>(_ProcessXmlResponse_QNAME, ProcessXmlResponse.class, null, value);
    }

}
