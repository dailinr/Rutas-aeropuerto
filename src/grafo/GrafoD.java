package grafo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/** Grafo dinamico */
public class GrafoD<E, C> implements Grafo<E, C>, Serializable {

    ArrayList<Vertice<E, C>> vertices = new ArrayList<>();

    @Override
    public E getVertice(int pos) {
        return vertices.get(pos).dato;
    }

    @Override
    public C getCosto(int aPos, int bPos) {
        var a = vertices.get(aPos);
        E b = vertices.get(bPos).dato;
        for (var arista : a) {
            if (arista.dato == b) return arista.costo;
        }
        return null;
    }

    @Override
    public void addVertice(E valor) {
        vertices.add(new Vertice<>(valor));
    }

    @Override
    public void addCosto(int aPos, int bPos, C costo) {
        var ver = vertices.get(aPos);
        ver.addArista(new Arista<>(vertices.get(bPos).dato, costo));
    }

    @Override
    public List<E> getSucesores(int pos) {
        ArrayList<E> sucesores = new ArrayList<>();

        for (var s : vertices.get(pos)) {
            sucesores.add(s.dato);
        }

        return sucesores;
    }

    @Override
    public void removeVertice(int pos) {
        if (pos >= orden()) throw new IndexOutOfBoundsException();

        var tar = vertices.get(pos);

        for (var v : vertices) {
            for (var a : v) {
                if (Objects.equals(a.dato, tar.dato)) {
                    v.removeArista(a);
                }
            }
        }
        vertices.remove(tar);
    }

    @Override
    public void removeArista(int ver, int ar) {
        if (0 > ver || ver >= orden() || 0 > ar || ar >= orden())
            throw new IndexOutOfBoundsException();
        var target = vertices.get(ar);
        var vertice = vertices.get(ver);
        for (var a : vertice) {
            if (a.dato.equals(target.dato)) {
                vertice.removeArista(a);
                return;
            }
        }
    }

    @Override
    public void aislar(int pos) {
        var target = vertices.get(pos);
        for (var v : vertices) {
            for (var a : v) {
                if (a.dato.equals(target.dato)) {
                    v.removeArista(a);
                    break;
                }
            }
        }
        target.removeAll();
    }

    @Override
    public int orden() {
        return vertices.size();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var vertice : vertices) {
            sb.append(vertice.dato).append(", ");
        }
        return sb.toString();
    }

}
