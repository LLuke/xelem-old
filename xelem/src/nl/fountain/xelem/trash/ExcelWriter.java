/*
 * Created on Sep 10, 2004
 *
 */
package nl.fountain.xelem.trash;

import java.io.IOException;

import nl.fountain.xelem.UnsupportedStyleException;
import nl.fountain.xelem.excel.Workbook;

/**
 *
 */
public interface ExcelWriter {
    
    void write(Workbook workbook) throws IOException, UnsupportedStyleException;

}
