/*
 * Created on 1-nov-2004
 *
 */
package nl.fountain.xelem;

import java.util.Set;
import java.util.TreeSet;

/**
 * Gathers global information about a workbook during assembly.
 * <P>
 * A {@link nl.fountain.xelem.excel.ss.XLWorkbook} is a rather loosely organized
 * set of java class instances. In order to not only make valid xml, but to also
 * assure that the produced xml will open in Excel, this class 
 * collects global information about a workbook during assembly.
 * The Workbook uses the information to set additional elements or element-
 * attributes when producing a {@link org.w3c.dom.Document} with the method
 * {@link nl.fountain.xelem.excel.Workbook#createDocument() createDocument}.
 * 
 * 
 */
public class GIO {
    
    private Set styleIDSet;
    private int selectedSheetsCount;
    private boolean printComments;
    
    /**
     * Adds the styleID to the set of styleID's of this GIO.
     * 
     * @param 	styleID	The styleID to be added.
     */
    public void addStyleID(String styleID) {
        getStyleIDSet().add(styleID);
    }
    
    /**
     * Gets the set of styleID's previously added with
     * {@link #addStyleID(String)}.
     * 
     * @return 	A set of styleID's.
     */
    public Set getStyleIDSet() {
        if (styleIDSet == null) {
            styleIDSet = new TreeSet();
        }
        return styleIDSet;
    }
    
    /**
     * Increases the number of selected sheets by one.
     */
    public void increaseSelectedSheets() {
        selectedSheetsCount++;
    }
    
    /**
     * Gets the number of selected sheets.
     * 
     * @return The number of selected sheets.
     */
    public int getSelectedSheetsCount() {
        return selectedSheetsCount;
    }
    
    /**
     * Sets whether the workbook's 
     * {@link nl.fountain.xelem.excel.Workbook#createDocument() createDocument}
     * -method will print comments.
     * 
     * @param 	print	<code>false</code> if comments must be ignored.
     * 
     * @see nl.fountain.xelem.excel.Workbook#setPrintElementComments(boolean print)
     */
    public void setPrintComments(boolean print) {
        printComments = print;
    }
    
    /**
     * Specifies whether the workbook's
     * {@link nl.fountain.xelem.excel.Workbook#createDocument() createDocument}
     * -method will print comments.
     *  
     * @return 	<code>true</code> if comments will be printed, 
     * 			<code>false</code> otherwise.
     */
    public boolean isPrintingComments() {
        return printComments;
    }

}
