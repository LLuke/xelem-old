/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.Workbook;

import org.xml.sax.SAXParseException;

import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

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
            // is thrown and *not* through the fatalError method of the ErrorHandler
            //System.out.println(e2.getClass());
            assertEquals(MalformedByteSequenceException.class, e2.getClass());
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
    }
    
    public void testDocumentProperties() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.read("testsuitefiles/ReaderTest/docprops.xml");
        DocumentProperties props = wb.getDocumentProperties();
        
        //System.out.println(props.getCreated().getTime());
        assertEquals(1110888086000L, props.getCreated().getTime());
        assertEquals("Title for docprops    ", props.getTitle());
        assertEquals("a test file", props.getSubject());
        assertEquals("ExcelReader                 tester", props.getAuthor());
        assertEquals("java xml bla bla", props.getKeywords());
        assertEquals("testing ë é ç ƒ Ñ documentproperties", props.getDescription());
        //assertEquals("Tom Poes", props.getLastAuthor());
        
    }

}
