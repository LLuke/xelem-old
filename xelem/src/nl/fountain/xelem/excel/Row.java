/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;



/**
 * Represents the Row element. The XLElement Row is not aware of it's parent,
 * nor of it's index-position in a table or worksheet. This makes it possible
 * to use the same instances of Rows on different worksheets, or different
 * places in the worksheet. Only at the time of
 * assembly the row-index and the attribute ss:Index are automatically set, 
 * if necessary. See also: {@link nl.fountain.xelem.excel.Table#rowIterator()}.
 * 
 * @see nl.fountain.xelem.excel.Worksheet#addRow()
 * @see nl.fountain.xelem.excel.Table#addRow(CellPointer)
 */
public interface Row extends XLElement {
    
	/**
	 * Sets the ss:StyleID on this row. If no styleID is set on a row,
	 * the ss:StyleID-attribute is not deployed in the resulting xml and
	 * Excel employes the Default-style on the row.
	 * 
	 * @param 	id	the id of the style to employ on this row.
	 */
    void setStyleID(String id);
    
    /**
     * Gets the ss:StyleID which was set on this row.
     * 
     * @return 	The id of the style to employ on this row or
     * 			<code>null</code> if no styleID was previously set.
     */
    String getStyleID();
    
    /**
     * Sets the span of this row.
     * The value of 
     * <code>s</code> must be greater than 0 in order to have effect on
     * the resulting xml.
     * <P>
     * No other rows must be added within the span of this row:
     * <PRE>
     *         Row row = table.addRow(5); // adds a row with index 5
     *         row.setHeight(25.2);       // do some formatting on the row
     *         row.setSpan(3);            // span a total of 4 rows
     *         // illegal: first free index = 5 + 3 + 1 = 9
     *         // Row row2 = table.addRow(8);
     * </PRE>
     * 
     * @param 	s	The number of additional rows to include in the span.
     */
    void setSpan(int s);
    
    /**
     * Sets the height of this row.
     * 
     * @param h	The height of the row (in points).
     */
    void setHeight(double h);
    
    /**
     * Sets whether this row will be hidden.
     * 
     * @param hide	<code>true</code> if this row must not be displayed.
     */
    void setHidden(boolean hide);
    
    //void setAutoFitHeight(boolean autoFit);
    
    /**
     * Adds a new Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * 
     * @return A new cell with an initial datatype of "String" and an
     * 			empty ("") value.
     */
    Cell addCell();
    
    /**
     * Adds a new Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * <P>
     * Datatype and value of the new cell depend on the passed object.
     * 
     * @param data	The data to be displayed in this cell.
     * 
     * @return A new cell.
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(Object)
     */
    Cell addCell(Object data);
    
    /**
     * Adds a new Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * <P>
     * Datatype and value of the new cell depend on the passed object.
     * The new cell will have it's styleID set to the given id.
     * 
     * @param data		The data to be displayed in this cell.
     * @param styleID	The id of the style to employ on this cell.
     * 
     * @return A new cell.
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(Object)
     * @see nl.fountain.xelem.excel.Cell#setStyleID(String)
     */
    Cell addCell(Object data, String styleID);
    
    /**
     * Adds a new Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * 
     * @param data		The data to be displayed in this cell.
     * 
     * @return A new cell with a datatype "Number" and the given double as value. 
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(double)
     */
    Cell addCell(double data);
    
    /**
     * Adds a new Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * The new cell will have it's styleID set to the given id.
     * 
     * @param data		The data to be displayed in this cell.
     * @param styleID	The id of the style to employ on this cell.
     * 
     * @return A new cell with a datatype "Number" and the given double as value. 
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(double)
     * @see nl.fountain.xelem.excel.Cell#setStyleID(String)
     */
    Cell addCell(double data, String styleID);
    
    /**
     * Adds a new Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * 
     * @param data		The data to be displayed in this cell.
     * 
     * @return A new cell with a datatype "Number" and the given int as value. 
     */
    Cell addCell(int data);
    
    /**
     * Adds a new Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * The new cell will have it's styleID set to the given id.
     * 
     * @param data		The data to be displayed in this cell.
     * @param styleID	The id of the style to employ on this cell.
     * 
     * @return A new cell with a datatype "Number" and the given int as value. 
     * 
     * @see nl.fountain.xelem.excel.Cell#setStyleID(String)
     */
    Cell addCell(int data, String styleID);
    
    /**
     * Adds the given Cell to this row. If no cells were previously added
     * the cell will be added at index 1. Otherwise the cell will be added
     * at {@link #maxCellIndex()} + 1.
     * 
     * @param 	cell	The cell that is to be appended to this row.
     * 
     * @return 	The passed cell.
     */
    Cell addCell(Cell cell);
    
    /**
     * Adds a new Cell at the given index. If the index was allready occupied
     * by another cell, replaces this cell.
     * 
     * @param index The index (column number) of the cell.
     * 
     * @return A new cell with an initial datatype of "String" and an
     * 			empty ("") value.
     */
    Cell addCellAt(int index); 
    
    /**
     * Adds the given Cell at the given index. If the index was allready occupied
     * by another cell, replaces this cell.
     * 
     * @param index The index (column number) of the cell.
     * @param cell	The cell that is to be appended to this row.
     * 
     * @return 	The passed cell.
     */
    Cell addCellAt(int index, Cell cell);
    
    /**
     * Removes the cell at the given index.
     * 
     * @param index The index (column number) of the cell.
     * 
     * @return The removed cell or <code>null</code> if the index was not occupied
     * 			by a cell.
     */
    Cell removeCellAt(int index);
    
    /**
     * Gets the cell at the given index.
     * 
     * @param index The index (column number) of the cell.
     * 
     * @return The cell at the given index or <code>null</code> 
     * 			if there was no cell at the given index.
     */
    Cell getCellAt(int index);
    
    /**
     * Gets all the cells of this row in the order of their index.
     * 
     * @return A collection of cells.
     */
    Collection getCells();
    
    /**
     * Gets all the cells of this row. The key of the Map.Entrys is of type
     * Integer and stands for the index number of the cell.
     * 
     * @return A TreeMap of cells.
     */
    TreeMap getCellMap();
    
    /**
     * Gets the number of cells in this row.
     * 
     * @return The number of cells in this row.
     */
    int size();
    
    /**
     * Gets the highest index number of the cells in this row.
     * 
     * @return The highest index number of the cells in this row.
     */
    int maxCellIndex();
    
    /**
     * Returns an iterator for the cells in this row. Cells are aware of their
     * index number when passed by this iterator.
     * 
     * @return An iterator for the cells in this row.
     */
    Iterator cellIterator();
    
}
