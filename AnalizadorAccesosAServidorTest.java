

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class AnalizadorAccesosAServidorTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class AnalizadorAccesosAServidorTest
{
    private AnalizadorAccesosAServidor analizad1;

    

    /**
     * Default constructor for test class AnalizadorAccesosAServidorTest
     */
    public AnalizadorAccesosAServidorTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        analizad1 = new AnalizadorAccesosAServidor();
        analizad1.analizarArchivoDeLog("access02.log");
        analizad1.analizarArchivoDeLog("access02.log");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
