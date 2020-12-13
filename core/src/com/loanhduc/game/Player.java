package com.loanhduc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.loanhduc.game.screen.GameOver;
import com.loanhduc.game.screen.MyGdxGame;
import com.loanhduc.game.util.SoundEffect;
import com.loanhduc.game.util.Utils;


public class Player extends MovingEntity {
    AnimationController animationController_run;
    AnimationController animationController_normal;
    AnimationController animationController_dead;
    private Bomb bomb;
    int bombSet;
    int maxBomb;
    int flameLength;
    boolean isDead;
    boolean isTeleport;
    int direction = 2;

    public Player(MyGdxGame myGdxGame) {
        super(myGdxGame);
        path = "bomberman.g3db";
        isDead = false;
        isTeleport = false;
        velocity = 10;
        bombSet = 0;
        maxBomb = 2;
        width = 150;
        height = 150;
        flameLength = 1;
        canWalkThrough.add(' ');
        canWalkThrough.add('x'); // portal
        canWalkThrough.add('b'); // bomb item
        canWalkThrough.add('s'); // speed item
        canWalkThrough.add('f'); // flame item
        canWalkThrough.add('1'); // enemy1
        canWalkThrough.add('2'); // enemy2
        canWalkThrough.add('F'); // Flame
        canWalkThrough.add('w');
        bomb = new Bomb(myGdxGame);
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
        if (collisionWithFire()) {
            isDead = true;
        }
        if (collisionWithEnemy()) {
            game.getPlayer().setDead(true);
        }
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
            game.explode.createExplode(bombInstanceX, bombInstanceZ, flameLength);
            canWalkThrough.add('B');
            Utils.setTimeout(() -> bomb.explode(bombInstance, bombAnimationController, bombInstanceX, bombInstanceZ), 3000);
        }
    }

    public void eventHandle() {
        if (game.getPlayer().isDead()) {
            animationController_dead.update(Gdx.graphics.getDeltaTime());
            SoundEffect.stopInGameSound();
            game.getGame().setScreen(new GameOver(game.getGame()));
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

    public boolean collisionWithEnemy() {
        for (MovingEntity enemy : game.getEnemy().getEnemies()) {
            if(collisionWith(enemy)) {
                return true;
            }
        }
        return false;
    }

    public char collisionWithItem() {
        for (ObjectInstance objectInstance : Items.getItemInstances()) {
            if (collisionWith(objectInstance)) {
                Items.removeInstance(objectInstance);
                SoundEffect.playEatItem();
                char c = objectInstance.getEntity();
                objectInstance.disappear();
                return c;
            }
        }
        return ' ';
    }

    public ObjectInstance collisionWithPortal() {
        for (ObjectInstance objectInstance : game.getPortal().rallyEntity) {
            if (collisionWith(objectInstance)) {
                return objectInstance;
            }
        }
        return null;
    }

    public boolean collisionWithWinPortal() {
        for (ObjectInstance objectInstance : game.getWinPortal().rallyEntity) {
            if (collisionWith(objectInstance)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void moveUp() {
        super.moveUp();
        checkCollision();
    }

    @Override
    public void moveDown() {
        super.moveDown();
        checkCollision();
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        checkCollision();
    }

    @Override
    public void moveRight() {
        super.moveRight();
        checkCollision();
    }

    public void checkCollision() {
        char entity = collisionWithItem();
        getItem(entity);
        ObjectInstance portal = collisionWithPortal();
        if (portal != null && !isTeleport) {
            teleport(portal);
        }
        if (portal == null) {
            isTeleport = false;
        }
    }

    private void teleport(ObjectInstance portal) {
        int indexOfPortal = game.getPortal().rallyEntity.indexOf(portal);
        ObjectInstance nextPortal;
        if (indexOfPortal != game.getPortal().rallyEntity.size() - 1) {
            nextPortal = game.getPortal().rallyEntity.get(indexOfPortal + 1);
        } else {
            nextPortal = game.getPortal().rallyEntity.get(0);
        }
        this.x = nextPortal.getPosition().x;
        this.z = nextPortal.getPosition().z;
        isTeleport = true;
    }

    private void getItem(char item) {
        if (item != ' ') {
            switch (item) {
                case 'b':
                    if (maxBomb < 6) maxBomb += 1;
                    System.out.println("Max Bomb = " + maxBomb);
                    break;
                case 'f':
                    if (flameLength < 6) flameLength += 1;
                    System.out.println("Flame Length = " + flameLength);
                    break;
                case 's':
                    if (velocity < 18) velocity += 2;
                    System.out.println("speed = " + velocity);
                    break;
            }
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Bomb getBomb() {
        return bomb;
    }
}
