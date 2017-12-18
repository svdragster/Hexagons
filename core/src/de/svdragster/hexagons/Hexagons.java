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
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.svdragster.hexagons.map.TileLocation;
import de.svdragster.hexagons.util.HexagonUtil;
import de.svdragster.hexagons.world.Engine;
import de.timolia.replay.plugin.ReplayApi;

public class Hexagons extends ApplicationAdapter {


	private static Hexagons instance;

    private Engine      WorldLogicEngine;

    ////////////////////////////////
    // 3 dimensional stuff
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Model model;
    private Environment environment;

    private CameraInputController camController;
    ////////////////////////////////
    ////////////////////////////////

    private List<TileLocation> testTiles = new ArrayList<>();
    private Map<TileLocation, ModelInstance> modelInstanceMap = new HashMap<>();

	public Hexagons() {
		instance = this;

		this.WorldLogicEngine = new Engine();

	}

	@Override
	public void create() {

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

        float height = 0;
        for (int x=0; x<7; x++) {
            for (int y=0; y<7; y++) {
                ModelInstance modelInstance = new ModelInstance(model);
                TileLocation loc = HexagonUtil.getTileLocation(x, y);
                modelInstance.transform.translate((float) loc.getX(), height, (float) loc.getY());
                height += 0.05;
                modelInstance.transform.rotate(0, 1, 0, 90);

                modelInstanceMap.put(loc, modelInstance);
            }
        }

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.6f, 0.6f, 0.6f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.5f, 0.5f, 0f, -0.8f, -0.2f));

        camController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void render () {
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        int i = 0;
        for (TileLocation loc : this.modelInstanceMap.keySet()) {
            if (i == 0) {
                ModelInstance modelInstance = this.modelInstanceMap.get(loc);
                /*Vector3 v = modelInstance.transform.getTranslation(Vector3.Zero);
                if (modelInstance.transform.getTranslation(Vector3.Zero).x <= 0) {
                    modelInstance.transform.translate(20f, 0f, 0f);
                } else {
                    modelInstance.transform.translate(-0.1f, 0f, 0f);
                }*/
                i++;
            }
            ModelInstance model = this.modelInstanceMap.get(loc);
            modelBatch.render(model, environment);
        }
        modelBatch.end();
	}
	
	@Override
	public void dispose () {
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
