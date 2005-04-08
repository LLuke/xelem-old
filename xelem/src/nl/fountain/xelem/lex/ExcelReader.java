/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.File;
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
import nl.fountain.xelem.excel.ss.XLWorkbook;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 */
public class ExcelReader {
    
    private SAXParser parser;
    XMLReader reader;
    
    Workbook currentWorkbook;
    private Director director;
    private Handler handler;
    private Map uris;
    
    public ExcelReader() throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        parser = spf.newSAXParser();
    }
    
    protected SAXParser getSaxParser() {
        return parser;
    }
    
    public void setBuildArea(Area area) {
        getDirector().setBuildArea(area);
    }
    
    public void clearBuildArea() {
        getDirector().setBuildArea(null);
    }
    
    public Area getBuildArea() {
        if (getDirector().hasBuildArea()) {
            return getDirector().getBuildArea();
        } else {
            return null;
        }
    }
    
    public boolean hasBuildArea() {
        return getDirector().hasBuildArea();
    }
    
    public List getListeners() {
        return getDirector().getListeners();
    }
    
    public void addExcelReaderListener(ExcelReaderListener l) {
        getDirector().addExcelReaderListener(l);
    }
    
    public boolean removeExcelReaderListener(ExcelReaderListener l) {
        return getDirector().removeExcelReaderListener(l);
    }
    
    public void clearExcelReaderListeners() {
        getDirector().clearExcelReaderListeners();
    }
    
    public void setListenOnly(boolean listen) {
        getDirector().setListenOnly(listen);
    }
    
    public boolean isListeningOnly() {
        return getDirector().isListeningOnly();
    }
    
    public Workbook read(String filename) throws IOException, SAXException {
        InputSource in = new InputSource(filename);
        return read(in);
    }
    
    public Workbook read(InputSource in) throws IOException, SAXException {
        currentWorkbook = null;
        getPrefixMap().clear();
        reader = parser.getXMLReader();
        
        reader.setContentHandler(getHandler());
        reader.setErrorHandler(getHandler());
        //reader.setDTDHandler(this);
        reader.parse(in);
        return currentWorkbook;
    }

    public Map getPrefixMap() {
        if (uris == null) {
            uris = new HashMap();
        }
        return uris;
    }
    
    protected Director getDirector() {
        if (director == null) {
            director = new Director();
        }
        return director;
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
        
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
            getPrefixMap().put(prefix, uri);
        }
        
        public void notationDecl(String name, String publicId, String systemId)
                throws SAXException {
            System.out.println("notation decl: " + "\n\tname=" + name
                    + "\n\tpublicId=" + publicId + "\n\tsystemId=" + systemId);
        }
        
        public void unparsedEntityDecl(String name, String publicId,
                String systemId, String notationName) throws SAXException {
            System.out.println("unparsedEntityDecl:"
                    + "\n\tname=" + name
                    + "\n\tpublicId=" + publicId
                    + "\n\tsystemId=" + systemId
                    + "\n\tnotationName=" + notationName);
        }
        
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            if (XLElement.XMLNS_SS.equals(uri) && "Workbook".equals(localName)) {
                String systemId = getSystemId();
                String wbName = getWorkbookName(systemId);
                Director bfac = getDirector();
                Builder builder = bfac.getXLWorkbookBuilder(); 
                
                if (!bfac.isListeningOnly()) {                   
	                currentWorkbook = new XLWorkbook(wbName);
	                currentWorkbook.setFileName(systemId);
                }
                for (Iterator iter = bfac.getListeners().iterator(); iter.hasNext();) {
                    ExcelReaderListener listener = (ExcelReaderListener) iter.next();
                    listener.startWorkbook(systemId, wbName);
                }
                
                builder.build(reader, this, bfac, currentWorkbook);               
            }       
        }
        
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            System.out.println(qName);
        }
        
        public void endPrefixMapping(String prefix) throws SAXException {
            //System.out.println(prefix);
        }
        
        public void startDocument() throws SAXException {        
            //System.out.println("doc started: ");
        }
        
        public void endDocument() throws SAXException {
            //System.out.println("doc ended: ");
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
        
        public void setDocumentLocator(Locator locator) {
            //System.out.println("recieved locator");
            this.locator = locator;
        }
        
        private String getSystemId() {
            String systemId = "source";
            if (locator != null) {
                String temp = locator.getSystemId();
                if (temp != null) {
                    systemId = temp;
                }
            }
            return systemId;
        }
        
        private String getWorkbookName(String systemId) {
            File file = new File(systemId);
            String[] s = file.getName().split("\\.");
            if (s.length > 0) {
                return s[0];
            } else {
                return "";
            }
        }
        

    }
    

}
