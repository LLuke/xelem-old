/*
 * Created on Nov 3, 2004
 *
 */
package nl.fountain.xelem.excel.x;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.Pane;

/**
 *
 */
public class XPane extends AbstractXLElement implements Pane {
    
    private int number;
    private int activeCol;
    private int activeRow;
    private String rangeSelection;
    
    public XPane(int paneNumber) {
        if (paneNumber < 0 || paneNumber > 3) {
            throw new IllegalArgumentException(paneNumber 
                    + ". Legal arguments are 0, 1, 2 and 3.");
        }
        number = paneNumber;
    }

    // @see nl.fountain.xelem.excel.Pane#getNumber()
    public int getNumber() {
        return number;
    }

    // @see nl.fountain.xelem.excel.Pane#setActiveCol(int)
    public void setActiveCol(int col) {
        activeCol = col - 1;
    }

    // @see nl.fountain.xelem.excel.Pane#getActiveCol()
    public int getActiveCol() {
        return activeCol;
    }

    // @see nl.fountain.xelem.excel.Pane#setActiveRow()
    public void setActiveRow(int row) {
        activeRow = row - 1;
    }

    // @see nl.fountain.xelem.excel.Pane#getActiveRow()
    public int getActiveRow() {
        return activeRow;
    }
    
    // @see nl.fountain.xelem.excel.Pane#setActiveCell(int, int)
    public void setActiveCell(int row, int col) {
        setActiveRow(row);
        setActiveCol(col);
    }

    // @see nl.fountain.xelem.excel.Pane#setRangeSelection(java.lang.String)
    public void setRangeSelection(String rc) {
        rangeSelection = rc;
    }

    // @see nl.fountain.xelem.excel.Pane#getRangeSelection()
    public String getRangeSelection() {
        return rangeSelection;
    }

    // @see nl.fountain.xelem.excel.XLElement#getTagName()
    public String getTagName() {
        return "Pane";
    }

    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS_X;
    }

    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_X;
    }

    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element pe = assemble(doc, gio);
        
        pe.appendChild(createElementNS(doc, "Number", number));
        if (activeCol > -1) pe.appendChild(
                createElementNS(doc, "ActiveCol", activeCol));
        if (activeRow > -1) pe.appendChild(
                createElementNS(doc, "ActiveRow", activeRow));
        if (rangeSelection != null) pe.appendChild(
                createElementNS(doc, "RangeSelection", rangeSelection));
        
        parent.appendChild(pe);
        return pe;
    }

}
