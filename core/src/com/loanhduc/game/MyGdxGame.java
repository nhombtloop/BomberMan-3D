package com.loanhduc.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.UBJsonReader;

public class MyGdxGame implements ApplicationListener {
	private PerspectiveCamera cam;
	private CameraInputController cameraInputController;
	private ModelBatch modelBatch;
	private Model model;
	private ModelInstance modelInstance;
	private Environment environment;
	private AnimationController controller;

	@Override
	public void create() {
		cam = new PerspectiveCamera(76, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.position.set(0f, 2000, 0f);
		cam.lookAt(0f, 0f, 0f);
		cam.near = 0.1f;
		cam.far = 3000.0f;

		cameraInputController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(cameraInputController);

		modelBatch = new ModelBatch();

		UBJsonReader jsonReader = new UBJsonReader();

		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		model = modelLoader.loadModel(Gdx.files.getFileHandle("model2.g3db", Files.FileType.Internal));
		modelInstance = new ModelInstance(model);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		controller = new AnimationController(modelInstance);
		controller.setAnimation("mixamo.com", -1);

	}

	@Override
	public void resize(int i, int i1) {

	}
	int x =0, z = 0;
	public void event() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) x-= 10;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x+= 10;
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) z-= 10;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) z+= 10;
		modelInstance.transform.setToTranslation(x, 0, z);
	}
	@Override
	public void render() {
		cameraInputController.update();
		event();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

		cam.update();
		controller.update(Gdx.graphics.getDeltaTime());
		modelBatch.begin(cam);
		modelBatch.render(modelInstance, environment);
		modelBatch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}
}