/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class SSTable extends AbstractXLElement implements Table {
    
    private TreeMap columns;
    private TreeMap rows;
    private Row currentRow;
    private String styleID;
    private double rowheight;
    private double columnwidth;

    
    public SSTable() {
        rows = new TreeMap();
        columns = new TreeMap();
    }
    
    public void setStyleID(String id) {
        styleID = id;
    }

    public String getStyleID() {
        return styleID;
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#setRowHeight(int)
    public void setDefaultRowHeight(double points) {
        rowheight = points;
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#setDefaultColumnWidth(double)
    public void setDefaultColumnWidth(double points) {
        columnwidth = points;
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#addColumn()
    public Column addColumn() {
        return addColumn(0, new SSColumn());
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#addColumn(int)
    public Column addColumn(int index) {
        return addColumn(index, new SSColumn());
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#addColumn(nl.fountain.xelem.excel.ss.Column)
    public Column addColumn(Column column) {
        return addColumn(0, column);
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#addColumn(int, nl.fountain.xelem.excel.ss.Column)
    public Column addColumn(int index, Column column) {
        if (index < 1) index = maxColumnIndex() + 1;
        columns.put(new Integer(index), column);
        return column;
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#removeColumn(int)
    public Column removeColumn(int columnIndex) {
        return (Column) columns.remove(new Integer(columnIndex));
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#getColumn(int)
    public Column getColumn(int columnIndex) {
        return (Column) columns.get(new Integer(columnIndex));
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#getColumns()
    public Collection getColumns() {
        return columns.values();
    }

    // @see nl.fountain.xelem.std.ss.Table#addRow()
    public Row addRow() {
        return addRow(0, new SSRow());
    }

    // @see nl.fountain.xelem.std.ss.Table#addRow(int)
    public Row addRow(int index) {
        return addRow(index, new SSRow());
    }

    // @see nl.fountain.xelem.std.ss.Table#addRow(nl.fountain.xelem.std.ss.Row)
    public Row addRow(Row row) {
        return addRow(0, row);
    }
    
    // @see nl.fountain.xelem.std.ss.Table#addRow(int, nl.fountain.xelem.std.ss.Row)
    public Row addRow(int index, Row row) {
        if (index < 1) index = maxRowIndex() + 1;
        rows.put(new Integer(index), row);
        currentRow = row;
        return row;
    }

    // @see nl.fountain.xelem.std.ss.Table#removeRow(int)
    public Row removeRow(int rowIndex) {
        Row row = (Row) rows.remove(new Integer(rowIndex));
        if (currentRow().equals(row)) {
            currentRow = null;
        }
        return row;
    }

    // @see nl.fountain.xelem.std.ss.Table#getRows()
    public Collection getRows() {
        return rows.values();
    }
    
    public Map getRowMap() {
        return rows;
    }

    // @see nl.fountain.xelem.std.ss.Table#getRow(int)
    public Row getRow(int rowIndex) {
        return (Row) rows.get(new Integer(rowIndex));
    }

    public Row currentRow() {
        if (currentRow == null) {
            currentRow = addRow();
        }
        return currentRow;
    }

    // @see nl.fountain.xelem.std.ss.Table#size()
    public int size() {
        return rows.size();
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#hasChildren()
    public boolean hasChildren() {
        return (columns.size() + rows.size()) > 0;
    }
    
    // @see nl.fountain.xelem.std.ss.Table#maxCellIndex()
    public int maxCellIndex() {
        int max = 0;
        for (Iterator iter = rows.values().iterator(); iter.hasNext();) {
            Row row = (Row) iter.next();
            if (row.maxCellIndex() > max) max = row.maxCellIndex();
        }
        return max;
    }
    
    public int maxRowIndex() {
        int lastKey;
        if (rows.size() == 0) {
            lastKey = 0;
        } else {
            lastKey = ((Integer)rows.lastKey()).intValue();
        }
        return lastKey;
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#maxColumnIndex()
    public int maxColumnIndex() {
        int lastKey;
        if (columns.size() == 0) {
            lastKey = 0;
        } else {
            lastKey = ((Integer)columns.lastKey()).intValue();
        }
        return lastKey;
    }
    
    public Iterator rowIterator() {
        return new RowIterator();
    }
    
    // @see nl.fountain.xelem.excel.ss.Table#columnIterator()
    public Iterator columnIterator() {
        return new ColumnIterator();
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getTagName()
    public String getTagName() {
        return "Table";
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS_SS;
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_SS;
    } 
    
    // @see nl.fountain.xelem.excel.ss.Table#assemble(org.w3c.dom.Document, org.w3c.dom.Node, nl.fountain.xelem.excel.GIO)
    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element te = assemble(doc, gio);
        
        if (getStyleID() != null) {
            te.setAttributeNodeNS(createAttributeNS(doc, "StyleID", getStyleID()));
            gio.addStyleID(getStyleID());
        }
        if (rowheight > 0.0) te.setAttributeNodeNS(
                createAttributeNS(doc, "DefaultRowHeight", "" + rowheight));
        if (columnwidth > 0.0) te.setAttributeNodeNS(
                createAttributeNS(doc, "DefaultColumnWidth", "" + columnwidth));  
        
        parent.appendChild(te);
        
        Iterator iter = columnIterator();
        while (iter.hasNext()) {
            Column column = (Column) iter.next();
            column.assemble(te, gio);
        }
        
        iter = rowIterator();
        while (iter.hasNext()) {
            Row row = (Row) iter.next();
            row.assemble(te, gio);
        }
        return te;
    }
    
    /////////////////////////////////////////////
    private class RowIterator implements Iterator {
        
        private Iterator rit;
        private Integer current;
        private int prevIndex;
        
        protected RowIterator() {
            rit = rows.keySet().iterator();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return rit.hasNext();
        }

        public Object next() {
            current = (Integer) rit.next();  
            int curIndex = current.intValue();
            SSRow r = (SSRow) rows.get(current);
            if (prevIndex + 1 != curIndex) {
                r.setIndex(curIndex);
            } else {
                r.setIndex(0);
            }
            prevIndex = curIndex;
            return r;
        }
        
    }
    
    private class ColumnIterator implements Iterator {
        
        private Iterator cit;
        private Integer current;
        private int prevIndex;
        private int maxSpan;
        
        protected ColumnIterator() {
            cit = columns.keySet().iterator();
        }
        
        // @see java.util.Iterator#remove()
        public void remove() {
            throw new UnsupportedOperationException();
        }

        // @see java.util.Iterator#hasNext()
        public boolean hasNext() {
            return cit.hasNext();
        }

        // @see java.util.Iterator#next()
        public Object next() {
            current = (Integer) cit.next();  
            int curIndex = current.intValue();
            SSColumn c = (SSColumn) columns.get(current);
            if (prevIndex + 1 != curIndex) {
                c.setIndex(curIndex);
            } else {
                c.setIndex(0);
            }
            prevIndex = curIndex;
            
            return c;
        }
        
    }

}
