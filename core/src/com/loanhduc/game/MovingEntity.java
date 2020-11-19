package com.loanhduc.game;

public abstract class MovingEntity extends Entity {
    protected float x;
    protected float y = 0;
    protected float z;
    protected float velocity;

    public abstract void move(float x, float y, float z);

    public void moveUp() {
        z -= velocity;
    }
    public void moveDown() {
        z += velocity;
    }
    public void moveLeft() {
        x -= velocity;
    }
    public void moveRight() {
        x += velocity;
    }

}
