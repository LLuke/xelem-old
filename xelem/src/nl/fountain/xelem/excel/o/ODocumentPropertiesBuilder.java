/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.excel.o;

import java.io.CharArrayWriter;

import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.lex.AbstractBuilder;
import nl.fountain.xelem.lex.BuilderFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class ODocumentPropertiesBuilder extends AbstractBuilder {
    
    private ODocumentProperties current;
    
    public ODocumentPropertiesBuilder() {
        contents = new CharArrayWriter();
    }

    public void build(XMLReader reader, ContentHandler parent, 
            BuilderFactory factory, XLElement xle) {
        build(reader, parent, factory);
        current = (ODocumentProperties) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        //System.out.println(qName);
        contents.reset();
    }
    
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (XLElement.XMLNS_O.equals(uri)) {	        
	        if ("DocumentProperties".equals(qName)) {
	            reader.setContentHandler(parent);
	            return;
	        }
	        invokeMethod(current, qName, contents.toString());
        }
    }
    
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        contents.write(ch, start, length);
    }

}
