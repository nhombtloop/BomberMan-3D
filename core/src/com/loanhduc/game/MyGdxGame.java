package com.loanhduc.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.FileNotFoundException;

public class MyGdxGame implements ApplicationListener {
	private static PerspectiveCamera cam;
	private static CameraInputController cameraInputController;
	private static ModelBatch modelBatch;
	private static Environment environment;
	protected Stage stage;
	//private static AnimationController animationController;

	private static Player player = new Player();
	private Wall wall = new Wall();
	private Solid solid = new Solid();

	public void changeView() {
		cam.position.set(player.x, 200, player.z-300);
		cam.lookAt(0, 0, 0);
	}

	@Override
	public void create() {
		cam = new PerspectiveCamera(76, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.position.set(0f, 2000, 100f);
		cam.lookAt(0f, 0f, 0f);
		cam.near = 1f;
		cam.far = 10000.0f;

		cameraInputController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(cameraInputController);

		modelBatch = new ModelBatch();

		try {
			Map.loadMap("core/assets/Map.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		player.create();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 255f, 255f, 255f, 1.0f));
		environment.add(new DirectionalLight().set(255f, 255f, 255f, -10f, -10f, -10f));

		solid.create();
		wall.create(); //khoi tao

		renderMap();
	}

	@Override
	public void resize(int i, int i1) {

	}

	public void eventHandle() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.moveLeft();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.moveRight();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) player.moveUp();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.moveDown();
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) changeView();
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) player.createBomb();
		player.getPlayerInstance().transform.setToTranslation(player.x, player.y, player.z);
	}

	public static ModelBatch getModelBatch() {
		return modelBatch;
	}

	public static Environment getEnvironment() {
		return environment;
	}


	@Override
	public void render() {
		cameraInputController.update();

		eventHandle();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0.2f,1f,1f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		cameraFollowPlayer();
		cam.update();
		player.update();
		modelBatch.begin(cam);
		solid.render();
		player.render();
		wall.render();

		modelBatch.end();
	}

	private void cameraFollowPlayer() {
		cam.position.set(player.x, 1500, player.z + 500);
		cam.lookAt(player.x, player.y, player.z);
	}

	public void renderMap() {
		for (int i = 0; i < Map.ROWS; i++) {
			for (int j = 0; j < Map.COLUMNS; j++) {
				char c = Map.map[i][j];
				solid.spawn(j * Map.CELL_WIDTH, 0, i * Map.CELL_WIDTH);
				switch (c) {
					case '#': // wall
						wall.spawn(j * Map.CELL_WIDTH, 100, i * Map.CELL_WIDTH);
						break;
					case '*': // brick
						break;
					case 'x': // portal
						break;
					case 'b': // bomb item
						break;
					case 'f': // flame Item
						break;
					case 's': // speed Item
						break;
				}
			}
		}
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
		player.dispose();
		wall.dispose(); //pha huy
		solid.dispose();
	}
}