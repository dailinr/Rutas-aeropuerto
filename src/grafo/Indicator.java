package grafo;

import java.util.Comparator;

public interface Indicator<T> extends Comparator<T> {
    T add(T o, T oo);
}
