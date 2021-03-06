//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.10 at 10:31:10 PM CET 
//


package it.nerdammer.seasurf.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for security-token-constraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="security-token-constraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="include" type="{http://www.nerdammer.it/sea-surf-config_1_0}request-type-collection"/>
 *         &lt;element name="exclude" type="{http://www.nerdammer.it/sea-surf-config_1_0}request-type-collection" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "security-token-constraint", propOrder = {

})
public class SecurityTokenConstraint {

    @XmlElement(required = true)
    protected RequestTypeCollection include;
    protected RequestTypeCollection exclude;

    /**
     * Gets the value of the include property.
     * 
     * @return
     *     possible object is
     *     {@link RequestTypeCollection }
     *     
     */
    public RequestTypeCollection getInclude() {
        return include;
    }

    /**
     * Sets the value of the include property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestTypeCollection }
     *     
     */
    public void setInclude(RequestTypeCollection value) {
        this.include = value;
    }

    /**
     * Gets the value of the exclude property.
     * 
     * @return
     *     possible object is
     *     {@link RequestTypeCollection }
     *     
     */
    public RequestTypeCollection getExclude() {
        return exclude;
    }

    /**
     * Sets the value of the exclude property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestTypeCollection }
     *     
     */
    public void setExclude(RequestTypeCollection value) {
        this.exclude = value;
    }

}
