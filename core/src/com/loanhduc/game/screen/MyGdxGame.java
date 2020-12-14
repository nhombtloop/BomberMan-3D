package com.loanhduc.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.loanhduc.game.*;
import com.loanhduc.game.util.SoundEffect;

import java.io.FileNotFoundException;

public class MyGdxGame extends ScreenAdapter {
	private BoomGame game;
	private static PerspectiveCamera cam;
	private static ModelBatch modelBatch;
	private static Environment environment;

	private Player player = new Player(this);
	public Explode explode = new Explode(this);
	private Wall wall = new Wall();
	public Brick brick = new Brick();
	private Solid solid = new Solid();
	private Portal portal = new Portal();
	private SpeedItem speedItem = new SpeedItem();
	private FlameItem flameItem = new FlameItem();
	private BombItem bombItem = new BombItem();
	private Enemy enemy = new Enemy(this);
	private WinPortal winPortal = new WinPortal();
	public int view = -1;
	public int direction = 0;

	public MyGdxGame(BoomGame game) {
		this.game = game;
	}

	public void setupView() {

	}

	public void changeDirection() {
	    if(direction == 0) direction = 1;
        if(direction == 1) direction = 2;
        if(direction == 2) direction = 3;
        if(direction == 3) direction = 0;
    }

	private void cameraFollowPlayer() {
		if(view==-1) {
				cam.position.set(player.getX(), 1500, player.getZ() + 600);
				cam.lookAt(player.getX(), player.getY(), player.getZ());
		} else {
				if(direction ==0) {
                    cam.position.set(player.getX(), 200, player.getZ() -300);
                    cam.lookAt(player.getX(), 100, player.getZ() + 800);
                } else if(direction ==1) {
                    cam.position.set(player.getX() - 300, 200, player.getZ());
                    cam.lookAt(player.getX() + 800, 100, player.getZ());
                } else if(direction ==3) {
                    cam.position.set(player.getX() + 300, 200, player.getZ());
                    cam.lookAt(player.getX() - 800, 100, player.getZ());
                } else if(direction ==2) {
                    cam.position.set(player.getX(), 200, player.getZ() +300);
                    cam.lookAt(player.getX(), 100, player.getZ() - 800);
                }
		}
	}

	public void changeView() {
		view *= -1;
	}
	public BoomGame getGame() {
		return game;
	}

	@Override
	public void show() {
		cam = new PerspectiveCamera(76, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.near = 1f;
		cam.far = 10000.0f;

		modelBatch = new ModelBatch();

		try {
			Map.loadMap("core/assets/Map.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 200f, 200f, 200f, 1.0f));
		environment.add(new DirectionalLight().set(200f, 200f, 200f, -10f, -10f, -10f));

		player.create();
		brick.create();
		solid.create();
		wall.create();
		portal.create();

		speedItem.create();
		flameItem.create();
		bombItem.create();
		enemy.createEnemy();
		winPortal.create();

		Map.renderMap(this);
		SoundEffect.playSoundInGame();
		setupView();
	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
	public void render(float delta) {
		Utils.DELTA_TIME = Gdx.graphics.getDeltaTime();
		Utils.TIME += Utils.DELTA_TIME;
		Utils.runFunction();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0.2f,1f,1f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

		cameraFollowPlayer();

		cam.update();

		modelBatch.begin(cam);
		solid.render();
		player.render();
		wall.render();
		brick.render();
		portal.render();

		speedItem.render();
		flameItem.render();
		bombItem.render();
		winPortal.render();

		enemy.renderEnemy();
		explode.renderExplode();
		player.update();
		enemy.update();
		modelBatch.end();
	}

	public void checkBurned() {
		char[][] map = Map.map;
		int cell = Map.CELL_WIDTH;
		brick.checkBurned(map, cell);
		player.getBomb().checkBurned(map, cell);
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
		enemy.dispose();
		winPortal.dispose();
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public Player getPlayer() {
		return player;
	}

	public Portal getPortal() {
		return portal;
	}

	public Explode getExplode() {
		return explode;
	}

	public WinPortal getWinPortal() {
		return winPortal;
	}

	public Wall getWall() {
		return wall;
	}

	public Brick getBrick() {
		return brick;
	}

	public Solid getSolid() {
		return solid;
	}

	public SpeedItem getSpeedItem() {
		return speedItem;
	}

	public FlameItem getFlameItem() {
		return flameItem;
	}

	public BombItem getBombItem() {
		return bombItem;
	}

	public static ModelBatch getModelBatch() {
		return modelBatch;
	}

	public static Environment getEnvironment() {
		return environment;
	}
}