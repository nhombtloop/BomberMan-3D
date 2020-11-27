package com.loanhduc.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.FileNotFoundException;
import java.util.Random;

public class MyGdxGame extends ScreenAdapter {
	private static PerspectiveCamera cam;
	private static CameraInputController cameraInputController;
	private static ModelBatch modelBatch;
	private static Environment environment;
	protected Stage stage;
	public static Random generator = new Random();

	private static Player player = new Player();
	private Wall wall = new Wall();
	private Brick brick = new Brick();
	private Solid solid = new Solid();
	private Portal portal = new Portal();
	private SpeedItem speedItem = new SpeedItem();
	private FlameItem flameItem = new FlameItem();
	private BombItem bombItem = new BombItem();
	private ItemBox itemBox = new ItemBox();

	public void changeView() {

	}

	@Override
	public void show() {
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
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 200f, 200f, 200f, 1.0f));
		environment.add(new DirectionalLight().set(200f, 200f, 200f, -10f, -10f, -10f));

		brick.create();
		solid.create();
		wall.create(); //khoi tao
		portal.create();

		speedItem.create();
		flameItem.create();
		bombItem.create();
		itemBox.create();
		Enemy1.createEnemy1();


		renderMap();
		SoundEffect.playSoundInGame();
	}

	@Override
	public void resize(int i, int i1) {

	}

	public static ModelBatch getModelBatch() {
		return modelBatch;
	}

	public static Environment getEnvironment() {
		return environment;
	}


	@Override
	public void render(float delta) {
		cameraInputController.update();

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
		brick.render();
		itemBox.render();
		portal.render();

		speedItem.render();
		flameItem.render();
		bombItem.render();

		Enemy1.renderEnemy1();


		modelBatch.end();
	}

	private void cameraFollowPlayer() {
		cam.position.set(player.x, 1500, player.z + 600);
		cam.lookAt(player.x, player.y, player.z);
	}

	public void renderMap() {
		for (int i = 0; i < Map.ROWS; i++) {
			for (int j = 0; j < Map.COLUMNS; j++) {
				char c = Map.map[i][j];
				int x = j * Map.CELL_WIDTH;
				int z = i * Map.CELL_WIDTH;
				solid.spawn(x, 0, z);
				switch (c) {
					case '#': // wall
						wall.spawn(x, 0, z);
						break;
					case '*': // brick
						brick.spawn(x + 30, 0, z - 10);
						break;
					case 'x': // portal
						portal.spawn(x, 0, z);
						break;
					case 'b': // bomb item
						bombItem.spawn(x, 0, z);
						break;
					case 'f': // flame Item
						flameItem.spawn(x, 0, z);
						break;
					case 's': // speed Item
						speedItem.spawn(x, 0, z);
						break;
					case '?': // Question Mark block
						itemBox.spawn(x, 0, z);
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
		wall.dispose();
		solid.dispose();
		brick.dispose();
		portal.dispose();
		speedItem.dispose();
		flameItem.dispose();
		bombItem.dispose();
		itemBox.dispose();
	}

	public static Player getPlayer() {
		return player;
	}


}