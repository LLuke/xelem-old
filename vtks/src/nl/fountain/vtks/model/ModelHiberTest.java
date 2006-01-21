/*
 * Created on Jun 7, 2005
 * 
 *
 */
package nl.fountain.vtks.model;

import java.util.Set;

import junit.framework.TestCase;
import nl.fountain.vtks.data.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ModelHiberTest extends TestCase {
    
    static Logger logger = Logger.getLogger(ModelHiberTest.class.getName());

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ModelHiberTest.class);
    }
    
    public void testModel_Kolomkop() {
        logger.info(this);
        
        Session session;
        Transaction tx;
        Kolomkop kop1;
        Kolomkop kop2;
        
        Long modelId = createAndStoreModel(); 
        // --------------------------------------
        
        session = HibernateUtil.currentSession();
        tx = session.beginTransaction();
        
        Model modelx = (Model) session.get(Model.class, modelId);
        Set<Kolomkop> koppen = modelx.getKolomkoppen();
        
        assertEquals(1, koppen.size());
        kop1 = koppen.iterator().next();
        assertEquals("DATA", kop1.getType());
        kop2 = kop1.getChildren().iterator().next();
        assertEquals("LEEG", kop2.getType());
        
        tx.commit();
        HibernateUtil.closeSession(); 
    }
    
    public void testTabelkop_Model() {
        logger.info(this);
        
        Long modelId = createAndStoreModel();
        
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Model modelx = (Model) session.get(Model.class, modelId);
        Tabelkop tKop = new Tabelkop();
        tKop.setBladnaam("bladnaam1");
        tKop.setModel(modelx);
        session.save(tKop);
        
        tx.commit();
        HibernateUtil.closeSession(); 
    }

    private Long createAndStoreModel() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Model model = new Model();
        Kolomkop kop1 = new Kolomkop();
        kop1.setType(Kolomkop.TYPE_DATA);
        Kolomkop kop2 = new Kolomkop();
        kop2.setType(Kolomkop.TYPE_LEEG);
        kop1.addChild(kop2);
        model.addKolomkop(kop1);
        session.save(model);
        Long modelId = model.getId();
        session.flush();
        
        tx.commit();
        HibernateUtil.closeSession();
        return modelId;
    }
    

}
