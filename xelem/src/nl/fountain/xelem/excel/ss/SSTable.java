/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Worksheet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An implementation of the XLElement Table.
 */
public class SSTable extends AbstractXLElement implements Table {
    
    private TreeMap columns;
    private TreeMap rows;
    private String styleID;
    private double rowheight;
    private double columnwidth;

    /**
     * Constructs a new SSTable.
     * 
     * @see nl.fountain.xelem.excel.Worksheet#getTable()
     */
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
    
    public void setDefaultRowHeight(double points) {
        rowheight = points;
    }
    
    public void setDefaultColumnWidth(double points) {
        columnwidth = points;
    }
    
    public Column addColumn() {
        return addColumnAt(maxColumnIndex() + 1, new SSColumn());
    }
    
    public Column addColumnAt(int index) {
        return addColumnAt(index, new SSColumn());
    }
    
    public Column addColumn(Column column) {
        return addColumnAt(maxColumnIndex() + 1, column);
    }
    
    public Column addColumnAt(int index, Column column) {
        if (index < Worksheet.firstColumn || index > Worksheet.lastColumn) {
            throw new IndexOutOfBoundsException("columnIndex = " + index);
        }
        columns.put(new Integer(index), column);
        return column;
    }
    
    public Column removeColumnAt(int columnIndex) {
        return (Column) columns.remove(new Integer(columnIndex));
    }
    
    public Column getColumnAt(int columnIndex) {
        Column column = (Column) columns.get(new Integer(columnIndex));
        if (column == null) {
            column = addColumnAt(columnIndex);
        }
        return column;
    }
    
    public boolean hasColumnAt(int index) {
        return columns.get(new Integer(index)) != null;
    }
    
    public Collection getColumns() {
        return columns.values();
    }

    public Row addRow() {
        return addRowAt(maxRowIndex() + 1, new SSRow());
    }

    public Row addRowAt(int index) {
        return addRowAt(index, new SSRow());
    }

    public Row addRow(Row row) {
        return addRowAt(maxRowIndex() + 1, row);
    }
    
    public Row addRowAt(int index, Row row) {
        if (index < Worksheet.firstRow || index > Worksheet.lastRow) {
            throw new IndexOutOfBoundsException("rowIndex = " + index);
        }
        rows.put(new Integer(index), row);
        return row;
    }

    public Row removeRowAt(int rowIndex) {
        Row row = (Row) rows.remove(new Integer(rowIndex));
        return row;
    }

    public Collection getRows() {
        return rows.values();
    }
    
    public TreeMap getRowMap() {
        return rows;
    }

    public Row getRowAt(int rowIndex) {
        Row row = (Row) rows.get(new Integer(rowIndex));
        if (row == null) {
            row = addRowAt(rowIndex);
        }
        return row;
    }
    
    public boolean hasRowAt(int index) {
        return rows.get(new Integer(index)) != null;
    }
    
//    public Row getRow(int index) {
//        return (Row) rows.get(new Integer(index));
//    }

    public int rowCount() {
        return rows.size();
    }
    
    public int columnCount() {
        return columns.size();
    }
    
    public boolean hasChildren() {
        return (columns.size() + rows.size()) > 0;
    }
    
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
    
    public Iterator columnIterator() {
        return new ColumnIterator();
    }
    
    public String getTagName() {
        return "Table";
    }
    
    public String getNameSpace() {
        return XMLNS_SS;
    }
    
    public String getPrefix() {
        return PREFIX_SS;
    } 
    
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
