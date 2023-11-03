package tests;

import grafo.Grafo;
import grafo.GrafoD;
import grafo.GrafoE;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class GrafoTest {
    public Grafo<String, Double> g;

    public GrafoTest(Grafo<String, Double> grafo) {
        this.g = grafo;
    }

    @Test
    public void addVertice() {
        g.addVertice("8");
        g.addVertice("9");
        g.addVertice("2");
        g.addVertice("7");
        assertEquals("8", g.getVertice(0));
        assertEquals("9", g.getVertice(1));
        assertEquals("2", g.getVertice(2));
        assertEquals("7", g.getVertice(3));
    }

    @Test
    public void addArista() {
        g.addVertice("8");
        g.addVertice("9");
        g.addVertice("2");
        g.addCosto(0, 1, 152.0);
        g.addCosto(1, 2, 485.0);
        g.addCosto(2, 0, 740.0);
        assertEquals(152, g.getCosto(0, 1), 0.1);
        assertEquals(485, g.getCosto(1, 2), 0.1);
        assertEquals(740, g.getCosto(2, 0), 0.1);
        assertNull(g.getCosto(0, 2));
        assertNull(g.getCosto(1, 0));
        assertNull(g.getCosto(2, 1));
    }

    @Test
    public void removeVertice() {
        g.addVertice("8");
        g.addVertice("9");
        g.addVertice("2");
        g.addCosto(0, 1, 152.0);
        g.addCosto(1, 2, 485.0);
        g.addCosto(2, 0, 740.0);

        g.removeVertice(0);
        assertEquals(g.getVertice(0), "9");
        assertEquals(g.getVertice(1), "2");
    }

    @Test
    public void removeArista() {
        g.addVertice("8");
        g.addVertice("9");
        g.addVertice("2");
        g.addCosto(0, 1, 152.0);
        g.addCosto(1, 2, 485.0);
        g.addCosto(2, 0, 740.0);

        g.removeArista(0, 1);
        g.removeArista(2, 0);

        assertNull(g.getCosto(0, 1));
        assertNull(g.getCosto(2, 0));
        assertNotNull(g.getCosto(1, 2));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[]{new GrafoD<>()},
                new Object[]{new GrafoE<>(20, new ArrayList<>(), Double.class)}
        );
    }
}
