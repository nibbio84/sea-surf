//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.10 at 10:31:10 PM CET 
//


package it.nerdammer.seasurf.config;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.nerdammer.seasurf.config package. 
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

    private final static QName _SeaSurfConfig_QNAME = new QName("http://www.nerdammer.it/sea-surf-config_1_0", "sea-surf-config");
    private final static QName _RequestOriginPage_QNAME = new QName("http://www.nerdammer.it/sea-surf-config_1_0", "page");
    private final static QName _RequestOriginDomain_QNAME = new QName("http://www.nerdammer.it/sea-surf-config_1_0", "domain");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.nerdammer.seasurf.config
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RefererConstraint }
     * 
     */
    public RefererConstraint createRefererConstraint() {
        return new RefererConstraint();
    }

    /**
     * Create an instance of {@link Preferences }
     * 
     */
    public Preferences createPreferences() {
        return new Preferences();
    }

    /**
     * Create an instance of {@link RefererConstraints }
     * 
     */
    public RefererConstraints createRefererConstraints() {
        return new RefererConstraints();
    }

    /**
     * Create an instance of {@link SecurityTokenConstraints }
     * 
     */
    public SecurityTokenConstraints createSecurityTokenConstraints() {
        return new SecurityTokenConstraints();
    }

    /**
     * Create an instance of {@link RequestOrigin }
     * 
     */
    public RequestOrigin createRequestOrigin() {
        return new RequestOrigin();
    }

    /**
     * Create an instance of {@link SecurityTokenConstraint }
     * 
     */
    public SecurityTokenConstraint createSecurityTokenConstraint() {
        return new SecurityTokenConstraint();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link Methods }
     * 
     */
    public Methods createMethods() {
        return new Methods();
    }

    /**
     * Create an instance of {@link SeaSurfConfig }
     * 
     */
    public SeaSurfConfig createSeaSurfConfig() {
        return new SeaSurfConfig();
    }

    /**
     * Create an instance of {@link RequestTypeCollection }
     * 
     */
    public RequestTypeCollection createRequestTypeCollection() {
        return new RequestTypeCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SeaSurfConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.nerdammer.it/sea-surf-config_1_0", name = "sea-surf-config")
    public JAXBElement<SeaSurfConfig> createSeaSurfConfig(SeaSurfConfig value) {
        return new JAXBElement<SeaSurfConfig>(_SeaSurfConfig_QNAME, SeaSurfConfig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.nerdammer.it/sea-surf-config_1_0", name = "page", scope = RequestOrigin.class)
    public JAXBElement<String> createRequestOriginPage(String value) {
        return new JAXBElement<String>(_RequestOriginPage_QNAME, String.class, RequestOrigin.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.nerdammer.it/sea-surf-config_1_0", name = "domain", scope = RequestOrigin.class)
    public JAXBElement<String> createRequestOriginDomain(String value) {
        return new JAXBElement<String>(_RequestOriginDomain_QNAME, String.class, RequestOrigin.class, value);
    }

}
