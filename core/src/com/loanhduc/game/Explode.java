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

    public static class Fire extends StaticEntity {
        List<AnimationController> rallyAnimation = new ArrayList<>();
        boolean isExplode = false;

        public Fire() {
            path = "fire.g3db";
        }

        public void spawn(int x, int z, int size) {
            ModelInstance modelInstance = new ModelInstance(model);
            modelInstance.transform.setToTranslation(x, 0, z);
            ObjectInstance objectInstance = new ObjectInstance(modelInstance);
            rallyEntity.add(objectInstance);
            AnimationController ani = new AnimationController(modelInstance);
            ani.setAnimation("Armature|idle", -1);
            rallyAnimation.add(ani);
            for (int i = 1; i <= size; i++) {
                if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
                ModelInstance modelInstance1 = new ModelInstance(model);
                modelInstance1.transform.setToTranslation(x + i * 200, 0, z);
                ObjectInstance objectInstance1 = new ObjectInstance(modelInstance1);
                rallyEntity.add(objectInstance1);
                AnimationController ani1 = new AnimationController(modelInstance1);
                ani1.setAnimation("Armature|idle", -1);
                rallyAnimation.add(ani1);
            }
            for (int i = 1; i <= size; i++) {
                if (Map.map[(z + i * 200) / Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
                ModelInstance modelInstance2 = new ModelInstance(model);
                modelInstance2.transform.setToTranslation(x, 0, z + i * 200);
                ObjectInstance objectInstance2 = new ObjectInstance(modelInstance2);
                rallyEntity.add(objectInstance2);
                AnimationController ani2 = new AnimationController(modelInstance2);
                ani2.setAnimation("Armature|idle", -1);
                rallyAnimation.add(ani2);
            }
            for (int i = -1; i >= -size; i--) {
                if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
                ModelInstance modelInstance1 = new ModelInstance(model);
                modelInstance1.transform.setToTranslation(x + i * 200, 0, z);
                ObjectInstance objectInstance1 = new ObjectInstance(modelInstance1);
                rallyEntity.add(objectInstance1);
                AnimationController ani1 = new AnimationController(modelInstance1);
                ani1.setAnimation("Armature|idle", -1);
                rallyAnimation.add(ani1);
            }
            for (int i = -1; i >= -size; i--) {
                if (Map.map[(z + i * 200) / Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
                ModelInstance modelInstance2 = new ModelInstance(model);
                modelInstance2.transform.setToTranslation(x, 0, z + i * 200);
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

        public void active(int x, int z, int size, MyGdxGame game) {
            isExplode = true;
            Map.map[z / Map.CELL_WIDTH][x / Map.CELL_WIDTH] = 'F';
            for (int i = 0; i <= size; i++) {
                if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
                Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] = 'F';
            }
            for (int i = 0; i <= size; i++) {
                if (Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
                Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] = 'F';
            }
            for (int i = 0; i >= -size; i--) {
                if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
                Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] = 'F';
            }
            for (int i = 0; i >= -size; i--) {
                if (Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
                Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] = 'F';
            }
            game.checkBurned();
        }
    }

    public void doneExplode(Fire doneFire, int x, int z, int size) {
        Map.map[z / Map.CELL_WIDTH][x / Map.CELL_WIDTH] = ' ';
        for (int i = 0; i <= size; i++) {
            if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
            Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] = ' ';
        }
        for (int i = 0; i <= size; i++) {
            if (Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
            Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] = ' ';
        }
        for (int i = 0; i >= -size; i--) {
            if (Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] == '#') break;
            Map.map[(z / Map.CELL_WIDTH)][(x + i * 200) / Map.CELL_WIDTH] = ' ';
        }
        for (int i = 0; i >= -size; i--) {
            if (Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] == '#') break;
            Map.map[(z + i*200)/ Map.CELL_WIDTH][(x / Map.CELL_WIDTH)] = ' ';
        }
        fire.remove(doneFire);
    }

    public void createExplode(int x, int z, int size) {
        Fire newFire = new Fire();
        newFire.create();
        newFire.spawn(x, z, size);
        fire.add(newFire);
        Utils.setTimeout(() -> newFire.active(x, z, size, game), 3000);
        Utils.setTimeout(() -> doneExplode(newFire, x, z, size), 3500);
    }

    public void renderExplode() {
        for (Explode.Fire i : fire) {
            if (i.isExplode) i.render();
        }
    }
}
