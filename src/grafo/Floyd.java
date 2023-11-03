package grafo;

import java.lang.reflect.Array;

public class Floyd<E, C> {
    private final C[][] f;
    private final int[][] r;

    public C[][] getMinimasDistancias() {
        return f;
    }

    public int[][] getRecorridos() {
        return r;
    }

    public Floyd(Grafo<E, C> grafo, Indicator<C> c, Class<? extends C> cls) {
        int n = grafo.orden();

        @SuppressWarnings("unchecked")
        C[][] array = (C[][]) Array.newInstance(cls, n, n);
        f = array;
        r = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    f[i][j] = grafo.getCosto(i, j);
                    r[i][j] = (f[i][j] == null ? -1 : j);
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (c.compare( c.add( f[i][k], f[k][j] ), f[i][j]) < 0) {
                        f[i][j] = c.add( f[i][k], f[k][j] );
                        r[i][j] = k;
                    }
                }
            }
        }
    }
}
