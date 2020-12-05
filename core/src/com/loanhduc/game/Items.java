package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;
import java.util.List;

public abstract class Items extends StaticEntity {
    protected static List<ObjectInstance> itemInstances = new ArrayList<>();

    public static List<ObjectInstance> getItemInstances() {
        return itemInstances;
    }

    @Override
    public void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        ObjectInstance objectInstance = new ObjectInstance(modelInstance);
        width = 200;
        height = 200;
        rallyEntity.add(objectInstance);
        itemInstances.add(objectInstance);
    }

    public static void removeInstance(ObjectInstance objectInstance) {
        itemInstances.remove(objectInstance);
    }

    @Override
    public void render() {
        for (ObjectInstance objectInstance : itemInstances) {
            MyGdxGame.getModelBatch().render(objectInstance.getModelInstance(), MyGdxGame.getEnvironment());
        }
    }
}
