/*
 * Created on May 19, 2005
 *
 */
package nl.fountain.vtks.trash;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.lex.ExcelReader;

import org.xml.sax.SAXException;

/**
 *
 */
public class HeaderReader {
    
    
    private EmptyRowFilter emptyRowFilter;
    private ExcelReader excelReader;
    private EmptyLineListener emptyLineListener;
    
    
    public void read(String filename, int start, int stop) throws ParserConfigurationException, IOException, SAXException {
        ExcelReader reader = getExcelReader();
        EmptyRowFilter filter = getEmptyRowFilter();
        EmptyLineListener listener = getListener();
        filter.addExcelReaderListener(listener);
        filter.setRowIndex(start, stop);
        reader.addExcelReaderListener(filter);
        reader.read(filename);
    }
    
    public Workbook getWorkbook() {
        return getListener().getWorkbook();
    }
    
    private ExcelReader getExcelReader() throws ParserConfigurationException, SAXException {
        if (excelReader == null) {
            excelReader = new ExcelReader();
        }
        return excelReader;
    }
    
    private EmptyRowFilter getEmptyRowFilter() throws ParserConfigurationException, SAXException {
        if (emptyRowFilter == null) {
            emptyRowFilter = new EmptyRowFilter(getExcelReader());
        }
        return emptyRowFilter;
    }
    
    private EmptyLineListener getListener() {
        if (emptyLineListener == null) {
            emptyLineListener = new EmptyLineListener();
        }
        return emptyLineListener;
    }

}
