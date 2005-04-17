/*
 * Created on 16-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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


public class DefaultExcelReaderFilter implements ExcelReaderFilter {
    
    private List listeners;

    public List getListeners() {
        if (listeners == null) {
            listeners = new ArrayList();
        }
        return listeners;
    }

    public void addExcelReaderListener(ExcelReaderListener listener) {
        if (!getListeners().contains(listener)) {
            getListeners().add(listener);
        }
    }

    public boolean removeExcelReaderListener(ExcelReaderListener listener) {
        return getListeners().remove(listener);
    }

    public void clearExcelReaderListeners() {
        getListeners().clear();
    }

    public void startDocument() {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startDocument();
        }
    }

    public void processingInstruction(String target, String data) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.processingInstruction(target, data);
        }
    }

    public void startWorkbook(String systemID) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startWorkbook(systemID);
        }
    }

    public void setDocumentProperties(DocumentProperties docProps) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setDocumentProperties(docProps);
        }
    }

    public void setExcelWorkbook(ExcelWorkbook excelWb) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setExcelWorkbook(excelWb);
        }
    }

    public void setNamedRange(NamedRange namedRange) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setNamedRange(namedRange);
        }
    }

    public void startWorksheet(int sheetIndex, Worksheet sheet) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startWorksheet(sheetIndex, sheet);
        }
    }

    public void setNamedRange(int sheetIndex, String sheetName,
            NamedRange namedRange) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setNamedRange(sheetIndex, sheetName, namedRange);
        }
    }

    public void startTable(int sheetIndex, String sheetName, Table table) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startTable(sheetIndex, sheetName, table);
        }
    }

    public void setColumn(int sheetIndex, String sheetName, Column column) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setColumn(sheetIndex, sheetName, column);
        }
    }

    public void setRow(int sheetIndex, String sheetName, Row row) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setRow(sheetIndex, sheetName, row);
        }
    }

    public void setCell(int sheetIndex, String sheetName, int rowIndex,
            Cell cell) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setCell(sheetIndex, sheetName, rowIndex, cell);
        }
    }

    public void setWorksheetOptions(int sheetIndex, String sheetName,
            WorksheetOptions wsOptions) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setWorksheetOptions(sheetIndex, sheetName, wsOptions);
        }
    }

    public void setAutoFilter(int sheetIndex, String sheetName,
            AutoFilter autoFilter) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setAutoFilter(sheetIndex, sheetName, autoFilter);
        }
    }

    public void endDocument(Map prefixMap) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.endDocument(prefixMap);
        }
    }

}
