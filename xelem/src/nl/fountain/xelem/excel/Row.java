/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;



/**
 * Represents the Row element.
 */
public interface Row extends XLElement {
    
    void setStyleID(String id);
    String getStyleID();
    void setSpan(int s);
    void setHeight(double h);
    void setHidden(boolean hide);
    //void setAutoFitHeight(boolean autoFit);
    
    Cell addCell();
    Cell addCell(Object data);
    Cell addCell(Object data, String styleID);
    Cell addCell(double data);
    Cell addCell(double data, String styleID);
    Cell addCell(int data);
    Cell addCell(int data, String styleID);
    
    Cell addCell(Cell cell);
    
    Cell addCellAt(int index);   
    Cell addCellAt(int index, Cell cell);
    Cell removeCellAt(int index);
    Cell getCellAt(int index);
    
    Collection getCells();
    Map getCellMap();
    int size();
    
    int maxCellIndex();
    
    Iterator cellIterator();
    
}
