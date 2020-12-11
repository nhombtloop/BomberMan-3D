package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;
import java.util.List;

public class WinPortal extends StaticEntity {
    private List<AnimationController> animationControllers = new ArrayList<>();
    private boolean isOpen = false;

    public WinPortal() {
        path = "winPortal/exit.g3db";
    }

    @Override
    public void spawn(float x, float y, float z) {
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        ObjectInstance objectInstance = new ObjectInstance(modelInstance);
        rallyEntity.add(objectInstance);

        AnimationController animationController = new AnimationController(modelInstance);
        animationController.setAnimation("Armature|idle", -1);
        animationControllers.add(animationController);
    }

    @Override
    public void render() {
        super.render();
        for (int i = 0; i < rallyEntity.size(); i++) {
            MyGdxGame.getModelBatch().render(rallyEntity.get(i).getModelInstance(), MyGdxGame.getEnvironment());
            animationControllers.get(i).update(Gdx.graphics.getDeltaTime());
        }
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
