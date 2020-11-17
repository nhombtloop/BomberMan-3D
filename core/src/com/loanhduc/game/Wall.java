package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;
import java.util.List;

public class Wall extends StaticEntity {
    private static List<ModelInstance> walls = new ArrayList<>();

    @Override
    public void create() {
        path = "box.g3db";
        super.create();
    }

    public static void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        walls.add(modelInstance);
    }

    @Override
    public void render() {
        for (ModelInstance wall : walls) {
            MyGdxGame.getModelBatch().render(wall, MyGdxGame.getEnvironment());
        }
    }
}
