package com.loanhduc.game;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public abstract class MovingEntity extends Entity {
    protected AnimationController animationController;

    protected float velocity;

    public abstract void move(float x, float y, float z);



}
