/*
 * Created on 30-nov-2004
 *
 */
package nl.fountain.xelem;

import junit.framework.TestCase;


public class AddressTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AddressTest.class);
    }

    public void testEquals() {
        Address adr = new Address(5, 3);
        Address adr2 = new Address(5, 3);
        assertTrue(adr.equals(adr));
        assertTrue(adr.equals(adr2));
        assertTrue(adr2.equals(adr));
    }

    public void testToString() {
        Address adr = new Address(5, 3);
        assertEquals("nl.fountain.xelem.Address[row=5,column=3]", adr.toString());
    }

}
