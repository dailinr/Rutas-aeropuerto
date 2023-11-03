package datos;

import grafo.Indicator;

public class TimeIndicator implements Indicator<Viaje> {
    @Override
    public Viaje add(Viaje o, Viaje oo) {
        if (o == null || oo == null) return null;
        return new Viaje(
                o.getCosto() + oo.getCosto(),
                o.getDistancia() + oo.getDistancia(),
                o.getTiempo() + oo.getTiempo()
        );
    }

    @Override
    public int compare(Viaje o, Viaje oo) {
        if (o == oo) return 0;
        if (o == null) return 1;
        if (oo == null) return -1;
        return o.getTiempo().compareTo(oo.getTiempo());
    }
}
