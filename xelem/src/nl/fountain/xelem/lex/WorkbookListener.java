/*
 * Created on 8-apr-2005
 * Copyright (C) 2005  Henk van den Berg
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * see license.txt
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

/**
 * An implementation of {@link ExcelReaderListener}.
 * 
 * @since xelem.2.0
 */
public class WorkbookListener extends DefaultExcelReaderListener {
    
    private Workbook currentWorkbook;
    private Worksheet currentWorksheet;
    private Table currentTable;

    /**
     * Recieve notification of the start of the Workbook tag.
     * Creates a new {@link nl.fountain.xelem.excel.ss.XLWorkbook}.
     * The name of the workbook is set to <code>workbookName</code>.
     * The fileName of the workbook is set to <code>systemID</code>.
     * After the ExcelReader has finished reading, the workbook is populated
     * with all the {@link nl.fountain.xelem.excel.XLElement XLElements}
     * encountered during the read and can be obtained by
     * {@link #getWorkbook()}
     * 
     * @param systemID the systemID or "source" if no systemID was encountered
     * @param workbookName	the name of the workbook or "source" 
     * 	if no systemID was encountered
     */
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
