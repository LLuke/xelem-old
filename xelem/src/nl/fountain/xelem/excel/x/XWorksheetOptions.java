/*
 * Created on Oct 15, 2004
 *
 */
package nl.fountain.xelem.excel.x;

import java.util.HashMap;
import java.util.Map;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.XLUtil;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.Pane;
import nl.fountain.xelem.excel.WorksheetOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class XWorksheetOptions extends AbstractXLElement implements WorksheetOptions {
    
    private Map panes;
    private int zoom;
    private int tabColorIndex;
    private String gridlineColor;
    private String visible;
    private boolean selected;
    private boolean noHeadings;
    private boolean noGridlines;
    private boolean displayFormulas;
    private int topRowVisible = -1;
    private int leftColumnVisible = -1;
    private int splitHorizontal;
    private int topRowBottomPane = -1;
    private int leftColumnRightPane = -1;
    private int activePane = -1;
    private int splitVertical;
    private boolean freezePanes;
    

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#setTopRowVisible(int)
    public void setTopRowVisible(int tr) {
        topRowVisible = tr;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#getTopRowVisible()
    public int getTopRowVisible() {
        return topRowVisible;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#setLeftColumnVisible(int)
    public void setLeftColumnVisible(int lc) {
        leftColumnVisible = lc;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#getLeftColumnVisible()
    public int getLeftColumnVisible() {
        return leftColumnVisible;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#setZoom(int)
    public void setZoom(int z) {
        zoom = z;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#getZoom()
    public int getZoom() {
        return zoom;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#setTabColorIndex(int)
    public void setTabColorIndex(int ci) {
        tabColorIndex = ci;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#getTabColorIndex()
    public int getTabColorIndex() {
        return tabColorIndex;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#setSelected(boolean)
    public void setSelected(boolean s) {
        selected = s;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#isSelected()
    public boolean isSelected() {
        return selected;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#doNotDisplayHeadings(boolean)
    public void doNotDisplayHeadings(boolean b) {
        noHeadings = b;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#displaysNoHeadings()
    public boolean displaysNoHeadings() {
        return noHeadings;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#doNotDisplayGridlines(boolean)
    public void doNotDisplayGridlines(boolean b) {
        noGridlines = b;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#displaysNoGridlines()
    public boolean displaysNoGridlines() {
        return noGridlines;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#doDisplayFormulas(boolean)
    public void doDisplayFormulas(boolean f) {
        displayFormulas = f;
    }

    // @see nl.fountain.xelem.excel.x.WorksheetOptions#displaysFormulas()
    public boolean displaysFormulas() {
        return displayFormulas;
    }
    
    // @see nl.fountain.xelem.excel.x.WorksheetOptions#setVisible(boolean)
    public void setVisible(String wsoValue) {
        if (SHEET_HIDDEN.equals(wsoValue)
                || SHEET_VERY_HIDDEN.equals(wsoValue)
                || SHEET_VISIBLE.equals(wsoValue)
                || wsoValue == null) {
            visible = wsoValue;
        } else {
            throw new IllegalArgumentException(wsoValue +
                    ". Should be one of WorksheetOptions.SHEET_xxx values.");
        }
    }
    
    /**
     * @throws java.lang.IllegalArgumentException if r < 1 or c < 1.
     */
    public void setActiveCell(int r, int c) {
        if (r < 1) {
            throw new IllegalArgumentException(r + ". Can't be less than 1.");
        }
        if (c < 1) {
            throw new IllegalArgumentException(c + ". Can't be less than 1.");
        }
        getPane(3).setActiveCell(r, c);
    }
    
    /**
     * @throws java.lang.IllegalArgumentException if paneNumber < 0 or
     * 			paneNumber > 3.
     * @throws java.lang.IllegalArgumentException if r < 1 or c < 1.
     */
    public void setActiveCell(int paneNumber, int r, int c) {
        getPane(paneNumber).setActiveCell(r, c);
        activePane = paneNumber;
    }
    
    /**
     * @throws java.lang.IllegalArgumentException if topRow < 1.
     */
    public void splitHorizontal(int points, int topRow) {
        if (topRow < 1) {
            throw new IllegalArgumentException(topRow + ". Can't be less than 1.");
        }
        splitHorizontal = points;
        topRowBottomPane = topRow - 1;
    }
    
    /**
     * @throws java.lang.IllegalArgumentException if leftColumn < 1.
     */
    public void splitVertical(int points, int leftColumn) {
        if (leftColumn < 1) {
            throw new IllegalArgumentException(leftColumn + ". Can't be less than 1.");
        }
        splitVertical = points;
        leftColumnRightPane = leftColumn - 1;
    }
    
    // @see nl.fountain.xelem.excel.WorksheetOptions#freezePanes(int, int)
    public void freezePanesAt(int row, int column) {
        freezePanes = true;
        splitHorizontal = row;
        topRowBottomPane = row;
        splitVertical = column;
        leftColumnRightPane = column;
        if (row > 0 && column > 0) {
            activePane = Pane.BOTTOM_RIGHT;
        } else if ( row > 0) {
            activePane = Pane.BOTTOM_LEFT;
        }       
    }
    
    
    // @see nl.fountain.xelem.excel.x.WorksheetOptions#setGridlineColorIndex(int)
    public void setGridlineColor(int r, int g, int b) {
        gridlineColor = XLUtil.convertToHex(r, g , b);
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getTagName()
    public String getTagName() {
        return "WorksheetOptions";
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS_X;
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_X;
    }
    
    /**
     * @return the newly assembled element
     */
    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element wsoe = assemble(doc, gio);
        
        parent.appendChild(wsoe);
        
        if (isSelected()) {
            wsoe.appendChild(createElementNS(doc, "Selected"));
            gio.increaseSelectedSheets();
        }
        if (displaysNoHeadings()) wsoe.appendChild(
                createElementNS(doc, "DoNotDisplayHeadings"));
        if (displaysNoGridlines()) wsoe.appendChild(
                createElementNS(doc, "DoNotDisplayGridlines"));
        if (displaysFormulas()) wsoe.appendChild(
                createElementNS(doc, "DisplayFormulas"));
        if (topRowVisible > -1) wsoe.appendChild(
                createElementNS(doc, "TopRowVisible", topRowVisible));
        if (leftColumnVisible > -1) wsoe.appendChild(
                createElementNS(doc, "LeftColumnVisible", leftColumnVisible));
        if (zoom > 0) wsoe.appendChild(
                createElementNS(doc, "Zoom", zoom));
        if (tabColorIndex != 0) wsoe.appendChild(
                createElementNS(doc, "TabColorIndex", tabColorIndex));
        if (gridlineColor != null) wsoe.appendChild(
                createElementNS(doc, "GridlineColor", gridlineColor));
        if (visible != null) wsoe.appendChild(
                createElementNS(doc, "Visible", visible));
        if (freezePanes) {
            wsoe.appendChild(createElementNS(doc, "FreezePanes"));
            wsoe.appendChild(createElementNS(doc, "FrozenNoSplit"));
        }
        
        boolean splitH = false;
        if (splitHorizontal > 0 || topRowBottomPane > -1) {
            splitH = true;
            wsoe.appendChild(createElementNS(
                    doc, "SplitHorizontal", splitHorizontal));
            wsoe.appendChild(createElementNS(
                    doc, "TopRowBottomPane", topRowBottomPane));
        }
        
        boolean splitV = false;
        if (splitVertical > 0 || leftColumnRightPane > -1) {
            splitV = true;
            wsoe.appendChild(createElementNS(
                    doc, "SplitVertical", splitVertical));
            wsoe.appendChild(createElementNS(
                    doc, "LeftColumnRightPane", leftColumnRightPane));
        }
        
        if (activePane == 3
                || (splitH && activePane == 2)
                || (splitV && activePane == 1)
                || (splitH && splitV && activePane == 0)) {
            wsoe.appendChild(createElementNS(doc, "ActivePane", activePane));
        }
        
        if (panes != null) {            
            Element panesE = createElementNS(doc, "Panes");
            wsoe.appendChild(panesE);
            
            Pane pane3 = (Pane) panes.get(new Integer(3));
            if (pane3 != null) {
                pane3.assemble(panesE, gio);
            }
            
            Pane pane2 = (Pane) panes.get(new Integer(2));
            if (pane2 != null && splitH) {
                pane2.assemble(panesE, gio);
            }
            
            Pane pane1 = (Pane) panes.get(new Integer(1));
            if (pane1 != null && splitV) {
                pane1.assemble(panesE, gio);
            }
            
            Pane pane0 = (Pane) panes.get(new Integer(0));
            if (pane0 != null && splitH && splitV) {
                pane0.assemble(panesE, gio);
            }
        }
        
        return wsoe;
    }
    
    private Pane getPane(int number) {
        if (panes == null) {
            panes = new HashMap();
        }
        Pane pane = (Pane) panes.get(new Integer(number));
        if (pane == null) {
            pane = new XPane(number);
            panes.put(new Integer(pane.getNumber()), pane);
        }
        return pane;
    }

}
