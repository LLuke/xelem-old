/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Collection;

import nl.fountain.xelem.CellPointer;

/**
 * Represents the Worksheet element.
 */
public interface Worksheet extends XLElement {
    
    /**
     * Returns the name of this worksheet.
     * 
     */
    String getName();
    
    /**
     * Sets whether protection will be applied to this worksheet.
     */
    void setProtected(boolean p);
    
    /**
     * Sets whether this worksheet will be displayed from right to left.
     */
    void setRightToLeft(boolean r);
    
    /**
     * Indicates whether WorksheetOptions was added to this worksheet.
     * 
     * @return <code>true</code> if this worksheet has WorksheetOptions.
     */
    boolean hasWorksheetOptions();
    
    /**
     * Gets the WorksheetOptions of this worksheet.
     * 
     * @return The WorksheetOptions of this worksheet.
     */
    WorksheetOptions getWorksheetOptions();
    
    /**
     * Gets the table of this worksheet.
     * 
     * @return The table of this worksheet. Never <code>null</code>.
     */
    Table getTable();
    
    /**
     * Indicates whether this worksheet has a table.
     */
    boolean hasTable();
    
    /**
     * Gets the cellpointer of this worksheet.
     * 
     * @return This worksheets cellpointer.
     */
    CellPointer getCellPointer();
       
    /**
     * Adds a new cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     * <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * 
     * @return A new cell with an initial datatype of "String" and an
     * 			empty ("") value.
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCell();
    
    /**
     * Adds the given cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     * <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * 
     * @return The passed cell.
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCell(Cell cell);
    
    /**
     * Adds a new cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     *  <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * <P>
     * Datatype and value of the new cell depend on the passed object.
     * 
     * @param data	The data to be displayed in this cell.
     * 
     * @return A new cell.
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(Object)
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCell(Object data);
    
    /**
     * Adds a new cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     *  <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * <P>
     * Datatype and value of the new cell depend on the passed object.
     * The new cell will have it's styleID set to the given id.
     * 
     * @param data		The data to be displayed in this cell.
     * @param styleID	The id of the style to employ on this cell.
     * 
     * @return A new cell.
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(Object)
     * @see nl.fountain.xelem.excel.Cell#setStyleID(String)
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCell(Object data, String styleID);
    
    /**
     * Adds a new cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     *  <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * 
     * @param data		The data to be displayed in this cell.
     * 
     * @return A new cell with a datatype "Number" and the given double as value. 
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(double)
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCell(double data);
    
    /**
     * Adds a new cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     *  <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * The new cell will have it's styleID set to the given id.
     * 
     * @param data		The data to be displayed in this cell.
     * @param styleID	The id of the style to employ on this cell.
     * 
     * @return A new cell with a datatype "Number" and the given double as value. 
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.excel.Cell#setData(double)
     * @see nl.fountain.xelem.excel.Cell#setStyleID(String)
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCell(double data, String styleID);
    
    /**
     * Adds a new cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     *  <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * 
     * @param data		The data to be displayed in this cell.
     * 
     * @return A new cell with a datatype "Number" and the given int as value.
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCell(int data);
    
    /**
     * Adds a new cell at the current position of the worksheets
     * {@link nl.fountain.xelem.CellPointer} and moves the cellpointer.
     *  <br>
     * If this worksheet did not have a table, 
     * a new table will be added.
     * 
     * @param data		The data to be displayed in this cell.
     * @param styleID	The id of the style to employ on this cell.
     * 
     * @return A new cell with a datatype "Number" and the given int as value. 
     * 
     * @throws IndexOutOfBoundsException if the position of the cellpointer
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.excel.Cell#setStyleID(String)
     * @see nl.fountain.xelem.CellPointer#move
     */
    Cell addCell(int data, String styleID);
    
    /**
     * Adds a new cell at the given row and column index. If this worksheet
     * didn't have a row at the given index, a new row will be added. If the
     * place at the given coordinates was allredy occupied by another cell,
     * replaces this cell. If this worksheet did not have a table, 
     * a new table will be added.
     * <br>
     * Moves the cellpointer to a new position relative to
     * <code>rowIndex</code> and <code>columnIndex</code>.
     * 
     * @param rowIndex		The row index (row number).
     * @param columnIndex	The column index (column number).
     * 
     * @return A new cell with an initial datatype of "String" and an
     * 			empty ("") value.
     * 
     * @throws IndexOutOfBoundsException if the position
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCellAt(int rowIndex, int columnIndex);
    
    /**
     * Adds the given cell at the given row and column index. If this worksheet
     * didn't have a row at the given index, a new row will be added. If the
     * place at the given coordinates was allredy occupied by another cell,
     * replaces this cell. If this worksheet did not have a table, 
     * a new table will be added. 
     * <br>
     * Moves the cellpointer to a new position relative to
     * <code>rowIndex</code> and <code>columnIndex</code>.
     * 
     * @param rowIndex		The row index (row number).
     * @param columnIndex	The column index (column number).
     * @param cell			The cell to be added.
     * 
     * @return The passed cell.
     * 
     * @throws IndexOutOfBoundsException if the position
     * 			is not within the limits of the worksheet.
     * 
     * @see nl.fountain.xelem.CellPointer#move()
     */
    Cell addCellAt(int rowIndex, int columnIndex, Cell cell);
    
    /**
     * Removes the cell at the given coordinates.
     * 
     * @param rowIndex		The row index (row number).
     * @param columnIndex	The column index (column number).
     * 
     * @return The removed cell or <code>null</code>.
     */
    Cell removeCellAt(int rowIndex, int columnIndex);
    
    /**
     * Gets the cell at the given coordinates.
     * 
     * @param rowIndex		The row index (row number).
     * @param columnIndex	The column index (column number).
     * 
     * @return The cell at the given coordinates or <code>null</code> 
     * 			if no cell was at that position.
     */
    Cell getCellAt(int rowIndex, int columnIndex);
    
    /**
     * Adds a new Row to this worksheet. If no rows were previously added
     * the row will be added at index 1. Otherwise the row will be added at 
     * {@link nl.fountain.xelem.excel.Table#maxRowIndex() maxRowIndex} 
     * of the underlying Table + 1.
     * 
     * @return A new Row.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow();
    
    /**
     * Adds a new Row at the given index to this worksheet. If the index 
     * was allready occupied by another row, replaces this row.
     * 
     * @param index The index (row number) of the row.
     * @return A new Row.
     * @throws IndexOutOfBoundsException If the given index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow(int index);
    
    /**
     * Adds the given row to this worksheet. If no rows were previously added
     * the row will be added at index 1. Otherwise the row will be added at
     * {@link nl.fountain.xelem.excel.Table#maxRowIndex() maxRowIndex} 
     * of the underlying Table + 1.
     * 
     * @param row	The row to be added.
     * @return The passed row.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow(Row row);
    
    /**
     * Adds the given Row at the given index to this worksheet. If the index 
     * was allready occupied by another row, replaces this row.
     * 
     * @param index The index (row number) of the row.
     * @param row	The row to be added.
     * @return The passed row.
     * @throws IndexOutOfBoundsException If the given index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstRow} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastRow}.
     */
    Row addRow(int index, Row row);
    
    /**
     * Removes the row at the given index.
     * 
     * @param rowIndex The index (row number) of the row.
     * 
     * @return The removed row or <code>null</code> if the index was not occupied
     * 			by a row.
     */
    Row removeRow(int rowIndex);
    
    /**
     * Gets all the rows of this worksheet in the order of their index.
     * 
     * @return A collection of rows.
     */
    Collection getRows();
    
    /**
     * Gets the row at the given index.
     * 
     * @param rowIndex The index (row number) of the row.
     * 
     * @return The row at the given index or <code>null</code> 
     * 			if there was no row at the given index.
     */
    Row getRow(int rowIndex);
    
    
    /**
     * Adds a new Column to this worksheet. If no columns were previously added
     * the column will be added at index 1. Otherwise the column will be added at 
     * {@link nl.fountain.xelem.excel.Table#maxColumnIndex() maxColumnIndex} 
     * of the underlying Table + 1.
     * 
     * @return A new column.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn();
    
    /**
     * Adds a new Column at the given index to this worksheet. If the index 
     * was allready occupied by another column, replaces this column.
     * 
     * @param index The index (column number) of the column.
     * @return A new column.
     * @throws IndexOutOfBoundsException If the given index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn(int index);
    
    /**
     * Adds the given column to this worksheet. If no columns were previously added
     * the column will be added at index 1. Otherwise the ccolumn will be added at 
     * {@link nl.fountain.xelem.excel.Table#maxColumnIndex() maxColumnIndex} 
     * of the underlying Table + 1.
     * 
     * @param column	The column to be added.
     * @return The passed column.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn(Column column);
    
    /**
     * Adds the given Column at the given index to this worksheet. If the index 
     * was allready occupied by another column, replaces this column.
     * 
     * @param index The index (column number) of the column.
     * @param column	The column to be added.
     * @return The passed column.
     * @throws IndexOutOfBoundsException If the calculated index is less then
     * 			{@link nl.fountain.xelem.CellPointer#firstColumn} or greater
     * 			then {@link nl.fountain.xelem.CellPointer#lastColumn}.
     */
    Column addColumn(int index, Column column);
    
    /**
     * Removes the column at the given index.
     * 
     * @param columnIndex The index (column number) of the column.
     * 
     * @return The removed column or <code>null</code> if the index was not occupied
     * 			by a column.
     */
    Column removeColumn(int columnIndex);
    
    /**
     * Gets all the columns of this worksheet in the order of their index.
     * 
     * @return A collection of columns.
     */
    Collection getColumns();
    
    /**
     * Gets the column at the given index.
     * 
     * @param columnIndex The index (column number) of the column.
     * 
     * @return The column at the given index or <code>null</code> 
     * 			if there was no column at the given index.
     */
    Column getColumn(int columnIndex);
    
    /**
     * Sets the AutoFilter-option on the specified range.
     * <P>
     * The String <code>rcString</code> should include all of the
     * columns that are to be equiped with a drop-down button at their column-
     * headings, and should either include just the row of the column-headings
     * or the entire range on which the AutoFilter must be set.
     * <P>
     * Here are some examples:
     * <br>
     * Given a table of 3 columns and 11 rows. The column-headings are at row 10.
     * The table-data expands to row 21.
     * <UL>
     * <LI><code><b>R10C1:R10C3</b></code> - the AutoFilter includes the entire range
     * 		(R10C1:R21C3) and will dynamically expand if more rows of data are
     * 		added below.
     * <LI><code><b>R10C1:R21C3</b></code> - the AutoFilter includes the entire range
     * 		(R10C1:R21C3) and will dynamically expand if more rows of data are
     * 		added below.
     * <LI><code><b>R10C1:R10C2</b></code> - the AutoFilter includes the entire range
     * 		(R10C1:R21C3) and will dynamically expand if more rows of data are
     * 		added below, however, only the first two column-headings have drop
     * 		-down buttons.
     * <LI><code><b>R12C1:R12C3</b></code> - drop-down buttons manifest at row 12;
     * 		this is probably not what you wanted.
     * </UL>
     * 
     * @param rcString	A String of R1C1 reference style.
     */
    void setAutoFilter(String rcString);
    
    /**
     * Specifies whether the {@link #setAutoFilter(String) setAutoFilter}-method 
     * was applied on this worksheet.
     *  
     * @return true if the {@link #setAutoFilter(String) setAutoFilter}-method 
     * 				was applied on this worksheet, false otherwise.
     */
    boolean hasAutoFilter();
    
    //int rowCount();
    //int columnCount();
    
}
