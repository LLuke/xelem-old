/*
 * Created on 16-apr-2005
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

/**
 * Passes all events, values and instances unfiltered. It's up to the subclasses
 * of this class to take appropriate filtering action.
 * 
 * @since xelem.2.0
 */
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
    
    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void startDocument() {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startDocument();
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void processingInstruction(String target, String data) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.processingInstruction(target, data);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void startWorkbook(String systemID) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startWorkbook(systemID);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setDocumentProperties(DocumentProperties docProps) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setDocumentProperties(docProps);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setExcelWorkbook(ExcelWorkbook excelWb) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setExcelWorkbook(excelWb);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setNamedRange(NamedRange namedRange) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setNamedRange(namedRange);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void startWorksheet(int sheetIndex, Worksheet sheet) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startWorksheet(sheetIndex, sheet);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setNamedRange(int sheetIndex, String sheetName,
            NamedRange namedRange) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setNamedRange(sheetIndex, sheetName, namedRange);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void startTable(int sheetIndex, String sheetName, Table table) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.startTable(sheetIndex, sheetName, table);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setColumn(int sheetIndex, String sheetName, Column column) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setColumn(sheetIndex, sheetName, column);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setRow(int sheetIndex, String sheetName, Row row) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setRow(sheetIndex, sheetName, row);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setCell(int sheetIndex, String sheetName, int rowIndex,
            Cell cell) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setCell(sheetIndex, sheetName, rowIndex, cell);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setWorksheetOptions(int sheetIndex, String sheetName,
            WorksheetOptions wsOptions) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setWorksheetOptions(sheetIndex, sheetName, wsOptions);
        }
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void setAutoFilter(int sheetIndex, String sheetName,
            AutoFilter autoFilter) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.setAutoFilter(sheetIndex, sheetName, autoFilter);
        }
    }
    
    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void endWorksheet(int sheetIndex, String sheetName) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.endWorksheet(sheetIndex, sheetName);
        } 
    }

    /**
     * Passes the event unfiltered to it's listeners. 
     * Subclasses can override this method and take appropriate action.
     */
    public void endDocument(Map prefixMap) {
        for (Iterator iter = getListeners().iterator(); iter.hasNext();) {
            ExcelReaderListener listener = (ExcelReaderListener) iter.next();
            listener.endDocument(prefixMap);
        }
    }

}