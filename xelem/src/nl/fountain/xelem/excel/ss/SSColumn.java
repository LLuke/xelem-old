/*
 * Created on Oct 28, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.Column;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class SSColumn extends AbstractXLElement implements Column {
    
    private int idx;
    private String styleID;
    private int span;
    private double width;
    private boolean hidden;
    private boolean autoFitWidth = true;

    // @see nl.fountain.xelem.excel.ss.Column#setStyleID(java.lang.String)
    public void setStyleID(String id) {
        styleID = id;
    }

    // @see nl.fountain.xelem.excel.ss.Column#getStyleID()
    public String getStyleID() {
        return styleID;
    }
    
    // @see nl.fountain.xelem.excel.Column#setAutoFitWidth(boolean)
    public void setAutoFitWidth(boolean autoFit) {
        autoFitWidth = autoFit;
    }
    
    // @see nl.fountain.xelem.excel.ss.Column#setSpan(int)
    public void setSpan(int s) {
        span = s;
    }
    
    // @see nl.fountain.xelem.excel.ss.Column#setWidth(double)
    public void setWidth(double w) {
        width = w;
    }
    
    // @see nl.fountain.xelem.excel.ss.Column#setHidden(boolean)
    public void setHidden(boolean hide) {
        hidden = hide;
    }
    
    public String getTagName() {
        return "Column";
    }
    
    public String getNameSpace() {
        return XMLNS_SS;
    }
    
    public String getPrefix() {
        return PREFIX_SS;
    }
    
    protected void setIndex(int index) {
        idx = index;
    }
    
    public Element assemble(Document doc, Element parent, GIO gio) {
        Element ce = assemble(doc, gio);
        
        if (idx != 0) ce.setAttributeNodeNS(
                createAttributeNS(doc, "Index", idx));
        if (getStyleID() != null) {
            ce.setAttributeNodeNS(createAttributeNS(doc, "StyleID", getStyleID()));
            gio.addStyleID(getStyleID());
        }
        
        if (span > 0) ce.setAttributeNodeNS(
                createAttributeNS(doc, "Span", span));
        if (width > 0.0) ce.setAttributeNodeNS(
                createAttributeNS(doc, "Width", "" + width));
        if (hidden) ce.setAttributeNodeNS(
                createAttributeNS(doc, "Hidden", "1"));
        if (!autoFitWidth) ce.setAttributeNodeNS(
                createAttributeNS(doc, "AutoFitWidth", "0"));
        
        parent.appendChild(ce);
        return ce;
    }



}
