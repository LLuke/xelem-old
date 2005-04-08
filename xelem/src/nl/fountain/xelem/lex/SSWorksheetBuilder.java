/*
 * Created on 17-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.Iterator;

import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.WorksheetOptions;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.SSColumn;
import nl.fountain.xelem.excel.ss.SSNamedRange;
import nl.fountain.xelem.excel.ss.SSTable;
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
            Director director, XLElement xle) {
        setUpBuilder(reader, parent, director);
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
                Builder builder = director.getAnonymousBuilder();
                builder.build(reader, this, director, wso);
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
            if (director.getBuildArea().isRowPartOfArea(currentRowIndex)) {
	            currentRow = currentTable.addRowAt(currentRowIndex);
	            currentRow.setAttributes(atts);
	            director.setCurrentRowInfo(currentSheet.getName(), currentRowIndex);
	            Builder builder = director.getSSRowBuilder();
	            builder.build(reader, this, director, currentRow);
            }
        } else if ("Column".equals(localName)) {
            String index = atts.getValue(XLElement.XMLNS_SS, "Index");
            if (index != null) {
                currentColumnIndex = Integer.parseInt(index);
            } else {
                currentColumnIndex++;
            }
            if (director.getBuildArea().isColumnPartOfArea(currentColumnIndex)) {
                Column column;
                if (director.isListeningOnly()) {
                    column = new SSColumn();
                } else {
                    column = currentTable.addColumnAt(currentColumnIndex);
                }
	            column.setAttributes(atts);
	            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
	                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
	                listener.setColumn(currentSheet.getName(), currentColumnIndex,
	                        column);
	            }
            }
        } else if ("Table".equals(localName)) {
            if (director.isListeningOnly()) {
                currentTable = new SSTable();
            } else {
                currentTable = currentSheet.getTable();
            }
            currentTable.setAttributes(atts);
            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.startTable(currentSheet.getName(), 
                        currentTable.getExpandedRowCount(), 
                        currentTable.getExpandedColumnCount());
            }
        } else if ("NamedRange". equals(localName)) {
            NamedRange nr;
            String name = atts.getValue(XLElement.XMLNS_SS, "Name");
            if (director.isListeningOnly()) {
                nr = new SSNamedRange(name, null);
            } else {
                nr = currentSheet.addNamedRange(name, null);
            }
            nr.setAttributes(atts);
            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.setNamedRange(currentSheet.getName(), nr);
            }
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