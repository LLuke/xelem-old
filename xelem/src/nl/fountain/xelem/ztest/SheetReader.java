/*
 * Created on Dec 23, 2004
 *
 */
package nl.fountain.xelem.ztest;

import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import nl.fountain.xelem.excel.Worksheet;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;

/**
 *
 */
public class SheetReader extends DefaultHandler {
    
    private Worksheet sheet;
    private String valueString = "";
    private Locator locator;
    
    public SheetReader(String fileName) throws IOException {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            ParserAdapter pa = new ParserAdapter(sp.getParser());
            pa.setContentHandler(this);
            pa.parse(fileName);
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
    
    public void startDocument() throws SAXException {
        locate();
        System.out.println("start document");
        //sheet = new SSWorksheet("naam");
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (!"".equals(valueString)) System.out.println("text=" + valueString);
        valueString = "";
        System.out.println("==============================================");
        locate();
        System.out.println("uri=" + uri);
        System.out.println("localName=" + localName);
        System.out.println("qName=" + qName);
        if (attributes.getLength() > 0) {
            System.out.println("-------------- attributes:" + attributes.getLength());
        
	        for (int i = 0; i < attributes.getLength(); i++) {
	            System.out.println("\t" + "uri=" + attributes.getURI(i));
	            System.out.println("\t" + "localName=" + attributes.getLocalName(i));
	            System.out.println("\t" + "qName=" + attributes.getQName(i));
	            System.out.println("\t" + "value=" + attributes.getValue(i));
	            System.out.println("\t" + "type=" + attributes.getType(i));
	        }
	        System.out.println("-------------- /attributes");
        }
    }
    
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String temp2;
        temp2 = new String(ch, start, length).trim();
        if (!temp2.equals("")) {
            //System.out.println("temp2=" + temp2);
            valueString += temp2;
        }
    }
    
//    public void endElement(String uri, String localName, String qName)
//            throws SAXException {
//        locate();
//        System.out.println("/////////////////" + qName);
//    }
    
    // @see org.xml.sax.helpers.DefaultHandler#processingInstruction(java.lang.String, java.lang.String)
    public void processingInstruction(String target, String data)
            throws SAXException {
        locate();
        System.out.println("==processing instruction==");
        System.out.println("target=" + target);
        System.out.println("data=" + data);
        System.out.println("[/processing instruction]");
    }
    
    // @see org.xml.sax.helpers.DefaultHandler#startPrefixMapping(java.lang.String, java.lang.String)
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        locate();
        System.out.println("==prefix mapping==");
        System.out.println("prefix=" + prefix);
        System.out.println("uri=" + uri);
        System.out.println("[/prefix mapping]");
    }
    
    // @see org.xml.sax.helpers.DefaultHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    public void unparsedEntityDecl(String name, String publicId,
            String systemId, String notationName) throws SAXException {
        locate();
        System.out.println("==unparsed entity declaration==");
        System.out.println("name=" + name);
        System.out.println("publicId=" + publicId);
        System.out.println("systemId=" + systemId);
        System.out.println("notationName=" + notationName);
        System.out.println("[/unparsed entity declaration]");
    }
    
    // @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator)
    public void setDocumentLocator(Locator locator) {
        System.out.println(locator);
        this.locator = locator;
    }
    
    private void locate() {
        System.out.println("[" + locator.getSystemId() + "] [" 
                + locator.getPublicId() + "] [ln="
                + locator.getLineNumber() + "] [cn="
                + locator.getColumnNumber() + "]");
    }

}
