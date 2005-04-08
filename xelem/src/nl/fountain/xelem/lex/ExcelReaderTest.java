/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;
import nl.fountain.xelem.Area;
import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Column;
import nl.fountain.xelem.excel.Comment;
import nl.fountain.xelem.excel.DocumentProperties;
import nl.fountain.xelem.excel.ExcelWorkbook;
import nl.fountain.xelem.excel.NamedRange;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.WorksheetOptions;

import org.xml.sax.SAXParseException;

/**
 *
 */
public class ExcelReaderTest extends TestCase {
    
    private static Workbook readerwb;

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
            xlr.getWorkbook("testsuitefiles/ReaderTest/excel.xls");
            fail("should have thrown Exception");
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
            xlr.getWorkbook("testsuitefiles/ReaderTest/invalid.xml");
            fail("should have thrown Exception");
        } catch (SAXParseException e) {
            //System.out.println(e.getMessage());
            assertEquals(11, e.getLineNumber());
        }
    }
    
    public void testRead() throws Exception {
        Workbook wb = getReaderWorkbook();
        assertNotNull(wb);
        assertEquals("reader", wb.getName());
        
    }
    
    private Workbook getReaderWorkbook() throws Exception {
        if (readerwb == null) {
	        ExcelReader xlr = new ExcelReader();
	        readerwb = xlr.getWorkbook("testsuitefiles/ReaderTest/reader.xml");
	        
        }
        return readerwb;
    }
    
    public void testReWrite() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.getWorkbook("testsuitefiles/ReaderTest/reader.xml");
        File out = new File("testoutput/ReaderTest/rewrite.xls");
        XSerializer xs = new XSerializer();
        xs.serialize(wb, out);
        
        // try read another
        wb = xlr.getWorkbook("testsuitefiles/ReaderTest/docprops.xml");
        out = new File("testoutput/ReaderTest/docprops_r.xls");
        xs.serialize(wb, out);
    }
    
    

    public void testDocumentProperties() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Workbook wb = xlr.getWorkbook("testsuitefiles/ReaderTest/docprops.xml");
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
        Workbook wb = xlr.getWorkbook("testsuitefiles/ReaderTest/excelworkbook.xml");
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
        Workbook wb = xlr.getWorkbook("testsuitefiles/ReaderTest/excelworkbook2.xml");
        
        assertTrue(wb.hasExcelWorkbook());
        assertTrue(!wb.hasDocumentProperties());
        
        Map prfxs = xlr.getPrefixMap();
        assertEquals("urn:schemas-microsoft-com:office:spreadsheet", prfxs.get(""));
        assertEquals("urn:schemas-microsoft-com:office:office", prfxs.get("o"));
        assertEquals("http://www.w3.org/TR/REC-html40", prfxs.get("html"));
        assertEquals("urn:schemas-microsoft-com:office:spreadsheet", prfxs.get("ss"));
        assertEquals("urn:schemas-microsoft-com:office:excel", prfxs.get("x"));
    }
    
    public void testNamedRange() throws Exception {
        Workbook wb = getReaderWorkbook();
        Map map = wb.getNamedRanges();
        assertEquals(1, map.size());
        NamedRange nr = (NamedRange) map.get("foo");
        assertEquals("foo", nr.getName());
        assertEquals("='Tom Poes'!R9C4:R11C4", nr.getRefersTo());
        assertTrue(!nr.isHidden());
    }
    
    public void testWorksheet() throws Exception {
        Workbook wb = getReaderWorkbook();
        Iterator iter = wb.getSheetNames().iterator();
        
        assertEquals("Tom Poes", iter.next());
        assertEquals("Donald Duck", iter.next());
        assertEquals("Asterix", iter.next());
        assertEquals("Sponge Bob", iter.next());
        
        Worksheet sheet = wb.getWorksheet("Donald Duck");
        assertTrue(sheet.isProtected());
        sheet = wb.getWorksheet("Asterix");
        assertTrue(sheet.isRightToLeft());
    }
    
    public void testGetWorksheetAt() throws Exception {
        String[] sheetNames = { "Tom Poes", "Donald Duck", "Asterix",
                "Sponge Bob", "window" };
        Workbook wb = getReaderWorkbook();
        Worksheet sheet;
        int i = 0;
        while ((sheet = wb.getWorksheetAt(i)) != null) {
            assertEquals(sheetNames[i++], sheet.getName());
        }
        assertEquals(5, i);
        assertNull(wb.getWorksheetAt(i));
    }
    
    public void testNamedRangeOnWorksheet() throws Exception {
        Workbook wb = getReaderWorkbook();
        Worksheet sheet = wb.getWorksheet("Tom Poes");
        Map map = sheet.getNamedRanges();
        assertEquals(1, map.size());
        NamedRange nr = (NamedRange) map.get("_FilterDatabase");
        assertEquals("_FilterDatabase", nr.getName());
        assertEquals("='Tom Poes'!R8C4:R11C5", nr.getRefersTo());
        assertTrue(nr.isHidden());
    }
    
    public void testTable() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        assertEquals(14, table.getExpandedColumnCount());
        assertEquals(25, table.getExpandedRowCount());
        assertEquals("s22", table.getStyleID());
    }
    
    public void testColumn() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        Column column1 = table.getColumnAt(1);
        assertEquals("s23", column1.getStyleID());
        assertTrue(!column1.getAutoFitWith());
        assertEquals(0, column1.getSpan());
        assertEquals(78.75D, column1.getWidth(), 0.0D);
        
        Column column7 = table.getColumnAt(7);
        assertEquals("s23", column7.getStyleID());
        assertTrue(column7.getAutoFitWith());
        
        Column column9 = table.getColumnAt(9);
        assertEquals(1, column9.getSpan());
        Column c9 = wb.getWorksheet("Tom Poes").getColumnAt("i");
        assertSame(column9, c9);
        
        assertEquals(5, table.getColumns().size());
    }
    
    public void testRow() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        assertTrue(!table.hasRowAt(1));
        assertTrue(table.hasRowAt(3));
        
        Row row3 = table.getRowAt(3);
        assertEquals("s25", row3.getStyleID());
        assertEquals(27.0D, row3.getHeight(), 0.0D);
        assertTrue(row3.isHidden());
        assertEquals(0, row3.getSpan());
        
        assertTrue(table.hasRowAt(8));
        assertTrue(table.hasRowAt(9));
        assertTrue(table.hasRowAt(20));
        assertTrue(table.hasRowAt(22));
        
        assertEquals(13, table.getRows().size());
    }
    
    public void testCell() throws Exception {
        Workbook wb = getReaderWorkbook();
        Table table = wb.getWorksheet("Tom Poes").getTable();
        
        Row row3 = table.getRowAt(3);
        assertTrue(row3.hasCellAt(1));
        Cell cell = row3.getCellAt(1);
        assertEquals("String", cell.getXLDataType());
        assertEquals("blaadje 1", cell.getData$());
        assertTrue(cell.hasData());
        assertTrue(!cell.hasError());
        
        cell = table.getRowAt(6).getCellAt(2);
        assertEquals("=R[-2]C[1]+R[-1]C[1]", cell.getFormula());
        assertEquals("Number", cell.getXLDataType());
        assertEquals("0", cell.getData$());
        
        cell = table.getRowAt(10).getCellAt(2);
        assertEquals("=5/R[-4]C", cell.getFormula());
        assertEquals("Error", cell.getXLDataType());
        assertEquals("#DIV/0!", cell.getData$());
        assertTrue(cell.hasData());
        assertTrue(cell.hasError());
        
        cell = table.getRowAt(20).getCellAt(2);
        assertEquals(1, cell.getMergeAcross());
        assertEquals(5, cell.getMergeDown());
        assertEquals("foo", cell.getData$());
        assertTrue(cell.hasData());
        assertEquals("#foo", cell.getHRef());
    }
    
    public void testCellValues() throws Exception {
        Workbook wb = getReaderWorkbook();
        Worksheet sheet = wb.getWorksheet("Tom Poes");
        
        Cell cell = sheet.getCellAt(8, 9);
        assertTrue(cell.booleanValue());
        Boolean boo = (Boolean) cell.getData();
        assertTrue(boo.booleanValue());
        assertEquals(1, cell.intValue());
        assertEquals(1.0, cell.doubleValue(), 0.0D);
        
        cell = sheet.getCellAt(9, 9);
        assertFalse(cell.booleanValue());
        boo = (Boolean) cell.getData();
        assertFalse(boo.booleanValue());
        assertEquals(0, cell.intValue());
        assertEquals(0.0, cell.doubleValue(), 0.0);
        
        cell = sheet.getCellAt(10, 9);
        assertFalse(cell.booleanValue());
        assertEquals(0, cell.intValue());
        Date date = (Date) cell.getData();
        assertEquals(1112306400000L, date.getTime());
        
        cell = sheet.getCellAt(11, 9);
        date = (Date) cell.getData();
        assertEquals(-2209033800000L, date.getTime());
        assertEquals("1899-12-31T12:30:00.000", cell.getData$());
        //System.out.println(date);
        
        cell = sheet.getCellAt(12, 9);
        assertEquals(1, cell.intValue());
        assertEquals(1.234, cell.doubleValue(), 0.0);
        assertFalse(cell.booleanValue());
        
        cell = sheet.getCellAt(13, 9);
        Double doo = (Double) cell.getData();
        assertEquals(5, doo.intValue());
        assertEquals(5.0, cell.doubleValue(), 0.0D);
        assertEquals(5, cell.intValue());
        
        cell = sheet.getCellAt(14, 9);
        assertEquals("#NAME?", cell.getData());
        
        cell = sheet.getCellAt(15, 9);
        assertEquals("xelem", cell.getData());
    }
    
    public void testWorksheetOptions() throws Exception {
        Workbook wb = getReaderWorkbook();
        
        Worksheet sheet = wb.getWorksheet("Tom Poes");        
        assertTrue(sheet.hasWorksheetOptions());
        WorksheetOptions wso = sheet.getWorksheetOptions();
        assertTrue(wso.isSelected());
        assertEquals(6, wso.getTopRowVisible());
        assertEquals(1, wso.getLeftColumnVisible());
        assertEquals(75, wso.getZoom());
        assertEquals(-1, wso.getTabColorIndex());
        assertTrue(!wso.displaysNoHeadings());
        assertTrue(!wso.displaysNoGridlines());
        assertTrue(!wso.displaysFormulas());
        assertEquals("SheetVisible", wso.getVisible());
        assertNull(wso.getGridlineColor());
        
        sheet = wb.getWorksheet("Donald Duck");
        wso = sheet.getWorksheetOptions();
        assertTrue(!wso.isSelected());
        assertTrue(wso.displaysNoHeadings());
        assertTrue(wso.displaysNoGridlines());
        
        sheet = wb.getWorksheet("Asterix");
        wso = sheet.getWorksheetOptions();
        assertTrue(!wso.isSelected());
        assertEquals(12, wso.getTabColorIndex());
        assertTrue(wso.displaysFormulas());
        assertEquals("#FF0000", wso.getGridlineColor());
        
        sheet = wb.getWorksheet("Sponge Bob");
        wso = sheet.getWorksheetOptions();
        assertEquals("SheetHidden", wso.getVisible());
    }
    
    public void testAutoFilter() throws Exception {
        Workbook wb = getReaderWorkbook();
        
        Worksheet sheet = wb.getWorksheet("Tom Poes");  
        assertTrue(sheet.hasAutoFilter());
        sheet = wb.getWorksheet("Sponge Bob");
        assertTrue(!sheet.hasAutoFilter());
    }
    
    public void testComment() throws Exception {
        Workbook wb = getReaderWorkbook();
        Worksheet sheet = wb.getWorksheet("Tom Poes");
        Cell cell = sheet.getCellAt(16, 5);
        
        assertTrue(cell.hasComment());
        Comment comment = cell.getComment();
        assertTrue(comment.showsAlways());
        assertEquals("WF Hermans", comment.getAuthor());
        char lf = 10;
        assertEquals("WF Hermans:" + lf + "this is comment", comment.getData());
        assertEquals("this is comment", comment.getDataClean());
    }
    
    public void testPartialRead() throws Exception {
        ExcelReader xlr = new ExcelReader();
        Area area = new Area("E11:M16");
        xlr.setBuildArea(area);
        Workbook wb = xlr.getWorkbook("testsuitefiles/ReaderTest/reader.xml");        
        Worksheet sheet = wb.getWorksheetAt(0);
        assertFalse(sheet.hasColumnAt("A"));
        assertTrue(sheet.hasColumnAt("G"));
        assertFalse(sheet.hasRowAt(10));
        assertTrue(sheet.hasRowAt(11));
        assertTrue(sheet.hasRowAt(16));
        assertFalse(sheet.hasRowAt(20));
        assertEquals("c", sheet.getCellAt("E11").getData());
        Date date = (Date) sheet.getCellAt("I11").getData();
        assertEquals(-2209033800000L, date.getTime());
        
        int i = 0;
        while ((sheet = wb.getWorksheetAt(i++)) != null) {
            // cannot set proper rangeselection on split sheets
            if (!sheet.getWorksheetOptions().hasSplit()) {
	            sheet.getWorksheetOptions().setRangeSelection(area);
	            sheet.getWorksheetOptions().setActiveCell(14, 8);
	            sheet.addCellAt(1, 1).setData("only the selected area has been read");
            } else {
                sheet.addCellAt(1, 1).setData(
                        "only " + area.getA1Reference() + " has been read");
            }
        }
        File out = new File("testoutput/ReaderTest/partial.xls");
        new XSerializer().serialize(wb, out);
    }

}
