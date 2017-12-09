package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import de.svdragster.hexagons.components.ComponentManager;
import de.svdragster.hexagons.entities.EntityManager;
import de.svdragster.hexagons.map.Hexagon;
import de.svdragster.hexagons.map.Point;
import de.svdragster.hexagons.map.TileLocation;
import de.svdragster.hexagons.util.HexagonUtil;
import de.svdragster.hexagons.world.Engine;

public class Hexagons extends ApplicationAdapter {


	private static Hexagons instance;

    private Engine      WorldLogicEngine;

    private List<TileLocation> testTiles = new ArrayList<>();

	SpriteBatch batch;
	Texture img;
    Texture imgSelected;


	public Hexagons() {
		instance = this;

		this.WorldLogicEngine = new Engine();

	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("hexagon_flat.png");
        imgSelected = new Texture("hexagon_flat_selected.png");

		// test
        for (int x=0; x<7; x++) {
            for (int y=0; y<7; y++) {
				TileLocation loc = HexagonUtil.getTileLocation(x, y);
                testTiles.add(loc);
                System.out.println(loc.toString());
            }
        }

        /*testTiles.add(HexagonUtil.getTileLocation(0, 0));
        testTiles.add(HexagonUtil.getTileLocation(1, 0));
        testTiles.add(HexagonUtil.getTileLocation(2, 0));
        testTiles.add(HexagonUtil.getTileLocation(3, 0));
        testTiles.add(HexagonUtil.getTileLocation(0, 1));
        testTiles.add(HexagonUtil.getTileLocation(0, 2));

        testTiles.add(HexagonUtil.getTileLocation(2, 1));*/

        System.out.println("============================");
        System.out.println("============================");
        System.out.println("============================");

        System.out.println(HexagonUtil.getArrayLocation(new TileLocation(90, 100)));
        //System.out.println(HexagonUtil.getArrayLocation(testTiles.get(1)));
        //System.out.println(HexagonUtil.getArrayLocation(testTiles.get(2)));

        //System.out.println(HexagonUtil.getArrayLocation(testTiles.get(7)));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// Gdx.input.getX() and Gdx.input.getY()
        int i = 0;

        Point p = HexagonUtil.getArrayLocation(new TileLocation(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()));
		for (TileLocation loc : this.testTiles) {
		    if (i == p.getX() * 7 + p.getY()) {
                batch.draw(imgSelected, (int) loc.getX(), (int) loc.getY());
            } else {
                batch.draw(img, (int) loc.getX(), (int) loc.getY());
            }
            i++;
        }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}



	public static Hexagons getInstance() {
		return instance;
	}

    public Engine getWorldLogicEngine() {
        return WorldLogicEngine;
    }

}
