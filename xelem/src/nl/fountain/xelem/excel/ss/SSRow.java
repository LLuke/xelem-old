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
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class SSRow extends AbstractXLElement implements Row {

    private TreeMap cells;
    private int idx;
    private String styleID;
    private double height;
    private int span;
    private boolean hidden;
    //private boolean autoFitHeight;
    
    public SSRow() {
        cells = new TreeMap();
    }
    
    public void setStyleID(String id) {
        styleID = id;
    }
    
    public String getStyleID() {
        return styleID;
    }
    
    // @see nl.fountain.xelem.excel.ss.Row#setHeight(double)
    public void setHeight(double h) {
        height = h;
    }
    
    // @see nl.fountain.xelem.excel.ss.Row#setSpan(int)
    public void setSpan(int s) {
        span = s;
    }
    
    // @see nl.fountain.xelem.excel.ss.Row#setHidden(boolean)
    public void setHidden(boolean hide) {
        hidden = hide;
    }
    
//    // @see nl.fountain.xelem.excel.Row#setAutoFitHeight(boolean)
//    public void setAutoFitHeight(boolean autoFit) {
//        autoFitHeight = autoFit;
//    }

    // @see nl.fountain.xelem.std.ss.Row#addCell()
    public Cell addCell() {
        return addCellAt(0, new SSCell());
    }
    
    public Cell addCell(Object data) {
        Cell cell = new SSCell();
        cell.setData(data);
        return addCellAt(0, cell);
    }
    
    public Cell addCell(Object data, String styleID) {
        Cell cell = new SSCell();
        cell.setData(data);
        cell.setStyleID(styleID);
        return addCellAt(0, cell);
    }
    
    public Cell addCell(double data) {
        Cell cell = new SSCell();
        cell.setData(data);
        return addCellAt(0, cell);
    }
    
    public Cell addCell(double data, String styleID) {
        Cell cell = new SSCell();
        cell.setData(data);
        cell.setStyleID(styleID);
        return addCellAt(0, cell);
    }
    
    // @see nl.fountain.xelem.excel.ss.Row#addCell(int)
    public Cell addCell(int data) {
        Cell cell = new SSCell();
        cell.setData(data);
        return addCellAt(0, cell);
    }
    
    // @see nl.fountain.xelem.excel.ss.Row#addCell(int, java.lang.String)
    public Cell addCell(int data, String styleID) {
        Cell cell = new SSCell();
        cell.setData(data);
        cell.setStyleID(styleID);
        return addCellAt(0, cell);
    }

    // @see nl.fountain.xelem.std.ss.Row#addCell(int)
    public Cell addCellAt(int index) {
        return addCellAt(index, new SSCell());
    }
    
    // @see nl.fountain.xelem.std.ss.Row#addCell(nl.fountain.xelem.std.ss.Cell)
    public Cell addCell(Cell cell) {
        return addCellAt(0, cell);
    }
    
    // @see nl.fountain.xelem.std.ss.Row#addCell(nl.fountain.xelem.std.ss.Cell)
    public Cell addCellAt(int index, Cell cell) {
        if (index < 1) index = maxCellIndex() + 1;
        cells.put(new Integer(index), cell);
        return cell;
    }
    
    public Cell removeCellAt(int index) {
       return (Cell) cells.remove(new Integer(index)); 
    }

    // @see nl.fountain.xelem.std.ss.Row#getCells()
    public Collection getCells() {
        return cells.values();
    }

    // @see nl.fountain.xelem.std.ss.Row#getCell(int)
    public Cell getCellAt(int index) {
        return (Cell) cells.get(new Integer(index));
    }
    
    public int size() {
       return cells.size(); 
    }
    
    public Map getCellMap() {
        return cells;
    }
      
    public int maxCellIndex() {
        int lastKey;
        if (cells.size() == 0) {
            lastKey = 0;
        } else {
            lastKey = ((Integer)cells.lastKey()).intValue();
        }
        return lastKey;
    }
    
    protected void setIndex(int index) {
        idx = index;
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getTagName()
    public String getTagName() {
        return "Row";
    }

    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS_SS;
    }

    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_SS;
    }

    public Iterator cellIterator() {
        return new CellIterator();
    }
    
    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element re = assemble(doc, gio);
        
        if (idx != 0) re.setAttributeNodeNS(
                createAttributeNS(doc, "Index", idx));
        if (getStyleID() != null) {
            re.setAttributeNodeNS(createAttributeNS(doc, "StyleID", getStyleID()));
            gio.addStyleID(getStyleID());
        }
        if (span > 0) re.setAttributeNodeNS(
                createAttributeNS(doc, "Span", span));
        if (height > 0.0) re.setAttributeNodeNS(
                createAttributeNS(doc, "Height", "" + height));
        if (hidden) re.setAttributeNodeNS(
                createAttributeNS(doc, "Hidden", "1"));
//        if (autoFitHeight) re.setAttributeNodeNS(
//                createAttributeNS(doc, "AutoFitHeight", "1"));
        
        parent.appendChild(re);
        
        Iterator iter = cellIterator();
        while (iter.hasNext()) {
            Cell cell = (Cell) iter.next();
            cell.assemble(re, gio);
        }
        
        return re;
    }
    
    /////////////////////////////////////////////
    private class CellIterator implements Iterator {
        
        private Iterator cit;
        private Integer current;
        private int prevIndex;
        
        protected CellIterator() {
            cit = cells.keySet().iterator();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return cit.hasNext();
        }

        public Object next() {
            current = (Integer) cit.next();
            int curIndex = current.intValue();
            SSCell c = (SSCell) cells.get(current);
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
