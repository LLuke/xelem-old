/*
 * Created on 17-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.SSWorksheet;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *  
 */
public class SSWorksheetBuilder extends AnonymousBuilder {

    private SSWorksheet current;

    public void build(XMLReader reader, ContentHandler parent,
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        current = (SSWorksheet) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (XLElement.XMLNS_SS.equals(uri) && "Table".equals(localName)) {
            
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (current.getNameSpace().equals(uri)) {
            if (current.getTagName().equals(qName)) {
                reader.setContentHandler(parent);
                return;
            }
        }
    }

}