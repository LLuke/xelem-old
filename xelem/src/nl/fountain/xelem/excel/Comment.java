/*
 * Created on 31-mrt-2005
 *
 */
package nl.fountain.xelem.excel;

/**
 *
 */
public interface Comment extends XLElement {
    
    void setAuthor(String author);
    String getAuthor();
    
    void setShowAlways(boolean show);
    boolean showsAlways();
    
    void setData(String data);
    String getData();
    String getDataClean();

}
