package com.loanhduc.game;

public class Wall extends StaticEntity {
    @Override
    public void create(float x, float y, float z) {
        path = "box.g3db";
        super.create(x, y, z);
    }
}
