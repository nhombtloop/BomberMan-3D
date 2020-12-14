package com.loanhduc.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.utils.UBJsonReader;
import com.loanhduc.game.screen.MyGdxGame;

public class Crispy extends Monster {
    AnimationController idle;
    AnimationController transform;
    boolean immortal = true;
    boolean wakeup = false;

    public Crispy(MyGdxGame game) {
        super(game);
        path = "crispy.g3db";
        velocity = 3;
    }

    @Override
    public void create() {
        UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle(path, Files.FileType.Internal));
        modelInstance = new ModelInstance(model);
        modelInstance.transform.setToTranslation(x, y, z);
        animationController_runs = new AnimationController(modelInstance);
        animationController_runs.setAnimation("Armature|Armature|Armature|run|Armature|run", -1);
        idle = new AnimationController(modelInstance);
        idle.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
        transform = new AnimationController(modelInstance);
        transform.setAnimation("Armature|Armature|Armature|transform_in|Armature|transform_in", 1);
    }

    @Override
    public void render() {
        if (wakeup) {
            if(checkCollisionWithFire() && !immortal) {
                game.getEnemy().getEnemies().remove(this);
                return;
            }
            MyGdxGame.getModelBatch().render(modelInstance, MyGdxGame.getEnvironment());
            if (x % Map.CELL_WIDTH == 0 && z % Map.CELL_WIDTH == 0) {
                chasing();
            }
            move();
            animationController_runs.update(Gdx.graphics.getDeltaTime());
            return;
        }
        if(checkCollisionWithFire() && !wakeup) {
            Utils.setTime(this::setWakeup, 2.0);
            transform.update((float) Utils.DELTA_TIME);
            Utils.setTime(this::setImmortal, 1.0);
            return;
        }
        MyGdxGame.getModelBatch().render(modelInstance, MyGdxGame.getEnvironment());
        idle.update((float) Utils.DELTA_TIME);
    }

    public void setWakeup() {
        wakeup = true;
    }

    public void setImmortal() {
        immortal = false;
    }
}
