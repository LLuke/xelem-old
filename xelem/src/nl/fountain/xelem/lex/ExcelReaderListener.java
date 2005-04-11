/*
 * Created on 6-apr-2005
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
 * Recieve notification of parsing events and the construction of 
 * {@link nl.fountain.xelem.excel.XLElement XLElements}.
 * A registered ExcelReaderListener listens to events fired by {@link ExcelReader}
 * while the last object is reading xml-spreadsheets of type spreadsheetML.
 * 
 * @since xelem.2.0
 */
public interface ExcelReaderListener {
    
    /**
     * Recieve notification of the start of the document.
     */
    void startDocument();
    
    /**
     * Recieve notification of processing instruction.
     * 
     * @param target 	the target of the processing instruction
     * @param data		the data of the processing instruction
     */
    void processingInstruction(String target, String data);
    
    /**
     * Recieve notification of the start of the Workbook tag.
     * 
     * @param systemID the systemID or "source" if no systemID was encountered
     * @param workbookName	the name of the workbook or "source" 
     * 	if no systemID was encountered
     */
    void startWorkbook(String systemID, String workbookName);
    
    /**
     * Recieve notification of the the construction of DocumentProperties.
     */
    void setDocumentProperties(DocumentProperties docProps);
    
    /**
     * Recieve notification of the the construction of ExcelWorkbook.
     */
    void setExcelWorkbook(ExcelWorkbook excelWb);
    
    /**
     * Recieve notification of the the construction of a NamedRange on the 
     * workbook level.
     */
    void setNamedRange(NamedRange namedRange);
    
    /**
     * 
     */
    void startWorksheet(int sheetIndex, Worksheet sheet);
    
    /**
     * Recieve notification of the the construction of a NamedRange on the 
     * worksheet level.
     */
    void setNamedRange(int sheetIndex, String sheetName, NamedRange namedRange);
    
    void startTable(int sheetIndex, String sheetName, Table table);
    
    void setColumn(int sheetIndex, String sheetName, Column column);
    
    /**
     * Recieve notification of the the construction of a Row. The row is fully
     * populated, it's row index has been set and can be obtained by
     * {@link nl.fountain.xelem.excel.Row#getIndex() row.getIndex()}.
     * 
     * @param sheetIndex	the index of the worksheet (0-based)
     * @param sheetName		the name of the worksheet where the row was found
     * @param row 			fully populated row implementation
     */
    void setRow(int sheetIndex, String sheetName, Row row);
    
    void setCell(int sheetIndex, String sheetName, int rowIndex, Cell cell);
    
    void setWorksheetOptions(int sheetIndex, String sheetName, WorksheetOptions wsOptions);
    
    void setAutoFilter(int sheetIndex, String sheetName, AutoFilter autoFilter);
    
    /**
     * Recieve notification of the end of the document.
     * 
     * @param prefixMap a map of prefixes (keys) and uri's recieved while reading
     */
    void endDocument(Map prefixMap);

}
