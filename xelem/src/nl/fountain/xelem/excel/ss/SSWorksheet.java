/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.AutoFilter;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.WorksheetOptions;
import nl.fountain.xelem.excel.x.XAutoFilter;
import nl.fountain.xelem.excel.x.XWorksheetOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class SSWorksheet extends AbstractXLElement implements Worksheet {
    
    private String name;
    private boolean protect;
    private boolean righttoleft;
    private Table table;
    private WorksheetOptions options;
    private AutoFilter autoFilter;
    
    public SSWorksheet(String name) {
        this.name = name;
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#getName()
    public String getName() {
        return name;
    }
    
    // @see nl.fountain.xelem.excel.ss.Worksheet#setProtected(boolean)
    public void setProtected(boolean p) {
        protect = p;
    }
    
    // @see nl.fountain.xelem.excel.ss.Worksheet#setRightToLeft(boolean)
    public void setRightToLeft(boolean r) {
        righttoleft = r;
    }
    
    // @see nl.fountain.xelem.excel.ss.Worksheet#addWorksheetOptions()
    public WorksheetOptions addWorksheetOptions() {
        options = new XWorksheetOptions();
        return options;
    }

    // @see nl.fountain.xelem.excel.ss.Worksheet#addWorksheetOptions(nl.fountain.xelem.excel.x.WorksheetOptions)
    public WorksheetOptions addWorksheetOptions(WorksheetOptions wso) {
        options = wso;
        return options;
    }

    // @see nl.fountain.xelem.excel.ss.Worksheet#hasWorksheetOptions()
    public boolean hasWorksheetOptions() {
        return options != null;
    }

    // @see nl.fountain.xelem.excel.ss.Worksheet#getWorksheetOptions()
    public WorksheetOptions getWorksheetOptions() {
        return options;
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#addTable()
    public Table addTable() {
        table = new SSTable();
        return table;
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#getTable()
    public Table getTable() {
        if (table == null) {
            table = addTable();
        }
        return table;
    }
    
    // @see nl.fountain.xelem.excel.ss.Worksheet#hasTable()
    public boolean hasTable() {
        return table != null;
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#addCell()
    public Cell addCell() {
        return getTable().currentRow().addCell();
    }
    
    public Cell addCell(Object data) {
        return getTable().currentRow().addCell(data);
    }
    
    public Cell addCell(Object data, String styleID) {
        return getTable().currentRow().addCell(data, styleID);
    }
    
    public Cell addCell(double data) {
        return getTable().currentRow().addCell(data);
    }
    
    public Cell addCell(double data, String styleID) {
        return getTable().currentRow().addCell(data, styleID);
    }
    
    // @see nl.fountain.xelem.excel.ss.Worksheet#addCell(int)
    public Cell addCell(int data) {
        return getTable().currentRow().addCell(data);
    }
    
    // @see nl.fountain.xelem.excel.ss.Worksheet#addCell(int, java.lang.String)
    public Cell addCell(int data, String styleID) {
        return getTable().currentRow().addCell(data, styleID);
    }
    
    // @see nl.fountain.xelem.std.ss.Worksheet#addCell(nl.fountain.xelem.std.ss.Cell)
    public Cell addCell(Cell cell) {
        return getTable().currentRow().addCell(cell);
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#addCell(int, int)
    public Cell addCellAt(int rowIndex, int columnIndex) {
        Row row = getTable().getRow(rowIndex);
        if (row == null) row = getTable().addRow(rowIndex);
        return row.addCellAt(columnIndex);
    }
    
    // @see nl.fountain.xelem.std.ss.Worksheet#addCell(int, int, nl.fountain.xelem.std.ss.Cell)
    public Cell addCellAt(int rowIndex, int columnIndex, Cell cell) {
        Row row = getTable().getRow(rowIndex);
        if (row == null) row = getTable().addRow(rowIndex);
        return row.addCellAt(columnIndex, cell);
    }
    
    // @see nl.fountain.xelem.std.ss.Worksheet#getCell(int, int)
    public Cell getCellAt(int rowIndex, int columnIndex) {
        Row row = getTable().getRow(rowIndex);
        if (row == null) return null;
        return row.getCellAt(columnIndex);
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#removeCell(int, int)
    public Cell removeCellAt(int rowIndex, int columnIndex) {
        Row row = getTable().getRow(rowIndex);
        if (row == null) return null;
        return row.removeCellAt(columnIndex);
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#getCells()
    public Collection getCells() {
        Collection cells = new ArrayList();
        for (Iterator iter = getRows().iterator(); iter.hasNext();) {
            Row row = (Row) iter.next();
            for (Iterator iterator = row.getCells().iterator(); iterator.hasNext();) {
                cells.add(iterator.next());               
            }
        }
        return cells;
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#addRow()
    public Row addRow() {
        return getTable().addRow();
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#addRow(int)
    public Row addRow(int rowIndex) {
        return getTable().addRow(rowIndex);
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#addRow(nl.fountain.xelem.std.ss.Row)
    public Row addRow(Row row) {
        return getTable().addRow(row);
    }
    
    // @see nl.fountain.xelem.std.ss.Worksheet#addRow(int, nl.fountain.xelem.std.ss.Row)
    public Row addRow(int index, Row row) {
        return getTable().addRow(index, row);
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#removeRow(int)
    public Row removeRow(int rowIndex) {
        return getTable().removeRow(rowIndex);
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#getRows()
    public Collection getRows() {
        return getTable().getRows();
    }

    // @see nl.fountain.xelem.std.ss.Worksheet#getRow(int)
    public Row getRow(int rowIndex) {
        return getTable().getRow(rowIndex);
    }
    
    public Row currentRow() {
        return getTable().currentRow();
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getTagName()
    public String getTagName() {
        return "Worksheet";
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS_SS;
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_SS;
    }
    
    // @see nl.fountain.xelem.excel.Worksheet#setAutoFilter(java.lang.String)
    public void setAutoFilter(String rcString) {
        autoFilter = new XAutoFilter();
        autoFilter.setRange(rcString);
    }
    
    // @see nl.fountain.xelem.excel.Worksheet#hasAutoFilter()
    public boolean hasAutoFilter() {
        return autoFilter != null;
    }
    
    // @see nl.fountain.xelem.excel.ss.Worksheet#assemble(org.w3c.dom.Document, org.w3c.dom.Node, nl.fountain.xelem.excel.GIO)
    public Element assemble(Document doc, Element parent, GIO gio) {
        Element wse = assemble(doc, gio);
        
        wse.setAttributeNodeNS(createAttributeNS(doc, "Name", getName()));
        if (protect) wse.setAttributeNodeNS(
                createAttributeNS(doc, "Protected", "1"));
        if (righttoleft) wse.setAttributeNodeNS(
                createAttributeNS(doc, "RightToLeft", "1"));
        
        parent.appendChild(wse);
        
        if (hasTable()) {
            getTable().assemble(doc, wse, gio);
        }
        if (hasWorksheetOptions()) {
            getWorksheetOptions().assemble(doc, wse, gio);
        }
        if (hasAutoFilter()) {
            autoFilter.assemble(doc, wse, gio);
        }
        return wse;
    }

}
