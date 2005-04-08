/*
 * Created on 8-apr-2005
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


public class DefaultExcelReaderListener implements ExcelReaderListener {

    public void startDocument() {
    }

    public void processingInstruction(String target, String data) {
    }

    public void startWorkbook(String systemID, String workbookName) {
    }

    public void setDocumentProperties(DocumentProperties docProps) {
    }

    public void setExcelWorkbook(ExcelWorkbook excelWb) {
    }

    public void setNamedRange(NamedRange namedRange) {
    }

    public void startWorksheet(int sheetIndex, Worksheet sheet) {
    }

    public void setNamedRange(int sheetIndex, String sheetName,
            NamedRange namedRange) {
    }

    public void startTable(int sheetIndex, String sheetName, Table table) {
    }

    public void setColumn(int sheetIndex, String sheetName, Column column) {
    }

    public void setRow(int sheetIndex, String sheetName, Row row) {
    }

    public void setCell(int sheetIndex, String sheetName, int rowIndex,
            Cell cell) {
    }

    public void setWorksheetOptions(int sheetIndex, String sheetName,
            WorksheetOptions wsOptions) {
    }

    public void setAutoFilter(int sheetIndex, String sheetName,
            AutoFilter autoFilter) {
    }

    public void endDocument(Map prefixMap) {
    }

}
