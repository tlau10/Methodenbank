
package de.htwg_konstanz.tpsolver.web.metadata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.htwg_konstanz.tpsolver.web.metadata package. 
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

    private final static QName _GetAdequateSolversResponse_QNAME = new QName("http://metadata.web.tpsolver.htwg_konstanz.de/", "getAdequateSolversResponse");
    private final static QName _GetAdequateSolvers_QNAME = new QName("http://metadata.web.tpsolver.htwg_konstanz.de/", "getAdequateSolvers");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.htwg_konstanz.tpsolver.web.metadata
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAdequateSolversResponse }
     * 
     */
    public GetAdequateSolversResponse createGetAdequateSolversResponse() {
        return new GetAdequateSolversResponse();
    }

    /**
     * Create an instance of {@link GetAdequateSolvers }
     * 
     */
    public GetAdequateSolvers createGetAdequateSolvers() {
        return new GetAdequateSolvers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAdequateSolversResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://metadata.web.tpsolver.htwg_konstanz.de/", name = "getAdequateSolversResponse")
    public JAXBElement<GetAdequateSolversResponse> createGetAdequateSolversResponse(GetAdequateSolversResponse value) {
        return new JAXBElement<GetAdequateSolversResponse>(_GetAdequateSolversResponse_QNAME, GetAdequateSolversResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAdequateSolvers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://metadata.web.tpsolver.htwg_konstanz.de/", name = "getAdequateSolvers")
    public JAXBElement<GetAdequateSolvers> createGetAdequateSolvers(GetAdequateSolvers value) {
        return new JAXBElement<GetAdequateSolvers>(_GetAdequateSolvers_QNAME, GetAdequateSolvers.class, null, value);
    }

}
