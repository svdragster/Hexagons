package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

import de.svdragster.hexagons.map.Point;
import de.svdragster.hexagons.map.TileLocation;
import de.svdragster.hexagons.screens.MainScreen;
import de.svdragster.hexagons.util.CameraUtil;
import de.svdragster.hexagons.util.HexagonUtil;
import de.svdragster.hexagons.world.Engine;

public class Hexagons extends ApplicationAdapter {


	private static Hexagons instance;

    private Engine      WorldLogicEngine;

    private Screen currentScreen;

    private List<TileLocation> testTiles = new ArrayList<>();

    private CameraUtil camera;
	SpriteBatch batch;
	Texture img;
    Texture imgSelected;


	public Hexagons() {
		instance = this;

		this.WorldLogicEngine = new Engine();
		this.currentScreen = new MainScreen();

	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("hexagon_flat.png");
		img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        imgSelected = new Texture("hexagon_flat_selected.png");
        camera = new CameraUtil(new OrthographicCamera(1280, 720));


		// test
        for (int x=0; x<7; x++) {
            for (int y=0; y<7; y++) {
				TileLocation loc = HexagonUtil.getTileLocation(x, y);
				loc.setX(loc.getX() - x);
				loc.setY(loc.getY() - y);
                testTiles.add(loc);
            }
        }

        /*testTiles.add(HexagonUtil.getTileLocation(0, 0));
        testTiles.add(HexagonUtil.getTileLocation(1, 0));
        testTiles.add(HexagonUtil.getTileLocation(2, 0));
        testTiles.add(HexagonUtil.getTileLocation(3, 0));
        testTiles.add(HexagonUtil.getTileLocation(0, 1));
        testTiles.add(HexagonUtil.getTileLocation(0, 2));

        testTiles.add(HexagonUtil.getTileLocation(2, 1));*/


        Gdx.input.setInputProcessor(new InputListener());
	}

	@Override
	public void render () {
		//super.render();

		camera.update();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.getCamera().combined);
		batch.begin();
        int i = 0;

        Vector3 mousePos = camera.getMousePosInGameWorld();

        Point p = HexagonUtil.getArrayLocation(new TileLocation(mousePos.x, mousePos.y));
		for (TileLocation loc : this.testTiles) {
		    if (i == p.getX() * 7 + p.getY()) {
		        // tile is selected
                batch.draw(imgSelected, (int) loc.getX(), (int) loc.getY(), (float) HexagonUtil.hexagon.getSideLengthX()*2, (float) HexagonUtil.hexagon.getSideLengthY()*2);
            } else {
                batch.draw(img, (int) loc.getX(), (int) loc.getY(), (float) HexagonUtil.hexagon.getSideLengthX()*2, (float) HexagonUtil.hexagon.getSideLengthY()*2);
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

    public CameraUtil getCamera() {
        return camera;
    }
}
