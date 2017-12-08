package de.svdragster.hexagons.map.generator;

import java.util.Random;

import de.svdragster.hexagons.map.Tile;

/**
 * Created by Sven on 08.12.2017.
 */

public abstract class TileGenerator {

    public abstract Tile generate(Tile tile, int x, int y, Random random);

}
