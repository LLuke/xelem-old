/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;

/**
 * Represents the Workbook element, the root of a SpreadsheetML document.
 * <P>
 * Besides adding child XLElements and setting other features,
 * the Workbook can be used to merge Styles.
 * After finishing the Workbook you'll probably want to create the 
 * {@link org.w3c.dom.Document}, the structure that holds this Workbook
 * and all of it's children. This is done with the 
 * {@link #createDocument()}-method.
 * 
 */
public interface Workbook extends XLElement {
    
    public static final String INFO_WORKSHEET = "xelem-info";
    
    /**
     * Sets the name of this Workbook.
     */
    void setName(String name);
    
    /**
     * Gets the name of this Workbook.
     */
    String getName();
    
    /**
     * Sets the filename of this Workbook. 
     */
    void setFileName(String filename);
    
    /**
     * Gets the filename of this Workbook. If no filename was supplied with
     * {@link #setFileName(String)} the name of the workbook is returned
     * with the extension '.xls'.
     */
    String getFileName();
    
    /**
     * Merges two SpreadsheetML Style elements.
     * 
     * @see nl.fountain.xelem.XFactory#mergeStyles(String, String, String)
     */
    void mergeStyles(String newID, String id1, String id2);
    
    /**
     * Sets whether this Workbook's {@link #createDocument()}-method
     * will include comments.
     * 
     * @see nl.fountain.xelem.excel.XLElement#addComment(String)
     */
    void setPrintComments(boolean print);
    
    /**
     * Specifies whether this Workbook's {@link #createDocument()}-method
     * will include comments.
     */
    boolean isPrintingComments();
    
    /**
     * Sets whether this Workbook's {@link #createDocument()}-method
     * will include docComments.
     * 
     * @see nl.fountain.xelem.XFactory#getDocComments()
     */
    void setPrintDocComments(boolean print);
    
    /**
     * Specifies whether this Workbook's {@link #createDocument()}-method
     * will include docComments.
     */
    boolean isPrintingDocComments();
    
    void setAppendInfoSheet(boolean append);
    
    
    DocumentProperties addDocumentProperties();
    DocumentProperties getDocumentProperties();
    boolean hasDocumentProperties();
    
    ExcelWorkbook addExcelWorkbook();
    ExcelWorkbook getExcelWorkbook();
    boolean hasExcelWorkbook();
    
    NamedRange addNamedRange(String name, String refersTo);
    
    Worksheet addSheet();
    Worksheet addSheet(String name) throws DuplicateNameException;
    Worksheet addSheet(Worksheet sheet) throws DuplicateNameException;    
    Worksheet removeSheet(String name);    
    Collection getWorksheets();
    Worksheet getWorksheet(String name);
    
    /**
     * Creates a {@link org.w3c.dom.Document}, the structure that holds this Workbook
     * and all of it's children. 
     */
    Document createDocument() throws ParserConfigurationException;
    List getWarnings();
    

}
