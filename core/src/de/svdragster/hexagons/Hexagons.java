package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.svdragster.hexagons.components.ComponentMovement;
import de.svdragster.hexagons.components.ComponentPosition;
import de.svdragster.hexagons.components.ComponentType;
import de.svdragster.hexagons.system.System;
import de.svdragster.hexagons.system.SystemMovement;
import de.svdragster.hexagons.world.Engine;

public class Hexagons extends ApplicationAdapter {


	private static Hexagons instance;

    private Engine      WorldLogicEngine;

	SpriteBatch batch;
	Texture img;

	public Hexagons() {
		instance = this;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");


		this.WorldLogicEngine = new Engine();
		WorldLogicEngine.getEntityManager().createEntity(new ComponentPosition(100,100),new ComponentMovement(1,0.7));

		WorldLogicEngine.getSystemManager().addSystem(new SystemMovement(300,300));

		// test
        //System.out.println(HexagonUtil.getTileLocation(0, 0));
        //System.out.println(HexagonUtil.getTileLocation(1, 0));
        //System.out.println(HexagonUtil.getTileLocation(2, 0));
	}

	@Override
	public void render () {

		for(System system: WorldLogicEngine.getSystemManager())
			system.process(1);

		//should be handled by a system but for ease i put it bluntly here.
		ComponentPosition position = (ComponentPosition) WorldLogicEngine.getEntityManager().retrieveComponent(1,ComponentType.POSITION);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, (int)position.X, (int)position.Y);
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
