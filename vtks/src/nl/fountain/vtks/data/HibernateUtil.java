/*
 * Created on Jun 2, 2005
 * 
 *
 */
package nl.fountain.vtks.data;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This class does not only produce the global SessionFactory 
 * in its static initializer (called once by the JVM when the class is loaded), 
 * but also has a ThreadLocal variable to hold the Session for the current thread. 
 * No matter when you call HibernateUtil.currentSession(), 
 * it will always return the same Hibernate unit of work in the same thread. 
 * A call to HibernateUtil.closeSession() ends the unit of work 
 * currently associated with the thread. 
 *
 */
public class HibernateUtil {
    
    public static final SessionFactory sessionFactory;

    static Logger logger = Logger.getLogger(HibernateUtil.class.getName());

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            logger.debug("-- STATIC INITIALIZATION OF THE SESSIONFACTORY --"
                    + " using hibernate.cfg.xml");
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed.", ex);
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    static final ThreadLocal<Session> threadVar = new ThreadLocal<Session>();

    public static Session currentSession() throws HibernateException {
        Session s = threadVar.get();
        // Open a new Session, if this thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            logger.debug("session from factory: " + s);
            // Store it in the ThreadLocal variable
            threadVar.set(s);
        }
        return s;
    }
    
    /**
     * 
     * @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session s = threadVar.get();
        if (s != null)
            s.close();
        threadVar.set(null);
    }

}
