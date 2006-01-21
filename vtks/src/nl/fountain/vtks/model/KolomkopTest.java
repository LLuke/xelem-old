/*
 * Created on Jun 7, 2005
 * 
 *
 */
package nl.fountain.vtks.model;

import junit.framework.TestCase;

public class KolomkopTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(KolomkopTest.class);
    }

    public void testToString() {
        Kolomkop kk1 = new Kolomkop();
        kk1.setCaption("Opschrift");
        kk1.setComment("commentaar");
        kk1.setLeftMostColumn(25);
        kk1.setNote("noot");
        kk1.setRightMostColumn(31);
        kk1.setType("DATA");
        
        Kolomkop kk2 = new Kolomkop();
        kk2.setCaption("Opschrift");
        kk2.setComment("commentaar");
        kk2.setLeftMostColumn(25);
        kk2.setNote("noot");
        kk2.setRightMostColumn(31);
        kk2.setType("DATA");
        
        assertEquals(kk1.toString(), kk2.toString());
    }
    
    public void testEquals0() {
        Kolomkop kk1 = new Kolomkop();
        assertFalse(kk1.equals(null));
        assertFalse(kk1.equals("a String"));
        Kolomkop kk2 = new Kolomkop();
        assertTrue(kk1.equals(kk2));
        
        kk1.setCaption("Caption");
        assertFalse(kk1.equals(kk2));
        kk2.setCaption("Caption");
        assertTrue(kk1.equals(kk2));
        
        kk1.setComment("Comment");
        assertFalse(kk1.equals(kk2));
        kk2.setComment("Comment");
        assertTrue(kk1.equals(kk2));
        
        kk1.setLeftMostColumn(1);
        assertFalse(kk1.equals(kk2));
        kk2.setLeftMostColumn(1);
        assertTrue(kk1.equals(kk2));
        
        kk1.setNote("Note");
        assertFalse(kk1.equals(kk2));
        kk2.setNote("Note");
        assertTrue(kk1.equals(kk2));
        
        kk1.setRightMostColumn(2);
        assertFalse(kk1.equals(kk2));
        kk2.setRightMostColumn(2);
        assertTrue(kk1.equals(kk2));
        
        kk1.setType(Kolomkop.TYPE_RIJ);
        assertFalse(kk1.equals(kk2));
        kk2.setType(Kolomkop.TYPE_RIJ);
        assertTrue(kk1.equals(kk2));
    }
    
    public void testEquals1() {
        // test children-equality
        Kolomkop parent1 = new Kolomkop();
        Kolomkop parent2 = new Kolomkop();
        
        Kolomkop kid1A = new Kolomkop();
        Kolomkop kid2A = new Kolomkop();
        
        parent1.addChild(kid1A);
        assertFalse(parent1.equals(parent2));
        parent2.addChild(kid2A);
        assertEquals(1, parent1.getChildren().size());
        assertEquals(1, parent2.getChildren().size());
        assertTrue(parent1.equals(parent2));
        
        kid1A.setCaption("kid's caption");
        assertFalse(parent1.equals(parent2));
        kid2A.setCaption("kid's caption");
        assertTrue(parent1.equals(parent2));
        
        Kolomkop kid1B = new Kolomkop();
        Kolomkop kid2B = new Kolomkop();
        
        parent1.addChild(kid1B);
        assertFalse(parent1.equals(parent2));
        parent2.addChild(kid2B);
        assertTrue(parent1.equals(parent2));
        
        kid1B.setComment("kid's comment");
        assertFalse(parent1.equals(parent2));
        kid2B.setComment("kid's comment");
        assertTrue(parent1.equals(parent2));
        
        Kolomkop kid1A_kid = new Kolomkop();
        Kolomkop kid2A_kid = new Kolomkop();
        
        kid1A.addChild(kid1A_kid);
        assertFalse(parent1.equals(parent2));
        kid2A.addChild(kid2A_kid);
        assertTrue(parent1.equals(parent2));
        
        kid1A_kid.setType(Kolomkop.TYPE_META);
        assertFalse(parent1.equals(parent2));
        kid2A_kid.setType(Kolomkop.TYPE_META);
        assertTrue(parent1.equals(parent2));
    }
    
    public void testEquals2() {
        // equality is only tested down the line
        Kolomkop parent1 = new Kolomkop();
        Kolomkop kid1 = new Kolomkop();
        
        Kolomkop parent2 = new Kolomkop();
        Kolomkop kid2 = new Kolomkop();
        
        assertTrue(kid1.equals(kid2));
        
        parent1.addChild(kid1);
        parent2.addChild(kid2);
        
        assertTrue(kid1.equals(kid2)); 
    }
    
    public void testEquals3() {
        // children are stored in their natural order
        Kolomkop parent1 = new Kolomkop();
        Kolomkop parent2 = new Kolomkop();
        
        Kolomkop kid1A = new Kolomkop();
        Kolomkop kid1B = new Kolomkop();
        kid1A.setCaption("A");
        kid1B.setCaption("B");
        
        Kolomkop kid2A = new Kolomkop();
        Kolomkop kid2B = new Kolomkop();
        kid2A.setCaption("A");
        kid2B.setCaption("B");
        
        parent1.addChild(kid1A);
        parent1.addChild(kid1B);
        
        parent2.addChild(kid2B);
        parent2.addChild(kid2A);
        
        assertTrue(parent1.equals(parent2));
    }

}
