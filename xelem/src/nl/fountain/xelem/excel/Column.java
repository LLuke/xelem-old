/*
 * Created on Oct 28, 2004
 *
 */
package nl.fountain.xelem.excel;



/**
 * Represents the Column element.
 */
public interface Column extends XLElement {
    
    void setStyleID(String id);
    String getStyleID();
    void setAutoFitWidth(boolean autoFit);
    void setSpan(int s);
    void setWidth(double w);
    void setHidden(boolean hide);
    

}
