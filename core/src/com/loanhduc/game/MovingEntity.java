package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public abstract class MovingEntity extends Entity {
    protected AnimationController animationController;
    protected float x;
    protected float y = 0;
    protected float z;
    protected float velocity;

    public abstract void moveTo(float x, float y, float z);

    public abstract void update();
}
