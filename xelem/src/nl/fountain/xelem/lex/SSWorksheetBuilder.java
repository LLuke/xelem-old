/*
 * Created on 17-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.WorksheetOptions;
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
    private Row row;

    public void build(XMLReader reader, ContentHandler parent,
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        current = (SSWorksheet) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (XLElement.XMLNS_SS.equals(uri)) {
	        buildSSElement(localName, atts);
        } else if (XLElement.XMLNS_X.equals(uri)) {
            if ("WorksheetOptions".equals(localName)) {
                WorksheetOptions wso = current.getWorksheetOptions();
                Builder builder = factory.getAnonymousBuilder();
                builder.build(reader, this, factory, wso);
            }
        }        
    }

    private void buildSSElement(String localName, Attributes atts) {
        if ("Cell".equals(localName)) {
            Cell cell;
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                cell = row.addCellAt(Integer.parseInt(index));
            } else {
                cell = row.addCell();
            }
            cell.setAttributes(atts);
            Builder builder = factory.getSSCellBuilder();
            builder.build(reader, this, factory, cell);
        } else if ("Row".equals(localName)) {
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                row = table.addRowAt(Integer.parseInt(index));
            } else {
                row = table.addRow();
            }
            row.setAttributes(atts);
        } else if ("Column".equals(localName)) {
            Column column;
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                column = table.addColumnAt(Integer.parseInt(index));
            } else {
                column = table.addColumn();
            }
            column.setAttributes(atts);
        } else if ("Table".equals(localName)) {
            table = current.getTable();
            table.setAttributes(atts);
        } else if ("NamedRange". equals(localName)) {
            NamedRange nr = current.addNamedRange(
                    atts.getValue(XLElement.XMLNS_SS, "Name"), 
                    null);
            nr.setAttributes(atts);
        } 
    }
    

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (current.getNameSpace().equals(uri)) {
            if (current.getTagName().equals(localName)) {
                reader.setContentHandler(parent);
                return;
            }
        }
    }

}