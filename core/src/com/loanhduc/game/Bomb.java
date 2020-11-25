package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import java.util.ArrayList;
import java.util.List;


public class Bomb extends StaticEntity {
    List<AnimationController> animationControllers = new ArrayList<>();
    List<ModelInstance> modelInstances = new ArrayList<>();
    boolean isSet = false;

    public Bomb() {
        path = "bomb.g3db";
    }


    public void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public void explode(ModelInstance instance, AnimationController controller, int bombInstanceX, int bombInstanceZ) {
        // destroy boom here
        System.out.println("destroy boom");
        SoundEffect.playSoundBoom();
        Map.map[(bombInstanceZ / Map.CELL_WIDTH)][(bombInstanceX / Map.CELL_WIDTH)] = ' ';
        MyGdxGame.getPlayer().bombSet--;
        modelInstances.remove(instance);
        animationControllers.remove(controller);
    }

    @Override
    public void render() {
        for (int i = 0; i < modelInstances.size(); i++) {
            MyGdxGame.getModelBatch().render(modelInstances.get(i), MyGdxGame.getEnvironment());
            animationControllers.get(i).update(Gdx.graphics.getDeltaTime());
        }

    }
}
