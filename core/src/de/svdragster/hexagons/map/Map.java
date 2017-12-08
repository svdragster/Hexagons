package de.svdragster.hexagons.map;

/**
 * Created by Sven on 08.12.2017.
 */

public class Map {

    private Hexagon hexagon; // reference hexagon

    private Tile[][] tiles;

    public Map(int sizeX, int sizeY) {
        tiles = new Tile[sizeX][sizeY];

        hexagon = new Hexagon(20);
    }

    /**
     * get the tiles location from the array indexes
     * @param indexX
     * @param indexY
     * @return
     */
    private TileLocation getTileLocation(int indexX, int indexY) {
        double x = indexX * 2 * hexagon.getRadius() + (indexX & 1) * hexagon.getRadius(); // Adds radius if indexX is odd
        double y = indexY * (hexagon.getHeight() + hexagon.getSideLength());
        return new TileLocation(x, y);
    }

    /**
     * Get the array indexes from the tiles location
     * @param tileLocation
     * @return
     */
    private Point getArrayLocation(TileLocation tileLocation) {
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
