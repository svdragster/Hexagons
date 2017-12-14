package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.svdragster.hexagons.components.ComponentManager;
import de.svdragster.hexagons.entities.EntityManager;
import de.svdragster.hexagons.screens.ScreenManager;
import de.svdragster.hexagons.screens.ScreenType;
import de.svdragster.hexagons.world.Engine;

public class Hexagons extends Game {


	private static Hexagons instance;

    private Engine      WorldLogicEngine;

    private ScreenManager screenManager;

	public Hexagons() {
		instance = this;

		this.WorldLogicEngine = new Engine();
		this.screenManager = new ScreenManager();

	}

	@Override
	public void create () {
	    screenManager.getCurrentScreen().create(); // create the loading screen
        screenManager.setCurrentScreen(ScreenType.LOADING);
	    //screenManager.createAll();
	}

	@Override
	public void render () {
	    clearScreen();
		super.render();

	}
	
	@Override
	public void dispose () {

	}

	private void clearScreen() {
        Gdx.gl.glClearColor(0.1f, 0.15f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }



	public static Hexagons getInstance() {
		return instance;
	}

    public Engine getWorldLogicEngine() {
        return WorldLogicEngine;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
