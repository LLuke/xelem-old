/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.excel.ss;

import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.lex.AbstractBuilder;
import nl.fountain.xelem.lex.Builder;
import nl.fountain.xelem.lex.BuilderFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class XLWorkbookBuilder extends AbstractBuilder {
    
    private XLWorkbook currentWorkbook;
    
    public void build(XMLReader reader, ContentHandler parent, 
            BuilderFactory factory, XLElement xle) {
        build(reader, parent, factory);
        currentWorkbook = (XLWorkbook) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (XLElement.XMLNS_O.equals(uri) && "DocumentProperties".equals(qName)) {
            Builder builder = factory.getODocumentPropertiesBuilder();
            builder.build(reader, this, factory, currentWorkbook.getDocumentProperties());
        }
    }
    
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (XLElement.XMLNS_SS.equals(uri)) {	        
	        if ("Workbook".equals(qName)) {
	            reader.setContentHandler(parent);
	            return;
	        }
        }
    }
    

}
