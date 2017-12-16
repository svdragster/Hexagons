package de.svdragster.hexagons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.UBJsonReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.svdragster.hexagons.map.TileLocation;
import de.svdragster.hexagons.util.HexagonUtil;
import de.svdragster.hexagons.world.Engine;

public class Hexagons extends ApplicationAdapter {


	private static Hexagons instance;

    private Engine      WorldLogicEngine;

    ////////////////////////////////
    // 3 dimensional stuff
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Model model;
    //private ModelInstance modelInstance;
    private Environment environment;

    private CameraInputController camController;
    ////////////////////////////////
    ////////////////////////////////

    private List<TileLocation> testTiles = new ArrayList<>();
    private Map<TileLocation, ModelInstance> modelInstanceMap = new HashMap<>();

	SpriteBatch batch;
	Texture img;
    Texture imgSelected;


	public Hexagons() {
		instance = this;

		this.WorldLogicEngine = new Engine();

	}

	@Override
	public void create() {
		/*batch = new SpriteBatch();
		img = new Texture("hexagon_flat.png");
        imgSelected = new Texture("hexagon_flat_selected.png");

		// test
        for (int x=0; x<7; x++) {
            for (int y=0; y<7; y++) {
				TileLocation loc = HexagonUtil.getTileLocation(x, y);
                testTiles.add(loc);
                System.out.println(loc.toString());
            }
        }*/

        modelBatch = new ModelBatch();
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        // Model loader needs a binary json reader to decode
        UBJsonReader jsonReader = new UBJsonReader();
        // Create a model loader passing in our json reader
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle("hexagon.g3db", Files.FileType.Internal));

        for (int x=0; x<7; x++) {
            for (int y=0; y<7; y++) {
                ModelInstance modelInstance = new ModelInstance(model);
                TileLocation loc = HexagonUtil.getTileLocation(x, y);
                modelInstance.transform.translate((float) loc.getX(), 0, (float) loc.getY());
                modelInstance.transform.rotate(0, 1, 0, 90);
                modelInstanceMap.put(loc, modelInstance);
                System.out.println(loc.toString());
            }
        }

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        camController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void render () {
        camController.update();

		/*Gdx.gl.glClearColor(1, 0, 0, 1);
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
		batch.end();*/

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        for (TileLocation loc : this.modelInstanceMap.keySet()) {
            ModelInstance model = this.modelInstanceMap.get(loc);
            modelBatch.render(model, environment);
        }
        modelBatch.end();
	}
	
	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();

        modelBatch.dispose();
        model.dispose();
	}



	public static Hexagons getInstance() {
		return instance;
	}

    public Engine getWorldLogicEngine() {
        return WorldLogicEngine;
    }

}
