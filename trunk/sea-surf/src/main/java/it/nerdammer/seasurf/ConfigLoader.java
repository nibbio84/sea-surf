package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.ObjectFactory;
import it.nerdammer.seasurf.config.SeaSurfConfig;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

class ConfigLoader {
	
	protected String configFileName = "META-INF/sea-surf-config.xml";
	protected String xmlSchemaFileName = "META-INF/sea-surf-config_1_0.xsd";
	
	protected Logger logger = Logger.getLogger(getClass());
	
	public ConfigLoader() {
		
	}
	
	public SeaSurfConfig loadConfig() {
		InputStream in = null;
		InputStream schemaIn = null;
		try {
			in = getClass().getClassLoader().getResourceAsStream(getConfigFileName());
			if(in==null) {
				throw new IllegalStateException("Configuraiton file not found: " + getConfigFileName());
			}
			
			schemaIn = getClass().getClassLoader().getResourceAsStream(getXmlSchemaFileName());
			if(schemaIn==null) {
				throw new IllegalStateException("Schema file not found: " + getXmlSchemaFileName());
			}
			
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			Document doc = builder.parse(in);
			
			SchemaFactory schemaFact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Source schemaSource = new StreamSource(schemaIn);
			Schema schema = schemaFact.newSchema(schemaSource);
			
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(doc));
			
			JAXBContext ctx = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
			Unmarshaller unmarsaller = ctx.createUnmarshaller();
			@SuppressWarnings("unchecked")
			JAXBElement<SeaSurfConfig> jaxbElement = (JAXBElement<SeaSurfConfig>) unmarsaller.unmarshal(doc);
			SeaSurfConfig config = jaxbElement.getValue();
			return config;
			
		} catch(RuntimeException ex) {
			logger.error("Error in sea-surf configuration");
			throw ex;
		} catch(SAXException sax) {
			logger.error("Error in sea-surf configuration");
			throw new IllegalStateException("Seasurf configuration does not respect its XML schema", sax);
		} catch(IOException io) {
			logger.error("Error in sea-surf configuration");
			throw new IllegalStateException("Unable to read " + getConfigFileName(), io);
		} catch(ParserConfigurationException pa) {
			logger.error("Error in sea-surf configuration");
			throw new IllegalStateException("Invalid parser configuration", pa);
		} catch(JAXBException ja) {
			logger.error("Error in sea-surf configuration");
			throw new IllegalStateException("Invalid jaxb configuration", ja);
		} finally {
			if(in!=null) {
				try {in.close();} catch(Throwable e) {}
			}
			if(schemaIn!=null) {
				try {schemaIn.close();} catch(Throwable e) {}
			}
		}
	}
	
	
	public String getConfigFileName() {
		return configFileName;
	}
	
	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}
	
	public String getXmlSchemaFileName() {
		return xmlSchemaFileName;
	}
	
	public void setXmlSchemaFileName(String xmlSchemaFileName) {
		this.xmlSchemaFileName = xmlSchemaFileName;
	}
	
}
