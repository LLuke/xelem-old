/*
 * Created on 17-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
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
    private Table table;

    public void build(XMLReader reader, ContentHandler parent,
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        current = (SSWorksheet) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (XLElement.XMLNS_SS.equals(uri) && "Table".equals(localName)) {
            table = current.getTable();
            table.setAttributes(atts);
        } else if (XLElement.XMLNS_SS.equals(uri) && "Column".equals(localName)) {
            Column column;
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                column = table.addColumnAt(Integer.parseInt(index));
            } else {
                column = table.addColumn();
            }
            column.setAttributes(atts);
        } else if (XLElement.XMLNS_SS.equals(uri) && "Row".equals(localName)) {
            Row row;
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                row = table.addRowAt(Integer.parseInt(index));
            } else {
                row = table.addRow();
            }
            row.setAttributes(atts);
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