/*
 * Created on 28-nov-2004
 *
 */
package nl.fountain.xelem;

import junit.framework.TestCase;


public class CellPointerTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(CellPointerTest.class);
    }
    
    public void testConstructor() {
        CellPointer cp = new CellPointer();
        assertEquals(1, cp.getColumnIndex());
        assertEquals(1, cp.getRowIndex());
    }
    
    public void testParameterlessMove() {
        CellPointer cp = new CellPointer();
        cp.move();
        assertEquals(2, cp.getColumnIndex());
        assertEquals(1, cp.getRowIndex());
    }
    
    public void testSetMovement() {
        CellPointer cp = new CellPointer();
        
        cp.setMovement(CellPointer.MOVE_DOWN);
        cp.move();
        assertEquals(1, cp.getColumnIndex());
        assertEquals(2, cp.getRowIndex());
        
        cp.setMovement(CellPointer.MOVE_LEFT);
        cp.move();
        assertEquals(0, cp.getColumnIndex());
        assertEquals(2, cp.getRowIndex());
        
        cp.setMovement(CellPointer.MOVE_RIGHT);
        cp.move();
        assertEquals(1, cp.getColumnIndex());
        assertEquals(2, cp.getRowIndex());
        
        cp.setMovement(CellPointer.MOVE_UP);
        cp.move();
        assertEquals(1, cp.getColumnIndex());
        assertEquals(1, cp.getRowIndex());
        
        try {
            cp.setMovement(-1);
            fail("geen exceptie gegooid.");
        } catch (IllegalArgumentException e) {
            assertEquals("-1. Legal values are 0, 1, 2 and 3.", e.getMessage());
        }
    } 
    
    public void testStepDistance() {
        CellPointer cp = new CellPointer();
        
        cp.setHorizontalStepDistance(10);
        cp.move();
        assertEquals(11, cp.getColumnIndex());
        assertEquals(1, cp.getRowIndex());
        
        cp.setVerticalStepDistance(5);
        cp.setMovement(CellPointer.MOVE_DOWN);
        cp.move();
        assertEquals(11, cp.getColumnIndex());
        assertEquals(6, cp.getRowIndex());
        
        cp.setMovement(CellPointer.MOVE_LEFT);
        cp.move();
        assertEquals(1, cp.getColumnIndex());
        assertEquals(6, cp.getRowIndex());
        
        cp.setMovement(CellPointer.MOVE_UP);
        cp.move();
        assertEquals(1, cp.getColumnIndex());
        assertEquals(1, cp.getRowIndex());
    }
    
    public void testIsWithinSheet() {
        CellPointer cp = new CellPointer();
        assertTrue(cp.isWithinSheet());
        cp.moveTo(0, 1);
        assertTrue(!cp.isWithinSheet());
        cp.moveTo(1, 0);
        assertTrue(!cp.isWithinSheet());
        cp.moveTo(65537, 1);
        assertTrue(!cp.isWithinSheet());
        cp.moveTo(1, 257);
        assertTrue(!cp.isWithinSheet());
        cp.moveTo(1000, 10);
        assertTrue(cp.isWithinSheet());
        
    }
    
    public void testGetAbsoluteAddress() {
        CellPointer cp = new CellPointer();
        assertEquals("R1C1", cp.getAbsoluteAddress());
        cp.moveTo(25, 107);
        assertEquals("R25C107", cp.getAbsoluteAddress());
    }
    
    public void testGetRelativeAddress() {
        CellPointer cp = new CellPointer();
        assertEquals("RC", cp.getRelativeAddress(1, 1));
        assertEquals("R[-1]C", cp.getRelativeAddress(2, 1));
        assertEquals("RC[-2]", cp.getRelativeAddress(1, 3));
        cp.moveTo(10, 10);
        assertEquals("R[8]C[6]", cp.getRelativeAddress(2, 4));
        assertEquals("R[-5]C[-2]", cp.getRelativeAddress(15, 12));
    }


}
