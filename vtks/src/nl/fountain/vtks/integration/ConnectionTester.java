/*
 * Created on May 24, 2005
 *
 */
package nl.fountain.vtks.integration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.cfg.Configuration;

/**
 * A standalone test to test connection conditions and connection to the database.
 *
 */
public class ConnectionTester {
    
    protected Connection con = null;
    
    public void doTest() {
        Configuration config = new Configuration().configure();
        String driver = config.getProperty("connection.driver_class");
        String url = config.getProperty("connection.url");
        String user = config.getProperty("connection.username");
        String password = config.getProperty("connection.password");
        doTest(driver, url, user, password);
    }
    
    public void doTest(String driver, String url, String user, String password) {
        System.out.println("---------------------------------------------------");
        System.out.println("driver = " + driver);
        System.out.println("url = " + url);
        System.out.println("user = " + user);
        System.out.println("password = " + password);
        System.out.println("---------------------------------------------------");
        System.out.println("DETECTING DRIVER");
        System.out.println("---------------------------------------------------");
        detectClass(driver);
        
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            System.out.println("---------------------------------------------------");
            System.out.println("ESTABLISHING CONNECTION");
            System.out.println("---------------------------------------------------");
            conn = getConnection(url, user, password);
            System.out.println("- OK connection established");
            System.out.println("className: " + conn.getClass().getName());
            System.out.println("catalog: " + conn.getCatalog());
            System.out.println("autocommit: " + conn.getAutoCommit());
            
            System.out.println("---------------------------------------------------");
            System.out.println("LISTING DATABASEMETADATA");
            System.out.println("---------------------------------------------------");
            dbmd = con.getMetaData();
            System.out.println("productname: " + dbmd.getDatabaseProductName());
            System.out.println("productversion: " + dbmd.getDatabaseProductVersion());
            System.out.println("maxconnections: " + dbmd.getMaxConnections());
            System.out.println("catalogTerm: " + dbmd.getCatalogTerm());
            System.out.println("allProceduresAreCallable: " + dbmd.allProceduresAreCallable());
            System.out.println("allTablesAreSelectable: " + dbmd.allTablesAreSelectable());
            System.out.println("getMaxRowSize: " + dbmd.getMaxRowSize());
            
            
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
    }
    
    protected Connection getConnection(String url, String user, String password) throws SQLException {
        if (con == null) {
            con =
                DriverManager.getConnection(
                    url,
                    user,
                    password);
        }
        return con;
    }
    
    protected void closeConnection() {
        System.out.println("---------------------------------------------------");
        System.out.println("CLOSING CONNECTION");
        System.out.println("---------------------------------------------------");
        if (con != null) {
            try {
                con.close();
                System.out.println("- OK " + con.getClass().getName() + " closed");
                con = null;                
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("- no connection found.");
        }
    }
    
    private void detectClass(String className) {
        try {
            Class.forName(className);
            System.out.println("- OK " + className + " detected");
        } catch (ClassNotFoundException e) {
                System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        ConnectionTester tc = new ConnectionTester();
        System.out.println("-- ConnectionTester --");
        if (args.length < 4) {
            System.out.println("Usage: java " +
                    ConnectionTester.class.getName() +
                    " [driver url user password]");
            System.out.println("using arguments from hibernate.cfg.xml:");
            tc.doTest();
        } else {
            System.out.println("using arguments:");
            tc.doTest(args[0], args[1], args[2], args[3]);
        }
    }

}
