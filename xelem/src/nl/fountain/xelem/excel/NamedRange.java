/*
 * Created on Nov 4, 2004
 *
 */
package nl.fountain.xelem.excel;

/**
 * Represents the NamedRange element.
 */
public interface NamedRange extends XLElement {
    
    /**
     * Sets whether this named range will be displayed in the
     * drop-down box of the Excel application. The default is false:
     * do not hide the name.
     */
    void setHidden(boolean hide);

}
