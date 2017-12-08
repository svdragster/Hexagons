package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.svdragster.hexagons.components.ComponentManager;
import de.svdragster.hexagons.entities.EntityManager;

public class Hexagons extends ApplicationAdapter {


	private static Hexagons instance;

	private ComponentManager componentManager;
	private EntityManager entityManager;

	SpriteBatch batch;
	Texture img;

	public Hexagons() {
		instance = this;

		this.componentManager = new ComponentManager();
		this.entityManager = new EntityManager();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public ComponentManager getComponentManager() {
		return componentManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public static Hexagons getInstance() {
		return instance;
	}
}
