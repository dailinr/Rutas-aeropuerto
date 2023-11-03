package grafo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class DFS <E ,C> {
    HashSet<E> visitados = new HashSet<>();
    List<E> recorridos = new ArrayList<>();
    private List<E> recorrido(Grafo<E, C> grafo, int pos){
        visitados.add(grafo.getVertice(pos));
        recorridos.add(grafo.getVertice(pos));
        List<E> sucesores =  grafo.getSucesores(pos);
        for (E sucesor : sucesores) {
            if (!visitados.contains(sucesor)) {
                int ind = posicion(grafo, sucesor);
                recorrido(grafo, ind);
            }
        }

        return recorridos;
        
    }
    
    public List<E> recorridoDFS(Grafo<E, C> grafo, int pos) {
        visitados.clear();
        return recorrido(grafo, pos);
    }

    private int posicion(Grafo<E, C> grafo, E sucesor) {
        for (int i = 0; i < grafo.orden(); i++) {
            if (grafo.getVertice(i).equals(sucesor)) {
                return i;
            }
            
        }
        return -1;
    }
}
