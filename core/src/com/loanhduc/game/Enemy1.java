package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.loanhduc.game.screen.MyGdxGame;

import java.util.ArrayList;

public class Enemy1 {
    static ArrayList<Robot> enemy1 = new ArrayList<>();

    static class Robot extends MovingEntity {
        int rdNumber = 0;
        boolean turnLeft = true;
        boolean turnRight = false;
        AnimationController animationController_normal;

        public Robot() {
            path = "robot.g3db";
            velocity = 1;
            width = 200;
            height = 200;
            canWalkThrough.add('p');
            canWalkThrough.add('1');
            canWalkThrough.add(' ');
            canWalkThrough.add('b'); // bomb item
            canWalkThrough.add('s'); // speed item
            canWalkThrough.add('f'); // flame item
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
            if (turnLeft) {
                this.moveLeft();
            } else if (turnRight) {
                this.moveRight();
            }
            animationController_normal.update(Gdx.graphics.getDeltaTime());
        }

        @Override
        public void update() {

        }

        @Override
        public void moveLeft() {
            modelInstance.transform.setToTranslation(x, y, z);
            modelInstance.transform.rotate(new Vector3(0, 1, 0), -90);
            if (canMoveLeft()) {
                x -= velocity;
            } else {
                turnLeft = false;
                turnRight = true;
            }
            checkCollision();
        }

        @Override
        public void moveRight() {
            modelInstance.transform.setToTranslation(x, y, z);
            modelInstance.transform.rotate(new Vector3(0, 1, 0), 90);
            if (canMoveRight()) {
                x += velocity;
            } else {
                turnRight = false;
                turnLeft = true;
            }
            checkCollision();
        }

        private boolean collisionWithPlayer() {
            if(collisionWith(this)) {
                return true;
            }
            return false;
        }

        public void checkCollision() {
            if (collisionWithPlayer()) {
                MyGdxGame.getPlayer().setDead(true);
            }
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
                    Map.map[i][j] = ' ';
                }
            }
        }
    }

    public static void renderEnemy1() {
        for (Robot i : enemy1) {
            i.render();
        }
    }

}
