/*
 * Created on 8-nov-2004
 *
 */
package nl.fountain.xelem.excel;

import java.util.Date;

/**
 * Represents the DocumentProperties element.
 */
public interface DocumentProperties extends XLElement {
    
    void setTitle(String title);
    
    void setSubject(String subject);
    
    void setKeywords(String keywords);
    
    void setDescription(String description);
    
    void setCategory(String category);
    
    void setAuthor(String author);
    
    void setLastAuthor(String lastAuthor);
    
    void setManager(String manager);
    
    void setCompany(String company);
    
    /**
     * Sets the hyperlinkbase for this workbook. The hyperlinkbase -when set-
     * is prepended before link-values in cells.
     * For instance:
     * a hyperlinkbase of <code>"file://C:/foo/"</code> and a cell where upon
     * the HRef is set as <code>"bar.txt"</code> will result in the link encountered
     * in that cell pointing to <code>"file://C:/foo/bar.txt"</code>.
     */
    void setHyperlinkBase(String hyperlinkbase);
    
    void setAppName(String appname);
    
    void setCreated(Date created);
    
    

}
