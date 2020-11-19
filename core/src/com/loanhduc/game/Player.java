package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class Player extends MovingEntity {
    private ModelInstance playerInstance;

    public Player() {
        path = "model2.g3db";
        velocity = 10;
    }

    @Override
    public void create() {
        super.create();
        playerInstance = new ModelInstance(model);
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(playerInstance, MyGdxGame.getEnvironment());
    }

    public ModelInstance getPlayerInstance() {
        return playerInstance;
    }

    @Override
    public void move(float x, float y, float z) {
        playerInstance.transform.setToTranslation(x, y, z);
    }
}
