package de.svdragster.hexagons.map;

/**
 * Created by Sven on 08.12.2017.
 */

public class TileLocation {

    private double x;
    private double y;

    public TileLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
