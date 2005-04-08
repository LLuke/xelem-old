/*
 * Created on 5-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.Iterator;

import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.SSRow;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class SSRowBuilder extends AnonymousBuilder {
    
    private Row currentRow;
    private int currentCellIndex;
    
    public void build(XMLReader reader, ContentHandler parent,
            Director director, XLElement xle) {
        setUpBuilder(reader, parent, director);
        currentRow = (SSRow) xle;
        currentCellIndex = 0;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (XLElement.XMLNS_SS.equals(uri)) {
            if ("Cell".equals(localName)) {
                String index = atts.getValue(XLElement.XMLNS_SS, "Index");
                if (index != null) {
                    currentCellIndex = Integer.parseInt(index);
                } else {
                    currentCellIndex++;
                }
                if (director.getBuildArea().isColumnPartOfArea(currentCellIndex)) {
	                Cell cell = currentRow.addCellAt(currentCellIndex);
	                cell.setAttributes(atts);
	                Builder builder = director.getSSCellBuilder();
	                builder.build(reader, this, director, cell);
                }
            }
        }
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentRow.getTagName().equals(localName)) {
            if (currentRow.getNameSpace().equals(uri)) {
                for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                    ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                    listener.setRow(director.getCurrentSheetName(),
                            director.getCurrentRowIndex(), currentRow);
                }
                reader.setContentHandler(parent);
                return;
            }
        }
        // no child elements?
    }

}
