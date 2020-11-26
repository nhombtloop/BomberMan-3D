package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class Player extends MovingEntity {
    AnimationController animationController_run;
    AnimationController animationController_normal;
    AnimationController animationController_dead;
    public Bomb bomb = new Bomb();
    int maxBomb;
    int bombSet;
    boolean isDead;


    public Player() {
        path = "bomberman.g3db";
        isDead = false;
        velocity = 10;
        bombSet = 0;
        maxBomb = 2;
        width = 150;
        height = 150;
        canWalkThrough.add(' ');
        canWalkThrough.add('x'); // portal
        canWalkThrough.add('b'); // bomb item
        canWalkThrough.add('s'); // speed item
        canWalkThrough.add('f'); // flame item
        canWalkThrough.add('1'); // enemy1

    }

    @Override
    public void create() {
        for (int i = 0; i < Map.ROWS; i++) {
            for (int j = 0; j < Map.COLUMNS; j++) {
                if (Map.map[i][j] == 'p') {
                    x = j * Map.CELL_WIDTH;
                    z = i * Map.CELL_WIDTH;
                    Map.map[i][j] = ' ';
                    break;
                }
            }
        }
        super.create();
        animationController_run = new AnimationController(modelInstance);
        animationController_normal = new AnimationController(modelInstance);
        animationController_dead = new AnimationController(modelInstance);
        animationController_run.setAnimation("Armature|Armature|Armature|run|Armature|run", -1);
        animationController_normal.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
        animationController_dead.setAnimation("Armature|Armature|Armature|death|Armature|death", -1);
        bomb.create();
    }

    @Override
    public void render() {
        super.render();
        if (bomb.isSet) {
            bomb.render();
            if (!runAwayBomb()) canWalkThrough.remove((Character) 'B');

        }
    }

    boolean runAwayBomb() {
        if (Map.map[(int) (z / 200)][(int) (x / 200)] == 'B'
                || Map.map[(int) (z / 200)][(int) ((x + 150) / 200)] == 'B') return true;
        return Map.map[(int) ((z + 150) / 200)][(int) (x / 200)] == 'B'
                || Map.map[(int) ((z + 150) / 200)][(int) ((x + 150) / 200)] == 'B';
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    @Override
    public void update() {
        eventHandle();
    }

    public void createBomb() {
        int bombInstanceX = Math.round(x / 200) * 200;
        int bombInstanceZ = Math.round(z / 200) * 200;
        if (Map.map[(bombInstanceZ / Map.CELL_WIDTH)][(bombInstanceX / Map.CELL_WIDTH)] == ' ' && bombSet < maxBomb) {
            bombSet++;
            ModelInstance bombInstance = new ModelInstance(bomb.model);
            bomb.modelInstances.add(bombInstance);
            AnimationController bombAnimationController = new AnimationController(bombInstance);
            bomb.animationControllers.add(bombAnimationController);
            bombAnimationController.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
            Map.map[(bombInstanceZ / Map.CELL_WIDTH)][(bombInstanceX / Map.CELL_WIDTH)] = 'B';
            bombInstance.transform.setToTranslation(bombInstanceX, 0, bombInstanceZ);
            bomb.isSet = true;

            canWalkThrough.add('B');
            // ai đó sửa thành bomb nổi nhé
            bomb.setTimeout(() -> bomb.explode(bombInstance, bombAnimationController, bombInstanceX, bombInstanceZ), 3000);
        }
    }

    public void eventHandle() {
        if (MyGdxGame.getPlayer().isDead()) {
            animationController_dead.update(Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            animationController_run.update(Gdx.graphics.getDeltaTime());
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveLeft();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveRight();
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveUp();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveDown();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) createBomb();
        }
        else animationController_normal.update(Gdx.graphics.getDeltaTime());
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
