/*
 * Created on 18-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 *
 */
public class AnonymousBuilderTest extends TestCase {
    
    private String dinges;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AnonymousBuilderTest.class);
    }
    
//    public void testMethods() {
//        Class klas = AnonymousBuilder.class;
//        Method[] m = klas.getDeclaredMethods();
//        for (int i = 0; i < m.length; i++) {
//            System.out.println(m[i].getName() + " \t" 
//                    + m[i].getModifiers());
//        }
//    }
    
    private void setDinges(String s) {
        dinges = s;
    }
    
    public void testDinges() throws Exception {
        Class[] types = new Class[] {"gelukt".getClass()};
        Method m = this.getClass().getDeclaredMethod("setDinges", types);
        m.invoke(this, new Object[] {"gelukt"});
        System.out.println(dinges);
    }

}
