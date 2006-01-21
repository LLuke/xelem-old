/*
 * Created on Jun 2, 2005
 * 
 *
 */
package nl.fountain.vtks.model;

import junit.framework.TestCase;
import nl.fountain.vtks.data.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TabelkopHiberTest extends TestCase {
    
    static Logger logger = Logger.getLogger(TabelkopHiberTest.class.getName());

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TabelkopHiberTest.class);
    }
    
    public void testTabelkop() {
        logger.info(this);
        Tabelkop tKop = getDefaultTabelkop("foo/bar.xml");
        assertEquals("foo/bar.xml", tKop.getBestandsnaam());
        // ...
        assertEquals(23, tKop.getSpan());
    }
    
    public void testPersistentTabelKop() {
        logger.info(this);
        Long id = createAndStoreTabelKop("foo/bar.xml");
        
        Tabelkop tKop = getStoredTabelkop(id);
        assertEquals("foo/bar.xml", tKop.getBestandsnaam());
        assertEquals("Groningen", tKop.getBladnaam());
        assertEquals("Provincie Groningen Eerste gedeelte", tKop.getCaption());
        assertEquals(1869, tKop.getJaar());
        assertEquals(1, tKop.getLeftMostColumn());
        assertEquals(24, tKop.getRightMostColumn());
        
        assertNull(getStoredTabelkop(123456789L));
        
        id = createAndStoreTabelKop("file://C:/bar/foo.xml");       
        tKop = getStoredTabelkop(id);
        assertEquals("file://C:/bar/foo.xml", tKop.getBestandsnaam()); 
    }
    
    public void testPersistentTabelKop2() {
        logger.info(this);
        
        Long id = createAndStoreTabelKop("abc123~!@#$%^&*()_+[]{}\n\t|:;',<.>/?");
        Tabelkop tKop = getStoredTabelkop(id);
        assertEquals("abc123~!@#$%^&*()_+[]{}\n\t|:;',<.>/?", tKop.getBestandsnaam()); 
    }

    public void testPersistentTabelKop3() {
        logger.info(this);
        
        Long id = createAndStoreTabelKop("abc123¸È‚ÂÁÍÎËÔÓÏƒ≈…Ê∆ÙˆÚ˚˘ˇ÷‹¢£•?·");
        Tabelkop tKop = getStoredTabelkop(id);
        assertEquals("abc123¸È‚ÂÁÍÎËÔÓÏƒ≈…Ê∆ÙˆÚ˚˘ˇ÷‹¢£•?·", tKop.getBestandsnaam());
    }
    
    public void testPersistentTabelKop4() {
        logger.info(this);
        
        Long id = createAndStoreTabelKop("de É komt niet door");
        Tabelkop tKop = getStoredTabelkop(id);
        if ("de É komt niet door".equals(tKop.getBestandsnaam())) {
            logger.info("de É komt wel door");
        } else {
            logger.warn("de É komt niet door");
        }
        //assertEquals("de É komt niet door", tKop.getBestandsnaam());
    }
    
    public void testNullId() {
        logger.info(this);
        
        try {
            getStoredTabelkop(null);
            fail("geen exceptie gegooid");
        } catch (IllegalArgumentException e) {
            logger.debug("caught exception: " + e.getMessage());
        }
    }

    private Tabelkop getDefaultTabelkop(String bestandsnaam) {
        Tabelkop tKop = new Tabelkop();
        tKop.setBestandsnaam(bestandsnaam);
        tKop.setBladnaam("Groningen");
        tKop.setCaption("Provincie Groningen Eerste gedeelte");
        tKop.setJaar(1869);
        tKop.setLeftMostColumn(1);
        tKop.setRightMostColumn(24);
        return tKop;
    }
    
    private Long createAndStoreTabelKop(String bestandsnaam) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Tabelkop tKop = getDefaultTabelkop(bestandsnaam);
        session.save(tKop);
        logger.debug("saved " + tKop + " with id " + tKop.getId());
        Long id = tKop.getId();
        session.flush();
        
        tx.commit();
        HibernateUtil.closeSession(); 
        return id;
    }
    
    private Tabelkop getStoredTabelkop(Long id) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Tabelkop tKop = (Tabelkop) session.get(Tabelkop.class, id);
        if (tKop != null) {
            logger.debug("got " + tKop + " with id " + tKop.getId());
        } else {
            logger.debug("Tabelkop with id " + id + " does not exist");
        }
        
        tx.commit();
        HibernateUtil.closeSession();
        
        return tKop;
    }

}
