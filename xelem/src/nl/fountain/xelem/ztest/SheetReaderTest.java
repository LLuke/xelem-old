/*
 * Created on Dec 23, 2004
 *
 */
package nl.fountain.xelem.ztest;

import java.io.IOException;

import junit.framework.TestCase;

/**
 *
 */
public class SheetReaderTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SheetReaderTest.class);
    }
    
    public void testConstructor() {
        try {
            new SheetReader("testsuitefiles/ReaderTest/reader.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new SheetReader("non/existing/file.name");
            fail("geen exceptie gegooid.");
        } catch (IOException e1) {
            //
        }
    }

}
