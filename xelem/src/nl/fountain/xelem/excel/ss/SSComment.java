/*
 * Created on 31-mrt-2005
 *
 */
package nl.fountain.xelem.excel.ss;

import java.lang.reflect.Method;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.Comment;

/**
 *
 */
public class SSComment extends AbstractXLElement implements Comment {
    
    private String author;
    private boolean showAlways;
    private String data;

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setShowAlways(boolean show) {
        showAlways = show;
    }

    public boolean showsAlways() {
        return showAlways;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getDataStripped() {
        if (data != null) {
            char lf = 10;
            String lfs = "" + lf;
            String s = data.replaceAll("\\<.*?\\>","");
            return s.replaceAll("&#10;", lfs);
        } else {
            return data;
        }
    }
    
    public String getDataClean() {
        if (data != null) {            
            String s = data.replaceAll("\\<.*?\\>","");
            s = s.replaceAll("&#10;", " ");
            if (author != null) {
                s = s.replaceFirst(author + ":", "");
            }
            return s.trim();
        } else {
            return data;
        }
    }

    public String getTagName() {
        return "Comment";
    }

    public String getNameSpace() {
        return XMLNS_SS;
    }

    public String getPrefix() {
        return PREFIX_SS;
    }

    public Element assemble(Element parent, GIO gio) {
        if (data == null) return null;
        
        Document doc = parent.getOwnerDocument();
        Element coe = assemble(doc, gio);
        
        if (author != null) coe.setAttributeNodeNS(
                createAttributeNS(doc, "Author", author));
        if (showAlways) coe.setAttributeNodeNS(
                createAttributeNS(doc, "ShowAlways", "1"));
        
        Element dae = doc.createElementNS(XMLNS_SS, "Data");
        dae.setPrefix(PREFIX_SS);
        dae.appendChild(doc.createTextNode(getDataStripped()));
        coe.appendChild(dae);
        
        parent.appendChild(coe);
        
        return coe;
    }
    
    public void setAttributes(Attributes attrs) {
        for (int i = 0; i < attrs.getLength(); i++) {
            invokeMethod(attrs.getLocalName(i), attrs.getValue(i));
        }
    }
    
    public void setChildElement(String localName, String content) {
        setData(content);
    }
    
	private void invokeMethod(String name, Object value) {
        Class[] types = new Class[] { value.getClass() };
        Method method = null;
        try {
            method = this.getClass().getDeclaredMethod("set" + name, types);
            method.invoke(this, new Object[] { value });
        } catch (NoSuchMethodException e) {
            // no big deal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
