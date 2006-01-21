/*
 * Created on May 20, 2005
 *
 */
package nl.fountain.vtks.trash;

import java.util.LinkedHashSet;

import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.lex.WorkbookListener;

public class EmptyLineListener extends WorkbookListener {
    
    private LinkedHashSet<String> dataColumns;
    private LinkedHashSet<String> dataRows;
    
    public void startWorkbook(String systemID) {
        super.startWorkbook(systemID);
        dataColumns = new LinkedHashSet<String>();
        dataRows = new LinkedHashSet<String>();
    }
    
    public void setCell(int sheetIndex, String sheetName, int rowIndex, Cell cell) {
        super.setCell(sheetIndex, sheetName, rowIndex, cell);
        if (cell.hasData()) {
            dataColumns.add(sheetIndex + "C" + cell.getIndex());
            dataRows.add(sheetIndex + "R" + rowIndex);
        }
    }
    
    public boolean isDataRow(int sheetIndex, int rowIndex) {
        return dataRows.contains(sheetIndex + "R" + rowIndex);
    }
    
    public boolean isDataColumn(int sheetIndex, int columnIndex) {
        return dataColumns.contains(sheetIndex + "C" + columnIndex);
    }
    
    public int getFirstEmptyRow(int sheetIndex, int afterRow) {
        while (isDataRow(sheetIndex, ++afterRow));
        return afterRow;
    }
    
    public int getFirstEmptyColumn(int sheetIndex, int afterColumn) {
        while (isDataColumn(sheetIndex, ++afterColumn));
        return afterColumn;
    }
    
    public LinkedHashSet getDataRows() {
        return dataRows;
    }
    
    public LinkedHashSet getDataColumns() {
        return dataColumns;
    }

}
