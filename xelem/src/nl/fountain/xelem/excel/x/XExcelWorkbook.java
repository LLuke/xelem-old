/*
 * Created on 30-okt-2004
 * Copyright (C) 2004  Henk van den Berg
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * see license.txt
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
    
    /**
     * Method called by 
     * {@link nl.fountain.xelem.lex.ExcelReader}.
     * 
     * @param s the node value of the tag <code>%gt;WindowHeight&lt;</code>
     */
    public void setWindowHeight(String s) {
        windowHeight = Integer.parseInt(s);
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowWidth(int width) {
        windowWidth = width;
    }
    
    /**
     * Method called by 
     * {@link nl.fountain.xelem.lex.ExcelReader}.
     * 
     * @param s the node value of the tag <code>%gt;WindowWidth&lt;</code>
     */
    public void setWindowWidth(String s) {
       windowWidth = Integer.parseInt(s); 
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowTopX(int x) {
        windowTopX = x;
    }
    
    /**
     * Method called by 
     * {@link nl.fountain.xelem.lex.ExcelReader}.
     * 
     * @param s the node value of the tag <code>%gt;WindowTopX&lt;</code>
     */
    public void setWindowTopX(String s) {
       windowTopX = Integer.parseInt(s);
    }

    public int getWindowTopX() {
        return windowTopX;
    }

    public void setWindowTopY(int y) {
        windowTopY = y;
    }
    
    /**
     * Method called by 
     * {@link nl.fountain.xelem.lex.ExcelReader}.
     * 
     * @param s the node value of the tag <code>%gt;WindowTopY&lt;</code>
     */
    public void setWindowTopY(String s) {
        windowTopY = Integer.parseInt(s);
    }

    public int getWindowTopY() {
        return windowTopY;
    }
    
    public void setActiveSheet(int nr) {
        activeSheet = nr;
    }
    
    /**
     * Method called by 
     * {@link nl.fountain.xelem.lex.ExcelReader}.
     * 
     * @param s the node value of the tag <code>%gt;ActiveSheet&lt;</code>
     */
    public void setActiveSheet(String s) {
        activeSheet = Integer.parseInt(s);
    }
    
	public int getActiveSheet() {
	    if (activeSheet < 0) return 0;
	    return activeSheet;
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
