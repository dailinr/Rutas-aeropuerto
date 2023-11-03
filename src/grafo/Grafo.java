package grafo;

import java.util.List;

public interface Grafo<E, C> {
    /**
     * Obtener un vertice
     * @param pos Posicion en el cojunto de vertices
     * @return el valor del vertice
     */
    E getVertice(int pos);

    /**
     * @param aPos vertice de salida
     * @param bPos vertice de llegada
     * @return Costo para "viajar" desde aPos a bPos
     */
    C getCosto(int aPos, int bPos);

    /**
     * Agregar un vertice
     * @param valor valor del vertice
      */
    void addVertice(E valor);

    /**
     * Determinar el costo desde un vertice a otro
     * @param aPos Vertice de salida
     * @param bPos Vertice de llegada
     * @param costo Costo para "viajar" de aPos a bPos
     */
    void addCosto(int aPos, int bPos, C costo);

    /**
     * Obtener los sucesores del vertice en la posicion pos
     * @return colleccion de sucesores
      */
    List<E> getSucesores(int pos);

    /**
     * Elimina el vertice y todas las aristas
     */
    void removeVertice(int pos);

    /**
     * Remove arista
     * @param ver index of vertex from
     * @param ar index of vertex to
     */
    void removeArista(int ver, int ar);

    /**
     * Remueve todas las aristas relacionadas
     * con un vertice
     */
    void aislar(int pos);

    /**
     * @return numero de vertices del grafo
     */
    int orden();

}
