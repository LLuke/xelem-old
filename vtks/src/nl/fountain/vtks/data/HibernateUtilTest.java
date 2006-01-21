/*
 * Created on Jun 3, 2005
 * 
 *
 */
package nl.fountain.vtks.data;

import junit.framework.TestCase;

import org.hibernate.Session;

public class HibernateUtilTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(HibernateUtilTest.class);
    }
    
    public void testSessionFactory() {
        Session session = HibernateUtil.currentSession();
        assertTrue(session.isConnected());
        assertFalse(session.isDirty());
        assertTrue(session.isOpen());
        HibernateUtil.closeSession();
        assertFalse(session.isOpen());
        Session session2 = HibernateUtil.currentSession();
        assertNotSame(session, session2);
        assertTrue(session2.isConnected());
        assertFalse(session2.isDirty());
        assertTrue(session2.isOpen());
    }

}
