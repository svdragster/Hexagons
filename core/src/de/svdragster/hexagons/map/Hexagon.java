package de.svdragster.hexagons.map;

/**
 * Created by Sven on 08.12.2017.
 */

public class Hexagon {

    private double radius;
    private double sideLength;
    private double height;

    public Hexagon(double sideLength) {
        this.sideLength = sideLength;

        this.sideLength = sideLength;

        this.radius = Math.cos(30) * sideLength;
        this.height = Math.sin(30) * sideLength;
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
