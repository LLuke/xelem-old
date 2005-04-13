/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import nl.fountain.xelem.Area;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.XLElement;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Basic class for reading xml-spreadsheets of type spreadsheetML.
 */
public class ExcelReader {
    
    private SAXParser parser;
    XMLReader reader;
    
    Director director;
    private Handler handler;
    private Map uris;
    
    public ExcelReader() throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        parser = spf.newSAXParser();
        director = new Director();
    }
    
    SAXParser getSaxParser() {
        return parser;
    }
    
    public void setReadArea(Area area) {
        director.setBuildArea(area);
    }
    
    public void clearReadArea() {
        director.setBuildArea(null);
    }
    
    public Area getReadArea() {
        if (director.hasBuildArea()) {
            return director.getBuildArea();
        } else {
            return null;
        }
    }
    
    public boolean hasReadArea() {
        return director.hasBuildArea();
    }
    
    public List getListeners() {
        return director.getListeners();
    }
    
    public void addExcelReaderListener(ExcelReaderListener l) {
        director.addExcelReaderListener(l);
    }
    
    public boolean removeExcelReaderListener(ExcelReaderListener l) {
        return director.removeExcelReaderListener(l);
    }
    
    public void clearExcelReaderListeners() {
        director.clearExcelReaderListeners();
    }
    
    public Workbook getWorkbook(String fileName) throws IOException, SAXException {
        InputSource in = new InputSource(fileName);
        return getWorkbook(in);
    }
    
    public Workbook getWorkbook(InputSource in) throws IOException, SAXException {
        WorkbookListener wbl = new WorkbookListener();
        addExcelReaderListener(wbl);
        read(in);
        removeExcelReaderListener(wbl);
        return wbl.getWorkbook();
    }
    
    public void read(String fileName) throws IOException, SAXException {
        InputSource in = new InputSource(fileName);
        read(in);
    }
    
    public void read(InputSource in) throws IOException, SAXException {
        getPrefixMap().clear();
        reader = parser.getXMLReader();
        
        reader.setContentHandler(getHandler());
        reader.setErrorHandler(getHandler());
        //reader.setDTDHandler(this);
        reader.parse(in);
    }

    public Map getPrefixMap() {
        if (uris == null) {
            uris = new HashMap();
        }
        return uris;
    }
    
    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }
      
    
    //////////////////////////////////////////////////////////////////////////////
    
    private class Handler extends DefaultHandler {
        
        private Locator locator;
        
        public void setDocumentLocator(Locator locator) {
            this.locator = locator;
        }
        
        public void processingInstruction(String target, String data) throws SAXException {
            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.processingInstruction(target, data);
            }
        }
        
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
            getPrefixMap().put(prefix, uri);
        }
        
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            if (XLElement.XMLNS_SS.equals(uri) && "Workbook".equals(localName)) {
                String systemId = getSystemId();
                Builder builder = director.getXLWorkbookBuilder(); 
                for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                    ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                    listener.startWorkbook(systemId);
                }               
                builder.build(reader, this);               
            }       
        }
        
        public void startDocument() throws SAXException {        
            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.startDocument();
            }
        }
        
        public void endDocument() throws SAXException {
            for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
                ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                listener.endDocument(getPrefixMap());
            }
        }
        
        public void fatalError(SAXParseException e) throws SAXException {
            //System.out.println("fatal error detected: " + e.getMessage());
            throw e;
        }
        
        public void error(SAXParseException e) throws SAXException {
            System.out.println("error detected: " + e.getMessage());
        }
        
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("warning detected: " + e.getMessage());
        }
        
        
        
        private String getSystemId() {
            String systemId = null;
            if (locator != null) {
                systemId = locator.getSystemId();
            }
            if (systemId == null) {
                systemId = "source";
            }
            return systemId;
        }
        

        

    }
    

}
