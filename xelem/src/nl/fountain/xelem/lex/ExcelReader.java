/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.IOException;

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
public class ExcelReader extends DefaultHandler {
    
    private SAXParser parser;
    private XMLReader reader;
    private Locator locator;
    private Workbook currentWorkbook;
    private BuilderFactory factory;
    
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
        reader = parser.getXMLReader();
        
        reader.setContentHandler(this);
        reader.setErrorHandler(this);
        //reader.setDTDHandler(this);
        reader.parse(in);
        return currentWorkbook;
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
    
//    public void setDocumentLocator(Locator locator) {
//        System.out.println("recieved locator");
//        this.locator = locator;
//    }
    
    

    public void startDocument() throws SAXException {        
        //System.out.println("doc started: ");
    }
    
    public void endDocument() throws SAXException {
        //System.out.println("doc ended: ");
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
//        System.out.println("startElement: "
//                + "\n\turi=" + uri
//                + "\n\tlcalName=" + localName
//                + "\n\tqName=" + qName);
        if (XLElement.XMLNS_SS.equals(uri) && "Workbook".equals(qName)) {
            currentWorkbook = new XLWorkbook("this must be fixed");
            Builder builder = getFactory().getXLWorkbookBuilder();
            builder.build(reader, this, getFactory(), currentWorkbook);
        }       
    }
    
    // @see org.xml.sax.helpers.DefaultHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
    public void unparsedEntityDecl(String name, String publicId,
            String systemId, String notationName) throws SAXException {
        System.out.println("unparsedEntityDecl:"
                + "\n\tname=" + name
                + "\n\tpublicId=" + publicId
                + "\n\tsystemId=" + systemId
                + "\n\tnotationName=" + notationName);
    }
    
    public void notationDecl(String name, String publicId, String systemId)
            throws SAXException {
        System.out.println("notation decl: "
                + "\n\tname=" + name
                + "\n\tpublicId=" + publicId
                + "\n\tsystemId=" + systemId);
    }
    
//    public void startPrefixMapping(String prefix, String uri)
//            throws SAXException {
//        System.out.println("prefix mapping: "
//                + "\n\tprefix=" + prefix
//                + "\n\turi=" + uri);
//    }
    
    private BuilderFactory getFactory() {
        if (factory == null) {
            factory = new BuilderFactory();
        }
        return factory;
    }
    

}
