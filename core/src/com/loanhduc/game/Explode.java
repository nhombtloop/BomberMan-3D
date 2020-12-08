package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;
import java.util.List;

public class Explode {
    MyGdxGame game;
    private ArrayList<Fire> fire = new ArrayList<>();

    public Explode(MyGdxGame game) {
        this.game = game;
    }

    public class Fire extends StaticEntity {
        List<AnimationController> rallyAnimation = new ArrayList<>();
        boolean isExplode = false;

        public Fire() {
            path = "fire.g3db";
        }

        public void spawn(float x, float y, float z, int size) {
            for (int i = -size; i <= size; i++) {
                ModelInstance modelInstance1 = new ModelInstance(model);
                modelInstance1.transform.setToTranslation(x + i * 200, y, z);
                ObjectInstance objectInstance1 = new ObjectInstance(modelInstance1);
                rallyEntity.add(objectInstance1);
                AnimationController ani1 = new AnimationController(modelInstance1);
                ani1.setAnimation("Armature|idle", -1);
                rallyAnimation.add(ani1);

                ModelInstance modelInstance2 = new ModelInstance(model);
                modelInstance2.transform.setToTranslation(x, y, z + i * 200);
                ObjectInstance objectInstance2 = new ObjectInstance(modelInstance2);
                rallyEntity.add(objectInstance2);
                AnimationController ani2 = new AnimationController(modelInstance2);
                ani2.setAnimation("Armature|idle", -1);
                rallyAnimation.add(ani2);
            }
        }

        @Override
        public void render() {
            super.render();
            for (AnimationController i : rallyAnimation) {
                i.update(Gdx.graphics.getDeltaTime());
            }
        }

        public void active() {
            isExplode = true;
        }
    }

    public void createExplode(int x, int y, int z, int size) {
        Fire newFire = new Fire();
        newFire.create();
        newFire.spawn(x, y, z, size);
        fire.add(newFire);
        Utils.setTimeout(newFire::active, 3000);
        Utils.setTimeout(() -> fire.remove(newFire), 4000);
    }

    public void renderExplode() {
        for (Explode.Fire i : fire) {
            if(i.isExplode) i.render();
        }
    }
}
