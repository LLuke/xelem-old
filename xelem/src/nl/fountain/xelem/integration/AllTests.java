/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem.integration;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import nl.fountain.xelem.CellPointerTest;
import nl.fountain.xelem.XFactoryTest;
import nl.fountain.xelem.XLUtilTest;
import nl.fountain.xelem.excel.ss.CreateDocumentTest;
import nl.fountain.xelem.excel.ss.SSCellTest;
import nl.fountain.xelem.excel.ss.SSColumnTest;
import nl.fountain.xelem.excel.ss.SSRowTest;
import nl.fountain.xelem.excel.ss.SSTableTest;
import nl.fountain.xelem.excel.ss.SSWorksheetTest;
import nl.fountain.xelem.excel.ss.XLWorkbookTest;
import nl.fountain.xelem.excel.x.XPaneTest;
import nl.fountain.xelem.excel.x.XWorksheetOptionsTest;

/**
 *
 */
public class AllTests extends TestCase {

    
    public static void main(String[] args) {
        TestRunner.run(suite());
    }
    
    public AllTests(String arg0) {
		super(arg0);
	}
    
    public static TestSuite suite() {
        TestSuite suite = new TestSuite("AllTests");
        
        suite.addTestSuite(CellPointerTest.class);       
        suite.addTestSuite(XLUtilTest.class);
        suite.addTestSuite(SSCellTest.class);
        suite.addTestSuite(SSColumnTest.class);
        suite.addTestSuite(SSRowTest.class);
        suite.addTestSuite(SSTableTest.class);
        suite.addTestSuite(XWorksheetOptionsTest.class);
        suite.addTestSuite(XPaneTest.class);
        suite.addTestSuite(SSWorksheetTest.class);
        
        suite.addTestSuite(XFactoryTest.class);
        suite.addTestSuite(XLWorkbookTest.class);
        suite.addTestSuite(CreateDocumentTest.class);
        
        return suite;
    }

}
