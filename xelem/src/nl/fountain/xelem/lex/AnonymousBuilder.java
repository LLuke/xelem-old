/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.CharArrayWriter;

import nl.fountain.xelem.excel.XLElement;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class AnonymousBuilder implements Builder {
    
    private boolean occupied;
    protected XMLReader reader;
    protected ContentHandler parent;
    protected BuilderFactory factory;
    protected CharArrayWriter contents;
    protected XLElement current;
    
    protected AnonymousBuilder() {
        contents = new CharArrayWriter();
    }
    
    public void build(XMLReader reader, ContentHandler parent, 
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        current = xle;
    }
    
    protected void setUpBuilder(XMLReader reader, ContentHandler parent, 
            BuilderFactory factory) {
        this.reader = reader;
        this.parent = parent;
        this.factory = factory;
        reader.setContentHandler(this);
    }
    
    protected void setOccupied(boolean b) {
        occupied = b;
    }
    
    protected boolean isOccupied() {
        return occupied;
    }
    
//    protected void invokeMethod(Object obj, String localName, Object value) {
//        Class[] types = new Class[] {value.getClass()};
//        Method method = null;
//        try {
//            method = obj.getClass().getMethod("set" + localName, types);
//            method.invoke(obj, new Object[]{value});
//        } catch (NoSuchMethodException e) {
//            // no big deal
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setDocumentLocator(Locator locator) {
    }

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
    }

    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        contents.reset();
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (current.getNameSpace().equals(uri)) {	        
            if (current.getTagName().equals(qName)) {
                reader.setContentHandler(parent);
                occupied = false;
                return;
            }
            //invokeMethod(current, localName, contents.toString());
            current.setChildElement(localName, contents.toString());
        }
    }

    public void characters(char[] ch, int start, int length)
		throws SAXException {
        contents.write(ch, start, length);
    }

    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
    }

    public void processingInstruction(String target, String data)
            throws SAXException {
    }

    public void skippedEntity(String name) throws SAXException {
    }


    

}
