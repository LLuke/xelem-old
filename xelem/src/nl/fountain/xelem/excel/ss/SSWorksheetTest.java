/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem.excel.ss;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Table;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.WorksheetOptions;
import nl.fountain.xelem.excel.XLElementTest;

/**
 *  
 */
public class SSWorksheetTest extends XLElementTest {

    private Worksheet ws;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SSWorksheetTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        ws = new SSWorksheet("Sheet1");
    }

    public void testConstructor() {
        assertEquals("Sheet1", ws.getName());
        Worksheet ws2 = new SSWorksheet("Blad1");
        assertEquals("Blad1", ws2.getName());
        Worksheet ws3 = new SSWorksheet("this & that");
        assertEquals("this & that", ws3.getName());
        Worksheet ws4 = new SSWorksheet(null);
        assertEquals(null, ws4.getName());
    }

    public void testGetTable() {
        assertNotNull(ws.getTable());
        Table table = ws.getTable();
        assertSame(table, ws.getTable());
        Table table3 = ws.getTable();
        assertSame(table, ws.getTable());
        assertSame(table3, ws.getTable());
    }

    public void testAddCell() {
        Cell cell = ws.addCell();
        assertNotNull(cell);
        Cell rCell = ws.getCellAt(1, 1);
        assertSame(cell, rCell);
        Cell rCell2 = ws.getTable().currentRow().getCellAt(1);
        assertSame(cell, rCell2);
    }

    public void testAddCellRC() {
        Cell cell = ws.addCellAt(5, 7);
        assertNotNull(cell);
        Cell rCell = ws.getCellAt(5, 7);
        assertSame(cell, rCell);
        Cell rCell2 = ws.getTable().currentRow().getCellAt(7);
        assertSame(cell, rCell2);
        Cell rCell3 = ws.getRow(5).getCellAt(7);
        assertSame(cell, rCell3);
    }

    public void testAddCellCell() {
        Cell cell = new SSCell();
        ws.addCell(cell);
        assertEquals(1, ws.getTable().rowCount());
        assertEquals(1, ws.currentRow().size());
        Cell rCell = ws.getCellAt(1, 1);
        assertSame(cell, rCell);
        Cell rCell2 = ws.getTable().currentRow().getCellAt(1);
        assertSame(cell, rCell2);
    }

    public void testRemoveCell() {
        assertNull(ws.removeCellAt(5, 9));
        Cell cell = ws.addCellAt(5, 9);
        Cell cell2 = ws.removeCellAt(5, 9);
        assertSame(cell, cell2);
    }

    public void testAssemble() {
        ws.addRow(5);
        ws.setProtected(true);
        ws.setRightToLeft(true);
        WorksheetOptions wso = ws.getWorksheetOptions();
        wso.setGridlineColor(0, 0, 255);
        wso.setSelected(true);
        
        GIO gio = new GIO();
        String xml = xmlToString(ws, gio);
        
        assertTrue(xml.indexOf("<ss:Worksheet ss:Name=\"Sheet1\" ss:Protected=\"1\" ss:RightToLeft=\"1\">") > 0);
        assertTrue(xml.indexOf("<ss:Row ss:Index=\"5\"/>") > 0);
        assertTrue(xml.indexOf("<x:WorksheetOptions>") > 0);
        assertTrue(xml.indexOf("<x:Selected/>") > 0);
        assertTrue(xml.indexOf("<x:GridlineColor>#0000ff</x:GridlineColor>") > 0);
        assertEquals(1, gio.getSelectedSheetsCount());
        assertEquals(1, gio.getSelectedSheetsCount());
        
        //System.out.println(xml);
    }
}