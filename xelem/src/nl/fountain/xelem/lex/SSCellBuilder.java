/*
 * Created on 22-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.Comment;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.SSCell;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class SSCellBuilder extends AnonymousBuilder {
    
    private SSCell current;
    
    public void build(XMLReader reader, ContentHandler parent,
            BuilderFactory factory, XLElement xle) {
        setUpBuilder(reader, parent, factory);
        current = (SSCell) xle;
    }
    
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        contents.reset();
        if (XLElement.XMLNS_SS.equals(uri)) {
            if ("Data".equals(localName)) {
	            // set the atts of the data element
	            current.setAttributes(atts);
            } else if ("Comment".equals(localName)) {
                Comment comment = current.addComment();
                comment.setAttributes(atts);
                Builder builder = factory.getAnonymousBuilder();
                builder.build(reader, this, factory, comment);
            }
        }
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (current.getNameSpace().equals(uri)) {
            if (current.getTagName().equals(localName)) {
                reader.setContentHandler(parent);
                return;
            }
            current.setChildElement(localName, contents.toString());
        }
    }

}
