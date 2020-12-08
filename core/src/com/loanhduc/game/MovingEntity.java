package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;

public abstract class MovingEntity extends Entity {
    protected float velocity;
    protected ModelInstance modelInstance;
    protected ArrayList<Character> canWalkThrough = new ArrayList<>();
    protected MyGdxGame game;

    @Override
    public void create() {
        super.create();
        modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
    }

    public MovingEntity(MyGdxGame game) {
        this.game = game;
        canWalkThrough.add(' ');
    }

    public void moveUp() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), 180);
        if (canMoveUp()) {
            z -= velocity;
        }
    }

    public void moveDown() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), 0);
        if (canMoveDown()) {
            z += velocity;
        }
    }

    public void moveLeft() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), -90);
        if (canMoveLeft()) {
            x -= velocity;
        }
    }

    public void moveRight() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), 90);
        if (canMoveRight()) {
            x += velocity;
        }
    }


    boolean canMoveUp() {
        return canWalkThrough.contains(Map.map[(int) ((z - velocity) / 200)][(int) (x / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z - velocity) / 200)][(int) ((x + width) / 200)]);
    }

    boolean canMoveDown() {
        return canWalkThrough.contains(Map.map[(int) ((z + height + velocity) / 200)][(int) (x / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + height + velocity) / 200)][(int) ((x + width) / 200)]);
    }

    boolean canMoveLeft() {
        return canWalkThrough.contains(Map.map[(int) (z / 200)][(int) ((x - velocity) / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + height) / 200)][(int) ((x - velocity) / 200)]);
    }

    boolean canMoveRight() {
        return canWalkThrough.contains(Map.map[(int) (z / 200)][(int) ((x + width + velocity) / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + height) / 200)][(int) ((x + width + velocity) / 200)]);
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(modelInstance, MyGdxGame.getEnvironment());
    }

    public abstract void update();

    protected boolean collisionWith(Entity other) {
        return (game.getPlayer().x < other.x + other.width &&
                game.getPlayer().x + game.getPlayer().width > other.x &&
                game.getPlayer().z < other.z + other.height &&
                game.getPlayer().z + game.getPlayer().height > other.z);
    }

    protected boolean collisionWith(ObjectInstance other) {
        return (game.getPlayer().x < other.getPosition().x + other.getWidth() &&
                game.getPlayer().x + game.getPlayer().width > other.getPosition().x &&
                game.getPlayer().z < other.getPosition().z + other.getHeight() &&
                game.getPlayer().z + game.getPlayer().height > other.getPosition().z);
    }


}
