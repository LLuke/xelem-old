/*
 * Created on 12-nov-2004
 *
 */
package nl.fountain.xelem.examples;

import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.ss.XLWorkbook;


public class HelloExcel {

    public static void main(String[] args) throws XelemException {
        Workbook wb = new XLWorkbook("HelloExcel");
        wb.addSheet().addCell("Hello Excel!");
        new XSerializer().serialize(wb);
    }
}
