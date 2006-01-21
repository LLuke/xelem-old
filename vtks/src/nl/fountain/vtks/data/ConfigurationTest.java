package nl.fountain.vtks.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.hibernate.cfg.Configuration;

public class ConfigurationTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ConfigurationTest.class);
	}
	
	public void testConnection() {
		Configuration config = new Configuration().configure();
		String driver = config.getProperty("connection.driver_class");
		String url = config.getProperty("connection.url");
		String user = config.getProperty("connection.username");
		String password = config.getProperty("connection.password");
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			fail("driverclass not found: " + driver);
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {	
			e.printStackTrace();
			fail("connection could not be established to " + url);
			
		} finally {
	        if (conn != null) {
	            try {
	                conn.close();;                
	            } catch (SQLException e) {
	            	e.printStackTrace();
	                fail("connection could not be closed.");
	                
	            }
	        }
		}
	}
	


}
