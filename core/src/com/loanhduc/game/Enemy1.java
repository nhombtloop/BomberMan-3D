package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.loanhduc.game.screen.MyGdxGame;
import com.loanhduc.game.util.Utils;

import java.util.ArrayList;

public class Enemy1 {
    MyGdxGame game;
    private ArrayList<Robot> enemy1 = new ArrayList<>();

    public ArrayList<Robot> getEnemy1() {
        return enemy1;
    }

    public Enemy1 (MyGdxGame game) {
        this.game = game;
    }
    public class Robot extends MovingEntity {
        int rdNumber = 0;
        private boolean turnLeft = true;
        private boolean turnRight = false;
        private boolean turnUp = false;
        private boolean turnDown = false;
        AnimationController animationController_normal;
        AnimationController animationController_runs;

        public Robot() {
            super(Enemy1.this.game);
            path = "robot.g3db";
            velocity = 3;
            width = 150;
            height = 150;
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
            } else if(turnUp) {
                moveUp();
            } else if(turnDown) {
                moveDown();
            }
            animationController_runs.update(Gdx.graphics.getDeltaTime());
            modelInstance.transform.scl(2);
        }

        @Override
        public void update() {

        }

        private void moveRandomLeftRight() {
            if(canMoveRight() && canMoveLeft()) {
                if(Utils.generateRandom() == 1) {
                    turnRight = true;
                    turnDown = false;
                } else if(Utils.generateRandom() == 2) {
                    turnLeft = true;
                    turnDown = false;
                } else {
                    if(turnDown) {
                        turnUp = true;
                        turnDown = false;
                    } else {
                        turnDown = true;
                        turnLeft = false;
                    }
                }
            } else if(canMoveRight()) {
                if(turnDown) {
                    turnRight = true;
                    turnDown = false;
                } else {
                    turnRight = true;
                    turnUp = false;
                }
            } else if(canMoveLeft()) {
                if(turnUp) {
                    turnLeft = true;
                    turnUp = false;
                } else {
                    turnLeft = true;
                    turnDown = false;
                }
            } else {
                if(turnDown) {
                    turnUp = true;
                    turnDown = false;
                } else {
                    turnUp = false;
                    turnDown = true;
                }
            }
        }

        private void moveRandomUpDown() {
            System.out.println(canMoveUp());
            if(canMoveUp() && canMoveDown()) {
                if(Utils.generateRandom() == 1) {
                    turnUp = true;
                    turnLeft = false;
                } else if(Utils.generateRandom() == 2) {
                    turnDown = true;
                    turnLeft = false;
                } else {
                    if(turnLeft) {
                        turnRight = true;
                        turnLeft = false;
                    } else {
                        turnRight = false;
                        turnLeft = true;
                    }
                }
            } else if(canMoveUp()) {
                if(turnLeft) {
                    turnUp = true;
                    turnLeft = false;
                } else {
                    turnUp = true;
                    turnRight = false;
                }
            } else if(canMoveDown()) {
                if(turnLeft) {
                    turnDown = true;
                    turnLeft = false;
                } else {
                    turnDown = true;
                    turnRight = false;
                }
            } else {
                if(turnLeft) {
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
            checkCollision();
        }
        @Override
        public void moveDown() {
            modelInstance.transform.setToTranslation(x, y, z);
            modelInstance.transform.rotate(new Vector3(0, 1, 0), 0);
            if (canMoveDown()) {
                z += velocity;
            } else  {
                moveRandomLeftRight();
            }
            checkCollision();
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
            checkCollision();
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
                game.getPlayer().setDead(true);
            }
        }

    }

    public  void createEnemy1() {
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

    public  void renderEnemy1() {
        for (Robot i : enemy1) {
            i.render();
        }
    }

}
