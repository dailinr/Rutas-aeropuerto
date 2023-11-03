package tests;

import grafo.Indicator;

public class DoubleIndicator implements Indicator<Double> {
    @Override
    public Double add(Double o, Double oo) {
        if (o == null || oo == null) return null;
        return o + oo;
    }

    @Override
    public int compare(Double o, Double oo) {
        if (o == null && oo == null) return 0;
        if (o == null) return 1;
        if (oo == null) return -1;
        return o.compareTo(oo);
    }

}
