/*
 * Created on 17-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

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

    private SSWorksheet currentSheet;
    private Table currentTable;
    private Row currentRow;
    private int currentRowIndex;
    private int currentColumnIndex;

    public void build(XMLReader reader, ContentHandler parent,
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        currentSheet = (SSWorksheet) xle;
        currentRowIndex = 0;
        currentColumnIndex = 0;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (XLElement.XMLNS_SS.equals(uri)) {
	        startSSElement(localName, atts);
        } else if (XLElement.XMLNS_X.equals(uri)) {
            if ("WorksheetOptions".equals(localName)) {
                WorksheetOptions wso = currentSheet.getWorksheetOptions();
                Builder builder = factory.getAnonymousBuilder();
                builder.build(reader, this, factory, wso);
            } else if ("AutoFilter".equals(localName)) {
                currentSheet.setAutoFilter(atts.getValue(XLElement.XMLNS_X, "Range"));
            }
        }        
    }

    private void startSSElement(String localName, Attributes atts) {
        if ("Row".equals(localName)) {
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                currentRowIndex = Integer.parseInt(index);
            } else {
                currentRowIndex++;
            }
            if (factory.getBuildArea().isRowPartOfArea(currentRowIndex)) {
	            currentRow = currentTable.addRowAt(currentRowIndex);
	            currentRow.setAttributes(atts);
	            Builder builder = factory.getSSRowBuilder();
	            builder.build(reader, this, factory, currentRow);
            }
        } else if ("Column".equals(localName)) {
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                currentColumnIndex = Integer.parseInt(index);
            } else {
                currentColumnIndex++;
            }
            if (factory.getBuildArea().isColumnPartOfArea(currentColumnIndex)) {
	            Column column = currentTable.addColumnAt(currentColumnIndex);
	            column.setAttributes(atts);
            }
        } else if ("Table".equals(localName)) {
            currentTable = currentSheet.getTable();
            currentTable.setAttributes(atts);
        } else if ("NamedRange". equals(localName)) {
            NamedRange nr = currentSheet.addNamedRange(
                    atts.getValue(XLElement.XMLNS_SS, "Name"), 
                    null);
            nr.setAttributes(atts);
        } 
    }
    

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentSheet.getTagName().equals(localName)) {
            if (currentSheet.getNameSpace().equals(uri)) {
                reader.setContentHandler(parent);
                return;
            }
        }
        // no child elements?
    }

}