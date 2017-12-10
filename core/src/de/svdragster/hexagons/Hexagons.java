package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

	public Hexagons() {
		instance = this;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");


		this.WorldLogicEngine = new Engine();


		WorldLogicEngine.getSystemManager().addSystem(new SystemMovement(400,300));
		WorldLogicEngine.getSystemManager().addSystem(new SystemMessageDelivery(WorldLogicEngine.getEntityManager()));


		WorldLogicEngine.getEntityManager().createEntity(new ComponentPosition(100,100),new ComponentMovement(1,0.7),new ComponentMailbox());
		WorldLogicEngine.getEntityManager().createEntity(new ComponentPosition(50,150),new ComponentMovement(3,1.5));
		WorldLogicEngine.getEntityManager().createEntity(new ComponentPosition(0,50),new ComponentMovement(4,2));

		WorldLogicEngine.getSystemManager().BroadcastMessage(1);
		WorldLogicEngine.getSystemManager().BroadcastMessage(2);
		WorldLogicEngine.getSystemManager().BroadcastMessage(3);


		// test
        //System.out.println(HexagonUtil.getTileLocation(0, 0));
        //System.out.println(HexagonUtil.getTileLocation(1, 0));
        //System.out.println(HexagonUtil.getTileLocation(2, 0));
	}

	@Override
	public void render () {

		WorldLogicEngine.run();

		//should be handled by a system but for ease i put it bluntly here.
		ComponentPosition position = (ComponentPosition) WorldLogicEngine.getEntityManager().retrieveComponent(1,ComponentType.POSITION);
		ComponentPosition position2 = (ComponentPosition) WorldLogicEngine.getEntityManager().retrieveComponent(2,ComponentType.POSITION);
		ComponentPosition position3 = (ComponentPosition) WorldLogicEngine.getEntityManager().retrieveComponent(3,ComponentType.POSITION);

		ComponentMailbox msg = (ComponentMailbox) WorldLogicEngine.getEntityManager().retrieveComponent(1,ComponentType.MESSAGE);
		if(!msg.Inbox.isEmpty())
			msg.Inbox.poll().InvokeCallback();

		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			ComponentMailbox.Message  SpeedUp = new ComponentMailbox.Message(1, 1, "", new Delegate() {
				@Override
				public void invoke(Object... args) {
					ComponentMovement speed = (ComponentMovement) WorldLogicEngine.getEntityManager().retrieveComponent(1,ComponentType.MOVEMENT);
					if(speed.dX < 0)
						speed.dX--;
					else
						speed.dX++;
				}
			});

			WorldLogicEngine.getSystemManager().BroadcastMessage(SpeedUp);
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			ComponentMailbox.Message  SpeedUp = new ComponentMailbox.Message(1, 1, "", new Delegate() {
				@Override
				public void invoke(Object... args) {
					ComponentMovement speed = (ComponentMovement) WorldLogicEngine.getEntityManager().retrieveComponent(1,ComponentType.MOVEMENT);
					if(speed.dX < 0)
						speed.dX++;
					else
						speed.dX--;
				}
			});

			WorldLogicEngine.getSystemManager().BroadcastMessage(SpeedUp);
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, (int)position.X, (int)position.Y);
		batch.draw(img, (int)position2.X, (int)position2.Y);
		batch.draw(img, (int)position3.X, (int)position3.Y);
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
