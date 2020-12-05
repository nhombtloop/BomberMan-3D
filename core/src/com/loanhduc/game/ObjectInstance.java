package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;


public class ObjectInstance {
    private ModelInstance modelInstance;
    private Vector3 position;
    private int width = 200;
    private int height = 200;

    public ObjectInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        position = new Vector3();
        modelInstance.transform.getTranslation(position);
    }

    public void disappear() {
        Map.map[(int) position.z/200][(int) position.x/200] = ' ';
    }

    public char getEntity() {
        return Map.map[(int) position.z/200][(int) position.x/200];
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public void setModelInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
