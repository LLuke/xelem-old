/*
 * Created on Nov 3, 2004
 *
 */
package nl.fountain.xelem.excel;

/**
 * Represents the Pane element.
 */
public interface Pane extends XLElement {
    
    public static int TOP_LEFT = 3;
    public static int BOTTOM_LEFT = 2;
    public static int TOP_RIGHT = 1;
    public static int BOTTOM_RIGHT = 0;
    
    int getNumber();
    void setActiveCell(int row, int col);
    void setActiveCol(int col);
    int getActiveCol();
    void setActiveRow(int row);
    int getActiveRow();
    void setRangeSelection(String rc);
    String getRangeSelection();

}
