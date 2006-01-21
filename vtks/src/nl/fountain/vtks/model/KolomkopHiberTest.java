/*
 * Created on Jun 3, 2005
 * 
 *
 */
package nl.fountain.vtks.model;

import java.util.Iterator;

import junit.framework.TestCase;
import nl.fountain.vtks.data.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class KolomkopHiberTest extends TestCase {
    
    static Logger logger = Logger.getLogger(KolomkopHiberTest.class.getName());

    public static void main(String[] args) {
        junit.textui.TestRunner.run(KolomkopHiberTest.class);
    }
    
    public void testType() {
        logger.info(this);
        
        Kolomkop kKop = new Kolomkop();
        kKop.setType(Kolomkop.TYPE_META);
        assertEquals("META", kKop.getType());
        try {
            kKop.setType("foo");
            fail("geen exceptie gegooid.");
        } catch (IllegalArgumentException e) {
            assertEquals("'foo', legal values: 'RIJ', 'DATA', 'META', 'LEEG'.", 
                    e.getMessage());
        }
        
        kKop.setType("");
        assertEquals("", kKop.getType());
        
        kKop.setType(null);
        assertNull(kKop.getType());
    }
    
    public void testType2() {
        logger.info(this);
        
        Long id = createAndStoreKolomkop("LEEG");
        logger.debug("KolomKop, type LEEG met id " + id);
        Kolomkop kKop = getStoredKolomkop(id);
        assertEquals("LEEG", kKop.getType());
    }
    
    public void testType3() {
        logger.info(this);
        
        Long id = createAndStoreKolomkop(null);
        logger.debug("KolomKop, type null met id " + id);
        Kolomkop kKop = getStoredKolomkop(id);
        assertNull(kKop.getType());
    }
    
    public void testType4() {
        logger.info(this);
        
        Long id = createAndStoreKolomkop("");
        logger.debug("KolomKop, type empty String met id " + id);
        Kolomkop kKop = getStoredKolomkop(id);
        assertEquals("", kKop.getType());
    }
    
    
    public void testParent() {
        logger.info(this);
        
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Kolomkop parent = new Kolomkop();
        parent.setCaption("parent");
        parent.setType(Kolomkop.TYPE_RIJ);
        Kolomkop child = new Kolomkop();
        child.setCaption("child");
        child.setType(Kolomkop.TYPE_DATA);
        Kolomkop child2 = new Kolomkop();
        child2.setCaption("child2");
        child2.setType(Kolomkop.TYPE_META);
        parent.addChild(child);
        parent.addChild(child2);
        session.save(parent);
        Long childId = child.getId();
        Long parentId = parent.getId();
        session.flush();
        
        tx.commit();
        HibernateUtil.closeSession(); 
        // --------------------------------------
        session = HibernateUtil.currentSession();
        tx = session.beginTransaction();
        
        Kolomkop childx = (Kolomkop) session.get(Kolomkop.class, childId);
        Kolomkop parentx = childx.getParent();
        assertEquals("parent", parentx.getCaption());
        assertEquals(parentId, parentx.getId());
        Iterator<Kolomkop> iter = parentx.getChildren().iterator();
        Kolomkop kid = iter.next();
        if (kid.getCaption().equals("child")) kid = iter.next();
        assertEquals("child2", kid.getCaption());
        assertEquals(child2.getId(), kid.getId());
        
        // ook mogelijk: kind is zijn eigen grootouder. 
        // is dit gewenst? voorkomen? Komt in de praktijk niet voor.
        kid.addChild(parentx);
        session.save(kid);
        assertSame(kid, parentx.getParent());
        
        tx.commit();
        HibernateUtil.closeSession(); 
    }
    
    public void testCascadingDelete() {
        logger.info(this);
        
        Long parentId = createAndStoreParentAndChild();
        // --------------------------------------
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Kolomkop parentx = (Kolomkop) session.get(Kolomkop.class, parentId);
        assertEquals(1, parentx.getChildren().size());
        Long childId = parentx.getChildren().iterator().next().getId();
        
        session.delete(parentx);
        logger.debug("kolomkop " + parentx.getId() 
        		+ " deleted. Also it's child " + childId + " deleted?");
        session.flush();
        tx.commit();
        HibernateUtil.closeSession(); 
        // --------------------------------------
        session = HibernateUtil.currentSession();
        tx = session.beginTransaction();
        
        Kolomkop childx = (Kolomkop) session.get(Kolomkop.class, childId);
        assertNull(childx);
        
        tx.commit();
        HibernateUtil.closeSession();
    }
    
    public void testDeleteKid() {
        logger.info(this);
        
        Long parentId = createAndStoreParentAndChild();
        // --------------------------------------
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Kolomkop parentx = (Kolomkop) session.get(Kolomkop.class, parentId);
        Kolomkop childx = parentx.getChildren().iterator().next();
        parentx.removeChild(childx);
        logger.debug("Kind " + childx.getId() + " verstoten.");
        // niet nodig vanwege cascade="all-delete-orphan"
        //session.delete(childx);
        
        tx.commit();
        HibernateUtil.closeSession();
    }
    
    public void testDeleteKid2() {
        logger.info(this);
        
        Long parentId = createAndStoreParentAndChild();
        logger.debug("kolomkop-ouder met id: " + parentId);
        // --------------------------------------
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Kolomkop parentx = (Kolomkop) session.get(Kolomkop.class, parentId);
        Kolomkop childx = parentx.getChildren().iterator().next();
        logger.debug("kolomkop-kid met id: " + childx.getId());
        
        parentx.removeChild(childx);
        session.update(parentx);

        
        tx.commit();
        HibernateUtil.closeSession();
    }
    
    private Long createAndStoreKolomkop(String type) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Kolomkop kKop = new Kolomkop();
        kKop.setType(type);
        session.save(kKop);
        logger.debug("saved " + kKop + " with id " + kKop.getId());
        Long id = kKop.getId();
        session.flush();
        
        tx.commit();
        HibernateUtil.closeSession(); 
        return id;
    }
    
    private Kolomkop getStoredKolomkop(Long id) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Kolomkop kKop = (Kolomkop) session.get(Kolomkop.class, id);
        if (kKop != null) {
            logger.debug("got " + kKop + " with id " + kKop.getId());
        } else {
            logger.debug("Kolomkop with id " + id + " does not exist");
        }
        
        tx.commit();
        HibernateUtil.closeSession();
        
        return kKop;
    }
    
    private Long createAndStoreParentAndChild() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Kolomkop parent = new Kolomkop();
        parent.setCaption("I am the parent from createAndStoreParentAndChild");
        Kolomkop child = new Kolomkop();
        child.setCaption("me is the child from createAndStoreParentAndChild");
        parent.addChild(child);
        session.save(parent);
        // heeft geen invloed op verdwijnen van kind na parent.removeChild
        //session.save(child);
        Long parentId = parent.getId();
        session.flush();
        
        tx.commit();
        HibernateUtil.closeSession(); 
        return parentId;
    }

}
