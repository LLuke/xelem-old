/*
 * Created on 22-mrt-2005
 *
 */
package nl.fountain.xelem.ztest;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.lex.DefaultExcelReaderListener;
import nl.fountain.xelem.lex.ExcelReader;

import org.xml.sax.SAXException;



/**
 *
 */
public class Test {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XelemException {
        ExcelReader reader = new ExcelReader();
        reader.addExcelReaderListener(new DefaultExcelReaderListener() {
            public void setRow(int sheetIndex, String sheetName, Row row) {
                System.out.println(row.getIndex());
                for (Iterator iter = row.getCells().iterator(); iter.hasNext();) {
                    Cell cell = (Cell) iter.next();
                    System.out.println(cell.getIndex() + " " + cell.getData());
                }
//                for (int i = 0; i < 1000000000; i++) {
//                    
//                }
            }
        });
       reader.read("testsuitefiles/ReaderTest/reader.xml");
    }
    
}
