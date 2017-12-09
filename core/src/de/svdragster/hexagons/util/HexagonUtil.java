package de.svdragster.hexagons.util;

import de.svdragster.hexagons.map.Hexagon;
import de.svdragster.hexagons.map.Point;
import de.svdragster.hexagons.map.TileLocation;

/**
 * Created by Sven on 08.12.2017.
 */

public class HexagonUtil {

    public static Hexagon hexagon = new Hexagon(20);

    /**
     * get the tiles location from the array indexes
     * @param indexX
     * @param indexY
     * @return
     */
    public static TileLocation getTileLocation(int indexX, int indexY) {
        double x = indexX * 2 * hexagon.getRadius() + (indexX & 1) * hexagon.getRadius(); // Adds radius if indexX is odd
        double y = indexY * (hexagon.getHeight() + hexagon.getSideLength());
        return new TileLocation(x, y);
    }

    /**
     * Get the array indexes from the tiles location
     * @param tileLocation
     * @return
     */
    public static Point getArrayLocation(TileLocation tileLocation) {
        int sectX = (int) (tileLocation.getX() / (2 * hexagon.getRadius()));
        int sectY = (int) (tileLocation.getY() / (hexagon.getHeight() + hexagon.getSideLength()));

        int sectPixelX = (int) (tileLocation.getX() % (2 * hexagon.getRadius()));
        int sectPixelY = (int) (tileLocation.getY() % (hexagon.getHeight() + hexagon.getSideLength()));

        Point point = new Point(sectX, sectY);

        double gradient = hexagon.getHeight() / hexagon.getRadius();

        if ((sectY & 1) == 0) {
            if (sectPixelY < (hexagon.getHeight() - sectPixelX * gradient)) {
                // top left edge
                point.setX(sectX - 1);
                point.setY(sectY - 1);
            } else if (sectPixelY < (- hexagon.getHeight() + sectPixelX * gradient)) {
                // top right edge
                point.setX(sectX);
                point.setY(sectY - 1);
            }
            return point;
        }
        if (sectPixelX >= hexagon.getRadius()
                && sectPixelY < (2 * hexagon.getHeight() - sectPixelX * gradient)) {
            // right side
            point.setX(sectX - 1);
            point.setY(sectY - 1);
        } else {
            // left side
            if (sectPixelY < (sectPixelX * gradient)) {
                point.setY(sectY - 1);
            } else {
                point.setX(sectX - 1);
            }
        }
        return point;
    }

}