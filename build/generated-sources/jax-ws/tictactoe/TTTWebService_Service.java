
package tictactoe;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "TTTWebService", targetNamespace = "http://server.james.ttt/", wsdlLocation = "http://localhost:8888/TTTWebApplication/TTTWebService?WSDL")
public class TTTWebService_Service
    extends Service
{

    private final static URL TTTWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException TTTWEBSERVICE_EXCEPTION;
    private final static QName TTTWEBSERVICE_QNAME = new QName("http://server.james.ttt/", "TTTWebService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8888/TTTWebApplication/TTTWebService?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TTTWEBSERVICE_WSDL_LOCATION = url;
        TTTWEBSERVICE_EXCEPTION = e;
    }

    public TTTWebService_Service() {
        super(__getWsdlLocation(), TTTWEBSERVICE_QNAME);
    }

    public TTTWebService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), TTTWEBSERVICE_QNAME, features);
    }

    public TTTWebService_Service(URL wsdlLocation) {
        super(wsdlLocation, TTTWEBSERVICE_QNAME);
    }

    public TTTWebService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TTTWEBSERVICE_QNAME, features);
    }

    public TTTWebService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TTTWebService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns TTTWebService
     */
    @WebEndpoint(name = "TTTWebServicePort")
    public TTTWebService getTTTWebServicePort() {
        return super.getPort(new QName("http://server.james.ttt/", "TTTWebServicePort"), TTTWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TTTWebService
     */
    @WebEndpoint(name = "TTTWebServicePort")
    public TTTWebService getTTTWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://server.james.ttt/", "TTTWebServicePort"), TTTWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TTTWEBSERVICE_EXCEPTION!= null) {
            throw TTTWEBSERVICE_EXCEPTION;
        }
        return TTTWEBSERVICE_WSDL_LOCATION;
    }

}
