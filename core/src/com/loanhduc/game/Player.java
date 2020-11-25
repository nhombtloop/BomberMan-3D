package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class Player extends MovingEntity {
    public Bomb bomb = new Bomb();

    public Player() {
        path = "bomberman.g3db";
        velocity = 10;
        canWalkThrough.add(' ');
        canWalkThrough.add('x'); // portal
        canWalkThrough.add('b'); // bomb item
        canWalkThrough.add('s'); // speed item
        canWalkThrough.add('f'); // flame item

    }

    @Override
    public void create() {
        super.create();
        modelInstance = new ModelInstance(model);
        this.animationController = new AnimationController(modelInstance);
        this.animationController.setAnimation("Armature|Armature|Armature|run|Armature|run", -1);
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
        modelInstance.transform.setToTranslation(x, y, z);
        bomb.create();
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(modelInstance, MyGdxGame.getEnvironment());
        animationController.update(Gdx.graphics.getDeltaTime());
        if (bomb.isSet) {
            bomb.render();
            if(!runAwayBomb()) canWalkThrough.remove((Character)'b');
            bomb.animationController.update(Gdx.graphics.getDeltaTime());
        }
    }

    boolean runAwayBomb() {
        if (Map.map[(int) (z / 200)][(int) (x / 200)] == 'b'
                || Map.map[(int) (z / 200)][(int) ((x + 150) / 200)] == 'b') return true;
        return Map.map[(int) ((z + 150) / 200)][(int) (x / 200)] == 'b'
                || Map.map[(int) ((z + 150) / 200)][(int) ((x + 150) / 200)] == 'b';
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }


    public void createBomb() {
        if (!bomb.hasBoomOnMap) {
            bomb.modelInstance = new ModelInstance(bomb.model);
            bomb.animationController = new AnimationController(bomb.modelInstance);
            bomb.animationController.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
            bomb.x = Math.round(x / 200) * 200;
            bomb.z = Math.round(z / 200) * 200;
            Map.map[(int) (bomb.z / Map.CELL_WIDTH)][(int) (bomb.x / Map.CELL_WIDTH)] = 'b';
            bomb.modelInstance.transform.setToTranslation(bomb.x, bomb.y, bomb.z);
            bomb.isSet = true;
            bomb.hasBoomOnMap = true;
            canWalkThrough.add('b');
            // ai đó sửa thành bomb nổi nhé
            bomb.setTimeout(() -> bomb.explode(), 3000);
        }
    }

    public void eventHandle() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveLeft();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveRight();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) moveUp();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveDown();
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) createBomb();
    }

    @Override
    public void update() {
        eventHandle();
    }
}
