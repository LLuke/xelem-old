/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.Iterator;

import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.o.ODocumentProperties;
import nl.fountain.xelem.excel.ss.SSNamedRange;
import nl.fountain.xelem.excel.ss.SSWorksheet;
import nl.fountain.xelem.excel.ss.XLWorkbook;
import nl.fountain.xelem.excel.x.XExcelWorkbook;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class XLWorkbookBuilder extends AnonymousBuilder {
    
    private XLWorkbook currentWB;
    private int sheetCounter;
        
    public void build(XMLReader reader, ContentHandler parent, 
            Director director, XLElement xle) {
        setUpBuilder(reader, parent, director);
        currentWB = (XLWorkbook) xle;
        sheetCounter = 0;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        
        // DocumentProperties
        if (XLElement.XMLNS_O.equals(uri) && "DocumentProperties".equals(localName)) {            
            DocumentProperties docprops;
            if (director.isListeningOnly()) {
                docprops = new ODocumentProperties();
            } else {
                docprops = currentWB.getDocumentProperties();
            }
            Builder builder = director.getAnonymousBuilder();
            builder.build(reader, this, director, docprops);
        
        // ExcelWorkbook
        } else if (XLElement.XMLNS_X.equals(uri) && "ExcelWorkbook".equals(localName)) {
            ExcelWorkbook xlwb;
            if (director.isListeningOnly()) {
                xlwb = new XExcelWorkbook();
            } else {
                xlwb = currentWB.getExcelWorkbook();
            }
            Builder builder = director.getAnonymousBuilder();
            builder.build(reader, this, director, xlwb);
                       
        // Styles
        // NamedRange
        } else if (XLElement.XMLNS_SS.equals(uri) && "NamedRange". equals(localName)) {
            NamedRange nr;
            if (director.isListeningOnly()) {
                nr = new SSNamedRange(atts.getValue(XLElement.XMLNS_SS, "Name"), null);
            } else {
                nr = currentWB.addNamedRange(
                    atts.getValue(XLElement.XMLNS_SS, "Name"), 
                    null);
            }
            nr.setAttributes(atts);
            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.setNamedRange(nr);
            }
            
        // Worksheet
        } else if (XLElement.XMLNS_SS.equals(uri) && "Worksheet". equals(localName)) {
            Worksheet sheet;
            String sheetName = atts.getValue(XLElement.XMLNS_SS, "Name");
            if (director.isListeningOnly()) {
                sheet = new SSWorksheet(sheetName);
            } else {
                sheet = currentWB.addSheet(sheetName);
            }
            sheet.setAttributes(atts);
            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.startWorksheet(sheetCounter, sheetName);
            }
            
            sheetCounter++;
            Builder builder = director.getSSWorksheetBuilder();
            builder.build(reader, this, director, sheet);
        }
    }
    
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (XLElement.XMLNS_SS.equals(uri)) {
            if ("Workbook".equals(localName)) {
                reader.setContentHandler(parent);
                return;
            }
        }
        // no child elements
    }
    

}
