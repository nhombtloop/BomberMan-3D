package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;
import java.util.List;

public abstract class StaticEntity extends Entity{
    protected List<ModelInstance> rallyEntity = new ArrayList<>();

    public void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        rallyEntity.add(modelInstance);
    }

    public void render() {
        for (ModelInstance i : rallyEntity) {
            MyGdxGame.getModelBatch().render(i, MyGdxGame.getEnvironment());
        }
    }
}
