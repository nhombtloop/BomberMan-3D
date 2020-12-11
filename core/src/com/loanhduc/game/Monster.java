package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.loanhduc.game.screen.MyGdxGame;

public class Monster extends Enemy {
    private boolean isMovingLeft = true;
    private boolean isMovingRight = false;
    private boolean isMovingUp = false;
    private boolean isMovingDown = false;
    AnimationController animationController_runs;

    public Monster(MyGdxGame game) {
        super(game);
        path = "monster/monster.g3db";
        velocity = 5;
        width = 195;
        height = 195;
        canWalkThrough.add('p');
        canWalkThrough.add(' ');
        canWalkThrough.add('b'); // bomb item
        canWalkThrough.add('s'); // speed item
        canWalkThrough.add('f'); // flame item
        canWalkThrough.add('x');
        canWalkThrough.add('1');
        canWalkThrough.add('2');
        canWalkThrough.add('F');
    }

    @Override
    public void create() {
        super.create();
        animationController_runs = new AnimationController(modelInstance);
        animationController_runs.setAnimation("Armature|run", -1);
    }

    @Override
    public void render() {
        if(checkCollisionWithFire()) {
            game.getEnemy().getEnemies().remove(this);
            return;
        }
        super.render();
        if (x % Map.CELL_WIDTH == 0 && z % Map.CELL_WIDTH == 0) {
            chasing();
        }
        move();
        animationController_runs.update(Gdx.graphics.getDeltaTime());
    }

    private void move() {
        if (isMovingLeft) {
            this.moveLeft();
        } else if (isMovingRight) {
            this.moveRight();
        } else if (isMovingUp) {
            moveUp();
        } else if (isMovingDown) {
            moveDown();
        }
    }

    private void chasing() {
        float playerX = game.getPlayer().x;
        float playerZ = game.getPlayer().z;
        float distanceX = Math.abs(playerX - this.x);
        float distanceZ = Math.abs(playerZ - this.z);
        if (distanceX > distanceZ) {
            // move Right or Left
            if (this.x > playerX && canMoveLeft()) {
                isMovingLeft = true;
                isMovingDown = false;
                isMovingUp = false;
                isMovingRight = false;
            } else if (this.x < playerX && canMoveRight()) {
                isMovingLeft = false;
                isMovingDown = false;
                isMovingUp = false;
                isMovingRight = true;
            }
        } else {
            // move Up or Down
            if (this.z < playerZ && canMoveDown()) {
                isMovingLeft = false;
                isMovingDown = true;
                isMovingUp = false;
                isMovingRight = false;
            } else if (this.z > playerZ && canMoveUp()) {
                isMovingLeft = false;
                isMovingDown = false;
                isMovingUp = true;
                isMovingRight = false;
            }
        }

    }

    @Override
    public void update() {

    }
}
