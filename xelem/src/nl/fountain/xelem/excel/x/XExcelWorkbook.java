/*
 * Created on 30-okt-2004
 *
 */
package nl.fountain.xelem.excel.x;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.ExcelWorkbook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An implementation of the XLElement ExcelWorkbook.
 */
public class XExcelWorkbook extends AbstractXLElement implements ExcelWorkbook {
    
    private int windowHeight;
    private int windowWidth;
    private int windowTopX;
    private int windowTopY;
    private int activeSheet = -1;
    
    /**
     * Creates a new XExcelWorkbook.
     * 
     * @see nl.fountain.xelem.excel.Workbook#getExcelWorkbook()
     */
    public XExcelWorkbook() {}
    
    public void setWindowHeight(int height) {
        windowHeight = height;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowWidth(int width) {
        windowWidth = width;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowTopX(int x) {
        windowTopX = x;
    }

    public int getWindowTopX() {
        return windowTopX;
    }

    public void setWindowTopY(int y) {
        windowTopY = y;
    }

    public int getWindowTopY() {
        return windowTopY;
    }
    
    public void setActiveSheet(int nr) {
        activeSheet = nr;
    }

    public String getTagName() {
        return "ExcelWorkbook";
    }
    
    public String getNameSpace() {
        return XMLNS_X;
    }
    
    public String getPrefix() {
        return PREFIX_X;
    }
    
    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element ewbe = assemble(doc, gio);
        
        if (windowHeight > 0)
            ewbe.appendChild(createElementNS(doc, "WindowHeight", windowHeight));
        if (windowWidth > 0)
            ewbe.appendChild(createElementNS(doc, "WindowWidth", windowWidth));
        if (windowTopX != 0)
            ewbe.appendChild(createElementNS(doc, "WindowTopX", windowTopX));
        if (windowTopY != 0)
            ewbe.appendChild(createElementNS(doc, "WindowTopY", windowTopY));
        if (activeSheet > -1)
            ewbe.appendChild(createElementNS(doc, "ActiveSheet", activeSheet));
        
        parent.appendChild(ewbe);
        return ewbe;
    }

}
