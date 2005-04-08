/*
 * Created on 6-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.List;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Row;
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
    protected NamedRange workbookNamedRange;
    protected int workbookNamedRangeCounter;
    protected String lastWorksheetName;
    protected int lastWorksheetIndex;
    protected int worksheetCounter;
    protected NamedRange worksheetNamedRange;
    protected int worksheetNamedRangeCounter;
    protected String lastWorksheetWithNamedRange;
    protected String lastSheetWithTable;
    protected int tableCounter;
    protected int lastExpandedRowCount;
    protected int lastExpandedColumnCount;
    protected int columnCounter;
    protected int lastColumnIndex;

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
        //try {
            wb = xlReader.read("testsuitefiles/ReaderTest/reader.xml");
        //} catch (NullPointerException e) {
            //
        //}
        
        assertNull(wb);
        assertEquals(1, wbCounter);
        assertTrue(sysId.endsWith("testsuitefiles/ReaderTest/reader.xml"));
        assertEquals("reader", wbName);
        
        assertEquals(1, propsCounter);
        assertEquals("Asterix", props.getAuthor());
        
        assertEquals(1, excelWBCounter);
        assertEquals(360, excelWB.getWindowTopX());
        
        assertEquals(1, workbookNamedRangeCounter);
        assertEquals("='Tom Poes'!R9C4:R11C4", workbookNamedRange.getRefersTo());
        assertEquals("foo", workbookNamedRange.getName());
        
        assertEquals(5, worksheetCounter);
        assertEquals(4, lastWorksheetIndex);
        assertEquals("window", lastWorksheetName);
        
        assertEquals(1, worksheetNamedRangeCounter);
        assertEquals("_FilterDatabase", worksheetNamedRange.getName());
        assertTrue(worksheetNamedRange.isHidden());
        assertEquals("Tom Poes", lastWorksheetWithNamedRange);
        
        assertEquals(4, tableCounter);
        assertEquals("window", lastSheetWithTable);
        assertEquals(7, lastExpandedRowCount);
        assertEquals(4, lastExpandedColumnCount);
        
        assertEquals(5, columnCounter);
        assertEquals(14, lastColumnIndex);
    }
    
    
    private class Listener implements ExcelReaderListener {

        public void startWorkbook(String systemID, String workbookName) {
            System.out.println("Workbook sytemID=" + systemID + " name=" + workbookName);
            sysId = systemID;
            wbName = workbookName;
            wbCounter++;
        }

        public void setDocumentProperties(DocumentProperties docprops) {
            System.out.println(docprops);
            assertTrue(sysId.endsWith("testsuitefiles/ReaderTest/reader.xml"));
            assertEquals("reader", wbName);
            props = docprops;
            propsCounter++;
        }

        public void setExcelWorkbook(ExcelWorkbook xlwb) {
            System.out.println(xlwb);
            assertEquals("Asterix", props.getAuthor());
            excelWB = xlwb;
            excelWBCounter++;
        }

        public void setNamedRange(NamedRange nr) {
            System.out.println(nr);
            assertEquals(360, excelWB.getWindowTopX());
            workbookNamedRange = nr;
            workbookNamedRangeCounter++;
        }

        public void startWorksheet(int index, String sheetName) {
            System.out.println("Worksheet index=" + index + " name=" + sheetName);
            lastWorksheetIndex = index;
            lastWorksheetName = sheetName;
            worksheetCounter++;
        }

        public void setNamedRange(String sheetName, NamedRange nr) {
            System.out.println("NamedRange sheetName=" + sheetName + " " + nr);
            lastWorksheetWithNamedRange = sheetName;
            worksheetNamedRange = nr;
            worksheetNamedRangeCounter++;
        }

        public void startTable(String sheetName, 
                int expandedRowCount, int expandedColumnCount) {
            System.out.println("Table on " + sheetName 
                    + ", rowCount=" + expandedRowCount 
                    + " columnCount=" + expandedColumnCount);
            lastSheetWithTable = sheetName;
            lastExpandedRowCount = expandedRowCount;
            lastExpandedColumnCount = expandedColumnCount;
            tableCounter++;
        }

        public void setColumn(String sheetName, int columnIndex, Column column) {
            System.out.println("Column on " + sheetName
                    + ", columnIndex=" + columnIndex + " " + column);
            lastColumnIndex = columnIndex;
            columnCounter++;
        }

        public void setRow(String sheetName, int rowIndex, Row row) {
            System.out.println("Row on " + sheetName
                    + ", rowIndex =" + rowIndex + " " + row);
        }
        
    }

}
