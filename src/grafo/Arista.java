package grafo;

import java.io.Serializable;

public class Arista<E, C> implements Serializable{
    public C costo;
    public E dato;

    public Arista(E dato, C costo) {
        this.dato = dato;
        this.costo = costo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Arista<?, ?>)) return false;
        return ((Arista<?, ?>) obj).dato.equals(dato);
    }

}
