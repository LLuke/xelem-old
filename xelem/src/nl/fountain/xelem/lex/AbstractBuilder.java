/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.CharArrayWriter;
import java.lang.reflect.Method;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public abstract class AbstractBuilder implements Builder {
    
    protected XMLReader reader;
    protected ContentHandler parent;
    protected BuilderFactory factory;
    protected CharArrayWriter contents;
    
    protected void build(XMLReader reader, ContentHandler parent, BuilderFactory factory) {
        this.reader = reader;
        this.parent = parent;
        this.factory = factory;
        reader.setContentHandler(this);
    }
    
    protected void invokeMethod(Object obj, String qName, Object value) {
        Class[] types = new Class[] {value.getClass()};
        Method method = null;
        try {
            method = obj.getClass().getMethod("set" + qName, types);
            method.invoke(obj, new Object[]{value});
        } catch (NoSuchMethodException e) {
            // no big deal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
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
