/*
 * Created on Oct 15, 2004
 *
 */
package nl.fountain.xelem.excel;


/**
 * Represents the WorksheetOptions element.
 */
public interface WorksheetOptions extends XLElement {
    
    public static final String SHEET_VISIBLE = "SheetVisible";
    public static final String SHEET_HIDDEN = "SheetHidden";
    public static final String SHEET_VERY_HIDDEN = "SheetVeryHidden";
    
    void setTopRowVisible(int tr);
    int getTopRowVisible();
    void setLeftColumnVisible(int lc);
    int getLeftColumnVisible();
    
    void setZoom(int z);
    int getZoom();
    
    void setGridlineColor(int r, int g, int b);
    
    void setTabColorIndex(int ci);
    int getTabColorIndex();
    
    void setSelected(boolean s);
    boolean isSelected();
    
    void doNotDisplayHeadings(boolean b);
    boolean displaysNoHeadings();
    
    void doNotDisplayGridlines(boolean b);
    boolean displaysNoGridlines();
    
    void doDisplayFormulas(boolean f);
    boolean displaysFormulas();
    
    void setVisible(String wsoValue);
    
    void setActiveCell(int r, int c);
    void setActiveCell(int paneNumber, int r, int c);
    void setRangeSelection(String rcRange);
    void setRangeSelection(int paneNumber, String rcRange);
    
    void splitHorizontal(int points, int topRow);
    void splitVertical(int points, int leftColumn);
    
    void freezePanesAt(int r, int c);
    
}
