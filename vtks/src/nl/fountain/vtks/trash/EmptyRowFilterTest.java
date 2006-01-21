/*
 * Created on May 17, 2005
 *
 */
package nl.fountain.vtks.trash;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.lex.DefaultExcelReaderListener;
import nl.fountain.xelem.lex.ExcelReader;

/**
 *
 */
public class EmptyRowFilterTest extends TestCase {
    
    private static String fileLocation = "testsuitefiles/emptyrowfilter/";
    int rowCount;
    private ExcelReader reader;
    private EmptyRowFilter filter;
    int[] passedRows;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(EmptyRowFilterTest.class);
    }
    
    // @see junit.framework.TestCase#setUp()
    protected void setUp() throws Exception {
        reader = new ExcelReader();
        filter = new EmptyRowFilter(reader);       
        filter.addExcelReaderListener(new TestListener());
        reader.addExcelReaderListener(filter);
    }
    
    public void testEmptyRowCount0() throws Exception {
        passedRows = new int[] {};
        reader.read(fileLocation + "er.xml");
        assertEquals(passedRows.length, rowCount);
    }
    
    public void testEmptyRowCount1() throws Exception {
        filter.setRowIndex(0, 2);
        passedRows = new int[] {1, 2, 4, 5, 6, 7, 1, 7};
        reader.read(fileLocation + "er.xml");
        assertEquals(passedRows.length, rowCount);
    }
    
    public void testEmptyRowCount2() throws Exception {
        filter.setRowIndex(0, 1);
        passedRows = new int[] {1, 2, 4, 1};
        reader.read(fileLocation + "er.xml");
        assertEquals(passedRows.length, rowCount);
    }
    
    public void testEmptyRowCount3() throws Exception {
        filter.setRowIndex(2, 5);
        passedRows = new int[] {9, 10, 13, 9, 17, 18, 19};
        reader.read(fileLocation + "er.xml");
        assertEquals(passedRows.length, rowCount);
    }
    
    class TestListener extends DefaultExcelReaderListener {
        
        TestListener() {
            rowCount = 0;
        }
        
        public void setRow(int sheetIndex, String sheetName, Row row) {           
            //System.out.println("sheetName=" + sheetName + " rowIndex=" + row.getIndex());
            assertEquals(passedRows[rowCount++], row.getIndex());
        }
        
    }

}
