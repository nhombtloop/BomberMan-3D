package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import java.util.ArrayList;
import java.util.List;

public class Portal extends StaticEntity {
    private List<AnimationController> animationControllers = new ArrayList<>();

    public Portal() {
        path = "portal.g3db";
    }

    @Override
    public void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        rallyEntity.add(modelInstance);

        AnimationController animationController = new AnimationController(modelInstance);
        animationController.setAnimation("Armature|idle", -1);
        animationControllers.add(animationController);
    }

    @Override
    public void render() {
        super.render();
        for (int i = 0; i < rallyEntity.size(); i++) {

            MyGdxGame.getModelBatch().render(rallyEntity.get(i), MyGdxGame.getEnvironment());
            animationControllers.get(i).update(Gdx.graphics.getDeltaTime());
        }
    }
}
