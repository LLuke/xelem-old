/*
 * Created on 16-apr-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.List;


public interface ExcelReaderFilter extends ExcelReaderListener {
    
    List getListeners();
    void addExcelReaderListener(ExcelReaderListener listener);
    boolean removeExcelReaderListener(ExcelReaderListener listener);
    void clearExcelReaderListeners();
}
