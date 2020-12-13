package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;
import java.util.List;

public class Explode {
    MyGdxGame game;
    private ArrayList<Fire> fire = new ArrayList<>();

    public Explode(MyGdxGame game) {
        this.game = game;
    }

    public static class Fire extends StaticEntity {
        List<AnimationController> rallyAnimation = new ArrayList<>();
        boolean isExplode = false;

        public Fire() {
            path = "fire.g3db";
        }

        public void spawn(int x, int z, int size) {
                width = 200;
                height = 200;
                ModelInstance modelInstance = new ModelInstance(model);
                modelInstance.transform.setToTranslation(x, 0, z);
                ObjectInstance objectInstance = new ObjectInstance(modelInstance);
                rallyEntity.add(objectInstance);
                AnimationController ani = new AnimationController(modelInstance);
                ani.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
                rallyAnimation.add(ani);
                for (int i = 1; i <= size; i++) {
                    if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
                    ModelInstance modelInstance1 = new ModelInstance(model);
                    modelInstance1.transform.setToTranslation(x + i * 200, 0, z);
                    ObjectInstance objectInstance1 = new ObjectInstance(modelInstance1);
                    rallyEntity.add(objectInstance1);
                    AnimationController ani1 = new AnimationController(modelInstance1);
                    ani1.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
                    rallyAnimation.add(ani1);
                    if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '*') break;
                }
                for (int i = 1; i <= size; i++) {
                    if (Map.map[(z + i * 200) / Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
                    ModelInstance modelInstance2 = new ModelInstance(model);
                    modelInstance2.transform.setToTranslation(x, 0, z + i * 200);
                    ObjectInstance objectInstance2 = new ObjectInstance(modelInstance2);
                    rallyEntity.add(objectInstance2);
                    AnimationController ani2 = new AnimationController(modelInstance2);
                    ani2.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
                    rallyAnimation.add(ani2);
                    if (Map.map[(z + i * 200) / Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '*') break;
                }
                for (int i = -1; i >= -size; i--) {
                    if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
                    ModelInstance modelInstance1 = new ModelInstance(model);
                    modelInstance1.transform.setToTranslation(x + i * 200, 0, z);
                    ObjectInstance objectInstance1 = new ObjectInstance(modelInstance1);
                    rallyEntity.add(objectInstance1);
                    AnimationController ani1 = new AnimationController(modelInstance1);
                    ani1.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
                    rallyAnimation.add(ani1);
                    if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '*') break;
                }
                for (int i = -1; i >= -size; i--) {
                    if (Map.map[(z + i * 200) / Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
                    ModelInstance modelInstance2 = new ModelInstance(model);
                    modelInstance2.transform.setToTranslation(x, 0, z + i * 200);
                    ObjectInstance objectInstance2 = new ObjectInstance(modelInstance2);
                    rallyEntity.add(objectInstance2);
                    AnimationController ani2 = new AnimationController(modelInstance2);
                    ani2.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
                    rallyAnimation.add(ani2);
                    if (Map.map[(z + i * 200) / Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '*') break;
                }
        }

        @Override
        public void render() {
            super.render();
            for (AnimationController i : rallyAnimation) {
                i.update(Gdx.graphics.getDeltaTime());
            }
        }

        public void active(MyGdxGame game) {
            isExplode = true;
            for (ObjectInstance objectInstance : rallyEntity) {
                Vector3 pos = objectInstance.getPosition();
                Map.map[(int) ((pos.z + 1) / Map.CELL_WIDTH)][(int) ((pos.x + 1) / Map.CELL_WIDTH)] = 'F';
            }
        }
    }

    public void doneExplode(Fire doneFire, int x, int z, int size) {
        for (ObjectInstance objectInstance : doneFire.rallyEntity) {
            Vector3 pos = objectInstance.getPosition();
            Map.map[(int) ((pos.z + 1) / Map.CELL_WIDTH)][(int) ((pos.x + 1) / Map.CELL_WIDTH)] = ' ';
        }
        fire.remove(doneFire);
    }

    public void createExplode(int x, int z, int size) {
        Fire newFire = new Fire();
        newFire.create();
        newFire.spawn(x, z, size);
        fire.add(newFire);
        newFire.active(game);
        Utils.setTime(() -> doneExplode(newFire, x, z, size), 0.8);
    }

    public void renderExplode() {
        for (int i = 0; i < fire.size(); i++) {
            Fire tmp = fire.get(i);
            if (tmp.isExplode) tmp.render();
        }
    }

}