/*
 * Created on 31-mrt-2005
 *
 */
package nl.fountain.xelem.excel.ss;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.excel.Comment;
import nl.fountain.xelem.excel.XLElementTest;

/**
 *
 */
public class SSCommentTest extends XLElementTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SSCommentTest.class);
    }
    
    public void testGetDataStripped() {
        Comment comment = new SSComment();
        comment.setData("<B><I><Font html:Face=\"Tahoma\" html:Size=\"9\" "
                + "html:Color=\"#000000\">the great author:</Font></I></B>"
                + "<Font html:Face=\"Tahoma\" html:Size=\"9\" "
                + "html:Color=\"#000000\">&#10;dit is&#10;commentaar.</Font>");
        char lf = 10;
        assertEquals("the great author:" + lf + "dit is" + lf + "commentaar.", 
                comment.getDataStripped());
        
    }
    
    public void testGetDataClean() {
        Comment comment = new SSComment();
        comment.setData("<B><I><Font html:Face=\"Tahoma\" html:Size=\"9\" "
                + "html:Color=\"#000000\">the great author:</Font></I></B>"
                + "<Font html:Face=\"Tahoma\" html:Size=\"9\" "
                + "html:Color=\"#000000\">&#10;dit is&#10;commentaar.</Font>");
        comment.setAuthor("the great author");
        assertEquals("dit is commentaar.", comment.getDataClean());
    }
    
    public void testAssemble() {
        Comment comment = new SSComment();
        comment.setData("commentaartekst");
        GIO gio = new GIO();
        String xml = xmlToString(comment, gio);
        assertTrue(xml.indexOf("<ss:Comment>") > 0);
        assertTrue(xml.indexOf("<ss:Data>commentaartekst</ss:Data>") > 0);
        assertTrue(xml.indexOf("</ss:Comment>") > 0);
        
//        comment.setData("<B><I><Font html:Face=\"Tahoma\" html:Size=\"9\" "
//                + "html:Color=\"#000000\">the great author:</Font></I></B>"
//                + "<Font html:Face=\"Tahoma\" html:Size=\"9\" "
//                + "html:Color=\"#000000\">&#10;dit is&#10;commentaar.</Font>");
//        xml = xmlToString(comment, gio);
        //System.out.println(xml);
    }

}
