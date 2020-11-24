package com.loanhduc.game;

import java.util.ArrayList;

public abstract class MovingEntity extends Entity {
    protected float x;
    protected float y = 0;
    protected float z;
    protected float velocity;
    protected ArrayList<Character> canWalkThrough = new ArrayList<>();

    boolean canMoveUp() {
        return canWalkThrough.contains(Map.map[(int) ((z - velocity) / 200)][(int) (x / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z - velocity) / 200)][(int) ((x + 150) / 200)]);
    }

    boolean canMoveDown() {
        return canWalkThrough.contains(Map.map[(int) ((z + 150 + velocity) / 200)][(int) (x / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + 150 + velocity) / 200)][(int) ((x + 150) / 200)]);
    }

    boolean canMoveLeft() {
        return canWalkThrough.contains(Map.map[(int) (z / 200)][(int) ((x - velocity) / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + 150) / 200)][(int) ((x - velocity) / 200)]);
    }

    boolean canMoveRight() {
        return canWalkThrough.contains(Map.map[(int) (z / 200)][(int) ((x + 150 + velocity) / 200)])
                && canWalkThrough.contains(Map.map[(int) ((z + 150) / 200)][(int) ((x + 150 + velocity) / 200)]);
    }

    public abstract void update();
}
