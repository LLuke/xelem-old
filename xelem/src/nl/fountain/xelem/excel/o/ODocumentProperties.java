/*
 * Created on 8-nov-2004
 *
 */
package nl.fountain.xelem.excel.o;

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.XLUtil;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.DocumentProperties;

/**
 * An implementation of the XLElement DocumentProperties.
 */
public class ODocumentProperties extends AbstractXLElement implements DocumentProperties {
    
    private String title;
    private String subject;
    private String keywords;
    private String description;
    private String category;
    private String author;
    private String lastAuthor;
    private String manager;
    private String created;
    private String company;
    private String hyperlinkbase;
    private String appname;
    
    /**
     * Creates a new ODocumentProperties.
     * 
     * @see nl.fountain.xelem.excel.Workbook#getDocumentProperties()
     */
    public ODocumentProperties() {}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLastAuthor(String lastAuthor) {
        this.lastAuthor = lastAuthor;
    }
    
    public void setManager(String manager) {
        this.manager = manager;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public void setHyperlinkBase(String hyperlinkbase) {
        this.hyperlinkbase = hyperlinkbase;
    }
    
    public void setAppName(String appname) {
        this.appname = appname;
    }

    public void setCreated(Date created) {
        this.created = XLUtil.format(created).substring(0, 16) + "Z";
    }

    public String getTagName() {
        return "DocumentProperties";
    }

    public String getNameSpace() {
        return XMLNS_O;
    }

    public String getPrefix() {
        return PREFIX_O;
    }

    public Element assemble(Element parent, GIO gio) {
        Document doc = parent.getOwnerDocument();
        Element dpe = assemble(doc, gio);
        
        if (title != null) dpe.appendChild(
                createElementNS(doc, "Title", title));
        if (subject != null) dpe.appendChild(
                createElementNS(doc, "Subject", subject));
        if (keywords != null) dpe.appendChild(
                createElementNS(doc, "Keywords", keywords));
        if (description != null) dpe.appendChild(
                createElementNS(doc, "Description", description));
        if (category != null) dpe.appendChild(
                createElementNS(doc, "Category", category));
        if (author != null) dpe.appendChild(
                createElementNS(doc, "Author", author));
        if (lastAuthor != null) dpe.appendChild(
                createElementNS(doc, "LastAuthor", lastAuthor));
        if (manager != null) dpe.appendChild(
                createElementNS(doc, "Manager", manager));       
        if (company != null) dpe.appendChild(
                createElementNS(doc, "Company", company));
        if (hyperlinkbase != null) dpe.appendChild(
                createElementNS(doc, "HyperlinkBase", hyperlinkbase));
        if (appname != null) dpe.appendChild(
                createElementNS(doc, "AppName", appname));
        if (created != null) dpe.appendChild(
                createElementNS(doc, "Created", created));
        
        parent.appendChild(dpe);
        return dpe;
    }

}
