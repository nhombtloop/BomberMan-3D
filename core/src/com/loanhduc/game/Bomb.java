package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends MovingEntity {
    ModelInstance modelInstance;
    boolean isSet = false;

    public Bomb() {
        path = "bomb.g3db";
    }

    @Override
    public void moveTo(float x, float y, float z) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(modelInstance, MyGdxGame.getEnvironment());
    }
}
