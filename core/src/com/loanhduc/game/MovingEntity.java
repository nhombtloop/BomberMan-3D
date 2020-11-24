package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public abstract class MovingEntity extends Entity {
    protected float x;
    protected float y = 0;
    protected float z;
    protected float velocity;
    protected ModelInstance modelInstance;
    protected ArrayList<Character> canWalkThrough = new ArrayList<>();

    MovingEntity() {
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
                && canWalkThrough.contains(Map.map[(int) ((z - velocity) / 200)][(int) ((x + 150) / 200)]);
    }

    boolean canMoveDown() {
        return canWalkThrough.contains(Map.map[(int) ((z + 150 + velocity) / 200)][(int) (x / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + 150 + velocity) / 200)][(int) ((x + 150) / 200)]);
    }

    boolean canMoveLeft() {
        return canWalkThrough.contains(Map.map[(int) (z / 200)][(int) ((x - velocity) / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + 150) / 200)][(int) ((x - velocity) / 200)]);
    }

    boolean canMoveRight() {
        return canWalkThrough.contains(Map.map[(int) (z / 200)][(int) ((x + 150 + velocity) / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + 150) / 200)][(int) ((x + 150 + velocity) / 200)]);
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(modelInstance, MyGdxGame.getEnvironment());
    }

    public abstract void update();
}
