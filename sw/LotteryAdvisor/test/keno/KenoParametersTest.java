/*
 * KenoParametersTest.java
 * JUnit based test
 *
 * Created on April 23, 2010, 12:59 PM
 */

package keno;

import junit.framework.*;
import org.apache.log4j.Logger;


public class KenoParametersTest extends TestCase {
    private static Logger logger;
    
    public KenoParametersTest(String testName) {
        super(testName);
        
        // get logger instance for this class".
        logger = Logger.getLogger(KenoParametersTest.class);
    }

    protected void setUp() throws java.lang.Exception {
    }

    protected void tearDown() throws java.lang.Exception {
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(KenoParametersTest.class);
        
        return suite;
    }

    /**
     * Test of readParameters method, of class keno.kenoParameters.
     */
    public void testReadParameters() {
        System.out.println("testReadParameters");
                       
        /*kenoParameters p = new kenoParameters();
        assertEquals(p.readParameters(), true);*/
    }

    /**
     * Test of saveParameters method, of class keno.kenoParameters.
     */
    public void testSaveParameters() {
        System.out.println("testSaveParameters");
        
        /*kenoParameters p = new kenoParameters();
        assertEquals(p.readParameters(), true);
        assertEquals(p.saveParameters(), true);*/
    }

    /**
     * Test of getParameter method, of class keno.kenoParameters.
     */
    public void testGetParameter() {
        System.out.println("testGetParameter");
        
        /*kenoParameters p = new kenoParameters();
        assertEquals(p.readParameters(), true);
        assertEquals(p.getParameter("user"),"keno");
        assertEquals(p.getParameter("passwd"),"keno1234");
        assertEquals(p.getParameter("server"),"10.31.6.93");
        assertEquals(p.getParameter("db"),"keno");*/
    }

    /**
     * Test of setParameter method, of class keno.kenoParameters.
     */
    public void testSetParameter() {
        System.out.println("testSetParameter");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }
    
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    
}
