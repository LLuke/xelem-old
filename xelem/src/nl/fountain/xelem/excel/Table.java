/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import nl.fountain.xelem.CellPointer;



/**
 * Represents the Table element.
 */
public interface Table extends XLElement {
    
	/**
	 * Sets the ss:StyleID on this table. If no styleID is set on a table,
	 * the ss:StyleID-attribute is not deployed in the resulting xml and
	 * Excel employes the Default-style on the table.
	 * 
	 * @param 	id	the id of the style to employ on this table.
	 */
    void setStyleID(String id);
    
    /**
     * Gets the ss:StyleID which was set on this table.
     * 
     * @return 	The id of the style to employ on this table or
     * 			<code>null</code> if no styleID was previously set.
     */
    String getStyleID();
    
    /**
     * Sets the default row height.
     * 
     * @param points	The default row height.
     */
    void setDefaultRowHeight(double points);
    
    /**
     * Sets the default column width.
     * 
     * @param points	The default column width.
     */
    void setDefaultColumnWidth(double points);
    
    /**
     * Adds a new Column to this table. If no columns were previously added
     * the column will be added at index 1. Otherwise the column will be added
     * at {@link #maxColumnIndex()} + 1.
     * 
     * @param cellpointer A CellPointer with information regarding
     * 			first and last column indexes.
     * @return A new column.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn(CellPointer cellpointer);
    
    /**
     * Adds a new Column at the given index to this table. If the index 
     * was allready occupied by another column, replaces this column.
     * 
     * @param index The index (column number) of the column.
     * @param cellpointer A CellPointer with information regarding
     * 			first and last column indexes.	
     * @return A new column.
     * @throws IndexOutOfBoundsException If the given index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn(int index, CellPointer cellpointer);
    
    /**
     * Adds the given column to this table. If no columns were previously added
     * the column will be added at index 1. Otherwise the ccolumn will be added
     * at {@link #maxColumnIndex()} + 1.
     * 
     * @param column	The column to be added.
     * @param cellpointer A CellPointer with information regarding
     * 			first and last column indexes.	
     * @return The passed column.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn(Column column, CellPointer cellpointer);
    
    /**
     * Adds the given Column at the given index to this table. If the index 
     * was allready occupied by another column, replaces this column.
     * 
     * @param index The index (column number) of the column.
     * @param column	The column to be added.
     * @param cellpointer A CellPointer with information regarding
     * 			first and last column indexes.	
     * @return The passed column.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn(int index, Column column, CellPointer cellpointer);
    
    /**
     * Removes the column at the given index.
     * 
     * @param index The index (column number) of the column.
     * 
     * @return The removed column or <code>null</code> if the index was not occupied
     * 			by a column.
     */
    Column removeColumn(int index);
    
    /**
     * Gets the column at the given index.
     * 
     * @param index The index (column number) of the column.
     * 
     * @return The column at the given index or <code>null</code> 
     * 			if there was no column at the given index.
     */
    Column getColumn(int index);
    
    /**
     * Gets all the columns of this table in the order of their index.
     * 
     * @return A collection of columns.
     */
    Collection getColumns();
    
    /**
     * Returns an iterator for the columns in this table. Columns are aware of their
     * index number when passed by this iterator.
     * 
     * @return An iterator for the columns in this table.
     */
    Iterator columnIterator();
    
    /**
     * Adds a new Row to this table. If no rows were previously added
     * the row will be added at index 1. Otherwise the row will be added
     * at {@link #maxRowIndex()} + 1.
     * 
     * @param cellpointer A CellPointer with information regarding
     * 			first and last row indexes.
     * @return A new Row.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow(CellPointer cellpointer);
    
    /**
     * Adds a new Row at the given index to this table. If the index 
     * was allready occupied by another row, replaces this row.
     * 
     * @param index The index (row number) of the row.
     * @param cellpointer A CellPointer with information regarding
     * 			first and last row indexes.	
     * @return A new Row.
     * @throws IndexOutOfBoundsException If the given index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow(int index, CellPointer cellpointer);
    
    /**
     * Adds the given row to this table. If no rows were previously added
     * the row will be added at index 1. Otherwise the row will be added
     * at {@link #maxRowIndex()} + 1.
     * 
     * @param row	The row to be added.
     * @param cellpointer A CellPointer with information regarding
     * 			first and last row indexes.	
     * @return The passed row.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow(Row row, CellPointer cellpointer);
    
    /**
     * Adds the given Row at the given index to this table. If the index 
     * was allready occupied by another row, replaces this row.
     * 
     * @param index The index (row number) of the row.
     * @param row	The row to be added.
     * @param cellpointer A CellPointer with information regarding
     * 			first and last row indexes.	
     * @return The passed row.
     * @throws IndexOutOfBoundsException If the given index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow(int index, Row row, CellPointer cellpointer); 
    
    /**
     * Removes the row at the given index.
     * 
     * @param index The index (row number) of the row.
     * 
     * @return The removed row or <code>null</code> if the index was not occupied
     * 			by a row.
     */
    Row removeRow(int index); 
    
    /**
     * Gets the row at the given index. If no row was at the given index,
     * returns a new row at that index.
     * 
     * @param index The index (row number) of the row.
     * @param cellpointer A CellPointer with information regarding
     * 			first and last row indexes.	
     * @return The row at the given index.
     * @throws IndexOutOfBoundsException If the given index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row getRow(int index, CellPointer cellpointer); 
    
    /**
     * Gets the row at the given index, or <code>null</code> if no row
     * was at that index.
     */
    Row getRow(int index);
    
    /**
     * Gets all the rows of this table in the order of their index.
     * 
     * @return A collection of rows.
     */
    Collection getRows(); 
    
    /**
     * Gets all the rows of this table. The key of the Map.Entrys is of type
     * Integer and stands for the index number of the row.
     * 
     * @return A TreeMap of rows.
     */
    TreeMap getRowMap();
    
    /**
     * Returns an iterator for the rows in this table. Rows are aware of their
     * index number when passed by this iterator.
     * 
     * @return An iterator for the rows in this table.
     */
    Iterator rowIterator();
    
    /**
     * Returns the number of rows in this table.
     * @return The number of rows in this table.
     */
    int rowCount();
    
    /**
     * Returns the number of columns in this table.
     * @return The number of columns in this table.
     */
    int columnCount();
    
    /**
     * Indicates whether this table has any rows or columns.
     */
    boolean hasChildren();
    
    /**
     * Gets the highest index number of all the cells of all the rows in this table:
     * the right-most cell.
     * 
     * @return The highest index number of cells in this table.
     */
    int maxCellIndex();
    
    /**
     * Gets the highest index number of the rows in this table.
     * 
     * @return The highest index number of the rows in this table.
     */
    int maxRowIndex();
    
    /**
     * Gets the highest index number of the columns in this table.
     * 
     * @return The highest index number of the columns in this table.
     */
    int maxColumnIndex();  
    
}
