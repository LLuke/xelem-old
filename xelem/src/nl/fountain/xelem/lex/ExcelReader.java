/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
    private XMLReader reader;
    
    private Workbook currentWorkbook;
    private BuilderFactory factory;
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
    
    public Workbook read(String filename) throws IOException, SAXException {
        InputSource in = new InputSource(filename);
        return read(in);
    }
    
    
    public Workbook read(InputSource in) throws IOException, SAXException {
        currentWorkbook = null;
        getUris().clear();
        reader = parser.getXMLReader();
        
        reader.setContentHandler(getHandler());
        reader.setErrorHandler(getHandler());
        //reader.setDTDHandler(this);
        reader.parse(in);
        return currentWorkbook;
    }
    
    public Map getUris() {
        if (uris == null) {
            uris = new HashMap();
        }
        return uris;
    }
    
    protected BuilderFactory getFactory() {
        if (factory == null) {
            factory = new BuilderFactory();
        }
        return factory;
    }
    
    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }
    
    protected String getWorkbookName(String systemId) {
        File file = new File(systemId);
        String[] s = file.getName().split("\\.");
        if (s.length > 0) {
            return s[0];
        } else {
            return "";
        }
    }
      
    
    //////////////////////////////////////////////////////////////////////////////
    
    private class Handler extends DefaultHandler {
        
        private Locator locator;
        
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
//            System.out.println("prefix mapping: "
//                    + "\n\tprefix=" + prefix
//                    + "\n\turi=" + uri);
            getUris().put(prefix, uri);
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
//            System.out.println("startElement: "
//                    + "\n\turi=" + uri
//                    + "\n\tlcalName=" + localName
//                    + "\n\tqName=" + qName);
            if (XLElement.XMLNS_SS.equals(uri) && "Workbook".equals(localName)) {
                String systemId = getSystemId();
                currentWorkbook = new XLWorkbook(getWorkbookName(systemId));
                currentWorkbook.setFileName(systemId);
                Builder builder = getFactory().getXLWorkbookBuilder();
                builder.build(reader, this, getFactory(), currentWorkbook);
            }       
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
        

        

    }
    

}
