/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.XLElement;

import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;

/**
 *
 */
public interface Builder extends ContentHandler {
    
    void build(XMLReader reader, ContentHandler parent, 
            BuilderFactory factory, XLElement xle);

}
