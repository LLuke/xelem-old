/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.File;
import java.util.Iterator;

import junit.framework.TestCase;
import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;

import org.xml.sax.SAXParseException;

/**
 *
 */
public class ExcelReaderTest extends TestCase {
    
    private Workbook readerwb;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ExcelReaderTest.class);
    }
    
    public void testConstructor() throws Exception {
        ExcelReader xlr = new ExcelReader();
        // leeds to different results depending on the JRE being used
        // 1.4: org.apache.crimson.jaxp.SAXParserImpl
        // 1.5: com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl
        //System.out.println(xlr.getSaxParser().getClass());
        assertNotNull(xlr.getSaxParser());
    }
    
    public void testGetWorkbookName() throws Exception {
        ExcelReader xlr = new ExcelReader();
        String systemId = "A:/foo/bar/file.xml";
        assertEquals("file", xlr.getWorkbookName(systemId));
        systemId = "source";
        assertEquals("source", xlr.getWorkbookName(systemId));
        systemId = ".xml";
        assertEquals("", xlr.getWorkbookName(systemId));
        systemId = "";
        assertEquals("", xlr.getWorkbookName(systemId));
        systemId = "A:/foo/bar/.";
        assertEquals("", xlr.getWorkbookName(systemId));
    }
    
    public void testReadNoXML() throws Exception {
        ExcelReader xlr = new ExcelReader();
        
        try {
            xlr.read("testsuitefiles/ReaderTest/excel.xls");
        } catch (SAXParseException e) {
            assertEquals(1, e.getLineNumber());
        } catch (Exception e2) {
            // under java 1.5 a 
            // com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException
            // is thrown and *not* through the fatalError method of the ErrorHandler.
            // MalformedByteSequenceException is unknown in java 1.4
            //
            //System.out.println(e2.getClass());
            //assertEquals(MalformedByteSequenceException.class, e2.getClass());
        }
    }
    
    public void testReadInvalidXML() throws Exception {
        ExcelReader xlr = new ExcelReader();
        try {
            xlr.read("testsuitefiles/ReaderTest/invalid.xml");
        } catch (SAXParseException e) {
            //System.out.println(e.getMessage());
            assertEquals(11, e.getLineNumber());
        }
    }
    
    public void testRead() throws Exception {
        Workbook wb = getReaderWorkbook();
        assertNotNull(wb);
        assertEquals("reader", wb.getName());
        //System.out.println(wb.getFileName());
//        Map map = xlr.getUris();
//        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
//            Object element = iter.next();
//            System.out.println(element + "=" + map.get(element));
//        }
    }
    
    private Workbook getReaderWorkbook() throws Exception {
        if (readerwb == null) {
	        ExcelReader xlr = new ExcelReader();
	        readerwb = xlr.read("testsuitefiles/ReaderTest/reader.xml");
        }
        return readerwb;
    }
    
    public void testReWrite() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.read("testsuitefiles/ReaderTest/reader.xml");
        File out = new File("testoutput/ReaderTest/rewrite.xls");
        new XSerializer().serialize(wb, out);
    }

    public void testDocumentProperties() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.read("testsuitefiles/ReaderTest/docprops.xml");
        DocumentProperties props = wb.getDocumentProperties();
        
        assertEquals(1110888086000L, props.getCreated().getTime());
        assertEquals(1110889149000L, props.getLastSaved().getTime());
        assertEquals(1110979976000L, props.getLastPrinted().getTime());
        assertEquals("Title for docprops    ", props.getTitle());
        assertEquals("a test file", props.getSubject());
        assertEquals("ExcelReader                 tester", props.getAuthor());
        assertEquals("java xml bla bla", props.getKeywords());
        char cr = 13;
        char lf = 10;
        assertEquals("testing ë é ç ƒ Ñ documentproperties"
                + cr + lf + "more comments"
                + cr + lf + "and more...", props.getDescription());
        assertEquals("Tom Poes", props.getLastAuthor());
        assertNull(props.getAppName());
        assertEquals("NIWI-KNAW", props.getCompany());
        assertEquals("Asterix", props.getManager());
        assertEquals("foo", props.getCategory());
        assertEquals("http://xelem.sourceforge.net/", props.getHyperlinkBase());
        assertEquals("11.5703", props.getVersion());
    }
    
    public void testExcelWorkbook() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.read("testsuitefiles/ReaderTest/excelworkbook.xml");
        ExcelWorkbook exw = wb.getExcelWorkbook();
        
        assertEquals(0, exw.getActiveSheet());
        assertEquals(8835, exw.getWindowHeight());
        assertEquals(120, exw.getWindowTopX());
        assertEquals(90, exw.getWindowTopY());
        assertEquals(15180, exw.getWindowWidth());
        assertTrue(!exw.getProtectStructure());
        assertTrue(exw.getProtectWindows());
    }
    
    public void testExcelWorkbook2() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.read("testsuitefiles/ReaderTest/excelworkbook2.xml");
        
        assertTrue(wb.hasExcelWorkbook());
        assertTrue(!wb.hasDocumentProperties());
    }
    
    
    public void testWorksheet() throws Exception {
        Workbook wb = getReaderWorkbook();
        Iterator iter = wb.getSheetNames().iterator();
        
        assertEquals("Tom Poes", iter.next());
        assertEquals("Donald Duck", iter.next());
        assertEquals("Asterix", iter.next());
        
        Worksheet sheet = wb.getWorksheet("Donald Duck");
        assertTrue(sheet.isProtected());
        sheet = wb.getWorksheet("Asterix");
        assertTrue(sheet.isRightToLeft());
    }
    
    public void testTable() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        assertEquals(10, table.getExpandedColumnCount());
        assertEquals(25, table.getExpandedRowCount());
        assertEquals("s21", table.getStyleID());
    }
    
    public void testColumn() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        Column column1 = table.getColumnAt(1);
        assertEquals("s22", column1.getStyleID());
        assertTrue(!column1.getAutoFitWith());
        assertEquals(0, column1.getSpan());
        assertEquals(78.75D, column1.getWidth(), 0.0D);
        
        Column column7 = table.getColumnAt(7);
        assertEquals("s22", column7.getStyleID());
        assertTrue(column7.getAutoFitWith());
        
        Column column9 = table.getColumnAt(9);
        assertEquals(1, column9.getSpan());
        
        assertEquals(3, table.getColumns().size());
    }
    
    public void testRow() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        assertTrue(!table.hasRowAt(1));
        assertTrue(table.hasRowAt(3));
        
        Row row3 = table.getRowAt(3);
        assertEquals("s24", row3.getStyleID());
        assertEquals(27.0D, row3.getHeight(), 0.0D);
        assertTrue(row3.isHidden());
        assertEquals(0, row3.getSpan());
        
        assertTrue(table.hasRowAt(8));
        assertTrue(table.hasRowAt(9));
        assertTrue(table.hasRowAt(20));
        assertTrue(table.hasRowAt(22));
        
        assertEquals(8, table.getRows().size());
    }
    
    public void testCell() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        Row row3 = table.getRowAt(3);
        assertTrue(row3.hasCellAt(1));
        Cell cell = row3.getCellAt(1);
        assertEquals("String", cell.getXlDataType());
        assertEquals("blaadje 1", cell.getData$());
        assertTrue(cell.hasData());
        assertTrue(!cell.hasError());
        
        cell = table.getRowAt(6).getCellAt(2);
        assertEquals("=R[-2]C[1]+R[-1]C[1]", cell.getFormula());
        assertEquals("Number", cell.getXlDataType());
        assertEquals("0", cell.getData$());
        
        cell = table.getRowAt(10).getCellAt(2);
        assertEquals("=5/R[-4]C", cell.getFormula());
        assertEquals("Error", cell.getXlDataType());
        assertEquals("#DIV/0!", cell.getData$());
        assertTrue(cell.hasData());
        assertTrue(cell.hasError());
        
        cell = table.getRowAt(20).getCellAt(2);
        assertEquals(1, cell.getMergeAcross());
        assertEquals(5, cell.getMergeDown());
        assertEquals("", cell.getData$());
        assertTrue(!cell.hasData());
    }
    
    

}
