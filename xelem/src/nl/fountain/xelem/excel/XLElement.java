/*
 * Created on 31-okt-2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.List;

import nl.fountain.xelem.GIO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Represents an element in SpreadsheetML. 
 * <P>
 * XLElements are unaware of their parent. This makes it possible to move and
 * duplicate them to other, allthough appropriate, XLElements of the Workbook.
 * Even to other Workbooks.
 * <P>
 * An XLElement is capable of assembling it's state into an
 * {@link org.w3c.dom.Element} and attach this Element to a parent-element in a
 * {@link org.w3c.dom.Document}.
 * 
 */
public interface XLElement {
    
    /**
     * The default SpreadsheetML namespace: {@value}
     */
    public static final String XMLNS="urn:schemas-microsoft-com:office:spreadsheet";
    
    /**
     * The Office namespace.
     */
    public static final String XMLNS_O="urn:schemas-microsoft-com:office:office";
    
    /**
     * The Excel namespace.
     */
    public static final String XMLNS_X="urn:schemas-microsoft-com:office:excel";
    
    /**
     * The SpreadsheetML namespace.
     */
    public static final String XMLNS_SS="urn:schemas-microsoft-com:office:spreadsheet";
    
    /**
     * HTML-namespace.
     */
    public static final String XMLNS_HTML="http://www.w3.org/TR/REC-html40";
    
    /**
     * The Office namespace prefix: {@value}.
     */
    public static final String PREFIX_O = "o";
    
    /**
     * The Excel namespace prefix: {@value}.
     */
    public static final String PREFIX_X = "x";
    
    /**
     * The SpreadsheetML namespace prefix: {@value}.
     */
    public static final String PREFIX_SS = "ss";
    
    /**
     * HTML-namespace prefix: {@value}.
     */
    public static final String PREFIX_HTML = "html";
    
    /**
     * Gets the tagname (qualified name) of this XLElement.
     * @return qualified name
     */
    String getTagName();
    
    /**
     * Gets the namespace of this XLElement.
     * @return namespace
     */
    String getNameSpace();
    
    /**
     * Gets the prefix of this XLElement.
     * @return prefix
     */
    String getPrefix();
    
    /**
     * Adds a comment to this XLElement.
     * @param comment	
     * @see nl.fountain.xelem.excel.Workbook#setPrintComments(boolean print)
     */
    void addComment(String comment);
    
    /**
     * Gets a List of added comments.
     * @return List of Strings
     */
    List getComments();   
    
    /**
     * Assembles the state of this XLElement and all of it's children into an
     * {@link org.w3c.dom.Element}. Attaches the new element to it's
     * parent-element if needed.
     * 
     * @param 	doc	the enveloping Document
     * @param 	parent the parent-element to which the new formed element 
     * 			will be appended
     * @param 	gio a global information object
     * 
     * @return	the newly assembled element. may be null.
     */
    Element assemble(Document doc, Element parent, GIO gio);
}
