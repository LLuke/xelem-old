/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;



/**
 * Represents the Table element.
 */
public interface Table extends XLElement {
    
    void setStyleID(String id);
    String getStyleID();
    void setDefaultRowHeight(double points);
    void setDefaultColumnWidth(double points);
    
    Column addColumn();
    Column addColumn(int index);
    Column addColumn(Column column);
    Column addColumn(int index, Column column);
    Column removeColumn(int columnIndex);
    Column getColumn(int columnIndex);
    Collection getColumns();
    Iterator columnIterator();
    
    Row addRow();
    Row addRow(int index);
    Row addRow(Row row);
    Row addRow(int index, Row row);   
    Row removeRow(int rowIndex);   
    Row getRow(int rowIndex);   
    Row currentRow();
    Collection getRows();    
    Map getRowMap();
    Iterator rowIterator();
    
    int size();
    boolean hasChildren();
    
    int maxCellIndex();
    int maxRowIndex();
    int maxColumnIndex();  
    
}
