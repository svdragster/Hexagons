package de.svdragster.hexagons.map;

/**
 * Created by Sven on 08.12.2017.
 */

public class Map {

    private Tile[][] tiles;

    public Map(int sizeX, int sizeY) {
        tiles = new Tile[sizeX][sizeY];
    }

    private TileLocation getTileLocation(int indexX, int indexY) {
        Tile tile = tiles[indexX][indexY];

        double x = indexX * 2 * tile.getRadius() + (indexX & 1) * tile.getRadius(); // Adds radius if indexX is odd
        double y = indexY * (tile.getHeight() + tile.getSideLength());
    }

    private Point getArrayLocation(TileLocation tileLocation) {

    }


}
