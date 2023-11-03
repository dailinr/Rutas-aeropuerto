package gui.util;

import java.io.Serializable;

public class Vector2D implements Serializable{
    public Integer x, y;

    public Vector2D(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Double x, Double y) {
        this.x = x.intValue();
        this.y = y.intValue();
    }

    public Vector2D plus(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D plus(Vector2F other) {
        return new Vector2D((int)(x + other.x), (int)(y + other.y));
    }

    public Vector2D mines(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D mines(Vector2F other) {
        return new Vector2D((int)(x - other.x), (int)(y - other.y));
    }

}
