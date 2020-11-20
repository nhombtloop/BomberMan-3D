package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class Player extends MovingEntity {
    private ModelInstance playerInstance;
    public Bomb bomb = new Bomb() ;
    protected float x;
    protected float y = 0;
    protected float z;

    public Player() {
        path = "model2.g3db";
        velocity = 10;
    }

    @Override
    public void create() {
        super.create();
        playerInstance = new ModelInstance(model);
        animationController = new AnimationController(playerInstance);
        animationController.setAnimation("mixamo.com", -1);
        bomb.create();
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(playerInstance, MyGdxGame.getEnvironment());
        if(bomb.isSet) {
            bomb.render();
        }
        animationController.update(Gdx.graphics.getDeltaTime());
    }

    public ModelInstance getPlayerInstance() {
        return playerInstance;
    }

    @Override
    public void move(float x, float y, float z) {
        playerInstance.transform.setToTranslation(x, y, z);
    }

    public void createBomb() {
        bomb.modelInstance = new ModelInstance(bomb.model);
        int cellX = Math.round(x / 200)  * 200;
        int cellZ = Math.round(z / 200) * 200;
        bomb.modelInstance.transform.setToTranslation(cellX, y ,cellZ);
        bomb.isSet = true;
        AnimationController bombController = new AnimationController(bomb.modelInstance);
        bombController.setAnimation("Armature|idle", -1);
    }

    public void moveUp() {
        z -= velocity;
    }
    public void moveDown() {
        z += velocity;
    }
    public void moveLeft() {
        x -= velocity;
    }
    public void moveRight() {
        x += velocity;
    }
}
