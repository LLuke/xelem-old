/*
 * Created on 22-mrt-2005
 *
 */
package nl.fountain.xelem.ztest;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.Area;
import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.lex.ExcelReader;

import org.xml.sax.SAXException;



/**
 *
 */
public class Test {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XelemException {
        ExcelReader xlReader = new ExcelReader();
        Area area = new Area("A1:AZ100");
        xlReader.setBuildArea(area);
        Workbook wb = xlReader.read("D:/test/VT1869.xml");
        wb.setFileName("D:/test/VT_1869.xml");
        new XSerializer().serialize(wb);
       
    }
    
}
