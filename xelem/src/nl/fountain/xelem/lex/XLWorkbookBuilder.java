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
        
    public void build(XMLReader reader, ContentHandler parent, 
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        currentWB = (XLWorkbook) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        // DocumentProperties
        if (XLElement.XMLNS_O.equals(uri) && "DocumentProperties".equals(localName)) {            
            DocumentProperties docprops;
            if (factory.isListeningOnly()) {
                docprops = new ODocumentProperties();
            } else {
                docprops = currentWB.getDocumentProperties();
            }
            Builder builder = factory.getAnonymousBuilder();
            builder.build(reader, this, factory, docprops);
        
        // ExcelWorkbook
        } else if (XLElement.XMLNS_X.equals(uri) && "ExcelWorkbook".equals(localName)) {
            ExcelWorkbook xlwb;
            if (factory.isListeningOnly()) {
                xlwb = new XExcelWorkbook();
            } else {
                xlwb = currentWB.getExcelWorkbook();
            }
            Builder builder = factory.getAnonymousBuilder();
            builder.build(reader, this, factory, xlwb);
                       
        // Styles
        // NamedRange
        } else if (XLElement.XMLNS_SS.equals(uri) && "NamedRange". equals(localName)) {
            NamedRange nr;
            if (factory.isListeningOnly()) {
                nr = new SSNamedRange(atts.getValue(XLElement.XMLNS_SS, "Name"), null);
            } else {
                nr = currentWB.addNamedRange(
                    atts.getValue(XLElement.XMLNS_SS, "Name"), 
                    null);
            }
            nr.setAttributes(atts);
            for (Iterator iter = factory.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.setNamedRange(currentWB, nr);
            }
            
        // Worksheet
        } else if (XLElement.XMLNS_SS.equals(uri) && "Worksheet". equals(localName)) {
            Builder builder = factory.getSSWorksheetBuilder();
            Worksheet sheet = currentWB.addSheet(
                    atts.getValue(XLElement.XMLNS_SS, "Name"));
            sheet.setAttributes(atts);
            builder.build(reader, this, factory, sheet);
        }
    }
    
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentWB.getNameSpace().equals(uri)) {
            if (currentWB.getTagName().equals(localName)) {
                reader.setContentHandler(parent);
                return;
            }
        }
        // no child elements
    }
    

}
