package de.svdragster.hexagons.map.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.svdragster.hexagons.map.Tile;

/**
 * Created by Sven on 08.12.2017.
 */

public class MapGenerator implements Runnable {

    //////////////////
    //// SETTINGS ////
    //////////////////
    private Thread thread;
    private int sizeX;
    private int sizeY;

    private List<TileGenerator> tileGeneratorList;
    private Random random;

    ////////////////////
    //// GENERATING ////
    ////////////////////
    private int generatedThisTick = 0;
    private int tilesPerTick = 1;
    private int curX = 0;
    private int curY = 0;

    private List<Tile> generatedTiles;

    ///////////////
    ///////////////

    public MapGenerator(int sizeX, int sizeY, long seed, int tilesPerTick) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.random = new Random(seed);
        this.tilesPerTick = tilesPerTick;


        tileGeneratorList = new ArrayList<TileGenerator>();
        generatedTiles = new ArrayList<Tile>();
        thread = new Thread();
    }

    public void startGenerating() {
        thread.start();
    }

    private void generateTileAt(int x, int y) {
        Tile tile = new Tile();
        for (TileGenerator tileGenerator : tileGeneratorList) {
            tileGenerator.generate(tile, x, y, random);
        }
    }


    @Override
    public void run() {
        generatedThisTick++;
        if (generatedThisTick >= tilesPerTick) {
            generatedThisTick = 0;
            return;
        }
    }
}
