package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public abstract class MovingEntity extends Entity {
    protected float velocity;
    protected ModelInstance modelInstance;
    protected ArrayList<Character> canWalkThrough = new ArrayList<>();

    @Override
    public void create() {
        super.create();
        modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
    }

    MovingEntity() {
        canWalkThrough.add(' ');
    }

    public void moveUp() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), 180);
        if (canMoveUp()) {
            z -= velocity;
            if (collisionWithEnemy()) {
                System.out.println("collision");
                MyGdxGame.getPlayer().setDead(true);
            }
            if (collisionWithItem()) {
                System.out.println("item");
            }
        }
    }

    public void moveDown() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), 0);
        if (canMoveDown()) {
            z += velocity;
            if (collisionWithEnemy()) {
                System.out.println("collision");
                MyGdxGame.getPlayer().setDead(true);
            }
            if (collisionWithItem()) {
                System.out.println("item");
            }
        }
    }

    public void moveLeft() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), -90);
        if (canMoveLeft()) {
            x -= velocity;
            if (collisionWithEnemy()) {
                System.out.println("collision");
                MyGdxGame.getPlayer().setDead(true);
            }
            if (collisionWithItem()) {
                System.out.println("item");
            }
        }
    }

    public void moveRight() {
        modelInstance.transform.setToTranslation(x, y, z);
        modelInstance.transform.rotate(new Vector3(0, 1, 0), 90);
        if (canMoveRight()) {
            x += velocity;
            if (collisionWithEnemy()) {
                System.out.println("collision");
                MyGdxGame.getPlayer().setDead(true);
            }
            if (collisionWithItem()) {
                System.out.println("item");
            }
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

    private boolean collisionWith(Entity other) {
        return (MyGdxGame.getPlayer().x < other.x + other.width &&
                MyGdxGame.getPlayer().x + MyGdxGame.getPlayer().width > other.x &&
                MyGdxGame.getPlayer().z < other.z + other.height &&
                MyGdxGame.getPlayer().z + MyGdxGame.getPlayer().height > other.z);
    }

    public boolean collisionWithEnemy() {
        for (int i = 0; i < Enemy1.enemy1.size(); i++) {
            if(collisionWith(Enemy1.enemy1.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean collisionWithItem() {
        for (Items item : Items.getItems()) {
            if (collisionWith(item)) {
                return true;
            }
        }
        return false;
    }
}
