/*
 * Created on Nov 10, 2004
 *
 */
package nl.fountain.xelem.trash;

import java.util.Date;

import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.ss.XLWorkbook;

/**
 *
 */
public class Testje extends Date {

    public static void main(String[] args) throws XelemException {
        Workbook wb = new XLWorkbook("testje");
        Worksheet sheet = wb.addSheet();
        Cell cell = sheet.addCellAt(11, 2);
        cell.setStyleID("currency");
        cell.setFormula("=SUBTOTAL(9;R[-10]C:R[-1]C");
        for (int i = 2; i < 9; i++) {
            sheet.addCell(cell);
        }
        Object datum = new Testje();
        Cell c = sheet.addCell();
        c.setData(datum);
        XSerializer xs = new XSerializer();
        String xml = xs.serializeToString(wb);
        System.out.println(xml);
    }
    
 
}
