/*
 * Created on Sep 7, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.UnsupportedStyleException;
import nl.fountain.xelem.XFactory;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.AbstractXLElement;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.DuplicateNameException;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.o.ODocumentProperties;
import nl.fountain.xelem.excel.x.XExcelWorkbook;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 */
public class XLWorkbook extends AbstractXLElement implements Workbook {
    
    private DocumentProperties documentProperties;
    private ExcelWorkbook excelWorkbook;
    private String name;
    private String filename;
    private Collection docComments;
    private Map sheets;
    private List sheetList;
    private Map namedRanges;
    private boolean printComments = true;
    private boolean printDocComments = true;
    private boolean appendInfoSheet;
    private XFactory xFactory;
    private List warnings;
    private SimpleDateFormat sdf;

    public XLWorkbook(String name) {
        sheets = new HashMap();
        sheetList = new ArrayList();
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // @see nl.fountain.xelem.excel.Workbook#getName()
    public String getName() {
        return name;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

    public String getFileName() {
        if (filename == null) {
            return name + ".xls";
        } else {
            return filename;
        }
    }
    
    // @see nl.fountain.xelem.excel.Workbook#mergeStyle(java.lang.String, java.lang.String, java.lang.String)
    public void mergeStyles(String newID, String id1, String id2) {
        try {
            getFactory().mergeStyles(newID, id1, id2);
        } catch (UnsupportedStyleException e) {
            addWarning(e);
        }
    }
    
    // @see nl.fountain.xelem.excel.Workbook#setAppendInfoSheet(boolean)
    public void setAppendInfoSheet(boolean append) {
        appendInfoSheet = append;
    }
    
    public DocumentProperties addDocumentProperties() {
        documentProperties = new ODocumentProperties();
        return documentProperties;
    }

    public DocumentProperties getDocumentProperties() {
        if (documentProperties == null) {
            documentProperties = new ODocumentProperties();
        }
        return documentProperties;
    }

    public boolean hasDocumentProperties() {
        return documentProperties != null;
    }
    
    public ExcelWorkbook addExcelWorkbook() {
        excelWorkbook = new XExcelWorkbook();
        return excelWorkbook;
    }
    
    public ExcelWorkbook getExcelWorkbook() {
        if (excelWorkbook == null) {
            addExcelWorkbook();
        }
        return excelWorkbook;
    }
    
    // @see nl.fountain.xelem.excel.Workbook#hasExcelWorkbook()
    public boolean hasExcelWorkbook() {
        return excelWorkbook != null;
    }
    
    // @see nl.fountain.xelem.excel.Workbook#addNamedRange(java.lang.String, java.lang.String)
    public NamedRange addNamedRange(String name, String refersTo) {
        NamedRange nr = new SSNamedRange(name, refersTo);
        if (namedRanges == null) {
            namedRanges = new HashMap();
        }
        namedRanges.put(name, nr);
        return nr;
    }
    
    // @see nl.fountain.xelem.std.Workbook#addSheet()
    public Worksheet addSheet() {
        int nr = sheets.size();
        String name;
        do
            name = "Sheet" + ++nr;
        while(sheetList.contains(name));
        Worksheet ws = null;
        try {
            ws = addSheet(name);
        } catch (DuplicateNameException e) {
           // will not happen
        }
        return ws;
    }
    
    // @see nl.fountain.xelem.std.Workbook#addSheet(java.lang.String)
    public Worksheet addSheet(String name) throws DuplicateNameException {
        if (name == null || "".equals(name)) {
            return addSheet();
        }
        return addSheet(new SSWorksheet(name));
    }

    public Worksheet addSheet(Worksheet sheet) throws DuplicateNameException {
        if (sheetList.contains(sheet.getName())) {
           throw new DuplicateNameException(
                   "Duplicate name in worksheets collection: '"
                   + sheet.getName() + "'."); 
        }
        sheetList.add(sheet.getName());
        sheets.put(sheet.getName(), sheet);
        return sheet;
    }

    public Collection getWorksheets() {
        Collection worksheets = new ArrayList();
        for (Iterator iter = sheetList.iterator(); iter.hasNext();) {
            worksheets.add(sheets.get(iter.next()));           
        }
        return worksheets;
    }

    public Worksheet getWorksheet(String name) {
        return (Worksheet) sheets.get(name);
    }
    
    // @see nl.fountain.xelem.std.Workbook#removeSheet(java.lang.String)
    public Worksheet removeSheet(String name) {
        int index = sheetList.indexOf(name);
        if (index < 0) return null;
        sheetList.remove(index);
        return (Worksheet) sheets.remove(name);
    }

    public void setPrintComments(boolean print) {
        printComments = print;
    }

    public void setPrintDocComments(boolean print) {
        printDocComments = print;
    }

    public boolean isPrintingComments() {
        return printComments;
    }

    public boolean isPrintingDocComments() {
        return printDocComments;
    }

    public String getTagName() {
        return "Workbook";
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getNameSpace()
    public String getNameSpace() {
        return XMLNS;
    }
    
    // @see nl.fountain.xelem.excel.XLElement#getPrefix()
    public String getPrefix() {
        return PREFIX_SS;
    }
    
    // @see nl.fountain.xelem.excel.Workbook#createDocument()
    public Document createDocument() throws ParserConfigurationException {
        GIO gio = new GIO();
        Document doc = getDoc();
        Element root = doc.getDocumentElement();
        assemble(doc, root, gio);
        return doc;
    }
    
    public Element assemble(Document doc, Element root, GIO gio) {
        warnings = null;
        gio.setPrintComments(isPrintingComments());
        doc.insertBefore(doc.createProcessingInstruction("mso-application",
                "progid=\"Excel.Sheet\""), root);
        
        if (isPrintingDocComments()) {
            for (Iterator iter = getFactory().getDocComments().iterator(); iter.hasNext();) {
                String dc = (String) iter.next();
                doc.insertBefore(doc.createComment(dc), root);
            }
        }
        
        root.setAttribute("xmlns", XMLNS);
        root.setAttribute("xmlns:o", XMLNS_O);
        root.setAttribute("xmlns:x", XMLNS_X);
        root.setAttribute("xmlns:ss", XMLNS_SS);
        root.setAttribute("xmlns:html", XMLNS_HTML);
        
        if (isPrintingComments() && getComments() != null) {
            for (Iterator iter = getComments().iterator(); iter.hasNext();) {
                String comment = (String) iter.next();
                root.appendChild(doc.createComment(comment));
            }
        }

        //o:DocumentProperties
        if (hasDocumentProperties()) {
            documentProperties.assemble(doc, root, gio);
        }

        //x:ExcelWorkbook
        Element xlwbe = getExcelWorkbook().assemble(doc, root, gio);

        //Styles
        Element styles = doc.createElement("Styles");
        root.appendChild(styles);
        appendDefaultStyle(doc, styles);
        
        // Names
        if (namedRanges != null) {
            Element names = doc.createElement("Names");
            root.appendChild(names);
            for (Iterator iter = namedRanges.values().iterator(); iter.hasNext();) {
                NamedRange nr = (NamedRange) iter.next();
                nr.assemble(doc, names, gio);
            }
        }

        // Worksheets
        if (sheets.size() < 1) {
            addSheet();
        }
        for (Iterator iter = getWorksheets().iterator(); iter.hasNext();) {
            Worksheet ws = (Worksheet) iter.next();
            ws.assemble(doc, root, gio);
        }
        
        // append xelem-info sheet
        if (appendInfoSheet) {
	        Node infoWS = doc.importNode(getFactory().getSheet(INFO_WORKSHEET), true);
	        root.appendChild(infoWS);
	        gio.addStyleID("info_def");
	        gio.addStyleID("info_desc");
	        gio.addStyleID("hyperlink");
	        gio.addStyleID("info_hyperlink");
        }
        
        // append Global Information
        int selectedSheets = gio.getSelectedSheetsCount();
        if ( selectedSheets > 1) {
            Element n = doc.createElementNS(XMLNS_X, "SelectedSheets");
            n.setPrefix(PREFIX_X);
            n.appendChild(doc.createTextNode("" + selectedSheets));
            xlwbe.appendChild(n);
        }        
        appendStyles(doc, styles, gio);
        
        return root;
    }

    public List getWarnings() {
        if (warnings == null) {
            return Collections.EMPTY_LIST;
        } else {
            return warnings;
        }
    }
    
    private Document getDoc() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImpl = builder.getDOMImplementation();
        return domImpl.createDocument(null, getTagName(), null);
    }
    
    private void appendDefaultStyle(Document doc, Element styles) {
        Element dse = getFactory().getStyle("Default");
        if (dse == null) {
            dse = doc.createElement("Style");
            
            dse.setAttributeNodeNS(createAttributeNS(doc, "ID", "Default"));
            dse.setAttributeNodeNS(createAttributeNS(doc, "Name", "Normal"));
            
            Element alignment = doc.createElement("Alignment");
            dse.appendChild(alignment);
            alignment.setAttributeNodeNS(createAttributeNS(doc, "Vertical", "Bottom"));
            
            dse.appendChild(doc.createElement("Borders"));
            dse.appendChild(doc.createElement("Font"));
            dse.appendChild(doc.createElement("Interior"));
            dse.appendChild(doc.createElement("NumberFormat"));
            dse.appendChild(doc.createElement("Protection"));
        } else {
            dse = (Element) doc.importNode(dse, true);
        }
        styles.appendChild(dse);
    }
    
    private void appendStyles(Document doc, Element styles, GIO gio) {
        for (Iterator iter = gio.getStyleIDSet().iterator(); iter.hasNext();) {
            String id = (String) iter.next();
            Element style = getFactory().getStyle(id);
            if (style == null) {
                // last resort: create one on the spot
                style = doc.createElement("Style");
                style.setAttributeNodeNS(createAttributeNS(doc, "ID", id));
                addWarning(new UnsupportedStyleException(
                        "Style '" + id + "' not found."));
            } else {
                // we have a style from the XFactory
                style = (Element) doc.importNode(style, true);
            }
            styles.appendChild(style);
        }
    }
    
    private XFactory getFactory() {
        if (xFactory == null) {
            try {
                xFactory = XFactory.newInstance();
            } catch (XelemException e) {
                addWarning(e.getCause());
                // return an empty factory
                xFactory = XFactory.emptyFactory();
            } 
        }
        return xFactory;
    }
    
    private void addWarning(Throwable e) {
        if (warnings == null) {
            warnings = new ArrayList();
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        }
        StringBuffer msg = new StringBuffer("\n\tWARNING: ");
        msg.append(e.toString());
        StackTraceElement[] st = e.getStackTrace();
        for (int i = 0; i < st.length; i++) {
            msg.append("\n\tat ");
            msg.append(st[i].toString());
        }
        warnings.add(sdf.format(new Date()) + msg.toString());
    }



}
