package datos;

import java.io.Serializable;

public class Viaje implements Serializable{
    private Double costo;

    private Double distancia;

    private Integer tiempo;

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Viaje(Double costo, Double distancia, Integer tiempo) {
        this.costo = costo;
        this.distancia = distancia;
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "costo=" + costo +
                ", distancia=" + distancia +
                ", tiempo=" + tiempo +
                '}';
    }
}
