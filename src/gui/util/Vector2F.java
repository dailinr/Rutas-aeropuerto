package gui.util;

import java.io.Serializable;

public class Vector2F implements Serializable {
    public Double x, y;

    public Vector2F(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2F by(double t) {
        return new Vector2F(x * t, y * t);
    }
}
