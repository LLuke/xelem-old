/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.CharArrayWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.WorksheetOptions;
import nl.fountain.xelem.excel.XLElement;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
class AnonymousBuilder implements Builder {
    
    private static Map methodMap;
    private boolean occupied;
    protected XMLReader reader;
    protected ContentHandler parent;
    protected Director director;
    protected CharArrayWriter contents;
    protected XLElement current;
    
    protected AnonymousBuilder(Director director) {
        this.director = director;
        contents = new CharArrayWriter();
    }
    
    public void build(XMLReader reader, ContentHandler parent, XLElement xle) {
        setUpBuilder(reader, parent);
        current = xle;
    }
    
    public void build(XMLReader reader, ContentHandler parent) {
        setUpBuilder(reader, parent);
    }
    
    protected void setUpBuilder(XMLReader reader, ContentHandler parent) {
        this.reader = reader;
        this.parent = parent;
        reader.setContentHandler(this);
    }
    
    protected void setOccupied(boolean b) {
        occupied = b;
    }
    
    protected boolean isOccupied() {
        return occupied;
    }
    
    private Method getMethod(String tagName) {
        return (Method) getMethodMap().get(tagName);
    }
    
    private Map getMethodMap() {
        if (methodMap == null) {
            methodMap = new HashMap();
            Object[][] methods = null;
            try {
                methods = new Object[][]{
                   {"DocumentProperties",  ExcelReaderListener.class.getMethod("setDocumentProperties", new Class[]{DocumentProperties.class})},
                   {"ExcelWorkbook", ExcelReaderListener.class.getMethod("setExcelWorkbook", new Class[]{ExcelWorkbook.class})},
                   {"WorksheetOptions", ExcelReaderListener.class.getMethod("setWorksheetOptions", new Class[]{int.class, String.class, WorksheetOptions.class})}
                };
                for (int i = 0; i < methods.length; i++) {
                    methodMap.put(methods[i][0], methods[i][1]);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return methodMap;
    }
    
    private void informListeners() {
        if (director.getListeners().size() > 0) {
            Method m = getMethod(current.getTagName());
            if (m != null) {
	            try {
	                for (Iterator iter = director.getListeners().iterator(); iter.hasNext();) {
	                    ExcelReaderListener listener = (ExcelReaderListener) iter.next();
	                    if (m.getParameterTypes()[0].equals(int.class)) {
	                        m.invoke(listener, new Object[] {new Integer(director.getCurrentSheetIndex()), director.getCurrentSheetName(), current});
	                    } else {
	                        m.invoke(listener, new Object[] { current });
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            } 
            }
        }
    }

    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        //System.out.println(localName);
        if (!XLElement.XMLNS_HTML.equals(uri)) {
            contents.reset();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println(localName);
        if (current.getNameSpace().equals(uri)) {	        
            if (current.getTagName().equals(localName)) {
                informListeners();
                reader.setContentHandler(parent);
                occupied = false;
                return;
            }
        }
        current.setChildElement(localName, contents.toString());
    }

    public void characters(char[] ch, int start, int length)
		throws SAXException {
        contents.write(ch, start, length);
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

    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
    }

    public void processingInstruction(String target, String data)
            throws SAXException {
    }

    public void skippedEntity(String name) throws SAXException {
    }


    

}
