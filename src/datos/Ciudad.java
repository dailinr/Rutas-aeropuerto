package datos;


import java.io.Serializable;

import gui.util.Vector2D;

public class Ciudad implements Serializable {
    private String nombre;

    private String pais;

    private Vector2D position;

    public Ciudad(String nombre, String pais, Vector2D p) {
        this.nombre = nombre;
        this.pais = pais;
        this.position = p;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Ciudad [nombre=" + nombre + ", pais=" + pais + "]";
    }
}
