/*
 * Created on Dec 24, 2004
 *
 */
package nl.fountain.xelem;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.XLElement;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 *
 */
public class XLDocumentTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(XLDocumentTest.class);
    }
    
    public void testConstructor() {
        try {
            new XLDocument("this/file/does/not/exist");
            fail("geen exceptie gegooid.");
        } catch (XelemException e) {
            assertEquals("java.io.FileNotFoundException", 
                    e.getCause().getClass().getName());
        }
        try {
            XLDocument xldoc = new XLDocument("config/aviso.xml");
            assertNotNull(xldoc.getDocument());
        } catch (XelemException e1) {
            fail(e1.getMessage());
        }
    }
    
    public void testGetTable() throws Exception {
        XLDocument xldoc = new XLDocument("config/aviso.xml");
        Node table = xldoc.getTable("Sheet1");
        assertNotNull(table);
        try {
            table.getAttributes().removeNamedItemNS(
                                        XLElement.XMLNS_SS, "ExpandedColumnCount");
            fail("should have thrown exception.");
        } catch (DOMException e) {
            //
        }
        try {
            table.getAttributes().removeNamedItemNS(
                                        XLElement.XMLNS_SS, "ExpandedRowCount");
            fail("should have thrown exception.");
        } catch (DOMException e) {
            //
        }
        assertNull(xldoc.getTable("nosheetwiththisname"));
    }

}
