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
import de.svdragster.hexagons.map.TileLocation;
import de.svdragster.hexagons.util.HexagonUtil;
import de.svdragster.hexagons.world.Engine;

public class Hexagons extends ApplicationAdapter {


	private static Hexagons instance;

    private Engine      WorldLogicEngine;

    private List<TileLocation> testTiles = new ArrayList<>();

	SpriteBatch batch;
	Texture img;


	public Hexagons() {
		instance = this;

		this.WorldLogicEngine = new Engine();

	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("hexagon_flat.png");

		// test
        for (int x=0; x<7; x++) {
            for (int y=0; y<7; y++) {
                testTiles.add(HexagonUtil.getTileLocation(x, y));
            }
        }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (TileLocation loc : this.testTiles) {
            batch.draw(img, (int) loc.getX(), (int) loc.getY());
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
