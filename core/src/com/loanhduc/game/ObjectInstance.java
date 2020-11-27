package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class ObjectInstance {
    ModelInstance modelInstance;
    float x;
    float y;
    float z;
    float width = 200;
    float height = 200;

    public ObjectInstance(ModelInstance modelInstance, float x, float y, float z) {
        this.modelInstance = modelInstance;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void disappear() {
        Map.map[(int) z/200][(int) x/200] = ' ';
    }

    public char getEntity() {
        return Map.map[(int) z/200][(int) x/200];
    }
}
