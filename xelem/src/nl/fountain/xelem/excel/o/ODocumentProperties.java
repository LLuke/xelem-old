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


public class ODocumentProperties extends AbstractXLElement implements DocumentProperties {
    
    private String title;
    private String subject;
    private String author;
    private String lastAuthor;
    private String created;
    private String company;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLastAuthor(String lastAuthor) {
        this.lastAuthor = lastAuthor;
    }

    public void setCreated(Date created) {
        this.created = XLUtil.format(created);
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Element assemble(Document doc, Element parent, GIO gio) {
        Element dpe = assemble(doc, gio);
        
        if (title != null) dpe.appendChild(
                createElementNS(doc, "Title", title));
        if (subject != null) dpe.appendChild(
                createElementNS(doc, "Subject", subject));
        if (author != null) dpe.appendChild(
                createElementNS(doc, "Author", author));
        if (lastAuthor != null) dpe.appendChild(
                createElementNS(doc, "LastAuthor", lastAuthor));
        if (created != null) dpe.appendChild(
                createElementNS(doc, "Created", created));
        if (company != null) dpe.appendChild(
                createElementNS(doc, "Company", company));
        
        parent.appendChild(dpe);
        return dpe;
    }

}
