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
 * <P>
 * An ExcelReader
 * can deliver the contents of an xml-file or an xml-InputSource as a
 * fully populated {@link nl.fountain.xelem.excel.Workbook}.
 * <P>
 * Furthermore, it can dispatch events, values and instances of 
 * {@link nl.fountain.xelem.excel.XLElement XLElements} to listeners
 * registered at this ExcelReader while parsing an xml-file or
 * xml-InputSource.
 * 
 * @see <a href=package-summary.html#package_description">package overview</a>
 * 
 * @since xelem.2.0
 */
public class ExcelReader {
    
    private SAXParser parser;
    XMLReader reader;
    
    Director director;
    private Handler handler;
    private Map uris;
    
    /**
     * Constructs a new ExcelReader.
     * Obtains a {@link javax.xml.parsers.SAXParser} from an instance of
     * {@link javax.xml.parsers.SAXParserFactory} to do the parsing.
     * There is no need to configure factory parameters.
     * 
     * @throws ParserConfigurationException if a parser cannot be created
     * 		 which satisfies the current configuration
     * @throws SAXException	for SAX errors
     */
    public ExcelReader() throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        parser = spf.newSAXParser();
        director = new Director();
    }
    
    /**
     * Returns the SAXParser that is used by this ExcelReader. (Just to see
     * what we've got under the hood.)
     * <P>
     * Under Java 1.4 we might see a org.apache.crimson.jaxp.SAXParserImpl,
     * it could be a com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl
     * under Java 1.5. 
     * 
     * @return	the SAXParser that is used by this ExcelReader
     */
    public SAXParser getSaxParser() {
        return parser;
    }
    
    /**
     * Sets the area that is to be read. 
     * Reading will be restricted to the specified area until another
     * area is set or until the read area has been cleared.
     * 
     * @param area the area to read
     */
    public void setReadArea(Area area) {
        director.setBuildArea(area);
    }
    
    /**
     * Clears the read area. Reading will not be rerstricted after a call
     * to this method.
     *
     */
    public void clearReadArea() {
        director.setBuildArea(null);
    }
    
    /**
     * Gets the area that restricts reading on this ExcelReader. May be null if
     * no read area was set or the read area was cleared.
     * 
     * @return  the read area
     */
    public Area getReadArea() {
        if (director.hasBuildArea()) {
            return director.getBuildArea();
        } else {
            return null;
        }
    }
    
    /**
     * Specifies whether reading is restricted on this ExcelReader.
     * 
     */
    public boolean hasReadArea() {
        return director.hasBuildArea();
    }
    
    /**
     * Gets a list of registered listeners on this ExcelReader.
     * 
     * @return a list of registered listeners
     */
    public List getListeners() {
        return director.getListeners();
    }
    
    /**
     * Registers the given listener on this ExcelReader.
     * 
     * @param listener the ExcelReaderListener to be registered
     */
    public void addExcelReaderListener(ExcelReaderListener listener) {
        director.addExcelReaderListener(listener);
    }
    
    /**
     * Removes the passed listener on this ExcelReader.
     * 
     * @param listener the ExcelReaderListener to be removed
     * @return <code>true</code> if the passed listener was registered,
     * 		<code>false</code> otherwise
     */
    public boolean removeExcelReaderListener(ExcelReaderListener listener) {
        return director.removeExcelReaderListener(listener);
    }
    
    /**
     * Remove all listeners on this ExcelReader
     *
     */
    public void clearExcelReaderListeners() {
        director.clearExcelReaderListeners();
    }
    
    /**
     * Delivers the contents of the specified file as a fully populated Workbook.
     * If a read area was set on this ExcelReader the workbook and its worksheets
     * are only populated in the specified area. If listeners are registered
     * on this ExcelReader dispatches events to these listeners during the read.
     * Performs a read.
     * 
     * @param fileName 		the name of the file to be read
     * @return				a fully populated Workbook
     * @throws IOException 	signals a failed or interrupted I/O operation
     * @throws SAXException	signals a general SAX error or warning
     */
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
