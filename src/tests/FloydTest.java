package tests;

import grafo.Floyd;
import grafo.Grafo;
import grafo.GrafoD;
import org.junit.Test;
import static org.junit.Assert.*;


public class FloydTest {

    @Test
    public void getF() {
        Grafo<String, Double> g = new GrafoD<>();
        Floyd<String, Double> floyd;
        g.addVertice("8");
        g.addVertice("9");
        g.addVertice("2");
        g.addVertice("15");
        g.addCosto(0, 1, 152.0);
        g.addCosto(0, 2, 252.0);
        g.addCosto(0, 3, 452.0);
        g.addCosto(1, 0, 45.0);
        g.addCosto(1, 2, 100.0);
        g.addCosto(2, 0, 540.0);
        g.addCosto(2, 1, 620.0);
        g.addCosto(2, 3, 25.0);
        g.addCosto(3, 0, 640.0);
        g.addCosto(3, 1, 240.0);

        floyd = new Floyd<>(g, new DoubleIndicator(), Double.class);
        var f = floyd.getMinimasDistancias();
        assertEquals(152.0, f[0][1], 1.0);
        assertEquals(45.0, f[1][0], 1.0);
        assertEquals(277.0, f[0][3], 1.0);
        assertEquals(340.0, f[3][2], 1.0);
    }

    @Test
    public void getR() {
        Grafo<String, Double> g = new GrafoD<>();
        Floyd<String, Double> floyd;
        g.addVertice("8");
        g.addVertice("9");
        g.addVertice("2");
        g.addVertice("15");
        g.addVertice("58");
        g.addCosto(0, 1, 41.1);
        g.addCosto(0, 2, 252.0);
        g.addCosto(0, 3, 452.0);
        g.addCosto(0, 4, 885.2);
        g.addCosto(1, 0, 45.0);
        g.addCosto(1, 2, 100.0);
        g.addCosto(1, 4, 17.0);
        g.addCosto(2, 0, 540.0);
        g.addCosto(2, 1, 620.0);
        g.addCosto(2, 4, 94.0);
        g.addCosto(2, 3, 25.0);
        g.addCosto(3, 0, 640.0);
        g.addCosto(3, 2, 60.0);
        g.addCosto(3, 4, 21.8);
        g.addCosto(4, 0, 40.0);
        g.addCosto(4, 2, 20.0);

        floyd = new Floyd<>(g, new DoubleIndicator(), Double.class);
        var r = floyd.getRecorridos();
        assertEquals(4, r[0][3]);
        assertEquals(3, r[2][3]);
        assertEquals(0, r[1][0]);
        assertEquals(4, r[3][1]);
        assertEquals(4, r[3][0]);
        assertEquals(4, r[2][1]);
        assertEquals(0, r[4][0]);
        assertEquals(2, r[4][3]);
        assertEquals(1, r[0][4]);
    }
}