/*
 * Created on Nov 4, 2004
 *
 */
package nl.fountain.xelem.excel;

/**
 * Represents the AutoFilter element. 
 * <P>
 * There's a lot more to autofiltering
 * than this interface provides. However, if it suffices to just set the
 * autofiltering-mode to 'on' for a certain range, here's all you need.
 * <P>
 * Usually you don't have to deal with implementations of this interface.
 * You just call the Worksheet-method 
 * {@link nl.fountain.xelem.excel.Worksheet#setAutoFilter(String)}.
 */
public interface AutoFilter extends XLElement {
    
    
    void setRange(String rcString);
    String getRange();

}
