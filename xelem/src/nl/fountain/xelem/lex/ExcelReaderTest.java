/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.Iterator;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;

import org.xml.sax.SAXParseException;

/**
 *
 */
public class ExcelReaderTest extends TestCase {

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
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.read("testsuitefiles/ReaderTest/reader.xml");
        assertNotNull(wb);
        assertEquals("reader.xml", wb.getName());
        //System.out.println(wb.getFileName());
//        Map map = xlr.getUris();
//        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
//            Object element = iter.next();
//            System.out.println(element + "=" + map.get(element));
//        }
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
    
    public void testWorksheet() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.read("testsuitefiles/ReaderTest/reader.xml");
        Iterator iter = wb.getSheetNames().iterator();
        
        assertEquals("Tom Poes", iter.next());
        assertEquals("Donald Duck", iter.next());
        assertEquals("Asterix", iter.next());
        
        Worksheet sheet = wb.getWorksheet("Donald Duck");
        assertTrue(sheet.isProtected());
        sheet = wb.getWorksheet("Asterix");
        assertTrue(sheet.isRightToLeft());
    }
    
    

}
