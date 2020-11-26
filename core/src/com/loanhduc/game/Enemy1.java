package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import java.util.ArrayList;

public class Enemy1 {
    static ArrayList<Robot> enemy1 = new ArrayList<>();

    static class Robot extends MovingEntity {
        int rdNumber = 0;
        AnimationController animationController_normal;

        public Robot() {
            path = "robot.g3db";
            velocity = 1;
            width = 200;
            height = 200;
            canWalkThrough.add('1');
            canWalkThrough.add('#');
            canWalkThrough.add('*');
        }

        @Override
        public void create() {
            super.create();
            animationController_normal = new AnimationController(modelInstance);
            animationController_normal.setAnimation("Armature|idle", -1);
            modelInstance.transform.scl(2);
        }

        @Override
        public void render() {
            super.render();
            animationController_normal.update(Gdx.graphics.getDeltaTime());
        }

        @Override
        public void update() {

        }

    }

    public static void createEnemy1() {
        for (int i = 0; i < Map.ROWS; i++) {
            for (int j = 0; j < Map.COLUMNS; j++) {
                if (Map.map[i][j] == '1') {
                    Robot newRb = new Robot();
                    newRb.x = j * Map.CELL_WIDTH;
                    newRb.z = i * Map.CELL_WIDTH;
                    newRb.create();
                    enemy1.add(newRb);
                }
            }
        }
    }

    public static void renderEnemy1() {
        for(Robot i : enemy1) {
            i.render();
        }
    }

}
