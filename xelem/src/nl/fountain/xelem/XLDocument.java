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
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.XLElement;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
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
        
    }
    
    public void appendRows(String sheetName, Collection rows) {
        
    }
    
    protected Node getTable(String sheetName) {
        Node tableNode = (Node) tableMap.get(sheetName);
        if (tableNode == null) {
	        NodeList nodelist = doc.getElementsByTagName("Worksheet");
	        if (nodelist.getLength() == 0) {
	            nodelist = doc.getElementsByTagNameNS(XLElement.XMLNS_SS, "Worksheet");
	        }
	        for (int i = 0; i < nodelist.getLength(); i++) {
	            Node sheet = nodelist.item(i);
	            NamedNodeMap atrbs = sheet.getAttributes();
	            Node nameAtrb = atrbs.getNamedItemNS(XLElement.XMLNS_SS, "Name");
	            if (nameAtrb != null && sheetName.equals(nameAtrb.getNodeValue())) {
	                NodeList sheetKits = sheet.getChildNodes();
	                for (int j = 0; j < sheetKits.getLength(); j++) {
                        Node table = sheetKits.item(j);
                        if ("Table".equals(table.getLocalName())) {
                            try {
                                table.getAttributes().removeNamedItemNS(
                                        XLElement.XMLNS_SS, "ExpandedColumnCount");
                            } catch (DOMException e) {
                                // maybe there was no expandedetc.
                            }
                            try {
                                table.getAttributes().removeNamedItemNS(
                                        XLElement.XMLNS_SS, "ExpandedRowCount");
                            } catch (DOMException e1) {
                                // maybe there was no expandedetc.
                            }
                            tableMap.put(sheetName, table);
                            return table;
                        }
                    }
	            }
	        }
        }
        return tableNode;
    }
    
    
    private Document loadDocument(String fileName) throws XelemException {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new FileInputStream(fileName);
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
