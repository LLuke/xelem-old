/*
 * Created on 16-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.x.XExcelWorkbook;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.XMLFilterImpl;


public class BuilderFactoryTest extends TestCase {
    
    private BuilderFactory factory;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(BuilderFactoryTest.class);
    }

    protected void setUp() throws Exception {
        factory = new BuilderFactory();
    }
    
    public void testGetAnonymousBuilder() throws Exception {
        AnonymousBuilder builder1 = (AnonymousBuilder) factory.getAnonymousBuilder();
        assertTrue(builder1.isOccupied());
        AnonymousBuilder builder2 = (AnonymousBuilder) factory.getAnonymousBuilder();
        assertTrue(builder2.isOccupied());
        assertNotSame(builder1, builder2);
        XLElement xle = new XExcelWorkbook();
        builder2.build(new MockReader(), null, factory, xle);
        builder2.endElement(XLElement.XMLNS_X, "ExcelWorkbook", null);
        assertTrue(!builder2.isOccupied());
        AnonymousBuilder builder3 = (AnonymousBuilder) factory.getAnonymousBuilder();
        assertTrue(builder3.isOccupied());
        assertSame(builder2, builder3);
        builder3.build(new MockReader(), null, factory, xle);
        builder3.endElement(XLElement.XMLNS_X, "ExcelWorkbook", null);
        assertTrue(!builder3.isOccupied());
        builder1.build(new MockReader(), null, factory, xle);
        builder1.endElement(XLElement.XMLNS_X, "ExcelWorkbook", null);
        assertTrue(!builder1.isOccupied());
    }
    
    
    private class MockReader extends XMLFilterImpl {
        
        public void setContentHandler(ContentHandler handler) {}
    }

}
