/*
 * Created on Jun 3, 2005
 * 
 *
 */
package nl.fountain.vtks.integration;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import nl.fountain.vtks.data.ConfigurationTest;
import nl.fountain.vtks.data.HibernateUtilTest;
import nl.fountain.vtks.model.KolomkopHiberTest;
import nl.fountain.vtks.model.TabelkopHiberTest;

public class AllTests {

    public static void main(String[] args) {
    	System.out.println("Testing cofigration...");
        TestSuite suite = new TestSuite("Testing configuration");
        suite.addTestSuite(ConfigurationTest.class);
    	TestResult result = TestRunner.run(suite);
        if (result.wasSuccessful()) {
        	Test test = suite();
        	System.out.println(test.toString() + ": " + test.countTestCases() + " tests...");
            TestRunner.run(test);
        } else {
            System.out.println("Configuration testing was unsuccesfull. "
                    + "Abandoning further tests.");
        }
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for nl.fountain.vtks");
        //$JUnit-BEGIN$
        suite.addTestSuite(HibernateUtilTest.class);
        suite.addTestSuite(TabelkopHiberTest.class);
        suite.addTestSuite(KolomkopHiberTest.class);
        //$JUnit-END$
        return suite;
    }

}
