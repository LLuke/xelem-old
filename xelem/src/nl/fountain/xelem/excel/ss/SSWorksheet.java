/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import java.util.Collection;

import nl.fountain.xelem.CellPointer;
import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.AutoFilter;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.WorksheetOptions;
import nl.fountain.xelem.excel.x.XAutoFilter;
import nl.fountain.xelem.excel.x.XWorksheetOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An implementation of the XLElement Worksheet.
 */
public class SSWorksheet extends AbstractXLElement implements Worksheet {
    
    private CellPointer cellPointer;
    private String name;
    private boolean protect;
    private boolean righttoleft;
    private Table table;
    private WorksheetOptions options;
    private AutoFilter autoFilter;
    
    /**
     * Constructs a new SSWorksheet with the given name.
     * The worksheets cellpointer will be at position row 1, column 1.
     * 
     * @see nl.fountain.xelem.excel.Workbook#addSheet()
     */
    public SSWorksheet(String name) {
        this.name = name;
        cellPointer = new CellPointer();
    }

    public String getName() {
        return name;
    }
    
    public void setProtected(boolean p) {
        protect = p;
    }
    
    public void setRightToLeft(boolean r) {
        righttoleft = r;
    }
    
    public boolean hasWorksheetOptions() {
        return options != null;
    }

    public WorksheetOptions getWorksheetOptions() {
        if (options == null) {
            options = new XWorksheetOptions();
        }
        return options;
    }

    public Table getTable() {
        if (table == null) {
            table = new SSTable();
        }
        return table;
    }
    
    public boolean hasTable() {
        return table != null;
    }
    
    protected void doS() {
        
    }
    
    public CellPointer getCellPointer() {
        return cellPointer;
    }

    public Cell addCell() {
        return addCell(new SSCell());
    }
    
    public Cell addCell(Cell cell) {
        boolean withinSheet = cellPointer.isWithinSheet();
        int prevRow = cellPointer.getRowIndex();
        int prevColumn = cellPointer.getColumnIndex();
        cellPointer.move();
        if (withinSheet) {
            getTable().getRow(prevRow, cellPointer).addCellAt(prevColumn, cell);
        } else {
            throw new IndexOutOfBoundsException("rowIndex = "
                    + prevRow + ", columnIndex = " + prevColumn);
        }       
        return cell;
    }
    
    public Cell addCell(Object data) {
        Cell cell = addCell();
        cell.setData(data);
        return cell;
    }
    
    public Cell addCell(Object data, String styleID) {
        Cell cell = addCell(data);
        cell.setStyleID(styleID);
        return cell;
    }
    
    public Cell addCell(double data) {
        Cell cell = addCell();
        cell.setData(data);
        return cell;
    }
    
    public Cell addCell(double data, String styleID) {
        Cell cell = addCell(data);
        cell.setStyleID(styleID);
        return cell;
    }
    
    public Cell addCell(int data) {
        Cell cell = addCell();
        cell.setData(data);
        return cell;
    }
    
    public Cell addCell(int data, String styleID) {
        Cell cell = addCell(data);
        cell.setStyleID(styleID);
        return cell;
    }

    public Cell addCellAt(int rowIndex, int columnIndex) {
        cellPointer.moveTo(rowIndex, columnIndex);
        return addCell();
    }
    
    public Cell addCellAt(int rowIndex, int columnIndex, Cell cell) {
        cellPointer.moveTo(rowIndex, columnIndex);
        return addCell(cell);
    }
    
    public Cell getCellAt(int rowIndex, int columnIndex) {
        Row row = getTable().getRow(rowIndex);
        if (row == null) return null;
        return row.getCellAt(columnIndex);
    }

    public Cell removeCellAt(int rowIndex, int columnIndex) {
        Row row = getTable().getRow(rowIndex);
        if (row == null) return null;
        return row.removeCellAt(columnIndex);
    }

    public Row addRow() {
        return getTable().addRow(cellPointer);
    }

    public Row addRow(int rowIndex) {
        return getTable().addRow(rowIndex, cellPointer);
    }

    public Row addRow(Row row) {
        return getTable().addRow(row, cellPointer);
    }
    
    public Row addRow(int index, Row row) {
        return getTable().addRow(index, row, cellPointer);
    }

    public Row removeRow(int rowIndex) {
        return getTable().removeRow(rowIndex);
    }

    public Collection getRows() {
        return getTable().getRows();
    }

    public Row getRow(int rowIndex) {
        return getTable().getRow(rowIndex);
    }
    
    public Column addColumn() {
        return getTable().addColumn(cellPointer);
    }
    
    public Column addColumn(int index) {
        return getTable().addColumn(index, cellPointer);
    }
    
    public Column addColumn(Column column) {
        return getTable().addColumn(column, cellPointer);
    }
    
    public Column addColumn(int index, Column column) {
        return getTable().addColumn(index, column, cellPointer);
    }
    
    public Column removeColumn(int columnIndex) {
        return getTable().removeColumn(columnIndex);
    }
    
    public Collection getColumns() {
        return getTable().getColumns();
    }
    
    public Column getColumn(int columnIndex) {
        return getTable().getColumn(columnIndex);
    }
    
    public String getTagName() {
        return "Worksheet";
    }
    
    public String getNameSpace() {
        return XMLNS_SS;
    }
    
    public String getPrefix() {
        return PREFIX_SS;
    }
    
    public void setAutoFilter(String rcString) {
        autoFilter = new XAutoFilter();
        autoFilter.setRange(rcString);
    }
    
    public boolean hasAutoFilter() {
        return autoFilter != null;
    }
    
    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element wse = assemble(doc, gio);
        
        wse.setAttributeNodeNS(createAttributeNS(doc, "Name", getName()));
        if (protect) wse.setAttributeNodeNS(
                createAttributeNS(doc, "Protected", "1"));
        if (righttoleft) wse.setAttributeNodeNS(
                createAttributeNS(doc, "RightToLeft", "1"));
        
        parent.appendChild(wse);
        
        if (hasTable()) {
            getTable().assemble(wse, gio);
        }
        if (hasWorksheetOptions()) {
            getWorksheetOptions().assemble(wse, gio);
        }
        if (hasAutoFilter()) {
            autoFilter.assemble(wse, gio);
        }
        return wse;
    }

}
