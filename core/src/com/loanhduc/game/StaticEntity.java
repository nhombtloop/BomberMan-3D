package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;
import java.util.List;

public abstract class StaticEntity extends Entity{
    List<ObjectInstance> rallyEntity = new ArrayList<>();

    public void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        ObjectInstance objectInstance = new ObjectInstance(modelInstance, x, y, z);
        width = 200;
        height = 200;
        rallyEntity.add(objectInstance);
    }

    public void remove(ObjectInstance objectInstance) {
        rallyEntity.remove(objectInstance);
    }

    public void render() {
        for (ObjectInstance objectInstance : rallyEntity) {
            MyGdxGame.getModelBatch().render(objectInstance.modelInstance, MyGdxGame.getEnvironment());
        }
    }

    public List<ObjectInstance> getRallyEntity() {
        return rallyEntity;
    }
}
