package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;
import java.util.List;

public abstract class StaticEntity extends Entity {
    List<ObjectInstance> rallyEntity = new ArrayList<>();

    public void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        ObjectInstance objectInstance = new ObjectInstance(modelInstance);
        width = 200;
        height = 200;
        rallyEntity.add(objectInstance);
    }

    public void render() {
        for (int i = 0; i < rallyEntity.size(); i++) {
            MyGdxGame.getModelBatch().render(rallyEntity.get(i).getModelInstance(), MyGdxGame.getEnvironment());
        }
    }

    public void checkBurned(char[][] map, int cell) {
        for (int i = 0; i < rallyEntity.size(); i++) {
            Vector3 pos = rallyEntity.get(i).getPosition();
            if (map[(int) ((pos.z+100) / cell)][(int) ((pos.x+100) / cell)] == 'F') {
                rallyEntity.remove(i);
                i--;
            }
        }
    }
}
