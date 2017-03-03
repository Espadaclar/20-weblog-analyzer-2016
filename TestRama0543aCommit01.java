import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TestObtenerHoraMasAccesos.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TestRama0543aCommit01
{
    /**
     * Default constructor for test class TestObtenerHoraMasAccesos
     */
    public TestRama0543aCommit01()
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

    @Test
    public void testObtenerHoraMasAccesos()
    {
        AnalizadorAccesosAServidor analizad1 = new AnalizadorAccesosAServidor();
        assertEquals(-1, analizad1.obtenerHoraMasAccesos());        
        analizad1.analizarArchivoDeLog("old.access.log");
        assertEquals(4, analizad1.obtenerHoraMasAccesos());
        analizad1.analizarArchivoDeLog("old.access02.log");
        assertEquals(3, analizad1.obtenerHoraMasAccesos());
        analizad1.analizarArchivoDeLog("old.access03.log");
        assertEquals(15, analizad1.obtenerHoraMasAccesos());
        analizad1.analizarArchivoDeLog("old.access04.log");
        assertEquals(20, analizad1.obtenerHoraMasAccesos());
    }
}
