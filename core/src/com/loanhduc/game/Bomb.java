package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;


public class Bomb extends MovingEntity {
    ModelInstance modelInstance;
    boolean isSet = false;
    boolean hasBoomOnMap = false;

    public Bomb() {
        path = "bomb.g3db";
    }

    @Override
    public void update() {

    }

    public void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public void explode() {
        // destroy boom here
        System.out.println("destroy boom");
        hasBoomOnMap = false;
        Map.map[(int) (z / Map.CELL_WIDTH)][(int) (x / Map.CELL_WIDTH)] = ' ';
    }

    @Override
    public void render() {
        MyGdxGame.getModelBatch().render(modelInstance, MyGdxGame.getEnvironment());
    }
}
