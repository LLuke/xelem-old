/*
 * Created on Nov 10, 2004
 *
 */
package nl.fountain.xelem.trash;

import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.ss.XLWorkbook;

/**
 *
 */
public class Testje {

    public static void main(String[] args) throws XelemException {
        Workbook wb = new XLWorkbook("testje");
        wb.setAppendInfoSheet(true);
        XSerializer xs = new XSerializer();
        xs.serialize(wb);
    }
}
