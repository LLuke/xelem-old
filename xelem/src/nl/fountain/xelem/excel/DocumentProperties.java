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
    void setAuthor(String author);
    void setLastAuthor(String lastAuthor);
    void setCreated(Date created);
    void setCompany(String company);
    

}
