/*
 * Created on 12-nov-2004
 *
 */
package nl.fountain.xelem.examples;

import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.Worksheet;
import nl.fountain.xelem.excel.ss.XLWorkbook;


public class Fibonacci {

    public static void main(String[] args) {
        Workbook wb = new XLWorkbook("fibonacci");
        Worksheet sheet = wb.addSheet();
        
        // columnheadings
        sheet.addRow(10);
        sheet.addCell("fibonacci", "b_yellow");
        sheet.addCell("ratio 1", "b_yellow");
        sheet.addCell("ratio 2", "b_yellow");
        
        // now for Fibonacci...
        int f1 = 1;
        int f2 = 1;
        int f3 = 1;
        while (f1 < 100000) {
            System.out.println(f1);
            
            f2 = f3;
            f1 = f3;
            f3 = f2 + f1;
        }
        
        // 
        sheet.addWorksheetOptions().freezePanesAt(11, 0);
        sheet.setAutoFilter("R10C1:R10C4");
        
        try {
            new XSerializer().serialize(wb);
        } catch (XelemException e) {
            e.printStackTrace();
        }
    }
}
