/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;


/**
 * Represents the ExcelWorkbook element.
 */
public interface ExcelWorkbook extends XLElement {
    
    void setWindowHeight(int height);
    int getWindowHeight();
    
    void setWindowWidth(int width);
    int getWindowWidth();
    
    void setWindowTopX(int x);
    int getWindowTopX();
    
    void setWindowTopY(int y);
    int getWindowTopY();
    
    void setActiveSheet(int nr);
    
}
