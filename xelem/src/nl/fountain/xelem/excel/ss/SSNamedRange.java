/*
 * Created on Nov 4, 2004
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
package nl.fountain.xelem.excel.ss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.NamedRange;

/**
 * An implementation of the XLElement NamedRange.
 */
public class SSNamedRange extends AbstractXLElement implements NamedRange {
    
    private String name;
    private String refersTo;
    private boolean hidden;

    
    /**
     * Creates a new SSNamedRange.
     * 
     * @see nl.fountain.xelem.excel.Workbook#addNamedRange(String, String)
     */
    public SSNamedRange(String name, String refersTo) {
        this.name = name;
        this.refersTo = refersTo;
    }

    public void setHidden(boolean hide) {
        hidden = hide;
    }

    // @see nl.fountain.xelem.excel.XLElement#getTagName()
    public String getTagName() {
        return "NamedRange";
    }

    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS_SS;
    }

    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_SS;
    }

    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element nre = assemble(doc, gio);
        
        nre.setAttributeNodeNS(createAttributeNS(doc, "Name", name));
        if (refersTo != null) nre.setAttributeNodeNS(
                createAttributeNS(doc, "RefersTo", refersTo));
        if (hidden) nre.setAttributeNodeNS(
                createAttributeNS(doc, "Hidden", "1"));
        
        parent.appendChild(nre);
        return nre;
    }

}
