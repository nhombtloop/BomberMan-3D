package com.loanhduc.game;

public abstract class MovingEntity extends Entity {
    protected float x;
    protected float y = 0;
    protected float z;
    protected float velocity;

    public abstract void move();

    public void moveUp() {
        z++;
    }
    public void moveDown() {
        z--;
    }
    public void moveLeft() {
        x--;
    }
    public void moveRight() {
        x++;
    }

}
