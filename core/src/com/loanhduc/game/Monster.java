package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.loanhduc.game.screen.MyGdxGame;

public class Monster extends Enemy {
    AnimationController animationController_runs;

    public Monster(MyGdxGame game) {
        super(game);
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
