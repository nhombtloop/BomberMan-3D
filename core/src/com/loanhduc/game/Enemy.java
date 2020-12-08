package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.loanhduc.game.screen.MyGdxGame;
import com.loanhduc.game.util.Utils;

import java.util.ArrayList;

public class Enemy {
    MyGdxGame game;
    private ArrayList<MovingEntity> enemies = new ArrayList<>();

    public ArrayList<MovingEntity> getEnemies() {
        return enemies;
    }

    public Enemy(MyGdxGame game) {
        this.game = game;
    }

    public class Robot extends MovingEntity {
        private boolean turnLeft = true;
        private boolean turnRight = false;
        private boolean turnUp = false;
        private boolean turnDown = false;
        AnimationController animationController_runs;

        public Robot() {
            super(Enemy.this.game);
            path = "robot/robot.g3db";
            velocity = 3;
            width = 180;
            height = 180;
            canWalkThrough.add('p');
            canWalkThrough.add(' ');
            canWalkThrough.add('b'); // bomb item
            canWalkThrough.add('s'); // speed item
            canWalkThrough.add('f'); // flame item
            canWalkThrough.add('x');
            canWalkThrough.add('1');
            canWalkThrough.add('2');
        }

        @Override
        public void create() {
            super.create();
            animationController_runs = new AnimationController(modelInstance);
            animationController_runs.setAnimation("Armature|run", -1);
        }

        @Override
        public void render() {
            super.render();
            if (turnLeft) {
                this.moveLeft();
            } else if (turnRight) {
                this.moveRight();
            } else if (turnUp) {
                moveUp();
            } else if (turnDown) {
                moveDown();
            }
            animationController_runs.update(Gdx.graphics.getDeltaTime());
        }

        @Override
        public void update() {

        }

        private void moveRandomLeftRight() {
            if (canMoveRight() && canMoveLeft()) {
                if (Utils.generateRandom() == 1) {
                    turnRight = true;
                    turnDown = false;
                } else if (Utils.generateRandom() == 2) {
                    turnLeft = true;
                    turnDown = false;
                } else {
                    if (turnDown) {
                        turnUp = true;
                        turnDown = false;
                    } else {
                        turnDown = true;
                        turnLeft = false;
                    }
                }
            } else if (canMoveRight()) {
                if (turnDown) {
                    turnRight = true;
                    turnDown = false;
                } else {
                    turnRight = true;
                    turnUp = false;
                }
            } else if (canMoveLeft()) {
                if (turnUp) {
                    turnLeft = true;
                    turnUp = false;
                } else {
                    turnLeft = true;
                    turnDown = false;
                }
            } else {
                if (turnDown) {
                    turnUp = true;
                    turnDown = false;
                } else {
                    turnUp = false;
                    turnDown = true;
                }
            }
        }

        private void moveRandomUpDown() {
            if (canMoveUp() && canMoveDown()) {
                if (Utils.generateRandom() == 1) {
                    turnUp = true;
                    turnLeft = false;
                } else if (Utils.generateRandom() == 2) {
                    turnDown = true;
                    turnLeft = false;
                } else {
                    if (turnLeft) {
                        turnRight = true;
                        turnLeft = false;
                    } else {
                        turnRight = false;
                        turnLeft = true;
                    }
                }
            } else if (canMoveUp()) {
                if (turnLeft) {
                    turnUp = true;
                    turnLeft = false;
                } else {
                    turnUp = true;
                    turnRight = false;
                }
            } else if (canMoveDown()) {
                if (turnLeft) {
                    turnDown = true;
                    turnLeft = false;
                } else {
                    turnDown = true;
                    turnRight = false;
                }
            } else {
                if (turnLeft) {
                    turnLeft = false;
                    turnRight = true;
                } else {
                    turnRight = false;
                    turnLeft = true;
                }
            }
        }

        @Override
        public void moveLeft() {
            modelInstance.transform.setToTranslation(x, y, z);
            modelInstance.transform.rotate(new Vector3(0, 1, 0), -90);
            if (canMoveLeft()) {
                x -= velocity;
            } else {
                moveRandomUpDown();
            }
        }

        @Override
        public void moveDown() {
            modelInstance.transform.setToTranslation(x, y, z);
            modelInstance.transform.rotate(new Vector3(0, 1, 0), 0);
            if (canMoveDown()) {
                z += velocity;
            } else {
                moveRandomLeftRight();
            }
        }

        @Override
        public void moveUp() {
            modelInstance.transform.setToTranslation(x, y, z);
            modelInstance.transform.rotate(new Vector3(0, 1, 0), 180);
            if (canMoveUp()) {
                z -= velocity;
            } else {
                moveRandomLeftRight();
            }
        }

        @Override
        public void moveRight() {
            modelInstance.transform.setToTranslation(x, y, z);
            modelInstance.transform.rotate(new Vector3(0, 1, 0), 90);
            if (canMoveRight()) {
                x += velocity;
            } else {
                moveRandomUpDown();
            }
        }
    }

    public class Monster extends MovingEntity {
        AnimationController animationController_runs;

        public Monster() {
            super(Enemy.this.game);
            path = "monster/monster.g3db";
            velocity = 5;
            width = 150;
            height = 150;
            canWalkThrough.add('p');
            canWalkThrough.add(' ');
            canWalkThrough.add('b'); // bomb item
            canWalkThrough.add('s'); // speed item
            canWalkThrough.add('f'); // flame item
            canWalkThrough.add('x');
            canWalkThrough.add('1');
            canWalkThrough.add('2');
        }

        @Override
        public void create() {
            super.create();
            animationController_runs = new AnimationController(modelInstance);
            animationController_runs.setAnimation("Armature|run", -1);
        }

        @Override
        public void render() {
            super.render();
            chasing();
            animationController_runs.update(Gdx.graphics.getDeltaTime());
        }

        private void chasing() {
            float playerX = game.getPlayer().x;
            float playerZ = game.getPlayer().z;
            float distanceX = Math.abs(playerX - this.x);
            float distanceZ = Math.abs(playerZ - this.z);
            if (this.x < playerX && this.z >= playerZ) {
                if (distanceX <= distanceZ && canMoveUp())
                    moveUp();
                else if (distanceX > distanceZ && canMoveRight())
                    moveRight();
            } else if (this.x >= playerX && this.z > playerZ) {
                if (distanceX <= distanceZ && canMoveUp())
                    moveUp();
                else if (distanceX > distanceZ && canMoveLeft())
                    moveLeft();
            } else if (this.x > playerX && this.z <= playerZ) {
                if (distanceX <= distanceZ && canMoveDown())
                    moveDown();
                else if (distanceX > distanceZ && canMoveLeft())
                    moveLeft();
            } else if (this.x <= playerX && this.z < playerZ) {
                if (distanceX <= distanceZ && canMoveDown())
                    moveDown();
                else if (distanceX > distanceZ && canMoveRight())
                    moveRight();
            }
        }

        @Override
        public void update() {

        }
    }


    public void createEnemy() {
        for (int i = 0; i < Map.ROWS; i++) {
            for (int j = 0; j < Map.COLUMNS; j++) {
                if (Map.map[i][j] == '1') {
                    Robot newRb = new Robot();
                    newRb.x = j * Map.CELL_WIDTH;
                    newRb.z = i * Map.CELL_WIDTH;
                    newRb.create();
                    enemies.add(newRb);
                    Map.map[i][j] = ' ';
                }
                if (Map.map[i][j] == '2') {
                    Monster newMonster = new Monster();
                    newMonster.x = j * Map.CELL_WIDTH;
                    newMonster.z = i * Map.CELL_WIDTH;
                    newMonster.create();
                    enemies.add(newMonster);
                    Map.map[i][j] = ' ';
                }
            }
        }
    }

    public void renderEnemy() {
        for (MovingEntity enemy : enemies) {
            enemy.render();
        }
    }
}
