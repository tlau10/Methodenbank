
package de.htwg_konstanz.tpsolver.web.solver;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SolverService", targetNamespace = "http://solver.web.tpsolver.htwg_konstanz.de/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SolverService {


    /**
     * 
     * @param inputXml
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "resultXml", partName = "resultXml")
    public String processXml(
        @WebParam(name = "inputXml", partName = "inputXml")
        String inputXml);

}
