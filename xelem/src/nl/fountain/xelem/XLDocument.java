/*
 * Created on Dec 24, 2004
 *
 */
package nl.fountain.xelem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.XLElement;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A class that will take an existing xml-spreadsheet as a template.
 * <P>
 * <i>Sometimes you just want to dump a lot of data produced within your java-
 * application into a preformatted template. Building an 
 * {@link nl.fountain.xelem.excel.ss.XLWorkbook XLWorkbook} from scratch may be
 * time consuming and is a waste of effort.</i>
 * <P>
 * Create your template in Excel. Select columns to set formatting (i.e. StyleID's)
 * on entire columns. If you wish, you may put table headings, formula's, titles
 * and what have you, in the first rows of the sheets. Save your template and
 * instantiate an instance of XLDocument with the parameter 
 * <code>fileName</code> set to the path where your template resides. The methods
 * {@link #appendRow(String, Row) appendRow(String sheetName, Row row)} and 
 * {@link #appendRows(String, Collection) appendRows(String sheetName, Collection rows)}
 * will append rows directly under the last row-element of the sheet. Your data
 * will be formatted according to the StyleID's which where set on your columns
 * during the creation of the template. 
 * <P>
 * The method {@link #setCellData(Cell, String, int, int)} will set or replace
 * (only) the value of the data-element of the mentioned cell.
 * 
 * @since xelem.1.0.1
 * 
 */
public class XLDocument {
    
    private Document doc;
    private Map tableMap;
    
    /**
     * Creates a new XLDocument by parsing the specified file into a
     * {@link org.w3c.dom.Document}.
     * @param fileName	the filename of the xml-spreadsheet template
     * @throws XelemException	if loading or parsing of the document fails.
     * <br>Could be any of:
     * <UL>
     * <LI>{@link java.io.FileNotFoundException}
     * <LI>{@link javax.xml.parsers.FactoryConfigurationError}
     * <LI>{@link javax.xml.parsers.ParserConfigurationException}
     * <LI>{@link org.xml.sax.SAXException}
     * <LI>{@link java.io.IOException}
     * </UL>
     */
    public XLDocument(String fileName) throws XelemException {
        doc = loadDocument(fileName);
        tableMap = new HashMap();
    }
    
    /**
     * 
     * @return the {@link org.w3c.dom.Document} for which this XLDocument
     * 	acts as a wrapper
     */
    public Document getDocument() {
        return doc;
    }
    
    public void appendRow(String sheetName, Row row) {
        Element rowElement = row.createElement(doc);
        getTableElement(sheetName).appendChild(rowElement);
    }
    
    public void appendRows(String sheetName, Collection rows) {
        Node table = getTableElement(sheetName);
        for (Iterator iter = rows.iterator(); iter.hasNext();) {
            Row row = (Row) iter.next();
            table.appendChild(row.createElement(doc));
        }      
    }
    
    public void setCellData(Cell cell, String sheetName, int rowIndex, int columnIndex) {
        Element data = cell.getDataElement(doc);
        Element cellElement = getCellElement(sheetName, rowIndex, columnIndex);
        NodeList nodelist = cellElement.getChildNodes();
        Node oldData = null;
        for (int i = 0; i < nodelist.getLength(); i++) {
            if ("Data".equals(nodelist.item(i).getLocalName())) {
                oldData = nodelist.item(i);
            }
        }
        if (oldData == null) {
            cellElement.appendChild(data);
        } else {
            cellElement.replaceChild(data, oldData);
        }
    }
    
    protected Element getSheetElement(String sheetName) {
        NodeList nodelist = doc.getElementsByTagName("Worksheet");
        if (nodelist.getLength() == 0) {
            nodelist = doc.getElementsByTagNameNS(XLElement.XMLNS_SS, "Worksheet");
        }
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node sheetNode = nodelist.item(i);
            NamedNodeMap atrbs = sheetNode.getAttributes();
            Node nameAtrb = atrbs.getNamedItemNS(XLElement.XMLNS_SS, "Name");
            if (nameAtrb != null && sheetName.equals(nameAtrb.getNodeValue())) {
                return (Element) sheetNode;
            }
        }
        throw new NoSuchElementException("The worksheet '" + sheetName +
                "' does not exist.");
    }
    
    protected Element getTableElement(String sheetName) {
        Element tableElement = (Element) tableMap.get(sheetName);
        if (tableElement == null) {
	        Node sheet = getSheetElement(sheetName);
            NodeList sheetKits = sheet.getChildNodes();
            for (int i = 0; i < sheetKits.getLength(); i++) {
                Node node = sheetKits.item(i);
                if ("Table".equals(node.getLocalName())) {
                    NamedNodeMap atrbs = node.getAttributes();
                    if (atrbs.getNamedItemNS(
                            XLElement.XMLNS_SS, "ExpandedColumnCount") != null) {
                        atrbs.removeNamedItemNS(
                                XLElement.XMLNS_SS, "ExpandedColumnCount");
                    }
                    if (atrbs.getNamedItemNS(
                            XLElement.XMLNS_SS, "ExpandedRowCount") != null) {
                        atrbs.removeNamedItemNS(
                                XLElement.XMLNS_SS, "ExpandedRowCount");
                    }
                    tableMap.put(sheetName, node);
                    return (Element) node;
                }
            }
        }
        return tableElement;
    }
    
    protected Element getCellElement(String sheetName, int rowIndex, int columnIndex) {
        Element row = getRowElement(sheetName, rowIndex);
        NodeList nodelist = row.getChildNodes();
        int teller = 0;
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            if ("Cell".equals(node.getLocalName())) {
                teller++;
                Node idx = node.getAttributes().getNamedItemNS(
                        XLElement.XMLNS_SS, "Index");
                if (idx != null) {
                    teller = Integer.parseInt(idx.getNodeValue());
                }
                if (teller == columnIndex) {
                    return (Element) node;
                } else if (teller > columnIndex) {
                    Element cellElement = createIndexedElement("Cell", columnIndex);
                    row.insertBefore(cellElement, node);
                    return cellElement;
                }
            }
        }
        Element cellElement = createIndexedElement("Cell", columnIndex);
        row.appendChild(cellElement);
        return cellElement;
    }
    
    protected Element getRowElement(String sheetName, int index) {
        Element table = getTableElement(sheetName);
        NodeList nodelist = table.getChildNodes();
        int teller = 0;
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            if ("Row".equals(node.getLocalName())) {
                teller++;
                Node idx = node.getAttributes().getNamedItemNS(
                        XLElement.XMLNS_SS, "Index");
                if (idx != null) {
                    teller = Integer.parseInt(idx.getNodeValue());
                }
                if (teller == index) {
                    return (Element) node;
                } else if (teller > index) {
                    Element rowElement = createIndexedElement("Row", index);
                    table.insertBefore(rowElement, node);
                    return rowElement;
                }
            }
        }
        Element rowElement = createIndexedElement("Row", index);
        table.appendChild(rowElement);
        return rowElement;
    }
    
    
    private Element createIndexedElement(String qName, int index) {
        Element element = 
            doc.createElementNS(XLElement.XMLNS_SS, qName);
        element.setPrefix(XLElement.PREFIX_SS);
        element.setAttributeNodeNS(
            createAttributeNS("Index", XLElement.XMLNS_SS, 
                    XLElement.PREFIX_SS, index));
        return element;
    }
    
    private Attr createAttributeNS(String qName, String nameSpace, String prefix, int i) {
        Attr attr = doc.createAttributeNS(nameSpace, qName);
        attr.setPrefix(prefix);
        attr.setValue("" + i);
        return attr;
    }
    
    private Document loadDocument(String fileName) throws XelemException {
        Document document = null;
        InputStream is = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            is = new FileInputStream(fileName);
            document = builder.parse(is);
            is.close();
        } catch (FileNotFoundException e) {
            throw new XelemException(e.fillInStackTrace());
        } catch (FactoryConfigurationError e) {
            throw new XelemException(e.fillInStackTrace());
        } catch (ParserConfigurationException e) {
            throw new XelemException(e.fillInStackTrace());
        } catch (SAXException e) {
            throw new XelemException(e.fillInStackTrace());
        } catch (IOException e) {
            throw new XelemException(e.fillInStackTrace());
        }
        return document;
    }

}
