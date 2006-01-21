/*
 * Created on May 19, 2005
 *
 */
package nl.fountain.vtks.trash;

import java.util.Iterator;

import junit.framework.TestCase;
import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.excel.AutoFilter;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;

/**
 *
 */
public class HeaderReaderTest extends TestCase {
    
    private static String fileLocation = "testsuitefiles/emptyrowfilter/";
    private static String outputLocation = "testoutput/emptyrowfilter/";

    public static void main(String[] args) {
        junit.textui.TestRunner.run(HeaderReaderTest.class);
    }
    
    public void testNoReading() throws Exception {
        HeaderReader hr = new HeaderReader();
        assertNull(hr.getWorkbook());
    }
    
    public void testGetWorkbook() throws Exception {
        HeaderReader hr = new HeaderReader();
        hr.read(fileLocation + "er.xml", 0, 2);
        Workbook wb = hr.getWorkbook();
        Worksheet sheet = wb.getWorksheetAt(0);
        assertEquals(6, sheet.getRows().size());
        int[] passedRows = new int[] {1, 2, 4, 5, 6, 7};
        Iterator iter = sheet.getRows().iterator();
        for (int i = 0; i < passedRows.length; i++) {
            Row row = (Row) iter.next();
            assertEquals(passedRows[i], row.getIndex());
        }
    }
    
    public void test4Real() throws Exception {
        HeaderReader hr = new HeaderReader();
        hr.read(fileLocation + "vt1869.xml", 0, 2);
        Workbook wb = hr.getWorkbook();
        Worksheet sheet;
        int i = 0;
        while ((sheet = wb.getWorksheetAt(i++)) != null) {
            AutoFilter af = null;
           sheet.setAutoFilter(af); 
        }
        wb.setFileName(outputLocation + "vt1869_.xml");
        new XSerializer().serialize(wb);
    }

}
