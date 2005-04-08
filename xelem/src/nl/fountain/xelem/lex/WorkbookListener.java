/*
 * Created on 8-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.AutoFilter;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.WorksheetOptions;
import nl.fountain.xelem.excel.ss.XLWorkbook;


public class WorkbookListener extends DefaultExcelReaderListener {
    
    private Workbook currentWorkbook;
    private Worksheet currentWorksheet;
    private Table currentTable;

    public void startWorkbook(String systemID, String workbookName) {
        currentWorkbook = new XLWorkbook(workbookName);
        currentWorkbook.setFileName(systemID);
    }

    public void setDocumentProperties(DocumentProperties docProps) {
        currentWorkbook.setDocumentProperties(docProps);
    }

    public void setExcelWorkbook(ExcelWorkbook excelWb) {
        currentWorkbook.setExcelWorkbook(excelWb);
    }

    public void setNamedRange(NamedRange namedRange) {
        currentWorkbook.addNamedRange(namedRange);
    }

    public void startWorksheet(int sheetIndex, Worksheet sheet) {
        currentWorksheet = sheet;
        currentWorkbook.addSheet(currentWorksheet);
    }

    public void setNamedRange(int sheetIndex, String sheetName, NamedRange namedRange) {
        currentWorksheet.addNamedRange(namedRange);
    }

    public void startTable(int sheetIndex, String sheetName, Table table) {
        currentTable = table;
        currentWorksheet.setTable(table);
    }

    public void setColumn(int sheetIndex, String sheetName, Column column) {
        currentTable.addColumnAt(column.getIndex(), column);
    }

    public void setRow(int sheetIndex, String sheetName, Row row) {
        currentTable.addRowAt(row.getIndex(), row);
    }

    public void setWorksheetOptions(int sheetIndex, String sheetName,
            WorksheetOptions wsOptions) {
        currentWorksheet.setWorksheetOptions(wsOptions);
    }

    public void setAutoFilter(int sheetIndex, String sheetName,
            AutoFilter autoFilter) {
        currentWorksheet.setAutoFilter(autoFilter);
    }
    
    public Workbook getWorkbook() {
        return currentWorkbook;
    }

}
