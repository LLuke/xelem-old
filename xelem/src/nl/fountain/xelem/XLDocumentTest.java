/*
 * Created on Dec 24, 2004
 *
 */
package nl.fountain.xelem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.SSCell;
import nl.fountain.xelem.excel.ss.SSRow;

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
    private boolean toFile = true;
    
    private String templateFile;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(XLDocumentTest.class);
    }
    
    public void setUp() {
        templateFile =
            this.getClass().getClassLoader()
            .getResource("nl/fountain/xelem/aviso.xml")
            .getFile();
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
            XLDocument xldoc = new XLDocument(templateFile);
            assertNotNull(xldoc.getDocument());
        } catch (XelemException e1) {
            fail(e1.getMessage());
        }
    }
    
    public void testGetTable() throws Exception {
        XLDocument xldoc = new XLDocument(templateFile);
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
        XLDocument xldoc = new XLDocument(templateFile);
        //XLDocument xldoc = new XLDocument("D:/test/book2.xml");
        xldoc.appendRow("Sheet1", row);
        Document doc = xldoc.getDocument();
        String xml = serialize(doc);
        //System.out.println(xml);
        String expected = "<Data ss:Type=\"Number\">-12345.67</Data>";
        assertTrue(xml.indexOf(expected) > 0);
        
        if (toFile) serialize(doc, testFileDir + "/aviso01.xls");
    }
    
    public void testAppendRows() throws Exception {
       Collection rows = new ArrayList();
       Row row = new SSRow();
       row.addCell("501000");
       row.addCell("wk 02, Claude 5,8 u.");
       row.addCell("123456");
       row.addCell(45.67);
       rows.add(row);
       row = new SSRow();
       row.addCell("551000");
       row.addCell("wk 02, Claude voor 123456 5,8 u.");
       row.addCell("002020");
       row.addCell(-45.67);
       rows.add(row);
       
       XLDocument xldoc = new XLDocument(templateFile);
       xldoc.appendRows("Sheet1", rows);
       
       Cell cell = new SSCell();
       cell.setData(new Date());
       xldoc.setCellData(cell, "Sheet1", 7, 2);
       
       Document doc = xldoc.getDocument();
       String xml = serialize(doc);
       //System.out.println(xml);
       String expected = "<Data ss:Type=\"Number\">-45.67</Data>";
       assertTrue(xml.indexOf(expected) > 0);
       expected = "<Data ss:Type=\"String\">wk 02, Claude voor 123456 5,8 u.</Data>";
       assertTrue(xml.indexOf(expected) > 0);
       
       if (toFile) serialize(doc, testFileDir + "/aviso02.xls");
    }
    
    public void testSetCellData() throws Exception {
        XLDocument xldoc = new XLDocument(templateFile);
        Cell cell = new SSCell();
        cell.setData("nieuw gegeven");
        xldoc.setCellData(cell, "Sheet1", 1, 2);
        Document doc = xldoc.getDocument();
        String xml = serialize(doc);
        String expected = "<Data ss:Type=\"String\">nieuw gegeven</Data>";
        assertTrue(xml.indexOf(expected) > 0);
        //System.out.println(xml);
    }
    
//    public void testSetCellDataSpeed() throws Exception {
//        XLDocument xldoc = new XLDocument(templateFile);
//        Cell cell = new SSCell();
//        int itters = 1000;
//        long start = System.currentTimeMillis();
//        for (int i = 1; i <= itters; i++) {
//            cell.setData(i);
//            xldoc.setCellData(cell, "Sheet2", i, 1);
//        }
//        long timeXLDoc = System.currentTimeMillis() - start;
//        Workbook wb = new XLWorkbook("timetest");
//        Worksheet sheet = wb.addSheet();
//        start = System.currentTimeMillis();
//        for (int i = 1; i <= itters; i++) {
//            sheet.addCell(i);
//            sheet.getCellPointer().moveCRLF();
//        }
//        long timeXelem = System.currentTimeMillis() - start;
//        System.out.println();
//        System.out.println("setCellData: " + timeXLDoc + " ms.");
//        System.out.println("addCell    : " + timeXelem + " ms.");
//    }
    
    
    public void testPivot() throws Exception {
        if (toFile) {
	        Object[][] data = {
	        	{"koffie", "java", "Sun", new Double(2.95), new Double(55.6)},
	        	{"koffie", "arabica", "Sun", new Double(3.10), new Double(123.5)},
	        	{"thee", "ceylon", "Sun", new Double(3.21), new Double(20.356)},
	        	{"soep", "tomaten", "Moon", new Double(4.23), new Double(456)},
	        	{"soep", "groente", "Moon", new Double(4.21), new Double(789)}
	        };
	        
	        Collection rows = new ArrayList();
	        for (int r = 0; r < data.length; r++) {
	            Row row = new SSRow();
	            for (int c = 0; c < data[r].length; c++) {
	                row.addCell(data[r][c]);
	            }
	            rows.add(row);
	        }
	        
	        String template =
	            this.getClass().getClassLoader()
	            .getResource("nl/fountain/xelem/prices0.xml")
	            .getFile();
	        XLDocument xlDoc = new XLDocument(template);
	        Cell cel = new SSCell();
	        cel.setData("created on " + new Date());
	        xlDoc.setCellData(cel, "average prices", 1, 5);
	        
	        xlDoc.appendRows("data", rows);
	        String fileName = testFileDir + "/prices.xls";
	        File out = new File(fileName);
	        xlDoc.setConsilidationReferenceFileName(out.getName());
	        
	        XSerializer xs = new XSerializer(XSerializer.US_ASCII);
	        xs.serialize(xlDoc.getDocument(), out);
        }
    }

    
    private void serialize(Document doc, String fileName) throws Exception {
        OutputStream out = new FileOutputStream(fileName);       
        serialize(doc, out);
        out.close();
    }
    
    private String serialize(Document doc) throws Exception {
        XSerializer xs = new XSerializer(XSerializer.US_ASCII);
        StringWriter sw = new StringWriter();
        xs.serialize(doc, sw);
        return sw.toString();
    }

    private void serialize(Document doc, OutputStream out) throws Exception {
        XSerializer xs = new XSerializer(XSerializer.US_ASCII);
        xs.serialize(doc, out);
    }

}
