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
 *
 */
public class XLDocument {
    
    private Document doc;
    private Map tableMap;
    
    public XLDocument(String fileName) throws XelemException {
        doc = loadDocument(fileName);
        tableMap = new HashMap();
    }
    
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
