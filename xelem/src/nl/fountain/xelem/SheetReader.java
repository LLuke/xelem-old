/*
 * Created on Dec 23, 2004
 *
 */
package nl.fountain.xelem;

import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.ss.SSWorksheet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;

/**
 *
 */
public class SheetReader extends DefaultHandler {
    
    private Worksheet sheet;
    
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
        System.out.println("start document");
        sheet = new SSWorksheet("naam");
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        System.out.println("==============================================");
        System.out.println("uri:" + uri);
        System.out.println("localName:" + localName);
        System.out.println("qName:" + qName);
        System.out.println(attributes.getValue(0));
    }
    
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String temp2;
        temp2 = new String(ch, start, length).trim();
        if (!temp2.equals("")) {
            System.out.println("temp2:" + temp2);
        }
    }

}
