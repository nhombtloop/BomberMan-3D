package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class Player extends MovingEntity {
    private ModelInstance playerInstance;
    public Bomb bomb = new Bomb();

    public Player() {
        path = "bomberman.g3db";
        velocity = 10;
        canWalkThrough.add(' ');
    }

    @Override
    public void create() {
        super.create();
        playerInstance = new ModelInstance(model);
        this.animationController = new AnimationController(playerInstance);
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
        bomb.create();
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(playerInstance, MyGdxGame.getEnvironment());
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

    public ModelInstance getPlayerInstance() {
        return playerInstance;
    }


    public void createBomb() {
        if (!bomb.hasBoomOnMap) {
            bomb.modelInstance = new ModelInstance(bomb.model);
            bomb.animationController = new AnimationController(bomb.modelInstance);
            bomb.animationController.setAnimation("Armature|Armature|Armature|idle|Armature|idle", -1);
            bomb.x = Math.round(x / 200) * 200;
            bomb.z = Math.round(z / 200) * 200;
            Map.map[(int) (bomb.z / 200)][(int) (bomb.x / 200)] = 'b';
            bomb.modelInstance.transform.setToTranslation(bomb.x, bomb.y, bomb.z);
            bomb.isSet = true;
            bomb.hasBoomOnMap = true;
            canWalkThrough.add('b');
            // ai đó sửa thành bomb nổi nhé
            bomb.setTimeout(() -> bomb.explode(), 3000);
        }
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

    @Override
    public void update() {
    }
}
