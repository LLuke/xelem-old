/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Date;


/**
 * Represents the Cell element.
 */
public interface Cell extends XLElement {
    
    public static final String datatype_Number = "Number";
    public static final String datatype_DateTime = "DateTime";
    public static final String datatype_Boolean = "Boolean";
    public static final String datatype_String = "String";
    public static final String datatype_Error = "Error";
    
    public static final String errortype_DIVby0 = "#DIV/0";
    public static final String errortype_NAME = "#NAME?";
    public static final String errortype_NULL = "#NULL!";
    public static final String errortype_NUM = "#NUM!";
    public static final String errortype_NA = "#N/A";
    public static final String errortype_REF = "#REF!";
    public static final String errortype_VALUE = "#VALUE!";
    
    void setStyleID(String styleID);
    String getStyleID();
    
    void setFormula(String formula);
    String getFormula();
    
    void setHRef(String href);
    String getHRef();
    
    void setMergeAcross(int m);
    void setMergeDown(int m);
    
    void setData(Number data);
    void setData(Integer data);
    void setData(Double data);
    void setData(Long data);
    void setData(Float data);
    void setData(Date data);
    void setData(Boolean data);
    void setData(String data);
    void setData(Object data);
    void setError(String error);
    
    void setData(byte data);
    void setData(short data);
    void setData(int data);
    void setData(long data);    
    void setData(float data);
    void setData(double data);
    void setData(char data);   
    void setData(boolean data);

    String getData$();
    String getXlDataType();
    
    
}
