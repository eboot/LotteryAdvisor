/*
 * KenoRoundTest.java
 * JUnit based test
 *
 * Created on April 23, 2010, 10:12 AM
 */

package keno;

import junit.framework.*;
import org.apache.log4j.Logger;


public class KenoRoundTest extends TestCase {
    private static Logger logger;
    
    public KenoRoundTest(String testName) {
        super(testName);
        
        // get logger instance for this class".
        logger = Logger.getLogger(KenoRoundTest.class);
    }

    @Override
    protected void setUp() throws java.lang.Exception {
    }

    @Override
    protected void tearDown() throws java.lang.Exception {
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(KenoRoundTest.class);
        
        return suite;
    }
    
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    
    public void test1kenoRound() {
        
            /*kenoRound k = new kenoRound("","","");
            
            int[] tmp = k.getKenoNumbers();
            assertNull(tmp);                                   */
    }

    public void test2kenoRound() {
        
            /*kenoRound k = new kenoRound("10","","");
            
            int[] tmp = k.getKenoNumbers();
            assertNull(tmp);         */                          
    }

    public void test3kenoRound() {
        
            /*kenoRound k = new kenoRound("10","","10 20 30");
            
            int[] tmp = k.getKenoNumbers();
            assertNull(tmp);                            */
    }
    
    public void test4kenoRound() {
        
            /*kenoRound k = new kenoRound("10","4.4.2010","5 6 15 20 24 28 31 33 38 40 46 47 48 49 50 53 54 63 68 70");
            
            int[] tmp = k.getKenoNumbers();
            assertNotNull(tmp);     
            
            assertEquals(tmp[0], 5);
            assertEquals(tmp[1], 6);
            assertEquals(tmp[19], 70);*/
    }
}
