/*
 * Created on 6-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.List;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Workbook;

/**
 *
 */
public class ExcelReaderListenerTest extends TestCase {
    
    protected String sysId;
    protected String wbName;
    protected int wbCounter;
    protected DocumentProperties props;
    protected int propsCounter;
    protected ExcelWorkbook excelWB;
    protected int excelWBCounter;
    protected NamedRange wbNR;
    protected int wbNRCounter;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ExcelReaderListenerTest.class);
    }
    
    public void testAddRemoveClearListener() throws Exception {
        ExcelReader xlReader = new ExcelReader();
        List listeners = xlReader.getListeners();
        assertEquals(0, listeners.size());
        
        Listener listener = new Listener();
        xlReader.addExcelReaderListener(listener);
        assertEquals(1, listeners.size());
        xlReader.addExcelReaderListener(listener);
        assertEquals(1, listeners.size());
        
        Listener listener2 = new Listener();
        xlReader.addExcelReaderListener(listener2);
        assertEquals(2, listeners.size());
        assertSame(listener, listeners.get(0));
        assertSame(listener2, listeners.get(1));
        
        assertTrue(xlReader.removeExcelReaderListener(listener));
        assertFalse(xlReader.removeExcelReaderListener(new Listener()));
        assertEquals(1, listeners.size());
        
        xlReader.clearExcelReaderListeners();
        assertEquals(0, listeners.size());
    }
    
    public void testListener() throws Exception {
        ExcelReader xlReader = new ExcelReader();
        Listener listener = new Listener();
        xlReader.addExcelReaderListener(listener);
        assertFalse(xlReader.isListeningOnly());
        xlReader.setListenOnly(true);
        assertTrue(xlReader.isListeningOnly());
        
        Workbook wb = null;
        try {
            wb = xlReader.read("testsuitefiles/ReaderTest/reader.xml");
        } catch (NullPointerException e) {
            //
        }
        
        assertNotNull(wb);
        assertTrue(!wb.hasDocumentProperties());
        assertTrue(!wb.hasExcelWorkbook());
        assertEquals(0, wb.getNamedRanges().size());
        assertEquals(1, wbCounter);
        assertTrue(sysId.endsWith("testsuitefiles/ReaderTest/reader.xml"));
        assertEquals("reader", wbName);
        
        assertEquals(1, propsCounter);
        assertEquals("Asterix", props.getAuthor());
        assertEquals(1, excelWBCounter);
        assertEquals(360, excelWB.getWindowTopX());
        assertEquals(1, wbNRCounter);
        assertEquals("='Tom Poes'!R9C4:R11C4", wbNR.getRefersTo());
        assertEquals("foo", wbNR.getName());
        
    }
    
    
    private class Listener implements ExcelReaderListener {

        public void setWorkbook(String systemID, String workbookName) {
            sysId = systemID;
            wbName = workbookName;
            wbCounter++;
        }

        public void setDocumentProperties(DocumentProperties docprops) {
            assertTrue(sysId.endsWith("testsuitefiles/ReaderTest/reader.xml"));
            assertEquals("reader", wbName);
            props = docprops;
            propsCounter++;
        }

        public void setExcelWorkbook(ExcelWorkbook xlwb) {
            assertEquals("Asterix", props.getAuthor());
            excelWB = xlwb;
            excelWBCounter++;
        }

        public void setNamedRange(Workbook workbook, NamedRange nr) {
            assertEquals(360, excelWB.getWindowTopX());
            wbNR = nr;
            wbNRCounter++;
        }
        
    }

}
