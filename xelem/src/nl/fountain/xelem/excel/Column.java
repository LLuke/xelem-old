/*
 * Created on Oct 28, 2004
 *
 */
package nl.fountain.xelem.excel;



/**
 * Represents the Column element. Only add columns
 * if formatting of columns is necessary.
 * <P>
 * The XLElement Column is not aware of it's parent,
 * nor of it's index-position in a table or worksheet. This makes it possible
 * to use the same instances of columns on different worksheets, or different
 * places in the worksheet. Only at the time of
 * assembly the column-index and the attribute ss:Index are automatically set, 
 * if necessary. See also: {@link nl.fountain.xelem.excel.Table#columnIterator()}.
 */
public interface Column extends XLElement {
    
    /**
     * Sets the ss:StyleID on this column. If no styleID is set on a column,
     * the ss:StyleID-attribute is not deployed in the resulting xml and
     * Excel employes the Default-style on the column.
     * 
     * @param 	id	the id of the style to employ on this column.
     */
    void setStyleID(String id);
    
    /**
     * Gets the ss:StyleID which was set on this column.
     * 
     * @return 	The id of the style to employ on this column or
     * 			<code>null</code> if no styleID was previously set.
     */
    String getStyleID();
    
    /**
     * Sets column-width to autofit for datatypes 'DateTime' and 'Number'. 
     * The default setting of Excel is 'true', so this method only has effect
     * when <code>false</code> is passed as a parameter.
     * 
     * @param autoFit	<code>false</code> if no autofit of column-width is
     * 					desired.
     */
    void setAutoFitWidth(boolean autoFit);
    
    /**
     * Sets the span of this column.
     * The value of 
     * <code>s</code> must be greater than 0 in order to have effect on
     * the resulting xml.
     * <P>
     * No other columns must be added within the span of this column:
     * <PRE>
     *         Column column = table.addColumn(5); // adds a column with index 5
     *         column.setWidth(25.2);              // do some formatting on the column
     *         column.setSpan(3);                  // span a total of 4 columns
     *         // illegal: first free index = 5 + (3+1) + 1 = 10
     *         // Column column2 = table.addColumn(8);
     * </PRE>
     * 
     * @param 	s	The number of columns to include in the span.
     */
    void setSpan(int s);
    
    /**
     * Sets the width of this column.
     * 
     * @param w	The width of the column (in points).
     */
    void setWidth(double w);
    
    /**
     * Sets whether this column will be hidden.
     * 
     * @param hide	<code>true</code> if this column must not be displayed.
     */
    void setHidden(boolean hide);
    

}
