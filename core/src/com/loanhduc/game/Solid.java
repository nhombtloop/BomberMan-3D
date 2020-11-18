package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;
import java.util.List;

public class Solid extends StaticEntity {
    private static List<ModelInstance> solids = new ArrayList<>();

    @Override
    public void create() {
        path = "solid.g3db";
        super.create();
    }

    public void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        solids.add(modelInstance);
    }

    @Override
    public void render() {
        for (ModelInstance wall : solids) {
            MyGdxGame.getModelBatch().render(wall, MyGdxGame.getEnvironment());
        }
    }
}
