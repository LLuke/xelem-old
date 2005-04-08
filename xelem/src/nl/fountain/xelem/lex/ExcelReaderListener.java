/*
 * Created on 6-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.Map;

import nl.fountain.xelem.excel.AutoFilter;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.WorksheetOptions;

/**
 *
 */
public interface ExcelReaderListener {
    
    void startDocument();
    
    void processingInstruction(String target, String data);
    
    void startWorkbook(String systemID, String workbookName);
    
    void setDocumentProperties(DocumentProperties docProps);
    
    void setExcelWorkbook(ExcelWorkbook excelWb);
    
    void setNamedRange(NamedRange namedRange);
    
    void startWorksheet(int sheetIndex, Worksheet sheet);
    
    void setNamedRange(int sheetIndex, String sheetName, NamedRange namedRange);
    
    void startTable(int sheetIndex, String sheetName, Table table);
    
    void setColumn(int sheetIndex, String sheetName, Column column);
    
    void setRow(int sheetIndex, String sheetName, Row row);
    
    void setCell(int sheetIndex, String sheetName, int rowIndex, Cell cell);
    
    void setWorksheetOptions(int sheetIndex, String sheetName, WorksheetOptions wsOptions);
    
    void setAutoFilter(int sheetIndex, String sheetName, AutoFilter autoFilter);
    
    void endDocument(Map prefixMap);

}
