/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.XLWorkbook;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class XLWorkbookBuilder extends AnonymousBuilder {
    
    private XLWorkbook current;
    
    public void build(XMLReader reader, ContentHandler parent, 
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        current = (XLWorkbook) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (XLElement.XMLNS_O.equals(uri) && "DocumentProperties".equals(localName)) {
            Builder builder = factory.getAnonymousBuilder();
            builder.build(reader, this, factory, current.getDocumentProperties());
        } else if (XLElement.XMLNS_X.equals(uri) && "ExcelWorkbook".equals(localName)) {
            Builder builder = factory.getAnonymousBuilder();
            builder.build(reader, this, factory, current.getExcelWorkbook());
        // Styles
        // NamedRange
        } else if (XLElement.XMLNS_SS.equals(uri) && "NamedRange". equals(localName)) {
            NamedRange nr = current.addNamedRange(
                    atts.getValue(XLElement.XMLNS_SS, "Name"), 
                    null);
            nr.setAttributes(atts);
        // Worksheet
        } else if (XLElement.XMLNS_SS.equals(uri) && "Worksheet". equals(localName)) {
            Builder builder = factory.getSSWorksheetBuilder();
            Worksheet sheet = current.addSheet(
                    atts.getValue(XLElement.XMLNS_SS, "Name"));
            sheet.setAttributes(atts);
            builder.build(reader, this, factory, sheet);
        }
    }
    
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (current.getNameSpace().equals(uri)) {
            if (current.getTagName().equals(localName)) {
                reader.setContentHandler(parent);
                return;
            }
        }
        // no child elements
    }
    

}
