/*
 * Created on Dec 24, 2004
 *
 */
package nl.fountain.xelem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.xml.transform.OutputKeys;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.SSCell;
import nl.fountain.xelem.excel.ss.SSRow;

import org.apache.xalan.serialize.SerializerToXML;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 */
public class XLDocumentTest extends TestCase {
    
//  the path to the directory for test files.
    private String testFileDir = "test_xls";
    
    // when set to true, test files will be created.
    // the path mentioned after 'testFileDir' should exist.
    private boolean toFile = false;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(XLDocumentTest.class);
    }
    
    public void testConstructor() {
        try {
            new XLDocument("this/file/does/not/exist");
            fail("geen exceptie gegooid.");
        } catch (XelemException e) {
            assertEquals("java.io.FileNotFoundException", 
                    e.getCause().getClass().getName());
        }
        try {
            XLDocument xldoc = new XLDocument("config/aviso.xml");
            assertNotNull(xldoc.getDocument());
        } catch (XelemException e1) {
            fail(e1.getMessage());
        }
    }
    
    public void testGetTable() throws Exception {
        XLDocument xldoc = new XLDocument("config/aviso.xml");
        Node table = xldoc.getTableElement("Sheet1");
        assertNotNull(table);
        try {
            table.getAttributes().removeNamedItemNS(
                                        XLElement.XMLNS_SS, "ExpandedColumnCount");
            fail("should have thrown exception.");
        } catch (DOMException e) {
            //
        }
        try {
            table.getAttributes().removeNamedItemNS(
                                        XLElement.XMLNS_SS, "ExpandedRowCount");
            fail("should have thrown exception.");
        } catch (DOMException e) {
            //
        }
        try {
            xldoc.getTableElement("nosheetwiththisname");
            fail("should have thrown exception.");
        } catch (RuntimeException e1) {
            assertEquals("The worksheet 'nosheetwiththisname' does not exist.",
                    e1.getMessage());
        }
    }
    
    public void testAppendRow() throws Exception {
        Row row = new SSRow();
        row.addCell("419900");
        row.addCell("wk 43, piet 45,8 u.");
        row.addCell("002020");
        row.addCell(-12345.67);
        XLDocument xldoc = new XLDocument("config/aviso.xml");
        xldoc.appendRow("Sheet1", row);
        Document doc = xldoc.getDocument();
        String xml = serialize(doc);
        String expected = "<Row>" +
    		"<ss:Cell><Data ss:Type=\"String\">419900</Data></ss:Cell>" +
    		"<ss:Cell><Data ss:Type=\"String\">wk 43, piet 45,8 u.</Data></ss:Cell>" +
    		"<ss:Cell><Data ss:Type=\"String\">002020</Data></ss:Cell>" +
    		"<ss:Cell><Data ss:Type=\"Number\">-12345.67</Data></ss:Cell>" +
    		"</Row></Table>";
        assertTrue(xml.indexOf(expected) > 0);
        
        //System.out.println(xml);
        if (toFile) serialize(doc, testFileDir + "/aviso01.xls");
    }
    
    public void testSetCellData() throws Exception {
        //XLDocument xldoc = new XLDocument("config/aviso.xml");
        XLDocument xldoc = new XLDocument("test_xls/test30.xls");
        Cell cell = new SSCell();
        cell.setData("nieuw gegeven");
        xldoc.setCellData(cell, "Sheet1", 1, 2);
        Document doc = xldoc.getDocument();
        String xml = serialize(doc);
        
        //System.out.println(xml);
    }
    
    public void testGetRow() throws Exception {
        XLDocument xldoc = new XLDocument("test_xls/test08.xls");
        xldoc.getRowElement("Sheet1", 2);
        Document doc = xldoc.getDocument();
        String xml = serialize(doc);
        
        //System.out.println(xml);
    }
    
    public void testGetCell() throws Exception {
        XLDocument xldoc = new XLDocument("test_xls/test08.xls");
        xldoc.getCellElement("Sheet1", 2, 5);
        Document doc = xldoc.getDocument();
        String xml = serialize(doc);
        
        //System.out.println(xml);
    }
    
    private void serialize(Document doc, String fileName) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        OutputStream out = new FileOutputStream(fileName);       
        serialize(doc, out);
        out.close();
    }
    
    private String serialize(Document doc) throws IOException {
        StringWriter sw = new StringWriter();
        Properties props = new Properties();
        props.setProperty(OutputKeys.INDENT, "no");
        props.setProperty(OutputKeys.ENCODING, "US-ASCII");
        SerializerToXML s = new SerializerToXML();
        s.init(sw, props);
        s.serialize(doc);
        return sw.toString();
    }

    private void serialize(Document doc, OutputStream out) throws UnsupportedEncodingException, IOException {
        Properties props = new Properties();
        props.setProperty(OutputKeys.INDENT, "yes");
        props.setProperty(OutputKeys.ENCODING, "US-ASCII");
        SerializerToXML s = new SerializerToXML();
        s.init(out, props);
        s.serialize(doc);
    }

}
