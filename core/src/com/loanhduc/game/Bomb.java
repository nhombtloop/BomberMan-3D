package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.loanhduc.game.screen.MyGdxGame;
import com.loanhduc.game.util.SoundEffect;

import java.util.ArrayList;
import java.util.List;


public class Bomb extends StaticEntity {
    List<AnimationController> animationControllers = new ArrayList<>();
    List<ModelInstance> modelInstances = new ArrayList<>();
    boolean isSet = false;
    MyGdxGame myGdxGame;

    public Bomb(MyGdxGame game) {
        path = "bomb.g3db";
        this.myGdxGame = game;
    }


    public void explode(ModelInstance instance, AnimationController controller, int bombInstanceX, int bombInstanceZ) {
        SoundEffect.playSoundBoom();
        Map.map[(bombInstanceZ / Map.CELL_WIDTH)][(bombInstanceX / Map.CELL_WIDTH)] = ' ';
        myGdxGame.getPlayer().bombSet--;
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
