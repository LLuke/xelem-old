/*
 * Created on 6-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Workbook;

/**
 *
 */
public interface ExcelReaderListener {
    
    public void setWorkbook(String systemID, String workbookName);
    
    public void setDocumentProperties(DocumentProperties docprops);
    
    public void setExcelWorkbook(ExcelWorkbook xlwb);
    
    public void setNamedRange(Workbook workbook, NamedRange nr);

}
