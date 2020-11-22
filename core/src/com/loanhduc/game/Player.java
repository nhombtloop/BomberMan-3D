package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class Player extends MovingEntity {
    private ModelInstance playerInstance;
    public Bomb bomb = new Bomb();
    private boolean[] direction = {true, true, true, true}; // up down left right


    public Player() {
        path = "model2.g3db";
        velocity = 10;
    }

    @Override
    public void create() {
        super.create();
        playerInstance = new ModelInstance(model);
        this.animationController = new AnimationController(playerInstance);
        this.animationController.setAnimation("mixamo.com", -1);
        for (int i = 0; i < Map.ROWS; i++) {
            for (int j = 0; j < Map.COLUMNS; j++) {
                if (Map.map[i][j] == 'p') {
                    x = j * Map.CELL_WIDTH;
                    z = i * Map.CELL_WIDTH;
                    break;
                }
            }
        }

        bomb.create();
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(playerInstance, MyGdxGame.getEnvironment());
        if(bomb.isSet) {
            bomb.render();
        }
        animationController.update(Gdx.graphics.getDeltaTime());
        if (bomb.isSet) {
            bomb.animationController.update(Gdx.graphics.getDeltaTime());
        }
    }

    public ModelInstance getPlayerInstance() {
        return playerInstance;
    }

    @Override
    public void moveTo(float x, float y, float z) {

    }

    public void createBomb() {
        bomb.modelInstance = new ModelInstance(bomb.model);
        bomb.animationController = new AnimationController(bomb.modelInstance);
        bomb.animationController.setAnimation("Armature|idle", -1);
        bomb.x = Math.round(x / 200) * 200;
        bomb.z = Math.round(z / 200) * 200;
        bomb.modelInstance.transform.setToTranslation(bomb.x, bomb.y , bomb.z);
        bomb.isSet = true;

        // ai đó sửa thành bomb nổi nhé
        bomb.setTimeout(() -> bomb.destroyBoom(),2000);

    }

    public void moveUp() {
        if (canMoveUp()) {
            z -= velocity;
        }
    }

    public void moveDown() {
        if (canMoveDown()) {
            z += velocity;
        }
    }
    public void moveLeft() {
        if (canMoveLeft()) {
            x -= velocity;
        }
    }
    public void moveRight() {
        if (canMoveRight()) {
            x += velocity;
        }
    }

    private boolean canMoveUp() {
        if (z < Map.CELL_WIDTH) return false;
        return (direction[0]);
    }

    private boolean canMoveDown() {
        if (z >= Map.CELL_WIDTH * (Map.ROWS - 2)) return false;
        return (direction[1]);
    }

    private boolean canMoveRight() {
        if (x >= Map.CELL_WIDTH * (Map.COLUMNS - 2)) return false;
        return (direction[3]);
    }

    private boolean canMoveLeft() {
        if (x < Map.CELL_WIDTH) return false;
        return (direction[2]);
    }

    @Override
    public void update() {
//        for (int i = 0; i < direction.length; i++) {
//            direction[i] = true;
//        }
//        if (Map.map[Math.round(z/200) - 1][Math.round(x/200)] == '#') direction[0] = false;
//        if (Map.map[Math.round(z/200) + 1][Math.round(x/200)] == '#') direction[1] = false;
//        if (Map.map[Math.round(z/200)][Math.round(x/200) - 1] == '#') direction[2] = false;
//        if (Map.map[Math.round(z/200)][Math.round(x/200) + 1] == '#') direction[3] = false;

    }
}
