/*
 * Created on 22-mrt-2005
 *
 */
package nl.fountain.xelem.excel.ss;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.lex.AnonymousBuilder;
import nl.fountain.xelem.lex.BuilderFactory;

/**
 *
 */
public class SSCellBuilder extends AnonymousBuilder {
    
    private SSCell current;
    
    public void build(XMLReader reader, ContentHandler parent,
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        current = (SSCell) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        contents.reset();
        if (XLElement.XMLNS_SS.equals(uri) && "Data".equals(localName)) {
            current.setXLDataType(atts.getValue(XLElement.XMLNS_SS, "Type"));
        }
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (current.getNameSpace().equals(uri)) {
            if (current.getTagName().equals(localName)) {
                reader.setContentHandler(parent);
                return;
            }
            current.setChildElement(localName, contents.toString());
        }
    }

}
