/*
 * Created on 16-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.XLElement;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class DefaultBuilder extends AbstractBuilder {
    
    private XLElement current;

    public void build(XMLReader reader, ContentHandler parent,
            BuilderFactory factory, XLElement xle) {
        build(reader, parent, factory);
        current = xle;
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
    

}
