package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import de.svdragster.hexagons.components.ComponentMailbox;
import de.svdragster.hexagons.components.ComponentMovement;
import de.svdragster.hexagons.components.ComponentPosition;
import de.svdragster.hexagons.components.ComponentType;
import de.svdragster.hexagons.system.SystemMessageDelivery;
import de.svdragster.hexagons.system.SystemMovement;
import de.svdragster.hexagons.util.Delegate;
import de.svdragster.hexagons.world.Engine;

public class Hexagons extends ApplicationAdapter {
	private static Hexagons instance;
    private Engine      WorldLogicEngine;


	SpriteBatch batch;
	Texture img;
	ShapeRenderer 	render;

	public Hexagons() {
		instance = this;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		render = new ShapeRenderer();

		//render.setProjectionMatrix();


		this.WorldLogicEngine = new Engine();


		WorldLogicEngine.getSystemManager().addSystem(new SystemMovement(400,300));
		WorldLogicEngine.getSystemManager().addSystem(new SystemMessageDelivery(WorldLogicEngine.getEntityManager()));





		// test
        //System.out.println(HexagonUtil.getTileLocation(0, 0));
        //System.out.println(HexagonUtil.getTileLocation(1, 0));
        //System.out.println(HexagonUtil.getTileLocation(2, 0));
	}

	public int id = -1;

	@Override
	public void render () {


		id = WorldLogicEngine.getEntityManager().getEntityContext().size();

		ComponentMailbox msg = null;
		if(id > 0)
			msg = (ComponentMailbox) WorldLogicEngine.getEntityManager().retrieveComponent(id,ComponentType.MESSAGE);
		if(msg != null && !msg.Inbox.isEmpty())
			msg.Inbox.poll().InvokeCallback();

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			Random rn = new Random();
			 id = WorldLogicEngine.getEntityManager().createEntity(
					new ComponentPosition(rn.nextInt(400),rn.nextInt(300)),
					new ComponentMovement(1+rn.nextInt(9),1+rn.nextInt(9)),
					new ComponentMailbox());

			WorldLogicEngine.getSystemManager().BroadcastMessage(id);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.R)){
			if(id > 0)
				WorldLogicEngine.getEntityManager().removeEntity(id);
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
		{
			ComponentMailbox.Message  SpeedUp = new ComponentMailbox.Message(id, id, "", new Delegate()
			{
				@Override
				public void invoke(Object... args) {
					ComponentMovement speed = (ComponentMovement) WorldLogicEngine.getEntityManager().retrieveComponent(id,ComponentType.MOVEMENT);
					if(speed.dX < 0)
						speed.dX -= 1.1;
					else
						speed.dX += 1.1;

					if(speed.dY < 0)
						speed.dY -= 1.1;
					else
						speed.dY += 1.1;
				}
			});

			WorldLogicEngine.getSystemManager().BroadcastMessage(SpeedUp);
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
		{
			ComponentMailbox.Message  SpeedUp = new ComponentMailbox.Message(id, id, "", new Delegate()
			{
				@Override
				public void invoke(Object... args) {
					ComponentMovement speed = (ComponentMovement) WorldLogicEngine.getEntityManager().retrieveComponent(id,ComponentType.MOVEMENT);
					if(speed.dX < 0)
						speed.dX += 1.1;
					else
						speed.dX -= 1.1;

					if(speed.dY < 0)
						speed.dY += 1.1;
					else
						speed.dY -= 1.1;
				}
			});

			WorldLogicEngine.getSystemManager().BroadcastMessage(SpeedUp);
		}

		WorldLogicEngine.run();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		for(int entity : WorldLogicEngine.getEntityManager().getEntityContext().keySet()){


			ComponentPosition pos = (ComponentPosition) WorldLogicEngine.getEntityManager().retrieveComponent(entity,ComponentType.POSITION);
			//batch.draw(img, (int)pos.X, (int)pos.Y);
			render.begin(ShapeRenderer.ShapeType.Line);

			Random rn = new Random();
			render.setColor(rn.nextInt(255)/2, rn.nextInt(255), rn.nextInt(255), 1);
			render.rect((float)pos.X, (float)pos.Y,10,10);

			render.flush();
			render.end();
		}

		batch.end();
		System.out.println("FPS: "+Gdx.app.getGraphics().getFramesPerSecond()+ " Entity Count: " + id);
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
