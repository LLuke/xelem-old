/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Collection;

/**
 * Represents the Worksheet element.
 */
public interface Worksheet extends XLElement {
    
    String getName();
    
    void setProtected(boolean p);
    void setRightToLeft(boolean r);
    
    WorksheetOptions addWorksheetOptions();
    WorksheetOptions addWorksheetOptions(WorksheetOptions wso);
    boolean hasWorksheetOptions();
    WorksheetOptions getWorksheetOptions();
    
    Table addTable();
    Table getTable();
    boolean hasTable();
    
    Cell addCell();
    Cell addCell(Cell cell);
    Cell addCell(Object data);
    Cell addCell(Object data, String styleID);
    Cell addCell(double data);
    Cell addCell(double data, String styleID);
    Cell addCell(int data);
    Cell addCell(int data, String styleID);
    
    Cell addCellAt(int rowIndex, int columnIndex);
    Cell addCellAt(int rowIndex, int columnIndex, Cell cell);
    
    Cell removeCellAt(int rowIndex, int columnIndex);
    Collection getCells();
    Cell getCellAt(int rowIndex, int columnIndex);
    
    Row addRow();
    Row addRow(int index);
    Row addRow(Row row);
    Row addRow(int index, Row row);
    
    Row removeRow(int rowIndex);
    Collection getRows();
    Row getRow(int rowIndex);
    
    Row currentRow();
    
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
