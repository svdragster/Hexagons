package de.svdragster.hexagons.map;

/**
 * Created by Sven on 08.12.2017.
 */

public class Tile {

    private TileLocation tileLocation;
    private double radius;
    private double sideLength;
    private double height;

    public Tile(double sideLength) {
        this.sideLength = sideLength;

        this.radius = Math.cos(30) * sideLength;
        this.height = Math.sin(30) * sideLength;
    }

    public TileLocation getTileLocation() {
        return tileLocation;
    }

    public void setTileLocation(TileLocation tileLocation) {
        this.tileLocation = tileLocation;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
