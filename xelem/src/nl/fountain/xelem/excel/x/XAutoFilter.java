/*
 * Created on Nov 4, 2004
 *
 */
package nl.fountain.xelem.excel.x;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.AutoFilter;

/**
 *
 */
public class XAutoFilter extends AbstractXLElement implements AutoFilter {
    
    private String range;

    // @see nl.fountain.xelem.excel.AutoFilter#setRange(java.lang.String)
    public void setRange(String rcString) {
        range = rcString;
    }
    
    // @see nl.fountain.xelem.excel.AutoFilter#getRange()
    public String getRange() {
        return range;
    }

    // @see nl.fountain.xelem.excel.XLElement#getTagName()
    public String getTagName() {
        return "AutoFilter";
    }

    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS_X;
    }

    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_X;
    }

    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        if (getRange() != null) {
	        Element afe = assemble(doc, gio);
	        afe.setAttributeNodeNS(createAttributeNS(doc, "Range", getRange()));
	        parent.appendChild(afe);
	        return afe;
        } else {
            return null;
        }
    }

}
