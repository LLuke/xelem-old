/*
 * Created on May 17, 2005
 *
 */
package nl.fountain.vtks.trash;

import nl.fountain.xelem.Area;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.lex.DefaultExcelReaderFilter;
import nl.fountain.xelem.lex.ExcelReader;

/**
 *
 */
public class EmptyRowFilter extends DefaultExcelReaderFilter {
    
    private int emptyRowCounter;
    private int maxDataRow;
    private int prevMaxDataRow;
    private int prevRowIndex;
    private int start;
    private int stop;
    private ExcelReader reader;
    private boolean reading;
    
    public EmptyRowFilter(ExcelReader reader) {
        this.reader = reader;
    }
    
    public void setRowIndex(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }
    
    public void startWorksheet(int arg0, Worksheet arg1) {
        emptyRowCounter = 0;
        prevRowIndex = 0;
        maxDataRow = 0;
        setReadingMode(true);
        super.startWorksheet(arg0, arg1);
    }
    
    public void setCell(int sheetIndex, String sheetName, int rowIndex,
            Cell cell) {
        if (cell.hasData()) {
            int localMaxDataRow = rowIndex + cell.getMergeDown();
            if (localMaxDataRow > maxDataRow) maxDataRow = localMaxDataRow;
        }
        super.setCell(sheetIndex, sheetName, rowIndex, cell);
    }
    
    public void setRow(int sheetIndex, String sheetName, Row row) {
        if (row.getIndex() > prevMaxDataRow) {
            emptyRowCounter += row.getIndex() - prevMaxDataRow - 1;
        }
        prevRowIndex = row.getIndex();
        prevMaxDataRow = maxDataRow;
 
        // 
        if (emptyRowCounter >= start && emptyRowCounter < stop) {
            super.setRow(sheetIndex, sheetName, row);
        }
        
        if (emptyRowCounter >= stop) {
            setReadingMode(false);
        }
    }
    
    private void setReadingMode(boolean mode) {
        if (reading != mode) {
            if (reading) {
                reader.setReadArea(new Area(0, 0, 0, 0));
            } else {
                reader.clearReadArea();
            }
            reading = mode;
        }
    }
    

}
