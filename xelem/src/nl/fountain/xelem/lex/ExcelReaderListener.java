/*
 * Created on 6-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Row;

/**
 *
 */
public interface ExcelReaderListener {
    
    public void startWorkbook(String systemID, String workbookName);
    
    public void setDocumentProperties(DocumentProperties docprops);
    
    public void setExcelWorkbook(ExcelWorkbook xlwb);
    
    public void setNamedRange(NamedRange nr);
    
    public void startWorksheet(int index, String sheetName);
    
    public void setNamedRange(String sheetName, NamedRange nr);
    
    public void startTable(String sheetName, 
            int expandedRowCount, int expandedColumnCount);
    
    public void setColumn(String sheetName, int columnIndex, Column column);
    
    public void setRow(String sheetName, int rowIndex, Row row);

}
