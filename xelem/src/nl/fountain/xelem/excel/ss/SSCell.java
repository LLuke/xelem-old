/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import java.lang.reflect.Method;
import java.util.Date;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.XLUtil;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.Cell;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class SSCell extends AbstractXLElement implements Cell {
    
    private int idx;
    private String styleID;
    private String formula;
    private String href;
    private String data$;
    private String datatype;
    private int mergeacross;
    private int mergedown;
    
    
    public SSCell() {
        datatype = datatype_String;
        data$ = "";
    }
    
    public void setStyleID(String id) {
        styleID = id;
    }
    
    public String getStyleID() {
        return styleID;
    }
    

    // @see nl.fountain.xelem.std.ss.Cell#setFormaula(java.lang.String)
    public void setFormula(String formula) {
        //this.formula = XLUtil.escapeHTML(formula);
        this.formula = formula;
    }

    // @see nl.fountain.xelem.std.ss.Cell#getFormula()
    public String getFormula() {
        return formula;
    }
    
    public void setHRef(String href) {
        //this.href = XLUtil.escapeHTML(href);
        this.href = href;
    }

    public String getHRef() {
        return href;
    }
    
    // @see nl.fountain.xelem.excel.ss.Cell#setMergeAcross(int)
    public void setMergeAcross(int m) {
        mergeacross = m;
    }
    
    // @see nl.fountain.xelem.excel.ss.Cell#setMergeDown(int)
    public void setMergeDown(int m) {
        mergedown = m;
    }

    // @see nl.fountain.xelem.std.ss.Cell#getXlDataType()
    public String getXlDataType() { 
        return datatype;
    }

    // @see nl.fountain.xelem.std.ss.Cell#setData(java.lang.Number)
    public void setData(Number data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        datatype = datatype_Number;
        data$ = data.toString();
    }
    
    public void setData(Integer data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        datatype = datatype_Number;
        data$ = data.toString();
    }
    
    public void setData(Double data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        if (data.isInfinite()) {
            datatype = datatype_String;
        } else {
            datatype = datatype_Number;
        }
        data$ = data.toString();
    }
    
    public void setData(Long data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        datatype = datatype_Number;
        data$ = data.toString();
    }
    
    public void setData(Float data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        if (data.isInfinite()) {
            datatype = datatype_String;
        } else {
            datatype = datatype_Number;
        }
        data$ = data.toString();
    }

    // @see nl.fountain.xelem.std.ss.Cell#setData(java.util.Date)
    public void setData(Date data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        datatype = datatype_DateTime;
        data$ = XLUtil.format(data);
    }

    // @see nl.fountain.xelem.std.ss.Cell#setData(java.lang.Boolean)
    public void setData(Boolean data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        setData(data.booleanValue());
    }

    // @see nl.fountain.xelem.std.ss.Cell#setData(java.lang.String)
    public void setData(String data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        datatype = datatype_String;
        data$ = data;
    }
    
    // @see nl.fountain.xelem.std.ss.Cell#setData(java.lang.Object)
    public void setData(Object data) {
        if (data == null) {
            setError(errortype_NULL);
            return;
        }
        Class[] types = new Class[] {data.getClass()};
        Method method = null;
        try {
            method = this.getClass().getMethod("setData", types);
            method.invoke(this, new Object[]{data});
        } catch (NoSuchMethodException e) {
            setData(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @see nl.fountain.xelem.std.ss.Cell#setError(java.lang.String)
    public void setError(String error) {
        datatype = datatype_Error;
        data$ = error;
        setFormula("=" + error);
    }
    
    // @see nl.fountain.xelem.std.ss.Cell#setData(byte)
    public void setData(byte data) {
        datatype = datatype_Number;
        data$ = String.valueOf(data);
    }
    
    // @see nl.fountain.xelem.std.ss.Cell#setData(short)
    public void setData(short data) {
        datatype = datatype_Number;
        data$ = String.valueOf(data);
    }

    // @see nl.fountain.xelem.std.ss.Cell#setData(int)
    public void setData(int data) {
        datatype = datatype_Number;
        data$ = String.valueOf(data);
    }

    // @see nl.fountain.xelem.std.ss.Cell#setData(long)
    public void setData(long data) {
        datatype = datatype_Number;
        data$ = String.valueOf(data);
    }
    
    // @see nl.fountain.xelem.std.ss.Cell#setData(float)
    public void setData(float data) {
        if (Float.isInfinite(data)) {
            datatype = datatype_String;
        } else {
            datatype = datatype_Number;
        }
        data$ = String.valueOf(data);
    }

    // @see nl.fountain.xelem.std.ss.Cell#setData(double)
    public void setData(double data) {
        if (Double.isInfinite(data)) {
            datatype = datatype_String;
        } else {
            datatype = datatype_Number;
        }
        data$ = String.valueOf(data);
        
    }
    
    // @see nl.fountain.xelem.std.ss.Cell#setData(char)
    public void setData(char data) {
        setData(String.valueOf(data));
    }
    
    // @see nl.fountain.xelem.std.ss.Cell#setData(boolean)
    public void setData(boolean data) {
        datatype = datatype_Boolean;
        if (data) {
            data$ = "1";
        } else {
            data$ = "0";
        }
    }

    // @see nl.fountain.xelem.std.ss.Cell#getData$()
    public String getData$() {
        return data$;
    }

    protected void setIndex(int index) {
        idx = index;
    }

    public String getTagName() {
        return "Cell";
    }
    
    public String getNameSpace() {
        return XMLNS_SS;
    }
    
    public String getPrefix() {
        return PREFIX_SS;
    }

    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element ce = assemble(doc, gio);
        
        if (idx != 0) ce.setAttributeNodeNS(
                createAttributeNS(doc, "Index", idx));
        if (getStyleID() != null) {
            ce.setAttributeNodeNS(createAttributeNS(doc, "StyleID", getStyleID()));
            gio.addStyleID(getStyleID());
        }
        if (formula != null) ce.setAttributeNodeNS(
                createAttributeNS(doc, "Formula", formula));
        if (href != null) ce.setAttributeNodeNS(
                createAttributeNS(doc, "HRef", href));
        if (mergeacross > 0) ce.setAttributeNodeNS(
                createAttributeNS(doc, "MergeAcross", mergeacross));
        if (mergedown > 0) ce.setAttributeNodeNS(
                createAttributeNS(doc, "MergeDown", mergedown));
        
        parent.appendChild(ce);
        
        if (!"".equals(getData$())) {
            Element data = doc.createElement("Data");
            data.setAttributeNodeNS(
                    createAttributeNS(doc, "Type", getXlDataType()));
            data.appendChild(doc.createTextNode(getData$()));
            ce.appendChild(data);
        }
        return ce;
    }
    
    protected void setXLDataType(String type) {
        if (datatype_Boolean.equals(type)
                || datatype_DateTime.equals(type)
                || datatype_Error.equals(type)
                || datatype_Number.equals(type)
                || datatype_String.equals(type)) {
            datatype = type;
        } else {
            throw new IllegalArgumentException(type + " is not a valid datatype.");
        }
    }

}
